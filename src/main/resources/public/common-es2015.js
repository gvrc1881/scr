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
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");


class ReportService {
    constructor(http) {
        this.http = http;
        this.url = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]({
            'Content-Type': 'application/json;charset=UTF-8',
            'accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    makeReport(model) {
        return this.http.post(this.url + '/makeReport', model, { headers: this.header });
    }
    reportParameterNames() {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/reportParameterNames', { headers: this.header });
    }
    dailyProgressReports(reportType) {
        return this.http.get(this.url + '/dailyProgressReports/' + reportType, { headers: this.header });
    }
    facilityNames() {
        console.log('facilityNames::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/facilityNames', { headers: this.header });
    }
    failuresTable() {
        console.log('failuresTable::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/failuresTable', { headers: this.header });
    }
    powerBlocks() {
        console.log('powerBlocks::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/powerBlocks', { headers: this.header });
    }
    oheProductData() {
        console.log('productTable-8-1-2020::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/oheProductData', { headers: this.header });
    }
    towerCarAssetTypes() {
        console.log('towerCarReport::');
        return this.http.get(this.url + '/towerCarAssetTypes', { headers: this.header });
    }
    oheAssetTypes() {
        console.log('oheAssetTypes::');
        return this.http.get(this.url + '/oheAssetTypes', { headers: this.header });
    }
    oheAssetId() {
        console.log('oheAssetIdScheduleDate::');
        return this.http.get(this.url + '/oheAssetId', { headers: this.header });
    }
    oheScheduleDate() {
        console.log('oheAssetIdScheduleDate::');
        return this.http.get(this.url + '/oheScheduleDate', { headers: this.header });
    }
    scheduleCode() {
        console.log('scheduleCode Services::');
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/scheduleCode', { headers: this.header });
    }
    pbSwitchControl() {
        return this.http.get(this.url + '/pbSwitchControl', { headers: this.header });
    }
    elementarySections() {
        return this.http.get(this.url + '/elementarySections', { headers: this.header });
    }
    submitForm(reportModel) {
        console.log("model2-1-2020" + JSON.stringify(reportModel.reportId));
        return this.http.post(this.url + '/submitForm', reportModel, { headers: this.header });
    }
}


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
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");


class RepositoryService {
    constructor(http) {
        this.http = http;
        this.myAppUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_0__["HttpHeaders"]({
            'Content-Type': 'application/json;charset=UTF-8',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    getAllRepositories() {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/findAllRepositories', { headers: this.header });
    }
    getRepositoryById(id) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/findRepositoryById/' + id, { headers: this.header });
    }
    existsRepositoryCode(repositoryCode) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/existsRepositoryCode/' + repositoryCode, { headers: this.header });
    }
    existsRepositoryName(repositoryName) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/existsRepositoryName/' + repositoryName, { headers: this.header });
    }
    existsRepositoryIp(repositoryIp) {
        return this.http.get(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/existsRepositoryIp/' + repositoryIp, { headers: this.header });
    }
    AddRepository(repository) {
        return this.http.post(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/addRepository', repository, { headers: this.header });
    }
    updateRepository(repository) {
        return this.http.put(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/updateRepository', repository, { headers: this.header });
    }
    deleteRepository(id) {
        return this.http.delete(_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].apiUrl + '/deleteRepository/' + id, { headers: this.header });
    }
}


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
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm2015/http.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var rxjs_Observable__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! rxjs/Observable */ "./node_modules/rxjs-compat/_esm2015/Observable.js");
/* harmony import */ var rxjs_add_operator_map__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/add/operator/map */ "./node_modules/rxjs-compat/_esm2015/add/operator/map.js");
/* harmony import */ var rxjs_add_operator_catch__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! rxjs/add/operator/catch */ "./node_modules/rxjs-compat/_esm2015/add/operator/catch.js");
/* harmony import */ var rxjs_add_observable_throw__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! rxjs/add/observable/throw */ "./node_modules/rxjs-compat/_esm2015/add/observable/throw.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");







class RoleTypeService {
    constructor(_http) {
        this._http = _http;
        this.myAppUrl = _environments_environment__WEBPACK_IMPORTED_MODULE_6__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    getRoleList() {
        return this._http.get(this.myAppUrl + '/findAllRoles', { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    getMasterRoleList() {
        return this._http.get(this.myAppUrl + '/findMasterRoles', { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    getRoledata(id) {
        return this._http.get(this.myAppUrl + '/roleById/' + id, { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    addMroleType(roleType) {
        console.log("roleType:" + JSON.stringify(roleType));
        return this._http.post(this.myAppUrl + '/addRole', roleType, { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    editMroleType(roleType, id) {
        console.log(roleType);
        console.log(id);
        return this._http.post(this.myAppUrl + '/editRole', roleType, { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    saveRolePermission(row, id) {
        console.log(row);
        console.log(id);
        console.log("saveRolePermission...........");
        return this._http.post(this.myAppUrl + '/api/saveRolePermission/ ' + id + '?access_token=' + this.accessToken.access_token, row)
            .map((response) => response
        // {
        //     console.log("data in save role permissions");
        //     console.log(response);
        // }
        );
        //response;
    }
    deleteRole(id) {
        console.log("response");
        return this._http.get(this.myAppUrl + "/deleteRole/" + id, { headers: this.header })
            .map((response) => {
            console.log(response);
        }
        // response
        )
            .catch(this.errorHandler);
    }
    getPermissions() {
        return this._http.get(this.myAppUrl + '/permissions', { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    deleteRolePermission(id) {
        return this._http.get(this.myAppUrl + "/api/api/Permissions/Delete?id=" + id + '?access_token=' + this.accessToken.access_token)
            .map((response) => response)
            .catch(this.errorHandler);
    }
    getRoleTypePermissons(id) {
        return this._http.get(this.myAppUrl + '/api/getRoleTypePermissons/ ' + id + '?access_token=' + this.accessToken.access_token)
            .map((response) => response);
    }
    duplicateRoletype(roleType) {
        return this._http.get(this.myAppUrl + '/findDuplicateRole/' + roleType.roleType + '/1', { headers: this.header })
            .map((response) => response)
            .catch(this.errorHandler);
    }
    createdPermissions(permissions, createdBy, obj, id) {
        const data = {
            "createdBy": createdBy,
            "roleTypeId": id
        };
        return this._http.post(this.myAppUrl + '/savePermission', data, { headers: this.header })
            .map((response) => {
            console.log(permissions);
            console.log(createdBy);
            console.log(obj);
            console.log(id);
        }
        // response
        );
        //.catch(this.errorHandler);
    }
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
    errorHandler(error) {
        console.log(error);
        return rxjs_Observable__WEBPACK_IMPORTED_MODULE_2__["Observable"].throw(error);
    }
}


/***/ })

}]);
//# sourceMappingURL=common-es2015.js.map