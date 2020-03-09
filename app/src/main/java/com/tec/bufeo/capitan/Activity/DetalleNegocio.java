package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.EstadisticasEmpresas;
import com.tec.bufeo.capitan.Activity.ratings.BarLabels;
import com.tec.bufeo.capitan.Activity.ratings.RatingReviews;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoCanchaEmpresa;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleNegocio extends AppCompatActivity implements View.OnClickListener {

    ImageView img_fotoEmpresa,Misnegocios;
    RecyclerView rcv_canchas;
    AppBarLayout abl_detalleEmpresa;
    public TextView txt_nombreEmpresa, txt_descripcionEmpresa, txt_direccionEmpresa,separadorTelefonos,
            txt_telefonoEmpresa,txt_telefonoEmpresa2, txt_horario,rating_float,conteo,estadoNegocio,vistaMapa;

    /*TextView txt_promedio,txt_conteo;
    RatingBar rtb_valoracion;*/

    public RatingBar  rtb_valorar,ratingBar_promedio;
    CardView cdv_detalleEmpresa;
    LinearLayout lny_telefono;
    AdaptadorListadoCanchaEmpresa adaptadorListadoCanchaEmpresa;
    CardView cdv_mensaje;
    public Context context;
    public Activity activity;
    CoordinatorLayout cordinator;
    ArrayList<Cancha> arraycanchaactual;
    Preferences preferences;
    String id_empresa, tipo_usuario,latitud,longitud;
    FrameLayout frameCarga,frameCarga2;
    DataConnection  dc1, dc2,dc4;
    /*static ProgressBar progressbar, progressbarcanchas;*/
    public String fecha_actual, hora_actual, cancha_id, horario;
    String saldo_cargado;
    public ArrayList<Empresas> arrayempresa;
    public ArrayList<Cancha> arraycancha;
    ArrayList<String> saldo;

    public void getReportes() {
        Intent intent = new Intent(this, EstadisticasEmpresas.class);
        intent.putExtra("id_empresa",id_empresa);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_negocio);

        preferences= new Preferences(this);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/

        img_fotoEmpresa = (ImageView) findViewById(R.id.img_fotoEmpresa);

        cordinator=(CoordinatorLayout)findViewById(R.id.cordinator);
        int colors[] = new int[]{
                Color.parseColor("#0e9d58"),
                Color.parseColor("#bfd047"),
                Color.parseColor("#ffc105"),
                Color.parseColor("#ef7e14"),
                Color.parseColor("#d36259")};

        int raters[] = new int[5];

        for (int i = 0; i < 5; i++) {
            raters[i] = i + 10 ;

        }
        /*{
                new Random().nextInt(100),
                new Random().nextInt(100),
                new Random().nextInt(100),
                new Random().nextInt(100),
                new Random().nextInt(100)
        };*/
        RatingReviews rating_reviews =  findViewById(R.id.rating_reviews);

        rating_reviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);

        txt_nombreEmpresa = (TextView) findViewById(R.id.txt_nombreEmpresa);
        txt_telefonoEmpresa2 = (TextView) findViewById(R.id.txt_telefonoEmpresa2);
        rating_float = (TextView) findViewById(R.id.rating_float);
        ratingBar_promedio = (RatingBar) findViewById(R.id.ratingBar_promedio);
        conteo = (TextView) findViewById(R.id.conteo);
        /*txt_promedio = (TextView) findViewById(R.id.txt_promedio);
        txt_conteo = (TextView) findViewById(R.id.txt_conteo);*/
        txt_telefonoEmpresa = (TextView) findViewById(R.id.txt_telefonoEmpresa);
        txt_descripcionEmpresa = (TextView) findViewById(R.id.txt_descripcionEmpresa);
        txt_horario = (TextView) findViewById(R.id.txt_horario);
        txt_direccionEmpresa = (TextView) findViewById(R.id.txt_direccionEmpresa);
        /*rtb_valoracion = (RatingBar) findViewById(R.id.rtb_valoracion);*/
        rtb_valorar = (RatingBar) findViewById(R.id.rtb_valorar);
        cdv_detalleEmpresa = (CardView) findViewById(R.id.cdv_detalleEmpresa);
        frameCarga = (FrameLayout) findViewById(R.id.frameCarga);
        frameCarga2 = (FrameLayout) findViewById(R.id.frameCarga2);
        //progressbar = (ProgressBar) findViewById(R.id.progressbar);
        //progressbarcanchas = (ProgressBar) findViewById(R.id.progressbarCanchas);
        rcv_canchas = (RecyclerView) findViewById(R.id.rcv_canchas);
        abl_detalleEmpresa = (AppBarLayout) findViewById(R.id.abl_detalleEmpresa);
        lny_telefono = (LinearLayout) findViewById(R.id.lny_telefono);
        separadorTelefonos = (TextView) findViewById(R.id.separadorTelefonos);
        cdv_mensaje = (CardView) findViewById(R.id.cdv_mensaje);
        estadoNegocio = (TextView) findViewById(R.id.estadoNegocio);
        vistaMapa = (TextView) findViewById(R.id.vistaMapa);
        Misnegocios = (ImageView) findViewById(R.id.Misnegocios);
        context = this;
        activity = this;
        //rtb_valorar.setRating(4);
        //rtb_valoracion.setEnabled(true);





        id_empresa = getIntent().getStringExtra("id_empresa");
        tipo_usuario = getIntent().getStringExtra("tipo_usuario");


        txt_telefonoEmpresa.setOnClickListener(this);
        txt_telefonoEmpresa2.setOnClickListener(this);
        vistaMapa.setOnClickListener(this);

        if (tipo_usuario.equals("admin")){
            Misnegocios.setVisibility(View.VISIBLE);
        }else{
            Misnegocios.setVisibility(View.GONE);
        }

        obtenerSaldo();



        cargarEmpresa();
        dc2 = new DataConnection(DetalleNegocio.this, "listarcanchasEmpresas", new Cancha(id_empresa), false);
        new DetalleNegocio.GetListadoCanchas().execute();


        rtb_valorar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(getApplicationContext(),"V "+rating,Toast.LENGTH_SHORT).show();



                Intent i = new Intent(DetalleNegocio.this, CalificarNegocios.class);
                i.putExtra("valor_rating", Float.toString(rtb_valorar.getRating()));
                i.putExtra("id_empresa", id_empresa);
                i.putExtra("nombre_empresa", txt_nombreEmpresa.getText().toString());
                startActivityForResult(i,LAUNCH_SECOND_ACTIVITY);
                //dialogCarga();

            }
        });

        // rtb_valoracion.setRating();

        showToolbar("Detalle de Negocio",true);
        Misnegocios.setOnClickListener(this);
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
    public void obtenerSaldo(){
        dc4 = new DataConnection(activity, "ObtenerSaldo", false);
        new DetalleNegocio.GetSaldo().execute();
    }
    public void cargarEmpresa(){
        dc1 = new DataConnection(DetalleNegocio.this, "mostrarDetalleEmpresa", new Empresas(id_empresa, preferences.getIdUsuarioPref()), false);
        new DetalleNegocio.GetDetalleNegocio().execute();
    }
    int LAUNCH_SECOND_ACTIVITY = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {

            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                String valor=data.getStringExtra("valor");

                if (result.equals("1")){
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "Comentario enviado correctamente", Snackbar.LENGTH_LONG);
                    snackbar.show();



                }else{
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), "hubo un error", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    Dialog dialog_cargando;

    public void dialogCarga() {

        dialog_cargando = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);

        dialog_cargando.show();

    }

    public class GetSaldo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            saldo = new ArrayList<>();
            saldo = dc4.getSaldo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (saldo.size() > 0) {
                saldo_cargado = saldo.get(0);
            } else {
                saldo_cargado = "vacio";
            }


        }
    }


    @Override
    public void onClick(View v) {

        if (v.equals(txt_telefonoEmpresa)){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txt_telefonoEmpresa.getText().toString()));
            startActivity(intent);
        }else if(v.equals(txt_telefonoEmpresa2)){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txt_telefonoEmpresa2.getText().toString()));
            startActivity(intent);
        }else if (v.equals(vistaMapa)){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+latitud+","+longitud+"?z=16&q="+latitud+","+longitud+"("+txt_direccionEmpresa.getText().toString()+")"));
            startActivity(intent);

        }else  if(v.equals(Misnegocios)){
            getReportes();
        }

    }

    public class GetDetalleNegocio extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //abl_detalleEmpresa.setVisibility(View.INVISIBLE);
            cdv_detalleEmpresa.setVisibility(View.INVISIBLE);


        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayempresa = dc1.getListadoEmpresas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(getApplicationContext(),"H:"+arrayempresa.get(0).getEmpresa_cancha_hora(),Toast.LENGTH_SHORT).show();


            if (arrayempresa.size() > 0) {
                Glide.with(context).load(IP2 + "/" + arrayempresa.get(0).getEmpresas_foto()).into(img_fotoEmpresa);

                latitud = arrayempresa.get(0).getLatitud();
                longitud = arrayempresa.get(0).getLongitud();


                txt_nombreEmpresa.setText(arrayempresa.get(0).getEmpresas_nombre());
                txt_descripcionEmpresa.setText(arrayempresa.get(0).getEmpresas_descripcion());

                Date date =new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH");
                String hora = sdf.format(date);
                int horaactual = Integer.parseInt(hora);


                if (arrayempresa.get(0).getDia_de_la_semana().equals("7")){
                    txt_horario.setText(arrayempresa.get(0).getHorario_ls());
                    horario = arrayempresa.get(0).getHorario_ls();
                }else{
                    txt_horario.setText(arrayempresa.get(0).getHorario_d());
                    horario = arrayempresa.get(0).getHorario_d();
                }


                String separador,part1,part2,separador_part1,horaApertura,separador_part2,horaCierre;
                String[] resultado,resultado_part1 ,resultado_part2;

                separador = Pattern.quote("-");
                resultado = txt_horario.getText().toString().split(separador);
                part1 = resultado[0];
                part2 = resultado[1];

                separador_part1 = Pattern.quote(":");
                resultado_part1 = part1.split(separador_part1);
                horaApertura = resultado_part1[0];


                separador_part2 = Pattern.quote(":");
                resultado_part2 = part2.split(separador_part2);
                horaCierre = resultado_part2[0];
                horaCierre = horaCierre.trim();

                if (horaactual >= Integer.parseInt(horaApertura) && horaactual < Integer.parseInt(horaCierre)){
                    estadoNegocio.setText("ABIERTO");
                    estadoNegocio.setBackgroundColor(Color.rgb(56,142,60));
                }else{
                    estadoNegocio.setText("CERRADO");
                    estadoNegocio.setBackgroundColor(Color.RED);
                }

                txt_direccionEmpresa.setText(arrayempresa.get(0).getEmpresas_direccion());

                if (arrayempresa.get(0).getArrayRatingList().size() >0){
                    rating_float.setText(arrayempresa.get(0).getArrayRatingList().get(0).getRatingfloat());
                    conteo.setText(arrayempresa.get(0).getArrayRatingList().get(0).getConteo());
                    ratingBar_promedio.setRating(Float.parseFloat(arrayempresa.get(0).getArrayRatingList().get(0).getRatingfloat()));
                }else{
                    rating_float.setText("0");
                    conteo.setText("0");
                    ratingBar_promedio.setRating(0);
                }

                //latitud =  arrayempresa.get(0).get

                if (arrayempresa.get(0).getEmpresas_telefono_1().isEmpty() && !arrayempresa.get(0).getEmpresas_telefono_2().isEmpty()){
                    txt_telefonoEmpresa.setVisibility(View.GONE);
                    separadorTelefonos.setVisibility(View.GONE);
                    txt_telefonoEmpresa2.setText(arrayempresa.get(0).getEmpresas_telefono_2());
                }else if (!arrayempresa.get(0).getEmpresas_telefono_1().isEmpty() && arrayempresa.get(0).getEmpresas_telefono_2().isEmpty()){
                    txt_telefonoEmpresa2.setVisibility(View.GONE);
                    separadorTelefonos.setVisibility(View.GONE);
                    txt_telefonoEmpresa.setText(arrayempresa.get(0).getEmpresas_telefono_1());
                }else{
                    txt_telefonoEmpresa.setText(arrayempresa.get(0).getEmpresas_telefono_1());
                    txt_telefonoEmpresa2.setText(arrayempresa.get(0).getEmpresas_telefono_2());
                }









                fecha_actual = arrayempresa.get(0).getEmpresa_cancha_fecha();
                hora_actual = arrayempresa.get(0).getEmpresa_cancha_hora();
                frameCarga.setVisibility(View.GONE);
                frameCarga2.setVisibility(View.GONE);
                //progressbar.setVisibility(ProgressBar.INVISIBLE);


                abl_detalleEmpresa.setVisibility(View.VISIBLE);
                cdv_detalleEmpresa.setVisibility(View.VISIBLE);


            }


        }
    }




    public  class GetListadoCanchas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            arraycancha = dc2.getListadoCanchas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            //progressbarcanchas.setVisibility(ProgressBar.INVISIBLE);

            GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
            rcv_canchas.setLayoutManager(linearLayoutManager);

            adaptadorListadoCanchaEmpresa = new AdaptadorListadoCanchaEmpresa(context, arraycancha, R.layout.rcv_item_card_canchas, new AdaptadorListadoCanchaEmpresa.OnItemClickListener() {
                @Override
                public void onItemClick(Cancha cancha, final int position) {

                    cancha_id = cancha.getCancha_id().toString();
                    Toast.makeText(context, "ID cancha :" + cancha_id, Toast.LENGTH_SHORT).show();

                    if (!saldo_cargado.equals("") && !saldo_cargado.equals(null) ) {

                        if (!saldo_cargado.equals("vacio")){
                            Intent intent = new Intent(context, DetalleCanchas.class);
                            intent.putExtra("id_cancha", cancha.getCancha_id());
                            intent.putExtra("nombre_empresa", txt_nombreEmpresa.getText().toString());
                            intent.putExtra("nombre_cancha", cancha.getCancha_nombre());
                            intent.putExtra("precio_dia", cancha.getCancha_precioD());
                            intent.putExtra("precio_noche", cancha.getCancha_precioN());
                            intent.putExtra("horario", horario);
                            intent.putExtra("tipo_usuario", tipo_usuario);
                            intent.putExtra("hora_actual", hora_actual);
                            intent.putExtra("fecha_actual", fecha_actual);
                            intent.putExtra("saldo", saldo_cargado);
                            context.startActivity(intent);
                        }else{
                            obtenerSaldo();
                        }

                    }


                }
            });
            rcv_canchas.setAdapter(adaptadorListadoCanchaEmpresa);


            if (arraycancha.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    /*public class ActualizarCanchass extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            frameCarga.setVisibility(ProgressBar.VISIBLE);
            frameCarga2.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arraycanchaactual = dc3.getListadoCanchas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
            rcv_canchas.setLayoutManager(linearLayoutManager);

            adaptadorListadoCanchaEmpresa = new AdaptadorListadoCanchaEmpresa(context, arraycanchaactual, R.layout.rcv_item_card_canchas, new AdaptadorListadoCanchaEmpresa.OnItemClickListener() {
                @Override
                public void onItemClick(Cancha cancha, final int position) {

                    if (!saldo_cargado.equals("") && !saldo_cargado.equals(null)) {
                        Intent intent = new Intent(context, DetalleCanchas.class);
                        intent.putExtra("id_cancha", cancha.getCancha_id());
                        intent.putExtra("nombre_empresa", txt_nombreEmpresa.getText().toString());
                        intent.putExtra("nombre_cancha", cancha.getCancha_nombre());
                        intent.putExtra("precio_dia", cancha.getCancha_precioD());
                        intent.putExtra("precio_noche", cancha.getCancha_precioN());
                        intent.putExtra("horario", horario);
                        intent.putExtra("tipo_usuario", tipo_usuario);
                        intent.putExtra("saldo", saldo_cargado);
                        context.startActivity(intent);
                    }


                }

            });

            frameCarga.setVisibility(View.GONE);
            frameCarga2.setVisibility(View.GONE);
            //progressbarcanchas.setVisibility(ProgressBar.INVISIBLE);

            rcv_canchas.setAdapter(adaptadorListadoCanchaEmpresa);


            if (arraycanchaactual.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }

    public  void ActualizarCanchas() {
        dc3 = new DataConnection(activity, "listarcanchasEmpresas", new Cancha(id_empresa), false);
        new ActualizarCanchass().execute();
    }*/



}


