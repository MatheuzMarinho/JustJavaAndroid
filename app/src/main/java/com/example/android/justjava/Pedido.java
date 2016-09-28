package com.example.android.justjava;

import java.util.ArrayList;

/**
 * Created by Hellow on 26/09/2016.
 */
public class Pedido {

    public String nome_cliente;
    public Double valor_total= 0.0;
    public ArrayList<Produto> item_pedido = new ArrayList<>();
    //public String item_pedido;
    public String chave;

    public Pedido(){

    }

    public String getNome_cliente(){
        return nome_cliente;
    }
    public void setNome_cliente(String nome_cliente){
        this.nome_cliente = nome_cliente;
    }

    public Double getValor_total(){
        return valor_total;
    }

    public void setValor_total(Double valor_produto){
        valor_total += valor_produto;
    }

    public ArrayList<Produto> getItem_pedido(){
        return item_pedido;
    }

   public void setItem_pedido(ArrayList<Produto> produto){
        this.item_pedido = produto;


    }
    public String getChave(){
        return chave;
    }
    public void setChave(String chave){
        this.chave = chave;
    }
}
