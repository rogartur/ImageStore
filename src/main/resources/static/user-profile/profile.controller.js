(function() {
	'use strict';

	angular.module('app').controller('UserProfileController',
			UserProfileController);

	UserProfileController.$inject = [ '$location', '$rootScope', 'UserService' ];
	function UserProfileController($location, $rootScope, UserService) {
		var vm = this;

		vm.userEmail = $rootScope.globals.currentUser.username;
		UserService.get(vm.userEmail).then(function(response) {
			vm.userName = response.name;
			vm.userPassword = response.password;
		}, function(error) {
			vm.error = error;
		});

		vm.edit = edit;
		vm.error = null;

		function edit() {
			UserService.update(vm.userEmail, vm.userName, vm.userPassword)
					.then(function(response) {
						if (response.message) {
							vm.error = response.message;
						} else {
							$rootScope.logout();
						}
					});
		}
	}

})();