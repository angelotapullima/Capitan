package com.tec.bufeo.capitan.WebService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Fragments.tabsBuscar.FragmentBuscar;
import com.tec.bufeo.capitan.Fragments.tabsBuscar.FragmentBuscarFechas;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Models.Torneo;
import com.tec.bufeo.capitan.Modelo.Cancha;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.Modelo.Saldo;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.others.Equipo;

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
import java.util.Date;
import java.util.regex.Pattern;


public class DataConnection extends AppCompatActivity {

    public String funcion, idciudad , fecha, torneo_id;
    public String parametros, respuesta, cargarDatos,hora;
    boolean mensajeprogres;

    public static final String IP2 = "https://www.guabba.com/capitan2";
    public static final String NombreCapeta ="/.Capitan";
    Activity context;
    JSONObject json_data;
    Usuario usuario;
    Empresas empresas;
    Cancha cancha;
    Reserva reserva;
    Equipo equipo;
    Retos retos;
    Torneo torneo;
    SharedPreferences preferencesUser;
    Preferences preferences;




    public ArrayList<Empresas> listaCachasHoraEmpresa = new ArrayList();
    public ArrayList<FragmentBuscar.GroupItem> listaCanchasDisponiblesOriginal = new ArrayList();
    public ArrayList<FragmentBuscarFechas.GroupItemBusqueda> listaCanchasDisponiblesBusqueda= new ArrayList();
    public ArrayList<Cancha> listaCanchaEmpresa = new ArrayList();
    public ArrayList<Reserva> listaCanchaReserva= new ArrayList();
    public ArrayList<Saldo> saldo = new ArrayList();
    public ArrayList<Empresas> listaEmpresasDistrito= new ArrayList();



    public DataConnection() {

    }

    //Listar Canchas Disponibles
    public DataConnection(Activity context, String funcion, boolean mensajeprogres) {
        this.context = context;
        this.funcion = funcion;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }



    //Asignamos valores a traves del constructor (Multifuncion Usuario-Listados tablas)
    public DataConnection(Activity context, String funcion, Usuario usuario, boolean mensajeprogres) {
        this.context = context;
        this.funcion = funcion;
        this.usuario = usuario;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }
    //Registro de Empresas
    public DataConnection(Activity context, String funcion, Empresas empresas, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.empresas = empresas;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }

    //Listado de Empresas - Listado Distrito
    public DataConnection(Activity context, String funcion, String idciudad, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.idciudad = idciudad;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }
    //Registro de Canchas- Listar
    public DataConnection(Activity context, String funcion, Cancha cancha, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.cancha = cancha;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }

    //Listado de Canchas Reservas
    public DataConnection(Activity context, String funcion, Reserva reserva, String fecha, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.reserva = reserva;
        this.fecha = fecha;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }
    //Insertar Equipos a un torneo
    public DataConnection(Activity context, String funcion, Equipo equipo, String torneo_id, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.equipo= equipo;
        this.torneo_id = torneo_id;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }
    public DataConnection(Activity context, String funcion, Reserva reserva, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.reserva = reserva;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }

    //º de Empresas
    public DataConnection(Activity context, String funcion, Equipo equipo, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.equipo = equipo;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }

    //De Retos-Registar-Listar
    public DataConnection(Activity context, String funcion, Retos retos, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.retos = retos;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }
    //De Torneo-Registar-Listar
    public DataConnection(Activity context, String funcion, Torneo torneo, boolean mensajeprogres){
        this.context = context;
        this.funcion = funcion;
        this.torneo = torneo;
        this.mensajeprogres = mensajeprogres;
        preferences =  new Preferences(context);
        new GetAndSet().execute();
    }




