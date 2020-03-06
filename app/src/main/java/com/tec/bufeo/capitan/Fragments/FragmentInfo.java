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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository.EequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.ViewModels.EequiposViewModel;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository.TequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.ViewModels.TequiposViewModel;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones.PublicacionesTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels.PublicacionesTorneoViewModel;
import com.tec.bufeo.capitan.Activity.Login;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Repository.MovimientosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisMovimientos.ViewModels.MovimientosViewModel;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Views.MisMovimientos;
import com.tec.bufeo.capitan.Activity.MisReservas.Views.MisReservasActivity;
import com.tec.bufeo.capitan.Activity.RealizarRecarga;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository.RegistroEquiposTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.ViewModels.RegistroEquiposTorneoViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores.JugadoresRoomDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados.SeleccionadosDBRepository;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Jugadores.JugadoresViewModel;
import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.ViewModel.Seleccionados.SeleccionadosViewModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository.GruposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.ViewModels.GruposListViewModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository.InstanciasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.ViewModels.InstanciasViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository.CommentsRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.ViewModels.CommentsListViewModel;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository.MensajesRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.ViewModels.MensajesViewModel;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository.ChatsRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.ViewModels.ChatsListViewModel;
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
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.GlideCache.IntegerVersionSignature;
import com.tec.bufeo.capitan.Util.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.tec.bufeo.capitan.Util.GlideCache.IntegerVersionSignature.GlideOptions.LOGO_OPTION;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentInfo extends Fragment implements View.OnClickListener {

  CardView cdv_perfil;
  CircleImageView civ_iconoPerfil;
  TextView txt_tituloPerfil;
  SharedPreferences preferencesUser;

  LinearLayout misMovimientos,logout,realizarRecarga,bufis,mensajes,misReservas;


  EequiposViewModel eequiposViewModel;
  TequiposViewModel tequiposViewModel;
  PublicacionesTorneoViewModel feedTorneoListViewModel;
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
  MovimientosViewModel movimientosViewModel;



  Preferences pref;



    public FragmentInfo() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        preferencesUser = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        pref =  new Preferences(getContext());

        otrosTorneosViewModel = ViewModelProviders.of(getActivity()).get(OtrosTorneosViewModel.class);
        misTorneoViewModel = ViewModelProviders.of(getActivity()).get(MisTorneoViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(getActivity()).get(MisEquiposViewModel.class);
        chatsListViewModel = ViewModelProviders.of(getActivity()).get(ChatsListViewModel.class);
        mensajesViewModel = ViewModelProviders.of(getActivity()).get(MensajesViewModel.class);
        feedListViewModel = ViewModelProviders.of(getActivity()).get(FeedListViewModel.class);
        commentsListViewModel = ViewModelProviders.of(getActivity()).get(CommentsListViewModel.class);
        eequiposViewModel = ViewModelProviders.of(getActivity()).get(EequiposViewModel.class);
        tequiposViewModel = ViewModelProviders.of(getActivity()).get(TequiposViewModel.class);
        feedTorneoListViewModel = ViewModelProviders.of(getActivity()).get(PublicacionesTorneoViewModel.class);
        registroEquiposTorneoViewModel = ViewModelProviders.of(getActivity()).get(RegistroEquiposTorneoViewModel.class);
        jugadoresViewModel = ViewModelProviders.of(getActivity()).get(JugadoresViewModel.class);
        seleccionadosViewModel = ViewModelProviders.of(getActivity()).get(SeleccionadosViewModel.class);
        gruposListViewModel = ViewModelProviders.of(getActivity()).get(GruposListViewModel.class);
        instanciasViewModel = ViewModelProviders.of(getActivity()).get(InstanciasViewModel.class);
        estadisticasViewModel = ViewModelProviders.of(getActivity()).get(EstadisticasViewModel.class);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);
        movimientosViewModel = ViewModelProviders.of(getActivity()).get(MovimientosViewModel.class);






        civ_iconoPerfil = view.findViewById(R.id.civ_iconoPerfil);
        txt_tituloPerfil = view.findViewById(R.id.txt_tituloPerfil);
        cdv_perfil = view.findViewById(R.id.cdv_perfil);
        misMovimientos = view.findViewById(R.id.misMovimientos);
        mensajes = view.findViewById(R.id.mensajes);
        realizarRecarga = view.findViewById(R.id.realizarRecarga);
        misReservas = view.findViewById(R.id.misReservas);
        logout = view.findViewById(R.id.logout);
        bufis = view.findViewById(R.id.bufis);



        txt_tituloPerfil.setText(pref.getPersonName() + " " + pref.getPersonSurname());


        cdv_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Glide.with(getContext())
                .load(IP2+"/"+ pref.getFotoUsuario())
                .signature(new IntegerVersionSignature(pref.getCantidadFotoPerfil()))
                .apply(LOGO_OPTION)
                .into(civ_iconoPerfil);




        misMovimientos.setOnClickListener(this);
        realizarRecarga.setOnClickListener(this);
        logout.setOnClickListener(this);
        bufis.setOnClickListener(this);
        mensajes.setOnClickListener(this);
        misReservas.setOnClickListener(this);
        return view;
    }

    Application application;

    Dialog dialog_logout;
    public void dialogLogout(){

        dialog_logout= new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_logout.setCancelable(true);
        dialog_logout.setContentView(R.layout.dialogo_mensaje);

        LinearLayout btn_cancela = dialog_logout.findViewById(R.id.btn_cancelar);
        LinearLayout btn_acepta =  dialog_logout.findViewById(R.id.btn_aceptar);
        TextView txtMensaje = dialog_logout.findViewById(R.id.txtMensaje);
        txtMensaje.setText("¿Desea cerrar Sesión?");

        btn_cancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_logout.dismiss();
            }
        });

        btn_acepta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Eliminamos los datos de la SharedPreferences
                preferencesUser.edit().clear().apply();
                dialog_logout.dismiss();
                EliminarDBs();
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();

                Intent intent = new Intent(getActivity(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        dialog_logout.show();

    }
  public void logout(){
      dialogLogout();
  }
    private void EliminarDBs() {

        EequiposRoomDbRepository eequiposRoomDbRepository =  new EequiposRoomDbRepository(application);
        eequiposRoomDbRepository.DeleteAllEstadisticasEquipos();

        TequiposRoomDbRepository tequiposRoomDbRepository = new TequiposRoomDbRepository(application);
        tequiposRoomDbRepository.deleteAllTorneosEquipos();

        PublicacionesTorneoRoomDBRepository feedTorneoRoomDBRepository = new PublicacionesTorneoRoomDBRepository(application);
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

        MovimientosRoomDBRepository movimientosRoomDBRepository = new MovimientosRoomDBRepository(application);
        movimientosRoomDBRepository.deleteAllEquipos();
    }


    @Override
    public void onClick(View v) {
        if (v.equals(misMovimientos)){

            Intent i = new Intent(getContext(), MisMovimientos.class);
            startActivity(i);
        }else if (v.equals(logout)){
            logout();
        }else if (v.equals(realizarRecarga)){
            Intent i = new Intent(getContext(), RealizarRecarga.class);
            startActivity(i);
        }else if (v.equals(misReservas)){
            Intent i = new Intent(getContext(), MisReservasActivity.class);
            startActivity(i);
        }
    }
}
