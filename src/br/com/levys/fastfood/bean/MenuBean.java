package br.com.levys.fastfood.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class MenuBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	public String gotoCardapio() {
		
		return "cardapio?faces-redirect=true";
		
	}
	
	

	
	public String gotoMeusPedidos() {
		
		return "meuspedidos?faces-redirect=true";
		
	}
	
	
	
	

}
