package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.RegistroReserva;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

public class AdapListadoCanchaReserva extends RecyclerView.Adapter<AdapListadoCanchaReserva.canchaReservaViewHolder> {

    private ArrayList<Reserva> array;
    private int layoutpadre;
    Context context;
    Reserva obj;
    private  OnItemClickListener listener;

    public AdapListadoCanchaReserva() {

    }

    public AdapListadoCanchaReserva(Context context, ArrayList<Reserva> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }


    public class canchaReservaViewHolder extends RecyclerView.ViewHolder{

       // private ImageView img_fotoCancha;
        private TextView txt_horasCancha, txt_precioCancha, txt_estadoCancha, txt_nombreReservaCancha, txt_precioAbonadoCancha;
        private LinearLayout lny_precioAbonado;
        private CardView cdv_canchas_horario_reserva;
        private ImageButton imb_reserva_llamada;


        public canchaReservaViewHolder(View itemView) {
            super(itemView);

            txt_horasCancha = (TextView) itemView.findViewById(R.id.txt_horasCancha);
            txt_precioCancha = (TextView) itemView.findViewById(R.id.txt_precioCancha);
            txt_estadoCancha = (TextView) itemView.findViewById(R.id.txt_estadoCancha);
            txt_nombreReservaCancha = (TextView) itemView.findViewById(R.id.txt_nombreReservaCancha);
            txt_precioAbonadoCancha = (TextView) itemView.findViewById(R.id.txt_precioAbonadoCancha);
            lny_precioAbonado = (LinearLayout) itemView.findViewById(R.id.lny_precioAbonado);
            cdv_canchas_horario_reserva = (CardView) itemView.findViewById(R.id.cdv_canchas_horario_reserva);
            imb_reserva_llamada =(ImageButton)itemView.findViewById(R.id.imb_reserva_llamada);


        }

        public void bid( final Reserva reserva,final OnItemClickListener listener){

            imb_reserva_llamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RegistroReserva.class);
                    intent.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                    context.startActivity(intent);
                   // listener.onItemClick(reserva,getAdapterPosition());
                }
            });


        }
    }

    @Override
    public canchaReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new canchaReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final canchaReservaViewHolder holder, int position) {

        obj = array.get(position);

       // Picasso.with(context).load("http://"+IP+"/"+ obj.getCancha_foto()).into(holder.img_fotoCancha);
        holder.txt_horasCancha.setText(obj.getReserva_hora());
        holder.txt_precioCancha.setText(obj.getReserva_precioCancha());
        holder.txt_estadoCancha.setText(obj.getReserva_estado());
        holder.txt_nombreReservaCancha.setText(obj.getReserva_nombre());
        holder.txt_precioAbonadoCancha.setText(obj.getReserva_costo());

        switch(obj.getReserva_color()){
            case "rojo":
                holder.cdv_canchas_horario_reserva.setCardBackgroundColor(context.getResources().getColor( R.color.colorRojo));

                break;
            case "verde":
                holder.cdv_canchas_horario_reserva.setCardBackgroundColor(context.getResources().getColor( R.color.colorVerde));
                break;
            case "anaranjado":
                holder.cdv_canchas_horario_reserva.setCardBackgroundColor(context.getResources().getColor( R.color.colorAnaranjado));
                break;

        }
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