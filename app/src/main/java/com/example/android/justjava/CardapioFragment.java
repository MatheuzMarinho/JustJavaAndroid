package com.example.android.justjava;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import android.view.View.OnClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardapioFragment extends Fragment {
    private Produto p = new Produto();
    private Pedido pedido = new Pedido();
    private ArrayList<Produto> lista_de_produtos= new ArrayList<>();
    private ArrayList<Produto> lista_do_pedido= new ArrayList<>();
    private ListView listView;
    private ProdutoAdapter adapter;
    private ProdutoAdapter adapter_pedido;
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Produtos");


    public CardapioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listar_produtos, container,false);
        listView = (ListView) view.findViewById(R.id.listView);
        final TelaPrincipal master = (TelaPrincipal) getActivity();


        Button mButton = (Button) view.findViewById(R.id.btFinalizarPedido);
        mButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pedido.setItem_pedido(lista_do_pedido);
                pedido.nome_cliente = master.nome_cliente2;
                finalizar_pedido();

            }
        });

        adapter = new ProdutoAdapter(getContext(), lista_de_produtos);
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

                visualizar_produto(position);

            }
        });







        return view;

    }




    private AlertDialog alerta;


    private void  visualizar_produto(final int posicao) {
        p = lista_de_produtos.get(posicao);


        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //define o titulo
        builder.setTitle(p.nome);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
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
                Toast.makeText(getContext(), "Produto : "+p.nome+" Adicionado!", Toast.LENGTH_SHORT).show();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //define o titulo
        builder.setTitle("Seu Pedido. Total: "+pedido.getValor_total()+" Reais");

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.finalizar_pedido, null);
        TextView preco_tv = (TextView) view.findViewById(R.id.tv_preco);
        ListView listViewPedido = (ListView) view.findViewById(R.id.listViewPedido);

        adapter_pedido = new ProdutoAdapter(getActivity(),lista_do_pedido);
        listViewPedido.setAdapter(adapter_pedido);

        builder.setView(view);
        builder.setPositiveButton("Pedir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(pedido.item_pedido.isEmpty())
                    Toast.makeText(getContext(),"Por Favor, faça um Pedido", Toast.LENGTH_SHORT).show();
                else {
                    PedidosDb.inserirDb(pedido);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction() ;
                    fragmentTransaction.replace(R.id.fragment_container, new PedidoClienteFragment());
                    fragmentTransaction.commit();


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
