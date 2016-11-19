$(document).ready(function(){
	$("#bt").click(function(){
		//if($("#name").val()&&$("#provinceDiv").val()&&$("#cityDiv").val()&&$("#countyDiv").val()
		//		&&$("#consigneeAddress").val()&&$("#consigneeMobile").val()){
			$("#form").submit();
		//}
		/*else{
			alert("请补全信息");
		}*/
	});
	
	$(".del-btn").click(function(){
		var addressid = $(this).closest(".contacts_1").attr("addressid");
		$.ajax({
			url:'/customer/host/delete',
			type:'post',
			dataType:'json',
			data:{"id":addressid},
			success:function(data){
				if(data.code==1)
				{
					$("#success").show();
					setTimeout(function () { $(".hidden").hide(); }, 2000);
					$("#del"+data.msg).remove();
				}
				else
				{
					$("#error").show();
					setTimeout(function () { $(".hidden").hide(); }, 2000);
					alert(data.msg);
				}
			}
		});
	});
	
	
	initselect();
	$("#provinceDiv").change(function(){
		initCity($(this).val());
	});
	$("#cityDiv").change(function(){
		initArea($(this).val());
	});
	//初始化下拉列表
	function initselect(){
		$.ajax({
			url:'/getProvince',
			type:'post',
			dataType:'json',
			success:function(data){
				$("#provinceDiv option").eq(0).nextAll().remove();
				if(data.code==1)
				{
					$(data.msg).each(function(index,item){
						var option ='<option value="'+item.provincecode+'">'+item.name+'</option>'; 
						$("#provinceDiv").append(option);
					});
				}
			}
		});
	}
	function initCity(provinceCode){
		$.ajax({
			url:'/getCity',
			type:'post',
			dataType:'json',
			data:{'provinceCode':provinceCode},
			success:function(data){
				$("#cityDiv option").eq(0).nextAll().remove();
				if(data.code==1)
				{
					$(data.msg).each(function(index,item){
						var option ='<option value="'+item.citycode+'">'+item.name+'</option>'; 
						$("#cityDiv").append(option);
					});
				}
			}
		});
	}
	function initArea(cityCode){
		$.ajax({
			url:'/getArea',
			type:'post',
			dataType:'json',
			data:{'cityCode':cityCode},
			success:function(data){
				$("#countyDiv option").eq(0).nextAll().remove();
				if(data.code==1)
				{
					$(data.msg).each(function(index,item){
						var option ='<option value="'+item.areacode+'">'+item.name+'</option>'; 
						$("#countyDiv").append(option);
					});
				}
			}
		});
	}
});