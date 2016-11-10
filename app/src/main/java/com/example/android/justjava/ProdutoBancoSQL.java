package com.example.android.justjava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hellow on 07/11/2016.
 */
public class ProdutoBancoSQL extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ProdutoBD.db";
    public static final String NOME_TABELA = "Produto";
    public static final String COLUNA_CHAVE = "chave";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_PRECO = "preco";
    public static final String COLUNA_QUANTIDADE = "quantidade";


    public static final String SCRIPT_CRIACAO_TABELA_PRODUTO = "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_CHAVE + " TEXT," + COLUNA_NOME + " TEXT,"
            + COLUNA_DESCRICAO + " TEXT,"+ COLUNA_PRECO + " TEXT,"+ COLUNA_QUANTIDADE + " TEXT" + ")";

    public ProdutoBancoSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CRIACAO_TABELA_PRODUTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Produto");
        onCreate(db);
    }



}
