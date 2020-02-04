package com.tec.bufeo.capitan.Activity.RegistroReserva;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
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

    Reserva reserva;

    Activity activity;
    DataConnection dc,dc1;




    String h_reserva,precio_cancha,fecha,nombre_empresa,cancha_nombre,saldo,cancha_id;
    TextView hora_reserva,nombre_empresa_Reserva,nombre_cancha_reserva,fecha_reserva,saldo_bufis,precioDeLaCancha,comisionCancha,precioAPagar,txt_costo_cancha;
    Spinner spn_tipo_pago,spn_equipex;
    LinearLayout layout_precios,layout_bufis,layout_equipo,layout_botones,layout_costo_cancha;
    LinearLayout btn_reservar,layout_precio_con_chancha,ingresar_precio;
    Preferences preferences;
    ArrayList<String> arrayEquipo;
    ArrayList<Mequipos> ListEquipos = new ArrayList<>();
    MisEquiposViewModel misEquiposViewModel;
    double comision = 3,total;
    double monto_a_pagar ;
    EditText nombre_reserva;
    RecyclerView rcv_colaboraciones;
    EditText precioDeCanchaTodo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_reserva);

        misEquiposViewModel= ViewModelProviders.of(this).get(MisEquiposViewModel.class);

        preferences= new Preferences(this);
        layout_precio_con_chancha = findViewById(R.id.layout_precio_con_chancha);
        hora_reserva = findViewById(R.id.hora_reserva);
        ingresar_precio = findViewById(R.id.ingresar_precio);
        nombre_empresa_Reserva = findViewById(R.id.nombre_empresa_Reserva);
        nombre_cancha_reserva = findViewById(R.id.nombre_cancha_reserva);
        fecha_reserva = findViewById(R.id.fecha_reserva);
        spn_tipo_pago = findViewById(R.id.spn_tipo_pago);
        spn_equipex = findViewById(R.id.spn_equipex);
        layout_precios = findViewById(R.id.layout_precios);
        layout_bufis = findViewById(R.id.layout_bufis);
        layout_equipo = findViewById(R.id.layout_equipo);
        saldo_bufis = findViewById(R.id.saldo_bufis);
        precioDeLaCancha = findViewById(R.id.precioDeLaCancha);
        comisionCancha = findViewById(R.id.comisionCancha);
        precioAPagar = findViewById(R.id.precioAPagar);
        btn_reservar = findViewById(R.id.btn_reservar);
        nombre_reserva = findViewById(R.id.nombre_reserva);
        rcv_colaboraciones = findViewById(R.id.rcv_colaboraciones);
        layout_botones = findViewById(R.id.layout_botones);
        precioDeCanchaTodo = findViewById(R.id.precioDeCanchaTodo);
        txt_costo_cancha = findViewById(R.id.txt_costo_cancha);
        layout_costo_cancha = findViewById(R.id.layout_costo_cancha);


        Colaboraciones();
        // horaclick

        h_reserva = getIntent().getStringExtra("h_reserva");
        precio_cancha = getIntent().getStringExtra("precio_cancha");
        fecha = getIntent().getStringExtra("fecha");
        nombre_empresa = getIntent().getStringExtra("nombre_empresa");
        cancha_nombre = getIntent().getStringExtra("cancha_nombre");
        saldo = getIntent().getStringExtra("saldo");
        cancha_id = getIntent().getStringExtra("cancha_id");

        hora_reserva.setText(h_reserva);
        nombre_empresa_Reserva.setText(nombre_empresa);
        nombre_cancha_reserva.setText(cancha_nombre);
        fecha_reserva.setText(fecha);
        saldo_bufis.setText(saldo);
        txt_costo_cancha.setText(precio_cancha);
        precioDeLaCancha.setText(precio_cancha);
        comisionCancha.setText(String.valueOf(comision));

        total = Double.parseDouble(precio_cancha )+ comision;
        precioAPagar.setText(String.valueOf(total));



        spn_tipo_pago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    layout_precios.setVisibility(View.GONE);
                    layout_bufis.setVisibility(View.GONE);
                    rcv_colaboraciones.setVisibility(View.GONE);
                    layout_equipo.setVisibility(View.GONE);
                    layout_botones.setVisibility(View.GONE);
                    ingresar_precio.setVisibility(View.GONE);
                    layout_costo_cancha.setVisibility(View.GONE);
                    layout_precio_con_chancha.setVisibility(View.GONE);
                }else if(position==1){
                    layout_precios.setVisibility(View.VISIBLE);
                    layout_bufis.setVisibility(View.VISIBLE);
                    ingresar_precio.setVisibility(View.VISIBLE);
                    layout_costo_cancha.setVisibility(View.VISIBLE);
                    layout_precio_con_chancha.setVisibility(View.GONE);
                    layout_equipo.setVisibility(View.VISIBLE);
                    rcv_colaboraciones.setVisibility(View.GONE);
                    layout_botones.setVisibility(View.VISIBLE);
                }else{
                    layout_precios.setVisibility(View.VISIBLE);
                    layout_bufis.setVisibility(View.GONE);
                    layout_equipo.setVisibility(View.GONE);
                    rcv_colaboraciones.setVisibility(View.VISIBLE);
                    layout_botones.setVisibility(View.GONE);
                    layout_costo_cancha.setVisibility(View.GONE);
                    ingresar_precio.setVisibility(View.GONE);
                    layout_precio_con_chancha.setVisibility(View.VISIBLE);
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
                }

            }
        });
        btn_reservar.setOnClickListener(this);
        precioDeCanchaTodo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String v = String.valueOf(s);

                monto_a_pagar = Double.parseDouble(v) + comision;
                precioAPagar.setText(String.valueOf(monto_a_pagar));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
