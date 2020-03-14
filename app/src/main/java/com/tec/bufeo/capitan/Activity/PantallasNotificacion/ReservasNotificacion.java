package com.tec.bufeo.capitan.Activity.PantallasNotificacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.bufeo.capitan.Activity.ConfirmacionReserva;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.ViewModels.MisReservasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.NombreCapeta;

public class ReservasNotificacion extends AppCompatActivity implements View.OnClickListener {


    LinearLayout llscrol ;
    Button guardar;
    ImageView logoBufeoxD;
    TextView nombreEmprex,precioReserva,canchaReserva,nameReserva,fechaReserva,direccionReserva,
    horaReserva,telefono2Reserva,telefonoReserva;
    Activity activity;
    String id;
    Preferences preferences;
    MisReservasViewModel misReservasViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_notificacion);

        misReservasViewModel = ViewModelProviders.of(this).get(MisReservasViewModel.class);
        preferences= new Preferences(this);

        id = getIntent().getExtras().getString("id");
        //id = "30";
        initViews();
        cargarVista();





        activity = ReservasNotificacion.this;
        showToolbar("Reserva",true);

        guardar.setOnClickListener(this);
    }

    private void initViews() {
        nombreEmprex= findViewById(R.id.nombreEmprex);
        precioReserva= findViewById(R.id.precioReserva);
        canchaReserva= findViewById(R.id.canchaReserva);
        nameReserva= findViewById(R.id.nameReserva);
        fechaReserva= findViewById(R.id.fechaReserva);
        horaReserva= findViewById(R.id.horaReserva);
        direccionReserva= findViewById(R.id.direccionReserva);
        telefono2Reserva= findViewById(R.id.telefono2Reserva);
        telefonoReserva= findViewById(R.id.telefonoReserva);
        llscrol= findViewById(R.id.llscrol);
        guardar= findViewById(R.id.guardar);
        logoBufeoxD= findViewById(R.id.logoBufeoxD);
    }

    private void cargarVista() {


        misReservasViewModel.getAllID(id,preferences.getToken(),"notificacion").observe(this, new Observer<List<MisReservas>>() {
            @Override
            public void onChanged(List<MisReservas> misReservas) {
                if (misReservas.size()>0){

                    float totalex;
                    nombreEmprex.setText(misReservas.get(0).getEmpresa_nombre());
                    if (Float.parseFloat(misReservas.get(0).getReserva_pago2())>0){

                        totalex= Float.parseFloat(misReservas.get(0).getReserva_pago1()) + Float.parseFloat(misReservas.get(0).getReserva_pago2());
                        precioReserva.setText(String.valueOf(totalex));
                    }else{
                        precioReserva.setText(misReservas.get(0).getReserva_pago1());
                    }

                    canchaReserva.setText(misReservas.get(0).getCancha_nombre());
                    nameReserva.setText(misReservas.get(0).getReserva_nombre());
                    fechaReserva.setText(misReservas.get(0).getReserva_fecha());
                    horaReserva.setText(misReservas.get(0).getReserva_hora());
                    direccionReserva.setText(misReservas.get(0).getEmpresa_direccion());
                    telefonoReserva.setText(misReservas.get(0).getEmpresa_telefono_1());
                    telefono2Reserva.setText(misReservas.get(0).getEmpresa_telefono_2());
                }

            }
        });
    }


    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.rgb(76,175,80));                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }

    public void enviarADondeSea(Uri ur){ //Recibimos el string y lo convertimos a Uri
        try {

            logoBufeoxD.setVisibility(View.GONE);
            guardar.setVisibility(View.VISIBLE);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, ur);
            intent.setType("image/png");
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    int contador;


    private void deleteFolder(File fileDel) {
        if(fileDel.isDirectory()){

            if(fileDel.list().length == 0)
                fileDel.delete();
            else{

                for (String temp : fileDel.list()) {
                    File fileDelete = new File(fileDel, temp);
                    //recursive delete
                    deleteFolder(fileDelete);
                }

                //check the directory again, if empty then delete it
                if(fileDel.list().length==0)
                    fileDel.delete();

            }

        }else{

            //if file, then delete it
            fileDel.delete();
        }
    }

    public void guardarImagen() {
        File carpetas1 = new File(Environment.getExternalStorageDirectory() + NombreCapeta);
        deleteFolder(carpetas1);


        Log.d("size"," "+llscrol.getWidth() +"  "+llscrol.getWidth());
        logoBufeoxD.setVisibility(View.VISIBLE);
        guardar.setVisibility(View.GONE);
        Bitmap bitmap = loadBitmapFromView(llscrol, llscrol.getWidth(), llscrol.getMeasuredHeight());
        contador=contador+1;


        File carpetas = new File(Environment.getExternalStorageDirectory() + "/.Capitan/","screen");

        carpetas.mkdirs();

        String aleatorio = preferences.getIdUsuarioPref()+"_"+new Double(Math.random() * 100).intValue();

        String nombre = aleatorio +".jpg";

        File imagen = new File(carpetas,nombre);



        Toast.makeText(getApplicationContext(), "ensayo"+String.valueOf(contador)+".png",
                Toast.LENGTH_LONG).show();


        boolean success = false;

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(imagen);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (success) {
            Toast.makeText(getApplicationContext(), "Image saved with success",
                    Toast.LENGTH_LONG).show();


            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=activity.getPackageName()+".provider";
                imageUri = FileProvider.getUriForFile(activity,authorities,imagen);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
            }
            //output = Uri.fromFile(imagen);



            //String stringUri = output.toString();
            enviarADondeSea(imageUri);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error during image saving", Toast.LENGTH_LONG).show();
        }
    }


    Uri imageUri;
    @Override
    public void onClick(View v) {
        if (v.equals(guardar)){
            guardarImagen();
        }
    }
}
