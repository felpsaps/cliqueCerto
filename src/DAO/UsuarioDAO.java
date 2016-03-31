package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UsuarioBean;

public class UsuarioDAO extends DAO{

	public void insert(UsuarioBean usr) throws SQLException {
		PreparedStatement ps = null;		
		if (conecta()) {
			try{

				StringBuilder sql = new StringBuilder();

				sql.append(" INSERT INTO usuario (usr_nome) ");
				sql.append(" 	VALUES (?) ");

				ps = conexao.prepareStatement(sql.toString());
				ps.setString(1, usr.getUsrNome());
				ps.executeUpdate();

			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				if (ps != null) {
					ps.close();
				}
				fechaConexao();
			}
		}
	}
	
	public UsuarioBean login(String email, String pass) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		UsuarioBean usr = null;
		if (conecta()) {
			try{

				StringBuilder sql = new StringBuilder();

				sql.append(" SELECT *  ");
				sql.append(" 	FROM usuario ");
				sql.append(" 	WHERE usr_email = ? AND usr_pass = ? ");

				ps = conexao.prepareStatement(sql.toString());
				ps.setString(1, email);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				if (rs.next()) {
					usr = new UsuarioBean();
					usr.setUsrId(rs.getLong("usr_id"));
					usr.setUsrNome(rs.getString("usr_nome"));
					usr.setLogin(rs.getString("usr_login"));
					usr.setPass(rs.getString("usr_pass"));
					usr.setEmail(rs.getString("usr_email"));
					usr.setLances(rs.getInt("usr_lances"));
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				fechaConexao();
			}
		}
		return usr;
	}

}
