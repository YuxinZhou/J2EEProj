/**
 * Created by Y2X on 2016/3/6.
 */
module.exports = {
	setCookie: function (key, value, day = 1) {
		let exp = new Date();
		exp.setTime(exp.getTime() + day * 24 * 60 * 60 * 1000);
		document.cookie = key + '=' + value + ';expires=' + exp.toGMTString() + ';path=/';
	},
	getCookie: function (key) {
		let ck = document.cookie;
		let arr = ck.split(';');
		arr.forEach(function (item) {
			if (item.indexOf(key) == -1) {
				return item.substr(item.indexOf('=') + 1, item.length);
			} else {
				return null;
			}
		})
	},
	deleteCookie: function (key) {
		this.setCookie(key, '', -1);
	},
	checkCookie: function (key) {
		return document.cookie.indexOf(key) != -1;
	}
};