package br.com.levys.fastfood.testes;

import br.com.levys.fastfood.dao.ProdutoDAO;
import br.com.levys.fastfood.modelo.Produto;

public class teste {

	public static void main(String[] args) {
		
		Produto p = new Produto();
		
		p.setNome("Coca Cola");
		
		
		if(new ProdutoDAO().save(p)) {
			
			System.out.println("produto cadastrado com sucesso");
		}else {
			
			System.out.println("falha");
		}
		
		
		
		
	}

	
}
