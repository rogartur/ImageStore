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
            return $http.post('/user/register', {email: userEmail, name: userName, password: userPassword}).then(handleSuccess, handleError('Registration failed'));
        }
        
        function update(userEmail, userName, userPassword) {
            return $http.put('/user/update', {email: userEmail, name: userName, password: userPassword}).then(handleSuccess, handleError('Registration failed'));
        }
        
        function get(userEmail) {
            return $http.get('/user/get/'+userEmail).then(handleSuccess, handleError('User not found'));
        }
        
        function handleSuccess(res) {
            return res.data;
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }
 
})();