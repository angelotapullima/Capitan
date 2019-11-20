package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.APIUrl;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

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

public class OtrosEquiposWebServiceRepository {

    Application application;
    public OtrosEquiposWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Mequipos> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Mequipos>> providesWebService(String id_usuario) {

     final MutableLiveData<List<Mequipos>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         OtrosEquiposAPIService service = retrofit.create(OtrosEquiposAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.getEquipo(id_usuario).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 OtrosEquiposRoomDBRepository otrosEquiposRoomDBRepository = new OtrosEquiposRoomDBRepository(application);
                 otrosEquiposRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<Mequipos> parseJson(String response) {

        List<Mequipos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Mequipos mMovieModel = new Mequipos();


                mMovieModel = new Mequipos();
                mMovieModel.setEquipo_id(jsonNode.optString("equipo_id"));
                mMovieModel.setEquipo_nombre(jsonNode.optString("nombre"));
                mMovieModel.setEquipo_foto(jsonNode.optString("foto"));
                mMovieModel.setUsuario_nombre(jsonNode.optString("capitan"));
                mMovieModel.setCapitan_id(jsonNode.optString("capitan_id"));
                //mMovieModel.set(jsonNode.optString("capitan"));
                mMovieModel.setMi_equipo("no");


                apiResults.add(mMovieModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}