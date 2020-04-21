package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Models.Mensajes;
import com.tec.bufeo.capitan.Util.APIUrl;
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

public class MensajesWebServiceRepository {

    Application application;
    Preferences preferences;
    public MensajesWebServiceRepository(Application application){
        this.application = application;
        preferences = new Preferences(application);
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Mensajes> webserviceResponseList = new ArrayList<>();


 public LiveData<List<Mensajes>> providesWebService(String id,String token) {

     final MutableLiveData<List<Mensajes>> data = new MutableLiveData<>();


     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         MensajesAPIService service = retrofit.create(MensajesAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.makeRequest(id,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository mensajes","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 MensajesRoomDBRepository mensajesRoomDBRepository = new MensajesRoomDBRepository(application);
                 mensajesRoomDBRepository.insertReviews(webserviceResponseList);
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


    private List<Mensajes> parseJson(String response) {

        List<Mensajes> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("data");

            String foto="";
            String nombre="";

            JSONObject jsonObject1 =jsonObject.getJSONObject("datos_user");

            if (jsonObject1.optString("id_usuario_1").equals(preferences.getIdUsuarioPref())){
                foto = jsonObject1.optString("foto_usuario_2");
                nombre = jsonObject1.optString("nombre_usuario_2");
            }else{

                foto = jsonObject1.optString("foto_usuario_1");
                nombre = jsonObject1.optString("nombre_usuario_1");
            }




            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Mensajes mensajes = new Mensajes();
                mensajes.setChat_id(jsonNode.getString("chat_id"));
                mensajes.setMensaje_id(jsonNode.getString("detalle_chat_id"));
                mensajes.setMensajes_id_usuario(jsonNode.getString("id_usuario"));
                mensajes.setMensaje_contenido(jsonNode.getString("mensaje"));
                mensajes.setMensaje_foto(foto);
                mensajes.setMensaje_nombre(nombre);
                mensajes.setMensaje_fecha(jsonNode.getString("fecha"));
                mensajes.setMensaje_hora(jsonNode.getString("hora"));
                mensajes.setMensaje_estado("1");




                apiResults.add(mensajes);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
