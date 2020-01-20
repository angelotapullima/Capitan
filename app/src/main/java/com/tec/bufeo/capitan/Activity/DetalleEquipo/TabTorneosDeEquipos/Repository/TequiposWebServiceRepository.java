package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository;

import android.app.Application;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;
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

public class TequiposWebServiceRepository {

    Application application;
    public TequiposWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<TorneosDeEquipos> webserviceResponseList = new ArrayList<>();

    public LiveData<List<TorneosDeEquipos>> providesWebService(String id_equipo, String token) {

        final MutableLiveData<List<TorneosDeEquipos>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            //Defining retrofit api service
            APIServiceTequipos service = retrofit.create(APIServiceTequipos.class);
            //  response = service.makeRequest().execute().body();
            service.getRetos(id_equipo,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Repository torneoEquipo","Response::::"+response.body());
                    webserviceResponseList = parseJson(response.body());
                    TequiposRoomDbRepository tequiposRoomDbRepository = new TequiposRoomDbRepository(application);
                    tequiposRoomDbRepository.insertTorneosEquipos(webserviceResponseList);
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


    private List<TorneosDeEquipos> parseJson(String response) {

        List<TorneosDeEquipos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                TorneosDeEquipos torneo = new TorneosDeEquipos();


                TequiposRoomDbRepository tequiposRoomDbRepository =new TequiposRoomDbRepository(application);
                tequiposRoomDbRepository.deleteAllTorneosEquipos();

                torneo.setId_torneo(jsonNode.optInt("id_torneo"));
                torneo.setEquipo_id(jsonNode.optString("equipo_id"));
                torneo.setNombre(jsonNode.optString("nombre"));
                torneo.setFoto(jsonNode.optString("foto"));
                torneo.setFecha(jsonNode.optString("fecha"));
                torneo.setLugar(jsonNode.optString("lugar"));
                torneo.setOrganizador(jsonNode.optString("organizador"));


                apiResults.add(torneo);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}
