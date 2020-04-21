package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Views;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Models.Mensajes;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

public class AdapterMensajes extends RecyclerView.Adapter<AdapterMensajes.MensajesViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Mensajes current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class MensajesViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView message,fecha_mensaje;
        LinearLayout contenedorMensajes;


        private MensajesViewHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardMensajesCliente);
            contenedorMensajes=itemView.findViewById(R.id.contenedorMensajes);
            message=itemView.findViewById(R.id.textMensajesCliente);
            fecha_mensaje=itemView.findViewById(R.id.fecha_mensaje);

        }

        public void bid(final Mensajes chats,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(chats , getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Mensajes> mUsers; // Cached copy of users


    public AdapterMensajes(Context context ,OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.mensajes, parent, false);
        return new MensajesViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MensajesViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());


            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)holder.cardView.getLayoutParams();
            FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams)holder.contenedorMensajes.getLayoutParams();
            if (current.getMensajes_id_usuario().equals(preferencesUser.getIdUsuarioPref())){
                holder.contenedorMensajes.setBackgroundResource(R.drawable.black);

                rl.addRule(RelativeLayout.ALIGN_PARENT_END,0);
                rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                fl.gravity= Gravity.RIGHT;
                holder.message.setGravity(Gravity.RIGHT);
                holder.fecha_mensaje.setGravity(Gravity.RIGHT);
            } else {
                holder.contenedorMensajes.setBackgroundResource(R.drawable.accent);
                rl.addRule(RelativeLayout.ALIGN_PARENT_START,0);
                rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                fl.gravity= Gravity.LEFT;

                holder.message.setGravity(Gravity.LEFT);
                holder.fecha_mensaje.setGravity(Gravity.LEFT);
            }
            holder.cardView.setLayoutParams(rl);
            holder.contenedorMensajes.setLayoutParams(fl);

            holder.message.setText(current.getMensaje_contenido());
            holder.fecha_mensaje.setText(current.getMensaje_hora());
            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP)holder.cardView.getBackground().setAlpha(0);
            else holder.cardView.setBackgroundColor(ContextCompat.getColor(ctx,android.R.color.transparent));




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    public void setWords(List<Mensajes> users){
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
        void onItemClick(Mensajes comments, int position);
    }

}