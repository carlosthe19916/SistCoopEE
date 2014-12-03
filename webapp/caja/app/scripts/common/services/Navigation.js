define(['module'], function (module) {
    'use strict';

    module.factory('Navigation', function(){
        //state = {name:'', state: '', object: ''}
        var states = [];
        return {
            getStates: function(){
                return states;
            },
            addState: function(elem){
                states.push(elem);
            }
        }
    });
});