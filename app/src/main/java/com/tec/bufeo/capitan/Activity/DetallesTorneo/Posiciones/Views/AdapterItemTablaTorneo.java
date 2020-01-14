package com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.Posiciones.Models.TablaTorneoItem;
import com.tec.bufeo.capitan.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterItemTablaTorneo extends RecyclerView.Adapter<AdapterItemTablaTorneo.TablaTorneoViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<TablaTorneoItem> tablaTorneoItems;
    Context ctx;

    public AdapterItemTablaTorneo(Context context, List<TablaTorneoItem> tablaTorneoItems) {
        this.ctx=context;
        this.tablaTorneoItems=tablaTorneoItems;
    }

    class TablaTorneoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_grupo;
        private RecyclerView rcv_subitem_tabla;


        private TablaTorneoViewHolder(View itemView) {


            super(itemView);
            nombre_grupo =  itemView.findViewById(R.id.nombre_grupo);
            rcv_subitem_tabla =  itemView.findViewById(R.id.rcv_subitem_tabla);


        }

    }

    @NonNull
    @Override
    public TablaTorneoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_tabla_item_torneo, parent, false);
        return new TablaTorneoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TablaTorneoViewHolder holder, int position) {


            TablaTorneoItem tablaTorneoItem = tablaTorneoItems.get(position);


            holder.nombre_grupo.setText(tablaTorneoItem.getNombre_grupo());

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    holder.rcv_subitem_tabla.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getTablaTorneoSubItems().size());

            // Create sub item view adapter
            AdapterSubItemTablaTorneo adapterSubItemTablaTorneo =  new AdapterSubItemTablaTorneo(ctx,tablaTorneoItem.getTablaTorneoSubItems());

            holder.rcv_subitem_tabla.setLayoutManager(layoutManager);
            holder.rcv_subitem_tabla.setAdapter(adapterSubItemTablaTorneo);
            holder.rcv_subitem_tabla.setRecycledViewPool(viewPool);






    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

}