package com.tec.bufeo.capitan.Util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.StringRes;

import com.tec.bufeo.capitan.Activity.RegistroForo;
import com.tec.bufeo.capitan.R;

import net.gotev.uploadservice.UploadNotificationAction;
import net.gotev.uploadservice.UploadNotificationConfig;

import static android.content.Context.MODE_PRIVATE;
import static net.gotev.uploadservice.Placeholders.ELAPSED_TIME;
import static net.gotev.uploadservice.Placeholders.PROGRESS;
import static net.gotev.uploadservice.Placeholders.TOTAL_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOADED_FILES;
import static net.gotev.uploadservice.Placeholders.UPLOAD_RATE;

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

    public void saveValuePORT(String llave, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(llave, valor);
        editor.commit();
    }






}
