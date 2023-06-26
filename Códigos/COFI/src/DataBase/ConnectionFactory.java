package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE = "projeto_cofi";
    private static final String DRIVER_CONEXAO = "com.mysql.jdbc.Driver";
    private static final String STR_CONEXAO = "jdbc:mysql://localhost:3306/";
    
    

    public static Connection getConexao() throws ClassNotFoundException, SQLException {
        
    //Abastecendo conexão como nula
        Connection conn = null;
        
        try {
            
        //Puxando arquivo.class da biblioteca
            Class.forName(DRIVER_CONEXAO);

        //criando conexão (comunicação) com driver usando a classe getConnection
            conn = DriverManager.getConnection(STR_CONEXAO + DATABASE, USER, PASSWORD);
            
            System.out.println("Conexão com Banco de Dados feita com SUCESSO\nTabela: "+ DATABASE);
            
            return conn;
            
            
            
        } catch (ClassNotFoundException ex1) {
            
        //Exceção de classe
            throw new ClassNotFoundException("Erro, não foi possivel encontrar a classe do Driver\n" + ex1.getMessage());
            
        } catch (SQLException ex2) {
            
        //Exceção de Conexão
            throw new SQLException("Erro de Conexão com Banco: " + ex2.getMessage());
            
        }
        
    }
    
//Classe para fechar conexão com Banco de Dados
    public static void conexaoEnd(Connection con) throws SQLException{
        try {
        //Verificando se a conexão está nula ou não
        
            if (con != null) {
                con.close();
                System.out.println("Conexão com Bando de Dados ENCERRADA");
                
            }
        } catch (SQLException ex){
            throw new SQLException("Erro para encerrar conexão com Banco de Dados");
        }
    }
    
//Classe para fechar conexão com Banco de Dados e o Statement - Sobrecarga de método   
    public static void conexaoEnd(Connection con, Statement st) throws SQLException{
        try{
        //Tenho conexão
            if (con != null) {
                //Puxa o método de encerrar conexão somente com BD
                conexaoEnd(con);
            }
        //Tenho um Statement
            if (st != null) {
                st.close();
                System.out.println("Conexão com Statement ENCERRADA");
            }
        } catch (SQLException ex){
            throw new SQLException("Erro para encerrar conexão com Statement");
        }
    }

//Classe para fechar conexão com Banco de Dados e o Statement - Sobrecarga de método  
    public static void conexaoEnd(Connection con, Statement st, ResultSet rs) throws SQLException{
        try{
        //Verifica se tenho uma conexão ou um Statement
            if (con != null || st != null) {
                //Puxa o método de encerrar conexão somente com BD
                conexaoEnd(con, st);
            }
        //Verifica se tenho um Statement
            if (rs != null) {
                rs.close();
                System.out.println("Conexão com Statement ENCERRADA");
            }
        } catch (SQLException ex){
            throw new SQLException("Erro para encerrar conexão com ResultSet");
        }
    }    
    
}
