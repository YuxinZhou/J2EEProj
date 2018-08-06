/**
 * Created by Y2X on 2016/3/7.
 */
let user = require('../../module/user.js');
require('../../module/config.js');

let requestPath = window.config.requestPath;
$(document).ready(function () {
	$('#btn-login').click(function () {
		let username = $('#username').val().trim();
		let password = $('#password').val().trim();
		console.log(username, password);
		if (username && password) {//username & password不为空
			$('.login-error').html('登录中...').removeClass('hide');
			$(this).attr('disabled', true);//disable button
			user.login(username, password);
		} else {//为空
			$('.login-error').text('输入不完整').removeClass('hide');
		}
	});
});