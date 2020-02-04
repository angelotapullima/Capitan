package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Repository.MovimientosRoomDBRepository;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RealizarRecarga extends AppCompatActivity implements View.OnClickListener {

    EditText edt_monto_solicitar;
    Button btn_generar;
    TextView codigo_generado,texto_ayuda;
    Preferences preferences;
    ImageView LogoBufeo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_recarga);

        preferences = new Preferences(this);
        edt_monto_solicitar= findViewById(R.id.edt_monto_solicitar);
        btn_generar= findViewById(R.id.btn_generar);
        codigo_generado= findViewById(R.id.codigo_generado);
        texto_ayuda= findViewById(R.id.texto_ayuda);
        LogoBufeo= findViewById(R.id.LogoBufeo);


        btn_generar.setOnClickListener(this);

    }

    StringRequest stringRequest;
    String code,codigo,date_expiracion;

    private void generarRecarga(){

        dialogCarga();




        String url =IP2+"/api/Cuenta/save_recargar_mi_cuenta";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject resultJSON = jsonObject.getJSONObject("result");

                    code = resultJSON.optString("code");
                    codigo = resultJSON.optString("codigo");
                    date_expiracion = resultJSON.optString("date_expiracion");


                    String ayuda = "Código generado válido hasta " + date_expiracion + " para usarlo en cualquiera de nuestro agentes afiliados a BufeoTec";

                    LogoBufeo.setVisibility(View.VISIBLE);
                    codigo_generado.setVisibility(View.VISIBLE);
                    texto_ayuda.setVisibility(View.VISIBLE);


                    codigo_generado.setText(codigo);
                    texto_ayuda.setText(ayuda);
                    edt_monto_solicitar.setText("");
                    dialog_cargando.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: "+error.toString() );
                dialog_cargando.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("monto",edt_monto_solicitar.getText().toString());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                Log.e("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
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

    @Override
    public void onClick(View v) {
        if (v.equals(btn_generar)){

            if (!edt_monto_solicitar.getText().toString().equals("") ){
                if (!edt_monto_solicitar.getText().toString().equals("0") ){

                    generarRecarga();

                }else{
                    Toast.makeText(RealizarRecarga.this, "El monto a solicitar no puede ser 0", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RealizarRecarga.this, "El monto a solicitar no puede ser vacio", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
