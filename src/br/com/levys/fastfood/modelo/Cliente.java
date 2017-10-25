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
    private int id;
	@ManyToOne
	@JoinColumn(name="login")
    private Usuario login;
    private String nome;
    private String cpf;
    private Date dt_nasc;
    private char sexo;
    private String email;
    private String telefone;
    //private String senha; /* O sistema salva a senha do cart√£o?*/ n„o precisa de senha para compras online
    private String n_cartao;
    private Date dt_venc;
    private char csv;
    
  
   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDt_nasc() {
        return dt_nasc;
    }

    public void setDt_nasc(Date dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public char getCsv() {
        return csv;
    }

    public void setCsv(char csv) {
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
