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
	 * Created by Y2X on 2016/4/7.
	 */
	var user = __webpack_require__(1);
	var data = __webpack_require__(4);
	var requestPath = window.config.requestPath;
	var exam = window.localStorage.exam ? JSON.parse(window.localStorage.exam) : {};
	var stus = exam ? exam.students : [];
	var standards = exam ? exam.standard : [];
	console.log(standards);
	var currStu = 0; //当前学生的index
	var currId = 0; //当前学生的id
	//获取学生列表和标准
	// let stus = [
	// 	{
	// 		"id": '1000',
	// 		"firstGrade": 0,
	// 		"finalGrade": 0,
	// 		"lastGrade": 0,
	// 		"isUpload": false
	// 	},
	// 	{
	// 		"id": '29889',
	// 		"firstGrade": 0,
	// 		"finalGrade": 0,
	// 		"lastGrade": 0,
	// 		"isUpload": false
	// 	},
	// 	{
	// 		"id": '229',
	// 		"firstGrade": 0,
	// 		"finalGrade": 0,
	// 		"lastGrade": 0,
	// 		"isUpload": false
	// 	}
	// ];
	// let standards = [{
	// 	"standardName": "流利程度",
	// 	"type": "english",
	// 	"gradeSection": [20, 40, 60]
	// }, {
	// 	"standardName": "流利程度",
	// 	"type": "humanity",
	// 	"gradeSection": [20, 40, 60]
	// }, {
	// 	"standardName": "正确性",
	// 	"type": "major",
	// 	"gradeSection": [20, 40, 60]
	// }];
	var typeTrans = {
		'专业': 'major',
		'英语': 'english',
		'人文': 'humanity'
	};
	$(document).ready(function () {
		//第一个位学生
		currId = stus[currStu].id;
		$('#student-id').text(currId);
	
		//获取标准
		var type = void 0;
		var name = void 0;
		var grade = [];
		standards.forEach(function (item) {
			type = typeTrans[item.type];
			name = item.standardName;
			grade = item.gradeSection;
			console.log(type, name, grade);
			var appendEle = '\n\t\t\t\t<div class="title std-name">' + name + '</div> <!--std-name-->\n\t\t\t\t<div class="content">\n\t\t\t\t\t<label class="radio-inline">\n\t\t\t\t\t\t<input type="radio" name="std-' + type + '-' + name + '-value" value="' + grade[0] + '"><!--std-type-name-value-->\n\t\t\t\t\t\t差\n\t\t\t\t\t</label>\n\t\t\t\t\t<label class="radio-inline">\n\t\t\t\t\t\t<input type="radio" name="std-' + type + '-' + name + '-value" value="' + grade[1] + '">\n\t\t\t\t\t\t良\n\t\t\t\t\t</label>\n\t\t\t\t\t<label class="radio-inline">\n\t\t\t\t\t\t<input type="radio" name="std-' + type + '-' + name + '-value" value="' + grade[2] + '">\n\t\t\t\t\t\t优\n\t\t\t\t\t</label>\n\t\t\t\t</div><hr/>';
			$('.' + type + ' .panel-body').append(appendEle);
		});
	
		//当前学生
		$('#next-student').click(function () {
			//记录当前考生分数
			var currScore = 0;
			$("input[type='radio']:checked").each(function () {
				currScore += Number($(this).val());
			});
			console.log(currId + ':' + currScore);
			//上传分数,并存储在本地
			var score = {
				"examId": exam.examId,
				"examNumber": currId,
				"subject": exam.subject || '',
				"gradeType": 'lastGrade',
				"grade": currScore,
				"expertId": exam.expertId || '',
				"uploadTime": new Date()
			};
	
			$.ajax({
				type: "post",
				async: false,
				url: requestPath + 'pad/uploadOneGrade',
				data: JSON.stringify(score),
				success: function success() {
					//更新locastorage中的数据,
					setExam(currId, {
						"lastGrade": currScore,
						"isUpload": true
					});
					console.log('上传成功');
				},
				error: function error() {
					//更新locastorage中的数据
					setExam(currId, {
						"lastGrade": currScore,
						"isUpload": true
					});
					console.log('上传失败');
				}
			});
	
			if (currStu < stus.length - 1) {
				$('#student-id').text(stus[++currStu].id);
				//clear radio
				$('input[type="radio"]').attr("checked", false);
			} else {
				window.localStorage.exam = JSON.stringify(exam); //更新localStorage中的分数
				$('.modal-body p').html('评分完成，请返回设置页面');
				$("#mymodal").modal("toggle");
				$('#modal-ok').click(function () {
					window.location.href = './login-admin.html';
				});
			}
		});
		//清空所有
	});
	
	function setExam(id, option) {
		exam.students.forEach(function (item) {
			if (item.id == id) {
				for (var i in option) {
					item[i] = option[i];
				}
				return;
			}
		});
	}
	function getExam(id, option) {
		var value;
		exam.students.forEach(function (item) {
			if (item.id == id) {
				value = item[option];
				return;
			}
		});
		return value;
	}

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

