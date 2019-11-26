package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class AdaptadorListaCanchasBusqueda extends RecyclerView.Adapter<AdaptadorListaCanchasBusqueda.canchaBusquedaViewHolder> {

    private ArrayList<Empresas> array;
    private int layoutpadre;
    Context context;
    Empresas obj;
    private OnItemClickListener listener;

    public AdaptadorListaCanchasBusqueda() {

    }

    public AdaptadorListaCanchasBusqueda(Context context, ArrayList<Empresas> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }


    public class canchaBusquedaViewHolder extends RecyclerView.ViewHolder {

        private ImageButton imb_llamar;
        private TextView txt_buscar_nombreEmpresa, txt_buscar_precioCancha, txt_buscar_telefonoEmpresa, txt_buscar_direccionEmpresa;
        //private LinearLayout lny_precioAbonado;
        //private CardView cdv_canchas_horario_reserva;
        private ImageView imagen_cancha;


        public canchaBusquedaViewHolder(View itemView) {
            super(itemView);

            txt_buscar_nombreEmpresa = (TextView) itemView.findViewById(R.id.txt_buscar_nombreEmpresa);
            txt_buscar_precioCancha = (TextView) itemView.findViewById(R.id.txt_buscar_precioCancha);
            txt_buscar_telefonoEmpresa = (TextView) itemView.findViewById(R.id.txt_buscar_telefonoEmpresa);
            txt_buscar_direccionEmpresa = (TextView) itemView.findViewById(R.id.txt_buscar_direccionEmpresa);
            imagen_cancha = (ImageView) itemView.findViewById(R.id.imagen_cancha);
            imb_llamar =(ImageButton)itemView.findViewById(R.id.imb_llamar);


        }

        public void bid(final Empresas empresas, final OnItemClickListener listener) {

            /*imb_reserva_llamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RegistroReserva.class);
                    intent.putExtra("h_reserva",reserva.getReserva_hora_cancha());
                    context.startActivity(intent);
                    // listener.onItemClick(reserva,getAdapterPosition());
                }
            });*/


        }
    }

    @Override
    public canchaBusquedaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutpadre, parent, false);
        return new canchaBusquedaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final canchaBusquedaViewHolder holder, int position) {

        obj = array.get(position);

        Picasso.with(context).load(IP+"/"+ obj.getEmpresas_foto()).into(holder.imagen_cancha);
        holder.txt_buscar_nombreEmpresa.setText(obj.getEmpresas_nombre());
        holder.txt_buscar_direccionEmpresa.setText(obj.getEmpresas_direccion());
        holder.txt_buscar_precioCancha.setText(obj.getCancha_precioD());
        holder.txt_buscar_telefonoEmpresa.setText(obj.getEmpresas_telefono());
        //holder.txt_precioAbonadoCancha.setText(obj.getReserva_costo());

        /*switch (obj.getReserva_color()) {
            case "rojo":
                holder.cdv_canchas_horario_reserva.setCardBackgroundColor(context.getResources().getColor(R.color.colorRojo));

                break;
            case "verde":
                holder.cdv_canchas_horario_reserva.setCardBackgroundColor(context.getResources().getColor(R.color.colorVerde));
                break;
            case "anaranjado":
                holder.cdv_canchas_horario_reserva.setCardBackgroundColor(context.getResources().getColor(R.color.colorAnaranjado));
                break;

        }*/
        holder.bid(obj, listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Empresas empresas, int position);
    }
}

