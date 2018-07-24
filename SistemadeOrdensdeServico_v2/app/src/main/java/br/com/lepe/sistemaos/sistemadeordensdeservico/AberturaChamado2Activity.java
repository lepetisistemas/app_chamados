package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class AberturaChamado2Activity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private ListView empresas; //Lista de empresas
    private SearchView pesquisar; //Caixa de pesquisa por empresa
    private Filter filtro; //Filtro para empresas

    //Campos do chamado
    private String assunto; //Assunto informado
    private String sessao; //Sessão informada
    private String descricao; //Descrição informada
    private String empresa; //Empresa selecionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_chamado2);

        //Receber dados da tela anterior
        Intent newIntent = getIntent();
        Bundle dados = newIntent.getExtras();
        assunto = dados.getString("assunto");
        sessao = dados.getString("sessao");
        descricao = dados.getString("descricao");

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();

        //Lista de Empresas
        empresas = (ListView) findViewById(R.id.lstOpcoes);
        ArrayList<String> opcaoEmpresas = osbd.getConsultarEmpresas(); //Obtém a lista de empresas existentes
        //Atribui a lista de empresas para um ArrayAdapter padrão
        final ArrayAdapter<String> listaEmpresas = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, opcaoEmpresas);
        empresas.setAdapter(listaEmpresas); //Adiciona a lista de empresas ao ListView
        empresas.requestFocus();

        //Ação executada ao selecionar uma empresa no ListView
        empresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                empresa = listaEmpresas.getItem(i); //Captura o nome da empresa selecionada
                Intent intent = new Intent(AberturaChamado2Activity.this, AberturaChamado3Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("assunto", assunto);
                bundle.putString("sessao", sessao);
                bundle.putString("descricao", descricao);
                bundle.putString("empresa", empresa);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        //SearchView
        pesquisar = (SearchView) findViewById(R.id.schPesquisa);
        pesquisar.setIconifiedByDefault(false); //Exibe o SearchView sem personalização (padrão)
        pesquisar.setQueryHint("Buscar..."); //Exibe uma mensagem no SearchView
        pesquisar.setOnQueryTextListener(this); //Ativa a execução do SearchView

        //Filtra a lista de empresas de acordo com a pesquisa do usuário
        filtro = listaEmpresas.getFilter();
    }

    //Ação do botão prosseguir do SearchView (Equivalente a ação da tecla ENTER)
    @Override
    public boolean onQueryTextSubmit(String textoPesquisa) {
        return false;
    }

    //Pesquisar empresas no ListView
    @Override
    public boolean onQueryTextChange(String textoPesquisa) {
        if (TextUtils.isEmpty(textoPesquisa)) {
            filtro.filter(null);
        } else {
            filtro.filter(textoPesquisa);
        }
        return true;
    }


}
