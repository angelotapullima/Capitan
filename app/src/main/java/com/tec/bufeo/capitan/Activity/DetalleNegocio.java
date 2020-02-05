package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoCanchaEmpresa;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleNegocio extends AppCompatActivity  implements View.OnClickListener{

    static ImageView img_fotoEmpresa;
    Empresas empresas;
    static RecyclerView rcv_canchas;
    static FloatingActionButton fab_registrarCancha;
    static AppBarLayout abl_detalleEmpresa;
    public static TextView txt_nombreEmpresa,txt_nombreUsuarioEmpresa,txt_descripcionEmpresa,txt_direccionEmpresa,
            txt_telefonoEmpresa, txt_horario, txt_promedio, txt_conteo;

    public  static  RatingBar rtb_valoracion,rtb_valorar;
    static  CardView cdv_detalleEmpresa;
    static LinearLayout lny_telefono;
    static  AdaptadorListadoCanchaEmpresa adaptadorListadoCanchaEmpresa;
    static CardView cdv_mensaje;
    public static Context context;
    public static Activity activity;

    static ArrayList<Cancha> arraycanchaactual;
    public static Button btn_enviarV, btn_cancelarV,btn_editVal;
    static  String id_empresa,tipo_usuario;

    static DataConnection dc,dc1,dc2, dc3;
    static DataConnection dc4;
    static  ProgressBar progressbar,progressbarcanchas;
    public static String fecha_actual, hora_actual, cancha_id, horario;
    static String saldo_cargado;
    public static  ArrayList<Empresas> arrayempresa;
    public static  ArrayList<Empresas> arrayempresaVal;
    public static ArrayList<Cancha> arraycancha;
    static ArrayList<String> saldo;

    public void getReportes(){
        Intent intent= new Intent(this,ReporteDiario2.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_negocio);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/

        img_fotoEmpresa = (ImageView) findViewById(R.id.img_fotoEmpresa);
        btn_enviarV = (Button) findViewById(R.id.btn_enviarV);
        btn_cancelarV = (Button) findViewById(R.id.btn_cancelarV);
        btn_editVal = (Button) findViewById(R.id.btn_editVal);
        txt_nombreEmpresa = (TextView) findViewById(R.id.txt_nombreEmpresa);
        txt_promedio = (TextView) findViewById(R.id.txt_promedio);
        txt_conteo = (TextView) findViewById(R.id.txt_conteo);
        txt_telefonoEmpresa = (TextView) findViewById(R.id.txt_telefonoEmpresa);
        txt_nombreUsuarioEmpresa = (TextView) findViewById(R.id.txt_nombreUsuarioEmpresa);
        txt_descripcionEmpresa = (TextView) findViewById(R.id.txt_descripcionEmpresa);
        txt_horario = (TextView) findViewById(R.id.txt_horario);
        txt_direccionEmpresa = (TextView) findViewById(R.id.txt_direccionEmpresa);
        rtb_valoracion = (RatingBar) findViewById(R.id.rtb_valoracion);
        rtb_valorar= (RatingBar) findViewById(R.id.rtb_valorar);
        cdv_detalleEmpresa = (CardView) findViewById(R.id.cdv_detalleEmpresa);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        rcv_canchas =(RecyclerView)findViewById(R.id.rcv_canchas) ;
        progressbarcanchas = (ProgressBar) findViewById(R.id.progressbarCanchas);
        abl_detalleEmpresa = (AppBarLayout) findViewById(R.id.abl_detalleEmpresa);
        lny_telefono = (LinearLayout) findViewById(R.id.lny_telefono);
        fab_registrarCancha = (FloatingActionButton) findViewById(R.id.fab_registrarCancha);
        cdv_mensaje = (CardView)findViewById(R.id.cdv_mensaje);
        context = this;
        activity =this;
        //rtb_valorar.setRating(4);
        rtb_valoracion.setEnabled(true);



        fab_registrarCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegistroCancha.class);
                intent.putExtra("id_empresa",id_empresa);
                startActivity(intent);
            }
        });




        id_empresa = getIntent().getStringExtra("id_empresa");
        tipo_usuario = getIntent().getStringExtra("tipo_usuario");


        lny_telefono.setOnClickListener(this);
        btn_cancelarV.setOnClickListener(this);
        btn_enviarV.setOnClickListener(this);
        btn_editVal.setOnClickListener(this);

        dc4 = new DataConnection(DetalleNegocio.this,"ObtenerSaldo",false);
        new DetalleNegocio.GetSaldo().execute();

        dc1 = new DataConnection(DetalleNegocio.this,"mostrarDetalleEmpresa",new Empresas(id_empresa,usuario_id),false);
        new GetDetalleNegocio().execute();

        dc2 = new DataConnection(DetalleNegocio.this,"listarcanchasEmpresas",new Cancha(id_empresa),false);
        new GetListadoCanchas().execute();



        rtb_valorar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(getApplicationContext(),"V "+rating,Toast.LENGTH_SHORT).show();

                    btn_enviarV.setVisibility(View.VISIBLE);
                    btn_cancelarV.setVisibility(View.VISIBLE);
                    Intent i = new Intent(DetalleNegocio.this,CalificarNegocios.class);
                    i.putExtra("valor_rating",Float.toString (rtb_valorar.getRating()));
                    startActivity(i);
                //dialogCarga();

            }
        });

       // rtb_valoracion.setRating();

    }

    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);

        dialog_cargando.show();

    }

    public static class GetSaldo extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            saldo =  new ArrayList<>();
            saldo =  dc4.getSaldo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (saldo.size()>0){
                saldo_cargado=saldo.get(0);
            }else{
                saldo_cargado="vacio";
            }




        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.lny_telefono:

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+txt_telefonoEmpresa.getText().toString()));
                startActivity(intent);
                break;

            case R.id.btn_enviarV:
                dc = new DataConnection(DetalleNegocio.this, "valorarEmpresa",
                        new Empresas(usuario_id, id_empresa,Float.toString (rtb_valorar.getRating())), true);

                break;
            case R.id.btn_cancelarV:
                rtb_valorar.setRating(Float.parseFloat(arrayempresa.get(0).getEmpresas_valoracion()));
               // rtb_valorar.setEnabled(false);
                if(arrayempresa.get(0).getEmpresas_valoracion().equals("0")){
                    rtb_valorar.setEnabled(true);
                }
                else{
                    rtb_valorar.setEnabled(false);
                    btn_editVal.setVisibility(View.VISIBLE);
                }
                btn_enviarV.setVisibility(View.GONE);
                btn_cancelarV.setVisibility(View.GONE);
                break;
            case R.id.btn_editVal:
                rtb_valorar.setEnabled(true);
                break;
        }
    }

    public static class GetDetalleNegocio extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            abl_detalleEmpresa.setVisibility(View.INVISIBLE);
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


            if (arrayempresa.size()>0){
                Picasso.with(context).load(IP2+"/"+arrayempresa.get(0).getEmpresas_foto()).into(img_fotoEmpresa);

                txt_nombreEmpresa.setText(arrayempresa.get(0).getEmpresas_nombre());
                txt_nombreUsuarioEmpresa.setText(arrayempresa.get(0).getUsuario_id());
                txt_descripcionEmpresa.setText(arrayempresa.get(0).getEmpresas_descripcion());
                txt_horario.setText(arrayempresa.get(0).getEmpresas_horario());
                txt_direccionEmpresa.setText(arrayempresa.get(0).getEmpresas_direccion());
                rtb_valorar.setRating(Float.parseFloat(arrayempresa.get(0).getEmpresas_valoracion()));
                rtb_valoracion.setRating(Float.parseFloat(arrayempresa.get(0).getEmpresas_promedio()));
                txt_conteo.setText(arrayempresa.get(0).getEmpresas_conteo());
                txt_promedio.setText(arrayempresa.get(0).getEmpresas_promedio());
                txt_telefonoEmpresa.setText(arrayempresa.get(0).getEmpresas_telefono());


                horario=arrayempresa.get(0).getEmpresas_horario();
                fecha_actual = arrayempresa.get(0).getEmpresa_cancha_fecha();
                hora_actual = arrayempresa.get(0).getEmpresa_cancha_hora();
                progressbar.setVisibility(ProgressBar.INVISIBLE);

                //Toast.makeText(getApplicationContext(),"fecha  "+fecha+"  "+"Hora  "+ hora,Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,"V "+ arrayempresa.get(0).getEmpresas_valoracion(), Toast.LENGTH_SHORT).show();
                //int nun =09;

                abl_detalleEmpresa.setVisibility(View.VISIBLE);
                cdv_detalleEmpresa.setVisibility(View.VISIBLE);

         /*   btn_cancelarV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_enviarV.setVisibility(View.GONE);
                    btn_cancelarV.setVisibility(View.GONE);
                    rtb_valorar.setRating(Float.parseFloat(arrayempresa.get(0).getEmpresas_promedio()));
                    rtb_valorar.setEnabled(false);
                }
            });*/

                if(arrayempresa.get(0).getEmpresas_valoracion().equals("0")){
                    rtb_valorar.setEnabled(true);
                    btn_editVal.setVisibility(View.INVISIBLE);
                    btn_enviarV.setVisibility(View.GONE);
                    btn_cancelarV.setVisibility(View.GONE);

                }
                else{
                    rtb_valorar.setEnabled(false);
                    btn_cancelarV.setVisibility(View.GONE);
                    btn_enviarV.setVisibility(View.GONE);
                    btn_editVal.setVisibility(View.VISIBLE);
                }
            }





        }
    }
    public static class GetDetalleNegocioVal extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //abl_detalleEmpresa.setVisibility(View.INVISIBLE);
            //cdv_detalleEmpresa.setVisibility(View.INVISIBLE);


        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayempresa = dc1.getListadoEmpresas();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);




            rtb_valorar.setRating(Float.parseFloat(arrayempresa.get(0).getEmpresas_valoracion()));
            rtb_valoracion.setRating(Float.parseFloat(arrayempresa.get(0).getEmpresas_promedio()));
            txt_conteo.setText(arrayempresa.get(0).getEmpresas_conteo());
            txt_promedio.setText(arrayempresa.get(0).getEmpresas_promedio());


            progressbar.setVisibility(ProgressBar.INVISIBLE);

            Toast.makeText(context,"AV "+ arrayempresa.get(0).getEmpresas_valoracion(), Toast.LENGTH_SHORT).show();


            btn_enviarV.setVisibility(View.GONE);
            btn_cancelarV.setVisibility(View.GONE);



            if(arrayempresa.get(0).getEmpresas_valoracion().equals("0")){
                rtb_valorar.setEnabled(true);
                btn_editVal.setVisibility(View.INVISIBLE);
                btn_enviarV.setVisibility(View.GONE);
                btn_cancelarV.setVisibility(View.GONE);

            }
            else{
                rtb_valorar.setEnabled(false);
                btn_enviarV.setVisibility(View.GONE);
                btn_cancelarV.setVisibility(View.GONE);
                btn_editVal.setVisibility(View.VISIBLE);
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


            progressbarcanchas.setVisibility(ProgressBar.INVISIBLE);

            GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
            linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
            rcv_canchas.setLayoutManager(linearLayoutManager);

            adaptadorListadoCanchaEmpresa = new AdaptadorListadoCanchaEmpresa(context, arraycancha, R.layout.rcv_item_card_canchas, new AdaptadorListadoCanchaEmpresa.OnItemClickListener() {
                @Override
                public void onItemClick(Cancha cancha, final int position) {

                    cancha_id = cancha.getCancha_id().toString();
                    Toast.makeText(context,"ID cancha :"+cancha_id,Toast.LENGTH_SHORT).show();

                    if (!saldo_cargado.equals("") && !saldo_cargado.equals(null) ){
                        Intent intent = new Intent(context,DetalleCanchas.class);
                        intent.putExtra("id_cancha",cancha.getCancha_id());
                        intent.putExtra("nombre_empresa",txt_nombreEmpresa.getText().toString());
                        intent.putExtra("nombre_cancha",cancha.getCancha_nombre());
                        intent.putExtra("precio_dia",cancha.getCancha_precioD());
                        intent.putExtra("precio_noche",cancha.getCancha_precioN());
                        intent.putExtra("horario",horario);
                        intent.putExtra("tipo_usuario",tipo_usuario);
                        intent.putExtra("saldo",saldo_cargado);
                        context.startActivity(intent);
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
            progressbarcanchas.setVisibility(ProgressBar.VISIBLE);
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

            adaptadorListadoCanchaEmpresa = new AdaptadorListadoCanchaEmpresa(context, arraycanchaactual, R.layout.rcv_item_card_canchas, new AdaptadorListadoCanchaEmpresa.OnItemClickListener(){
                @Override
                public void onItemClick(Cancha cancha, final int position) {

                    if (!saldo_cargado.equals("") && !saldo_cargado.equals(null) ){
                        Intent intent = new Intent(context,DetalleCanchas.class);
                        intent.putExtra("id_cancha",cancha.getCancha_id());
                        intent.putExtra("nombre_empresa",txt_nombreEmpresa.getText().toString());
                        intent.putExtra("nombre_cancha",cancha.getCancha_nombre());
                        intent.putExtra("precio_dia",cancha.getCancha_precioD());
                        intent.putExtra("precio_noche",cancha.getCancha_precioN());
                        intent.putExtra("horario",horario);
                        intent.putExtra("tipo_usuario",tipo_usuario);
                        intent.putExtra("saldo",saldo_cargado);
                        context.startActivity(intent);
                    }



                }

            });

            progressbarcanchas.setVisibility(ProgressBar.INVISIBLE);

            rcv_canchas.setAdapter(adaptadorListadoCanchaEmpresa);


            if( arraycanchaactual.size()>0){
                cdv_mensaje.setVisibility(View.INVISIBLE);
            }else{
                cdv_mensaje.setVisibility(View.VISIBLE);
            }

        }
    }
    public static void ActualizarCanchas(){
        dc3 = new DataConnection(activity,"listarcanchasEmpresas",new Cancha(id_empresa),false);
        new ActualizarCanchass().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (tipo_usuario.equals("admin")){
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


