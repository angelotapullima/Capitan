package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.Util.APIUrl;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;

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

public class RetosWebServiceRepository {

    Application application;

    public RetosWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Retos> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Retos>> providesWebService(String id_usuario) {

     final MutableLiveData<List<Retos>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         RetosAPIService service = retrofit.create(RetosAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.getRetos(id_usuario).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 RetosRoomDBRepository retosRoomDBRepository = new RetosRoomDBRepository(application);
                 retosRoomDBRepository.insertRetos(webserviceResponseList);
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


    private List<Retos> parseJson(String response) {

        List<Retos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Retos retos = new Retos();



                retos.setRetos_id(jsonNode.optString("id_reto"));
                retos.setRetador_id(jsonNode.optString("equipo_id_1"));
                retos.setRetado_id(jsonNode.optString("equipo_id_2"));
                retos.setRetos_nombre_retador(jsonNode.optString("nombre_1"));
                retos.setRetos_nombre_retado(jsonNode.optString("nombre_2"));
                retos.setRetos_foto_retador(jsonNode.optString("foto_1"));
                retos.setRetos_foto_retado(jsonNode.optString("foto_2"));
                retos.setRetos_fecha(jsonNode.optString("fecha"));
                retos.setRetos_hora(jsonNode.optString("hora"));
                retos.setRetos_lugar(jsonNode.optString("lugar"));
                retos.setRetos_estado(jsonNode.optString("estado"));
                retos.setRetos_respuesta(jsonNode.optString("respuesta"));
                //retos.setRetos_respuesta("2");


                apiResults.add(retos);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}