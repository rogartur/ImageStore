(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};
 
        service.authorize = authorize;
        service.register = register;
        service.update = update;
        service.get = get;
        
        return service;
        
        function authorize(userEmail, userPassword) {
            return $http.post('/user/authorize', {email: userEmail, password: userPassword}).then(handleSuccess, handleError('Authorization failed'));
        }
        
        function register(userEmail, userName, userPassword) {
            return $http.post('/user/register', {email: userEmail, name: userName, password: userPassword}).then(handleSuccess, function(data) {
            	var respMsg;
            	if(data.data.exception == 'javax.validation.ConstraintViolationException') {
            		respMsg = 'Nieprawidłowe hasło. Poprawne hasło to przynajmniej jedna duża, jedna mała litera, jedna liczba oraz jeden znak specjalny (!@#$%^&*)';
            	} else {
            		respMsg = data.data.message;
            	}
            	return { success: false, message: respMsg }
            });
        }
        
        function update(userEmail, userName, userPassword) {
            return $http.put('/user/update', {email: userEmail, name: userName, password: userPassword}).then(handleSuccess, function(data) {
            	return { success: false, message: data.data.message }
            });
        }
        
        function get(userEmail) {
            return $http.get('/user/get/'+userEmail+'/').then(handleSuccess, handleError('User not found'));
        }
        
        function handleSuccess(res) {
            return res.data;
        }
 
        function handleError(data) {
            return function () {
                return { success: false, message: data };
            };
        }
    }
 
})();