/**
 * Created by liukai on 2016/3/22.
 */
window.onload=function(){
    var div  = document.getElementById("rooms");
    var html = "";
    for(var i in db.Data){
        var room = db.Data[i];
        html += '<h3 class="text-center">'+ room.roomname +'</h3> ';
        html += '<br/>';
        var cubicle_count = room.cubicles.length;
        for(var j = 0;j< cubicle_count/2;j++){
            var cubicle1 = room.cubicles[2*j];
            var devicecount = cubicle1.devices.length;
            var cubicle2 = room.cubicles[2*j+1];
            if(cubicle2 != null){
                devicecount = Math.max(devicecount, cubicle2.devices.length);
            }

            html += '<div class="row-fluid text-center">';
            html += '<div class="span12">';
            html += write_cubicle(cubicle1,devicecount);
            html += write_cubicle(cubicle2,devicecount);

            html += '</div>';
            html += '</div></br>';
        }
    }
    div.innerHTML = html;

}

function write_cubicle(cubicle,devicecount){
    var html="";
    if(cubicle == null)
        return "";
    html += '<div class="span6">';
    html += '<div class="text-center" style="border: 1px solid #D6D6D6;padding: 5px;margin-left: 5px;background-color:white;">';
    html += '<ul class="list-unstyled" style="padding: 0px;margin-left: 0px;">';
    for(var i = 0;i<devicecount;i++){
        if(i < cubicle.devices.length){
            var device = cubicle.devices[i];
            html += '<li><a href="device'+device.deviceid + '.html">'+ device.devicename+ '</a></li>';
        }
        else{
            html += '<li>&nbsp</li>';
        }
    }
    html += '</ul>';
    html += '</div>';
    html += '<div class="text-center">';
    html += '<a href="cable'+ cubicle.cubicleid +'.html">'+ cubicle.cubiclename + '</a>';
    html += '</div>';
    html += '</div>';
    return html;
}