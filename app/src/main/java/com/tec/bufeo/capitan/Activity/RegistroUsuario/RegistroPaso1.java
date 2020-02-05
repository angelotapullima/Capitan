package com.tec.bufeo.capitan.Activity.RegistroUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;


public class RegistroPaso1 extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btn_continuar,finalizar_Rusuario;
    EditText edt_nombreUsuario,edt_apellidoUsuario,edt_num_fav,edt_emailUsuario,edt_telefono;
    Spinner spn_posicion,spn_habilidad,spn_sexo;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paso1);

        preferences= new Preferences(this);
        btn_continuar = findViewById(R.id.btn_continuar);
        finalizar_Rusuario = findViewById(R.id.finalizar_Rusuario);
        edt_nombreUsuario = findViewById(R.id.edt_nombreUsuario);
        edt_apellidoUsuario = findViewById(R.id.edt_apellidoUsuario);
        spn_posicion = findViewById(R.id.spn_posicion);
        spn_habilidad = findViewById(R.id.spn_habilidad);
        spn_sexo = findViewById(R.id.spn_sexo);
        edt_num_fav = findViewById(R.id.edt_num_fav);
        edt_emailUsuario = findViewById(R.id.edt_emailUsuario);
        edt_telefono = findViewById(R.id.edt_telefono);


        btn_continuar.setOnClickListener(this);
        finalizar_Rusuario.setOnClickListener(this);

    }
    String nombre,apellido,posicion,habilidad,sexo,num_fav,email,telefono;
    @Override
    public void onClick(View v) {
        if (v.equals(btn_continuar)){
            nombre = edt_nombreUsuario.getText().toString();
            apellido = edt_apellidoUsuario.getText().toString();
            posicion=spn_posicion.getSelectedItem().toString();
            habilidad=spn_habilidad.getSelectedItem().toString();
            sexo = spn_sexo.getSelectedItem().toString();
            num_fav= edt_num_fav.getText().toString();
            email =edt_emailUsuario.getText().toString();
            telefono =  edt_telefono.getText().toString();



            if (nombre.isEmpty()){
                preferences.codeAdvertencia("El campo Nombre no debe estar vacio");
            }else if (apellido.isEmpty()){
                preferences.codeAdvertencia("El campo Apellido no debe estar vacio");
            }else if (posicion.equals("Seleccionar")){
                preferences.codeAdvertencia("Debe Seleccionar una posición");
            }else if (habilidad.equals("Seleccionar")){
                preferences.codeAdvertencia("Debe Seleccionar una habilidad");
            }else if (sexo.equals("Seleccionar")){
                preferences.codeAdvertencia("Debe Seleccionar el Sexo");
            }else if (num_fav.isEmpty()){
                preferences.codeAdvertencia("El campo Número Favorito no debe estar vacio");
            }else if (email.isEmpty()){
                preferences.codeAdvertencia("El campo Email no debe estar vacio");
            }else if (telefono.isEmpty()){
                preferences.codeAdvertencia("El campo Teléfono no debe estar vacio");
            }else{

                if (spn_sexo.getSelectedItem().toString().equals("Masculino")){
                    sexo="M";
                }else{
                    sexo="F";
                }
                Intent i = new Intent(RegistroPaso1.this,RegistroUsuario.class);
                i.putExtra("nombre",nombre);
                i.putExtra("apellido",apellido);
                i.putExtra("posicion",posicion);
                i.putExtra("habilidad",habilidad);
                i.putExtra("sexo",sexo);
                i.putExtra("num_fav",num_fav);
                i.putExtra("email",email);
                i.putExtra("telefono",telefono);
                startActivity(i);
            }

        }else if ((v.equals(finalizar_Rusuario))){
            finish();
        }
    }
}
