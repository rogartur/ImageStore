(function () {
    'use strict';
 
    angular
        .module('app', ['ngRoute', 'ngCookies', 'ui.grid', 'ui.grid.pagination'])
        .config(config)
        .run(run);
 
    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'ImagesController',
                templateUrl: 'image/images-list.html',
                controllerAs: 'vm'
            })
 
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login/login.html',
                controllerAs: 'vm'
            })
 
            .when('/register', {
                controller: 'RegistrationController',
                templateUrl: 'registration/registration.html',
                controllerAs: 'vm'
            })
            
            .when('/upload', {
                controller: 'ImageUploadController',
                templateUrl: 'image/image-upload.html',
                controllerAs: 'vm'
            })
 
            .otherwise({ redirectTo: '/login' });
    }
    
    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http', 'AuthenticationService'];
    function run($rootScope, $location, $cookieStore, $http, AuthenticationService) {
    	
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + $rootScope.globals.currentUser.authdata
        }
        
        $rootScope.loggedIn = $rootScope.globals.currentUser;
        
        $rootScope.logout = function() {
    		var response = AuthenticationService.ClearCredentials();
    		
            if (response) {
            	$location.path('/login'); 
            }
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            $rootScope.loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !$rootScope.loggedIn) {
                $location.path('/login');
            }
        });
    }
    
})();