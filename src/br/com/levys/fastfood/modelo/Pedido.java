package br.com.levys.fastfood.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.levys.fastfood.dao.PedidoDAO;

@Entity
public class Pedido implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="cliente")
    private Cliente cliente;
    private double valor_total;
    private int forma_pagamento;
    private int status;
    private double valor_pago;
    private String data;
	private String hora;
	
	@ManyToOne
	@JoinColumn(name="parceiro", nullable=true)
    private Cliente parceiro;
	
	private int status_parceiro;///0- aguardando aceitação  1 - pago  2 - recusado
	
	@Transient
    private String desc_status;
	
	@Transient
	private int barrastatus;
    
    @Transient
    List<ItemPedido> itens;
    
    @Transient
    private int st_pagamento;//0 ---- não pago  1 - bx parcial  - 2 - pago


    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public int getForma_pagamento() {
        return forma_pagamento;
    }

    public void setForma_pagamento(int forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(double valor_pago) {
        this.valor_pago = valor_pago;
    }

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}
   
	public void buscaItens() {
		
		itens = new PedidoDAO().getAllbyPedido(this);
	}
    
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
	public String getDesc_status() {
		String status = "";
		switch(this.status) { 
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

	public int getBarrastatus() {
		int status = 0;
		switch(this.status) { 
		case 1: status = 25;
		break;
		case 2: status = 50;
		break;
		case 3: status = 75;
		break;
		case 4: status = 100;
		break;
		default:status = 0;
		break; 
	}return status;	
	}

	public Cliente getParceiro() {
		return parceiro;
	}

	public void setParceiro(Cliente parceiro) {
		this.parceiro = parceiro;
	}

	public int getStatus_parceiro() {
		return status_parceiro;
	}

	public void setStatus_parceiro(int status_parceiro) {
		this.status_parceiro = status_parceiro;
	}

	public int getSt_pagamento() {
		
		if(this.valor_pago==0) {
			st_pagamento=0;
		}else if(this.valor_pago<this.valor_total) {
			
			st_pagamento=1;
		}else {
			st_pagamento=2;
		}
		
		return st_pagamento;
	}
	
	
	
	
}
