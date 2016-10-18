package com.example.android.justjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hellow on 20/09/2016.
 */
public class ProdutoAdapter extends ArrayAdapter <Produto> {

    public ProdutoAdapter (Context context, ArrayList<Produto> produtos) {
        super(context, 0, produtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Produto produto = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listar_produto
                    , parent, false);
        }
        // Lookup view for data population
        TextView tvNome = (TextView) convertView.findViewById(R.id.tv_nome);
       TextView tvPreco = (TextView) convertView.findViewById(R.id.tv_preco);
        // Populate the data into the template view using the data object
        tvNome.setText(produto.nome);

        tvPreco.setText("  Preço R$: "+produto.preco.toString());
        // Return the completed view to render on screen
        return convertView;
    }


}
