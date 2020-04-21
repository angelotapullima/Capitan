package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Views;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tec.bufeo.capitan.Activity.ChanchasEquipos;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository.ChanchasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.ViewModels.ChanchasViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Jugadores.JugadoresViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ChanchasFragment extends Fragment {


    ChanchasViewModel chanchasViewModel;
    MaterialButton agregar_Chancha;
    String id_equipo,nombre,capitan_id;
    Preferences preferences;
    RecyclerView rcv_chanchas;
    AdapterChanchasItem adapterChanchasItem;


    public ChanchasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chanchasViewModel= ViewModelProviders.of(getActivity()).get(ChanchasViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chanchas, container, false);

        final Bundle bdl = getArguments();
        preferences = new Preferences(getContext());
        id_equipo = bdl.getString("id_equipo");
        nombre = bdl.getString("nombre");
        capitan_id = bdl.getString("capitan_id");

        initViews(view);
        cargarvista();


        if (capitan_id.equals(preferences.getIdUsuarioPref())){
            agregar_Chancha.setVisibility(View.VISIBLE);
        }else{
            agregar_Chancha.setVisibility(View.GONE);
        }

        return  view;
    }

    public void initViews(View view){
        agregar_Chancha = view.findViewById(R.id.agregar_Chancha);
        rcv_chanchas = view.findViewById(R.id.rcv_chanchas);


        agregar_Chancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_crearChancha();
            }
        });

    }

   
    public void cargarvista(){





        chanchasViewModel.getChanchas(id_equipo,preferences.getToken()).observe(this, new Observer<List<Chanchas>>() {
            @Override
            public void onChanged(List<Chanchas> chanchas) {
                if (chanchas.size()>0){

                    adapterChanchasItem = new AdapterChanchasItem(getContext(), chanchas, new AdapterChanchasItem.OnItemClickListener() {
                        @Override
                        public void onItemClick(Chanchas mequipos, String tipo, int position) {

                            if (tipo.equals("btn_reservar_en_chancha")){

                                double mf=0,ma=0;

                                mf = Double.parseDouble(mequipos.getMonto_final());
                                ma = Double.parseDouble(mequipos.getMonto_actual());

                                if (ma<mf){
                                    dialogo_Colaboracion(mequipos.getId_chancha(),mequipos.getMonto_final(),mequipos.getMonto_actual());
                                }else {
                                    preferences.toasVerde("esta chancha ya llego a su Límite de colaboraciones");
                                }

                            }
                        }
                    });

                    GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 1);
                    linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                    rcv_chanchas.setLayoutManager(linearLayoutManager);
                    rcv_chanchas.setAdapter(adapterChanchasItem);

                }else{

                }

            }
        });

    }



    AlertDialog customdialog ;
    public void dialogo_crearChancha(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_agregar_chanchas,null);
        MaterialButton crearChanchex = customView.findViewById(R.id.crearChanchex);
        MaterialButton cancelar = customView.findViewById(R.id.cancelar);
        final EditText name_chancha = customView.findViewById(R.id.name_chancha);
        final EditText monto_chancha = customView.findViewById(R.id.monto_chancha);

        dialog.setView(customView);
        customdialog  =dialog.create();
        customdialog.show();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customdialog.dismiss();
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

    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(getContext(), android.R.style.Theme_Translucent);
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
        customdialog.dismiss();

        String url =IP2+"/api/Torneo/agregar_chancha";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reg chancha: ",""+response);


                if (response.equals("1")){
                    preferences.toasVerde("Chancha registrada correctamente");
                    dialog_cargando.dismiss();
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
                parametros.put("id_equipo",id_equipo);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());
                Log.d("parametros: ",""+parametros.toString());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }




    AlertDialog dialogAggColaboracion ;
    public void dialogo_Colaboracion(final String id_colab, final String montoFinal, final String montoActual){
        AlertDialog.Builder dialogColaboracion = new AlertDialog.Builder(getContext());
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_colaboracion,null);
        MaterialButton guardarColaboracion = customView.findViewById(R.id.guardarColaboracion);
        MaterialButton cancelar = customView.findViewById(R.id.cancelar);

        final EditText monto_colaboracion = customView.findViewById(R.id.monto_colaboracion);

        dialogColaboracion.setView(customView);
        dialogAggColaboracion  =dialogColaboracion.create();
        dialogAggColaboracion.show();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAggColaboracion.dismiss();
            }
        });

        guardarColaboracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mf=0,ma=0,res=0;
                mf = Double.parseDouble(montoFinal);
                ma = Double.parseDouble(montoActual);
                res = mf-ma;

                if (monto_colaboracion.getText().toString().isEmpty()){
                    preferences.codeAdvertencia("Debe colocar un monto mayor a 0");
                }else{
                    if (Double.parseDouble(monto_colaboracion.getText().toString())>0){
                        if ( Double.parseDouble(monto_colaboracion.getText().toString())<= res ){
                            crearColaboracion(id_colab,monto_colaboracion.getText().toString());

                        }else{
                            preferences.codeAdvertencia("El monto no puede superar la cantidad que falta para completar la colaboracion");
                        }
                    }else{
                        preferences.codeAdvertencia("Debe colocar un monto mayor a 0");
                    }
                }




            }
        });
    }

    private void crearColaboracion( final String id ,final String monto) {

        dialogCarga();
        dialogAggColaboracion.dismiss();

        String url =IP2+"/api/Torneo/agregar_colaboracion";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reg colaboracion: ",""+response);


                if (response.equals("1")){
                    preferences.toasVerde("Chancha registrada correctamente");
                    dialog_cargando.dismiss();
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

                parametros.put("id_colab",id);
                parametros.put("monto",monto);
                parametros.put("app","true");
                parametros.put("id_user",preferences.getIdUsuarioPref());
                parametros.put("token",preferences.getToken());
                Log.d("parametros: ",""+parametros.toString());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
}
