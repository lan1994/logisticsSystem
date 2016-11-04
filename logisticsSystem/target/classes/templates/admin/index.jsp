<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>后台管理首页</title>
<jsp:include page="../shared/manager_headtitle.jsp"></jsp:include>
<body>
	<jsp:include page="../shared/manager_head.jsp"></jsp:include>
	<div class="manager_main">
		<div class="content">
			<div class="leftbox">
				<!-- Begin of  #contentlist -->
				<div id="pageIntro">
					<h2>欢迎进入后台管理页面</h2>
					<p>
						您可以在这里编辑公告、新闻、相关解决方案、产品等内容。<br /> <br />
						并且每个管理帐户的权限也是不一样的，指定权限的管理帐户可以做对应权限的操作。 <br /> <br /> <strong>注意：</strong>
						如您不知道该如何操作，您需要学习一下<a href="Help/帮助文档.pdf">网站管理手册。</a>
					</p>
				</div>

			</div>
			<jsp:include page="../shared/manager_siderbar.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="../shared/manager_footer.jsp"></jsp:include>
</body>
</html>
