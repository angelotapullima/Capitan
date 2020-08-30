package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.DetalleMovimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;
import com.tec.bufeo.capitan.Activity.MisMovimientos.ViewModels.MovimientosViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class DetalleMovimientosActivity extends AppCompatActivity {

    TextView fechaOperacion,cliente,tipo,concepto,monto,cantidadSolo,totalmonto,SubTotal,Coimision,total,nOperacion;
    MovimientosViewModel movimientosViewModel;
    Preferences preferences;
    LinearLayout loperacion;

    String nro_operacion,conceptollegada,tipo_pago,montollegada,comision,fecha,ind,clientellegada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_movimientos);

        movimientosViewModel = ViewModelProviders.of(this).get(MovimientosViewModel.class);
        preferences= new Preferences(this);

        nro_operacion = getIntent().getExtras().getString("nro_operacion");
        conceptollegada = getIntent().getExtras().getString("concepto");
        tipo_pago = getIntent().getExtras().getString("tipo_pago");
        montollegada = getIntent().getExtras().getString("monto");
        comision = getIntent().getExtras().getString("comision");
        fecha = getIntent().getExtras().getString("fecha");
        ind = getIntent().getExtras().getString("ind");
        clientellegada = getIntent().getExtras().getString("cliente");


        initViews();



        nOperacion.setText(nro_operacion);
        fechaOperacion.setText(fecha);
        cliente.setText(clientellegada);
        tipo.setText(tipo_pago);
        concepto.setText(conceptollegada);
        monto.setText(montollegada);
        Coimision.setText(comision);
        totalmonto.setText(montollegada);
        SubTotal.setText(montollegada);

        total.setText(String.valueOf(Float.parseFloat(SubTotal.getText().toString()) + Float.parseFloat(comision)));

        if (nro_operacion.equals("") || nro_operacion.isEmpty()){
            loperacion.setVisibility(View.GONE);
        }
        showToolbar("",true);

    }

    private void initViews() {
        fechaOperacion = findViewById(R.id.fechaOperacion);
        cliente = findViewById(R.id.cliente);
        tipo = findViewById(R.id.tipo);
        concepto = findViewById(R.id.concepto);
        monto = findViewById(R.id.monto);
        cantidadSolo = findViewById(R.id.cantidadSolo);
        totalmonto = findViewById(R.id.totalmonto);
        SubTotal = findViewById(R.id.SubTotal);
        Coimision = findViewById(R.id.Coimision);
        total = findViewById(R.id.total);
        nOperacion = findViewById(R.id.nOperacion);
        loperacion = findViewById(R.id.loperacion);
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);      //asociamos el toolbar con el archivo xml
        toolbar.setTitleTextColor(Color.WHITE);                     //el titulo color blanco
        toolbar.setSubtitleTextColor(Color.GREEN);                  //el subtitulo color blanco
        setSupportActionBar(toolbar);                               //pasamos los parametros anteriores a la clase Actionbar que controla el toolbar

        getSupportActionBar().setTitle(tittle);                     //asiganmos el titulo que llega
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);//y habilitamos la flacha hacia atras

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();                        //definimos que al dar click a la flecha, nos lleva a la pantalla anterior
        return false;
    }
}
