(function() {
    'use strict';
    angular
        .module('colisuiviApp')
        .factory('Cheminement', Cheminement);

    Cheminement.$inject = ['$resource', 'DateUtils'];

    function Cheminement ($resource, DateUtils) {
        var resourceUrl =  'api/cheminements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataArrive = DateUtils.convertDateTimeFromServer(data.dataArrive);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
