"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", []);

app.controller("ctrlExpenses", ["$rootScope", "$scope", "Restangular", function ExpensesCtrl($rootScope, $scope, $Restangular) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";
	
	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);
	
	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

    $Restangular.setResponseInterceptor(function(response, operation, route, url) {
        var newResponse;
        if (operation === "getList") {
            newResponse = response.expenses;
        }
        else {
            newResponse = response;
        }
   
        return newResponse;
    });
    
	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		$Restangular.one("alchemy-service/resources/expenses").getList().then(function(expenses) {
			$scope.expenses = expenses;
		});
	}

	$scope.saveExpense = function() {
		if ($scope.expensesform.$valid) {
			// Post the expense via REST
			$Restangular.one("alchemy-service/resources/expenses").post(null, $scope.newExpense).then(function() {
				// Reload new expenses list
				loadExpenses();
			});
		}
	};

	$scope.clearExpense = function() {
		$scope.newExpense = {};
	};

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
}]);
