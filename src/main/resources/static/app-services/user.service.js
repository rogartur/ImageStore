(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http'];
    function UserService($http) {
        var service = {};
 
        service.login = login;
        service.register = register;
        service.getImages = getImages;
        
        return service;
        
        function login(userEmail, userPassword) {
            return $http.post('/user/login', {email: userEmail, password: userPassword}).then(function(response) {
                return response.data.token;
            });
        }
        
        function register() {
            return $http.post('/user/register', {email: userEmail, name: userName, password: userPassword}).then(function(response){
                console.log(response);
                return response.data;
            });
        }

        function getImages() {
            return $http.get('/images/list').then(function(response){
                console.log(response);
                return response.data;
            });
        }
    }
 
})();