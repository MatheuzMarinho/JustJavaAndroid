package com.example.android.justjava;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
public class PedidoClienteFragment extends Fragment  {

    private PedidoClienteAdapter adpter;
    private ListView listView;
    private ArrayList<Pedido> lista_dos_pedidos= new ArrayList<>();
    private ProdutoAdapter adapter_item_pedido;
    private ArrayList<Produto> lista_do_pedido= new ArrayList<>();
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Pedidos");
    String nome_cliente ;
    public PedidoClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TelaPrincipal master = (TelaPrincipal) getActivity();
        nome_cliente = master.nome_cliente2;
        View view = inflater.inflate(R.layout.pedido_cliente, container,false);
        listView = (ListView) view.findViewById(R.id.listViewPedidoCliente);

        FloatingActionButton fab = (FloatingActionButton)  getActivity().findViewById(R.id.fab);
        fab.hide();

        adpter = new PedidoClienteAdapter(getContext(), lista_dos_pedidos);
        listView.setAdapter(adpter);

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
                            adpter.add(pedido);
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
                adpter.clear();
                while (i.hasNext()) {
                    pedido = ((DataSnapshot)i.next()).getValue(Pedido.class);
                    if(pedido.nome_cliente!=null) {
                        if ((pedido.nome_cliente).equals(nome_cliente))
                            adpter.add(pedido);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                visualizar_pedido(position);
            }
        });






        return view;
    }


    private AlertDialog alerta;

    private void exemplo_simples() {


        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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



    private void visualizar_pedido(final int posicao){
        Pedido pedido;
        pedido = lista_dos_pedidos.get(posicao);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //define o titulo
        builder.setTitle("Seu Pedido");

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.finalizar_pedido, null);
        ListView listViewPedido = (ListView) view.findViewById(R.id.listViewPedido);

        lista_do_pedido = pedido.getItem_pedido();

        adapter_item_pedido = new ProdutoAdapter(getActivity(),lista_do_pedido);
        listViewPedido.setAdapter(adapter_item_pedido);

        builder.setView(view);
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