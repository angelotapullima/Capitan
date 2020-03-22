package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Views;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.DetalleTorneoNuevo;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Activity.PantallasNotificacion.ChatsNotificacion;
import com.tec.bufeo.capitan.Activity.PantallasNotificacion.RetosNotificacion;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Models.Notificaciones;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository.NotificacionesRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.ViewModels.NotificacionesViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class NotificacionesList extends DialogFragment {

    NotificacionesViewModel notificacionesViewModel;
    Activity activity;
    Context context;
    RecyclerView rcv_partidos;
    AdapterNotificacionesList adapterNotificacionesList;
    Preferences preferences;
    public NotificacionesList() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NotificacionesList newInstance(String title) {
        NotificacionesList frag = new NotificacionesList();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Dialog);
        notificacionesViewModel = ViewModelProviders.of(getActivity()).get(NotificacionesViewModel.class);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dialog_partidos_pendientes, container,false);
        preferences= new Preferences(getActivity());
        context = getContext();
        activity = getActivity();
        initViews(view);
        setAdapter();
        cargarvista();
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    private void initViews(View view){



        rcv_partidos = (RecyclerView) view.findViewById(R.id.rcv_partidos);
    }


    public void cargarvista(){
        notificacionesViewModel.getAllNotificaciones(preferences.getIdUsuarioPref(),preferences.getToken()).observe(this, new Observer<List<Notificaciones>>() {
            @Override
            public void onChanged(List<Notificaciones> notificaciones) {
                adapterNotificacionesList.setWords(notificaciones);
            }
        });

    }


    Application application;

    private void setAdapter(){

        adapterNotificacionesList =new AdapterNotificacionesList(context, new AdapterNotificacionesList.OnItemClickListener() {
            @Override
            public void onItemClick(Notificaciones notificaciones, String tipo, int position) {

                if (tipo.equals("RlNotificaciones")){
                    if (notificaciones.getNotificacion_tipo().equals("Reto")){
                        Intent i = new Intent(getActivity(), RetosNotificacion.class);
                        i.putExtra("id",notificaciones.getNotificacion_id_rel());

                        startActivity(i);
                    }else if (notificaciones.getNotificacion_tipo().equals("Mensaje")){
                        Intent i = new Intent(getActivity(), ChatsNotificacion.class);
                        i.putExtra("id",notificaciones.getNotificacion_id_rel());
                        startActivity(i);
                    }else if (notificaciones.getNotificacion_tipo().equals("Torneo")){
                        Intent i = new Intent(getActivity(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",notificaciones.getNotificacion_id_rel());
                        startActivity(i);
                    }

                    notificacionLeida(notificaciones.getId_notificacion());
                    NotificacionesRoomDBRepository notificacionesRoomDBRepository = new NotificacionesRoomDBRepository(application);
                    notificacionesRoomDBRepository.actualizarEstado(notificaciones.getId_notificacion());
                }
            }
        });

        rcv_partidos.setAdapter(adapterNotificacionesList);
        rcv_partidos.setLayoutManager(new LinearLayoutManager(getActivity()));



    }

    StringRequest stringRequest;
    private void notificacionLeida(final String id) {
        String url =IP2+"/api/User/notificacion_vista";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("notificacionLeida: ","id " + id +" -" +response);


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
                parametros.put("id_notificacion",id);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                return parametros;

            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
}