/***/ },
/* 4 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	
	/**
	 * Created by Y2X on 2016/3/22.
	 */
	__webpack_require__(2);
	var requestPath = window.config.requestPath;
	module.exports = {
		student: [],
		expert: [],
		grade: [],
		uploadData: function uploadData(option) {
			//'score'|'student'
			$('.modal-body p').html('上传中');
			$("#mymodal").modal("toggle");
			$.ajax({
				url: requestPath + (option == 'score' ? 'uploadScoreToMainServer' : 'uploadStudentToMainServer'),
				success: function success(res) {
					if (res.error_code == 2000) {
						$('.modal-body p').html('上传成功');
					} else {
						$('.modal-body p').html('上传失败');
					}
				},
				error: function error() {
					$('.modal-body p').html('网络错误，请稍后再试');
				}
			});
		},
		getData: function getData(option) {
			//option = 'student'|'expert'|'grade'
			$('.modal-body p').html('拉取数据中');
			$("#mymodal").modal("toggle");
			$.ajax({
				type: 'get',
				url: requestPath + option + 'List',
				success: function success(res) {
					if (res.error_code == 2000) {
						//弹框关闭
						$("#mymodal").modal("toggle");
						//替换表格数据
						this[option] = res.goods;
						$('#table').bootstrapTable('load', res.goods);
					}
				},
				error: function error() {
					$('.modal-body p').html('网络错误，请稍后再试');
				}
			});
		},
		getStudentInGroup: function getStudentInGroup(examGroup) {
			$('.modal-body p').html('拉取考生数据中');
			$("#mymodal").modal("toggle");
	
			$.ajax({
				type: 'get',
				async: false,
				url: requestPath + 'pad/groupCandidateList?examGroup=' + examGroup,
				success: function success(res) {
					if (res.error_code == 2000) {
						//弹框关闭
						$("#mymodal").modal("toggle");
						//替换表格数据
						var stu = res.goods;
						stu.forEach(function (item) {
							item.firstGrade = 0;
							item.finalGrade = 0;
							item.lastGrade = 0;
							item.isUpload = false;
						});
						$('#table').bootstrapTable('load', res.goods);
	
						//加入localStorage.exam
						//todo：如果没有进行初试要报错
						var exam = JSON.parse(window.localStorage.exam);
						exam.students = stu;
						window.localStorage.exam = JSON.stringify(exam);
					}
				},
				error: function error() {
					$('.modal-body p').html('网络错误，请稍后再试');
				}
			});
		},
		getStandard: function getStandard() {
			$('.modal-body p').html('获取评分标准中');
			$("#mymodal").modal("toggle");
			$.ajax({
				type: 'get',
				url: requestPath + 'pad/getStandard',
				success: function success(res) {
					if (res.error_code == 2000) {
						//弹框关闭
						$("#mymodal").modal("toggle");
	
						//加入localStorage.exam.standard
						var exam = JSON.parse(window.localStorage.exam);
						exam.standard = res.goods;
						window.localStorage.exam = JSON.stringify(exam);
	
						//跳转到lastScore页面
						window.location.href = './last-score.html';
					} else if (res.error_code == 2001) {
						$('.modal-body p').html('考试尚未开始');
					}
				},
				error: function error() {
					$('.modal-body p').html('网络错误，请稍后再试');
				}
			});
		}
	};

/***/ }
/******/ ]);
//# sourceMappingURL=lastScore.bundle.js.map