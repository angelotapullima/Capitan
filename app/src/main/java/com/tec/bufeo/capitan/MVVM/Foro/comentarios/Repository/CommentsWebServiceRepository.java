package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models.Comments;
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

public class CommentsWebServiceRepository {

    Application application;
    public CommentsWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Comments> webserviceResponseList = new ArrayList<>();


 public LiveData<List<Comments>> providesWebService(String id,String token) {

     final MutableLiveData<List<Comments>> data = new MutableLiveData<>();


     String response = "";
     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         //Defining retrofit api service
         CommentsAPIService service = retrofit.create(CommentsAPIService.class);
        //  response = service.makeRequest().execute().body();
         service.makeRequest(id,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 CommentsRoomDBRepository commentsRoomDBRepository = new CommentsRoomDBRepository(application);
                 commentsRoomDBRepository.insertReviews(webserviceResponseList);
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


    private List<Comments> parseJson(String response) {

        List<Comments> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Comments reviews = new Comments();

                //mMovieModel.setId(object.getString("id"));
                //reviews.setComments_foto(jsonNode.getString("foto"));
                reviews.setComments_fecha(jsonNode.getString("fecha"));
                reviews.setComments_nombre(jsonNode.getString("usuario_nombre"));
                reviews.setPublicacion_id(jsonNode.getString("id_publicacion"));
                reviews.setComments_comentario(jsonNode.getString("comentario"));
                reviews.setComments_foto(jsonNode.getString("usuario_foto"));
                reviews.setComments_id(jsonNode.getString("id_comentario"));

                apiResults.add(reviews);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
