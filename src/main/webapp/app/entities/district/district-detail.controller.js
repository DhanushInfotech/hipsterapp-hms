(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('DistrictDetailController', DistrictDetailController);

    DistrictDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'District', 'State', 'Country'];

    function DistrictDetailController($scope, $rootScope, $stateParams, previousState, entity, District, State, Country) {
        var vm = this;

        vm.district = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalManagementApp:districtUpdate', function(event, result) {
            vm.district = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
