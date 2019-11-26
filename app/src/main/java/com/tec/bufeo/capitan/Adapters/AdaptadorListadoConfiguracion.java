package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.bufeo.capitan.Modelo.MConfiguracion;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

public class AdaptadorListadoConfiguracion extends RecyclerView.Adapter<AdaptadorListadoConfiguracion.torneoViewHolder> {

    private ArrayList<MConfiguracion> array;
    private int layoutpadre;
    Context context;
    MConfiguracion obj;
    private  OnItemClickListener listener;

    public AdaptadorListadoConfiguracion() {
    }

    public AdaptadorListadoConfiguracion(Context context, ArrayList<MConfiguracion> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class torneoViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_icono;
        private TextView txt_titulo,txt_descripcion;



        public torneoViewHolder(View itemView) {
            super(itemView);

            img_icono=  itemView.findViewById(R.id.img_icono);
            txt_titulo=  itemView.findViewById(R.id.txt_titulo);
            txt_descripcion=  itemView.findViewById(R.id.txt_descripcion);
        }

        public void bid(final MConfiguracion mConfiguracion,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mConfiguracion,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public torneoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new torneoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final torneoViewHolder holder, int position) {

        obj = array.get(position);

        holder.img_icono.setImageResource(obj.getIcono());
        holder.txt_titulo.setText(obj.getTitulo());
        holder.txt_descripcion.setText(obj.getDescripcion());
        holder.bid(obj,listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(MConfiguracion mConfiguracion, int position);
    }
}