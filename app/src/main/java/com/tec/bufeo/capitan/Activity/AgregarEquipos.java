package com.tec.bufeo.capitan.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.DetalleTorneo;
import com.tec.bufeo.capitan.Adapters.AdaptadorListadoEquipoGeneral;
import com.tec.bufeo.capitan.others.Equipo;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;

public class AgregarEquipos extends AppCompatActivity  implements SearchView.OnQueryTextListener {
    static AdaptadorListadoEquipoGeneral adaptadorListadoEquipoGeneral;
    public static ArrayList<Equipo> arrayequipo;
    DataConnection dc;
    DataConnection dc1;
    static ListView list_equipoGeneral;
    static ProgressBar progressBar;
    static CardView cdv_mensaje;
    static Activity activity;
   // public static String torneo_id;
    String id_torneo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipos);

        id_torneo = getIntent().getStringExtra("id_torneo");
        Toast.makeText(this, "id_torneo" +id_torneo, Toast.LENGTH_SHORT).show();
        list_equipoGeneral = (ListView)findViewById(R.id.list_equipoGeneral);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        cdv_mensaje = (CardView) findViewById(R.id.cdv_mensaje);
        cdv_mensaje.setVisibility(View.INVISIBLE);
        //torneo_id = getIntent().getStringExtra("torneo_id");
        dc = new DataConnection(activity,"listarEquiposEnTorneoNot",id_torneo, false);
        new AgregarEquipos.GetEmpresas().execute();
    }

    public class GetEmpresas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayequipo = dc.getlistaEquiposEnTorneoNot();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(ProgressBar.INVISIBLE);


            // Toast.makeText(getApplicationContext(),"Z "+arrayusuario.get(0).getUsuario_id(),Toast.LENGTH_SHORT).show();

            adaptadorListadoEquipoGeneral =  new AdaptadorListadoEquipoGeneral(AgregarEquipos.this,arrayequipo,R.layout.rcv_item_list_equipo_general);
            list_equipoGeneral.setAdapter(adaptadorListadoEquipoGeneral);



            if (arrayequipo.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                list_equipoGeneral.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                list_equipoGeneral.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

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

                                for (int nC =0 ; nC<adaptadorListadoEquipoGeneral.getCount(); nC++) {
                                    if (list_equipoGeneral.isItemChecked(nC)) {
                                        // mDbAdapter.deleteReminderById(getIdFromPosition(nC));

                                        dc1 = new DataConnection(AgregarEquipos.this, "registrarEquipoTorneo",new Equipo(arrayequipo.get(nC).getEquipo_id(),id_torneo), false);
                                    }
                                }
                                mode.finish();
                              //  DetalleEquipo.ActualizarEquipo();
                                DetalleTorneo.ActualizarEquipoTorneo();

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
            adaptadorListadoEquipoGeneral.getFilter().filter(newText);

        }else{

            adaptadorListadoEquipoGeneral.Actualizar(arrayequipo);
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
