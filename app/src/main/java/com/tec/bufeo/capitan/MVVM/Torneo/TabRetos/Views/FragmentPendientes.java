package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Views;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository.RetosWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentPendientes extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    RetosViewModel retosViewModel;
    RecyclerView rcv_duelos_pendientes;
    AdaptadorRetos adaptadorRetos;
    Preferences preferences;
    Activity activity;
    Context context;

    SwipeRefreshLayout RefreshLayoutPendientes;
    public FragmentPendientes() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_pendientes, container, false);
        context =  getContext();
        preferences = new Preferences(context);
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }


    private void initViews(View view){

        RefreshLayoutPendientes =  view.findViewById(R.id.RefreshLayoutPendientes);

        RefreshLayoutPendientes.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        RefreshLayoutPendientes.setOnRefreshListener(this);
        rcv_duelos_pendientes =(RecyclerView)view.findViewById(R.id.rcv_duelos_pendientes);
        activity = getActivity();
        context = getContext();
    }

    public void cargarvista(){

        retosViewModel.getAllRetos("0").observe(this, new Observer<List<Retos>>() {
            @Override
            public void onChanged(@Nullable List<Retos> retos) {
                adaptadorRetos.setWords(retos);
            }
        });

    }

    private void setAdapter() {
        adaptadorRetos =  new AdaptadorRetos(getActivity(), new AdaptadorRetos.OnItemClickListener() {
            @Override
            public void onItemClick(Retos retos, int position) {

                if (retos.getUser_respuesta().equals(preferences.getIdUsuarioPref())){

                    dialogoAceptar(retos);


                }else{
                    preferences.codeAdvertencia("Solo el capitán de equipo retado puede responder");
                }




            }
        });

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_duelos_pendientes.setLayoutManager(linearLayoutManager);
        rcv_duelos_pendientes.setAdapter(adaptadorRetos);


    }

    public void dialogoAceptar(final Retos retos){
        final Dialog dialogr = new Dialog(getActivity());
        dialogr.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogr.setContentView(R.layout.dialogo_mensaje_responder_reto);

        ImageView fotoRetosRetador = dialogr.findViewById(R.id.fotoRetosRetador);
        ImageView fotoRetado = dialogr.findViewById(R.id.fotoRetado);

        TextView nombreRetador = dialogr.findViewById(R.id.nombreRetador);
        TextView nombreRetado = dialogr.findViewById(R.id.nombreRetado);

        TextView mensajeReto = dialogr.findViewById(R.id.mensajeReto);

        LinearLayout btn_cancelar = dialogr.findViewById(R.id.btn_cancelar);
        LinearLayout btn_aceptar =  dialogr.findViewById(R.id.btn_aceptar);

        String  texto = "tu equipo " + retos.getRetos_nombre_retador() +" fue retado por " + retos.getRetos_nombre_retado();
        mensajeReto.setText(texto);
        nombreRetador.setText(retos.getRetos_nombre_retador());
        nombreRetado.setText(retos.getRetos_nombre_retado());


        UniversalImageLoader.setImage(IP2+"/"+ retos.getRetos_foto_retador(),fotoRetosRetador,null);
        UniversalImageLoader.setImage(IP2+"/"+ retos.getRetos_foto_retado(),fotoRetado,null);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogr.dismiss();
                //EnviarEstadoReto(id_reto,"2");
            }
        });



        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Eliminamos los datos de la SharedPreferences
                //preferencesUser.edit().clear().apply();
                EnviarEstadoReto(retos.getRetos_id(),"1");
                dialogr.dismiss();



            }
        });
        dialogr.show();

    }

    StringRequest stringRequest;
    private void EnviarEstadoReto(final String id, final String respuesta) {
        String url =IP2+"/api/Torneo/responder_reto";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mensajes: ",""+response);




            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.d("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("id_reto",id);
                parametros.put("respuesta",respuesta);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest);
    }
    Application application;

    @Override
    public void onRefresh() {
        //retosViewModel.ElimarRetos();

        RetosWebServiceRepository retosWebServiceRepository = new RetosWebServiceRepository(application);
        retosWebServiceRepository.providesWebService(preferences.getIdUsuarioPref(),preferences.getToken(),"normal");
        RefreshLayoutPendientes.setRefreshing(false);
    }
}
