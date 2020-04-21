package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.Util.APIUrl;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Models.Torneo;
import com.tec.bufeo.capitan.Util.Preferences;

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

public class TorneosWebServiceRepository {


    Application application;
    Preferences preferences;
    public TorneosWebServiceRepository(Application application){
        this.application = application;
        preferences= new Preferences(application);
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Torneo> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Torneo>> providesWebService(final String id, String token, final String tipo, String dato) {

     final MutableLiveData<List<Torneo>> data = new MutableLiveData<>();

     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         if(tipo.equals("torneos")){
             TorneosAPIService service = retrofit.create(TorneosAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getRetos(id,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.d("Repository otros t","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo,id);
                     TorneosRoomDBRepository torneosRoomDBRepository = new TorneosRoomDBRepository(application);
                     torneosRoomDBRepository.insertTorneo(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }else if(tipo.equals("busqueda")){
             TorneosBusquedaAPIService service = retrofit.create(TorneosBusquedaAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getEquipo(dato,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.d("buscar torneos","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body(),tipo,id);
                     TorneosRoomDBRepository torneosRoomDBRepository = new TorneosRoomDBRepository(application);
                     torneosRoomDBRepository.insertTorneo(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }if(tipo.equals("id")){
             IdTorneoAPIService service = retrofit.create(IdTorneoAPIService.class);
             //  response = service.makeRequest().execute().body();
             service.getIdTorneo(id,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.d("torneos por  id","Response::::"+response.body());
                     webserviceResponseList = parseJsonID(response.body(),preferences.getIdUsuarioPref());
                     TorneosRoomDBRepository torneosRoomDBRepository = new TorneosRoomDBRepository(application);
                     torneosRoomDBRepository.insertTorneo(webserviceResponseList);
                     data.setValue(webserviceResponseList);

                 }

                 @Override
                 public void onFailure(Call<String> call, Throwable t) {
                     Log.d("Repository","Failed:::");
                 }
             });
         }

     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<Torneo> parseJson(String response,String tipo,String id) {

        List<Torneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();




            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Torneo torneo = new Torneo();



                torneo.setId_torneo(jsonNode.optString("id_torneo"));
                torneo.setTorneo_nombre(jsonNode.optString("nombre"));
                torneo.setTorneo_descripcion(jsonNode.optString("descripcion"));
                torneo.setTorneo_fecha(jsonNode.optString("fecha"));
                torneo.setTorneo_hora(jsonNode.optString("hora"));
                torneo.setFoto_torneo(jsonNode.optString("foto"));
                torneo.setTorneo_lugar(jsonNode.optString("lugar"));
                torneo.setTorneo_equipos(jsonNode.optString("equipos"));
                torneo.setTorneo_organizador(jsonNode.optString("organizador"));
                torneo.setUsuario_id(jsonNode.optString("id_organizador"));
                torneo.setTorneo_precio(jsonNode.optString("costo"));

                if (id.equals(jsonNode.optString("id_organizador"))){
                    torneo.setMi_torneo("si");
                }else{
                    torneo.setMi_torneo("no");
                }


                if(tipo.equals("busqueda")){
                    torneo.setMi_torneo("no");
                }





                apiResults.add(torneo);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

    private List<Torneo> parseJsonID(String response,String id) {

        List<Torneo> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonNode = jsonObject.getJSONObject("results");


                Torneo torneo = new Torneo();




                torneo.setId_torneo(jsonNode.optString("id_torneo"));
                torneo.setTorneo_nombre(jsonNode.optString("nombre"));
                torneo.setTorneo_descripcion(jsonNode.optString("descripcion"));
                torneo.setTorneo_fecha(jsonNode.optString("fecha"));
                torneo.setTorneo_hora(jsonNode.optString("hora"));
                torneo.setFoto_torneo(jsonNode.optString("foto"));
                torneo.setTorneo_lugar(jsonNode.optString("lugar"));
                torneo.setTorneo_equipos(jsonNode.optString("equipos"));
                torneo.setTorneo_organizador(jsonNode.optString("organizador"));
                torneo.setUsuario_id(jsonNode.optString("id_organizador"));
                torneo.setTorneo_precio(jsonNode.optString("costo"));

                if (id.equals(jsonNode.optString("id_organizador"))){
                    torneo.setMi_torneo("si");
                }else{
                    torneo.setMi_torneo("no");
                }






                apiResults.add(torneo);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}