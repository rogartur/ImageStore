(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ImageUploadController', ImageUploadController);
 
    ImageUploadController.$inject = ['$scope','$http', 'ImagesService'];
    function ImageUploadController($scope, $http, ImagesService) {
        $scope.filename = '';
        $scope.downloadedFile = null;
        $scope.error = null;

        $scope.uploadFile = function() {
            $scope.processDropzone();
        };
        
        $scope.downloadFile = function() {
        	ImagesService.downloadImage($scope.filename).then(function(data){
        		$scope.downloadedFile = data;
        	}, function(error) {
        		$scope.error = error;
        	});
        };

        $scope.reset = function() {
            $scope.resetDropzone();
        };
    }

})();