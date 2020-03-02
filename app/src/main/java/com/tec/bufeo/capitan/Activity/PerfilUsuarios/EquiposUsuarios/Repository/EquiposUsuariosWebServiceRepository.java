package com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;
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

public class EquiposUsuariosWebServiceRepository {

    Application application;
    public EquiposUsuariosWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<EquiposUsuarios> webserviceResponseList = new ArrayList<>();

 public LiveData<List<EquiposUsuarios>> providesWebService(final String id_user, String token) {

     final MutableLiveData<List<EquiposUsuarios>> data = new MutableLiveData<>();


     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         EquiposUsuariosAPIService service = retrofit.create(EquiposUsuariosAPIService.class);
         //response = service.getEquipo(id,"true",token).execute().body();
         service.getEquipo(id_user,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository EquiUsuarios","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body(),id_user);
                 EquiposUsuariosRoomDBRepository datosUsuarioRoomDBRepository = new EquiposUsuariosRoomDBRepository(application);
                 datosUsuarioRoomDBRepository.insertEquipos(webserviceResponseList);
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

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<EquiposUsuarios> parseJson(String response,String id_user ) {

        List<EquiposUsuarios> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            EquiposUsuariosRoomDBRepository datosUsuarioRoomDBRepository = new EquiposUsuariosRoomDBRepository(application);
            datosUsuarioRoomDBRepository.deleteAllEquipos();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                EquiposUsuarios equiposUsuarios = new EquiposUsuarios();

                equiposUsuarios.setEquipo_id(jsonNode.optString("equipo_id"));
                equiposUsuarios.setNombre(jsonNode.optString("nombre"));
                equiposUsuarios.setFoto(jsonNode.optString("foto"));
                equiposUsuarios.setCapitan(jsonNode.optString("capitan"));
                equiposUsuarios.setCapitan_id(jsonNode.optString("capitan_id"));
                equiposUsuarios.setUsuario_id(id_user);



                apiResults.add(equiposUsuarios);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}