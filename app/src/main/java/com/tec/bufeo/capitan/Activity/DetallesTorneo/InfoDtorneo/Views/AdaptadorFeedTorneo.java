package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class AdaptadorFeedTorneo extends RecyclerView.Adapter<AdaptadorFeedTorneo.foroTorneoViewHolder> {



    UniversalImageLoader universalImageLoader;
    StringRequest stringRequest;
    ImageButton imgbt_like_g,imgbt_comment_g;
    TextView nlike_g;
    int totalLikes;
    JSONObject json_data;
    String resultado;
    int posicionlocalc;
    FeedTorneo current;
    Context ctx;
    String id_publi;
    private  OnItemClickListener listener;
    Preferences preferencesUser;



    class foroTorneoViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_fotoForo,foto_perfil_publicacion;
        private TextView txt_tituloForo, txt_usuarioForo, txt_descripcionForo, txt_fechaHora,txt_totallike,txt_totalcoment;
        private ProgressBar prog_like;
        private ImageButton imgbt_like,imgbt_comment;
        FrameLayout frame_mas_contenido,progress_mas_contenido;
        Button btn_mas_contenido;
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
            frame_mas_contenido=itemView.findViewById(R.id.frame_mas_contenido);
            progress_mas_contenido=itemView.findViewById(R.id.progress_mas_contenido);
            foto_perfil_publicacion=itemView.findViewById(R.id.foto_perfil_publicacion);
            btn_mas_contenido=itemView.findViewById(R.id.btn_mas_contenido);
            materialCardView=itemView.findViewById(R.id.materialCardView);
        }

        public void bid(final FeedTorneo feedTorneo, final OnItemClickListener listener){
            btn_mas_contenido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("prueba2", feedTorneo, getAdapterPosition());
                    frame_mas_contenido.setVisibility(View.GONE);
                    progress_mas_contenido.setVisibility(View.VISIBLE);

                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<FeedTorneo> mUsers; // Cached copy of users
    private List<FeedTorneo> mDataFiltered; // Cached copy of users


    public AdaptadorFeedTorneo(Context context, OnItemClickListener listener) {
        requestQueue = Volley.newRequestQueue(context);
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
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

            if(position == mUsers.size()-1){
                holder.materialCardView.setVisibility(View.GONE);
            }
            holder.imgbt_like.setId(position);
            holder.imgbt_comment.setId(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            holder.txt_tituloForo.setText(current.getForo_titulo());
            holder.txt_usuarioForo.setText(current.getUsuario_nombre());
            holder.txt_descripcionForo.setText(current.getForo_descripcion());
            holder.txt_fechaHora.setText("Hace: "+current.getForo_feccha());
            holder.txt_totalcoment.setText(current.getCant_Comentarios());

            holder.txt_totallike.setText(current.getCant_likes());

            if(current.getDio_like().equals("0")){
                holder.imgbt_like.setImageResource(R.drawable.thumb_up_outline);
            }else {
                holder.imgbt_like.setImageResource(R.drawable.thumb_up);
            }
            UniversalImageLoader.setImage(IP+"/"+ current.getForo_foto(),holder.img_fotoForo,null);
            UniversalImageLoader.setImage(IP+"/"+ current.getUsuario_foto(),holder.foto_perfil_publicacion,null);
            //Picasso.with(ctx).load("http://"+IP+"/"+ current.getForo_foto()).into(holder.img_fotoForo);



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
    public void setWords(List<FeedTorneo> users){
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
        String url =IP+"/index.php?c=Foro&a=dar_like&key_mobile=123456asdfgh";
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
        String url =IP+"/index.php?c=Foro&a=quitar_like&key_mobile=123456asdfgh";
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

                return parametros;

            }
        };
        requestQueue.add(stringRequest);
        /*stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(ctx).addToRequestQueue(stringRequest);*/
    }


    public interface  OnItemClickListener{
        void onItemClick(String dato, FeedTorneo feedTorneo, int position);
    }

}