(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('ColisDialogController', ColisDialogController);

    ColisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Colis', 'Agence'];

    function ColisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Colis, Agence) {
        var vm = this;

        vm.colis = entity;
        vm.clear = clear;
        vm.save = save;
        
        vm.agences = Agence.query();
        
        vm.selectedAgenceId = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if(vm.selectedAgenceId){
            	angular.forEach(vm.agences, function(agence){
            		
            		if(vm.selectedAgenceId == agence.id){
            			var cheminement = {
                				dateArrive: new Date(),
                				agence: agence
                		}
            			if(vm.colis.cheminements == null){
            				vm.colis.cheminements = [];
            			}
            			vm.colis.cheminements.push(cheminement);
            		}
            	});
            }
            if (vm.colis.id !== null) {
                Colis.update(vm.colis, onSaveSuccess, onSaveError);
            } else {
                Colis.save(vm.colis, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('colisuiviApp:colisUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
