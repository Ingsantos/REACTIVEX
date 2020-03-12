package br.digitalhouse.padraoarquitetura.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.digitalhouse.padraoarquitetura.R;
import br.digitalhouse.padraoarquitetura.model.Produto;
import br.digitalhouse.padraoarquitetura.view.adapter.ProdutoAdapter;
import br.digitalhouse.padraoarquitetura.view.interfaces.ProdutoListener;
import br.digitalhouse.padraoarquitetura.viewmodel.ProdutoViewModel;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProdutoListener {

    private TextInputLayout nome;
    private TextInputLayout quantidade;
    private FloatingActionButton botaoAdd;
    private FloatingActionButton botaoDeletar;
    private RecyclerView recyclerViewProdutos;
    private ProdutoAdapter adapter;
    private ProdutoViewModel viewModel;
    private List<Produto> listaProdutos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        //inicialização do viewModel
        //está falando qual viewModel que eu criei terá acesso aos metodos nativos da classe View Model
        viewModel = ViewModelProviders.of(this).get(ProdutoViewModel.class);

        adapter = new ProdutoAdapter(listaProdutos, this);
        //set do layout do recycler view
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProdutos.setAdapter(adapter);

        //requisição do dado
        viewModel.getTodosProdutos(this);

        //Por que lista?
        //observa a mudança
        viewModel.liveDataProduto.observe(this, produtos ->
                adapter.atualizaListaProduto(produtos));
    }

    private void initViews(){
        nome = findViewById(R.id.textInputLayoutNomeProduto);
        quantidade = findViewById(R.id.textInputLayoutQuantidade);
        botaoAdd = findViewById(R.id.floatingActionButtonAdd);
        botaoDeletar = findViewById(R.id.floatingActionButtonDelete);
        recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
    }

    @Override
    public void clickProduto(Produto produto) {

    }
}
