package br.com.levys.fastfood.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Cliente;
import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.util.FacesUtils;
import br.com.levys.fastfood.util.VerificaPermissao;


@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable{
	
private static final long serialVersionUID = 1L;

	
	private List<Cliente> clientes              = new ArrayList<Cliente>();
	private Cliente       clienteSelected       = new Cliente();
	private Cliente       clienteSelectedTable  = new Cliente();
	private int    jacadastrado;
	private int contador;
	
	private String email, nome, telefone, senha;
	

	
	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = 0;
	}
	
	public void adiciona() {
		contador++;
		
	}
	public void remove() {
		contador--;
	}
	
	
	public void atualizatela() {
		
		if(jacadastrado==0) {
		jacadastrado = 1;
		}else {
			jacadastrado=0;
		}
		
	}
	
	
	public String loginCliente() {
		String ret="#";
		
		
		if(!email.isEmpty()&&!senha.isEmpty())
		{
			
			if(new UsuarioDAO().Login(email, senha)!=null) {
				
				ret = "bemvindo?faces-redirect=true";
			}else {
				
				FacesUtils.adicionaMensagemDeFatal("usuario e/ou senha inválidos");
			}
			
			
			
			
		}else {
			FacesUtils.adicionaMensagemDeAdvertencia("informe o email e a senha");
		}
		
		return ret;
		
		
		
	}
	
	public String cadastraCliente() {
		
		String ret="#";
		if(!nome.isEmpty()&&!email.isEmpty()&&!telefone.isEmpty()&&!senha.isEmpty()) {
			
			if(new UsuarioDAO().getAllbyEmail(email)==null) {
				
				
				Usuario u = new Usuario();
				u.setEmail(email);
				u.setPerfil(0);
				u.setSenha(senha);
				
				if(new UsuarioDAO().save(u)) {
					clienteSelected = new Cliente();
					clienteSelected.setLogin(u);
					clienteSelected.setNome(nome);
					clienteSelected.setTelefone(telefone);
					
					if(new ClienteDAO().save(clienteSelected)) {
						FacesUtils.adicionaMensagemDeInformacao("cadstro efetuado com sucesso");
						ret = "bemvindo?faces-redirect=true";
					}else {
						new UsuarioDAO().delete(u);						
					}
					
				}else {
					FacesUtils.adicionaMensagemDeFatal("falha ao efeturar o cadastro, tente novamente mais tarde");
				}
				
				
				
				
				
				
			}else {
				
				FacesUtils.adicionaMensagemDeErro("esse email já está cadastrado");
			}
			
			
			
			
			
			
		}else {
			
			FacesUtils.adicionaMensagemDeAdvertencia("todos os campos são obrigatórios");
		}
		
		
		return ret;
		
	}
	
	
	
	
	
	public ClienteBean(){
	clientes = new ClienteDAO().getAllOrderAsc(Cliente.class, "nome");
		
	}
	
	public void selecionaRowTable(AjaxBehaviorEvent ae) {
		if (clienteSelectedTable != null) {
			clienteSelected = clienteSelectedTable;
		}

	}
	
	
	private void recarregaCliente(){
		
		clientes = new ClienteDAO().getAllOrderAsc(Cliente.class, "nome");
	}
	
	public void salvar(){
		if(new VerificaPermissao().isSupervisor()){
		if(clienteSelected!=null)
		{
			if(new ClienteDAO().save(clienteSelected))
			{
				System.out.println("Operação realizada com sucesso!");
				clienteSelected = new Cliente();
				recarregaCliente();
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
		else
		{
			FacesUtils.adicionaMensagemDeErro("não é possível salvar esse cliente");
		}
	
		}else{
			FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar esta operação");
		}
	}

	
	public void excluir(){
		if(new VerificaPermissao().isSupervisor()){
		if(clienteSelected!=null && clienteSelected.getId()>0)
		{
			if(new ClienteDAO().delete(clienteSelected))
			{
				System.out.println("Operação realizada com sucesso!");
				clienteSelected = new Cliente();
				
				recarregaCliente();
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
		else
		{
			FacesUtils.adicionaMensagemDeErro("não é possível salvar esse cliente");
		}
		}else{
			FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar essa operação");
			
		}
		
	}

	
	public void limpar(){
		
		clienteSelected = new Cliente();
		clienteSelectedTable = new Cliente();
		
	}


	public Cliente getClienteSelected() {
		return clienteSelected;
	}


	public void setClienteSelected(Cliente clienteSelected) {
		this.clienteSelected = clienteSelected;
	}


	public Cliente getClienteSelectedTable() {
		return clienteSelectedTable;
	}


	public void setClienteSelectedTable(Cliente clienteSelectedTable) {
		this.clienteSelectedTable = clienteSelectedTable;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}



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


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public int getJacadastrado() {
		return jacadastrado;
	}


	public void setJacadastrado(int jacadastrado) {
		this.jacadastrado = jacadastrado;
	}
	
	
	
}
