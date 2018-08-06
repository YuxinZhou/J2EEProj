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
	 * Created by elsa on 16/4/7.
	 */
	
	__webpack_require__(2);
	var requestPath = window.config.requestPath;
	var timer = void 0;
	
	$(document).ready(function () {
	    $('#bnt').click(function () {
	
	        //setInterval("alert('间隔执行的方法')",500);
	
	        //var intDiff = parseInt(60);//倒计时总秒数量
	
	        var major = $("#examType").find("option:selected").val();
	        $('#bnt').attr('disabled', 'disabled');
	        $('.modal-body p').html('拉取考卷中');
	        $("#mymodal").modal("toggle");
	        $('#modal-ok').hide();
	        $('#next-question').hide();
	
	        $.ajax({
	            type: 'get',
	            url: requestPath + 'pad/getPaper?subject=' + major,
	            success: function success(res) {
	                $('#bnt').attr('disabled', false);
	                if (res.error_code == 2000) {
	                    (function () {
	                        // if (res.goods.length > 0) {
	                        //     $('.modal-body p').html('考题' + res.goods.shift().content +
	                        //         '<div>' +
	                        //         //'<span id="day_show">0天</span>' +
	                        //         // '<strong id="hour_show">0时</strong> ' +
	                        //         '<strong id="minute_show">0分</strong> ' +
	                        //         '<strong id="second_show">0秒</strong>' +
	                        //         '</div>');
	                        // }
	                        // else {
	                        //     $("#mymodal").modal("toggle");
	                        // }
	
	                        var nextQuestion = function nextQuestion() {
	                            count++;
	                            if (count == ques.length) {
	                                $('.modal-body p').html('考试结束，感谢答题');
	                                $('#next-question').hide();
	                                $('#modal-ok').show();
	                                count = 0;
	                                return;
	                            }
	                            showQuestion(ques[count]);
	                        };
	
	                        var timerCount = function timerCount(intDiff) {
	                            timer = window.setInterval(function () {
	                                var day = 0,
	                                    hour = 0,
	                                    minute = 0,
	                                    second = 0; //时间默认值
	                                if (intDiff > 0) {
	                                    day = Math.floor(intDiff / (60 * 60 * 24));
	                                    hour = Math.floor(intDiff / (60 * 60)) - day * 24;
	                                    minute = Math.floor(intDiff / 60) - day * 24 * 60 - hour * 60;
	                                    second = Math.floor(intDiff) - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60;
	                                }
	                                if (minute <= 9) minute = '0' + minute;
	                                if (second <= 9) second = '0' + second;
	                                $('#day_show').html(day + "天");
	                                $('#hour_show').html('<s id="h"></s>' + hour + '时');
	                                $('#minute_show').html('<s></s>' + minute + '分');
	                                $('#second_show').html('<s></s>' + second + '秒');
	                                intDiff--;
	                                if (intDiff == 0) {
	                                    clearInterval(timer);
	                                    nextQuestion();
	                                }
	                            }, 1000);
	                        };
	
	                        var clearTimer = function clearTimer() {
	                            window.clearInterval(timer);
	                        };
	
	                        var showQuestion = function showQuestion(item) {
	                            timer && clearTimer(timer); //清除时间
	                            $('.modal-body p').html('\n\t\t\t\t\t<div class="time-item">\n\t\t\t\t\t\t<strong id="minute_show">0分</strong>\n\t\t\t\t\t\t<strong id="second_show">0秒</strong>\n\t\t\t\t\t</div>\n                    <div class="paper-wrap">\n                        <h4>题目：' + item.content + '</h4>\n                        <div>类别：' + item.type + '</div>\n                    </div>\n                    \n\t\t\t\t\t');
	                            timerCount(item.limit); //重新开始时间
	                        };
	
	                        //按钮改变
	                        $('#next-question').show();
	                        $('#modal-ok').hide();
	
	                        //显示考卷
	                        var ques = res.goods;
	                        var count = 0;
	                        showQuestion(ques[count]);
	                        // $('.modal-body p').html(`
	                        // <div class="time-item">
	                        // <strong id="minute_show">0分</strong>
	                        // <strong id="second_show">0秒</strong>
	                        // </div>
	                        // <div class="paper-wrap">
	                        //     <h3>类别：${ques[count].type}</h3>
	                        //     <h4>题目：${ques[count].content}</h4>
	                        // </div>
	                        //
	                        // `);
	                        // timer(ques[count].limit);
	                        // $('.modal-body p').html('考题:' + res.goods.content +
	                        //     '<div class="time-item">' +
	                        //     //'<span id="day_show">0天</span>' +
	                        //     // '<strong id="hour_show">0时</strong> ' +
	                        //     '<strong id="minute_show">0分</strong> ' +
	                        //     '<strong id="second_show">0秒</strong>' +
	                        //     '</div>');
	
	                        $('#next-question').click(function () {
	                            nextQuestion();
	                        });
	                    })();
	                }
	                if (res.error_code == 2001) {
	                    $('.modal-body p').html('复试时间尚未开始');
	                    $('#next-question').hide();
	                    $('#modal-ok').show();
	                    $('#bnt').attr('disabled', false);
	                }
	            },
	            error: function error() {
	                $('.modal-body p').html('网络错误，请稍后再试');
	                $('#next-question').hide();
	                $('#bnt').attr('disabled', false);
	            }
	        });
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

/***/ }
/******/ ]);
//# sourceMappingURL=padStudent.bundle.js.map