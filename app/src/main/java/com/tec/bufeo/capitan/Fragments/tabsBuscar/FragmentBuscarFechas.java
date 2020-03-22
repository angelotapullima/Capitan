package com.tec.bufeo.capitan.Fragments.tabsBuscar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.RegistroReserva.ReservaEnBusqueda;
import com.tec.bufeo.capitan.Adapters.AdaptadorListaCanchasBusqueda;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.Modelo.Empresas;
import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.AnimatedExpandableListView;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.Util.Preferences;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;

public class FragmentBuscarFechas extends Fragment implements View.OnClickListener {

    DataConnection dc,dc2,dc3 ;
    Activity activity;
    Context context;
    ArrayList<String> arrayEmpresaID;
    ArrayList<Empresas> ArrayEmpresas;
    ArrayList<Empresas> listCanchaHoraEmpresa;
    Spinner spn_empresas,btnHoraCancha;
    Button buscar;
    TextView fecha,infoExpandable,infoHoraBusqueda;
    Preferences preferences;
    RecyclerView recyclerBusquedaCancha;
    AdaptadorListaCanchasBusqueda adaptadorListaCanchasBusqueda;
    LinearLayout layoutNormal , layoutExpandable, layoutInfoHora,layoutInfoHoraExpandable;
    RelativeLayout carga_Empresas;
    UniversalImageLoader universalImageLoader;

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

        universalImageLoader = new UniversalImageLoader(context);
        

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
        carga_Empresas = view.findViewById(R.id.carga_Empresas);

        buscar.setEnabled(false);
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



        fecha.setOnClickListener(this);
        buscar.setOnClickListener(this);

