package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.Negocios.Model.Galeria;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Ratings;
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

        JSONArray jsonArray,jsonArraye;

        try {
            jsonObject = new JSONObject(response);
            JSONArray resultJSON = jsonObject.getJSONArray("results");

            int count = resultJSON.length();



            for (int i = 0; i < count; i++) {
                JSONObject jsonNode = resultJSON.getJSONObject(i);
                Negocios negocios = new Negocios();

                negocios.setNombre_empresa(jsonNode.optString("nombre"));
                negocios.setId_empresa(jsonNode.optString("id_empresa"));
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
                negocios.setFecha_actual(jsonNode.optString("fecha_actual"));
                negocios.setHora_actual(jsonNode.optString("hora_actual"));
                negocios.setDia_actual(jsonNode.optString("dia"));
                negocios.setPromedio_empresa(jsonNode.optString("promedio"));
                negocios.setConteo_empresa(jsonNode.optString("conteo"));

                negocios.setSoy_admin(jsonNode.optString("soy_admin"));

                jsonArray = jsonNode.getJSONArray("rating");
                jsonArraye = jsonNode.getJSONArray("galeria");


                negocios.setList_galeria(buildSubItemListGaleria(jsonArraye));


                negocios.setList_ratings(buildSubItemList(negocios.getId_empresa(),jsonArray));



                apiResults.add(negocios);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }



    JSONObject jsonNode2;
    Ratings ratings;

    private List<Ratings> buildSubItemList(String id,JSONArray array) {
        List<Ratings> subItemList = new ArrayList<>();

        String conteo1 ="0",conteo2="0",conteo3="0",conteo4="0",conteo5="0";
        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);

                if(i==0){
                    conteo1 = jsonNode2.optString("conteo");
                }else  if(i==1){
                    conteo2 = jsonNode2.optString("conteo");
                }else if(i==2){
                    conteo3 = jsonNode2.optString("conteo");
                }else if(i==3){
                    conteo4 = jsonNode2.optString("conteo");
                }else if(i==4){
                    conteo5 = jsonNode2.optString("conteo");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        ratings = new Ratings(id,conteo1,conteo2,conteo3,conteo4,conteo5);
        subItemList.add(ratings);
        return subItemList;
    }


    JSONObject jsonNode3;
    Galeria galeria;

    private List<Galeria> buildSubItemListGaleria(JSONArray array) {
        List<Galeria> subItemList = new ArrayList<>();

        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode3 = array.getJSONObject(i);

                String id_galeria,id_empresa,galeria_foto;

                id_galeria = jsonNode3.optString("id_galeria");
                id_empresa = jsonNode3.optString("id_empresa");
                galeria_foto = jsonNode3.optString("galeria_foto");


                galeria = new Galeria(id_galeria,id_empresa,galeria_foto);
                subItemList.add(galeria);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
    }


}