$(document).ready(function() {
	$(".connectIPC").click(function() {
		//connectIPC?ip=${ipc.key}&id=${ipc.value.id}&username=${ipc.value.username}&password=${ipc.value.password}&serverIP=${ipc.value.serverIP}
		var ip = $(this).parent().parent().children("td.ip").text().trim();
		var id = $(this).parent().parent().find("input.id").val().trim();
		var username = $(this).parent().parent().find("input.username").val().trim();
		var password = $(this).parent().parent().find("input.password").val().trim();
		var serverIP = $(this).parent().parent().find("input.serverIP").val().trim();
		var streamName = $(this).parent().parent().find("input.streamName").val().trim();
		$.ajax({
            type: 'POST',
            url: 'connectIPC?ip='+ip+'&id='+id+'&username='+username+'&password='+password+'&serverIP='+serverIP,
            data: { },
            success: function (data) {
            	var message = data.message;
            	alert("success");
            	window.location.href="index.jsp";//刷新当前页面.
            }
        });
	});
	
	$(".sendStream").click(function() {
		//sendStreamForIPC?ip=${ipc.key}&id=${ipc.value.id}&username=${ipc.value.username}&password=${ipc.value.password}&serverIP=${ipc.value.serverIP}&StreamName=
		var ip = $(this).parent().parent().children("td.ip").text().trim();
		var id = $(this).parent().parent().find("input.id").val().trim();
		var username = $(this).parent().parent().find("input.username").val().trim();
		var password = $(this).parent().parent().find("input.password").val().trim();
		var serverIP = $(this).parent().parent().find("input.serverIP").val().trim();
		var streamName = $(this).parent().parent().find("input.streamName").val().trim();
		$.ajax({
            type: 'POST',
            url: 'sendStreamForIPC?ip='+ip+'&id='+id+'&username='+username+'&password='+password+'&serverIP='+serverIP+'&streamName='+streamName,
            data: { },
            success: function (data) {
            	alert("success");
            	window.location.href="index.jsp";//刷新当前页面.
            }
        });
	});
	
});