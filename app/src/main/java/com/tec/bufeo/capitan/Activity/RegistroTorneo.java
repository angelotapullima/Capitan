package com.tec.bufeo.capitan.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.horaDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tec.bufeo.capitan.Activity.MenuPrincipal.usuario_id;

public class RegistroTorneo extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

   EditText edt_nombreTorneo, edt_descripcionTorneo, edt_lugarTorneo;
   Button btn_fechaTorneo, btn_horaTorneo ,btn_crearTorneo;
   Torneo torneo;
   DataConnection dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_torneo);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);*/
        edt_nombreTorneo = findViewById(R.id.edt_nombreTorneo);
        edt_descripcionTorneo = findViewById(R.id.edt_descripcionTorneo);
        edt_lugarTorneo = findViewById(R.id.edt_lugarTorneo);
        btn_fechaTorneo = findViewById(R.id.btn_fechaTorneo);
        btn_horaTorneo = findViewById(R.id.btn_horaTorneo);
        btn_crearTorneo = findViewById(R.id.btn_crearTorneo);
        btn_fechaTorneo.setOnClickListener(this);
        btn_horaTorneo.setOnClickListener(this);
        btn_crearTorneo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fechaTorneo:
                new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                break;
            case R.id.btn_horaTorneo:
                new horaDialog().show(getSupportFragmentManager(), "TimePicker");
                break;

            case R.id.btn_crearTorneo:

                if (!(edt_nombreTorneo.getText().toString().isEmpty()) && !(edt_descripcionTorneo.getText().toString().isEmpty()) && !(btn_fechaTorneo.getText().toString().isEmpty())&& !(btn_horaTorneo.getText().toString().isEmpty()) && !(edt_lugarTorneo.getText().toString().isEmpty())) {
                    torneo = new Torneo();
                    torneo.setUsuario_id(usuario_id);
                    torneo.setTorneo_nombre(edt_nombreTorneo.getText().toString());
                    torneo.setTorneo_descripcion(edt_descripcionTorneo.getText().toString());
                    torneo.setTorneo_descripcion(edt_descripcionTorneo.getText().toString());
                    torneo.setTorneo_fecha(btn_fechaTorneo.getText().toString());
                    torneo.setTorneo_hora(btn_horaTorneo.getText().toString());
                    torneo.setTorneo_lugar(edt_lugarTorneo.getText().toString());

                    dc = new DataConnection(RegistroTorneo.this, "registrarTorneoPorUssuario", torneo, true);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();}
                break;
        }
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year+ "-" + (month+1) + "-" +dayOfMonth  );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        btn_fechaTorneo.setText(outDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hora, int minuto) {
        btn_horaTorneo.setText(String.format("%02d:%02d", hora, minuto));
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
