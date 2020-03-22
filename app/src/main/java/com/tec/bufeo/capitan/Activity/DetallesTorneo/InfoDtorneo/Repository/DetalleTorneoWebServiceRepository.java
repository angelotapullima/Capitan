package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;
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

public class DetalleTorneoWebServiceRepository {
    //Preferences preferencesUser;
    Application application;
    public DetalleTorneoWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<DetalleTorneo> webserviceResponseList = new ArrayList<>();

    public LiveData<List<DetalleTorneo>> providesWebService(String id_torneo, String token) {

        final MutableLiveData<List<DetalleTorneo>> data = new MutableLiveData<>();

        String response = "";
        //String id = preferencesUser.getIdUsuarioPref();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            DetalleTorneoAPIService service = retrofit.create(DetalleTorneoAPIService.class);
            //  response = service.makeRequest().execute().body();
            service.savePost(id_torneo,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Repository","detalle torneo::::"+response.body());
                    webserviceResponseList = parseJson(response.body());
                    DetalleTorneoRoomDBRepository feedTorneoRoomDBRepository = new DetalleTorneoRoomDBRepository(application);
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
    private List<DetalleTorneo> parseJson(String response) {

        List<DetalleTorneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);



            JSONObject resultJSON = jsonObject.getJSONObject("results");



                DetalleTorneo foro = new DetalleTorneo();


                foro.setId_torneo(resultJSON.optString("id_torneo"));
                foro.setNombre_torneo(resultJSON.optString("nombre"));
                foro.setFoto_torneo(resultJSON.optString("foto"));
                foro.setDescripcion_torneo(resultJSON.optString("descripcion"));
                foro.setFecha_torneo(resultJSON.optString("fecha"));
                foro.setHora_torneo(resultJSON.optString("hora"));
                foro.setLugar_torneo(resultJSON.optString("lugar"));
                foro.setId_organizador_torneo(resultJSON.optString("id_organizador"));
                foro.setOrganizador_torneo(resultJSON.optString("organizador"));
                foro.setCosto_torneo(resultJSON.optString("costo"));
                foro.setEquipos_torneo(resultJSON.optString("equipos"));
                apiResults.add(foro);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}

