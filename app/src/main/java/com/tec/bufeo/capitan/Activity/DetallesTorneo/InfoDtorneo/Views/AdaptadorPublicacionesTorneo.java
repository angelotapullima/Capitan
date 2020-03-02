package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.PublicacionesTorneo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorPublicacionesTorneo extends RecyclerView.Adapter<AdaptadorPublicacionesTorneo.foroTorneoViewHolder> {



    StringRequest stringRequest;
    ImageButton imgbt_like_g,imgbt_comment_g;
    TextView nlike_g;
    int totalLikes;
    JSONObject json_data;
    String resultado;
    int posicionlocalc;
    PublicacionesTorneo current;
    Context ctx;
    String id_publi;
    private  OnItemClickListener listener;
    Preferences preferencesUser;



    class foroTorneoViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_fotoForo,foto_perfil_publicacion;
        private TextView txt_tituloForo, txt_usuarioForo, txt_descripcionForo, txt_fechaHora,txt_totallike,txt_totalcoment;
        private ProgressBar prog_like;
        private ImageButton imgbt_like,imgbt_comment;
        CardView materialCardView;

        private foroTorneoViewHolder(View itemView) {
            super(itemView);
            img_fotoForo=  itemView.findViewById(R.id.img_fotoForo);
            txt_tituloForo=  itemView.findViewById(R.id.txt_tituloForo);
            txt_usuarioForo=  itemView.findViewById(R.id.txt_usuarioForo);
            txt_descripcionForo=  itemView.findViewById(R.id.txt_descripcionForo);
            txt_fechaHora = itemView.findViewById(R.id.txt_fechaHora);
            prog_like = itemView.findViewById(R.id.prog_like);
            imgbt_like = itemView.findViewById(R.id.imgbt_like);
            txt_totallike = itemView.findViewById(R.id.txt_totallike);
            imgbt_comment=itemView.findViewById(R.id.imgbt_comment);
            txt_totalcoment=itemView.findViewById(R.id.txt_totalcoment);
            foto_perfil_publicacion=itemView.findViewById(R.id.foto_perfil_publicacion);
            materialCardView=itemView.findViewById(R.id.materialCardView);
        }

        public void bid(final PublicacionesTorneo publicacionesTorneo, final OnItemClickListener listener){



        }
    }

    private final LayoutInflater mInflater;


    private List<PublicacionesTorneo> mUsers; // Cached copy of users
    private List<PublicacionesTorneo> mDataFiltered; // Cached copy of users


    public AdaptadorPublicacionesTorneo(Context context, OnItemClickListener listener) {
        requestQueue = Volley.newRequestQueue(context);
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public foroTorneoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_card_foro4, parent, false);
        return new foroTorneoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final foroTorneoViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);


            holder.imgbt_like.setId(position);
            holder.imgbt_comment.setId(position);
            holder.txt_tituloForo.setText(current.getForo_titulo());
            holder.txt_usuarioForo.setText(current.getPublicacion_torneo());
            holder.txt_descripcionForo.setText(current.getForo_descripcion());
            holder.txt_fechaHora.setText("Hace: "+current.getForo_feccha());
            holder.txt_totalcoment.setText(current.getCant_Comentarios());

            holder.txt_totallike.setText(current.getCant_likes());

            if(current.getDio_like().equals("0")){
                holder.imgbt_like.setImageResource(R.drawable.thumb_up_outline);
            }else {
                holder.imgbt_like.setImageResource(R.drawable.thumb_up);
            }


            Glide.with(ctx).load(IP2+"/"+ current.getForo_foto()).into(holder.img_fotoForo);
            Glide.with(ctx).load(IP2+"/"+ current.getUsuario_foto()).into(holder.foto_perfil_publicacion);



            //holder.imgbt_comment.setOnClickListener(this);
            holder.imgbt_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ForoFragment.dialogoComentarios();

                    posicionlocalc = v.getId();
                    imgbt_comment_g = (ImageButton) v;

                    id_publi = mUsers.get(posicionlocalc).getPublicacion_id();
                    /*Intent i =new Intent(ctx, ComentariosActivity.class);
                    i.putExtra("id_publicacion",id_publi);
                    ctx.startActivity(i);*/


                }
            });

            holder.imgbt_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posicionlocalc = v.getId();
                    imgbt_like_g = (ImageButton) v;
                    nlike_g = holder.txt_totallike;


                    if (mUsers.get(posicionlocalc).getDio_like().equals("0")){
                        darlike(mUsers.get(posicionlocalc).getPublicacion_id());
                        holder.imgbt_like.setBackgroundResource(R.drawable.thumb_up);

                    }else{
                        dislike(mUsers.get(posicionlocalc).getPublicacion_id());
                        holder.imgbt_like.setBackgroundResource(R.drawable.thumb_up_outline);
                    }
                }
            });

            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<PublicacionesTorneo> users){
        mUsers = users;
        mDataFiltered = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }else{
            return  0;
        }

    }

    private void darlike(final String idlike) {
        String url =IP2+"/api/Foro/dar_like";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("darlike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        nlike_g.setText(String.valueOf(totalLikes));
                        mUsers.get(posicionlocalc).setDio_like("1");
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }





                //Toast.makeText(ChoferDatosDeCarrera.this,"No se ha registrado ",Toast.LENGTH_SHORT).show();


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
                parametros.put("usuario_id",preferencesUser.getIdUsuarioPref());
                parametros.put("publicacion_id",idlike);
                parametros.put("app","true");
                parametros.put("token",preferencesUser.getToken());

                return parametros;

            }
        };
        requestQueue.add(stringRequest);
        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(ctx).addToRequestQueue(stringRequest);*/
    }

    RequestQueue requestQueue;
    private void dislike(final String iddislike) {
        String url =IP2+"/api/Foro/quitar_like";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dislike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        nlike_g.setText(String.valueOf(totalLikes));
                        mUsers.get(posicionlocalc).setDio_like("0");
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                parametros.put("usuario_id",preferencesUser.getIdUsuarioPref());
                parametros.put("publicacion_id",iddislike);
                parametros.put("app","true");
                parametros.put("token",preferencesUser.getToken());

                return parametros;

            }
        };
        requestQueue.add(stringRequest);
        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(ctx).addToRequestQueue(stringRequest);*/
    }


    public interface  OnItemClickListener{
        void onItemClick(String dato, PublicacionesTorneo feedTorneo, int position);
    }

}