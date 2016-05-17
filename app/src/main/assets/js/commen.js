/**
 * Created by Jxy on 2016/5/11.
 *
 */

  /*进度条加载*/
$.fn.extend({ProgressBarWars: function(opciones) {

    defaults = {
        porcentaje:"0",
        tiempo:1000
    }
    var opciones = $.extend({}, defaults, opciones);
    //$(".progress-bar").animate({width: opciones.porcentaje+"%"},opciones.tiempo);
    $(this).find(".progress-bar").animate({width: opciones.porcentaje+"%"},opciones.tiempo);
    return this;
}
});

function lessmoney(){
    var moneynum=$("#money").val();
    $("#money").val(moneynum-10);
}
function addmoney(){
    var moneynum=parseInt($("#money").val());
    $("#money").val(moneynum+10);
}
