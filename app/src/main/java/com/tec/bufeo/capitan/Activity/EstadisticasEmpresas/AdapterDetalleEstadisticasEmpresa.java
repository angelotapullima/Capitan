package com.tec.bufeo.capitan.Activity.EstadisticasEmpresas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.DetalleEstadisticasEmpresa;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdapterDetalleEstadisticasEmpresa extends RecyclerView.Adapter<AdapterDetalleEstadisticasEmpresa.DetalleEstadisticasViewHolder>  {

    private List<DetalleEstadisticasEmpresa> tablaTorneoSubItems;
    Context ctx;

    public AdapterDetalleEstadisticasEmpresa(Context context, List<DetalleEstadisticasEmpresa> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
    }

    class DetalleEstadisticasViewHolder extends RecyclerView.ViewHolder {
        private TextView hora_detalle,nombre_detalle,monto_detalle,origen_tipo_pago;


        private DetalleEstadisticasViewHolder(View itemView) {


            super(itemView);
            monto_detalle =  itemView.findViewById(R.id.monto_detalle);
            hora_detalle =  itemView.findViewById(R.id.hora_detalle);
            nombre_detalle =  itemView.findViewById(R.id.nombre_detalle);
            origen_tipo_pago =  itemView.findViewById(R.id.origen_tipo_pago);



        }


    }

    @NonNull
    @Override
    public DetalleEstadisticasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_estadistica_detalle, parent, false);
        return new DetalleEstadisticasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final DetalleEstadisticasViewHolder holder, int position) {

        DetalleEstadisticasEmpresa detalleColaboraciones = tablaTorneoSubItems.get(position);

        if (tablaTorneoSubItems.size()>0){
            holder.setIsRecyclable(false);
            holder.nombre_detalle.setText(detalleColaboraciones.getReserva_nombre());
            holder.hora_detalle.setText(detalleColaboraciones.getReserva_hora());
            holder.monto_detalle.setText(detalleColaboraciones.getReserva_pago1());

            if (detalleColaboraciones.getReserva_tipopago().equals("0")){
                holder.origen_tipo_pago.setText("Local");
            }else{
                holder.origen_tipo_pago.setText("App");
            }

        }




        /*holder.colaboracion_monto_detalle.setText(detalleColaboraciones.getColaboracion_monto_detalle());
        holder.nombre_colaborador.setText(detalleColaboraciones.getNombre_colaborador());*/


    }

    @Override
    public int getItemCount() {

        return  tablaTorneoSubItems.size();

    }



}