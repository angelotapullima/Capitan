package com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Models.Notificaciones;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

public class AdapterNotificacionesList extends RecyclerView.Adapter<AdapterNotificacionesList.NotificacionesViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Notificaciones current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class NotificacionesViewHolder extends RecyclerView.ViewHolder {
        private ImageView notificacion_foto;
        private TextView notificacion_mensaje,notificacion_datetime;
        private RelativeLayout RlNotificaciones;

        private NotificacionesViewHolder(View itemView) {
            super(itemView);
            notificacion_foto=  itemView.findViewById(R.id.notificacion_foto);
            notificacion_mensaje=  itemView.findViewById(R.id.notificacion_mensaje);
            RlNotificaciones=  itemView.findViewById(R.id.RlNotificaciones);
            notificacion_datetime=  itemView.findViewById(R.id.notificacion_datetime);






        }

        public void bid(final Notificaciones versus,final OnItemClickListener listener){
            RlNotificaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(versus,"RlNotificaciones", getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Notificaciones> mUsers; // Cached copy of users


    AdapterNotificacionesList(Context context , OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public NotificacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_notificaciones, parent, false);
        return new NotificacionesViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final NotificacionesViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            holder.notificacion_datetime.setText(current.getNotificacion_datetime());
            holder.notificacion_mensaje.setText(current.getNotificacion_mensaje());

            if (current.getNotificacion_estado().equals("0")){
                holder.RlNotificaciones.setBackgroundColor(Color.argb(50,35,159,35));
            }else {
                holder.RlNotificaciones.setBackgroundColor(Color.WHITE);
            }
            //UniversalImageLoader.setImage(current.getVersus_foto_equipo1(),holder.versus_foto_equipo1,null);

            if (current.getNotificacion_tipo().equals("Reto")){

                holder.notificacion_foto.setImageResource(R.drawable.retos);
            }else if (current.getNotificacion_tipo().equals("Mensaje")){
                holder.notificacion_foto.setImageResource(R.drawable.chats);
            }else if (current.getNotificacion_tipo().equals("Torneo")){
                holder.notificacion_foto.setImageResource(R.drawable.torneos);
            }


            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<Notificaciones> users){
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
        void onItemClick(Notificaciones comments,String tipo, int position);
    }





}