/**
 * Created by Y2X on 2016/3/7.
 */
require('./config.js');
let cookie = require('./cookie.js');
let requestPath = window.config.requestPath;
var user = {
	checkLogin: function () {
		if (!cookie.checkCookie('user')) {
			window.location.href = './login.html';
		}
	},
	expertLogin: function (expert) {
		$.ajax({
			type: 'post',
			async: false,
			url: requestPath + 'pad/expertLogin',
			data: JSON.stringify({
				"username": expert.username,
				"password": expert.password,
				"examPlace": expert.examPlace,
				"major": expert.major,
				"subject": expert.subject
			}),
			success: function (res) {
				let code = res.error_code;
				if (code == 2000) {
					//window.location.href = './score.html';
					cookie.setCookie('expert', username + '|' + password);//todo:md5加密//如果有cookie:teacher，就直接跳到评分页面,否则进行管理员登录
					let data = res.goods;
					window.localStorage.exam = JSON.stringify(data);//存到localStorage中
					$('.modal-body p').html(`
					ID号:${data.expertId},你好<br/>
					本场考试ID：${data.examId}</br>
					考点：${data.examPlace}<br/>
					考场号: ${data.examGroup}<br/>
					科目：${data.subject}<br/>
					时间：${data.examTime}`);
					$("#mymodal").modal("toggle");
				} else {
					if (code == 2001) {
						$('.login-error').html('<strong>登录失败</strong>：用户名或密码错误').removeClass('hide');
					} else if (code == 2002) {
						$('.login-error').html('<strong>登录失败</strong>：考点不正确').removeClass('hide');
					} else if (code == 2003) {
						$('.login-error').html('<strong>登录失败</strong>：类型不正确').removeClass('hide');
					} else if (code == 2003) {
						$('.login-error').html('<strong>登录失败</strong>：科目不正确').removeClass('hide');
					}
					$('#btn-login').attr('disabled', false);//enable button
				}
			},
			error: function () {
				$('.login-error').html('<strong>登录失败</strong>：网络故障').removeClass('hide');
				$('#btn-login').attr('disabled', false);//enable button
			}
		});
	},
	padAdminLogin: function (ip, username, password) {
		$.ajax({
			type: 'post',
			url: requestPath + 'pad/padAdminLogin',
			data: JSON.stringify({
				"username": username,
				"password": password
			}),
			success: function (res) {
				if (res.error_code == 2000) {
					window.location.href = 'admin.html';
					//cookie.setCookie('user',username+'|'+password);//todo:md5加密
				} else if (res.error_code == 2001) {
					$('.login-error').html('<strong>登录失败</strong>：输入有误').removeClass('hide');
					$('#btn-login').attr('disabled', false);//enable button
				}
			},
			error: function () {
				$('.login-error').html('<strong>登录失败</strong>：网络故障').removeClass('hide');
				$('#btn-login').attr('disabled', false);//enable button
			}
		});
	},
	login: function (username, password) {
		$.ajax({
			type: 'post',
			url: requestPath + 'adminLogin',
			data: JSON.stringify({
				"username": username,
				"password": password
			}),
			success: function (res) {
				if (res.error_code == 2000) {
					//alert('登录成功');
					window.location.href = 'personal-enroll.html';
					cookie.setCookie('user', username + '|' + password);//todo:md5加密
				} else if (res.error_code == 2001) {
					$('.login-error').html('<strong>登录失败</strong>：输入有误').removeClass('hide');
					$('#btn-login').attr('disabled', false);//enable button
				}
			},
			error: function () {
				$('.login-error').html('<strong>登录失败</strong>：网络故障').removeClass('hide');
				$('#btn-login').attr('disabled', false);//enable button
			}
		});
	},
	logout: function () {
		cookie.deleteCookie('user');
		window.location.href = 'login.html';
	}
};

module.exports = user;