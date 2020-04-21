package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;
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
    Preferences preferences;
    RadioGroup rg_enabled;
    LinearLayout Layoutcodigo,layoutRecarguex;
    TextView codigoG,montoCG,textoRecarga;
    String tipo;
    MaterialButton verAgentes,buttonRg2,btnCancelar;
    RadioButton rb;
    RelativeLayout layoutVerAgentes;

    boolean estado =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_recarga);

        preferences = new Preferences(this);
        edt_monto_solicitar= findViewById(R.id.edt_monto_solicitar);
        btn_generar= findViewById(R.id.btn_generar);
        Layoutcodigo= findViewById(R.id.Layoutcodigo);
        layoutRecarguex= findViewById(R.id.layoutRecarguex);
        codigoG= findViewById(R.id.codigoG);
        montoCG= findViewById(R.id.montoCG);
        textoRecarga= findViewById(R.id.textoRecarga);
        verAgentes= findViewById(R.id.verAgentes);
        buttonRg2= findViewById(R.id.buttonRg2);
        btnCancelar= findViewById(R.id.btnCancelar);
        layoutVerAgentes= findViewById(R.id.layoutVerAgentes);

        rg_enabled=findViewById(R.id.rg_enabled);
        rg_enabled.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 rb = group.findViewById(checkedId);

            }
        });

        btn_generar.setOnClickListener(this);
        verAgentes.setOnClickListener(this);
        buttonRg2.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        verSiTieneRecarga();

        showToolbar("Realizar recarga",true);

    }

    StringRequest stringRequest;
    String code,codigo,date_expiracion;

    String cantSolicitudes ,id_pagocip,pagocip_monto,pagocip_date_expiracion,pagocip_tipo;


    private void verSiTieneRecarga(){

        dialogCarga();


        String url =IP2+"/api/User/recarga_pendiente";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog_cargando.dismiss();
                Log.e("hay recargas", "onResponse: "+response );
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    cantSolicitudes = jsonObject.optString("hay");

                    if (Integer.parseInt(cantSolicitudes)==1){

                        JSONObject jsonArray = jsonObject.getJSONObject("results");
                        id_pagocip = jsonArray.optString("id_pagocip");
                        codigo = jsonArray.optString("pagocip_codigo");
                        pagocip_monto = jsonArray.optString("pagocip_monto");
                        pagocip_date_expiracion = jsonArray.optString("pagocip_date_expiracion");
                        pagocip_tipo = jsonArray.optString("pagocip_tipo");

                        String montex = "S/. " + pagocip_monto;

                        String textoA = "Acércate a cualquiera de nuestros agentes autorizados e indica que pagarás una recarga de Bufeo Tec con el código <font color=#000000 textStyle=bold>" + codigo + "</font> recuerda q el monto solicitado fue de S/. <font color=#000000>"+ pagocip_monto +"</font>  y tienes hasta el " + pagocip_date_expiracion + " para pagar.";
                        String textoB = "Realiza una transferencia bancaria al siguiente número de cuenta 01112122344 que está a nombre de Bufeo Tec con el monto de S/. <font color=#000000>"+ pagocip_monto +"</font> Una vez realizado enviar un mensaje de WhatsApp al número 999888777 enviando la foto del voucher y el código <font color=#000000>" + codigo + "</font>. Recuerda que tienes plazo hasta el " + pagocip_date_expiracion + " ";


                        if (pagocip_tipo.equals("Agente")) {
                            textoRecarga.setText(Html.fromHtml(textoA));


                        } else {
                            textoRecarga.setText(Html.fromHtml(textoB));
                            layoutVerAgentes.setVisibility(View.GONE);
                        }


                        preferences.toasVerde("Operación completada satisfactoriamente");
                        codigoG.setText(codigo);
                        montoCG.setText(montex);


                        edt_monto_solicitar.setText("");
                        dialog_cargando.dismiss();

                        Layoutcodigo.setVisibility(View.VISIBLE);
                        layoutVerAgentes.setVisibility(View.VISIBLE);
                        btnCancelar.setVisibility(View.VISIBLE);
                        layoutRecarguex.setVisibility(View.GONE);
                        buttonRg2.setVisibility(View.GONE);

                    }else if (Integer.parseInt(cantSolicitudes)==3){
                        dialogCancelar(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );
                dialog_cargando.dismiss();
                preferences.codeAdvertencia("Tenemos problemas con el internet, por favor inténtelo más tarde");
                btn_generar.setEnabled(false);
                estado=true;

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                Log.d("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);

    }
    private void generarRecarga(){

        dialogCarga();




        String url =IP2+"/api/Cuenta/save_recargar_mi_cuenta";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("recargar", "onResponse: "+response );
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject resultJSON = jsonObject.getJSONObject("result");

                    code = resultJSON.optString("code");
                    codigo = resultJSON.optString("codigo");
                    date_expiracion = resultJSON.optString("date_expiracion");

                    String montex = "S/. " + edt_monto_solicitar.getText().toString();

                    String textoA = "Acércate a cualquiera de nuestros agentes autorizados e indica que pagarás una recarga de Bufeo Tec con el código <font color=#000000 textStyle=bold>" + codigo + "</font> recuerda q el monto solicitado fue de S/. <font color=#000000>"+ edt_monto_solicitar.getText().toString() +"</font>  y tienes hasta el " + date_expiracion + " para pagar.";
                    String textoB = "Realiza una transferencia bancaria al siguiente número de cuenta 01112122344 que está a nombre de Bufeo Tec con el monto de S/. <font color=#000000>"+ edt_monto_solicitar.getText().toString() +"</font> Una vez realizado enviar un mensaje de WhatsApp al número 999888777 enviando la foto del voucher y el código <font color=#000000>" + codigo + "</font>. Recuerda que tienes plazo hasta el " + date_expiracion + " ";


                    if (tipo.equals("Agente")) {
                        textoRecarga.setText(Html.fromHtml(textoA));


                    } else {
                        textoRecarga.setText(Html.fromHtml(textoB));
                        layoutVerAgentes.setVisibility(View.GONE);
                    }


                    preferences.toasVerde("Operación completada satisfactoriamente");
                    codigoG.setText(codigo);
                    montoCG.setText(montex);


                    edt_monto_solicitar.setText("");
                    dialog_cargando.dismiss();

                Layoutcodigo.setVisibility(View.VISIBLE);
                    layoutVerAgentes.setVisibility(View.VISIBLE);
                    buttonRg2.setVisibility(View.VISIBLE);
                layoutRecarguex.setVisibility(View.GONE);
                    btnCancelar.setVisibility(View.GONE);


            } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );
                dialog_cargando.dismiss();
                preferences.codeAdvertencia("Tenemos problemas con el internet, por favor inténtelo más tarde");

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                if (rb.getText().toString().equals("Pagar a tráves de agente")){
                    tipo = "Agente";
                }else if(rb.getText().toString().equals("Pagar por banca móvil BCP")){
                    tipo = "Bcp";
                }else if(rb.getText().toString().equals("Pagar por banca móvil Interbank")){
                    tipo = "Interbank";
                }else if(rb.getText().toString().equals("Pagar por banca móvil BBVA")){
                    tipo = "Bbva";
                }
                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("monto",edt_monto_solicitar.getText().toString());
                parametros.put("app","true");
                parametros.put("tipo",tipo);
                parametros.put("token",preferences.getToken());

                Log.d("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);

    }
    private void cancelarRecarga(){

        dialogCarga();

        String url =IP2+"/api/User/cancelar_recarga";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("cancelar", "recarga: "+response );

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int resul = jsonObject.optInt("results");

                    if (resul==1){
                        Layoutcodigo.setVisibility(View.GONE);
                        layoutRecarguex.setVisibility(View.VISIBLE);
                    }else{
                        preferences.toasRojo("Lo sentimos, hubo un error" ,"Inténtelo de nuevo , en un momento");
                    }



                    dialog_cargando.dismiss();






                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );
                dialog_cargando.dismiss();
                preferences.codeAdvertencia("Tenemos problemas con el internet, por favor inténtelo más tarde");

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_pagocip",id_pagocip);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                Log.d("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);

    }

    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_cargando.findViewById(R.id.back);
        LinearLayout layout = dialog_cargando.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cargando.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
        });

        dialog_cargando.show();

    }
    Dialog dialog_cancelar;
    public void dialogCancelar(boolean cantidad){

        dialog_cancelar= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cancelar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cancelar.setCancelable(true);
        dialog_cancelar.setContentView(R.layout.dialogo_cancelar_recarga);
        MaterialButton cancelar = dialog_cancelar.findViewById(R.id.cancelar);
        MaterialButton continuar = dialog_cancelar.findViewById(R.id.continuar);
        MaterialButton continuarExpirado = dialog_cancelar.findViewById(R.id.continuarExpirado);
        LinearLayout botones = dialog_cancelar.findViewById(R.id.botones);
        TextView texto = dialog_cancelar.findViewById(R.id.texto);

        if(!cantidad){
            texto.setText("¿estas seguro de cancelar está recarga?");
            continuarExpirado.setVisibility(View.GONE);
            botones.setVisibility(View.VISIBLE);
        }else{
            texto.setText("Su recarga ya expiro , por favor intente solicita otra recarga");
            continuarExpirado.setVisibility(View.VISIBLE);
            botones.setVisibility(View.GONE);
        }



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cancelar.dismiss();
            }
        });
        continuarExpirado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cancelar.dismiss();
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cancelar.dismiss();
                cancelarRecarga();
            }
        });

        dialog_cancelar.show();

    }
    @Override
    public void onClick(View v) {
        if (v.equals(btn_generar)){
            if (estado==true){

                preferences.codeAdvertencia("Tenemos problemas con el internet, por favor inténtelo más tarde");
            }else{
                if (rb.getText().toString().equals("")){
                    preferences.codeAdvertencia("Debe seleccionar un método de pago");
                }else{
                    if (!edt_monto_solicitar.getText().toString().equals("") ){
                        if (!edt_monto_solicitar.getText().toString().equals("0") ){
                            if ( Integer.parseInt(edt_monto_solicitar.getText().toString())>1000 ){
                                preferences.codeAdvertencia("El valor de la recarga, supero los límite");
                            }else if (Integer.parseInt(String.valueOf(edt_monto_solicitar.getText().toString()))<5){
                                preferences.codeAdvertencia("El valor de la recarga, es menor al límite establecido");
                            }else{
                                generarRecarga();
                            }



                        }else{
                            preferences.codeAdvertencia("El monto a solicitar no puede ser 0");
                        }
                    }else{
                        preferences.codeAdvertencia("El monto a solicitar no puede ser vacio");
                    }
                }
            }



        }else if (v.equals(buttonRg2)){
            finish();
        }else if(v.equals(verAgentes)){

        }else if(v.equals(btnCancelar)){
            dialogCancelar(false);
        }
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);//y habilitamos la flacha hacia atras

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }
}
