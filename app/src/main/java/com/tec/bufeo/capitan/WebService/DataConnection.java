package com.tec.bufeo.capitan.WebService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tec.bufeo.capitan.Activity.DetalleNegocio;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Fragments.tabsBuscar.FragmentBuscar;
import com.tec.bufeo.capitan.Fragments.FragmentHoy;
import com.tec.bufeo.capitan.Fragments.FragmentMañana;
import com.tec.bufeo.capitan.Fragments.FragmentNegocio;
import com.tec.bufeo.capitan.Fragments.FragmentPasMañana;
import com.tec.bufeo.capitan.Fragments.tabsBuscar.FragmentBuscarFechas;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.others.Equipo;
import com.tec.bufeo.capitan.Modelo.HoraFecha;
import com.tec.bufeo.capitan.Modelo.MDistrito;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.Modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.tec.bufeo.capitan.Activity.DetalleCanchas.tabLayout;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.actualizarDetalle;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.btn_cancelarV;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.btn_editVal;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.btn_enviarV;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.rtb_valorar;

public class DataConnection extends AppCompatActivity {

    public String funcion, texto, jsondatos, idciudad ,idtorneo, idempresa, fecha, torneo_id;
    public String parametros, respuesta, cargarDatos,hora;
    boolean mensajeprogres, mensaje;
    public static final String IP = "https://www.guabba.com/capitan";
    public static final String IP2 = "https://www.guabba.com/capitan2";
    static Activity context;
    JSONObject json_data;
    Usuario usuario;
    Empresas empresas;
    Cancha cancha;
    Reserva reserva;
    Equipo equipo;
    Retos retos;
    Torneo torneo;
    SharedPreferences preferencesUser;


    public ArrayList<Empresas> listaEmpresa = new ArrayList();
    public ArrayList<Empresas> listaCachasHoraEmpresa = new ArrayList();
    public ArrayList<FragmentBuscar.GroupItem> listaCanchasDisponiblesOriginal = new ArrayList();
    public ArrayList<FragmentBuscarFechas.GroupItemBusqueda> listaCanchasDisponiblesBusqueda= new ArrayList();
    public ArrayList<Cancha> listaCanchaEmpresa = new ArrayList();
    public ArrayList<Reserva> listaCanchaReserva= new ArrayList();
    public ArrayList<Equipo> listaEquipos = new ArrayList();
    public ArrayList<Equipo> listaEquiposGenaral = new ArrayList();
    public ArrayList<Usuario> listaUsuarioGeneral = new ArrayList();
    public ArrayList<HoraFecha> listaHoraFecha = new ArrayList();
    public ArrayList<Usuario> listaUsuarioPorEquipo = new ArrayList();
    public ArrayList<Reserva> listaEstadisticasDiarias = new ArrayList();
    public ArrayList<Reserva> listaEstadisticasGeneral = new ArrayList();
    public ArrayList<Equipo> listaEquiposEnTorneo = new ArrayList();
    public ArrayList<Equipo> listaEquiposEnTorneoNot = new ArrayList();
    public ArrayList<String> listaCiudades = new ArrayList();
    public ArrayList<MDistrito> listaDistritoCiudades = new ArrayList();
    public ArrayList<Empresas> listaEmpresasDistrito= new ArrayList();


    public DataConnection() {

    }

