
angular.module('common.services', [])
    .factory('Navigation', function(){

        //{name:'', state:'', object:''}
        var elements = [];

        return {
            addElement: function(elemet){
                elements.push(elemet);
            },
            getLastElement: function(){
                return elements[elements.length-1];
            }
        }
    })
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
    });