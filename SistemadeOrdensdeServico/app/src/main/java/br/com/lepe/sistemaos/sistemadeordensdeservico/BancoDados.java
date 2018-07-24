package br.com.lepe.sistemaos.sistemadeordensdeservico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class BancoDados implements Runnable{
    private Connection conn;
    private String host = "192.168.2.253";
    private String db = "lepe";
    private int port = 5432;
    private String user = "postgres";
    private String pass = "postgres";
    private String url = "jdbc:postgresql://%s:%d/%s";

    public BancoDados() {
        super();
        this.url = String.format(this.url,this.host, this.port, this.db);

        this.conecta();
        this.disconecta();
    }

    @Override
    public void run() {
        try{
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(this.url,this.user,this.pass);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void conecta(){
        Thread thread = new Thread(this);
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void disconecta(){
        if (this.conn!= null){
            try{
                this.conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                this.conn = null;
            }
        }
    }

    public ResultSet select(String query){
        this.conecta();
        ResultSet resultSet = null;
        try {
            resultSet = new ExecuteBD(this.conn, query).execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet execute(String query){
        this.conecta();
        ResultSet resultSet = null;
        try {
            resultSet = new ExecuteBD(this.conn, query).execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
