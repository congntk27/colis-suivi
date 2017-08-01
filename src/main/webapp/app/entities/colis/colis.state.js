(function() {
    'use strict';

    angular
        .module('colisuiviApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('colis', {
            parent: 'entity',
            url: '/colis',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'colisuiviApp.colis.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/colis/colis.html',
                    controller: 'ColisController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('colis');
                    $translatePartialLoader.addPart('person');
                    $translatePartialLoader.addPart('livraisonStatut');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('colis-detail', {
            parent: 'colis',
            url: '/colis/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'colisuiviApp.colis.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/colis/colis-detail.html',
                    controller: 'ColisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('colis');
                    $translatePartialLoader.addPart('livraisonStatut');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Colis', function($stateParams, Colis) {
                    return Colis.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'colis',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('colis-detail.edit', {
            parent: 'colis-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/colis/colis-dialog.html',
                    controller: 'ColisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Colis', function(Colis) {
                            return Colis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('colis.new', {
            parent: 'colis',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/colis/colis-dialog.html',
                    controller: 'ColisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                statut: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('colis', null, { reload: 'colis' });
                }, function() {
                    $state.go('colis');
                });
            }]
        })
        .state('colis.edit', {
            parent: 'colis',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/colis/colis-dialog.html',
                    controller: 'ColisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Colis', function(Colis) {
                            return Colis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('colis', null, { reload: 'colis' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('colis.delete', {
            parent: 'colis',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/colis/colis-delete-dialog.html',
                    controller: 'ColisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Colis', function(Colis) {
                            return Colis.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('colis', null, { reload: 'colis' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
