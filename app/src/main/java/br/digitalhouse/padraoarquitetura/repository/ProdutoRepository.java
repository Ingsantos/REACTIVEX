package br.digitalhouse.padraoarquitetura.repository;

import android.content.Context;
import android.view.contentcapture.ContentCaptureCondition;

import java.util.List;

import br.digitalhouse.padraoarquitetura.model.Produto;
import br.digitalhouse.padraoarquitetura.repository.data.ProdutoDataBase;
import io.reactivex.Flowable;

public class ProdutoRepository {

    //SEMPRE VAI RETORNAR O MESMO TIPO DE DADO DO DAO OU DA API
    public Flowable<List<Produto>> getAllProdutos(Context context){
        return ProdutoDataBase.getDatabase(context).produtoDAO().todosOsProdutosBd();
    }

}
