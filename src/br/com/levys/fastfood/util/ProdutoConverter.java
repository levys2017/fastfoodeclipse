package br.com.levys.fastfood.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.levys.fastfood.dao.ClienteDAO;
import br.com.levys.fastfood.modelo.Cliente;



public class ProdutoConverter implements Converter {
	


	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {  
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(submittedValue);  
                ClienteDAO dao = new ClienteDAO();
                Cliente E = dao.getById(Cliente.class, number, "id");
               if(E==null)E = new Cliente();
               //System.out.println("converter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+E.getId());
               return E;
            } catch(NumberFormatException exception) {  
                //System.out.println("pequena falha"); 
                return null;
            }  
        }  
  
       
    }  
  
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
        	
        	 
        	 return "";  
        } else {  
        	  
        	 String ret = String.valueOf(((Cliente) value).getId());  
        return ret;
        }  
    }  

}
