package com.tec.bufeo.capitan.Activity.RegistroUsuario;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Modelo.MDistrito;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RegistroUsuario extends AppCompatActivity  implements View.OnClickListener{



    EditText edt_Usuario,edt_clave,edt_confirmarClave;
    LinearLayout btn_Registro_usuario,back_paso1;
    public Usuario usuario;
    Preferences preferences;
    String nombre,apellido,posicion,habilidad,sexo,num_fav,email,telefono;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        preferences= new Preferences(this);



        edt_Usuario = findViewById(R.id.edt_Usuario);
        edt_clave = findViewById(R.id.edt_clave);
        edt_confirmarClave =  findViewById(R.id.edt_confirmarClave);
        btn_Registro_usuario =  findViewById(R.id.btn_Registro_usuario);
        back_paso1 =  findViewById(R.id.back_paso1);


        nombre = getIntent().getExtras().getString("nombre");
        apellido = getIntent().getExtras().getString("apellido");
        posicion = getIntent().getExtras().getString("posicion");
        habilidad = getIntent().getExtras().getString("habilidad");
        sexo = getIntent().getExtras().getString("sexo");
        num_fav = getIntent().getExtras().getString("num_fav");
        email = getIntent().getExtras().getString("email");
        telefono = getIntent().getExtras().getString("telefono");

        back_paso1.setOnClickListener(this);
        btn_Registro_usuario.setOnClickListener(this);



    }




    @Override
    public void onClick(View v) {

        if (v.equals(btn_Registro_usuario)) {
            if (edt_Usuario.getText().toString().isEmpty()){
                preferences.codeAdvertencia("El campo Usuario no puede estar vacio");
            }else if(edt_clave.getText().toString().equals(edt_confirmarClave.getText().toString())){
                preferences.codeAdvertencia("las contraseñas no son iguales");
            }else{
                CrarUsuario();
            }

        } else if (v.equals(back_paso1)) {
            finish();
        }
    }


    DataConnection dc;
    StringRequest stringRequest;
    String valorcodigo,respuesta;
    private void CrarUsuario() {
        dialogCarga();
        String url =IP2+"/api/Registrar/new";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("registro new: ",""+response);




                    valorcodigo=response;


                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                        Toast.makeText(RegistroUsuario.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        usuario = new Usuario();
                        usuario.setUser_nickname(edt_Usuario.getText().toString());
                        usuario.setUser_password(edt_clave.getText().toString());

                        dc = new DataConnection(RegistroUsuario.this,"loginUsuario",usuario,false);

                    }else if(valorcodigo.equalsIgnoreCase("2")) {
                        respuesta = "2";
                        Toast.makeText(RegistroUsuario.this, "DNI ya existe", Toast.LENGTH_SHORT).show();
                        dialog_cargando.dismiss();
                    }else {
                        respuesta = "3";
                        Toast.makeText(RegistroUsuario.this, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                        dialog_cargando.dismiss();
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
                parametros.put("person_name",nombre);
                parametros.put("person_surname",apellido);
                parametros.put("person_birth","");
                parametros.put("user_image","perfil.png");
                parametros.put("person_genre",sexo);
                parametros.put("user_nickname",edt_Usuario.getText().toString());
                parametros.put("user_password",edt_clave.getText().toString());
                parametros.put("user_email",email);
                parametros.put("user_habilidad",habilidad);
                parametros.put("user_posicion",posicion);
                parametros.put("user_num",num_fav);
                parametros.put("person_dni","");
                parametros.put("person_number_phone",telefono);
                parametros.put("id_role","4");
                parametros.put("person_address","");
                parametros.put("app","true");
                parametros.put("ubigeo_id","1");

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