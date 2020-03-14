package com.tec.bufeo.capitan.Fragments.tabsBuscar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tec.bufeo.capitan.Activity.RegistroReserva.ReservaEnBusqueda;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.AnimatedExpandableListView;
import com.tec.bufeo.capitan.Util.UniversalImageLoader;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;
import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP2;


public class FragmentBuscar extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;
    DataConnection dc ;
    ArrayList<GroupItem> arraycanchasdisponiblesOriginal;
    View view;
    LinearLayout layout_carga;
    CardView cdv_mensaje;
    FragmentBuscar fragmentBuscar;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    UniversalImageLoader universalImageLoader;

    public FragmentBuscar() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buscar, container, false);
        context=getContext();
        universalImageLoader = new UniversalImageLoader(context);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
        layout_carga =  view.findViewById(R.id.layout_carga);
        cdv_mensaje =  view.findViewById(R.id.cdv_mensaje);

        swipeRefreshLayout =  view.findViewById(R.id.SwipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        //listView.setHasFixedSize(true);


        cdv_mensaje.setVisibility(View.INVISIBLE);
        fragmentBuscar = this;
        listView = (AnimatedExpandableListView) view.findViewById(R.id.listView);

        boolean online = ForoFragment.isOnLine();
        if (online){
            dc = new DataConnection(getActivity(),"listarCanchasDisponibles",false);
            new GetCanchasDisponibles().execute();
        }


        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });



        return view;
    }

    @Override
    public void onRefresh() {
        dc = new DataConnection(getActivity(),"listarCanchasDisponibles",false);
        new GetCanchasDisponibles().execute();
    }





    public class GetCanchasDisponibles extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arraycanchasdisponiblesOriginal = dc.getListadoCanchasDisponiblesOriginal();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            swipeRefreshLayout.setRefreshing(false);
            adapter = new ExampleAdapter(fragmentBuscar);
            adapter.setData(arraycanchasdisponiblesOriginal);

            listView.setAdapter(adapter);
            layout_carga.setVisibility(View.GONE);

            if (arraycanchasdisponiblesOriginal.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }
            //Toast.makeText(getContext(),"S:" +arraycanchasdisponibles.size(),Toast.LENGTH_SHORT).show();

        }
    }

    public static class GroupItem {
        public String title;
        public List<ChildItem> items = new ArrayList<ChildItem>();
    }

    public static class ChildItem {
        //String title;
       // String hint;
        public String txt_buscar_nombreEmpresa;
        public String txt_buscar_precioCancha;
        public String txt_buscar_direccionEmpresa;
        public String img_cancha;
        public String h_reserva;
        public String empresa_id;
        public String txt_llamar1;
        public String txt_llamar2;
        //String hint;

    }

    public static class ChildHolder {
        public TextView title;
        public TextView hint;
        public TextView txt_buscar_nombreEmpresa;
        public TextView txt_buscar_precioCancha;
        public TextView txt_buscar_direccionEmpresa;
        public TextView txt_buscar_telefonoEmpresa;
        public TextView txt_buscar_telefonoEmpresa2;
        public LinearLayout imb_llamar;
        public ImageView imagen_cancha;
        public LinearLayout layout_reserva_busqueda,layoutReserva;
        public TextView txt_llamar;

    }

    public static class GroupHolder {
        public TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    public class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(FragmentBuscar context) {
            inflater = LayoutInflater.from(getContext());
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ChildHolder holder;
            final ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.rcv_item_card_buscar_horario_general, parent, false);



                 holder.txt_buscar_nombreEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_nombreEmpresa);
                 holder.txt_buscar_direccionEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_direccionEmpresa);
                 holder.txt_buscar_precioCancha = (TextView) convertView.findViewById(R.id.txt_buscar_precioCancha);
                 holder.txt_buscar_telefonoEmpresa = (TextView) convertView.findViewById(R.id.txt_buscar_telefonoEmpresa);
                 holder.txt_buscar_telefonoEmpresa2 = (TextView) convertView.findViewById(R.id.txt_buscar_telefonoEmpresa2);
                 holder.imagen_cancha = (ImageView) convertView.findViewById(R.id.imagen_cancha);
                 holder.imb_llamar = (LinearLayout) convertView.findViewById(R.id.imb_llamar);
                 holder.layout_reserva_busqueda = (LinearLayout) convertView.findViewById(R.id.layout_reserva_busqueda);


                //holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

           // holder.title.setText(item.title);
           // holder.hint.setText(item.hint);
            universalImageLoader.setImage(IP2+"/"+ item.img_cancha,holder.imagen_cancha,null);

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
            //holder.imb_llamar.set(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
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
            GroupItem item = getGroup(groupPosition);
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


    AlertDialog dialog_telefono;
    public void dialogoTelefonos(final String fono1, final String fono2){

        AlertDialog.Builder builder =  new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_telefonos,null);
        builder.setView(vista);

        dialog_telefono = builder.create();
        dialog_telefono.show();

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

}
