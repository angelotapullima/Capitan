package com.tec.bufeo.capitan.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.RegistroUsuario.RegistroPaso1;
import com.tec.bufeo.capitan.Activity.RegistroUsuario.RegistroUsuario;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class Login extends AppCompatActivity {
    EditText edt_usuario,edt_password;
    Button btn_login;
    TextView txt_resgistrate;
    Preferences preferences;
    SharedPreferences preferencesUser;
    Usuario usuario;
    DataConnection dc;
    public ArrayList<String> arrayHoras;
    public static Activity activity;

    //pruebex

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_usuario =  findViewById(R.id.edt_usuario);
        btn_login =  findViewById(R.id.btn_login);
        edt_password = findViewById(R.id.edt_password);
        txt_resgistrate = findViewById(R.id.txt_resgistrate);

        preferences = new Preferences(this);
        preferencesUser = getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        if((ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(Login.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }


        txt_resgistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroPaso1.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !(edt_usuario.getText().toString().isEmpty()) && !(edt_password.getText().toString().isEmpty())){


                    login();

                }else {
                    preferences.codeAdvertencia("Llene los campos");


                }


            }
        });
    }


    int code;
    String messaje;
    JSONObject data_json;
    JSONObject json_data;
    StringRequest stringRequest;
    private void login() {
        dialogCarga();
        String url =IP2+"/api/Login/validar_usuario";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("validar_usuario new: ", "" + response);
                try {

                    json_data = new JSONObject(response);
                    JSONObject resultJSON = json_data.getJSONObject("result");

                code = resultJSON.optInt("code");
                messaje = resultJSON.optString("message");


                if (Integer.toString(code).equalsIgnoreCase("1")) {

                    data_json = json_data.optJSONObject("data");


                    //preferencesUser = this.getSharedPreferences("User", Context.MODE_PRIVATE);
                    //Guardamos los datos al sharetpreference
                    SharedPreferences.Editor editor = preferencesUser.edit();
                    editor.putString("id_user", data_json.optString("id_user"));
                    editor.putString("id_person", data_json.optString("id_person"));
                    editor.putString("user_nickname", data_json.optString("user_nickname"));
                    editor.putString("user_email", data_json.optString("user_email"));
                    editor.putString("user_image", data_json.optString("user_image"));
                    editor.putString("person_name", data_json.optString("person_name"));
                    editor.putString("person_surname", data_json.optString("person_surname"));
                    editor.putString("person_dni", data_json.optString("person_dni"));
                    editor.putString("person_birth", data_json.optString("person_birth"));
                    editor.putString("person_number_phone", data_json.optString("person_number_phone"));
                    editor.putString("person_genre", data_json.optString("person_genre"));
                    editor.putString("person_address",data_json.optString("person_address"));
                    editor.putString("user_num", data_json.optString("user_num"));
                    editor.putString("user_posicion", data_json.optString("user_posicion"));
                    editor.putString("user_habilidad", data_json.optString("user_habilidad"));
                    editor.putString("ubigeo_id",data_json.optString("ubigeo_id"));
                    editor.putString("token", data_json.optString("token"));
                    editor.putString("token_firebase",data_json.optString("token_firebase"));
                    editor.putString("tiene_negocio", data_json.optString("tiene_negocio"));
                    editor.putInt("cantida_foto_perfil", 0);
                    editor.putString("cantidad_ingreso", "1");
                    editor.apply();


                    Intent intent = new Intent(Login.this, MenuPrincipal.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    dialog_cargando.dismiss();

                }else if(Integer.toString(code).equalsIgnoreCase("2")){
                    preferences.codeAdvertencia("Ocurrio un error");
                    dialog_cargando.dismiss();
                }
                else if(Integer.toString(code).equalsIgnoreCase("3")){
                    preferences.codeAdvertencia( "Datos incorrectos");
                    dialog_cargando.dismiss();
                }else{
                    dialog_cargando.dismiss();
                }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);





                Map<String,String> parametros=new HashMap<>();
                parametros.put("user",edt_usuario.getText().toString());
                parametros.put("pass",edt_password.getText().toString());
                parametros.put("app","true");

                Log.e("params", "getParams: "+parametros );
                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }
    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);

        dialog_cargando.show();

    }

}
