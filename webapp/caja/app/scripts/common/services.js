
(function(window, angular, undefined) {'use strict';

    angular.module('common.services', [])
        .factory('Storage', function(){
            var object = undefined;
            return {
                setObject: function(elem){
                    object = elem;
                },
                getObject: function(){
                    var obj = angular.copy(object);
                    object = undefined;
                    return obj;
                }
            }
        })
        .factory('Navigation', function(){
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

})(window, window.angular);

