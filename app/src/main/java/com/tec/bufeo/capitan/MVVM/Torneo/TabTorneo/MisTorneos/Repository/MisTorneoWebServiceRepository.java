package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.Util.APIUrl;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

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

public class MisTorneoWebServiceRepository {

    Application application;
    public MisTorneoWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Torneo> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Torneo>> providesWebService(String id_usuario) {

     final MutableLiveData<List<Torneo>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         MisTorneoAPIService service = retrofit.create(MisTorneoAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.getRetos(id_usuario).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 MisTorneoRoomDBRepository misTorneoRoomDBRepository = new MisTorneoRoomDBRepository(application);
                 misTorneoRoomDBRepository.insertRetos(webserviceResponseList);
                 data.setValue(webserviceResponseList);

             }

             @Override
             public void onFailure(Call<String> call, Throwable t) {
                 Log.d("Repository","Failed:::");
             }
         });
     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<Torneo> parseJson(String response) {

        List<Torneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Torneo torneo = new Torneo();



                torneo.setTorneo_id(jsonNode.optString("id_torneo"));
                torneo.setTorneo_nombre(jsonNode.optString("nombre"));
                torneo.setTorneo_descripcion(jsonNode.optString("descripcion"));
                torneo.setTorneo_fecha(jsonNode.optString("fecha"));
                torneo.setTorneo_hora(jsonNode.optString("hora"));
                torneo.setTorneo_lugar(jsonNode.optString("lugar"));
                torneo.setTorneo_equipos(jsonNode.optString("equipos"));
                torneo.setTorneo_organizador(jsonNode.optString("organizador"));
                torneo.setUsuario_id(jsonNode.optString("id_organizador"));
                torneo.setMi_torneo("si");


                apiResults.add(torneo);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}