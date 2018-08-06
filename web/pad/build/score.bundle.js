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
	 * Created by Y2X on 2016/3/17.
	 */
	__webpack_require__(2);
	var data = __webpack_require__(4);
	
	$(document).ready(function () {
		var examGroup = window.localStorage.exam ? JSON.parse(window.localStorage.exam).examGroup : 1;
		data.getStudentInGroup(examGroup);
	
		$('#back-to-admin').click(function () {
			window.location.href = './login-admin.html';
		});
	});

/***/ },
/* 1 */,
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
/* 3 */,
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
//# sourceMappingURL=score.bundle.js.map