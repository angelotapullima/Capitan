package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models.EquiposTorneo;
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

public class EquiposTorneoWebServiceRepository {

    Application application;
    public EquiposTorneoWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<EquiposTorneo> webserviceResponseList = new ArrayList<>();

 public LiveData<List<EquiposTorneo>> providesWebService(String id_torneo) {

     final MutableLiveData<List<EquiposTorneo>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         EquiposTorneoAPIService service = retrofit.create(EquiposTorneoAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.getEquipo(id_torneo).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository","equipos por torneo::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 EquiposTorneoRoomDBRepository equiposTorneoRoomDBRepository = new EquiposTorneoRoomDBRepository(application);
                 equiposTorneoRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<EquiposTorneo> parseJson(String response) {

        List<EquiposTorneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                EquiposTorneo mMovieModel = new EquiposTorneo();


                mMovieModel = new EquiposTorneo();
                mMovieModel.setEquipo_id(jsonNode.optString("equipo_id"));
                mMovieModel.setEquipo_nombre(jsonNode.optString("nombre"));
                mMovieModel.setEquipo_foto(jsonNode.optString("foto"));
                mMovieModel.setCapitan_nombre(jsonNode.optString("capitan"));
                mMovieModel.setCapitan_id(jsonNode.optString("capitan_id"));
                mMovieModel.setEquipo_torneo_id(jsonNode.optString("id_torneo"));
                //mMovieModel.set(jsonNode.optString("capitan"));


                apiResults.add(mMovieModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}