package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
public class AdaptadorListadoEmpresas extends RecyclerView.Adapter<AdaptadorListadoEmpresas.EmpresasViewHolder> {

    private ArrayList<Empresas> array;
    private int layoutpadre;
    Context context;
    Empresas obj;
    private  OnItemClickListener listener;

    public AdaptadorListadoEmpresas() {
    }

    public AdaptadorListadoEmpresas(Context context, ArrayList<Empresas> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class EmpresasViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_fotoEmpresa;
        private TextView txt_nombreEmpresa,txt_direccionEmpresa;


        public EmpresasViewHolder(View itemView) {
            super(itemView);

            img_fotoEmpresa= (ImageView) itemView.findViewById(R.id.img_fotoEmpresa);
            txt_nombreEmpresa = (TextView) itemView.findViewById(R.id.txt_nombreEmpresa);
            txt_direccionEmpresa = (TextView) itemView.findViewById(R.id.txt_direccionEmpresa);



        }

        public void bid(final Empresas empresas,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(empresas,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public EmpresasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new EmpresasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EmpresasViewHolder holder, int position) {

        obj = array.get(position);

        Glide.with(context).load(IP2+"/"+ obj.getEmpresas_foto()).into(holder.img_fotoEmpresa);
        holder.txt_nombreEmpresa.setText(obj.getEmpresas_nombre());
        holder.txt_direccionEmpresa.setText(obj.getEmpresas_direccion());
        holder.bid(obj,listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Empresas empresas, int position);
    }

}