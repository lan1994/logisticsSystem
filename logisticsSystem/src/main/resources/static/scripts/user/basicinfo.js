$(document).ready(function(){
	$("#provinceDiv").click(function(){
		if($("#provinceDiv option").val()==0)
		{
		initselect();
		}
	});
	$("#provinceDiv").click(function(){
		initselect();
	});
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
			//	$("#provinceDiv option").eq(0).nextAll().remove();
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
			//	$("#cityDiv option").eq(0).nextAll().remove();
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
			//	$("#countyDiv option").eq(0).nextAll().remove();
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