
angular.module('common.controllers', [])
    .controller('DateController', function($scope){
        $scope.opened = false;
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
