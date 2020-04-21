package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

public class EstadisticasAdapter2 extends RecyclerView.Adapter<EstadisticasAdapter2.Estadisticas2ViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Estadisticas current;
    Context ctx;
    private OnItemClickListener listener;
    Preferences preferencesUser;


    class Estadisticas2ViewHolder extends RecyclerView.ViewHolder {
        private TextView temporada, semana,PA,PS,RE,RR,RG,REM,RP,torneos;


        private Estadisticas2ViewHolder(View itemView) {



            super(itemView);

            temporada=  itemView.findViewById(R.id.temporada);
            semana=  itemView.findViewById(R.id.semana);
            PA=  itemView.findViewById(R.id.PA);
            PS=  itemView.findViewById(R.id.PS);
            RE=  itemView.findViewById(R.id.RE);
            RR=  itemView.findViewById(R.id.RR);
            RG=  itemView.findViewById(R.id.RG);
            REM=  itemView.findViewById(R.id.REM);
            RP=  itemView.findViewById(R.id.RP);
            torneos=  itemView.findViewById(R.id.torneos);





        }

        public void bid(final Estadisticas estadisticas,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(estadisticas, getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Estadisticas> mUsers; // Cached copy of users


    EstadisticasAdapter2(Context context , OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public Estadisticas2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_estadistica2, parent, false);
        return new Estadisticas2ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final Estadisticas2ViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            holder.temporada.setText(current.getTemporada());
            holder.semana.setText(current.getSemana());
            holder.PA.setText(current.getPuntaje_acumulado());
            holder.PS.setText(current.getPuntaje_semanal());
            holder.RE.setText(current.getRetos_enviados());
            holder.RR.setText(current.getRetos_recibidos());
            holder.RG.setText(current.getRetos_ganados());
            holder.REM.setText(current.getRetos_empatados());
            holder.RP.setText(current.getRetos_perdidos());
            holder.torneos.setText(current.getTorneos());





            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<Estadisticas> users){
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
        void onItemClick(Estadisticas comments, int position);
    }

}