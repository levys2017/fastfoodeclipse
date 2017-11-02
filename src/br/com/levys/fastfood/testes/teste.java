package br.com.levys.fastfood.testes;

import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.modelo.Cliente;

public class teste {

	public static void main(String[] args) {
		String Pessoa;
		private List<Pessoa> pessoas = new ArrayList<>();

	    // Adiciona algumas pessoas.
	    pessoas.add(new Pessoa("José"));
	    pessoas.add(new Pessoa("Maria"));
	    pessoas.add(new Pessoa("Pedro"));

	    System.out.print("Pessoas cadastradas:\n");
	    for(int i = 0; i < pessoas.size(); i++)
	    {
	        System.out.print(pessoas.get(i).getNome() + "\n");
	    }

	    // Removendo Pedro:
	    for(int i = 0; i < pessoas.size(); i++)
	    {
	        Pessoa p = pessoas.get(i);

	        if(p.getNome().equals("Pedro"))
	        {
	            // Encontrou uma pessoa cadastrada com nome "Pedro".

	            // Remove.
	            pessoas.remove(p);

	            // Sai do loop.
	            break;
	        }
	    }

	    System.out.print("Pessoas cadastradas após remoção:\n");
	    for(int i = 0; i < pessoas.size(); i++)
	    {
	        System.out.print(pessoas.get(i).getNome() + "\n");
	    }
		
		
		
	}

	
}
