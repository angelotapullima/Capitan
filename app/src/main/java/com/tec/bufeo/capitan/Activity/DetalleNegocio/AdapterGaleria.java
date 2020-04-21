package com.tec.bufeo.capitan.Activity.DetalleNegocio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Galeria;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterGaleria extends RecyclerView.Adapter<AdapterGaleria.GaleriaViewHolder> {

    UniversalImageLoader universalImageLoader;
    Context ctx;
    Galeria current;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    public AdapterGaleria(Context context, OnItemClickListener listener) {

        this.ctx = context;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
    }

    public class GaleriaViewHolder extends RecyclerView.ViewHolder{

        private ImageView foto;

        public GaleriaViewHolder(View itemView) {
            super(itemView);

            foto= (ImageView) itemView.findViewById(R.id.foto);



        }

        public void bid(final Galeria galeria,final OnItemClickListener listener){

            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(galeria,"foto",getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater mInflater;


    private List<Galeria> mUsers; // Cached copy of users
    private List<Galeria> mDataFiltered; // Cached copy of users
    @Override
    public GaleriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= mInflater.inflate(R.layout.rcv_galeria_item,parent,false);
        return new GaleriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GaleriaViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            universalImageLoader.setImage(IP2+"/"+ current.getGaleria_foto(),holder.foto,null);

            holder.bid(current,listener);
        }else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }



    }

    public void setWords(List<Galeria> users){
        mUsers = users;
        mDataFiltered = users;
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
        void onItemClick(Galeria cancha,String tipo, int position);
    }

}