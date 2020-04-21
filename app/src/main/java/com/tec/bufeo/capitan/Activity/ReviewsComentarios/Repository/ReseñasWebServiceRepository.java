package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;
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

public class ReseñasWebServiceRepository {

    Application application;
    public ReseñasWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Reseñas> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Reseñas>> providesWebService(String id_empresa, String token) {

     final MutableLiveData<List<Reseñas>> data = new MutableLiveData<>();

     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();


             ReseñasAPIService service = retrofit.create(ReseñasAPIService.class);

             service.getEquipo(id_empresa,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.e("Repository mis reservas","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body());
                     ReseñasRoomDBRepository movimientosRoomDBRepository = new ReseñasRoomDBRepository(application);
                     movimientosRoomDBRepository.insertReseñas(webserviceResponseList);
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


     return  data;

    }


    private List<Reseñas> parseJson(String response ) {

        List<Reseñas> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            //jsonObject = new JSONObject(response);
            JSONArray resultJSON = new JSONArray(response);

            int count = resultJSON.length();



            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Reseñas reseñas = new Reseñas();

                reseñas.setId_rating_empresa(jsonNode.optString("id_rating_empresa"));
                reseñas.setRating_empresa_valor(jsonNode.optString("rating_empresa_valor"));
                reseñas.setRating_empresa_comentario(jsonNode.optString("rating_empresa_comentario"));
                reseñas.setId_user(jsonNode.optString("id_user"));
                reseñas.setUser_nickname(jsonNode.optString("user_nickname"));
                reseñas.setUser_image(jsonNode.optString("user_image"));

                apiResults.add(reseñas);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }




}