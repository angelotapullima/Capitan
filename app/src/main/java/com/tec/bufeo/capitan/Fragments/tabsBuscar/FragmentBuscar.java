package com.tec.bufeo.capitan.Fragments.tabsBuscar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.Modelo.HoraFecha;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.AnimatedExpandableListView;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.util.ArrayList;
import java.util.List;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;


public class FragmentBuscar extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;
    DataConnection dc ,dc1;
    ArrayList<GroupItem> arraycanchasdisponiblesOriginal;
    ArrayList<HoraFecha> arrayHoraFecha;
    View view;
    static ProgressBar progressBar;
    static CardView cdv_mensaje;
    FragmentBuscar fragmentBuscar;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;

    public FragmentBuscar() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buscar, container, false);
        context=getContext();
        progressBar =  view.findViewById(R.id.progressbar);
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
            dc1 = new DataConnection(getActivity(),"obtenerHoraFecha",false);
            new GetHoraFecha().execute();
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
        dc1 = new DataConnection(getActivity(),"obtenerHoraFecha",false);
        new GetHoraFecha().execute();
    }



    public class GetHoraFecha extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayHoraFecha = dc1.getHoraFecha();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(getContext(),"'h"+arrayHoraFecha.get(0).getH_f_hora().substring(0,2).toString(),Toast.LENGTH_LONG).show();

            dc = new DataConnection(getActivity(),"listarCanchasDisponibles",false,arrayHoraFecha.get(0).getH_f_hora().substring(0,2));
            new GetCanchasDisponibles().execute();
        }
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
            progressBar.setVisibility(ProgressBar.INVISIBLE);

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
        public String imb_llamar;
        public String img_cancha;
        public String txt_llamar;
        //String hint;

    }

    public static class ChildHolder {
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
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
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
                holder = (ChildHolder) convertView.getTag();
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


}
