package com.tec.bufeo.capitan.Activity.DetalleCanchas.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.Models.ReservasCancha;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.ViewModels.ReservasCanchaListViewModel;
import com.tec.bufeo.capitan.Activity.DetalleReservaEmpresa;
import com.tec.bufeo.capitan.Activity.RegistroReserva.RegistroReserva;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.Modelo.Saldo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleCanchas extends AppCompatActivity  {

    TextView saldo_contable;
    ImageView arrow_back;
    LinearLayout lbufis;
    RecyclerView rcv_reservas;
    Preferences preferences;
    AdapListadoCanchaReserva adapListadoCanchaReserva;
    ReservasCanchaListViewModel reservasCanchaListViewModel;

    public  String cancha_id, precio_dia,precio_noche,
            cancha_nombre,horario,nombre_empresa,tipo_usuario,fecha_actual,hora_actual;


    ArrayList<Reserva> arrayreservados = new ArrayList<>();
    ArrayList<Reserva> arrayreservados_WS = new ArrayList<>();
    String separador,part1,part2,separador_part1,part1_res,h_apertura,h_cierre,string_apertura,horaFinal= "",separador_hora,partHoraActual;
    String[] resultado,resultado_part1 ,resultado_horaActual;
    int hapertura = 0, hcierre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_canchas);

        reservasCanchaListViewModel = ViewModelProviders.of(this).get(ReservasCanchaListViewModel.class);
        preferences= new Preferences(this);

        cancha_id = getIntent().getStringExtra("id_cancha");
        nombre_empresa = getIntent().getStringExtra("nombre_empresa");
        cancha_nombre = getIntent().getStringExtra("nombre_cancha");
        precio_dia = getIntent().getStringExtra("precio_dia");
        precio_noche = getIntent().getStringExtra("precio_noche");
        horario = getIntent().getStringExtra("horario");
        tipo_usuario = getIntent().getStringExtra("tipo_usuario");
        fecha_actual = getIntent().getStringExtra("fecha_actual");
        hora_actual = getIntent().getStringExtra("hora_actual");



        initViews();
        //saldo_contable.setText(saldo);
        ConstruirCalendar();
        setAdapter();
        cargarvista(fecha_actual);
        obtenerSaldo();



    }

    DataConnection dc4;
    ArrayList<Saldo> saldo;
    String saldo_cargado="";
    public void obtenerSaldo(){
        dc4 = new DataConnection(this, "ObtenerSaldo", false);
        new DetalleCanchas.GetSaldo().execute();
    }

    public class GetSaldo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            saldo = new ArrayList<>();
            saldo = dc4.getSaldo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (saldo.size() > 0) {
                saldo_cargado =saldo.get(0).getSaldo_actual();
                saldo_contable.setText(saldo_cargado);
                preferences.saveValuePORT("comision", saldo.get(0).getComision());
                preferences.saveValuePORT("saldo", saldo.get(0).getSaldo_actual());
            } else {
                saldo_cargado = "vacio";
            }


        }
    }


    public void initViews(){
        saldo_contable= findViewById(R.id.saldo_contable);
        arrow_back= findViewById(R.id.arrow_back);
        lbufis= findViewById(R.id.lbufis);
        rcv_reservas= findViewById(R.id.rcv_reservas);
        preferences=new Preferences(this);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }



    public void cargarvista(final String fechex){

        reservasCanchaListViewModel.getReservasDia(fechex,cancha_id,preferences.getToken()).observe(this, new Observer<List<ReservasCancha>>() {
            @Override
            public void onChanged(List<ReservasCancha> reservasCanchas) {

                arrayreservados.clear();
                arrayreservados_WS.clear();



                separador = Pattern.quote("-");
                resultado = horario.split(separador);
                part1 = resultado[0];
                part2 = resultado[1];

                separador_part1 = Pattern.quote(":");
                resultado_part1 = part1.split(separador_part1);
                part1_res = resultado_part1[0];


                separador_hora = Pattern.quote(":");
                resultado_horaActual = hora_actual.split(separador_hora);
                partHoraActual = resultado_horaActual[0];
                partHoraActual = partHoraActual.trim();

                if(tipo_usuario.equals("admin")){
                    part1_res = resultado_part1[0];
                }else{
                    if (fecha_actual.equals(fechex)){
                        if (Integer.parseInt(partHoraActual) >= Integer.parseInt(part1_res)){

                            part1_res = partHoraActual;
                        }else{
                            part1_res = resultado_part1[0];
                        }
                    }else{

                        part1_res = resultado_part1[0];
                    }


                }



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

                for(int j =0; j<reservasCanchas.size();j++) {
                    Reserva reserva = new Reserva();
                    reserva.setReserva_id(reservasCanchas.get(j).getReserva_id());
                    reserva.setPago_id(reservasCanchas.get(j).getPago_id());
                    reserva.setTipopago(reservasCanchas.get(j).getTipopago());
                    reserva.setCancha_id(reservasCanchas.get(j).getCancha_id());
                    reserva.setCancha_nombre(reservasCanchas.get(j).getNombre());
                    reserva.setReserva_fecha(reservasCanchas.get(j).getFecha());
                    reserva.setReserva_hora(reservasCanchas.get(j).getHora());
                    reserva.setPago1(reservasCanchas.get(j).getPago1());
                    reserva.setPago1_date(reservasCanchas.get(j).getPago1_date());
                    reserva.setPago2(reservasCanchas.get(j).getPago2());
                    reserva.setPago2_date(reservasCanchas.get(j).getPago2_date());
                    reserva.setReserva_estado(reservasCanchas.get(j).getEstado());


                    arrayreservados_WS.add(reserva);
                    // Toast.makeText(context," gg"+i,Toast.LENGTH_LONG);
                }


                if(hcierre ==0){
                    hcierre =24;
                }
                if(hcierre ==1){
                    hcierre =25;
                }


                //-----------------------------------
                /*arrayreservados = new ArrayList<Reserva>();*/
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
                    String  h_reserva =""+i+":00-"+k+":00";
                    if(i>=18){
                        //if(){}
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

                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal, String.valueOf(pago), "rojo" , "Reservado", precio_noche, h_reserva));

                                    }else{
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal, String.valueOf(pago), "anaranjado" , "Reservado", precio_noche, h_reserva));

                                    }

                                    //arrayreservados.add(new Reserva("", cancha_id, obj.getReserva_nombre(), fecha_actual, horaFinal, obj.getReserva_costo(), Double.parseDouble(precio_noche) == Double.parseDouble(obj.getReserva_costo()) ? "rojo" : "anaranjado", "Reservado", precio_noche, h_reserva));
                                }
                            }else{
                                if (i == Integer.parseInt(neo_hora.substring(0, 2))) {
                                    reservado_pm = true;

                                    double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                    if (obj.getReserva_estado().equals("1")){
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal, String.valueOf(pago),"rojo", "Reservado", precio_noche, h_reserva));

                                    }else {
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal, String.valueOf(pago), "anaranjado", "Reservado", precio_noche, h_reserva));

                                    }
                                }
                            }


                        }

                        if(!reservado_pm){
                            arrayreservados.add(new Reserva("",cancha_id,"-------",fechex,horaFinal,"0","verde","Disponible",precio_noche, h_reserva));

                        }


                    }
                    else {

                        boolean reservado_am = false;
                        for(Reserva obj:arrayreservados_WS){
                            String hora =obj.getReserva_hora();

                            String separa = Pattern.quote(":");
                            String [] resul= hora.split(separa);
                            String neo_hora = resul[0];

                            if (neo_hora.length()!=2){


                                if (i == Integer.parseInt(neo_hora.substring(0, 1))) {
                                    reservado_am = true;

                                    /*Toast.makeText(context,"c1",Toast.LENGTH_LONG);*/
                                    double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                    if (obj.getReserva_estado().equals("1")){
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal,  String.valueOf(pago), "rojo" , "Reservado", precio_dia, h_reserva));

                                    }else{
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal,  String.valueOf(pago),"anaranjado", "Reservado", precio_dia, h_reserva));

                                    }

                                }
                            }else{
                                if (i == Integer.parseInt(neo_hora.substring(0, 2))) {
                                    /*     Toast.makeText(context,"c2",Toast.LENGTH_LONG);*/
                                    reservado_am = true;

                                    double pago = Double.parseDouble(obj.getPago1() ) + Double.parseDouble(obj.getPago2() );
                                    if (obj.getReserva_estado().equals("1")){
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal, String.valueOf(pago), "rojo" , "Reservado", precio_dia, h_reserva));

                                    }else{
                                        arrayreservados.add(new Reserva(obj.getReserva_id(), cancha_id, obj.getReserva_nombre(), fechex, horaFinal, String.valueOf(pago),  "anaranjado", "Reservado", precio_dia, h_reserva));

                                    }
                                }
                            }

                        }
                        if(!reservado_am) {
                            arrayreservados.add(new Reserva("", cancha_id, "-------", fechex, horaFinal, "0", "verde", "Disponible", precio_dia, h_reserva));
                        }
                    }

                }


                adapListadoCanchaReserva.setWords(arrayreservados);
            }
        });
    }
    public void setAdapter(){

        adapListadoCanchaReserva= new AdapListadoCanchaReserva(this, new AdapListadoCanchaReserva.OnItemClickListener() {
            @Override
            public void onItemClick(Reserva reserva, String tipo, int position) {
                if (tipo.equals("cdv_canchas_horario_reserva")){



                    if (reserva.getReserva_color().equals("rojo")){
                        if (tipo_usuario.equals("admin")){
                            Intent i = new Intent(getApplicationContext(), DetalleReservaEmpresa.class);
                            i.putExtra("id",reserva.getReserva_id());
                            startActivity(i);
                        }


                    }else if (reserva.getReserva_color().equals("verde")){
                        if (tipo_usuario.equals("admin")){
                            dialogoVerdeAdmin(reserva.getReserva_hora_cancha(),reserva.getReserva_precioCancha(),reserva.getReserva_fecha());
                        }else{
                            if (saldo_contable.getText().equals("") || saldo_contable.getText().toString().isEmpty()){
                                preferences.codeAdvertencia("Espere un momento, estamos cargando el Saldo Actual");
                            }else{
                                Intent i = new Intent(getApplicationContext(), RegistroReserva.class);
                                i.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                                i.putExtra("precio_cancha",reserva.getReserva_precioCancha());
                                i.putExtra("fecha",reserva.getReserva_fecha());
                                i.putExtra("nombre_empresa",nombre_empresa);
                                i.putExtra("cancha_nombre",cancha_nombre);
                                i.putExtra("saldo",saldo_cargado);
                                i.putExtra("cancha_id",cancha_id);
                                startActivity(i);
                            }

                        }

                    }else if (reserva.getReserva_color().equals("anaranjado")){

                        if (tipo_usuario.equals("admin")){
                            dialogoNaranjaAdmin(reserva.getReserva_hora_cancha(),reserva.getReserva_precioCancha(),reserva.getReserva_costo(),reserva.getReserva_nombre(),reserva.getReserva_id(),reserva.getReserva_fecha());

                        }else{
                            if (saldo_contable.getText().equals("") || saldo_contable.getText().toString().isEmpty()){
                                preferences.codeAdvertencia("Espere un momento, estamos cargando el Saldo Actual");
                            }else{
                                Intent i = new Intent(getApplicationContext(), RegistroReserva.class);
                                i.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                                i.putExtra("precio_cancha",reserva.getReserva_precioCancha());
                                i.putExtra("fecha",reserva.getReserva_fecha());
                                i.putExtra("nombre_empresa",nombre_empresa);
                                i.putExtra("cancha_nombre",cancha_nombre);
                                i.putExtra("saldo",saldo_cargado);
                                i.putExtra("cancha_id",cancha_id);
                                startActivity(i);
                            }

                        }
                    }


                }
            }
        });

        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(this, 1);
        rcv_reservas.setLayoutManager(linearLayoutManager2);
        rcv_reservas.setAdapter(adapListadoCanchaReserva);

    }
    public void ConstruirCalendar(){



        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if (tipo_usuario.equals("admin")){

            startDate.add(Calendar.MONTH, -1);
            endDate.add(Calendar.MONTH, 1);

        }else{
            startDate.add(Calendar.DATE, 0);
            endDate.add(Calendar.DATE, 7);
        }



        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                SimpleDateFormat fechex = new SimpleDateFormat("yyyy-MM-dd");
                String fechaActual = fechex.format(date.getTime());

                cargarvista(fechaActual);
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
                //Toast.makeText(PruebaDeCalendarios.this, dx + " - " + dy, Toast.LENGTH_SHORT).show();

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {



                return true;
            }
        });
    }




    android.app.AlertDialog dialog_verde;
    android.app.AlertDialog dialog_naranja;

    EditText nombre_reserva_naranja;
    EditText monto_pagado;
    double monto;

    public  void dialogoVerdeAdmin(final String hora_reserva, String precio_cancha, final String f_echa){


        android.app.AlertDialog.Builder builder =  new android.app.AlertDialog.Builder(this);
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
        fecha_dialog.setText(f_echa);
        nombre_cancha_dialog.setText(cancha_nombre);
        hora_reserva_dialog.setText(hora_reserva);
        precio_cancha_dialog.setText(precio_cancha);


        btn_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monto = Double.parseDouble(monto_pagado.getText().toString());
                String estado_pago;
                if ( monto >  Double.parseDouble(precio_cancha_dialog.getText().toString())){
                    preferences.codeAdvertencia("el monto supero el precio de la cancha");
                }else{
                    if (monto == Double.parseDouble(precio_cancha_dialog.getText().toString())){
                        estado_pago ="1";
                    }else{
                        estado_pago ="2";
                    }
                    registrarReservaVerdeAdmin(nombre_reserva_naranja.getText().toString(),hora_reserva,monto,estado_pago,f_echa);
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

    public  void dialogoNaranjaAdmin(final String hora_reserva, String precio_cancha,String pago_abonado,String reserva_nombre,final String id,String f_echa) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
        fecha_dialog.setText(f_echa);
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
                    preferences.codeAdvertencia("el monto supero el precio de la cancha");
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

    StringRequest stringRequest;
    private void registrarReservaVerdeAdmin(final String nombre, final String hora, final double monto, final String estado,final String FechaPaReservar){


        dialog_verde.dismiss();
        dialogCarga(this);

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
                    //onRefresh();
                    dialog_carga.dismiss();
                }else{
                    preferences.toasRojo("Fallo al registrar la reserva","intentelo más tarde");
                    dialog_carga.dismiss();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );


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
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("nombre",nombre);
                parametros.put("hora",hora);
                parametros.put("pago1",String.valueOf(monto));
                parametros.put("tipopago",tipopago);
                parametros.put("estado",estado);
                parametros.put("fecha",FechaPaReservar);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.d("parametros", "parametros: "+parametros.toString() );
                return parametros;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    Dialog dialog_carga;
    public void dialogCarga(Activity activity){

        dialog_carga= new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_carga.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_carga.setCancelable(true);
        dialog_carga.setContentView(R.layout.dialog_carga_reserva);


        dialog_carga.show();

    }
    private void registrarReservaNaranjaAdmin(final double pago2,final String id ){


        dialog_naranja.dismiss();
        dialogCarga(this);

        String url =IP2+"/api/Empresa/pagar_restante";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("registrar_reserva", "onResponse: "+response );

                if (response.equals("1")){
                    preferences.toasVerde("Registro Completo");
                    //onRefresh();
                    dialog_carga.dismiss();
                }else{
                    preferences.toasRojo("Fallo al registrar la reserva","intentelo más tarde");
                    dialog_carga.dismiss();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id",id);
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("pago2",String.valueOf(pago2));
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
}
