package com.example.android.justjava;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
 * Created by Hellow on 20/09/2016.
 */
public class ListarProduto extends Activity {

    private Produto p = new Produto();
    private ArrayList<Produto> lista_de_produtos= new ArrayList<>();
    private ListView listView;
    ProdutoAdapter adapter;
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Produtos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_produtos);
        listView = (ListView) findViewById(R.id.listView);

        adapter = new ProdutoAdapter(this, lista_de_produtos);
        listView.setAdapter(adapter);

        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                p = new Produto();
                while (i.hasNext()) {

                    adapter.add(((DataSnapshot)i.next()).getValue(Produto.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                exemplo_simples(position);

            }
        });


    }

    private AlertDialog alerta;

    private void exemplo_simples(final int posicao) {
        p = lista_de_produtos.get(posicao);


        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(p.nome);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.selecao_cardapio, null);
        TextView preco_tv = (TextView) view.findViewById(R.id.tv_preco);
        preco_tv.setText("Preço R$: "+p.preco.toString());
        TextView descricao_tv = (TextView) view.findViewById(R.id.tv_descricao);
        descricao_tv.setText("Descrição do Produto: "+p.descricao.toString());

        builder.setView(view);

        builder.setPositiveButton("Pedir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                /*
                status = true;
                dataBase.child(chave).child("status").setValue(status);
                lista_de_produtos.set(posicao,nome_produto + " : " + status);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(listarProdutos.this, "Tem!", Toast.LENGTH_SHORT).show();
                */
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                /*
                status = false;
                dataBase.child(chave).child("status").setValue(status);
                lista_de_produtos.set(posicao,nome_produto + " : " + status);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(listarProdutos.this, "Não tem!", Toast.LENGTH_SHORT).show();
                */
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }



}
