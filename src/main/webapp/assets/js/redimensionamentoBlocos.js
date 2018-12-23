var ajustarTamanhoBlocos = function() {
  
  tamanhos = [];
  
  $("#nivel01 .col-sm-3 .panel-body, #nivel011 .col-sm-3 .panel-body, #nivel02 .col-sm-3 .panel-body, #nivel03 .col-sm-3 .panel-body, #nivel031 .col-sm-3 .panel-body").each(function(index) {
    tamanhos.push($(this).children("h3").outerHeight(true) + $(this).children("i").outerHeight(true));
  });

  $("#nivel01 .col-sm-3 .panel-body, #nivel011 .col-sm-3 .panel-body, #nivel02 .col-sm-3 .panel-body, #nivel03 .col-sm-3 .panel-body, #nivel031 .col-sm-3 .panel-body").each(function(index){
    $(this).css
    (
      "height",
      (
        Math.max.apply(null, tamanhos) + 30)
    )
  });
};

$(window).load(ajustarTamanhoBlocos);
$(window).resize(ajustarTamanhoBlocos);