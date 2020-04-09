(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["common"],{

/***/ "./src/app/services/report.service.ts":
/*!********************************************!*\
  !*** ./src/app/services/report.service.ts ***!
  \********************************************/
/*! exports provided: ReportService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReportService", function() { return ReportService; });
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");


var ReportService = /** @class */ (function () {
    function ReportService(http) {
        this.http = http;
        this.url = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]({
            'Content-Type': 'application/json;charset=UTF-8',
            'accept': 'application/json',
            'Authorization': "Bearer " + this.accessToken
        });
    }
    ReportService.prototype.makeReport = function (model) {
        return this.http.post(this.url + '/makeReport', model, { headers: this.header });
    };
    ReportService.prototype.reportParameterNames = function () {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/reportParameterNames', { headers: this.header });
    };
    ReportService.prototype.dailyProgressReports = function (reportType) {
        return this.http.get(this.url + '/dailyProgressReports/' + reportType, { headers: this.header });
    };
    ReportService.prototype.facilityNames = function () {
        console.log('facilityNames::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/facilityNames', { headers: this.header });
    };
    ReportService.prototype.failuresTable = function () {
        console.log('failuresTable::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/failuresTable', { headers: this.header });
    };
    ReportService.prototype.powerBlocks = function () {
        console.log('powerBlocks::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/powerBlocks', { headers: this.header });
    };
    ReportService.prototype.oheProductData = function () {
        console.log('productTable-8-1-2020::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/oheProductData', { headers: this.header });
    };
    ReportService.prototype.towerCarAssetTypes = function () {
        console.log('towerCarReport::');
        return this.http.get(this.url + '/towerCarAssetTypes', { headers: this.header });
    };
    ReportService.prototype.oheAssetTypes = function () {
        console.log('oheAssetTypes::');
        return this.http.get(this.url + '/oheAssetTypes', { headers: this.header });
    };
    ReportService.prototype.oheAssetId = function () {
        console.log('oheAssetIdScheduleDate::');
        return this.http.get(this.url + '/oheAssetId', { headers: this.header });
    };
    ReportService.prototype.oheScheduleDate = function () {
        console.log('oheAssetIdScheduleDate::');
        return this.http.get(this.url + '/oheScheduleDate', { headers: this.header });
    };
    ReportService.prototype.scheduleCode = function () {
        console.log('scheduleCode Services::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/scheduleCode', { headers: this.header });
    };
    ReportService.prototype.pbSwitchControl = function () {
        return this.http.get(this.url + '/pbSwitchControl', { headers: this.header });
    };
    ReportService.prototype.elementarySections = function () {
        return this.http.get(this.url + '/elementarySections', { headers: this.header });
    };
    ReportService.prototype.submitForm = function (reportModel) {
        console.log("model2-1-2020" + JSON.stringify(reportModel.reportId));
        return this.http.post(this.url + '/submitForm', reportModel, { headers: this.header });
    };
    return ReportService;
}());



/***/ }),

/***/ "./src/app/services/repository.service.ts":
/*!************************************************!*\
  !*** ./src/app/services/repository.service.ts ***!
  \************************************************/
/*! exports provided: RepositoryService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RepositoryService", function() { return RepositoryService; });
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");


var RepositoryService = /** @class */ (function () {
    function RepositoryService(http) {
        this.http = http;
        this.myAppUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]({
            'Content-Type': 'application/json;charset=UTF-8',
            'Accept': 'application/json',
            'Authorization': "Bearer " + this.accessToken
        });
    }
    RepositoryService.prototype.getAllRepositories = function () {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/findAllRepositories', { headers: this.header });
    };
    RepositoryService.prototype.getRepositoryById = function (id) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/findRepositoryById/' + id, { headers: this.header });
    };
    RepositoryService.prototype.existsRepositoryCode = function (repositoryCode) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/existsRepositoryCode/' + repositoryCode, { headers: this.header });
    };
    RepositoryService.prototype.existsRepositoryName = function (repositoryName) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/existsRepositoryName/' + repositoryName, { headers: this.header });
    };
    RepositoryService.prototype.existsRepositoryIp = function (repositoryIp) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/existsRepositoryIp/' + repositoryIp, { headers: this.header });
    };
    RepositoryService.prototype.AddRepository = function (repository) {
        return this.http.post(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/addRepository', repository, { headers: this.header });
    };
    RepositoryService.prototype.updateRepository = function (repository) {
        return this.http.put(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/updateRepository', repository, { headers: this.header });
    };
    RepositoryService.prototype.deleteRepository = function (id) {
        return this.http.delete(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/deleteRepository/' + id, { headers: this.header });
    };
    return RepositoryService;
}());



/***/ }),

