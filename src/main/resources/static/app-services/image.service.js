(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('ImagesService', ImagesService);
 
    ImagesService.$inject = ['$http'];
    function ImagesService($http) {
        var service = {};
 
        service.getImages = getImages;
        service.downloadImage = downloadImage;
        
        return service;

        function getImages(pageNumber,size) {
        	pageNumber = pageNumber > 0?pageNumber - 1:0;
            return $http.get('/images/list?page='+pageNumber+'&size='+size).then(function(response){
                console.log(response);
                return response.data;
            });
        }
        
        function downloadImage(imageName) {
            return $http.get('/images/download?filename='+imageName).then(function(response){
                console.log(response);
                return response.data;
            });
        }
    }
 
})();