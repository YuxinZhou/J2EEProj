/**
 * Created by Y2X on 2016/3/22.
 */
require('./config.js');
let requestPath = window.config.requestPath;
module.exports = {
	student: [],
	expert: [],
	grade: [],
	uploadData: function (option) {//'score'|'student'
		$('.modal-body p').html('上传中');
		$("#mymodal").modal("toggle");
		$.ajax({
			url: requestPath + option == 'score' ? 'uploadScoreToMainServer' : 'uploadStudentToMainServer',
			success: function (res) {
				if (res.error_code == 2000) {
					$('.modal-body p').html('上传成功');
				} else {
					$('.modal-body p').html('上传失败');
				}
			},
			error: function () {
				$('.modal-body p').html('网络错误，请稍后再试');
			}
		})
	},
	getData: function (option) {//option = 'student'|'expert'|'grade'
		$('.modal-body p').html('拉取数据中');
		$("#mymodal").modal("toggle");
		$.ajax({
			type: 'get',
			url: requestPath + option + 'List',
			success: function (res) {
				if (res.error_code == 2000) {
					//弹框关闭
					$("#mymodal").modal("toggle");
					//替换表格数据
					this[option] = res.goods;
					$('#table').bootstrapTable('load', res.goods);
				}
			},
			error: function () {
				$('.modal-body p').html('网络错误，请稍后再试');
			}
		});
	},
	getStudentInGroup: function (examGroup) {
		$('.modal-body p').html('拉取考生数据中');
		$("#mymodal").modal("toggle");
		$.ajax({
			type: 'get',
			async:false,
			url: requestPath + 'pad/groupCandidateList?examGroup=' + examGroup,
			success: function (res) {
				if (res.error_code == 2000) {
					//弹框关闭
					$("#mymodal").modal("toggle");
					//替换表格数据
					let stu = res.goods;
					stu.forEach(item=> {
						item.firstGrade = 0;
						item.finalGrade = 0;
						item.isUpload = false;
					});
					$('#table').bootstrapTable('load', res.goods);

					//加入localStorage.exam
					let exam = JSON.parse(window.localStorage.exam);
					exam.students = stu;
					window.localStorage.exam = JSON.stringify(exam);

				}
			},
			error: function () {
				$('.modal-body p').html('网络错误，请稍后再试');
			}
		});
	}
};