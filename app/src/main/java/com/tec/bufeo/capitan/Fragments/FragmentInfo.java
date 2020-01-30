package com.tec.bufeo.capitan.Fragments;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
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
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository.EequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.ViewModels.EequiposViewModel;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository.TequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.ViewModels.TequiposViewModel;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.FeedTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.FeedTorneoListViewModel;
import com.tec.bufeo.capitan.Activity.Login;
import com.tec.bufeo.capitan.Activity.PerfilEdit;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository.RegistroEquiposTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.ViewModels.RegistroEquiposTorneoViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores.JugadoresRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados.SeleccionadosDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Jugadores.JugadoresViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Seleccionados.SeleccionadosViewModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository.GruposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.ViewModels.GruposListViewModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.LIstaInstancias.Instancias;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository.InstanciasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.ViewModels.InstanciasViewModel;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoConfiguracion;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository.CommentsRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.ViewModels.CommentsListViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models.Mensajes;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository.MensajesRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.ViewModels.MensajesViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository.ChatsRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.ViewModels.ChatsListViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository.EstadisticasRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.ViewModels.EstadisticasViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository.RetosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository.MisTorneoRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.ViewModels.MisTorneoViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.ViewModels.OtrosTorneosViewModel;
import com.tec.bufeo.capitan.Modelo.MConfiguracion;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_nombre;
import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_posicion;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentInfo extends Fragment {

  CardView cdv_perfil;
  CircleImageView civ_iconoPerfil;
  TextView txt_tituloPerfil,txt_descripcionPerfil;
  RecyclerView rcv_configuracion;
  ArrayList<MConfiguracion>arrayList;
  ProgressBar prog_imagenloading;
  AdaptadorListadoConfiguracion adaptadorListadoConfiguracion;
  SharedPreferences preferencesUser;


  EequiposViewModel eequiposViewModel;
  TequiposViewModel tequiposViewModel;
  FeedTorneoListViewModel feedTorneoListViewModel;
  RegistroEquiposTorneoViewModel registroEquiposTorneoViewModel;
  JugadoresViewModel jugadoresViewModel;
  SeleccionadosViewModel seleccionadosViewModel;
  GruposListViewModel gruposListViewModel;
  InstanciasViewModel instanciasViewModel;
  CommentsListViewModel commentsListViewModel;
  FeedListViewModel feedListViewModel;
  MensajesViewModel mensajesViewModel;
  ChatsListViewModel chatsListViewModel;
  EstadisticasViewModel estadisticasViewModel;
  MisEquiposViewModel misEquiposViewModel;
  RetosViewModel retosViewModel;
  MisTorneoViewModel misTorneoViewModel;
  OtrosTorneosViewModel otrosTorneosViewModel;



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

        otrosTorneosViewModel = ViewModelProviders.of(getActivity()).get(OtrosTorneosViewModel.class);
        misTorneoViewModel = ViewModelProviders.of(getActivity()).get(MisTorneoViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(getActivity()).get(MisEquiposViewModel.class);
        chatsListViewModel = ViewModelProviders.of(getActivity()).get(ChatsListViewModel.class);
        mensajesViewModel = ViewModelProviders.of(getActivity()).get(MensajesViewModel.class);
        feedListViewModel = ViewModelProviders.of(getActivity()).get(FeedListViewModel.class);
        commentsListViewModel = ViewModelProviders.of(getActivity()).get(CommentsListViewModel.class);
        eequiposViewModel = ViewModelProviders.of(getActivity()).get(EequiposViewModel.class);
        tequiposViewModel = ViewModelProviders.of(getActivity()).get(TequiposViewModel.class);
        feedTorneoListViewModel = ViewModelProviders.of(getActivity()).get(FeedTorneoListViewModel.class);
        registroEquiposTorneoViewModel = ViewModelProviders.of(getActivity()).get(RegistroEquiposTorneoViewModel.class);
        jugadoresViewModel = ViewModelProviders.of(getActivity()).get(JugadoresViewModel.class);
        seleccionadosViewModel = ViewModelProviders.of(getActivity()).get(SeleccionadosViewModel.class);
        gruposListViewModel = ViewModelProviders.of(getActivity()).get(GruposListViewModel.class);
        instanciasViewModel = ViewModelProviders.of(getActivity()).get(InstanciasViewModel.class);
        estadisticasViewModel = ViewModelProviders.of(getActivity()).get(EstadisticasViewModel.class);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);



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

        Picasso.with(getContext()).load(IP2+"/"+pref.getFotoUsuario()).error(R.drawable.error).fit().into(civ_iconoPerfil,new Callback() {

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
                                EliminarDBs();

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

    Application application;
    private void EliminarDBs() {

        EequiposRoomDbRepository eequiposRoomDbRepository =  new EequiposRoomDbRepository(application);
        eequiposRoomDbRepository.deleteAllTorneosEquipos();

        TequiposRoomDbRepository tequiposRoomDbRepository = new TequiposRoomDbRepository(application);
        tequiposRoomDbRepository.deleteAllTorneosEquipos();

        FeedTorneoRoomDBRepository feedTorneoRoomDBRepository = new FeedTorneoRoomDBRepository(application);
        feedTorneoRoomDBRepository.deleteAllFeed();

        RegistroEquiposTorneoRoomDBRepository registroEquiposTorneoRoomDBRepository = new RegistroEquiposTorneoRoomDBRepository(application);
        registroEquiposTorneoRoomDBRepository.deleteAllEquipos();

        JugadoresRoomDBRepository jugadoresRoomDBRepository =  new JugadoresRoomDBRepository(application);
        jugadoresRoomDBRepository.deleteAllJugadores();

        SeleccionadosDBRepository seleccionadosDBRepository = new SeleccionadosDBRepository(application);
        seleccionadosDBRepository.deleteAllSeleccionados();

        GruposRoomDBRepository gruposRoomDBRepository = new GruposRoomDBRepository(application);
        gruposRoomDBRepository.deleteAllGrupos();

        InstanciasRoomDBRepository instanciasRoomDBRepository = new InstanciasRoomDBRepository(application);
        instanciasRoomDBRepository.deleteAllInstancias();

        CommentsRoomDBRepository commentsRoomDBRepository = new CommentsRoomDBRepository(application);
        commentsRoomDBRepository.deleteAllComments();

        FeedRoomDBRepository feedRoomDBRepository = new FeedRoomDBRepository(application);
        feedRoomDBRepository.deleteAllFeed();

        MensajesRoomDBRepository mensajesRoomDBRepository = new MensajesRoomDBRepository(application);
        mensajesRoomDBRepository.deleteAllMensajes();

        ChatsRoomDBRepository chatsRoomDBRepository = new ChatsRoomDBRepository(application);
        chatsRoomDBRepository.deleteAllEquipos();

        EstadisticasRoomDBRepository estadisticasRoomDBRepository = new EstadisticasRoomDBRepository(application);
        estadisticasRoomDBRepository.deleteAllEstadisticas();

        MisEquiposRoomDBRepository misEquiposRoomDBRepository = new MisEquiposRoomDBRepository(application);
        misEquiposRoomDBRepository.deleteAllEquipos();

        RetosRoomDBRepository retosRoomDBRepository = new RetosRoomDBRepository(application);
        retosRoomDBRepository.deleteAllRetos();

        MisTorneoRoomDBRepository misTorneoRoomDBRepository =  new MisTorneoRoomDBRepository(application);
        misTorneoRoomDBRepository.deleteAllRetos();

        OtrosTorneosRoomDBRepository otrosTorneosRoomDBRepository = new OtrosTorneosRoomDBRepository(application);
        otrosTorneosRoomDBRepository.deleteAllRetos();
    }


}
