package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;


import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class CalificarNegocios extends AppCompatActivity {

    RatingBar rtb_valor;
    String id_empresa,nombre_empresa;
    Float valor_rating;
    EditText reseña;
    TextView nombre_negocio,entendido;
    MaterialButton publicar;
    Preferences preferences;
    LinearLayout layoutEntendido;

    CoordinatorLayout cordinator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_negocios);

        preferences = new Preferences(this);

        cordinator=(CoordinatorLayout)findViewById(R.id.cordinator);
        rtb_valor=findViewById(R.id.rtb_valor);
        reseña=findViewById(R.id.reseña);
        nombre_negocio=findViewById(R.id.nombre_negocio);
        publicar=findViewById(R.id.publicar);
        entendido=findViewById(R.id.entendido);
        layoutEntendido=findViewById(R.id.layoutEntendido);

        valor_rating = Float.parseFloat(getIntent().getExtras().getString("valor_rating"));
        id_empresa = getIntent().getExtras().getString("id_empresa");
        nombre_empresa = getIntent().getExtras().getString("nombre_empresa");

        rtb_valor.setRating(valor_rating);
        nombre_negocio.setText(nombre_empresa);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarCalificacion();
            }
        });
        entendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEntendido.setVisibility(View.GONE);
            }
        });
    }


    StringRequest stringRequest;
    private void enviarCalificacion() {
        String url =IP2+"/api/Empresa/valorar_empresa";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("calificacion: ",""+response);





                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",response);
                    returnIntent.putExtra("valor",rtb_valor.getRating());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();


                    /*Snackbar snackbar = Snackbar
                            .make(cordinator, "Here is your new Snackbar", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    finish();*/



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPUESTA: ",""+error.toString());


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_usuario",preferences.getIdUsuarioPref());
                parametros.put("id_empresa",id_empresa);
                parametros.put("valor",String.valueOf(rtb_valor.getRating()));
                parametros.put("comentario",reseña.getText().toString());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.d("parametros: ",""+parametros.toString());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }
}
