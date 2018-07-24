package br.com.lepe.sistemaos.sistemadeordensdeservico;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class OSBD {
    private int id;
    private Timestamp data;
    private String assunto;
    private String sessao;
    private String empresa;
    private int empresaId;
    private String categoria;
    private int categoriaId;
    private String solicitante;
    private int solicitanteId;
    private String analista;
    private int analistaId;
    private String computador;
    private int computadorId;
    private String equipamento;
    private int equipamentoId;
    private String prioridade;
    private int prioridadeId;
    private String problema;
    private ResultSet resultSet;
    private BancoDados bancoDados;

    public OSBD(){
        super();
        this.id = -1;
        this.data = null;
        this.assunto = "";
        this.sessao = "";
        this.empresa = "";
        this.empresaId = 0;
        this.categoria = "";
        this.categoriaId = 0;
        this.solicitante = "";
        this.solicitanteId = 0;
        this.analista = "";
        this.analistaId = 0;
        this.computador = "";
        this.computadorId = 0;
        this.equipamento = "";
        this.equipamentoId = 0;
        this.prioridade = "";
        this.prioridadeId = 0;
        this.problema = "";
    }

    //Consultar todas as empresas cadastradas
    public ArrayList<String> getConsultarEmpresas(){
        bancoDados = new BancoDados();
        ArrayList<String> listaEmpresas = new ArrayList<String>();
        try {
            resultSet = bancoDados.select("SELECT razao_social FROM lepe.empresa ORDER BY razao_social");
            if (resultSet != null){
                while (resultSet.next()){
                    String razaoSocial = resultSet.getString("razao_social");
                    listaEmpresas.add(razaoSocial);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaEmpresas;
    }

    //Consultar empresa selecionada
    public int getConsultarIdEmpresa(String razaoEmpresa){
        bancoDados = new BancoDados();
        int idEmpresa = 0;
        try {
            resultSet = bancoDados.select("SELECT empresa_id FROM lepe.empresa WHERE razao_social = '" + razaoEmpresa + "'");
            while (resultSet.next()){
                idEmpresa = resultSet.getInt("empresa_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idEmpresa;
    }

    //Consultar todas as categorias cadastradas
    public ArrayList<String> getConsultarCategorias(){
        bancoDados = new BancoDados();
        ArrayList<String> listaCategorias = new ArrayList<String>();
        try {
            resultSet = bancoDados.select("SELECT descricao FROM lepe.categoria WHERE inativo = FALSE ORDER BY descricao");
            if (resultSet != null){
                while (resultSet.next()){
                    String descCategoria = resultSet.getString("descricao");
                    listaCategorias.add(descCategoria);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaCategorias;
    }

    //Consultar categoria selecionada
    public int getConsultarIdCategoria(String categoriaOS){
        bancoDados = new BancoDados();
        int idCategoria = 0;
        try {
            resultSet = bancoDados.select("SELECT categoria_id FROM lepe.categoria WHERE descricao ='" + categoriaOS + "'");
            if (resultSet != null){
                while (resultSet.next()){
                    idCategoria = resultSet.getInt("categoria_id");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return idCategoria;
    }

    //Consultar todos os solicitantes cadastrados
    public ArrayList<String> getConsultarSolicitantes(){
        bancoDados = new BancoDados();
        ArrayList<String> listaSolicitantes = new ArrayList<String>();
        try {
            resultSet = bancoDados.select("SELECT nomecompl FROM lepe.ssl INNER JOIN lepe.orgao ON lepe.ssl.orgao_ID = orgao.orgao_id\n" +
                                          "WHERE demitido = FALSE AND system = TRUE ORDER BY nomecompl");
            if (resultSet != null){
                while (resultSet.next()){
                    String nomeSolicitante = resultSet.getString("nomecompl");
                    listaSolicitantes.add(nomeSolicitante);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaSolicitantes;
    }

    //Consutar solicitante selecionado
    public int getConsultarIdSolicitante(String solicitanteOS){
        bancoDados = new BancoDados();
        int idSolicitante = 0;
        try {
            resultSet = bancoDados.select("SELECT usuario_id FROM lepe.ssl WHERE nomecompl ='" + solicitanteOS + "'");
            if (resultSet != null){
                while (resultSet.next()){
                    idSolicitante = resultSet.getInt("usuario_id");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return idSolicitante;
    }

    //Consultar todos os analistas cadastrados
    public ArrayList<String> getConsultarAnalistas(){
        bancoDados = new BancoDados();
        ArrayList<String> listaAnalistas = new ArrayList<String>();
        try {
            resultSet = bancoDados.select("SELECT nomecompl FROM lepe.ssl WHERE analista = TRUE AND demitido = FALSE ORDER BY nomecompl");
            if (resultSet != null){
                while (resultSet.next()){
                    String nomeAnalista = resultSet.getString("nomecompl");
                    listaAnalistas.add(nomeAnalista);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaAnalistas;
    }

    //Consutar analista selecionado
    public int getConsultarIdAnalista(String analistaOS){
        bancoDados = new BancoDados();
        int idAnalista = 0;
        try {
            resultSet = bancoDados.select("SELECT usuario_id FROM lepe.ssl WHERE nomecompl ='" + analistaOS + "'");
            if (resultSet != null){
                while (resultSet.next()){
                    idAnalista = resultSet.getInt("usuario_id");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return idAnalista;
    }

    //Consultar todos os computadores/equipamentos cadastrados
    public ArrayList<String> getConsultarCompEquip(){
        bancoDados = new BancoDados();
        ArrayList<String> listaCompEquip = new ArrayList<String>();
        try {
            resultSet = bancoDados.select("SELECT c.maquina || '(' || TRIM(e.descricao) || ')' maquina FROM lepe.computador \n" +
                                          "c INNER JOIN lepe.equipamento e ON e.equipamento_id = c.equipamento_id \n" +
                                          "WHERE e.status NOT IN ('DESCARTADO') ORDER BY maquina");
            if (resultSet != null){
                while (resultSet.next()){
                    String maquinaUsuario = resultSet.getString("maquina");
                    listaCompEquip.add(maquinaUsuario);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaCompEquip;
    }

    //Consultar computador/equipamento selecionado
    public int getConsultarIdCompEquip(String compEquip){
        bancoDados = new BancoDados();
        int idCompEquip = 0;
        try {
            resultSet = bancoDados.select("SELECT computador.equipamento_id FROM lepe.computador INNER JOIN lepe.equipamento ON \n" +
                                          "equipamento.equipamento_id = computador.equipamento_id WHERE computador.maquina || '(' \n" +
                                          "|| TRIM(equipamento.descricao) || ')' ='" + compEquip + "'");
            if (resultSet != null){
                while (resultSet.next()){
                    idCompEquip = resultSet.getInt("equipamento_id");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return idCompEquip;
    }

    //Consultar todas as prioridades cadastradas
    public ArrayList<String> getConsultarPrioridades(){
        bancoDados = new BancoDados();
        ArrayList<String> listaPrioridades = new ArrayList<String>();
        try {
            resultSet = bancoDados.select("SELECT descricao FROM lepe.prioridade WHERE ativo = true ORDER BY prioridade_id");
            if (resultSet != null){
                while (resultSet.next()){
                    String descPrioridade = resultSet.getString("descricao");
                    listaPrioridades.add(descPrioridade);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaPrioridades;
    }

    //Consultar prioridade selecionada
    public int getConsultarIdPrioridade(String prioridadeOS){
        bancoDados = new BancoDados();
        int idPrioridade = 0;
        try {
            resultSet = bancoDados.select("SELECT prioridade_id FROM lepe.prioridade WHERE descricao ='" + prioridadeOS + "'");
            if (resultSet != null){
                while (resultSet.next()){
                    idPrioridade = resultSet.getInt("prioridade_id");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return idPrioridade;
    }

    //Gravar Ordem de Serviço
    public void salvar(){
        String comando = "";
        if (this.getId() == -1){
            comando = String.format("INSERT INTO lepe.os (datainicio, assunto, sessao, empresa_id, categoria_id, usuario_id, analista_id," +
                                    "equipamento_id, equipamentodefeito_id, prioridade_id, descricao, status, voto, situacao, gravado)" +
                                    "VALUES (CURRENT_TIMESTAMP,'%s','%s', '%d', '%d', '%d', '%d', '%d', '%d', '%d', '%s', 'P', 0, 0, FALSE);",
                                    this.getAssunto(), this.getSessao(), this.getEmpresaId(), this.getCategoriaId(), this.getSolicitanteId(),
                                    this.getAnalistaId(), this.getComputadorId(), this.getEquipamentoId(), this.getPrioridadeId(), this.getProblema());
        }
        BancoDados bd = new BancoDados();
        bd.execute(comando);
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } //Não utilizado

    public Date getData() {
        return data;
    }

    public void setData(Timestamp data) { this.data = data; }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public String getEmpresa() {
        return empresa;
    } //Não utilizado

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    } //Não utilizado

    public String getCategoria() {
        return categoria;
    } //Não utilizado

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    } //Não utilizado

    public String getSolicitante() {
        return solicitante;
    } //Não utilizado

    public void setSolicitante(String solicitante) { this.solicitante = solicitante; } //Não utilizado

    public String getAnalista() { return analista; } //Não utilizado

    public void setAnalista(String analista) { this.analista = analista; } //Não utilizado

    public String getComputador() {
        return computador;
    } //Não utilizado

    public void setComputador(String computador) {
        this.computador = computador;
    } //Não utilizado

    public String getEquipamento() {
        return equipamento;
    } //Não utilizado

    public void setEquipamento(String equipamento) { this.equipamento = equipamento; } //Não utilizado

    public String getPrioridade() {
        return prioridade;
    } //Não utilizado

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    } //Não utilizado

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public int getEmpresaId() { return empresaId; }

    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }

    public int getCategoriaId() { return categoriaId; }

    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public int getSolicitanteId() { return solicitanteId; }

    public void setSolicitanteId(int solicitanteId) { this.solicitanteId = solicitanteId; }

    public int getAnalistaId() { return analistaId; }

    public void setAnalistaId(int analistaId) { this.analistaId = analistaId; }

    public int getComputadorId() { return computadorId; }

    public void setComputadorId(int computadorId) { this.computadorId = computadorId; }

    public int getEquipamentoId() { return equipamentoId; }

    public void setEquipamentoId(int equipamentoId) { this.equipamentoId = equipamentoId; }

    public int getPrioridadeId() { return prioridadeId; }

    public void setPrioridadeId(int prioridadeId) { this.prioridadeId = prioridadeId; }
}
