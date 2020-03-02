package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
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

public class FeedWebServiceRepository {

    //Preferences preferencesUser;
    Application application;
    public FeedWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<ModelFeed> webserviceResponseList = new ArrayList<>();

 public LiveData<List<ModelFeed>> providesWebService(String id_usuario, String limite_sup, String limite_inf,final String token) {

     final MutableLiveData<List<ModelFeed>> data = new MutableLiveData<>();

     String response = "";
     //String id = preferencesUser.getIdUsuarioPref();
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         APIServiceFeed service = retrofit.create(APIServiceFeed.class);
         //  response = service.makeRequest().execute().body();
         service.savePost(id_usuario,limite_sup,"true",token,limite_inf).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository","feed::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
                 feedRoomDBRepository.insertPosts(webserviceResponseList);
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
    private List<ModelFeed> parseJson(String response) {

        List<ModelFeed> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;


        try {
            jsonObject = new JSONObject(response);

            String limite_sup2 = jsonObject.optString("limite_sup");
            String limite_inf2 = jsonObject.optString("limite_inf");
            int nuevos  = jsonObject.optInt("nuevos");


            JSONArray resultJSON = jsonObject.getJSONArray("results");


            FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
            feedRoomDBRepository.deleteAllFeed();
            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                ModelFeed foro = new ModelFeed();

                //mMovieModel.setId(object.getString("id"));
                foro.setPublicacion_id(jsonNode.optString("id_publicacion"));
                foro.setUsuario_nombre(jsonNode.optString("usuario_nombre"));
                foro.setUsuario_foto(jsonNode.optString("usuario_foto"));
                foro.setForo_titulo(jsonNode.optString("titulo"));
                foro.setUsuario_id(jsonNode.optString("id_usuario"));
                foro.setForo_descripcion(jsonNode.optString("descripcion"));
                foro.setPublicacion_concepto(jsonNode.optString("concepto"));
                foro.setId_torneo(jsonNode.optString("id_torneo"));
                foro.setPublicacion_torneo(jsonNode.optString("torneo"));
                foro.setForo_foto(jsonNode.optString("foto"));
                foro.setForo_feccha(jsonNode.optString("fecha"));
                foro.setForo_tipo(jsonNode.optString("tipo"));
                foro.setCant_likes(jsonNode.optString("cant_likes"));
                foro.setDio_like(jsonNode.optString("dio_like"));
                foro.setEstado(jsonNode.optString("estado"));
                foro.setCant_Comentarios(jsonNode.optString("cant_comentarios"));
                foro.setTorneo_foto(jsonNode.optString("torneo_imagen"));
                foro.setOrden("0");
                foro.setLimite_sup(limite_sup2);
                foro.setLimite_inf(limite_inf2);


                apiResults.add(foro);
            }
            datosNuevos= String.valueOf( nuevos);
            /*FeedRoomDBRepository feedTorneoRoomDBRepository = new FeedRoomDBRepository(application);
            feedTorneoRoomDBRepository.actualizarSup(limite_sup2);
            if (carga.equals("datos")){

                feedTorneoRoomDBRepository.ActualizarInf(limite_inf2);
                Log.e("datos","se hizo la luz");
            }*/
            feedRoomDBRepository.NuevosDatos(datosNuevos);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
