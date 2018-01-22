(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['$scope','$http','UserService'];
    function LoginController($scope, $http, UserService) {
    	$scope.greeting = 'ImageStore';
        $scope.token = null;
        $scope.error = null;
        $scope.imagesList = [];

        $scope.login = function() {
            $scope.error = null;
            UserService.login($scope.email, $scope.password).then(function(token) {
                $scope.token = token;
                $http.defaults.headers.common.Authorization = 'Bearer ' + token;
                $scope.getImages();
            },
            function(error){
                $scope.error = error
                $scope.email = '';
            });
        }

        $scope.getImages = function() {
        	UserService.hasRole('user').then(function(user) {$scope.roleUser = user});
        }

        $scope.logout = function() {
            $scope.email = '';
            $scope.token = null;
            $http.defaults.headers.common.Authorization = '';
        }

        $scope.loggedIn = function() {
            return $scope.token !== null;
        }
    }
 
})();