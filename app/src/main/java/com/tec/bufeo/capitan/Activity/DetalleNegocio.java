package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

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
import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.EstadisticasEmpresas;
import com.tec.bufeo.capitan.Activity.ratings.BarLabels;
import com.tec.bufeo.capitan.Activity.ratings.RatingReviews;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoCanchaEmpresa;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;
import java.util.Random;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleNegocio extends AppCompatActivity implements View.OnClickListener {

    static ImageView img_fotoEmpresa;
    static RecyclerView rcv_canchas;
    static AppBarLayout abl_detalleEmpresa;
    public static TextView txt_nombreEmpresa, txt_descripcionEmpresa, txt_direccionEmpresa,
            txt_telefonoEmpresa, txt_horario,rating_float,conteo,txt_telefonoEmpresa_2;

    /*TextView txt_promedio,txt_conteo;
    RatingBar rtb_valoracion;*/
    public static RatingBar  rtb_valorar,ratingBar_promedio;
    static CardView cdv_detalleEmpresa;
    static LinearLayout lny_telefono,lny_telefono_2;
    static AdaptadorListadoCanchaEmpresa adaptadorListadoCanchaEmpresa;
    static CardView cdv_mensaje;
    public static Context context;
    public static Activity activity;
    CoordinatorLayout cordinator;
    static ArrayList<Cancha> arraycanchaactual;
    Preferences preferences;
    static String id_empresa, tipo_usuario;
    static FrameLayout frameCarga,frameCarga2;
    static DataConnection dc, dc1, dc2, dc3;
    static DataConnection dc4;
    /*static ProgressBar progressbar, progressbarcanchas;*/
    public static String fecha_actual, hora_actual, cancha_id, horario;
    static String saldo_cargado;
    public static ArrayList<Empresas> arrayempresa;
    public static ArrayList<Cancha> arraycancha;
    static ArrayList<String> saldo;

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
        rating_float = (TextView) findViewById(R.id.rating_float);
        ratingBar_promedio = (RatingBar) findViewById(R.id.ratingBar_promedio);
        conteo = (TextView) findViewById(R.id.conteo);
        /*txt_promedio = (TextView) findViewById(R.id.txt_promedio);
        txt_conteo = (TextView) findViewById(R.id.txt_conteo);*/
        txt_telefonoEmpresa = (TextView) findViewById(R.id.txt_telefonoEmpresa);
        txt_telefonoEmpresa_2 = (TextView) findViewById(R.id.txt_telefonoEmpresa_2);
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
        lny_telefono_2 = (LinearLayout) findViewById(R.id.lny_telefono_2);
        cdv_mensaje = (CardView) findViewById(R.id.cdv_mensaje);
        context = this;
        activity = this;
        //rtb_valorar.setRating(4);
        //rtb_valoracion.setEnabled(true);





        id_empresa = getIntent().getStringExtra("id_empresa");
        tipo_usuario = getIntent().getStringExtra("tipo_usuario");


        lny_telefono.setOnClickListener(this);


        obtenerSaldo();


        cargarEmpresa();
        dc2 = new DataConnection(DetalleNegocio.this, "listarcanchasEmpresas", new Cancha(id_empresa), false);
        new GetListadoCanchas().execute();


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
    }
    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);  //y habilitamos la flacha hacia atras

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }
    public static void obtenerSaldo(){
        dc4 = new DataConnection(activity, "ObtenerSaldo", false);
        new DetalleNegocio.GetSaldo().execute();
    }
    public void cargarEmpresa(){
        dc1 = new DataConnection(DetalleNegocio.this, "mostrarDetalleEmpresa", new Empresas(id_empresa, preferences.getIdUsuarioPref()), false);
        new GetDetalleNegocio().execute();
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

    public static class GetSaldo extends AsyncTask<Void, Void, Void> {

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
        switch (v.getId()) {


            case R.id.lny_telefono:

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + txt_telefonoEmpresa.getText().toString()));
                startActivity(intent);
                break;


        }
    }

    public static class GetDetalleNegocio extends AsyncTask<Void, Void, Void> {

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
                Picasso.with(context).load(IP2 + "/" + arrayempresa.get(0).getEmpresas_foto()).into(img_fotoEmpresa);

                txt_nombreEmpresa.setText(arrayempresa.get(0).getEmpresas_nombre());
                txt_descripcionEmpresa.setText(arrayempresa.get(0).getEmpresas_descripcion());
                txt_horario.setText(arrayempresa.get(0).getEmpresas_horario());
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

                if (arrayempresa.get(0).getEmpresas_telefono_1().equals("")){
                    lny_telefono.setVisibility(View.GONE);
                }else{
                    txt_telefonoEmpresa.setText(arrayempresa.get(0).getEmpresas_telefono_1());
                }

                if (arrayempresa.get(0).getEmpresas_telefono_2().equals("")){
                    lny_telefono_2.setVisibility(View.GONE);
                }else{
                    txt_telefonoEmpresa_2.setText(arrayempresa.get(0).getEmpresas_telefono_2());
                }



                horario = arrayempresa.get(0).getEmpresas_horario();
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




    public static class GetListadoCanchas extends AsyncTask<Void, Void, Void> {

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

    public static class ActualizarCanchass extends AsyncTask<Void, Void, Void> {

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

    public static void ActualizarCanchas() {
        dc3 = new DataConnection(activity, "listarcanchasEmpresas", new Cancha(id_empresa), false);
        new ActualizarCanchass().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tipo_usuario.equals("admin")) {
            getMenuInflater().inflate(R.menu.menu_btn_reporte, menu);
            //menu.getItem(0).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            case R.id.action_btn_reporte:
                getReportes();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}


