package com.tec.bufeo.capitan.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.theartofdev.edmodo.cropper.CropImage;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationAction;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;
import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_nombre;
//import static com.tec.bufeo.capitan.others.FragmentForo.Actualizarforo;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class RegistroForo extends AppCompatActivity   implements View.OnClickListener{
    EditText edt_tituloForo, edt_descripcionForo;
    Button btn_registrarForo,btn_Camara;
    ImageView img_foroFoto;
    DataConnection dc;
    Preferences preferences;
    public Context context;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;
    FeedListViewModel feedListViewModel;
    BroadcastReceiver BR;
    public static final String registro= "registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_foro);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);

        context=this;
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);
        preferences = new Preferences(context);

        edt_tituloForo = (EditText) findViewById(R.id.edt_tituloForo);
        edt_descripcionForo = (EditText) findViewById(R.id.edt_descripcionForo);
        btn_registrarForo = (Button) findViewById(R.id.btn_registrarForo);
        btn_Camara = findViewById(R.id.btn_Camara);
        img_foroFoto = findViewById(R.id.img_foroFoto);
        btn_registrarForo.setOnClickListener(this);
        btn_Camara.setOnClickListener(this);


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
                Log.i("registro", "onMessageReceived:  funcionando la huevada de Registro" +to );
                ModelFeed modelFeed =  new ModelFeed();
                modelFeed.setPublicacion_id("1");
                modelFeed.setForo_foto("ubbbbjbfhh");
                modelFeed.setUsuario_nombre("angelo");
                modelFeed.setUsuario_id("18");
                modelFeed.setForo_titulo("x2");
                modelFeed.setForo_descripcion("x3");
                modelFeed.setForo_feccha("Hace 0 min");
                modelFeed.setForo_conteo("vamonos");
                modelFeed.setForo_tipo("1");
                modelFeed.setCant_likes("0");
                modelFeed.setCant_Comentarios("0");
                modelFeed.setDio_like("0");
                modelFeed.setOrden("1");

                Log.i("registro", "onMessageReceived:  funcionando la huevada de tipo" + modelFeed.toString() );
                RegistroForope(modelFeed);
            }
        };

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

        switch (v.getId()) {

            case R.id.btn_Camara:
                selectImage();
                break;

            case R.id.btn_registrarForo:
                if (!(edt_descripcionForo.getText().toString().isEmpty())&!(edt_tituloForo.getText().toString().isEmpty())) {


                    Toast.makeText(context, "" + path, Toast.LENGTH_SHORT).show();
                    uploadMultipart();
                    ModelFeed modelFeed =  new ModelFeed();
                    //modelFeed.setForo_publicacion_id("1");
                    modelFeed.setForo_foto(path);
                    modelFeed.setUsuario_nombre(usuario_nombre);
                    modelFeed.setUsuario_id(usuario_id);
                    modelFeed.setForo_titulo(edt_tituloForo.getText().toString());
                    modelFeed.setForo_descripcion(edt_descripcionForo.getText().toString());
                    modelFeed.setForo_feccha("Hace 0 min");
                    modelFeed.setForo_conteo(edt_tituloForo.getText().toString());
                    modelFeed.setForo_tipo("1");
                    modelFeed.setCant_likes("0");
                    modelFeed.setCant_Comentarios("0");
                    modelFeed.setDio_like("0");
                    modelFeed.setOrden("1");
                    //feedListViewModel.insert(modelFeed);
                    RegistroForope(modelFeed);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                }
                break;

        }


    }

    public void RegistroForope(ModelFeed modelFeed){
        feedListViewModel.insert(modelFeed);
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


                f2= new File(Environment.getExternalStorageDirectory() + "/Capitan/","Foro");
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
    private void selectImage() {
        final CharSequence[] items = { "Camara", "Galería","Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroForo.this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask ="Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","Foro");
                    carpetas.mkdirs();

                    String aleatorio = MenuPrincipal.usuario_id+"_"+ edt_tituloForo.getText().toString();
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

                }else if (items[item].equals("Galería")){
                    userChoosenTask ="Galería";


                    Intent intentgaleria = new Intent(Intent.ACTION_PICK);
                    intentgaleria.setType("image/*");
                    if (intentgaleria.resolveActivity(getPackageManager())!=null){


                        startActivityForResult(intentgaleria,SELET_GALERRY);
                    }
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    String url = IP+"/index.php?c=Foro&a=registrar&key_mobile=123456asdfgh";
    String path;
    public void uploadMultipart() {
        path = resultUriRecortada.getPath();

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            PendingIntent clickIntent = PendingIntent.getActivity(
                    context, 1, new Intent(context, RegistroForo.class), PendingIntent.FLAG_UPDATE_CURRENT);
            //Creating a multi part request
            new MultipartUploadRequest(context, uploadId, url)
                    .addFileToUpload(path, "imagen") //Adding file
                    .addParameter("usuario_id", usuario_id) //Adding text parameter to the request
                    .addParameter("titulo", edt_tituloForo.getText().toString()) //Adding text parameter to the request
                    .addParameter("descripcion", edt_descripcionForo.getText().toString()) //Adding text parameter to the request

                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)

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
