package com.tec.bufeo.capitan.Activity.DetalleCanchas.Views;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AdapListadoCanchaReserva extends RecyclerView.Adapter<AdapListadoCanchaReserva.canchaReservaViewHolder> {


    Reserva current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;
    UniversalImageLoader universalImageLoader;


    private final LayoutInflater mInflater;

    private List<Reserva> mUsers; // Cached copy of users
    private List<Reserva> mDataFiltered; // Cached copy of users


    public AdapListadoCanchaReserva(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        universalImageLoader= new UniversalImageLoader(context);
        this.listener = listener;
    }


    public class canchaReservaViewHolder extends RecyclerView.ViewHolder{

       // private ImageView img_fotoCancha;
        private TextView txt_horasCancha, txt_precioCancha, txt_estadoCancha, txt_nombreReservaCancha, txt_precioAbonadoCancha,txt_Fecha;
        private LinearLayout lny_precioAbonado;
        private CardView cdv_canchas_horario_reserva;
        private ImageView imb_reserva_llamada;


        public canchaReservaViewHolder(View itemView) {
            super(itemView);

            txt_horasCancha = (TextView) itemView.findViewById(R.id.txt_horasCancha);
            txt_precioCancha = (TextView) itemView.findViewById(R.id.txt_precioCancha);
            txt_estadoCancha = (TextView) itemView.findViewById(R.id.txt_estadoCancha);
            txt_nombreReservaCancha = (TextView) itemView.findViewById(R.id.txt_nombreReservaCancha);
            txt_precioAbonadoCancha = (TextView) itemView.findViewById(R.id.txt_precioAbonadoCancha);
            lny_precioAbonado = (LinearLayout) itemView.findViewById(R.id.lny_precioAbonado);
            cdv_canchas_horario_reserva = (CardView) itemView.findViewById(R.id.cdv_canchas_horario_reserva);
            imb_reserva_llamada =(ImageView)itemView.findViewById(R.id.imb_reserva_llamada);
            txt_Fecha =(TextView) itemView.findViewById(R.id.txt_Fecha);


        }

        public void bid( final Reserva reserva,final OnItemClickListener listener){

            cdv_canchas_horario_reserva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(reserva,"cdv_canchas_horario_reserva",getAdapterPosition());
                    /*Intent intent = new Intent(context, RegistroReserva.class);
                    intent.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                    context.startActivity(intent);*/
                   // listener.onItemClick(reserva,getAdapterPosition());
                }
            });


        }
    }


    public void setWords(List<Reserva> users){
        mUsers = users;
        mDataFiltered = users;
        notifyDataSetChanged();
    }
    @Override
    public canchaReservaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.rcv_item_card_canchas_horarios, parent, false);
        return new canchaReservaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final canchaReservaViewHolder holder, int position) {

        if (mUsers != null) {
            current = mUsers.get(position);

            holder.txt_horasCancha.setText(current.getReserva_hora());
            holder.txt_precioCancha.setText(current.getReserva_precioCancha());
            holder.txt_estadoCancha.setText(current.getReserva_estado());
            holder.txt_nombreReservaCancha.setText(current.getReserva_nombre());
            holder.txt_precioAbonadoCancha.setText(current.getReserva_costo());
            holder.txt_Fecha.setText(current.getReserva_fecha());

            switch(current.getReserva_color()){
                case "rojo":
                    holder.cdv_canchas_horario_reserva.setCardBackgroundColor(ctx.getResources().getColor( R.color.colorRojo));

                    break;
                case "verde":
                    holder.cdv_canchas_horario_reserva.setCardBackgroundColor(ctx.getResources().getColor( R.color.colorVerde));
                    holder.lny_precioAbonado.setVisibility(View.GONE);
                    break;
                case "anaranjado":
                    holder.cdv_canchas_horario_reserva.setCardBackgroundColor(ctx.getResources().getColor( R.color.colorAnaranjado));
                    break;

            }
            holder.bid(current,listener);

        }


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
        void onItemClick(Reserva reserva,String tipo, int position);
    }

}