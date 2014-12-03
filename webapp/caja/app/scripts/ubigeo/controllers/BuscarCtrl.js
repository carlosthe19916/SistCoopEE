define(['./module'], function (module) {
    'use strict';

    module.controller('BuscarUbigeoCtrl', function($scope){
        $scope.filterOptions = {
            filterText: undefined,
            offset: 0,
            limit: 10
        };
    });
});