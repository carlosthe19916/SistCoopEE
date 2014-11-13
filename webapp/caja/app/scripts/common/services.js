
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
        })
        .factory('Util', function(){
            return {
                getElementOfArray: function(object, attributeName, toCompare, ignoreCase){
                    if(object.length){
                        for(var i=0; i<object.length; i++){
                            var attribute1 = angular.isDefined(ignoreCase) ? object[i][attributeName.toString()].toLowerCase() : object[i][attributeName.toString()];
                            var attribute2 = angular.isDefined(ignoreCase) ? toCompare.toLowerCase() : toCompare;
                            if(attribute1 == attribute2)
                                return object[i];
                        }
                    }
                    return undefined;
                }
            }
        });

})(window, window.angular);

