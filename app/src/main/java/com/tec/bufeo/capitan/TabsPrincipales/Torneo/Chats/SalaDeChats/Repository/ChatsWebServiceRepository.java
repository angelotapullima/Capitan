package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Models.Chats;
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

public class ChatsWebServiceRepository {

    private Application application;

    public ChatsWebServiceRepository(Application application){
        this.application = application;
    }

    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    private List<Chats> webserviceResponseList = new ArrayList<>();


 public LiveData<List<Chats>> providesWebService(String id,String token) {

     final MutableLiveData<List<Chats>> data = new MutableLiveData<>();


     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         ChatsAPIService service = retrofit.create(ChatsAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.makeRequest(id,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                 Log.d("Repository chats","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());

                 ChatsRoomDBRepository chatsRoomDBRepository = new ChatsRoomDBRepository(application);
                 chatsRoomDBRepository.insertReviews(webserviceResponseList);
                 data.setValue(webserviceResponseList);

             }

             @Override
             public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                 Log.d("Repository","Failed:::");
             }
         });
     }catch (Exception e){
         e.printStackTrace();
     }

     //  return retrofit.create(ResultModel.class);
     return  data;

    }


    private List<Chats> parseJson(String response) {

        List<Chats> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Chats chats = new Chats();

                //mMovieModel.setId(object.getString("id"));
                //reviews.setComments_foto(jsonNode.getString("foto"));
                chats.setChat_id(jsonNode.getString("chat_id"));

                chats.setId_usuario_1(jsonNode.getString("id_usuario_1"));
                chats.setUsuario_1(jsonNode.getString("usuario_1"));
                chats.setUsuario_1_foto(jsonNode.getString("usuario_1_foto"));

                chats.setId_usuario_2(jsonNode.getString("id_usuario_2"));
                chats.setUsuario_2(jsonNode.getString("usuario_2"));
                chats.setUsuario_2_foto(jsonNode.getString("usuario_2_foto"));



                chats.setChat_fecha(jsonNode.getString("chat_fecha"));

                chats.setChat_ultimo_mensaje(jsonNode.getString("ultimo_msj"));
                chats.setChat_ultimo_mensaje_id(jsonNode.getString("ultimo_msj_id"));
                chats.setChat_ultimo_mensaje_fecha(jsonNode.getString("ultimo_msj_fecha"));
                chats.setChat_ultimo_mensaje_hora(jsonNode.getString("ultimo_msj_hora"));
                chats.setChat_ultimo_mensaje_usuario(jsonNode.getString("ultimo_msj_usuario"));

                apiResults.add(chats);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
