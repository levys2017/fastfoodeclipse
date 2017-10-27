package br.com.levys.fastfood.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.levys.fastfood.dao.UsuarioDAO;
import br.com.levys.fastfood.modelo.Usuario;
import br.com.levys.fastfood.util.FacesUtils;
import br.com.levys.fastfood.util.VerificaPermissao;


@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{

		private static final long serialVersionUID = 1L;

		
		private List<Usuario> usuarios      = new ArrayList<Usuario>();
		private Usuario      usuarioSelected = new Usuario();
		private Usuario      usuarioSelectedTable = new Usuario();
		
		
		
		public UsuarioBean(){
		usuarios= new UsuarioDAO().getAllOrderAsc(Usuario.class, "nome");
			
		}
		
		public void selecionaRowTable(AjaxBehaviorEvent ae) {
			if (usuarioSelectedTable != null) {
				usuarioSelected = usuarioSelectedTable;
			}

		}
		
		
		
		private void recarregaUsuario(){
			
			usuarios= new UsuarioDAO().getAllOrderAsc(Usuario.class, "descricao");
		}
		
		public void salvar(){
			if(new VerificaPermissao().isSupervisor()){
			if(usuarioSelected!=null)
			{
				if(new UsuarioDAO().save(usuarioSelected))
				{
					System.out.println("Operação realizada com sucesso!");
					usuarioSelected = new Usuario();
					recarregaUsuario();
				}
				else
				{
					System.out.println("Falha ao realizar a operação");
				}
			}
			else
			{
				FacesUtils.adicionaMensagemDeErro("não é possível salvar esse beneficiario");
			}
		
			}else{
				FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar esta operação");
			}
		}

		
		public void excluir(){
			if(new VerificaPermissao().isSupervisor()){
			if(usuarioSelected!=null && usuarioSelected.getId()>0)
			{
				if(new UsuarioDAO().delete(usuarioSelected))
				{
					System.out.println("Operação realizada com sucesso!");
					usuarioSelected = new Usuario();
					
					recarregaUsuario();
				}
				else
				{
					System.out.println("Falha ao realizar a operação");
				}
			}
			else
			{
				FacesUtils.adicionaMensagemDeErro("não é possível salvar esse beneficiario");
			}
			}else{
				FacesUtils.adicionaMensagemDeErro("você não possui permissão para efetuar essa operação");
				
			}
			
		}


		
		public void limpar(){
			
			usuarioSelected = new Usuario();
			usuarioSelectedTable = new Usuario();
			
		}


		public Usuario getUsuarioSelected() {
			return usuarioSelected;
		}


		public void setUsuarioSelected(Usuario usuarioSelected) {
			this.usuarioSelected = usuarioSelected;
		}


		public Usuario getUsuarioSelectedTable() {
			return usuarioSelectedTable;
		}




		public void setUsuarioSelectedTable(Usuario usuarioSelectedTable) {
			this.usuarioSelectedTable = usuarioSelectedTable;
		}

		public List<Usuario> getUsuarios() {
			return usuarios;
		}

}
