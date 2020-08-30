package com.tec.bufeo.capitan.Activity.MisReservas.Views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.Activity.DetalleReservaEmpresa;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.DetalleReservas;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdaptadorDetalleMisReservas extends RecyclerView.Adapter<AdaptadorDetalleMisReservas.DetallesMisReservasViewHolder>  {


    private List<DetalleReservas> tablaTorneoSubItems;
    Context ctx;

    //Preferences preferencesUser;

    public AdaptadorDetalleMisReservas(Context context, List<DetalleReservas> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
    }
    class DetallesMisReservasViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre_reserva_reserva,monto_final_reserva,empresa_reserva,nombre_cancha_reserva,horaReserva,fechaReserva;
        private MaterialButton verMas;

        private DetallesMisReservasViewHolder(View itemView) {
            super(itemView);

            nombre_reserva_reserva=  itemView.findViewById(R.id.nombre_reserva_reserva);
            monto_final_reserva = itemView.findViewById(R.id.monto_final_reserva);
            empresa_reserva = itemView.findViewById(R.id.empresa_reserva);
            nombre_cancha_reserva = itemView.findViewById(R.id.nombre_cancha_reserva);
            horaReserva = itemView.findViewById(R.id.horaReserva);
            fechaReserva = itemView.findViewById(R.id.fechaReserva);
            verMas = itemView.findViewById(R.id.verMas);
        }


    }






    @NonNull
    @Override
    public DetallesMisReservasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_detalle_mis_reservas, parent, false);
        return new DetallesMisReservasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final DetallesMisReservasViewHolder holder, int position) {

        final DetalleReservas current = tablaTorneoSubItems.get(position);

            float totalex;

            if (Float.parseFloat(current.getReserva_pago2())>0){

                totalex= Float.parseFloat(current.getReserva_pago1()) + Float.parseFloat(current.getReserva_pago2());
                holder.monto_final_reserva.setText(String.valueOf(totalex));
            }else{
                holder.monto_final_reserva.setText(current.getPago_total());
            }
            holder.nombre_reserva_reserva.setText(current.getReserva_nombre());
            holder.empresa_reserva.setText(current.getEmpresa_nombre());
            holder.nombre_cancha_reserva.setText(current.getCancha_nombre());
            holder.horaReserva.setText(current.getReserva_hora());
            holder.fechaReserva.setText(current.getReserva_fecha());


            holder.verMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetalleReservaEmpresa.class);
                    i.putExtra("id",current.getId_reserva());
                    i.putExtra("fecha",current.getPago_date());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(i);
                }
            });

    }


    @Override
    public int getItemCount() {

            return tablaTorneoSubItems.size();

    }




}