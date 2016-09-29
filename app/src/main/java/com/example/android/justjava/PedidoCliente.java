package com.example.android.justjava;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Hellow on 27/09/2016.
 */
public class PedidoCliente extends Activity {
    private String nome_cliente;
    private ListView listView;
    private ArrayList<Pedido> lista_dos_pedidos= new ArrayList<>();
    private ListarProdutoAdapter adapter_pedido;
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Pedidos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_cliente);
        listView = (ListView) findViewById(R.id.listViewPedidoCliente);
        nome_cliente = getIntent().getStringExtra("Chave");

        adapter_pedido = new ListarProdutoAdapter(this, lista_dos_pedidos);
        listView.setAdapter(adapter_pedido);

        exemplo_simples();

        dataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator i = dataSnapshot.getChildren().iterator();
                Pedido pedido ;
                while (i.hasNext()) {
                    pedido = ((DataSnapshot)i.next()).getValue(Pedido.class);
                    if(pedido.nome_cliente!=null) {
                        if ((pedido.nome_cliente).equals(nome_cliente))
                            adapter_pedido.add(pedido);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                Pedido pedido ;
                adapter_pedido.clear();
                while (i.hasNext()) {
                    pedido = ((DataSnapshot)i.next()).getValue(Pedido.class);
                    if(pedido.nome_cliente!=null) {
                        if ((pedido.nome_cliente).equals(nome_cliente))
                            adapter_pedido.add(pedido);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private AlertDialog alerta;

    private void exemplo_simples() {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(nome_cliente);
        builder.setMessage("Acompanhe o andamento do seu pedido!");


        builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {


            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
