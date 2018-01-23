(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('RegistrationController', RegistrationController);
 
    RegistrationController.$inject = ['$location','UserService'];
    function RegistrationController($location, UserService) {
        var vm = this;
        
        vm.register = register;
        vm.error = null;

        function register() {
            UserService.register(vm.userEmail, vm.userName, vm.userPassword).then(function(response) {
                if (response.message) {
                	vm.error = response.message;
                } else {
                    $location.path('/login');               	
                }
            });
        }
    }
 
})();