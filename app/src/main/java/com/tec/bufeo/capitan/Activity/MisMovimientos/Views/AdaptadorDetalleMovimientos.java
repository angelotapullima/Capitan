package com.tec.bufeo.capitan.Activity.MisMovimientos.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.DetalleMovimientosActivity;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.DetalleMovimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdaptadorDetalleMovimientos extends RecyclerView.Adapter<AdaptadorDetalleMovimientos.DetalleMovViewHolder>  {


    private List<DetalleMovimientos> tablaTorneoSubItems;
    Context ctx;

    public AdaptadorDetalleMovimientos(Context context, List<DetalleMovimientos> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
    }



    class DetalleMovViewHolder extends RecyclerView.ViewHolder {

        private TextView mov_monto,mov_nombre,mov_fecha;
        private ImageView ic_add,ic_remove;
        private LinearLayout detalleM;

        private DetalleMovViewHolder(View itemView) {
            super(itemView);

            mov_monto=  itemView.findViewById(R.id.mov_monto);
            mov_nombre = itemView.findViewById(R.id.mov_nombre);
            mov_fecha = itemView.findViewById(R.id.mov_fecha);
            ic_add = itemView.findViewById(R.id.ic_add);
            ic_remove = itemView.findViewById(R.id.ic_remove);
            detalleM = itemView.findViewById(R.id.detalleM);

        }


    }



    @NonNull
    @Override
    public DetalleMovViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_detalle_movimientos, parent, false);
        return new DetalleMovViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final DetalleMovViewHolder holder, int position) {

        final DetalleMovimientos current = tablaTorneoSubItems.get(position);



            holder.mov_fecha.setText(current.getFecha());
            holder.mov_monto.setText(current.getMonto());
            holder.mov_nombre.setText(current.getConcepto());

            if (current.getInd()==0){
                holder.ic_add.setVisibility(View.GONE);
                holder.mov_monto.setTextColor(Color.RED);
            }else{
                holder.ic_remove.setVisibility(View.GONE);
                holder.mov_monto.setTextColor(Color.rgb(76,175,80));



            }

            holder.detalleM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetalleMovimientosActivity.class);
                    i.putExtra("nro_operacion",current.getNro_operacion());
                    i.putExtra("concepto",current.getConcepto());
                    i.putExtra("tipo_pago",current.getTipo_pago());
                    i.putExtra("monto",current.getMonto());
                    i.putExtra("comision",current.getComision());
                    i.putExtra("fecha",current.getFecha());
                    i.putExtra("ind",current.getInd());
                    i.putExtra("cliente",current.getCliente());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(i);
                }
            });
    }


    @Override
    public int getItemCount() {
        return  tablaTorneoSubItems.size();
    }




}