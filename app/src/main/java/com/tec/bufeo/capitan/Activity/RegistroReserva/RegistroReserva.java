package com.tec.bufeo.capitan.Activity.RegistroReserva;


import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.NOUSADOSCSMARE.ConfirmacionReserva;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository.ReservasCanchaWebServiceRepository;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.ViewModels.ReservasCanchaListViewModel;
import com.tec.bufeo.capitan.Activity.RealizarRecarga;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RegistroReserva extends AppCompatActivity implements View.OnClickListener {


    String h_reserva,precio_cancha,fecha,nombre_empresa,cancha_nombre,saldo,cancha_id;
    TextView hora_reserva,nombre_empresa_Reserva,nombre_cancha_reserva,fecha_reserva,saldo_bufis,
            precioDeLaCancha,comisionCancha,precioAPagar;
    Spinner spn_tipo_pago,spn_equipex;
    LinearLayout layout_bufis,layout_equipo,layout_botones;
    LinearLayout btn_reservar,recargaSaldo,btn_cerrar;//layout_precio_con_chancha,layout_precios;
    Preferences preferences;
    ArrayList<String> arrayEquipo;
    ArrayList<Mequipos> ListEquipos = new ArrayList<>();
    MisEquiposViewModel misEquiposViewModel;
    double total;
    EditText nombre_reserva;
    String comision;
    RecyclerView rcv_colaboraciones;
    ImageView noChanchas;
    RelativeLayout relRes;
    boolean permiso =false;
    ReservasCanchaListViewModel reservasCanchaListViewModel;

    String telefono,telefono2,direccion,foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_reserva);

        misEquiposViewModel= ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        reservasCanchaListViewModel= ViewModelProviders.of(this).get(ReservasCanchaListViewModel.class);

        preferences= new Preferences(this);

        comision =preferences.getComision();
        //layout_precio_con_chancha = findViewById(R.id.layout_precio_con_chancha);
        hora_reserva = findViewById(R.id.hora_reserva);
        nombre_empresa_Reserva = findViewById(R.id.nombre_empresa_Reserva);
        nombre_cancha_reserva = findViewById(R.id.nombre_cancha_reserva);
        fecha_reserva = findViewById(R.id.fecha_reserva);
        spn_tipo_pago = findViewById(R.id.spn_tipo_pago);
        spn_equipex = findViewById(R.id.spn_equipex);
        //layout_precios = findViewById(R.id.layout_precios);
        layout_bufis = findViewById(R.id.layout_bufis);
        layout_equipo = findViewById(R.id.layout_equipo);
        btn_cerrar = findViewById(R.id.btn_cerrar);
        saldo_bufis = findViewById(R.id.saldo_bufis);
        precioDeLaCancha = findViewById(R.id.precioDeLaCancha);
        comisionCancha = findViewById(R.id.comisionCancha);
        precioAPagar = findViewById(R.id.precioAPagar);
        btn_reservar = findViewById(R.id.btn_reservar);
        recargaSaldo = findViewById(R.id.recargaSaldo);
        nombre_reserva = findViewById(R.id.nombre_reserva);
        rcv_colaboraciones = findViewById(R.id.rcv_colaboraciones);
        layout_botones = findViewById(R.id.layout_botones);
        relRes = findViewById(R.id.relRes);
        noChanchas = findViewById(R.id.noChanchas);


        Colaboraciones();
        // horaclick

        h_reserva = getIntent().getStringExtra("h_reserva");
        precio_cancha = getIntent().getStringExtra("precio_cancha");
        fecha = getIntent().getStringExtra("fecha");
        nombre_empresa = getIntent().getStringExtra("nombre_empresa");
        cancha_nombre = getIntent().getStringExtra("cancha_nombre");
        saldo = getIntent().getStringExtra("saldo");
        cancha_id = getIntent().getStringExtra("cancha_id");
        telefono = getIntent().getStringExtra("telefono");
        telefono2 = getIntent().getStringExtra("telefono2");
        direccion = getIntent().getStringExtra("direccion");
        foto = getIntent().getStringExtra("foto");

        if (Float.parseFloat(saldo)>0){
            permiso=true;
        }
        hora_reserva.setText(h_reserva);
        nombre_empresa_Reserva.setText(nombre_empresa);
        nombre_cancha_reserva.setText(cancha_nombre);
        fecha_reserva.setText(fecha);
        saldo_bufis.setText(saldo);

        precioDeLaCancha.setText(precio_cancha);
        comisionCancha.setText(String.valueOf(comision));

        total = Double.parseDouble(precio_cancha )+ Double.parseDouble(comision);
        precioAPagar.setText(String.valueOf(total));



        spn_tipo_pago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    //layout_precios.setVisibility(View.GONE);
                    layout_bufis.setVisibility(View.GONE);
                    rcv_colaboraciones.setVisibility(View.GONE);
                    layout_equipo.setVisibility(View.GONE);
                    layout_botones.setVisibility(View.GONE);
                    //layout_precio_con_chancha.setVisibility(View.GONE);
                }else if(position==1){
                    //layout_precios.setVisibility(View.VISIBLE);
                    layout_bufis.setVisibility(View.VISIBLE);
                    //layout_precio_con_chancha.setVisibility(View.VISIBLE);
                    layout_equipo.setVisibility(View.VISIBLE);
                    rcv_colaboraciones.setVisibility(View.GONE);
                    layout_botones.setVisibility(View.VISIBLE);
                }else{
                    //layout_precios.setVisibility(View.VISIBLE);
                    layout_bufis.setVisibility(View.GONE);
                    layout_equipo.setVisibility(View.GONE);
                    rcv_colaboraciones.setVisibility(View.VISIBLE);
                    recargaSaldo.setVisibility(View.GONE);
                    layout_botones.setVisibility(View.GONE);
                    //layout_precio_con_chancha.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        misEquiposViewModel.getAllEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(List<Mequipos> mequipos) {
                if(mequipos.size()>0){
                    ListEquipos.clear();
                    ListEquipos.addAll(mequipos);
                    new RegistroReserva.GetEquipos().execute();
                    relRes.setVisibility(View.GONE);
                }else{
                    relRes.setVisibility(View.VISIBLE);
                }

            }
        });
        btn_reservar.setOnClickListener(this);
        recargaSaldo.setOnClickListener(this);
        btn_cerrar.setOnClickListener(this);
        showToolbar("Reservar Cancha",true);


    }
    String pago_tipo,colaboracion,equipo_id,pago1;
    @Override
    public void onClick(View v) {
        if (v.equals(btn_reservar)){

            if (spn_tipo_pago.getSelectedItem().toString().equals("Seleccionar")){

                preferences.codeAdvertencia("Debe seleccionar un tipo de Pago");
            }else{
                if (spn_tipo_pago.getSelectedItem().toString().equals("Yo pago todo")){


                    if (permiso ==true){
                        if (nombre_reserva.getText().toString().isEmpty()){

                            preferences.codeAdvertencia("el campo nombre no debe estar vacio");
                        }else{
                            if (spn_equipex.getSelectedItem().toString().equals("Seleccionar Equipo")){

                                preferences.codeAdvertencia("Debe seleccionar un Equipo");
                            }else{


                                registrarReservaUsuario("0","0","1");


                            }
                        }
                    }else{
                        preferences.toasRojo("No cuentas con Bufis para esta operación", "por favor , recargue su saldo");
                        recargaSaldo.setVisibility(View.VISIBLE);
                    }



                }


            }

        }else if (v.equals(recargaSaldo)){
            Intent i = new Intent(RegistroReserva.this, RealizarRecarga.class);
            startActivity(i);
        }else if(v.equals(btn_cerrar)){
            finish();
        }
    }





    public class GetEquipos extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayEquipo =  new ArrayList<String>();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(ListEquipos.size()>0){
                for (Mequipos obj : ListEquipos) {
                    arrayEquipo.add(obj.getEquipo_nombre());
                }
            }else{
                arrayEquipo.add(0,"No tienes Equipos ");
            }

            arrayEquipo.add(0,"Seleccionar Equipo ");


            ArrayAdapter<String > adapchanchas= new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayEquipo);
            adapchanchas.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_equipex.setAdapter(adapchanchas);






        }
    }

    Application application;
    StringRequest stringRequest;
    private void registrarReservaUsuario(final String id_chancha,final String id_equipo,final String estado_pago){

        dialogCarga();



        String url =IP2+"/api/Empresa/registrar_reserva";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("registrar_reserva", "onResponse: "+response );



                if (response.equals("1")){
                    preferences.toasVerde("Registro Completo");
                    Intent i = new Intent(RegistroReserva.this, ConfirmacionReserva.class);
                    i.putExtra("cancha",nombre_cancha_reserva.getText().toString());
                    i.putExtra("lugar",nombre_empresa_Reserva.getText().toString());
                    i.putExtra("hora",h_reserva);
                    i.putExtra("fecha",fecha);
                    i.putExtra("precio",precioAPagar.getText().toString());
                    i.putExtra("nombre",nombre_reserva.getText().toString());
                    i.putExtra("direccion",direccion);
                    i.putExtra("telefono",telefono);
                    i.putExtra("telefono2",telefono2);
                    startActivity(i);
                    dialog_carga.dismiss();
                    ReservasCanchaWebServiceRepository reservasCanchaWebServiceRepository = new ReservasCanchaWebServiceRepository(application);
                    reservasCanchaWebServiceRepository.providesWebService(fecha,cancha_id,preferences.getToken());
                    finish();


                }else{
                    preferences.toasRojo("Lo sentimos hubo un error al registrar la reserva","intentelo nuevamente más tarde");

                    dialog_carga.dismiss();

                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );
                dialog_carga.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                if (spn_tipo_pago.getSelectedItem().toString().equals("Yo pago todo")){

                    pago_tipo="1";
                    pago1=precioDeLaCancha.getText().toString();
                    colaboracion="0";
                    equipo_id = ListEquipos.get(spn_equipex.getSelectedItemPosition()-1).getEquipo_id();
                }else {
                    pago_tipo="2";
                    colaboracion=id_chancha;
                    equipo_id = id_equipo;
                    pago1=String.valueOf(precio_cancha);
                }



                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_cancha",cancha_id);
                parametros.put("pago_id_user",preferences.getIdUsuarioPref());
                parametros.put("pago_equipo_id",equipo_id);
                parametros.put("nombre",nombre_reserva.getText().toString());
                parametros.put("hora",h_reserva);
                parametros.put("pago1",pago1);
                parametros.put("pago_tipo",pago_tipo);
                parametros.put("id_colaboracion",colaboracion);
                parametros.put("pago_comision",String.valueOf(comision));
                parametros.put("tipopago","1");
                parametros.put("estado",estado_pago);
                parametros.put("fecha",fecha);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                parametros.put("id_user",preferences.getIdUsuarioPref());
                Log.d("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);

    }

    Dialog dialog_carga;
    public void dialogCarga(){

        dialog_carga= new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_carga.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_carga.setCancelable(true);
        dialog_carga.setContentView(R.layout.dialog_carga_reserva);

        dialog_carga.show();

    }

    JSONObject jsonObject,jsonNode,jsonNode2,jsonNodecito;
    JSONArray resultJSON,jsonArray;
    Colaboraciones colaboraciones;
    DetalleColaboraciones detalleColaboraciones;


    public List<Colaboraciones> listaItem = new ArrayList<>();
    private void Colaboraciones() {
        String url =IP2+"/api/Empresa/obtener_chanchas_disponibles";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("chanchas_disponibles ",""+response);



                try {
                    jsonObject = new JSONObject(response);
                    resultJSON = jsonObject.getJSONArray("results");


                    listaItem.clear();
                    int count = resultJSON.length();
                    if (count==0){
                        noChanchas.setVisibility(View.VISIBLE);
                    }else{
                        noChanchas.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < count; i++) {

                        jsonNode = resultJSON.getJSONObject(i);


                        String ColaboracionID = jsonNode.optString("id");
                        String equipo_nombre = jsonNode.optString("equipo");
                        String colaboracion_nombre = jsonNode.optString("nombre");
                        String colaboracion_date = jsonNode.optString("fecha");
                        String id_equipo = jsonNode.optString("id_equipo");
                        jsonArray = jsonNode.getJSONArray("detalle");

                        int coun1 = jsonArray.length();

                        double monto_final_chancha=0,monto_final=0;
                        for (int x = 0; x < coun1; x++) {
                            jsonNodecito = jsonArray.getJSONObject(x);

                            monto_final_chancha = Double.parseDouble(jsonNodecito.optString("detalle_colaboracion_monto"));
                            monto_final = monto_final + monto_final_chancha;


                        }

                        colaboraciones = new Colaboraciones(ColaboracionID,id_equipo,equipo_nombre,colaboracion_nombre,colaboracion_date,String.valueOf(monto_final),buildSubItemList(jsonArray));
                        listaItem.add(colaboraciones);
                        //listaSubItem.clear();

                    }


                    AdapterColaboracionesItem itemAdapter = new AdapterColaboracionesItem(getApplicationContext(), listaItem, new AdapterColaboracionesItem.OnItemClickListener() {
                        @Override
                        public void onItemClick(Colaboraciones mequipos, String tipo, int position) {
                            if (tipo.equals("btn_reservar_en_chancha")){
                                Log.d("precios", "onItemClick: " + total +" - " + mequipos.getMonto_final() );
                                if (Double.parseDouble(mequipos.getMonto_final())> total){

                                    preferences.codeAdvertencia("El monto de la chancha supera el precio de la  cancha");

                                }else if (Double.parseDouble(mequipos.getMonto_final())< total){

                                    preferences.codeAdvertencia("El monto de la chancha es menor al precio de la  cancha");
                                }else{
                                    if (nombre_reserva.getText().toString().isEmpty()){

                                        preferences.codeAdvertencia("El campo reserva nombre de la reserva no debe estar vacio");
                                    }else{
                                        if (mequipos.getColaboracion_id()!=null || mequipos.getEquipo_id()!=null){
                                            registrarReservaUsuario( mequipos.getColaboracion_id(),mequipos.getEquipo_id(),"1");
                                        }else{
                                            preferences.codeAdvertencia("Valores nulos");

                                        }

                                    }

                                }


                            }
                        }
                    });
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                    rcv_colaboraciones.setLayoutManager(linearLayoutManager);
                    rcv_colaboraciones.setAdapter(itemAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.d("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",preferences.getIdUsuarioPref());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());


                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }


    private List<DetalleColaboraciones> buildSubItemList(JSONArray array) {
        List<DetalleColaboraciones> subItemList = new ArrayList<>();

        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);

                String detalle_colaboracion_monto,user_nickname;

                detalle_colaboracion_monto = jsonNode2.optString("detalle_colaboracion_monto");
                user_nickname = jsonNode2.optString("user_nickname");


                detalleColaboraciones = new DetalleColaboraciones(user_nickname,detalle_colaboracion_monto);
                subItemList.add(detalleColaboraciones);




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return subItemList;
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