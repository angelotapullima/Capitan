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

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.R;

import java.util.List;

public class AdaptadorMisReservas extends RecyclerView.Adapter<AdaptadorMisReservas.EquiposViewHolder>  {


    MisReservas current;
    Context ctx;
    private  OnItemClickListener listener;
    //Preferences preferencesUser;


    class EquiposViewHolder extends RecyclerView.ViewHolder {

        private TextView mov_monto,mov_nombre,mov_fecha;
        private ImageView ic_add,ic_remove;

        private EquiposViewHolder(View itemView) {
            super(itemView);

            mov_monto=  itemView.findViewById(R.id.mov_monto);
            mov_nombre = itemView.findViewById(R.id.mov_nombre);
            mov_fecha = itemView.findViewById(R.id.mov_fecha);
            ic_add = itemView.findViewById(R.id.ic_add);
            ic_remove = itemView.findViewById(R.id.ic_remove);

        }

        public void bid(final MisReservas mequipos, final OnItemClickListener listener){



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
        View itemView = mInflater.inflate(R.layout.rcv_item_movimientos, parent, false);
        return new EquiposViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);



            /*holder.mov_fecha.setText(current.getMovimiento_fecha());
            holder.mov_monto.setText(current.getMovimiento_monto());
            holder.mov_nombre.setText(current.getMovimiento_nombre());

            if (current.getMovimiento_estado().equals("2")){
                holder.ic_add.setVisibility(View.GONE);
                holder.mov_monto.setTextColor(Color.RED);
            }else{
                holder.ic_remove.setVisibility(View.GONE);
                holder.mov_monto.setTextColor(Color.rgb(76,175,80));
            }*/
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