String pago_tipo,colaboracion,equipo_id,pago1;
    @Override
    public void onClick(View v) {
        if (v.equals(btn_reservar)){

            if (spn_tipo_pago.getSelectedItem().toString().equals("Seleccionar")){

                Toast.makeText(getApplicationContext(), "Debe seleccionar un tipo de Pago", Toast.LENGTH_SHORT).show();
            }else{
                if (spn_tipo_pago.getSelectedItem().toString().equals("Yo pago todo")){

                    if (nombre_reserva.getText().toString().isEmpty()){

                        Toast.makeText(getApplicationContext(), "el campo nombre no debe estar vacio", Toast.LENGTH_SHORT).show();
                    }else{
                        if (spn_equipex.getSelectedItem().toString().equals("Seleccionar Equipo")){

                            Toast.makeText(getApplicationContext(), "Debe seleccionar un Equipo", Toast.LENGTH_SHORT).show();
                        }else{

                            if (Double.parseDouble(precioDeCanchaTodo.getText().toString()) > Double.parseDouble(precio_cancha)){
                                Toast.makeText(getApplicationContext(), "El monto a pagar no puede ser mayor al costo de la cancha", Toast.LENGTH_SHORT).show();
                            }else if (Double.parseDouble(precioDeCanchaTodo.getText().toString()) < Double.parseDouble(precio_cancha)){
                                registrarReservaUsuario("0","0","2");
                            }else{
                                registrarReservaUsuario("0","0","1");
                            }

                        }
                    }
                }


            }

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

    StringRequest stringRequest;
    private void registrarReservaUsuario(final String id_chancha,final String id_equipo,final String estado_pago){

        dialogCarga();



        String url =IP2+"/api/Empresa/registrar_reserva";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("registrar_reserva", "onResponse: "+response );

                if (response.equals("1")){
                    Toast.makeText(getApplicationContext(), "Registro Completo", Toast.LENGTH_SHORT).show();
                    dialog_carga.dismiss();
                    finish();


                }else{
                    Toast.makeText(getApplicationContext(), "Fallo al registrar la reserva", Toast.LENGTH_SHORT).show();
                    dialog_carga.dismiss();

                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: "+error.toString() );
                dialog_carga.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                if (spn_tipo_pago.getSelectedItem().toString().equals("Yo pago todo")){

                    pago_tipo="1";
                    pago1=precioDeCanchaTodo.getText().toString();
                    colaboracion="0";
                    equipo_id = ListEquipos.get(spn_equipex.getSelectedItemPosition()).getEquipo_id();
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
                Log.e("parametros", "parametros: "+parametros.toString() );
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

                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                    rcv_colaboraciones.setLayoutManager(linearLayoutManager);
                    AdapterColaboracionesItem itemAdapter = new AdapterColaboracionesItem(getApplicationContext(), listaItem, new AdapterColaboracionesItem.OnItemClickListener() {
                        @Override
                        public void onItemClick(Colaboraciones mequipos, String tipo, int position) {
                            if (tipo.equals("btn_reservar_en_chancha")){
                                Log.e("precios", "onItemClick: " + total +" - " + mequipos.getMonto_final() );
                                if (Double.parseDouble(mequipos.getMonto_final())> total){

                                    Toast.makeText(getApplicationContext(), "El monto de la chancha supera el precio de la  cancha", Toast.LENGTH_SHORT).show();
                                }else if (Double.parseDouble(mequipos.getMonto_final())< total){

                                    Toast.makeText(getApplicationContext(), "El monto de la chancha es menor al precio de la  cancha", Toast.LENGTH_SHORT).show();
                                }else{
                                    if (nombre_reserva.getText().toString().isEmpty()){

                                        Toast.makeText(getApplicationContext(), "El campo reserva nombre de la reserva no debe estar vacio", Toast.LENGTH_SHORT).show();
                                    }else{
                                        if (mequipos.getColaboracion_id()!=null || mequipos.getEquipo_id()!=null){
                                            registrarReservaUsuario( mequipos.getColaboracion_id(),mequipos.getEquipo_id(),"1");
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Valores nulos", Toast.LENGTH_SHORT).show();

                                        }

                                    }

                            }


                            }
                        }
                    });
                    rcv_colaboraciones.setAdapter(itemAdapter);


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
}