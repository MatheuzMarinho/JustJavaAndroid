package com.example.android.justjava;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        Button btStatus = (Button) convertView.findViewById(R.id.btStatus);

        tvNome.setText(pedido.nome_cliente);
        if(pedido.status_do_pedido == 0) {
            btStatus.setText("Pendente");
            btStatus.setBackgroundColor(Color.parseColor("#9E9E9E"));
        }
        else if (pedido.status_do_pedido == 1) {
            btStatus.setText("Em andamento");
            btStatus.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        else {
            btStatus.setText("Pronto! :D");
            btStatus.setBackgroundColor(Color.parseColor("#4CAF50"));
        }


        return convertView;
    }
}
