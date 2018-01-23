(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ImagesController', ImagesController);
 
    ImagesController.$inject = ['$scope','$http','ImagesService'];
    function ImagesController($scope, $http, ImagesService) {
    	
    	$scope.error = null;
    	
        var paginationOptions = {
                pageNumber: 1,
                pageSize: 5,
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
                paginationPageSizes: [5, 10, 20],
                paginationPageSize: paginationOptions.pageSize,
                enableColumnMenus:false,
            useExternalPagination: true,
                columnDefs: [
                   { name: 'name' },
                   { name: 'date' }
                ],
                onRegisterApi: function(gridApi) {
                   $scope.gridApi = gridApi;
                   gridApi.pagination.on.paginationChanged(
                     $scope, 
                     function (newPage, pageSize) {
                       paginationOptions.pageNumber = newPage;
                       paginationOptions.pageSize = pageSize;
                       StudentService.getStudents(newPage,pageSize)
                         .success(function(data){
                           $scope.gridOptions.data = data.content;
                           $scope.gridOptions.totalItems = data.totalElements;
                         });
                    });
                }
            };
    }
 
})();