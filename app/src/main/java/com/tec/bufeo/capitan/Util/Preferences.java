package com.tec.bufeo.capitan.Util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringRes;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    public static final String PREFS_NAME = "User";
    Context context;
    SharedPreferences pref;

    public Preferences(Context context){
        this.context = context;
    }

    public String getNombrePref(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("usuario_nombre","");
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


}
