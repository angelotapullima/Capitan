package com.tec.bufeo.capitan.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class ResutadoRetos extends AppCompatActivity {

    Spinner spn_equipos_reto;
    ArrayList<RetosVersus> listaRetos;
    ArrayList<String> listaSpinnerRetos = new ArrayList<String>();
    Preferences preferences;

    String retado,retador,retado_id,retador_id, retado_foto,retador_foto,id_reto;
    ImageView foto_retado_resultado,foto_retador_resultado;
    TextView nombre_retado_resultado,nombre_retador_resultado;
    AppCompatButton button_enviar_resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutado_retos);


        preferences =  new Preferences(this);
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

        Picasso.with(this).load(IP+"/"+retado_foto).error(R.drawable.error).fit().into(foto_retado_resultado);
        Picasso.with(this).load(IP+"/"+retador_foto).error(R.drawable.error).fit().into(foto_retador_resultado);

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
        String url =IP+"/index.php?c=Torneo&a=dar_resultado&key_mobile=123456asdfgh";
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
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("reto_id",id_reto);
                parametros.put("ganador_id",listaRetos.get(spn_equipos_reto.getSelectedItemPosition()-1).getId_participante());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
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
