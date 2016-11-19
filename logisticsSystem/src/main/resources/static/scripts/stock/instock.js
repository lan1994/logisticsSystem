$(document).ready(function(){
	$("#bt").click(function(){
		var reg = new RegExp("^[0-9]*$");  
		if($("#ordernumber").val().length>0&&reg.test($("#ordernumber").val()))
		{
			$.ajax({
				url:'/admin/instock',
				type:'post',
				dataType:'json',
				data:{'ordernumber':$("#ordernumber").val()},
				success:function(data)
				{
					$(".sTable tr").eq(0).nextAll().remove();
					if(data.code==1)
					{
						$("#success").show();
						setTimeout(function () { $(".hidden").hide(); }, 2000);
						//$($(".sTable tr").get(1)).remove();
						
						var tr = '<tr  style="font-size:20px;"><td>'+data.newid+'</td><td>'+data.ordernumber+'</td><td style="height:30px;color:green">'+data.msg+'</td>'+
									'<td>'+data.customername+'</td>'
								+'</tr>';
						$(".sTable").append($(tr));
					}else
					{
						$("#error").show();
						setTimeout(function () { $(".hidden").hide(); }, 2000);
						var tr = '<tr  style="font-size:20px;"><td>1</td><td>'+$("#ordernumber").val()+'</td><td style="height:30px;color:red">'+data.msg+'</td></tr>';
						//$($(".sTable tr").get(1)).remove();
						$(".sTable").append($(tr));
					}
				}
			});
		}
		else if($("#ordernumber").val().length==0)
		{
			alert("订单号不能为空");
		}else{
			alert("请输入正确格式的订单号");
		}
	});
});