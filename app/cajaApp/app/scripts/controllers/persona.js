angular.module('persona.controllers', [])
    .controller('CrearPersonaNaturalController', function($scope, Country){
        Country.$search();
    });
