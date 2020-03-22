package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository;

import android.app.Application;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models.InstanciasModel;
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

public class InstanciasWebServiceRepository {

    //Preferences preferencesUser;
    Application application;
    public InstanciasWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<InstanciasModel> webserviceResponseList = new ArrayList<>();

    public LiveData<List<InstanciasModel>> providesWebService(final String id_torneo, String token) {

        final MutableLiveData<List<InstanciasModel>> data = new MutableLiveData<>();

        String response = "";
        //String id = preferencesUser.getIdUsuarioPref();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            InstanciasAPIService service = retrofit.create(InstanciasAPIService.class);
            //  response = service.makeRequest().execute().body();
            service.savePost(id_torneo,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Repository","instancias::::"+response.body());
                    webserviceResponseList = parseJson(response.body(), id_torneo);
                    InstanciasRoomDBRepository instanciasRoomDBRepository = new InstanciasRoomDBRepository(application);
                    instanciasRoomDBRepository.insertPosts(webserviceResponseList);
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




    private List<InstanciasModel> parseJson(String response,String id_tor) {

        List<InstanciasModel> apiResults = new ArrayList<>();

        InstanciasRoomDBRepository instanciasRoomDBRepository= new InstanciasRoomDBRepository(application);
        instanciasRoomDBRepository.deleteAllInstancias();
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);

            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                InstanciasModel instanciasModel = new InstanciasModel();


                instanciasModel.setId_instancias(jsonNode.optString("id_instancia"));
                instanciasModel.setNombre_instancias(jsonNode.optString("nombre_instancia"));
                instanciasModel.setTipo_instancias(jsonNode.optString("tipo_instancia"));
                instanciasModel.setId_torneo(id_tor);
                instanciasModel.setEstado("0");



                apiResults.add(instanciasModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }



}
