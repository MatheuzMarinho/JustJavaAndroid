package com.example.android.justjava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hellow on 26/09/2016.
 */
public class ListarPedidos extends Activity {

    private ListView listView;
    private ArrayList<Pedido> lista_dos_pedidos= new ArrayList<>();
    private ListarProdutoAdapter adapter_pedido;
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Pedidos");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_pedidos);
        listView = (ListView) findViewById(R.id.listViewPedidos);

        adapter_pedido = new ListarProdutoAdapter(this, lista_dos_pedidos);
        listView.setAdapter(adapter_pedido);

        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {

                    adapter_pedido.add(((DataSnapshot)i.next()).getValue(Pedido.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}
