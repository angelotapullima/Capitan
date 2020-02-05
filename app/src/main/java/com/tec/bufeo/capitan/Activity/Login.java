package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
                    Toast.makeText(getApplicationContext(), "Lllene los campos", Toast.LENGTH_LONG).show();

                }

                /*String sintomas = "fiebre/diarrea/escalofrio/fiebre/diarrea/escalofrio";
                String separador = Pattern.quote("/");
                String[] sintomaslista = sintomas.split(separador);

                Toast.makeText(getApplicationContext(),""+sintomaslista.length,Toast.LENGTH_SHORT).show();*/
            }
        });
    }


}
