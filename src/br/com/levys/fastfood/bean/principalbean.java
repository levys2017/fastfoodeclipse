package br.com.levys.fastfood.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.dao.PedidoDAO;
import br.com.levys.fastfood.dao.ProdutoDAO;
import br.com.levys.fastfood.modelo.Cliente;
import br.com.levys.fastfood.modelo.ItemPedido;
import br.com.levys.fastfood.modelo.Pedido;
import br.com.levys.fastfood.modelo.Produto;
import br.com.levys.fastfood.util.FacesUtils;
import br.com.levys.fastfood.util.VerificaPermissao;


@ManagedBean
@ViewScoped
public class principalbean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<Produto> produtos      = new ArrayList<Produto>();
	private Produto      produtoSelected = new Produto();
	private Produto      produtoSelectedTable = new Produto();
	private Pedido   pedidoSelected = new Pedido();
	private List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
	private Cliente clienteSelected = new Cliente();
	
	
	
	
	
	
	public principalbean(){
	produtos= new ProdutoDAO().getAllOrderAsc(Produto.class, "nome");
	
	clienteSelected = new ClienteDAO().getById(Cliente.class, 3, "id");////substituir pelo cliente logado
	 produtoSelected = new Produto();
		
	}
	
	public void selecionaRowTable(AjaxBehaviorEvent ae) {
		if (produtoSelectedTable != null) {
			produtoSelected = produtoSelectedTable;
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
		if(new VerificaPermissao().isSupervisor()){
		if(produtoSelected!=null && produtoSelected.getId()>0)
		{
			if(new ProdutoDAO().delete(produtoSelected))
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
			FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar essa operação");
			
		}
		
	}
	
	
	
	private boolean salvaPedido() {
		boolean ret=false;
			if(pedidoSelected==null)
			{pedidoSelected = new Pedido();}
			
			
			pedidoSelected.setCliente(clienteSelected);
			
			pedidoSelected.setStatus(0);
			pedidoSelected.setStatus(0);
			
			ret = new PedidoDAO().save(pedidoSelected);
		
		
		return ret;
	}

	
	public void additem() {
		if(pedidoSelected!=null && produtoSelected!=null) {
			
			if(pedidoSelected==null || pedidoSelected.getId()<=0) {
				
				salvaPedido();
			}
				

				ItemPedido ip = new ItemPedido();
				
				ip.setData(new Date());
				ip.setPedido(pedidoSelected);
				ip.setProduto(produtoSelected);
				ip.setQtd(produtoSelected.getQtd());
				ip.setSub_total(ip.getQtd()*produtoSelected.getValor());
				ip.setTipo(produtoSelected.getCategoria());
				
		
			
			
			
			itensPedido.add(ip);	
			
					
			
		}
	}
	
	
	
	

	
	public void limpar(){
		
		produtoSelected = new Produto();
		produtoSelectedTable = new Produto();
		
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




	public void setProdutoSelectedTable(Produto produtoSelectedTable) {
		this.produtoSelectedTable = produtoSelectedTable;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	
	
	}
