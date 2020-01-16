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
        private CircleImageView civ_fotoEquipoRetar;
        private TextView txt_nombreEquipoRetar,txt_capitanReto;
        private Button btn_retar;

        private JugadoresViewHolder(View itemView) {
            super(itemView);

            civ_fotoEquipoRetar=  itemView.findViewById(R.id.civ_fotoEquipoRetar);
            txt_nombreEquipoRetar = itemView.findViewById(R.id.txt_nombreEquipoRetar);
            txt_capitanReto = itemView.findViewById(R.id.txt_capitanReto);
            btn_retar = itemView.findViewById(R.id.btn_retar);

        }

        public void bid(final Jugadores jugadores,final OnItemClickListener listener){
            btn_retar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(ctx, RegistroReto.class);
                    intent.putExtra("nombre_retado",mequipos.getEquipo_nombre());
                    intent.putExtra("foto_retado",mequipos.getEquipo_foto());
                    intent.putExtra("id_retado",mequipos.getEquipo_id());
                    intent.putExtra("capitan_id",mequipos.getCapitan_id());
                    ctx.startActivity(intent);*/
                    // listener.onItemClick(reserva,getAdapterPosition());
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
        View itemView = mInflater.inflate(R.layout.rcv_item_list_equipos_general_retar, parent, false);
        return new JugadoresViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final JugadoresViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            /*UniversalImageLoader.setImage(IP2+"/"+ current.getEquipo_foto(),holder.civ_fotoEquipoRetar,null);

            holder.txt_nombreEquipoRetar.setText(current.getEquipo_nombre());
            holder.txt_capitanReto.setText(current.getCapitan_nombre());
            holder.bid(current,listener);*/


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
        void onItemClick(Jugadores mequipos, int position);
    }

}