    //Enviamos los datos al webservice y devuelve un valor string-respuesta
    private  String obtenerDatos(){
        StringBuffer response = null;
        try {

            URL url = new URL(IP2+"/index.php?c=Cliente&a=registrar");


            if(funcion.equals("loginUsuario")){
                parametros = "user=" + URLEncoder.encode(usuario.getUser_nickname(),"UTF-8")
                        + "&pass=" + URLEncoder.encode(usuario.getUser_password(),"UTF-8")
                + "&app=" + URLEncoder.encode("true","UTF-8");

                //url = new URL(IP+"/index.php?c=Usuario&a=loguearse&key_mobile=123456asdfgh");
                url = new URL(IP2+"/api/Login/validar_usuario");

            }if(funcion.equals("registrarse")){
                parametros = "person_name=" + URLEncoder.encode(usuario.getUsuario_nombre(),"UTF-8")
                        + "&person_surname=" + URLEncoder.encode(usuario.getUsuario_nacimiento(),"UTF-8")
                        + "&person_birth=" + URLEncoder.encode(usuario.getUsuario_nacimiento(),"UTF-8")
                        + "&user_image=" + URLEncoder.encode(usuario.getUsuario_foto(),"UTF-8")
                        + "&person_genre=" + URLEncoder.encode(usuario.getUsuario_sexo(),"UTF-8")
                        + "&user_nickname=" + URLEncoder.encode(usuario.getUsuario_usuario(),"UTF-8")
                        + "&user_password=" + URLEncoder.encode(usuario.getUsuario_clave(),"UTF-8")
                        + "&user_email=" + URLEncoder.encode(usuario.getUsuario_email(),"UTF-8")
                        + "&user_habilidad=" + URLEncoder.encode(usuario.getUsuario_habilidad(),"UTF-8")
                        + "&user_posicion=" + URLEncoder.encode(usuario.getUsuario_posicion(),"UTF-8")
                        + "&user_num=" + URLEncoder.encode(usuario.getUsuario_numFavorito(),"UTF-8")
                        + "&person_dni=" + URLEncoder.encode(usuario.getUsuario_dni(),"UTF-8")
                        + "&person_number_phone=" + URLEncoder.encode(usuario.getUsuario_telefono(),"UTF-8")
                        + "&id_role=" + URLEncoder.encode(usuario.getRol_id(),"UTF-8")
                        + "&person_address=" + URLEncoder.encode(usuario.getRol_id(),"UTF-8")
                        + "&app=" + URLEncoder.encode("true","UTF-8")
                        + "&ubigeo_id=" + URLEncoder.encode(usuario.getUbigeo_id(),"UTF-8");

                url = new URL(IP2+"/api/Login/new");


            }



            if(funcion.equals("listarCanchasDisponibles")){
                parametros = " " + URLEncoder.encode(" ","UTF-8")
                + "&app=" + URLEncoder.encode("true","UTF-8")
                + "&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8");
                url = new URL(IP2+"/api/Empresa/listar_canchas_libres_por_hora");
            }




            if(funcion.equals("ObtenerSaldo")){
                parametros = "app=" + URLEncoder.encode("true","UTF-8")
                        + "&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8")
                        + "&id=" + URLEncoder.encode(preferences.getIdUsuarioPref(),"UTF-8");

                url = new URL(IP2+"/api/Empresa/obtener_saldo_actual");
            }


            if(funcion.equals("listarcanchasEmpresas")){
                parametros = "id_empresa=" + URLEncoder.encode(cancha.getEmpresas_id(),"UTF-8")
                        + "&app=" + URLEncoder.encode("true","UTF-8")
                        + "&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8");

