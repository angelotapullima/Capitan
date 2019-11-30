package com.tec.bufeo.capitan.Activity.Registro_Torneo;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Views.CrearGrupoRelampago;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;
import com.tec.bufeo.capitan.WebService.VolleySingleton;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class RegistroTorneo extends AppCompatActivity implements View.OnClickListener {

   EditText edt_nombreTorneo, edt_descripcionTorneo, edt_lugarTorneo,edt_organizador,edt_costo;
   Button  btn_crearTorneo;
   ArrayList<String> arrayDatos;
    Spinner spn_rutas;
   DataConnection dc;
   TextView btn_fechaTorneo ,btn_horaTorneo;
   ImageView camara,img_banner_torneo;
    int valor;
    String id_torneo,valor_tipo,valor_spinner;




    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;
    public Uri output,resultUriRecortada;
    String userChoosenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_torneo);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/
        edt_nombreTorneo = findViewById(R.id.edt_nombreTorneo);
        edt_descripcionTorneo = findViewById(R.id.edt_descripcionTorneo);
        edt_lugarTorneo = findViewById(R.id.edt_lugarTorneo);
        edt_organizador = findViewById(R.id.edt_organizador);
        edt_costo = findViewById(R.id.edt_costo);
        btn_fechaTorneo = findViewById(R.id.btn_fechaTorneo);
        btn_horaTorneo = findViewById(R.id.btn_horaTorneo);
        btn_crearTorneo = findViewById(R.id.btn_crearTorneo);
        spn_rutas= findViewById(R.id.spn_rutas);
        camara= findViewById(R.id.camara);
        img_banner_torneo= findViewById(R.id.img_banner_torneo);

        arrayDatos =  new ArrayList<String>();
        arrayDatos.add(0,"Seleccione");
        arrayDatos.add(1,"Torneo Relampago");
        arrayDatos.add(2,"Campeonato");
        ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayDatos);
        adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
        spn_rutas.setAdapter(adapEquipos);

        camara.setOnClickListener(this);
        btn_crearTorneo.setOnClickListener(this);
        btn_horaTorneo.setOnClickListener(this);
        btn_fechaTorneo.setOnClickListener(this);

        if((ContextCompat.checkSelfPermission(RegistroTorneo.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(RegistroTorneo.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(RegistroTorneo.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.equals(camara)){
            selectImage();
        }if (view.equals(btn_fechaTorneo)){
            showDatePickerDialog(btn_fechaTorneo);

        }if (view.equals(btn_horaTorneo)){
            obtenerHora(btn_horaTorneo);

        }
        if (view.equals(btn_crearTorneo)){

            valor_spinner = spn_rutas.getSelectedItem().toString();
            if (valor_spinner.equals("Campeonato")){
                valor_tipo = "2";
            }else{
                valor_tipo = "1";
            }

            if (img_banner_torneo.getDrawable() == null){
                    publicar_sin_foto();
            }else{
                uploadMultipart();
            }
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Camara", "Galería","Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroTorneo.this);
        builder.setTitle("Seleccione :");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Camara")) {
                    userChoosenTask ="Camara";

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","Torneo");
                    carpetas.mkdirs();

                    String aleatorio = MenuPrincipal.usuario_id+"_"+ edt_nombreTorneo.getText().toString();
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
                f1 = new File(getRealPathFromUri(this,uri));
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

                img_banner_torneo.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));

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



    String url = IP+"/index.php?c=Torneo&a=registrar_torneo&key_mobile=123456asdfgh";
    String path;
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
                    .addParameter("usuario_id", usuario_id) //Adding text parameter to the request
                    .addParameter("descripcion", edt_descripcionTorneo.getText().toString()) //Adding text parameter to the request
                    .addParameter("organizador", edt_organizador.getText().toString())//Adding text parameter to the request
                    .addParameter("costo", edt_costo.getText().toString()) //Adding text parameter to the request
                    .addParameter("fecha", btn_fechaTorneo.getText().toString()) //Adding text parameter to the request
                    .addParameter("hora", btn_horaTorneo.getText().toString()) //Adding text parameter to the request
                    .addParameter("lugar", edt_lugarTorneo.getText().toString()) //Adding text parameter to the request
                    .addParameter("nombre", edt_nombreTorneo.getText().toString()) //Adding text parameter to the request
                    .addParameter("tipo", valor_tipo) //Adding text parameter to the request


                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {


                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                            Log.e("Torneo", "multipart error: " + serverResponse.toString() + " " +usuario_id );
                            dialog_carga.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Log.e("Torneo", "multipart Completed: " + serverResponse.getBodyAsString() + " " +usuario_id );
                            //Toast.makeText(RegistroForo.this, "completo", Toast.LENGTH_SHORT).show();

                            try {
                            JSONObject jsonObject;;

                            jsonObject = new JSONObject(serverResponse.getBodyAsString());
                            JSONArray resultJSON = jsonObject.getJSONArray("results");

                            int count = resultJSON.length();


                            for (int i = 0; i < count; i++) {
                                JSONObject jsonNode = resultJSON.getJSONObject(i);
                                Grupos grupos = new Grupos();

                                valor = jsonNode.optInt("valor");
                                id_torneo = jsonNode.optString("id_torneo");

                                if (valor==1){

                                    if (valor_tipo.equals("2")){
                                        Log.e("tipo valor", "paso por campeonato: " + valor_tipo);
                                        Intent x = new Intent(RegistroTorneo.this, CrearGrupoRelampago.class);
                                        x.putExtra("id_torneo",id_torneo);
                                        x.putExtra("nombre_torneo",edt_nombreTorneo.getText().toString());
                                        startActivity(x);
                                    }else{
                                        Log.e("tipo valor", "paso por Relampago: " + valor_tipo);
                                        Intent x = new Intent(RegistroTorneo.this, CrearGrupoRelampago.class);
                                        x.putExtra("id_torneo",id_torneo);
                                        startActivity(x);
                                    }

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
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
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


    StringRequest stringRequest;
    private void publicar_sin_foto() {
        dialogoCargando();


        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Torneo","publicar sin foto"+response);


                try {
                    JSONObject jsonObject;;

                    jsonObject = new JSONObject(response);
                    JSONArray resultJSON = jsonObject.getJSONArray("results");

                    int count = resultJSON.length();


                    for (int i = 0; i < count; i++) {
                        JSONObject jsonNode = resultJSON.getJSONObject(i);
                        Grupos grupos = new Grupos();

                        valor = jsonNode.optInt("valor");
                        id_torneo = jsonNode.optString("id_torneo");

                        if (valor==1){

                            if (valor_tipo.equals("2")){
                                Intent x = new Intent(RegistroTorneo.this, CrearGrupoRelampago.class);
                                x.putExtra("id_torneo",id_torneo);
                                startActivity(x);
                            }else{
                                Intent x = new Intent(RegistroTorneo.this, CrearGrupoRelampago.class);
                                x.putExtra("id_torneo",id_torneo);
                                startActivity(x);
                            }

                        }


                    }
                    dialog_carga.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                dialog_carga.dismiss();


            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();

                parametros.put("usuario_id", usuario_id);
                parametros.put("descripcion", edt_descripcionTorneo.getText().toString());
                parametros.put("organizador", edt_organizador.getText().toString());
                parametros.put("costo", edt_costo.getText().toString());
                parametros.put("fecha", btn_fechaTorneo.getText().toString());
                parametros.put("hora", btn_horaTorneo.getText().toString());
                parametros.put("lugar", edt_lugarTorneo.getText().toString());
                parametros.put("nombre", edt_nombreTorneo.getText().toString());
                parametros.put("tipo", valor_tipo);

                Log.e("torneo", "getParams: "+parametros.toString() );
                return parametros;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(stringRequest);
    }

    private  final String CERO = "0";
    private  final String DOS_PUNTOS = ":";
    private void obtenerHora(final TextView Hora){
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                Hora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);

        recogerHora.show();

    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private void showDatePickerDialog(final TextView editText) {
        DateDialog.DatePickerFragment newFragment = DateDialog.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate =  year+ "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }


    android.app.AlertDialog dialog_carga;

    public void dialogoCargando(){

        android.app.AlertDialog.Builder builder =  new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialog_cargando,null);
        builder.setView(vista);


        dialog_carga = builder.create();
        dialog_carga.show();




    }
}
