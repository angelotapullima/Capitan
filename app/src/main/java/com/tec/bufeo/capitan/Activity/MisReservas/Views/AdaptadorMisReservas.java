package com.tec.bufeo.capitan.Activity.MisReservas.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdaptadorMisReservas extends RecyclerView.Adapter<AdaptadorMisReservas.EquiposViewHolder>  {


    MisReservas current;
    Context ctx;
    private  OnItemClickListener listener;
    //Preferences preferencesUser;


    class EquiposViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre_reserva_reserva,monto_final_reserva,empresa_reserva,nombre_cancha_reserva,horaReserva,fechaReserva;
        private MaterialButton verMas;

        private EquiposViewHolder(View itemView) {
            super(itemView);

            nombre_reserva_reserva=  itemView.findViewById(R.id.nombre_reserva_reserva);
            monto_final_reserva = itemView.findViewById(R.id.monto_final_reserva);
            empresa_reserva = itemView.findViewById(R.id.empresa_reserva);
            nombre_cancha_reserva = itemView.findViewById(R.id.nombre_cancha_reserva);
            horaReserva = itemView.findViewById(R.id.horaReserva);
            fechaReserva = itemView.findViewById(R.id.fechaReserva);
            verMas = itemView.findViewById(R.id.verMas);
        }

        public void bid(final MisReservas misReservas, final OnItemClickListener listener){

            verMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(misReservas,"verMas",getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater mInflater;

    private List<MisReservas> mUsers; // Cached copy of users


    public AdaptadorMisReservas(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        /*universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);*/
        this.listener = listener;}

    @NonNull
    @Override
    public EquiposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_mis_reservas, parent, false);
        return new EquiposViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);



            holder.nombre_reserva_reserva.setText(current.getNombre_reserva());
            holder.monto_final_reserva.setText(current.getMonto_final());
            holder.empresa_reserva.setText(current.getNombre_empresa());
            holder.nombre_cancha_reserva.setText(current.getCancha_nombre());
            holder.horaReserva.setText(current.getHora_reserva());
            holder.fechaReserva.setText(current.getFecha_reserva());


            holder.bid(current,listener);
        }
    }
    public void setWords(List<MisReservas> users){
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }else{
            return  0;
        }
    }




    public interface  OnItemClickListener{
        void onItemClick(MisReservas mequipos, String tipo, int position);
    }

}