/***/ "./src/app/services/roletype.service.ts":
/*!**********************************************!*\
  !*** ./src/app/services/roletype.service.ts ***!
  \**********************************************/
/*! exports provided: RoleTypeService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RoleTypeService", function() { return RoleTypeService; });
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var rxjs_Observable__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs/Observable */ "./node_modules/rxjs-compat/_esm5/Observable.js");
/* harmony import */ var rxjs_add_operator_map__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/add/operator/map */ "./node_modules/rxjs-compat/_esm5/add/operator/map.js");
/* harmony import */ var rxjs_add_operator_catch__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! rxjs/add/operator/catch */ "./node_modules/rxjs-compat/_esm5/add/operator/catch.js");
/* harmony import */ var rxjs_add_observable_throw__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! rxjs/add/observable/throw */ "./node_modules/rxjs-compat/_esm5/add/observable/throw.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");







var RoleTypeService = /** @class */ (function () {
    function RoleTypeService(_http) {
        this._http = _http;
        this.myAppUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_6__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': "Bearer " + this.accessToken
        });
    }
    RoleTypeService.prototype.getRoleList = function () {
        return this._http.get(this.myAppUrl + '/findAllRoles', { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.getMasterRoleList = function () {
        return this._http.get(this.myAppUrl + '/findMasterRoles', { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.getRoledata = function (id) {
        return this._http.get(this.myAppUrl + '/roleById/' + id, { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.addMroleType = function (roleType) {
        console.log("roleType:" + JSON.stringify(roleType));
        return this._http.post(this.myAppUrl + '/addRole', roleType, { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.editMroleType = function (roleType, id) {
        console.log(roleType);
        console.log(id);
        return this._http.post(this.myAppUrl + '/editRole', roleType, { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.saveRolePermission = function (row, id) {
        console.log(row);
        console.log(id);
        console.log("saveRolePermission...........");
        return this._http.post(this.myAppUrl + '/api/saveRolePermission/ ' + id + '?access_token=' + this.accessToken.access_token, row)
            .map(function (response) { return response; }
        // {
        //     console.log("data in save role permissions");
        //     console.log(response);
        // }
        );
        //response;
    };
    RoleTypeService.prototype.deleteRole = function (id) {
        console.log("response");
        return this._http.get(this.myAppUrl + "/deleteRole/" + id, { headers: this.header })
            .map(function (response) {
            console.log(response);
        }
        // response
        )
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.getPermissions = function () {
        return this._http.get(this.myAppUrl + '/permissions', { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.deleteRolePermission = function (id) {
        return this._http.get(this.myAppUrl + "/api/api/Permissions/Delete?id=" + id + '?access_token=' + this.accessToken.access_token)
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.getRoleTypePermissons = function (id) {
        return this._http.get(this.myAppUrl + '/api/getRoleTypePermissons/ ' + id + '?access_token=' + this.accessToken.access_token)
            .map(function (response) { return response; });
    };
    RoleTypeService.prototype.duplicateRoletype = function (roleType) {
        return this._http.get(this.myAppUrl + '/findDuplicateRole/' + roleType.roleType + '/1', { headers: this.header })
            .map(function (response) { return response; })
            .catch(this.errorHandler);
    };
    RoleTypeService.prototype.createdPermissions = function (permissions, createdBy, obj, id) {
        var data = {
            "createdBy": createdBy,
            "roleTypeId": id
        };
        return this._http.post(this.myAppUrl + '/savePermission', data, { headers: this.header })
            .map(function (response) {
            console.log(permissions);
            console.log(createdBy);
            console.log(obj);
            console.log(id);
        }
        // response
        );
        //.catch(this.errorHandler);
    };
    // createdPermissions(permissions,userId,clonedRoleId,createdRoleId){    
    //                      console.log("saveRolePermission...........");    
    //                          return this._http.post(this.myAppUrl +'/api/createMroleTypePermissions/'+userId+"/"+clonedRoleId+"/"+createdRoleId+'?access_token='+this.accessToken.access_token,permissions)   
    //                             .map((response: Response) =>
    //                                {      
    //                                         console.log("data in save role permissions"); 
    //                                         console.log(permissions);
    //                                                 console.log(userId);
    //                                                console.log(clonedRoleId);
    //                                               console.log(createdRoleId);        
    //                                 //  console.log(response);  
    //                                      }      
    //                                       );
    //                                     }
    RoleTypeService.prototype.errorHandler = function (error) {
        console.log(error);
        return rxjs_Observable__WEBPACK_IMPORTED_MODULE_2__["Observable"].throw(error);
    };
    return RoleTypeService;
}());



/***/ })

}]);
//# sourceMappingURL=common-es5.js.map