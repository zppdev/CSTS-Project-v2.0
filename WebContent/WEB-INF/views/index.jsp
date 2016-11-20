<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>摄像头Info</title>
<script type="text/javascript" src="views/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="views/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="views/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<table class="row col-md-12 table table-striped table-bordered">
			<caption>
				<h2>Camera列表</h2>
			</caption>
			<thead>
				<tr>
					<th class="col-md-1">IP地址</th>
					<th class="col-md-1">用户名</th>
					<th class="col-md-1">密码</th>
					<th class="col-md-1">状态</th>
					<th class="col-md-1">推送服务器地址</th>
					<th class="col-md-1">推送设备名称</th>
					<th class="col-md-1">推送操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<%
						System.out.print(request.getAttribute("ipc"));
					%>
					<td>${ipc.ip}</td>
					<td>${ipc.username}</td>
					<td>${ipc.password}</td>
					<td>${ipc.connectionStatus==true?'连接成功':'连接失败'}</td>
					<td>${ipc.serverIP}</td>
					<td>${ipc.streamName}</td>
					<td><c:if test="${ipc.connectionStatus==true }"></c:if> <a
						href="sendStream?ip=${ipc.ip}&username=${ipc.username}&password=${ipc.password}&serverIP=${ipc.serverIP}&streamName=${ipc.streamName}">推送</a>

						<c:if test="${ipc.connectionStatus==false }">
							<script type="text/javascript">
								alert('连接不成功,请检查配置文件是否错误!');
							</script>
						</c:if></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>