package com.tec.bufeo.capitan.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.bufeo.capitan.Activity.RegistroUsuario.RegistroPaso1;
import com.tec.bufeo.capitan.Activity.RegistroUsuario.RegistroUsuario;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText edt_usuario,edt_password;
    Button btn_login;
    TextView txt_resgistrate;

    Usuario usuario;
    DataConnection dc;
    public ArrayList<String> arrayHoras;
    public static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_usuario =  findViewById(R.id.edt_usuario);
        btn_login =  findViewById(R.id.btn_login);
        edt_password = findViewById(R.id.edt_password);
        txt_resgistrate = findViewById(R.id.txt_resgistrate);


        if((ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(Login.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(Login.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }


        txt_resgistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroPaso1.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !(edt_usuario.getText().toString().isEmpty()) && !(edt_password.getText().toString().isEmpty())){

                    usuario = new Usuario();
                    usuario.setUser_nickname(edt_usuario.getText().toString());
                    usuario.setUser_password(edt_password.getText().toString());

                    dc = new DataConnection(Login.this,"loginUsuario",usuario,true);



                }else {
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();

                }


            }
        });
    }


}
