/**
 * Created by Y2X on 2016/3/8.
 */
let user = require('../../module/user.js');
let data = require('../../module/data.js');
let requestPath = window.config.requestPath;

user.checkLogin();
data.getData('student');
$('.upload-all-enroll').click(function(){
	data.uploadData('student');
});