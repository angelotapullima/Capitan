package com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.tec.bufeo.capitan.Activity.DetalleCanchas.Models.ReservasCancha;
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

public class ReservasCanchaWebServiceRepository {



    //Preferences preferencesUser;
    Application application;
    public ReservasCanchaWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<ReservasCancha> webserviceResponseList = new ArrayList<>();

    public LiveData<List<ReservasCancha>> providesWebService(final String fecha,String cancha_id, String token) {

        final MutableLiveData<List<ReservasCancha>> data = new MutableLiveData<>();

        String response = "";
        //String id = preferencesUser.getIdUsuarioPref();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            ReservasCanchaAPIService service = retrofit.create(ReservasCanchaAPIService.class);
            //  response = service.makeRequest().execute().body();
            service.savePost(cancha_id,fecha,"true",token).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("Repository","DReservasCanchas::::"+response.body());
                    webserviceResponseList = parseJson(response.body());
                    ReservasCanchaRoomDBRepository goleadoresRoomDBRepository = new ReservasCanchaRoomDBRepository(application);
                    goleadoresRoomDBRepository.insertPosts(webserviceResponseList);
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




    private List<ReservasCancha> parseJson(String response) {

        List<ReservasCancha> apiResults = new ArrayList<>();

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);



            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();

            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                ReservasCancha reservasCancha = new ReservasCancha();


                reservasCancha.setFecha(jsonNode.optString("fecha"));
                reservasCancha.setReserva_id(jsonNode.optString("reserva_id"));
                reservasCancha.setPago_id(jsonNode.optString("pago_id"));
                reservasCancha.setTipopago(jsonNode.optString("tipopago"));
                reservasCancha.setCancha_id(jsonNode.optString("cancha_id"));
                reservasCancha.setNombre(jsonNode.optString("nombre"));
                reservasCancha.setHora(jsonNode.optString("hora"));
                reservasCancha.setPago1(jsonNode.optString("pago1"));
                reservasCancha.setPago1_date(jsonNode.optString("pago1_date"));
                reservasCancha.setPago2(jsonNode.optString("pago2"));
                reservasCancha.setPago2_date(jsonNode.optString("pago2_date"));
                reservasCancha.setDia(jsonNode.optString("dia"));
                reservasCancha.setEstado(jsonNode.optString("estado"));




                apiResults.add(reservasCancha);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
