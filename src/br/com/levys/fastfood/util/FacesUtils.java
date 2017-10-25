package br.com.levys.fastfood.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


public class FacesUtils implements Serializable {
	
	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void adicionaMensagemDeErro(String mensagem) {
	FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage 
			= new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		facesContext.addMessage(null, facesMessage);
	}

	public static void adicionaMensagemDeInformacao(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage 
			= new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
		facesContext.addMessage(null, facesMessage);
	}
	
	public static void adicionaMensagemDeAdvertencia(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage 
			= new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
		facesContext.addMessage(null, facesMessage);
	}
	
	public static void adicionaMensagemDeFatal(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage 
			= new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagem, mensagem);
		facesContext.addMessage(null, facesMessage);
	}
	
	
	
	
	/**
	 * Limpa os dados dos componentes de edição e de seus filhos,
	 * recursivamente. Checa se o componente é instância de EditableValueHolder
	 * e 'reseta' suas propriedades.
	 * <p>
	 * Quando este método, por algum motivo, não funcionar, parta para ignorância
	 * e limpe o componente assim:
	 * <p><blockquote><pre>
	 * 	component.getChildren().clear()
	 * </pre></blockquote>
	 * :-)
	 */
	public void cleanSubmittedValues(UIComponent component) {
		if (component instanceof EditableValueHolder) {
			EditableValueHolder evh = (EditableValueHolder) component;
			evh.setSubmittedValue(null);
			evh.setValue(null);
			evh.setLocalValueSet(false);
			evh.setValid(true);
		}
		if(component.getChildCount()>0){
			for (UIComponent child : component.getChildren()) {
				cleanSubmittedValues(child);
			}
		}
	}
	
	public void cleanSubmittedValues(String componentName) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIComponent component = facesContext.getViewRoot().findComponent(componentName);
		if (component != null)
			cleanSubmittedValues(component);
	}
	
}
