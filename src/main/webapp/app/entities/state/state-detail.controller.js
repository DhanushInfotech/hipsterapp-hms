(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('StateDetailController', StateDetailController);

    StateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'State', 'Country'];

    function StateDetailController($scope, $rootScope, $stateParams, previousState, entity, State, Country) {
        var vm = this;

        vm.state = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalManagementApp:stateUpdate', function(event, result) {
            vm.state = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