        layoutExpandable.setVisibility(View.GONE);
        layoutNormal.setVisibility(View.GONE);
        return view;
    }

    private  final String CERO = "0";



    String hours;

    int year,month,day;
    String dia,mes;
    private void showDateDailog(final TextView editText) {

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDate) {

                year = selectedYear;
                month = selectedMonth;
                day = selectedDate;

                if (day<10){
                    dia = CERO + String.valueOf(day);
                }else{
                    dia = String.valueOf(day);
                }

                if (month<10){
                    month = month+1;
                    mes = CERO + String.valueOf(month);
                }else{
                    month = month+1;
                    mes = String.valueOf(month);
                }
                editText.setText(new StringBuilder().append(year).append("-").append(mes).append("-").append(dia));

            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)+7,
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), 0);
        long time = cal.getTimeInMillis();
        datePickerDialog.getDatePicker().setMaxDate(time);
        datePickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v.equals(fecha)){
            showDateDailog(fecha);
           // showDatePickerDialog(fecha);
        }if (v.equals(buscar)){


            if (fecha.getText().toString().isEmpty()){
                preferences.codeAdvertencia("El campo fecha no debe estar vacio");
            }else{
                dialogCarga();
                    Log.d("click buscar", "onClick: funciona" );

                    hours = btnHoraCancha.getSelectedItem().toString();
                    if ((hours.equals("Todos"))){

                        layoutExpandable.setVisibility(View.VISIBLE);
                        layoutNormal.setVisibility(View.GONE);

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

                        layoutExpandable.setVisibility(View.GONE);
                        layoutNormal.setVisibility(View.VISIBLE);

                        Reserva e = new Reserva();
                        if (spn_empresas.getSelectedItem().toString().equals("Todos")){
                            e.setCancha_nombre("Todos");
                        }else{
                            e.setCancha_nombre(ArrayEmpresas.get(spn_empresas.getSelectedItemPosition()-1).getEmpresas_id());
                        }

                        e.setReserva_fecha(fecha.getText().toString());
                        e.setReserva_hora(hours.substring(0,2));


                        dc3 = new DataConnection(getActivity(),"listarCanchasDisponiblesBusqueda2",e,false);
                        new GetCanchasDisponiblesBusqueda2().execute();
                    }





            }

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

            adapter = new ExampleAdapterBusqueda(fragmentBuscarFechas);
            adapter.setData(arraycanchasdisponiblesbusqueda);

            listViewBusqueda.setAdapter(adapter);

            if (arraycanchasdisponiblesbusqueda.size() > 0) {

                DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
                Date convertido = null;
                try {
                    convertido = fechaHora.parse(fecha.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat formatex = new SimpleDateFormat("EEE, d MMM yyyy");


                infoExpandable.setText(formatex.format(convertido));

                layoutExpandable.setVisibility(View.VISIBLE);
                layoutNormal.setVisibility(View.GONE);

            } else {

            }
            dialog_cargando.dismiss();

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
            dialog_cargando.dismiss();

        }
    }


    private   void recycler() {

        recyclerBusquedaCancha.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerBusquedaCancha.setLayoutManager(layoutManager);

        adaptadorListaCanchasBusqueda = new AdaptadorListaCanchasBusqueda(activity, listCanchaHoraEmpresa, R.layout.rcv_item_card_buscar_horario_general, new AdaptadorListaCanchasBusqueda.OnItemClickListener() {
            @Override
            public void onItemClick(Empresas empresas, String tipo, int position) {
                if (tipo.equals("layout_reserva_busqueda")){

                    Intent i = new Intent(getActivity(), ReservaEnBusqueda.class);
                    i.putExtra("nombre_empresa",empresas.getEmpresas_nombre());
                    i.putExtra("h_reserva",empresas.getHora_reserva());
                    i.putExtra("empresa_id",empresas.getEmpresas_id());
                    i.putExtra("precio",empresas.getPrecio());
                    i.putExtra("telefono1",empresas.getEmpresas_telefono_1());
                    i.putExtra("telefono2",empresas.getEmpresas_telefono_2());
                    i.putExtra("direccion",empresas.getEmpresas_direccion());
                    startActivity(i);
                }else if( tipo.equals("imb_llamar")){

                    String telefono1 = empresas.getEmpresas_telefono_1();
                    String telefono2 = empresas.getEmpresas_telefono_2();

                    if (telefono2.equals("")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+  telefono1));
                        startActivity(intent);
                    }else{
                        dialogoTelefonos(telefono1,telefono2);
                    }

                }
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


    AlertDialog dialog_eliminar;
    public void dialogoTelefonos(final String fono1, final String fono2){

        AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_telefonos,null);
        builder.setView(vista);

        dialog_eliminar = builder.create();
        dialog_eliminar.show();

        TextView fonito1 = vista.findViewById(R.id.fono1);
        TextView fonito2 = vista.findViewById(R.id.fono2);

        fonito1.setText(fono1);
        fonito2.setText(fono2);


        fonito1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+  fono1));
                startActivity(intent);
            }
        });
        fonito2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+  fono2));
                startActivity(intent);
            }
        });



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
            carga_Empresas.setVisibility(View.GONE);

            buscar.setEnabled(true);

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
        public String h_reserva;
        public String empresa_id;
        public String txt_llamar1;
        public String txt_llamar2;
        //String hint;

    }

    public static class ChildHolderBusqueda {
        public TextView title;
        public TextView hint;
        public TextView txt_buscar_nombreEmpresa;
        public TextView txt_buscar_precioCancha;
        public TextView txt_buscar_direccionEmpresa;
        public TextView txt_buscar_telefonoEmpresa;
        public TextView txt_buscar_telefonoEmpresa2;
        public LinearLayout imb_llamar;
        public ImageView imagen_cancha;
        public LinearLayout layout_reserva_busqueda;

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
            final ChildItemBusqueda item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolderBusqueda();
                convertView = inflater.inflate(R.layout.rcv_item_card_buscar_horario_general, parent, false);



                holder.txt_buscar_nombreEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_nombreEmpresa);
                holder.txt_buscar_direccionEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_direccionEmpresa);
                holder.txt_buscar_precioCancha = (TextView) convertView.findViewById(R.id.txt_buscar_precioCancha);
                holder.txt_buscar_telefonoEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_telefonoEmpresa);
                holder.txt_buscar_telefonoEmpresa2 = (TextView) convertView.findViewById(R.id.txt_buscar_telefonoEmpresa2);
                holder.imagen_cancha = (ImageView) convertView.findViewById(R.id.imagen_cancha);
                holder.imb_llamar = (LinearLayout) convertView.findViewById(R.id.imb_llamar);
                holder.layout_reserva_busqueda =  convertView.findViewById(R.id.layout_reserva_busqueda);


                //holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolderBusqueda) convertView.getTag();
            }

            // holder.title.setText(item.title);
            // holder.hint.setText(item.hint);
            UniversalImageLoader.setImage(IP2+"/"+ item.img_cancha,holder.imagen_cancha,null);
            holder.txt_buscar_nombreEmpresa.setText(item.txt_buscar_nombreEmpresa);
            holder.txt_buscar_precioCancha.setText(item.txt_buscar_precioCancha);
            holder.txt_buscar_direccionEmpresa.setText(item.txt_buscar_direccionEmpresa);
            holder.txt_buscar_telefonoEmpresa.setText(item.txt_llamar1);
            holder.txt_buscar_telefonoEmpresa2.setText(item.txt_llamar2);
            holder.layout_reserva_busqueda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ReservaEnBusqueda.class);
                    i.putExtra("nombre_empresa",item.txt_buscar_nombreEmpresa);
                    i.putExtra("precio",item.txt_buscar_precioCancha);
                    i.putExtra("h_reserva",item.h_reserva);
                    i.putExtra("empresa_id",item.empresa_id);
                    i.putExtra("telefono1",item.txt_llamar1);
                    i.putExtra("telefono2",item.txt_llamar2);
                    i.putExtra("direccion",item.txt_buscar_direccionEmpresa);
                    context.startActivity(i);
                }
            });
            holder.imb_llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String telefono1 = item.txt_llamar1.toString();
                    String telefono2 = item.txt_llamar2.toString();

                    if (telefono2.equals("")){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+  telefono1));
                        startActivity(intent);
                    }else{
                        dialogoTelefonos(telefono1,telefono2);
                    }

                }
            });

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


    Dialog dialog_cargando;
    public void dialogCarga(){

        dialog_cargando= new Dialog(getContext(), android.R.style.Theme_Translucent);
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

    }
}
