(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('ColisController', ColisController);

    ColisController.$inject = ['Colis', 'Agence'];

    function ColisController(Colis) {

        var vm = this;

        vm.colis = [];
        vm.agences = [];
        vm.selectedAgenceId = null;

        loadAll();

        function loadAll() {
            Colis.query(function(result) {
                vm.colis = result;
                vm.searchQuery = null;
            });
        }
    }
})();
