(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('DoctorDialogController', DoctorDialogController);

    DoctorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Doctor', 'Organization'];

    function DoctorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Doctor, Organization) {
        var vm = this;

        vm.doctor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.organizations = Organization.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.doctor.id !== null) {
                Doctor.update(vm.doctor, onSaveSuccess, onSaveError);
            } else {
                Doctor.save(vm.doctor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalManagementApp:doctorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
