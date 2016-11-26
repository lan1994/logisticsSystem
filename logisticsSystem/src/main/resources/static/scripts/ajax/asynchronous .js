$("document").ready(function(){
	//级联城市选择的异步请求
	$("#provincelist").change(function(){
		//alert("你选择了"+$(this).find("option:selected").text());
		var provinceCode = $(this).val();
		var url = "/getCity";
		$("#citylist").find("option").remove(); //清空
		$.getJSON(url,{'provinceCode' : provinceCode},function(data){
			//alert(data.msg);
			$.each(data.msg,function(i, item) {
						$("<option></option>").val(item.citycode)
								.text(item.name).appendTo(
										$("#citylist"));
					}); //如果url访问成功  则执行function(data)这个函数（看仔细了`，这里该函数也是.getJSON的第三个参数）
		}); //function(data)获取了通过url返回来的值，并且循环读取出来    
		});	
	$("#citylist").change(function(){
		var provinceCode = $("#provincelist").find("option:selected").val();
		var cityCode = $(this).val();
		var url1 = "/getAdmin";
		var url2 = "/getArea";
		if($("#arealist").is(":hidden")){
			//alert("区域下拉列表隐藏");
			//二级分拣站添加操作时,当城市下拉列表该表示处罚该操作
			 $.getJSON(url1,{'uright' : 2,'cityCode' : cityCode},function(data){
		        	//alert(data.msg.length);
		        	if(data.msg.length==0){
		        		alert("当前没有可选的管理员！");
		        	}else{
		        		$("#adminlist").find("option").remove(); //清空
		        		$.each(data.msg,function(i,item){
		        			$("<option></option>").val(item.userid)
							.text(item.name).appendTo(
									$("#adminlist"));
		        		});
		        	}
		        });
		        $("input[name='stationId']").val(provinceCode+cityCode);
		}
		else{
			//alert("区域下拉列表显示");
			//三级分拣站添加操作时,当城市下拉列表该表示处罚该操作
			$("#arealist").find("option").remove(); //清空
			$.getJSON(url2,{'cityCode' : cityCode},function(data){
				//alert(data.msg);
				$.each(data.msg,function(i, item) {
							$("<option></option>").val(item.areacode)
									.text(item.name).appendTo(
											$("#arealist"));
						});
			}); 
		}
       
	});
	
	$("#arealist").change(function(){
		var cityCode = $("#citylist").find("option:selected").val();
		var areaCode = $(this).val();
		var url = "/getAdmin";
		 $.getJSON(url,{'uright' : 3,'cityCode' : cityCode},function(data){
	        	//alert(data.msg.length);
	        	if(data.msg.length==0){
	        		alert("当前没有可选的管理员！");
	        	}else{
	        		$("#adminlist").find("option").remove(); //清空
	        		$.each(data.msg,function(i,item){
	        			$("<option></option>").val(item.userid)
						.text(item.name).appendTo(
								$("#adminlist"));
	        		});
	        	}
	        });
		$("input[name='stationId']").val(cityCode+areaCode);
	});
});