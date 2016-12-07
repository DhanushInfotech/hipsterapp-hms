'use strict';

describe('Controller Tests', function() {

    describe('District Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDistrict, MockState, MockCountry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDistrict = jasmine.createSpy('MockDistrict');
            MockState = jasmine.createSpy('MockState');
            MockCountry = jasmine.createSpy('MockCountry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'District': MockDistrict,
                'State': MockState,
                'Country': MockCountry
            };
            createController = function() {
                $injector.get('$controller')("DistrictDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hospitalManagementApp:districtUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