                url = new URL(IP2+"/api/Empresa/listar_canchas_por_id_empresa");
            }



            if(funcion.equals("listarcanchasReservas")){
                parametros = "id_cancha=" + URLEncoder.encode(reserva.getCancha_id(),"UTF-8")
                + "&fecha=" + URLEncoder.encode(fecha,"UTF-8")
                + "&app=" + URLEncoder.encode("true","UTF-8")
                + "&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8");
                url = new URL(IP2+"/api/Empresa/listar_reservados_por_cancha_por_fecha");
            }



            if(funcion.equals("listarDistritoxCiudades")){
                parametros = "ciudad=" + URLEncoder.encode(idciudad,"UTF-8");

                url = new URL(IP2+"/api/Login/listar_distritos_por_ciudad");
            }

            if (funcion.equals("listarEmpresasID")){
                parametros = "id_ciudad=" + URLEncoder.encode(idciudad,"UTF-8")
                        +"&app=" + URLEncoder.encode("true","UTF-8")
                        +"&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8");;
                url = new URL(IP2+"/api/Empresa/listar_empresas_por_id_ciudad");

            }if(funcion.equals("listarCanchasDisponiblesBusqueda")){
                parametros = "hora=" + URLEncoder.encode(reserva.getReserva_hora(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(reserva.getReserva_fecha(),"UTF-8")
                        +"&negocio=" + URLEncoder.encode(reserva.getCancha_nombre(),"UTF-8")
                        +"&app=" + URLEncoder.encode("true","UTF-8")
                        +"&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8");
                url = new URL(IP2+"/api/Empresa/busqueda_avanzada");

            }if(funcion.equals("listarCanchasDisponiblesBusqueda2")){
                parametros = "hora=" + URLEncoder.encode(reserva.getReserva_hora(),"UTF-8")
                        + "&fecha=" + URLEncoder.encode(reserva.getReserva_fecha(),"UTF-8")
                        +"&negocio=" + URLEncoder.encode(reserva.getCancha_nombre(),"UTF-8")
                        +"&app=" + URLEncoder.encode("true","UTF-8")
                        +"&token=" + URLEncoder.encode(preferences.getToken(),"UTF-8");
                url = new URL(IP2+"/api/Empresa/busqueda_avanzada");
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
        Log.e("obtenerDatos", "cargarDatos: "+cargarDatos + funcion);
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
                            usuario.setTiene_negocio(data_json.optString("tiene_negocio"));




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
                            editor.putString("tiene_negocio",usuario.getTiene_negocio());
                            editor.putInt("cantida_foto_perfil",0);
                            editor.putString("cantidad_ingreso","1");
                            editor.apply();


                            //Nuevo
                            //usuario = new Usuario(usuario.getUsuario_id(),usuario.getUsuario_usuario(),usuario.getUsuario_nombre(), usuario.getUsuario_email(),usuario.getUsuario_habilidad(),usuario.getUsuario_posicion(), usuario.getUsuario_numFavorito(), usuario.getUsuario_foto(),usuario.getUbigeo_id(),usuario.getToken_firebase());
                            usuario = new Usuario(usuario.getId_user(),usuario.getId_person(),usuario.getUser_nickname(),usuario.getUser_email(),usuario.getUser_image(),
                                    usuario.getPerson_name(),usuario.getPerson_surname(),usuario.getPerson_dni(),usuario.getPerson_birth(),usuario.getPerson_number_phone(),
                                    usuario.getPerson_genre(),usuario.getPerson_address(),usuario.getUser_num(),usuario.getUser_posicion(),usuario.getUser_habilidad(),
                                    usuario.getUbigeo_id(),usuario.getToken(),usuario.getToken_firebase(),usuario.getTiene_negocio());

                    }else{
                        respuesta = "false";
                        usuario = null;
                    }




                }

                if(funcion.equals("registrarse")){

                  valorcodigo=cargarDatos;

                    if(valorcodigo.equalsIgnoreCase("1")){
                        respuesta = "1";
                    }else if(valorcodigo.equalsIgnoreCase("2")) {
                        respuesta = "2";
                    }else {
                        respuesta = "3";
                    }

                }
                if(funcion.equals("ObtenerSaldo")){


                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    for (int i = 0; i < count;i++){
                        Saldo s = new Saldo();
                        JSONObject jsonNode = resultJSON.getJSONObject(i);
                        s.setSaldo_actual(jsonNode.optString("cuenta_saldo"));
                        s.setComision(jsonNode.optString("comision"));
                        saldo.add(s);
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
                            child.txt_llamar1 = jsonNodehora.optString("empresa_telefono1");
                            child.txt_llamar2 = jsonNodehora.optString("empresa_telefono2");

                            item.items.add(child);
                        }

                        listaCanchasDisponiblesOriginal.add(item);
                    }


                }

                if(funcion.equals("listarCanchasDisponibles")){

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Date date =new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH");
                    hora = sdf.format(date);
                    final int horaactual = Integer.parseInt(hora);
                    int horasuma = 0;
                    String neo;
                    int horafinal = 0;

                    // Populate our list with groups and it's children
                    for(int i = 0; i < count-1;i++) {

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        horasuma = horaactual + i;
                        if (horasuma<10){
                            if (i==0){
                                neo ="0"+ String.valueOf(horasuma);
                            }else{
                                neo = String.valueOf(horasuma);
                            }
                        }else{
                            neo = String.valueOf(horasuma);
                        }

                        JSONArray resultJSONhora = jsonNode.optJSONArray(neo);
                        int counthora = resultJSONhora.length();


                        FragmentBuscar.GroupItem item = new FragmentBuscar.GroupItem();

                        int hf =0;
                       String h_res;

                        horafinal = horasuma + 1 ;
                        item.title = horasuma+":00 - " + horafinal+":00";
                        h_res = horasuma+":00-" + horafinal+":00";



                        for(int i1 = 0; i1 < counthora;i1++) {
                            JSONObject jsonNodehora = resultJSONhora.getJSONObject(i1);


                            String l_s,separador,part1,part2,separador_part1,hora_apertura,hora_cierre;
                            String[] resultado,resultado_part1,resultado_part2;


                            FragmentBuscar.ChildItem child = new FragmentBuscar.ChildItem();

                            SimpleDateFormat formatex = new SimpleDateFormat("E");
                            String dia = formatex.format(date);

                            if (dia.equals("dom.")){
                                l_s = jsonNodehora.optString("empresa_horario_d") ;
                            }else{
                                l_s = jsonNodehora.optString("empresa_horario_ls") ;
                            }

                            separador = Pattern.quote("-");
                            resultado = l_s.split(separador);
                            part1 = resultado[0];
                            part2 = resultado[1];

                            separador_part1 = Pattern.quote(":");
                            resultado_part1 = part1.split(separador_part1);
                            resultado_part2 = part2.split(separador_part1);
                            hora_apertura = resultado_part1[0];
                            hora_cierre= resultado_part2[0];

                            hora_cierre =hora_cierre.trim();
                            if (Integer.parseInt(neo) >=Integer.parseInt(hora_apertura) && Integer.parseInt(neo) < Integer.parseInt(hora_cierre.trim()) ){
                                child.txt_buscar_nombreEmpresa = jsonNodehora.optString("empresa_nombre") ;
                                child.txt_buscar_direccionEmpresa = jsonNodehora.optString("empresa_direccion");
                                child.img_cancha = jsonNodehora.optString("empresa_foto");
                                child.empresa_id = jsonNodehora.optString("empresa_id");
                                child.h_reserva = h_res;
                                //child.h_reserva = jsonNodehora.optString("empresa_horario_ls");
                                if (Integer.parseInt(neo)<18){
                                    child.txt_buscar_precioCancha = jsonNodehora.optString("cancha_precioD");
                                }else{
                                    child.txt_buscar_precioCancha = jsonNodehora.optString("cancha_precioN");
                                }


                                // child.imb_llamar = jsonNodehora.optString("cancha_telefono");;
                                child.txt_llamar1 = jsonNodehora.optString("empresa_telefono_1");
                                child.txt_llamar2 = jsonNodehora.optString("empresa_telefono_2");

                                item.items.add(child);
                            }


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
                    String neo;

                    String fecha  = reserva.getReserva_fecha();

                    // Populate our list with groups and it's children
                    for(int i = 0; i < count-1;i++) {

                        JSONObject jsonNode = resultJSON.getJSONObject(i);

                        horasuma = horaactual + i;
                        if (horasuma<10){
                            if (i==0){
                                neo ="0"+ String.valueOf(horasuma);
                            }else{
                                neo = String.valueOf(horasuma);
                            }
                        }else{
                            neo = String.valueOf(horasuma);
                        }
                        JSONArray resultJSONhora = jsonNode.optJSONArray(String.valueOf(neo));
                        int counthora = resultJSONhora.length();


                        FragmentBuscarFechas.GroupItemBusqueda item = new FragmentBuscarFechas.GroupItemBusqueda();

                        int hf =0;

                        String h_res;

                        horafinal = horasuma + 1 ;
                        item.title = horasuma+":00 - " + horafinal+":00";
                        h_res = horasuma+":00-" + horafinal+":00";


                        for(int i1 = 0; i1 < counthora;i1++) {
                            JSONObject jsonNodehora = resultJSONhora.getJSONObject(i1);




                            String l_s,separador,part1,part2,separador_part1,hora_apertura,hora_cierre;
                            String[] resultado,resultado_part1,resultado_part2;

                            FragmentBuscarFechas.ChildItemBusqueda child = new FragmentBuscarFechas.ChildItemBusqueda();


                            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
                            Date convertido = fechaHora.parse(fecha);

                            SimpleDateFormat formatex = new SimpleDateFormat("E");

                            String dia = formatex.format(convertido);


                            if (dia.equals("dom.")){
                                l_s = jsonNodehora.optString("empresa_horario_d") ;
                            }else{
                                l_s = jsonNodehora.optString("empresa_horario_ls") ;
                            }

                            separador = Pattern.quote("-");
                            resultado = l_s.split(separador);
                            part1 = resultado[0];
                            part2 = resultado[1];

                            separador_part1 = Pattern.quote(":");
                            resultado_part1 = part1.split(separador_part1);
                            resultado_part2 = part2.split(separador_part1);
                            hora_apertura = resultado_part1[0];
                            hora_cierre= resultado_part2[0];

                            hora_cierre =hora_cierre.trim();

                            if (Integer.parseInt(neo) >=Integer.parseInt(hora_apertura) && Integer.parseInt(neo) < Integer.parseInt(hora_cierre.trim()) ){
                                child.txt_buscar_nombreEmpresa = jsonNodehora.optString("empresa_nombre") ;
                                child.txt_buscar_direccionEmpresa = jsonNodehora.optString("empresa_direccion");
                                child.txt_buscar_precioCancha = jsonNodehora.optString("cancha_precioD");
                                child.img_cancha = jsonNodehora.optString("empresa_foto");
                                child.h_reserva = jsonNodehora.optString("empresa_foto");
                                child.empresa_id = jsonNodehora.optString("empresa_id");
                                child.h_reserva = h_res;
                                // child.imb_llamar = jsonNodehora.optString("cancha_telefono");;
                                child.txt_llamar1 = jsonNodehora.optString("empresa_telefono_1");;
                                child.txt_llamar2 = jsonNodehora.optString("empresa_telefono_2");;

                                item.items.add(child);
                            }



                        }

                        listaCanchasDisponiblesBusqueda.add(item);
                    }


                }if(funcion.equals("listarCanchasDisponiblesBusqueda2")){

                    String fecha  = reserva.getReserva_fecha();
                    String horabusqueda  = reserva.getReserva_hora();

                    JSONArray resultJSON = json_data.getJSONArray("results");
                    int count = resultJSON.length();
                    Empresas empresas;

                    for (int i = 0; i < count;i++){

                        JSONObject jsonNode = resultJSON.getJSONObject(i);


                        String l_s,separador,part1,part2,separador_part1,hora_apertura,hora_cierre,separador_hora;
                        String[] resultado,resultado_part1,resultado_part2,resultado_hora;

                        DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
                        Date convertido = fechaHora.parse(fecha);

                        SimpleDateFormat formatex = new SimpleDateFormat("E");

                        String dia = formatex.format(convertido);

                        if (dia.equals("dom.")){
                            l_s = jsonNode.optString("empresa_horario_d") ;
                        }else{
                            l_s = jsonNode.optString("empresa_horario_ls") ;
                        }

                        separador = Pattern.quote("-");
                        resultado = l_s.split(separador);
                        part1 = resultado[0];
                        part2 = resultado[1];

                        separador_part1 = Pattern.quote(":");
                        resultado_part1 = part1.split(separador_part1);
                        resultado_part2 = part2.split(separador_part1);
                        hora_apertura = resultado_part1[0];
                        hora_cierre= resultado_part2[0];

                        hora_cierre =hora_cierre.trim();


                        separador_hora= Pattern.quote(":");
                        resultado_hora= horabusqueda.split(separador_hora);

                        int horex = Integer.parseInt(resultado_hora[0]);


                        if (horex > Integer.parseInt(hora_apertura )&& horex <= Integer.parseInt(hora_cierre)){


                            empresas = new Empresas();
                            empresas.setEmpresas_id(jsonNode.optString("empresa_id"));
                            empresas.setUsuario_id(jsonNode.optString("usuario_id"));
                            empresas.setUbigeo_id(jsonNode.optString("ubigeo_id"));
                            empresas.setEmpresas_nombre(jsonNode.optString("empresa_nombre"));
                            empresas.setEmpresas_direccion(jsonNode.optString("empresa_direccion"));
                            empresas.setEmpresas_telefono_1(jsonNode.optString("empresa_telefono_1"));
                            empresas.setEmpresas_telefono_2(jsonNode.optString("empresa_telefono_2"));
                            empresas.setEmpresas_descripcion(jsonNode.optString("empresa_descripcion"));
                            empresas.setEmpresas_horario(jsonNode.optString("empresa_horario"));
                            empresas.setEmpresas_valoracion(jsonNode.optString("empresa_valoracion"));
                            empresas.setEmpresas_foto(jsonNode.optString("empresa_foto"));
                            empresas.setEmpresas_estado(jsonNode.optString("empresa_estado"));

                            if (horex > 17){
                                empresas.setPrecio(jsonNode.optString("cancha_precioN"));
                            }else{
                                empresas.setPrecio(jsonNode.optString("cancha_precioD"));
                            }

                            String horacio = String.valueOf(horex)+":00-"+String.valueOf(horex+1)+":00";
                            empresas.setHora_reserva(horacio);


                            listaCachasHoraEmpresa.add(empresas);
                        }




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
                        reserva.setPago_id(jsonNode.optString("pago_id"));
                        reserva.setTipopago(jsonNode.optString("tipopago"));
                        reserva.setCancha_id(jsonNode.optString("cancha_id"));
                        reserva.setReserva_nombre(jsonNode.optString("nombre"));
                        reserva.setReserva_fecha(jsonNode.optString("fecha"));
                        reserva.setReserva_hora(jsonNode.optString("hora"));
                        reserva.setPago1(jsonNode.optString("pago1"));
                        reserva.setPago1_date(jsonNode.optString("pago1_date"));
                        reserva.setPago2(jsonNode.optString("pago2"));
                        reserva.setPago2_date(jsonNode.optString("pago2_date"));
                        reserva.setReserva_estado(jsonNode.optString("estado"));

                        //Llenamos los datos al Array
                        listaCanchaReserva.add(reserva);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;
    }



    String valor = "";

    class GetAndSet extends AsyncTask<String,String,String > {



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
                valor = "Tenemos problemas con la conexion a internet";
            }catch (RuntimeException e){
                valor = "Tenemos Problemas con la conexion a internet";
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
                preferences.codeAdvertencia(valor);
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
                        intent.putExtra("mostrarPantalla","inicio");


                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }
                });
            }else {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(Integer.toString(code).equalsIgnoreCase("2")){
                            preferences.codeAdvertencia("Ocurrio un error");
                        }
                        else if(Integer.toString(code).equalsIgnoreCase("3")){
                            preferences.codeAdvertencia( "Datos incorrectos");
                        }

                    }
                });


            }
        }if(funcion.equals("registrarse")){

            if(respuesta.equals("1")){


                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preferences.toasVerde("Usuario registrado");

                        context.finish();

                    }
                });

            }else if(respuesta.equals("3")){

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preferences.codeAdvertencia("DNI ya existe");

                    }
                });

            } else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preferences.codeAdvertencia("Vuelva a intentarlo");

                    }
                });

            }
        }



    }


    public Usuario getUsuario(){
        return usuario;
    }
    public ArrayList<Cancha> getListadoCanchas(){return listaCanchaEmpresa;}
    public ArrayList<Reserva> getListadoCanchasReserva(){return listaCanchaReserva;}
    public ArrayList<FragmentBuscar.GroupItem> getListadoCanchasDisponiblesOriginal(){return listaCanchasDisponiblesOriginal;}
    public ArrayList<FragmentBuscarFechas.GroupItemBusqueda> getListadoCanchasDisponiblesBusqueda(){return listaCanchasDisponiblesBusqueda;}
    public ArrayList<Saldo> getSaldo(){
        return saldo;
    }
    public ArrayList<Empresas> getListaEmpresasDistrito(){
        return listaEmpresasDistrito;
    }
    public ArrayList<Empresas> getListaCachasHoraEmpresa(){
        return listaCachasHoraEmpresa;
    }


}