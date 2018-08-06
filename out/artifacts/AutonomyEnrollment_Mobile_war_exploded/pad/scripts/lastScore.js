/**
 * Created by Y2X on 2016/4/7.
 */
let user = require('../../module/user.js');
let data = require('../../module/data.js');
let requestPath = window.config.requestPath;
let exam = window.localStorage.exam ? JSON.parse(window.localStorage.exam) : {};
let stus = exam ? exam.students : [];
let standards = exam ? exam.standard : [];
console.log(standards);
let currStu = 0; //当前学生的index
let currId = 0; //当前学生的id
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
let typeTrans ={
	'专业':'major',
	'英语':'english',
	'人文':'humanity'
};
$(document).ready(function () {
	//第一个位学生
	currId = stus[currStu].id;
	$('#student-id').text(currId);

	//获取标准
	let type;
	let name;
	let grade = [];
	standards.forEach((item)=> {
		type = typeTrans[item.type];
		name = item.standardName;
		grade = item.gradeSection;
		console.log(type,name,grade);
		let appendEle = `
				<div class="title std-name">${name}</div> <!--std-name-->
				<div class="content">
					<label class="radio-inline">
						<input type="radio" name="std-${type}-${name}-value" value="${grade[0]}"><!--std-type-name-value-->
						差
					</label>
					<label class="radio-inline">
						<input type="radio" name="std-${type}-${name}-value" value="${grade[1]}">
						良
					</label>
					<label class="radio-inline">
						<input type="radio" name="std-${type}-${name}-value" value="${grade[2]}">
						优
					</label>
				</div><hr/>`;
		$('.' + type + ' .panel-body').append(appendEle);
	});

	//当前学生
	$('#next-student').click(()=> {
		//记录当前考生分数
		let currScore = 0;
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
			success: function () {
				//更新locastorage中的数据,
				setExam(currId, {
					"lastGrade": currScore,
					"isUpload": true
				});
				console.log('上传成功');
			},
			error: function () {
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
			window.localStorage.exam = JSON.stringify(exam);//更新localStorage中的分数
			$('.modal-body p').html('评分完成，请返回设置页面');
			$("#mymodal").modal("toggle");
			$('#modal-ok').click(()=> {
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
	})
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