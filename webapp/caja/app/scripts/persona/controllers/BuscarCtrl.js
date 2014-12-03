define(['module'], function (module) {
    'use strict';

    module.controller('BuscarCtrl', function($scope){
        $scope.filterOptions = {
            filterText: undefined,
            offset: 0,
            limit: 10
        };
    });
});