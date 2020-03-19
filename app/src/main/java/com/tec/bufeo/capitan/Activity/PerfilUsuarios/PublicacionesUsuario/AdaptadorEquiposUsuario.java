package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorEquiposUsuario extends RecyclerView.Adapter<AdaptadorEquiposUsuario.EquiposUsuarioViewHolder>  {

    EquiposUsuarios current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;
    UniversalImageLoader universalImageLoader;


    class EquiposUsuarioViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_fotoEquipoUsuarios,capitan,camiseta;
        private TextView txt_nombreEquipoUsuarios;

        private EquiposUsuarioViewHolder(View itemView) {
            super(itemView);

            img_fotoEquipoUsuarios= (ImageView) itemView.findViewById(R.id.img_fotoEquipoUsuarios);
            capitan= (ImageView) itemView.findViewById(R.id.capitan);
            camiseta= (ImageView) itemView.findViewById(R.id.camiseta);
            txt_nombreEquipoUsuarios = (TextView) itemView.findViewById(R.id.txt_nombreEquipoUsuarios);

        }

        public void bid(final EquiposUsuarios mequipos,final OnItemClickListener listener){
            img_fotoEquipoUsuarios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*listener.onItemClick(mequipos , getAdapterPosition());*/
                    listener.onItemClick(mequipos,"img_fotoEquipoUsuarios",getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<EquiposUsuarios> mUsers; // Cached copy of users


    public AdaptadorEquiposUsuario(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public EquiposUsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_equipos_usuarios, parent, false);
        return new EquiposUsuarioViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposUsuarioViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            if (current.getCapitan_id().equals(preferencesUser.getIdUsuarioPref())){
                holder.camiseta.setVisibility(View.GONE);
            }else{
                holder.capitan.setVisibility(View.GONE);
            }

            UniversalImageLoader.setImage(IP2+"/"+ current.getFoto(),holder.img_fotoEquipoUsuarios,null);

            holder.txt_nombreEquipoUsuarios.setText(current.getNombre());





            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<EquiposUsuarios> users){
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
        void onItemClick(EquiposUsuarios mequipos,String tipo, int position);
    }

}