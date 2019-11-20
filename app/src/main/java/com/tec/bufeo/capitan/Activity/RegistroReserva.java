package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.tec.bufeo.capitan.Activity.DetalleCanchas.cancha_id;
import static com.tec.bufeo.capitan.Activity.DetalleCanchas.cancha_nombre;
import static com.tec.bufeo.capitan.Activity.DetalleCanchas.precio_dia;
import static com.tec.bufeo.capitan.Activity.DetalleCanchas.precio_noche;
import static com.tec.bufeo.capitan.Activity.DetalleCanchas.tabLayout;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.context;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.fecha_actual;

public class
RegistroReserva extends AppCompatActivity {

    Reserva reserva;
    EditText edt_nombreReserva, edt_montoAbonadoReserva;
    TextView txt_nombreCanchaReserva,txt_fechaCanchaReserva, txt_horaCanchaReserva;
    Button btn_reservar, btn_cancelar;
    Activity activity;
    DataConnection dc;
    public static  String h_reserva ;
    String valor;
    private Date date;
    private String fecha_actual_mas_uno,fecha_actual_mas_dos, fecha_actual_title,fecha_actual_mas_uno_insert, fecha_actual_mas_dos_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_reserva);

        txt_nombreCanchaReserva =(TextView)findViewById(R.id.txt_nombreCanchaReserva) ;
        txt_fechaCanchaReserva =(TextView)findViewById(R.id.txt_fechaCanchaReserva) ;
        txt_horaCanchaReserva =(TextView)findViewById(R.id.txt_horaCanchaReserva) ;
        edt_nombreReserva = (EditText)findViewById(R.id.edt_nombreReserva);
        edt_montoAbonadoReserva = (EditText)findViewById(R.id.edt_montoAbonadoReserva);
        btn_reservar = (Button) findViewById(R.id.btn_reservar);
        btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
        // horaclick

        h_reserva = getIntent().getStringExtra("h_reserva");
        txt_nombreCanchaReserva.setText(cancha_nombre);
        txt_horaCanchaReserva.setText(h_reserva);

        String dtStart = fecha_actual;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Fecha Actual
        Calendar calendar0 = Calendar.getInstance();
        calendar0.setTime(date); // Configuramos la fecha que se recibe
        calendar0.add(Calendar.DAY_OF_YEAR, 0);  // numero de días a añadir, o restar en caso de días<0
        SimpleDateFormat format0 = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual_title = format0.format(calendar0.getTime());

        //fecha actual mas 1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        fecha_actual_mas_uno_insert = format1.format(calendar.getTime());
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual_mas_uno = format2.format(calendar.getTime());

        //Fecha actual mas 2
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date); // Configuramos la fecha que se recibe
        calendar2.add(Calendar.DAY_OF_YEAR,2);  // numero de días a añadir, o restar en caso de días<0
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
        fecha_actual_mas_dos_insert = format3.format(calendar2.getTime());
        SimpleDateFormat format4 = new SimpleDateFormat("dd-MM-yyyy");
        fecha_actual_mas_dos = format4.format(calendar2.getTime());

         if (tabLayout.getSelectedTabPosition()==0){
             txt_fechaCanchaReserva.setText(fecha_actual_title);
        }
         else if(tabLayout.getSelectedTabPosition()==1){
             txt_fechaCanchaReserva.setText(fecha_actual_mas_uno);

         }
        else if (tabLayout.getSelectedTabPosition()==2){
            txt_fechaCanchaReserva.setText(fecha_actual_mas_dos);
        }
        btn_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tabLayout.getSelectedTabPosition()==0){
                    int precio=0;
                    if (!(edt_nombreReserva.getText().toString().isEmpty()) && !(edt_montoAbonadoReserva.getText().toString().isEmpty()) ){

                        if(h_reserva.length()==11){

                            String h = h_reserva.substring(0, 2).trim();
                            if (Integer.parseInt(h) >= 18) {
                                precio = Integer.parseInt(precio_noche);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {

                                    reserva = new Reserva();
                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);

                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);
                                }

                            } else {
                                precio = Integer.parseInt(precio_dia);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();

                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);
                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);

                                }
                            }
                        }
                        else {
                            String h = h_reserva.substring(0, 1).toString().trim();
                            if (Integer.parseInt(h) >= 18) {
                                precio = Integer.parseInt(precio_noche);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();
                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);
                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);
                                }

                            } else {
                                precio = Integer.parseInt(precio_dia);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();

                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);

                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());

                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);

                                }

                            }
                        }

                    } else {
                        Toast.makeText(context, "Llene los campos", Toast.LENGTH_LONG).show();
                    }
                }
                else if (tabLayout.getSelectedTabPosition()==1){

                    int precio=0;
                    if (!(edt_nombreReserva.getText().toString().isEmpty()) && !(edt_montoAbonadoReserva.getText().toString().isEmpty()) ){

                        if(h_reserva.length()==11){

                            String h = h_reserva.substring(0, 2).toString().trim();
                            if (Integer.parseInt(h) >= 18) {
                                precio = Integer.parseInt(precio_noche);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {

                                    reserva = new Reserva();
                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);

                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_uno_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);
                                }

                            } else {
                                precio = Integer.parseInt(precio_dia);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();

                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);
                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_uno_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);

                                }
                            }
                        }
                        else {
                            String h = h_reserva.substring(0, 1).toString().trim();
                            if (Integer.parseInt(h) >= 18) {
                                precio = Integer.parseInt(precio_noche);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();
                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);
                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_uno_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);
                                }

                            } else {
                                precio = Integer.parseInt(precio_dia);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();

                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);

                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_uno_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());

                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);

                                }

                            }
                        }

                    } else {
                        Toast.makeText(context, "Llene los campos", Toast.LENGTH_LONG).show();
                    }
                }

                else if (tabLayout.getSelectedTabPosition()==2){

                    int precio=0;
                    if (!(edt_nombreReserva.getText().toString().isEmpty()) && !(edt_montoAbonadoReserva.getText().toString().isEmpty()) ){

                        if(h_reserva.length()==11){

                            String h = h_reserva.substring(0, 2).toString().trim();
                            if (Integer.parseInt(h) >= 18) {
                                precio = Integer.parseInt(precio_noche);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {

                                    reserva = new Reserva();
                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);

                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_dos_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);
                                }

                            } else {
                                precio = Integer.parseInt(precio_dia);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();

                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);
                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_dos_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);

                                }
                            }
                        }
                        else {
                            String h = h_reserva.substring(0, 1).toString().trim();
                            if (Integer.parseInt(h) >= 18) {
                                precio = Integer.parseInt(precio_noche);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();
                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);
                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_dos_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());
                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);
                                }

                            } else {
                                precio = Integer.parseInt(precio_dia);
                                if (Integer.parseInt(edt_montoAbonadoReserva.getText().toString()) > precio) {
                                    Toast.makeText(context, "El monto abonado supera el precio de la cancha", Toast.LENGTH_LONG).show();
                                } else {
                                    reserva = new Reserva();

                                    //Reserva.setUsuario_id(MenuPrincipal.usuario_id);

                                    reserva.setCancha_id(cancha_id);
                                    reserva.setReserva_nombre(edt_nombreReserva.getText().toString());
                                    reserva.setReserva_fecha(fecha_actual_mas_dos_insert);
                                    reserva.setReserva_hora(h_reserva);
                                    reserva.setReserva_costo(edt_montoAbonadoReserva.getText().toString());

                                    dc = new DataConnection(RegistroReserva.this, "registrarReserva", reserva, true);

                                }

                            }
                        }

                    } else {
                        Toast.makeText(context, "Llene los campos", Toast.LENGTH_LONG).show();
                    }
                }


            }
        }
        );
    }
}
