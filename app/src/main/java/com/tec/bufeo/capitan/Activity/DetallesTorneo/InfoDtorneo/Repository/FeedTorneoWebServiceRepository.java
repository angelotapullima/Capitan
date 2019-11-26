package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;

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

public class FeedTorneoWebServiceRepository {

    //Preferences preferencesUser;
    Application application;
    public FeedTorneoWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<FeedTorneo> webserviceResponseList = new ArrayList<>();

 public LiveData<List<FeedTorneo>> providesWebService(String id_usuario, String id_torneo,String limite_sup, String limite_inf, final String carga) {

     final MutableLiveData<List<FeedTorneo>> data = new MutableLiveData<>();

     String response = "";
     //String id = preferencesUser.getIdUsuarioPref();
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         APIServiceTorneo service = retrofit.create(APIServiceTorneo.class);
         //  response = service.makeRequest().execute().body();
         service.savePost(id_usuario,id_torneo,limite_sup,limite_inf).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository","feed::::"+response.body());
                 webserviceResponseList = parseJson(response.body(),carga);
                 FeedTorneoRoomDBRepository feedTorneoRoomDBRepository = new FeedTorneoRoomDBRepository(application);
                 feedTorneoRoomDBRepository.insertPosts(webserviceResponseList);
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



    String datosNuevos;
    private List<FeedTorneo> parseJson(String response, String carga) {

        List<FeedTorneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);

            String limite_sup2 = jsonObject.optString("limite_sup");
            String limite_inf2 = jsonObject.optString("limite_inf");
            int nuevos  = jsonObject.optInt("nuevos");


            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                FeedTorneo foro = new FeedTorneo();

                //mMovieModel.setId(object.getString("id"));
                foro.setPublicacion_id(jsonNode.optString("id_publicacion"));
                foro.setUsuario_nombre(jsonNode.optString("usuario_nombre"));
                foro.setUsuario_foto(jsonNode.optString("usuario_foto"));
                foro.setForo_titulo(jsonNode.optString("titulo"));
                foro.setForo_descripcion(jsonNode.optString("descripcion"));
                foro.setPublicacion_concepto(jsonNode.optString("concepto"));
                foro.setId_torneo(jsonNode.optString("id_torneo"));
                foro.setPublicacion_torneo(jsonNode.optString("torneo"));
                foro.setForo_foto(jsonNode.optString("foto"));
                foro.setForo_feccha(jsonNode.optString("fecha"));
                foro.setForo_tipo(jsonNode.optString("tipo"));
                foro.setCant_likes(jsonNode.optString("cant_likes"));
                foro.setDio_like(jsonNode.optString("dio_like"));
                foro.setCant_Comentarios(jsonNode.optString("cant_comentarios"));
                foro.setOrden("0");
                foro.setLimite_sup(limite_sup2);
                foro.setLimite_inf(limite_inf2);

                apiResults.add(foro);
            }
            datosNuevos= String.valueOf( nuevos);
            FeedTorneoRoomDBRepository feedTorneoRoomDBRepository = new FeedTorneoRoomDBRepository(application);
            feedTorneoRoomDBRepository.actualizarSup(limite_sup2);
            if (carga.equals("datos")){

                feedTorneoRoomDBRepository.ActualizarInf(limite_inf2);
                Log.e("datos","se hizo la luz");
            }
            feedTorneoRoomDBRepository.NuevosDatos(datosNuevos);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
