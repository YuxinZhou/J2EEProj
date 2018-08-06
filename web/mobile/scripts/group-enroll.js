/**
 * Created by Y2X on 2016/3/8.
 */

let user = require('../../module/user.js');
require('../../module/config.js');
let requestPath = window.config.requestPath;


user.checkLogin();
$(document).ready(function () {

	let formData = new FormData();
	let inputs = $('input[type="file"]');
	let btn = $('#sub-btn');
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
			success: function (res) {
				if (res.error_code == 2000) {
					$('.modal-body p').html('上传成功');
				}
			},
			error: function () {
				$('.modal-body p').html('上传失败,请稍后再试');
			}
		});
	});
	function chooseFile(e) {
		let f = e.target.files;
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