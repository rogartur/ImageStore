(function () {
    'use strict';
 
    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);
 
    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'LoginController',
                templateUrl: 'login/login.html',
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
            
            .when('/images', {
                controller: 'ImagesController',
                templateUrl: 'image/images-list.html',
                controllerAs: 'vm'
            })
            
            .when('/upload', {
                controller: 'ImageUploadController',
                templateUrl: 'image/image-upload.html',
                controllerAs: 'vm'
            })
 
            .otherwise({ redirectTo: '/' });
    }
    
    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
    	
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }
    
})();