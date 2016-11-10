package com.example.android.justjava;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
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
import java.util.List;

import android.view.View.OnClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardapioFragment extends Fragment {
    private Produto p = new Produto();
    private ArrayList<Produto> lista_de_produtos= new ArrayList<>();
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
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Cardápio");

        FloatingActionButton fab = (FloatingActionButton)  getActivity().findViewById(R.id.fab);
        fab.hide();

        final ProdutoSQLadapter BD = new ProdutoSQLadapter(getContext());

        // Pegar Lista do BD
        atualizarBD(BD);

        adapter = new ProdutoAdapter(getContext(), lista_de_produtos);
        listView.setAdapter(adapter);


        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                adapter.clear();
                while (i.hasNext()) {

                    adapter.add((((DataSnapshot)i.next()).getValue(Produto.class)));
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

    private void atualizarBD(ProdutoSQLadapter BD){
            while(BD.buscarSQLProduto()== null){

            }
            lista_de_produtos = BD.buscarSQLProduto();
    }

    private AlertDialog alerta;


    private void  visualizar_produto(final int posicao) {
        final TelaPrincipal master = (TelaPrincipal) getActivity();
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
                master.pedido_atual.setValor_total(p.getPreco());
                master.lista_do_pedido_atual.add(p);


                Toast.makeText(getContext(), "Produto : "+p.nome+" Adicionado!", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction() ;
                fragmentTransaction.replace(R.id.fragment_container, new PedidoAtualFragment());
                fragmentTransaction.commit();



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



}
