 // Define the module for our AngularJS application.
        var app = angular.module( "Pharmacy", ['ngMaterial', 'jkAngularRatingStars' ] );
        // -------------------------------------------------- //
        // -------------------------------------------------- //
        // I control the main demo.
        app.controller(
            "DemoController",
            function( $scope, $mdDialog,$filter, employeeService ) {
                // I contain the list of employees to be rendered.
                $scope.employees = [];

                $scope.newRecipe = {};
                $scope.newRecipe.date = new Date();
                $scope.newRecipe.medicaments = [];
                $scope.newRecipe.employee = "";
                $scope.newRecipe.medicament = "";


                $scope.newEmployee = {};
                $scope.newEmployee.name = "";
                $scope.newEmployee.surname = "";
                $scope.newEmployee.cnp = "";
                
                $scope.newMedicament = {};
                $scope.newMedicament.name = "";
                $scope.newMedicament.price = "";
                $scope.newMedicament.quantity = "";

                loadRemoteData();

                $scope.addEmployee = function() { // pacientName, cnp, employeeId, date, medicaments
                    employeeService.addEmployee( $scope.newEmployee.name, $scope.newEmployee.surname, $scope.newEmployee.cnp)
                        .then( loadRemoteData );

                    // clear fields!!!
                    $scope.newEmployee.name = "";
                    $scope.newEmployee.surname = "";
                    $scope.newEmployee.cnp = "";
                };

                $scope.addMedicament = function() { // pacientName, cnp, employeeId, date, medicaments
                    employeeService.addMedicament( $scope.newMedicament.name, $scope.newMedicament.price, $scope.newMedicament.quantity)
                        .then( loadRemoteData );

                    // clear fields!!!
                    $scope.newMedicament.name = "";
                    $scope.newMedicament.price = "";
                    $scope.newMedicament.quantity = "";
                };


                // I remove the given employee from the current collection.
                $scope.removeEmployee = function(employee ) {
                    // Rather than doing anything clever on the client-side, I'm
                    // just
                    // going to reload the remote data.
                    employeeService.removeEmployee( employee.id )
                        .then( loadRemoteData )
                    ;
                };

                $scope.removeMedicament = function(medicament ) {
                    // Rather than doing anything clever on the client-side, I'm
                    // just
                    // going to reload the remote data.
                    employeeService.removeMedicament( medicament.id )
                        .then( loadRemoteData )
                    ;
                };

                $scope.removeRecipe = function(prescription ) {
                    // Rather than doing anything clever on the client-side, I'm
                    // just
                    // going to reload the remote data.
                    employeeService.removeRecipe( prescription.id )
                        .then( loadRemoteData )
                    ;
                };

                $scope.addRecipe = function() { // pacientName, cnp, employeeId, date, medicaments
                    employeeService.addRecipe( $scope.newRecipe.pacientName, $scope.newRecipe.cnpPacient, $scope.newRecipe.employee, $scope.newRecipe.date, $scope.newRecipe.medicaments)
                        .then( loadRemoteData );
                    
                    //clear fields
                    $scope.newRecipe.cnpPacient="";
                    $scope.newRecipe.pacientName="";
                    $scope.newRecipe.date =new Date();
                    $scope.newRecipe.medicaments = [];
                    $scope.newRecipe.employee = "";
                    $scope.newRecipe.medicament = "";
                };

                $scope.selectedItem = null;
                $scope.searchText = null;

                $scope.querySearch = function querySearch(query) {

                    return query ? $scope.medicaments.filter(createFilterFor(query)) : $scope.medicaments;
                };

                function createFilterFor(query) {
                    var lowercaseQuery = angular.lowercase(query);

                    return function filterFn(medicament) {
                        return (angular.lowercase(medicament.name).indexOf(lowercaseQuery) === 0);
                    };
                }
                // I load the remote data from the server.
                function loadRemoteData() {
                    // The employeeService returns a promise.
                    employeeService.getEmployees()
                        .then(
                            function( employees ) {
                                $scope.employees = [].concat(employees);
                            }
                        );

                    reloadMedicaments();

                    employeeService.getRecipes()
                        .then(
                            function( recipes ) {
                                $scope.recipes = [].concat(recipes);
                            }
                        );
                }

                function reloadMedicaments() {
                    employeeService.getMedicaments()
                        .then(
                            function( medicaments ) {
                                $scope.medicaments = [].concat(medicaments);
                            }
                        );

                    return $scope.medicaments;
                }


                function reloadMedicamentsForComments(medicamentId, $medicamentForDialog) {
                    employeeService.getMedicaments()
                        .then(
                            function( medicaments ) {
                                $scope.medicaments = [].concat(medicaments);

                                var test = $filter('filter')(medicaments, function (d) {return d.id === medicamentId;})[0];
                                $medicamentForDialog.comments = [].concat(test.comments);
                            }
                        );

                    return $scope.medicaments;
                }


                $scope.showAddNewCommentPrompt = function(ev, medicament) {

                    var $globalScope = $scope;

                    $mdDialog.show({
                            controller: DialogController,
                            templateUrl: 'addNewCommentDialog.tmpl.html',
                            parent: angular.element(document.body),
                            targetEvent: ev,
                            clickOutsideToClose:true,
                            fullscreen: $scope.customFullscreen
                        }).then(function(answer) {
                            $scope.status = 'You said the information was "' + answer + '".';
                        }, function() {
                            $scope.status = 'You cancelled the dialog.';
                        });

                    function DialogController($scope, $mdDialog) {

                        $scope.newComment = {};
                        $scope.newComment.rating = 3;
                        $scope.medicamentForDialog = medicament;

                        $scope.addComment = function() {

                            employeeService.addComment(medicament.id, $scope.newComment.text, $scope.newComment.rating)
                                .then();

                            $scope.newComment.rating = 3;
                            $scope.newComment.text = "";
                        };

                        $scope.removeComment = function(comment) {

                            employeeService.removeComment(medicament.id, comment.id)
                                .then();
                        };

                        $scope.refresh = function() {
                            reloadMedicamentsForComments(medicament.id, $scope.medicamentForDialog)
                        };


                        $scope.increaseLike = function(comment) {
                            employeeService.increaseLike(medicament.id, comment.id)
                                .then();
                        };

                        $scope.increaseDislike = function(comment) {
                            employeeService.increaseDislike(medicament.id, comment.id)
                                .then();
                        };

                        $scope.hide = function() {
                            $mdDialog.hide();
                        };

                        $scope.cancel = function() {
                            $mdDialog.cancel();
                        };

                        $scope.answer = function(answer) {
                            $mdDialog.hide(answer);
                            //alert($scope.rating);
                        };
                    }
                };
            }
        );
        // -------------------------------------------------- //
        // -------------------------------------------------- //
        // I act a repository for the remote employee collection.
        app.service(
            "employeeService",
            function( $http, $q ) {
                // Return public API.
                var root_url = "https://farmacieonline.herokuapp.com/";
                return({
                    addEmployee: addEmployee,
                    getEmployees: getEmployees,
                    removeEmployee: removeEmployee,

                    addMedicament:addMedicament,
                    addComment:addComment,
                    removeComment:removeComment,
                    getMedicaments: getMedicaments,
                    getRecipes: getRecipes,
                    removeMedicament: removeMedicament,
                    removeRecipe: removeRecipe,
                    increaseLike: increaseLike,
                    increaseDislike: increaseDislike,

                    addRecipe: addRecipe
                });
                // ---
                // PUBLIC METHODS.
                // ---
                // I add a employee with the given name to the remote collection.
                function addEmployee( name, surname, cnp ) {
                	var employee = {
                        name: name,
                        surname: surname,
                        cnp: cnp
                    };

                    var request = $http({
                        method: "post",
                        url: root_url + "employee",
                        params: {
                            action: "add"
                        },
                        data: JSON.stringify(employee)
                    });
                    return( request.then( handleSuccess, handleError ) );
                }
                // I get all of the employees in the remote collection.
                function getEmployees() {

                    var request = $http({
                        method: "get",
                        url: root_url + "employee",
                        params: {
                            action: "get"
                        }
                    });
                    return( request.then( handleSuccess, handleError ) );
                }
                // I remove the employee with the given ID from the remote
				// collection.
                function removeEmployee( id ) {
                    var request = $http({
                        method: "delete",
                        url: root_url + "employee",
                        params: {
                            action: "delete"
                        },
                        data: id
                    });
                    return( request.then( handleSuccess, handleError ) );
                }


                // I get all of the employees in the remote collection.
                function addMedicament( name, price, quantity ) {
                	var medicament = {
                        name: name,
                        price: price,
                        quantity: quantity
                    };

                    var request = $http({
                        method: "post",
                        url: root_url + "medicament",
                        params: {
                            action: "add"
                        },
                        data: JSON.stringify(medicament)
                    });
                    return( request.then( handleSuccess, handleError ) );
                }
                function addComment( medicamentId, text, rating ) {
                    var comment = {
                        medicamentId: medicamentId,
                        text: text,
                        rating: rating
                    };

                    var request = $http({
                        method: "post",
                        url: root_url + "comment",
                        params: {
                            action: "add"
                        },
                        data: JSON.stringify(comment)
                    });
                    return( request.then( handleSuccess, handleError ) );
                }

                function removeComment( medicamentId, commentId) {
                    var request = $http({
                        method: "delete",
                        url: root_url + "comment",
                        params: {
                            action: "delete"
                        },
                        headers: {
                            "Content-Type": "application/json;charset=utf-8"
                        },
                        data: {
                            medicamentId: medicamentId,
                            id: commentId,
                            text: "",
                            rating: 0
                        }
                    });
                    return( request.then( handleSuccess, handleError ) );
                }

                function increaseLike(medicamentId, commentId) {

                    var comment = {
                        medicamentId: medicamentId,
                        id: commentId
                    };

                    var request = $http({
                        method: "post",
                        url: root_url + "increaseLikes",
                        params: {
                            action: "add"
                        },
                        data: JSON.stringify(comment)
                    });
                    return( request.then( handleSuccess, handleError ) );
                }

                function increaseDislike(medicamentId, commentId) {

                    var comment = {
                        medicamentId: medicamentId,
                        id: commentId
                    };

                    var request = $http({
                        method: "post",
                        url: root_url + "increaseDislikes",
                        params: {
                            action: "add"
                        },
                        data: JSON.stringify(comment)
                    });
                    return( request.then( handleSuccess, handleError ) );
                }


                function getMedicaments() {

                    var request = $http({
                        method: "get",
                        url: root_url + "medicament",
                        params: {
                            action: "get"
                        }
                    });
                    return( request.then(
                        handleSuccess,
                        handleError
                    ));
                }
                function getRecipes() {

                    var request = $http({
                        method: "get",
                        url: root_url + "prescription",
                        params: {
                            action: "get"
                        }
                    });
                    return( request.then( handleSuccess, handleError ) );
                }



                function removeMedicament( id ) {
                    var request = $http({
                        method: "delete",
                        url: root_url + "medicament",
                        params: {
                            action: "delete"
                        },
                        data: id
                    });
                    return( request.then( handleSuccess, handleError ) );
                }
                function removeRecipe( id ) {
                    var request = $http({
                        method: "delete",
                        url: root_url + "prescription",
                        params: {
                            action: "delete"
                        },
                        data: id
                    });
                    return( request.then( handleSuccess, handleError ) );
                }

                function addRecipe( pacientName, cnp, employee, date, medicaments ) {
                    var prescription = {
                        name: pacientName,
                        cnp: cnp,
                        employee : employee,
                        date: date,
                        medicaments :medicaments
                    };

                    var request = $http({
                        method: "post",
                        url: root_url + "prescription",
                        params: {
                            action: "add"
                        },
                        data: JSON.stringify(prescription)
                    });
                    return( request.then( handleSuccess, handleError ) );
                }
                // ---
                // PRIVATE METHODS.
                // ---
                // I transform the error response, unwrapping the application
				// dta from
                // the API response payload.
                function handleError( response ) {
                    // The API response from the server should be returned in a
                    // nomralized format. However, if the request was not
					// handled by the
                    // server (or what not handles properly - ex. server error),
					// then we
                    // may have to normalize it on our end, as best we can.
                    if (
                        ! angular.isObject( response.data ) ||
                        ! response.data.message
                        ) {
                        return( $q.reject( "An unknown error occurred." ) );
                    }
                    // Otherwise, use expected error message.
                    return( $q.reject( response.data.message ) );
                }
                // I transform the successful response, unwrapping the
				// application data
                // from the API response payload.
                function handleSuccess( response ) {
                    return( response.data );
                }
            }
        );