package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Model.EquiposGrupo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterEquiposSeleccionado extends RecyclerView.Adapter<AdapterEquiposSeleccionado.EquiposSeleccionadosViewHolder>  {


    EquiposGrupo current;
    Context ctx;
    private OnItemClickListener listener;
    Preferences preferencesUser;
    UniversalImageLoader universalImageLoader;


    class EquiposSeleccionadosViewHolder extends RecyclerView.ViewHolder {
        CircleImageView foto_equipo_elegido;
        LinearLayout contenedor_equipos;
        TextView name_equipo;

        private EquiposSeleccionadosViewHolder(View itemView) {
            super(itemView);

            foto_equipo_elegido= itemView.findViewById(R.id.foto_equipo_elegido);
            contenedor_equipos= itemView.findViewById(R.id.contenedor_equipos);
            name_equipo= itemView.findViewById(R.id.name_equipo);

        }

        public void bid(final EquiposGrupo mequipos,final OnItemClickListener listener){

            contenedor_equipos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"contenedor_equipos",getAdapterPosition());
                }
            });



        }
    }

    private final LayoutInflater mInflater;


    private List<EquiposGrupo> mUsers; // Cached copy of users


    public AdapterEquiposSeleccionado(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        universalImageLoader= new UniversalImageLoader(context);
        this.listener = listener;}

    @NonNull
    @Override
    public EquiposSeleccionadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_equipos_elegidos, parent, false);
        return new EquiposSeleccionadosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposSeleccionadosViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);


            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.foto_equipo_elegido,null);

            holder.name_equipo.setText(current.getEquipo_nombre());



            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<EquiposGrupo> users){
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
        void onItemClick(EquiposGrupo mequipos, String tipo, int position);
    }



}