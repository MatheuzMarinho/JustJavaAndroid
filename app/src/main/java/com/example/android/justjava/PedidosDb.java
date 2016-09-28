package com.example.android.justjava;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Hellow on 26/09/2016.
 */
public class PedidosDb {
    static private DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Pedidos");
    static private String chave;


    public PedidosDb() {

    }

    static public void inserirDb(Pedido p){
        chave = dataBase.push().getKey();
        p.setChave(chave);
        dataBase.child(chave).setValue(p);
    }
}
