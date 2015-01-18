define(['./module'], function (module) {
    'use strict';

    module.factory('TimeUnit', function() {
        var t = {};

        t.autoUnit = function(time) {
            if (!time) {
                return 'Hours';
            }

            var unit = 'Seconds';
            if (time % 60 == 0) {
                unit = 'Minutes';
                time  = time / 60;
            }
            if (time % 60 == 0) {
                unit = 'Hours';
                time = time / 60;
            }
            if (time % 24 == 0) {
                unit = 'Days';
                time = time / 24;
            }
            return unit;
        };

        t.toSeconds = function(time, unit) {
            switch (unit) {
                case 'Seconds': return time;
                case 'Minutes': return time * 60;
                case 'Hours': return time * 3600;
                case 'Days': return time * 86400;
                default: throw 'invalid unit ' + unit;
            }
        };

        t.toUnit = function(time, unit) {
            switch (unit) {
                case 'Seconds': return time;
                case 'Minutes': return Math.ceil(time / 60);
                case 'Hours': return Math.ceil(time / 3600);
                case 'Days': return Math.ceil(time / 86400);
                default: throw 'invalid unit ' + unit;
            }
        };

        t.convert = function(time, from, to) {
            var seconds = t.toSeconds(time, from);
            return t.toUnit(seconds, to);
        };

        return t;
    });
});