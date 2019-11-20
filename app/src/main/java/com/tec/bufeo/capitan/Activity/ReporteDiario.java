package com.tec.bufeo.capitan.Activity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.bufeo.capitan.Adapters.AdaptadorListadoReportesDiarios;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.tec.bufeo.capitan.Activity.DetalleNegocio.fecha_actual;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.id_empresa;

public class ReporteDiario extends AppCompatActivity  implements  DatePickerDialog.OnDateSetListener, View.OnClickListener{

    Button btn_fechaReporte,btn_fecha2Reporte, btn_buscar;
    TextView txt_totalRecaudado;
    RecyclerView rcv_reportes_por_dia;
    CardView cdv_mensaje;
    ProgressBar progressbar;
    DataConnection dc,dc1;
    ArrayList<Reserva> arrayreserva;
    AdaptadorListadoReportesDiarios adaptadorListadoReportesDiarios;
    int precioTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);
        precioTotal = 0;

        btn_fechaReporte = findViewById(R.id.btn_fechaReporte);
        btn_fecha2Reporte = findViewById(R.id.btn_fecha2Reporte);
        btn_buscar = findViewById(R.id.btn_buscar);
        txt_totalRecaudado = findViewById(R.id.txt_totalRecaudado);
        rcv_reportes_por_dia = findViewById(R.id.rcv_reportes_por_dia);
        cdv_mensaje = findViewById(R.id.cdv_mensaje);
        progressbar = findViewById(R.id.progressbar);
        btn_fechaReporte.setText(fecha_actual);
        btn_fechaReporte.setOnClickListener(this);
        btn_buscar.setOnClickListener(this);
        dc = new DataConnection(this,"listarEstadisticas",new Reserva(id_empresa,fecha_actual,fecha_actual ),false);
        new GetReporte().execute();
       // txt_totalRecaudado.setText("0");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fechaReporte:
                new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                break;
            case R.id.btn_buscar:
                if(!btn_fechaReporte.getText().toString().isEmpty()){
                    precioTotal =0;
                    dc = new DataConnection(this,"listarEstadisticasDiarias",new Reserva(id_empresa,btn_fechaReporte.getText().toString() ),false);
                    new GetReporte().execute();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Debe Seleccionar una fecha",Toast.LENGTH_SHORT).show();
                }

        }
    }

    public class GetReporte extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayreserva = dc.getLitadoEstadisticasDiarias();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressbar.setVisibility(ProgressBar.INVISIBLE);

            //Toast.makeText(getActivity(),"Z "+arrayempresas.size(),Toast.LENGTH_SHORT).show();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            rcv_reportes_por_dia.setLayoutManager(linearLayoutManager);

            adaptadorListadoReportesDiarios = new AdaptadorListadoReportesDiarios(getApplicationContext(), arrayreserva, R.layout.rcv_item_list_reportes_diarios_tabla, new AdaptadorListadoReportesDiarios.OnItemClickListener() {
                @Override
                public void onItemClick(Reserva reserva, final int position) {

                    //Toast.makeText(getActivity(),"ID "+empresas.getEmpresas_id(), Toast.LENGTH_SHORT).show();

                    /*Intent intent = new Intent(getContext(), DetalleNegocio.class);
                    intent.putExtra("id_empresa",empresas.getEmpresas_id());
                    startActivity(intent);*/

                }
            });
            rcv_reportes_por_dia.setAdapter(adaptadorListadoReportesDiarios);



            for(Reserva reserva : arrayreserva) {
                precioTotal += Integer.parseInt(reserva.getReserva_costo());
            }

          //  Toast.makeText(context,"total"+precioTotal,Toast.LENGTH_SHORT).show();

            txt_totalRecaudado.setText("Total Recaudado :  S/ "+precioTotal);



            if (arrayreserva.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year +"-" + (monthOfYear+1) + "-" +dayOfMonth );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        btn_fechaReporte.setText(outDate);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
