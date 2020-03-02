package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
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

public class DatosUsuarioWebServiceRepository {

    Application application;
    public DatosUsuarioWebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<DatosUsuario> webserviceResponseList = new ArrayList<>();

 public LiveData<List<DatosUsuario>> providesWebService(String id_user, String token) {

     final MutableLiveData<List<DatosUsuario>> data = new MutableLiveData<>();


     try {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(APIUrl.BASE_URL)
                 .addConverterFactory(ScalarsConverterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(providesOkHttpClientBuilder())
                 .build();

         DatosUsuarioAPIService service = retrofit.create(DatosUsuarioAPIService.class);
         //response = service.getEquipo(id,"true",token).execute().body();
         service.getEquipo(id_user,"true",token).enqueue(new Callback<String>() {
             @Override
             public void onResponse(Call<String> call, Response<String> response) {
                 Log.e("Repository datosUsuario","Response::::"+response.body());
                 webserviceResponseList = parseJson(response.body());
                 DatosUsuarioRoomDBRepository datosUsuarioRoomDBRepository = new DatosUsuarioRoomDBRepository(application);
                 datosUsuarioRoomDBRepository.insertdatosUsuarioDao(webserviceResponseList);
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


    private List<DatosUsuario> parseJson(String response ) {

        List<DatosUsuario> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonNode = jsonObject.getJSONObject("result");


                DatosUsuario datosUsuario = new DatosUsuario();

                datosUsuario.setId_user(jsonNode.optString("id_user"));
                datosUsuario.setId_person(jsonNode.optString("id_person"));
                datosUsuario.setNombre(jsonNode.optString("nombre"));
                datosUsuario.setDni(jsonNode.optString("dni"));
                datosUsuario.setCelular(jsonNode.optString("celular"));
                datosUsuario.setSexo(jsonNode.optString("sexo"));
                datosUsuario.setBirthday(jsonNode.optString("birthday"));
                datosUsuario.setDireccion(jsonNode.optString("direccion"));
                datosUsuario.setNickname(jsonNode.optString("nickname"));
                datosUsuario.setPosicion(jsonNode.optString("posicion"));
                datosUsuario.setHabilidad(jsonNode.optString("habilidad"));
                datosUsuario.setNum(jsonNode.optString("num"));
                datosUsuario.setEmail(jsonNode.optString("email"));
                datosUsuario.setImg(jsonNode.optString("img"));
                datosUsuario.setEstado(jsonNode.optString("estado"));
                datosUsuario.setCreated_at(jsonNode.optString("created_at"));



                apiResults.add(datosUsuario);





        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;

    }

}