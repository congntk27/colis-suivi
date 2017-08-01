(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('CheminementDialogController', CheminementDialogController);

    CheminementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cheminement'];

    function CheminementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cheminement) {
        var vm = this;

        vm.cheminement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cheminement.id !== null) {
                Cheminement.update(vm.cheminement, onSaveSuccess, onSaveError);
            } else {
                Cheminement.save(vm.cheminement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('colisuiviApp:cheminementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataArrive = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
