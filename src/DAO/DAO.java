package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Felipe
 */
public class DAO {

    /**
     * Conexao com o banco
     */
    protected static Connection conexao;

    /**
     * Cria uma conexão e um statement com o banco de dados
     *
     * @throws SQLException
     */
    protected static boolean conecta() throws SQLException
    {
    	try {
	        conexao = Banco.connect();
	        return true;
    	} catch (Exception ex) {
    		return false;
    	}
    }

    /**
     * Fecha uma conexão e o statement cm o banco de dados
     *
     * @throws SQLException
     */
    protected static void fechaConexao() 
    {
    	try {
    		conexao.close();    		
    	} catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    }


}
