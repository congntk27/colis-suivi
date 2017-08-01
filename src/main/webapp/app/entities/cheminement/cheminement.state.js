(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cheminement', {
            parent: 'entity',
            url: '/cheminement',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'colisuiviApp.cheminement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cheminement/cheminements.html',
                    controller: 'CheminementController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cheminement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cheminement-detail', {
            parent: 'cheminement',
            url: '/cheminement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'colisuiviApp.cheminement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cheminement/cheminement-detail.html',
                    controller: 'CheminementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cheminement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cheminement', function($stateParams, Cheminement) {
                    return Cheminement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cheminement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cheminement-detail.edit', {
            parent: 'cheminement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cheminement/cheminement-dialog.html',
                    controller: 'CheminementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cheminement', function(Cheminement) {
                            return Cheminement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cheminement.new', {
            parent: 'cheminement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cheminement/cheminement-dialog.html',
                    controller: 'CheminementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dataArrive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cheminement', null, { reload: 'cheminement' });
                }, function() {
                    $state.go('cheminement');
                });
            }]
        })
        .state('cheminement.edit', {
            parent: 'cheminement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cheminement/cheminement-dialog.html',
                    controller: 'CheminementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cheminement', function(Cheminement) {
                            return Cheminement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cheminement', null, { reload: 'cheminement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cheminement.delete', {
            parent: 'cheminement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cheminement/cheminement-delete-dialog.html',
                    controller: 'CheminementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cheminement', function(Cheminement) {
                            return Cheminement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cheminement', null, { reload: 'cheminement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
