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
import br.com.levys.fastfood.modelo.Cliente;
import br.com.levys.fastfood.modelo.ItemPedido;
import br.com.levys.fastfood.modelo.Pedido;
import br.com.levys.fastfood.modelo.Produto;
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
	private List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
	private Cliente clienteSelected = new Cliente();
	private ItemPedido itemPedidoSelected = new ItemPedido();
	
	
	
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
				
				Date date = new Date();
				SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy"); 
				SimpleDateFormat h = new SimpleDateFormat("H:m:s");
				String data = d.format(date);
				String hora = h.format(date);
				
				ip.setData(data);
				ip.setHora(hora);
				ip.setPedido(pedidoSelected);
				ip.setProduto(produtoSelected);
				//ip.setQtd(produtoSelected.getQtd());
				//ip.setSub_total(ip.getQtd()*produtoSelected.getValor());
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
				
				HttpSession sessions = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
						.getSession(true);
				
				sessions.setAttribute("pedido", pedidoSelected);
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
				case 1: System.out.println("Aguardando pagamento...");
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
	
	
	
	
	
	public String gotoFecharContaDinheiro() {
		String ret="#";
		
	//	pedidoSelected.setStatus(status);
		
		new PedidoDAO().savePedido(pedidoSelected, itensPedido);
		new ClienteDAO().save(pedidoSelected.getCliente());
		
			
		
		ret = "fimdinheiro? faces-redirect=true";
		
		return ret;
	}
	
	

	
	
	public String gotoFecharContaCartao() {
		String ret="#";
		
			pedidoSelected.setValor_pago(pedidoSelected.getValor_total());
		
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
	
	
	
}