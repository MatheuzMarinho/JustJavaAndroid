package com.example.android.justjava;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    public static final String cadCliente = "CadastroCliente";
    public String nome_cliente2= null;
    public Pedido pedido_atual = new Pedido();
    public ArrayList<Produto> lista_do_pedido_atual= new ArrayList<>();
    EditText nome;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        Intent intent = new Intent(getApplication(),ServicoTeste.class);
        startService(intent);

        SharedPreferences pref = getSharedPreferences(cadCliente, MODE_PRIVATE);
        nome_cliente2 = pref.getString("NomeCliente",null);
        if(nome_cliente2 == null)
        inserirNome();
        mTitle = "Cardápio";


        CardapioFragment fragment = new CardapioFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle(mTitle);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cardapio) {
            CardapioFragment fragment = new CardapioFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
            mTitle="Cardápio";
            getSupportActionBar().setTitle(mTitle);
        } else if (id == R.id.nav_meus_pedidos) {
            PedidoClienteFragment fragment = new PedidoClienteFragment();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
            mTitle="Meus Pedidos";
            getSupportActionBar().setTitle(mTitle);

        } else if (id == R.id.nav_pedido_atual) {

            PedidoAtualFragment fragment = new PedidoAtualFragment();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
            mTitle="Pedido Atual";
            getSupportActionBar().setTitle(mTitle);

        } else if (id == R.id.nav_cadastrar_produto) {
            CadastrarProdutoFragment fragment = new CadastrarProdutoFragment();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
            mTitle="Cadastrar Produto";
            getSupportActionBar().setTitle(mTitle);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private AlertDialog alerta;

    private void inserirNome() {


        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.nome_cliente,null);
        nome = (EditText) view.findViewById(R.id.nomePessoa);
        //definimos para o botão do layout um clickListener
        view.findViewById(R.id.setNome).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //exibe um Toast informativo.
                if (nome.getText().toString().isEmpty())
                    Toast.makeText(TelaPrincipal.this, "Informe seu nome!", Toast.LENGTH_LONG).show();

                else {
                    nome_cliente2 = nome.getText().toString().toUpperCase();
                    SharedPreferences settings = getSharedPreferences(cadCliente, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("NomeCliente",nome_cliente2);
                    editor.commit();

                    Toast.makeText(TelaPrincipal.this, "Seja Bem Vindo "+nome_cliente2, Toast.LENGTH_LONG).show();
                    //desfaz o alerta.
                    alerta.dismiss();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira seu nome");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }
}
