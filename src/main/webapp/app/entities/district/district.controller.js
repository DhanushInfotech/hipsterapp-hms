(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('DistrictController', DistrictController);

    DistrictController.$inject = ['$scope', '$state', 'District'];

    function DistrictController ($scope, $state, District) {
        var vm = this;

        vm.districts = [];

        loadAll();

        function loadAll() {
            District.query(function(result) {
                vm.districts = result;
            });
        }
    }
})();
