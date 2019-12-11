package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoPadre;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Views.RegistrarPartidos;
import com.tec.bufeo.capitan.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterInstanciasItem extends RecyclerView.Adapter<AdapterInstanciasItem.ItemViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Instancias> tablaTorneoItems;
    Context context;
    String id_torneo;

    public AdapterInstanciasItem(Context context, String id_torneo,List<Instancias> tablaTorneoItems) {
        this.context=context;
        this.id_torneo=id_torneo;
        this.tablaTorneoItems=tablaTorneoItems;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_intancia;
        private RecyclerView rcv_subitem_instancia;
        private LinearLayout agg_partido;


        private ItemViewHolder(View itemView) {


            super(itemView);
            nombre_intancia =  itemView.findViewById(R.id.nombre_intancia);
            rcv_subitem_instancia =  itemView.findViewById(R.id.rcv_subitem_instancia);
            agg_partido =  itemView.findViewById(R.id.agg_partido);


        }

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_equipos_intancias_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {


        final Instancias tablaTorneoItem = tablaTorneoItems.get(position);
        holder.agg_partido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(context, RegistrarPartidos.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id_torneo",id_torneo);
                i.putExtra("id_instancia",tablaTorneoItem.getId_instancia());

                context.startActivity(i);
            }
        });


        holder.nombre_intancia.setText(tablaTorneoItem.getNombre_instancia());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcv_subitem_instancia.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getListPartidos().size());

        // Create sub item view adapter


        AdapterInstanciasSubItem adapterInstanciasSubItem = new AdapterInstanciasSubItem(context,tablaTorneoItem.getListPartidos());
        holder.rcv_subitem_instancia.setLayoutManager(layoutManager);
        holder.rcv_subitem_instancia.setAdapter(adapterInstanciasSubItem);
        holder.rcv_subitem_instancia.setRecycledViewPool(viewPool);

    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

}
