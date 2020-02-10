package com.tec.bufeo.capitan.Activity.EstadisticasEmpresas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.DetalleEstadisticasEmpresa;
import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.ModelEstadisticasEmpresa;
import com.tec.bufeo.capitan.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AdapterEstadisticasEmpresa extends RecyclerView.Adapter<AdapterEstadisticasEmpresa.EstadisticasViewHolder>  {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ModelEstadisticasEmpresa> tablaTorneoItems;
    List<String> canchas = new ArrayList<String>();
    Context ctx;
    private  OnItemClickListener listener;


    public AdapterEstadisticasEmpresa(Context context,List<String> canchas, List<ModelEstadisticasEmpresa> tablaTorneoItems,OnItemClickListener listener) {
        this.ctx=context;
        this.canchas=canchas;
        this.tablaTorneoItems=tablaTorneoItems;
        this.listener=listener;
    }

    class EstadisticasViewHolder extends RecyclerView.ViewHolder {
        private TextView fecha,monto_fecha;
        private RecyclerView rcv_item_estadistica;
        private View Viewfecha,ViewfechaAbajo;
        private LinearLayout layout_monto_fecha;


        private EstadisticasViewHolder(View itemView) {


            super(itemView);
            fecha =  itemView.findViewById(R.id.fecha);
            monto_fecha =  itemView.findViewById(R.id.monto_fecha);
            Viewfecha =  itemView.findViewById(R.id.Viewfecha);
            ViewfechaAbajo =  itemView.findViewById(R.id.ViewfechaAbajo);
            rcv_item_estadistica =  itemView.findViewById(R.id.rcv_item_estadistica);
            layout_monto_fecha =  itemView.findViewById(R.id.layout_monto_fecha);


        }

        public void bid(final ModelEstadisticasEmpresa mequipos,final OnItemClickListener listener){


        }

    }

    public List<DetalleEstadisticasEmpresa> listaSubItems = new ArrayList<>();
    DetalleEstadisticasEmpresa detalleEstadisticasEmpresa;
    @NonNull
    @Override
    public EstadisticasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_estadistica_empresa, parent, false);
        return new EstadisticasViewHolder(itemView);
    }
    String cancha ;
    List<DetalleEstadisticasEmpresa> listPorcancha = new ArrayList<>();
    @Override
    public void onBindViewHolder(@NonNull final EstadisticasViewHolder holder, int position) {


        ModelEstadisticasEmpresa tablaTorneoItem = tablaTorneoItems.get(position);

        if (tablaTorneoItems.get(position).getEstadisticaSubItems().size()>0){
            holder.fecha.setText(tablaTorneoItem.getFecha());
            holder.monto_fecha.setText(tablaTorneoItem.getMontoFinal());
            holder.setIsRecyclable(false);



            listaSubItems.clear();
            String montoPorCancha;
            for (int x =0;x<canchas.size();x++){
                cancha = canchas.get(x);

                listPorcancha = buildSubItemList(cancha,tablaTorneoItem.getEstadisticaSubItems());

                montoPorCancha = montoCancha(cancha,listPorcancha);

                if (listPorcancha.size()>0){
                    detalleEstadisticasEmpresa = new DetalleEstadisticasEmpresa(montoPorCancha,cancha,listPorcancha);
                    listaSubItems.add(detalleEstadisticasEmpresa);
                }



            }


            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    holder.rcv_item_estadistica.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(tablaTorneoItem.getEstadisticaSubItems().size());





            AdapterEstadisticaSubItem adapterDetalleEstadisticasEmpresa = new AdapterEstadisticaSubItem(ctx, listaSubItems, new AdapterEstadisticaSubItem.OnItemClickListener() {
                @Override
                public void onItemClick(DetalleEstadisticasEmpresa mequipos, String tipo, int position) {

                }
            });



            holder.rcv_item_estadistica.setLayoutManager(layoutManager);
            holder.rcv_item_estadistica.setAdapter(adapterDetalleEstadisticasEmpresa);
            holder.rcv_item_estadistica.setRecycledViewPool(viewPool);




        }else {
            holder.rcv_item_estadistica.setVisibility(View.GONE);
            holder.fecha.setVisibility(View.GONE);
            holder.Viewfecha.setVisibility(View.GONE);
            holder.ViewfechaAbajo.setVisibility(View.GONE);
            holder.layout_monto_fecha.setVisibility(View.GONE);
        }

        holder.bid(tablaTorneoItem,listener);


    }


    @Override
    public int getItemCount() {

        return tablaTorneoItems.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(ModelEstadisticasEmpresa mequipos,String tipo, int position);
    }

    private  String montoCancha(String dato, List<DetalleEstadisticasEmpresa> lista){

        String monto;
            double monto_final_chancha=0,monto_final=0;
            for (int x = 0; x < lista.size(); x++) {
                monto = lista.get(x).getReserva_pago1();

                if (dato.equals(cancha)){

                    monto_final_chancha = Double.parseDouble(monto);
                    monto_final = monto_final + monto_final_chancha;
                }


            }
        return String.valueOf(monto_final);
    }
    private List<DetalleEstadisticasEmpresa> buildSubItemList(String dato, List<DetalleEstadisticasEmpresa> lista) {
        List<DetalleEstadisticasEmpresa> subItemList = new ArrayList<>();

        for (int i=0; i<lista.size(); i++) {




                String monto,hora,reserva,cancha;

                monto = lista.get(i).getReserva_pago1();
                hora = lista.get(i).getReserva_hora();
                reserva = lista.get(i).getReserva_nombre();
                cancha = lista.get(i).getCancha_nombre();

                if (dato.equals(cancha)){


                    detalleEstadisticasEmpresa = new DetalleEstadisticasEmpresa(reserva,hora,monto,cancha);
                    subItemList.add(detalleEstadisticasEmpresa);
                }

        }

        return subItemList;
    }

}