/**
 * Created by Y2X on 2016/3/8.
 */

let user = require('../../module/user.js');
require('../../module/config.js');
let requestPath = window.config.requestPath;

user.checkLogin();
$(document).ready(function () {
	$('#info').bootstrapValidator({
		onSuccess: function () {
			//alert('提交中');
			$('.modal-body p').html('提交中');
			$("#mymodal").modal("toggle");
			$.ajax({
				type: 'post',
				url: requestPath + 'personalEnroll',
				data: JSON.stringify({
					name : $('input[name="name"]').val(),
					gender : $('input[name="gender"]:checked').val(),
					age : $('input[name="age"]').val(),
					examType : $("#examType").find("option:selected").val(),
					province : $('input[name="province"]').val(),
					region : $('input[name="region"]').val(),
					highSchool : $('input[name="highSchool"]').val(),
					personalId : $('input[name="personalId"]').val(),
					telephone : $('input[name="telephone"]').val(),
					email : $('input[name="email"]').val(),
					address : $('input[name="address"]').val()
					}),
				success:function(res){
					if(res.error_code==2000){
						$('.modal-body p').html('报名成功，你的准考证号是：'+res.ticket);
					}else if(res.error_code ==2001){
						$('.modal-body p').html('报名失败，你的信息有误');
					}
				},
				error:function(){
					$('.modal-body p').html('网络出错，请稍后重试');
					$('#sub-bnt').attr('disabled',false);
				}
			});
		},
		onError: function () {
			$('#sub-bnt').attr('disabled',true);
		}
	});
});