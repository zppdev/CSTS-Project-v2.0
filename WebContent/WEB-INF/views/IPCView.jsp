<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>摄像头列表</title>
<script type="text/javascript" src="views/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="views/js/bootstrap.min.js"></script>
<script type="text/javascript" src="views/js/myjs.js"></script>
<link rel="stylesheet" href="views/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<div class="">
					<table class="row col-lg-12 table table-striped table-bordered">
						<caption>
							<h2>Camera列表</h2>
						</caption>
						<thead>
							<tr>
								<th class="col-lg-1">IP地址</th>
								<th class="col-lg-2">摄像头序列号</th>
								<th class="col-md-1">用户名</th>
								<th class="col-md-1">密码</th>
								<th class="col-lg-1">连接操作</th>
								<th class="col-lg-1">状态</th>
								<th class="col-lg-1">推送服务器地址</th>
								<th class="col-lg-1">推送设备名称</th>
								<th class="col-lg-1">推送操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ipcList}" var="ipc">
								<tr>
									<td class="ip">${ipc.key}</td>
									<td><input class="id" type="text" name="id" value="${ipc.value.id}" /></td>
									<td>
										<input class="username" type="text" name="username" value="${ipc.value.username}" />
									</td>
									<td><input class="password" type="text" name="password" value="${ipc.value.password}" /></td>
									<td><a class="connectIPC" href="javascript:void(0);">连接</a></td>
									<td>${ipc.value.connectionStatus==true?'连接成功':'未连接'}</td>
									<td><input class="serverIP" type="text" name=serverIP value="${ipc.value.serverIP}" /></td>
									<td><input class="streamName" type="text" name="streamName" value="${ipc.value.id}"/></td>
									<td><a class="sendStream" href="javascript:void(0);">推送</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		</div>
	</div>
</body>
</html>