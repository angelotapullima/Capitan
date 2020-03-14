package com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoSubItem;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class AdapterSubItemTablaTorneo extends RecyclerView.Adapter<AdapterSubItemTablaTorneo.SubItemTablaTorneoViewHolder>  {

    private List<TablaTorneoSubItem> tablaTorneoSubItems;
    Context ctx;
    UniversalImageLoader universalImageLoader;

    public AdapterSubItemTablaTorneo(Context context, List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.ctx=context;
        universalImageLoader = new UniversalImageLoader(context);
        this.tablaTorneoSubItems=tablaTorneoSubItems;
    }

    class SubItemTablaTorneoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_equipo,PJ,PG,PP,PE,GF,GC,DG,puntos;
        ImageView logoEquipo;


        private SubItemTablaTorneoViewHolder(View itemView) {


            super(itemView);
            nombre_equipo =  itemView.findViewById(R.id.nombre_equipo);
            PJ =  itemView.findViewById(R.id.PJ);
            PG =  itemView.findViewById(R.id.PG);
            PP =  itemView.findViewById(R.id.PP);
            PE =  itemView.findViewById(R.id.PE);
            GF =  itemView.findViewById(R.id.GF);
            GC =  itemView.findViewById(R.id.GC);
            DG =  itemView.findViewById(R.id.DG);
            puntos =  itemView.findViewById(R.id.puntos);
            logoEquipo =  itemView.findViewById(R.id.logoEquipo);



        }


    }

    @NonNull
    @Override
    public SubItemTablaTorneoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_tabla_subitem_torneo, parent, false);
        return new SubItemTablaTorneoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final SubItemTablaTorneoViewHolder holder, int position) {

            TablaTorneoSubItem torneoSubItem = tablaTorneoSubItems.get(position);



            holder.nombre_equipo.setText(torneoSubItem.getEquipo_nombre());
            holder.PJ.setText(torneoSubItem.getPart_j());
            holder.PG.setText(torneoSubItem.getPart_g());
            holder.PP.setText(torneoSubItem.getPart_p());
            holder.PE.setText(torneoSubItem.getPart_e());
            holder.GF.setText(torneoSubItem.getGf());
            holder.GC.setText(torneoSubItem.getGc());
            holder.puntos.setText(torneoSubItem.getPuntos());


        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        UniversalImageLoader.setImage(IP2+"/"+ torneoSubItem.getEquipo_foto(),holder.logoEquipo,null);


    }

    @Override
    public int getItemCount() {

            return  tablaTorneoSubItems.size();

    }



}