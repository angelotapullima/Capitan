package com.tec.bufeo.capitan.Activity.PantallasNotificacion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models.Mensajes;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.ViewModels.MensajesViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Views.AdapterMensajes;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ChatsNotificacion extends AppCompatActivity implements View.OnClickListener {

    TextView nombre_charla;
    RecyclerView rcv_list_mensajes;
    EditText mensaje_chat;
    ImageView enviar_mensaje;
    Preferences preferences;
    MensajesViewModel mensajesViewModel;
    AdapterMensajes adapterMensajes;
    String id_chat,foto,nombre;
    ImageView foto_chat;
    BroadcastReceiver BR;
    UniversalImageLoader universalImageLoader;

    public static final String CHATNOTIFICACION= "CHATNOTIFICACION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_notificacion);


        preferences = new Preferences(this);

        universalImageLoader= new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        id_chat = getIntent().getStringExtra("id");
        //id_chat = "25";

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
        foto_chat= findViewById(R.id.foto_chat);
        enviar_mensaje.setOnClickListener(this);






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
                if (chats.size()>0){
                    adapterMensajes.setWords(chats);

                    foto = chats.get(chats.size()-1).getMensaje_foto();
                    nombre =chats.get(chats.size()-1).getMensaje_nombre();
                    numero= chats;
                    contador = adapterMensajes.getItemCount()+1;

                }else{
                    nombre ="";
                    foto ="";
                }
                UniversalImageLoader.setImage(IP2+"/"+foto,foto_chat,null);
                nombre_charla.setText(nombre);

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
        String url =IP2+"/api/User/enviar_mensaje";
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
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(CHATNOTIFICACION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
        super.onPause();
    }

}
