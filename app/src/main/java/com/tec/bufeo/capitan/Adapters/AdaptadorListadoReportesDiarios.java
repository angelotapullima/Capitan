package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

public class AdaptadorListadoReportesDiarios extends RecyclerView.Adapter<AdaptadorListadoReportesDiarios.ReservaViewHolder> {

    private ArrayList<Reserva> array;
    private int layoutpadre;
    Context context;
    Reserva obj;
    private  OnItemClickListener listener;

    public AdaptadorListadoReportesDiarios() {
    }

    public AdaptadorListadoReportesDiarios(Context context, ArrayList<Reserva> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class ReservaViewHolder extends RecyclerView.ViewHolder{

       // private CircleImageView civ_fotoRetosRetador,civ_fotoRetosRetado;
        private TextView  txt_nombreCanchaR, txt_nombre_reservaR,txt_hora_reservaR, txt_costo_reservaR;

        public ReservaViewHolder(View itemView) {
            super(itemView);

            txt_nombreCanchaR=  itemView.findViewById(R.id.txt_nombreCanchaR);
            txt_nombre_reservaR = itemView.findViewById(R.id.txt_nombre_reservaR);
            txt_hora_reservaR = itemView.findViewById(R.id.txt_hora_reservaR);
            txt_costo_reservaR = itemView.findViewById(R.id.txt_costo_reservaR);

        }

        public void bid(final Reserva reserva,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(reserva,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public ReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReservaViewHolder holder, int position) {

        obj = array.get(position);
        holder.txt_nombreCanchaR.setText(obj.getCancha_nombre());
        holder.txt_nombre_reservaR.setText(obj.getReserva_nombre());
        holder.txt_hora_reservaR.setText(obj.getReserva_hora());
        holder.txt_costo_reservaR.setText("S/ "+obj.getReserva_costo());
        holder.bid(obj,listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Reserva reserva, int position);

    }

}