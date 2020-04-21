package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorReseñas extends RecyclerView.Adapter<AdaptadorReseñas.ReseñasViewHolder>  {


    Reseñas current;
    Context ctx;
    private  OnItemClickListener listener;
    UniversalImageLoader universalImageLoader;
    //Preferences preferencesUser;


    class ReseñasViewHolder extends RecyclerView.ViewHolder {

        private TextView usuario_reseña,reseña_comentario_empresa;
        private CircleImageView imagen_user_reseña;
        private RatingBar reseña_empresa_valor;

        private ReseñasViewHolder(View itemView) {
            super(itemView);

            imagen_user_reseña=  itemView.findViewById(R.id.imagen_user_reseña);
            usuario_reseña = itemView.findViewById(R.id.usuario_reseña);
            reseña_empresa_valor = itemView.findViewById(R.id.reseña_empresa_valor);
            reseña_comentario_empresa = itemView.findViewById(R.id.reseña_comentario_empresa);
        }

        public void bid(final Reseñas misReservas, final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(misReservas,"verMas",getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater mInflater;

    private List<Reseñas> mUsers; // Cached copy of users


    public AdaptadorReseñas(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        //preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public ReseñasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_resenas, parent, false);
        return new ReseñasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ReseñasViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);


            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            UniversalImageLoader.setImage(IP2+"/"+ current.getUser_image(),holder.imagen_user_reseña,null);
            holder.reseña_comentario_empresa.setText(current.getRating_empresa_comentario());
            holder.usuario_reseña.setText(current.getUser_nickname());
            holder.reseña_empresa_valor.setRating(Float.parseFloat(current.getRating_empresa_valor()));



            holder.bid(current,listener);
        }
    }
    public void setWords(List<Reseñas> users){
        mUsers = users;
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
        void onItemClick(Reseñas mequipos, String tipo, int position);
    }

}