(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ImagesController', ImagesController);
 
    ImagesController.$inject = ['$scope','$http','ImagesService'];
    function ImagesController($scope, $http, ImagesService) {
    	
    	$scope.imagesList=[];
    	$scope.error = null;
    	
        ImagesService.getImages().then(function(images) {
            $scope.imagesList = images;
        },
        function(error){
            $scope.error = error
            $scope.email = '';
        });
        
    }
 
})();