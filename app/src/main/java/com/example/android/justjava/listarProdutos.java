package com.example.android.justjava;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Hellow on 13/09/2016.
 */
public class listarProdutos extends Activity {
    private String nome_produto,chave_produto;
    private Boolean status_produto,status;
    private Produto p;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> lista_de_produtos = new ArrayList<>();
    private ArrayList<String> lista_chaves = new ArrayList<>();
    private ArrayList<String> lista_nome = new ArrayList<>();
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("ProdutosNovo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_produtos);
        Button botao_inicial= (Button) findViewById(R.id.botao_inicial);

        botao_inicial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(listarProdutos.this,
                       TelaInicial.class);

                startActivity(intent);

                finish();
            }
        });







        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista_de_produtos);
        listView.setAdapter(arrayAdapter);


        dataBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    chave_produto = (String) ((DataSnapshot) i.next()).getValue();
                    nome_produto = (String) ((DataSnapshot) i.next()).getValue();
                    status_produto = (Boolean) ((DataSnapshot) i.next()).getValue();
                    lista_chaves.add(chave_produto);
                    lista_nome.add(nome_produto);
                    lista_de_produtos.add(nome_produto + " : " + status_produto);

                }


                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chave = lista_chaves.get(position);
                String nome = lista_nome.get(position);
                exemplo_simples(chave,nome,position);
                Toast.makeText(listarProdutos.this, "-> " + chave, Toast.LENGTH_LONG).show();
            }
        });

    }

    private AlertDialog alerta;

    private void exemplo_simples(final String chave,final String nome_produto,final int posicao) {

        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(nome_produto);
        //define a mensagem
        builder.setMessage("Alterar Disponibilidade");
        //define um botão como positivo
        builder.setPositiveButton("Tem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                status = true;
                dataBase.child(chave).child("status").setValue(status);
                lista_de_produtos.set(posicao,nome_produto + " : " + status);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(listarProdutos.this, "Tem!", Toast.LENGTH_SHORT).show();

            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não Tem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                status = false;
                dataBase.child(chave).child("status").setValue(status);
                lista_de_produtos.set(posicao,nome_produto + " : " + status);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(listarProdutos.this, "Não tem!", Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


}

/*
    Iterator i = dataSnapshot.getChildren().iterator();
    list_produtos.clear();
    while(i.hasNext()){
        nome_produto = (String) ((DataSnapshot)i.next()).getValue();
        list_produtos.add(nome_produto);
        status_produto = (Boolean) ((DataSnapshot)i.next()).getValue();
    }

    arrayAdapter.notifyDataSetChanged();
*/