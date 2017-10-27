package br.com.levys.fastfood.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int     id;
	@ManyToOne
	@JoinColumn(name="login")
    private Usuario login;
    private String  nome;
    private String  telefone;
    private String  n_cartao;
    private Date    dt_venc;
    private String    csv;
    
  
   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

  

    public String getN_cartao() {
        return n_cartao;
    }

    public void setN_cartao(String n_cartao) {
        this.n_cartao = n_cartao;
    }

    public Date getDt_venc() {
        return dt_venc;
    }

    public void setDt_venc(Date dt_venc) {
        this.dt_venc = dt_venc;
    }

    


	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Usuario getLogin() {
		return login;
	}


	public void setLogin(Usuario login) {
		this.login = login;
	}
    
 
}
