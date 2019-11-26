package com.tec.bufeo.capitan.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class AdaptadorListadoUsuarioPorEquipo extends RecyclerView.Adapter<AdaptadorListadoUsuarioPorEquipo.usuarioViewHolder> {

    private ArrayList<Usuario> array;
    private int layoutpadre;
    Context context;
    Usuario obj;
    private  OnItemClickListener listener;

    public AdaptadorListadoUsuarioPorEquipo() {
    }

    public AdaptadorListadoUsuarioPorEquipo(Context context, ArrayList<Usuario> array, int layoutpadre, OnItemClickListener listener) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
        this.listener = listener;
    }

    public class usuarioViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView civ_fotoUsuario;
        private TextView txt_nombreUsuario,txt_numUsuario,txt_posicionUsuario,txt_habilidadUsuario;


        public usuarioViewHolder(View itemView) {
            super(itemView);
            civ_fotoUsuario=(CircleImageView)itemView.findViewById(R.id.civ_fotoUsuario);
            txt_nombreUsuario = (TextView) itemView.findViewById(R.id.txt_nombreUsuario);
            txt_numUsuario = (TextView) itemView.findViewById(R.id.txt_numUsuario);
            txt_posicionUsuario = (TextView)itemView.findViewById(R.id.txt_posicionUsuario);
            txt_habilidadUsuario = (TextView)itemView.findViewById(R.id.txt_habilidadUsuario);
        }

        public void bid(final Usuario usuario,final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(usuario,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public usuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(layoutpadre,parent,false);
        return new usuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final usuarioViewHolder holder, int position) {

        obj = array.get(position);

        Picasso.with(context).load(IP+"/"+ obj.getUsuario_foto()).into(holder.civ_fotoUsuario);
        holder.txt_nombreUsuario.setText(obj.getUsuario_nombre());
        holder.txt_numUsuario.setText(obj.getUsuario_numFavorito());
        holder.txt_posicionUsuario.setText(obj.getUsuario_posicion());
        holder.txt_habilidadUsuario.setText(obj.getUsuario_habilidad());
        holder.bid(obj,listener);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public interface  OnItemClickListener{
        void onItemClick(Usuario usuario, int position);
    }

}