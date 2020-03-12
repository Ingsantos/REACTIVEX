package br.digitalhouse.padraoarquitetura.viewmodel;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.digitalhouse.padraoarquitetura.model.Produto;
import br.digitalhouse.padraoarquitetura.repository.ProdutoRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

///Pode herdar de viewModel(Não tem acesso a context) ou androidViewModel (não é recomendado usar, mas pode)
public class ProdutoViewModel extends AndroidViewModel {

    private ProdutoRepository repository = new ProdutoRepository();
    //Atributo de Leitura e escrita
    private MutableLiveData<List<Produto>> mutableProduto = new MutableLiveData<>();
    //Atributo apenas de leitura - a view só ver por esse cara, não modifica
    public LiveData<List<Produto>> liveDataProduto = mutableProduto;
    //Gerenciamento de thread -> para dar o add e limpar
    private CompositeDisposable disposable = new CompositeDisposable();


    public ProdutoViewModel(@NonNull Application application) {
        super(application);
    }
    //Flowable é o observable -> quem chama ele direto é o repository, o repository retorna o mesmo tipo do DAO
    //CONSULTA NO BANCO DE DADOS repository.getAllProdutos(context)
    // AONDE VAI ACONTECER A EMISSÃO DE DADOS subscribeOn(Schedulers.newThread()
    //QUANDO TERMINAR A EMISSÃO DE DADOS PARA ONDE VAI observeOn(AndroidSchedulers.mainThread())

    public void getTodosProdutos(Context context){
        disposable.add(repository.getAllProdutos(context).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(produto -> {
                    mutableProduto.setValue(produto);
                }, throwable -> {
                    Log.i("Erro", throwable.getMessage());
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //Limpa a thread logo após a finalização dos processos
        disposable.clear();
    }
}
