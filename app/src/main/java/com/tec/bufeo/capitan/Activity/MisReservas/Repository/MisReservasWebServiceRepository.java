package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
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

public class MisReservasWebServiceRepository {

    Application application;
    public MisReservasWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<MisReservas> webserviceResponseList = new ArrayList<>();

 public LiveData<List<MisReservas>> providesWebService(String id_user, String token) {

     final MutableLiveData<List<MisReservas>> data = new MutableLiveData<>();

     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         MisReservasAPIService service = retrofit.create(MisReservasAPIService.class);
         //response = service.getEquipo(id,"true",token).execute().body();
         service.getEquipo(id_user,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository mis reservas","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 MisReservasRoomDBRepository movimientosRoomDBRepository = new MisReservasRoomDBRepository(application);
                 movimientosRoomDBRepository.insertEquipos(webserviceResponseList);
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


    private List<MisReservas> parseJson(String response ) {

        List<MisReservas> apiResults = new ArrayList<>();

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
                MisReservas misReservas = new MisReservas();

                misReservas.setId_reserva(jsonNode.optString("reserva_id"));
                misReservas.setNombre_empresa(jsonNode.optString("empresa_nombre"));
                misReservas.setCancha_nombre(jsonNode.optString("cancha_nombre"));
                misReservas.setMonto_final(jsonNode.optString("pago_total"));
                misReservas.setFecha_reserva(jsonNode.optString("reserva_fecha"));
                misReservas.setHora_reserva(jsonNode.optString("reserva_hora"));
                misReservas.setTelefono1_reserva(jsonNode.optString("empresa_telefono_1"));
                misReservas.setTelefono2_reserva(jsonNode.optString("empresa_telefono_2"));
                misReservas.setDireccion_reserva(jsonNode.optString("empresa_direccion"));
                misReservas.setNombre_reserva(jsonNode.optString("reserva_nombre"));


                apiResults.add(misReservas);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}