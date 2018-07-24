/*
    Data de Criação: 14/09/2017 - 17:16
    Desenvolvedor: Cristiano Rodrigues Rosa
    Projeto: Cadastro de chamados de TI - LEPE
 */
package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrincipalActivity extends AppCompatActivity {

    //Widgets e Variáveis
    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private TextView data; //Data exibida
    private Date dataAtual; //Data atual
    private SimpleDateFormat dateFormat; //Formatação da data
    private EditText assunto; //Assunto da OS
    private EditText sessao; //Sessão, relatório ou tipo (opcional)
    private Spinner empresas; //Empresas disponíveis
    private Spinner categorias; //Categorias disponíveis
    private Spinner solicitantes; //Solicitante da Ordem de Serviço
    private Spinner analistas; //Analistas de TI
    private Spinner computadores; //Computador do solicitante
    private Spinner equipamentos; //Equipamento com problema
    private Spinner prioridades; //Tipo de prioridade necessária
    private EditText problema; //Descrição do problema

    private Button salvar; //Botão gravar
    private Button desfazer; //Botão limpar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();

        //Data atual (apenas exibição no app)
        data = (TextView) findViewById(R.id.txtDatainicio);
        dataAtual = new Date(); //Data atual do sistema
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //Formatação Dia/Mês/Ano - Horas/Minutos
        data.setText(dateFormat.format(dataAtual)); //Aplicação da formatação na data do sistema

        //Descrição do asssunto
        assunto = (EditText)findViewById(R.id.edtAssunto);
        assunto.requestFocus();

        //Descrição da sessão, relatório ou tipo
        sessao = (EditText)findViewById(R.id.edtSessao);

        //Empresa
        empresas = (Spinner)findViewById(R.id.spnEmpresa);
        ArrayList<String> opcaoEmpresas = osbd.getConsultarEmpresas(); //Obtém a lista de empresas existentes
        String[] arrayEmpresas = opcaoEmpresas.toArray(new String[0]); //Recebe a lista de empresas existentes
        ArrayAdapter adaptadorEmpresas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayEmpresas); //Configura o adaptador do Spinner
        adaptadorEmpresas.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        empresas.setAdapter(adaptadorEmpresas); //Insere o adaptador no Spinner

        //Descrição da sessão, relatório ou tipo
        sessao = (EditText)findViewById(R.id.edtSessao);

        //Categoria
        categorias = (Spinner)findViewById(R.id.spnCategoria);
        ArrayList<String> opcaoCategorias = osbd.getConsultarCategorias(); //Obtém a lista de categorias existentes
        String[] arrayCategorias = opcaoCategorias.toArray(new String[0]); //Recebe a lista de categorias existentes
        ArrayAdapter adaptadorCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCategorias); //Configura o adaptador do Spinner
        adaptadorCategorias.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        categorias.setAdapter(adaptadorCategorias); //Insere o adaptador no Spinner

        //Solicitante
        solicitantes = (Spinner)findViewById(R.id.spnSolicitante);
        ArrayList<String> opcaoSolicitantes = osbd.getConsultarSolicitantes(); //Obtém a lista de solicitantes existentes
        String[] arraySolicitantes = opcaoSolicitantes.toArray(new String[0]); //Recebe a lista de solicitantes existentes
        ArrayAdapter adaptadorSolicitantes = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySolicitantes); //Configura o adaptador do Spinner
        adaptadorSolicitantes.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        solicitantes.setAdapter(adaptadorSolicitantes); //Insere o adaptador no Spinner

        //Analista
        analistas = (Spinner)findViewById(R.id.spnAnalista);
        ArrayList<String> opcaoAnalistas = osbd.getConsultarAnalistas(); //Obtém a lista de analistas existentes
        String[] arrayAnalistas = opcaoAnalistas.toArray(new String[0]); //Recebe a lista de analistas existentes
        ArrayAdapter adaptadorAnalistas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayAnalistas); //Configura o adaptador do Spinner
        adaptadorAnalistas.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        analistas.setAdapter(adaptadorAnalistas); //Insere o adaptador no Spinner

        //Computador
        computadores = (Spinner)findViewById(R.id.spnComputador);
        ArrayList<String> opcaoComputadores = osbd.getConsultarCompEquip(); //Obtém a lista de computadores/equipamentos existentes
        String[] arrayComputadores = opcaoComputadores.toArray(new String[0]); //Recebe a lista de computadores existentes
        ArrayAdapter adaptadorComputadores = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayComputadores); //Configura o adaptador do Spinner
        adaptadorComputadores.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        computadores.setAdapter(adaptadorComputadores); //Insere o adaptador no Spinner

        //Equipamento
        equipamentos = (Spinner)findViewById(R.id.spnEquipamento);
        ArrayList<String> opcaoEquipamentos = osbd.getConsultarCompEquip(); //Obtém a lista de computadores/equipamentos existentes
        String[] arrayEquipamentos = opcaoEquipamentos.toArray(new String[0]); //Recebe a lista de equipamentos existentes
        ArrayAdapter adaptadorEquipamentos = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayEquipamentos); //Configura o adaptador do Spinner
        adaptadorEquipamentos.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        equipamentos.setAdapter(adaptadorEquipamentos); //Insere o adaptador no Spinner

        //Prioridade
        prioridades = (Spinner)findViewById(R.id.spnPrioridade);
        ArrayList<String> opcaoPrioridades = osbd.getConsultarPrioridades(); //Obtém a lista de prioridades existentes
        String[] arrayPrioridades = opcaoPrioridades.toArray(new String[0]); //Recebe a lista de prioridades existentes
        ArrayAdapter adaptadorPrioridades = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayPrioridades); //Configura o adaptador do Spinner
        adaptadorPrioridades.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); //Estilo do spinner
        prioridades.setAdapter(adaptadorPrioridades); //Insere o adaptador no Spinner

        //Descrição do problema
        problema = (EditText) findViewById(R.id.edtDescricao);

        //Gravar Ordem de serviço
        salvar = (Button)findViewById(R.id.btnSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assunto.getText().toString().trim().isEmpty()) {
                    Toast.makeText(PrincipalActivity.this, "Informe o Assunto da Ordem de Serviço!", Toast.LENGTH_LONG).show();
                    assunto.setText("");
                    assunto.requestFocus();
                } else if (problema.getText().toString().trim().isEmpty()) {
                    Toast.makeText(PrincipalActivity.this, "Informe a Descrição do Problema!", Toast.LENGTH_LONG).show();
                    problema.setText("");
                    problema.requestFocus();
                } else {
                    osbd.setAssunto(assunto.getText().toString());
                    if (sessao.getText().toString().trim().isEmpty()){
                        String campoSessao = " ";
                        osbd.setSessao(campoSessao);
                    } else {
                        osbd.setSessao(sessao.getText().toString());
                    }
                    int idEmpresa = osbd.getConsultarIdEmpresa(empresas.getSelectedItem().toString());
                    osbd.setEmpresaId(idEmpresa);
                    int idCategoria = osbd.getConsultarIdCategoria(categorias.getSelectedItem().toString());
                    osbd.setCategoriaId(idCategoria);
                    int idSolicitante = osbd.getConsultarIdSolicitante(solicitantes.getSelectedItem().toString());
                    osbd.setSolicitanteId(idSolicitante);
                    int idAnalista = osbd.getConsultarIdAnalista(analistas.getSelectedItem().toString());
                    osbd.setAnalistaId(idAnalista);
                    int idComputador = osbd.getConsultarIdCompEquip(computadores.getSelectedItem().toString());
                    osbd.setComputadorId(idComputador);
                    int idEquipamento = osbd.getConsultarIdCompEquip(equipamentos.getSelectedItem().toString());
                    osbd.setEquipamentoId(idEquipamento);
                    int idPrioridade = osbd.getConsultarIdPrioridade(prioridades.getSelectedItem().toString());
                    osbd.setPrioridadeId(idPrioridade);
                    osbd.setProblema(problema.getText().toString());

                    osbd.salvar();
                    Toast.makeText(PrincipalActivity.this, "Chamado Gravado com Sucesso!", Toast.LENGTH_LONG).show();
                    limparCampos();
                }
            }
        });

        //Desfazer preenchimento dos campos
        desfazer = (Button) findViewById(R.id.btnDesfazer);
        desfazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            limparCampos();
            }
        });
    }

    //Limpar os campos e setar a seleção no índice inicial
    public void limparCampos(){
        assunto.setText("");
        sessao.setText("");
        empresas.setSelection(0);
        categorias.setSelection(0);
        solicitantes.setSelection(0);
        analistas.setSelection(0);
        computadores.setSelection(0);
        equipamentos.setSelection(0);
        prioridades.setSelection(0);
        problema.setText("");
        assunto.requestFocus();
    }
}
