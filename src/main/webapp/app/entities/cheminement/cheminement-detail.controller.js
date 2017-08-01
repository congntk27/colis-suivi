(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('CheminementDetailController', CheminementDetailController);

    CheminementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cheminement'];

    function CheminementDetailController($scope, $rootScope, $stateParams, previousState, entity, Cheminement) {
        var vm = this;

        vm.cheminement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('colisuiviApp:cheminementUpdate', function(event, result) {
            vm.cheminement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
