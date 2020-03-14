package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorMiEquipo extends RecyclerView.Adapter<AdaptadorMiEquipo.EquiposViewHolder>  {


    Mequipos current;
    String tipo;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;
    UniversalImageLoader universalImageLoader;


    class EquiposViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_fotoEquipo,img_fotoEquipo_300;
        private TextView txt_nombreEquipo,txt_nombreEquipo_300;
        private CardView cdv_foto_equipo,card_300;

        private EquiposViewHolder(View itemView) {
            super(itemView);

            img_fotoEquipo= (ImageView) itemView.findViewById(R.id.img_fotoEquipo);
            img_fotoEquipo_300= (ImageView) itemView.findViewById(R.id.img_fotoEquipo_300);
            txt_nombreEquipo_300 = (TextView) itemView.findViewById(R.id.txt_nombreEquipo_300);
            txt_nombreEquipo = (TextView) itemView.findViewById(R.id.txt_nombreEquipo);
            cdv_foto_equipo = (CardView) itemView.findViewById(R.id.cdv_foto_equipo);
            card_300 = (CardView) itemView.findViewById(R.id.card_300);

        }

        public void bid(final Mequipos mequipos,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos , getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Mequipos> mUsers; // Cached copy of users


    public AdaptadorMiEquipo(Context context,String tipo, OnItemClickListener listener) {
        this.ctx=context;
        this.tipo=tipo;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public EquiposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_card_equipos, parent, false);
        return new EquiposViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);
            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            if (tipo.equals("mas")){

                //Glide.with(ctx).load(IP2+"/"+ current.getEquipo_foto()).into(holder.img_fotoEquipo);
                UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.img_fotoEquipo,null);

                holder.txt_nombreEquipo.setText(current.getEquipo_nombre());
                //holder.txt_nombreEquipo_300.setText(current.getEquipo_nombre());

                holder.card_300.setVisibility(View.GONE);
            }else{
                UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.img_fotoEquipo_300,null);
                //Glide.with(ctx).load(IP2+"/"+ current.getEquipo_foto()).into(holder.img_fotoEquipo_300);

                //holder.txt_nombreEquipo.setText(current.getEquipo_nombre());
                holder.txt_nombreEquipo_300.setText(current.getEquipo_nombre());
                holder.cdv_foto_equipo.setVisibility(View.GONE);


            }




            holder.txt_nombreEquipo.setText(current.getEquipo_nombre());
            holder.bid(current,listener);

        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Mequipos> users){
        mUsers = users;
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
        void onItemClick(Mequipos mequipos , int position);
    }

}