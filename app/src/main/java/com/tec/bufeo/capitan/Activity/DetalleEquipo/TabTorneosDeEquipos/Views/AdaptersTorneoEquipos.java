package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptersTorneoEquipos extends RecyclerView.Adapter<AdaptersTorneoEquipos.TorneoEquiposViewHolder> {


    TorneosDeEquipos current;
    Context ctx;
    private OnItemClickListener listener;
    Preferences preferencesUser;


    class TorneoEquiposViewHolder extends RecyclerView.ViewHolder {


        private TextView txt_nombre_torneo,txt_descripcion_torneo,txt_lugar_torneo,txt_fecha_torneo,txt_hora_torneo, txt_cant_equipos_torneo, txt_organizador_torneo;


        private TorneoEquiposViewHolder(View itemView) {
            super(itemView);


            txt_nombre_torneo=  itemView.findViewById(R.id.txt_nombre_torneo);
            txt_descripcion_torneo=  itemView.findViewById(R.id.txt_descripcion_torneo);
            txt_lugar_torneo=  itemView.findViewById(R.id.txt_lugar_torneo);
            txt_fecha_torneo = itemView.findViewById(R.id.txt_fecha_torneo);
            txt_hora_torneo = itemView.findViewById(R.id.txt_hora_torneo);
            txt_cant_equipos_torneo = itemView.findViewById(R.id.txt_cant_equipos_torneo);
            txt_organizador_torneo = itemView.findViewById(R.id.txt_organizador_torneo);

        }

        public void bid(final TorneosDeEquipos torneo, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(torneo, getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<TorneosDeEquipos> mUsers; // Cached copy of users


    public AdaptersTorneoEquipos(Context context, OnItemClickListener listener) {
        this.ctx = context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TorneoEquiposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_mis_torneos, parent, false);
        return new TorneoEquiposViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final TorneoEquiposViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            holder.txt_nombre_torneo.setText(current.getNombre());
            //holder.txt_descripcion_torneo.setText(current.getTorneo_descripcion());
            holder.txt_lugar_torneo.setText(current.getLugar());
            holder.txt_fecha_torneo.setText(current.getFecha());
            //holder.txt_hora_torneo.setText(current.getTorneo_hora());
            //holder.txt_cant_equipos_torneo.setText(current.getTorneo_equipos());
            holder.txt_organizador_torneo.setText(current.getOrganizador());

            holder.bid(current, listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }

    public void setWords(List<TorneosDeEquipos> users) {
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
        void onItemClick(TorneosDeEquipos comments, int position);
    }
}
