package br.com.levys.fastfood.util;

import org.hibernate.cfg.AnnotationConfiguration;  
import org.hibernate.tool.hbm2ddl.SchemaExport;  
  
 
public class GeraBanco {  

public static void main(String[] args) {
	
	  
    AnnotationConfiguration conf = new AnnotationConfiguration();  
     
    conf.configure();  
    
    SchemaExport se = new SchemaExport(conf);  
    
    se.create(true, true);  
    
    System.out.println("fim");
}

}  