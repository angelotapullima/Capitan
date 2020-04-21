package com.tec.bufeo.capitan.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.R;
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
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class RegistroForo extends AppCompatActivity implements View.OnClickListener{
    EditText edt_tituloForo, edt_descripcionForo;
    MaterialButton btn_registrarForo;
    TextView nombre_para_publicar;
    ImageView img_foroFoto,publicar_foto_camara,publicar_foto_galeria;
    ImageView foto_perfil_para_publicacion;
    Preferences preferences;
    public Context context;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;
    FeedListViewModel feedListViewModel;
    BroadcastReceiver BR;
    public static final String registro= "registro";
    UniversalImageLoader universalImageLoader;
    String concepto,id_torneo;
    String id_torneo_publicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_foro);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);

        context=this;
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);
        preferences = new Preferences(context);
        universalImageLoader= new UniversalImageLoader(context);

        concepto = getIntent().getExtras().getString("concepto");
        id_torneo = getIntent().getExtras().getString("id_torneo");

        edt_tituloForo = (EditText) findViewById(R.id.edt_tituloForo);
        edt_descripcionForo = (EditText) findViewById(R.id.edt_descripcionForo);
        btn_registrarForo = (MaterialButton) findViewById(R.id.btn_registrarForo);
        img_foroFoto = findViewById(R.id.img_foroFoto);
        publicar_foto_galeria = findViewById(R.id.publicar_foto_galeria);
        publicar_foto_camara = findViewById(R.id.publicar_foto_camara);
        nombre_para_publicar = findViewById(R.id.nombre_para_publicar);
        foto_perfil_para_publicacion = findViewById(R.id.foto_perfil_para_publicacion);
        btn_registrarForo.setOnClickListener(this);


        if((ContextCompat.checkSelfPermission(RegistroForo.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(RegistroForo.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(RegistroForo.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                String to = intent.getStringExtra("tipo");
                Toast.makeText(context, "funciona" + to, Toast.LENGTH_SHORT).show();
                Log.d("registro", "onMessageReceived:  funcionando la huevada de Registro" +to );
                ModelFeed feedTorneo =  new ModelFeed();
                feedTorneo.setPublicacion_id("1");
                feedTorneo.setForo_foto("ubbbbjbfhh");
                feedTorneo.setUsuario_nombre("angelo");
                feedTorneo.setUsuario_id("18");
                feedTorneo.setForo_titulo("x2");
                feedTorneo.setForo_descripcion("x3");
                feedTorneo.setForo_feccha("Hace 0 min");
                feedTorneo.setForo_tipo("1");
                feedTorneo.setCant_likes("0");
                feedTorneo.setCant_Comentarios("0");
                feedTorneo.setDio_like("0");
                feedTorneo.setOrden("1");

                Log.d("registro", "onMessageReceived:  funcionando la huevada de tipo" + feedTorneo.toString() );
                RegistroForope(feedTorneo);
            }
        };
        showToolbar("Crear Publicación",true);

        UniversalImageLoader.setImage(IP2+"/"+ preferences.getFotoUsuario(),foto_perfil_para_publicacion,null);

        nombre_para_publicar.setText(preferences.getPersonName() + " " + preferences.getPersonSurname());
        publicar_foto_camara.setOnClickListener(this);
        publicar_foto_galeria.setOnClickListener(this);
        img_foroFoto.setOnClickListener(this);
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
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(registro));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        if (v.equals(publicar_foto_galeria)){
            galeria("");
        }else  if (v.equals(publicar_foto_camara)){
            camarex("");
        }else if (v.equals(btn_registrarForo)){
            if (!(edt_descripcionForo.getText().toString().isEmpty())&!(edt_tituloForo.getText().toString().isEmpty())) {


                if (img_foroFoto.getDrawable()== null){
                    preferences.codeAdvertencia("Debe adjuntar una imágen");
                }else{

                    uploadMultipart();

                    finish();
                }

            }else {
                preferences.codeAdvertencia("Llene los campos");
            }
        }else if (v.equals(img_foroFoto)){
            //dialogoFotos();
        }




    }

    public void RegistroForope(ModelFeed feedTorneo){
        feedListViewModel.insert(feedTorneo);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed () {
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA){

                CropImage.activity(output).start(this);
                //CropImage.activity(output).setMaxCropResultSize(1080,566 ).setMinCropResultSize(800,400).start(this);
            }

            else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();


                File f1,f2;
                f1 = new File(getRealPathFromUri(context,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/.Capitan/","Foro");
                f2.mkdirs();
                try {
                    FileUtils.copyFileToDirectory(f1,f2);
                    ContentValues values =new ContentValues();
                    values.put(MediaStore.Images.Media.DATE_TAKEN,System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
                    values.put(MediaStore.MediaColumns.DATA,f2.toString()+"/"+fname);
                    getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }  finally {
                    //Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                }


                CropImage.activity(uri).start(this);

            }
        }if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultado = CropImage.getActivityResult(result);
            if (resultCode == RESULT_OK) {

                resultUriRecortada = resultado.getUri();

                img_foroFoto.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = resultado.getError();
                //Toast.makeText(getApplicationContext(),"Error"+error, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"Error: Intente de nuevo", Toast.LENGTH_SHORT).show();

            }
        }

    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void camarex(String dialogo){
        userChoosenTask ="Camara";

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","Foro");
        carpetas.mkdirs();

        String aleatorio = preferences.getIdUsuarioPref()+"_"+ edt_tituloForo.getText().toString();
        String nombre = aleatorio +".jpg";

        File imagen = new File(carpetas,nombre);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {

            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(getApplicationContext(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));

        }
        output = Uri.fromFile(imagen);
        startActivityForResult(intent,REQUEST_CAMERA);

        if (dialogo.equals("dialogo")){
            dialog_fotos.dismiss();
        }

    }
    public void galeria(String dialogo){
        userChoosenTask ="Galería";


        Intent intentgaleria = new Intent(Intent.ACTION_PICK);
        intentgaleria.setType("image/*");
        if (intentgaleria.resolveActivity(getPackageManager())!=null){

            startActivityForResult(intentgaleria,SELET_GALERRY);
        }

        if (dialogo.equals("dialogo")){
            dialog_fotos.dismiss();
        }
    }

    AlertDialog dialog_fotos;
    public void dialogoFotos(){

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_camara_galeria,null);
        builder.setView(vista);

        dialog_fotos = builder.create();
        dialog_fotos.show();

        LinearLayout Tomarfoto= vista.findViewById(R.id.Tomarfoto);
        LinearLayout seleccionGaleria= vista.findViewById(R.id.seleccionGaleria);


        Tomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camarex("dialogo");
            }
        });
        seleccionGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeria("dialogo");
            }
        });





    }
    String url = IP2+"/api/Foro/registrar";
    String path;

    public void uploadMultipart() {
        path = resultUriRecortada.getPath();

        if (concepto.equals("publicacion")){
            id_torneo_publicacion = "0";
        }else{
            id_torneo_publicacion = id_torneo;
        }
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            PendingIntent clickIntent = PendingIntent.getActivity(
                    context, 1, new Intent(context, RegistroForo.class), PendingIntent.FLAG_UPDATE_CURRENT);
            //Creating a multi part request
            String titulex = edt_tituloForo.getText().toString();
            String descripcionex = edt_descripcionForo.getText().toString();
            new MultipartUploadRequest(context, uploadId, url)
                    .addFileToUpload(path, "imagen") //Adding file
                    .addParameter("usuario_id", preferences.getIdUsuarioPref()) //Adding text parameter to the request
                    .addParameter("titulo", titulex) //Adding text parameter to the request
                    .addParameter("descripcion", descripcionex) //Adding text parameter to the request
                    .addParameter("concepto", "publicacion") //Adding text parameter to the request
                    .addParameter("id_torneo", id_torneo_publicacion) //Adding text parameter to the request
                    .addParameter("app", "true") //Adding text parameter to the request
                    .addParameter("token", preferences.getToken()) //Adding text parameter to the request

                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)
                    .setUtf8Charset()
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.d("foro", "multipart Completed: " + serverResponse.getBodyAsString() );
                            //Toast.makeText(RegistroForo.this, "completo", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                        }
                    })

                    .startUpload(); //Starting the upload
                            /*getNotificationConfig().setTitleForAllStatuses("Cargando Imagen")
                            .setRingToneEnabled(false)
                            .setClickIntentForAllStatuses(clickIntent)
                            .setClearOnActionForAllStatuses(true))*/



        } catch (Exception exc) {
            Toast.makeText(context, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    protected UploadNotificationConfig getNotificationConfig(final String uploadId, @StringRes int title) {
        UploadNotificationConfig config = new UploadNotificationConfig();




        PendingIntent clickIntent = PendingIntent.getActivity(
                context, 1, new Intent(context, RegistroForo.class), PendingIntent.FLAG_UPDATE_CURRENT);


        config.setTitleForAllStatuses(getString(title))
                .setRingToneEnabled(false)
                .setClickIntentForAllStatuses(clickIntent);



        config.getProgress().message = "Subiendo " + UPLOADED_FILES + " de " + TOTAL_FILES
                + " a " + UPLOAD_RATE + " - " + PROGRESS;
        config.getProgress().iconResourceID = R.drawable.logo;
        config.getProgress().iconColorResourceID = Color.BLUE;
        config.getProgress().actions.add(new UploadNotificationAction(R.drawable.logo,"Ver progreso",clickIntent));



        config.getCompleted().message = "Subida completada exitosamente en " + ELAPSED_TIME;
        config.getCompleted().iconResourceID = R.drawable.logo;
        config.getCompleted().iconColorResourceID = Color.GREEN;
        config.getCompleted().actions.add(new UploadNotificationAction(R.drawable.logo,"Imagen Cargada Exitosamente",clickIntent));

        config.getError().message = "Error al Cargar Imagen";
        config.getError().iconResourceID = R.drawable.logo;
        config.getError().iconColorResourceID = Color.RED;

        config.getCancelled().message = "\n" +
                "La carga ha sido cancelada";
        config.getCancelled().iconResourceID = R.drawable.logo;
        config.getCancelled().iconColorResourceID = Color.YELLOW;

        return config;
    }
}
