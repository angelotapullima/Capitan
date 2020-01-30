package com.tec.bufeo.capitan.Activity.RegistroReserva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterColaboracionesItem extends RecyclerView.Adapter<AdapterColaboracionesItem.ColaboracionesViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Colaboraciones> tablaTorneoItems;
    Context ctx;
    private  OnItemClickListener listener;


    public AdapterColaboracionesItem(Context context, List<Colaboraciones> tablaTorneoItems,OnItemClickListener listener) {
        this.ctx=context;
        this.tablaTorneoItems=tablaTorneoItems;
        this.listener=listener;
    }

    class ColaboracionesViewHolder extends RecyclerView.ViewHolder {
        private TextView equipo_chancha,nombre_chancha,fecha_chancha,monto_final;
        private RecyclerView rcv_colaboraciones_detalle;
        private LinearLayout btn_reservar_en_chancha;


        private ColaboracionesViewHolder(View itemView) {


            super(itemView);
            equipo_chancha =  itemView.findViewById(R.id.equipo_chancha);
            nombre_chancha =  itemView.findViewById(R.id.nombre_chancha);
            fecha_chancha =  itemView.findViewById(R.id.fecha_chancha);
            monto_final =  itemView.findViewById(R.id.monto_final);
            btn_reservar_en_chancha =  itemView.findViewById(R.id.btn_reservar_en_chancha);
            rcv_colaboraciones_detalle =  itemView.findViewById(R.id.rcv_colaboraciones_detalle);


        }

        public void bid(final Colaboraciones mequipos,final OnItemClickListener listener){
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_colaboraciones, parent, false);
        return new ColaboracionesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ColaboracionesViewHolder holder, int position) {


        Colaboraciones tablaTorneoItem = tablaTorneoItems.get(position);


        holder.nombre_chancha.setText(tablaTorneoItem.getColaboracion_nombre());
        holder.equipo_chancha.setText(tablaTorneoItem.getEquipo_nombre());
        holder.fecha_chancha.setText(tablaTorneoItem.getColaboracion_date());
        holder.monto_final.setText(tablaTorneoItem.getMonto_final());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcv_colaboraciones_detalle.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getDetalleColaboracionesList().size());

        // Create sub item view adapter
        AdapterColaboracionesSubItem adapterColaboracionesSubItem = new AdapterColaboracionesSubItem(ctx,tablaTorneoItem.getDetalleColaboracionesList());
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
        void onItemClick(Colaboraciones mequipos,String tipo, int position);
    }

}