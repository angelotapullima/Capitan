package com.tec.bufeo.capitan.Activity.Registro_Torneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.R;

public class RegistroTorneoFinalizado extends AppCompatActivity implements View.OnClickListener {

    Button btn_finalizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_torneo_finalizado);
        btn_finalizar= findViewById(R.id.btn_finalizar);

        btn_finalizar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btn_finalizar)){
            Intent i =  new Intent(RegistroTorneoFinalizado.this, MenuPrincipal.class);
            startActivity(i);
        }
    }
}
