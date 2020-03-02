package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class EditarDatosDePerfil extends AppCompatActivity implements View.OnClickListener {


    LinearLayout password_change,dni_change,addres_change,posicion_change,habilidad_change,camiseta_change,nickname_change,
            btn_change,btn_change_nickname,btn_change_password;

    EditText passAntigua,passNueva,passRepetida;
    EditText dniAntiguo,dniNuevo,dniRepetido;
    EditText addressAntiguo,addresNuevo,addresRepetido;

    TextView posicionAntiguo;
    Spinner spn_posicion;


    TextView habilidadAntiguo;
    Spinner spn_habilidad;

    TextView camisetaAntigua;
    EditText camisetaNuevo;

    TextView nicknameAntiguo;
    EditText nicknameNuevo;


    Preferences preferences;


    String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos_de_perfil);

        preferences= new Preferences(this);

        password_change = findViewById(R.id.password_change);
        nickname_change = findViewById(R.id.nickname_change);
        dni_change = findViewById(R.id.dni_change);
        addres_change = findViewById(R.id.addres_change);
        posicion_change = findViewById(R.id.posicion_change);
        habilidad_change = findViewById(R.id.habilidad_change);
        camiseta_change = findViewById(R.id.camiseta_change);


        btn_change = findViewById(R.id.btn_change);
        btn_change_nickname = findViewById(R.id.btn_change_nickname);
        btn_change_password = findViewById(R.id.btn_change_password);

        passAntigua = findViewById(R.id.passAntigua);
        passNueva = findViewById(R.id.passNueva);
        passRepetida = findViewById(R.id.passRepetida);
        nicknameAntiguo = findViewById(R.id.nicknameAntiguo);
        nicknameNuevo = findViewById(R.id.nicknameNuevo);
        dniAntiguo = findViewById(R.id.dniAntiguo);
        dniNuevo = findViewById(R.id.dniNuevo);
        dniRepetido = findViewById(R.id.dniRepetido);
        addressAntiguo = findViewById(R.id.addressAntiguo);
        addresNuevo = findViewById(R.id.addresNuevo);
        addresRepetido = findViewById(R.id.addresRepetido);
        posicionAntiguo = findViewById(R.id.posicionAntiguo);
        spn_posicion = findViewById(R.id.spn_posicion);
        habilidadAntiguo = findViewById(R.id.habilidadAntiguo);
        spn_habilidad = findViewById(R.id.spn_habilidad);
        camisetaAntigua = findViewById(R.id.camisetaAntigua);
        camisetaNuevo = findViewById(R.id.camisetaNuevo);




        tipo= getIntent().getExtras().getString("tipo");

        if (tipo.equals("UsuarioNickname")){
            nickname_change.setVisibility(View.VISIBLE);
            btn_change_nickname.setVisibility(View.VISIBLE);
            btn_change.setVisibility(View.GONE);
        }else if (tipo.equals("DniDelUsuario")){
            dni_change.setVisibility(View.VISIBLE);
        }else if (tipo.equals("DireccionDelUsuario")){
            addres_change.setVisibility(View.VISIBLE);
        }else if (tipo.equals("CamisetaDelUsuario")){
            camiseta_change.setVisibility(View.VISIBLE);
        }else if (tipo.equals("PosicionDelUsuario")){
            posicion_change.setVisibility(View.VISIBLE);
        }else if (tipo.equals("HabilidadDelUsuario")){
            habilidad_change.setVisibility(View.VISIBLE);
        }else if (tipo.equals("Password")){
            password_change.setVisibility(View.VISIBLE);
            btn_change_password.setVisibility(View.VISIBLE);
            btn_change.setVisibility(View.GONE);
        }


        posicionAntiguo.setText(preferences.getPosicionJugador());
        habilidadAntiguo.setText(preferences.getHabilidadJuegador());
        nicknameAntiguo.setText(preferences.getNickname());

        btn_change.setOnClickListener(this);
        btn_change_nickname.setOnClickListener(this);
        btn_change_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_change)) {

            if (tipo.equals("DniDelUsuario")) {

                if (dniAntiguo.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo DNI antiguo");
                }else{
                    if (dniNuevo.getText().toString().isEmpty()){
                        preferences.codeAdvertencia("Debe llenar el campo DNI nuevo");
                    }else{
                        if (dniRepetido.getText().toString().isEmpty()){
                            preferences.codeAdvertencia("Debe llenar el campo DNI Repetido");
                        }else{
                            if (dniNuevo.getText().toString().equals(dniRepetido.getText().toString())){
                                Change();
                            }else {

                                preferences.codeAdvertencia("EL Dni Nuevo y Dni Repetido deben ser iguales");
                            }
                        }
                    }
                }


            }

            if (tipo.equals("DireccionDelUsuario")) {
                if (addressAntiguo.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo Dirección antiguo");
                }else{
                    if (addresNuevo.getText().toString().isEmpty()){
                        preferences.codeAdvertencia("Debe llenar el campo Dirección nuevo");
                    }else{
                        if (addresRepetido.getText().toString().isEmpty()){
                            preferences.codeAdvertencia("Debe llenar el campo Dirección repetido");
                        }else{
                            if (addresNuevo.getText().toString().equals(addresRepetido.getText().toString())){
                                Change();
                            }else {

                                preferences.codeAdvertencia("la Dirección Nuevo y la Dirección deben ser iguales");
                            }
                        }
                    }
                }


            }


            if (tipo.equals("CamisetaDelUsuario")) {
                if (camisetaAntigua.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo Camiseta antiguo");
                }else{
                    if (camisetaNuevo.getText().toString().isEmpty()){
                        preferences.codeAdvertencia("Debe llenar el campo Camiseta Nueva");
                    }else{

                        Change();
                    }
                }

            }

            if (tipo.equals("PosicionDelUsuario")) {

                if (posicionAntiguo.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo Posición antiguo");
                }else{
                    if (spn_posicion.getSelectedItem().toString().equals("Seleccionar")){
                        preferences.codeAdvertencia("Debe seleccionar una posición");
                    }else{
                        Change();
                    }

                }
            }

            if (tipo.equals("HabilidadDelUsuario")) {
                if (habilidadAntiguo.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo Habilidad antiguo");
                }else{
                    if (spn_habilidad.getSelectedItem().toString().equals("Seleccionar")){
                        preferences.codeAdvertencia("Debe seleccionar una Habilidad");
                    }else{
                        Change();
                    }

                }
            }





        }else if(v.equals(btn_change_nickname)){


            if (nicknameAntiguo.getText().toString().isEmpty()){
                preferences.codeAdvertencia("Debe llenar el campo Usuario antiguo");
            }else{
                if (nicknameNuevo.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo Usuario nuevo");
                }else{
                    ChangeNickname();
                }
            }
        }else if(v.equals(btn_change_password)){

            if (passAntigua.getText().toString().isEmpty()){
                preferences.codeAdvertencia("Debe llenar el campo Contraseña antigua");
            }else{
                if (passNueva.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe llenar el campo Contraseña nueva");
                }else{
                    if (passRepetida.getText().toString().isEmpty()){
                        preferences.codeAdvertencia("Debe llenar el campo Contraseña repetida");
                    }else{
                        if (passNueva.getText().toString().equals(passRepetida.getText().toString())){
                            ChangePass();
                        }else {

                            preferences.codeAdvertencia("la Contraseña Nueva no coincide con la Contraseña Repetida");
                        }
                    }
                }
            }

        }
    }


    StringRequest stringRequest;
    String person_dni,person_address,user_posicion,user_habilidad,user_num;
    public void Change(){
        String url = IP2+"/api/Edit/editar_user";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("edit","editar"+response);



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);

                if (tipo.equals("DniDelUsuario")){
                    person_dni = dniNuevo.getText().toString();
                    person_address = preferences.getDireccion();
                    user_posicion =preferences.getPosicionJugador();
                    user_habilidad=preferences.getHabilidadJuegador();
                    user_num= preferences.getNumeroCamiseta();


                }if (tipo.equals("DireccionDelUsuario")){
                    person_dni = preferences.getDni();
                    person_address = dniNuevo.getText().toString();
                    user_posicion =preferences.getPosicionJugador();
                    user_habilidad=preferences.getHabilidadJuegador();
                    user_num= preferences.getNumeroCamiseta();


                }if (tipo.equals("CamisetaDelUsuario")){
                    person_dni = preferences.getDni();
                    person_address = preferences.getDireccion();
                    user_posicion =preferences.getPosicionJugador();
                    user_habilidad=preferences.getHabilidadJuegador();
                    user_num= camisetaNuevo.getText().toString();

                }if (tipo.equals("PosicionDelUsuario")){
                    person_dni = preferences.getDni();
                    person_address = preferences.getDireccion();
                    user_posicion =spn_posicion.getSelectedItem().toString();
                    user_habilidad=preferences.getHabilidadJuegador();
                    user_num= preferences.getNumeroCamiseta();
                }

                if (tipo.equals("HabilidadDelUsuario")){
                    person_dni = preferences.getDni();
                    person_address = preferences.getDireccion();
                    user_posicion =preferences.getPosicionJugador();
                    user_habilidad=spn_habilidad.getSelectedItem().toString();
                    user_num= preferences.getNumeroCamiseta();
                }

                Map<String,String> parametros=new HashMap<>();

                parametros.put("id_user", preferences.getIdUsuarioPref());
                parametros.put("person_dni", person_dni);
                parametros.put("person_address", person_address);
                parametros.put("user_posicion", user_posicion);
                parametros.put("user_habilidad", user_habilidad);
                parametros.put("user_num", user_num);
                parametros.put("app", "true");
                parametros.put("token", preferences.getToken());

                Log.e("edit", "Params: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }


    public void ChangeNickname(){
        String url = IP2+"/api/Edit/editar_nickname";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("editar_nickname","editar"+response);



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);



                Map<String,String> parametros=new HashMap<>();

                parametros.put("id_user", preferences.getIdUsuarioPref());
                parametros.put("user_nickname", nicknameNuevo.getText().toString());
                parametros.put("app", "true");
                parametros.put("token", preferences.getToken());

                Log.e("editar_nickname", "Params: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }


    public void ChangePass(){
        String url = IP2+"/api/Edit/edit_pass";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("edit_pass","editar"+response);



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);



                Map<String,String> parametros=new HashMap<>();

                parametros.put("id_user", preferences.getIdUsuarioPref());
                parametros.put("user_password", passNueva.getText().toString());
                parametros.put("app", "true");
                parametros.put("token", preferences.getToken());

                Log.e("edit_pass", "Params: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }
}
