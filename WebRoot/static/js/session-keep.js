/**
 * 保持心跳
 */
window.setInterval(sessionInfo, 1 * 60 * 1000);
function sessionInfo() {
    $.ajax({
        type: "GET",
        url: ctx+"/login/sessionInfo",
        cache: false,
        dataType: "json",
        success: function (data) {

        }});
}