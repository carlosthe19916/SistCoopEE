define(['angular'], function (angular) {
    'use strict';

    return angular.module('persona',
        [
            'persona.controllers',
            'persona.directives',
            'persona.models'
        ]);
});
