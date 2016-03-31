<%@ include file="/includes/top.jsp"%>

<div id="quadroGeral">
<!--################################ Quadro de leiloes #################################################-->	
	<h2>Leilões</h2>
	<div style="height: 1250px;" class="boxHome" id="divLeiloes">
		<c:forEach items="${leiloes}" var="l">		
			<input type="hidden" name="leilaoId" id="leilaoId" value="${l.id}">
			<div class="boxProduto">
				<div>
					<a href="#" class="boxProduto" onclick="javascript:entrarLeilao(${l.id})">${l.produto} </a>
					<div style="width: 100%; height: 180px; border-style: solid; border-width: 1px;">
						<a href="#" onclick="javascript:entrarLeilao(${l.id})">
							<img  style="width: 100%; height: 180px;" src="../imagens/teste.jpg"/>
							<c:if test="${l.status == 'F'}">
								<div class="tarjaPreta" id="tarjaPreta_${l.id}">
									Início do leilão:<br> 
									<span style="font-size: 20pt; color: #FFFF00;"><fmt:formatDate pattern="dd/MM/yyyy - HH:mm" value="${l.dtInicio}" /></span> <br>
								</div>
							</c:if>
						</a>
					</div>
				</div>
				<br><br><br>		
				<div><span style="font-size: 14pt; color: #FF0000;" id="spanPreco_${l.id}"> <fmt:formatNumber value="${l.preco}" minFractionDigits="2" type="currency"/></span></div>
				<input type="hidden" name="tempo_${l.id}" id="tempo_${l.id}" value="${l.tempo}">				
				<input type="hidden" name="preco_${l.id}" id="preco_${l.id}" value="<fmt:formatNumber value="${l.preco}" minFractionDigits="2" type="currency"/>">
				<div>
					<div id="imagemTimer_${l.id}">
						<c:choose>
							<c:when test="${l.status == 'F'}">
								<c:set var="classeTimer">tempoDesativado</c:set>
							</c:when>
							<c:otherwise>
								<c:if test="${l.tempo > 10}">
									<c:set var="classeTimer">tempoNormal</c:set>
								</c:if>	
								<c:if test="${l.tempo <= 10}">
									<c:set var="classeTimer">tempoAlerta</c:set>
								</c:if>
							</c:otherwise>
						</c:choose>					
						<c:choose>				
							<c:when test="${l.tempoAtual < 10}">		
								<div class="${classeTimer}">0</div>
								<div class="${classeTimer}">${l.tempoAtual}</div>
							</c:when>
							<c:otherwise>
								<div class="${classeTimer}">${l.tempo1}</div>						
								<div class="${classeTimer}">${l.tempo2}</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div style="float: left; margin-left: 20%;">
						<div class="btnLance" id="linkLance_${l.id}" onclick="javascript:contabilizaLance(${l.id})">LANCE</div>
					</div><br><br><br>
					<div id="usrArremate_${l.id}">OII</div>
				</div>
						
			</div>
		</c:forEach>
	
		<div class="boxPaginacao">	
			<a style="margin-left: 30%" href="#" class="btnNumeroPaginacaoIr">1</a>
			<div align="center" class="btnNumeroPaginacaoAtual">2</div>
			<a href="#" class="btnNumeroPaginacaoIr">3</a>
			<a href="#" class="btnNumeroPaginacaoIr">4</a>
			<a href="#" class="btnNumeroPaginacaoIr">5</a>	
			<a href="#" class="btnNumeroPaginacaoIr">6</a>
			<a href="#" class="btnNumeroPaginacaoIr">7</a>
			<a href="#" class="btnNumeroPaginacaoIr">8</a>
			<a href="#" class="btnNumeroPaginacaoIr">9</a>
			<a href="#" class="btnNumeroPaginacaoIr">10</a>
		</div>
	</div>
<!--################################ FIM Quadro de leiloes #############################################-->

