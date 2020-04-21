package com.tec.bufeo.capitan.TabsPrincipales.Negocios.Views;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
public class AdaptadorListadoEmpresas extends RecyclerView.Adapter<AdaptadorListadoEmpresas.EmpresasViewHolder> {

    UniversalImageLoader universalImageLoader;
    Negocios current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;

    public AdaptadorListadoEmpresas(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;
    }



    public class EmpresasViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_fotoEmpresa;
        private TextView txt_nombreEmpresa,txt_direccionEmpresa;


        public EmpresasViewHolder(View itemView) {
            super(itemView);

            img_fotoEmpresa= (ImageView) itemView.findViewById(R.id.img_fotoEmpresa);
            txt_nombreEmpresa = (TextView) itemView.findViewById(R.id.txt_nombreEmpresa);
            txt_direccionEmpresa = (TextView) itemView.findViewById(R.id.txt_direccionEmpresa);



        }

        public void bid(final Negocios negocios,final OnItemClickListener listener){

            img_fotoEmpresa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(negocios,"img_fotoEmpresa",getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater mInflater;


    private List<Negocios> mUsers; // Cached copy of users
    private List<Negocios> mDataFiltered; // Cached copy of users
    @Override
    public EmpresasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= mInflater.inflate(R.layout.rcv_item_card_negocios,parent,false);
        return new EmpresasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EmpresasViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            universalImageLoader.setImage(IP2+"/"+ current.getFoto_empresa(),holder.img_fotoEmpresa,null);
            holder.txt_nombreEmpresa.setText(current.getNombre_empresa());
            holder.txt_direccionEmpresa.setText(current.getDireccion_empresa());
            holder.bid(current,listener);
        }else {
        // Covers the case of data not being ready yet.
        // holder.userNameView.setText("No Word");
        }



    }

    public void setWords(List<Negocios> users){
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
        void onItemClick(Negocios negocios ,String tipo, int position);
    }

}