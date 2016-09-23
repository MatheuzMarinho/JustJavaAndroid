package com.example.android.justjava;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Hellow on 15/09/2016.
 */
public class ProdutosDbAdapter {
    private DatabaseReference dataBase;
    private String chave;

    public ProdutosDbAdapter() {
        this.dataBase = FirebaseDatabase.getInstance().getReference().child("Produtos");
    }

    public void inserirDb(Produto p){
        chave = dataBase.push().getKey();
        p.setChave(chave);
        dataBase.child(chave).setValue(p);
    }

}
