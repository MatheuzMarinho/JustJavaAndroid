package com.example.android.justjava;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hellow on 14/10/2016.
 */
public class PedidoClienteAdapter extends ArrayAdapter<Pedido> {

    public PedidoClienteAdapter (Context context, ArrayList<Pedido> produtos) {
        super(context, 0, produtos);
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
        TextView tvStatusPedido = (TextView) convertView.findViewById(R.id.tv_status_pedido);
        TextView tvPreco = (TextView) convertView.findViewById(R.id.tv_preco);
        LinearLayout layout = (LinearLayout) convertView.findViewById((R.id.tv_bloco_pedido));

        if(pedido.status_do_pedido == 0) {
            tvStatusPedido.setText("Pendente");

            layout.setBackgroundColor(Color.parseColor("#9E9E9E"));
        }
        else if (pedido.status_do_pedido == 1) {
            tvStatusPedido.setText("Em andamento");
            layout.setBackgroundColor(Color.parseColor("#FFEB3B"));

        }
        else {
            tvStatusPedido.setText("Pronto! :D");
            layout.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        tvPreco.setText("Total do Pedido: R$ "+pedido.valor_total);

        // Return the completed view to render on screen
        return convertView;
    }

}
