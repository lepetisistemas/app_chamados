package br.com.lepe.sistemaos.sistemadeordensdeservico;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConsultaActivity extends AppCompatActivity {

    private OSBD osbd; //Manutenção do BD (consultar e gravar)
    private ListView chamados; //Lista de chamados abertos
    private TextView totalRegistros; //Total de chamados abertos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        //Acesso a classe de manutenção do banco de dados
        osbd = new OSBD();
        //Associa a variável ao componente ListView
        chamados = (ListView) findViewById(R.id.lstChamados);
        //Cria o ListView com os dados dos Chamados existentes
        listaChamados();
        //Associa a variável ao componente TextView
        totalRegistros = (TextView) findViewById(R.id.txtTotalRegistros);
        //Conta quantos chamados foram abertos
        contadorRegistros();

        //Toast.makeText(getApplicationContext(), "Clique em um chamado para exclui-lo!", Toast.LENGTH_LONG).show();
        Snackbar.make(this.findViewById(android.R.id.content), "Clique em um chamado para exclui-lo!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        chamados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Context context = view.getContext();
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.excluir_chamado, null, false);
                final EditText edtNumChamado = (EditText) formElementsView.findViewById(R.id.edtChamado);

                //Caixa de diálogo para exclusão de chamados
                AlertDialog.Builder excluir = new AlertDialog.Builder(ConsultaActivity.this);
                excluir.setView(formElementsView);
                excluir.setTitle("Excluir chamado...");
                //Botão Excluir
                excluir.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (edtNumChamado.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Chamado inválido!", Toast.LENGTH_LONG).show();
                        } else {
                            boolean excluidoSucesso = osbd.excluirChamado(Integer.parseInt(edtNumChamado.getText().toString()));
                            if (excluidoSucesso){
                                listaChamados();
                                contadorRegistros();
                                Toast.makeText(getApplicationContext(), "Chamado excluído com sucesso!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Não foi possível excluir o chamado!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                //Botão Cancelar
                excluir.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                excluir.show();
            }
        });
    }

    public void listaChamados() {
        //Obtém a lista de chamados existentes
        ArrayList<String> chamadosTI = osbd.getConsultarOS();
        //Atribui a lista de chamados para um ArrayAdapter padrão
        final ArrayAdapter<String> listaChamados = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, chamadosTI);
        //Adiciona a lista de chamados ao ListView
        chamados.setAdapter(listaChamados);
    }

    //Exibe o total de chamados abertos através do aplicativo
    public void contadorRegistros(){
        int contador = osbd.totalChamados();
        totalRegistros.setText("Total de chamados: " + String.valueOf(contador));
    }
}
