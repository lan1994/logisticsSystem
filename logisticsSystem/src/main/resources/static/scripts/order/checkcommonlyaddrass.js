
	function  inputcommonlyaddress(id){
		$.ajax({
			url:'/getCommonlyAddress',
			type:'post',
			dataType:'json',
			data:{'id':id},
			success:function(data){
				if(data.code==1)
				{
						$("#consigneeName").text(data.name);
						$("#provinceDiv").val(data.province);
						$("#cityDiv").val(data.city);
						$("#countyDiv").val(data.area);
						$("#consigneeAddress").text(data.datialaddress);
						$("#consigneeMobile").text(data.mobile);
					}
				}
			});
		
	
	}

