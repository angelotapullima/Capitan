package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

public class AdaptadorMisTorneos extends RecyclerView.Adapter<AdaptadorMisTorneos.TorneoViewHolder> {

    UniversalImageLoader universalImageLoader;
    Torneo current;
    Context ctx;
    private OnItemClickListener listener;
    Preferences preferencesUser;


    class TorneoViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_nombre_torneo,txt_descripcion_torneo,txt_lugar_torneo,txt_fecha_torneo,txt_hora_torneo, txt_cant_equipos_torneo, txt_organizador_torneo;


        private TorneoViewHolder(View itemView) {
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


    AdaptadorMisTorneos(Context context, OnItemClickListener listener) {
        this.ctx = context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TorneoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_mis_torneos, parent, false);
        return new TorneoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final TorneoViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

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

    void setWords(List<Torneo> users) {
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
