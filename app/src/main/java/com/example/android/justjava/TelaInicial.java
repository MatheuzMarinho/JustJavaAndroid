package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Avelino on 20/09/2016.
 */
public class TelaInicial extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial);
        Button cadProd = (Button) findViewById(R.id.cadProd);
        Button listProd = (Button) findViewById(R.id.listProd);
        Button listPed = (Button) findViewById(R.id.listPed);


        //Botão para Gerar view: Cadastrar Produtos
        cadProd.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(TelaInicial.this, CadastrarProduto.class);

                startActivity(intent);
            }
        });

        //Botão para Gerar view: Listar Produtos
        listProd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TelaInicial.this, ListarProduto.class);
                startActivity(intent);
            }
        });

        listPed.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(TelaInicial.this, ListarPedidos.class);

                startActivity(intent);
            }
        });

    }






}


