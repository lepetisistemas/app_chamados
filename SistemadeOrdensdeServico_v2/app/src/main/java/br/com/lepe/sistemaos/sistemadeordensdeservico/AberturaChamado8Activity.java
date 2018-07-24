package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AberturaChamado8Activity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private ListView prioridades; //Objeto ListView
    private SearchView pesquisar; //Caixa de pesquisa por empresa
    private Filter filtro; //Filtro para empresas
    private Button desfazer; //Desfaz o preenchimento de todos os campos
    //private Button salvar; //Salva um novo chamado no banco de dados

    private Date dataAtual; //Data atual
    private SimpleDateFormat dateFormat; //Formatação da data

    private String assunto; //Assunto informado
    private String sessao; //Sessão informada
    private String descricao; //Descrição informada
    private String empresa; //Empresa informada
    private String categoria; //Categoria informada
    private String solicitante; //Solicitante informado
    private String analista; //Analista informado
    private String computador; //Computador informado
    private String equipamento; //Equipamento informado
    private String prioridade; //Prioridade selecionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_chamado8);

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
        equipamento = dados.getString("equipamento");

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();

        //Prioridades
        prioridades = (ListView) findViewById(R.id.lstOpcoes);
        ArrayList<String> opcaoPrioridades = osbd.getConsultarPrioridades();
        //Obtém a lista de prioridades existentes
        final ArrayAdapter<String> listaPrioridades = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, opcaoPrioridades);
        prioridades.setAdapter(listaPrioridades);
        prioridades.requestFocus();

        //Ação executada ao selecionar um tipo de prioridade no ListView
        prioridades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Capturar a seleção de um item da lista
                prioridade = listaPrioridades.getItem(i);
                //Gravar os dados selecionados e voltar para a tela inicial do aplicativo
                final Context context = view.getContext();
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.salvar_chamado, null, false);
                //Data de Solicitação do chamado
                dataAtual = new Date(); //Data atual do sistema
                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //Formatação Dia/Mês/Ano - Horas/Minutos
                final TextView txtDataChamado = (TextView) formElementsView.findViewById(R.id.txtDataChamado);
                txtDataChamado.setText("DATA: " + dateFormat.format(dataAtual));
                //Assunto do chamado
                final TextView txtAssuntoChamado = (TextView) formElementsView.findViewById(R.id.txtAssuntoChamado);
                txtAssuntoChamado.setText("ASSUNTO: " + assunto);
                //Sessão/Relatório/Tipo do chamado
                final TextView txtSessaoChamado = (TextView) formElementsView.findViewById(R.id.txtSessaoChamado);
                txtSessaoChamado.setText("SESSÃO: " + sessao);
                //Empresa do chamado
                final TextView txtEmpresaChamado = (TextView) formElementsView.findViewById(R.id.txtEmpresaChamado);
                txtEmpresaChamado.setText("EMPRESA: " + empresa);
                //Categoria do chamado
                final TextView txtCategoriaChamado = (TextView) formElementsView.findViewById(R.id.txtCategoriaChamado);
                txtCategoriaChamado.setText("CATEGORIA: " + categoria);
                //Solicitante do chamado
                final TextView txtSolicitanteChamado = (TextView) formElementsView.findViewById(R.id.txtSolicitanteChamado);
                txtSolicitanteChamado.setText("SOLICITANTE: " + solicitante);
                //Analista do chamado
                final TextView txtAnalistaChamado = (TextView) formElementsView.findViewById(R.id.txtAnalistaChamado);
                txtAnalistaChamado.setText("ANALISTA: " + analista);
                //Computador do chamado
                final TextView txtComputadorChamado = (TextView) formElementsView.findViewById(R.id.txtComputadorChamado);
                txtComputadorChamado.setText("COMPUTADOR: " + computador);
                //Equipamento do chamado
                final TextView txtEquipamentoChamado = (TextView) formElementsView.findViewById(R.id.txtEquipamentoChamado);
                txtEquipamentoChamado.setText("EQUIPAMENTO: " + equipamento);
                //Prioridade do chamado
                final TextView txtPrioridadeChamado = (TextView) formElementsView.findViewById(R.id.txtPrioridadeChamado);
                txtPrioridadeChamado.setText("PRIORIDADE: " + prioridade);
                //Descrição do chamado
                final TextView txtDescricaoChamado = (TextView) formElementsView.findViewById(R.id.txtDescricaoChamado);
                txtDescricaoChamado.setText("DESCRIÇÃO: " + descricao);

                AlertDialog.Builder gravar = new AlertDialog.Builder(AberturaChamado8Activity.this);
                gravar.setView(formElementsView);
                gravar.setTitle("Gravar chamado...");
                gravar.setPositiveButton("Gravar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        osbd.setAssunto(assunto);
                        osbd.setSessao(sessao);
                        osbd.setProblema(descricao);
                        int idEmpresa = osbd.getConsultarIdEmpresa(empresa);
                        osbd.setEmpresaId(idEmpresa);
                        int idCategoria = osbd.getConsultarIdCategoria(categoria);
                        osbd.setCategoriaId(idCategoria);
                        int idSolicitante = osbd.getConsultarIdSolicitante(solicitante);
                        osbd.setSolicitanteId(idSolicitante);
                        int idAnalista = osbd.getConsultarIdAnalista(analista);
                        osbd.setAnalistaId(idAnalista);
                        int idComputador = osbd.getConsultarIdCompEquip(computador);
                        osbd.setComputadorId(idComputador);
                        int idEquipamento = osbd.getConsultarIdCompEquip(equipamento);
                        osbd.setEquipamentoId(idEquipamento);
                        int idPrioridade = osbd.getConsultarIdPrioridade(prioridade);
                        osbd.setPrioridadeId(idPrioridade);
                        //Inicia a gravação dos dados na tabela
                        osbd.salvar();
                        //Exibe uma mensagem de confirmação
                        Toast.makeText(getApplicationContext(), "Chamado Gravado com Sucesso!", Toast.LENGTH_SHORT).show();
                        //Volta para tela inicial de abertura de chamados
                        Intent intent = new Intent(AberturaChamado8Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                gravar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                gravar.show();
            }
        });

        //Filtra a lista de prioridades de acordo com a pesquisa do usuário
        filtro = listaPrioridades.getFilter();

        //SearchView
        pesquisar = (SearchView) findViewById(R.id.schPesquisa);
        pesquisar.setIconifiedByDefault(false);
        pesquisar.setQueryHint("Buscar...");
        pesquisar.setOnQueryTextListener(this);

        //Voltar para tela inicial de abertura de chamados sem gravar os dados selecionados
        desfazer = (Button) findViewById(R.id.btnDesfazer);
        desfazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AberturaChamado8Activity.this, AberturaChamado1Activity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    //Pesquisar prioridades no ListView
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
