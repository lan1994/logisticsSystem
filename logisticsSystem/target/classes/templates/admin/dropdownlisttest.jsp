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
<title>Title</title>
<script src="<%=basePath%>content/js/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>content/js/jquery.validate.vsdoc.js"
	type="text/javascript"></script>
<script type="text/javascript">
    var citylistjsondata=${citylistjson};
	$(function() {
		$("#citylist").change(
				function() { //Parent选项改变时激活
					var selec = $(this).val(); //获取改变的选项值
					var url =citylistjsondata; //"/Article/GetCategroyByLanguage"; //参数依次类型(action,Controller,area）            

					//$("#LanguageToCategory").find("option").remove(); //清空

					$.getJSON(url, {
						'selectlanguage' : selec
					}, function(data) { //parentId是参数名和Controllers中的action参数名相同
						$.each(data, function(i, item) {
							$("<option></option>").val(item["city_id"]).text(
									item["city_name"]).appendTo(
									$("#arealistbycityid"));
						}); //如果url访问成功  则执行function(data)这个函数（看仔细了`，这里该函数也是.getJSON的第三个参数）
					}); //function(data)获取了通过url返回来的值，并且循环读取出来            
				});

	});
</script>
</head>
<body>
	<select id="citylist" name="citylist">
	</select>
	<select id="arealistbycityid" name="arealistbycityid">
	</select>
</body>
</html>
