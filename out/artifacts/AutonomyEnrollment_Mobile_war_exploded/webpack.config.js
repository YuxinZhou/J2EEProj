/**
 * Created by Y2X on 2016/1/13.
 */
var webpack = require('webpack');
var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
//var TransferWebpackPlugin = require('transfer-webpack-plugin');
var mobile = true;//编译mobile或者pad部分
module.exports = {
	entry: mobile ? {
		login: './mobile/scripts/login.js',
		queryStudent: './mobile/scripts/query-student.js',
		queryExpert: './mobile/scripts/query-expert.js',
		queryGrade: './mobile/scripts/query-grade.js',
		password: './mobile/scripts/password.js',
		groupEnroll: './mobile/scripts/group-enroll.js',
		personalEnroll: './mobile/scripts/personal-enroll.js',
		confirm: './mobile/scripts/confirm.js'
	} : {
		admin: './pad/scripts/admin.js',
		loginAdmin: './pad/scripts/login-admin.js',
		loginExpert: './pad/scripts/login-expert.js',
		score: './pad/scripts/score.js',
		lastScore: './pad/scripts/lastScore.js',
		padStudent:'./pad/scripts/pad-student.js'
	},
	output: {
		path: path.join(__dirname, mobile ? "mobile/build" : "pad/build"),
		filename: '[name].bundle.js',//[hash:8].
		hash: 'false'
	},
	devtool: "source-map",
	module: {
		loaders: [
			{
				test: /\.jsx?$/,
				exclude: /(node_modules)/,
				loader: 'babel',
				query: {
					presets: ['es2015']
				}
			}, /*{
			 test: /\.scss$/,
			 loaders: ["style", "css", "sass?sourceMap"]
			 },*/ {
				test: /\.(jpe?g|png|gif|svg)$/i,
				loaders: [
					'image?{bypassOnDebug: true, progressive:true, \
						optimizationLevel: 3, pngquant:{quality: "65-80"}}',
					'url?limit=10000&name=images/[hash:8].[name].[ext]'
				]
			}, {
				test: /\.(woff|eot|ttf|svg)$/i,
				loader: 'url?limit=10000&name=font/[hash:8].[name].[ext]'
			}
		]
	},
	plugins: [
		//new HtmlWebpackPlugin({
		//	template: './mobile/login.html',
		//	filename: 'login.html'
		//}),
		//new HtmlWebpackPlugin({
		//	template: './mobile/enroll.html',
		//	filename: 'enroll.html'
		//}),
		new webpack.ProvidePlugin({
			'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'/*,
			 '$':'jquery'*/
		})/*,
		 new webpack.optimize.UglifyJsPlugin({
		 compress: {
		 warnings: false
		 }
		 }),
		 new TransferWebpackPlugin([
		 {from: './data',to:'./data'}
		 ], path.resolve(__dirname,"src"))*/
	],
	resolve: {
		extensions: ['', '.js']
	}
};