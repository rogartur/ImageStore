(function () {
    'use strict';
 
    angular
        .module('app', ['ngRoute'])
        .config(config);
 
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
                templateUrl: 'login/login.view.html',
                controllerAs: 'vm'
            })
 
            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register/register.html',
                controllerAs: 'vm'
            })
            
            .when('/images', {
                controller: 'ImagesController',
                templateUrl: 'images/images.html',
                controllerAs: 'vm'
            })
 
            .otherwise({ redirectTo: '/login' });
    }
    
})();