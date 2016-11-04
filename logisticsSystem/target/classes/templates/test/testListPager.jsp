<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 引入自定义分页标签 -->
<%@ taglib prefix="page" uri="/mvcPager"%>
<!-- 引入标准标签 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>测试分页标签</title>
<link href="<%=basePath%>content/bootstrap/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<c:forEach items="${list}" var="item">
			<div class="border-bottom1">
				<h3>
					<a href="<%=path%>/test/view?id=${item.city_id}">${item.city_name}</a>
				</h3>
				<p>${item.city_name}</p>
				<p class="text-right muted">
					2016-10-17 09:26:28 <a href="#">删除</a> <a href="#">编辑</a>
				</p>
			</div>
		</c:forEach>
		<form method="post" id="searchForm" action="<%=path%>/test/list">
			<input type="hidden" name="searchInfo" value="${searchInfo}">
		</form>
		<page:createPager pageSize="${pageSize}" totalPage="${totalPage}"
			totalCount="${totalCount}" curPage="${pageNum}" formId="searchForm" />
	</div>
</body>
</html>
