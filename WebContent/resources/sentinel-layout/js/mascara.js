
function Mascara(o,f){
        v_obj=o
        v_fun=f
        setTimeout("execmascara()",1)
    }
    
    /*Função que Executa os objetos*/
    function execmascara(){
        v_obj.value=v_fun(v_obj.value)
    }
    ///////
    function masccnpjcpf(v){

    	v=v.replace(/\D/g,"") //Remove tudo o que não é dígito

    	if (v.length<=11) // aqui verifica quantos digitos
    	{
    	v=v.replace(/(\d{3})(\d)/,"$1.$2") //Coloca um ponto entre o terceiro e quarto dígitos
    	v=v.replace(/(\d{3})(\d)/,"$1.$2") //Coloca um ponto entre o terceiro e quarto dígitos
    	//de novo (para o segundo bloco de números)
    	v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2") //Coloca um hífen entre o terceiro e o quarto dígitos
    	}
    	else
    	{
    	v=v.replace(/\D/g,"") //Remove tudo o que não é dígito
    	v=v.replace(/^(\d{2})(\d)/,"$1.$2") //Coloca ponto entre o segundoe o terceiro d
    	v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3") //Coloca ponto entre o quinto o sexto dígitos
    	v=v.replace(/\.(\d{3})(\d)/,".$1/$2") //Coloca uma barra entre o oitavo e o nono dígitos
    	v=v.replace(/(\d{4})(\d)/,"$1-$2") //Coloca um hífen depois do oco de quatro dígitos
    	}
    	return v
    	}
    
    
    
    
    ///////
    
    
    /*Função que Determina as expressões regulares dos objetos*/
    function leech(v){
        v=v.replace(/o/gi,"0")
        v=v.replace(/i/gi,"1")
        v=v.replace(/z/gi,"2")
        v=v.replace(/e/gi,"3")
        v=v.replace(/a/gi,"4")
        v=v.replace(/s/gi,"5")
        v=v.replace(/t/gi,"7")
        return v
    }
    
    /*Função que permite apenas numeros*/
    function Integer(v){
        return v.replace(/\D/g,"")
    }
    
    /*Função que padroniza  telefone               (11) 4184-1241         (11) 4184-1241*/
    function Telefone(v){
        v=v.replace(/\D/g,"")                 
        v=v.replace(/^(\d\d)(\d)/g,"($1) $2") 
        v=v.replace(/(\d{4})(\d)/,"$1-$2")    
        return v
    }
    
    /*Função que padroniza telefone               (11) 41841241         (11) 41841241*/
    function TelefoneCall(v){
        v=v.replace(/\D/g,"")                 
        v=v.replace(/^(\d\d)(\d)/g,"($1) $2")    
        return v
    }
    
    /*Função que padroniza CPF*/
    function Cpf(v){
        v=v.replace(/\D/g,"")                    
        v=v.replace(/(\d{3})(\d)/,"$1.$2")       
        v=v.replace(/(\d{3})(\d)/,"$1.$2")       
                                                 
        v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2") 
        return v
    }
    
    /*Função que padroniza CEP*/
    function Cep(v){
        v=v.replace(/D/g,"")                
        v=v.replace(/^(\d{5})(\d)/,"$1-$2") 
        return v
    }
    
    /*Função que padroniza CNPJ*/
    function Cnpj(v){
        v=v.replace(/\D/g,"")                   
        v=v.replace(/^(\d{2})(\d)/,"$1.$2")     
        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3") 
        v=v.replace(/\.(\d{3})(\d)/,".$1/$2")           
        v=v.replace(/(\d{4})(\d)/,"$1-$2")              
        return v
    }
    
    /*Função que permite apenas numeros Romanos*/
    function Romanos(v){
        v=v.toUpperCase()             
        v=v.replace(/[^IVXLCDM]/g,"") 
        
        while(v.replace(/^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$/,"")!="")
            v=v.replace(/.$/,"")
        return v
    }
    
    /*Função que padroniza o Site*/
    function  Site(v){
        v=v.replace(/^http:\/\/?/,"")
        dominio=v
        caminho=""
        if(v.indexOf("/")>-1)
            dominio=v.split("/")[0]
            caminho=v.replace(/[^\/]*/,"")
            dominio=dominio.replace(/[^\w\.\+-:@]/g,"")
            caminho=caminho.replace(/[^\w\d\+-@:\?&=%\(\)\.]/g,"")
            caminho=caminho.replace(/([\?&])=/,"$1")
        if(caminho!="")dominio=dominio.replace(/\.+$/,"")
            v="http://"+dominio+caminho
        return v
    }

    /*Função que padroniza DATA*/
    function Data(v){
        v=v.replace(/\D/g,"") 
        v=v.replace(/(\d{2})(\d)/,"$1/$2") 
        v=v.replace(/(\d{2})(\d)/,"$1/$2") 
        return v
    }
    
    /*Função que padroniza DATA*/
    function Hora(v){
        v=v.replace(/\D/g,"") 
        v=v.replace(/(\d{2})(\d)/,"$1:$2")  
        return v
    }
    
    /*Função que padroniza valor monétario*/
    function Valor(v){
        v=v.replace(/\D/g,"") //Remove tudo o que não é dígito
        v=v.replace(/^([0-9]{3}\.?){3}-[0-9]{2}$/,"$1.$2");
        //v=v.replace(/(\d{3})(\d)/g,"$1,$2")
        v=v.replace(/(\d)(\d{2})$/,"$1.$2") //Coloca ponto antes dos 2 últimos digitos
        return v
    }
    
    /*Função que padroniza Area*/
    function Area(v){
        v=v.replace(/\D/g,"") 
        v=v.replace(/(\d)(\d{2})$/,"$1.$2") 
        return v
        
    }
    function limpa(nome_id){
    	getElement(nome_id).value = "";

    	}

    function getElement(id) { 
    	return document.getElementById ? document.getElementById(id) : 
    	document.all ? document.all(id) : null; 
    	}

    function formatCurrency(num) {
    		num = num.toString().replace('.','');
    		num = num.toString().replace(',','.');
    		num = num.toString().replace(/\$|\,/g,'');
    		if(isNaN(num))
    		num = "0";
    		sign = (num == (num = Math.abs(num)));
    		num1 = Math.floor(num);
    		cents = num-num1;
    		cents = Math.floor(cents*10000+0.50000000001);
    		cents = cents/10000;
    		num=num1.toString();
    		ct = cents.toString()
    		if (cents>0) ct = ct.substring(2,ct.length+1);
    		if(ct.length<2)
    		ct = ct + "0";
    		if(ct.length<2)
    		ct = ct + "0";
    		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    		num = num.substring(0,num.length-(4*i+3))+'.'+
    		num.substring(num.length-(4*i+3));
    		return (((sign)?'':'-') + num + ',' + ct);
    		}

    function BlockKeybord()
    	{
    	if(window.event) // IE
    	{
    	if((event.keyCode < 48) || (event.keyCode > 57))
    	{
    	event.returnValue = false;
    	}
    	}
    	else if(e.which) // Netscape/Firefox/Opera
    	{
    	if((event.which < 48) || (event.which > 57))
    	{
    	event.returnValue = false;
    	}
    	}


    	}

    function troca(str,strsai,strentra)
    	{
    	while(str.indexOf(strsai)>-1)
    	{
    	str = str.replace(strsai,strentra);
    	}
    	return str;
    	}

    function FormataMoeda(campo,tammax,teclapres,caracter)
    	{
    	if(teclapres == null || teclapres == "undefined")
    	{
    	var tecla = -1;
    	}
    	else
    	{
    	var tecla = teclapres.keyCode;
    	}

    	if(caracter == null || caracter == "undefined")
    	{
    	caracter = ".";
    	}

    	vr = campo.value;
    	if(caracter != "")
    	{
    	vr = troca(vr,caracter,"");
    	}
    	vr = troca(vr,"/","");
    	vr = troca(vr,",","");
    	vr = troca(vr,".","");

    	tam = vr.length;
    	if(tecla > 0)
    	{
    	if(tam < tammax && tecla != 8)
    	{
    	tam = vr.length + 1;
    	}

    	if(tecla == 8)
    	{
    	tam = tam - 1;
    	}
    	}
    	if(tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105)
    	{
    	if(tam <= 2)
    	{
    	campo.value = vr;
    	}
    	if((tam > 2) && (tam <= 5))
    	{
    	campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam);
    	}
    	if((tam >= 6) && (tam <= 8))
    	{
    	campo.value = vr.substr(0, tam - 5) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
    	}
    	if((tam >= 9) && (tam <= 11))
    	{
    	campo.value = vr.substr(0, tam - 8) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
    	}
    	if((tam >= 12) && (tam <= 14))
    	{
    	campo.value = vr.substr(0, tam - 11) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
    	}
    	if((tam >= 15) && (tam <= 17))
    	{
    	campo.value = vr.substr(0, tam - 14) + caracter + vr.substr(tam - 14, 3) + caracter + vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
    	}
    	}
    	}

    	function maskKeyPress(objEvent)
    	{
    	var iKeyCode;

    	if(window.event) // IE
    	{
    	iKeyCode = objEvent.keyCode;
    	if(iKeyCode>=48 && iKeyCode<=57) return true;
    	return false;
    	}
    	else if(e.which) // Netscape/Firefox/Opera
    	{
    	iKeyCode = objEvent.which;
    	if(iKeyCode>=48 && iKeyCode<=57) return true;
    	return false;
    	}
    	}



