package com.tec.bufeo.capitan.Activity.old;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.others.Equipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class old extends AppCompatActivity   implements View.OnClickListener{
    EditText edt_nombreEquipo;
    Button btn_registrarEquipo,btn_Camara;
    ImageView img_equipoFoto;
    DataConnection dc;
    public Equipo equipo;
    public Context context;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/

        edt_nombreEquipo = (EditText) findViewById(R.id.edt_nombreEquipo);
        btn_registrarEquipo = (Button) findViewById(R.id.btn_registrarEquipo);
        btn_Camara = findViewById(R.id.btn_Camara);
        img_equipoFoto = findViewById(R.id.img_equipoFoto);
        btn_registrarEquipo.setOnClickListener(this);
        btn_Camara.setOnClickListener(this);
        context = getApplicationContext();

        if((ContextCompat.checkSelfPermission(old.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(old.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(old.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_Camara:
                selectImage();
                break;

            case R.id.btn_registrarEquipo:
                if (!(edt_nombreEquipo.getText().toString().isEmpty())) {
                    RequestParams params1 = new RequestParams();

                    try {
                        File imagen  = new File(resultUriRecortada.getPath());
                        params1.put("usuario_id", usuario_id);
                        params1.put("nombre", edt_nombreEquipo.getText().toString());
                        params1.put("imagen", imagen);



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(IP+"/index.php?c=Torneo&a=registrar_equipo&key_mobile=123456asdfgh", params1, new AsyncHttpResponseHandler() {

                        String respuesta=null;
                        ProgressDialog loading;

                        @Override
                        public void onStart() {
                            super.onStart();
                            loading = new ProgressDialog(old.this);
                            loading.setTitle("Capitan");
                            loading.setMessage("Por favor espere...");
                            loading.setIndeterminate(false);
                            loading.setCancelable(false);
                            loading.show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200){

                                try {
                                    respuesta =  new String(responseBody, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                if(respuesta.equals("1")){
                                    finish();
                                    Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                                    //FragmentEquiposHijo.ActualizarEquipo();

                                }else{
                                    Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplicationContext(),"Error al registrar", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            loading.dismiss();

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                }
                break;

        }


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

                //CropImage.activity(output).start(this);
                CropImage.activity(output).setMaxCropResultSize(1080,566 ).setMinCropResultSize(800,400).start(this);
            }

            else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();


                File f1,f2;
                f1 = new File(getRealPathFromUri(context,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/Capitan/","Equipo");
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

                img_equipoFoto.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

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

        AlertDialog.Builder builder = new AlertDialog.Builder(old.this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask ="Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","Equipo");
                    carpetas.mkdirs();

                    String aleatorio = MenuPrincipal.usuario_id+"_"+ edt_nombreEquipo.getText().toString();
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
}
