package com.tec.bufeo.capitan.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.RegistroReserva.RegistroReserva;
import com.tec.bufeo.capitan.Adapters.AdapListadoCanchaReserva;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentMañana extends Fragment implements  SwipeRefreshLayout.OnRefreshListener {
    AdapListadoCanchaReserva adapListadoCanchaReserva;
    public ArrayList<Reserva> arrayreservados;
    public  ArrayList<Reserva> arrayreservados_WS;
    DataConnection dc;
    RecyclerView rcv_reservados;
    ProgressBar progressBar;
    CardView cdv_mensaje;
    Activity activity;
    Context context;
    String fecha;
    public FragmentManager fragmentManager;
    public  String fecha_actual_mas_uno_insert;
    public Date date;
    public SwipeRefreshLayout swipeRefreshLayout;
    Preferences preferences;



    String separador,part1,part2,separador_part1,part1_res,h_apertura,h_cierre,string_apertura,h_reserva,horaFinal= "";
    String[] resultado,resultado_part1 ;
    int hapertura = 0, hcierre;


    String cancha_id,cancha_nombre,precio_dia,precio_noche,hora_actual,fecha_actual,horario,nombre_empresa,tipo_usuario,saldo;

    public FragmentMañana() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manana, container, false);
        fragmentManager=getFragmentManager();
        preferences= new Preferences(getContext());
        swipeRefreshLayout =  view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        final Bundle bdl = getArguments();


        cancha_id = bdl.getString("cancha_id");
        cancha_nombre = bdl.getString("cancha_nombre");
        precio_dia = bdl.getString("precio_dia");
        precio_noche = bdl.getString("precio_noche");
        hora_actual = bdl.getString("hora_actual");
        fecha_actual = bdl.getString("fecha_actual");
        horario = bdl.getString("horario");
        nombre_empresa = bdl.getString("nombre_empresa");
        tipo_usuario = bdl.getString("tipo_usuario");
        saldo = bdl.getString("saldo");



        String dtStart = fecha_actual;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //fecha actual mas 1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        fecha_actual_mas_uno_insert = format1.format(calendar.getTime());







        separador = Pattern.quote("-");
        resultado = horario.split(separador);
        part1 = resultado[0];
        part2 = resultado[1];

        separador_part1 = Pattern.quote(":");
        resultado_part1 = part1.split(separador_part1);
        part1_res = resultado_part1[0];


        if (part1_res.length()<2){
            h_apertura = part1_res.substring(0, 1).toString().trim();
        }else{
            h_apertura = part1_res.substring(0, 2).toString().trim();
        }

        h_cierre = part2.substring(0, 3).toString().trim();




        if(h_apertura.length()<2){
            string_apertura = "0"+h_apertura;
        }else{
            string_apertura = h_apertura;
        }
        hapertura = Integer.parseInt(string_apertura);
        hcierre = Integer.parseInt(h_cierre);

        //Toast.makeText(getContext(), "parte 1 : " + hapertura + "  parte 2  :" + hcierre, Toast.LENGTH_LONG).show();
        //Toast.makeText(getContext(),"parte 1 : " +h_apertura.toString()+ "  parte 2  :"+h_cierre.toString(),Toast.LENGTH_LONG).show();
        rcv_reservados = (RecyclerView) view.findViewById(R.id.rcv_reservas);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        cdv_mensaje = (CardView) view.findViewById(R.id.cdv_mensaje);
        fecha = fecha_actual_mas_uno_insert.toString();

        progressBar.setVisibility(View.INVISIBLE);

        cdv_mensaje.setVisibility(View.INVISIBLE);

        activity = getActivity();
        context = getContext();


        dc = new DataConnection(getActivity(), "listarcanchasReservas", new Reserva(cancha_id), fecha_actual_mas_uno_insert, false);
        new FragmentMañana.GetReservados().execute();
        //getActivity().setTitle("Mi Carrito");

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
        Log.e("OnRefresh", "la csmare se refresca al iniciar " );
    }


    @Override
    public void onRefresh() {
        dc = new DataConnection(getActivity(), "listarcanchasReservas", new Reserva(cancha_id), fecha_actual_mas_uno_insert, false);
        new FragmentMañana.GetReservados().execute();
    }

    public  class GetReservados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayreservados_WS = dc.getListadoCanchasReserva();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Toast.makeText(getContext(), "size "+arrayreservados_WS.get(0).getReserva_costo(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            swipeRefreshLayout.setRefreshing(false);

            fecha_actual_mas_uno_insert.toString();



            separador = Pattern.quote("-");
            resultado = horario.split(separador);
            part1 = resultado[0];
            part2 = resultado[1];

            separador_part1 = Pattern.quote(":");
            resultado_part1 = part1.split(separador_part1);
            part1_res = resultado_part1[0];


            if (part1_res.length()<2){
                h_apertura = part1_res.substring(0, 1).toString().trim();
            }else{
                h_apertura = part1_res.substring(0, 2).toString().trim();
            }

            h_cierre = part2.substring(0, 3).toString().trim();



            if(h_apertura.length()<2){
                string_apertura = "0"+h_apertura;
            }else{
                string_apertura = h_apertura;
            }
            hapertura = Integer.parseInt(string_apertura);
            hcierre = Integer.parseInt(h_cierre);


            for(int j =0; j<arrayreservados_WS.size();j++) {
                arrayreservados_WS.get(j).getReserva_hora();
                arrayreservados_WS.get(j).getReserva_estado();
                // Toast.makeText(context," gg"+i,Toast.LENGTH_LONG);
            }
            //String estado =arrayreservados_WS.get(0).getReserva_estado();

            if(hcierre ==0){
                hcierre =24;
            }
            if(hcierre ==1){
                hcierre =25;
            }


            //-----------------------------------
            arrayreservados = new ArrayList<Reserva>();
            for (int i = hapertura; i<hcierre;i++){
                int hf =i+1;
                int ii=i;
                if(hf>12){
                    hf= hf-12;
                    if(ii>12){
                        ii=ii-12 ;
                    }
                    horaFinal =  ii+":00 - "+hf+":00 pm";
                }
                else{
                    horaFinal =  i+":00 - "+hf+":00 am";
                }
                int k = i+1;
                 h_reserva =""+i+":00-"+k+":00";
                if(i>=18){


                    boolean reservado_pm = false;
                    for(Reserva obj:arrayreservados_WS){
                        String hora =obj.getReserva_hora();

                        String separa = Pattern.quote(":");
                        String [] resul= hora.split(separa);
                        String neo_hora = resul[0];


                        if (neo_hora.length()!=2){
                            if (i == Integer.parseInt(neo_hora.substring(0, 1))) {
                                reservado_pm = true;

                                double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                if (obj.getReserva_estado().equals("1")){

                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal, String.valueOf(pago), "rojo" , "Reservado", precio_noche, h_reserva));

                                }else{
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal, String.valueOf(pago), "anaranjado" , "Reservado", precio_noche, h_reserva));

                                }

                                //arrayreservados.add(new Reserva("", cancha_id, obj.getReserva_nombre(), fecha_actual, horaFinal, obj.getReserva_costo(), Double.parseDouble(precio_noche) == Double.parseDouble(obj.getReserva_costo()) ? "rojo" : "anaranjado", "Reservado", precio_noche, h_reserva));
                            }
                        }else{
                            if (i == Integer.parseInt(neo_hora.substring(0, 2))) {
                                reservado_pm = true;

                                double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                if (obj.getReserva_estado().equals("1")){
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal, String.valueOf(pago),"rojo", "Reservado", precio_noche, h_reserva));

                                }else {
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal, String.valueOf(pago), "anaranjado", "Reservado", precio_noche, h_reserva));

                                }
                            }
                        }




                    }

                    if(!reservado_pm){
                        arrayreservados.add(new Reserva("",cancha_id,"-------",fecha_actual_mas_uno_insert,horaFinal,"0","verde","Disponible",precio_noche, h_reserva));

                    }








                }
                else {
                    // arrayreservados.add(new Reserva("", cancha_id, "Miguel", fecha_actual_mas_uno_insert, horaFinal, precio_dia,"1"));

                    boolean reservado_am = false;
                    for(Reserva obj:arrayreservados_WS){
                        String hora =obj.getReserva_hora();

                        String separa = Pattern.quote(":");
                        String [] resul= hora.split(separa);
                        String neo_hora = resul[0];

                        if (neo_hora.length()!=2){


                            if (i == Integer.parseInt(neo_hora.substring(0, 1))) {
                                reservado_am = true;

                                Toast.makeText(context,"c1",Toast.LENGTH_LONG);
                                double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                if (obj.getReserva_estado().equals("1")){
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal,  String.valueOf(pago), "rojo" , "Reservado", precio_dia, h_reserva));

                                }else{
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal,  String.valueOf(pago),"anaranjado", "Reservado", precio_dia, h_reserva));

                                }

                            }
                        }else{
                            if (i == Integer.parseInt(neo_hora.substring(0, 2))) {
                                Toast.makeText(context,"c2",Toast.LENGTH_LONG);
                                reservado_am = true;

                                double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                if (obj.getReserva_estado().equals("1")){
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal, String.valueOf(pago), "rojo" , "Reservado", precio_dia, h_reserva));

                                }else{
                                    arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fecha_actual_mas_uno_insert, horaFinal, String.valueOf(pago),  "anaranjado", "Reservado", precio_dia, h_reserva));

                                }
                            }
                        }




                    }
                    if(!reservado_am) {
                        arrayreservados.add(new Reserva("", cancha_id, "-------", fecha_actual_mas_uno_insert, horaFinal, "0", "verde", "Disponible", precio_dia, h_reserva));
                    }

                }

            }

            adapListadoCanchaReserva = new AdapListadoCanchaReserva(context, arrayreservados, R.layout.rcv_item_card_canchas_horarios, new AdapListadoCanchaReserva.OnItemClickListener() {
                @Override
                public void onItemClick(Reserva reserva, String tipo, int position) {
                    if (tipo.equals("imb_reserva_llamada")){


                        if (reserva.getReserva_color().equals("rojo")){

                        }else if (reserva.getReserva_color().equals("verde")){
                            if (tipo_usuario.equals("admin")){
                                dialogoVerdeAdmin(reserva.getReserva_hora_cancha(),reserva.getReserva_precioCancha());
                            }else{
                                Intent i = new Intent(context, RegistroReserva.class);
                                i.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                                i.putExtra("precio_cancha",reserva.getReserva_precioCancha());
                                i.putExtra("fecha",fecha_actual_mas_uno_insert);
                                i.putExtra("nombre_empresa",nombre_empresa);
                                i.putExtra("cancha_nombre",cancha_nombre);
                                i.putExtra("saldo",saldo);
                                i.putExtra("cancha_id",cancha_id);
                                context.startActivity(i);
                            }

                        }else if (reserva.getReserva_color().equals("anaranjado")){

                            if (tipo_usuario.equals("admin")){
                                dialogoNaranjaAdmin(reserva.getReserva_hora_cancha(),reserva.getReserva_precioCancha(),reserva.getReserva_costo(),reserva.getReserva_nombre(),reserva.getReserva_id());

                            }else{
                                Intent i = new Intent(context, RegistroReserva.class);
                                i.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                                i.putExtra("precio_cancha",reserva.getReserva_precioCancha());
                                i.putExtra("fecha",fecha_actual_mas_uno_insert);
                                i.putExtra("nombre_empresa",nombre_empresa);
                                i.putExtra("cancha_nombre",cancha_nombre);
                                i.putExtra("saldo",saldo);
                                i.putExtra("cancha_id",cancha_id);
                                context.startActivity(i);
                            }
                        }



                    }

                }
            });

            GridLayoutManager linearLayoutManager2 = new GridLayoutManager(activity, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_reservados.setLayoutManager(linearLayoutManager2);
            rcv_reservados.setAdapter(adapListadoCanchaReserva);

        }
    }






    android.app.AlertDialog dialog_verde;
    android.app.AlertDialog dialog_naranja;

    EditText nombre_reserva_naranja;
    EditText monto_pagado;
    double monto;
    public  void dialogoVerdeAdmin(final String hora_reserva, String precio_cancha){


        android.app.AlertDialog.Builder builder =  new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialog_reserva_verde,null);
        builder.setView(vista);




        final TextView nombre_empresa_dialog = vista.findViewById(R.id.nombre_empresa_dialog);
        TextView fecha_dialog = vista.findViewById(R.id.fecha_dialog);
        TextView nombre_cancha_dialog = vista.findViewById(R.id.nombre_cancha_dialog);
        TextView hora_reserva_dialog = vista.findViewById(R.id.hora_reserva_dialog);
        nombre_reserva_naranja = vista.findViewById(R.id.nombre_reserva_naranja);
        final TextView precio_cancha_dialog = vista.findViewById(R.id.precio_cancha_dialog);
        LinearLayout btn_reservar = vista.findViewById(R.id.btn_reservar);
        LinearLayout btn_cerrar_dialog = vista.findViewById(R.id.btn_cerrar_dialog);
        monto_pagado = vista.findViewById(R.id.monto_pagado);


        nombre_empresa_dialog.setText(nombre_empresa);
        fecha_dialog.setText(fecha_actual);
        nombre_cancha_dialog.setText(cancha_nombre);
        hora_reserva_dialog.setText(hora_reserva);
        precio_cancha_dialog.setText(precio_cancha);


        btn_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monto = Double.parseDouble(monto_pagado.getText().toString());
                String estado_pago;
                if ( monto >  Double.parseDouble(precio_cancha_dialog.getText().toString())){
                    Toast.makeText(context, "el monto supero el precio de la cancha", Toast.LENGTH_SHORT).show();
                }else{
                    if (monto == Double.parseDouble(precio_cancha_dialog.getText().toString())){
                        estado_pago ="1";
                    }else{
                        estado_pago ="2";
                    }
                    registrarReservaVerdeAdmin(nombre_reserva_naranja.getText().toString(),hora_reserva,monto,estado_pago);
                }

            }
        });

        btn_cerrar_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_verde.dismiss();
            }
        });
        dialog_verde = builder.create();
        dialog_verde.show();


    }

    public  void dialogoNaranjaAdmin(final String hora_reserva, String precio_cancha,String pago_abonado,String reserva_nombre,final String id) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialog_reserva_naranja, null);
        builder.setView(vista);


        final double monto_restante = Double.parseDouble(precio_cancha) - Double.parseDouble(pago_abonado);
        String dato_pagoss = "S/."+pago_abonado+" (Falta pagar S/."+monto_restante+" )";

        final double monto;

        final TextView nombre_empresa_dialog = vista.findViewById(R.id.nombre_empresa_dialog);
        TextView fecha_dialog = vista.findViewById(R.id.fecha_dialog);
        TextView nombre_cancha_dialog = vista.findViewById(R.id.nombre_cancha_dialog);
        TextView hora_reserva_dialog = vista.findViewById(R.id.hora_reserva_dialog);
        TextView nombre_reserva_naranja = vista.findViewById(R.id.nombre_reserva_naranja);
        TextView datosdepago = vista.findViewById(R.id.datosdepago);
        final TextView precio_cancha_dialog = vista.findViewById(R.id.precio_cancha_dialog);
        LinearLayout btn_reservar = vista.findViewById(R.id.btn_reservar);
        LinearLayout btn_cerrar_dialog = vista.findViewById(R.id.btn_cerrar_dialog);


        nombre_empresa_dialog.setText(nombre_empresa);
        fecha_dialog.setText(fecha_actual);
        nombre_cancha_dialog.setText(cancha_nombre);
        hora_reserva_dialog.setText(hora_reserva);
        precio_cancha_dialog.setText(precio_cancha);
        nombre_reserva_naranja.setText(reserva_nombre);
        datosdepago.setText(dato_pagoss);

        monto = Double.parseDouble(pago_abonado);
        btn_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( monto >  Double.parseDouble(precio_cancha_dialog.getText().toString())){
                    Toast.makeText(context, "el monto supero el precio de la cancha", Toast.LENGTH_SHORT).show();
                }else{

                    registrarReservaNaranjaAdmin(monto_restante,id);
                }

            }
        });

        btn_cerrar_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_naranja.dismiss();
            }
        });
        dialog_naranja = builder.create();
        dialog_naranja.show();


    }
    Dialog dialog_carga;
    public void dialogCarga(Activity activity){

        dialog_carga= new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_carga.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_carga.setCancelable(true);
        dialog_carga.setContentView(R.layout.dialog_carga_reserva);


        dialog_carga.show();

    }


    StringRequest stringRequest;
    private void registrarReservaVerdeAdmin(final String nombre, final String hora, final double monto, final String estado){


        dialog_verde.dismiss();
        dialogCarga(activity);

        String url =IP2+"/api/Empresa/registrar_reserva";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("registrar_reserva", "onResponse: "+response );

                if (response.equals("1")){
                    Toast.makeText(activity, "Registro Completo", Toast.LENGTH_SHORT).show();
                    onRefresh();
                    dialog_carga.dismiss();
                }else{
                    Toast.makeText(activity, "Fallo al registrar la reserva", Toast.LENGTH_SHORT).show();
                    dialog_carga.dismiss();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: "+error.toString() );


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tipopago;
                if (tipo_usuario.equals("admin")){
                    tipopago ="0";
                }else{
                    tipopago ="1";
                }
                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_cancha",cancha_id);
                parametros.put("nombre",nombre);
                parametros.put("hora",hora);
                parametros.put("pago1",String.valueOf(monto));
                parametros.put("tipopago",tipopago);
                parametros.put("estado",estado);
                parametros.put("fecha",fecha_actual);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.e("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);

    }

    private void registrarReservaNaranjaAdmin(final double pago2,final String id ){


        dialog_naranja.dismiss();
        dialogCarga(activity);

        String url =IP2+"/api/Empresa/pagar_restante";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("registrar_reserva", "onResponse: "+response );

                if (response.equals("1")){
                    Toast.makeText(activity, "Registro Completo", Toast.LENGTH_SHORT).show();
                    onRefresh();
                    dialog_carga.dismiss();
                }else{
                    Toast.makeText(activity, "Fallo al registrar la reserva", Toast.LENGTH_SHORT).show();
                    dialog_carga.dismiss();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: "+error.toString() );


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("pago2",String.valueOf(pago2));
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.e("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);

    }
}
