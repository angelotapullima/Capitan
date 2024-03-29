package com.tec.bufeo.capitan.Activity.DetalleNegocio;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.CalificarNegocios;
import com.tec.bufeo.capitan.Activity.DetalleCanchas.Views.DetalleCanchas;
import com.tec.bufeo.capitan.Activity.DetalleFotoUsuario;
import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.EstadisticasEmpresas;
import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Views.ReviewComentarios;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Canchas;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Galeria;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model.Negocios;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.ViewModels.CanchasViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.ViewModels.NegociosViewModel;
import com.tec.bufeo.capitan.Activity.PromocionesCanchas;
import com.tec.bufeo.capitan.Activity.ratings.BarLabels;
import com.tec.bufeo.capitan.Activity.ratings.RatingReviews;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoCanchaEmpresa;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class DetalleNegocio extends AppCompatActivity implements View.OnClickListener {

    ImageView img_fotoEmpresa,Misnegocios;
    RecyclerView rcv_canchas,rcv_galeria;
    AppBarLayout abl_detalleEmpresa;
    public TextView txt_nombreEmpresa, txt_descripcionEmpresa, txt_direccionEmpresa,separadorTelefonos,
            txt_telefonoEmpresa,txt_telefonoEmpresa2, txt_horario,rating_float,conteo,estadoNegocio,vistaMapa;



    public RatingBar  rtb_valorar,ratingBar_promedio;
    CardView cdv_detalleEmpresa;
    LinearLayout lny_telefono,masComentarios;
    AdaptadorListadoCanchaEmpresa adaptadorListadoCanchaEmpresa;
    CardView cdv_mensaje;
    public Context context;
    public Activity activity;
    CoordinatorLayout cordinator;
    Preferences preferences;
    String id_empresa, tipo_usuario,latitud ="",longitud="";
    FrameLayout frameCargaArribaNegocios,frameCarga2;
    public String fecha_actual, hora_actual, cancha_id, horario;
    MaterialButton promociones;
    UniversalImageLoader universalImageLoader;
    RatingReviews rating_reviews;
    NegociosViewModel negociosViewModel;
    CanchasViewModel canchasViewModel;
    TextView imagenesLocal;
    AdapterGaleria adapterGaleria;
    ImageButton imb_mas_Comentarios;

    int colors[] = new int[]{
            Color.parseColor("#0e9d58"),
            Color.parseColor("#bfd047"),
            Color.parseColor("#ffc105"),
            Color.parseColor("#ef7e14"),
            Color.parseColor("#d36259")};
    public void getReportes() {
        Intent intent = new Intent(this, EstadisticasEmpresas.class);
        intent.putExtra("id_empresa",id_empresa);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_negocio);

        canchasViewModel = ViewModelProviders.of(this).get(CanchasViewModel.class);
        negociosViewModel = ViewModelProviders.of(this).get(NegociosViewModel.class);
        preferences= new Preferences(this);

        id_empresa = getIntent().getExtras().getString("id_empresa");
        universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        initViews();
        setAdapter();
        cargarvista();


    }


    private void initViews() {
        img_fotoEmpresa = (ImageView) findViewById(R.id.img_fotoEmpresa);

        cordinator=(CoordinatorLayout)findViewById(R.id.cordinator);




        rating_reviews =  findViewById(R.id.rating_reviews);



        txt_nombreEmpresa = (TextView) findViewById(R.id.txt_nombreEmpresa);
        txt_telefonoEmpresa2 = (TextView) findViewById(R.id.txt_telefonoEmpresa2);
        rating_float = (TextView) findViewById(R.id.rating_float);
        ratingBar_promedio = (RatingBar) findViewById(R.id.ratingBar_promedio);
        conteo = (TextView) findViewById(R.id.conteo);
        txt_telefonoEmpresa = (TextView) findViewById(R.id.txt_telefonoEmpresa);
        txt_descripcionEmpresa = (TextView) findViewById(R.id.txt_descripcionEmpresa);
        txt_horario = (TextView) findViewById(R.id.txt_horario);
        txt_direccionEmpresa = (TextView) findViewById(R.id.txt_direccionEmpresa);
        rtb_valorar = (RatingBar) findViewById(R.id.rtb_valorar);
        rcv_galeria = (RecyclerView) findViewById(R.id.rcv_galeria);
        cdv_detalleEmpresa = (CardView) findViewById(R.id.cdv_detalleEmpresa);
        frameCargaArribaNegocios = (FrameLayout) findViewById(R.id.frameCargaArribaNegocios);
        frameCarga2 = (FrameLayout) findViewById(R.id.frameCarga2);
        rcv_canchas = (RecyclerView) findViewById(R.id.rcv_canchas);
        abl_detalleEmpresa = (AppBarLayout) findViewById(R.id.abl_detalleEmpresa);
        lny_telefono = (LinearLayout) findViewById(R.id.lny_telefono);
        masComentarios = (LinearLayout) findViewById(R.id.masComentarios);
        separadorTelefonos = (TextView) findViewById(R.id.separadorTelefonos);
        cdv_mensaje = (CardView) findViewById(R.id.cdv_mensaje);
        estadoNegocio = (TextView) findViewById(R.id.estadoNegocio);
        imagenesLocal = (TextView) findViewById(R.id.imagenesLocal);
        vistaMapa = (TextView) findViewById(R.id.vistaMapa);
        Misnegocios = (ImageView) findViewById(R.id.Misnegocios);
        promociones = (MaterialButton) findViewById(R.id.promociones);
        imb_mas_Comentarios = (ImageButton) findViewById(R.id.imb_mas_Comentarios);
        context = this;
        activity = this;
        //rtb_valorar.setRating(4);
        //rtb_valoracion.setEnabled(true);





        id_empresa = getIntent().getStringExtra("id_empresa");
        tipo_usuario = getIntent().getStringExtra("tipo_usuario");


        txt_telefonoEmpresa.setOnClickListener(this);
        txt_telefonoEmpresa2.setOnClickListener(this);
        masComentarios.setOnClickListener(this);
        vistaMapa.setOnClickListener(this);

        if (tipo_usuario.equals("admin")){
            Misnegocios.setVisibility(View.VISIBLE);
            promociones.setVisibility(View.VISIBLE);
        }else{
            Misnegocios.setVisibility(View.GONE);
            promociones.setVisibility(View.GONE);
        }








        // rtb_valoracion.setRating();

        //showToolbar("Detalle de Negocio",true);
        Misnegocios.setOnClickListener(this);
        promociones.setOnClickListener(this);
    }

    private void setAdapter() {

        adaptadorListadoCanchaEmpresa = new AdaptadorListadoCanchaEmpresa(this, new AdaptadorListadoCanchaEmpresa.OnItemClickListener() {
            @Override
            public void onItemClick(Canchas cancha, String tipo, int position) {
                if (tipo.equals("img_fotoCancha")){



                        Intent intent = new Intent(context, DetalleCanchas.class);
                        intent.putExtra("id_cancha", cancha.getCancha_id());
                        intent.putExtra("nombre_empresa", txt_nombreEmpresa.getText().toString());
                        intent.putExtra("nombre_cancha", cancha.getNombre_cancha());
                        intent.putExtra("precio_dia", cancha.getPrecioD());
                        intent.putExtra("precio_noche", cancha.getPrecioN());
                        intent.putExtra("horario", horario);
                        intent.putExtra("tipo_usuario", tipo_usuario);
                        intent.putExtra("hora_actual", hora_actual);
                        intent.putExtra("fecha_actual", fecha_actual);
                        intent.putExtra("telefono", txt_telefonoEmpresa.getText().toString());
                        intent.putExtra("telefono2", txt_telefonoEmpresa2.getText().toString());
                        intent.putExtra("direccion", txt_direccionEmpresa.getText().toString());
                        intent.putExtra("promo_estado", cancha.getPromo_estado());
                        intent.putExtra("promo_fin", cancha.getPromo_fin());
                        intent.putExtra("promo_inicio", cancha.getPromo_inicio());
                        intent.putExtra("promo_precio", cancha.getPromo_precio());
                        intent.putExtra("foto", cancha.getFoto());
                        context.startActivity(intent);




                }
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        rcv_canchas.setLayoutManager(linearLayoutManager);
        rcv_canchas.setAdapter(adaptadorListadoCanchaEmpresa);


        adapterGaleria = new AdapterGaleria(this, new AdapterGaleria.OnItemClickListener() {
            @Override
            public void onItemClick(Galeria cancha, String tipo, int position) {

                if (tipo.equals("foto")){
                    Intent i = new Intent(getApplicationContext(), DetalleFotoUsuario.class);
                    i.putExtra("foto",cancha.getGaleria_foto());
                    i.putExtra("descripcion","0");
                    i.putExtra("cantidad_comentarios","0");
                    i.putExtra("id_publicacion","0");
                    startActivity(i);
                }
            }
        });

        //RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstBook);
        rcv_galeria.setLayoutManager(new GridLayoutManager(this,2));
        rcv_galeria.setAdapter(adapterGaleria);
    }







    private void cargarvista() {
        negociosViewModel.getAllDetalle(id_empresa).observe(this, new Observer<List<Negocios>>() {
            @Override
            public void onChanged(List<Negocios> negocios) {
                if (negocios.size()>0){


                    universalImageLoader.setImage(IP2+"/"+ negocios.get(0).getFoto_empresa(),img_fotoEmpresa,null);


                    txt_nombreEmpresa.setText(negocios.get(0).getNombre_empresa());
                    txt_descripcionEmpresa.setText(negocios.get(0).getDescripcion_empresa());

                    showToolbar(negocios.get(0).getNombre_empresa(),true);

                    Date date =new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH");
                    String hora = sdf.format(date);
                    int horaactual = Integer.parseInt(hora);


                    if (negocios.get(0).getDia_actual().equals("7")){
                        txt_horario.setText(negocios.get(0).getHorario_d_empresa());
                        horario = negocios.get(0).getHorario_d_empresa();
                    }else{
                        txt_horario.setText(negocios.get(0).getHorario_ls_empresa());
                        horario = negocios.get(0).getHorario_ls_empresa();

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

                    txt_direccionEmpresa.setText(negocios.get(0).getDireccion_empresa());


                    rating_float.setText(negocios.get(0).getPromedio_empresa());
                    conteo.setText(negocios.get(0).getConteo_empresa());
                    ratingBar_promedio.setRating(Float.parseFloat(negocios.get(0).getPromedio_empresa()));






                    int raters[] = new int[5];

                    for (int i = 0; i < 5; i++) {
                        if (i==0){
                            raters[i] = Integer.parseInt(negocios.get(0).getList_ratings().get(0).getConteo1());
                        }else if (i==1){
                            raters[i] = Integer.parseInt(negocios.get(0).getList_ratings().get(0).getConteo2());
                        }else if (i==2){
                            raters[i] = Integer.parseInt(negocios.get(0).getList_ratings().get(0).getConteo3());
                        }else if (i==3){
                            raters[i] = Integer.parseInt(negocios.get(0).getList_ratings().get(0).getConteo4());
                        }else if (i==4){
                            raters[i] = Integer.parseInt(negocios.get(0).getList_ratings().get(0).getConteo5());
                        }


                    }
                    rating_reviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);



                    if (negocios.get(0).getTelefono_1_empresa().isEmpty() && !negocios.get(0).getTelefono_2_empresa().isEmpty()){
                        txt_telefonoEmpresa.setVisibility(View.GONE);
                        separadorTelefonos.setVisibility(View.GONE);
                        txt_telefonoEmpresa2.setText(negocios.get(0).getTelefono_2_empresa());
                    }else if (!negocios.get(0).getTelefono_1_empresa().isEmpty() && negocios.get(0).getTelefono_2_empresa().isEmpty()){
                        txt_telefonoEmpresa2.setVisibility(View.GONE);
                        separadorTelefonos.setVisibility(View.GONE);
                        txt_telefonoEmpresa.setText(negocios.get(0).getTelefono_1_empresa());
                    }else{
                        txt_telefonoEmpresa.setText(negocios.get(0).getTelefono_1_empresa());
                        txt_telefonoEmpresa2.setText(negocios.get(0).getTelefono_2_empresa());
                    }


                    if (negocios.get(0).getList_galeria().size()>0){
                        adapterGaleria.setWords(negocios.get(0).getList_galeria());
                    }else{
                        rcv_galeria.setVisibility(View.GONE);
                        imagenesLocal.setVisibility(View.GONE);
                    }

                    fecha_actual = negocios.get(0).getFecha_actual();
                    hora_actual = negocios.get(0).getHora_actual();
                    frameCargaArribaNegocios.setVisibility(View.GONE);
                    frameCarga2.setVisibility(View.GONE);
                    //progressbar.setVisibility(ProgressBar.INVISIBLE);

                    latitud =negocios.get(0).getLatitud();
                    longitud =negocios.get(0).getLongitud();

                    if (!negocios.get(0).getValoracion_empresa().equals("0")){

                        rtb_valorar.setRating(Float.parseFloat(negocios.get(0).getValoracion_empresa()));
                    }else{

                    }

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
                    abl_detalleEmpresa.setVisibility(View.VISIBLE);
                    cdv_detalleEmpresa.setVisibility(View.VISIBLE);
                }
            }
        });

        canchasViewModel.getCanchas(id_empresa,preferences.getToken()).observe(this, new Observer<List<Canchas>>() {
            @Override
            public void onChanged(List<Canchas> canchas) {
                if (canchas.size()>0){
                    cdv_mensaje.setVisibility(View.GONE);
                    adaptadorListadoCanchaEmpresa.setWords(canchas);
                }else{
                    cdv_mensaje.setVisibility(View.VISIBLE);
                }

                //adaptadorListadoCanchaEmpresa
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
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
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_cargando.findViewById(R.id.back);
        LinearLayout layout = dialog_cargando.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cargando.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
        });

        dialog_cargando.show();

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
        }else if (v.equals(promociones)){

            Intent i = new Intent(DetalleNegocio.this, PromocionesCanchas.class);
            i.putExtra("id_empresa",id_empresa);
            startActivity(i);
        }else if (v.equals(masComentarios)){

            Intent i = new Intent(getApplicationContext(), ReviewComentarios.class);
            i.putExtra("id_empresa",id_empresa);
            startActivity(i);
        }

    }
}


