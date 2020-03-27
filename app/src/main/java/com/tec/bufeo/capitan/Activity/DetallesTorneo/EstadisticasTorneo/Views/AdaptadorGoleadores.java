package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import org.json.JSONObject;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorGoleadores extends RecyclerView.Adapter<AdaptadorGoleadores.GoleadoresViewHolder> {


    Goleadores current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;
    UniversalImageLoader universalImageLoader;



    class GoleadoresViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_image,equipo_foto;
        private TextView user_nickname, equipo_nombre, goles;


        private GoleadoresViewHolder(View itemView) {
            super(itemView);
            user_image=  itemView.findViewById(R.id.user_image);
            equipo_foto=  itemView.findViewById(R.id.equipo_foto);
            user_nickname=  itemView.findViewById(R.id.user_nickname);
            equipo_nombre=  itemView.findViewById(R.id.equipo_nombre);
            goles = itemView.findViewById(R.id.goles);
        }

        public void bid(final Goleadores goleadores, final OnItemClickListener listener){



        }
    }

    private final LayoutInflater mInflater;


    private List<Goleadores> mUsers; // Cached copy of users
    private List<Goleadores> mDataFiltered; // Cached copy of users


    public AdaptadorGoleadores(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
        universalImageLoader= new UniversalImageLoader(context);
        this.listener = listener;}

    @NonNull
    @Override
    public GoleadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_goleadores, parent, false);
        return new GoleadoresViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final GoleadoresViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());
            UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.equipo_foto,null);
            UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.user_image,null);


            holder.user_nickname.setText(current.getUser_nickname());
            holder.goles.setText(current.getGoles());
            holder.equipo_nombre.setText(current.getEquipo_nombre());

            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Goleadores> users){
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
        void onItemClick(String dato, Goleadores feedTorneo, int position);
    }

}