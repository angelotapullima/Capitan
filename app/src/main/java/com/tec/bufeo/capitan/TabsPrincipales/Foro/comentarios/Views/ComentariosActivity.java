package com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.PerfilUsuarios;
import com.tec.bufeo.capitan.Activity.ProfileActivity;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Models.Comments;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.ViewModels.CommentsListViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class ComentariosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView_comentarios;
    CommentsListAdapter adapter;
    CommentsListViewModel commentsListViewModel;
    String id_publicacion;
    EditText mensaje;
    ImageView enviar;

    Preferences preferences;
    ArrayList<Integer> Arraycito = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        preferences = new Preferences(this);
        commentsListViewModel = ViewModelProviders.of(this).get(CommentsListViewModel.class);

        id_publicacion= getIntent().getExtras().getString("id_publicacion");


        initViews();
        setAdapter();
        //progressDialog= ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        cargarvista();
    }

    private void initViews( ){
        recyclerView_comentarios = (RecyclerView)findViewById(R.id.news_rv);
        mensaje=findViewById(R.id.mensaje);
        enviar= findViewById(R.id.enviar);

        enviar.setOnClickListener(this);


    }
    private void setAdapter(){
        adapter = new CommentsListAdapter(this, new CommentsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comments comments, String tipo, int position) {
                if (tipo.equals("comentarios_nombre")){
                    if(comments.getId_usuario().equals(preferences.getIdUsuarioPref())){
                        Intent i = new Intent(ComentariosActivity.this, ProfileActivity.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(ComentariosActivity.this, PerfilUsuarios.class);
                        i.putExtra("id_user",comments.getId_usuario());
                        startActivity(i);
                    }
                }
            }
        });
        recyclerView_comentarios.setAdapter(adapter);
        recyclerView_comentarios.setLayoutManager(new LinearLayoutManager(this));
    }

    String tamaño_lista;
    public void cargarvista(){


        commentsListViewModel.getmAllReviews(id_publicacion,preferences.getToken()).observe(this, new Observer<List<Comments>>() {
            @Override
            public void onChanged(@Nullable List<Comments> comments) {

                Arraycito.clear();
                for (int i=0; i<comments.size();i++){
                    Arraycito.add(Integer.parseInt(comments.get(i).getComments_id()));
                }
                Collections.sort(Arraycito);
                Log.d("changed model" ,  "" +Arraycito.size());
                adapter.setWords(comments);

            }
        });
    }

    public void setScroolbar(){
        recyclerView_comentarios.scrollToPosition(adapter.getItemCount()-1);
    }
    int cantidad =1;
    @Override
    public void onClick(View v) {
        if (v.equals(enviar)){

            String des = mensaje.getText().toString();
            if (!des.isEmpty()){


                //Comments comments = new Comments( preferences.getFotoUsuario(),preferences.getNombrePref(),
                // des,id_publicacion);
                Comments comments =  new Comments();
                comments.setComments_foto(preferences.getFotoUsuario());
                comments.setComments_nombre(preferences.getNickname());
                comments.setComments_comentario(des);
                comments.setPublicacion_id(id_publicacion);
                comments.setComments_fecha("0 min");
                if(Arraycito.size()>0){
                    cantidad = Arraycito.get(Arraycito.size()-1) + 1;
                    Log.d("","tamaño lista " + cantidad );
                    comments.setComments_id(String.valueOf(cantidad));

                }else{
                    comments.setComments_id(String.valueOf(cantidad));
                }


                //cantidad++;

                commentsListViewModel.insertOne(comments);
                EnviarComentarios(des);
                mensaje.setText("");
                setScroolbar();
            }

        }
    }

    StringRequest stringRequest;

    private void EnviarComentarios(final String comentario) {
        String url =IP2+"/api/Foro/registrar_comentario";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("comentario: ",""+response);




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
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("publicacion_id",id_publicacion);
                parametros.put("comentario",comentario);
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
}
