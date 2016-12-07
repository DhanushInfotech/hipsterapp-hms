(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('PocDetailController', PocDetailController);

    PocDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Poc'];

    function PocDetailController($scope, $rootScope, $stateParams, previousState, entity, Poc) {
        var vm = this;

        vm.poc = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalManagementApp:pocUpdate', function(event, result) {
            vm.poc = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
