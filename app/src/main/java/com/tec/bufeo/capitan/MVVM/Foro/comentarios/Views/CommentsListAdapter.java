package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models.Comments;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ComentariosViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Comments current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class ComentariosViewHolder extends RecyclerView.ViewHolder {
        private ImageView comentarios_foto;
        private TextView comentarios_nombre, comentarios_descripcion,fecha_comentario;


        private ComentariosViewHolder(View itemView) {
            super(itemView);
            comentarios_nombre=  itemView.findViewById(R.id.comentarios_nombre);
            comentarios_foto=  itemView.findViewById(R.id.comentarios_foto);
            comentarios_descripcion=  itemView.findViewById(R.id.comentarios_descripcion);
            fecha_comentario=  itemView.findViewById(R.id.fecha_comentario);

        }

        public void bid(final Comments comments,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(comments , getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Comments> mUsers; // Cached copy of users


    CommentsListAdapter(Context context ,OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_comentarios, parent, false);
        return new ComentariosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ComentariosViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            UniversalImageLoader.setImage(IP+"/"+ current.getComments_foto(),holder.comentarios_foto,null);
            //Picasso.with(ctx).load("http://"+IP+"/"+ current.getForo_foto()).into(holder.img_fotoForo);
            holder.comentarios_descripcion.setText(current.getComments_comentario());
            holder.comentarios_nombre.setText(current.getComments_nombre());
            holder.fecha_comentario.setText(current.getComments_fecha());


            //holder.imgbt_comment.setOnClickListener(this);




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<Comments> users){
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
        void onItemClick(Comments comments, int position);
    }

}