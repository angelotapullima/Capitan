package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.DetalleReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Util.APIUrl;
import com.tec.bufeo.capitan.Util.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MisReservasWebServiceRepository {

    Preferences preferences;
    Application application;
    public MisReservasWebServiceRepository(Application application){
        this.application = application;
        preferences = new Preferences(application);
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<MisReservas> webserviceResponseList = new ArrayList<>();

    public LiveData<List<MisReservas>> providesWebService(String id_user, String token,String tipo) {

        final MutableLiveData<List<MisReservas>> data = new MutableLiveData<>();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();


            if (tipo.equals("normal")){
                MisReservasAPIService service = retrofit.create(MisReservasAPIService.class);
                //response = service.getEquipo(id,"true",token).execute().body();
                service.getEquipo(id_user,"true",token).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("Repository mis reservas","Response::::"+response.body());
                        webserviceResponseList = parseJson(response.body());
                        MisReservasRoomDBRepository movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
                        movimientosRoomDBRepository.insertEquipos(webserviceResponseList);
                        data.setValue(webserviceResponseList);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Repository","Failed:::");
                    }
                });
            }else{
                MisReservasNotificacionAPIService service = retrofit.create(MisReservasNotificacionAPIService.class);
                //response = service.getEquipo(id,"true",token).execute().body();
                service.getEquipo(id_user,"true",token).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("Repository mis reservas","Response::::"+response.body());
                        webserviceResponseList = parseJsonNotificacion(response.body());
                        MisReservasRoomDBRepository movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
                        movimientosRoomDBRepository.insertEquipos(webserviceResponseList);
                        data.setValue(webserviceResponseList);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Repository","Failed:::");
                    }
                });
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        //  return retrofit.create(ResultModel.class);
        return  data;

    }


    private List<MisReservas> parseJson(String response ) {

        List<MisReservas> apiResults = new ArrayList<>();
        List<String> array = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");
            jsonArray = jsonObject.getJSONArray("results");


            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                MisReservas misReservas = new MisReservas();
                String fechex = formatearFecha(jsonNode.optString("pago_date"));
                //misReservas.setFecha_reserva(fechex);

                array.add(fechex);

            }

            //Creamos un objeto HashSet
            HashSet hs = new HashSet();
            //Lo cargamos con los valores del array, esto hace quite los repetidos
            hs.addAll(array);
            //Limpiamos el array
            array.clear();
            //Agregamos los valores sin repetir
            array.addAll(hs);

            Collections.sort(array);


            int contador =1;
            for (int x = 0; x < array.size(); x++) {

                MisReservas misReservas2 = new MisReservas();

                String fecha = array.get(array.size()-contador).toString();
                misReservas2.setFecha_reserva(fecha);
                misReservas2.setDetalle_reservas(buildSubItemList(fecha,jsonArray));

                contador++;
                apiResults.add(misReservas2);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }



    JSONObject jsonNode2;
    DetalleReservas detalleReservas;

    private List<DetalleReservas> buildSubItemList(String dato,JSONArray array) {
        List<DetalleReservas> subItemList = new ArrayList<>();

        for (int z=0; z<array.length(); z++) {
            try {
                jsonNode2 = array.getJSONObject(z);

                if (dato.equals(formatearFecha(jsonNode2.optString("pago_date")))){
                    detalleReservas = new DetalleReservas();

                    detalleReservas.setId_reserva(jsonNode2.optString("reserva_id"));
                    detalleReservas.setPago_date(dato);
                    detalleReservas.setCancha_id(jsonNode2.optString("cancha_id"));
                    detalleReservas.setReserva_tipopago(jsonNode2.optString("reserva_tipopago"));
                    detalleReservas.setPago_id(jsonNode2.optString("pago_id"));
                    detalleReservas.setReserva_nombre(jsonNode2.optString("reserva_nombre"));
                    detalleReservas.setReserva_fecha(jsonNode2.optString("reserva_fecha"));
                    detalleReservas.setReserva_hora(jsonNode2.optString("reserva_hora"));
                    detalleReservas.setReserva_pago1(jsonNode2.optString("reserva_pago1"));
                    detalleReservas.setReserva_pago1_date(jsonNode2.optString("reserva_pago1_date"));
                    detalleReservas.setReserva_pago2(jsonNode2.optString("reserva_pago2"));
                    detalleReservas.setReserva_pago2_date(jsonNode2.optString("reserva_pago2_date"));
                    detalleReservas.setReserva_estado(jsonNode2.optString("reserva_estado"));
                    detalleReservas.setEmpresa_id(jsonNode2.optString("empresa_id"));
                    detalleReservas.setPago_total(jsonNode2.optString("pago_total"));
                    detalleReservas.setPago_tipo(jsonNode2.optString("pago_tipo"));
                    detalleReservas.setCancha_nombre(jsonNode2.optString("cancha_nombre"));
                    detalleReservas.setEmpresa_nombre(jsonNode2.optString("empresa_nombre"));
                    detalleReservas.setEmpresa_direccion(jsonNode2.optString("empresa_direccion"));
                    detalleReservas.setEmpresa_coord_x(jsonNode2.optString("empresa_coord_x"));
                    detalleReservas.setEmpresa_coord_y(jsonNode2.optString("empresa_coord_y"));
                    detalleReservas.setEmpresa_telefono_1(jsonNode2.optString("empresa_telefono_1"));
                    detalleReservas.setEmpresa_telefono_2(jsonNode2.optString("empresa_telefono_2"));
                    detalleReservas.setEmpresa_descripcion(jsonNode2.optString("empresa_descripcion"));
                    detalleReservas.setEmpresa_valoracion(jsonNode2.optString("empresa_valoracion"));
                    detalleReservas.setPago_comision(jsonNode2.optString("pago_comision"));
                    detalleReservas.setTransferencia_u_e_nro_operacion(jsonNode2.optString("transferencia_u_e_nro_operacion"));
                    detalleReservas.setNombre_user(preferences.getPersonName() + " " + preferences.getPersonSurname());
                    detalleReservas.setReserva_tipo("cliente");
                    subItemList.add(detalleReservas);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }


    private String formatearFecha(String dato){

        String salida ="",separador;
        String[] resultado;

        separador = Pattern.quote(" ");
        resultado = dato.split(separador);
        salida = resultado[0];
        return  salida;
    }


    private List<MisReservas> parseJsonNotificacion(String response ) {


        List<MisReservas> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonNode = jsonObject.getJSONObject("results");

            List<DetalleReservas> subItemList = new ArrayList<>();

            MisReservas misReservas = new MisReservas();
            detalleReservas =  new DetalleReservas();

            detalleReservas.setId_reserva(jsonNode.optString("reserva_id"));
            detalleReservas.setCancha_id(jsonNode.optString("cancha_id"));
            detalleReservas.setReserva_tipopago(jsonNode.optString("reserva_tipopago"));
            detalleReservas.setPago_id(jsonNode.optString("pago_id"));
            detalleReservas.setReserva_nombre(jsonNode.optString("reserva_nombre"));
            detalleReservas.setReserva_fecha(jsonNode.optString("reserva_fecha"));
            detalleReservas.setReserva_hora(jsonNode.optString("reserva_hora"));
            detalleReservas.setReserva_pago1(jsonNode.optString("reserva_pago1"));
            detalleReservas.setReserva_pago1_date(jsonNode.optString("reserva_pago1_date"));
            detalleReservas.setReserva_pago2(jsonNode.optString("reserva_pago2"));
            detalleReservas.setReserva_pago2_date(jsonNode.optString("reserva_pago2_date"));
            detalleReservas.setReserva_estado(jsonNode.optString("reserva_estado"));
            detalleReservas.setEmpresa_id(jsonNode.optString("empresa_id"));
            detalleReservas.setCancha_nombre(jsonNode.optString("cancha_nombre"));
            detalleReservas.setEmpresa_nombre(jsonNode.optString("empresa_nombre"));
            detalleReservas.setEmpresa_direccion(jsonNode.optString("empresa_direccion"));
            detalleReservas.setEmpresa_coord_x(jsonNode.optString("empresa_coord_x"));
            detalleReservas.setEmpresa_coord_y(jsonNode.optString("empresa_coord_y"));
            detalleReservas.setEmpresa_telefono_1(jsonNode.optString("empresa_telefono_1"));
            detalleReservas.setEmpresa_telefono_2(jsonNode.optString("empresa_telefono_2"));
            detalleReservas.setEmpresa_descripcion(jsonNode.optString("empresa_descripcion"));
            detalleReservas.setEmpresa_valoracion(jsonNode.optString("empresa_valoracion"));
            detalleReservas.setNombre_user(jsonNode.optString("nombre_user"));
            detalleReservas.setReserva_tipo("empresa");

            subItemList.add(detalleReservas);

            misReservas.setFecha_reserva(formatearFecha(jsonNode.optString("reserva_pago1_date")));
            misReservas.setDetalle_reservas(subItemList);
            apiResults.add(misReservas);





        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}