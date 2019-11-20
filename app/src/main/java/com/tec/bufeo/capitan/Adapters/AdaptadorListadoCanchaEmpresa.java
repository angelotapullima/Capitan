package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class AdaptadorListadoCanchaEmpresa extends RecyclerView.Adapter<AdaptadorListadoCanchaEmpresa.canchaEmpresasViewHolder> {

    private ArrayList<Cancha> array;
    private int layoutpadre;
    Context context;
    Cancha obj;
    private  OnItemClickListener listener;

    public AdaptadorListadoCanchaEmpresa() {
    }

    public AdaptadorListadoCanchaEmpresa(Context context, ArrayList<Cancha> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class canchaEmpresasViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_fotoCancha;
        private TextView txt_nombreCancha,txt_dimensones,txt_precio_d,txt_precio_n;


        public canchaEmpresasViewHolder(View itemView) {
            super(itemView);

            img_fotoCancha= (ImageView) itemView.findViewById(R.id.img_fotoCancha);
            txt_nombreCancha = (TextView) itemView.findViewById(R.id.txt_nombreCancha);
            txt_dimensones = (TextView) itemView.findViewById(R.id.txt_dimensones);
            txt_precio_d = (TextView) itemView.findViewById(R.id.txt_precio_d);
            txt_precio_n = (TextView) itemView.findViewById(R.id.txt_precio_n);



        }

        public void bid(final Cancha cancha,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(cancha,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public canchaEmpresasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new canchaEmpresasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final canchaEmpresasViewHolder holder, int position) {

        obj = array.get(position);

        Picasso.with(context).load(IP+"/"+ obj.getCancha_foto()).into(holder.img_fotoCancha);
        holder.txt_nombreCancha.setText(obj.getCancha_nombre());
        holder.txt_dimensones.setText(obj.getCancha_dimenciones());
        holder.txt_precio_d.setText(obj.getCancha_precioD());
        holder.txt_precio_n.setText(obj.getCancha_precioN());
        holder.bid(obj,listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Cancha cancha, int position);
    }

}