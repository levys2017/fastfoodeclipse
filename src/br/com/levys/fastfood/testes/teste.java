package br.com.levys.fastfood.testes;

import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.modelo.Cliente;

public class teste {

	public static void main(String[] args) {

		Date date = new Date();
		SimpleDateFormat d = new SimpleDateFormat("dd/MM/YYYY"); 
		SimpleDateFormat h = new SimpleDateFormat("H:m:s");
		String data = d.format(date);
		String hora = h.format(date);


		System.out.println("data: "+data);
		System.out.println(hora);
		
		
		
	}

	
}
