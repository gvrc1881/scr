(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["common"],{

/***/ "./src/app/services/drives.service.ts":
/*!********************************************!*\
  !*** ./src/app/services/drives.service.ts ***!
  \********************************************/
/*! exports provided: DrivesService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DrivesService", function() { return DrivesService; });
/* harmony import */ var src_environments_environment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! src/environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");


class DrivesService {
    constructor(http) {
        this.http = http;
        this.myAppUrl = src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl;
        this.accessToken = JSON.parse(localStorage.getItem('accessToken'));
        this.header = new _angular_common_http__WEBPACK_IMPORTED_MODULE_1__["HttpHeaders"]({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization': `Bearer ${this.accessToken}`
        });
    }
    getDrivesData() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/drives', { headers: this.header });
    }
    //functionalLocationsTypes
    findDepoTypeList() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/functionalLocationsTypes', { headers: this.header });
    }
    findAssetTypeList(assetType) {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/allAssetTypeReports/' + assetType, { headers: this.header });
    }
    findFunctionslUnits() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/facilityNames', { headers: this.header });
    }
    findDriveDataById(id) {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/driveById/' + id, { headers: this.header });
    }
    saveDriveData(saveDriveData) {
        return this.http.post(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/saveDrive', saveDriveData, { headers: this.header });
    }
    deleteDriveData(id) {
        return this.http.delete(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/deleteDrive/' + id, { headers: this.header });
    }
    getDrivesCheckListData() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/checklist', { headers: this.header });
    }
    saveCheckListData(save) {
        return this.http.post(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/saveCheckList', save, { headers: this.header });
    }
    updateCheckListData(update) {
        return this.http.put(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/updateCheckList', update, { headers: this.header });
    }
    findCheckListDataById(id) {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/checkListById/' + id, { headers: this.header });
    }
    deleteCheckListData(id) {
        return this.http.delete(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/deleteCheckList/' + id, { headers: this.header });
    }
    getElectrificationTargetsData() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/electrificationTargets', { headers: this.header });
    }
    saveElectrificationTargetsData(save) {
        return this.http.post(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/saveElectrificationTargets', save, { headers: this.header });
    }
    updateElectrificationTargetsData(update) {
        return this.http.put(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/updateElectrificationTargets', update, { headers: this.header });
    }
    findElectrificationTargetsDataById(id) {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/electrificationTargetsById/' + id, { headers: this.header });
    }
    deleteElectrificationTargetsData(id) {
        return this.http.delete(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/deleteElectrificationTargets/' + id, { headers: this.header });
    }
    getInspectionData() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/inspections', { headers: this.header });
    }
    saveInspectionsData(save) {
        return this.http.post(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/saveInspections', save, { headers: this.header });
    }
    updateInspectionsData(update) {
        return this.http.put(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/updateInspections', update, { headers: this.header });
    }
    findInspectionsDataById(id) {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/inspectionsById/' + id, { headers: this.header });
    }
    deleteInspectionsData(id) {
        return this.http.delete(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/deleteInspections/' + id, { headers: this.header });
    }
    getStipulationData() {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/stipulations', { headers: this.header });
    }
    saveStipulationData(save) {
        return this.http.post(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/saveStipulations', save, { headers: this.header });
    }
    updateStipulationData(update) {
        return this.http.put(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/updateStipulations', update, { headers: this.header });
    }
    findStipulationDataById(id) {
        return this.http.get(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/stipulationsById/' + id, { headers: this.header });
    }
    deleteStipulationData(id) {
        return this.http.delete(src_environments_environment__WEBPACK_IMPORTED_MODULE_0__["environment"].apiUrl + '/deleteStipulations/' + id, { headers: this.header });
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