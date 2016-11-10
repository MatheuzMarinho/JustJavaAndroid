package com.example.android.justjava;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Created by Hellow on 08/11/2016.
 */
public class ServicoTeste extends Service implements Runnable {
    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference().child("Produtos");
    private ProdutoSQLadapter BD;

    @Override
    public void onCreate() {
        Thread thread = new Thread(this);
        BD = new ProdutoSQLadapter(this);
        thread.start();
        Log.d("Alo","Servico Iniciado");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("Alo","Servico Destruido");
        super.onDestroy();
    }

    private void inicializarBD() {



        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();
                BD.deleteTodosSQLProduto();

                while (i.hasNext()) {

                    //adapter.add(((DataSnapshot)i.next()).getValue(Produto.class));
                    BD.inserirSQLProduto(((DataSnapshot) i.next()).getValue(Produto.class));

                }
                stopSelf();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void run() {
        inicializarBD();
    }
}
