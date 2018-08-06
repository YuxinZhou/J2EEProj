/**
 * Created by Y2X on 2016/3/8.
 */

let user = require('../../module/user.js');
require('../../module/config.js');
let requestPath = window.config.requestPath;

user.checkLogin();
$(document).ready(function () {
	$('#password').bootstrapValidator({
			onSuccess: function () {
				$('.modal-body p').html('提交中');
				$("#mymodal").modal("toggle");
				let query = '?username=' + $('input[name="name"]').val() +
					'&personalId=' + $('input[name="personalId"]').val();
				$.ajax({
					url: requestPath + 'expertFindPassword' + query,
					success: function (res) {
						if (res.error_code == 2000) {
							//alert('通知成功');
							$('.modal-body p').html('通知成功');
						} else if (res.error_code == 2001) {
							//alert('信息有误');
							$('.modal-body p').html('信息有误');
							$('#sub-bnt').attr('disabled', false);
						}
					},
					error: function () {
						//alert('网络出错，请稍后重试');
						$('.modal-body p').html('网络出错，请稍后重试');
						$('#sub-bnt').attr('disabled', false);
					}
				});
			},
			onError: function () {
				$('#sub-bnt').attr('disabled', true);
			}
		}
	);
});