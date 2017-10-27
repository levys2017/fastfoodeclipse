package br.com.levys.fastfood.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.levys.fastfood.dao.ProdutoDAO;
import br.com.levys.fastfood.modelo.Produto;
import br.com.levys.fastfood.util.FacesUtils;
import br.com.levys.fastfood.util.VerificaPermissao;


@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<Produto> produtos      = new ArrayList<Produto>();
	private Produto      produtoSelected = new Produto();
	private Produto      produtoSelectedTable = new Produto();
	
	
	
	
	public ProdutoBean(){
	produtos= new ProdutoDAO().getAllOrderAsc(Produto.class, "nome");
		
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
