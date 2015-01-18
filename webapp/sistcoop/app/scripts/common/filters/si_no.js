define(['./module'], function (module) {
    'use strict';

    module.filter('si_no', function() {
        return function(input, mode) {
            var defaultResult = ['Si', 'No'];
            var modeOneResult = ['Activo', 'Inactivo'];
            var modeTwoResult = ['Abierto', 'Cerrado'];
            var modeTreeResult = ['Descongelado', 'Congelado'];

            var result = defaultResult;
            if(mode){
                if(mode.toLowerCase() == 'si'){
                    result = defaultResult;
                } else if(mode.toLowerCase() == 'activo'){
                    result = modeOneResult;
                } else if(mode.toLowerCase() == 'abierto'){
                    result = modeTwoResult;
                } else if(mode.toLowerCase() == 'congelado'){
                    result = modeTreeResult;
                } else {
                    result = defaultResult;
                }
            }

            if (input) {
                return result[0];
            }
            return result[1];
        }
    });
});