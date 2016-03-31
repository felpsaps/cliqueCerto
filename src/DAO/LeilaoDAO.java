package DAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.LeilaoBean;
import model.UsuarioBean;

public class LeilaoDAO extends DAO {

	public List<LeilaoBean> getLeiloesHome() throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<LeilaoBean> leiloes = new ArrayList<LeilaoBean>();
		if (conecta()) {
			try{
				StringBuilder sql = new StringBuilder();

				sql.append(" SELECT * ");
				sql.append(" 	FROM leilao LEFT JOIN usuario ON ( leilao_usr_arremate_id = usr_id), produto ");
				sql.append(" 	WHERE prd_id = leilao_prd_id AND leilao_status <> 'E' ");
				sql.append(" ORDER BY leilao_dt_inicio ");
				sql.append(" LIMIT 9 OFFSET 0 ");

				ps = conexao.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				
				while (rs.next()) {
					LeilaoBean leilao = new LeilaoBean();
					leilao.setDtInicio(rs.getTimestamp("leilao_dt_inicio"));
					leilao.setId(rs.getLong("leilao_id"));
					leilao.setPreco(rs.getBigDecimal("leilao_preco_arremate"));
					leilao.setProduto(rs.getString("prd_desc"));
					leilao.setStatus(rs.getString("leilao_status"));
					leilao.setTempo(rs.getInt("leilao_tempo_segundos"));
					leilao.setTempoAtual(rs.getInt("leilao_tempo_segundos"));
					leilao.setUsrArremate(rs.getString("usr_login"));
					
					leiloes.add(leilao);
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
		return leiloes;
	}
	
	
	public LeilaoBean getPorId(Long id) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		LeilaoBean leilao = null;
		if (conecta()) {
			try{
				StringBuilder sql = new StringBuilder();

				sql.append(" SELECT * ");
				sql.append(" 	FROM leilao, produto ");
				sql.append(" 	WHERE prd_id = leilao_prd_id AND leilao_status <> 'E' ");
				sql.append(" AND leilao_id = ? ");

				ps = conexao.prepareStatement(sql.toString());
				ps.setLong(1, id);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					leilao = new LeilaoBean();
					leilao.setDtInicio(rs.getTimestamp("dataInicio"));
					leilao.setId(rs.getLong("leilao_id"));
					leilao.setPreco(rs.getBigDecimal("leilao_preco_arremate"));
					leilao.setProduto(rs.getString("prd_desc"));
					leilao.setStatus(rs.getString("leilao_status"));
					leilao.setTempo(rs.getInt("leilao_tempo_segundos"));
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
		return leilao;
	}
	
	
	public void iniciarLeilao(Long id) throws SQLException {
		PreparedStatement ps = null;
		if (conecta()) {
			try{
				StringBuilder sql = new StringBuilder();

				sql.append(" UPDATE leilao ");
				sql.append(" SET leilao_status = 'A' ");
				sql.append(" WHERE leilao_id = ? ");

				ps = conexao.prepareStatement(sql.toString());
				ps.setLong(1, id);
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
	
	public void encerrarLeilao(Long id) throws SQLException {
		PreparedStatement ps = null;
		if (conecta()) {
			try{
				StringBuilder sql = new StringBuilder();

				sql.append(" UPDATE leilao ");
				sql.append(" SET leilao_status = 'E', ");
				sql.append(" leilao_dt_fim = now(), ");
				sql.append(" leilao_lances_ganhador = (select COUNT(*)   ");
				sql.append(" 								FROM lance ");
				sql.append(" 								WHERE lc_leilao_id = leilao_id AND lc_usr_id = leilao_usr_arremate_id) ");
				sql.append(" WHERE leilao_id = ?");

				ps = conexao.prepareStatement(sql.toString());
				ps.setLong(1, id);
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
	
	public void computarLance(Long leilaoId, BigDecimal preco, UsuarioBean usr) throws SQLException {
		PreparedStatement ps = null;
		if (conecta()) {
			try{
				StringBuilder sql = new StringBuilder();

				// Update na tabela leilao colocando o usuario que deu este lance como atual ganhador e o preco atual do leilao
				sql.append(" UPDATE leilao ");
				sql.append(" SET leilao_usr_arremate_id = ?, ");
				sql.append(" leilao_preco_arremate = ? ");
				sql.append(" WHERE leilao_id = ?");				

				ps = conexao.prepareStatement(sql.toString());
				ps.setLong(1, usr.getUsrId());
				ps.setBigDecimal(2, preco);
				ps.setLong(3, leilaoId);
				ps.executeUpdate();
				

				// Update na quantidade de lances do usuario
				usr.setLances(usr.getLances()-1);
				sql.setLength(0);
				sql.append(" UPDATE usuario ");
				sql.append(" 	SET usr_lances = ? ");
				sql.append(" 	WHERE usr_id = ?");		
				
				ps = conexao.prepareStatement(sql.toString());
				ps.setLong(1, usr.getLances());
				ps.setLong(2, usr.getUsrId());
				ps.executeUpdate();
				
				// Inserindo informações na tabela de lances
				sql.setLength(0);
				sql.append(" INSERT INTO lance ");
				sql.append(" 	(lc_usr_id, lc_dt, lc_leilao_id) ");
				sql.append(" 	VALUES(?, now(), ?) ");
				
				ps = conexao.prepareStatement(sql.toString());
				ps.setLong(1, usr.getUsrId());
				ps.setLong(2, leilaoId);
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
}
