$('#tabs-usuario a').click(function (e) {
	  e.preventDefault();
	  var id = $(this).attr('id');
	  $('#tab-dados-body').css('display', (id == 'tab-dados'? 'block': 'none')); 
	  $('#tab-paineis-body').css('display', (id == 'tab-paineis'? 'block': 'none'));
	  $(this).tab('show');
});