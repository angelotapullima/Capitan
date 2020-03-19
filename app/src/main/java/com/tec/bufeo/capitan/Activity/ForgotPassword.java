package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    MaterialButton reestablecer;
    ImageView atras;
    EditText edt_emailForgot;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        preferences= new Preferences(this);
        reestablecer= findViewById(R.id.reestablecer);
        atras= findViewById(R.id.atras);
        edt_emailForgot= findViewById(R.id.edt_emailForgot);


        reestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Patrón para validar el email
                Pattern pattern = Pattern
                        .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(edt_emailForgot.getText().toString());

                if (mather.find() == true) {
                    dialogExito();
                }else{
                    preferences.codeAdvertencia("El formato de email es inválido");
                }


            }
        });
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







    }

    /*Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_cargando.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cargando.setCancelable(true);
        dialog_cargando.setContentView(R.layout.dialogo_cargando_logobufeo);
        LinearLayout back = dialog_cargando.findViewById(R.id.back);
        LinearLayout layout = dialog_cargando.findViewById(R.id.layout);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cargando.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog_cargando.show();

    }*/


    Dialog dialog_exito;
    public void dialogExito(){

        dialog_exito= new Dialog(this, android.R.style.Theme_Translucent);
        dialog_exito.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_exito.setCancelable(true);
        dialog_exito.setContentView(R.layout.dialogo_forgot_pass);
        MaterialButton cont_forgot = dialog_exito.findViewById(R.id.cont_forgot);
        LinearLayout back = dialog_exito.findViewById(R.id.back);
        LinearLayout layout = dialog_exito.findViewById(R.id.layout);



        cont_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_exito.dismiss();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog_exito.show();

    }
}
