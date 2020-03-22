package com.tec.bufeo.capitan.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tec.bufeo.capitan.Activity.MenuPrincipal;
import com.tec.bufeo.capitan.Activity.PantallasNotificacion.ChatsNotificacion;
import com.tec.bufeo.capitan.Activity.RegistroForo;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Views.ChatsActivity;
import com.tec.bufeo.capitan.R;

public class FireBaseMessaging extends FirebaseMessagingService {

    public static final String TAG = "FirebaseService";
    private final static String CHANNEL_ID = "NOTIFICACION";
    private static final String TIPO= "tipo";
    private static final String TOKEN= "token";
    public final static int NOTIFICACION_ID = 0;


    //CARGA UTIL DE MENSAJES
    private static final String MHORA= "hora";
    private static final String MFECHA= "fecha";
    private static final String MID_CHAT= "id_chat";
    private static final String MID_USUARIO= "id_usuario";
    private static final String MMENSAJE= "mensaje";


    String tipo, token,mhora,mfecha, mid_chat,mid_usuario,mmensaje;
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "Token: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        tipo=remoteMessage.getData().get(TIPO);
        token=remoteMessage.getData().get(TOKEN);

        mhora=remoteMessage.getData().get(MHORA);
        mfecha=remoteMessage.getData().get(MFECHA);
        mid_chat=remoteMessage.getData().get(MID_CHAT);
        mid_usuario=remoteMessage.getData().get(MID_USUARIO);
        mmensaje=remoteMessage.getData().get(MMENSAJE);


        NotificacionesService notificacionesService = new NotificacionesService();
        notificacionesService.setTitle(remoteMessage.getNotification().getTitle());
        notificacionesService.setDescription(remoteMessage.getNotification().getBody());
        notificacionesService.getDescount(remoteMessage.getData().get(TIPO));
        createNotificationChannel();

        //ShowNotification(notificaciones);

        if (tipo==null){
            ShowNotification(notificacionesService);
        }else{
            if (tipo.equals("alarmas")){
                Log.d(TAG, "onMessageReceived:  funcionando la huevada de tipo " +token );
                alarma(token);

            }if (tipo.equals("Mensaje")){
                //mensaje(mhora,mfecha,mid_chat,mmensaje,mid_usuario,tipo);
                Log.d("mensaje", "mensaje:  funcionando  tipo " +tipo );
                mensajeChats(mhora,mfecha,mid_chat,mmensaje,mid_usuario,tipo);
                mensajeNotificacionChats(mhora,mfecha,mid_chat,mmensaje,mid_usuario,tipo);
                notificacionesService.setTitle(mid_usuario);
                notificacionesService.setDescription(mmensaje);
                ShowNotification(notificacionesService);

            }

        }
    }



    public void alarma (String tokena ){
        Intent i = new Intent(RegistroForo.registro);
        i.putExtra("tipo",tokena);
        i.putExtra("concepto","publicacion");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }
    /*public void mensaje (String hora,String fecha,String id_chat,String mmensaje,String mid_usuario,String tipo ){
        Intent i = new Intent(MenuPrincipal.registro);
        i.putExtra("tipo",tipo);
        i.putExtra("hora",hora);
        i.putExtra("fecha",fecha);
        i.putExtra("id_chat",id_chat);
        i.putExtra("id_usuario",mid_usuario);
        i.putExtra("mensaje",mmensaje);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }*/
    public void mensajeChats (String hora,String fecha,String id_chat,String mmensaje,String mid_usuario,String tipo ){
        Intent i = new Intent(ChatsActivity.CHAT);
        i.putExtra("tipo",tipo);
        i.putExtra("hora",hora);
        i.putExtra("fecha",fecha);
        i.putExtra("id_chat",id_chat);
        i.putExtra("id_usuario",mid_usuario);
        i.putExtra("mensaje",mmensaje);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }
    public void mensajeNotificacionChats (String hora,String fecha,String id_chat,String mmensaje,String mid_usuario,String tipo ){
        Intent i = new Intent(ChatsNotificacion.CHATNOTIFICACION);
        i.putExtra("tipo",tipo);
        i.putExtra("hora",hora);
        i.putExtra("fecha",fecha);
        i.putExtra("id_chat",id_chat);
        i.putExtra("id_usuario",mid_usuario);
        i.putExtra("mensaje",mmensaje);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

    }
    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void ShowNotification(NotificacionesService notificacionesService){

        Intent i = new Intent(this, MenuPrincipal.class);
        i.putExtra("mostrarPantalla","inicio");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,NOTIFICACION_ID,i, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificacionBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(notificacionesService.getTitle())
                .setContentText(notificacionesService.getDescription())
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(defaultSound)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICACION_ID,notificacionBuilder.build());
        }


    }

}