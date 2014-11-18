
(function(window, angular, undefined) {'use strict';

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

            $scope.toggleMin = function() {
                $scope.minDate = $scope.minDate ? null : new Date(1920, 0, 1);
            };
            $scope.toggleMin();

            $scope.toggleMax = function() {
                $scope.maxDate = $scope.maxDate ? null : new Date();
            };
            $scope.toggleMax();

        })
        .controller('NavigationController', function($scope, $state, Navigation, Storage){
            $scope.states = Navigation.getStates();

            $scope.goToState = function(index){
                Storage.setObject($scope.states[index].object);
                var nextState = $scope.states[index].state;
                var params = $scope.states[index].params;
                $scope.states.splice(index, $scope.states.length - index);
                $state.go(nextState, params);
            };

            $scope.clear = function(){
                $scope.states = [];
            };
        });

})(window, window.angular);
