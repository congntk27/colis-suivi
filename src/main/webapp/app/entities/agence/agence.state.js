(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('agence', {
            parent: 'entity',
            url: '/agence',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'colisuiviApp.agence.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agence/agences.html',
                    controller: 'AgenceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agence');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('agence-detail', {
            parent: 'agence',
            url: '/agence/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'colisuiviApp.agence.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agence/agence-detail.html',
                    controller: 'AgenceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agence');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Agence', function($stateParams, Agence) {
                    return Agence.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'agence',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('agence-detail.edit', {
            parent: 'agence-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agence/agence-dialog.html',
                    controller: 'AgenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agence', function(Agence) {
                            return Agence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agence.new', {
            parent: 'agence',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agence/agence-dialog.html',
                    controller: 'AgenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                adresse: null,
                                phone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('agence', null, { reload: 'agence' });
                }, function() {
                    $state.go('agence');
                });
            }]
        })
        .state('agence.edit', {
            parent: 'agence',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agence/agence-dialog.html',
                    controller: 'AgenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agence', function(Agence) {
                            return Agence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agence', null, { reload: 'agence' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agence.delete', {
            parent: 'agence',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agence/agence-delete-dialog.html',
                    controller: 'AgenceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Agence', function(Agence) {
                            return Agence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agence', null, { reload: 'agence' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
