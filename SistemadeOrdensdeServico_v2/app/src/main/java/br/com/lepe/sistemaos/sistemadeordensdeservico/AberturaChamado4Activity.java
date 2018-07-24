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

public class AberturaChamado4Activity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private ListView solicitantes; //Objeto ListView
    private SearchView pesquisar; //Caixa de pesquisa por empresa
    private Filter filtro; //Filtro para empresas

    //Campos do chamado
    private String assunto; //Assunto informado
    private String sessao; //Sessão informada
    private String descricao; //Descrição informada
    private String empresa; //Empresa informada
    private String categoria; //Categoria informada
    private String solicitante; //Solicitante selecionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_chamado4);

        //Recebendo dados da tela anterior
        Intent newIntent = getIntent();
        Bundle dados = newIntent.getExtras();
        assunto = dados.getString("assunto");
        sessao = dados.getString("sessao");
        descricao = dados.getString("descricao");
        empresa = dados.getString("empresa");
        categoria = dados.getString("categoria");

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();

        //Solicitantes
        solicitantes = (ListView) findViewById(R.id.lstOpcoes);
        ArrayList<String> opcaoSolicitantes = osbd.getConsultarSolicitantes(); //Obtém a lista de solicitantes existentes
        final ArrayAdapter<String> listaSolicitantes = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, opcaoSolicitantes);
        solicitantes.setAdapter(listaSolicitantes);
        solicitantes.requestFocus();

        //Ação executada ao selecionar um solicitante no ListView
        solicitantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                solicitante = listaSolicitantes.getItem(i);
                Intent intent = new Intent(AberturaChamado4Activity.this, AberturaChamado5Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("assunto", assunto);
                bundle.putString("sessao", sessao);
                bundle.putString("descricao", descricao);
                bundle.putString("empresa", empresa);
                bundle.putString("categoria", categoria);
                bundle.putString("solicitante", solicitante);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        //Filtra a lista de solicitantes de acordo com a pesquisa do usuário
        filtro = listaSolicitantes.getFilter();

        //SearchView
        pesquisar = (SearchView) findViewById(R.id.schPesquisa);
        pesquisar.setIconifiedByDefault(false);
        pesquisar.setQueryHint("Buscar...");
        pesquisar.setOnQueryTextListener(this);
    }

    //Ação do botão prosseguir do SearchView (Equivalente a ação da tecla ENTER)
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //Pesquisar solicitantes no ListView
    @Override
    public boolean onQueryTextChange(String textoPesquisa) {
        if (TextUtils.isEmpty(textoPesquisa)) {
            filtro.filter(null);
        } else {
            filtro.filter(textoPesquisa);
        }
        return false;
    }
}
