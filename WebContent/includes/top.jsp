<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<fmt:setLocale value="pt-BR" />
 
<%@ include file="cliquecerto.css"%>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>

<head>
<title>Clique Certo Leilões</title>
</head>

<body bgcolor="#F1ED99" >
<br><br>
<!--################################ Quadro Login e Menu ###############################################-->
<div id="quadroMenuTop">
	<div class="boxLogin">
		<c:choose>
			<c:when test="${usr == null}">
			<form name="login" method="post" action="login.do">
				Email: <input type="text" id="email" name="email" class="textField" style="width: 40%; margin-top: 4%; margin-bottom: 2%"/> 
				Senha: <input type="password" id="pass" name="pass" class="textField" style="width: 20%;" onkeyup="javascript:verificaEnter(event)"/>
				<div  style="float: right;">
					<a href="javascript:login()" ><img style="margin-top: 2px;" src="../imagens/login.png"  ></a>
				</div>
				<a style="margin-left: 5%" href="#">Cadastre-se |</a><a href="#">Esqueci minha senha</a>
			</form>
			</c:when>
			<c:otherwise>
				<div><br>				
					<div style="width: 30%; font-size: 14pt; float: left;">
						<span style="margin-left: 10%;">${usr.login}<br> 
							<a  style="margin-left: 10%" href="logout.do">Deslogar</a>
						</span>
					</div> 
					<div style="width: 10%; font-size: 18pt; float:left; text-align: center;">
						<div id="nroLancesLogin">${usr.lances}</div> <div style=" margin-top: -20px"><span style="font-size:10pt;">lances</span></div>
					</div> 
					<div style="width: 50%; float:left; text-align: center">
						<a href="javascript:login()" ><img style="margin-top: -40px;" src="../imagens/comprar.png" title="comprar lances"></a>
					</div>
				</div>
			</c:otherwise>
		</c:choose> 
	</div>
	<table class="menuTop">
		<tr>
			<td class="menuTop">
				<a href="montarHome.do" class="menuTopLink" >Home</a>
			</td>
			<td class="menuTop">
				<c:choose>
					<c:when test="${usr == null}">
						<a href="#" class="menuTopLink">Cadastrar</a>
					</c:when>
					<c:otherwise>
						<a href="#" class="menuTopLink">Minha Conta</a>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="menuTop">
				<a href="#" class="menuTopLink">Comprar Lances</a>
			</td>
			<td class="menuTop">
				<a href="#" class="menuTopLink">Como Funciona</a>
			</td>
			<td class="menuTop">
				<a href="#" class="menuTopLink">Depoimentos</a>
			</td>
			<td class="menuTop">
				<a href="#" class="menuTopLink">Ajuda</a>
			</td>
			<td class="menuTop" style="width: 28%" id="relogio"></td>
		</tr>
	</table>
</div>
<!--################################ FIM Quadro Login e Menu ###########################################-->
</body>

<script type="text/javascript">

$(document).ready(function() {
	setInterval('atualizaRelogio()', 1000);
});

function atualizaRelogio() {
	$.ajax({
		  url: "atualizaRelogio.do",
		  method: "post",
		  dataType: "html",
		  success: function( data ) {
			 $('#relogio').html(data);
		  }
		});
}

function verificaEnter(event) {
	if (event.keyCode == 13) {
		login();
	}
}

function login() {
	document.login.submit();
}

function deslogar() {
	alert("aaa");
	document.login.action = "logout.do";
	document.login.submit();
}

</script>
</html>