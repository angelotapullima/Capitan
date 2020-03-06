package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores;

import android.app.Application;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
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

public class JugadoresWebServiceRepository {

    Application application;
    public JugadoresWebServiceRepository(Application application){
        this.application = application;
    }

    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }

    List<Jugadores> webserviceResponseList = new ArrayList<>();

    public LiveData<List<Jugadores>> providesWebService(String id_equipo, final String token, final String dato) {

        final MutableLiveData<List<Jugadores>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            if (dato.equals("")){
                APIServiceJugadores service = retrofit.create(APIServiceJugadores.class);
                //  response = service.makeRequest().execute().body();
                service.getJugadores(id_equipo,"true",token).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("Repository jugadores","Response::::"+response.body());
                        webserviceResponseList = parseJson(response.body(),dato);
                        JugadoresRoomDBRepository jugadoresRoomDBRepository = new JugadoresRoomDBRepository(application);
                        jugadoresRoomDBRepository.insertJugadores(webserviceResponseList);
                        data.setValue(webserviceResponseList);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Repository","Failed:::");
                    }
                });
            }else if (dato.equals("si")) {
                APIServiceMisjugadores service = retrofit.create(APIServiceMisjugadores.class);
                //  response = service.makeRequest().execute().body();
                service.getMisJugadores(id_equipo,"true",token).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("Repo mis jugadores","Response::::"+response.body());
                        webserviceResponseList = parseJson(response.body(),dato);
                        JugadoresRoomDBRepository jugadoresRoomDBRepository = new JugadoresRoomDBRepository(application);
                        jugadoresRoomDBRepository.insertJugadores(webserviceResponseList);
                        data.setValue(webserviceResponseList);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Repository","Failed:::");
                    }
                });
            }else{
                APIServiceBusquedaJugador service = retrofit.create(APIServiceBusquedaJugador.class);
                //  response = service.makeRequest().execute().body();
                service.getBuscarJugadores(id_equipo,"true",token,dato).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.e("Repository jugadores","Response::::"+response.body());
                        webserviceResponseList = parseJson(response.body(),dato);
                        JugadoresRoomDBRepository jugadoresRoomDBRepository = new JugadoresRoomDBRepository(application);
                        jugadoresRoomDBRepository.insertJugadores(webserviceResponseList);
                        data.setValue(webserviceResponseList);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Repository","Failed:::");
                    }
                });
            }





            //Defining retrofit api service


        }catch (Exception e){
            e.printStackTrace();
        }

        //  return retrofit.create(ResultModel.class);
        return  data;

    }


    private List<Jugadores> parseJson(String response,String dato ) {

        List<Jugadores> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();


            /*JugadoresRoomDBRepository jugadoresRoomDBRepository =  new JugadoresRoomDBRepository(application);
            jugadoresRoomDBRepository.deleteAllJugadores();*/


            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Jugadores jugadores = new Jugadores();

                if (dato.equals("si")){
                    jugadores.setJugador_mi_equipo("si");
                }else{
                    jugadores.setJugador_mi_equipo("no");
                }
                jugadores.setJugador_id(jsonNode.optString("usuario_id"));
                jugadores.setJugador_nombre(jsonNode.optString("nombre"));
                jugadores.setJugador_foto(jsonNode.optString("foto"));
                jugadores.setJugador_posicion(jsonNode.optString("posicion"));
                jugadores.setJugador_habilidad(jsonNode.optString("habilidad"));
                jugadores.setJugador_numero(jsonNode.optString("num"));
                jugadores.setJugador_estado("vacio");


                apiResults.add(jugadores);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }
}
