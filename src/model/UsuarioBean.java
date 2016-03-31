package model;

public class UsuarioBean {
	
	private Long usrId;
	private Integer lances;
	private String usrNome;
	private String login;
	private String pass;
	private String email;
	private String cpf;
	private String endereco;
	
	
	
	public Integer getLances() {
		return lances;
	}
	public void setLances(Integer lances) {
		this.lances = lances;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Long getUsrId() {
		return usrId;
	}
	public void setUsrId(Long usrId) {
		this.usrId = usrId;
	}
	public String getUsrNome() {
		return usrNome;
	}
	public void setUsrNome(String usrNome) {
		this.usrNome = usrNome;
	}
}
