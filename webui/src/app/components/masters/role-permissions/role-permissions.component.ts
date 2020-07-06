import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from '../../../common/common.service';
import { RolePermissionModel } from 'src/app/models/role-permissions.model';
import { RolePermissionPayload } from 'src/app/payloads/role-permission.payload';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
@Component({
  selector: 'app-role-permissions',
  templateUrl: './role-permissions.component.html',
  styleUrls: ['./role-permissions.component.scss']
})
export class RolePermissionsComponent implements OnInit {
  editPermission:boolean=true;
  addPermission:boolean=true;
  deletePermission:boolean=true;
  id: number = 0;
  saveRole: boolean = false;
  selected:string;
  editRole: boolean = true;
  displayedColumns = ['sno', 'roleName', 'permission', 'id'];
  dataSource: MatTableDataSource<RolePermissionModel>;
  roleTypeData:MatTableDataSource<RolePermissionModel>;
  rolepermissions: any;
  rolesList: any;
  PermissionId: string;
  roleType: string;
  permissionId: number;
  selectedRow: Number;
  permisssionList: any;
  status: boolean;
  public permissionname;
  permission: RolePermissionModel[] = [];
  permissionRole: boolean = true;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  permissionType = false;
  @ViewChild(MatPaginator,{static:true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static:true}) sort: MatSort;
  constructor(
      private route: ActivatedRoute, 
      private _router: Router,
      private spinnerService: Ng4LoadingSpinnerService,
      public dialog: MatDialog,
      private commonService: CommonService,
      private sendAndRequestService:SendAndRequestService
     ) { }

  ngOnInit() {   
    var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","ROLES PERMISSION") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName); 

    this.getRoleTypedata();    
  }
  getRoleTypedata() {
    this.spinnerService.show();
    const roles: RolePermissionModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.ROLE_PERMISSIONS.GET_ROLE_PERMISSION).subscribe((data) => {
      this.rolesList = data;
      for (let i = 0; i < this.rolesList.length; i++) {
        this.rolesList[i].sno = i + 1;
        roles.push(this.rolesList[i]);
      }
      this.roleTypeData = new MatTableDataSource(roles);      
          
      this.dataSource = new MatTableDataSource(roles);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
     this.id = this.rolesList[0].permissionId;
     this.selected = this.rolesList[0].permission;
     
      this.loadRolePermissionDataById(this.id);
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  loadRolePermissionDataById(id){    

   this.spinnerService.show();
    this.permisssionList = [];
    this.id = id;
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.ROLE_PERMISSIONS.GET_ROLE_PERMISSION).subscribe(resp => {     
      this.permisssionList = resp;
    })
  }

  editRoleTypePermission(id) {
    this.permissionType = false;
    this.rolepermissions = [];
    this.permission = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.ROLE_TYPE.GET_ROLE_TYPE_PERMISSION + this.id).subscribe((data) => {
      this.rolepermissions = (data["#result-set-1"]);      
      for (let i = 0; i < this.rolepermissions.length; i++) {
        this.rolepermissions[i].sno = i + 1;
        this.rolepermissions[i].editMode = false;
        this.permission.push(this.rolepermissions[i]);
      }
      this.dataSource = new MatTableDataSource<RolePermissionModel>();
      this.dataSource = new MatTableDataSource(this.permission);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;      
      this.spinnerService.hide();
    })
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  permissiontypeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.ROLE_PERMISSIONS.GET_ROLE_PERMISSION).subscribe(resp => {     
      this.permisssionList = resp;
    })
  }
  editPermissions(row) {
    row.editMode = true;        
    if (row.roleId) {
      this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.ROLE_PERMISSIONS.GET_ROLE_PERMISSION).subscribe(resp => {     
        this.permisssionList = resp;
      })
    }
  }
  cancelRoleTypePermission(row) {  
    row.editMode = false;
    this.permissionType = false;
  }
  onChange(event): void {   
    
    this.permissionname = event.value;
  }


  saveRoleTypePermission(row) {    
    this.spinnerService.show();
    let roleId = row.roleId;
    let permissionId = this.permissionname.id;
    let roleName = row.roleName;
    let permission = this.permissionname.permission;
    let modifiedBy = this.userdata.id;
      
      RolePermissionPayload.UPDATE_PAYLOAD.modifiedBy = modifiedBy;
      RolePermissionPayload.UPDATE_PAYLOAD.roleId = roleId;
      RolePermissionPayload.UPDATE_PAYLOAD.permissionId = permissionId
      RolePermissionPayload.UPDATE_PAYLOAD.roleName = roleName
      RolePermissionPayload.UPDATE_PAYLOAD.permission = permission
      
    this.sendAndRequestService.requestForPUT(Constants.app_urls.MASTERS.ROLE_PERMISSIONS.UPDATE_ROLE_PERMISSION, RolePermissionPayload.UPDATE_PAYLOAD, false).subscribe(response =>{
      this.spinnerService.hide();      
      this.loadRolePermissionDataById(this.id);
      this.getRoleTypedata()
      this.commonService.showAlertMessage('Permission Updated Successfully.');
      row.editMode = false;
      this.permissionType = false;     
      
      if (response) {
        this.status = false;
      }          
      setTimeout(() => { this.status = false }, 4000)
    }, error => {
      this.spinnerService.hide();      
      this.commonService.showAlertMessage(error);
    });
 
  }
  onGoBack() {
    this._router.navigate(['../'], { relativeTo: this.route });
  }
  deleteRolepermission(id) {

    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });

    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected user?';

    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.MASTERS.ROLE_TYPE.DELETE_ROLE_PERMISSION, id)
          .subscribe((data) => {           
            this.editRoleTypePermission(this.id);            
          });
      }
      this.confirmDialogRef = null;
    });
    this.spinnerService.hide();
  }
}
export interface PermissionModel {
  MenuName: String,
  SubMenuName: String,
  PermissionName: String,
  id: number,
  sno: number,
}