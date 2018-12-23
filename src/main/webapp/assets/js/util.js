/* Sort todos os boxes de select alfabeticamente */
function sortSelect(selElem) {
    var tmpAry = new Array();
    for (var i=0;i<selElem.options.length;i++) {
        tmpAry[i] = new Array();
        tmpAry[i][0] = selElem.options[i].text;
        tmpAry[i][1] = selElem.options[i].value;
    }
    tmpAry.sort();
    while (selElem.options.length > 0) {
        selElem.options[0] = null;
    }
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;
    }
    return;
};

/* Coloca formato DateTime para data DD/MM/YYYY */
var carregarData = function (r) {
	var rowvalue = new Date(r);
	if(rowvalue == "Invalid Date")
		return null;
	var year = rowvalue.getFullYear();
	var month = (1 + rowvalue.getMonth()).toString();
	month = month.length > 1 ? month : '0' + month;
	var day = rowvalue.getDate().toString();
	day = day.length > 1 ? day : '0' + day;
	return day + '/' + month + '/' + year;
};

/* Remove classe "has-error" de todos os elementos que tem essa class */
function removerClass() {
	$(".has-error").focus(function() {
		$(this).removeClass("has-error");
	});   
};

/*Método para que o indicador de quantidade de caracteres, nos campos de texto, seja alterado dinamicamente */
$(".limiteCar[maxlength]").keyup(function () {
	var limite = $(this).attr("maxlength");
	if(this.value.length > limite){
		return false;
	}
	$(".contadorCar").text(limite - this.value.length);
});

$('.marcarLinha').on('click', 'tr', function(){
	if(!($(this)[0].firstChild.classList) || !($(this)[0].firstChild.classList[0] == 'dataTables_empty')){
		if($(this).hasClass('selected')){
			$(this).removeClass("selected");
		}else{
			$(this).addClass("selected");
		}
	}
});

/* Método utilizado para validar, ou alternar, empresas ativas na view de Solução, ao incluí-las ou alterá-las.
 * Caso seja uma alteração, é necessária a posição da empresa que se encontra no formulário, para impedir uma auto-comparação.*/
function validarAtivo(nodes, posicao){
	var qtd = 0;
	if(posicao == null){
		for(var linha = 0; linha < nodes.length; linha++){
			if(nodes.length > 0){
				if($('#ativaSim')[0].checked && nodes[linha].cells[3].firstChild.classList.contains('fa-check')){
					var empresaAtiva = nodes[linha].cells[3].firstChild.id.slice(5);
					if(!(confirm("Deseja tornar esta empresa ativa?"))){
						return false;
					}else{
						$.ajax({
							url: "api/contrato_solucao/ativos/" + empresaAtiva,
							type: "POST"
						}).done(function(){
							alert("Alterado com sucesso!");
						}).fail(function(req, status, error){
							alert("Erro ao inativar empresa");
						});
					}
				}
			}
		}
	}else{
		for(var linha = 0; linha < nodes.length; linha++){
			if(nodes.length > 0){
				if(linha != posicao){
					if($('#ativaSim')[0].checked && nodes[linha].cells[3].firstChild.classList.contains('fa-check')){
						var empresaAtiva = nodes[linha].cells[3].firstChild.id.slice(5);
						if(!(confirm("Deseja tornar esta empresa ativa?"))){
							return false;
						}else{
							$.ajax({
								url: "api/contrato_solucao/ativos/" + empresaAtiva,
								type: "POST"
							}).done(function(){
								return true;
							});
						}
					}
				}
			}
		}
	}
};