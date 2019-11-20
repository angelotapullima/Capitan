package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.others.Equipo;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class AdaptadorListadoEquipoTorneo extends RecyclerView.Adapter<AdaptadorListadoEquipoTorneo.EquiposViewHolder> {

    private ArrayList<Equipo> array;
    private int layoutpadre;
    Context context;
    Equipo obj;

    public AdaptadorListadoEquipoTorneo() {
    }

    public AdaptadorListadoEquipoTorneo(Context context, ArrayList<Equipo> array, int layoutpadre) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
    }

    public class EquiposViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView civ_fotoEquipo;
        private TextView txt_nombreEquipo, txt_capitan;


        public EquiposViewHolder(View itemView) {
            super(itemView);

            civ_fotoEquipo=  itemView.findViewById(R.id.civ_fotoEquipo);
            txt_nombreEquipo =  itemView.findViewById(R.id.txt_nombreEquipo);
            txt_capitan =  itemView.findViewById(R.id.txt_capitan);
        }

    }

    @Override
    public EquiposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new EquiposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EquiposViewHolder holder, int position) {

        obj = array.get(position);

        Picasso.with(context).load(IP+"/"+ obj.getEquipo_foto()).into(holder.civ_fotoEquipo);
        holder.txt_nombreEquipo.setText(obj.getEquipo_nombre());
        holder.txt_capitan.setText(obj.getUsuario_nombre());
            }

    @Override
    public int getItemCount() {
        return array.size();
    }

}