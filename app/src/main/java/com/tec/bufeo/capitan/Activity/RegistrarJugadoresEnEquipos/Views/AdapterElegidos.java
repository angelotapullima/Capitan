package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.JugadoresSeleccionados;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterElegidos extends RecyclerView.Adapter<AdapterElegidos.ElegidosViewHolder>  {

    UniversalImageLoader universalImageLoader;
    JugadoresSeleccionados current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class ElegidosViewHolder extends RecyclerView.ViewHolder {
        CircleImageView foto_jugador_elegido;
        RelativeLayout contenedor_elegidos;
        TextView name_juegador;

        private ElegidosViewHolder(View itemView) {
            super(itemView);

            foto_jugador_elegido= itemView.findViewById(R.id.foto_jugador_elegido);
            contenedor_elegidos= itemView.findViewById(R.id.contenedor_elegidos);
            name_juegador= itemView.findViewById(R.id.name_juegador);


        }

        public void bid(final JugadoresSeleccionados jugadores,final OnItemClickListener listener) {

            contenedor_elegidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(jugadores,"contenedor_elegidos" , getAdapterPosition());
                }
            });

        }
    }

    private final LayoutInflater mInflater;


    private List<JugadoresSeleccionados> mUsers; // Cached copy of users


    public AdapterElegidos(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public ElegidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_elegidos, parent, false);
        return new ElegidosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ElegidosViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            UniversalImageLoader.setImage(IP2+"/"+ current.getJugadors_foto(),holder.foto_jugador_elegido,null);

            holder.name_juegador.setText(current.getJugadors_nombre());
            /*holder.txt_nombreUsuario.setText(current.getJugador_nombre());
            holder.txt_habilidadUsuario.setText(current.getJugador_habilidad());
            holder.txt_numUsuario.setText(current.getJugador_numero());
            holder.txt_posicionUsuario.setText(current.getJugador_posicion());*/
            holder.bid(current,listener);


            //holder.imgbt_comment.setOnClickListener(this);




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<JugadoresSeleccionados> users){
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
        void onItemClick(JugadoresSeleccionados jugadores,String tipo, int position);
    }

}