define(['angular'], function (angular) {
    'use strict';

    return angular.module('common',
        [
            'common.controllers',
            'common.directives',
            'common.services',
            'common.filters'
        ]);
});
