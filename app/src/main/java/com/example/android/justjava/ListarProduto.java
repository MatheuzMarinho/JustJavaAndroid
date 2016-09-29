package com.example.android.justjava;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    private Pedido pedido = new Pedido();
    private ArrayList<Produto> lista_de_produtos= new ArrayList<>();
    private ArrayList<Produto> lista_do_pedido= new ArrayList<>();
    private ListView listView;
    private ProdutoAdapter adapter;
    private ProdutoAdapter adapter_pedido;
    private EditText nome;
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Produtos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_produtos);
        listView = (ListView) findViewById(R.id.listView);

        inserirNome();


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




    public void clickFinalizarPedido(View view){
        pedido.setItem_pedido(lista_do_pedido);

        finalizar_pedido();

    }




    private AlertDialog alerta;


    private void inserirNome() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.nome_cliente,null);
        nome = (EditText) view.findViewById(R.id.nomePessoa);
        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.setNome).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //exibe um Toast informativo.
                if (nome.getText().toString().isEmpty())
                    Toast.makeText(ListarProduto.this, "Informe seu nome!", Toast.LENGTH_LONG).show();

                else {
                    pedido.nome_cliente = nome.getText().toString();
                    //desfaz o alerta.
                    alerta.dismiss();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira seu nome");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }


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

                pedido.setValor_total(p.getPreco());
                lista_do_pedido.add(p);
                Toast.makeText(ListarProduto.this, "Produto : "+p.nome+" Adicionado!", Toast.LENGTH_SHORT).show();
                listView.getChildAt(posicao).setBackgroundColor(Color.parseColor("#4CAF50"));
            }
        });

        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void finalizar_pedido(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle("Seu Pedido. Total: "+pedido.getValor_total()+" Reais");

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.finalizar_pedido, null);
        TextView preco_tv = (TextView) view.findViewById(R.id.tv_preco);
        ListView listViewPedido = (ListView) view.findViewById(R.id.listViewPedido);

        adapter_pedido = new ProdutoAdapter(this,lista_do_pedido);
        listViewPedido.setAdapter(adapter_pedido);

        builder.setView(view);
        builder.setPositiveButton("Pedir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    if(pedido.item_pedido.isEmpty())
                        Toast.makeText(ListarProduto.this,"Por Favor, faça um Pedido", Toast.LENGTH_SHORT).show();
                        else {
                        PedidosDb.inserirDb(pedido);
                        Intent intent = new Intent();
                        intent.setClass(ListarProduto.this, PedidoCliente.class);
                        intent.putExtra("Chave", pedido.nome_cliente.toString());
                        startActivity(intent);
                    }
                    }
                });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });



        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();



    }



}
