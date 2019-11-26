package com.tec.bufeo.capitan.Fragments.tabsBuscar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Adapters.AdaptadorListaCanchasBusqueda;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.AnimatedExpandableListView;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class FragmentBuscarFechas extends Fragment implements View.OnClickListener {

    DataConnection dc,dc2,dc3 ;
    Activity activity;
    Context context;
    ArrayList<String> arrayEmpresaID;
    ArrayList<Empresas> ArrayEmpresas;
    ArrayList<Empresas> listCanchaHoraEmpresa;
    Spinner spn_empresas;
    Button buscar;
    TextView fecha,btnHoraCancha,infoExpandable,infoHoraBusqueda;
    Preferences preferences;
    RecyclerView recyclerBusquedaCancha;
    AdaptadorListaCanchasBusqueda adaptadorListaCanchasBusqueda;
    LinearLayout layoutNormal , layoutExpandable, layoutInfoHora,layoutInfoHoraExpandable;


    public FragmentBuscarFechas() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_buscar_fechas, container, false);
        activity= getActivity();
        context=getContext();
        fragmentBuscarFechas=this;
        preferences= new Preferences(context);
        buscar=view.findViewById(R.id.buscar);
        fecha=view.findViewById(R.id.fecha);
        btnHoraCancha=view.findViewById(R.id.btnHoraCancha);
        recyclerBusquedaCancha=view.findViewById(R.id.recyclerBusquedaCancha);
        spn_empresas = view.findViewById(R.id.spn_empresas);
        listViewBusqueda = view.findViewById(R.id.listViewBusqueda);
        layoutNormal = view.findViewById(R.id.layoutNormal);
        layoutExpandable = view.findViewById(R.id.layoutExpandable);
        infoExpandable = view.findViewById(R.id.infoExpandable);
        infoHoraBusqueda = view.findViewById(R.id.infoHoraBusqueda);
        layoutInfoHora = view.findViewById(R.id.layoutInfoHora);
        layoutInfoHoraExpandable = view.findViewById(R.id.layoutInfoHoraExpandable);

        boolean online = ForoFragment.isOnLine();

        if (online){
            dc = new DataConnection(activity, "listarEmpresasID",preferences.getUbigeoId(), false);
            new GetEmpresasID().execute();
        }



        listViewBusqueda.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listViewBusqueda.isGroupExpanded(groupPosition)) {
                    listViewBusqueda.collapseGroupWithAnimation(groupPosition);
                } else {
                    listViewBusqueda.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        btnHoraCancha.setOnClickListener(this);
        fecha.setOnClickListener(this);
        buscar.setOnClickListener(this);

        layoutExpandable.setVisibility(View.GONE);
        layoutNormal.setVisibility(View.GONE);
        return view;
    }

    private  final String CERO = "0";
    private  final String DOS_PUNTOS = ":";

    private void obtenerHora(final TextView Hora){
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog recogerHora = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                Hora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);

        recogerHora.show();




    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private void showDatePickerDialog(final TextView editText) {
        DateDialog.DatePickerFragment newFragment = DateDialog.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate =  year+ "-" + twoDigits(month+1) + "-" + twoDigits(day);
                editText.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    String hours;

    @Override
    public void onClick(View v) {
        if (v.equals(fecha)){
            showDatePickerDialog(fecha);
        }if (v.equals(btnHoraCancha)){
            obtenerHora(btnHoraCancha);
        }if (v.equals(buscar)){
            Log.e("click buscar", "onClick: funciona" );

             hours = btnHoraCancha.getText().toString();
            if ((hours.equals("Todos"))){

                Reserva e = new Reserva();
                if (spn_empresas.getSelectedItem().toString().equals("Todos")){

                    e.setCancha_nombre("Todos");
                }else{
                    e.setCancha_nombre(ArrayEmpresas.get(spn_empresas.getSelectedItemPosition()-1).getEmpresas_id());
                }
                e.setReserva_fecha(fecha.getText().toString());
                e.setReserva_hora("Todos");


                dc2 = new DataConnection(getActivity(),"listarCanchasDisponiblesBusqueda",e,false);
                new GetCanchasDisponiblesBusqueda().execute();

            }else{


                Reserva e = new Reserva();
                if (spn_empresas.getSelectedItem().toString().equals("Todos")){
                    e.setCancha_nombre("Todos");
                }else{
                    e.setCancha_nombre(ArrayEmpresas.get(spn_empresas.getSelectedItemPosition()-1).getEmpresas_id());
                }

                e.setReserva_fecha(fecha.getText().toString());
                e.setReserva_hora(hours.substring(0,2));
                //e.setReserva_hora(btnHoraCancha.getText().toString().substring(0,2));


                dc3 = new DataConnection(getActivity(),"listarCanchasDisponiblesBusqueda2",e,false);
                new GetCanchasDisponiblesBusqueda2().execute();
            }
            btnHoraCancha.setText("Todos");
        }
    }
    ArrayList<GroupItemBusqueda> arraycanchasdisponiblesbusqueda;
    private ExampleAdapterBusqueda adapter;
    FragmentBuscarFechas fragmentBuscarFechas;
    AnimatedExpandableListView listViewBusqueda;

    public class GetCanchasDisponiblesBusqueda extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arraycanchasdisponiblesbusqueda = dc2.getListadoCanchasDisponiblesBusqueda();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //swipeRefreshLayout.setRefreshing(false);
            adapter = new ExampleAdapterBusqueda(fragmentBuscarFechas);
            adapter.setData(arraycanchasdisponiblesbusqueda);

            listViewBusqueda.setAdapter(adapter);
            //progressBar.setVisibility(ProgressBar.INVISIBLE);

            if (arraycanchasdisponiblesbusqueda.size() > 0) {
                infoExpandable.setText(fecha.getText().toString());
                //e.setReserva_hora(btnHoraCancha.getText().toString().substring(0,2));

                layoutExpandable.setVisibility(View.VISIBLE);
                layoutNormal.setVisibility(View.GONE);//cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                //cdv_mensaje.setVisibility(View.VISIBLE);
            }
            //Toast.makeText(getContext(),"S:" +arraycanchasdisponibles.size(),Toast.LENGTH_SHORT).show();

        }
    }



    public class GetCanchasDisponiblesBusqueda2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listCanchaHoraEmpresa = dc3.getListaCachasHoraEmpresa();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            recycler();


        }
    }


    private   void recycler() {



        //progressBar.setVisibility(ProgressBar.INVISIBLE);
        recyclerBusquedaCancha.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerBusquedaCancha.setLayoutManager(layoutManager);

        adaptadorListaCanchasBusqueda =   new AdaptadorListaCanchasBusqueda(activity, listCanchaHoraEmpresa, R.layout.rcv_item_card_buscar_horario_general, new AdaptadorListaCanchasBusqueda.OnItemClickListener() {
            @Override
            public void onItemClick(Empresas empresas, int position) {

            }
        });


        recyclerBusquedaCancha.setAdapter(adaptadorListaCanchasBusqueda);
        if( listCanchaHoraEmpresa.size()>0){
            infoHoraBusqueda.setText(hours);
            layoutNormal.setVisibility(View.VISIBLE);
            layoutExpandable.setVisibility(View.GONE);
            //cdv_mensaje.setVisibility(View.INVISIBLE);
        }else{
            //cdv_mensaje.setVisibility(View.VISIBLE);
        }
    }

    public class GetEmpresasID extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayEmpresaID = new ArrayList<String>();
            ArrayEmpresas = dc.getListaEmpresasDistrito();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(context,"size"+arrayArea.size(),Toast.LENGTH_LONG).show();

            for (Empresas obj : ArrayEmpresas) {
                arrayEmpresaID.add(obj.getEmpresas_nombre());
            }
            //progressBar.setVisibility(ProgressBar.INVISIBLE);
            arrayEmpresaID.add(0, "Todos");
            ArrayAdapter<String> adapEquipos = new ArrayAdapter<String>(context, R.layout.spiner_item, arrayEmpresaID);
            adapEquipos.setDropDownViewResource(R.layout.spiner_dropdown_item);
            spn_empresas.setAdapter(adapEquipos);

        }
    }




    public static class GroupItemBusqueda {
        public String title;
        public List<ChildItemBusqueda> items = new ArrayList<ChildItemBusqueda>();
    }

    public static class ChildItemBusqueda {
        //String title;
        // String hint;
        public String txt_buscar_nombreEmpresa;
        public String txt_buscar_precioCancha;
        public String txt_buscar_direccionEmpresa;
        public String imb_llamar;
        public String img_cancha;
        public String txt_llamar;
        //String hint;

    }

    public static class ChildHolderBusqueda {
        public TextView title;
        public TextView hint;
        public TextView txt_buscar_nombreEmpresa;
        public TextView txt_buscar_precioCancha;
        public TextView txt_buscar_direccionEmpresa;
        public TextView txt_buscar_telefonoEmpresa;
        public ImageButton imb_llamar;
        public ImageView imagen_cancha;
        public TextView txt_llamar;

    }

    public static class GroupHolder {
        public TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItemBusqueda}s.
     */
    public class ExampleAdapterBusqueda extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItemBusqueda> items;

        public ExampleAdapterBusqueda(FragmentBuscarFechas context) {
            inflater = LayoutInflater.from(getContext());
        }

        public void setData(List<GroupItemBusqueda> items) {
            this.items = items;
        }

        @Override
        public ChildItemBusqueda getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ChildHolderBusqueda holder;
            ChildItemBusqueda item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolderBusqueda();
                convertView = inflater.inflate(R.layout.rcv_item_card_buscar_horario_general, parent, false);



                holder.txt_buscar_nombreEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_nombreEmpresa);
                holder.txt_buscar_direccionEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_direccionEmpresa);
                holder.txt_buscar_precioCancha = (TextView) convertView.findViewById(R.id.txt_buscar_precioCancha);
                holder.txt_buscar_telefonoEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_telefonoEmpresa);
                holder.imagen_cancha = (ImageView) convertView.findViewById(R.id.imagen_cancha);
                holder.imb_llamar = (ImageButton) convertView.findViewById(R.id.imb_llamar);


                //holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolderBusqueda) convertView.getTag();
            }

            // holder.title.setText(item.title);
            // holder.hint.setText(item.hint);
            Picasso.with(context).load(IP+"/"+ item.img_cancha).into(holder.imagen_cancha);
            holder.txt_buscar_nombreEmpresa.setText(item.txt_buscar_nombreEmpresa);
            holder.txt_buscar_precioCancha.setText(item.txt_buscar_precioCancha);
            holder.txt_buscar_direccionEmpresa.setText(item.txt_buscar_direccionEmpresa);
            holder.txt_buscar_telefonoEmpresa.setText(item.txt_llamar);
            holder.imb_llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+  holder.txt_buscar_telefonoEmpresa.getText().toString()));
                    startActivity(intent);
                }
            });
            //holder.imb_llamar.set(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItemBusqueda getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItemBusqueda item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

}
