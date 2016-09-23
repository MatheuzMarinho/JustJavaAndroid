package com.example.android.justjava;


/**
 * Created by Hellow on 12/09/2016.
 */
public class Produto {
    public String chave;
    public String nome;
    public String descricao;
    public Double preco;
    public int quantidade;



    public Produto() {
    }

    public Produto(String nome, String descricao, Double preco,int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getChave(){
        return chave;
    }
    public void setChave(String chave){
        this.chave = chave;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public double getPreco(){
        return preco;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }


}