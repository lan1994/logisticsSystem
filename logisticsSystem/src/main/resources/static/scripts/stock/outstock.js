$(document).ready(function(){
	if($("#citycode").val()){
	initselect($("#citycode").val());
	}
	//查询ajax
	$("#bt").click(function(){
		var reg = new RegExp("^[0-9]*$");  
		if($("#ordernumber").val().length>0&&reg.test($("#ordernumber").val()))
		{
			$.ajax({
				url:'/admin/queryandout',
				type:'post',
				dataType:'json',
				data:{'ordernumber':$("#ordernumber").val()},
				success:function(data)
				{
					$("#province").text('');
					$("#city").text('');
					$("#stationname").text('');
					$("#next").text('');
					$("#status").text('');
					$("#order").text('');
					
					$("#toprovince").text('');
					$("#tocity").text('');
					$("#toarea").text('');
					$("#detailaddress").text('');
					
					$("#repertoryid").val('');
					$("#citycode").val('');
					$("#area option").eq(0).nextAll().remove();
					$("#sortstation option").eq(0).nextAll().remove();
					$("#status").css("color","black");
					if(data.code==1)
					{
						$("#success").show();
						setTimeout(function () { $(".hidden").hide(); }, 2000);
						$("#province").text(data.province);
						$("#city").text(data.city);
						$("#stationname").text(data.stationname);
						$("#next").text(data.next);
						$("#status").text(data.status);
						$("#order").text(data.ordernumber);
						
						$("#toprovince").text(data.toprovince);
						$("#tocity").text(data.tocity);
						$("#toarea").text(data.toarea);
						$("#detailaddress").text(data.detailaddress);
						
						$("#repertoryid").val(data.id);
						$("#citycode").val(data.citycode);
						if(data.canout==1)
						{
						initselect(data.citycode);
						$("#outstockbt").attr("disabled",false);
						}else{
						$("#outstockbt").attr("disabled",true);
						}
					}
					else
					{
						$("#error").show();
						setTimeout(function () { $(".hidden").hide(); }, 2000);
					}
				}
			});
		}
		else{
			alert("请输入正确格式的订单号");
		}
	});
	
	//自动出库（单个）
	$("#outstockbt").click(function(){
		var sortstation = $("#sortstation option:selected").val();
		var repertoryid = $("#repertoryid").val();
		if(sortstation==null||sortstation=='default')
		{
			alert("请选择分拣站");
		}
		outstocksingle(repertoryid,sortstation);
		
	});
	//出库函数
	function outstocksingle(repertoryid,sortstation){
		$.ajax({
			url:'/admin/outstockwithmodify',
			type:'post',
			dataType:'json',
			data:{'repertoryid':repertoryid,'next':sortstation},
			success:function(data)
			{
				if(data.code==1)
				{
					$("#success").show();
					setTimeout(function () { $(".hidden").hide(); }, 2000);
					$("#next").text(data.next);
					$("#status").text(data.status);
					$("#status").css("color","green");
				}else
				{
					$("#error").show();
					setTimeout(function () { $(".hidden").hide(); }, 2000);
					$("#status").text("失败");
					$("#status").css("color","red");
				}
			}
		});
	}
	
	$("#area").change(function(){
		initsortstation($("#area").val());
	});
	//下拉
	//初始化下拉列表
	function initselect(cityCode){
		$.ajax({
			url:'/getArea',
			type:'post',
			dataType:'json',
			data:{'cityCode':cityCode},
			success:function(data){
				$("#area option").eq(0).nextAll().remove();
				if(data.code==1)
				{
					$(data.msg).each(function(index,item){
						var option ='<option value="'+item.areacode+'">'+item.name+'</option>'; 
						$("#area").append(option);
					});
				}
			}
		});
	}
	function initsortstation(areaCode){
		$.ajax({
			url:'/getStation',
			type:'post',
			dataType:'json',
			data:{'areaCode':areaCode},
			success:function(data){
				$("#sortstation option").eq(0).nextAll().remove();
				if(data.code==1)
				{
					$(data.msg).each(function(index,item){
						var option ='<option value="'+item.id+'">'+item.name+'</option>'; 
						$("#sortstation").append(option);
					});
				}
			}
		});
	}
});