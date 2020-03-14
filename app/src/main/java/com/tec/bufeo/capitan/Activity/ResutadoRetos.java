package com.tec.bufeo.capitan.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class ResutadoRetos extends AppCompatActivity {

    Spinner spn_equipos_reto;
    ArrayList<RetosVersus> listaRetos;
    ArrayList<String> listaSpinnerRetos = new ArrayList<String>();
    Preferences preferences;
    String retado,retador,retado_id,retador_id, retado_foto,retador_foto,id_reto;
    ImageView foto_retado_resultado,foto_retador_resultado;
    TextView nombre_retado_resultado,nombre_retador_resultado;
    AppCompatButton button_enviar_resultado;
    UniversalImageLoader universalImageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutado_retos);


        preferences =  new Preferences(this);
        universalImageLoader= new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        spn_equipos_reto = findViewById(R.id.spn_equipos_reto);
        button_enviar_resultado = findViewById(R.id.button_enviar_resultado);
        foto_retado_resultado = findViewById(R.id.foto_retado_resultado);
        foto_retador_resultado = findViewById(R.id.foto_retador_resultado);
        nombre_retado_resultado = findViewById(R.id.nombre_retado_resultado);
        nombre_retador_resultado = findViewById(R.id.nombre_retador_resultado);


        id_reto =  getIntent().getStringExtra("id_reto");
        retado =  getIntent().getStringExtra("retado");
        retado_id =  getIntent().getStringExtra("retado_id");
        retado_foto =  getIntent().getStringExtra("retado_foto");
        retador =  getIntent().getStringExtra("retador");
        retador_id =  getIntent().getStringExtra("retador_id");
        retador_foto=  getIntent().getStringExtra("retador_foto");


        nombre_retado_resultado.setText(retado);
        nombre_retador_resultado.setText(retador);

        UniversalImageLoader.setImage(IP2+"/"+retado_foto,foto_retado_resultado,null);
        UniversalImageLoader.setImage(IP2+"/"+retador_foto,foto_retador_resultado,null);

        llenarSpinner();

        button_enviar_resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviarResultado();

            }
        });
    }

    public  void llenarSpinner() {


        listaRetos =  new ArrayList<>();
        listaRetos.add(new RetosVersus(retado,retado_id));
        listaRetos.add(new RetosVersus(retador,retador_id));
        listaRetos.add(new RetosVersus("Empate","0"));

        for (RetosVersus obj : listaRetos) {
            listaSpinnerRetos.add(obj.nombre_participante);
        }
        //progressBar.setVisibility(ProgressBar.INVISIBLE);
        listaSpinnerRetos.add(0, "Seleccione");
        ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(this, R.layout.spiner_item, listaSpinnerRetos);
        adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
        spn_equipos_reto.setAdapter(adapEquipos);

    }


    StringRequest stringRequest;

    String respuesta;
    private void enviarResultado() {
        dialogCarga();
        String url =IP2+"/api/Torneo/dar_resultado";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dar resultado: ",""+response);

                JSONObject jsonObject;
                JSONArray jsonArray;

                try {
                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    respuesta = jsonNodev.optString("valor");
                    
                    if (respuesta.equals("1")){
                        Toast.makeText(ResutadoRetos.this, "registrado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        preferences.toasRojo("Lo sentimos hubo un error ", "intentelo más tarde ");
                    }
                    dialog_cargando.dismiss();
                    
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
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("reto_id",id_reto);
                parametros.put("ganador_id",listaRetos.get(spn_equipos_reto.getSelectedItemPosition()-1).getId_participante());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
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


    public class  RetosVersus {
        String nombre_participante;
        String id_participante;

        public RetosVersus() {
        }

        public RetosVersus(String nombre_participante, String id_participante) {
            this.nombre_participante = nombre_participante;
            this.id_participante = id_participante;
        }

        public String getNombre_participante() {
            return nombre_participante;
        }

        public void setNombre_participante(String nombre_participante) {
            this.nombre_participante = nombre_participante;
        }

        public String getId_participante() {
            return id_participante;
        }

        public void setId_participante(String id_participante) {
            this.id_participante = id_participante;
        }
    }
}