<!--################################ Ultimos Ganhadores ################################################-->
	<h2>Últimos Ganhdores</h2>
	<div style="height: 735px; margin-top: 10px" class="boxHome">
		<div class="boxUltimosGanhadores">Hello</div>
		<div class="boxUltimosGanhadores">Hello</div>
		<div class="boxUltimosGanhadores">Hello</div>
		<div class="boxUltimosGanhadores">Hello</div>
		<div class="boxUltimosGanhadores">Hello</div>
		
		<br>
		<div class="boxPaginacao" style="margin-top: 15px">	
			<a style="margin-left: 30%" href="#" class="btnNumeroPaginacaoIr">1</a>
			<div align="center" class="btnNumeroPaginacaoAtual">2</div>
			<a href="#" class="btnNumeroPaginacaoIr">3</a>
			<a href="#" class="btnNumeroPaginacaoIr">4</a>
			<a href="#" class="btnNumeroPaginacaoIr">5</a>	
			<a href="#" class="btnNumeroPaginacaoIr">6</a>
			<a href="#" class="btnNumeroPaginacaoIr">7</a>
			<a href="#" class="btnNumeroPaginacaoIr">8</a>
			<a href="#" class="btnNumeroPaginacaoIr">9</a>
			<a href="#" class="btnNumeroPaginacaoIr">10</a>
		</div>
	</div>
<!--################################ FIM Ultimos Ganhadores #############################################-->

<!--################################ Quadro Formas de Pagamento #########################################-->
	<h2>Formas de Pagamento</h2>
	<div style="height: 135px; margin-top: 10px" class="boxHome">
		
	</div>
<!--################################ FIM Quadro Formas de Pagamento #####################################-->

</div>
<br>


<script type="text/javascript">		

	
	
	function entrarLeilao(id) {
		var data = {"leilaoId": id};
		alert($('#quadroGeral').val());
		$.post("entrar.do", data, function(data) {
			$("#quadroGeral").html(data);
		});
	}
	
	$(document).ready(function() {
		setInterval("atualizar()", 200);
	});

	function contabilizaLance(id) {
		$.ajax({
			  url: "lanceFromHome.do",
			  method: "post",
			  data: {"leilaoId" : id, "preco" : $('#preco_'+ id).val()},
			  dataType: "html",
			  success: function( data ) {
				  atualizaDados(data, id + ',');
			  }
			});
	}
	
	function atualizar() {
		verificaPreco();
	}
	
	function verificaPreco() {
		var param = "";
		for (var i = 0; i < document.getElementsByName("leilaoId").length; i++) {
			param += document.getElementsByName("leilaoId").item(i).value + ',';
		}
		$.ajax({
			  url: "atualizaDadosHome.do",
			  method: "post",
			  data: {"leilaoId" : param},
			  dataType: "html",
			  success: function( data ) {
				  atualizaDados(data, param);
			  }
			});
	}
	
	function atualizaDados(data, param) {
		var dados = data.split(",");
		param = param.split(',');
		for (var j = 0; j < dados.length; j++) {
			var dados1 = dados[j].trim().split('|');
			  
			  if (dados1[0] != null && dados1[0] != "" && dados1[0] != "undefined") {
				  if (dados1[0] != $('#preco_' + param[j]).val()) {
					  $('#preco_' + param[j]).val(dados1[0]);
					  $('#spanPreco_' + param[j]).html( 'R$ ' + dados1[0]);
					  $('#spanPreco_' + param[j]).effect("highlight", {color: "yellow"}, 200); 
					  $('#nroLancesLogin').html(dados1[3]);
					  $('#usrArremate_' + param[j]).html(dados1[4]);
				  }

				  if (dados1[2] == 'A') {
					  var classeTimer = 'tempoNormal';
					  if (dados1[1] <= 10) {
						  classeTimer = 'tempoAlerta';
					  }
					  var html = '';
							 if (dados1[1] < 10) {
								html += '<div class="'+classeTimer+'">0</div>' 
									 +  '<div class="'+classeTimer+'">'+dados1[1]+'</div>';
							 } else {
								html += '<div class="'+classeTimer+'">'+dados1[1].substring(0,1)+'</div>' 
									 +  '<div class="'+classeTimer+'">'+dados1[1].substring(1,2)+'</div>';
							 }
					  $("#imagemTimer_" +  param[j]).html(html);
					  $("#tarjaPreta_" + param[j]).remove();
				  } else if (dados1[2] == 'E') {
					  $('#spanPreco_' + param[j]).html('ARREMATADO!!');
					  $('#spanPreco_' + param[j]).attr('color', 'yellow');
					  $('#spanPreco_' + param[j]).effect('pulsate', 2300);
					  $("#imagemTimer_" +  param[j]).html("");
					  $("#linkLance_" +  param[j]).remove();
				  } 
			  }
		}
	}
</script>

<%@ include file="/includes/bottom.jsp"%>
