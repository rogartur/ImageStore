(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ImageUploadController', ImageUploadController);
 
    ImageUploadController.$inject = ['$scope','$http'];
    function ImageUploadController($scope, $http) {
        $scope.partialDownloadLink = 'http://localhost:8080/images/download?filename=';
        $scope.filename = '';

        $scope.uploadFile = function() {
        	$scope.headerr = $http.defaults.headers.common['Authorization'];
            $scope.processDropzone();
        };

        $scope.reset = function() {
            $scope.resetDropzone();
        };
    }

})();