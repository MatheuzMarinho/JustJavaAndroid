package com.example.android.justjava;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoAtualFragment extends Fragment {

    private ListView listView;
    private ArrayList<Produto> lista_de_produtos= new ArrayList<>();
    private ProdutoAdapter adapter;

    public PedidoAtualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedido_atual, container,false);
        listView = (ListView) view.findViewById(R.id.listViewPedidoClienteAtual);
        final TelaPrincipal master = (TelaPrincipal) getActivity();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Pedido Atual");

        adapter = new ProdutoAdapter(getContext(),master.lista_do_pedido_atual);

        listView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction() ;
                fragmentTransaction.replace(R.id.fragment_container, new CardapioFragment());
                fragmentTransaction.commit();
            }
        });


        Button mButton = (Button) view.findViewById(R.id.btFinalizarPedido);
        mButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                master.pedido_atual.setItem_pedido(master.lista_do_pedido_atual);
                master.pedido_atual.nome_cliente = master.nome_cliente2;

                finalizar_pedido();

            }
        });


        return view;
    }

    private AlertDialog alerta;

    private void finalizar_pedido(){
        final TelaPrincipal master = (TelaPrincipal) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //define o titulo
        builder.setTitle("Total: R$ "+master.pedido_atual.valor_total+"!");

        builder.setPositiveButton("Pedir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(master.pedido_atual.item_pedido.isEmpty())
                    Toast.makeText(getContext(),"Por Favor, faça um pedido", Toast.LENGTH_SHORT).show();
                else {
                    PedidosDb.inserirDb(master.pedido_atual);
                    master.pedido_atual.item_pedido.clear();
                    master.pedido_atual.valor_total = 0.0;
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
