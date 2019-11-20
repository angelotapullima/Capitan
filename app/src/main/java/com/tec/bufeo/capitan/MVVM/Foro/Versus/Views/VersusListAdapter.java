package com.tec.bufeo.capitan.MVVM.Foro.Versus.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

public class VersusListAdapter extends RecyclerView.Adapter<VersusListAdapter.VersusViewHolder>  {

    UniversalImageLoader universalImageLoader;
    Versus current;
    Context ctx;
    private  OnItemClickListener listener;
    Preferences preferencesUser;


    class VersusViewHolder extends RecyclerView.ViewHolder {
        private ImageView versus_foto_equipo1,versus_foto_equipo2,versus_jugador1a,versus_jugador1b, versus_jugador1c,
                versus_jugador1d,versus_jugador1e,versus_jugador1f,versus_jugador1g,versus_jugador1h,versus_jugador2a ,
                versus_jugador2b,versus_jugador2c,versus_jugador2d,versus_jugador2e,versus_jugador2f,  versus_jugador2g,versus_jugador2h ;
        private TextView versus_equipo1, versus_equipo2,versus_lugar,versus_fecha;


        private VersusViewHolder(View itemView) {
            super(itemView);
            versus_equipo1=  itemView.findViewById(R.id.versus_equipo1);
            versus_foto_equipo1=  itemView.findViewById(R.id.versus_foto_equipo1);
            versus_jugador1a=  itemView.findViewById(R.id.versus_jugador1a);
            versus_jugador1b=  itemView.findViewById(R.id.versus_jugador1b);
            versus_jugador1c=  itemView.findViewById(R.id.versus_jugador1c);
            versus_jugador1d=  itemView.findViewById(R.id.versus_jugador1d);
            versus_jugador1e=  itemView.findViewById(R.id.versus_jugador1e);
            versus_jugador1f=  itemView.findViewById(R.id.versus_jugador1f);
            versus_jugador1g=  itemView.findViewById(R.id.versus_jugador1g);
            versus_jugador1h=  itemView.findViewById(R.id.versus_jugador1h);

            versus_equipo2=  itemView.findViewById(R.id.versus_equipo2);
            versus_foto_equipo2=  itemView.findViewById(R.id.versus_foto_equipo2);
            versus_jugador2a=  itemView.findViewById(R.id.versus_jugador2a);
            versus_jugador2b=  itemView.findViewById(R.id.versus_jugador2b);
            versus_jugador2c=  itemView.findViewById(R.id.versus_jugador2c);
            versus_jugador2d=  itemView.findViewById(R.id.versus_jugador2d);
            versus_jugador2e=  itemView.findViewById(R.id.versus_jugador2e);
            versus_jugador2f=  itemView.findViewById(R.id.versus_jugador2f);
            versus_jugador2g=  itemView.findViewById(R.id.versus_jugador2g);
            versus_jugador2h=  itemView.findViewById(R.id.versus_jugador2h);


            versus_lugar=  itemView.findViewById(R.id.versus_lugar);
            versus_fecha=  itemView.findViewById(R.id.versus_fecha);






        }

        public void bid(final Versus versus,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(versus, getAdapterPosition());
                }
            });


        }
    }

    private final LayoutInflater mInflater;


    private List<Versus> mUsers; // Cached copy of users


    VersusListAdapter(Context context , OnItemClickListener listener) {
        this.ctx=context;
        mInflater = LayoutInflater.from(context);
        universalImageLoader = new UniversalImageLoader(context);
        preferencesUser = new Preferences(context);
        this.listener = listener;}

    @NonNull
    @Override
    public VersusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rcv_versus, parent, false);
        return new VersusViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final VersusViewHolder holder, int position) {
        if (mUsers != null) {
            current = mUsers.get(position);

            ImageLoader.getInstance().init(universalImageLoader.getConfig());

            holder.versus_equipo1.setText(current.getVersus_equipo1());
            UniversalImageLoader.setImage(current.getVersus_foto_equipo1(),holder.versus_foto_equipo1,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1a(),holder.versus_jugador1a,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1b(),holder.versus_jugador1b,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1c(),holder.versus_jugador1c,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1d(),holder.versus_jugador1d,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1e(),holder.versus_jugador1e,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1f(),holder.versus_jugador1f,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1g(),holder.versus_jugador1g,null);
            UniversalImageLoader.setImage(current.getVersus_jugador1h(),holder.versus_jugador1h,null);



            holder.versus_equipo2.setText(current.getVersus_equipo2());
            UniversalImageLoader.setImage(current.getVersus_foto_equipo2(),holder.versus_foto_equipo2,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2a(),holder.versus_jugador2a,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2b(),holder.versus_jugador2b,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2c(),holder.versus_jugador2c,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2d(),holder.versus_jugador2d,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2e(),holder.versus_jugador2e,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2f(),holder.versus_jugador2f,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2g(),holder.versus_jugador2g,null);
            UniversalImageLoader.setImage(current.getVersus_jugador2h(),holder.versus_jugador2h,null);
            //Picasso.with(ctx).load("http://"+IP+"/"+ current.getForo_foto()).into(holder.img_fotoForo);

            holder.versus_lugar.setText(current.getVersus_lugar());
            holder.versus_fecha.setText(current.getVersus_fecha());


            //holder.imgbt_comment.setOnClickListener(this);




            holder.bid(current,listener);
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }
    void setWords(List<Versus> users){
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
        void onItemClick(Versus comments, int position);
    }

}