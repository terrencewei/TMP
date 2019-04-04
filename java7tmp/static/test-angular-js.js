var myApp = angular.module('myApp', ['ngMessages','ngResource','ngRoute']);
myApp.config(function ($routeProvider) {
	
	$routeProvider.when('/home',{
		tempateUrl: 'main.html',
		controller: 'mainController'
	}).when('/pdp',{
		tempateUrl: 'pdp.html',
		controller: 'secondController'
	})
});
myApp.controller('mainController', ['$log','$scope','$filter','$resource','$timeout', '$http', '$location',
                                    function($log, $scope, $filter, $resource, $timeout, $http, $location){
//	console.log("=====================");
//	console.log($scope);
//	console.log("=====================");
//	console.log($log);
//	console.log("=====================");
//	$log.log("hello log!")
//	$log.info("hello info log!")
//	$log.debug("hello debug log!")
//	$log.warn("hello warn log!")
//	$log.error("hello error log!")
//	console.log("=====================");
//	$scope.name = "abc";
//	$scope.name2 = $filter('uppercase')($scope.name);
//	$log.log($scope.name)
//	$log.log($scope.name2)
//	console.log("=====================");
//	$log.log($resource)
//	console.log("=====================");
//	$scope.myName = "Terrence!";
//	console.log("=====================");
//	$scope.inputValue = "aaa";
//	$scope.inputValueUpperCaseFunction = function() {
//		return $filter('uppercase')($scope.inputValue);
//	}
//	console.log("=====================");
//	$scope.$watch('inputValue', function(newValue, oldValue){
//		console.log("newValue:"+newValue);
//		console.log("oldValue:"+oldValue);
//	});
//	console.log("=====================");
//	setTimeout(function(){
//		$scope.$apply(function(){
//			// 位于apply函数内的东西才会被angularjs的watchers侦测,并调用digest loop进入angularjs的渲染生命周期内
//			$scope.inputValue = "bbb";
//			console.log("inputValue changed to bbb!");
//		});
//		$scope.inputValue = "ccc";
//		console.log("inputValue changed to ccc!");
//	},3000);
//	$timeout(function(){
//		$scope.inputValue = "ddd";
//		console.log("inputValue changed to ddd!");
//	},4000);
//	console.log("=====================");
//	$scope.minLength = 5;
//	console.log("=====================");
//	$scope.lists = [ 
//		{ displayName : "this is 1st element in list!" },
//		{ displayName : "this is 2nd element in list!" },
//		{ displayName : "this is 3rd element in list!" },
//	];
//	console.log("=====================");
//	// 注意:视频中的.success.error已经被废弃,用.then(fun1, fun2)代替
//	$http.get('http://localhost/rest/model/queryList').then(
//		function(result) {
//			// success function
//		},function(data,status) {
//			// error function
//			console.log(data);
//		});
//	$http.post('http://localhost/rest/model/queryList',{ id:"XX", name:"XXX" }).then(
//			function(result) {
//				// success function
//			},function(data,status) {
//				// error function
//				console.log(data);
//			});
//	console.log("=====================");
//	$scope.name2="mainController name2";
	console.log("=====================");
	$log.info("hash:"+$location.hash());
	$log.info("path:"+$location.path());
}]);

myApp.controller('secondController', ['$log','$scope','$filter','$resource','$timeout', '$http',
                                    function($log, $scope, $filter, $resource, $timeout, $http){
	$scope.name2="secondController name2";
}]);

var myFunction = function(p1,p2,p3){}
// angularjs通过这种方法来取得所有function的参数名，并在注入的时候会根据参数名匹配查找注入对应变量
// 所以一个很重要的特性就是angularjs里function的参数顺序是第几个无关紧要，只会关注参数名写对了就行！
// 也即：
// myApp.controller('mainController', function($scope, $log){});
// 和
// myApp.controller('mainController', function($log, $scope){});
// 完全等效
//console.log("=====================");
//console.log(angular.injector().annotate(myFunction));
//console.log("=====================");
// 但是变量重命名的时候就要保持顺序，比如：
// myApp.controller('mainController', ['$scope','$log',function(a,b){}]);
// 这时只有a代表了数组里第一个参数"$scope", b代表"$log"
// 这个特性也被用来解决js压缩时会对function的params重命名的问题
// 所以如果有重命名的写法时，优先以重命名为准
// 比如：
// myApp.controller('mainController', ['$scope','$log',function($log, $scope){}]);
// 这时在方法体里$log就代表了$scope

// 这个就相当于tag一样,自定义一个html标签或者class或者属性和它的行为
//myApp.directive...
// 其中属性
// scope:{
//	firstName:"@"
//	}
// 即表示directive中有一个属性是first-name,并且值是text类型
// 比如:<myCustomSearchResult first-name="{{ user.name }}"></myCustomSearchResult>
// 在template的页面中就用{{ firstName }}即可取值了
// 如果是
// firstNameAttr:"@firstName"
// 即表示directive中有一个属性是first-name
// 在template的页面中用{{ firstNameAttr }}才能取值
// 如果是"="等号表示pass的是一个object类型
// scope:{
//  user:"="
// }
// 比如:<myCustomSearchResult person="user"></myCustomSearchResult>
// 并且此时是双向绑定, 如果在directive中值被改变了,会影响原object
// 在template中{{ user }}即可使用
// 如果是"&"and符号表示pass的是一个function
// scope:{
//  formattedUserName:"&"
// }
// 可以在页面结合ng-repeat={person in people}
// 来实现一个directive就能重复显示多个的效果, 其中people是多个person结构组成的数组
// 比如:<myCustomSearchResult formatted-user-name="{{ formatName(userVar) }}"></myCustomSearchResult>
// 在template中{{ formattedUserName({ userVar: user }) }}即可使用
// 另一个scope中的属性:{
// 	restrict:"AECM"
// }
// 表示是否将指定属性渲染为directive
// A: attribute, 比如<div myCustomSearchResult></div>
// E: element, 比如<myCustomSearchResult></myCustomSearchResult>
// C: class, 比如<div class="myCustomSearchResult"></div>
// M: comments, 比如<!-- myCustomSearchResult -->
// 
// 另一个属性compile:
// compile: function(element, attributes) {
//   return {
//	   pre: function(scope, elements, attributes) {
//		   
//	   },
//	   post: function(scope, elements, attributes) {
//		   //通常只在这里写自己的业务逻辑
//	   }
//   }
// }
//等效于:
//link: function(scope, elements, attributes) {
//	
//}
//如果在html中的directive标签,中间加入dom,想让这些dom再自动被template替换后,显示在template里的地方,用:
//<ng-transclude></ng-transclude>
//在directive里写上:
//	transclude: true
//比如:
//<myCustomSearchResult>
//	<h3><ng-transclude></ng-transclude></h3>
//</myCustomSearchResult>
//或者<h3 ng-transclude></h3>
