define(['./module'], function (module) {
    'use strict';

    module.controller('NavigationController', function($scope, $state, Navigation, Storage){
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
});