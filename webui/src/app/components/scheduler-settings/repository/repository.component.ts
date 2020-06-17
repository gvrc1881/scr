import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { UsersService } from '../../../services/users.service';
import { UsersModel } from '../../../models/users.model';
import { Subscription } from 'rxjs';
import { RepositoryPayload } from '../../../payloads/repository.payload';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { RepositoryModel } from 'src/app/models/repository.model';
import { RepositoryService } from 'src/app/services/repository.service';
import { Constants } from 'src/app/common/constants';
import { CommonService } from 'src/app/common/common.service';

@Component({
  selector: 'app-repository',
  templateUrl: './repository.component.html',
  styleUrls: ['./repository.component.css']
})
export class RepositoryComponent implements OnInit {

  rolePermission:boolean=true;
  editPermission:boolean=true;
  addPermission:boolean=true;
  deletePermission:boolean=true;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  status: boolean;
  addRepository: boolean = false;
  saveRepository: boolean;
  cloneUpdateRepository: boolean = true;
  repositoryData: any;
  repositoryFormGroup: FormGroup;
  repositoryErrors: any;
  repositoryResponse: any;
  portPattern = "^[0-9]{4}$";
  pattern = "[a-zA-Z][a-zA-Z ]*";
  ipPattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  title: string = "Add";
  repositoryDisplayedColumns = ['sno', 'repositoryCode', 'repositoryName', 'repositoryIp', 'repositoryPort', 'repositoryDbName', 'repositoryUser', 'repositoryPassword', 'id'];
  repositoryDataSource: MatTableDataSource<RepositoryModel>;
  @ViewChild(MatPaginator, { static: true }) repositoryPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  constructor(
    private formBuilder: FormBuilder,
    private repositoryService: RepositoryService,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
  ) {
    this.repositoryErrors = {
      divisionCode: {},
      divisionName: {},
      ip: {},
      port: {},
      dbName: {},
      userName: {},
      password: {}
    };
  }

  ngOnInit() {
    this.rolePermission = this.commonService.rolePermission();
    var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","REPOSITORY") ;
  		console.log("permissionName = "+permissionName);
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    this.spinnerService.show();
    this.findAllRepositoryData();
    this.repositoryFormGroup = this.formBuilder.group({     
      'divisionCode': [null, Validators.compose([Validators.required]), this.duplicateDivisionCode.bind(this)], 
      'divisionName': [null, Validators.compose([Validators.required]) , this.duplicateDivisionName.bind(this)], 
      'ip': [null, Validators.compose([Validators.required, Validators.pattern(this.ipPattern)]), this.duplicateIP.bind(this)], 
      'port': [null, Validators.compose([Validators.required, Validators.pattern(this.portPattern)])],
      'dbName': [null, Validators.compose([Validators.required])],
      'userName': [null, Validators.compose([Validators.required])],
      'password': [null, Validators.compose([Validators.required])]
    });
    this.repositoryFormGroup.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
  }
  onFormValuesChanged() {
    for (const field in this.repositoryErrors) {
      if (!this.repositoryErrors.hasOwnProperty(field)) {
        continue;
      }
      this.repositoryErrors[field] = {};
      const control = this.repositoryFormGroup.get(field);
      if (control && control.dirty && !control.valid) {
        this.repositoryErrors[field] = control.errors;
      }
    }
  }

