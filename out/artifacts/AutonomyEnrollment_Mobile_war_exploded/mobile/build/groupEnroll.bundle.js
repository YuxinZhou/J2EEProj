/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	/**
	 * Created by Y2X on 2016/3/8.
	 */
	
	var user = __webpack_require__(1);
	__webpack_require__(2);
	var requestPath = window.config.requestPath;
	
	user.checkLogin();
	$(document).ready(function () {
	
		var formData = new FormData();
		var inputs = $('input[type="file"]');
		var btn = $('#sub-btn');
		btn.attr('disabled', 'disabled');
		inputs.change(function (e) {
			if (!chooseFile(e)) {
				return;
			}
			btn.attr('disabled', false);
			formData.append('file', e.target.files[0]);
		});
		btn.click(function (e) {
			$('.modal-body p').html('提交中');
			$("#mymodal").modal("toggle");
	
			$.ajax({
				url: requestPath + 'groupEnroll',
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				success: function success(res) {
					if (res.error_code == 2000) {
						$('.modal-body p').html('上传成功');
					}
				},
				error: function error() {
					$('.modal-body p').html('上传失败,请稍后再试');
				}
			});
		});
		function chooseFile(e) {
			var f = e.target.files;
			if (f.length == 0) {
				return false;
			}
			if (!/\.(?:ms-excel|sheet)$/i.test(f[0].type)) {
				$('.input-error').html('文件格式有误');
				$('.input-error').removeClass('hide');
				e.target.value = '';
				return false;
			}
			$('.input-error').addClass('hide');
			return true;
		}
	});

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	/**
	 * Created by Y2X on 2016/3/7.
	 */
	__webpack_require__(2);
	var cookie = __webpack_require__(3);
	var requestPath = window.config.requestPath;
	var user = {
		checkLogin: function checkLogin() {
			if (!cookie.checkCookie('user')) {
				window.location.href = './login.html';
			}
		},
		expertLogin: function expertLogin(expert) {
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
				success: function success(res) {
					var code = res.error_code;
					if (code == 2000) {
						//window.location.href = './score.html';
						cookie.setCookie('expert', username + '|' + password); //todo:md5加密//如果有cookie:teacher，就直接跳到评分页面,否则进行管理员登录
						var data = res.goods;
						window.localStorage.exam = JSON.stringify(data); //存到localStorage中
						$('.modal-body p').html('\n\t\t\t\t\tID号:' + data.expertId + ',你好<br/>\n\t\t\t\t\t本场考试ID：' + data.examId + '</br>\n\t\t\t\t\t考点：' + data.examPlace + '<br/>\n\t\t\t\t\t考场号: ' + data.examGroup + '<br/>\n\t\t\t\t\t科目：' + data.subject + '<br/>\n\t\t\t\t\t时间：' + data.examTime);
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
						$('#btn-login').attr('disabled', false); //enable button
					}
				},
				error: function error() {
					$('.login-error').html('<strong>登录失败</strong>：网络故障').removeClass('hide');
					$('#btn-login').attr('disabled', false); //enable button
				}
			});
		},
		padAdminLogin: function padAdminLogin(ip, username, password) {
			$.ajax({
				type: 'post',
				url: requestPath + 'pad/padAdminLogin',
				data: JSON.stringify({
					"username": username,
					"password": password
				}),
				success: function success(res) {
					if (res.error_code == 2000) {
						window.location.href = 'admin.html';
						//cookie.setCookie('user',username+'|'+password);//todo:md5加密
					} else if (res.error_code == 2001) {
							$('.login-error').html('<strong>登录失败</strong>：输入有误').removeClass('hide');
							$('#btn-login').attr('disabled', false); //enable button
						}
				},
				error: function error() {
					$('.login-error').html('<strong>登录失败</strong>：网络故障').removeClass('hide');
					$('#btn-login').attr('disabled', false); //enable button
				}
			});
		},
		login: function login(username, password) {
			$.ajax({
				type: 'post',
				url: requestPath + 'adminLogin',
				data: JSON.stringify({
					"username": username,
					"password": password
				}),
				success: function success(res) {
					if (res.error_code == 2000) {
						//alert('登录成功');
						window.location.href = 'personal-enroll.html';
						cookie.setCookie('user', username + '|' + password); //todo:md5加密
					} else if (res.error_code == 2001) {
							$('.login-error').html('<strong>登录失败</strong>：输入有误').removeClass('hide');
							$('#btn-login').attr('disabled', false); //enable button
						}
				},
				error: function error() {
					$('.login-error').html('<strong>登录失败</strong>：网络故障').removeClass('hide');
					$('#btn-login').attr('disabled', false); //enable button
				}
			});
		},
		logout: function logout() {
			cookie.deleteCookie('user');
			window.location.href = 'login.html';
		}
	};
	
	module.exports = user;

/***/ },
/* 2 */
/***/ function(module, exports) {

	'use strict';
	
	/**
	 * Created by Y2X on 2016/3/15.
	 */
	window.config = {
		requestPath: 'http://192.168.1.102:8080/AutonomyEnrollment/',
		myURL: {
			personalEnroll: 'personalEnroll',
			groupEnroll: 'groupEnroll',
			studentConfirm: 'studentConfirm',
			studentList: 'studentList',
			teacherList: 'teacherList',
			arrangeExperts: 'arrangeExperts',
			gradeList: 'gradeList',
			expertFindPassword: 'expertFindPassword',
			adminLogin: 'adminLogin',
			padAdminLogin: 'pad/padAdminLogin',
			expertLogin: 'pad/expertLogin',
			groupCandidateList: 'pad/groupCandidateList', //获取考场学生列表
			uploadOneGrade: 'pad/uploadOneGrade/',
			warn: 'pad/warn',
			uploadAllGrade: 'pad/uploadAllGrade/'
		}
	};

/***/ },
/* 3 */
/***/ function(module, exports) {

	'use strict';
	
	/**
	 * Created by Y2X on 2016/3/6.
	 */
	module.exports = {
		setCookie: function setCookie(key, value) {
			var day = arguments.length <= 2 || arguments[2] === undefined ? 1 : arguments[2];
	
			var exp = new Date();
			exp.setTime(exp.getTime() + day * 24 * 60 * 60 * 1000);
			document.cookie = key + '=' + value + ';expires=' + exp.toGMTString() + ';path=/';
		},
		getCookie: function getCookie(key) {
			var ck = document.cookie;
			var arr = ck.split(';');
			arr.forEach(function (item) {
				if (item.indexOf(key) == -1) {
					return item.substr(item.indexOf('=') + 1, item.length);
				} else {
					return null;
				}
			});
		},
		deleteCookie: function deleteCookie(key) {
			this.setCookie(key, '', -1);
		},
		checkCookie: function checkCookie(key) {
			return document.cookie.indexOf(key) != -1;
		}
	};

/***/ }
/******/ ]);
//# sourceMappingURL=groupEnroll.bundle.js.map