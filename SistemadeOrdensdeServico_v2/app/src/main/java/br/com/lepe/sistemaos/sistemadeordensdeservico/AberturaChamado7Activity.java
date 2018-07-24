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

public class AberturaChamado7Activity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private ListView equipamentos; //Objeto ListView
    private SearchView pesquisar; //Caixa de pesquisa por empresa
    private Filter filtro; //Filtro para empresas

    //Campos do chamado
    private String assunto; //Assunto informado
    private String sessao; //Sessão informada
    private String descricao; //Descrição informada
    private String empresa; //Empresa informada
    private String categoria; //Categoria informada
    private String solicitante; //Solicitante informado
    private String analista; //Analista informado
    private String computador; //Computador informado
    private String equipamento; //Equipamento informado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_chamado7);

        //Recebendo dados da tela anterior
        Intent newIntent = getIntent();
        Bundle dados = newIntent.getExtras();
        assunto = dados.getString("assunto");
        sessao = dados.getString("sessao");
        descricao = dados.getString("descricao");
        empresa = dados.getString("empresa");
        categoria = dados.getString("categoria");
        solicitante = dados.getString("solicitante");
        analista = dados.getString("analista");
        computador = dados.getString("computador");

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();

        //Equipamentos
        equipamentos = (ListView) findViewById(R.id.lstOpcoes);
        ArrayList<String> opcaoEquipamentos = osbd.getConsultarCompEquip(); //Obtém a lista de equipamentos existentes
        final ArrayAdapter<String> listaEquipamentos = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, opcaoEquipamentos);
        equipamentos.setAdapter(listaEquipamentos);
        equipamentos.requestFocus();

        //Ação executada ao selecionar um equipamento no ListView
        equipamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                equipamento = listaEquipamentos.getItem(i);
                Intent intent = new Intent(AberturaChamado7Activity.this, AberturaChamado8Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("assunto", assunto);
                bundle.putString("sessao", sessao);
                bundle.putString("descricao", descricao);
                bundle.putString("empresa", empresa);
                bundle.putString("categoria", categoria);
                bundle.putString("solicitante", solicitante);
                bundle.putString("analista", analista);
                bundle.putString("computador", computador);
                bundle.putString("equipamento", equipamento);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        //Filtra a lista de equipamentos de acordo com a pesquisa do usuário
        filtro = listaEquipamentos.getFilter();

        //SearchView
        pesquisar = (SearchView) findViewById(R.id.schPesquisa);
        pesquisar.setIconifiedByDefault(false);
        pesquisar.setQueryHint("Buscar...");
        pesquisar.setOnQueryTextListener(this);

        //Lista o equipamento ao abrir a tela de acordo com o computador selecionado
        onQueryTextChange(computador);
    }

    //Ação do botão prosseguir do SearchView (Equivalente a ação da tecla ENTER)
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //Pesquisar computadores no ListView
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
