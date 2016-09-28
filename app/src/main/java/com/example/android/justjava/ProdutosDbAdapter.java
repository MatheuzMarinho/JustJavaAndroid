package com.example.android.justjava;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

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
