package com.tec.bufeo.capitan.Activity.MisMovimientos.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.DetalleMovimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.Util.APIUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MovimientosWebServiceRepository {

    Application application;
    public MovimientosWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Movimientos> webserviceResponseList = new ArrayList<>();

    public LiveData<List<Movimientos>> providesWebService(String id_user,String token) {

        final MutableLiveData<List<Movimientos>> data = new MutableLiveData<>();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            MovimientosAPIService service = retrofit.create(MovimientosAPIService.class);
            //response = service.getEquipo(id,"true",token).execute().body();
            service.getEquipo(id_user,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Repository movimientos","Response::::"+response.body());
                    webserviceResponseList = parseJson(response.body());
                    MovimientosRoomDBRepository movimientosRoomDBRepository = new MovimientosRoomDBRepository(application);
                    movimientosRoomDBRepository.insertEquipos(webserviceResponseList);
                    data.setValue(webserviceResponseList);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Repository","Failed:::");
                }
            });
            //Defining retrofit api service


        }catch (Exception e){
            e.printStackTrace();
        }

        //  return retrofit.create(ResultModel.class);
        return  data;

    }


    private List<Movimientos> parseJson(String response ) {

        List<Movimientos> apiResults = new ArrayList<>();
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
                Movimientos movimientos = new Movimientos();

                //movimientos.setMovimiento_id(jsonNode.optString("pago_id"));
                String fechex = jsonNode.optString("solo_fecha");




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

                Movimientos movimientos2 = new Movimientos();

                String fecha = array.get(array.size()-contador);
                movimientos2.setFecha(fecha);
                movimientos2.setDetalle_movimientos(buildSubItemList(fecha,jsonArray));

                contador++;
                apiResults.add(movimientos2);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }



    JSONObject jsonNode2;
    DetalleMovimientos detalleMovimientos;

    private List<DetalleMovimientos> buildSubItemList(String dato,JSONArray array) {
        List<DetalleMovimientos> subItemList = new ArrayList<>();

        for (int z=0; z<array.length(); z++) {
            try {
                jsonNode2 = array.getJSONObject(z);

                if (dato.equals(jsonNode2.optString("solo_fecha"))){
                    detalleMovimientos = new DetalleMovimientos();

                    detalleMovimientos.setNro_operacion(jsonNode2.optString("nro_operacion"));
                    detalleMovimientos.setConcepto(jsonNode2.optString("concepto"));
                    detalleMovimientos.setTipo_pago(jsonNode2.optString("tipo_pago"));
                    detalleMovimientos.setMonto(jsonNode2.optString("monto"));
                    detalleMovimientos.setFecha(jsonNode2.optString("fecha"));
                    detalleMovimientos.setSolo_fecha(jsonNode2.optString("solo_fecha"));
                    detalleMovimientos.setInd(jsonNode2.optInt("ind"));
                    subItemList.add(detalleMovimientos);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }
}