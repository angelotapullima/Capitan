package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatsViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Chats current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class ChatsViewHolder extends RecyclerView.ViewHolder {

        private TextView textCapi,txtHorra,txtmensashe;


        private ChatsViewHolder(View itemView) {
            super(itemView);

            textCapi =  itemView.findViewById(R.id.textCapi);
            txtHorra =  itemView.findViewById(R.id.txtHorra);
            txtmensashe =  itemView.findViewById(R.id.txtmensashe);

        }

        public void bid(final Chats chats, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(chats, getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Chats> mUsers; // Cached copy of users


    ChatsListAdapter(Context context , OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_list_chats, parent, false);
        return new ChatsViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ChatsViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            //UniversalImageLoader.setImage("http://"+IP+"/"+ current.getComments_foto(),holder.comentarios_foto,null);
            //Picasso.with(ctx).load("http://"+IP+"/"+ current.getForo_foto()).into(holder.img_fotoForo);
            holder.textCapi.setText(current.getChat_usuario());
            holder.txtmensashe.setText(current.getChat_ultimo_mensaje());
            holder.txtHorra.setText(current.getChat_ultimo_mensaje_hora());


            //holder.imgbt_comment.setOnClickListener(this);




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<Chats> users){
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
        void onItemClick(Chats chats, int position);
    }

}