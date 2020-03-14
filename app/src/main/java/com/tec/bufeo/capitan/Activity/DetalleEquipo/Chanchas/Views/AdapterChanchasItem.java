package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdapterChanchasItem extends RecyclerView.Adapter<AdapterChanchasItem.ColaboracionesViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Chanchas> tablaTorneoItems;
    Context ctx;
    private  OnItemClickListener listener;


    public AdapterChanchasItem(Context context, List<Chanchas> tablaTorneoItems, OnItemClickListener listener) {
        this.ctx=context;
        this.tablaTorneoItems=tablaTorneoItems;
        this.listener=listener;
    }

    class ColaboracionesViewHolder extends RecyclerView.ViewHolder {
        private TextView equipo_chancha,nombre_chancha,fecha_chancha,monto_final;
        private RecyclerView rcv_colaboraciones_detalle;
        private MaterialButton btn_reservar_en_chancha;


        private ColaboracionesViewHolder(View itemView) {


            super(itemView);
            equipo_chancha =  itemView.findViewById(R.id.equipo_chancha);
            nombre_chancha =  itemView.findViewById(R.id.nombre_chancha);
            fecha_chancha =  itemView.findViewById(R.id.fecha_chancha);
            monto_final =  itemView.findViewById(R.id.monto_final);
            btn_reservar_en_chancha =  itemView.findViewById(R.id.btn_reservar_en_chancha);
            rcv_colaboraciones_detalle =  itemView.findViewById(R.id.rcv_colaboraciones_detalle);


        }

        public void bid(final Chanchas mequipos,final OnItemClickListener listener){
            btn_reservar_en_chancha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"btn_reservar_en_chancha",getAdapterPosition());
                }
            });

        }

    }

    @NonNull
    @Override
    public ColaboracionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_chanchas, parent, false);
        return new ColaboracionesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ColaboracionesViewHolder holder, int position) {


        Chanchas tablaTorneoItem = tablaTorneoItems.get(position);


        holder.nombre_chancha.setText(tablaTorneoItem.getNombre_chancha());
        holder.equipo_chancha.setText(tablaTorneoItem.getEquipo_nombre());
        holder.fecha_chancha.setText(tablaTorneoItem.getChancha_fecha());
        holder.monto_final.setText(tablaTorneoItem.getMonto_actual());

        if (Double.parseDouble( tablaTorneoItem.getMonto_actual())>=Double.parseDouble(tablaTorneoItem.getMonto_final())){
            holder.btn_reservar_en_chancha.setBackgroundColor(Color.RED);
            holder.btn_reservar_en_chancha.setText("Chancha Completa");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcv_colaboraciones_detalle.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getChancha_detalle().size());

        // Create sub item view adapter
        AdapterChanchasSubItem adapterColaboracionesSubItem = new AdapterChanchasSubItem(ctx,tablaTorneoItem.getChancha_detalle());
        //AdapterSubItemTablaTorneo adapterSubItemTablaTorneo =  new AdapterSubItemTablaTorneo(ctx,tablaTorneoItem.getTablaTorneoSubItems());

        holder.rcv_colaboraciones_detalle.setLayoutManager(layoutManager);
        holder.rcv_colaboraciones_detalle.setAdapter(adapterColaboracionesSubItem);
        holder.rcv_colaboraciones_detalle.setRecycledViewPool(viewPool);




        holder.bid(tablaTorneoItem,listener);


    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(Chanchas mequipos, String tipo, int position);
    }

}