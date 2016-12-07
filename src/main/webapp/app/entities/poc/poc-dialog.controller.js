(function() {
    'use strict';

    angular
        .module('hospitalManagementApp')
        .controller('PocDialogController', PocDialogController);

    PocDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Poc'];

    function PocDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Poc) {
        var vm = this;

        vm.poc = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.poc.id !== null) {
                Poc.update(vm.poc, onSaveSuccess, onSaveError);
            } else {
                Poc.save(vm.poc, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hospitalManagementApp:pocUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
