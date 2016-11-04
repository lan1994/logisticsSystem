<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
	<head>
                <base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>Title</title>
	</head>
	<body>
		<h1>标题：${announceInfo.announce_Title}</h1>作者： ${announceInfo.manager_name}
		<p>
		    ${announceInfo.announce_Content}
		</p>
		<p>${announceInfo.writeTime}</p>
	</body>
</html>
