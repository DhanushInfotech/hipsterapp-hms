(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('AppointmentDetailController', AppointmentDetailController);

    AppointmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Appointment', 'Patient', 'Doctor'];

    function AppointmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Appointment, Patient, Doctor) {
        var vm = this;

        vm.appointment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hospitalManagementApp:appointmentUpdate', function(event, result) {
            vm.appointment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
