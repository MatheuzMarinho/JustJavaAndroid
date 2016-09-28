package com.example.android.justjava;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hellow on 16/09/2016.
 */

public class CadastrarProduto extends Activity {
    private EditText nome;
    private EditText descricao;
    private EditText preco;
    private EditText quantidade;
    private ProdutosDbAdapter Bd = new ProdutosDbAdapter();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_produto);

        nome = (EditText) findViewById(R.id.nome_prod);
        descricao = (EditText) findViewById(R.id.desc_prod);
        preco = (EditText) findViewById(R.id.preco_prod);
        quantidade = (EditText) findViewById(R.id.quantidade_prod);

    }

    public void clickSalvar(View view) {

        if (nome.getText().toString().isEmpty())
            Toast.makeText(CadastrarProduto.this, "Informe o Produto!", Toast.LENGTH_LONG).show();
        else if (preco.getText().toString().isEmpty())
            Toast.makeText(CadastrarProduto.this, "Informe o Preco!", Toast.LENGTH_LONG).show();
        else if (descricao.getText().toString().isEmpty())
            Toast.makeText(CadastrarProduto.this, "Informe uma descricao!", Toast.LENGTH_LONG).show();
        else if (quantidade.getText().toString().isEmpty())
            Toast.makeText(CadastrarProduto.this, "Informe a Quantidade!", Toast.LENGTH_LONG).show();
        else {
            Produto produto = new Produto(nome.getText().toString(), descricao.getText().toString()
                    , Double.parseDouble(preco.getText().toString()),Integer.parseInt(quantidade.getText().toString()));

            Bd.inserirDb(produto);

            Toast.makeText(CadastrarProduto.this, produto.getNome()+" cadastrado!", Toast.LENGTH_LONG).show();

        }
    }

}