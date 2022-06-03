package com.tec.bufeo.capitan.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.DetalleEquipoNuevo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.DetalleTorneoNuevo;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.PerfilUsuarios;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Views.AdaptadorForo;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Models.Mequipos;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Views.AdaptadorMiEquipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyler_publish,recycler_equipos;
    MisEquiposViewModel misEquiposViewModel;
    AdaptadorMiEquipo adaptadorMiEquipo;
    Preferences preferences;
    ImageView fotodeperfil;
    TextView nombre_perfil,posicion_juegador,Ncamiseta,habilidadjuegador,EmailJuegador;
    LinearLayout editarPerfil;
    ImageButton buttonEditarPerfil;

    View bottomOpcionesFoto;
    BottomSheetBehavior mBottomSheetBehavior;
    LinearLayout tap_de_accion_profile,verFoto,cambiarFoto,tap_foto,accionGaleria,accionTomarFoto,publicar;
    ImageView btnClose_profile;
    TextView titulotap;
    UniversalImageLoader universalImageLoader;
    FeedListViewModel feedListViewModel;
    AdaptadorForo adaptadorForo;
    Activity activity;
    Context context;
    String userChoosenTask;
    public Uri output,resultUriRecortada;
    private int REQUEST_CAMERA = 0,  SELET_GALERRY = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(this).get(MisEquiposViewModel.class);
        preferences =  new Preferences(this);
        universalImageLoader= new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        activity = ProfileActivity.this;
        context = getApplicationContext();




        initViews();

        UniversalImageLoader.setImage(IP2+"/"+ preferences.getFotoUsuario(),fotodeperfil,null);




            nombre_perfil.setText(preferences.getPersonName()+" "+preferences.getPersonSurname());
            posicion_juegador.setText(preferences.getPosicionJugador());
            Ncamiseta.setText(preferences.getNumeroCamiseta());
            habilidadjuegador.setText(preferences.getHabilidadJuegador());
            EmailJuegador.setText(preferences.getEmailJuegador());



          this.getWindow().getDecorView().setSystemUiVisibility( //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                     View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    //| View.SYSTEM_UI_FLAG_FULLSCREEN
                    //| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          );



        setAdapter();
        cargarvista();
        showToolbar("",true);

        mBottomSheetBehavior= BottomSheetBehavior.from(bottomOpcionesFoto);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    tap_de_accion_profile.setVisibility(View.GONE);
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    tap_de_accion_profile.setVisibility(View.VISIBLE);
                }
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    tap_de_accion_profile.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });


        fotodeperfil.setOnClickListener(this);
        verFoto.setOnClickListener(this);
        cambiarFoto.setOnClickListener(this);
        btnClose_profile.setOnClickListener(this);
        accionGaleria.setOnClickListener(this);
        accionTomarFoto.setOnClickListener(this);
        editarPerfil.setOnClickListener(this);
        buttonEditarPerfil.setOnClickListener(this);
        publicar.setOnClickListener(this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();//definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.WHITE);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);  //y habilitamos la flacha hacia atras

    }

    private void setAdapter() {

        adaptadorForo= new AdaptadorForo(this, new AdaptadorForo.OnItemClickListener() {
            @Override
            public void onItemClick(String dato, ModelFeed feedTorneo, int position) {
                /*if (dato.equals("btnAccion")){

                    idpublicacion=feedTorneo.getPublicacion_id();
                    fab_registrarForo.hide();
                    if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }*/
                if (dato.equals("foto_perfil_publicacion")){
                    if (feedTorneo.getId_torneo().equals("0")){
                        if(feedTorneo.getUsuario_id().equals(preferences.getIdUsuarioPref())){
                            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getApplicationContext(), PerfilUsuarios.class);
                            i.putExtra("id_user",feedTorneo.getUsuario_id());
                            startActivity(i);
                        }
                    }else{
                        Intent i = new Intent(getApplicationContext(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",feedTorneo.getId_torneo());
                        i.putExtra("foto",feedTorneo.getTorneo_foto());
                        i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                        i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                        startActivity(i);
                    }

                }if (dato.equals("txt_usuarioForo")){
                    if (feedTorneo.getId_torneo().equals("0")){
                        if(feedTorneo.getUsuario_id().equals(preferences.getIdUsuarioPref())){
                            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getApplicationContext(), PerfilUsuarios.class);
                            i.putExtra("id_user",feedTorneo.getUsuario_id());
                            startActivity(i);
                        }
                    }else{
                        Intent i = new Intent(getApplicationContext(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",feedTorneo.getId_torneo());
                        i.putExtra("foto",feedTorneo.getTorneo_foto());
                        i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                        i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                        startActivity(i);
                    }


                }if (dato.equals("img_fotoForo")){
                    Intent i = new Intent(getApplicationContext(), DetalleFotoUsuario.class);
                    i.putExtra("foto",feedTorneo.getForo_foto());
                    i.putExtra("descripcion",feedTorneo.getForo_descripcion());
                    i.putExtra("cantidad_comentarios",feedTorneo.getCant_Comentarios());
                    i.putExtra("id_publicacion",feedTorneo.getPublicacion_id());
                    startActivity(i);
                }else if(dato.equals("pedir")){
                    //feed();
                    preferences.codeAdvertencia(String.valueOf(position));
                }else if(dato.equals("verMasTorneo")){
                    Intent i = new Intent(getApplicationContext(), DetalleTorneoNuevo.class);
                    i.putExtra("id_torneo",feedTorneo.getId_torneo());
                    i.putExtra("foto",feedTorneo.getTorneo_foto());
                    i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                    i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                    startActivity(i);
                }else if (dato.equals("imgbt_like")){
                    if (feedTorneo.getDio_like().equals("0")){
                        darlike(feedTorneo.getPublicacion_id());
                    }else{
                        dislike(feedTorneo.getPublicacion_id());
                    }
                }

            }
        });

        recyler_publish.setAdapter(adaptadorForo);
        recyler_publish.setLayoutManager(new LinearLayoutManager(this));


        adaptadorMiEquipo =  new AdaptadorMiEquipo(this,"", new AdaptadorMiEquipo.OnItemClickListener() {
            @Override
            public void onItemClick(Mequipos mequipos, int position) {

                Intent intent = new Intent(ProfileActivity.this, DetalleEquipoNuevo.class);
                intent.putExtra("id_equipo", mequipos.getEquipo_id());
                intent.putExtra("nombre_equipo", mequipos.getEquipo_nombre());
                intent.putExtra("foto_equipo", mequipos.getEquipo_foto());
                intent.putExtra("capitan_equipo", mequipos.getCapitan_nombre());
                intent.putExtra("capitan_id", mequipos.getCapitan_id());
                startActivity(intent);
            }
        });



        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_equipos.setLayoutManager(linearLayoutManager);
        recycler_equipos.setAdapter(adaptadorMiEquipo);
    }

    private void cargarvista() {

        feedListViewModel.getIdUsuario(preferences.getIdUsuarioPref(),preferences.getToken(),"").observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(List<ModelFeed> modelFeeds) {
                adaptadorForo.setWords(modelFeeds);
            }
        });




        misEquiposViewModel.getAllEquipo("si").observe(this, new Observer<List<Mequipos>>() {
            @Override
            public void onChanged(@Nullable List<Mequipos> mequipos) {
                adaptadorMiEquipo.setWords(mequipos);
            }
        });

    }

    private void initViews() {
        recyler_publish = findViewById(R.id.recyler_publish);
        recycler_equipos = findViewById(R.id.recycler_equipos);
        fotodeperfil = findViewById(R.id.fotodeperfil);
        nombre_perfil = findViewById(R.id.nombre_perfil);
        posicion_juegador = findViewById(R.id.posicion_juegador);
        Ncamiseta = findViewById(R.id.Ncamiseta);
        habilidadjuegador = findViewById(R.id.habilidadjuegador);
        EmailJuegador = findViewById(R.id.EmailJuegador);
        editarPerfil = findViewById(R.id.editarPerfil);
        buttonEditarPerfil = findViewById(R.id.buttonEditarPerfil);
        publicar = findViewById(R.id.publicar);



        bottomOpcionesFoto = findViewById(R.id.bottomOpcionesFoto);
        tap_de_accion_profile = findViewById(R.id.tap_de_accion_profile);
        accionGaleria = findViewById(R.id.accionGaleria);
        accionTomarFoto = findViewById(R.id.accionTomarFoto);
        tap_foto = findViewById(R.id.tap_foto);
        titulotap = findViewById(R.id.titulotap);
        verFoto = findViewById(R.id.verFoto);
        cambiarFoto = findViewById(R.id.cambiarFoto);
        btnClose_profile = findViewById(R.id.btnClose_profile);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(fotodeperfil)){
            tap_de_accion_profile.setVisibility(View.VISIBLE);
            tap_foto.setVisibility(View.GONE);
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }else{
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }else if (v.equals(verFoto)){
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

            Intent i = new Intent(getApplicationContext(), DetalleFotoUsuario.class);
            i.putExtra("foto",preferences.getFotoUsuario());
            i.putExtra("descripcion","0");
            i.putExtra("cantidad_comentarios","0");
            i.putExtra("id_publicacion","0");
            startActivity(i);

        }else if (v.equals(cambiarFoto)){
            preferences.toasVerde("aca Cambiaremos la foto");
            //selectImage("Perfil");
            tap_de_accion_profile.setVisibility(View.GONE);
            tap_foto.setVisibility(View.VISIBLE);
            /*if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }*/
        }else if (v.equals(btnClose_profile)){
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }else if (v.equals(accionGaleria)){
            acciongaleria();
        }else if (v.equals(accionTomarFoto)){
            accionCamara();
        }else if (v.equals(editarPerfil)){
            Intent i = new Intent(ProfileActivity.this, InformacionGeneral.class);
            startActivity(i);
        }else if (v.equals(buttonEditarPerfil)){
            Intent i = new Intent(ProfileActivity.this, InformacionGeneral.class);
            startActivity(i);
        }else if (v.equals(publicar)){
            Intent i = new Intent(ProfileActivity.this, RegistroForo.class);
            startActivity(i);
        }

    }



    public void accionCamara(){
        userChoosenTask ="Camara";

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","perfiles");
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
    }

    public void acciongaleria(){
        userChoosenTask ="Galería";


        Intent intentgaleria = new Intent(Intent.ACTION_PICK);
        intentgaleria.setType("image/*");
        if (intentgaleria.resolveActivity(activity.getPackageManager())!=null){


            startActivityForResult(intentgaleria,SELET_GALERRY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA){


                CropImage.activity(output).start(this);
            }else if (requestCode == SELET_GALERRY) {

                Uri uri = result.getData();

                File f1,f2;
                f1 = new File(getRealPathFromUri(activity,uri));
                String fname = f1.getName();


                f2= new File(Environment.getExternalStorageDirectory() + "/Capitan/","perfiles");
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
                    Log.e("cortado", "onActivityResult: "+ e.getMessage() );
                }  finally {
                    //Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                }


                CropImage.activity(uri).start(activity);

            }
        }if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultado = CropImage.getActivityResult(result);
            if (resultCode == RESULT_OK) {

                resultUriRecortada = resultado.getUri();

                fotodeperfil.setImageBitmap(BitmapFactory.decodeFile(resultUriRecortada.getPath()));
                uploadMultipart();




            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = resultado.getError();
                Log.e("cortado", "onActivityResult: "+ error );
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

    String url = IP2+"/api/User/actualizar_perfil";
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
                    .addParameter("app", "true") //Adding file
                    .addParameter("token", preferences.getToken()) //Adding file
                    .addParameter("usuario_id", preferences.getIdUsuarioPref()) //Adding text parameter to the request

                    .setNotificationConfig(getNotificationConfig(uploadId,R.string.cargando))
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {


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





                            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            }

                            //UniversalImageLoader.setImage(IP2+"/"+ preferences.getFotoUsuario(),fotodeperfil,null);
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
            Log.e("cortado", "onActivityResult: "+ exc.getMessage() );
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


    StringRequest stringRequest;
    JSONObject json_data;
    String resultado;
    int totalLikes;
    Application application;
    private void darlike(final String idlike) {
        String url =IP2+"/api/Foro/dar_like";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("darlike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
                        feedRoomDBRepository.darlike(idlike);
                        feedRoomDBRepository.cantidadLikes(String.valueOf(totalLikes));
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("publicacion_id",idlike);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                return parametros;

            }
        };
        /*requestQueue.add(stringRequest);*/
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    private void dislike(final String iddislike) {
        String url =IP2+"/api/Foro/quitar_like";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dislike: ",""+response);

                try {
                    json_data = new JSONObject(response);
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    resultado = jsonNodev.optString("resultado");
                    totalLikes = Integer.parseInt(jsonNodev.optString("likes"));

                    if (resultado.equals("1")){

                        FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
                        feedRoomDBRepository.dislike(iddislike);
                        feedRoomDBRepository.cantidadLikes(String.valueOf(totalLikes));
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"error ",Toast.LENGTH_SHORT).show();
                Log.i("RESPUESTA: ",""+error.toString());

            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String imagen=convertirImgString(bitmap);


                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario_id",preferences.getIdUsuarioPref());
                parametros.put("publicacion_id",iddislike);
                parametros.put("app","true");
                parametros.put("token",preferences.getToken());

                return parametros;

            }
        };
        //requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
