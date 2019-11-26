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
    public String getIdUsuarioPref(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("idusuario","");
    }
    public String getUbigeoId(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("ubigeo_id","");
    }
    public String getNicknamePref(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("nickname","");
    }
    public String getFotoUsuario(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("usuario_foto","");
    }
    public String getVehiculoPref(){
        pref=context.getSharedPreferences("User", MODE_PRIVATE);
        return pref.getString("vehiculo","");
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






}
