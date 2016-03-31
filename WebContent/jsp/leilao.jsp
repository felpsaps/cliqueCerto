

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%  
    response.setHeader("Cache-Control", "no-cache");  
    response.setHeader("Pragma", "no-cache");  
    response.setDateHeader("Expires", 0);  
%> 

<html>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form name="form" id="form" action="lance.do" method="post">
<span id="spanPreco">${preco}</span>
<div style="font-size: 30" id="tempoDiv">
	${tempo}
</div>
<input type="button" value="lance" onclick="javascript:lance();">
<input type="hidden" value="${preco}" name="preco" id="preco">
<input type="hidden" value="${tempo}" name="tempo" id="tempo">
<input type="hidden" value="${maximoSegundos}" name="maximoSegundos" id="maximoSegundos">
<input type="hidden" value="01" name="leilaoId" id="leilaoId" >
</form>
</body>

<script type="text/javascript">


	var intervalId;
	var i = $('#tempo').val();
	function contagemRegressiva() {
	    --i;
	    document.getElementById('tempoDiv').innerHTML = i;
	    $('#tempo').val(i);
	    if(i == 0) {
	    	clearInterval(intervalId);
	    	alert("acabou");
	    }
	}
	
	$(document).ready(function() {
	   intervalId = setInterval("contagemRegressiva()", 1200);
	   setInterval("verificaPreco()", 200);
	});
	
	function lance() {
		/*var dataForm = $("#form").serialize();
		$.ajax({
			  url: "lance.do",
			  method: "post",
			  data: dataForm,
			  success: function( data ) {
				  	i = 150;
				    document.getElementById('segundos').innerHTML = 1500;
				    alert(data);
			  }
			});*/
			document.form.submit();
	}
	
	function verificaPreco() {
		var preco = $('#preco').val().toString();
		$.ajax({
			  url: "atualizarDados.do",
			  method: "post",
			  data: $("#form").serialize(),
			  dataType: "html",
			  success: function( data ) {
				  atualizaDados(data);
			  }
			});
	}
	
	function atualizaDados(data) {
		var dados = data.split(",");		
		  if (dados[0] != $('#preco').val()) {
			  $('#preco').val(dados[0]);
			  $('#spanPreco').html(dados[0]);					  
		  }
		$('#tempo').val(dados[1]);
		i = dados[1];
	}
	
	
</script>


</html>