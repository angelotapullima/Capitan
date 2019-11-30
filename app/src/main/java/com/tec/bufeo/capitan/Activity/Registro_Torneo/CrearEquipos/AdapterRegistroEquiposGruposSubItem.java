package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.TablaDtorneo.Models.TablaTorneoSubItem;
import com.tec.bufeo.capitan.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRegistroEquiposGruposSubItem extends RecyclerView.Adapter<AdapterRegistroEquiposGruposSubItem.SubItemViewHolder>  {

    private List<TablaTorneoSubItem> tablaTorneoSubItems;
    Context ctx;

    public AdapterRegistroEquiposGruposSubItem(Context context, List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        private TextView posicion_equipo_lista,nombre_equipo_lista;


        private SubItemViewHolder(View itemView) {


            super(itemView);
            posicion_equipo_lista =  itemView.findViewById(R.id.posicion_equipo_lista);
            nombre_equipo_lista =  itemView.findViewById(R.id.nombre_equipo_lista);




        }


    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_equipo_grupo_subitem, parent, false);
        return new SubItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder holder, int position) {

        TablaTorneoSubItem torneoSubItem = tablaTorneoSubItems.get(position);



        holder.posicion_equipo_lista.setText(torneoSubItem.getPosicion_lista());
        holder.nombre_equipo_lista.setText(torneoSubItem.getEquipo_nombre());

        //UniversalImageLoader.setImage(IP+"/"+current.getFoto(),holder.logoEquipo,null);


    }

    @Override
    public int getItemCount() {

        return  tablaTorneoSubItems.size();

    }



}