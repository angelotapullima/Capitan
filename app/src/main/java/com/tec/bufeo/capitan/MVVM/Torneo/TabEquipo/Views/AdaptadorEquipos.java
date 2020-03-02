package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class AdaptadorEquipos extends RecyclerView.Adapter<AdaptadorEquipos.EquiposViewHolder>  {


    Mequipos current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class EquiposViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_fotoEquipoRetar;
        private TextView txt_nombreEquipoRetar,txt_capitanReto;
        private Button btn_retar;

        private EquiposViewHolder(View itemView) {
            super(itemView);

            civ_fotoEquipoRetar=  itemView.findViewById(R.id.civ_fotoEquipoRetar);
            txt_nombreEquipoRetar = itemView.findViewById(R.id.txt_nombreEquipoRetar);
            txt_capitanReto = itemView.findViewById(R.id.txt_capitanReto);
            btn_retar = itemView.findViewById(R.id.btn_retar);

        }

        public void bid(final Mequipos mequipos,final OnItemClickListener listener){
            btn_retar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"btn_retar",getAdapterPosition());
                }
            });
            txt_nombreEquipoRetar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(mequipos,"txt_nombreEquipoRetar",getAdapterPosition());
                }
            });
            civ_fotoEquipoRetar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(ctx, RegistroReto.class);
                    intent.putExtra("nombre_retado",mequipos.getEquipo_nombre());
                    intent.putExtra("foto_retado",mequipos.getEquipo_foto());
                    intent.putExtra("id_retado",mequipos.getEquipo_id());
                    intent.putExtra("capitan_id",mequipos.getCapitan_id());
                    ctx.startActivity(intent);*/
                    listener.onItemClick(mequipos,"civ_fotoEquipoRetar",getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Mequipos> mUsers; // Cached copy of users


    public AdaptadorEquipos(Context context, OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        preferencesUser = new Preferences(context);
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

            Glide.with(ctx).load(IP2+"/"+ current.getEquipo_foto()).into(holder.civ_fotoEquipoRetar);

            holder.txt_nombreEquipoRetar.setText(current.getEquipo_nombre());
            holder.txt_capitanReto.setText(current.getCapitan_nombre());
            holder.bid(current,listener);


            //holder.imgbt_comment.setOnClickListener(this);




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
        void onItemClick(Mequipos mequipos,String tipo, int position);
    }

}