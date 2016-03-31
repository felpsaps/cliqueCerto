package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class LeilaoBean {
	
	private Long id;
	private String status;
	private String produto;
	private Timestamp dtInicio;
	private BigDecimal preco;
	private Integer tempo;
	private Integer tempoAtual;
	private String usrArremate;
	
	
	
	public String getUsrArremate() {
		return usrArremate;
	}
	public void setUsrArremate(String usrArremate) {
		this.usrArremate = usrArremate;
	}
	public Integer getTempoAtual() {
		return tempoAtual;
	}
	public void setTempoAtual(Integer tempoAtual) {
		this.tempoAtual = tempoAtual;
	}
	public String getTempo1() {
		return String.valueOf(tempoAtual).substring(0, 1);
	}
	public String getTempo2() {
		return String.valueOf(tempoAtual).substring(1);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public Timestamp getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Timestamp dtInicio) {
		this.dtInicio = dtInicio;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {		
		this.preco = preco.setScale(2, RoundingMode.HALF_EVEN);;
	}
	public Integer getTempo() {
		return tempo;
	}
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
	
	

}
