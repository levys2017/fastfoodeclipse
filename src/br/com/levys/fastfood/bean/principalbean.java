package br.com.levys.fastfood.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.dao.PedidoDAO;
import br.com.levys.fastfood.dao.ProdutoDAO;
import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Cliente;
import br.com.levys.fastfood.modelo.ItemPedido;
import br.com.levys.fastfood.modelo.Pedido;
import br.com.levys.fastfood.modelo.Produto;
import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.util.FacesUtils;
import br.com.levys.fastfood.util.VerificaPermissao;


@ManagedBean
@SessionScoped
public class principalbean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<Produto> produtos      = new ArrayList<Produto>();
	private Produto       produtoSelected = new Produto();
	private Produto       produtoSelectedTable = new Produto();
	private Pedido   	  pedidoSelected = new Pedido();
	private Pedido   	  pedidoSelectedTable = new Pedido();
	private List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
	private Cliente clienteSelected = new Cliente();
	private ItemPedido itemPedidoSelected = new ItemPedido();
	private ItemPedido itemPedidoSelectedTable = new ItemPedido();
	
	private List<Cliente> clientes              = new ArrayList<Cliente>();
	private Cliente       clienteSelectedTable  = new Cliente();
	private int    jacadastrado;
	private String email, nome, telefone, senha, n_cartao, email_parceiro;
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	
	private List<Pedido> parcerias = new ArrayList<Pedido>();

	private List<Pedido> pedidos_cozinha = new ArrayList<Pedido>();
	private int tp_pag;/// 1 - pagar o restante   2 - parceiro
	
	public principalbean(){
	produtos= new ProdutoDAO().getAllOrderAsc(Produto.class, "nome");
	
    produtoSelected = new Produto();
  
	
	}
	
	public void selecionaRowTable(AjaxBehaviorEvent ae) {
		if (produtoSelectedTable != null) {
			produtoSelected = produtoSelectedTable;
		}

	}
	
	
	public void selecionaRowItemTable(AjaxBehaviorEvent ae) {
		if (itemPedidoSelectedTable != null) {
			itemPedidoSelected = itemPedidoSelectedTable;
		}

	}
	
	
	public void zerar() {
		pedidoSelected = new Pedido();
		itensPedido = new ArrayList<ItemPedido>();
		produtoSelected = new Produto();
		jacadastrado=0;
		
		email="";
		nome="";
		telefone=""; 
		senha="";
		n_cartao="";
		email_parceiro="";
		tp_pag=0;
	}
	
	

	public void zerartudo() {
		pedidoSelected = new Pedido();
		itensPedido = new ArrayList<ItemPedido>();
		produtoSelected = new Produto();
		clienteSelected = new Cliente();	
		nome="";
		telefone=""; 
		n_cartao="";
		email_parceiro="";
		tp_pag=0;
		
	}
	
	
	public void informaParceiro() {
		
		Cliente p = new ClienteDAO().getAllbyEmail(email_parceiro);
		
		if(p.getId()>0) {
			pedidoSelected.setParceiro(p);
			if(new PedidoDAO().save(pedidoSelected)) {
				zerar();
				FacesUtils.adicionaMensagemDeInformacao("Conta dividida com sucesso, aguardando pagamento do parceiro");
			}else {
				
				FacesUtils.adicionaMensagemDeFatal("Falha ao realizar a operação, contacte o suporte");		
			}
			
			
		}else {
			
			FacesUtils.adicionaMensagemDeAdvertencia("Parceiro não cadastrado, ou email incorreto");
		}
		
		
		
	}
	
	
	

	private void recarregaProduto(){
		
		produtos= new ProdutoDAO().getAllOrderAsc(Produto.class, "descricao");
		
	}
	
	public void salvar(){
		if(new VerificaPermissao().isSupervisor()){
		if(produtoSelected!=null)
		{
			if(new ProdutoDAO().save(produtoSelected))
			{
				System.out.println("Operação realizada com sucesso!");
				produtoSelected = new Produto();
				recarregaProduto();
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
		else
		{
			FacesUtils.adicionaMensagemDeErro("não é possível salvar esse produto");
		}
	
		}else{
			FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar esta operação");
		}
	}

	
	public void excluir(){
		String ret = "";
	
			
		if(new VerificaPermissao().isSupervisor()){
		if(pedidoSelected!=null && pedidoSelected.getId()>0)
		{	if(new PedidoDAO().deletePedido(pedidoSelected, itensPedido))
			{
				System.out.println("Operação realizada com sucesso!");
				
				pedidoSelected =new Pedido();
				recarregaProduto();
				itensPedido.clear();
				ret = "cardapio? faces-redirect=true";
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
		else
		{
			FacesUtils.adicionaMensagemDeErro("Sem itens selecionados");
		}
		}else{
			FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar essa operação");
			
		}
		
	}
	
	
	
	private boolean salvaPedido() {
		boolean ret=false;
			if(pedidoSelected==null)
			{pedidoSelected = new Pedido();}
			
			Date date = new Date();
			SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy"); 
			SimpleDateFormat h = new SimpleDateFormat("H:m:s");
			String data = d.format(date);
			String hora = h.format(date);
			
			pedidoSelected.setData(data);
			pedidoSelected.setHora(hora);
			pedidoSelected.setCliente(clienteSelected);
			
			ret = new PedidoDAO().save(pedidoSelected);
		
		
		return ret;
	}
	
	
	public void additem() {
		if(pedidoSelected!=null && produtoSelected!=null) {
			
			if(pedidoSelected.getId()<=0) {
				
				salvaPedido();
			}

				ItemPedido ip = new ItemPedido();
				
				ip.setPedido(pedidoSelected);
				ip.setProduto(produtoSelected);
				ip.setSub_total(produtoSelected.getValor());
				ip.setTipo(produtoSelected.getCategoria());
			
			itensPedido.add(ip);	
			recalculaPedido();
			
			System.out.println("Item adicionado!!!");
			FacesUtils.adicionaMensagemDeInformacao("Item adicionado!!!");
		}
	}
	
	public void removeItem() {
			
			  for (int i = 0; i < itensPedido.size(); i++) {
			    if (itensPedido.get(i).equals(itemPedidoSelected)) {
			    	itensPedido.remove(i);
			    
			    	recalculaPedido();
			    	System.out.println("Item removido!!!");
			    	FacesUtils.adicionaMensagemDeInformacao("Item removido!!!");
			    }
			  }
			}
		
	
	private void recalculaPedido() {
		
		pedidoSelected.setValor_total(0);
		for(ItemPedido ip: itensPedido) {
			
			pedidoSelected.setValor_total(pedidoSelected.getValor_total()+ip.getProduto().getValor());	
			
		}
	}
	
	
	public String fecharPedido() {
	String ret ="#";
		if(itensPedido!=null) {
			for(ItemPedido ip: itensPedido) {
				pedidoSelected.setStatus(1);
				new PedidoDAO().savePedido(pedidoSelected, itensPedido);
				
				ret = "pedidocliente? faces-redirect=true";
			}
			
		}
		
		return ret;
		
	}

	public void limpar(){
		
		produtoSelected = new Produto();
		produtoSelectedTable = new Produto();
		pedidoSelected = new Pedido();
		recalculaPedido();
		
	}


	public Produto getProdutoSelected() {
		return produtoSelected;
	}


	public void setProdutoSelected(Produto produtoSelected) {
		this.produtoSelected = produtoSelected;
	}


	public Produto getProdutoSelectedTable() {
		return produtoSelectedTable;
	}

	public Pedido getPedidoSelectedTable() {
		return pedidoSelectedTable;
	}

	public void setPedidoSelectedTable(Pedido pedidoSelectedTable) {
		this.pedidoSelectedTable = pedidoSelectedTable;
	}


	public void setProdutoSelectedTable(Produto produtoSelectedTable) {
		this.produtoSelectedTable = produtoSelectedTable;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public Pedido getPedidoSelected() {
		return pedidoSelected;
	}

	public void setPedidoSelected(Pedido pedidoSelected) {
		this.pedidoSelected = pedidoSelected;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public String status(){
		String status = "";
				switch(pedidoSelected.getStatus()) { 
				case 1: status = "Aguardando pagamento...";
				break;
				case 2: status = "Pagamento efetuado...";
				break;
				case 3: status = "Em preparo...";
				break;
				case 4: status = "Pronto!!!";
				break;
				default:status = "Faça seu pedido!!!";
				break; 
			}return status;
	}
			
	
	public String gotobemvindo() {
		String ret="#";
		ret = "bemvindo? faces-redirect=true";
		
		return ret;
	}
	
	
	public String gotoCardapio() {
		String ret="#";
		ret = "cardapio? faces-redirect=true";
		
		return ret;
	}

	
	public String gotoPagamento() {
		String ret="#";
		ret = "pagamento? faces-redirect=true";
		
		return ret;
	}
	
	public String gotoPedidoCliente() {
		String ret="#";
		ret = "pedidocliente? faces-redirect=true";
		
		return ret;
	}
	
	
	public String gotoFecharContaDinheiro() {
		String ret="#";
		
	//	pedidoSelected.setStatus(status);
		
		new PedidoDAO().savePedido(pedidoSelected, itensPedido);
		new ClienteDAO().save(pedidoSelected.getCliente());
		
			
		
		ret = "fimdinheiro? faces-redirect=true";
		
		return ret;
	}
	
	
	
	
	
	public String gotoPagarParceiro() {
		
		
		return  "fimcartaoparceria? faces-redirect=true";
	}
	
	
	public void pagarrestante() {
		tp_pag=1;
		
	}
	
	public void enviar_parceiro() {
		tp_pag=2;
		
	}
	
	
	
	
	public String quitarContaParceiro() {
		String ret="#";
		
	//	pedidoSelected.setValor_pago(pedidoSelected.getValor_total());
		
		
		
		pedidoSelected.setStatus(2);
		
		pedidoSelected.setValor_pago(pedidoSelected.getValor_total());
		
			
		new PedidoDAO().savePedido(pedidoSelected, itensPedido);
		new ClienteDAO().save(pedidoSelected.getCliente());
		

		ret = "bemvindo? faces-redirect=true";
		
		return ret;
	}
	
	
	
	


	public String gotoFecharContaCartao() {
		String ret="#";
		
	//	pedidoSelected.setValor_pago(pedidoSelected.getValor_total());
		
		if(pedidoSelected.getSt_pagamento()==1) {
		
		pedidoSelected.setStatus(1);
		}else if(pedidoSelected.getSt_pagamento()==2) {
			pedidoSelected.setStatus(2);
		}
			
		new PedidoDAO().savePedido(pedidoSelected, itensPedido);
		new ClienteDAO().save(pedidoSelected.getCliente());
		

		ret = "fimcartao? faces-redirect=true";
		
		return ret;
	}

	public ItemPedido getItemPedidoSelected() {
		return itemPedidoSelected;
	}

	public void setItemPedidoSelected(ItemPedido itemPedidoSelected) {
		this.itemPedidoSelected = itemPedidoSelected;
	}
	
	public void aceitarPedido() {
		if(pedidoSelectedTable != null && pedidoSelectedTable.getStatus() ==2) {
			pedidoSelected.setStatus(3);
		}else {
			
			System.out.println("Pedido inválido!! ");
			  }
	}
	
	public void finalizarPedido() {
		if(pedidoSelected != null && pedidoSelected.getStatus() ==3) {
			pedidoSelected.setStatus(4);
		}else {
			
			System.out.println("Pedido inválido!! ");
		}
		
	}
	
	
public void atualizatela() {
		
		if(jacadastrado==0) {
		jacadastrado = 1;
		}else {
			jacadastrado=0;
		}
		
	}
	
	
	public String loginCliente() {
		
		zerartudo();
		
		
		String ret="#";
		
		if(!email.isEmpty()&&!senha.isEmpty())
		{
			Usuario utmp = new UsuarioDAO().Login(email, senha);
			if(utmp!=null){
				clienteSelected = new ClienteDAO().getAllbyUsuario(utmp);
				HttpSession sessions = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);   
                sessions.setAttribute("usuario",clienteSelected.getLogin() );
             
				utmp=null;
				if(clienteSelected.getLogin().getPerfil()==0) {
				ret = "cliente/bemvindo?faces-redirect=true";
				
				} else if (clienteSelected.getLogin().getPerfil()==2) {
					ret = "pages/cozinha?faces-redirect=true";	
					
				}else {
					ret = "pages/pedidoatendimento?faces-redirect=true";
					
				}
			}else {
				
				FacesUtils.adicionaMensagemDeFatal("Usuario e/ou senha inválidos");
			}
			
		}else {
			FacesUtils.adicionaMensagemDeAdvertencia("Informe o email e a senha");
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
						FacesUtils.adicionaMensagemDeInformacao("Cadastro efetuado com sucesso");
						
						loginCliente();
						
						ret = "cliente/bemvindo?faces-redirect=true";
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
		}
		
		return ret;
		
	}

	
	private void recarregaCliente(){
		
		clientes = new ClienteDAO().getAllOrderAsc(Cliente.class, "nome");
	}
	
	public void salvarcliente(){
		if(new VerificaPermissao().isSupervisor()){
		if(clienteSelected!=null)
		{
			if(new ClienteDAO().save(clienteSelected))
			{
				FacesUtils.adicionaMensagemDeInformacao("Alterado com Sucesso!");
				System.out.println("Operação realizada com sucesso!");
			
			}
			else
			{
				System.out.println("Falha ao realizar a operação");
			}
		}
		else
		{
			FacesUtils.adicionaMensagemDeErro("Não é possível salvar esse cliente");
		}
	
		}else{
			FacesUtils.adicionaMensagemDeErro("Você não possui permissão para efetuar esta operação");
		}
	}

	
	public void excluircliente(){
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
			FacesUtils.adicionaMensagemDeErro("Não é possível salvar esse cliente");
		}
		}else{
			FacesUtils.adicionaMensagemDeErro("Você não possui permissão para efetuar essa operação");
			
		}
		
	}

	
	public void limparCliente(){
		
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


	public String getN_cartao() {
		return n_cartao;
	}


	public void setN_cartao(String n_cartao) {
		this.n_cartao = n_cartao;
	}

	public ItemPedido getItemPedidoSelectedTable() {
		return itemPedidoSelectedTable;
	}

	public void setItemPedidoSelectedTable(ItemPedido itemPedidoSelectedTable) {
		this.itemPedidoSelectedTable = itemPedidoSelectedTable;
	}

	public List<Pedido> getPedidos() {
		
		  if(clienteSelected!=null&&clienteSelected.getId()>0) {
			    pedidos = new PedidoDAO().getAllbyCliente(clienteSelected);
		  }
		
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public List<Pedido> getPedidos_cozinha() {
		
		if(pedidos!=null) {
			pedidos_cozinha = new PedidoDAO().getAllbyStatus(clienteSelected);
		}
		return pedidos_cozinha;
	}

	public void setPedidos_cozinha(List<Pedido> pedidos_cozinha) {
		this.pedidos_cozinha = pedidos_cozinha;
	}

	public int getTp_pag() {
		return tp_pag;
	}

	public String getEmail_parceiro() {
		return email_parceiro;
	}

	public void setEmail_parceiro(String email_parceiro) {
		this.email_parceiro = email_parceiro;
	}
	
	
	public List<Pedido> getParcerias() {
		 if(clienteSelected!=null&&clienteSelected.getId()>0) {
			    parcerias = new PedidoDAO().getAllbyParceiro(clienteSelected);
		  }
		return parcerias;
	}
	
	public String logout() {
		String ret = "#";
		FacesContext facesContext = FacesContext.getCurrentInstance(); 
		HttpSession session = (HttpSession) facesContext .getExternalContext().getSession(false); 
		session.invalidate();
		
		ret = "/welcome?faces-redirect=true";

		return ret; 
	}

	
	
	
	
	
}