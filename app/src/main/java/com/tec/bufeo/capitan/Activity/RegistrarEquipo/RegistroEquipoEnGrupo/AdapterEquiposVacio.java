package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterEquiposVacio extends RecyclerView.Adapter<AdapterEquiposVacio.EquiposSeleccionarViewHolder>  {


    BusquedaEquipos current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class EquiposSeleccionarViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_fotoEquipo;
        private CardView cardEquipos_vacio;
        private TextView nombreEquipo,capitanEquipo;

        private EquiposSeleccionarViewHolder(View itemView) {
            super(itemView);

            cardEquipos_vacio=  itemView.findViewById(R.id.cardEquipos_vacio);
            civ_fotoEquipo=  itemView.findViewById(R.id.civ_fotoEquipo);
            nombreEquipo = itemView.findViewById(R.id.nombreEquipo);
            capitanEquipo = itemView.findViewById(R.id.capitanEquipo);


        }

        public void bid(final BusquedaEquipos mequipos,final OnItemClickListener listener){

            cardEquipos_vacio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    listener.onItemClick(mequipos,"cardEquipos_vacio", getAdapterPosition());

                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<BusquedaEquipos> mUsers; // Cached copy of users


    public AdapterEquiposVacio(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public EquiposSeleccionarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_equipos_seleccionar, parent, false);
        return new EquiposSeleccionarViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposSeleccionarViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);


            Glide.with(ctx).load(IP2+"/"+ current.getEquipo_foto()).into(holder.civ_fotoEquipo);

            holder.nombreEquipo.setText(current.getEquipo_nombre());
            holder.capitanEquipo.setText(current.getCapitan_nombre());



            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<BusquedaEquipos> users){
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
        void onItemClick(BusquedaEquipos mequipos ,String tipo, int position);
    }



}