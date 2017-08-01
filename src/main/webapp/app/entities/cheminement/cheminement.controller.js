(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('CheminementController', CheminementController);

    CheminementController.$inject = ['Cheminement'];

    function CheminementController(Cheminement) {

        var vm = this;

        vm.cheminements = [];

        loadAll();

        function loadAll() {
            Cheminement.query(function(result) {
                vm.cheminements = result;
                vm.searchQuery = null;
            });
        }
    }
})();
