package com.tec.bufeo.capitan.TabsPrincipales.Negocios.Repository.Canchas;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Canchas;
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

public class CanchasWebServiceRepository {

    Application application;
    public CanchasWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Canchas> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Canchas>> providesWebService(String id_empresa, String token) {

     final MutableLiveData<List<Canchas>> data = new MutableLiveData<>();

     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         CanchasAPIService service = retrofit.create(CanchasAPIService.class);
         //response = service.getEquipo(id,"true",token).execute().body();
         service.getEquipo(id_empresa,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository mis canchas","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 CanchasRoomDBRepository canchasRoomDBRepository = new CanchasRoomDBRepository(application);
                 canchasRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<Canchas> parseJson(String response ) {

        List<Canchas> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            /*MisReservasRoomDBRepository movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
            movimientosRoomDBRepository.deleteAllEquipos();*/

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Canchas cancha = new Canchas();

                cancha.setCancha_id(jsonNode.optString("cancha_id"));
                cancha.setNombre_cancha(jsonNode.optString("nombre"));
                cancha.setDimensiones_cancha(jsonNode.optString("dimensiones"));
                cancha.setPrecioD(jsonNode.optString("precioD"));
                cancha.setPrecioN(jsonNode.optString("precioN"));
                cancha.setFoto(jsonNode.optString("foto"));
                cancha.setId_empresa(jsonNode.optString("id_empresa"));
                cancha.setPromo_precio(jsonNode.optString("promo_precio"));
                cancha.setPromo_inicio(jsonNode.optString("promo_inicio"));
                cancha.setPromo_fin(jsonNode.optString("promo_fin"));
                cancha.setPromo_estado(jsonNode.optString("promo_estado"));


                apiResults.add(cancha);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}