    //Listar Canchas Disponibles
    public DataConnection(Activity context, String funcion, boolean mensajeprogres,String hora) {
        this.context = context;
        this.funcion = funcion;
        this.hora = hora;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    public DataConnection(Activity context, String funcion, boolean mensajeprogres) {
        this.context = context;
        this.funcion = funcion;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }



    //Asignamos valores a traves del constructor (Multifuncion Usuario-Listados tablas)
    public DataConnection(Activity context, String funcion, Usuario usuario, boolean mensajeprogres) {
        this.context = context;
        this.funcion = funcion;
        this.usuario = usuario;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }
    //Registro de Empresas
    public DataConnection(Activity context, String funcion, Empresas empresas, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.empresas = empresas;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    //Listado de Empresas - Listado Distrito
    public DataConnection(Activity context, String funcion, String idciudad, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.idciudad = idciudad;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }
    //Registro de Canchas- Listar
    public DataConnection(Activity context, String funcion, Cancha cancha, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.cancha = cancha;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    //Listado de Canchas Reservas
    public DataConnection(Activity context, String funcion, Reserva reserva, String fecha, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.reserva = reserva;
        this.fecha = fecha;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }
    //Insertar Equipos a un torneo
    public DataConnection(Activity context, String funcion, Equipo equipo, String torneo_id, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.equipo= equipo;
        this.torneo_id = torneo_id;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }
    public DataConnection(Activity context, String funcion, Reserva reserva, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.reserva = reserva;
        this.mensajeprogres = mensajeprogres;
        new GetAndSet().execute();
    }

    //º de Empresas
    public DataConnection(Activity context, String funcion, Equipo equipo, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.equipo = equipo;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }

    //De Retos-Registar-Listar
    public DataConnection(Activity context, String funcion, Retos retos, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.retos = retos;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }
    //De Torneo-Registar-Listar
    public DataConnection(Activity context, String funcion, Torneo torneo, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.torneo = torneo;
        this.mensajeprogres = mensajeprogres;

        new GetAndSet().execute();
    }




    //Enviamos los datos al webservice y devuelve un valor string-respuesta
    private  String obtenerDatos(){
        StringBuffer response = null;
        try {

            URL url = new URL(IP+"/index.php?c=Cliente&a=registrar");


            if(funcion.equals("loginUsuario")){
                parametros = "user=" + URLEncoder.encode(usuario.getUser_nickname(),"UTF-8")
                        + "&pass=" + URLEncoder.encode(usuario.getUser_password(),"UTF-8")
                + "&app=" + URLEncoder.encode("true","UTF-8");

                //url = new URL(IP+"/index.php?c=Usuario&a=loguearse&key_mobile=123456asdfgh");
                url = new URL(IP2+"/api/Login/validar_usuario");

            }if(funcion.equals("registrarse")){
                parametros = "nombre=" + URLEncoder.encode(usuario.getUsuario_nombre(),"UTF-8")
                        + "&nacimiento=" + URLEncoder.encode(usuario.getUsuario_nacimiento(),"UTF-8")
                        + "&foto=" + URLEncoder.encode(usuario.getUsuario_foto(),"UTF-8")
                        + "&sexo=" + URLEncoder.encode(usuario.getUsuario_sexo(),"UTF-8")
                        + "&usuario=" + URLEncoder.encode(usuario.getUsuario_usuario(),"UTF-8")
                        + "&clave=" + URLEncoder.encode(usuario.getUsuario_clave(),"UTF-8")
                        + "&email=" + URLEncoder.encode(usuario.getUsuario_email(),"UTF-8")
                        + "&habilidad=" + URLEncoder.encode(usuario.getUsuario_habilidad(),"UTF-8")
                        + "&posicion=" + URLEncoder.encode(usuario.getUsuario_posicion(),"UTF-8")
                        + "&num=" + URLEncoder.encode(usuario.getUsuario_numFavorito(),"UTF-8")
                        + "&dni=" + URLEncoder.encode(usuario.getUsuario_dni(),"UTF-8")
                        + "&telefono=" + URLEncoder.encode(usuario.getUsuario_telefono(),"UTF-8")
                        + "&rol_id=" + URLEncoder.encode(usuario.getRol_id(),"UTF-8")
                        + "&ubigeo_id=" + URLEncoder.encode(usuario.getUbigeo_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Usuario&a=registrar&key_mobile=123456asdfgh");


            }if(funcion.equals("registrarEmpresas")){
                parametros = "usuario_id=" + URLEncoder.encode(empresas.getUsuario_id(),"UTF-8")
                        + "&ubigeo_id=" + URLEncoder.encode(empresas.getUbigeo_id(),"UTF-8")
                        + "&nombre=" + URLEncoder.encode(empresas.getEmpresas_nombre(),"UTF-8")
                        + "&direccion=" + URLEncoder.encode(empresas.getEmpresas_direccion(),"UTF-8")
                        + "&telefono=" + URLEncoder.encode(empresas.getEmpresas_telefono(),"UTF-8")
                        + "&horario=" + URLEncoder.encode(empresas.getEmpresas_horario(),"UTF-8")
                        + "&descripcion=" + URLEncoder.encode(empresas.getEmpresas_descripcion(),"UTF-8")
                        + "&foto=" + URLEncoder.encode(empresas.getEmpresas_foto(),"UTF-8");

                url = new URL(IP+"/index.php?c=Empresa&a=registrar&key_mobile=123456asdfgh");

            }
            if(funcion.equals("valorarEmpresa")){
                parametros = "id_usuario=" + URLEncoder.encode(empresas.getUsuario_id(),"UTF-8")
                        + "&id_empresa=" + URLEncoder.encode(empresas.getEmpresas_id(),"UTF-8")
                        + "&valor=" + URLEncoder.encode(empresas.getEmpresas_valoracion(),"UTF-8");

                url = new URL(IP+"/index.php?c=Empresa&a=valorar_empresa&key_mobile=123456asdfgh");

            }
            if(funcion.equals("listarUsuarioGeneral")){
                parametros = "id_equipo=" + URLEncoder.encode(usuario.getEquipo_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=buscar_jugadores_nuevos&key_mobile=123456asdfgh");
            }
            if(funcion.equals("listarEstadisticasDiarias")){
                parametros = "fecha=" + URLEncoder.encode(reserva.getFecha_reporte(),"UTF-8")
                + "&id_empresa=" + URLEncoder.encode(reserva.getEmpresa_id(),"UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=estadisticas_por_empresa&key_mobile=123456asdfgh");
            }
            if(funcion.equals("listarEstadisticasGeneral")){
                parametros = "fecha_i=" + URLEncoder.encode(reserva.getFecha_i_reporte(),"UTF-8")
                        + "&fecha_f=" + URLEncoder.encode(reserva.getFecha_f_reporte(),"UTF-8")
                        + "&id_empresa=" + URLEncoder.encode(reserva.getEmpresa_id(),"UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=estadisticas_por_empresa&key_mobile=123456asdfgh");
            }

            if(funcion.equals("listarCanchasDisponibles")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=listar_canchas_libres_por_hora&key_mobile=123456asdfgh");
            }

            if(funcion.equals("listarForo")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");
                url = new URL(IP+"/index.php?c=Foro&a=listar_publicaciones&key_mobile=123456asdfgh");
            }
            if(funcion.equals("listarEquiposEnTorneo")){
                parametros = "id_torneo=" + URLEncoder.encode(idciudad,"UTF-8");
                url = new URL(IP+"/index.php?c=Torneo&a=listar_equipos_en_torneo&key_mobile=123456asdfgh");
            }
            if(funcion.equals("listarEquipo")){
                parametros = "id_usuario=" + URLEncoder.encode(equipo.getUsuario_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=listar_equipos_por_id_usuario&key_mobile=123456asdfgh");
            }
            if(funcion.equals("listarEquiposEnTorneoNot")){
                parametros = "id_torneo=" + URLEncoder.encode(idciudad,"UTF-8");
                url = new URL(IP+"/index.php?c=Torneo&a=listar_equipos_por_torneo_not&key_mobile=123456asdfgh");
            }

            if(funcion.equals("obtenerHoraFecha")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");

                url = new URL(IP+"/index.php?c=Usuario&a=fecha_hora_actual&key_mobile=123456asdfgh");
            }

            if(funcion.equals("listarUsuarioPorEquipo")){
                parametros = "id_equipo=" + URLEncoder.encode(usuario.getEquipo_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=listar_detalle_equipo&key_mobile=123456asdfgh");
            }


            if(funcion.equals("registrarUsuarioEquipo")){
                parametros = "id_equipo=" + URLEncoder.encode(usuario.getEquipo_id(),"UTF-8")
                        + "&id_usuario=" + URLEncoder.encode(usuario.getUsuario_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=registrar_equipo_usuario&key_mobile=123456asdfgh");

            }
            if(funcion.equals("registrarEquipoTorneo")){
                parametros = "id_equipo=" + URLEncoder.encode(equipo.getEquipo_id(),"UTF-8")
                        + "&id_torneo=" + URLEncoder.encode(equipo.getTorneo_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=registrar_equipo_en_torneo&key_mobile=123456asdfgh");

            }
            if(funcion.equals("registrarTorneoPorUssuario")){
                parametros = "usuario_id=" + URLEncoder.encode(torneo.getUsuario_id(),"UTF-8")
                        + "&nombre=" + URLEncoder.encode(torneo.getTorneo_nombre(),"UTF-8")
                        + "&descripcion=" + URLEncoder.encode(torneo.getTorneo_descripcion(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(torneo.getTorneo_fecha(),"UTF-8")
                        + "&hora=" + URLEncoder.encode(torneo.getTorneo_hora(),"UTF-8")
                        + "&lugar=" + URLEncoder.encode(torneo.getTorneo_lugar(),"UTF-8");


                url = new URL(IP+"/index.php?c=Torneo&a=registrar_torneo&key_mobile=123456asdfgh");

            }

            if(funcion.equals("registrarCancha")){
                parametros = "empresa_id=" + URLEncoder.encode(cancha.getEmpresas_id(),"UTF-8")
                        + "&foto=" + URLEncoder.encode(cancha.getCancha_foto(),"UTF-8")
                        + "&nombre=" + URLEncoder.encode(cancha.getCancha_nombre(),"UTF-8")
                        + "&dimensiones=" + URLEncoder.encode(cancha.getCancha_dimenciones(),"UTF-8")
                        + "&precioD=" + URLEncoder.encode(cancha.getCancha_precioD(),"UTF-8")
                        + "&precioN=" + URLEncoder.encode(cancha.getCancha_precioN(),"UTF-8");

                url = new URL(IP+"/index.php?c=Empresa&a=registrar_cancha&key_mobile=123456asdfgh");

            }
            if(funcion.equals("registrarReto")){
                parametros = "retador=" + URLEncoder.encode(retos.getRetador_id(),"UTF-8")
                        + "&retado=" + URLEncoder.encode(retos.getRetado_id(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(retos.getRetos_fecha(),"UTF-8")
                        + "&hora=" + URLEncoder.encode(retos.getRetos_hora(),"UTF-8")
                        + "&lugar=" + URLEncoder.encode(retos.getRetos_lugar(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=retar_equipo&key_mobile=123456asdfgh");

            }
            if(funcion.equals("listarcanchasEmpresas")){
            parametros = "id_empresa=" + URLEncoder.encode(cancha.getEmpresas_id(),"UTF-8");

            url = new URL(IP+"/index.php?c=Empresa&a=listar_canchas_por_id_empresa&key_mobile=123456asdfgh");
           }

            if(funcion.equals("registrarReserva")){
                parametros = "id_cancha=" + URLEncoder.encode(reserva.getCancha_id(),"UTF-8")
                        + "&nombre=" + URLEncoder.encode(reserva.getReserva_nombre(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(reserva.getReserva_fecha(),"UTF-8")
                        + "&hora=" + URLEncoder.encode(reserva.getReserva_hora(),"UTF-8")
                        + "&costo=" + URLEncoder.encode(reserva.getReserva_costo(),"UTF-8");

                url = new URL(IP+"/index.php?c=Empresa&a=registrar_reserva&key_mobile=123456asdfgh");

            }

            if(funcion.equals("listarcanchasReservas")){
                parametros = "id_cancha=" + URLEncoder.encode(reserva.getCancha_id(),"UTF-8")
                + "&fecha=" + URLEncoder.encode(fecha,"UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=listar_reservados_por_cancha_por_fecha&key_mobile=123456asdfgh");
            }

            if(funcion.equals("registrarEquipo")){
                parametros = "usuario_id=" + URLEncoder.encode(equipo.getUsuario_id(),"UTF-8")
                        + "&nombre=" + URLEncoder.encode(equipo.getEquipo_nombre(),"UTF-8");

                url = new URL(IP+"/index.php?c=Torneo&a=registrar_equipo&key_mobile=123456asdfgh");
            }

            if(funcion.equals("listarEquipoGeneral")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");
                url = new URL(IP+"/index.php?c=Torneo&a=listar_equipos&key_mobile=123456asdfgh");
            }


            if(funcion.equals("listarEmpresas")){
                parametros = "id_ciudad=" + URLEncoder.encode(idciudad,"UTF-8");

                url = new URL(IP+"/index.php?c=Empresa&a=listar_empresas_por_id_ciudad&key_mobile=123456asdfgh");


            }if(funcion.equals("mostrarDetalleEmpresa")){
                parametros = "id_empresa=" + URLEncoder.encode(empresas.getEmpresas_id(),"UTF-8")
                             + "&id_usuario=" + URLEncoder.encode(empresas.getUsuario_id(),"UTF-8");

                url = new URL(IP+"/index.php?c=Empresa&a=listar_detalle_empresa&key_mobile=123456asdfgh");

            }if(funcion.equals("listarCiudades")){
                parametros = " " + URLEncoder.encode(" ","UTF-8");

                url = new URL(IP+"/index.php?c=Usuario&a=listar_ciudades&key_mobile=123456asdfgh");
            }if(funcion.equals("listarDistritoxCiudades")){
                parametros = "ciudad=" + URLEncoder.encode(idciudad,"UTF-8");

                url = new URL(IP+"/index.php?c=Usuario&a=listar_distritos_por_ciudad&key_mobile=123456asdfgh");
            }

            if (funcion.equals("listarEmpresasID")){
                parametros = "id_ciudad=" + URLEncoder.encode(idciudad,"UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=listar_empresas_por_id_ciudad&key_mobile=123456asdfgh");

            }if(funcion.equals("listarCanchasDisponiblesBusqueda")){
                parametros = "hora=" + URLEncoder.encode(reserva.getReserva_hora(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(reserva.getReserva_fecha(),"UTF-8")
                        +"&negocio=" + URLEncoder.encode(reserva.getCancha_nombre(),"UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=busqueda_avanzada&key_mobile=123456asdfgh");

            }if(funcion.equals("listarCanchasDisponiblesBusqueda2")){
                parametros = "hora=" + URLEncoder.encode(reserva.getReserva_hora(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(reserva.getReserva_fecha(),"UTF-8")
                        +"&negocio=" + URLEncoder.encode(reserva.getCancha_nombre(),"UTF-8");
                url = new URL(IP+"/index.php?c=Empresa&a=busqueda_avanzada&key_mobile=123456asdfgh");
            }


            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language","en-US,en;q=0.5");
            con.setDoOutput(true);

            con.setFixedLengthStreamingMode(parametros.getBytes().length);

            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(parametros.getBytes());
            out.flush();
            out.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine=in.readLine()) !=null){
                response.append(inputLine);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();

    }
    int code;
    String messaje,valorcodigo;
    JSONObject data_json;

    private  boolean filtrardDatos(){

        cargarDatos = obtenerDatos();
        try {

            if(!(cargarDatos.equalsIgnoreCase("")  )){


                json_data = new JSONObject(cargarDatos);
                //Log.e("Login", "filtrardDatos: "+cargarDatos );

                if(funcion.equals("loginUsuario")){

                    //if(cargarDatos.equalsIgnoreCase("{\"results\":[]}")){

                    JSONObject resultJSON = json_data.getJSONObject("result");
                    code = resultJSON.optInt("code");
                    messaje = resultJSON.optString("message");


                    if (Integer.toString(code).equalsIgnoreCase("1")){
                        respuesta = "true";

                        data_json = json_data.optJSONObject("data");
                        //resultJSON = json_data.getJSONArray("results");



                            /*usuario.setUsuario_id(jsonNode.optString("idusuario"));
                            usuario.setUsuario_usuario(usuario.getUsuario_usuario());
                            usuario.setUsuario_nombre(jsonNode.optString("usuario_nombre"));
                            usuario.setUsuario_habilidad(jsonNode.optString("habilidad"));
                            usuario.setUsuario_posicion(jsonNode.optString("posicion"));
                            usuario.setUsuario_numFavorito(jsonNode.optString("usuario_numFavorito"));
                            usuario.setUsuario_email(jsonNode.optString("email"));
                            usuario.setUsuario_foto(jsonNode.optString("usuario_foto"));
                            usuario.setUbigeo_id(jsonNode.optString("ubigeo_id"));
                            usuario.setToken_firebase(jsonNode.optString("token_firebase"));*/

                            usuario.setId_user(data_json.optString("id_user"));
                            usuario.setId_person(data_json.optString("id_person"));
                            usuario.setUser_nickname(data_json.optString("user_nickname"));
                            usuario.setUser_email(data_json.optString("user_email"));
                            usuario.setUser_image(data_json.optString("user_image"));
                            usuario.setPerson_name(data_json.optString("person_name"));
                            usuario.setPerson_surname(data_json.optString("person_surname"));
                            usuario.setPerson_dni(data_json.optString("person_dni"));
                            usuario.setPerson_birth(data_json.optString("person_birth"));
                            usuario.setPerson_number_phone(data_json.optString("person_number_phone"));
                            usuario.setPerson_genre(data_json.optString("person_genre"));
                            usuario.setPerson_address(data_json.optString("person_address"));
                            usuario.setUser_num(data_json.optString("user_num"));
                            usuario.setUser_posicion(data_json.optString("user_posicion"));
                            usuario.setUser_habilidad(data_json.optString("user_habilidad"));
                            usuario.setUbigeo_id(data_json.optString("ubigeo_id"));
                            usuario.setToken(data_json.optString("token"));
                            usuario.setToken_firebase(data_json.optString("token_firebase"));




                            preferencesUser = context.getSharedPreferences("User", Context.MODE_PRIVATE);
                            //Guardamos los datos al sharetpreference
                            SharedPreferences.Editor editor=preferencesUser.edit();
                            editor.putString("id_user",usuario.getId_user());
                            editor.putString("id_person",usuario.getId_person());
                            editor.putString("user_nickname",usuario.getUser_nickname());
                            editor.putString("user_email",usuario.getUser_email());
                            editor.putString("user_image",usuario.getUser_image());
                            editor.putString("person_name",usuario.getPerson_name());
                            editor.putString("person_surname",usuario.getPerson_surname());
                            editor.putString("person_dni",usuario.getPerson_dni());
                            editor.putString("person_birth",usuario.getPerson_birth());
                            editor.putString("person_number_phone",usuario.getPerson_number_phone());
                            editor.putString("person_genre",usuario.getPerson_genre());
                            editor.putString("person_address",usuario.getPerson_address());
                            editor.putString("user_num",usuario.getUser_num());
                            editor.putString("user_posicion",usuario.getUser_posicion());
                            editor.putString("user_habilidad",usuario.getUser_habilidad());
                            editor.putString("ubigeo_id",usuario.getUbigeo_id());
                            editor.putString("token",usuario.getToken());
                            editor.putString("token_firebase",usuario.getToken_firebase());
                            editor.apply();


                            //Nuevo
                            //usuario = new Usuario(usuario.getUsuario_id(),usuario.getUsuario_usuario(),usuario.getUsuario_nombre(), usuario.getUsuario_email(),usuario.getUsuario_habilidad(),usuario.getUsuario_posicion(), usuario.getUsuario_numFavorito(), usuario.getUsuario_foto(),usuario.getUbigeo_id(),usuario.getToken_firebase());
                            usuario = new Usuario(usuario.getId_user(),usuario.getId_person(),usuario.getUser_nickname(),usuario.getUser_email(),usuario.getUser_image(),
                                    usuario.getPerson_name(),usuario.getPerson_surname(),usuario.getPerson_dni(),usuario.getPerson_birth(),usuario.getPerson_number_phone(),
                                    usuario.getPerson_genre(),usuario.getPerson_address(),usuario.getUser_num(),usuario.getUser_posicion(),usuario.getUser_habilidad(),
                                    usuario.getUbigeo_id(),usuario.getToken(),usuario.getToken_firebase());

                    }else{
                        respuesta = "false";
                        usuario = null;
                    }




                }if(funcion.equals("registrarse")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");


                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else if(valorcodigo.equalsIgnoreCase("2")) {
                        respuesta = "2";
                    }else {
                        respuesta = "3";
                    }

                }if(funcion.equals("registrarEmpresas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }
                //listarEquipo

                if(funcion.equals("listarEquipo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Equipo equipo;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        equipo = new Equipo();
                        equipo.setEquipo_id(jsonNode.optString("equipo_id"));
                        equipo.setEquipo_nombre(jsonNode.optString("nombre"));
                        equipo.setEquipo_foto(jsonNode.optString("foto"));
                        //Llenamos los datos al Array
                        listaEquipos.add(equipo);

                    }
                }

                if(funcion.equals("valorarEmpresa")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }

                if(funcion.equals("registrarUsuarioEquipo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }
                if(funcion.equals("registrarEquipoTorneo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }
                if(funcion.equals("registrarTorneoPorUssuario")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }



                if(funcion.equals("registrarCancha")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }

                //registrarReto

                if(funcion.equals("registrarReto")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else {
                        respuesta = "2";
                    }

                }
                if(funcion.equals("registrarReserva")) {

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if (valorcodigo.equalsIgnoreCase("1")) {
                        respuesta = "1";
                    } else {
                        respuesta = "2";
                    }
                }
                if(funcion.equals("registrarEquipo")) {

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    valorcodigo = jsonNodev.optString("valor");

                    if (valorcodigo.equalsIgnoreCase("1")) {
                        respuesta = "1";
                    } else {
                        respuesta = "2";
                    }
                }


                if(funcion.equals("listarcanchasEmpresas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Cancha cancha;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        cancha = new Cancha();
                        cancha.setCancha_id(jsonNode.optString("cancha_id"));
                        cancha.setCancha_nombre(jsonNode.optString("nombre"));
                        cancha.setCancha_dimenciones(jsonNode.optString("dimensiones"));
                        cancha.setCancha_precioD(jsonNode.optString("precioD"));
                        cancha.setCancha_precioN(jsonNode.optString("precioN"));
                        cancha.setCancha_foto(jsonNode.optString("foto"));

                        //Llenamos los datos al Array
                        listaCanchaEmpresa.add(cancha);
                    }
                }

              //


                if(funcion.equals("obtenerHoraFecha")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    HoraFecha horaFecha;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        horaFecha = new HoraFecha();
                        horaFecha.setH_f_hora(jsonNode.optString("hora"));
                        horaFecha.setH_f_fecha(jsonNode.optString("fecha"));


                        //Llenamos los datos al Array
                        listaHoraFecha.add(horaFecha);
                    }
                }


                if(funcion.equals("listarCanchasDisponibles2")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    final int horaactual = Integer.parseInt(hora);
                    int horasuma = 0;
                    int horafinal = 0;


                    // Populate our list with groups and it's children
                    for(int i = 1; i < count;i++) {

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        horasuma = horaactual + i;
                        JSONArray resultJSONhora = jsonNode.optJSONArray(String.valueOf(horasuma));
                        int counthora = resultJSONhora.length();


                        FragmentBuscar.GroupItem item = new FragmentBuscar.GroupItem();
                        String  horaFinal= "";
                      //  int hf =0;
                         int ii=i;
                         if(ii==0){
                             ii=12;
                         }
                        if(horasuma>12){
                            horasuma= horasuma-12;
                            if(ii>12){
                                ii=ii-12;
                            }
                           // horaFinal =  ii+":00 - "+horasuma+":00 pm";
                           // horafinal = hf + 1 ;
                            //item.title = hf+":00 - " + horafinal+":00 pm";
                            item.title = ii+":00 - "+horasuma+":00 pm";

                        }
                        else{
                           // horafinal = horasuma + 1 ;
                            item.title = i+":00 - "+horasuma+":00 am";
                        }

                        //horafinal = horasuma + 1 ;
                        //item.title = horasuma+":00 - " + horafinal+":00";

                        for(int i1 = 0; i1 < counthora;i1++) {
                            JSONObject jsonNodehora = resultJSONhora.getJSONObject(i1);


                            FragmentBuscar.ChildItem child = new FragmentBuscar.ChildItem();
                            child.txt_buscar_nombreEmpresa = jsonNodehora.optString("empresa_nombre") ;
                            child.txt_buscar_direccionEmpresa = jsonNodehora.optString("empresa_direccion");
                            child.txt_buscar_precioCancha = jsonNodehora.optString("cancha_precioD");
                            // child.imb_llamar = jsonNodehora.optString("cancha_telefono");;
                            child.txt_llamar = jsonNodehora.optString("empresa_telefono");;

                            item.items.add(child);
                        }

                        listaCanchasDisponiblesOriginal.add(item);
                    }


                }

                if(funcion.equals("listarCanchasDisponibles")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    final int horaactual = Integer.parseInt(hora);
                    int horasuma = 0;
                    int horafinal = 0;

                    // Populate our list with groups and it's children
                    for(int i = 0; i < count-1;i++) {

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        horasuma = horaactual + i;
                        JSONArray resultJSONhora = jsonNode.optJSONArray(String.valueOf(horasuma));
                        int counthora = resultJSONhora.length();


                        FragmentBuscar.GroupItem item = new FragmentBuscar.GroupItem();

                        int hf =0;
                       // int ii=i;
                       /* if(hf==0){
                            hf=12;
                        }*/
                        if(horasuma>12){
                            hf= horasuma-12;
                            //horaFinal =  ii+":00 - "+hf+":00 pm";
                            horafinal = hf + 1 ;
                            item.title = hf+":00 - " + horafinal+":00 pm";

                        }
                        else{
                            horafinal = horasuma + 1 ;
                            item.title = horasuma+":00 - " + horafinal+":00 am";
                        }

                        //horafinal = horasuma + 1 ;
                        //item.title = horasuma+":00 - " + horafinal+":00";

                        for(int i1 = 0; i1 < counthora;i1++) {
                            JSONObject jsonNodehora = resultJSONhora.getJSONObject(i1);


                            FragmentBuscar.ChildItem child = new FragmentBuscar.ChildItem();
                            child.txt_buscar_nombreEmpresa = jsonNodehora.optString("empresa_nombre") ;
                            child.txt_buscar_direccionEmpresa = jsonNodehora.optString("empresa_direccion");
                            child.txt_buscar_precioCancha = jsonNodehora.optString("cancha_precioD");
                            child.img_cancha = jsonNodehora.optString("empresa_foto");

                           // child.imb_llamar = jsonNodehora.optString("cancha_telefono");;
                            child.txt_llamar = jsonNodehora.optString("empresa_telefono");;

                            item.items.add(child);
                        }

                        listaCanchasDisponiblesOriginal.add(item);
                    }


                }if(funcion.equals("listarCanchasDisponiblesBusqueda")){

                    String horaaaaa = "10";
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    final int horaactual = Integer.parseInt(horaaaaa);
                    //final int horaactual = 06;
                    int horasuma = 0;
                    int horafinal = 0;

                    // Populate our list with groups and it's children
                    for(int i = 0; i < count-1;i++) {

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        horasuma = horaactual + i;
                        JSONArray resultJSONhora = jsonNode.optJSONArray(String.valueOf(horasuma));
                        int counthora = resultJSONhora.length();


                        FragmentBuscarFechas.GroupItemBusqueda item = new FragmentBuscarFechas.GroupItemBusqueda();

                        int hf =0;
                        // int ii=i;
                       /* if(hf==0){
                            hf=12;
                        }*/
                        if(horasuma>12){
                            hf= horasuma-12;
                            //horaFinal =  ii+":00 - "+hf+":00 pm";
                            horafinal = hf + 1 ;
                            item.title = hf+":00 - " + horafinal+":00 pm";

                        }
                        else{
                            horafinal = horasuma + 1 ;
                            item.title = horasuma+":00 - " + horafinal+":00 am";
                        }

                        //horafinal = horasuma + 1 ;
                        //item.title = horasuma+":00 - " + horafinal+":00";

                        for(int i1 = 0; i1 < counthora;i1++) {
                            JSONObject jsonNodehora = resultJSONhora.getJSONObject(i1);


                            FragmentBuscarFechas.ChildItemBusqueda child = new FragmentBuscarFechas.ChildItemBusqueda();
                            child.txt_buscar_nombreEmpresa = jsonNodehora.optString("empresa_nombre") ;
                            child.txt_buscar_direccionEmpresa = jsonNodehora.optString("empresa_direccion");
                            child.txt_buscar_precioCancha = jsonNodehora.optString("cancha_precioD");
                            child.img_cancha = jsonNodehora.optString("empresa_foto");

                            // child.imb_llamar = jsonNodehora.optString("cancha_telefono");;
                            child.txt_llamar = jsonNodehora.optString("empresa_telefono");;

                            item.items.add(child);
                        }

                        listaCanchasDisponiblesBusqueda.add(item);
                    }


                }if(funcion.equals("listarCanchasDisponiblesBusqueda2")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Empresas empresas;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        empresas = new Empresas();
                        empresas.setEmpresas_id(jsonNode.optString("empresa_id"));
                        empresas.setUsuario_id(jsonNode.optString("usuario_id"));
                        empresas.setUbigeo_id(jsonNode.optString("ubigeo_id"));
                        empresas.setEmpresas_nombre(jsonNode.optString("empresa_nombre"));
                        empresas.setEmpresas_direccion(jsonNode.optString("empresa_direccion"));
                        empresas.setEmpresas_telefono(jsonNode.optString("empresa_telefono"));
                        empresas.setEmpresas_descripcion(jsonNode.optString("empresa_descripcion"));
                        empresas.setEmpresas_horario(jsonNode.optString("empresa_horario"));
                        empresas.setEmpresas_valoracion(jsonNode.optString("empresa_valoracion"));
                        empresas.setEmpresas_foto(jsonNode.optString("empresa_foto"));
                        empresas.setEmpresas_estado(jsonNode.optString("empresa_estado"));
                        empresas.setCancha_precioD(jsonNode.optString("cancha_precioD"));
                        empresas.setCancha_precioN(jsonNode.optString("cancha_precioN"));

                        listaCachasHoraEmpresa.add(empresas);
                    }
                }

                if(funcion.equals("listarUsuarioGeneral")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Usuario usuario;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        usuario = new Usuario();
                        usuario.setUsuario_id(jsonNode.optString("usuario_id"));
                        usuario.setUsuario_nombre(jsonNode.optString("nombre"));
                        usuario.setUsuario_foto(jsonNode.optString("foto"));

                        //Llenamos los datos al Array
                        listaUsuarioGeneral.add(usuario);
                    }
                }



                if(funcion.equals("listarEstadisticasDiarias")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Reserva reserva;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        reserva = new Reserva();
                        reserva.setCancha_id(jsonNode.optString("cancha_id"));
                        reserva.setReserva_color(jsonNode.optString("reserva_id"));
                        reserva.setCancha_nombre(jsonNode.optString("cancha_nombre"));
                        reserva.setReserva_nombre(jsonNode.optString("reserva_nombre"));
                        reserva.setReserva_hora(jsonNode.optString("reserva_hora"));
                        reserva.setReserva_costo(jsonNode.optString("reserva_costo"));


                        listaEstadisticasDiarias.add(reserva);
                    }
                }

                if(funcion.equals("listarEstadisticasGeneral")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Reserva reserva;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        reserva = new Reserva();
                        reserva.setCancha_id(jsonNode.optString("cancha_id"));
                        reserva.setReserva_color(jsonNode.optString("reserva_id"));
                        reserva.setCancha_nombre(jsonNode.optString("cancha_nombre"));
                        reserva.setReserva_nombre(jsonNode.optString("reserva_nombre"));
                        reserva.setReserva_hora(jsonNode.optString("reserva_hora"));
                        reserva.setReserva_costo(jsonNode.optString("reserva_costo"));


                        listaEstadisticasGeneral.add(reserva);
                    }
                }

                if(funcion.equals("listarEquiposEnTorneo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Equipo equipo;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        equipo = new Equipo();
                        equipo.setEquipo_id(jsonNode.optString("equipo_id"));
                        equipo.setEquipo_nombre(jsonNode.optString("nombre"));
                        equipo.setEquipo_foto(jsonNode.optString("foto"));
                        equipo.setUsuario_nombre(jsonNode.optString("capitan"));

                        listaEquiposEnTorneo.add(equipo);
                    }
                }



                if(funcion.equals("listarEquiposEnTorneoNot")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Equipo equipo;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        equipo = new Equipo();
                        equipo.setEquipo_id(jsonNode.optString("equipo_id"));
                        equipo.setEquipo_nombre(jsonNode.optString("nombre"));
                        equipo.setEquipo_foto(jsonNode.optString("foto"));
                        equipo.setUsuario_nombre(jsonNode.optString("capitan"));

                        listaEquiposEnTorneoNot.add(equipo);
                    }
                }




                if(funcion.equals("listarUsuarioPorEquipo")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Usuario usuario;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        usuario = new Usuario();
                        usuario.setUsuario_id(jsonNode.optString("usuario_id"));
                        usuario.setUsuario_nombre(jsonNode.optString("nombre"));
                        usuario.setUsuario_foto(jsonNode.optString("foto"));
                        usuario.setUsuario_posicion(jsonNode.optString("posicion"));
                        usuario.setUsuario_habilidad(jsonNode.optString("habilidad"));
                        usuario.setUsuario_numFavorito(jsonNode.optString("num"));

                        //Llenamos los datos al Array
                        listaUsuarioPorEquipo.add(usuario);
                    }
                }


                //listarEquipoGeneral

                if(funcion.equals("listarEquipoGeneral")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Equipo equipo;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        equipo = new Equipo();
                        equipo.setEquipo_id(jsonNode.optString("equipo_id"));
                        equipo.setEquipo_nombre(jsonNode.optString("nombre"));
                        equipo.setEquipo_foto(jsonNode.optString("foto"));
                        equipo.setUsuario_nombre(jsonNode.optString("capitan"));
                        //Llenamos los datos al Array
                        listaEquiposGenaral.add(equipo);
                    }
                }
                if(funcion.equals("listarEmpresas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Empresas empresas;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        empresas = new Empresas();
                        empresas.setEmpresas_nombre(jsonNode.optString("nombre"));
                        empresas.setEmpresas_direccion(jsonNode.optString("direccion"));
                        empresas.setEmpresas_foto(jsonNode.optString("foto"));
                        empresas.setEmpresas_id(jsonNode.optString("id_empresa"));

                        //Llenamos los datos al Array
                        listaEmpresa.add(empresas);
                    }
                 }
                if(funcion.equals("listarcanchasReservas")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Reserva reserva;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        reserva = new Reserva();

                        reserva.setReserva_id(jsonNode.optString("reserva_id"));
                        reserva.setCancha_id(jsonNode.optString("cancha_id"));
                        reserva.setReserva_nombre(jsonNode.optString("nombre"));
                        reserva.setReserva_fecha(jsonNode.optString("fecha"));
                        reserva.setReserva_hora(jsonNode.optString("hora"));
                        reserva.setReserva_costo(jsonNode.optString("costo"));
                       /// reserva.setReserva_abono(jsonNode.optString("costo"));
                        reserva.setReserva_estado(jsonNode.optString("estado"));

                        //Llenamos los datos al Array
                        listaCanchaReserva.add(reserva);
                    }
                }


                if(funcion.equals("mostrarDetalleEmpresa")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Empresas obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Empresas();
                        obj.setEmpresas_nombre(jsonNode.optString("nombre"));
                        obj.setEmpresas_direccion(jsonNode.optString("direccion"));
                        obj.setEmpresas_descripcion(jsonNode.optString("descripcion"));
                        obj.setEmpresas_horario(jsonNode.optString("horario"));
                        obj.setEmpresas_valoracion(jsonNode.optString("valoracion"));
                        obj.setEmpresas_promedio(jsonNode.optString("promedio"));
                        obj.setEmpresas_conteo(jsonNode.optString("conteo"));
                        obj.setEmpresas_foto(jsonNode.optString("foto"));
                        obj.setEmpresas_estado(jsonNode.optString("estado"));
                        obj.setUsuario_id(jsonNode.optString("usuario"));
                        obj.setUbigeo_id(jsonNode.optString("distrito"));
                        obj.setEmpresa_cancha_fecha(jsonNode.optString("fecha_actual"));
                        obj.setEmpresa_cancha_hora(jsonNode.optString("hora_actual"));
                        obj.setEmpresas_telefono(jsonNode.optString("telefono"));

                        //Llenamos los datos al Array
                        listaEmpresa.add(obj);
                    }

                }if(funcion.equals("listarCiudades")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);
                        //Llenamos los datos al Array
                        listaCiudades.add(jsonNode.optString("ubigeo_ciudad"));
                    }
                }if(funcion.equals("listarDistritoxCiudades")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    MDistrito obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new MDistrito();
                        obj.setUbigeo_id(jsonNode.optString("ubigeo_id"));
                        obj.setDistrito(jsonNode.optString("distrito"));

                        //Llenamos los datos al Array
                        listaDistritoCiudades.add(obj);
                    }
                }
                if(funcion.equals("dar_like")){
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    respuesta = jsonNodev.optString("cant");

                }if(funcion.equals("quitar_like")){
                    JSONArray resultJSON = json_data.getJSONArray("results");
                    JSONObject jsonNodev = resultJSON.getJSONObject(0);
                    respuesta = jsonNodev.optString("cant");

                }if (funcion.equals("listarEmpresasID")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Empresas obj;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        obj = new Empresas();
                        obj.setEmpresas_nombre(jsonNode.optString("nombre"));
                        obj.setEmpresas_id(jsonNode.optString("id_empresa"));
                        //Llenamos los datos al Array
                        listaEmpresasDistrito.add(obj);
                    }

                }
                return true;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }


        return false;
    }




    class GetAndSet extends AsyncTask<String,String,String > {


        String valor = "";
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(mensajeprogres){
                loading = ProgressDialog.show(context, "Capitan", "Por favor espere...", false, false);
            }

        }



        @Override
        protected  String doInBackground(String... params) {

            try{
                if(filtrardDatos()){

                    actividad();
                }
            }catch (NullPointerException e){
                valor = "Problema con la conexion a internet null";
            }catch (RuntimeException e){
                valor = "Problema con la conexion a internet";
            }
            return valor;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(mensajeprogres){
                loading.dismiss();
            }
            if(!valor.equals("")){
                Toast.makeText(context, valor, Toast.LENGTH_LONG).show();
            }

        }
    }

    private  void actividad(){

        if(funcion.equals("loginUsuario")){

            if(respuesta.equals("true")){

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(context, MenuPrincipal.class);

                        intent.putExtra("usuario_nombre",usuario.getPerson_name() + " "+ usuario.getPerson_surname());
                        intent.putExtra("usuario_id",usuario.getId_user());
                        intent.putExtra("usuario_email",usuario.getUser_email());
                        intent.putExtra("usuario_foto",usuario.getUser_image());
                        intent.putExtra("ubigeo_id",usuario.getUbigeo_id());
                        intent.putExtra("posicion",usuario.getUser_posicion());


                        /*intent.putExtra("usuario_nombre",usuario.getUsuario_nombre());
                        intent.putExtra("usuario_id",usuario.getUsuario_id());
                        intent.putExtra("usuario_email",usuario.getUsuario_email());
                        intent.putExtra("usuario_foto",usuario.getUsuario_foto());
                        intent.putExtra("ubigeo_id",usuario.getUbigeo_id());
                        intent.putExtra("posicion",usuario.getUsuario_posicion());*/
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }
                });
            }else {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(Integer.toString(code).equalsIgnoreCase("2")){
                            Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        else if(Integer.toString(code).equalsIgnoreCase("3")){
                            Toast.makeText(context, "Cuenta desactivada", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "No tiene permisos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        }if(funcion.equals("registrarse")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show();
                        context.finish();

                    }
                });

            }else if(respuesta.equals("3")){

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "DNI ya existe", Toast.LENGTH_SHORT).show();

                    }
                });

            } else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
        if(funcion.equals("registrarEmpresas")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/
                        FragmentNegocio.ActualizarEmpresas();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }

        if(funcion.equals("valorarEmpresa")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // context.finish();
                        Toast.makeText(context, "Valoracion enviada correctamente", Toast.LENGTH_SHORT).show();
                      //  btn_enviarV.setVisibility(View.GONE);
                        //btn_cancelarV.setVisibility(View.GONE);
                        btn_enviarV.setVisibility(View.GONE);
                        btn_cancelarV.setVisibility(View.GONE);
                        btn_editVal.setVisibility(View.VISIBLE);
                        rtb_valorar.setEnabled(false);
                        actualizarDetalle();


                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/
                        //FragmentNegocio.ActualizarEmpresas();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }
        if(funcion.equals("registrarUsuarioEquipo")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                       // Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/
                        //DetalleEquipo.ActualizarEquipo();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error al agregar jugadores", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }
        //registrarEquipoTorneo
        if(funcion.equals("registrarEquipoTorneo")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                         //Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/
                        //DetalleEquipo.ActualizarEquipo();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error al agregar equipos", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }
        if(funcion.equals("registrarTorneoPorUssuario")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                         Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/
                        //DetalleEquipo.ActualizarEquipo();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error al registar Torneo", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }
        if(funcion.equals("registrarReserva")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       // DetalleEquipo.ActualizarEquipo();
                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/

                        if (tabLayout.getSelectedTabPosition()==0){
                            FragmentHoy.actualizarReserva();
                        }
                        else if (tabLayout.getSelectedTabPosition()==1){
                            FragmentMañana.actualizarReserva();
                        }
                       else{FragmentPasMañana.actualizarReserva(); }

                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }

        if(funcion.equals("registrarEquipo")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(context, FragmentNegocio.class);
                        intent.putExtra("empresas_id",empresas.getEmpresas_id());*/
                        //FragmentEquiposHijo.ActualizarEquipo();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }

        if(funcion.equals("registrarCancha")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();
                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                       // FragmentNegocio.ActualizarEmpresas();
                        DetalleNegocio.ActualizarCanchas();
                    }
                });

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }
        //registrarReto
        if(funcion.equals("registrarReto")) {

            if (respuesta.equals("1")) {
                //tabLayout.getSelectedTabPosition();

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        context.finish();

                        /*Intent intent = new Intent(context, FragmentEquiposHijo.class);
                        startActivity(intent);*/

                        Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show();

                        // FragmentNegocio.ActualizarEmpresas();
                       // DetalleNegocio.ActualizarCanchas();
                        //FragmentEquiposHijo.ActualizarRetos();
                        //tabLayoutT.setSelected(true);
                    }
                });

               // FragmentTorneoHijo.getTap();
                //tabLayoutT.getSelectedTabPosition();

            } else {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    public Usuario getUsuario(){
        return usuario;
    }
    public ArrayList<Usuario> getListadoUsuarioGeneral(){return listaUsuarioGeneral;}
    public ArrayList<Usuario> getListadoUsuarioPorEquipo(){return listaUsuarioPorEquipo;}
    public ArrayList<Empresas> getListadoEmpresas(){return listaEmpresa;}
    public ArrayList<Cancha> getListadoCanchas(){return listaCanchaEmpresa;}
    public ArrayList<Reserva> getListadoCanchasReserva(){return listaCanchaReserva;}
    public ArrayList<Equipo> getListadoEquipos(){return listaEquipos;}
    public ArrayList<Equipo> getlistaEquiposEnTorneo(){return listaEquiposEnTorneo;}
    public ArrayList<Equipo> getlistaEquiposEnTorneoNot(){return listaEquiposEnTorneoNot;}
    public ArrayList<HoraFecha> getHoraFecha(){return listaHoraFecha;}

    public ArrayList<Reserva> getLitadoEstadisticasDiarias(){return listaEstadisticasDiarias;}
    public ArrayList<Reserva> getLitadoEstadisticasGeneral(){return listaEstadisticasGeneral;}
    public ArrayList<FragmentBuscar.GroupItem> getListadoCanchasDisponiblesOriginal(){return listaCanchasDisponiblesOriginal;}
    public ArrayList<FragmentBuscarFechas.GroupItemBusqueda> getListadoCanchasDisponiblesBusqueda(){return listaCanchasDisponiblesBusqueda;}

    public ArrayList<String> getListaCiudades(){
        return listaCiudades;
    }

    public ArrayList<Empresas> getListaEmpresasDistrito(){
        return listaEmpresasDistrito;
    }


    public ArrayList<Empresas> getListaCachasHoraEmpresa(){
        return listaCachasHoraEmpresa;
    }

    public ArrayList<MDistrito> getListaDistritoxCiudad(){
        return listaDistritoCiudades;
    }

}