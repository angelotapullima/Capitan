package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos.RegistrarEquipoEnGrupo;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias.ListarInstancias;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models.InstanciasModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.ViewModels.InstanciasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class CrearInstancias extends AppCompatActivity implements View.OnClickListener {


    Button btn_agregar_instancias,btnNext_a_listaInstancias;
    InstanciasViewModel instanciasViewModel;
    RecyclerView rcv_listaInstancias;
    EditText nombre_instancia;
    AdapterInstancias adapterInstancias;
    Spinner spn_tipo_instancias;
    ArrayList<String> arrayDatos;
    Preferences preferences;

    int cantidad;
    String id_torneo,valor_spinner,valor_tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_instancias);

        instanciasViewModel = ViewModelProviders.of(this).get(InstanciasViewModel.class);

        preferences= new Preferences(this);

        initViews();
        setAdapter();
        cargarvista();

    }

    private void initViews() {
        if(getIntent().getExtras()!=null){
            id_torneo = getIntent().getExtras().getString("id_torneo");


            //nombre_torneo = getIntent().getExtras().getString("nombre_torneo");
        }

        //id_torneo = "1";
        rcv_listaInstancias =  findViewById(R.id.rcv_listaInstancias);
        nombre_instancia =  findViewById(R.id.nombre_instancia);
        btn_agregar_instancias =  findViewById(R.id.btn_agregar_instancias);
        btnNext_a_listaInstancias =  findViewById(R.id.btnNext_a_listaInstancias);
        spn_tipo_instancias =  findViewById(R.id.spn_tipo_instancias);


        arrayDatos =  new ArrayList<String>();
        arrayDatos.add(0,"Seleccione");
        arrayDatos.add(1,"Fase de Grupos");
        arrayDatos.add(2,"Eliminatorias");
        ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayDatos);
        adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
        spn_tipo_instancias.setAdapter(adapEquipos);




        spn_tipo_instancias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) {

                }else if(position==1){
                    nombre_instancia.setHint("Fecha 1, Fecha 2 , Fecha 3, etc");
                }else if(position==2){
                    nombre_instancia.setHint("8vos de Final, 4tos de final , Semifinal, etc");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_agregar_instancias.setOnClickListener(this);
        btnNext_a_listaInstancias.setOnClickListener(this);
    }

    private void setAdapter() {

        adapterInstancias =  new AdapterInstancias(this, new AdapterInstancias.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, InstanciasModel instanciasModel, int position) {

            }
        });

        rcv_listaInstancias.setAdapter(adapterInstancias);
        rcv_listaInstancias.setLayoutManager(new LinearLayoutManager(this));




    }

    private void cargarvista() {

        instanciasViewModel.getIdTorneo(id_torneo, preferences.getToken()).observe(this, new Observer<List<InstanciasModel>>() {
            @Override
            public void onChanged(List<InstanciasModel> instanciasModels) {
                if (instanciasModels.size()>0) {
                    adapterInstancias.setWords(instanciasModels);
                }
                cantidad = instanciasModels.size()+1;
            }
        });


    }


    String url = IP2+"/api/Torneo/registrar_instancia";
    StringRequest stringRequest;
    int valor;
    private void crear_instancia() {


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Torneo","registrar_instancia"+response);


                try {
                    JSONObject jsonObject;;

                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");
                    JSONObject jsonNode = resultJSON.getJSONObject(0);
                    valor = jsonNode.optInt("valor");
                    if (valor==1){
                        instanciasViewModel.getIdTorneo(id_torneo, preferences.getToken());
                        nombre_instancia.setText("");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nombre_instancia.setText("");
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();



            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();

                parametros.put("id_torneo", id_torneo);
                parametros.put("instancia_tipo", valor_tipo);
                parametros.put("token", preferences.getToken());
                parametros.put("app", "true");
                parametros.put("instancia_nombre", nombre_instancia.getText().toString());



                Log.d("torneo", "getParams: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }

    public void agregar_instancia_a_bd(){
        InstanciasModel instanciasModel =  new InstanciasModel();

        instanciasModel.setId_instancias(String.valueOf(cantidad));
        instanciasModel.setNombre_instancias(nombre_instancia.getText().toString());
        instanciasModel.setId_torneo(id_torneo);
        instanciasModel.setTipo_instancias(valor_tipo);
        instanciasModel.setEstado("1");
        instanciasViewModel.insert(instanciasModel);
    }
    @Override
    public void onClick(View view) {

        if (view.equals(btn_agregar_instancias)){
            if (!spn_tipo_instancias.getSelectedItem().toString().equals("Seleccione")){
                if(!nombre_instancia.getText().toString().isEmpty()){
                    valor_spinner = spn_tipo_instancias.getSelectedItem().toString();

                    if (valor_spinner.equals("Eliminatorias")){
                        valor_tipo = "2";
                    }else{
                        valor_tipo = "1";
                    }
                    if (!nombre_instancia.getText().equals("")){

                        agregar_instancia_a_bd();
                    }
                    crear_instancia();
            }else{
                    preferences.codeAdvertencia("debe seleccionar el tipo de instancia");
                }


            }else{
                preferences.codeAdvertencia("el nombre de la instancia no debe quedar vacio");
            }

        }if (view.equals(btnNext_a_listaInstancias)) {


            Intent i = new Intent(this, ListarInstancias.class);
            i.putExtra("id_torneo",id_torneo);
            startActivity(i);
        }

    }
}