  duplicateIP() {
    const q = new Promise((resolve, reject) => {
      if(this.title == Constants.EVENTS.UPDATE){
        resolve(null);
      }else{
      let ip = this.repositoryFormGroup.controls['ip'].value;
      ip = ip.replace(/\./g, '*');     
      this.repositoryService.existsRepositoryIp(ip).subscribe((duplicate) => {        
        if (duplicate) {
          resolve({ 'duplicateIP': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateIP': true }); });
    }
    });
    return q;
  }

  duplicateDivisionName() {
    const q = new Promise((resolve, reject) => {     
      if(this.title == Constants.EVENTS.UPDATE){
        resolve(null);
      }else{ 
      this.repositoryService.existsRepositoryName(this.repositoryFormGroup.controls['divisionName'].value).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateDivisionName': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateDivisionName': true }); });
    }
    });
    return q;
  }
  duplicateDivisionCode() {
    const q = new Promise((resolve, reject) => {      
      //console.log("title: "+this.title);
      if(this.title == Constants.EVENTS.UPDATE){
        resolve(null);
      }else{
      this.repositoryService.existsRepositoryCode(this.repositoryFormGroup.controls['divisionCode'].value).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateDivisionCode': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateDivisionCode': true }); });
    }
    });
    return q;
  }



  onAddRepositoryFormSubmit() {
    let divisionCode: string = this.repositoryFormGroup.value.divisionCode;
    let divisionName: string = this.repositoryFormGroup.value.divisionName;
    let ip: string = this.repositoryFormGroup.value.ip;
    let port: number = this.repositoryFormGroup.value.port;
    let dbName: string = this.repositoryFormGroup.value.dbName;
    let userName: string = this.repositoryFormGroup.value.userName;
    let password: string = this.repositoryFormGroup.value.password;

    if (!this.repositoryFormGroup.valid) {
      return;
    }
   // console.log('title: ' + this.title);
    if (this.title == Constants.EVENTS.ADD || this.title == Constants.EVENTS.SAVE) {
      RepositoryPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
      RepositoryPayload.ADD_PAYLOAD.modifiedBy = this.loggedUserData.id;
      RepositoryPayload.ADD_PAYLOAD.repositoryCode = divisionCode;
      RepositoryPayload.ADD_PAYLOAD.repositoryName = divisionName;
      RepositoryPayload.ADD_PAYLOAD.repositoryIp = ip;
      RepositoryPayload.ADD_PAYLOAD.repositoryPort = port;
      RepositoryPayload.ADD_PAYLOAD.repositoryDbName = dbName;
      RepositoryPayload.ADD_PAYLOAD.repositoryUser = userName;
      RepositoryPayload.ADD_PAYLOAD.repositoryPassword = password;
      //console.log('Add Payload =' + JSON.stringify(RepositoryPayload.ADD_PAYLOAD));
      this.spinnerService.show();
      this.addRepository = false;
      this.repositoryService.AddRepository(RepositoryPayload.ADD_PAYLOAD).subscribe((response) => {
        //console.log('Response: ' + JSON.stringify(response));
        this.spinnerService.hide();
        this.repositoryResponse = response;
        if (!!this.repositoryResponse && this.repositoryResponse.code == 200) {
          this.commonService.showAlertMessage(this.repositoryResponse.message);
          this.saveRepository = false;
          this.findAllRepositoryData();
          this.repositoryFormGroup.reset();
          this.addRepository = false;
        } else {
          this.commonService.showAlertMessage("Repository Addition Failed");
        }
      },
        error => error => {
          this.repositoryErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage(this.repositoryErrors);
          this.spinnerService.hide();
        })
    }
    else if (this.title == Constants.EVENTS.UPDATE) {
      this.spinnerService.show();
      this.saveRepository = false;
      //console.log("repositoryResponse: " + JSON.stringify(this.repositoryResponse));
      let repositoryId: number = this.repositoryResponse.repositoryId;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryId = repositoryId;
      RepositoryPayload.UPDATE_PAYLOAD.modifiedBy = this.loggedUserData.id;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryCode = divisionCode;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryName = divisionName;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryIp = ip;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryPort = port;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryDbName = dbName;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryUser = userName;
      RepositoryPayload.UPDATE_PAYLOAD.repositoryPassword = password;
      //console.log("Update Payload =" + JSON.stringify(RepositoryPayload.UPDATE_PAYLOAD))
      this.repositoryService.updateRepository(RepositoryPayload.UPDATE_PAYLOAD).subscribe((response) => {
        //console.log('Update Response: ' + JSON.stringify(response));
        this.spinnerService.hide();
        this.repositoryResponse = response;
        if (!!this.repositoryResponse && this.repositoryResponse.code == 200) {
          this.commonService.showAlertMessage(this.repositoryResponse.message);
          this.saveRepository = false;
          this.findAllRepositoryData();
          this.repositoryFormGroup.reset();
          this.addRepository = false;
        } else {
          this.commonService.showAlertMessage("Repository Updating Failed");
        }
      },
        error => error => {
          this.repositoryErrors = error
          console.log(' >>> ERROR ' + error);
          this.commonService.showAlertMessage("Repository Updating Failed");
          this.spinnerService.hide();
        })
    }


  }

  deleteRepository(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected division?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.repositoryService.deleteRepository(id)
          .subscribe((response) => {
            this.repositoryResponse = response;
            if (!!this.repositoryResponse && this.repositoryResponse.code == 200) {
              this.commonService.showAlertMessage(this.repositoryResponse.message);
              this.saveRepository = false;
              this.findAllRepositoryData();
              this.repositoryFormGroup.reset();
              this.addRepository = false;
            } else {
              this.commonService.showAlertMessage("Repository Deletion Failed");
            }
          }, error => {
            this.repositoryErrors = error
            console.log(' >>> ERROR ' + error);
            this.commonService.showAlertMessage("Repository Deletion Failed");
            this.spinnerService.hide();
          });
      }
      this.confirmDialogRef = null;
    });


    this.spinnerService.hide();

  }

  findAllRepositoryData() {
    const repositoryData: RepositoryModel[] = [];
    this.repositoryService.getAllRepositories().subscribe((data) => {
      this.repositoryData = data;
      for (let i = 0; i < this.repositoryData.length; i++) {
        if (!this.rolePermission) {
          if (this.repositoryData[i].repositoryCode == this.loggedUserData.divisionCode) {
            this.repositoryData[i].sno = i + 1;
            repositoryData.push(this.repositoryData[i]);
          }
        }
        else{
          this.repositoryData[i].sno = i + 1;
          repositoryData.push(this.repositoryData[i]);
        }
      }
      this.repositoryDataSource = new MatTableDataSource(repositoryData);
      this.repositoryDataSource.paginator = this.repositoryPaginator;
      this.repositoryDataSource.sort = this.sort;
      this.spinnerService.hide();
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.repositoryDataSource.filter = filterValue;
  }

  repositoryEditAction(id: number) {
    this.addRepository = true;
    this.repositoryService.getRepositoryById(id).subscribe((response) => {
      this.cloneUpdateRepository = false;      
      this.saveRepository = false;      
      this.repositoryResponse = response;
      //console.log("resp: " + JSON.stringify(response));
      this.repositoryFormGroup.patchValue({
        divisionCode: this.repositoryResponse.repositoryCode,
        divisionName: this.repositoryResponse.repositoryName,
        ip: this.repositoryResponse.repositoryIp,
        dbName: this.repositoryResponse.repositoryDbName,
        port: this.repositoryResponse.repositoryPort,
        userName: this.repositoryResponse.repositoryUser,
        password: this.repositoryResponse.repositoryPassword,
      });
    }, error => this.repositoryErrors = error);
    this.commonService.scrollTop("forms");
  }

  editRepositoryById(id) {
    this.spinnerService.show();
    this.addRepository = true;
    this.cloneUpdateRepository = false;
    this.repositoryEditAction(id);
    this.title = "Update";
    this.spinnerService.hide();
  }

  AddRepository() {
    this.addRepository = true;
    this.title = "Add";
  }
  onGoBack() {
    this.repositoryFormGroup.reset();
    this.saveRepository = false;
    this.cloneUpdateRepository = true;
    this.addRepository = false;
    this.title = 'Save';
  }
}
