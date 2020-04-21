package com.tec.bufeo.capitan.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Views.ComentariosActivity;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class DetalleFotoUsuario extends AppCompatActivity {
    ImageView img_iconoPerfil ;
   PhotoViewAttacher PVAttacher;
    TextView comentarios,cantidad,tabComen;
    String foto,descripcion,cantidad_comentarios,id_publicacion;
    UniversalImageLoader universalImageLoader;
    Preferences preferences;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_foto_usuario);


        preferences= new Preferences(this);


        universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());


        img_iconoPerfil = findViewById(R.id.img_iconoPerfil) ;
        comentarios = findViewById(R.id.comentarios) ;
        cantidad = findViewById(R.id.cantidad) ;
        tabComen = findViewById(R.id.tabComen) ;
        layout = findViewById(R.id.layout) ;

        foto =  getIntent().getExtras().getString("foto");
        descripcion =  getIntent().getExtras().getString("descripcion");
        cantidad_comentarios =  getIntent().getExtras().getString("cantidad_comentarios");
        id_publicacion =  getIntent().getExtras().getString("id_publicacion");


        if (descripcion.equals("0")){
            comentarios.setVisibility(View.GONE);
        }
        if (id_publicacion.equals("0")){
            layout.setVisibility(View.GONE);
        }

        UniversalImageLoader.setImage(IP2+"/"+ foto,img_iconoPerfil,null);
        //Glide.with(getApplicationContext()).load(IP2 + "/" + foto).error(R.drawable.error).into(img_iconoPerfil);
        PVAttacher = new PhotoViewAttacher(img_iconoPerfil);

        comentarios.setText(descripcion);
        cantidad.setText(cantidad_comentarios);
        tabComen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(DetalleFotoUsuario.this, ComentariosActivity.class);
                i.putExtra("id_publicacion",id_publicacion);
                startActivity(i);
            }
        });

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
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }

}
