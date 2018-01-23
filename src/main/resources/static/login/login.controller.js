(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['$scope','$http','$location','UserService'];
    function LoginController($scope, $http, $location, UserService) {
        $scope.token = null;
        $scope.error = null;
        $scope.imagesList = [];

        $scope.login = function() {
            $scope.error = null;
            UserService.login($scope.email, $scope.password).then(function(message) {
                $scope.token = message;
                $http.defaults.headers.common['Authorization'] = 'Bearer ' + message;
                $location.path('/images');
            },
            function(error){
                $scope.error = error
                $scope.email = '';
            });
        }

        $scope.loggedIn = function() {
            return $scope.token !== null;
        }
    }
 
})();