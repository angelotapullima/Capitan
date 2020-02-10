package com.tec.bufeo.capitan.Activity.EstadisticasEmpresas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.DetalleEstadisticasEmpresa;
import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.ModelEstadisticasEmpresa;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class EstadisticasEmpresas extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rcv_estadisticas_empresa;
    Preferences preferences;
    TextView f_inicio,f_final;
    Button btn_buscar_estadisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_empresas);

        preferences= new Preferences(this);
        rcv_estadisticas_empresa= findViewById(R.id.rcv_estadisticas_empresa);
        f_inicio= findViewById(R.id.f_inicio);
        f_final= findViewById(R.id.f_final);
        btn_buscar_estadisticas= findViewById(R.id.btn_buscar_estadisticas);

        f_inicio.setOnClickListener(this);
        f_final.setOnClickListener(this);
        btn_buscar_estadisticas.setOnClickListener(this);
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

    JSONObject jsonObject,jsonNode,jsonNode2,jsonNodecito;
    JSONArray resultJSON,jsonArray;
    ModelEstadisticasEmpresa modelEstadisticasEmpresa;
    DetalleEstadisticasEmpresa detalleEstadisticasEmpresa;
    StringRequest stringRequest;
    String fecha,fecha2;
    public Date date;

    public List<ModelEstadisticasEmpresa> listaItem = new ArrayList<>();
    private void obtenerEstadisticas() {

        fecha = f_inicio.getText().toString();
        String url =IP2+"/api/Empresa/estadisticas_por_empresa";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("estadisticas_empresa ",""+response);



                try {
                    jsonObject = new JSONObject(response);
                    resultJSON = jsonObject.getJSONArray("results");

                    listaItem.clear();
                    int count = resultJSON.length();

                    for (int i = 0; i < count; i++) {


                        jsonNode = resultJSON.getJSONObject(i);




                        jsonArray = jsonNode.getJSONArray(fecha);

                       int coun1 = jsonArray.length();

                        double monto_final_chancha=0,monto_final=0;
                        for (int x = 0; x < coun1; x++) {
                            jsonNodecito = jsonArray.getJSONObject(x);

                            monto_final_chancha = Double.parseDouble(jsonNodecito.optString("reserva_pago1"));
                            monto_final = monto_final + monto_final_chancha;

                        }


                        DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
                        Date convertido = fechaHora.parse(fecha);

                        SimpleDateFormat formatex = new SimpleDateFormat("EEE, d MMM yyyy");

                        fecha2 = formatex.format(convertido);
                        //fecha2 = String.valueOf(convertido);



                        modelEstadisticasEmpresa = new ModelEstadisticasEmpresa(String.valueOf(monto_final),fecha2,buildSubItemList(jsonArray));
                        listaItem.add(modelEstadisticasEmpresa);
                        //listaItem.clear();
                        String dtStart = fecha;
                        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            date = formate.parse(dtStart);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        date = sumarRestarHorasFecha(date,1);
                        fecha = formate.format(date);

                    }

                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
                    rcv_estadisticas_empresa.setLayoutManager(linearLayoutManager);

                    AdapterEstadisticasEmpresa itemAdapter = new AdapterEstadisticasEmpresa(getApplicationContext(), canchas,listaItem, new AdapterEstadisticasEmpresa.OnItemClickListener() {
                        @Override
                        public void onItemClick(ModelEstadisticasEmpresa mequipos, String tipo, int position) {

                        }
                    });
                    rcv_estadisticas_empresa.setAdapter(itemAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
                parametros.put("id_empresa","4");
                parametros.put("fecha_i",f_inicio.getText().toString());
                parametros.put("fecha_f",f_final.getText().toString());
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

    List<String> canchas = new ArrayList<String>();
    private List<DetalleEstadisticasEmpresa> buildSubItemList(JSONArray array) {
        List<DetalleEstadisticasEmpresa> subItemList = new ArrayList<>();


        for (int i=0; i<array.length(); i++) {
            try {
                jsonNode2 = array.getJSONObject(i);

                String monto,hora,reserva,cancha;

                monto = jsonNode2.optString("reserva_pago1");
                hora = jsonNode2.optString("reserva_hora");
                reserva = jsonNode2.optString("reserva_nombre");
                cancha = jsonNode2.optString("cancha_nombre");


                detalleEstadisticasEmpresa = new DetalleEstadisticasEmpresa(reserva,hora,monto,cancha);
                subItemList.add(detalleEstadisticasEmpresa);

                canchas.add(cancha);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Set<String> hashSet = new HashSet<String>(canchas);
        canchas.clear();
        canchas.addAll(hashSet);

        return subItemList;
    }


    public Date sumarRestarHorasFecha(Date fecha, int dias){


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de horas a añadir, o restar en caso de horas<0
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

    }

    @Override
    public void onClick(View v) {
        if (v.equals(f_inicio)){
            showDatePickerDialog(f_inicio);
        }
        if (v.equals(f_final)){
            showDatePickerDialog(f_final);
        }
        if (v.equals(btn_buscar_estadisticas)){

            obtenerEstadisticas();
        }

    }
}
