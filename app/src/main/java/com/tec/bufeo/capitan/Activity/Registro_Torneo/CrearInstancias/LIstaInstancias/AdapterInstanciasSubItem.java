package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterInstanciasSubItem extends RecyclerView.Adapter<AdapterInstanciasSubItem.SubItemViewHolder>  {

    UniversalImageLoader universalImageLoader;
    private List<PartidosInstancias> tablaTorneoSubItems;
    Context ctx;

    public AdapterInstanciasSubItem(Context context, List<PartidosInstancias> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
        universalImageLoader = new UniversalImageLoader(context);
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_equipo_local,nombre_equipo_visita,partido_fecha,partido_hora;
        ImageView foto_equipo_local,foto_equipo_visita;


        private SubItemViewHolder(View itemView) {


            super(itemView);
            nombre_equipo_local =  itemView.findViewById(R.id.nombre_equipo_local);
            nombre_equipo_visita =  itemView.findViewById(R.id.nombre_equipo_visita);
            partido_fecha =  itemView.findViewById(R.id.partido_fecha);
            partido_hora =  itemView.findViewById(R.id.partido_hora);
            foto_equipo_local =  itemView.findViewById(R.id.foto_equipo_local);
            foto_equipo_visita =  itemView.findViewById(R.id.foto_equipo_visita);

        }

    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_equipos_instancias_subitem, parent, false);
        return new SubItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder holder, int position) {
        if (tablaTorneoSubItems != null) {
            PartidosInstancias torneoSubItem = tablaTorneoSubItems.get(position);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            UniversalImageLoader.setImage(IP2+"/"+ torneoSubItem.getFoto_equipo_local(),holder.foto_equipo_local,null);
            UniversalImageLoader.setImage(IP2+"/"+ torneoSubItem.getFoto_equipo_visita(),holder.foto_equipo_visita,null);


            holder.nombre_equipo_local.setText(torneoSubItem.getNombre_equipo_local());
            holder.nombre_equipo_visita.setText(torneoSubItem.getNombre_equipo_visita());
            holder.partido_fecha.setText(torneoSubItem.getPartido_fecha());
            holder.partido_hora.setText(torneoSubItem.getPartido_hora());
        }

    }

    @Override
    public int getItemCount() {

        return  tablaTorneoSubItems.size();

    }



}