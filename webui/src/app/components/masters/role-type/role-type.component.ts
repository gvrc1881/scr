import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { RoleTypeService } from '../../../services/roletype.service';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { RoleTypeModule } from './role-type.module';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from '../../../common/common.service';
import { RoleTypeModel } from 'src/app/models/role-type.model';
@Component({
  selector: 'app-role-type',
  templateUrl: './role-type.component.html',
  styleUrls: ['./role-type.component.scss']
})

export class RoleTypeComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  availableRolesList:any;
  displayedColumns = ['sno', 'role', 'id'];
  rolesList: any;
  permissionList: any;
  RoleType: FormGroup;
  roleTypeErrors: any;
  title: string = "Save";
  status: boolean;
  saveRole: boolean;
  cloneupdate: boolean = true;
  ID: number = 0;
  id: number = 0;
  pattern = "[a-zA-Z][a-zA-Z ]*";
  addRole: boolean = false;
  updatedata: boolean = true;
  data: any;
  rolePermission: boolean = true;
  responseStatus: any;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  dataSource: MatTableDataSource<RoleTypeModel>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  constructor(
    private _roleTypemaster: RoleTypeService,
    private formBuilder: FormBuilder,
    private _router: Router, private router: ActivatedRoute,
    private actRoute: ActivatedRoute,
    public dialog: MatDialog,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
  ) {

    this.roleTypeErrors = {
      roles: {},
      role: {}
    };

  }


  ngOnInit() {

    if (!!localStorage.getItem("loggedUser")) {
      if (!this.commonService.rolePermission()) {
        this._router.navigate(['users']);
      }
    var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","ROLES") ;
  	console.log("permissionName = "+permissionName);
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    }
    
    this.spinnerService.show();
    this.RoleType = this.formBuilder.group({
      id: 0,
      'roles': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)]), this.duplicateRoletype.bind(this)],
      'role': [null],
    });
    this.RoleType.valueChanges.subscribe(() => {
      this.onFormValuesChanged();
    });
    this.getRoleTypeData();
  }
  RoleEditAction(id: number) {
    this.addRole = true;
    this._roleTypemaster.getRoledata(id).subscribe((resp) => {
      this.cloneupdate = false;
      this.updatedata = false;
      this.saveRole = false;
      // this.addRole =false;    
      this.responseStatus = resp;
      this.RoleType.patchValue({
        id: this.responseStatus.id,
        roles: this.responseStatus.roleType,
      });
    }, error => this.roleTypeErrors = error);
    this.commonService.scrollTop("forms");
  }

  getRoleTypeData() {
    const roles: RoleTypeModel[] = [];
    this._roleTypemaster.getRoleList().subscribe((data) => {
      this.rolesList = data;
      for (let i = 0; i < this.rolesList.length; i++) {
        this.rolesList[i].sno = i + 1;
        roles.push(this.rolesList[i]);
      }
      this.dataSource = new MatTableDataSource(roles);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

  onFormValuesChanged() {
    for (const field in this.roleTypeErrors) {
      if (!this.roleTypeErrors.hasOwnProperty(field)) {
        continue;
      }
      // Clear previous errors
      this.roleTypeErrors[field] = {};

      // Get the control
      const control = this.RoleType.get(field);
      if (control && control.dirty && !control.valid) {
        this.roleTypeErrors[field] = control.errors;
      }
    }
  }
  onRoleTypeSubmit() {
    let var_id: string = this.RoleType.value.id;
    let var_role: string = this.RoleType.value.role;
    let var_roles: string = this.RoleType.value.roles;
    if (!this.RoleType.valid) {
      return;
    }


    if (this.title == "Save") { // on create menu form submit      
      this.spinnerService.show();
      this.addRole = false;
      this.updatedata = true;

      this._roleTypemaster.addMroleType({
        'createdBy': this.userdata.id,
        'createdDate': null,
        'roleType': var_roles,
        'modifiedBy': this.userdata.id,
        'modifiedDate': null,
        'name': var_role,
        //  'RoleType': var_roles,
        //  'id': null,
        'statusId': 1,
        //'sno': 1,
      }).subscribe((data) => {
        this.data = data;
        this.commonService.showAlertMessage("Role Saved Successfully");
        let temObj = 0;
        if (this.saveRole == true) {
          temObj = 1;
        }
        /*  this._roleTypemaster.createdPermissions({
           'createdBy': this.userdata.id,
           'id': this.data.id,
           'obj': temObj,
         }, this.userdata.id, temObj, this.data.id).subscribe(rolePermissionResponse => {          
           this.permissionList = rolePermissionResponse;
         }); */
        if (data) {
          this.status = false;
        }
        setTimeout(() => { this.status = false }, 4000)
        this.saveRole = false;
        this.getRoleTypeData();
        this.RoleType.reset();
        this.addRole = false;
      }, error => error => {
        this.roleTypeErrors = error
        this.commonService.showAlertMessage(this.roleTypeErrors);
        this.spinnerService.hide();
      })
    }
    else if (this.title == "Update") {
      this.spinnerService.show();
      this.saveRole = false;
      this._roleTypemaster.editMroleType({
        'createdBy': this.userdata.id,
        'createdDate': null,
        'roleType': var_roles,
        'modifiedBy': this.userdata.id,
        'modifiedDate': null,
        // 'roleName': null,
        //  'RoleType': var_roles,
        'statusId': 1,
        'sno': 1,
        'id': var_id,
      }, var_id)
        .subscribe((data) => {
          this.commonService.showAlertMessage("Role Updated Successfully");
          this.title = "Save";
          this.getRoleTypeData();
          this.saveRole = false;
          this.updatedata = true;
          this.addRole = false;
          this.RoleType.reset();
        }, error => {
          this.roleTypeErrors = error;
          this.commonService.showAlertMessage(error.statusText);
          this.spinnerService.hide();
        });
    }

  }
  duplicateRoletype() {

    const q = new Promise((resolve, reject) => {
      let role: string = this.RoleType.controls['roles'].value;
      var filter = !!this.rolesList && this.rolesList.filter(roles => {
        return roles.roleType.toLowerCase() == role.trim().toLowerCase();
      });
      if (filter.length > 0) {
        resolve({ 'duplicateRoletype': true });
      }
      this._roleTypemaster.duplicateRoletype({
        'modifiedBy': null,
        'createdBy': null,
        'roleType': this.RoleType.controls['roles'].value,
        'id': null,
        'role': null
      }).subscribe((duplicate) => {
        if (duplicate) {
          resolve({ 'duplicateRoletype': true });
        } else {
          resolve(null);
        }
      }, () => { resolve({ 'duplicateRoletype': true }); });
    });
    return q;
  }

  AddRole() {
    this.spinnerService.show();
    this._roleTypemaster.getMasterRoleList().subscribe((response) => {
      this.availableRolesList = response;
      let list = this.availableRolesList;
      for (let i = 0; i < list.length; i++) {
        let flag = this.rolesList.find(val=> {
          return val.roleType == list[i].roleName;
        });
        console.log(flag+" : "+list[i].roleName)
        if(!flag){
          this.availableRolesList.splice( this.availableRolesList.indexOf(list[i].roleName), 1 );
        }
      }
      
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
    this.addRole = true;
  }
  onGoBack() {
    this.RoleType.reset();
    this.saveRole = false;
    this.cloneupdate = true;
    this.addRole = false;
    this.title = 'Save';
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }
  deleteRole(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected user?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this._roleTypemaster.deleteRole(id)
          .subscribe((data) => {
            this.commonService.showAlertMessage('Role Deleted Successfully.')
            this.getRoleTypeData();
          }, error => {
            this.roleTypeErrors = error;
            this.commonService.showAlertMessage(error.statusText);
            this.spinnerService.hide();
          });
      }
      this.confirmDialogRef = null;
    });


    this.spinnerService.hide();

  }

  editRoleType(ID) {
    this.spinnerService.show();
    this.addRole = true;
    this.cloneupdate = false;
    this.RoleEditAction(ID);
    this.title = "Update";
    this.spinnerService.hide();
  }
  getRoleTypePermissons(id: number) {
    this._roleTypemaster.getRoleTypePermissons(id)
    this._router.navigate([id], { relativeTo: this.router });
  }
}


