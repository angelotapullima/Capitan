package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.DetalleChancha;
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

public class ChanchasWebServiceRepository {

    Application application;
    public ChanchasWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Chanchas> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Chanchas>> providesWebService(String id_equipo, String token) {

     final MutableLiveData<List<Chanchas>> data = new MutableLiveData<>();

     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();


         ChanchasAPIService service = retrofit.create(ChanchasAPIService.class);
             //response = service.getEquipo(id,"true",token).execute().body();
             service.getEquipo(id_equipo,"true",token).enqueue(new Callback<String>() {
                 @Override
                 public void onResponse(Call<String> call, Response<String> response) {
                     Log.d("Repository mis chanchas","Response::::"+response.body());
                     webserviceResponseList = parseJson(response.body());
                     ChanchasRoomDBRepository movimientosRoomDBRepository = new ChanchasRoomDBRepository(application);
                     movimientosRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<Chanchas> parseJson(String response ) {

        List<Chanchas> apiResults = new ArrayList<>();

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
                Chanchas chanchas = new Chanchas();

                chanchas.setId_chancha(jsonNode.optString("id"));
                chanchas.setId_equipo(jsonNode.optString("id_equipo"));
                chanchas.setEquipo_nombre(jsonNode.optString("equipo"));
                chanchas.setNombre_chancha(jsonNode.optString("nombre"));
                chanchas.setMonto_final(jsonNode.optString("monto"));
                chanchas.setChancha_fecha(jsonNode.optString("fecha"));
                jsonArray = jsonNode.getJSONArray("detalle");

                int coun1 = jsonArray.length();

                double monto_final_chancha=0,monto_final=0;
                for (int x = 0; x < coun1; x++) {
                    jsonNodecito = jsonArray.getJSONObject(x);

                    monto_final_chancha = Double.parseDouble(jsonNodecito.optString("detalle_colaboracion_monto"));
                    monto_final = monto_final + monto_final_chancha;


                }
                chanchas.setMonto_actual(String.valueOf(monto_final));

                chanchas.setChancha_detalle( buildSubItemList(jsonArray));
                apiResults.add(chanchas);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

    JSONArray jsonArray;
    JSONObject jsonNode2,jsonNodecito;
    DetalleChancha detalleChancha;

    private List<DetalleChancha> buildSubItemList(JSONArray array) {
        List<DetalleChancha> subItemList = new ArrayList<>();

        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);

                String id_colaboracion,id_detalle_colaboracion,colaboracion_date,
                        colaboracion_estado,colaboracion_monto,user_nickname,user_image;

                id_colaboracion = jsonNode2.optString("id_colaboracion");
                id_detalle_colaboracion = jsonNode2.optString("id_detalle_colaboracion");
                colaboracion_date = jsonNode2.optString("colaboracion_date");
                colaboracion_estado = jsonNode2.optString("colaboracion_estado");
                colaboracion_monto = jsonNode2.optString("detalle_colaboracion_monto");
                user_nickname = jsonNode2.optString("user_nickname");
                user_image = jsonNode2.optString("user_image");


                detalleChancha = new DetalleChancha(id_colaboracion,id_detalle_colaboracion,colaboracion_date,colaboracion_estado,colaboracion_monto,user_nickname,user_image);
                subItemList.add(detalleChancha);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }



}