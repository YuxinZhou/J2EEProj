/**
 * Created by Y2X on 2016/3/17.
 */
require('../../module/config.js');
let data = require('../../module/data.js');

$(document).ready(function () {
	var examGroup = window.localStorage.exam ? JSON.parse(window.localStorage.exam).examGroup : 1;
	data.getStudentInGroup(examGroup);

	$('#back-to-admin').click(function(){
		window.location.href = './login-admin.html';
	})
});