package com.tec.bufeo.capitan.Activity;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.bufeo.capitan.Modelo.Reserva;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.DateDialog;
import com.tec.bufeo.capitan.WebService.DataConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.tec.bufeo.capitan.Activity.DetalleNegocio.fecha_actual;
import static com.tec.bufeo.capitan.Activity.DetalleNegocio.id_empresa;

public class ReporteDiario2 extends AppCompatActivity  implements  DatePickerDialog.OnDateSetListener, View.OnClickListener{

    Button btn_fechaReporte,btn_fecha2Reporte, btn_buscar;
    TextView txt_totalRecaudado;
   // RecyclerView rcv_reportes_por_dia;
    TableLayout tably_listadoabonos;
    CardView cdv_mensaje;
   // ProgressBar progressbar;
    DataConnection dc;
    ArrayList<Reserva> arrayreserva;
    //AdaptadorListadoReportesDiarios adaptadorListadoReportesDiarios;
    int precioTotal;
    static String nombre_btn="";
    private LinearLayout llscrol;
    private Bitmap bitmap;
    public String fechaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.barra_cerrar);
        getSupportActionBar().setTitle("Reportes ");
        tably_listadoabonos = findViewById(R.id.tably_listadoabonos);
        llscrol = findViewById(R.id.llscrol);
        precioTotal = 0;

        btn_fechaReporte = findViewById(R.id.btn_fechaReporte);
        btn_fecha2Reporte = findViewById(R.id.btn_fecha2Reporte);
        btn_buscar = findViewById(R.id.btn_buscar);
        txt_totalRecaudado = findViewById(R.id.txt_totalRecaudado);
     //   rcv_reportes_por_dia = findViewById(R.id.rcv_reportes_por_dia);
        cdv_mensaje = findViewById(R.id.cdv_mensaje);
     //   progressbar = findViewById(R.id.progressbar);
        btn_fechaReporte.setText(fecha_actual);
        btn_fecha2Reporte.setText(fecha_actual);
        btn_fechaReporte.setOnClickListener(this);
        btn_fecha2Reporte.setOnClickListener(this);
        btn_buscar.setOnClickListener(this);
        dc = new DataConnection(this,"listarEstadisticasGeneral",new Reserva(id_empresa,fecha_actual,fecha_actual ),false);
        new GetReporte().execute();
        // txt_totalRecaudado.setText("0");
        mostarCabecera();


      /*  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        fechaHora = calendar.getTime().toString();*/

        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        fechaHora = dateFormat.format(date);*/

        Date horaActual=new Date();

        fechaHora=horaActual.getYear()+1900+""+(horaActual.getMonth()+1)+""+horaActual.getDate()+""+horaActual.getHours()+""+horaActual.getMinutes()+""+horaActual.getSeconds();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fechaReporte:
                nombre_btn ="txt_fecha_i";
                new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                break;
            case R.id.btn_fecha2Reporte:
                nombre_btn ="txt_fecha_f";
                new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                break;

            case R.id.btn_buscar:

                tably_listadoabonos.removeAllViews();
                mostarCabecera();
                txt_totalRecaudado.setVisibility(View.GONE);
                if(!btn_fechaReporte.getText().toString().isEmpty()){
                    precioTotal =0;

                    dc = new DataConnection(this,"listarEstadisticasGeneral",new Reserva(id_empresa,btn_fechaReporte.getText().toString() ,btn_fecha2Reporte.getText().toString() ),false);
                    new GetReporte().execute();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Debe Seleccionar una fecha",Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void mostarCabecera(){
        View v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_reportes_cabecera,null,false);
        tably_listadoabonos.addView(v);
    }
    public class GetReporte extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            arrayreserva = dc.getLitadoEstadisticasGeneral();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
       //     progressbar.setVisibility(ProgressBar.INVISIBLE);
            txt_totalRecaudado.setVisibility(View.VISIBLE);
          // mostarCabecera();
            for(Reserva obj: arrayreserva){
                obj.setReserva_costo("40.00");
                View v1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_reportes_diarios,null,false);


                TextView txt_nombre_reservaR = v1.findViewById(R.id.txt_nombre_reservaR);
                TextView txt_nombreCanchaR = v1.findViewById(R.id.txt_nombreCanchaR);
                TextView txt_hora_reservaR = v1.findViewById(R.id.txt_hora_reservaR);
                TextView txt_costo_reservaR = v1.findViewById(R.id.txt_costo_reservaR);


                txt_nombre_reservaR.setText(""+obj.getReserva_nombre());
                txt_nombreCanchaR.setText(""+obj.getCancha_nombre());
                txt_hora_reservaR.setText(""+obj.getReserva_hora());
                txt_costo_reservaR.setText(""+obj.getReserva_costo());

                tably_listadoabonos.addView(v1);

            }








            for(Reserva reserva : arrayreserva) {
                reserva.setReserva_costo("40.00");
                precioTotal += Double.parseDouble(reserva.getReserva_costo());

            }

            //  Toast.makeText(context,"total"+precioTotal,Toast.LENGTH_SHORT).show();

            txt_totalRecaudado.setText("TOTAL : S/ "+precioTotal);



          /*  if (arrayreserva.size() > 0) {
                cdv_mensaje.setVisibility(View.INVISIBLE);
            } else {
                cdv_mensaje.setVisibility(View.VISIBLE);
            }*/
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year +"-" + (monthOfYear+1) + "-" +dayOfMonth );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        if (nombre_btn.equals("txt_fecha_i")){
            btn_fechaReporte.setText(outDate);
        }
        else {
            btn_fecha2Reporte.setText(outDate);
    }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            case R.id.action_btn_pdf :
                Log.d("size"," "+llscrol.getWidth() +"  "+llscrol.getWidth());
                bitmap = loadBitmapFromView(llscrol, llscrol.getWidth(), llscrol.getMeasuredHeight());
                createPdf();
                //Toast.makeText(getApplicationContext(),"Reporte",Toast.LENGTH_LONG).show();

                return true;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_btn_pdf_email, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
        }
        PdfDocument.PageInfo pageInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        }
        PdfDocument.Page page = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            page = document.startPage(pageInfo);
        }

        Canvas canvas = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            canvas = page.getCanvas();
        }

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document.finishPage(page);
        }

        // write the document content
        File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","reportes");
        carpetas.mkdirs();

        String aleatorio = "DOC"+MenuPrincipal.usuario_id+"_"+fechaHora ;
        String nombre = aleatorio +".pdf";

        File file = new File(carpetas,nombre);

        //String targetPdf = "/sdcard/pdffromScroll.pdf";
        //File filePath;
        //filePath = new File(targetPdf);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                document.writeTo(new FileOutputStream(file));
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            document.close();
        }
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){

        File carpetas = new File(Environment.getExternalStorageDirectory() + "/Capitan/","reportes");
        carpetas.mkdirs();

        String aleatorio = "DOC"+MenuPrincipal.usuario_id+"_"+ fechaHora;
        String nombre = aleatorio +".pdf";

        File file = new File(carpetas,nombre);
        //File file = new File("/sdcard/pdffromScroll.pdf");
        if (file.exists())
        {
            try
            {
            Intent target=new Intent(Intent.ACTION_VIEW);

            Uri uri;

            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                String authorities=getApplicationContext().getPackageName()+".provider";
                uri = FileProvider.getUriForFile(getApplicationContext(),authorities,file);
            } else{
                uri = Uri.fromFile(new File(String.valueOf(file)));
            }

            target.setDataAndType(uri, "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Intent intent = Intent.createChooser(target, "Abrir PDF");



            startActivity(intent);

            }
            catch(ActivityNotFoundException e)
            {
                Log.e("pdf", "openGeneratedPDF: "+e );
                Toast.makeText(ReporteDiario2.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }




}
