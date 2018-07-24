package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AberturaChamado1Activity extends AppCompatActivity{

    //Objetos e Variáveis
    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private TextView data; //Data exibida
    private Date dataAtual; //Data atual
    private SimpleDateFormat dateFormat; //Formatação da data
    private EditText assunto; //Assunto do chamado
    private EditText sessao; //Sessão, relatório ou tipo (opcional)
    private EditText descricao; //Descrição do problema
    private Button avancar; //Avança para próxima tela

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_chamado1);

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();

        //Data atual (apenas exibição no app)
        data = (TextView) findViewById(R.id.txtDataSolicitacao);
        dataAtual = new Date(); //Data atual do sistema
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //Formatação Dia/Mês/Ano - Horas/Minutos
        data.setText(dateFormat.format(dataAtual)); //Aplicação da formatação na data do sistema

        //Descrição do asssunto
        assunto = (EditText)findViewById(R.id.edtAssunto2);
        assunto.requestFocus();

        //Descrição da sessão, relatório ou tipo
        sessao = (EditText)findViewById(R.id.edtSessao2);

        //Descrição do problema
        descricao = (EditText) findViewById(R.id.edtDescricao2);

        //Avançar para próxima tela
        avancar = (Button) findViewById(R.id.btnAvancar);
        avancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String campoSessao = " ";
                if (assunto.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AberturaChamado1Activity.this, "Informe o assunto do chamado!", Toast.LENGTH_SHORT).show();
                    assunto.requestFocus();
                } else if (descricao.getText().toString().trim().isEmpty()) {
                    Toast.makeText(AberturaChamado1Activity.this, "Informe a descrição do problema!", Toast.LENGTH_SHORT).show();
                    descricao.requestFocus();
                }  else {
                    if (!sessao.getText().toString().trim().isEmpty()){
                        campoSessao = sessao.getText().toString();
                    }
                    Intent intent = new Intent(AberturaChamado1Activity.this, AberturaChamado2Activity.class);
                    //Enviando dados para próxima tela
                    Bundle bundle = new Bundle();
                    bundle.putString("assunto", assunto.getText().toString());
                    bundle.putString("sessao", campoSessao);
                    bundle.putString("descricao", descricao.getText().toString());
                    intent.putExtras(bundle);
                    //Executando a Intent
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }
        });
    }
}
