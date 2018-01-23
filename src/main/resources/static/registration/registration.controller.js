(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegistrationController', RegistrationController);
 
    RegistrationController.$inject = ['$scope','$http','$location','UserService'];
    function RegistrationController($scope, $http, $location, UserService) {
    	
        $scope.error = null;
        $scope.account = null;
        
        $scope.register = function() {
            $scope.error = null;
            UserService.register($scope.userEmail, $scope.userName, $scope.userPassword).then(function(account) {
            	$scope.account = account;
            	$location.path('/login');
            },
            function(error){
                $scope.error = error
                $scope.email = '';
            });
        }
    }
 
})();