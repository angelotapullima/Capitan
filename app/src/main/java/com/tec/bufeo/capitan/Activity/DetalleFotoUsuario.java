package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Views.ComentariosActivity;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationAction;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;


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
