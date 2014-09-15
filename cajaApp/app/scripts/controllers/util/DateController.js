define(['../module'], function (controllers) {
    'use strict';
    controllers.controller('DateController', function($scope){

        $scope.dateOptions = {
            formatYear: 'yyyy',
            startingDay: 1
        };

        $scope.open = function($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.opened = true;
        };

    });
});