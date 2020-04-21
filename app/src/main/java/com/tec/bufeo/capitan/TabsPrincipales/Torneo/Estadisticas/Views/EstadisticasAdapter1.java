package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class EstadisticasAdapter1 extends RecyclerView.Adapter<EstadisticasAdapter1.EstadisticasViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Estadisticas current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class EstadisticasViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoEquipo;
        private TextView txt_numero, nombre_equipo;
        private TextView PA,RG,REM,RP;


        private EstadisticasViewHolder(View itemView) {
            super(itemView);

            logoEquipo =  itemView.findViewById(R.id.logoEquipo);
            txt_numero =  itemView.findViewById(R.id.txt_numero);
            nombre_equipo =  itemView.findViewById(R.id.nombre_equipo);
            RG=  itemView.findViewById(R.id.RG);
            REM=  itemView.findViewById(R.id.REM);
            RP=  itemView.findViewById(R.id.RP);
            PA=  itemView.findViewById(R.id.PA);


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


    public EstadisticasAdapter1(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public EstadisticasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_estadistica1, parent, false);
        return new EstadisticasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EstadisticasViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());


            //holder.txt_numero.setText(current.getEstadisticas_id());
            holder.nombre_equipo.setText(current.getNombre());
            holder.txt_numero.setText(current.getPosicion());
            holder.RG.setText(current.getRetos_ganados());
            holder.REM.setText(current.getRetos_empatados());
            holder.RP.setText(current.getRetos_perdidos());
            holder.PA.setText(current.getPuntaje_acumulado());
            UniversalImageLoader.setImage(IP2+"/"+current.getFoto(),holder.logoEquipo,null);




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Estadisticas> users){
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