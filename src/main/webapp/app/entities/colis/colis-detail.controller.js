(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('ColisDetailController', ColisDetailController);

    ColisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Colis'];

    function ColisDetailController($scope, $rootScope, $stateParams, previousState, entity, Colis) {
        var vm = this;

        vm.colis = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('colisuiviApp:colisUpdate', function(event, result) {
            vm.colis = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
