$(document).ready(function(){
	//全选
	$("#checkall").click(function(){
		if($(this).is(":checked")){
			$(".checkbox").each(function(){
				$(this).attr("checked",true);
			});
		}else
		{
			$(".checkbox").each(function(){
				$(this).attr("checked",false);
			});
		}
	});
	$("input:checkbox[name=id]").bind("click",function(){
		if($(this).attr("checked")){
			$("#checkall").attr("checked",false);
		}
	});
	
	//查询ajax
	$("#bt").click(function(){
		var reg = new RegExp("^[0-9]*$");  
		if($("#ordernumber").val().length>0&&reg.test($("#ordernumber").val()))
		{
			$.ajax({
				url:'/admin/querybyorder',
				type:'post',
				dataType:'json',
				data:{'ordernumber':$("#ordernumber").val()},
				success:function(data)
				{
					$(".sTable tr").eq(0).nextAll().remove();
					/*var len = $(".sTable").find("tr").length;
					 * for(var i=1;i<len;i++){
						$($(".sTable").find("tr").get(1)).remove();
					}*/
					if(data.code==1)
					{
						$("#success").show();
						setTimeout(function () { $(".hidden").hide(); }, 2000);
						var tr = '<tr class="oddRow"> <input type="hidden" value="'+data.id+'"/>'+
									'<td class="check_list"><input type="checkbox" class="checkbox" value="'+data.id+'" name="id"/></td>'+
									'<td class="list_title">'+'1'+'</td>'+
									'<td class="list_title">'+data.ordernumber+'</td>'+
									'<td class="list_title">'+data.status+'</td>'+
									'<td class="list_title">'+data.username+'</td>'+
									'<td class="list_title">'+data.toMobile+'</td>'+
									'<td class="list_title">'+data.from+'</td>'+
									'<td class="list_title">'+data.to+'</td>'+
									'<td class="list_title">'+data.next+'</td>'+
									'<td class="list_title"><input type="button" value="自动出库" class="outstock"/></td>'+
									'<td class="list_title">'+
										'<form action="/admin/outstockpage" method="post">'+
											'<input type="hidden" value="'+data.id+'" name="repertoryId"/>'+
											'<input  type="submit" value="手动出库">'+
										 '</form>'+
									'</td>'+
								'</tr>';
						$(".sTable").append(tr);
						if(data.canout!=2){
							$($("tr").get(1)).find(".outstock").attr('disabled',true);
							$($("tr").get(1)).find("input[type='submit']").attr('disabled',true);
						}
					}else
					{
						$("#error").show();
						setTimeout(function () { $(".hidden").hide(); }, 2000);
						var tr = '<tr class="oddRow"> <input type="hidden" value="'+data.id+'"/>'+
						'<td class="check_list"><input type="checkbox" class="checkbox" value="'+data.id+'" name="id"/></td>'+
						'<td class="list_title">'+'1'+'</td>'+
						'<td class="list_title">'+data.msg+'</td>'+
						'<td class="list_title" style="color:red">'+'不在本库'+'</td>'+
						'</tr>';
						//alert(tr)
						$(".sTable").append(tr);
					}
				}
			});
		}
		else if($("#ordernumber").val().length==0)
		{
			window.location.href='http://localhost:8080/admin/stockpage';
		}else{
			alert("请输入正确格式的订单号");
		}
	});
	
	//自动出库（单个）
	$(".outstock").live('click',function(){
		var id = $(this).closest("tr").find("input:checkbox").val();
		outstocksingle(id);
	});
	//出库函数
	function outstocksingle(repertoryid){
		$.ajax({
			url:'/admin/outstock',
			type:'post',
			dataType:'json',
			data:{'repertoryid':repertoryid},
			success:function(data)
			{
				if(data.code==1)
				{
					$("#success").show();
					setTimeout(function () { $(".hidden").hide(); }, 2000);
					for(var i=0;i<$(".sTable").find("tr").length;i++){
						if($($(".sTable").find("tr").get(i)).find("td input:checkbox").val()==data.msg){
							$($($(".sTable").find("tr").get(i)).find("td").get(3)).text("出库成功");
							$($($(".sTable").find("tr").get(i)).find("td").get(3)).css("color","green");
							break;
						}
					}
				}else
				{
					$("#error").show();
					setTimeout(function () { $(".hidden").hide(); }, 2000);
					for(var i=0;i<$(".sTable").find("tr").length;i++){
						if($($(".sTable").find("tr").get(i)).find("td input:checkbox").val()==data.msg){
							$($($(".sTable").find("tr").get(i)).find("td").get(3)).text("出库失败");
							$($($(".sTable").find("tr").get(i)).find("td").get(3)).css("color","red");
							break;
						}
					}
				}
			}
		});
	}
	
	
	
});