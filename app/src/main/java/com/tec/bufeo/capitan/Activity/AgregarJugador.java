package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.tec.bufeo.capitan.Adapters.AdaptadorListadoUsuarioGeneral;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

import static com.tec.bufeo.capitan.Activity.DetalleEquipo.id_equipo;

public class AgregarJugador extends AppCompatActivity  implements SearchView.OnQueryTextListener{

    static AdaptadorListadoUsuarioGeneral adaptadorListadoUsuarioGeneral;
    public static ArrayList<Usuario> arrayusuario;
    DataConnection dc;
    DataConnection dc1;
    static ListView list_usuarioGeneral;
    static ProgressBar progressBar;
    static CardView cdv_mensaje;
    static Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_jugador);

        list_usuarioGeneral = (ListView)findViewById(R.id.list_usuarioGeneral);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        cdv_mensaje = (CardView) findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);

        dc = new DataConnection(activity,"listarUsuarioGeneral",new Usuario(id_equipo),false);
        new AgregarJugador.GetUsuarios().execute();


    }

    public class GetUsuarios extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayusuario = dc.getListadoUsuarioGeneral();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(ProgressBar.INVISIBLE);


           // Toast.makeText(getApplicationContext(),"Z "+arrayusuario.get(0).getUsuario_id(),Toast.LENGTH_SHORT).show();

            adaptadorListadoUsuarioGeneral =  new AdaptadorListadoUsuarioGeneral(AgregarJugador.this,arrayusuario,R.layout.rcv_item_list_usuarios_general);
            list_usuarioGeneral.setAdapter(adaptadorListadoUsuarioGeneral);



            if (arrayusuario.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                list_usuarioGeneral.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                list_usuarioGeneral.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        MenuInflater inflater = mode.getMenuInflater();
                        inflater.inflate(R.menu.menu_btn_check, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                       switch (item.getItemId()) {
                            case R.id.action_btn_check:
                                //Toast.makeText(getApplicationContext(),"id_equipo"+id_equipo,Toast.LENGTH_LONG);

                                for (int nC =0 ; nC<adaptadorListadoUsuarioGeneral.getCount(); nC++) {
                                    if (list_usuarioGeneral.isItemChecked(nC)) {
                                       // mDbAdapter.deleteReminderById(getIdFromPosition(nC));

                                        dc1 = new DataConnection(AgregarJugador.this, "registrarUsuarioEquipo",new Usuario(arrayusuario.get(nC).getUsuario_id(),id_equipo), false);
                                    }
                                }
                                mode.finish();
                                 DetalleEquipo.ActualizarEquipo();
                                //mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                                return true;
                        }
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                    }
                });

            }

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (!newText.equals("")){
            adaptadorListadoUsuarioGeneral.getFilter().filter(newText);

        }else{

            adaptadorListadoUsuarioGeneral.Actualizar(arrayusuario);
        }


        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_btn_buscar items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_btn_buscar, menu);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_btn_buscar));
        searchView.setOnQueryTextListener(this);
        return true;
    }
}
