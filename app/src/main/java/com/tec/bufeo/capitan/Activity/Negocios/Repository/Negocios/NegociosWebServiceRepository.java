package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;
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

public class NegociosWebServiceRepository {

    Application application;
    public NegociosWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Negocios> webserviceResponseList = new ArrayList<>();

 public LiveData<List<Negocios>> providesWebService(String id_ciudad ,String id_user, String token) {

     final MutableLiveData<List<Negocios>> data = new MutableLiveData<>();

     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         NegociosAPIService service = retrofit.create(NegociosAPIService.class);
         //response = service.getEquipo(id,"true",token).execute().body();
         service.getEquipo(id_ciudad,id_user,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.d("Repository negocios","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 NegociosRoomDBRepository negociosRoomDBRepository = new NegociosRoomDBRepository(application);
                 negociosRoomDBRepository.insertNegocios(webserviceResponseList);
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


    private List<Negocios> parseJson(String response ) {

        List<Negocios> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();



            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Negocios negocios = new Negocios();

                negocios.setId_empresa(jsonNode.optString("id_empresa"));
                negocios.setNombre_empresa(jsonNode.optString("nombre"));
                negocios.setDireccion_empresa(jsonNode.optString("direccion"));
                negocios.setTelefono_1_empresa(jsonNode.optString("telefono_1"));
                negocios.setTelefono_2_empresa(jsonNode.optString("telefono_2"));
                negocios.setDescripcion_empresa(jsonNode.optString("descripcion"));
                negocios.setValoracion_empresa(jsonNode.optString("valoracion"));
                negocios.setFoto_empresa(jsonNode.optString("foto"));
                negocios.setEstado_empresa(jsonNode.optString("estado"));
                negocios.setUsuario_empresa(jsonNode.optString("usuario"));
                negocios.setDistrito_empresa(jsonNode.optString("distrito"));
                negocios.setHorario_ls_empresa(jsonNode.optString("horario_ls"));
                negocios.setHorario_d_empresa(jsonNode.optString("horario_d"));
                negocios.setPromedio_empresa(jsonNode.optString("promedio"));
                negocios.setConteo_empresa(jsonNode.optString("conteo"));
                negocios.setFecha_actual(jsonNode.optString("fecha_actual"));
                negocios.setHora_actual(jsonNode.optString("hora_actual"));
                negocios.setDia_actual(jsonNode.optString("dia"));
                negocios.setSoy_admin(jsonNode.optString("soy_admin"));

                JSONArray jsonArray1 = jsonNode.optJSONArray("rating");

                int count2 = jsonArray1.length();
                if (count2>0){
                    for (int x = 0 ; x<count2 ; x++){
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(x);
                        negocios.setRating_empresa_valor(jsonObject1.optString("rating_empresa_valor"));
                        negocios.setRating_conteo(jsonObject1.optString("conteo"));
                    }
                }





                apiResults.add(negocios);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}