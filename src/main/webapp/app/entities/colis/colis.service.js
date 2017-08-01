(function() {
    'use strict';
    angular
        .module('colisuiviApp')
        .factory('Colis', Colis);

    Colis.$inject = ['$resource'];

    function Colis ($resource) {
        var resourceUrl =  'api/colis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
