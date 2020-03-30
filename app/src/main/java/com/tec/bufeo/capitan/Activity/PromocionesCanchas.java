package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Canchas;
import com.tec.bufeo.capitan.Activity.Negocios.ViewModels.CanchasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class PromocionesCanchas extends AppCompatActivity implements View.OnClickListener {

    Spinner spn_canchas;
    TextView fechaInicio, horaInicio, fechaFinal, horaFinal;
    EditText preciopromo;
    MaterialButton confirmarPromo;
    String id_empresa;

    CanchasViewModel canchasViewModel;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones_canchas);
        canchasViewModel = ViewModelProviders.of(this).get(CanchasViewModel.class);
        preferences = new Preferences(this);

        id_empresa = getIntent().getExtras().getString("id_empresa");



        initViews();
        cargarVista();
        setAdapter();
        showToolbar("Promociones" ,true);
    }

    public void initViews(){
        spn_canchas=findViewById(R.id.spn_canchas);
        fechaInicio=findViewById(R.id.fechaInicio);
        horaInicio=findViewById(R.id.horaInicio);
        fechaFinal=findViewById(R.id.fechaFinal);
        horaFinal=findViewById(R.id.horaFinal);
        preciopromo=findViewById(R.id.preciopromo);
        confirmarPromo=findViewById(R.id.confirmarPromo);


        confirmarPromo.setOnClickListener(this);
        fechaInicio.setOnClickListener(this);
        horaInicio.setOnClickListener(this);
        fechaFinal.setOnClickListener(this);
        horaFinal.setOnClickListener(this);
    }

    public void setAdapter(){

    }

    ArrayList<Canchas> arrayCanchas = new ArrayList<>();
    ArrayList<String> arraycanchas = new ArrayList<>();


    public void cargarVista(){
        canchasViewModel.getCanchas(id_empresa,preferences.getToken()).observe(this, new Observer<List<Canchas>>() {
            @Override
            public void onChanged(List<Canchas> canchas) {
                if (canchas.size()>0){
                    arrayCanchas.clear();
                    arraycanchas.clear();
                    arrayCanchas.addAll(canchas);
                    //Log.e("mis Equipos", "onChanged: "+mequipos.size() );

                    for (Canchas obj :arrayCanchas){
                        arraycanchas.add(obj.getNombre_cancha());
                    }
                    //progressBar.setVisibility(ProgressBar.INVISIBLE);
                    arraycanchas.add(0,"Seleccionar");
                    ArrayAdapter<String> adapNegocios = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arraycanchas);
                    adapNegocios.setDropDownViewResource(R.layout.spiner_dropdown_item);
                    spn_canchas.setAdapter(adapNegocios);

                }

                //adaptadorListadoCanchaEmpresa
            }
        });
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

    @Override
    public void onClick(View v) {
        if (v.equals(fechaInicio)){
            showDateDailog(fechaInicio);

        }else if (v.equals(fechaFinal)){
            showDateDailog(fechaFinal);

        }else if (v.equals(horaInicio)){
            obtenerHora(horaInicio);

        }else if (v.equals(horaFinal)){
            obtenerHora(horaFinal);

        }else  if (v.equals(confirmarPromo)){

            if (spn_canchas.getSelectedItem().toString().equals("Seleccionar")){
                preferences.codeAdvertencia("Por favor, seleccione una cancha para la promoción");

            }else if (fechaInicio.getText().toString().equals("Fecha")){
                preferences.codeAdvertencia("Por favor, seleccione una fecha de inicio");

            }else if (horaInicio.getText().toString().equals("Hora")){
                preferences.codeAdvertencia("Por favor, seleccione una Hora de inicio");

            }else if (fechaFinal.getText().toString().equals("Fecha")){
                preferences.codeAdvertencia("Por favor, seleccione una fecha final");

            }else if (horaFinal.getText().toString().equals("Hora")){
                preferences.codeAdvertencia("Por favor, seleccione una Hora final");

            }else if (preciopromo.getText().toString().equals("")){
                preferences.codeAdvertencia("Por favor, ingrese el precio de la promoción");
            }else{
                registrarPromo();
            }
        }
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

    int year,month,day; String dia,mes;
    private void showDateDailog(final TextView textView) {

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDate) {

                year = selectedYear;
                month = selectedMonth;
                day = selectedDate;


                if (day<10){
                    dia = CERO + String.valueOf(day);
                }else{
                    dia = String.valueOf(day);
                }

                if (month<10){
                    month = month+1;
                    mes = CERO + String.valueOf(month);
                }else{
                    month = month+1;
                    mes = String.valueOf(month);
                }
                textView.setText(new StringBuilder().append(year).append("-").append(mes).append("-").append(dia));

            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
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

StringRequest stringRequest;
    private void registrarPromo(){

        dialogoCargando();

        String url =IP2+"/api/Empresa/registrar_promo";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("cancelar", "recarga: "+response );

                if(response.equals("1")){
                    preferences.toasVerde("Registro exitoso");
                    fechaInicio.setText("");
                    fechaFinal.setText("");
                    horaFinal.setText("");
                    horaInicio.setText("");
                    preciopromo.setText("");
                    dialog_carga.dismiss();


                }else{
                    preferences.toasRojo("Ocurrio un error","por favor intenta más tarde");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: "+error.toString() );
                dialog_carga.dismiss();
                preferences.codeAdvertencia("Tenemos problemas con el internet, por favor inténtelo más tarde");

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros=new HashMap<>();
                parametros.put("cancha_id",arrayCanchas.get(spn_canchas.getSelectedItemPosition()-1).getCancha_id());
                parametros.put("cancha_promo_precio",preciopromo.getText().toString());
                parametros.put("cancha_promo_inicio",fechaInicio.getText().toString() + " " + horaInicio.getText().toString()+":00");
                parametros.put("cancha_promo_fin",fechaFinal.getText().toString() + " " + horaFinal.getText().toString()+":00");
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
