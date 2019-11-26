package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;


import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;

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

public class VersusWebServiceRepository {

    Application application;
    public VersusWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Versus> webserviceResponseList = new ArrayList<>();


 public LiveData<List<Versus>> providesWebService(String id) {

     final MutableLiveData<List<Versus>> data = new MutableLiveData<>();


     String response = "";
     String url = "https://www.pousperu.com/";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(url)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         VersusAPIService service = retrofit.create(VersusAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.makeRequest(id).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 VersusRoomDBRepository versusRoomDBRepository = new VersusRoomDBRepository(application);
                 versusRoomDBRepository.insertVersus(webserviceResponseList);
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


    private List<Versus> parseJson(String response) {

        List<Versus> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Versus versus = new Versus();


                versus.setVersus_equipo1(jsonNode.getString("equipo1"));
                versus.setVersus_foto_equipo1(jsonNode.getString("foto_equipo_1"));
                versus.setVersus_jugador1a(jsonNode.getString("jugador1a"));
                versus.setVersus_jugador1b(jsonNode.getString("jugador1b"));
                versus.setVersus_jugador1c(jsonNode.getString("jugador1c"));
                versus.setVersus_jugador1d(jsonNode.getString("jugador1d"));
                versus.setVersus_jugador1e(jsonNode.getString("jugador1e"));
                versus.setVersus_jugador1f(jsonNode.getString("jugador1f"));
                versus.setVersus_jugador1g(jsonNode.getString("jugador1g"));
                versus.setVersus_jugador1h(jsonNode.getString("jugador1h"));
                versus.setVersus_equipo2(jsonNode.getString("equipo2"));
                versus.setVersus_foto_equipo2(jsonNode.getString("foto_equipo_2"));
                versus.setVersus_jugador2a(jsonNode.getString("jugador2a"));
                versus.setVersus_jugador2b(jsonNode.getString("jugador2b"));
                versus.setVersus_jugador2c(jsonNode.getString("jugador2c"));
                versus.setVersus_jugador2d(jsonNode.getString("jugador2d"));
                versus.setVersus_jugador2e(jsonNode.getString("jugador2e"));
                versus.setVersus_jugador2f(jsonNode.getString("jugador2f"));
                versus.setVersus_jugador2g(jsonNode.getString("jugador2g"));
                versus.setVersus_jugador2h(jsonNode.getString("jugador2h"));
                versus.setVersus_fecha(jsonNode.getString("fecha"));
                versus.setVersus_lugar(jsonNode.getString("lugar"));

                apiResults.add(versus);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
