/**
 * Created by Y2X on 2016/3/7.
 */
let user = require('../../module/user.js');
let data = require('../../module/data.js');

$(document).ready(function () {
    $('#admin').bootstrapValidator({
        onSuccess: function () {
            //$('#sub-bnt').attr('disabled', false);
            let scoreMin = Number($('#scoreMin').val().trim());
            let scoreMax = Number($('#scoreMax').val().trim());
            let scoreDifference = Number($('#scoreDifference').val().trim());
            if (scoreMax - scoreMin > scoreDifference && scoreMax > scoreMin) {
                let score = {
                    "scoreMin": scoreMin || 0,
                    "scoreMax": scoreMax || 100,
                    "scoreDiff": scoreDifference || 50
                };
                localStorage.score = JSON.stringify(score);
                window.location.href = './login-expert.html';
            } else {
                $('.error').html('设置分数有误').removeClass('hide');
            }
        },
        onError: function () {
            //$('#sub-bnt').attr('disabled', true);
        }
    });


    let examData = window.localStorage.exam ? JSON.parse(window.localStorage.exam) : null;
    examData && $('#table').bootstrapTable('load', examData.students);

    $('#enter-last').click(function () {
        console.log('get standard')
        data.getStandard();
    });
});