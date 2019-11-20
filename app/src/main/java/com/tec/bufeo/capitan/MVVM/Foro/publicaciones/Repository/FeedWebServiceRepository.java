package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;

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

 public LiveData<List<ModelFeed>> providesWebService() {

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

         //Defining retrofit api service
         APIServiceFeed service = retrofit.create(APIServiceFeed.class);
        //  response = service.makeRequest().execute().body();
         service.savePost("10").enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","feed::::"+response.body());
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
     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ModelFeed.class);
     return  data;

    }


    private List<ModelFeed> parseJson(String response) {

        List<ModelFeed> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                ModelFeed foro = new ModelFeed();

                //mMovieModel.setId(object.getString("id"));
                foro.setPublicacion_id(jsonNode.optString("id_publicacion"));
                foro.setUsuario_nombre(jsonNode.optString("usuario_nombre"));
                foro.setForo_titulo(jsonNode.optString("titulo"));
                foro.setForo_descripcion(jsonNode.optString("descripcion"));
                foro.setForo_foto(jsonNode.optString("foto"));
                foro.setForo_feccha(jsonNode.optString("fecha"));
                foro.setForo_tipo(jsonNode.optString("tipo"));
                foro.setCant_likes(jsonNode.optString("cant_likes"));
                foro.setDio_like(jsonNode.optString("dio_like"));
                foro.setCant_Comentarios(jsonNode.optString("cant_comentarios"));
                foro.setUsuario_foto(jsonNode.optString("usuario_foto"));
                foro.setOrden("0");

                apiResults.add(foro);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
