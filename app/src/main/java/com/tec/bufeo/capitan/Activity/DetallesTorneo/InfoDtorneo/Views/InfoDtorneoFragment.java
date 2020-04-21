package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Views;


import android.app.Application;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tec.bufeo.capitan.Activity.DetalleFotoUsuario;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.DetalleTorneoNuevo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.DetalleTorneoViewModel;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.PerfilUsuarios;
import com.tec.bufeo.capitan.Activity.ProfileActivity;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Views.AdaptadorForo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class InfoDtorneoFragment extends Fragment {


    TextView titulo_infotorneo,fecha_infotorneo,hora_infotorneo,organizador_infotorneo,lugar_infotorneo,costo_infotorneo;

    RecyclerView rcv_infotorneo;
    String id_torneo;
    Application application;
    Preferences preferences;
    FeedListViewModel feedListViewModel;
    AdaptadorForo adaptadorForo;
    DetalleTorneoViewModel detalleTorneoViewModel;

    public InfoDtorneoFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedListViewModel = ViewModelProviders.of(this).get(FeedListViewModel.class);
        detalleTorneoViewModel = ViewModelProviders.of(this).get(DetalleTorneoViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_dtorneo, container, false);

        preferences= new Preferences(getContext());

        final Bundle bdl = getArguments();


        id_torneo = bdl.getString("id_torneo");


        initViews(view);
        setAdapter();
        cargarvista();



        return  view;

    }

    private void initViews(View view) {
        titulo_infotorneo = view.findViewById(R.id.titulo_infotorneo);
        fecha_infotorneo = view.findViewById(R.id.fecha_infotorneo);
        hora_infotorneo = view.findViewById(R.id.hora_infotorneo);
        organizador_infotorneo = view.findViewById(R.id.organizador_infotorneo);
        lugar_infotorneo = view.findViewById(R.id.lugar_infotorneo);
        costo_infotorneo = view.findViewById(R.id.costo_infotorneo);
        rcv_infotorneo = view.findViewById(R.id.rcv_infotorneo);



    }
    private void setAdapter() {

        adaptadorForo = new AdaptadorForo(getContext(), new AdaptadorForo.OnItemClickListener() {
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
                            Intent i = new Intent(getContext(), ProfileActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getContext(), PerfilUsuarios.class);
                            i.putExtra("id_user",feedTorneo.getUsuario_id());
                            startActivity(i);
                        }
                    }else{
                        Intent i = new Intent(getContext(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",feedTorneo.getId_torneo());
                        i.putExtra("foto",feedTorneo.getTorneo_foto());
                        i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                        i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                        startActivity(i);
                    }

                }if (dato.equals("txt_usuarioForo")){
                    if (feedTorneo.getId_torneo().equals("0")){
                        if(feedTorneo.getUsuario_id().equals(preferences.getIdUsuarioPref())){
                            Intent i = new Intent(getContext(), ProfileActivity.class);
                            startActivity(i);
                        }else{
                            Intent i = new Intent(getContext(), PerfilUsuarios.class);
                            i.putExtra("id_user",feedTorneo.getUsuario_id());
                            startActivity(i);
                        }
                    }else{
                        Intent i = new Intent(getContext(), DetalleTorneoNuevo.class);
                        i.putExtra("id_torneo",feedTorneo.getId_torneo());
                        i.putExtra("foto",feedTorneo.getTorneo_foto());
                        i.putExtra("nombre",feedTorneo.getPublicacion_torneo());
                        i.putExtra("id_usuario",feedTorneo.getUsuario_id());
                        startActivity(i);
                    }


                }if (dato.equals("img_fotoForo")){
                    Intent i = new Intent(getContext(), DetalleFotoUsuario.class);
                    i.putExtra("foto",feedTorneo.getForo_foto());
                    i.putExtra("descripcion",feedTorneo.getForo_descripcion());
                    i.putExtra("cantidad_comentarios",feedTorneo.getCant_Comentarios());
                    i.putExtra("id_publicacion",feedTorneo.getPublicacion_id());
                    startActivity(i);
                }if(dato.equals("pedir")){
                    //feed();
                    preferences.codeAdvertencia(String.valueOf(position));
                }else if(dato.equals("verMasTorneo")){
                    Intent i = new Intent(getContext(), DetalleTorneoNuevo.class);
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

        rcv_infotorneo.setAdapter(adaptadorForo);
        rcv_infotorneo.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void cargarvista() {
        feedListViewModel.getIdTorneo(preferences.getIdUsuarioPref(),preferences.getToken(),id_torneo).observe(this, new Observer<List<ModelFeed>>() {
            @Override
            public void onChanged(List<ModelFeed> modelFeeds) {
                adaptadorForo.setWords(modelFeeds);
            }
        });

        detalleTorneoViewModel.getIdTorneo(id_torneo,preferences.getToken()).observe(this, new Observer<List<DetalleTorneo>>() {
            @Override
            public void onChanged(List<DetalleTorneo> detalleTorneos) {
                if (detalleTorneos.size()>0){





                    titulo_infotorneo.setText(detalleTorneos.get(0).getNombre_torneo());
                    fecha_infotorneo.setText(detalleTorneos.get(0).getFecha_torneo());
                    hora_infotorneo.setText(detalleTorneos.get(0).getHora_torneo());
                    organizador_infotorneo.setText(detalleTorneos.get(0).getOrganizador_torneo());
                    lugar_infotorneo.setText(detalleTorneos.get(0).getLugar_torneo());
                    String costoso = detalleTorneos.get(0).getCosto_torneo();
                    costo_infotorneo.setText(costoso);
                    //costo_infotorneo= costoso;
                }
            }
        });



    }



    StringRequest stringRequest;
    JSONObject json_data;
    String resultado;
    int totalLikes;
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
                Log.d("RESPUESTA: ",""+error.toString());

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
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
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
                Log.d("RESPUESTA: ",""+error.toString());

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
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
}
