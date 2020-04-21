package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Canchas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorListadoCanchaEmpresa extends RecyclerView.Adapter<AdaptadorListadoCanchaEmpresa.canchaEmpresasViewHolder> {

    UniversalImageLoader universalImageLoader;
    Context ctx;
    Canchas current;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    public AdaptadorListadoCanchaEmpresa(Context context, OnItemClickListener listener) {

        this.ctx = context;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
    }

    public class canchaEmpresasViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_fotoCancha;
        private TextView txt_nombreCancha,txt_dimensones,txt_precio_d,txt_precio_n;


        public canchaEmpresasViewHolder(View itemView) {
            super(itemView);

            img_fotoCancha= (ImageView) itemView.findViewById(R.id.img_fotoCancha);
            txt_nombreCancha = (TextView) itemView.findViewById(R.id.txt_nombreCancha);
            txt_dimensones = (TextView) itemView.findViewById(R.id.txt_dimensones);
            txt_precio_d = (TextView) itemView.findViewById(R.id.txt_precio_d);
            txt_precio_n = (TextView) itemView.findViewById(R.id.txt_precio_n);



        }

        public void bid(final Canchas canchas,final OnItemClickListener listener){

            img_fotoCancha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(canchas,"img_fotoCancha",getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater mInflater;


    private List<Canchas> mUsers; // Cached copy of users
    private List<Canchas> mDataFiltered; // Cached copy of users
    @Override
    public canchaEmpresasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= mInflater.inflate(R.layout.rcv_item_card_canchas,parent,false);
        return new canchaEmpresasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final canchaEmpresasViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            universalImageLoader.setImage(IP2+"/"+ current.getFoto(),holder.img_fotoCancha,null);
            holder.txt_nombreCancha.setText(current.getNombre_cancha());
            holder.txt_dimensones.setText(current.getDimensiones_cancha());
            holder.txt_precio_d.setText(current.getPrecioD());
            holder.txt_precio_n.setText(current.getPrecioN());
            holder.bid(current,listener);
        }else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }



    }

    public void setWords(List<Canchas> users){
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
        void onItemClick(Canchas cancha,String tipo, int position);
    }

}