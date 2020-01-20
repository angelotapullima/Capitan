package com.tec.bufeo.capitan.Activity.RegistrarEquipo.BuscarEquipo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterEquipos extends RecyclerView.Adapter<AdapterEquipos.EquiposSeleccionarViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Mequipos current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class EquiposSeleccionarViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_fotoEquipo;
        private ImageView seleccionado,sin_seleccionar;
        ProgressBar carga_seleccionado;
        private TextView nombreEquipo,capitanEquipo;
        private RelativeLayout btn_seleccionar;

        private EquiposSeleccionarViewHolder(View itemView) {
            super(itemView);

            civ_fotoEquipo=  itemView.findViewById(R.id.civ_fotoEquipo);
            nombreEquipo = itemView.findViewById(R.id.nombreEquipo);
            capitanEquipo = itemView.findViewById(R.id.capitanEquipo);
            btn_seleccionar = itemView.findViewById(R.id.btn_seleccionar);
            seleccionado = itemView.findViewById(R.id.seleccionado);
            sin_seleccionar = itemView.findViewById(R.id.sin_seleccionar);
            carga_seleccionado = itemView.findViewById(R.id.carga_seleccionado);

        }

        public void bid(final Mequipos mequipos,final OnItemClickListener listener){

            btn_seleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    carga_seleccionado.setVisibility(View.VISIBLE);
                    sin_seleccionar.setVisibility(View.GONE);
                    seleccionado.setVisibility(View.GONE);

                    listener.onItemClick(mequipos, getAdapterPosition());

                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Mequipos> mUsers; // Cached copy of users


    public AdapterEquipos(Context context,OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public EquiposSeleccionarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_equipos_seleccionar, parent, false);
        return new EquiposSeleccionarViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposSeleccionarViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.civ_fotoEquipo,null);

            holder.nombreEquipo.setText(current.getEquipo_nombre());
            holder.capitanEquipo.setText(current.getCapitan_nombre());

            if (current.getEstado_seleccion().equals("0")){
                holder.sin_seleccionar.setVisibility(View.VISIBLE);
                holder.seleccionado.setVisibility(View.GONE);
                holder.carga_seleccionado.setVisibility(View.GONE);
            }else {
                holder.sin_seleccionar.setVisibility(View.GONE);
                holder.seleccionado.setVisibility(View.VISIBLE);
                holder.carga_seleccionado.setVisibility(View.GONE);
            }

            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Mequipos> users){
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
        void onItemClick(Mequipos mequipos, int position);
    }



}