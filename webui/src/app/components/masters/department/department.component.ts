import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';

import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from '../../../common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DepartmentModel } from 'src/app/models/department.model';
import { Constants } from 'src/app/common/constants';
import { DepartmentPayload } from 'src/app/payloads/department.payload';
@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.scss']
})

export class DepartmentComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  departmentDisplayedColumns = ['sno', 'departmentName', 'id'];
  departmentList: any;
  permissionList: any;
  departmentFormGroup: FormGroup;
  departmentErrors: any;
  title: string = "Save";
  status: boolean;
  saveDepartment: boolean;
  cloneupdate: boolean = true;
  ID: number = 0;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  addDepartment: boolean = false;
  updatedata: boolean = true;
  data: any;
  editDepartResponse: any;
  responseStatus: any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
  departmentDataSource: MatTableDataSource<DepartmentModel>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService:SendAndRequestService
  ) {

    this.departmentErrors = {
      departmentName: {},

    };

  }


  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","DEPARTMENT") ;
  	console.log("permissionName = "+permissionName);
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getDepartmentdata();
    this.departmentFormGroup = this.formBuilder.group({
      id: 0,
      'departmentName': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)]), this.duplicateDepartment.bind(this)],
    });
    this.departmentFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });

  }
  departmentEditAction(id: number) {
    this.addDepartment = true;
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.DEPARTMENT.GET_DEPARTMENT_ID+id).subscribe((resp) => {
      this.cloneupdate = false;
      this.updatedata = false;
      this.saveDepartment = false;
      this.editDepartResponse = resp;
      this.responseStatus = resp;
      this.departmentFormGroup.patchValue({
        id: this.responseStatus.id,
        departmentName: this.responseStatus.departmentName,
      });
    }, error => this.departmentErrors = error);
    this.commonService.scrollTop("forms");
  }

  getDepartmentdata() {
    const department: DepartmentModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.DEPARTMENT.GET_DEPARTMENTS).subscribe((data) => {
      this.departmentList = data;
      for (let i = 0; i < this.departmentList.length; i++) {
        this.departmentList[i].sno = i + 1;
        department.push(this.departmentList[i]);
      }
      this.departmentDataSource = new MatTableDataSource(department);
      this.departmentDataSource.paginator = this.paginator;
      this.departmentDataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
      console.log(">>> ERROR.")
    });
  }

  onFormValuesChanged() {
    for (const field in this.departmentErrors) {
      if (!this.departmentErrors.hasOwnProperty(field)) {
        continue;
      }
      // Clear previous errors
      this.departmentErrors[field] = {};

      // Get the control
      const control = this.departmentFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.departmentErrors[field] = control.errors;
      }
    }
  }
  onDepartmentSubmit() {
    let departmentName: string = this.departmentFormGroup.value.departmentName;

    if (!this.departmentFormGroup.valid) {
      return;
    }


    if (this.title == Constants.EVENTS.SAVE) {
      this.spinnerService.show();
      this.addDepartment = false;
      this.updatedata = true;
      DepartmentPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
      DepartmentPayload.ADD_PAYLOAD.modifiedBy = this.loggedUserData.id;
      DepartmentPayload.ADD_PAYLOAD.departmentName = departmentName;
      this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.DEPARTMENT.SAVE_DEPARTMENT, DepartmentPayload.ADD_PAYLOAD, false)
       .subscribe((data) => {
        this.data = data;
        this.commonService.showAlertMessage("Department Saved Successfully");
        setTimeout(() => { this.status = false }, 4000)
        this.saveDepartment = false;
        this.getDepartmentdata();
        this.departmentFormGroup.reset();
        this.addDepartment = false;
      }, error => error => {
        this.departmentErrors = error
        this.commonService.showAlertMessage(this.departmentErrors);
        this.spinnerService.hide();
      })
    }
    else if (this.title == "Update") {
      this.spinnerService.show();
      this.saveDepartment = false;
      let departmentId: number = this.editDepartResponse.departmentId;
      DepartmentPayload.UPDATE_PAYLOAD.departmentId = departmentId;
      DepartmentPayload.UPDATE_PAYLOAD.modifiedBy = this.loggedUserData.id;
      DepartmentPayload.UPDATE_PAYLOAD.departmentName = departmentName;
      //console.log("Update Payload =" + JSON.stringify(DepartmentPayload.UPDATE_PAYLOAD))
      this.sendAndRequestService.requestForPUT(Constants.app_urls.MASTERS.DEPARTMENT.UPDATE_DEPARTMENT,DepartmentPayload.UPDATE_PAYLOAD, false)
        .subscribe((data) => {
          this.commonService.showAlertMessage("Department Updated Successfully");
          this.title = "Save";
          this.getDepartmentdata();
          this.saveDepartment = false;
          this.updatedata = true;
          this.addDepartment = false;
          this.departmentFormGroup.reset();
        }, error => {
          this.departmentErrors = error;
          this.commonService.showAlertMessage(error.statusText);
          this.spinnerService.hide();
        });
    }

  }
  duplicateDepartment() {
    const q = new Promise((resolve, reject) => {
      let repartment: string = this.departmentFormGroup.controls['departmentName'].value;
      var filter = !!this.departmentList && this.departmentList.filter(repartments => {
        return repartments.departmentName.toLowerCase() == repartment.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicateDepartment': true });
      }
      this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.DEPARTMENT.EXIST_DEPARTMENT +
        this.departmentFormGroup.controls['departmentName'].value
      ).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateDepartment': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateDepartment': true }); });
    });
    return q;
  }

  addNewDepartment() {
    this.addDepartment = true;
  }
  onGoBack() {
    this.departmentFormGroup.reset();
    this.saveDepartment = false;
    this.cloneupdate = true;
    this.addDepartment = false;
    this.title = 'Save';
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.departmentDataSource.filter = filterValue;
  }
  deleteDepartment(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected department?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.MASTERS.DEPARTMENT.DELETE_DEPARTMENT, id)
          .subscribe((data) => {
            this.commonService.showAlertMessage('Department Deleted Successfully.')
            this.getDepartmentdata();
          }, error => {
            this.departmentErrors = error;
            this.commonService.showAlertMessage(error.statusText);
            this.spinnerService.hide();
          });
      }
      this.confirmDialogRef = null;
    });


    this.spinnerService.hide();

  }

  editDepartment(id) {
    this.spinnerService.show();
    this.addDepartment = true;
    this.cloneupdate = false;
    this.departmentEditAction(id);
    this.title = "Update";
    this.spinnerService.hide();
  }
}


