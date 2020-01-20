package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Views;


import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.ResutadoRetos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorRetos extends RecyclerView.Adapter<AdaptadorRetos.RetosViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Retos current;
    Context ctx;
    private OnItemClickListener listener;
    Preferences preferencesUser;

    int posicionlocalc;


    class RetosViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civ_fotoRetosRetador,civ_fotoRetosRetado;
        private TextView txt_nombreRetosRetador,txt_nombreRetosRetado,txt_fechaRetos,txt_lugarRetos,txt_horaRetos;
        private LinearLayout layoutListRetos;


        private RetosViewHolder(View itemView) {
            super(itemView);


            civ_fotoRetosRetador=  itemView.findViewById(R.id.civ_fotoRetosRetador);
            civ_fotoRetosRetado = itemView.findViewById(R.id.civ_fotoRetosRetado);
            txt_nombreRetosRetador = itemView.findViewById(R.id.txt_nombreRetosRetador);
            txt_nombreRetosRetado = itemView.findViewById(R.id.txt_nombreRetosRetado);
            txt_fechaRetos = itemView.findViewById(R.id.txt_fechaRetos);
            txt_lugarRetos = itemView.findViewById(R.id.txt_lugarRetos);
            txt_horaRetos = itemView.findViewById(R.id.txt_horaRetos);
            layoutListRetos = itemView.findViewById(R.id.layoutListRetos);




        }


        public void bid(final Retos retos, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(retos, getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Retos> mUsers; // Cached copy of users


    public AdaptadorRetos(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public RetosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_retos, parent, false);
        return new RetosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final RetosViewHolder holder, final int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            holder.layoutListRetos.setId(position);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            UniversalImageLoader.setImage(IP2+"/"+ current.getRetos_foto_retador(),holder.civ_fotoRetosRetador,null);
            UniversalImageLoader.setImage(IP2+"/"+ current.getRetos_foto_retado(),holder.civ_fotoRetosRetado,null);


            holder.txt_nombreRetosRetador.setText(current.getRetos_nombre_retador());
            holder.txt_nombreRetosRetado.setText(current.getRetos_nombre_retado());
            holder.txt_horaRetos.setText(current.getRetos_hora());
            holder.txt_fechaRetos.setText(current.getRetos_fecha());
            holder.txt_lugarRetos.setText(current.getRetos_lugar());

            if (current.getRetos_respuesta().equals("1")){

                holder.layoutListRetos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        posicionlocalc = v.getId();

                        Intent intent =  new Intent(ctx, ResutadoRetos.class);
                        intent.putExtra("id_reto",mUsers.get(posicionlocalc).getRetos_id());
                        intent.putExtra("retado",mUsers.get(posicionlocalc).getRetos_nombre_retado());
                        intent.putExtra("retado_id",mUsers.get(posicionlocalc).getRetado_id());
                        intent.putExtra("retado_foto",mUsers.get(posicionlocalc).getRetos_foto_retado());
                        intent.putExtra("retador",mUsers.get(posicionlocalc).getRetos_nombre_retador());
                        intent.putExtra("retador_id",mUsers.get(posicionlocalc).getRetador_id());
                        intent.putExtra("retador_foto",mUsers.get(posicionlocalc).getRetos_foto_retador());
                        ctx.startActivity(intent);
                    }
                });
            }

            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Retos> users){
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
        void onItemClick(Retos retos, int position);
    }

}