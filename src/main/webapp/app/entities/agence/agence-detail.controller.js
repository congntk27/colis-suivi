(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('AgenceDetailController', AgenceDetailController);

    AgenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Agence'];

    function AgenceDetailController($scope, $rootScope, $stateParams, previousState, entity, Agence) {
        var vm = this;

        vm.agence = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('colisuiviApp:agenceUpdate', function(event, result) {
            vm.agence = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
