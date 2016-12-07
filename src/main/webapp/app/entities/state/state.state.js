(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('state', {
            parent: 'entity',
            url: '/state',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'hospitalManagementApp.state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/state/states.html',
                    controller: 'StateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('state-detail', {
            parent: 'entity',
            url: '/state/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'hospitalManagementApp.state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/state/state-detail.html',
                    controller: 'StateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'State', function($stateParams, State) {
                    return State.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'state',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('state-detail.edit', {
            parent: 'state-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state/state-dialog.html',
                    controller: 'StateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['State', function(State) {
                            return State.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('state.new', {
            parent: 'state',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state/state-dialog.html',
                    controller: 'StateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                state: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('state', null, { reload: 'state' });
                }, function() {
                    $state.go('state');
                });
            }]
        })
        .state('state.edit', {
            parent: 'state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state/state-dialog.html',
                    controller: 'StateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['State', function(State) {
                            return State.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('state', null, { reload: 'state' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('state.delete', {
            parent: 'state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/state/state-delete-dialog.html',
                    controller: 'StateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['State', function(State) {
                            return State.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('state', null, { reload: 'state' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
