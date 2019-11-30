package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository;

import android.app.Application;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;
import com.tec.bufeo.capitan.Util.APIUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GruposWebServiceRepository {

    //Preferences preferencesUser;
    Application application;
    public GruposWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Grupos> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Grupos>> providesWebService(final String id_torneo) {

     final MutableLiveData<List<Grupos>> data = new MutableLiveData<>();

     String response = "";
     //String id = preferencesUser.getIdUsuarioPref();
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         APIServiceGrupos service = retrofit.create(APIServiceGrupos.class);
         //  response = service.makeRequest().execute().body();
         service.savePost(id_torneo).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository","feed::::"+response.body());
                 webserviceResponseList = parseJson(response.body(), id_torneo);
                 GruposRoomDBRepository gruposRoomDBRepository = new GruposRoomDBRepository(application);
                 gruposRoomDBRepository.insertPosts(webserviceResponseList);
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

     //  return retrofit.create(PublicacionesTorneo.class);
     return  data;

    }




    private List<Grupos> parseJson(String response,String id_tor) {

        List<Grupos> apiResults = new ArrayList<>();

        GruposRoomDBRepository gruposRoomDBRepository= new GruposRoomDBRepository(application);
        gruposRoomDBRepository.deleteAllGrupos();
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);

            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Grupos grupos = new Grupos();


                grupos.setId_grupo(jsonNode.optString("id_torneo_grupo"));
                grupos.setNombre_grupo(jsonNode.optString("grupo_nombre"));
                grupos.setId_torneo(id_tor);
                grupos.setEstado("0");



                apiResults.add(grupos);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }



}
