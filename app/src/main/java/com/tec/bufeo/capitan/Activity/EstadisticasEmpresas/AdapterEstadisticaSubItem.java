package com.tec.bufeo.capitan.Activity.EstadisticasEmpresas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.DetalleEstadisticasEmpresa;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdapterEstadisticaSubItem extends RecyclerView.Adapter<AdapterEstadisticaSubItem.EstadisticasSubItemViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<DetalleEstadisticasEmpresa> tablaTorneoItems;
    Context ctx;
    private  OnItemClickListener listener;


    public AdapterEstadisticaSubItem(Context context, List<DetalleEstadisticasEmpresa> tablaTorneoItems,OnItemClickListener listener) {
        this.ctx=context;
        this.tablaTorneoItems=tablaTorneoItems;
        this.listener=listener;
    }

    class EstadisticasSubItemViewHolder extends RecyclerView.ViewHolder {
        private TextView cancha,monto_cancha;
        private RecyclerView rcv_item_estadistica_subItem;
        private View ViewCancha,ViewCanchaAbajo;
        private LinearLayout layout_cancha;


        private EstadisticasSubItemViewHolder(View itemView) {


            super(itemView);
            cancha =  itemView.findViewById(R.id.cancha);
            layout_cancha =  itemView.findViewById(R.id.layout_cancha);
            monto_cancha =  itemView.findViewById(R.id.monto_cancha);
            ViewCancha =  itemView.findViewById(R.id.ViewCancha);
            ViewCanchaAbajo =  itemView.findViewById(R.id.ViewCanchaAbajo);
            rcv_item_estadistica_subItem =  itemView.findViewById(R.id.rcv_item_estadistica_subItem);


        }

        public void bid(final DetalleEstadisticasEmpresa mequipos, final OnItemClickListener listener){


        }

    }

    @NonNull
    @Override
    public EstadisticasSubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_estadistica_subitem, parent, false);
        return new EstadisticasSubItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EstadisticasSubItemViewHolder holder, int position) {


        DetalleEstadisticasEmpresa tablaTorneoItem = tablaTorneoItems.get(position);

        if (tablaTorneoItems.get(position).getDetalleEstadisticasEmpresas().size()>0){
            holder.cancha.setText(tablaTorneoItems.get(position).getCancha_nombre());
            holder.monto_cancha.setText(tablaTorneoItems.get(position).getMonto_cancha());
            holder.setIsRecyclable(false);

        /*holder.nombre_chancha.setText(tablaTorneoItem.getColaboracion_nombre());
        holder.equipo_chancha.setText(tablaTorneoItem.getEquipo_nombre());
        holder.fecha_chancha.setText(tablaTorneoItem.getColaboracion_date());
        holder.monto_final.setText(tablaTorneoItem.getMonto_final());*/

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    holder.rcv_item_estadistica_subItem.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getDetalleEstadisticasEmpresas().size());

            // Create sub item view adapter

            AdapterDetalleEstadisticasEmpresa adapterDetalleEstadisticasEmpresa = new AdapterDetalleEstadisticasEmpresa(ctx, tablaTorneoItem.getDetalleEstadisticasEmpresas());



            holder.rcv_item_estadistica_subItem.setLayoutManager(layoutManager);
            holder.rcv_item_estadistica_subItem.setAdapter(adapterDetalleEstadisticasEmpresa);
            holder.rcv_item_estadistica_subItem.setRecycledViewPool(viewPool);
        }else{
            holder.rcv_item_estadistica_subItem.setVisibility(View.GONE);
            holder.cancha.setVisibility(View.GONE);
            holder.ViewCancha.setVisibility(View.GONE);
            holder.ViewCanchaAbajo.setVisibility(View.GONE);
            holder.layout_cancha.setVisibility(View.GONE);
        }






        holder.bid(tablaTorneoItem,listener);


    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(DetalleEstadisticasEmpresa mequipos,String tipo, int position);
    }

}