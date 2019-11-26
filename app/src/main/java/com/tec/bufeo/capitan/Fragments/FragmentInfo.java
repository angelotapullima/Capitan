package com.tec.bufeo.capitan.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Activity.Login;
import com.tec.bufeo.capitan.Activity.PerfilEdit;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoConfiguracion;
import com.tec.bufeo.capitan.Modelo.MConfiguracion;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_nombre;
import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_posicion;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP;


public class FragmentInfo extends Fragment {

  CardView cdv_perfil;
  CircleImageView civ_iconoPerfil;
  TextView txt_tituloPerfil,txt_descripcionPerfil;
  RecyclerView rcv_configuracion;
  ArrayList<MConfiguracion>arrayList;
  ProgressBar prog_imagenloading;
  AdaptadorListadoConfiguracion adaptadorListadoConfiguracion;
  SharedPreferences preferencesUser;


  Preferences pref;
  public  void getPerfilEdit(){
      Intent intent = new Intent(getContext(),PerfilEdit.class);

      startActivity(intent);
  }


    public FragmentInfo() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        pref =  new Preferences(getContext());


        civ_iconoPerfil = view.findViewById(R.id.civ_iconoPerfil);
        txt_tituloPerfil = view.findViewById(R.id.txt_tituloPerfil);
        txt_descripcionPerfil = view.findViewById(R.id.txt_descripcionPerfil);
        rcv_configuracion = view.findViewById(R.id.rcv_configuracion);
        cdv_perfil = view.findViewById(R.id.cdv_perfil);
        prog_imagenloading = view.findViewById(R.id.prog_imagenloading);



        txt_tituloPerfil.setText(usuario_nombre);
        txt_descripcionPerfil.setText(usuario_posicion);

        cdv_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPerfilEdit();
            }
        });

        Picasso.with(getContext()).load(IP+"/"+pref.getFotoUsuario()).error(R.drawable.error).fit().into(civ_iconoPerfil,new Callback() {

            @Override
            public void onSuccess() {
                prog_imagenloading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                prog_imagenloading.setVisibility(View.GONE);
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add(new MConfiguracion("Cuenta","nknfkfdndkndffnfd",R.drawable.alarm_check));
        arrayList.add(new MConfiguracion("Ayuda","jsbjsbdjbjdsbdsjdsds",R.drawable.brain));
        arrayList.add(new MConfiguracion("Cuenta","nknfkfdndkndffnfd",R.drawable.alarm_check));
        arrayList.add(new MConfiguracion("Ayuda","jsbjsbdjbjdsbdsjdsds",R.drawable.brain));
        arrayList.add(new MConfiguracion("Cuenta","nknfkfdndkndffnfd",R.drawable.alarm_check));
        arrayList.add(new MConfiguracion("Ayuda","jsbjsbdjbjdsbdsjdsds",R.drawable.brain));
        arrayList.add(new MConfiguracion("Cuenta","nknfkfdndkndffnfd",R.drawable.alarm_check));
        arrayList.add(new MConfiguracion("Ayuda","jsbjsbdjbjdsbdsjdsds",R.drawable.brain));
        arrayList.add(new MConfiguracion("Cuenta","nknfkfdndkndffnfd",R.drawable.alarm_check));
        arrayList.add(new MConfiguracion("Cerrar Sesion","Salir de la Cuenta",R.drawable.brain));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_configuracion.setLayoutManager(linearLayoutManager);

        adaptadorListadoConfiguracion = new AdaptadorListadoConfiguracion(getActivity(), arrayList, R.layout.rcv_item_list_configuracion, new AdaptadorListadoConfiguracion.OnItemClickListener() {
            @Override
            public void onItemClick(MConfiguracion mConfiguracion, final int position) {
                switch (mConfiguracion.getTitulo()) {
                    case "Cerrar Sesion":
                        final Dialog dialogr = new Dialog(getActivity());
                        dialogr.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogr.setContentView(R.layout.dialogo_mensaje);

                        Button btn_cancela = dialogr.findViewById(R.id.btn_cancelar);
                        Button btn_acepta =  dialogr.findViewById(R.id.btn_aceptar);
                        TextView txtMensaje = dialogr.findViewById(R.id.txtMensaje);
                        txtMensaje.setText("¿Desea cerrar Sesión?");

                        btn_cancela.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogr.dismiss();
                            }
                        });

                        btn_acepta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Eliminamos los datos de la SharedPreferences
                                preferencesUser.edit().clear().apply();
                                dialogr.dismiss();

                                Intent intent = new Intent(getActivity(),Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        });
                        dialogr.show();
                        break;
                }


            }
        });
        rcv_configuracion.setAdapter(adaptadorListadoConfiguracion);
        preferencesUser = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);

        return view;
    }


}
