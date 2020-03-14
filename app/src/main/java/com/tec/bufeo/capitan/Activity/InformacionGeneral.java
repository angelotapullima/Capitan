package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

public class InformacionGeneral extends AppCompatActivity implements View.OnClickListener {

    TextView UsuarioNickname,DniDelUsuario,DireccionDelUsuario,CamisetaDelUsuario,
            PosicionDelUsuario,HabilidadDelUsuario;

    Preferences preferences;
    RelativeLayout usuario,dni,direccion,NumeroCamiseta,posicionJugadors,habilidadDestacada,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_general);

         preferences = new Preferences(this);


        UsuarioNickname= findViewById(R.id.UsuarioNickname);
        DniDelUsuario= findViewById(R.id.DniDelUsuario);
        DireccionDelUsuario= findViewById(R.id.DireccionDelUsuario);
        CamisetaDelUsuario= findViewById(R.id.CamisetaDelUsuario);
        PosicionDelUsuario= findViewById(R.id.PosicionDelUsuario);
        HabilidadDelUsuario= findViewById(R.id.HabilidadDelUsuario);




        Password= findViewById(R.id.Password);
        usuario= findViewById(R.id.usuario);
        dni= findViewById(R.id.dni);
        direccion= findViewById(R.id.direccion);
        NumeroCamiseta= findViewById(R.id.NumeroCamiseta);
        posicionJugadors= findViewById(R.id.posicionJugadors);
        habilidadDestacada= findViewById(R.id.habilidadDestacada);



        UsuarioNickname.setText(preferences.getNickname());
        DniDelUsuario.setText(preferences.getDni());
        DireccionDelUsuario.setText(preferences.getDireccion());
        CamisetaDelUsuario.setText(preferences.getNumeroCamiseta());
        PosicionDelUsuario.setText(preferences.getPosicionJugador());
        HabilidadDelUsuario.setText(preferences.getHabilidadJuegador());




        usuario.setOnClickListener(this);
        dni.setOnClickListener(this);
        direccion.setOnClickListener(this);
        NumeroCamiseta.setOnClickListener(this);
        posicionJugadors.setOnClickListener(this);
        habilidadDestacada.setOnClickListener(this);
        Password.setOnClickListener(this);



        showToolbar("",true);
    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);//y habilitamos la flacha hacia atras

    }

    @Override
    public void onClick(View v) {

        if (v.equals(usuario)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","UsuarioNickname");
            startActivity(i);
        }else if (v.equals(dni)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","DniDelUsuario");
            startActivity(i);
        }else if (v.equals(direccion)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","DireccionDelUsuario");
            startActivity(i);
        }else if (v.equals(NumeroCamiseta)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","CamisetaDelUsuario");
            startActivity(i);
        }else if (v.equals(posicionJugadors)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","PosicionDelUsuario");
            startActivity(i);
        }else if (v.equals(habilidadDestacada)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","HabilidadDelUsuario");
            startActivity(i);
        }else if (v.equals(Password)){
            Intent i = new Intent(InformacionGeneral.this,EditarDatosDePerfil.class);
            i.putExtra("tipo","Password");
            startActivity(i);
        }
    }
}
