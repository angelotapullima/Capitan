package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Views.ComentariosActivity;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorForo extends RecyclerView.Adapter<AdaptadorForo.foroViewHolder>  {

    UniversalImageLoader universalImageLoader;
    LinearLayout imgbt_comment_g;
    JSONObject json_data;
    String resultado;
    int posicionlocalc;
    ModelFeed current;
    Context ctx;
    String id_publi;
    private  OnItemClickListener listener;
    Preferences preferencesUser;



    class foroViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_fotoForo,foto_perfil_publicacion;
        private TextView txt_tituloForo, txt_usuarioForo, txt_descripcionForo, txt_fechaHora,txt_totallike,txt_totalcoment;
        private ImageButton imgbt_like,btnAccion;
        private LinearLayout layout_comentar,layout_like;
        private Button verMasTorneo;
        CardView materialCardView;

        private foroViewHolder(View itemView) {
            super(itemView);
            img_fotoForo=  itemView.findViewById(R.id.img_fotoForo);
            txt_tituloForo=  itemView.findViewById(R.id.txt_tituloForo);
            txt_usuarioForo=  itemView.findViewById(R.id.txt_usuarioForo);
            txt_descripcionForo=  itemView.findViewById(R.id.txt_descripcionForo);
            txt_fechaHora = itemView.findViewById(R.id.txt_fechaHora);
            imgbt_like = itemView.findViewById(R.id.imgbt_like);
            layout_comentar = itemView.findViewById(R.id.layout_comentar);
            layout_like = itemView.findViewById(R.id.layout_like);
            txt_totallike = itemView.findViewById(R.id.txt_totallike);
            btnAccion=itemView.findViewById(R.id.btnAccion);
            txt_totalcoment=itemView.findViewById(R.id.txt_totalcoment);
            foto_perfil_publicacion=itemView.findViewById(R.id.foto_perfil_publicacion);
            materialCardView=itemView.findViewById(R.id.materialCardView);
            verMasTorneo=itemView.findViewById(R.id.verMasTorneo);
        }

        public void bid(final ModelFeed feedTorneo, final OnItemClickListener listener){


            btnAccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("btnAccion",feedTorneo,getAdapterPosition());
                }
            });
            verMasTorneo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("verMasTorneo",feedTorneo,getAdapterPosition());
                }
            });

            foto_perfil_publicacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("foto_perfil_publicacion",feedTorneo,getAdapterPosition());
                }
            });

            txt_usuarioForo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("txt_usuarioForo",feedTorneo,getAdapterPosition());
                }
            });

            img_fotoForo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("img_fotoForo",feedTorneo,getAdapterPosition());
                }
            });

            imgbt_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (feedTorneo.getDio_like().equals("0")){
                        imgbt_like.setBackgroundResource(R.drawable.thumb_up);

                    }else{

                        imgbt_like.setBackgroundResource(R.drawable.thumb_up_outline);
                    }
                    listener.onItemClick("imgbt_like",feedTorneo,getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<ModelFeed> mUsers; // Cached copy of users
    private List<ModelFeed> mDataFiltered; // Cached copy of users


    public AdaptadorForo(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public foroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_card_foro4, parent, false);
        return new foroViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull final foroViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);


            holder.layout_like.setId(position);
            holder.layout_comentar.setId(position);

            if (current.getUsuario_id().equals(preferencesUser.getIdUsuarioPref())){
                holder.btnAccion.setVisibility(View.VISIBLE);
                holder.verMasTorneo.setVisibility(View.GONE);

            }else{
                holder.btnAccion.setVisibility(View.GONE);

                if(current.getId_torneo().equals("0")){
                    holder.verMasTorneo.setVisibility(View.GONE);
                }else{
                    holder.verMasTorneo.setVisibility(View.VISIBLE);
                }
            }



            ImageLoader.getInstance().init(universalImageLoader.getConfig());


            if(current.getId_torneo().equals("0")){
                holder.txt_usuarioForo.setText(current.getUsuario_nombre());
                UniversalImageLoader.setImage(IP2+"/"+ current.getUsuario_foto(),holder.foto_perfil_publicacion,null);
            }else{

                holder.txt_usuarioForo.setText(current.getPublicacion_torneo());
                UniversalImageLoader.setImage(IP2+"/"+ current.getTorneo_foto(),holder.foto_perfil_publicacion,null);

            }


            holder.txt_tituloForo.setText(current.getForo_titulo());
            holder.txt_descripcionForo.setText(current.getForo_descripcion());
            holder.txt_fechaHora.setText("Hace: "+current.getForo_feccha());
            holder.txt_totalcoment.setText(current.getCant_Comentarios());

            holder.txt_totallike.setText(current.getCant_likes());

            if(current.getDio_like().equals("0")){
                holder.imgbt_like.setImageResource(R.drawable.thumb_up_outline);
            }else {
                holder.imgbt_like.setImageResource(R.drawable.thumb_up);
            }

            UniversalImageLoader.setImage(IP2+"/"+ current.getForo_foto(),holder.img_fotoForo,null);

            //holder.imgbt_comment.setOnClickListener(this);
            holder.layout_comentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ForoFragment.dialogoComentarios();

                    posicionlocalc = v.getId();
                    imgbt_comment_g = (LinearLayout) v;

                    id_publi = mUsers.get(posicionlocalc).getPublicacion_id();
                    Intent i =new Intent(ctx, ComentariosActivity.class);
                    i.putExtra("id_publicacion",id_publi);
                    ctx.startActivity(i);


                }
            });



            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<ModelFeed> users){
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




    public interface  OnItemClickListener{
        void onItemClick(String dato, ModelFeed feedTorneo, int position);
    }

}