(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ImagesController', ImagesController);
 
    ImagesController.$inject = ['$scope','$http','ImagesService','uiGridConstants'];
    function ImagesController($scope, $http, ImagesService, uiGridConstants) {
    	
    	$scope.error = null;
    	
        var paginationOptions = {
                pageNumber: 1,
                pageSize: 10,
            sort: null
            };
        
        ImagesService.getImages(
        	      paginationOptions.pageNumber,
        	      paginationOptions.pageSize).then(function(data){
        	        $scope.gridOptions.data = data.content;
        	        $scope.gridOptions.totalItems = data.totalElements;
        	      },
		        function(error){
		            $scope.error = error
		        });
        
        $scope.gridOptions = {
                paginationPageSizes: [10, 20, 30],
                paginationPageSize: paginationOptions.pageSize,
                enableRowSelection: true,
                enableColumnMenus:false,
                useExternalPagination: true,
                enableFiltering: true,
                columnDefs: [
                   { name: 'id', enableFiltering: false },
                   { name: 'name'},
                   { name: 'dateUpload', type: 'date', cellFilter: 'date:\'yyyy-MM-dd HH:mm\'', enableFiltering: false }
                ],
                onRegisterApi: function(gridApi) {
                   $scope.gridApi = gridApi;
                   gridApi.pagination.on.paginationChanged(
                     $scope, 
                     function (newPage, pageSize) {
                       paginationOptions.pageNumber = newPage;
                       paginationOptions.pageSize = pageSize;
                       ImagesService.getImages(newPage,pageSize)
                         .then(function(data){
                           $scope.gridOptions.data = data.content;
                           $scope.gridOptions.totalItems = data.totalElements;
                         });
                    });
                }
            };
    }
 
})();