package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models.Comments;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.ViewModels.CommentsListViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class ComentariosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView_comentarios;
    CommentsListAdapter adapter;
    CommentsListViewModel commentsListViewModel;
    String id_publicacion;
    EditText mensaje;
    ImageView enviar;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        preferences = new Preferences(this);
        commentsListViewModel = ViewModelProviders.of(this).get(CommentsListViewModel.class);
        id_publicacion= getIntent().getExtras().getString("id_publicacion");

        Toast.makeText(this, ""+ id_publicacion, Toast.LENGTH_SHORT).show();
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
        adapter= new CommentsListAdapter(this, new CommentsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Comments comments, int position) {

            }
        });
        recyclerView_comentarios.setAdapter(adapter);
        recyclerView_comentarios.setLayoutManager(new LinearLayoutManager(this));
    }

    public void cargarvista(){


        commentsListViewModel.getmAllReviews(id_publicacion).observe(this, new Observer<List<Comments>>() {
            @Override
            public void onChanged(@Nullable List<Comments> comments) {
                adapter.setWords(comments);

            }
        });
    }

    public void setScroolbar(){
        recyclerView_comentarios.scrollToPosition(adapter.getItemCount()-1);
    }
    @Override
    public void onClick(View v) {
        if (v.equals(enviar)){

            String des = mensaje.getText().toString();
            if (!des.equals("")){

                //Comments comments = new Comments( preferences.getFotoUsuario(),preferences.getNombrePref(),
                // des,id_publicacion);
                Comments comments =  new Comments();
                comments.setComments_foto(preferences.getFotoUsuario());
                comments.setComments_nombre(preferences.getNombrePref());
                comments.setComments_comentario(des);
                comments.setComments_id(id_publicacion);

                commentsListViewModel.insertOne(comments);
                EnviarComentarios(des);
                mensaje.setText("");
                setScroolbar();
            }else{

            }

        }
    }

    StringRequest stringRequest;

    private void EnviarComentarios(final String comentario) {
        String url =IP+"/index.php?c=Foro&a=registrar_comentario&key_mobile=123456asdfgh";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("comentario: ",""+response);




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
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("publicacion_id",id_publicacion);
                parametros.put("comentario",comentario);

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }
}
