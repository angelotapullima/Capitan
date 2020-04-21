package com.tec.bufeo.capitan.Activity.MisMovimientos.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdaptadorMovimientos extends RecyclerView.Adapter<AdaptadorMovimientos.MovimientosItemViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Movimientos> tablaTorneoItems;
    Context ctx;
    private  OnItemClickListener listener;


    public AdaptadorMovimientos(Context context, List<Movimientos> tablaTorneoItems, OnItemClickListener listener) {
        this.ctx=context;
        this.tablaTorneoItems=tablaTorneoItems;
        this.listener=listener;
    }

    class MovimientosItemViewHolder extends RecyclerView.ViewHolder {
        private TextView fecha_cabecera;
        private RecyclerView rcv_detalle_movimientos;


        private MovimientosItemViewHolder(View itemView) {


            super(itemView);
            rcv_detalle_movimientos =  itemView.findViewById(R.id.rcv_detalle_movimientos);
            fecha_cabecera =  itemView.findViewById(R.id.fecha_cabecera);

        }

        public void bid(final Movimientos movimientos,final OnItemClickListener listener){
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
    public MovimientosItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_movimientos, parent, false);
        return new MovimientosItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovimientosItemViewHolder holder, int position) {


        Movimientos tablaTorneoItem = tablaTorneoItems.get(position);


        holder.fecha_cabecera.setText(tablaTorneoItem.getFecha());



        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcv_detalle_movimientos.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getDetalle_movimientos().size());

        // Create sub item view adapter
        AdaptadorDetalleMovimientos adapterColaboracionesSubItem = new AdaptadorDetalleMovimientos(ctx,tablaTorneoItem.getDetalle_movimientos());
        //AdapterSubItemTablaTorneo adapterSubItemTablaTorneo =  new AdapterSubItemTablaTorneo(ctx,tablaTorneoItem.getTablaTorneoSubItems());

        holder.rcv_detalle_movimientos.setLayoutManager(layoutManager);
        holder.rcv_detalle_movimientos.setAdapter(adapterColaboracionesSubItem);
        holder.rcv_detalle_movimientos.setRecycledViewPool(viewPool);




        holder.bid(tablaTorneoItem,listener);


    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(Movimientos mequipos, String tipo, int position);
    }

}