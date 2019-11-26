package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.APIUrl;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;

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

public class EstadisticasWebServiceRepository {

    Application application;
    public EstadisticasWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Estadisticas> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Estadisticas>> providesWebService(String id_usuario) {

     final MutableLiveData<List<Estadisticas>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         EstadisticasAPIService service = retrofit.create(EstadisticasAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.getEquipo(id_usuario).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 EstadisticasRoomDBRepository menuRoomDBRepository = new EstadisticasRoomDBRepository(application);
                 menuRoomDBRepository.insertEstadisticas(webserviceResponseList);
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


    private List<Estadisticas> parseJson(String response) {

        List<Estadisticas> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Estadisticas estadisticas = new Estadisticas();


                estadisticas.setEquipo_id(jsonNode.optString("equipo_id"));
                estadisticas.setNombre(jsonNode.optString("nombre"));
                estadisticas.setFoto(jsonNode.optString("foto"));
                estadisticas.setTemporada(jsonNode.optString("temporada"));
                estadisticas.setSemana(jsonNode.optString("semana"));
                estadisticas.setPuntaje_acumulado(jsonNode.optString("puntaje_acumulado"));
                estadisticas.setPuntaje_semanal(jsonNode.optString("puntaje_semanal"));
                estadisticas.setRetos_enviados(jsonNode.optString("retos_enviados"));
                estadisticas.setRetos_recibidos(jsonNode.optString("retos_recibidos"));
                estadisticas.setRetos_ganados(jsonNode.optString("retos_ganados"));
                estadisticas.setRetos_empatados(jsonNode.optString("retos_empatados"));
                estadisticas.setRetos_perdidos(jsonNode.optString("retos_perdidos"));
                estadisticas.setTorneos(jsonNode.optString("torneos"));
                //estadisticas.setPosicion(String.valueOf(Integer.parseInt(String.valueOf(i))));
                estadisticas.setPosicion(String.valueOf(i+1));


                apiResults.add(estadisticas);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}