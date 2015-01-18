define(['angular'], function (angular) {
    'use strict';

    return angular.module('organizacion',
        [
            'organizacion.controllers',
            'organizacion.directives',
            'organizacion.models'
        ]);
});
