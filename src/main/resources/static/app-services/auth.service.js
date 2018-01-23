(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthenticationService', AuthenticationService);
 
    AuthenticationService.$inject = ['$http', '$cookies', '$rootScope', '$timeout', 'UserService'];
    function AuthenticationService($http, $cookies, $rootScope, $timeout, UserService) {
        var service = {};
        var authdata;
 
        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
 
        return service;
 
        function Login(email, password, callback) {
            var response;
            UserService.get(email)
                .then(function (user) {
                    if (user !== null && user.password === password) {
                    	response = { success: true, message: 'User authorized' };
                    } else {
                        response = { success: false, message: 'Username or password is incorrect' };
                    }
                    callback(response);
                });
        }
 
        function SetCredentials(username, password, callback) {
        	
        	UserService.authorize(username, password).then(function (response) {
        		authdata = response.message;
        		
                	$rootScope.globals = {
                        currentUser: {
                            username: username,
                            authdata: authdata
                        }
                    };
                	
                	$http.defaults.headers.common['Authorization'] = 'Bearer ' + authdata;
                	
                	var cookieExp = new Date();
                	cookieExp.setDate(cookieExp.getDate() + 7);
                	$cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
                	callback({ success: true });
        	});

        }
 
        function ClearCredentials() {
            $rootScope.globals = {};
            $cookies.remove('globals');
            $http.defaults.headers.common.Authorization = '';
            return true;
        }
    }
     
})();