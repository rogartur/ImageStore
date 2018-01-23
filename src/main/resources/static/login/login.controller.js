(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginController', LoginController);
 
    LoginController.$inject = ['$location', 'AuthenticationService'];
    function LoginController($location, AuthenticationService) {
        var vm = this;
 
        vm.login = login;
        vm.error = null;
 
        (function initController() {
            AuthenticationService.ClearCredentials();
        })();
 
        function login() {
            AuthenticationService.Login(vm.email, vm.password, function (response) {
                if (response.success) {
                    AuthenticationService.SetCredentials(vm.email, vm.password, function (response) {
                    	if (response.success) {
                    		$location.path('/');
                    	} else {
                        	vm.error = 'Error while authorizing user. Check cookies.';
                        }
                    })
                } else {
                	vm.error = response.message;
                }
            });
        };
    }
 
})();