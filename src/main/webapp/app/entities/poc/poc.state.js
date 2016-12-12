(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('poc', {
            parent: 'entity',
            url: '/poc?page&sort&search',
            data: {
                authorities: ['ROLE_DOCTOR','ROLE_HELPDESK','ROLE_ADMIN'],
                pageTitle: 'hospitalManagementApp.poc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/poc/pocs.html',
                    controller: 'PocController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('poc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('poc-detail', {
            parent: 'entity',
            url: '/poc/{id}',
            data: {
                authorities: ['ROLE_DOCTOR','ROLE_HELPDESK','ROLE_ADMIN'],
                pageTitle: 'hospitalManagementApp.poc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/poc/poc-detail.html',
                    controller: 'PocDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('poc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Poc', function($stateParams, Poc) {
                    return Poc.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'poc',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('poc-detail.edit', {
            parent: 'poc-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_DOCTOR','ROLE_HELPDESK','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poc/poc-dialog.html',
                    controller: 'PocDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Poc', function(Poc) {
                            return Poc.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('poc.new', {
            parent: 'poc',
            url: '/new',
            data: {
                authorities: ['ROLE_DOCTOR','ROLE_HELPDESK','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poc/poc-dialog.html',
                    controller: 'PocDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                chiefcomplaint: null,
                                bp: null,
                                pulse: null,
                                temperature: null,
                                weight: null,
                                drugs: null,
                                docnote: null,
                                diagnosis: null,
                                investigation: null,
                                patientname: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('poc', null, { reload: 'poc' });
                }, function() {
                    $state.go('poc');
                });
            }]
        })
        .state('poc.edit', {
            parent: 'poc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_DOCTOR','ROLE_HELPDESK','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poc/poc-dialog.html',
                    controller: 'PocDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Poc', function(Poc) {
                            return Poc.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('poc', null, { reload: 'poc' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('poc.delete', {
            parent: 'poc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_DOCTOR','ROLE_HELPDESK','ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poc/poc-delete-dialog.html',
                    controller: 'PocDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Poc', function(Poc) {
                            return Poc.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('poc', null, { reload: 'poc' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
