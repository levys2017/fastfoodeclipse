package br.com.levys.fastfood.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.dao.FuncionarioDAO;
import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Cliente;
import br.com.levys.fastfood.modelo.Funcionario;
import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.util.FacesUtils;
import br.com.levys.fastfood.util.VerificaPermissao;


@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable{
	
private static final long serialVersionUID = 1L;

	
	private List<Funcionario> funcionarios              = new ArrayList<Funcionario>();
	private Funcionario       funcionarioSelected       = new Funcionario();
	private Funcionario       funcionarioSelectedTable  = new Funcionario();
	private String email, senha;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public FuncionarioBean(){
	funcionarios = new FuncionarioDAO().getAllOrderAsc(Funcionario.class, "nome");
		
	}
	
	public void selecionaRowTable(AjaxBehaviorEvent ae) {
		if (funcionarioSelectedTable != null) {
			funcionarioSelected = funcionarioSelectedTable;
		}

	}
	
	
	private void recarregaFuncionario(){
		
		funcionarios = new FuncionarioDAO().getAllOrderAsc(Funcionario.class, "nome");
	}
	
	public void salvar(){
		if(new VerificaPermissao().isSupervisor()){
	
		if(!(funcionarioSelected.getNome()).isEmpty()&&!email.isEmpty()&&!(funcionarioSelected.getTelefone()).isEmpty()
		 &&!senha.isEmpty()&&!(funcionarioSelected.getCpf()).isEmpty()) {
			
			if(funcionarioSelected!=null){
			
				if(new UsuarioDAO().getAllbyEmail(email)==null) {
				
					Usuario u = new Usuario();
					u.setEmail(email);
					u.setPerfil(funcionarioSelected.getCargo());
					u.setSenha(senha);
					
					if(new UsuarioDAO().save(u)) {
						funcionarioSelected.setUsuario(u);
						funcionarioSelected.setNome(funcionarioSelected.getNome());
						funcionarioSelected.setTelefone(funcionarioSelected.getTelefone());
						funcionarioSelected.setCpf(funcionarioSelected.getCpf());
						funcionarioSelected.setSexo(funcionarioSelected.getSexo());
						funcionarioSelected.setCargo(funcionarioSelected.getCargo());
						funcionarioSelected.setFuncao(funcionarioSelected.getFuncao());
						
						
						if(new FuncionarioDAO().save(funcionarioSelected)) {
							FacesUtils.adicionaMensagemDeInformacao("Cadastro efetuado com sucesso");
							
						}else {
							new UsuarioDAO().delete(u);						
						}
						
					}else {
						FacesUtils.adicionaMensagemDeFatal("Falha ao efeturar o cadastro, tente novamente mais tarde");
					}
					
				}else {
					
					FacesUtils.adicionaMensagemDeErro("Email já cadastrado");
				}
			}else {
				
				FacesUtils.adicionaMensagemDeAdvertencia("Todos os campos são obrigatórios");
			
				if(new FuncionarioDAO().save(funcionarioSelected))
			{
				System.out.println("Operação realizada com sucesso!");
				funcionarioSelected = new Funcionario();
				recarregaFuncionario();
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
			}
		else
		{
			FacesUtils.adicionaMensagemDeErro("Todos os campos são obrigatórios");
		}
	
		}else{
			FacesUtils.adicionaMensagemDeErro("Você não possui permissão para efetuar esta operação");
		}
	}

	public void excluir(){
		if(new VerificaPermissao().isSupervisor()){
		if(funcionarioSelected!=null && funcionarioSelected.getId()>0)
		{
			if(new FuncionarioDAO().delete(funcionarioSelected)){
				if(new UsuarioDAO().delete(funcionarioSelected.getUsuario())) {
					
			}
				System.out.println("Operação realizada com sucesso!");
				funcionarioSelected = new Funcionario();
				
				recarregaFuncionario();
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
		else
		{
			FacesUtils.adicionaMensagemDeErro("não é possível salvar esse funcionario");
		}
		}else{
			FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar essa operação");
			
		}
		
	}

	
	public void limpar(){
		
		funcionarioSelected = new Funcionario();
		funcionarioSelectedTable = new Funcionario();
		
	}


	public Funcionario getFuncionarioSelected() {
		return funcionarioSelected;
	}


	public void setFuncionarioSelected(Funcionario funcionarioSelected) {
		this.funcionarioSelected = funcionarioSelected;
	}


	public Funcionario getFuncionarioSelectedTable() {
		return funcionarioSelectedTable;
	}


	public void setFuncionarioSelectedTable(Funcionario funcionarioSelectedTable) {
		this.funcionarioSelectedTable = funcionarioSelectedTable;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	


	
	
}	