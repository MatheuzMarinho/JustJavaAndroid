package com.example.android.justjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hellow on 26/09/2016.
 */
public class ListarProdutoAdapter extends ArrayAdapter<Pedido> {

    public ListarProdutoAdapter (Context context, ArrayList<Pedido> pedido) {
        super(context, 0, pedido);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Pedido pedido = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listar_pedidos
                    , parent, false);
        }
        // Lookup view for data population
        TextView tvNome = (TextView) convertView.findViewById(R.id.tvCliente);
        //  TextView tvDescricao = (TextView) convertView.findViewById(R.id.tvDescricao);
        // Populate the data into the template view using the data object
        tvNome.setText(pedido.chave);

        // tvDescricao.setText(produto.descricao);
        // Return the completed view to render on screen
        return convertView;
    }
}
