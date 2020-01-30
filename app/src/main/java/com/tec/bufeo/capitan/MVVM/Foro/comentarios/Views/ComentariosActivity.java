package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

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

    String tamaño_lista;
    public void cargarvista(){


        commentsListViewModel.getmAllReviews(id_publicacion,preferences.getToken()).observe(this, new Observer<List<Comments>>() {
            @Override
            public void onChanged(@Nullable List<Comments> comments) {
                Log.e("tamaño", "onChanged: " +comments.size() );
                tamaño_lista = String.valueOf(comments.size());
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
            if (!des.isEmpty()){

                //Comments comments = new Comments( preferences.getFotoUsuario(),preferences.getNombrePref(),
                // des,id_publicacion);
                Comments comments =  new Comments();
                comments.setComments_foto(preferences.getFotoUsuario());
                comments.setComments_nombre(preferences.getPersonName() + " " + preferences.getPersonSurname());
                comments.setComments_comentario(des);
                comments.setPublicacion_id(id_publicacion);
                comments.setComments_fecha("0 min");
                comments.setComments_id(tamaño_lista);

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
