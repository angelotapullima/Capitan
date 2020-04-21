package com.tec.bufeo.capitan.TabsPrincipales;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.ChanchasEquipos;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository.EequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.ViewModels.EequiposViewModel;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository.TequiposRoomDbRepository;
import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.ViewModels.TequiposViewModel;
import com.tec.bufeo.capitan.Activity.Login;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Repository.MovimientosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.MisMovimientos.ViewModels.MovimientosViewModel;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Views.MisMovimientos;
import com.tec.bufeo.capitan.Activity.MisReservas.Views.MisReservasActivity;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.Repository.Negocios.NegociosRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Negocios.ViewModels.NegociosViewModel;
import com.tec.bufeo.capitan.Activity.ProfileActivity;
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
import com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Repository.NotificacionesRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.ViewModels.NotificacionesViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Repository.CommentsRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.ViewModels.CommentsListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.ViewModels.FeedListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Repository.MensajesRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.ViewModels.MensajesViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository.ChatsRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.ViewModels.ChatsListViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Repository.EstadisticasRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.ViewModels.EstadisticasViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository.MisEquiposRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.ViewModels.MisEquiposViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Repository.RetosRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.ViewModels.RetosViewModel;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository.TorneosRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.ViewModels.TorneosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentInfo extends Fragment implements View.OnClickListener {


  CircleImageView civ_iconoPerfil;
  TextView txt_tituloPerfil,email;
  SharedPreferences preferencesUser;
  UniversalImageLoader universalImageLoader;
  LinearLayout misMovimientos,logout,realizarRecarga,bufis,mensajes,detalleMisReservas,Chanchex;


  EequiposViewModel eequiposViewModel;
  TequiposViewModel tequiposViewModel;
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
  TorneosViewModel torneosViewModel;
  MovimientosViewModel movimientosViewModel;
  NegociosViewModel negociosViewModel;
  NotificacionesViewModel notificacionesViewModel;



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
        universalImageLoader= new UniversalImageLoader(getContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        torneosViewModel = ViewModelProviders.of(getActivity()).get(TorneosViewModel.class);
        misEquiposViewModel = ViewModelProviders.of(getActivity()).get(MisEquiposViewModel.class);
        chatsListViewModel = ViewModelProviders.of(getActivity()).get(ChatsListViewModel.class);
        mensajesViewModel = ViewModelProviders.of(getActivity()).get(MensajesViewModel.class);
        feedListViewModel = ViewModelProviders.of(getActivity()).get(FeedListViewModel.class);
        commentsListViewModel = ViewModelProviders.of(getActivity()).get(CommentsListViewModel.class);
        eequiposViewModel = ViewModelProviders.of(getActivity()).get(EequiposViewModel.class);
        tequiposViewModel = ViewModelProviders.of(getActivity()).get(TequiposViewModel.class);
        registroEquiposTorneoViewModel = ViewModelProviders.of(getActivity()).get(RegistroEquiposTorneoViewModel.class);
        jugadoresViewModel = ViewModelProviders.of(getActivity()).get(JugadoresViewModel.class);
        seleccionadosViewModel = ViewModelProviders.of(getActivity()).get(SeleccionadosViewModel.class);
        gruposListViewModel = ViewModelProviders.of(getActivity()).get(GruposListViewModel.class);
        instanciasViewModel = ViewModelProviders.of(getActivity()).get(InstanciasViewModel.class);
        estadisticasViewModel = ViewModelProviders.of(getActivity()).get(EstadisticasViewModel.class);
        retosViewModel = ViewModelProviders.of(getActivity()).get(RetosViewModel.class);
        movimientosViewModel = ViewModelProviders.of(getActivity()).get(MovimientosViewModel.class);
        negociosViewModel = ViewModelProviders.of(getActivity()).get(NegociosViewModel.class);
        notificacionesViewModel = ViewModelProviders.of(getActivity()).get(NotificacionesViewModel.class);


        civ_iconoPerfil = view.findViewById(R.id.civ_iconoPerfil);
        txt_tituloPerfil = view.findViewById(R.id.txt_tituloPerfil);
        misMovimientos = view.findViewById(R.id.misMovimientos);
        mensajes = view.findViewById(R.id.mensajes);
        email = view.findViewById(R.id.email);
        Chanchex = view.findViewById(R.id.Chanchex);
        realizarRecarga = view.findViewById(R.id.realizarRecarga);
        detalleMisReservas = view.findViewById(R.id.detalleMisReservas);
        logout = view.findViewById(R.id.logout);
        bufis = view.findViewById(R.id.bufis);




        txt_tituloPerfil.setText(pref.getPersonName() + " " + pref.getPersonSurname());
        email.setText(pref.getEmailJuegador());




        UniversalImageLoader.setImage(IP2+"/"+ pref.getFotoUsuario(),civ_iconoPerfil,null);





        bufis.setOnClickListener(this);
        misMovimientos.setOnClickListener(this);
        detalleMisReservas.setOnClickListener(this);
        realizarRecarga.setOnClickListener(this);
        mensajes.setOnClickListener(this);
        logout.setOnClickListener(this);

        Chanchex.setOnClickListener(this);

        showToolbar("Cuenta",false,view);
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

                Intent intent = new Intent(getContext(),Login.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        dialog_logout.show();

    }
    public void logout(){
      dialogLogout();
  }
    private void EliminarDBs() {


        NotificacionesRoomDBRepository notificacionesRoomDBRepository = new NotificacionesRoomDBRepository(application);
        notificacionesRoomDBRepository.deleteAllMisReservas();


        EequiposRoomDbRepository eequiposRoomDbRepository =  new EequiposRoomDbRepository(application);
        eequiposRoomDbRepository.DeleteAllEstadisticasEquipos();

        TequiposRoomDbRepository tequiposRoomDbRepository = new TequiposRoomDbRepository(application);
        tequiposRoomDbRepository.deleteAllTorneosEquipos();

        NegociosRoomDBRepository negociosRoomDBRepository = new NegociosRoomDBRepository(application);
        negociosRoomDBRepository.deleteAllMisNegocios();

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

        TorneosRoomDBRepository torneosRoomDBRepository =  new TorneosRoomDBRepository(application);
        torneosRoomDBRepository.DeleteAllTorneosAsyncTask();



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
        }else if (v.equals(detalleMisReservas)){
            Intent i = new Intent(getContext(), MisReservasActivity.class);
            startActivity(i);
        }
        else if (v.equals(mensajes)){
            Intent i = new Intent(getContext(), MenuPrincipal.class);
            i.putExtra("inicio","mensajes");
            pref.saveValuePORT("inicio","mensajes");
            startActivity(i);
        }else if (v.equals(Chanchex)){
            Intent i = new Intent(getContext(), ChanchasEquipos.class);
            startActivity(i);
        }
    }

    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
