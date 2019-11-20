package com.tec.bufeo.capitan.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tec.bufeo.capitan.Modelo.Usuario;
import com.tec.bufeo.capitan.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tec.bufeo.capitan.WebService.DataConnection.IP;

public class AdaptadorListadoUsuarioGeneral extends BaseAdapter implements Filterable {

    private ArrayList<Usuario> array;
    private int layoutpadre;
    Context context;
    Usuario obj;

    public AdaptadorListadoUsuarioGeneral(Context context, ArrayList<Usuario> array, int layoutpadre) {
        this.array = array;
        this.layoutpadre = layoutpadre;
        this.context = context;
    }

    public class ViewHolder{
         CircleImageView civ_fotoUsuario;
         TextView txt_nombreUsuario;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row= convertView;

        ViewHolder holder= new ViewHolder();

        //para evitar errores null
        if(row==null){

            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutpadre,null);

            holder.civ_fotoUsuario= (CircleImageView) row.findViewById(R.id.civ_fotoUsuario);
            holder.txt_nombreUsuario= (TextView) row.findViewById(R.id.txt_nombreUsuario);

            row.setTag(holder);

        }else{
            holder= (ViewHolder) row.getTag();
        }


        //sacamos el objeto
        obj= array.get(position);

        //rellenamos los datos que tienes ese objeto
        Picasso.with(context).load(IP+"/"+ obj.getUsuario_foto()).into(holder.civ_fotoUsuario);
        holder.txt_nombreUsuario.setText(obj.getUsuario_nombre());

        return row;
    }


    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(array.get(position).getUsuario_id());
    }


    public void Actualizar(ArrayList<Usuario> array){
        this.array =array;
        notifyDataSetChanged();
    }

    CFilter mFilter;
    @Override
    public Filter getFilter() {
        if (mFilter == null)
            mFilter = new CFilter();

        return mFilter;
    }
    // Filter

    private class CFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // Create a FilterResults object
            FilterResults results = new FilterResults();

            // If the constraint (search string/pattern) is null
            // or its length is 0, i.e., its empty then
            // we just set the `values` property to the
            // original contacts list which contains all of them
            if (constraint == null || constraint.length() == 0) {
                results.values = array;
                results.count = array.size();
            }
            else {
                // Some search copnstraint has been passed
                // so let's filter accordingly
                ArrayList<Usuario> filteredContacts = new ArrayList<Usuario>();

                // We'll go through all the contacts and see
                // if they contain the supplied string
                for (Usuario c : array) {
                    if (c.getUsuario_nombre().toUpperCase().contains( constraint.toString().toUpperCase() )) {
                        // if `contains` == true then add it
                        // to our filtered list
                        filteredContacts.add(c);
                    }
                }

                // Finally set the filtered values and size/count
                results.values = filteredContacts;
                results.count = filteredContacts.size();
            }

            // Return our FilterResults object
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            array = (ArrayList<Usuario>) results.values;
            notifyDataSetChanged();
        }
    }
}