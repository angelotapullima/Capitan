package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;
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

public class GoleadoresWebServiceRepository {



    //Preferences preferencesUser;
    Application application;
    public GoleadoresWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Goleadores> webserviceResponseList = new ArrayList<>();

    public LiveData<List<Goleadores>> providesWebService(final String id_torneo, String token) {

        final MutableLiveData<List<Goleadores>> data = new MutableLiveData<>();

        String response = "";
        //String id = preferencesUser.getIdUsuarioPref();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            APIServiceGoleadores service = retrofit.create(APIServiceGoleadores.class);
            //  response = service.makeRequest().execute().body();
            service.savePost(id_torneo,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("Repository","goleadores::::"+response.body());
                    webserviceResponseList = parseJson(response.body(),id_torneo);
                    GoleadoresRoomDBRepository goleadoresRoomDBRepository = new GoleadoresRoomDBRepository(application);
                    goleadoresRoomDBRepository.insertPosts(webserviceResponseList);
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




    private List<Goleadores> parseJson(String response,String id_torneo) {

        List<Goleadores> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);



            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Goleadores goleadores = new Goleadores();

                //mMovieModel.setId(object.getString("id"));
                goleadores.setId_torneo(id_torneo);
                goleadores.setUser_image(jsonNode.optString("user_image"));
                goleadores.setUser_nickname(jsonNode.optString("user_nickname"));
                goleadores.setEquipo_foto(jsonNode.optString("equipo_foto"));
                goleadores.setEquipo_nombre(jsonNode.optString("equipo_nombre"));
                goleadores.setGoles(jsonNode.optString("conteo"));


                apiResults.add(goleadores);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
