package com.tec.bufeo.capitan.Activity.RegistroReserva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec.bufeo.capitan.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterColaboracionesSubItem extends RecyclerView.Adapter<AdapterColaboracionesSubItem.DetalleColaboracionesViewHolder>  {

    private List<DetalleColaboraciones> tablaTorneoSubItems;
    Context ctx;

    public AdapterColaboracionesSubItem(Context context, List<DetalleColaboraciones> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
    }

    class DetalleColaboracionesViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_colaborador,colaboracion_monto_detalle;


        private DetalleColaboracionesViewHolder(View itemView) {


            super(itemView);
            nombre_colaborador =  itemView.findViewById(R.id.nombre_colaborador);
            colaboracion_monto_detalle =  itemView.findViewById(R.id.colaboracion_monto_detalle);



        }


    }

    @NonNull
    @Override
    public DetalleColaboracionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_colaboraciones_detalle, parent, false);
        return new DetalleColaboracionesViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final DetalleColaboracionesViewHolder holder, int position) {

        DetalleColaboraciones detalleColaboraciones = tablaTorneoSubItems.get(position);




        holder.colaboracion_monto_detalle.setText(detalleColaboraciones.getColaboracion_monto_detalle());
        holder.nombre_colaborador.setText(detalleColaboraciones.getNombre_colaborador());


    }

    @Override
    public int getItemCount() {

        return  tablaTorneoSubItems.size();

    }



}