<%@ tag body-content="scriptless" language="java" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="baseurl" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Tribunal Regional Federal da 5ª Região - Portal de Soluções</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="Portal da Justiça Federal da 5ª Região">
<meta name="contact" content="contato@caesar.com.br">
<meta name="author" content="Tribunal Regional Federal da 5ª Região, Subsecretaria de Tecnologia da Informação, SIPIT, Setor de Portais e Usabilidade de Software e Seção de Arquitetura de Sistemas">
<meta name="keywords" content="Justiça Federal, Autenticação">

<link href="${baseurl}/assets/css/jquery-ui-1.12.1.css" rel="stylesheet">
<link href="${baseurl}/assets/css/jquery-ui.css" rel="stylesheet">
<link href="${baseurl}/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="${baseurl}/assets/css/bootstrap-datepicker.min.css" rel="stylesheet">
<link href="${baseurl}/assets/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link href="${baseurl}/assets/css/BI.css" rel="stylesheet">
<link href="${baseurl}/assets/css/mediaqueries.css" rel="stylesheet">
<link href="${baseurl}/assets/datatables.min.css" rel="stylesheet">
<link href="${baseurl}/assets/jstree-3.2.1/style.min.css" rel="stylesheet">
<link href="${baseurl}/assets/css/bootstrap-select.min.css" rel="stylesheet" >
<link href="${baseurl}/assets/images/logo-jf5-favicon.ico" rel="shortcut icon">

<script src="${baseurl}/assets/js/jquery-2.1.4.min.js"></script>
<script src="${baseurl}/assets/js/jquery.min.js"></script>
<script src="${baseurl}/assets/js/jquery-ui.min.js"></script>
<script src="${baseurl}/assets/js/bootstrap.min.js"></script>
<script src="${baseurl}/assets/datatables.min.js"></script>
<script src="${baseurl}/assets/js/datatable-sort.js"></script> <!-- Script responsável pelo sort das colunas do datatable -->

<!--[if IE]>
		<style type="text/css">
			.caret {
			float: none;
			margin-top: 0px;
			}
		</style>
	<![endif]-->

<style type="text/css">
thead {
	background-color: #0D7D81;
}

/*Altera cor do índice de paginação dos data-tables*/
.dataTables_wrapper .dataTables_paginate .paginate_button:hover {
	background: #0c767a;
	color: white!important;
	border-radius: 4px;
	border: 1px solid #0c767a;
}
.dataTables_wrapper .dataTables_paginate .paginate_button:active {
	background: #074547;
	color: white!important;
}

/*Altera cor de fundo do hover das linhas e do ícone no dashboard*/
table.dataTable tbody tr:hover {
	background-color: #b8d0d1;
}
table.dataTable tbody tr.selected{
	background-color: #a8c1c1;
}
i.fa-lightbulb-o:hover {
	background-color: #0c767a;
	color: white;
	transition: background 0.3s;
}

/* Inclui reticências caso ultrapasse o número de caracteres da linda de uma tabela */
 .formatacaoRet th, .formatacaoRet td {
	max-width: 70px; /* Define a largura maxima de uma elemento */
	text-overflow: ellipsis; /*Não exibe o conteúdo excedido, ele exibe reticências.*/
	white-space: nowrap; /* Trata das propiedade de espaços em branco*/
	overflow: hidden; /*propriedade para esconder transbordo de um texto.*/ /**** o erro está aqui ***/
	stylewidth: 70px; /* define ou retorna a largura de um elemento */
}

/*Exibição da lixeira nas tabelas principais*/
tbody.areaClicavel > tr:hover > td > a > i.fa.fa-trash{
	display: inline !important;
	padding-left: 0.5%;
}

#tblempresas td .fa-check{
	color: #0D7478;
}

#tblempresas td .fa-times{
	color: #D9534F;
}

#tblmodalresp td {
	overflow: visible; /*propriedade para esconder transbordo de um texto está visivel apenas para lists do modal.*/
}

.ui-dialog {
	z-index: 1060 !important;
	position: absolute;
	left: 40%;
	top: 40%;
}

.has-error {
	border-color: none;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}

.has-error:focus {
	border-color: #ed7474;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #efc4c4;
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #efc4c4;
}

