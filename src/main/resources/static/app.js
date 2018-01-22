var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['mainService','$scope','$http',
        function(mainService, $scope, $http) {
            $scope.greeting = 'ImageStore';
            $scope.token = null;
            $scope.error = null;
            $scope.imagesList = [];

            $scope.login = function() {
                $scope.error = null;
                mainService.login($scope.email, $scope.password).then(function(token) {
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
                mainService.hasRole('user').then(function(user) {$scope.roleUser = user});
            }

            $scope.logout = function() {
                $scope.email = '';
                $scope.token = null;
                $http.defaults.headers.common.Authorization = '';
            }

            $scope.loggedIn = function() {
                return $scope.token !== null;
            }
        } ]);



appModule.service('mainService', function($http) {
    return {
        login : function(userEmail, userPassword) {
            return $http.post('/user/login', {email: userEmail, password: userPassword}).then(function(response) {
                return response.data.token;
            });
        },

        getImages : function() {
            return $http.get('/images/list').then(function(response){
                console.log(response);
                return response.data;
            });
        }
    };
});
