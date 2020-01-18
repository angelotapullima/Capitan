package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdapterJugadores extends RecyclerView.Adapter<AdapterJugadores.JugadoresViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Jugadores current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class JugadoresViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_fotoUsuario;
        private CardView contenedor_jugadores;
        private TextView txt_nombreUsuario,txt_posicionUsuario,txt_habilidadUsuario,txt_numUsuario;

        private JugadoresViewHolder(View itemView) {
            super(itemView);

            civ_fotoUsuario=  itemView.findViewById(R.id.civ_fotoUsuario);
            txt_nombreUsuario = itemView.findViewById(R.id.txt_nombreUsuario);
            txt_posicionUsuario = itemView.findViewById(R.id.txt_posicionUsuario);
            txt_habilidadUsuario = itemView.findViewById(R.id.txt_habilidadUsuario);
            txt_numUsuario = itemView.findViewById(R.id.txt_numUsuario);
            contenedor_jugadores = itemView.findViewById(R.id.contenedor_jugadores);

        }

        public void bid(final Jugadores jugadores,final OnItemClickListener listener){


            contenedor_jugadores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(jugadores,"contenedor_jugadores" , getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Jugadores> mUsers; // Cached copy of users


    public AdapterJugadores(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public JugadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_item_list_usuarios_por_equipo, parent, false);
        return new JugadoresViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final JugadoresViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            UniversalImageLoader.setImage(IP2+"/"+ current.getJugador_foto(),holder.civ_fotoUsuario,null);

            holder.txt_nombreUsuario.setText(current.getJugador_nombre());
            holder.txt_habilidadUsuario.setText(current.getJugador_estado());
            holder.txt_numUsuario.setText(current.getJugador_numero());
            holder.txt_posicionUsuario.setText(current.getJugador_posicion());
            holder.bid(current,listener);


            //holder.imgbt_comment.setOnClickListener(this);




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Jugadores> users){
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
        void onItemClick(Jugadores mequipos,String tipo, int position);
    }

}