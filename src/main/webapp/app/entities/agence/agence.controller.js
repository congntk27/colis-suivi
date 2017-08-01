(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .controller('AgenceController', AgenceController);

    AgenceController.$inject = ['Agence'];

    function AgenceController(Agence) {

        var vm = this;

        vm.agences = [];

        loadAll();

        function loadAll() {
            Agence.query(function(result) {
                vm.agences = result;
                vm.searchQuery = null;
            });
        }
    }
})();
