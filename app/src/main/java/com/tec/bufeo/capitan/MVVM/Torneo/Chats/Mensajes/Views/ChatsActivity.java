package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models.Mensajes;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.ViewModels.MensajesViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ChatsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView nombre_charla;
    RecyclerView rcv_list_mensajes;
    EditText mensaje_chat;
    ImageView enviar_mensaje;
    Preferences preferences;
    MensajesViewModel mensajesViewModel;
    AdapterMensajes adapterMensajes;
    String id_chat,nombre_chat;
    BroadcastReceiver BR;


    public static final String CHAT= "CHAT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        preferences = new Preferences(this);
        id_chat = getIntent().getStringExtra("id_chat");
        nombre_chat = getIntent().getStringExtra("nombre_chat");
        mensajesViewModel = ViewModelProviders.of(this).get(MensajesViewModel.class);
        initViews();
        setAdapter();
        //progressDialog= ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        cargarvista();

        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String to = intent.getStringExtra("tipo");
                String hora_mensaje =intent.getStringExtra("hora");
                String fecha_mensaje =intent.getStringExtra("fecha");
                String idchat_mensaje =intent.getStringExtra("id_chat");
                String idusuario_mensaje =intent.getStringExtra("id_usuario");
                String mensaje_mensaje =intent.getStringExtra("mensaje");

                if (to.equals("Mensaje")){
                    registrarMensaje(hora_mensaje,fecha_mensaje,idchat_mensaje,idusuario_mensaje,mensaje_mensaje);

                }
            }
        };
    }

    private void registrarMensaje(String hora_mensaje, String fecha_mensaje, String idchat_mensaje, String idusuario_mensaje, String mensaje_mensaje) {


        Mensajes mensajes =  new Mensajes();
        contador= contador+1;
        mensajes.setChat_id(idchat_mensaje);
        mensajes.setMensaje_contenido(mensaje_mensaje);
        mensajes.setMensajes_id_usuario(idusuario_mensaje);
        mensajes.setMensaje_hora(hora_mensaje);
        mensajes.setMensaje_fecha(fecha_mensaje);
        mensajes.setMensaje_id(String.valueOf(contador));
        mensajesViewModel.insertOne(mensajes);
        setScroolbar();

    }
    private void initViews( ){
        rcv_list_mensajes = (RecyclerView)findViewById(R.id.rcv_list_mensajes);
        mensaje_chat=findViewById(R.id.mensaje_chat);
        enviar_mensaje= findViewById(R.id.enviar_mensaje);
        nombre_charla= findViewById(R.id.nombre_charla);
        enviar_mensaje.setOnClickListener(this);

        nombre_charla.setText(nombre_chat);




    }
    private void setAdapter(){
        adapterMensajes = new AdapterMensajes(this, new AdapterMensajes.OnItemClickListener() {
            @Override
            public void onItemClick(Mensajes comments, int position) {

            }
        });

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rcv_list_mensajes.setLayoutManager(lm);
        rcv_list_mensajes.setAdapter(adapterMensajes);
    }

    List<Mensajes> numero;
    public void cargarvista(){

        mensajesViewModel.getmAllMensajes(id_chat,preferences.getToken()).observe(this, new Observer<List<Mensajes>>() {
            @Override
            public void onChanged(@Nullable List<Mensajes> chats) {
                adapterMensajes.setWords(chats);
                numero= chats;
                contador = adapterMensajes.getItemCount()+1;
            }
        });


    }

    public void setScroolbar(){

        rcv_list_mensajes.scrollToPosition(adapterMensajes.getItemCount()-1);
        //rcv_list_mensajes.getLayoutManager().smoothScrollToPosition(rcv_list_mensajes, new RecyclerView.State(), rcv_list_mensajes.getAdapter().getItemCount()-1);
    }

    int contador;
    @Override
    public void onClick(View v) {
        if (v.equals(enviar_mensaje)){

            String des = mensaje_chat.getText().toString();
            if (!des.equals("")){

                contador = contador+1;
                Mensajes mensajes = new Mensajes();


                String mens = mensaje_chat.getText().toString();
                mensajes.setChat_id(id_chat);
                mensajes.setMensaje_id(String.valueOf(contador));
                mensajes.setMensaje_contenido(mens);
                mensajes.setMensajes_id_usuario(preferences.getIdUsuarioPref());
                mensajes.setMensaje_estado("1");
                EnviarMes(mens);

                mensajesViewModel.insertOne(mensajes);
                mensaje_chat.setText("");
                setScroolbar();
            }else{

            }

        }
    }

    StringRequest stringRequest;
    private void EnviarMes(final String mensaje) {
        String url =IP2+"/api/Usuario/enviar_mensaje";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("mensajes: ",""+response);




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
                parametros.put("id_usuario",preferences.getIdUsuarioPref());
                parametros.put("id_chat",id_chat);
                parametros.put("mensaje",mensaje);
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

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(CHAT));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
        super.onPause();
    }

}
