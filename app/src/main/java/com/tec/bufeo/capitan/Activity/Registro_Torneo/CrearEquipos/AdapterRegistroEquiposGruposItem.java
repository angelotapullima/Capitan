package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearEquipos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoItem;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipo;
import com.tec.bufeo.capitan.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRegistroEquiposGruposItem extends RecyclerView.Adapter<AdapterRegistroEquiposGruposItem.ItemViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<TablaTorneoItem> tablaTorneoItems;
    Context context;
    int cantidad;

    public AdapterRegistroEquiposGruposItem(Context context, List<TablaTorneoItem> tablaTorneoItems) {
        this.context=context;
        this.tablaTorneoItems=tablaTorneoItems;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_grupo;
        private RecyclerView rcv_subitem_tabla;
        private LinearLayout agg_equipo;
        private View divider;


        private ItemViewHolder(View itemView) {


            super(itemView);
            nombre_grupo =  itemView.findViewById(R.id.nombre_equipo_grupos);
            rcv_subitem_tabla =  itemView.findViewById(R.id.rcv_subitem_equipo);
            agg_equipo =  itemView.findViewById(R.id.agg_equipo);
            divider =  itemView.findViewById(R.id.divider);


        }

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_equipo_grupo_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {


        final TablaTorneoItem tablaTorneoItem = tablaTorneoItems.get(position);
        holder.agg_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(context, RegistroEquipo.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id_grupo",tablaTorneoItem.getId_grupo());

                context.startActivity(i);
            }
        });


        holder.nombre_grupo.setText(tablaTorneoItem.getNombre_grupo());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcv_subitem_tabla.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        cantidad = tablaTorneoItem.getTablaTorneoSubItems().size();

        if (cantidad>0){
            holder.divider.setVisibility(View.VISIBLE);

        }else{
            holder.divider.setVisibility(View.GONE);
            holder.rcv_subitem_tabla.setVisibility(View.GONE);
        }
        layoutManager.setInitialPrefetchItemCount(cantidad);

        // Create sub item view adapter

        //AdapterSubItemTablaTorneo adapterSubItemTablaTorneo =  new AdapterSubItemTablaTorneo(ctx,tablaTorneoItem.getTablaTorneoSubItems());
        AdapterRegistroEquiposGruposSubItem adapterRegistroEquiposGruposSubItem = new AdapterRegistroEquiposGruposSubItem(context,tablaTorneoItem.getTablaTorneoSubItems());

        holder.rcv_subitem_tabla.setLayoutManager(layoutManager);
        holder.rcv_subitem_tabla.setAdapter(adapterRegistroEquiposGruposSubItem);
        holder.rcv_subitem_tabla.setRecycledViewPool(viewPool);

    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

}