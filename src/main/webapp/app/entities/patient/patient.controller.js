(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('PatientController', PatientController);

    PatientController.$inject = ['$scope', '$state', 'Patient'];

    function PatientController ($scope, $state, Patient) {
        var vm = this;

        vm.patients = [];

        loadAll();

        function loadAll() {
            Patient.query(function(result) {
                vm.patients = result;
            });
        }
    }
})();
