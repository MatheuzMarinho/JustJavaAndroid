package com.example.android.justjava;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastrarProdutoFragment extends Fragment {
    private EditText nome;
    private EditText descricao;
    private EditText preco;
    private EditText quantidade;
    private ProdutosDbAdapter Bd = new ProdutosDbAdapter();

    public CadastrarProdutoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cadastrar_produto, container,false);
        nome = (EditText) view.findViewById(R.id.nome_prod);
        descricao = (EditText) view.findViewById(R.id.desc_prod);
        preco = (EditText) view.findViewById(R.id.preco_prod);
        quantidade = (EditText) view.findViewById(R.id.quantidade_prod);

        FloatingActionButton fab = (FloatingActionButton)  getActivity().findViewById(R.id.fab);
        fab.hide();

        Button mButton = (Button) view.findViewById(R.id.btSalvar);
        mButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nome.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Informe o Produto!", Toast.LENGTH_LONG).show();
                else if (preco.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Informe o Preco!", Toast.LENGTH_LONG).show();
                else if (descricao.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Informe uma descricao!", Toast.LENGTH_LONG).show();
                else if (quantidade.getText().toString().isEmpty())
                    Toast.makeText(getActivity(), "Informe a Quantidade!", Toast.LENGTH_LONG).show();
                else {
                    Produto produto = new Produto(nome.getText().toString(), descricao.getText().toString()
                            , Double.parseDouble(preco.getText().toString()), Integer.parseInt(quantidade.getText().toString()));

                    Bd.inserirDb(produto);

                    Toast.makeText(getActivity(), produto.getNome() + " cadastrado!", Toast.LENGTH_LONG).show();

                    nome.setText("");
                    preco.setText("");
                    descricao.setText("");
                    quantidade.setText("");
                }
            }
        });


        return view;
    }

}
