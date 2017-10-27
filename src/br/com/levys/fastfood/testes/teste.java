package br.com.levys.fastfood.testes;

import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.modelo.Cliente;

public class teste {

	public static void main(String[] args) {
		
		Cliente c = new Cliente();
		Usuario user = new Usuario();
		
		user.setLogin("levy@asdas");
		user.setSenha("12345");
		c.setNome("Levy");
		c.setCpf("0555");
		
		if(new ClienteDAO().save(c)) {
			System.out.println("Usuario cadastrado com sucesso");
		}else {
			
			System.out.println("falha");
		}
		
		
		
		
	}

	
}
