<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>更新公告</title>
<jsp:include page="../shared/manager_headtitle.jsp"></jsp:include>
<script src="<%=basePath%>content/kindeditor/kindeditor-min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var editor;

	KindEditor.ready(function(K) {
		editor = K.create('#notice_Content', {
			resizeType : 1,
			allowImageUpload : false,
			items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
					'bold', 'italic', 'underline', 'removeformat', '|',
					'justifyleft', 'justifycenter', 'justifyright',
					'insertorderedlist', 'insertunorderedlist', '|',
					'emoticons', 'image', 'link' ]

		});
	});
</script>
</head>
<body>
   <!-- header开始 -->
	<jsp:include page="../shared/manager_head.jsp"></jsp:include>
	<div class="manager_main">
		<div class="content">
			<div class="leftbox">
				<!-- Begin of  #contentlist -->
				<div class="contentBox">
					<div class="contentBoxTop">
						<h3>发布公告</h3>
					</div>
					<div class="innerContent">
						<form action="<%=basePath%>announce/updateData" method="post">
							<table class="aTable">
								<tr>
									<td class="aTitle">标题</td>
                                    <input type="hidden" name="manager_id" value="${announceInfo.manager_id}" />
                                    <input type="hidden" name="announce_Id" value="${announceInfo.announce_Id}" />
								</tr>
								<tr>
									<td><input class="text-box aTitlInput"
										style="width: 600px;" id="notice_title" name="announce_Title"
										type="text" value="${announceInfo.announce_Title}" /></td>

								</tr>
								<tr>
									<td class="aTitle">内容</td>

								</tr>
								<tr>
									<td><textarea cols="20" id="notice_Content"
											name="announce_Content" style="width:680px; height:400px;">${announceInfo.announce_Content}</textarea>
</textarea></td>

								</tr>
								<tr class="aSubmit">
									<td><input type="submit" class="from_sub" value="保存" /></td>

								</tr>
							</table>
						</form>
						<!-- End of  #contentlist -->
					</div>
				</div>
				<!-- End of  #contentlist -->
			</div>
			<jsp:include page="../shared/manager_siderbar.jsp"></jsp:include>
		</div>
	</div>
	<!-- footer开始 -->
	<jsp:include page="../shared/manager_footer.jsp"></jsp:include>
</body>
</html>
