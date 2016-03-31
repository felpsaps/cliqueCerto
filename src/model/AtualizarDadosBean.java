package model;

import java.math.BigDecimal;

public class AtualizarDadosBean {

	BigDecimal preco;
	Integer tempo;
	String status;
	Integer lances;
	String ultimoLance;
		
	public String getUltimoLance() {
		return ultimoLance;
	}
	public void setUltimoLance(String ultimoLance) {
		this.ultimoLance = ultimoLance;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco.setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}
	public Integer getTempo() {
		return tempo;
	}
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLances() {
		return lances;
	}
	public void setLances(Integer lances) {
		this.lances = lances;
	}
	
	
}
