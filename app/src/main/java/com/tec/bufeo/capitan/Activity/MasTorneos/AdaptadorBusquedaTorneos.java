package com.tec.bufeo.capitan.Activity.MasTorneos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Models.Torneo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class AdaptadorBusquedaTorneos extends RecyclerView.Adapter<AdaptadorBusquedaTorneos.BusquedaTorneoViewHolder> {

    Torneo current;
    Context ctx;
    private OnItemClickListener listener;
    Preferences preferencesUser;


    class BusquedaTorneoViewHolder extends RecyclerView.ViewHolder {


        private TextView txt_nombre_torneo,txt_descripcion_torneo,txt_lugar_torneo,txt_fecha_torneo,txt_hora_torneo, txt_cant_equipos_torneo, txt_organizador_torneo;


        private BusquedaTorneoViewHolder(View itemView) {
            super(itemView);


            txt_nombre_torneo=  itemView.findViewById(R.id.txt_nombre_torneo);
            txt_descripcion_torneo=  itemView.findViewById(R.id.txt_descripcion_torneo);
            txt_lugar_torneo=  itemView.findViewById(R.id.txt_lugar_torneo);
            txt_fecha_torneo = itemView.findViewById(R.id.txt_fecha_torneo);
            txt_hora_torneo = itemView.findViewById(R.id.txt_hora_torneo);
            txt_cant_equipos_torneo = itemView.findViewById(R.id.txt_cant_equipos_torneo);
            txt_organizador_torneo = itemView.findViewById(R.id.txt_organizador_torneo);

        }

        public void bid(final Torneo torneo, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(torneo, getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Torneo> mUsers; // Cached copy of users


    public AdaptadorBusquedaTorneos(Context context, OnItemClickListener listener) {
        this.ctx = context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public BusquedaTorneoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_mis_torneos, parent, false);
        return new BusquedaTorneoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final BusquedaTorneoViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            holder.txt_nombre_torneo.setText(current.getTorneo_nombre());
            holder.txt_descripcion_torneo.setText(current.getTorneo_descripcion());
            holder.txt_lugar_torneo.setText(current.getTorneo_lugar());
            holder.txt_fecha_torneo.setText(current.getTorneo_fecha());
            holder.txt_hora_torneo.setText(current.getTorneo_hora());
            holder.txt_cant_equipos_torneo.setText(current.getTorneo_equipos());
            holder.txt_organizador_torneo.setText(current.getTorneo_organizador());

            holder.bid(current, listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }

    public void setWords(List<Torneo> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        } else {
            return 0;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Torneo comments, int position);
    }
}
