(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('ColisDeleteController',ColisDeleteController);

    ColisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Colis'];

    function ColisDeleteController($uibModalInstance, entity, Colis) {
        var vm = this;

        vm.colis = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Colis.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
