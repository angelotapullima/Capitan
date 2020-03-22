package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
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

public class BusquedaEquiposWebServiceRepository {

    Application application;
    public BusquedaEquiposWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<BusquedaEquipos> webserviceResponseList = new ArrayList<>();

 public LiveData<List<BusquedaEquipos>> providesWebService(final String tipo, String limite_sup, String limite_inf, String dato, final String token) {

     final MutableLiveData<List<BusquedaEquipos>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         if (tipo.equals("carga")){
             EquiposEnBAPIService service = retrofit.create(EquiposEnBAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(limite_sup,limite_inf,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.d("buscar equipos","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo);
                     BusquedaEquiposRoomDBRepository menuRoomDBRepository = new BusquedaEquiposRoomDBRepository(application);
                     menuRoomDBRepository.insertEquipos(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }else{
             BusquedaEquiposAPIService service = retrofit.create(BusquedaEquiposAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(dato,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.d("buscar equipos","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo);
                     BusquedaEquiposRoomDBRepository busquedaEquiposRoomDBRepository = new BusquedaEquiposRoomDBRepository(application);
                     busquedaEquiposRoomDBRepository.insertEquipos(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }


         //Defining retrofit api service


     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<BusquedaEquipos> parseJson(String response,String tipo ) {

        List<BusquedaEquipos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            if (tipo.equals("carga")){
                BusquedaEquiposRoomDBRepository busquedaEquiposRoomDBRepository = new BusquedaEquiposRoomDBRepository(application);
                busquedaEquiposRoomDBRepository.deleteAllEquipos();
            }else{
                /*BusquedaEquiposRoomDBRepository busquedaEquiposRoomDBRepository = new BusquedaEquiposRoomDBRepository(application);
                busquedaEquiposRoomDBRepository.deleteAllVacio();*/
            }

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                BusquedaEquipos misequipos = new BusquedaEquipos();
                misequipos.setEquipo_id(jsonNode.optString("equipo_id"));
                misequipos.setEquipo_nombre(jsonNode.optString("nombre"));
                misequipos.setEquipo_foto(jsonNode.optString("foto"));
                misequipos.setCapitan_nombre(jsonNode.optString("capitan"));
                misequipos.setCapitan_id(jsonNode.optString("capitan_id"));
                misequipos.setEstado_seleccion("vacio");




                apiResults.add(misequipos);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}