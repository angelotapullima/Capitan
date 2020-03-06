package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.Util.APIUrl;
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

public class MisEquiposWebServiceRepository {

    Application application;
    public MisEquiposWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Mequipos> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Mequipos>> providesWebService(String id_usuario, final String tipo_equipo,final String token,String dato) {

     final MutableLiveData<List<Mequipos>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         if (tipo_equipo.equals("mi_equipo")){
             MisEquiposAPIService service = retrofit.create(MisEquiposAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(id_usuario,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.e("Repository mi_equipo","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo_equipo);
                     MisEquiposRoomDBRepository menuRoomDBRepository = new MisEquiposRoomDBRepository(application);
                     menuRoomDBRepository.insertEquipos(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }else if (tipo_equipo.equals("otro_equipo")){
             OtrosEquiposAPIService service = retrofit.create(OtrosEquiposAPIService.class);
             service.getEquipo(id_usuario,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.e("Repository otro_equipo" ,"Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo_equipo);
                     MisEquiposRoomDBRepository menuRoomDBRepository = new MisEquiposRoomDBRepository(application);
                     menuRoomDBRepository.insertEquipos(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     //Log.d("Repository","Failed:::");
                 }
             });
         }else{
             BusquedaEquiposAPIService service = retrofit.create(BusquedaEquiposAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(dato,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.e("buscar torneos","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo_equipo);
                     MisEquiposRoomDBRepository menuRoomDBRepository = new MisEquiposRoomDBRepository(application);
                     menuRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<Mequipos> parseJson(String response ,String tipo) {

        List<Mequipos> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            /*MisEquiposRoomDBRepository misEquiposRoomDBRepository = new MisEquiposRoomDBRepository(application);
            misEquiposRoomDBRepository.deleteAllEquipos();*/
            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Mequipos misequipos = new Mequipos();
                misequipos.setEquipo_id(jsonNode.optString("equipo_id"));
                misequipos.setEquipo_nombre(jsonNode.optString("nombre"));
                misequipos.setEquipo_foto(jsonNode.optString("foto"));
                misequipos.setCapitan_nombre(jsonNode.optString("capitan"));
                misequipos.setCapitan_id(jsonNode.optString("capitan_id"));
                misequipos.setEstado_seleccion("0");

                if (tipo.equals("mi_equipo")) {
                    misequipos.setMis_equipos("si");
                } else {
                    misequipos.setMis_equipos("no");
                }


                apiResults.add(misequipos);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(tipo, String.valueOf(apiResults.size()));
        return apiResults;

    }

}