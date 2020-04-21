package com.tec.bufeo.capitan.Activity.MisReservas.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdaptadorMisReservas extends RecyclerView.Adapter<AdaptadorMisReservas.MisReservasItemViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<MisReservas> tablaTorneoItems;
    Context ctx;
    private  OnItemClickListener listener;


    public AdaptadorMisReservas(Context context, List<MisReservas> tablaTorneoItems, OnItemClickListener listener) {
        this.ctx=context;
        this.tablaTorneoItems=tablaTorneoItems;
        this.listener=listener;
    }

    class MisReservasItemViewHolder extends RecyclerView.ViewHolder {
        private TextView fecha_m_reservas;
        private RecyclerView rcv_detalle_m_reservas;


        private MisReservasItemViewHolder(View itemView) {


            super(itemView);
            rcv_detalle_m_reservas =  itemView.findViewById(R.id.rcv_detalle_m_reservas);
            fecha_m_reservas =  itemView.findViewById(R.id.fecha_m_reservas);

        }

        public void bid(final MisReservas movimientos,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(movimientos,"btn_reservar_en_chancha",getAdapterPosition());
                }
            });

        }

    }

    @NonNull
    @Override
    public MisReservasItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_mis_reservas, parent, false);
        return new MisReservasItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MisReservasItemViewHolder holder, int position) {


        MisReservas tablaTorneoItem = tablaTorneoItems.get(position);


        holder.fecha_m_reservas.setText(tablaTorneoItem.getFecha_reserva());



        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcv_detalle_m_reservas.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getDetalle_reservas().size());

        // Create sub item view adapter
        AdaptadorDetalleMisReservas adapterColaboracionesSubItem = new AdaptadorDetalleMisReservas(ctx,tablaTorneoItem.getDetalle_reservas());
        //AdapterSubItemTablaTorneo adapterSubItemTablaTorneo =  new AdapterSubItemTablaTorneo(ctx,tablaTorneoItem.getTablaTorneoSubItems());

        holder.rcv_detalle_m_reservas.setLayoutManager(layoutManager);
        holder.rcv_detalle_m_reservas.setAdapter(adapterColaboracionesSubItem);
        holder.rcv_detalle_m_reservas.setRecycledViewPool(viewPool);




        holder.bid(tablaTorneoItem,listener);


    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(MisReservas mequipos, String tipo, int position);
    }

}