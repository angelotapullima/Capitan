package com.tec.bufeo.capitan.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tec.bufeo.capitan.Adapters.AdapListadoCanchaReserva;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static com.tec.bufeo.capitan.Activity.DetalleCanchas.precio_dia;
import static com.tec.bufeo.capitan.Activity.DetalleCanchas.precio_noche;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.cancha_id;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.fecha_actual;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.hora_actual;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.horario;


public class FragmentPasMañana extends Fragment  implements  SwipeRefreshLayout.OnRefreshListener {
    static AdapListadoCanchaReserva adapListadoCanchaReserva;
    public static ArrayList<Reserva> arrayreservados;
    public static  ArrayList<Reserva> arrayreservados_WS;
    static DataConnection dc;
    static RecyclerView rcv_reservados;
    static ProgressBar progressBar;
    static CardView cdv_mensaje;
    static Activity activity;
    static Context context;
    String fecha;
    public static FragmentManager fragmentManager;
    public static  String fecha_actual_mas_dos_insert;
    public static Date date;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public FragmentPasMañana() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pas_manana, container, false);
        fragmentManager=getFragmentManager();
        swipeRefreshLayout =  view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        String dtStart = fecha_actual;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //fecha actual mas 1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 2);  // numero de días a añadir, o restar en caso de días<0
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        fecha_actual_mas_dos_insert = format1.format(calendar.getTime());



        cancha_id.toString();
        hora_actual.toString();

        precio_dia.toString();
        precio_noche.toString();


        horario.toString();
        String separador = Pattern.quote("-");
        String[] resultado = horario.split(separador);
        String part1 = resultado[0];
        String part2 = resultado[1];

        String h_apertura = part1.substring(0, 2).toString().trim();
        String h_cierre = part2.substring(0, 3).toString().trim();

        int hapertura, hcierre;

        hapertura = Integer.parseInt(h_apertura);
        hcierre = Integer.parseInt(h_cierre);
        Toast.makeText(getContext(), "parte 1 : " + hapertura + "  parte 2  :" + hcierre, Toast.LENGTH_LONG).show();
        //Toast.makeText(getContext(),"parte 1 : " +h_apertura.toString()+ "  parte 2  :"+h_cierre.toString(),Toast.LENGTH_LONG).show();
        rcv_reservados = (RecyclerView) view.findViewById(R.id.rcv_reservas);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        cdv_mensaje = (CardView) view.findViewById(R.id.cdv_mensaje);
        fecha = fecha_actual_mas_dos_insert.toString();

        progressBar.setVisibility(View.INVISIBLE);

        cdv_mensaje.setVisibility(View.INVISIBLE);

        activity = getActivity();
        context = getContext();


        dc = new DataConnection(getActivity(), "listarcanchasReservas", new Reserva(cancha_id), fecha_actual_mas_dos_insert, false);
        new FragmentPasMañana.GetReservados().execute();
        //getActivity().setTitle("Mi Carrito");

        return view;
    }
    public static void actualizarReserva(){

        dc = new DataConnection(activity, "listarcanchasReservas", new Reserva(cancha_id), fecha_actual_mas_dos_insert, false);
        new FragmentPasMañana.GetReservados().execute();

    }

    @Override
    public void onRefresh() {
        dc = new DataConnection(getActivity(), "listarcanchasReservas", new Reserva(cancha_id), fecha_actual_mas_dos_insert, false);
        new FragmentPasMañana.GetReservados().execute();
    }

    public static class GetReservados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayreservados_WS = dc.getListadoCanchasReserva();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Toast.makeText(getContext(), "size "+arrayreservados_WS.get(0).getReserva_costo(), Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            cancha_id.toString();
            hora_actual.toString();
            fecha_actual_mas_dos_insert.toString();


            horario.toString();
            String separador = Pattern.quote("-");
            String[] resultado = horario.split(separador);
            String part1 = resultado[0];
            String part2 = resultado[1];

            String h_apertura = part1.substring(0, 2).toString().trim();
            String h_cierre = part2.substring(0, 3).toString().trim();

            int hapertura, hcierre;

            hapertura = Integer.parseInt(h_apertura);
            hcierre = Integer.parseInt(h_cierre);
            for(int j =0; j<arrayreservados_WS.size();j++) {
                arrayreservados_WS.get(j).getReserva_hora();
                arrayreservados_WS.get(j).getReserva_estado();
                // Toast.makeText(context," gg"+i,Toast.LENGTH_LONG);
            }
            //String estado =arrayreservados_WS.get(0).getReserva_estado();

            if(hcierre ==0){
                hcierre =24;
            }
            if(hcierre ==1){
                hcierre =25;
            }
            String  horaFinal= "";

            //-----------------------------------
            arrayreservados = new ArrayList<Reserva>();
            for (int i = hapertura; i<hcierre;i++){
                int hf =i+1;
                int ii=i;
                if(hf>12){
                    hf= hf-12;
                    if(ii>12){
                        ii=ii-12 ;
                    }
                    horaFinal =  ii+":00 - "+hf+":00 pm";
                }
                else{
                    horaFinal =  i+":00 - "+hf+":00 am";
                }
                int k = i+1;
                String  h_reserva =""+i+":00-"+k+":00";
                if(i>=18){

                    //if(){}
                    boolean reservado_pm = false;
                    for(Reserva obj:arrayreservados_WS){
                        String hora =obj.getReserva_hora();
                        if(hora.length() ==11) {

                            if (i == Integer.parseInt(hora.substring(0, 2))) {
                                reservado_pm = true;
                                arrayreservados.add(new Reserva("", cancha_id, obj.getReserva_nombre(), fecha_actual_mas_dos_insert, horaFinal, obj.getReserva_costo(), Integer.parseInt(precio_noche) == Integer.parseInt(obj.getReserva_costo()) ? "rojo" : "anaranjado", "Reservado", precio_noche, h_reserva));
                            }
                        }
                        else{
                            if (i == Integer.parseInt(hora.substring(0, 1))) {
                                reservado_pm = true;
                                arrayreservados.add(new Reserva("", cancha_id, obj.getReserva_nombre(), fecha_actual_mas_dos_insert, horaFinal, obj.getReserva_costo(), Integer.parseInt(precio_noche) == Integer.parseInt(obj.getReserva_costo()) ? "rojo" : "anaranjado", "Reservado", precio_noche, h_reserva));
                            }
                        }

                    }

                    if(!reservado_pm){
                        arrayreservados.add(new Reserva("",cancha_id,"-------",fecha_actual_mas_dos_insert,horaFinal,"0","verde","Disponible",precio_noche, h_reserva));

                    }


                }
                else {
                    // arrayreservados.add(new Reserva("", cancha_id, "Miguel", fecha_actual_mas_uno_insert, horaFinal, precio_dia,"1"));

                    boolean reservado_am = false;
                    for(Reserva obj:arrayreservados_WS){
                        String hora =obj.getReserva_hora();
                        if(hora.length() ==11) {

                            if (i == Integer.parseInt(hora.substring(0, 2))) {
                                Toast.makeText(context,"c2",Toast.LENGTH_LONG);
                                reservado_am = true;
                                // arrayreservados.add(new Reserva("",cancha_id,obj.getReserva_nombre(),fecha_actual_mas_uno_insert,horaFinal,precio_dia,"rojo","Reservado"));
                                arrayreservados.add(new Reserva("", cancha_id, obj.getReserva_nombre(), fecha_actual_mas_dos_insert, horaFinal, obj.getReserva_costo(), Integer.parseInt(precio_dia) == Integer.parseInt(obj.getReserva_costo()) ? "rojo" : "anaranjado", "Reservado", precio_dia, h_reserva));
                            }
                        }
                        else{

                            if (i == Integer.parseInt(hora.substring(0, 1))) {
                                reservado_am = true;
                                Toast.makeText(context,"c1",Toast.LENGTH_LONG);
                                // arrayreservados.add(new Reserva("",cancha_id,obj.getReserva_nombre(),fecha_actual_mas_uno_insert,horaFinal,precio_dia,"rojo","Reservado"));
                                arrayreservados.add(new Reserva("", cancha_id, obj.getReserva_nombre(), fecha_actual_mas_dos_insert, horaFinal, obj.getReserva_costo(), Integer.parseInt(precio_dia) == Integer.parseInt(obj.getReserva_costo()) ? "rojo" : "anaranjado", "Reservado", precio_dia, h_reserva));
                            }


                        }
                    }
                    if(!reservado_am) {
                        arrayreservados.add(new Reserva("", cancha_id, "-------", fecha_actual_mas_dos_insert, horaFinal, "0", "verde", "Disponible", precio_dia, h_reserva));
                    }
                }

            }

            adapListadoCanchaReserva = new AdapListadoCanchaReserva(context, arrayreservados, R.layout.rcv_item_card_canchas_horarios, new AdapListadoCanchaReserva.OnItemClickListener() {

                @Override
                public void onItemClick(Reserva reserva, int position) {
                    Toast.makeText(context,"hora :"+reserva.getReserva_hora_cancha(), Toast.LENGTH_LONG).show();
                }
            });

            GridLayoutManager linearLayoutManager2 = new GridLayoutManager(activity, 1);
            //linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcv_reservados.setLayoutManager(linearLayoutManager2);
            rcv_reservados.setAdapter(adapListadoCanchaReserva);

        }
    }


}
