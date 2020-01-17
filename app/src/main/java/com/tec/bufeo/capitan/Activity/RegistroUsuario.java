package com.tec.bufeo.capitan.Activity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.bufeo.capitan.Modelo.MDistrito;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegistroUsuario extends AppCompatActivity  implements View.OnClickListener , DatePickerDialog.OnDateSetListener{


    Spinner spn_sexo, spn_ciudad, spn_distrito, spn_habilidad,spn_posicion;
    EditText edt_nombreUsuario,edt_dniUsuario,edt_emailUsuario,edt_UsuarioUsuario,edt_clave,edt_telefonoUsuario,
            edt_address,edt_confirmarClave, numFav,edt_surnameUsuario;
    Button btn_registrar;
    TextView btn_fechaNacimientoUsuario;
    ImageView back;
    public Usuario usuario;
    DataConnection dc,dc1,dc2;

    ArrayList<String> arrayCiudad;
    ArrayList<String> arraynombresDistrito;
    ArrayList<MDistrito> arrayDistritoxCiudad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);



        edt_nombreUsuario =  findViewById(R.id.edt_nombreUsuario);
        edt_telefonoUsuario =  findViewById(R.id.edt_telefonoUsuario);
        edt_emailUsuario = findViewById(R.id.edt_emailUsuario);
        edt_dniUsuario =  findViewById(R.id.edt_dniUsuario);
        edt_UsuarioUsuario = findViewById(R.id.edt_UsuarioUsuario);
        btn_fechaNacimientoUsuario =  findViewById(R.id.btn_fechaNacimientoUsuario);
        spn_sexo = findViewById(R.id.spn_sexo);
        spn_habilidad =  findViewById(R.id.spn_habilidad);
        spn_posicion = findViewById(R.id.spn_posicion);
        spn_ciudad = findViewById(R.id.spn_ciudad);
        spn_distrito =  findViewById(R.id.spn_distrito);
        edt_clave = findViewById(R.id.edt_clave);
        edt_address = findViewById(R.id.edt_address);
        edt_confirmarClave =  findViewById(R.id.edt_confirmarClave);
        numFav = findViewById(R.id.edt_numFav);
        edt_surnameUsuario = findViewById(R.id.edt_surnameUsuario);
        btn_registrar =  findViewById(R.id.btn_registrar);
        back =  findViewById(R.id.back);
        btn_registrar.setOnClickListener(this);
        back.setOnClickListener(this);
        btn_fechaNacimientoUsuario.setOnClickListener(this);

        dc1 = new DataConnection(RegistroUsuario.this,"listarCiudades",false);
        new GetCiudades().execute();


        spn_ciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    spn_distrito.setEnabled(false);
                    spn_distrito.setSelection(0);
                }else {
                    spn_distrito.setEnabled(true);
                    dc2 = new DataConnection(RegistroUsuario.this,"listarDistritoxCiudades",arrayCiudad.get(spn_ciudad.getSelectedItemPosition()),false);
                    new GetDistritoxCiudad().execute();                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class GetCiudades extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayCiudad =  new ArrayList<>();
            arrayCiudad = dc1.getListaCiudades();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(getApplicationContext(),"C: "+arrayCiudad.size(),Toast.LENGTH_LONG).show();
            arrayCiudad.add(0,"Seleccione");

            ArrayAdapter<String > adapciudades = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arrayCiudad);
            adapciudades.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_ciudad.setAdapter(adapciudades);



        }
    }

    public class GetDistritoxCiudad extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayDistritoxCiudad = new ArrayList<>();
            arrayDistritoxCiudad = dc2.getListaDistritoxCiudad();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(getApplicationContext(),"S:" +arrayDistritoxCiudad.size(),Toast.LENGTH_SHORT).show();
            arraynombresDistrito = new ArrayList<>();
            //Secciones
            for (MDistrito obj :arrayDistritoxCiudad){
                arraynombresDistrito.add(obj.getDistrito());
            }

            arraynombresDistrito.add(0,"Seleccione");
            ArrayAdapter<String > adapsecciones = new ArrayAdapter<String>(getApplicationContext(),R.layout.spiner_item,arraynombresDistrito);
            adapsecciones.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_distrito.setAdapter(adapsecciones);

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year + "-" + (monthOfYear+1) + "-" +dayOfMonth );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        btn_fechaNacimientoUsuario.setText(outDate);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_registrar:

                if (!(edt_nombreUsuario.getText().toString().isEmpty()) && !(edt_UsuarioUsuario.getText().toString().isEmpty()) && !(edt_emailUsuario.getText().toString().isEmpty())&& !(edt_clave.getText().toString().isEmpty()) && !(edt_confirmarClave.getText().toString().isEmpty()) &&  !(spn_distrito.getSelectedItem().toString().equals("Seleccione")) ) {
                        if (edt_clave.getText().toString().equals(edt_confirmarClave.getText().toString())) {
                            usuario = new Usuario();

                            usuario.setPerson_name(edt_nombreUsuario.getText().toString());
                            usuario.setPerson_surname(edt_surnameUsuario.getText().toString());
                            usuario.setPerson_dni(edt_dniUsuario.getText().toString());
                            usuario.setPerson_birth(btn_fechaNacimientoUsuario.getText().toString());
                            usuario.setPerson_number_phone(edt_telefonoUsuario.getText().toString());
                            usuario.setPerson_genre(spn_sexo.getSelectedItem().toString());
                            usuario.setPerson_address(edt_address.getText().toString());
                            usuario.setUser_nickname(edt_UsuarioUsuario.getText().toString());
                            usuario.setUser_password(edt_clave.getText().toString());
                            usuario.setUser_email(edt_emailUsuario.getText().toString());
                            usuario.setUser_posicion(spn_posicion.getSelectedItem().toString());
                            usuario.setUser_habilidad(spn_habilidad.getSelectedItem().toString());
                            usuario.setUsuario_numFavorito(numFav.getText().toString());
                            usuario.setRol_id("1");
                            usuario.setUsuario_foto("perfil.png");
                            usuario.setUbigeo_id(arrayDistritoxCiudad.get(spn_distrito.getSelectedItemPosition()-1).getUbigeo_id());

                            dc = new DataConnection(RegistroUsuario.this, "registrarse", usuario, true);


                        } else {
                            Toast.makeText(getApplicationContext(), "Las claves no coinciden", Toast.LENGTH_LONG).show();}

                    }else {
                        Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();}
                    break;

            case R.id.btn_fechaNacimientoUsuario:
                new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                break;
            case R.id.back:
                finish();
                break;
                }
        }
    }