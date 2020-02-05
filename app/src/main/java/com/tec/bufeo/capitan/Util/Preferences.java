package com.tec.bufeo.capitan.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.tec.bufeo.capitan.R;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    public static final String PREFS_NAME = "User";
    Context context;
    SharedPreferences pref;

    public Preferences(Context context){
        this.context = context;
    }






    public String getLimite_sup(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("lim_sup","");
    }

    public String getLimite_inf(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("lim_inf","");
    }
    public void saveValuePORT(String llave, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(llave, valor);
        editor.commit();
    }


/*aca estan los nuevos datos que vienen con el nuevo Login*/

    public String getIdUsuarioPref(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("id_user","");
    }

    public String getFotoUsuario(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("user_image","");
    }
    public String getUbigeoId(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("ubigeo_id","");
    }
    public String getToken(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("token","");
    }

    public String getPersonName(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("person_name","");
    }
    public String getPersonSurname(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("person_surname","");
    }
    public String getTieneNegocio(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("tiene_negocio","");
    }








    public void toasRojo(String titulo, String contenido){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ); //(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast,null);
        //View layout = inflater.inflate(R.layout.custom_toast),(ViewGroup)findViewById(R.id.base);

        TextView textView =  layout.findViewById(R.id.txtTitulo);
        textView.setText(titulo);

        TextView textView2 =  layout.findViewById(R.id.txtContenido);
        textView2.setText(contenido);

        Toast toast =  new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void toasVerde(String titulo  ){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ); //(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast_1,null);

        TextView textView =  layout.findViewById(R.id.txtTitulo);
        textView.setText(titulo);



        Toast toast =  new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void codeAdvertencia(String titulo  ){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ); //(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_advertencia,null);

        TextView textView =  layout.findViewById(R.id.txtTitulo);
        textView.setText(titulo);



        Toast toast =  new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.BOTTOM,0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
