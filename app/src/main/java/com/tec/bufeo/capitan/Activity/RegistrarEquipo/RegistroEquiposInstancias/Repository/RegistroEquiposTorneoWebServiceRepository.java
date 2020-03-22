package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository;

import android.app.Application;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models.RegistroEquiposTorneo;
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

public class RegistroEquiposTorneoWebServiceRepository {

    Application application;
    public RegistroEquiposTorneoWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<RegistroEquiposTorneo> webserviceResponseList = new ArrayList<>();

 public LiveData<List<RegistroEquiposTorneo>> providesWebService(String id_torneo,String token) {

     final MutableLiveData<List<RegistroEquiposTorneo>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         RegistroEquiposTorneoAPIService service = retrofit.create(RegistroEquiposTorneoAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.getEquipo(id_torneo,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","equipos por torneo::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 RegistroEquiposTorneoRoomDBRepository registroEquiposTorneoRoomDBRepository = new RegistroEquiposTorneoRoomDBRepository(application);
                 registroEquiposTorneoRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<RegistroEquiposTorneo> parseJson(String response) {

        List<RegistroEquiposTorneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        RegistroEquiposTorneoRoomDBRepository registroEquiposTorneoRoomDBRepository = new RegistroEquiposTorneoRoomDBRepository(application);
        registroEquiposTorneoRoomDBRepository.deleteAllEquipos();
        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                RegistroEquiposTorneo mMovieModel = new RegistroEquiposTorneo();


                mMovieModel.setEquipo_id(jsonNode.optString("equipo_id"));
                mMovieModel.setEquipo_nombre(jsonNode.optString("nombre"));
                mMovieModel.setEquipo_foto(jsonNode.optString("foto"));
                mMovieModel.setCapitan_nombre(jsonNode.optString("capitan"));
                mMovieModel.setLocal("0");
                mMovieModel.setVisitante("0");
                mMovieModel.setEstado_equipo("0");


                //mMovieModel.set(jsonNode.optString("capitan"));


                apiResults.add(mMovieModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}