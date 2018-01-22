(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegistrationController', RegistrationController);
 
    RegistrationController.$inject = ['$scope','$http','UserService'];
    function RegistrationController($scope, $http, UserService) {

    }
 
})();