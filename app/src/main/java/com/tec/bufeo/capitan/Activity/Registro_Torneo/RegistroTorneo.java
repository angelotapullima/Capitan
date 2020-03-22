package com.tec.bufeo.capitan.Activity.Registro_Torneo;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.Activity.Negocios.ViewModels.NegociosViewModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Views.CrearGrupoRelampago;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;
import com.theartofdev.edmodo.cropper.CropImage;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationAction;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class RegistroTorneo extends AppCompatActivity implements View.OnClickListener {


    EditText edt_nombreTorneo, edt_descripcionTorneo,edt_organizador,edt_costo;
   Button  btn_crearTorneo;
   Spinner spn_negocios;
   ArrayList<String> arrayDatos;
   Spinner spn_rutas;
   DataConnection dc;
   TextView btn_fechaTorneo ,btn_horaTorneo;
   int valor;
   String id_torneo,valor_tipo,valor_spinner;
   Preferences preferences;
   NegociosViewModel negociosViewModel;
    ArrayList<Negocios> arrayNegocios = new ArrayList<>();
    ArrayList<String> arraynegocios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_torneo);


        preferences = new Preferences(this);

        negociosViewModel = ViewModelProviders.of(this).get(NegociosViewModel.class);


        edt_nombreTorneo = findViewById(R.id.edt_nombreTorneo);
        edt_descripcionTorneo = findViewById(R.id.edt_descripcionTorneo);
        spn_negocios = findViewById(R.id.spn_negocios);
        edt_organizador = findViewById(R.id.edt_organizador);
        edt_costo = findViewById(R.id.edt_costo);
        btn_fechaTorneo = findViewById(R.id.btn_fechaTorneo);
        btn_horaTorneo = findViewById(R.id.btn_horaTorneo);
        btn_crearTorneo = findViewById(R.id.btn_crearTorneo);
        spn_rutas= findViewById(R.id.spn_rutas);

        arrayDatos =  new ArrayList<String>();
        arrayDatos.add(0,"Seleccione");
        arrayDatos.add(1,"Torneo Relampago");
        arrayDatos.add(2,"Campeonato");
        ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayDatos);
        adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
        spn_rutas.setAdapter(adapEquipos);

        //camara.setOnClickListener(this);
        btn_crearTorneo.setOnClickListener(this);
        btn_horaTorneo.setOnClickListener(this);
        btn_fechaTorneo.setOnClickListener(this);

        if((ContextCompat.checkSelfPermission(RegistroTorneo.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(RegistroTorneo.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(RegistroTorneo.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }
        showToolbar("Registro torneo" ,true);

        negociosViewModel.getAllNegocios(preferences.getUbigeoId(),preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<Negocios>>() {
            @Override
            public void onChanged(List<Negocios> negocios) {
                if (negocios.size()>0){
                    arrayNegocios.clear();
                    arrayNegocios.addAll(negocios);
                    //Log.e("mis Equipos", "onChanged: "+mequipos.size() );

                    for (Negocios obj :arrayNegocios){
                        arraynegocios.add(obj.getNombre_empresa());
                    }
                    //progressBar.setVisibility(ProgressBar.INVISIBLE);
                    arraynegocios.add(0,"Seleccionar");
                    ArrayAdapter<String> adapNegocios = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arraynegocios);
                    adapNegocios.setDropDownViewResource(R.layout.spiner_dropdown_item);
                    spn_negocios.setAdapter(adapNegocios);
                }
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
    public void onClick(View view) {

        if (view.equals(btn_fechaTorneo)){
            showDateDailog(btn_fechaTorneo);

        }if (view.equals(btn_horaTorneo)){
            obtenerHora(btn_horaTorneo);

        }
        if (view.equals(btn_crearTorneo)){

            if (edt_nombreTorneo.getText().toString().isEmpty()){
                preferences.codeAdvertencia("debe registrar un nombre para el torneo");
            }else if (edt_descripcionTorneo.getText().toString().isEmpty()) {
                preferences.codeAdvertencia("debe registrar una descripción para el torneo");
            }else if(btn_fechaTorneo.getText().toString().equals("Fecha")){
                preferences.codeAdvertencia("debe seleccionar una Fecha para el torneo");
            }else if(btn_horaTorneo.getText().toString().equals("Hora")){
                preferences.codeAdvertencia("debe seleccionar una Hora para el torneo");
            }else if (spn_negocios.getSelectedItem().equals("Seleccionar")) {
                preferences.codeAdvertencia("debe registrar un Lugar para el torneo");
            }else if (edt_organizador.getText().toString().isEmpty()) {
                preferences.codeAdvertencia("debe registrar un Organizador del torneo");
            }else if (edt_costo.getText().toString().isEmpty()) {
                preferences.codeAdvertencia("debe el costo del torneo");
            }else if (spn_rutas.getSelectedItem().toString().equals("Seleccione")) {
                preferences.codeAdvertencia("debe seleccionar un tipo de torneo");
            }else {
                    valor_spinner = spn_rutas.getSelectedItem().toString();
                    if (valor_spinner.equals("Campeonato")){
                        valor_tipo = "2";
                    }else{
                        valor_tipo = "1";
                    }
                    publicar_sin_foto();
            }
        }



    }



    String url = IP2+"/api/Torneo/registrar_torneo";

    StringRequest stringRequest;
    private void publicar_sin_foto() {
        dialogoCargando();


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Torneo","publicar sin foto"+response);


                try {
                    JSONObject jsonObject;;

                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");

                    int count = resultJSON.length();


                    for (int i = 0; i < count; i++) {
                        JSONObject jsonNode = resultJSON.getJSONObject(i);
                        Grupos grupos = new Grupos();

                        valor = jsonNode.optInt("valor");
                        id_torneo = jsonNode.optString("id_torneo");

                        if (valor==1){

                            if (valor_tipo.equals("2")){
                                Intent x = new Intent(RegistroTorneo.this, CrearGrupoRelampago.class);
                                x.putExtra("id_torneo",id_torneo);
                                x.putExtra("nombre_torneo",edt_nombreTorneo.getText().toString());
                                x.putExtra("tipo_campeonato","campeonato");
                                startActivity(x);
                            }else{
                                Intent x = new Intent(RegistroTorneo.this, CrearGrupoRelampago.class);
                                x.putExtra("id_torneo",id_torneo);
                                x.putExtra("nombre_torneo",edt_nombreTorneo.getText().toString());
                                x.putExtra("tipo_campeonato","relampago");
                                startActivity(x);
                            }
                        }
                    }
                    dialog_carga.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                dialog_carga.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();

                parametros.put("usuario_id", preferences.getIdUsuarioPref());
                parametros.put("descripcion", edt_descripcionTorneo.getText().toString());
                parametros.put("organizador", edt_organizador.getText().toString());
                parametros.put("costo", edt_costo.getText().toString());
                parametros.put("fecha", btn_fechaTorneo.getText().toString());
                parametros.put("hora", btn_horaTorneo.getText().toString());
                parametros.put("lugar", spn_negocios.getSelectedItem().toString());
                parametros.put("nombre", edt_nombreTorneo.getText().toString());
                parametros.put("tipo", valor_tipo);
                parametros.put("app", "true");
                parametros.put("token", preferences.getToken());

                Log.d("torneo", "getParams: "+parametros.toString() );
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


}
