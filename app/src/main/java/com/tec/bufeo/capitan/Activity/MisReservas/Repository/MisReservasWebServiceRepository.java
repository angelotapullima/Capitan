package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Util.APIUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MisReservasWebServiceRepository {

    Application application;
    public MisReservasWebServiceRepository(Application application){
        this.application = application;
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
         }else if(tipo.equals("notificacion")){
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

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            /*MisReservasRoomDBRepository movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
            movimientosRoomDBRepository.deleteAllEquipos();*/

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                MisReservas misReservas = new MisReservas();

                misReservas.setId_reserva(jsonNode.optString("reserva_id"));
                misReservas.setCancha_id(jsonNode.optString("cancha_id"));
                misReservas.setReserva_tipopago(jsonNode.optString("reserva_tipopago"));
                misReservas.setPago_id(jsonNode.optString("pago_id"));
                misReservas.setReserva_nombre(jsonNode.optString("reserva_nombre"));
                misReservas.setReserva_fecha(jsonNode.optString("reserva_fecha"));
                misReservas.setReserva_hora(jsonNode.optString("reserva_hora"));
                misReservas.setReserva_pago1(jsonNode.optString("reserva_pago1"));
                misReservas.setReserva_pago1_date(jsonNode.optString("reserva_pago1_date"));
                misReservas.setReserva_pago2(jsonNode.optString("reserva_pago2"));
                misReservas.setReserva_pago2_date(jsonNode.optString("reserva_pago2_date"));
                misReservas.setReserva_estado(jsonNode.optString("reserva_estado"));
                misReservas.setEmpresa_id(jsonNode.optString("empresa_id"));
                misReservas.setCancha_nombre(jsonNode.optString("cancha_nombre"));
                misReservas.setEmpresa_nombre(jsonNode.optString("empresa_nombre"));
                misReservas.setEmpresa_direccion(jsonNode.optString("empresa_direccion"));
                misReservas.setEmpresa_coord_x(jsonNode.optString("empresa_coord_x"));
                misReservas.setEmpresa_coord_y(jsonNode.optString("empresa_coord_y"));
                misReservas.setEmpresa_telefono_1(jsonNode.optString("empresa_telefono_1"));
                misReservas.setEmpresa_telefono_2(jsonNode.optString("empresa_telefono_2"));
                misReservas.setEmpresa_descripcion(jsonNode.optString("empresa_descripcion"));
                misReservas.setEmpresa_valoracion(jsonNode.optString("empresa_valoracion"));
                misReservas.setReserva_tipo("cliente");


                apiResults.add(misReservas);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }


    private List<MisReservas> parseJsonNotificacion(String response ) {

        List<MisReservas> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonNode = jsonObject.getJSONObject("results");


                MisReservas misReservas = new MisReservas();

                misReservas.setId_reserva(jsonNode.optString("reserva_id"));
                misReservas.setCancha_id(jsonNode.optString("cancha_id"));
                misReservas.setReserva_tipopago(jsonNode.optString("reserva_tipopago"));
                misReservas.setPago_id(jsonNode.optString("pago_id"));
                misReservas.setReserva_nombre(jsonNode.optString("reserva_nombre"));
                misReservas.setReserva_fecha(jsonNode.optString("reserva_fecha"));
                misReservas.setReserva_hora(jsonNode.optString("reserva_hora"));
                misReservas.setReserva_pago1(jsonNode.optString("reserva_pago1"));
                misReservas.setReserva_pago1_date(jsonNode.optString("reserva_pago1_date"));
                misReservas.setReserva_pago2(jsonNode.optString("reserva_pago2"));
                misReservas.setReserva_pago2_date(jsonNode.optString("reserva_pago2_date"));
                misReservas.setReserva_estado(jsonNode.optString("reserva_estado"));
                misReservas.setEmpresa_id(jsonNode.optString("empresa_id"));
                misReservas.setCancha_nombre(jsonNode.optString("cancha_nombre"));
                misReservas.setEmpresa_nombre(jsonNode.optString("empresa_nombre"));
                misReservas.setEmpresa_direccion(jsonNode.optString("empresa_direccion"));
                misReservas.setEmpresa_coord_x(jsonNode.optString("empresa_coord_x"));
                misReservas.setEmpresa_coord_y(jsonNode.optString("empresa_coord_y"));
                misReservas.setEmpresa_telefono_1(jsonNode.optString("empresa_telefono_1"));
                misReservas.setEmpresa_telefono_2(jsonNode.optString("empresa_telefono_2"));
                misReservas.setEmpresa_descripcion(jsonNode.optString("empresa_descripcion"));
                misReservas.setEmpresa_valoracion(jsonNode.optString("empresa_valoracion"));
                misReservas.setNombre_user(jsonNode.optString("nombre_user"));
                misReservas.setReserva_tipo("empresa");


                apiResults.add(misReservas);





        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}