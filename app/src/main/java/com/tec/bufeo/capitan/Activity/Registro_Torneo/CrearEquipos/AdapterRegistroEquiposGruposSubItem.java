package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos;

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

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterRegistroEquiposGruposSubItem extends RecyclerView.Adapter<AdapterRegistroEquiposGruposSubItem.SubItemViewHolder>  {

    private List<TablaTorneoSubItem> tablaTorneoSubItems;
    Context ctx;
    UniversalImageLoader universalImageLoader;

    public AdapterRegistroEquiposGruposSubItem(Context context, List<TablaTorneoSubItem> tablaTorneoSubItems) {
        this.ctx=context;
        this.tablaTorneoSubItems=tablaTorneoSubItems;
        universalImageLoader = new UniversalImageLoader(context);
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_equipo_lista;
        private ImageView imagenEquipo;


        private SubItemViewHolder(View itemView) {


            super(itemView);
            imagenEquipo =  itemView.findViewById(R.id.imagenEquipo);
            nombre_equipo_lista =  itemView.findViewById(R.id.nombre_equipo_lista);




        }


    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_equipo_grupo_subitem, parent, false);
        return new SubItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final SubItemViewHolder holder, int position) {

        TablaTorneoSubItem torneoSubItem = tablaTorneoSubItems.get(position);



        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        UniversalImageLoader.setImage(IP2+"/"+ torneoSubItem.getEquipo_foto(),holder.imagenEquipo,null);
        //holder.imagenEquipo.setText(torneoSubItem.getPosicion_lista());
        holder.nombre_equipo_lista.setText(torneoSubItem.getEquipo_nombre());

        //UniversalImageLoader.setImage(IP+"/"+current.getFoto(),holder.logoEquipo,null);


    }

    @Override
    public int getItemCount() {

        return  tablaTorneoSubItems.size();

    }



}