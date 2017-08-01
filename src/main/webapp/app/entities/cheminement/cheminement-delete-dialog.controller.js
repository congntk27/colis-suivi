(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('CheminementDeleteController',CheminementDeleteController);

    CheminementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cheminement'];

    function CheminementDeleteController($uibModalInstance, entity, Cheminement) {
        var vm = this;

        vm.cheminement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cheminement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
