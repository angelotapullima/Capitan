package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models.RegistroEquiposTorneo;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository.RegistroEquiposTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository.RegistroEquiposTorneoWebServiceRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.ViewModels.RegistroEquiposTorneoViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RegistrarPartidos extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rcv_equipos_partidos;
    RegistroEquiposTorneoViewModel registroEquiposTorneoViewModel;
    AdapterRegistroPartidos adapterRegistroPartidos;
    String id_torneo,id_local,id_visita,id_instancia;
    TextView partido_hora,partido_fecha,local,visitante,btn_aceptar,btn_limpiar,btn_cancelar;
    Preferences preferences;

    RegistroEquiposTorneoRoomDBRepository registroEquiposTorneoRoomDBRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_partidos);

        registroEquiposTorneoViewModel = ViewModelProviders.of(this).get(RegistroEquiposTorneoViewModel.class);

        preferences = new Preferences(this);
        id_torneo=getIntent().getExtras().getString("id_torneo");
        id_instancia=getIntent().getExtras().getString("id_instancia");


        cargarDatos(id_torneo);
        registroEquiposTorneoRoomDBRepository= new RegistroEquiposTorneoRoomDBRepository(application);
        initViews();
        setAdapter();
        cargarvista();
    }

    private void initViews( ) {
        rcv_equipos_partidos = findViewById(R.id.rcv_equipos_partidos);
        partido_hora = findViewById(R.id.partido_hora);
        partido_fecha = findViewById(R.id.partido_fecha);
        local = findViewById(R.id.local);
        visitante = findViewById(R.id.visitante);
        btn_aceptar = findViewById(R.id.btn_aceptar);
        btn_limpiar = findViewById(R.id.btn_limpiar);
        btn_cancelar = findViewById(R.id.btn_cancelar);

        btn_aceptar.setOnClickListener(this);
        btn_limpiar.setOnClickListener(this);
        btn_cancelar.setOnClickListener(this);
        partido_fecha.setOnClickListener(this);
        partido_hora.setOnClickListener(this);
    }
    private void setAdapter() {


        adapterRegistroPartidos =  new AdapterRegistroPartidos(this, new AdapterRegistroPartidos.OnItemClickListener() {
            @Override
            public void onItemClick(String tipo, RegistroEquiposTorneo mequipos, int position) {

                /*if (tipo.equals("seleccionado")){
                    registroEquiposTorneoRoomDBRepository.actualizarEstado0(mequipos.getEquipo_id());
                    registroEquiposTorneoRoomDBRepository.actualizarLocal0(mequipos.getEquipo_id());

                }*/
                    if (tipo.equals("sin_seleccionar")){


                    if (local.getText().equals("")){

                        registroEquiposTorneoRoomDBRepository.actualizarLocal1(mequipos.getEquipo_id());
                        registroEquiposTorneoRoomDBRepository.actualizarEstado1(mequipos.getEquipo_id());

                    }else if(visitante.getText().equals("")){
                        registroEquiposTorneoRoomDBRepository.actualizarVisitante1(mequipos.getEquipo_id());
                        registroEquiposTorneoRoomDBRepository.actualizarEstado1(mequipos.getEquipo_id());

                    }else{

                        Toast.makeText(getApplicationContext(), "Los campos estan llenos", Toast.LENGTH_SHORT).show();
                        registroEquiposTorneoRoomDBRepository.actualizarEstado0(mequipos.getEquipo_id());
                    }

                }
            }
        });

        rcv_equipos_partidos.setAdapter(adapterRegistroPartidos);
        rcv_equipos_partidos.setLayoutManager(new LinearLayoutManager(this));




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegistroEquiposTorneoRoomDBRepository registroEquiposTorneoRoomDBRepository =  new RegistroEquiposTorneoRoomDBRepository(application);
        registroEquiposTorneoRoomDBRepository.deleteAllEquipos();
    }


    private void cargarvista() {



        registroEquiposTorneoViewModel.getAllEquiposTorneo().observe(this, new Observer<List<RegistroEquiposTorneo>>() {
            @Override
            public void onChanged(List<RegistroEquiposTorneo> registroEquiposTorneos) {
                adapterRegistroPartidos.setWords(registroEquiposTorneos);



                if(registroEquiposTorneos.size()>0){

                    local.setText("");
                    visitante.setText("");
                    id_visita = "";
                    id_local = "";


                    for (int i = 0; i<registroEquiposTorneos.size();i++){

                        Log.e("cambios", "local: "+registroEquiposTorneos.get(i).getLocal() + " -  visitante" + registroEquiposTorneos.get(i).getVisitante() +"- estado " +registroEquiposTorneos.get(i).getEstado_equipo() +"nombre" +registroEquiposTorneos.get(i).getEquipo_nombre() );

                        if(registroEquiposTorneos.get(i).getLocal().equals("1")){
                            local.setText(registroEquiposTorneos.get(i).getEquipo_nombre());
                            id_local=registroEquiposTorneos.get(i).getEquipo_id();
                        }



                        if(registroEquiposTorneos.get(i).getVisitante().equals("1")){
                            visitante.setText(registroEquiposTorneos.get(i).getEquipo_nombre());
                            id_visita=registroEquiposTorneos.get(i).getEquipo_id();
                        }


                    }


                }


            }
        });


    }
    Application application;
    public void cargarDatos(String id){
        RegistroEquiposTorneoWebServiceRepository registroEquiposTorneoWebServiceRepository = new RegistroEquiposTorneoWebServiceRepository(application);
        registroEquiposTorneoWebServiceRepository.providesWebService(id,preferences.getToken());
    }


    @Override
    public void onClick(View view) {
        if (view.equals(btn_aceptar)){

            if (id_local.isEmpty()){
                Toast.makeText(getApplicationContext(), "el campo de Equipo Local esta vacio", Toast.LENGTH_SHORT).show();
            }else if(id_visita.isEmpty()){
                Toast.makeText(getApplicationContext(), "el campo de Equipo Visitante esta vacio", Toast.LENGTH_SHORT).show();
            }else if(partido_fecha.getText().toString().equals("Fecha")){
                Toast.makeText(getApplicationContext(), "debe seleccionar fecha de partido", Toast.LENGTH_SHORT).show();
            }else  if (partido_hora.getText().toString().equals("hora")){
                Toast.makeText(getApplicationContext(), "debe seleccionar hora de partido", Toast.LENGTH_SHORT).show();
            }else{
                registrar_partidos();
            }


        }if (view.equals(btn_cancelar)){

            registroEquiposTorneoRoomDBRepository.Estado0();
            registroEquiposTorneoRoomDBRepository.local0();
            registroEquiposTorneoRoomDBRepository.visitante0();
            onBackPressed();

        }if (view.equals(btn_limpiar)){
            registroEquiposTorneoRoomDBRepository.Estado0();
            registroEquiposTorneoRoomDBRepository.local0();
            registroEquiposTorneoRoomDBRepository.visitante0();

        }
        if (view.equals(partido_fecha)){

            showDatePickerDialog(partido_fecha);

        }
        if (view.equals(partido_hora)){
            obtenerHora(partido_hora);

        }
    }



    int valor;
    String url = IP2+"/api/Torneo/registrar_partido";
    StringRequest stringRequest;
    private void registrar_partidos() {
        dialogoCargando();


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Torneo","publicar sin foto"+response);


                try {
                    JSONObject jsonObject;;

                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");
                    JSONObject jsonNode = resultJSON.getJSONObject(0);
                    valor = jsonNode.optInt("valor");
                    if (valor==1){
                        Toast.makeText(getApplicationContext(), "registro generado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    dialog_carga.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog_carga.dismiss();
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();



            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();

                parametros.put("id_torneo_instancia", id_instancia);
                parametros.put("id_equipo_local", id_local);
                parametros.put("id_equipo_visita", id_visita);
                parametros.put("fecha", partido_fecha.getText().toString());
                parametros.put("hora", partido_hora.getText().toString());
                parametros.put("app", "true");
                parametros.put("token", preferences.getToken());

                Log.e("torneo", "getParams: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }



    private  final String CERO = "0";
    private  final String DOS_PUNTOS = ":";
    private void obtenerHora(final TextView Hora){
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                Hora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);

        recogerHora.show();

    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private void showDatePickerDialog(final TextView editText) {
        DateDialog.DatePickerFragment newFragment = DateDialog.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate =  year+ "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }


    Dialog dialog_carga;
    public void dialogoCargando(){

        dialog_carga= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_carga.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_carga.setCancelable(true);
        dialog_carga.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_carga.findViewById(R.id.back);
        LinearLayout layout = dialog_carga.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_carga.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
        });

        dialog_carga.show();

    }

}
