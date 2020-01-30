package com.tec.bufeo.capitan.Activity.RegistroReserva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ReservaEnBusqueda extends AppCompatActivity implements View.OnClickListener {


    //yo pago todo
    LinearLayout l_todo;
    Spinner spn_equipex_busqueda;
    TextView total_cancha_todo,pago_total_todo,comision_todo;
    EditText pago1_todo;
    double comision_todo_dato = 3,monto_todo;


    //chancha
    LinearLayout l_chancha;
    RecyclerView rcv_colaboraciones_busqueda;
    TextView pago1_chancha,comision_chancha,pago_total_chancha;


    //ambos

    TextView nombre_empresa,hora_r,fecha_r;
    Spinner spn_tipo_pago_busqueda,spn_cancha_busqueda;
    MisEquiposViewModel misEquiposViewModel;
    DataConnection dc4,dc2;
    Preferences preferences;

    String nombre_empresa_dato,empresa_id,h_reserva,fecha,hora;
    LinearLayout btn_reservar_busqueda;
    TextView nombre_reserva_busqueda,saldo_bufis_busqueda;



    ArrayList<String> arrayEquipo_busqueda,arrayCanchaBusqueda;
    ArrayList<Mequipos> ListEquipos_busqueda = new ArrayList<>();

    double total_busqueda ,precio_cancha_busqueda;
    ArrayList<String> saldo;
    String saldo_cargado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_en_busqueda);

        misEquiposViewModel= ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        nombre_empresa_dato = getIntent().getExtras().getString("nombre_empresa");
        empresa_id = getIntent().getExtras().getString("empresa_id");
        h_reserva = getIntent().getExtras().getString("h_reserva");

        dc4 = new DataConnection(ReservaEnBusqueda.this,"ObtenerSaldo",false);
        new ReservaEnBusqueda.GetSaldo().execute();

        dc2 = new DataConnection(ReservaEnBusqueda.this,"listarcanchasEmpresas",new Cancha(empresa_id),false);
        new GetListadoCanchas().execute();

        Date hoy = new Date();
        SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hours = new SimpleDateFormat("HH");
        fecha = objSDF.format(hoy);
        hora = hours.format(hoy);

        preferences= new Preferences(this);

        /*  Widgets que trabajan con YoPagoTodo*/
        //layout para ocultar contenido de yo pagoTodo
        l_todo = findViewById(R.id.l_todo);

        comision_todo  = findViewById(R.id.comision_todo);
        pago_total_todo  = findViewById(R.id.pago_total_todo);
        pago1_todo = findViewById(R.id.pago1_todo);
        total_cancha_todo  = findViewById(R.id.total_cancha_todo);
        saldo_bufis_busqueda  = findViewById(R.id.saldo_bufis_busqueda);


        /*  Widgets que trabajan con Chancha*/
        //layout para ocultar contenido de Chancha
        l_chancha = findViewById(R.id.l_chancha);
        comision_chancha  = findViewById(R.id.comision_chancha);
        pago_total_chancha  = findViewById(R.id.pago_total_chancha);
        pago1_chancha = findViewById(R.id.pago1_chancha);


        /*  Datos Generales*/
        nombre_empresa = findViewById(R.id.nombre_empresa);
        hora_r = findViewById(R.id.hora_r);
        fecha_r = findViewById(R.id.fecha_r);
        spn_tipo_pago_busqueda = findViewById(R.id.spn_tipo_pago_busqueda);
        spn_equipex_busqueda = findViewById(R.id.spn_equipex_busqueda);
        spn_cancha_busqueda = findViewById(R.id.spn_cancha_busqueda);
        nombre_reserva_busqueda  = findViewById(R.id.nombre_reserva_busqueda);






        btn_reservar_busqueda  = findViewById(R.id.btn_reservar_busqueda);
        rcv_colaboraciones_busqueda = findViewById(R.id.rcv_colaboraciones_busqueda);





        Colaboraciones_busqueda();


        nombre_empresa.setText(nombre_empresa_dato);
        hora_r.setText(h_reserva);
        fecha_r.setText(fecha);


        spn_tipo_pago_busqueda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    l_todo.setVisibility(View.GONE);
                    l_chancha.setVisibility(View.GONE);


                }else if(position==1){
                    l_todo.setVisibility(View.VISIBLE);
                    l_chancha.setVisibility(View.GONE);


                }else{
                    l_todo.setVisibility(View.GONE);
                    l_chancha.setVisibility(View.VISIBLE);
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
                    ListEquipos_busqueda.clear();
                    ListEquipos_busqueda.addAll(mequipos);
                    new ReservaEnBusqueda.GetEquipos().execute();
                }

            }
        });

        btn_reservar_busqueda.setOnClickListener(this);

        pago_total_todo.setText(String.valueOf(Double.parseDouble(pago1_todo.getText().toString()) + comision_todo_dato));
    }

    @Override
    public void onClick(View v) {


        if (v.equals(btn_reservar_busqueda)){

            String pagox =pago1_todo.getText().toString();
            if (!pagox.equals("0")){
                if (spn_tipo_pago_busqueda.getSelectedItem().toString().equals("Seleccionar")){

                    Toast.makeText(getApplicationContext(), "Debe seleccionar un tipo de Pago", Toast.LENGTH_SHORT).show();
                }else{
                    if (spn_tipo_pago_busqueda.getSelectedItem().toString().equals("Yo pago todo")){

                        if (nombre_reserva_busqueda.getText().toString().isEmpty()){

                            Toast.makeText(getApplicationContext(), "el campo nombre no debe estar vacio", Toast.LENGTH_SHORT).show();
                        }else{
                            if (spn_equipex_busqueda.getSelectedItem().toString().equals("Seleccionar Equipo")){

                                Toast.makeText(getApplicationContext(), "Debe seleccionar un Equipo", Toast.LENGTH_SHORT).show();
                            }else{

                                if (Double.parseDouble(pago1_todo.getText().toString()) > Double.parseDouble(total_cancha_todo.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "El monto a pagar no puede ser mayor al costo de la cancha", Toast.LENGTH_SHORT).show();
                                }else if (Double.parseDouble(pago1_todo.getText().toString()) < Double.parseDouble(total_cancha_todo.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "El monto a pagar no puede ser menor al costo de la cancha", Toast.LENGTH_SHORT).show();
                                }else{
                                    registrarReservaUsuario("0","0","1");
                                }

                            }
                        }
                    }


                }
            }else{
                Toast.makeText(getApplicationContext(), "Debe Elegir el con el que desea reservar", Toast.LENGTH_SHORT).show();
            }

        }
    }



    public class GetSaldo extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            saldo =  new ArrayList<>();
            saldo =  dc4.getSaldo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (saldo.size()>0){
                saldo_cargado=saldo.get(0);
            }else{
                saldo_cargado="vacio";
            }

            saldo_bufis_busqueda.setText(saldo_cargado);



        }
    }
    public class GetEquipos extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayEquipo_busqueda =  new ArrayList<String>();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(ListEquipos_busqueda.size()>0){
                for (Mequipos obj : ListEquipos_busqueda) {
                    arrayEquipo_busqueda.add(obj.getEquipo_nombre());
                }
            }else{
                arrayEquipo_busqueda.add(0,"No tienes Equipos ");
            }

            arrayEquipo_busqueda.add(0,"Seleccionar Equipo ");


            ArrayAdapter<String > adapchanchas= new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayEquipo_busqueda);
            adapchanchas.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_equipex_busqueda.setAdapter(adapchanchas);



        }
    }

    JSONObject jsonObject,jsonNode,jsonNode2,jsonNodecito;
    JSONArray resultJSON,jsonArray;
    Colaboraciones colaboraciones;
    StringRequest stringRequest;
    DetalleColaboraciones detalleColaboraciones;


    public List<Colaboraciones> listaItem = new ArrayList<>();
    private void Colaboraciones_busqueda() {
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
                    rcv_colaboraciones_busqueda.setLayoutManager(linearLayoutManager);
                    AdapterColaboracionesItem itemAdapter = new AdapterColaboracionesItem(getApplicationContext(), listaItem, new AdapterColaboracionesItem.OnItemClickListener() {
                        @Override
                        public void onItemClick(Colaboraciones mequipos, String tipo, int position) {
                            if (tipo.equals("btn_reservar_en_chancha")){
                                Log.e("precios", "onItemClick: " + total_busqueda +" - " + mequipos.getMonto_final() );
                                if (Double.parseDouble(mequipos.getMonto_final())> Double.parseDouble(pago_total_chancha.getText().toString())){

                                    Toast.makeText(getApplicationContext(), "El monto de la chancha supera el precio de la  cancha", Toast.LENGTH_SHORT).show();
                                }else if (Double.parseDouble(mequipos.getMonto_final())< Double.parseDouble(pago_total_chancha.getText().toString())){

                                    Toast.makeText(getApplicationContext(), "El monto de la chancha es menor al precio de la  cancha", Toast.LENGTH_SHORT).show();
                                }else{
                                    if (nombre_reserva_busqueda.getText().toString().isEmpty()){

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
                    rcv_colaboraciones_busqueda.setAdapter(itemAdapter);


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

    String pago_tipo_busqueda,colaboracion_busqueda,equipo_id_busqueda,pago1_busqueda;
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
                String comision ="";
                int  posicion_equipo;

                if (spn_tipo_pago_busqueda.getSelectedItem().toString().equals("Yo pago todo")){


                    posicion_equipo= spn_equipex_busqueda.getSelectedItemPosition()-1;
                    pago_tipo_busqueda="1";
                    pago1_busqueda=pago1_todo.getText().toString();
                    colaboracion_busqueda="0";
                    comision = comision_todo.getText().toString();

                    equipo_id_busqueda = ListEquipos_busqueda.get(posicion_equipo).getEquipo_id();

                }else {
                    pago_tipo_busqueda="2";
                    colaboracion_busqueda=id_chancha;

                    equipo_id_busqueda = id_equipo;
                    pago1_busqueda=pago1_chancha.getText().toString();
                    comision = comision_chancha.getText().toString();
                }



                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_cancha",arraycancha.get(spn_cancha_busqueda.getSelectedItemPosition()).getCancha_id());
                parametros.put("pago_id_user",preferences.getIdUsuarioPref());
                parametros.put("pago_equipo_id",equipo_id_busqueda);
                parametros.put("nombre",nombre_reserva_busqueda.getText().toString());
                parametros.put("hora",h_reserva);
                parametros.put("pago1",pago1_busqueda);
                parametros.put("pago_tipo",pago_tipo_busqueda);
                parametros.put("id_colaboracion",colaboracion_busqueda);
                parametros.put("pago_comision",comision);
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


    public  ArrayList<Cancha> arraycancha;;
    public  class GetListadoCanchas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayCanchaBusqueda = new ArrayList<String>();
            arraycancha = dc2.getListadoCanchas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(arraycancha.size()>0){
                for (Cancha obj : arraycancha) {
                    arrayCanchaBusqueda.add(obj.getCancha_nombre());
                }
            }else{
                arrayCanchaBusqueda.add(0,"No tienes Equipos ");
            }




            ArrayAdapter<String > adapchanchas= new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayCanchaBusqueda);
            adapchanchas.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_cancha_busqueda.setAdapter(adapchanchas);



            spn_cancha_busqueda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (Integer.parseInt(hora )< 18){
                        total_cancha_todo.setText(arraycancha.get(position).getCancha_precioD());
                        pago1_chancha.setText(arraycancha.get(position).getCancha_precioD());
                        //precioDeLaCancha_busqueda.setText(arraycancha.get(position).getCancha_precioD());
                    }else{
                        total_cancha_todo.setText(arraycancha.get(position).getCancha_precioN());
                        pago1_chancha.setText(arraycancha.get(position).getCancha_precioN());
                        //precioDeLaCancha_busqueda.setText(arraycancha.get(position).getCancha_precioN());
                    }
                    comision_todo.setText(String.valueOf(comision_todo_dato));
                    comision_chancha.setText(String.valueOf(comision_todo_dato));

                    double pagoFinalChancha = 0;

                    pagoFinalChancha = Double.parseDouble(pago1_chancha.getText().toString()) + comision_todo_dato;
                    pago_total_chancha.setText(String.valueOf(pagoFinalChancha));

                    pago1_todo.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            String v = String.valueOf(s);

                            if (v.isEmpty()){

                            }else{
                                monto_todo = Double.parseDouble(v) + comision_todo_dato;
                                pago_total_todo.setText(String.valueOf(monto_todo));
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //txt_costo_cancha_busqueda.setText(arraycancha.get());

            //progressbarcanchas.setVisibility(ProgressBar.INVISIBLE);


        }
    }
}
