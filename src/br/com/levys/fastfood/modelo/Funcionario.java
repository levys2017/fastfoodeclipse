package br.com.levys.fastfood.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Funcionario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;//matricula
    @ManyToOne
    @JoinColumn(name="usuario")
	private Usuario usuario;
    private String nome;
    private String cpf;
    private int sexo;
    private String telefone;
    private int cargo; //0 - cliente 1- Atendimento  2- Cozinha 3 - Diretoria
    private String funcao;
    @Transient
    private String desc_Sexo;
    @Transient
    private String desc_Cargo;
    
    
    
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

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDesc_sexo() {
		String desc_Sexo;
		desc_Sexo ="";
		
		switch(this.sexo) {
		case 1: desc_Sexo = "Masculino";
		break;
		case 2: desc_Sexo = "Feminino";
		break;
		
	}return desc_Sexo;
	
	}
	public String getDesc_cargo() {
		String desc_Cargo;
		desc_Cargo ="";
		
		switch(this.sexo) {
		case 1: desc_Cargo = "Atendimento";
		break;
		case 2: desc_Cargo = "Cozinha";
		break;
		case 3: desc_Cargo = "Diretoria";
		break;
	}return desc_Cargo;
	
	}

    
}
