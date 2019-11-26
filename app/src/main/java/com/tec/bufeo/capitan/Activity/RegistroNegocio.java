package com.tec.bufeo.capitan.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tec.bufeo.capitan.Fragments.FragmentNegocio;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.Modelo.MDistrito;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.horaDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RegistroNegocio extends AppCompatActivity  implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    Spinner spn_ciudad, spn_distrito;
    EditText edt_nombreEmpresa,edt_descripcionEmpresa,edt_telefonoEmpresa,edt_direccionEmpresa;
    Button btn_registrarEmpresa,btn_horaApertura, btn_horaCierre,btn_Camara;
    ImageView img_empresaFoto;
    public Empresas empresas;

    DataConnection dc,dc1,dc2;

    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;
    public Context context;

    ArrayList<String> arrayCiudad;
    ArrayList<String> arraynombresDistrito;
    ArrayList<MDistrito> arrayDistritoxCiudad;

    static String nombre_btn="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_negocio);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/

        edt_nombreEmpresa = findViewById(R.id.edt_nombreEmpresa);
        edt_telefonoEmpresa =  findViewById(R.id.edt_telefonoEmpresa);
        edt_descripcionEmpresa =  findViewById(R.id.edt_descripcionEmpresa);
        edt_direccionEmpresa =  findViewById(R.id.edt_direccionEmpresa);
        spn_ciudad =  findViewById(R.id.spn_ciudad);
        spn_distrito =  findViewById(R.id.spn_distrito);
        btn_registrarEmpresa =  findViewById(R.id.btn_registrarEmpresa);
        btn_horaApertura =  findViewById(R.id.btn_horaApertura);
        btn_horaCierre = findViewById(R.id.btn_horaCierre);
        btn_Camara = findViewById(R.id.btn_Camara);
        img_empresaFoto = findViewById(R.id.img_empresaFoto);

        btn_horaCierre.setOnClickListener(this);
        btn_horaApertura.setOnClickListener(this);
        btn_registrarEmpresa.setOnClickListener(this);
        btn_Camara.setOnClickListener(this);
        context = getApplicationContext();

        if((ContextCompat.checkSelfPermission(RegistroNegocio.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(RegistroNegocio.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(RegistroNegocio.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

        dc1 = new DataConnection(RegistroNegocio.this,"listarCiudades",false);
        new GetCiudades().execute();


        spn_ciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    spn_distrito.setEnabled(false);
                    spn_distrito.setSelection(0);
                }else {
                    spn_distrito.setEnabled(true);
                    dc2 = new DataConnection(RegistroNegocio.this,"listarDistritoxCiudades",arrayCiudad.get(spn_ciudad.getSelectedItemPosition()),false);
                    new GetDistritoxCiudad().execute();                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class GetCiudades extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayCiudad =  new ArrayList<>();
            arrayCiudad = dc1.getListaCiudades();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            arrayCiudad.add(0,"Seleccione");

            ArrayAdapter<String > adapciudades = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayCiudad);
            adapciudades.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_ciudad.setAdapter(adapciudades);



        }
    }

    public class GetDistritoxCiudad extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayDistritoxCiudad = new ArrayList<>();
            arrayDistritoxCiudad = dc2.getListaDistritoxCiudad();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(getApplicationContext(),"S:" +arrayDistritoxCiudad.size(),Toast.LENGTH_SHORT).show();
            arraynombresDistrito = new ArrayList<>();
            //Secciones
            for (MDistrito obj :arrayDistritoxCiudad){
                arraynombresDistrito.add(obj.getDistrito());
            }

            arraynombresDistrito.add(0,"Seleccione");
            ArrayAdapter<String > adapsecciones = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arraynombresDistrito);
            adapsecciones.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_distrito.setAdapter(adapsecciones);

        }
    }



    @Override
        public void onClick (View v){
            switch (v.getId()) {

                case R.id.btn_Camara:
                    selectImage();
                    break;

                case R.id.btn_registrarEmpresa:
                    if (!(edt_nombreEmpresa.getText().toString().isEmpty()) && !(edt_descripcionEmpresa.getText().toString().isEmpty()) && !(edt_direccionEmpresa.getText().toString().isEmpty()) && !(edt_telefonoEmpresa.getText().toString().isEmpty()) &&  !(spn_distrito.getSelectedItem().toString().equals("Seleccione")) ) {

                        RequestParams params1 = new RequestParams();

                        try {

                            File imagen  = new File(resultUriRecortada.getPath());
                            params1.put("usuario_id", MenuPrincipal.usuario_id);
                            params1.put("ubigeo_id", arrayDistritoxCiudad.get(spn_distrito.getSelectedItemPosition()).getUbigeo_id());
                            params1.put("nombre", edt_nombreEmpresa.getText().toString());
                            params1.put("direccion", edt_direccionEmpresa.getText().toString());
                            params1.put("descripcion", edt_descripcionEmpresa.getText().toString() );
                            params1.put("telefono", edt_telefonoEmpresa.getText().toString());
                            params1.put("horario",((btn_horaApertura.getText().toString())+" - "+ (btn_horaCierre.getText().toString())));
                            params1.put("imagen", imagen);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post("http://www.guabba.com/capitan/index.php?c=Empresa&a=registrar&key_mobile=123456asdfgh", params1, new AsyncHttpResponseHandler() {

                            String respuesta=null;
                            ProgressDialog loading;

                            @Override
                            public void onStart() {
                                super.onStart();
                                loading = new ProgressDialog(RegistroNegocio.this);
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
                                        FragmentNegocio.ActualizarEmpresas();

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

                case R.id.btn_horaApertura:
                    nombre_btn ="txt_horaApertura";
                    new horaDialog().show(getSupportFragmentManager(), "TimePicker");
                    break;

                case R.id.btn_horaCierre:
                    nombre_btn ="txt_horaCierre";
                    new horaDialog().show(getSupportFragmentManager(), "TimePicker");
                    break;

            }
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA){

                CropImage.activity(output).start(this);
                //CropImage.activity(output).setMaxCropResultSize(700,700).setMinCropResultSize(500,500).start(this);
            }

            else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();


                File f1,f2;
                f1 = new File(getRealPathFromUri(context,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/Capitan/","Negocios");
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

                img_empresaFoto.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

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

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroNegocio.this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask ="Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","Negocios");
                    carpetas.mkdirs();

                    String aleatorio = MenuPrincipal.usuario_id+"_"+ edt_nombreEmpresa.getText().toString();
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
    public void onTimeSet(TimePicker view, int hora, int minuto) {

       if (nombre_btn.equals("txt_horaApertura")){
           btn_horaApertura.setText(String.format("%02d:%02d", hora, minuto));
        }
        else {
           btn_horaCierre.setText(String.format("%02d:%02d", hora, minuto));}
        }

        @Override
        public void onBackPressed () {
            finish();
        }

    }
