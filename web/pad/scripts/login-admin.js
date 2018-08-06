/**
 * Created by Y2X on 2016/3/17.
 */
let user = require('../../module/user.js');
$('#btn-login').click(function () {
	let ipAddress = $('#ipAddress').val().trim();
	let username = $('#username').val().trim();
	let password = $('#password').val().trim();
	console.log(ipAddress, username, password);
	if (ipAddress && username && password) {//username & password不为空
		$('.login-error').html('登录中...').removeClass('hide');
		$('#btn-login').attr('disabled', true);//disable button
		user.padAdminLogin(ipAddress, username, password);
	} else {//为空
		$('.login-error').text('输入不完整').removeClass('hide');
	}
});