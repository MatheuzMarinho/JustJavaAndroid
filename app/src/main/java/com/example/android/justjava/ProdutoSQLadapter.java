package com.example.android.justjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hellow on 07/11/2016.
 */
public class ProdutoSQLadapter {
    private SQLiteDatabase bd;

    public ProdutoSQLadapter(Context context){
        ProdutoBancoSQL auxBD= new ProdutoBancoSQL(context);
        bd = auxBD.getWritableDatabase();
    }


    public void inserirSQLProduto(Produto produto){
        ContentValues valores = new ContentValues();
        valores.put ("chave",produto.chave);
        valores.put ("nome",produto.nome);
        valores.put ("descricao",produto.descricao);
        valores.put ("preco",produto.preco);
        valores.put ("quantidade",produto.quantidade);
        bd.insert("Produto","teste",valores);


    }

    public void deleteTodosSQLProduto(){
        bd.delete("Produto",null,null);

    }

    public Produto retornarInserido(){
            Produto produto = new Produto();
        String [] colunas = new String[]{"chave","nome","descricao","preco","quantidade"};
        Cursor cursor = bd.query("Produto",colunas,null,null,null,null,null);
        cursor.moveToLast();
        produto.setChave(cursor.getString(0));
        produto.setNome(cursor.getString(1));
        produto.setDescricao(cursor.getString(2));
        produto.setPreco(cursor.getDouble(3));
        produto.setQuantidade(cursor.getInt(4));

        return produto;
    }

    public ArrayList<Produto> buscarSQLProduto() {
        ArrayList<Produto> list = new ArrayList<Produto>();
        String [] colunas = new String[]{"chave","nome","descricao","preco","quantidade"};
        Cursor cursor = bd.query("Produto",colunas,null,null,null,null,null);

        if(cursor.getCount()>0){
        cursor.moveToFirst();
            do{
            Produto produto = new Produto();
                produto.setChave(cursor.getString(0));
                produto.setNome(cursor.getString(1));
                produto.setDescricao(cursor.getString(2));
                produto.setPreco(cursor.getDouble(3));
                produto.setQuantidade(cursor.getInt(4));

                list.add(produto);
            }while(cursor.moveToNext());

        }
        return(list);
    }


}
