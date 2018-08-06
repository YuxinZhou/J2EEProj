/**
 * Created by Y2X on 2016/3/17.
 */
let user = require('../../module/user.js');

$(document).ready(function () {
	$('#login-expert').bootstrapValidator({
		onSuccess: function () {
			$('.login-error').html('登录中...').removeClass('hide');
			let expert = {
				username: $('input[name="username"]').val(),
				password: $('input[name="password"]').val(),
				examPlace: $('input[name="examPlace"]').val(),
				major:  $("#examType").find("option:selected").val(),
				subject: $("#subject").find("option:selected").val()
			};
			user.expertLogin(expert);
		},
		onError: function () {
			//$('#sub-bnt').attr('disabled', true);
		}
	});
	$('#go-to-score').click(function(){
		window.location.href='./score.html';
	});
});