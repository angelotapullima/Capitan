package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorEquipos extends RecyclerView.Adapter<AdaptadorEquipos.EquiposViewHolder>  {


    Mequipos current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;
    UniversalImageLoader universalImageLoader;
    String tipo;


    class EquiposViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_fotoEquipoRetar250;
        private TextView txt_nombreEquipoRetar250,txt_capitanReto250;
        private Button btn_retar250;
        private CardView cardMatch;

        private EquiposViewHolder(View itemView) {
            super(itemView);

            civ_fotoEquipoRetar250=  itemView.findViewById(R.id.civ_fotoEquipoRetar250);
            txt_nombreEquipoRetar250 = itemView.findViewById(R.id.txt_nombreEquipoRetar250);
            txt_capitanReto250 = itemView.findViewById(R.id.txt_capitanReto250);
            btn_retar250 = itemView.findViewById(R.id.btn_retar250);
            cardMatch = itemView.findViewById(R.id.cardMatch);

        }

        public void bid(final Mequipos mequipos,final OnItemClickListener listener){
            btn_retar250.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"btn_retar250",getAdapterPosition());
                }
            });
            txt_nombreEquipoRetar250.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"txt_nombreEquipoRetar",getAdapterPosition());
                }
            });
            civ_fotoEquipoRetar250.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"civ_fotoEquipoRetar",getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Mequipos> mUsers; // Cached copy of users


    public AdaptadorEquipos(Context context,String tipo, OnItemClickListener listener) {
        this.ctx=context;
        this.tipo=tipo;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        universalImageLoader= new UniversalImageLoader(context);
        this.listener = listener;}

    @NonNull
    @Override
    public EquiposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_equipos_general_retar, parent, false);
        return new EquiposViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final EquiposViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            if (tipo.equals("normal")){
                holder.cardMatch.setVisibility(View.GONE);
                ImageLoader.getInstance().init(universalImageLoader.getConfig());
                UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.civ_fotoEquipoRetar250,null);

                holder.txt_nombreEquipoRetar250.setText(current.getEquipo_nombre());
                holder.txt_capitanReto250.setText(current.getCapitan_nombre());
                holder.bid(current,listener);
            }




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
        void onItemClick(Mequipos mequipos,String tipo, int position);
    }

}