/*Exibe o ícone da lixeira pelo hover, nos data-tables dos formulários
  A Classe customizada trash-hover deve ser adicionada. */
a.trash-hover{ 
	visibility: hidden;
}
tr:hover > td > a.trash-hover {
	visibility: visible; 
	padding-left: 4px;
	cursor: pointer;
}

.formatacaoModal{
	width: 590px;
	height: 600px;
	overflow: auto;
}

/****FORMATAÇÃO DO DOCUMENTO DE CONTROLE DE VERSÃO ******/
.formatacaoTabelaPrincipal thead{
	background-color: #0D7D81;
	text-align: center;
	font-size: 13px;	
}
.table-bordered.formatacaoTabelaPrincipal{
    border:2px solid #0D7D81;
    font-size: 12px;
}
.table-bordered.formatacaoTabelaPrincipal > thead > tr > th{
    border:2px solid #0D7D81;
}
.table-bordered.formatacaoTabelaPrincipal > tbody > tr > td{
    border:2px solid #0D7D81;
}

.formatacaoTabela thead{
	background-color: #151515;
	font-size: 13px;
}
.table-bordered.formatacaoTabela{
    font-size: 12px;
}
.table-bordered{
    border:1px solid #151515;
}
.table-bordered > thead > tr > th{
	border:1px solid #151515;
}
.table-bordered > tbody > tr > td{
	border:1px solid #151515;
}
/**** FIM ******/

a:link.vs-sistema {
	text-decoration: none;
	color: #FFF;
}

a:hover.vs-sistema {
	text-decoration: underline;
	color: #FFF;
}

/*Cor do hover nos botões de dispensar modal*/
button.close:hover > span{
	color: red;
}

input[type=checkbox]{
	transform: scale(1.3);
}

