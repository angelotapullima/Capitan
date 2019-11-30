package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterGrupos extends RecyclerView.Adapter<AdapterGrupos.gruposViewHolder> {


    Grupos current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;



    class gruposViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre_grupo_rcv;
        ProgressBar progress;
        ImageView check;


        private gruposViewHolder(View itemView) {
            super(itemView);

            nombre_grupo_rcv=  itemView.findViewById(R.id.nombre_grupo_rcv);
            progress=  itemView.findViewById(R.id.progress);
            check=  itemView.findViewById(R.id.check);

        }

        public void bid(final Grupos grupos, final OnItemClickListener listener){
            nombre_grupo_rcv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("prueba2", grupos, getAdapterPosition());



                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Grupos> mUsers; // Cached copy of users


    public AdapterGrupos(Context context, OnItemClickListener listener) {

        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public gruposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_grupos, parent, false);
        return new gruposViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final gruposViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);


            if (current.getEstado().equals("0")){
                holder.progress.setVisibility(View.GONE);
                holder.check.setVisibility(View.VISIBLE);
            }else{
                holder.check.setVisibility(View.GONE);
                holder.progress.setVisibility(View.VISIBLE);
            }


            holder.nombre_grupo_rcv.setText(current.getNombre_grupo());


            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Grupos> users){
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
        void onItemClick(String dato, Grupos feedTorneo, int position);
    }

}