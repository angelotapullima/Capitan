package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.chip.Chip;
import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;
import com.tec.bufeo.capitan.Activity.ReviewsComentarios.ViewModels.ReseñasViewModel;
import com.tec.bufeo.capitan.R;
import com.tec.bufeo.capitan.Util.Preferences;

import java.util.List;

public class ReviewComentarios extends AppCompatActivity implements View.OnClickListener {


    ReseñasViewModel reseñasViewModel;
    Preferences preferences;
    RecyclerView rcv_reseñas;
    AdaptadorReseñas adaptadorReseñas;
    String id_empresa;
    ImageView back;
    Chip chipTodos,chip5,chip4,chip3,chip2,chip1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_comentarios);
        reseñasViewModel = ViewModelProviders.of(this).get(ReseñasViewModel.class);
        preferences= new Preferences(this);

        id_empresa= getIntent().getExtras().getString("id_empresa");
        initViews();
        setAdapter();
        cargarVista();
    }

    private void initViews() {
        rcv_reseñas= findViewById(R.id.rcv_reseñas);
        chipTodos= findViewById(R.id.chipTodos);
        chip1= findViewById(R.id.chip1);
        chip2= findViewById(R.id.chip2);
        chip3= findViewById(R.id.chip3);
        chip4= findViewById(R.id.chip4);
        chip5= findViewById(R.id.chip5);
        back= findViewById(R.id.back);


        chipTodos.setOnClickListener(this);
        chip1.setOnClickListener(this);
        chip2.setOnClickListener(this);
        chip3.setOnClickListener(this);
        chip4.setOnClickListener(this);
        chip5.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    private void setAdapter() {
        adaptadorReseñas= new AdaptadorReseñas(this, new AdaptadorReseñas.OnItemClickListener() {
            @Override
            public void onItemClick(Reseñas mequipos, String tipo, int position) {

            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        rcv_reseñas.setLayoutManager(linearLayoutManager);
        rcv_reseñas.setAdapter(adaptadorReseñas);
    }

    private void setValorAdapter(String valor){

        reseñasViewModel.getValor(valor).observe(this, new Observer<List<Reseñas>>() {
            @Override
            public void onChanged(List<Reseñas> reseñas) {

                    adaptadorReseñas.setWords(reseñas);

            }
        });
    }
    private void cargarVista() {
        reseñasViewModel.getAll(id_empresa,preferences.getToken()).observe(this, new Observer<List<Reseñas>>() {
            @Override
            public void onChanged(List<Reseñas> reseñas) {
                if (reseñas.size()>0){
                    adaptadorReseñas.setWords(reseñas);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.equals(chipTodos)){

            cargarVista();
        }else if (v.equals(chip1)){

            setValorAdapter("1");
        }else if (v.equals(chip2)){

            setValorAdapter("2");
        }else if (v.equals(chip3)){

            setValorAdapter("3");
        }else if (v.equals(chip4)){

            setValorAdapter("4");
        }else if (v.equals(chip5)){

            setValorAdapter("5");
        }else if (v.equals(back)){

            finish();
        }
    }
}