</style>
</head>
<body>
	<div id="header_principal">
		<div id="background-divgrey" class="row">
			<div class="col-md-6 col-sm-6">
				<img id="imgLogo" class="img-responsive"
					src="${baseurl}/assets/images/LogoCaesar.png"
					title="Logo do Tribunal Regional Federal da 5ª Região"
					alt="Logo do Tribunal Regional Federal da 5ª Região" />
			</div>
			<div class="col-md-6 col-sm-6 text-right">
				
				<c:if test="${not empty usuario}">
					<c:if test="${usuario.administrador}">
						<div class="dropdown" style="margin-right: 14px; display: inline">
							<a href="#" data-toggle="dropdown" class="dropdown-toggle"><i
								class="fa fa-bars fa-lg"></i> Administração</a>
							<ul class="dropdown-menu">
								<li><a href="${baseurl}/usuarios.html">Usuários</a></li>
								<li><a href="${baseurl}/configuracoes.html">Configurações</a></li>
								<li><a href="${baseurl}/listarCategorias.html">Categoria</a></li>
								<li><a href="${baseurl}/listarPapeis.html">Papel</a></li>
								<li><a href="${baseurl}/listarStatus.html">Status</a></li>
								<li><a href="${baseurl}/listarUnidades.html">Unidade</a></li>
								<li><a href="${baseurl}/listarTipos.html">Tipo de base de dados</a></li>
								<li><a href="${baseurl}/listarMedidas.html">Unidade de Medida</a></li>
							</ul>
						</div>
					</c:if>
					
						<div class="dropdown" style="margin-right: 14px; display: inline">
							<c:if test="${usuario.tipoAutenticacao eq 'SISTEMA'}">
								<a href="${baseurl}/senha.html" style="margin-right: 12px"><i class="fa fa-key fa-lg"></i> Alterar senha</a>
							</c:if>
						</div>
					
				
					<div class="dropdown" style="margin-right: 14px; display: inline">
						<a href="#" data-toggle="dropdown" class="dropdown-toggle"><i
							class="fa fa-bars fa-lg"></i> Cadastros básicos</a>
						<ul class="dropdown-menu">
							<li><a href="${baseurl}/listarAditivo.html">Aditivo</a></li>
							<li><a href="${baseurl}/listarAreas.html">Área</a></li>
							<li><a href="${baseurl}/listarBases.html">Base de dados</a></li>
							<li><a href="${baseurl}/listarContratos.html">Contrato</a></li>
							<li><a href="${baseurl}/listarEmpresas.html">Empresa</a></li>
							<li><a href="${baseurl}/listarResponsaveis.html">Responsável</a></li>
						</ul>
					</div>
					
					<div style="margin-right: 12px; display: inline">	
						<a id="botaoSair" href="#" style="margin-right: 12px"><i class="fa fa-power-off fa-lg"></i> Sair</a>
					</div>
				
				</c:if>
				
				<div style="margin-right: 12px; display: inline">
					<a href="mailto:contato@caesar.com.br" title="Enviar email para contato@caesar.com.br"><i class="fa fa-envelope fa-lg"></i> Contato</a>
				</div>
				
				
				<div>
					<label><strong>Configure a cor da sua tela:</strong></label>
					<button class="noborder btn-css btn-css-alto-contraste"
						id="altoContraste" type="button" title="Alto contraste"
						style="background: top left no-repeat url(${baseurl}/assets/images/botao-alto-contraste.png)"></button>
					<button class="noborder btn-css btn-css-cor1" id="cinza"
						type="button" title="Fundo cinza"
						style="background-image: url(${baseurl}/assets/images/botao-cinza.png)"></button>
					<button class="noborder btn-css btn-css-cor2" id="azul"
						type="button" title="Fundo verde"
						style="background-image: url(${baseurl}/assets/images/botao-azul.png)"></button>
				</div>
				
				<p>
					<strong>Última Atualização:</strong>
					${initParam.ULTIMA_ATUALIZACAO}
				</p>
			</div>
		</div>
		<div id="background-divgrey02" class="row">
			<div class="col-md-6 col-sm-6">
				<div class="row">
					<div class="col-md-7 col-sm-7">
						<h3>
							<c:if test="${not empty usuario}"> 
								<a href="${baseurl}/dashboard.html" title="Página Inicial">&nbsp;Portal de Soluções da 5ª Região</a>
							</c:if>
							<c:if test="${empty usuario}"> 
								<span>&nbsp;Portal de Soluções da 5ª Região</span>
							</c:if>
						</h3>
					</div>
				</div>
			</div>       
			<div class="container-fluid corHomologacao" data-spy="scroll" data-offset="0"> 	
				<div class="col-md-6 col-sm-6 text-right info_version">	
				    <!--  Exibe a documentação das versões -->
					<c:if test="${not empty usuario}">			
						<a href="${baseurl}/documentoVersao.html" class="vs vs-sistema" title="Clique para ver o histórico de versões"
							data-toggle="modal" data-target=".bs-example-modal-vs">&nbsp;Versão ${initParam.VERSAO}</a> 
					</c:if>		
					<!--  Exibir a versao atual -->		
					<c:if test="${empty usuario}">	
						<span>&nbsp;Versão ${initParam.VERSAO}</span> 
					</c:if>
				</div>
                <!-- Base do documento de controle de versão -->
				<div class="modal fade bs-example-modal-vs"
					id="dialogVersao" aria-labelledby="myLargeModalLabel"> 	
					<div class="modal-dialog modal-vs">
						<div class="modal-content" >
						</div>
					</div>
				</div> 
			</div> 
	   			
		</div> 
</div>
	

	<jsp:doBody />
		<c:if test="${not empty usuario}">
			<iframe id="qvLogout" style="display: none"></iframe>
			<script>
				$(document).ready(function() {
					$('#botaoSair').click(function() {
						$('#qvLogout').prop('src', '${baseurl}/login.html');
						$('#qvLogout').on('load', function() {
							self.location = '${baseurl}/sair.html';
						});
					});

					//$(".dropdown-toggle").dropdown(); //Correção secundária do bug do menu dropdown, não remover
				});
			</script>
		</c:if>
	
	<script>
		$(document).ready(function() {
			$("#altoContraste").click(function() {
				document.body.style.background = "#DADADA";
			});
			$("#azul").click(function() {
				document.body.style.background = "#D9E7E8";
			});
			$("#cinza").click(function() {
				document.body.style.background = "#EFEEEE";
			});
			
			$('#dialogVersao').click(function(){
			       $('.btnVersaoSistema').toggle("Fade");
			});
			
			/*DESCOMENTAR ANTES DE GERAR O PACOTE PARA HOMOLOGAÇÃO*/
			//$(".corHomologacao,thead,.btn").css("background-color", "#d85b5b");
		});
	</script>
</body>
</html>
