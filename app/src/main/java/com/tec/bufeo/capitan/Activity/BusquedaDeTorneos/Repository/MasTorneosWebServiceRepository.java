package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Models.MasTorneos;
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

public class MasTorneosWebServiceRepository {

    Application application;
    public MasTorneosWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<MasTorneos> webserviceResponseList = new ArrayList<>();

 public LiveData<List<MasTorneos>> providesWebService( String tipo,String id_usuario, String dato,final String token) {

     final MutableLiveData<List<MasTorneos>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();


         if (tipo.equals("carga")){
             MasTorneosAPIService service = retrofit.create(MasTorneosAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(id_usuario,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.e("listar mas torneos","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body());
                     MasTorneosRoomDBRepository menuRoomDBRepository = new MasTorneosRoomDBRepository(application);
                     menuRoomDBRepository.insertEquipos(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }else{
             MasTorneosBusquedaAPIService service = retrofit.create(MasTorneosBusquedaAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(dato,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.e("buscar torneos","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body());
                     MasTorneosRoomDBRepository menuRoomDBRepository = new MasTorneosRoomDBRepository(application);
                     menuRoomDBRepository.insertEquipos(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });

         }



         //Defining retrofit api service


     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<MasTorneos> parseJson(String response ) {

        List<MasTorneos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");


            MasTorneosRoomDBRepository masTorneosRoomDBRepository = new MasTorneosRoomDBRepository(application);
            masTorneosRoomDBRepository.deleteAllEquipos();
            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                MasTorneos torneo = new MasTorneos();
                torneo.setTorneo_id(jsonNode.optString("id_torneo"));
                torneo.setTorneo_nombre(jsonNode.optString("nombre"));
                torneo.setTorneo_descripcion(jsonNode.optString("descripcion"));
                torneo.setTorneo_fecha(jsonNode.optString("fecha"));
                torneo.setTorneo_hora(jsonNode.optString("hora"));
                torneo.setFoto_torneo(jsonNode.optString("foto"));
                torneo.setTorneo_lugar(jsonNode.optString("lugar"));
                torneo.setTorneo_equipos(jsonNode.optString("equipos"));
                torneo.setTorneo_organizador(jsonNode.optString("organizador"));
                torneo.setUsuario_id(jsonNode.optString("id_organizador"));




                apiResults.add(torneo);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}