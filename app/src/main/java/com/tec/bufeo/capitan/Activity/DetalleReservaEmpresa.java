package com.tec.bufeo.capitan.Activity;

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
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.DetalleReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.MisReservas.ViewModels.MisReservasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.tec.bufeo.capitan.WebService.DataConnection.NombreCapeta;

public class DetalleReservaEmpresa extends AppCompatActivity implements View.OnClickListener {

    String id,fecha;
    Preferences preferences;
    MisReservasViewModel misReservasViewModel;
    Activity activity;
    LinearLayout llscrol ;
    TextView Comision;
    TextView fechaPago1,fechaPago2,pago2,clienteReserva,NameReserrva,SubTotal,totalFinal,textoMensaje;
    //RelativeLayout carga_Reservasss;
    LinearLayout layoutSolo,layout1,layout2;
    TextView detalleCompraSolo,precioSolo,cantidadSolo,totalSolo;
    TextView detalleCompra1,precio1,cantidad1,total1,nOperacion;
    TextView detalleCompra2,precio2,cantidad2,total2;
    LinearLayout lCliente,loperacion;
    MaterialButton compartir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reserva_empresa);
        misReservasViewModel = ViewModelProviders.of(this).get(MisReservasViewModel.class);
        preferences= new Preferences(this);

        id = getIntent().getExtras().getString("id");
        fecha = formatearFecha(getIntent().getExtras().getString("fecha"));
        //fecha = formatearFecha(fecha);

        initViews();
        cargarVista();


        activity = DetalleReservaEmpresa.this;
        showToolbar("Reserva",true);
    }

    private void initViews() {
        llscrol= findViewById(R.id.llscrol);
        fechaPago1= findViewById(R.id.fechaPago1);
        fechaPago2= findViewById(R.id.fechaPago2);
        fechaPago2= findViewById(R.id.fechaPago2);
        pago2= findViewById(R.id.pago2);
        clienteReserva= findViewById(R.id.clienteReserva);
        NameReserrva= findViewById(R.id.NameReserrva);
        lCliente= findViewById(R.id.lCliente);
        compartir= findViewById(R.id.compartir);
        loperacion= findViewById(R.id.loperacion);
        nOperacion= findViewById(R.id.nOperacion);
        //carga_Reservasss= findViewById(R.id.carga_Reservasss);

        detalleCompraSolo= findViewById(R.id.detalleCompraSolo);
        precioSolo= findViewById(R.id.precioSolo);
        cantidadSolo= findViewById(R.id.cantidadSolo);
        totalSolo= findViewById(R.id.totalSolo);

        detalleCompra1= findViewById(R.id.detalleCompra1);
        precio1= findViewById(R.id.precio1);
        cantidad1= findViewById(R.id.cantidad1);
        total1= findViewById(R.id.total1);

        detalleCompra2= findViewById(R.id.detalleCompra2);
        precio2= findViewById(R.id.precio2);
        cantidad2= findViewById(R.id.cantidad2);
        total2= findViewById(R.id.total2);


        layoutSolo= findViewById(R.id.layoutSolo);
        layout1= findViewById(R.id.layout1);
        layout2= findViewById(R.id.layout2);






        SubTotal= findViewById(R.id.SubTotal);
        totalFinal= findViewById(R.id.totalFinal);
        textoMensaje= findViewById(R.id.textoMensaje);
        Comision= findViewById(R.id.Comision);

        compartir.setOnClickListener(this);

    }

    private String formatearFecha(String dato){

        String salida ="",separador;
        String[] resultado;

        separador = Pattern.quote(" ");
        resultado = dato.split(separador);
        salida = resultado[0];
        return  salida;
    }
    List<DetalleReservas> detallitos =  new ArrayList<>();
    private void cargarVista() {

      detallitos.clear();
        misReservasViewModel.getAllDetalle(id,fecha,preferences.getToken(),"detalle").observe(this, new Observer<List<MisReservas>>() {
            @Override
            public void onChanged(List<MisReservas> misReservas) {
                DetalleReservas detalleReservas = new DetalleReservas();
                if(misReservas.size()>0){

                    for (int i= 0 ; i< misReservas.get(0).getDetalle_reservas().size();i++){

                        if (misReservas.get(0).getDetalle_reservas().get(i).getId_reserva().equals(id)){
                            detalleReservas.setId_reserva(misReservas.get(0).getDetalle_reservas().get(i).getId_reserva());
                            detalleReservas.setCancha_id(misReservas.get(0).getDetalle_reservas().get(i).getCancha_id());
                            detalleReservas.setReserva_tipopago(misReservas.get(0).getDetalle_reservas().get(i).getReserva_tipopago());
                            detalleReservas.setPago_id(misReservas.get(0).getDetalle_reservas().get(i).getPago_id());
                            detalleReservas.setReserva_nombre(misReservas.get(0).getDetalle_reservas().get(i).getReserva_nombre());
                            detalleReservas.setReserva_fecha(misReservas.get(0).getDetalle_reservas().get(i).getReserva_fecha());
                            detalleReservas.setReserva_hora(misReservas.get(0).getDetalle_reservas().get(i).getReserva_hora());
                            detalleReservas.setReserva_pago1(misReservas.get(0).getDetalle_reservas().get(i).getReserva_pago1());
                            detalleReservas.setReserva_pago1_date(misReservas.get(0).getDetalle_reservas().get(i).getReserva_pago1_date());
                            detalleReservas.setReserva_pago2(misReservas.get(0).getDetalle_reservas().get(i).getReserva_pago2());
                            detalleReservas.setReserva_pago2_date(misReservas.get(0).getDetalle_reservas().get(i).getReserva_pago2_date());
                            detalleReservas.setReserva_estado(misReservas.get(0).getDetalle_reservas().get(i).getReserva_estado());
                            detalleReservas.setEmpresa_id(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_id());
                            detalleReservas.setCancha_nombre(misReservas.get(0).getDetalle_reservas().get(i).getCancha_nombre());
                            detalleReservas.setEmpresa_nombre(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_nombre());
                            detalleReservas.setEmpresa_direccion(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_direccion());
                            detalleReservas.setEmpresa_coord_x(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_coord_x());
                            detalleReservas.setEmpresa_coord_y(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_coord_y());
                            detalleReservas.setEmpresa_telefono_1(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_telefono_1());
                            detalleReservas.setEmpresa_telefono_2(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_telefono_2());
                            detalleReservas.setEmpresa_descripcion(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_descripcion());
                            detalleReservas.setEmpresa_valoracion(misReservas.get(0).getDetalle_reservas().get(i).getEmpresa_valoracion());
                            detalleReservas.setNombre_user(misReservas.get(0).getDetalle_reservas().get(i).getNombre_user());
                            detalleReservas.setReserva_tipo(misReservas.get(0).getDetalle_reservas().get(i).getReserva_tipo());
                            detalleReservas.setNombre_user(misReservas.get(0).getDetalle_reservas().get(i).getNombre_user());
                            detalleReservas.setPago_comision(misReservas.get(0).getDetalle_reservas().get(i).getPago_comision());
                            detalleReservas.setTransferencia_u_e_nro_operacion(misReservas.get(0).getDetalle_reservas().get(i).getTransferencia_u_e_nro_operacion());
                        }

                        detallitos.add(detalleReservas);
                    }


                    //carga_Reservasss.setVisibility(View.GONE);
                    String detalle1,detalle2 ;
                    String textInfo;
                    float totalex,totalex2;

                    Comision.setText(detallitos.get(0).getPago_comision());
                    nOperacion.setText(detallitos.get(0).getTransferencia_u_e_nro_operacion());

                    if (detallitos.get(0).getTransferencia_u_e_nro_operacion().equals("") || detallitos.get(0).getTransferencia_u_e_nro_operacion().isEmpty()){
                        loperacion.setVisibility(View.GONE);
                    }
                    if (detallitos.get(0).getReserva_tipopago().equals("0")){

                        lCliente.setVisibility(View.GONE);
                    }else{

                        lCliente.setVisibility(View.VISIBLE);
                    }

                    NameReserrva.setText(detallitos.get(0).getReserva_nombre());
                    clienteReserva.setText(detallitos.get(0).getNombre_user());

                    if (Float.parseFloat(detallitos.get(0).getReserva_pago2())>0){

                        detalle1 = "Reserva de " + detallitos.get(0).getCancha_nombre()
                                + " de " + detallitos.get(0).getReserva_hora() + " por el primer pago";

                        detalle2 = "Reserva de " + detallitos.get(0).getCancha_nombre()
                                + " de " + detallitos.get(0).getReserva_hora() + " por el segundo pago";


                        detalleCompra1.setText(detalle1);
                        detalleCompra2.setText(detalle2);
                        fechaPago1.setText(detallitos.get(0).getReserva_pago1_date());
                        fechaPago2.setText(detallitos.get(0).getReserva_pago2_date());
                        precio1.setText(detallitos.get(0).getReserva_pago1());
                        precio2.setText(detallitos.get(0).getReserva_pago2());


                        totalex= Float.parseFloat(detallitos.get(0).getReserva_pago1()) * Float.parseFloat(cantidad1.getText().toString());
                        totalex2= Float.parseFloat(detallitos.get(0).getReserva_pago2()) * Float.parseFloat(cantidad2.getText().toString());
                        total1.setText(String.valueOf(totalex));
                        total2.setText(String.valueOf(totalex2));

                        SubTotal.setText(String.valueOf(Float.parseFloat(total1.getText().toString())
                                + Float.parseFloat(total2.getText().toString())) );
                        layoutSolo.setVisibility(View.GONE);
                    }
                    else{
                        detalle1 = "Reserva de " + detallitos.get(0).getCancha_nombre()
                                + " de " + detallitos.get(0).getReserva_hora() ;
                        detalleCompraSolo.setText(detalle1);
                        fechaPago1.setText(detallitos.get(0).getReserva_pago1_date());
                        precioSolo.setText(detallitos.get(0).getReserva_pago1());
                        totalex= Float.parseFloat(detallitos.get(0).getReserva_pago1()) * Float.parseFloat(cantidadSolo.getText().toString());
                        totalSolo.setText(String.valueOf(totalex));

                        SubTotal.setText(String.valueOf(Float.parseFloat(totalSolo.getText().toString())) );
                        layout1.setVisibility(View.GONE);
                        layout2.setVisibility(View.GONE);
                        pago2.setVisibility(View.GONE);
                        fechaPago2.setVisibility(View.GONE);

                    }


                    totalFinal.setText(String.valueOf(Float.parseFloat(SubTotal.getText().toString())
                            + Float.parseFloat(Comision.getText().toString())));


                    if (detallitos.get(0).getReserva_tipopago().equals("0")){
                        textInfo = "Reserva generada a travez de la App Capitán, creada por el Administrador  "
                                + detallitos.get(0).getNombre_user() + " del Negocio " + detallitos.get(0).getEmpresa_nombre();
                        textoMensaje.setText(textInfo);

                    }else{
                        textInfo = "Reserva generada a travez de la App Capitán, creada por el Usuario  "
                                + detallitos.get(0).getNombre_user() ;
                        textoMensaje.setText(textInfo);
                    }




                }else{
                    //carga_Reservasss.setVisibility(View.VISIBLE);
                }

            }
        });


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

            /*logoBufeoxD.setVisibility(View.GONE);
            guardar.setVisibility(View.VISIBLE);*/
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
        /*logoBufeoxD.setVisibility(View.VISIBLE);
        guardar.setVisibility(View.GONE);*/
        Bitmap bitmap = loadBitmapFromView(llscrol, llscrol.getWidth(), llscrol.getMeasuredHeight());
        contador=contador+1;


        File carpetas = new File(Environment.getExternalStorageDirectory() + "/.Capitan/","screen");

        carpetas.mkdirs();

        String aleatorio = preferences.getIdUsuarioPref()+"_"+new Double(Math.random() * 100).intValue();

        String nombre = aleatorio +".jpg";

        File imagen = new File(carpetas,nombre);


        boolean success = false;

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(imagen);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream);
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
        if (v.equals(compartir)){
            guardarImagen();
        }
    }
}