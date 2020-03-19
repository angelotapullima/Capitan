package com.tec.bufeo.capitan.Activity;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Views.RegistrarJugadoresEnEquipos;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.RegistroTorneo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.others.Equipo;
import com.theartofdev.edmodo.cropper.CropImage;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationAction;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class CrearEquipos extends AppCompatActivity implements View.OnClickListener {

    EditText edt_nombreEquipo;
    Button btn_registrarEquipo;
    ImageView img_equipoFoto,publicar_foto_camara,publicar_foto_galeria,foto_perfil_para_publicacion;
    public Equipo equipo;
    public Context context;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;
    TextView nombre_para_publicar;
    UniversalImageLoader universalImageLoader;

    Preferences preferences;
    String url = IP2+"/api/Torneo/registrar_equipo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipos);


        preferences = new Preferences(getApplicationContext());
        universalImageLoader= new UniversalImageLoader(getApplicationContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());


        edt_nombreEquipo = (EditText) findViewById(R.id.edt_nombreEquipo);
        btn_registrarEquipo = (Button) findViewById(R.id.btn_registrarEquipo);
        img_equipoFoto = findViewById(R.id.img_equipoFoto);
        foto_perfil_para_publicacion = findViewById(R.id.foto_perfil_para_publicacion);
        publicar_foto_camara = findViewById(R.id.publicar_foto_camara);
        publicar_foto_galeria = findViewById(R.id.publicar_foto_galeria);
        nombre_para_publicar = findViewById(R.id.nombre_para_publicar);
        btn_registrarEquipo.setOnClickListener(this);
        img_equipoFoto.setOnClickListener(this);
        publicar_foto_camara.setOnClickListener(this);
        publicar_foto_galeria.setOnClickListener(this);
        context = getApplicationContext();

        if((ContextCompat.checkSelfPermission(CrearEquipos.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(CrearEquipos.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(CrearEquipos.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

        UniversalImageLoader.setImage(IP2+"/"+ preferences.getFotoUsuario(),foto_perfil_para_publicacion,null);

        nombre_para_publicar.setText(preferences.getPersonName() + " " + preferences.getPersonSurname());
        showToolbar("Crear un Equipo",true);
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
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA){

                //CropImage.activity(output).start(this);
                CropImage.activity(output).setMaxCropResultSize(1080,566 ).setMinCropResultSize(800,400).start(this);
            }

            else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();


                File f1,f2;
                f1 = new File(getRealPathFromUri(context,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/.Capitan/","Equipo");
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


                }  finally {

                }


                CropImage.activity(uri).start(this);

            }
        }if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultado = CropImage.getActivityResult(result);
            if (resultCode == RESULT_OK) {

                resultUriRecortada = resultado.getUri();

                img_equipoFoto.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = resultado.getError();

                preferences.toasRojo("Error","Intente de nuevo");

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
                camarex();
            }
        });
        seleccionGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeria();
            }
        });





    }

    private void camarex() {
        userChoosenTask ="Camara";

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","Equipo");
        carpetas.mkdirs();

        String aleatorio = preferences.getIdUsuarioPref()+"_"+ edt_nombreEquipo.getText().toString();
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
        if ( dialog_fotos!= null ){
            //if (&& dialog_fotos.isShowing()){
            dialog_fotos.dismiss();
        }

    }

    private void galeria() {
        userChoosenTask ="Galería";


        Intent intentgaleria = new Intent(Intent.ACTION_PICK);
        intentgaleria.setType("image/*");
        if (intentgaleria.resolveActivity(getPackageManager())!=null){


            startActivityForResult(intentgaleria,SELET_GALERRY);
        }
        if (dialog_fotos!=null){
            dialog_fotos.dismiss();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.equals(img_equipoFoto)){
            dialogoFotos();
        }else if (view.equals(btn_registrarEquipo)){
            if (img_equipoFoto.getDrawable() == null){
                preferences.codeAdvertencia("Debe seleccionar una imagen para el equipo");
            }else{
                if (edt_nombreEquipo.getText().toString().equals("")){
                    preferences.codeAdvertencia("Debe elegir un nombre  para el equipo");
                }else {

                    uploadMultipart();
                }
            }
        }else if (view.equals(publicar_foto_camara)){
            camarex();
        }else if (view.equals(publicar_foto_galeria)){
            galeria();
        }
    }








    String path,id_equipo;
    int valor;
    public void uploadMultipart() {
        dialogoCargando();
        path = resultUriRecortada.getPath();



        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            PendingIntent clickIntent = PendingIntent.getActivity(
                    this, 1, new Intent(this, RegistroTorneo.class), PendingIntent.FLAG_UPDATE_CURRENT);
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, url)
                    .addFileToUpload(path, "imagen") //Adding file
                    .addParameter("usuario_id", preferences.getIdUsuarioPref()) //Adding text parameter to the request
                    .addParameter("nombre", edt_nombreEquipo.getText().toString())//Adding text parameter to the request
                    .addParameter("app", "true")//Adding text parameter to the request
                    .addParameter("token", preferences.getToken())//Adding text parameter to the request
                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {


                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                            Log.e("Torneo", "multipart error: " + serverResponse.toString() + " " +preferences.getIdUsuarioPref() );
                            dialog_carga.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Crear Equipos", "multipart Completed: " + serverResponse.getBodyAsString() + " " +preferences.getIdUsuarioPref() );
                            //Toast.makeText(RegistroForo.this, "completo", Toast.LENGTH_SHORT).show();


                            try {
                                JSONObject jsonObject;;

                                jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                JSONArray resultJSON = jsonObject.getJSONArray("results");

                                int count = resultJSON.length();


                                for (int i = 0; i < count; i++) {
                                    JSONObject jsonNode = resultJSON.getJSONObject(i);


                                    valor = jsonNode.optInt("valor");
                                    id_equipo = jsonNode.optString("id_equipo");

                                    if (valor==1){


                                        Intent x = new Intent(CrearEquipos.this, RegistrarJugadoresEnEquipos.class);
                                        x.putExtra("id_equipo",id_equipo);
                                        x.putExtra("nombre",edt_nombreEquipo.getText().toString());
                                        startActivity(x);
                                        finish();


                                    }else{
                                        dialog_carga.dismiss();
                                    }

                                    dialog_carga.dismiss();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            dialog_carga.dismiss();
                        }
                    })

                    .startUpload(); //Starting the upload
                            /*getNotificationConfig().setTitleForAllStatuses("Cargando Imagen")
                            .setRingToneEnabled(false)
                            .setClickIntentForAllStatuses(clickIntent)
                            .setClearOnActionForAllStatuses(true))*/




        } catch (Exception exc) {

        }
    }



    protected UploadNotificationConfig getNotificationConfig(final String uploadId, @StringRes int title) {
        UploadNotificationConfig config = new UploadNotificationConfig();




        PendingIntent clickIntent = PendingIntent.getActivity(
                this, 1, new Intent(this, RegistroTorneo.class), PendingIntent.FLAG_UPDATE_CURRENT);


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



Dialog dialog_carga;
    public void dialogoCargando(){

        dialog_carga= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_carga.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_carga.setCancelable(true);
        dialog_carga.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_carga.findViewById(R.id.back);
        LinearLayout layout = dialog_carga.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_carga.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
        });

        dialog_carga.show();

    }

    
}
