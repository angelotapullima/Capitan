package com.tec.bufeo.capitan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;

import com.tec.bufeo.capitan.R;

public class CalificarNegocios extends AppCompatActivity {

    RatingBar rtb_valor;
    Float valor_rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_negocios);

        rtb_valor=findViewById(R.id.rtb_valor);

        valor_rating = Float.parseFloat(getIntent().getExtras().getString("valor_rating"));

        rtb_valor.setRating(valor_rating);
    }
}
