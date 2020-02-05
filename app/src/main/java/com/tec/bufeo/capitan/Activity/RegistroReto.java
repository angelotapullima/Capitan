package com.tec.bufeo.capitan.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;
import com.tec.bufeo.capitan.others.Equipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.horaDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RegistroReto extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


   Spinner spn_misEquipos;
   CircleImageView civ_fotoEquipoRetador, civ_fotoEquipoRetado;
   TextView txt_equipoRetador, txt_equipoRetado,btn_fechaReto,btn_horaReto;
   Button  btn_registrarReto;
   EditText edt_lugarReto;
   DataConnection dc,d1;
   Retos retos;
   public static ArrayList<Equipo> arrayEquipo;
   ArrayList<String> arrayequipo;
   Context context;
    public String nombre_retado,foto_retado,id_retado, fecha;
    private Date date;
    Preferences preferences;
    RetosViewModel retosViewModel;
    String capitan_id;
    int cantidad_de_retos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_reto);
        retosViewModel = ViewModelProviders.of(this).get(RetosViewModel.class);

        preferences= new Preferences(this);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);
        preferences = new Preferences(this);*/

        spn_misEquipos = findViewById(R.id.spn_misEquipos);
        civ_fotoEquipoRetador = findViewById(R.id.civ_fotoEquipoRetador);
        txt_equipoRetador = findViewById(R.id.txt_equipoRetador);
        txt_equipoRetado = findViewById(R.id.txt_equipoRetado);
        civ_fotoEquipoRetado = findViewById(R.id.civ_fotoEquipoRetado);
        btn_fechaReto = findViewById(R.id.btn_fechaReto);
        btn_horaReto = findViewById(R.id.btn_horaReto);
        edt_lugarReto = findViewById(R.id.edt_lugarReto);
        btn_registrarReto = findViewById(R.id.btn_registrarReto);
        context = getApplicationContext();

        btn_fechaReto.setOnClickListener(this);
        btn_horaReto.setOnClickListener(this);
        btn_registrarReto.setOnClickListener(this);
        nombre_retado = getIntent().getStringExtra("nombre_retado");
        foto_retado = getIntent().getStringExtra("foto_retado");
        id_retado = getIntent().getStringExtra("id_retado");
        capitan_id = getIntent().getStringExtra("capitan_id");



        txt_equipoRetado.setText(nombre_retado);
        Picasso.with(context).load(IP2+"/"+ foto_retado).into(civ_fotoEquipoRetado);
        Log.e("registroReto", "foto " + foto_retado );



        dc = new DataConnection(RegistroReto.this,"listarEquipo",new Equipo(preferences.getIdUsuarioPref()),false);
        new RegistroReto.GetEquipo().execute();

        spn_misEquipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    txt_equipoRetador.setText("");
                    civ_fotoEquipoRetador.setImageResource(R.drawable.error);
                    //falta imagen
                }else {
                    txt_equipoRetador.setText(arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_nombre());
                    Picasso.with(context).load(IP2+"/"+ arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_foto()).into(civ_fotoEquipoRetador);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        retosViewModel.getAll().observe(this, new Observer<List<Retos>>() {
            @Override
            public void onChanged(List<Retos> retos) {
                if (retos.size()>0){
                    cantidad_de_retos = retos.size();
                }
            }
        });

    }

    public class GetEquipo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayequipo =  new ArrayList<String>();
            arrayEquipo = dc.getListadoEquipos();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(context,"size"+arrayEquipo.size(),Toast.LENGTH_LONG).show();

            for (Equipo obj :arrayEquipo){
                arrayequipo.add(obj.getEquipo_nombre());
            }
            //progressBar.setVisibility(ProgressBar.INVISIBLE);
            arrayequipo.add(0,"Seleccione");
            ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(context,R.layout.spiner_item,arrayequipo);
            adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_misEquipos.setAdapter(adapEquipos);

            }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();
                return true;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hora, int minuto) {
        btn_horaReto.setText(String.format("%02d:%02d", hora, minuto));
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year+ "-" + (monthOfYear+1) + "-" +dayOfMonth  );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        btn_fechaReto.setText(outDate);


    }



    int year,month,day; String dia,mes;
    private void showDateDailog(final TextView editText) {

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
                editText.setText(new StringBuilder().append(year).append("-").append(mes).append("-").append(dia));

            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fechaReto:
                showDateDailog(btn_fechaReto);
                break;
            case R.id.btn_horaReto:
                obtenerHora(btn_horaReto);
                break;

            case R.id.btn_registrarReto:

                if (!(txt_equipoRetador.getText().toString().isEmpty()) && !(txt_equipoRetado.getText().toString().isEmpty()) && !(btn_fechaReto.getText().toString().isEmpty())&& !(btn_horaReto.getText().toString().isEmpty()) && !(edt_lugarReto.getText().toString().isEmpty()) &&  !(spn_misEquipos.getSelectedItem().toString().equals("Seleccione")) ) {


                    retos = new Retos();
                    retos.setRetos_id(String.valueOf(cantidad_de_retos+1));
                    retos.setRetador_id(arrayEquipo.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_id());
                    retos.setRetado_id(id_retado);
                    retos.setRetos_fecha(btn_fechaReto.getText().toString());
                    retos.setRetos_hora(btn_horaReto.getText().toString());
                    retos.setRetos_lugar(edt_lugarReto.getText().toString());

                    enviarReto(retos);
                    }
                else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();}
                break;

        }
    }


    List<Retos> retosList = new ArrayList<>();

    String valor;
    StringRequest stringRequest;
    private void enviarReto(final Retos retos) {
        dialogCarga();
        String url =IP2+"/api/Torneo/retar_equipo";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("registro reto: ",""+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");

                    int count = resultJSON.length();

                    //for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(0);
                        valor = jsonNode.optString("valor");
                    //}


                    if(valor.equals("1")){
                        retosList.add(retos);
                        retosViewModel.insertRetos(retosList);

                        //crearChatReto(capitan_id,preferences.getIdUsuarioPref());
                    }else if(valor.equals("3")){
                        preferences.codeAdvertencia("Ya cuentas con un reto pendiente");
                        dialog_cargando.dismiss();
                    }else if(valor.equals("2")){
                        preferences.toasRojo("Ocurrio un error","vuelva intentarlo más tarde");
                        dialog_cargando.dismiss();
                    }else{
                        preferences.toasRojo("Ocurrio un error","vuelva intentarlo más tarde");
                        dialog_cargando.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());
                dialog_cargando.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("retador",retos.getRetador_id());
                parametros.put("retado",retos.getRetado_id());
                parametros.put("fecha",retos.getRetos_fecha());
                parametros.put("hora",retos.getRetos_hora());
                parametros.put("lugar",retos.getRetos_lugar());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.e("registro reto", "getParams: " +parametros );
                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }

    public void crearChatReto(final String retado, final String retador){
        String url =IP2+"/api/User/crear_chat";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("crear chat: ",""+response);
                dialog_cargando.dismiss();
                preferences.toasVerde("Reto creado Correctamente");
                finish();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());
                dialog_cargando.dismiss();

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_1",retado);
                parametros.put("id_2",retador);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.e("registro chat", "getParams: " +parametros );
                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);

    }

    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);

        dialog_cargando.show();

    }

}
