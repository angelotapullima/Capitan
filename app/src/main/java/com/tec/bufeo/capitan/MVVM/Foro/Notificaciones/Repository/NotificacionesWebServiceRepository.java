package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Models.Notificaciones;
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

public class NotificacionesWebServiceRepository {

    Application application;
    public NotificacionesWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Notificaciones> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Notificaciones>> providesWebService(String id_usuario, String token) {

     final MutableLiveData<List<Notificaciones>> data = new MutableLiveData<>();

     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         NotificacionesAPIService service = retrofit.create(NotificacionesAPIService.class);
         //response = service.getEquipo(id,"true",token).execute().body();
         service.getEquipo(id_usuario,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repo notificaciones","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 NotificacionesRoomDBRepository notificacionesRoomDBRepository = new NotificacionesRoomDBRepository(application);
                 notificacionesRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<Notificaciones> parseJson(String response ) {

        List<Notificaciones> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();



            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Notificaciones notificaciones = new Notificaciones();

                notificaciones.setId_notificacion(jsonNode.optString("id_notificacion"));
                notificaciones.setId_user(jsonNode.optString("id_user"));
                notificaciones.setNotificacion_tipo(jsonNode.optString("notificacion_tipo"));
                notificaciones.setNotificacion_id_rel(jsonNode.optString("notificacion_id_rel"));
                notificaciones.setNotificacion_mensaje(jsonNode.optString("notificacion_mensaje"));
                notificaciones.setNotificacion_datetime(jsonNode.optString("fecha"));
                notificaciones.setNotificacion_estado(jsonNode.optString("estado"));



                apiResults.add(notificaciones);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}