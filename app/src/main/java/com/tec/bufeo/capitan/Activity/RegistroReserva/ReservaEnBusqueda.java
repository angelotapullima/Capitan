package com.tec.bufeo.capitan.Activity.RegistroReserva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
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
import com.tec.bufeo.capitan.Activity.ConfirmacionReserva;
import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.Modelo.Saldo;
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
import java.util.regex.Pattern;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ReservaEnBusqueda extends AppCompatActivity implements View.OnClickListener {


    ImageView backReserva;
    //yo pago todo
    LinearLayout l_todo;
    Spinner spn_equipex_busqueda;
    String comision_todo_dato;
    ImageView noChanchas;
    TextView total,costo,comision;

    //chancha
    LinearLayout l_chancha;
    RecyclerView rcv_colaboraciones_busqueda;




    //ambos

    TextView nombre_empresa,hora_r,fecha_r;
    Spinner spn_tipo_pago_busqueda,spn_cancha_busqueda;
    MisEquiposViewModel misEquiposViewModel;
    DataConnection dc4,dc2;
    Preferences preferences;
    RelativeLayout relaitveCarga;
    String nombre_empresa_dato,empresa_id,h_reserva,fecha,hora,precio,telefono1,telefono2,direccion;
    LinearLayout btn_reservar_busqueda;
    TextView nombre_reserva_busqueda,saldo_bufis_busqueda;



    ArrayList<String> arrayEquipo_busqueda,arrayCanchaBusqueda;
    ArrayList<Mequipos> ListEquipos_busqueda = new ArrayList<>();

    double total_busqueda ;
    ArrayList<Saldo> saldo;
    String saldo_cargado;
    RelativeLayout relRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_en_busqueda);

        misEquiposViewModel= ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        nombre_empresa_dato = getIntent().getExtras().getString("nombre_empresa");
        empresa_id = getIntent().getExtras().getString("empresa_id");
        h_reserva = getIntent().getExtras().getString("h_reserva");
        precio = getIntent().getExtras().getString("precio");
        telefono1 = getIntent().getExtras().getString("telefono1");
        telefono2 = getIntent().getExtras().getString("telefono2");
        direccion = getIntent().getExtras().getString("direccion");

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

        comision_todo_dato =preferences.getComision();

        /*  Widgets que trabajan con YoPagoTodo*/
        //layout para ocultar contenido de yo pagoTodo
        l_todo = findViewById(R.id.l_todo);

        comision  = findViewById(R.id.comision);
        relRes  = findViewById(R.id.relRes);
        costo  = findViewById(R.id.costo);
        //pago1_todo = findViewById(R.id.pago1_todo);
        total= findViewById(R.id.total);
        noChanchas= findViewById(R.id.noChanchas);
        saldo_bufis_busqueda  = findViewById(R.id.saldo_bufis_busqueda);


        costo.setText(precio);

        total.setText(String.valueOf(comision_todo_dato + Double.parseDouble(precio)));
        /*  Widgets que trabajan con Chancha*/
        //layout para ocultar contenido de Chancha
        l_chancha = findViewById(R.id.l_chancha);


        /*  Datos Generales*/
        nombre_empresa = findViewById(R.id.nombre_empresa);
        hora_r = findViewById(R.id.hora_r);
        fecha_r = findViewById(R.id.fecha_r);
        spn_tipo_pago_busqueda = findViewById(R.id.spn_tipo_pago_busqueda);
        spn_equipex_busqueda = findViewById(R.id.spn_equipex_busqueda);
        spn_cancha_busqueda = findViewById(R.id.spn_cancha_busqueda);
        nombre_reserva_busqueda  = findViewById(R.id.nombre_reserva_busqueda);
        relaitveCarga  = findViewById(R.id.relaitveCarga);
        backReserva  = findViewById(R.id.backReserva);






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
                    relRes.setVisibility(View.GONE);
                }else{
                    relRes.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_reservar_busqueda.setOnClickListener(this);
        backReserva.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        if (v.equals(btn_reservar_busqueda)){



                if (spn_tipo_pago_busqueda.getSelectedItem().toString().equals("Seleccionar")){


                    preferences.codeAdvertencia("Debe seleccionar un tipo de Pago");
                }else{
                    if (spn_tipo_pago_busqueda.getSelectedItem().toString().equals("Yo pago todo")){

                        if (nombre_reserva_busqueda.getText().toString().isEmpty()){

                            preferences.codeAdvertencia("el campo nombre no debe estar vacio");
                        }else{
                            if (spn_equipex_busqueda.getSelectedItem().toString().equals("Seleccionar Equipo")){

                                preferences.codeAdvertencia("Debe seleccionar un Equipo");
                            }else{


                                if (Double.parseDouble(saldo_bufis_busqueda.getText().toString())>= Double.parseDouble(total.getText().toString())){
                                    registrarReservaUsuario("0","0","1");
                                }else{
                                    preferences.codeAdvertencia("No cuenta con las Bufis Suficientes para la operación");
                                }


                            }
                        }
                    }


                }


        }else if (v.equals(backReserva)){
            finish();
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
                saldo_cargado=saldo.get(0).getSaldo_actual();
                preferences.saveValuePORT("comision", saldo.get(0).getComision());
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

                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                    rcv_colaboraciones_busqueda.setLayoutManager(linearLayoutManager);
                    AdapterColaboracionesItem itemAdapter = new AdapterColaboracionesItem(getApplicationContext(), listaItem, new AdapterColaboracionesItem.OnItemClickListener() {
                        @Override
                        public void onItemClick(Colaboraciones mequipos, String tipo, int position) {
                            if (tipo.equals("btn_reservar_en_chancha")){
                                Log.d("precios", "onItemClick: " + total_busqueda +" - " + mequipos.getMonto_final() );
                                if (Double.parseDouble(mequipos.getMonto_final())> Double.parseDouble(total.getText().toString())){

                                    preferences.codeAdvertencia("El monto de la chancha supera el precio de la  cancha");}else if (Double.parseDouble(mequipos.getMonto_final())< Double.parseDouble(total.getText().toString())){

                                   }else{
                                    if (nombre_reserva_busqueda.getText().toString().isEmpty()){

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
                    rcv_colaboraciones_busqueda.setAdapter(itemAdapter);


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

    String pago_tipo_busqueda,colaboracion_busqueda,equipo_id_busqueda,pago1_busqueda;
    private void registrarReservaUsuario(final String id_chancha,final String id_equipo,final String estado_pago){

        dialogCarga();



        String url =IP2+"/api/Empresa/registrar_reserva";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("registrar_reserva", "onResponse: "+response );

                String separador,part1;
                String[] resultado;

                separador = Pattern.quote("}");
                resultado = response.split(separador);
                part1 = resultado[2];

                if (part1.equals("1")){
                    preferences.toasVerde("Registro Completo");
                    dialog_carga.dismiss();
                    Intent i = new Intent(ReservaEnBusqueda.this, ConfirmacionReserva.class);
                    i.putExtra("cancha",spn_cancha_busqueda.getSelectedItem().toString());
                    i.putExtra("lugar",nombre_empresa.getText().toString());
                    i.putExtra("hora",h_reserva);
                    i.putExtra("fecha",fecha);
                    i.putExtra("precio",total.getText().toString());
                    i.putExtra("nombre",nombre_reserva_busqueda.getText().toString());
                    i.putExtra("direccion",direccion);
                    i.putExtra("telefono",telefono1);
                    i.putExtra("telefono2",telefono2);
                    startActivity(i);
                    finish();


                }else if(response.equals("3")){

                    preferences.codeAdvertencia("Lo sentimos , la cancha seleccionado ya fue ocupada . Intente con otra cancha u otro horario");
                    dialog_carga.dismiss();

                }else if(response.equals("2")){

                    preferences.codeAdvertencia("Lo sentimos "+  preferences.getNickname()+" , Ocurrio un error . Intentelo más tarde");
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
                String comisionex ="";
                int  posicion_equipo;

                if (spn_tipo_pago_busqueda.getSelectedItem().toString().equals("Yo pago todo")){


                    posicion_equipo= spn_equipex_busqueda.getSelectedItemPosition()-1;
                    pago_tipo_busqueda="1";
                    pago1_busqueda=total.getText().toString();
                    colaboracion_busqueda="0";
                    comisionex = comision.getText().toString();

                    equipo_id_busqueda = ListEquipos_busqueda.get(posicion_equipo).getEquipo_id();

                }else {
                    pago_tipo_busqueda="2";
                    colaboracion_busqueda=id_chancha;

                    equipo_id_busqueda = id_equipo;
                    pago1_busqueda=costo.getText().toString();
                    comisionex = comision.getText().toString();
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
                parametros.put("pago_comision",comisionex);
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
                relaitveCarga.setVisibility(View.GONE);
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


                    String separador,part1,part2,separador_part1,hora_apertura,hora_cierre,separador_hora;
                    String[] resultado,resultado_part1,resultado_part2,resultado_hora;

                    separador = Pattern.quote("-");
                    resultado = h_reserva.split(separador);
                    part1 = resultado[0];
                    part2 = resultado[1];

                    separador_part1 = Pattern.quote(":");
                    resultado_part1 = part1.split(separador_part1);
                    resultado_part2 = part2.split(separador_part1);
                    hora_apertura = resultado_part1[0];
                    hora_cierre= resultado_part2[0];


                    if (Integer.parseInt(hora_apertura )< 18){
                        total.setText(arraycancha.get(position).getCancha_precioD());
                        costo.setText(arraycancha.get(position).getCancha_precioD());
                    }else{
                        total.setText(arraycancha.get(position).getCancha_precioN());
                        costo.setText(arraycancha.get(position).getCancha_precioN());
                    }
                    comision.setText(String.valueOf(comision_todo_dato));

                    double pagoFinalChancha = 0;


                    total.setText(String.valueOf(Double.parseDouble(costo.getText().toString()) + Double.parseDouble(comision_todo_dato)));

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
