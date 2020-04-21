package com.tec.bufeo.capitan.Activity.PantallasNotificacion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class RetosNotificacion extends AppCompatActivity {

    TextView tituloNotiReto,nameRetador,nameRetado,fechaRetoN,horaRetoN,lugarRetoN;
    ImageView RetadorFoto,Retadofoto;
    RetosViewModel retosViewModel;
    String id;
    Preferences preferences;
    UniversalImageLoader universalImageLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retos_notificacion);

        retosViewModel = ViewModelProviders.of(this).get(RetosViewModel.class);
        id = getIntent().getExtras().getString("id");

        preferences= new Preferences(this);
        universalImageLoader = new UniversalImageLoader(this);


        initViews();
        cargarvista();
    }

    private void initViews() {
        tituloNotiReto= findViewById(R.id.tituloNotiReto);
        RetadorFoto= findViewById(R.id.RetadorFoto);
        nameRetador= findViewById(R.id.nameRetador);

        Retadofoto= findViewById(R.id.Retadofoto);
        nameRetado= findViewById(R.id.nameRetado);

        fechaRetoN= findViewById(R.id.fechaRetoN);
        horaRetoN= findViewById(R.id.horaRetoN);
        lugarRetoN= findViewById(R.id.lugarRetoN);

    }

    public void cargarvista(){

        retosViewModel.getAllRetoID(id,preferences.getToken(),"notificacion").observe(this, new Observer<List<Retos>>() {
            @Override
            public void onChanged(@Nullable List<Retos> retos) {

                if (retos.size()>0){


                    String  texto = "Tú equipo " + retos.get(0).getRetos_nombre_retador() +" fue retado por " + retos.get(0).getRetos_nombre_retado();
                    UniversalImageLoader.setImage(IP2+"/"+ retos.get(0).getRetos_foto_retador(),RetadorFoto,null);
                    UniversalImageLoader.setImage(IP2+"/"+ retos.get(0).getRetos_foto_retado(),Retadofoto,null);


                    tituloNotiReto.setText(texto);
                    nameRetador.setText(retos.get(0).getRetos_nombre_retador());
                    nameRetado.setText(retos.get(0).getRetos_nombre_retado());
                    horaRetoN.setText(retos.get(0).getRetos_hora());
                    fechaRetoN.setText(retos.get(0).getRetos_fecha());
                    lugarRetoN.setText(retos.get(0).getRetos_lugar());
                }
            }
        });

    }
}
