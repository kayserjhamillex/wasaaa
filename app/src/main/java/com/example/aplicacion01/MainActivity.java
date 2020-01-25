package com.example.aplicacion01;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicacion01.adapters.ContactoAdaptador;
import com.example.aplicacion01.helpers.QueueUtils;
import com.example.aplicacion01.models.Contacto;

import java.util.ArrayList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    ListView contactosList;
    ContactoAdaptador contactoAdaptador;

    QueueUtils.QueueObject queue = null;
    ArrayList<Contacto> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

Contacto nuevoRow = new Contacto( "bichito", "el menjor", "");
nuevoRow.id = 0;
nuevoRow.setLocal(this);
        Toast.makeText(this, "n registros:"+ nuevoRow.getAll(this).size(), Toast.LENGTH_SHORT).show();

        Button btnTipo = findViewById(R.id.btnTipo);
        btnTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Refrescar platos",
                        Toast.LENGTH_SHORT).show();
                items.clear();
                Contacto.injectContactsFromCloud(queue, items,
                        MainActivity.this,
                        "limeno");
            }
        });

        Button btnTipo2 = findViewById(R.id.nuevoitem);
        btnTipo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevo();
                contactoAdaptador.clear();
                Contacto alv = new Contacto("","","");
                contactoAdaptador.addAll(alv.getAll(MainActivity.this));
                contactoAdaptador.notifyDataSetChanged();
            }
        });

        contactosList = findViewById(R.id.contactosList);

        queue = QueueUtils.getInstance(this.getApplicationContext());

        items = new ArrayList<>();
        items = nuevoRow.getAll(this);
        //Contacto.injectContactsFromCloud(queue, items,
//                this, "huancaino");

        contactoAdaptador = new ContactoAdaptador(this,items, queue.getImageLoader());
        contactosList.setAdapter(contactoAdaptador);
        Contacto.sendRequestPOST(queue, this);
    }
    public void nuevo(){
        Contacto nuevoRow = new Contacto( "xhulsita", "forever", "");
        nuevoRow.id = 0;
        nuevoRow.setLocal(this);
        Toast.makeText(this, "n registros:"+ nuevoRow.getAll(this).size(), Toast.LENGTH_SHORT).show();
    }


    //esta wea refresca la lista
    public void refreshList(){
        if ( contactoAdaptador!= null ) {
            contactoAdaptador.notifyDataSetChanged();
        }
    }
    public void refresh2(){

    }
}
