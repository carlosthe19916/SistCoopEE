define(['angular'], function (angular) {
    'use strict';

    return angular.module('xenon',
        [
            'xenon.controllers',
            'xenon.directives',
            'xenon.factory',
            'xenon.services'
        ]);
});
