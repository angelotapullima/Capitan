package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository;

import android.app.Application;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;
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

public class EequiposWebServiceRepository {

    Application application;
    public EequiposWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<EstadisticasDeEquipos> webserviceResponseList = new ArrayList<>();

    public LiveData<List<EstadisticasDeEquipos>> providesWebService(String id_equipo, String token) {

        final MutableLiveData<List<EstadisticasDeEquipos>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            //Defining retrofit api service
            APIServiceEequipos service = retrofit.create(APIServiceEequipos.class);
            //  response = service.makeRequest().execute().body();
            service.getRetos(id_equipo,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Repository estadistica","Response::::"+response.body());
                    webserviceResponseList = parseJson(response.body());
                    EequiposRoomDbRepository tequiposRoomDbRepository = new EequiposRoomDbRepository(application);
                    tequiposRoomDbRepository.insertEstadisticasEquipos(webserviceResponseList);
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


    private List<EstadisticasDeEquipos> parseJson(String response) {

        List<EstadisticasDeEquipos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");


            EequiposRoomDbRepository eequiposRoomDbRepository =  new EequiposRoomDbRepository(application);
            eequiposRoomDbRepository.DeleteAllEstadisticasEquipos();

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                EstadisticasDeEquipos estadisticasDeEquipos = new EstadisticasDeEquipos();


                estadisticasDeEquipos.setEquipo_id(jsonNode.optInt("equipo_id"));
                estadisticasDeEquipos.setNombre(jsonNode.optString("nombre"));
                estadisticasDeEquipos.setFoto(jsonNode.optString("foto"));
                estadisticasDeEquipos.setTemporada(jsonNode.optString("temporada"));
                estadisticasDeEquipos.setSemana(jsonNode.optString("semana"));
                estadisticasDeEquipos.setPuntaje_acumulado(jsonNode.optString("puntaje_acumulado"));
                estadisticasDeEquipos.setPuntaje_semanal(jsonNode.optString("puntaje_semanal"));
                estadisticasDeEquipos.setRetos_enviados(jsonNode.optString("retos_enviados"));
                estadisticasDeEquipos.setRetos_recibidos(jsonNode.optString("retos_recibidos"));
                estadisticasDeEquipos.setRetos_ganados(jsonNode.optString("retos_ganados"));
                estadisticasDeEquipos.setRetos_empatados(jsonNode.optString("retos_empatados"));
                estadisticasDeEquipos.setRetos_perdidos(jsonNode.optString("retos_perdidos"));
                estadisticasDeEquipos.setTorneos(jsonNode.optString("torneos"));




                apiResults.add(estadisticasDeEquipos);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}
