(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('ImagesService', ImagesService);
 
    ImagesService.$inject = ['$http'];
    function ImagesService($http) {
        var service = {};
 
        service.getImages = getImages;
        
        return service;

        function getImages() {
            return $http.get('/images/list').then(function(response){
                console.log(response);
                return response.data;
            });
        }
    }
 
})();