package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
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

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class PerfilEdit extends AppCompatActivity implements View.OnClickListener{

    static Activity activity;
    static Context context;
    String userChoosenTask,opcionCarpeta;
    public Uri output,resultUriRecortada;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    ProgressBar prog_imagenloading;
    CircleImageView civ_iconoPerfil;

    Preferences preferences;
   ImageButton imb_camara;
    public void getDetalleFoto(){
        Intent intent = new Intent(getApplicationContext(),DetalleFotoUsuario.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_edit);
        preferences = new Preferences(this);
        civ_iconoPerfil = findViewById(R.id.civ_iconoPerfil);
        prog_imagenloading = findViewById(R.id.prog_imagenloading);
        imb_camara = findViewById(R.id.imb_camara);



        // Picasso.with(getApplicationContext()).load("http://"+IP+"/"+usuario_foto).into(civ_iconoPerfil);

        Picasso.with(context).load(IP2 + "/" + preferences.getFotoUsuario()).error(R.drawable.error).fit().into(civ_iconoPerfil, new Callback() {

            @Override
            public void onSuccess() {
                prog_imagenloading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                prog_imagenloading.setVisibility(View.GONE);
            }

        });
        activity = PerfilEdit.this;
        context = getApplicationContext();
        imb_camara.setOnClickListener(this);
        civ_iconoPerfil.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imb_camara:
                selectImage("Perfil");
                break;

            case R.id.civ_iconoPerfil:
                getDetalleFoto();
                break;

        }
    }
    private void selectImage(final String carpetacChild) {
        final CharSequence[] items = { "Camara", "Galería","Cancelar" };

        opcionCarpeta = carpetacChild;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask ="Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/.Capitan/",carpetacChild);
                    carpetas.mkdirs();

                    String aleatorio = preferences.getIdUsuarioPref()+"_"+new Double(Math.random() * 100).intValue();

                    String nombre = aleatorio +".jpg";

                    File imagen = new File(carpetas,nombre);

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                    {
                        String authorities=activity.getPackageName()+".provider";
                        Uri imageUri = FileProvider.getUriForFile(activity,authorities,imagen);
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
                    if (intentgaleria.resolveActivity(activity.getPackageManager())!=null){


                        startActivityForResult(intentgaleria,SELET_GALERRY);
                    }
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA){

                if (opcionCarpeta.equals("Perfil")){
                    CropImage.activity(output).setMaxCropResultSize(700,700).setMinCropResultSize(500,500).start(activity);
                }
            }else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();

                File f1,f2;
                f1 = new File(getRealPathFromUri(activity,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/.Capitan/",opcionCarpeta);
                f2.mkdirs();


                try {
                    FileUtils.copyFileToDirectory(f1,f2);
                    ContentValues values =new ContentValues();
                    values.put(MediaStore.Images.Media.DATE_TAKEN,System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE,"image/*");
                    values.put(MediaStore.MediaColumns.DATA,f2.toString()+"/"+fname);
                    activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }  finally {
                    //Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                }


                CropImage.activity(uri).start(activity);

            }
        }if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultado = CropImage.getActivityResult(result);
            if (resultCode == RESULT_OK) {

                resultUriRecortada = resultado.getUri();



                uploadMultipart();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = resultado.getError();
                Toast.makeText(context,"Error: Intente de nuevo", Toast.LENGTH_SHORT).show();

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


    String url = IP2+"/api/Usuario/actualizar_perfil";
    String path;

    public void uploadMultipart() {
        path = resultUriRecortada.getPath();

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            PendingIntent clickIntent = PendingIntent.getActivity(
                    context, 1, new Intent(context, DetalleFotoUsuario.class), PendingIntent.FLAG_UPDATE_CURRENT);
            //Creating a multi part request
            new MultipartUploadRequest(context, uploadId, url)
                    .addFileToUpload(path, "imagen") //Adding file
                    .addParameter("usuario_id", preferences.getIdUsuarioPref()) //Adding text parameter to the request
                    .addParameter("app", "true") //Adding text parameter to the request
                    .addParameter("token", preferences.getToken()) //Adding text parameter to the request

                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            prog_imagenloading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                            String ruta = IP2+"/";

                            final String str = serverResponse.getBodyAsString();
                            ruta = ruta+str.substring(1,str.length()-1);
                            Log.e("subirImagen", "onCompleted: " + ruta );
                            Toast.makeText(context,"Actualizado correctamente"+"-"+ruta, Toast.LENGTH_LONG).show();

                            Picasso.with(context).load(ruta).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.error).into(civ_iconoPerfil,new Callback() {

                                @Override
                                public void onSuccess() {
                                    prog_imagenloading.setVisibility(View.GONE);


                                    preferences.saveValuePORT("usuario_foto",str.substring(1,str.length()-1));


                                }

                                @Override
                                public void onError() {
                                    prog_imagenloading.setVisibility(View.GONE);
                                }
                            });
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

