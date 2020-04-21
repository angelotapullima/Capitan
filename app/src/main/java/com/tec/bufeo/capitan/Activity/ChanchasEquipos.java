package com.tec.bufeo.capitan.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ChanchasEquipos extends AppCompatActivity {



    MisEquiposViewModel misEquiposViewModel;
    RelativeLayout no_hay_equipos;
    ArrayList<Mequipos> arrayEquipos = new ArrayList<>();
    ArrayList<String> arrayequipo = new ArrayList<>();
    Spinner spn_misEquipos;
    Preferences preferences;
    LinearLayout btnFinishHim;
    MaterialButton crearChanchex,cancelar;
    EditText name_chancha,monto_chancha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanchas_equipos);
        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);

        preferences = new Preferences(this);

        no_hay_equipos = findViewById(R.id.no_hay_equipos);
        spn_misEquipos = findViewById(R.id.spn_misEquipos);
        btnFinishHim = findViewById(R.id.btnFinishHim);
        crearChanchex = findViewById(R.id.crearChanchex);
        cancelar = findViewById(R.id.cancelar);
        name_chancha = findViewById(R.id.name_chancha);
        monto_chancha = findViewById(R.id.monto_chancha);




        misEquiposViewModel.getAllEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                //adaptadorMiEquipo.setWords(mequipos);
                if(mequipos.size()>0){
                    no_hay_equipos.setVisibility(View.GONE);
                    arrayEquipos.clear();
                    arrayequipo.clear();
                    arrayEquipos.addAll(mequipos);
                    Log.e("mis Equipos", "onChanged: "+mequipos.size() );

                    for (Mequipos obj :arrayEquipos){
                        arrayequipo.add(obj.getEquipo_nombre());
                    }
                    //progressBar.setVisibility(ProgressBar.INVISIBLE);
                    arrayequipo.add(0,"Seleccione");
                    ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayequipo);
                    adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
                    spn_misEquipos.setAdapter(adapEquipos);
                }else{

                }

            }
        });
        showToolbar("Crear chancha",true);

        btnFinishHim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        crearChanchex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name_chancha.getText().toString().isEmpty()){
                    if (!monto_chancha.getText().toString().isEmpty()){
                        if (Integer.parseInt(monto_chancha.getText().toString())>0){
                            crearChancha(name_chancha.getText().toString(),monto_chancha.getText().toString());
                        }else{
                            preferences.codeAdvertencia("El monto debe ser mayor a 0");
                        }
                    }else{
                        preferences.codeAdvertencia("El campo monto no debe estar vacio");
                    }

                }else{
                    preferences.codeAdvertencia("El campo nombre no debe estar vacio");
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


    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_cargando.findViewById(R.id.back);
        LinearLayout layout = dialog_cargando.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cargando.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog_cargando.show();

    }



    StringRequest stringRequest;
    private void crearChancha(final String nombre, final String monto) {

        dialogCarga();


        String url =IP2+"/api/Torneo/agregar_chancha";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reg chancha: ",""+response);


                if (response.equals("1")){
                    preferences.toasVerde("Chancha registrada correctamente");
                    dialog_cargando.dismiss();
                    finish();
                }else{
                    preferences.codeAdvertencia("Lo siento hubo un error, intentelo más tarde");
                    dialog_cargando.dismiss();
                }




            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPUESTA: ",""+error.toString());


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();

                parametros.put("nombre",nombre);
                parametros.put("monto",monto);
                parametros.put("id_equipo",arrayEquipos.get(spn_misEquipos.getSelectedItemPosition()-1).getEquipo_id());
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.d("parametros: ",""+parametros.toString());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }
}
