import { Component, ViewChild, OnInit, ElementRef, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UsersModel } from '../../../../models/users.model';
import { Subscription } from 'rxjs';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
    selector: 'app-user-menu',
    templateUrl: './user-menu.component.html',
    styleUrls: []
})
export class UserMenuComponent implements OnInit {
    editPermission:boolean=true;
    addPermission:boolean=true;
    deletePermission:boolean=true;
    saveUserList:boolean = false;
    updateUserList:boolean = false;
    message:string;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    usersList :FormGroup;
    status:boolean ;
    data: any ;
    rolePermission:boolean=true;
    loggedDelete:boolean=true;
    userdata: any = JSON.parse(sessionStorage.getItem('userData'));
    ID: number = 0;
    action: string = "";
    title: string = "Create";
    displayedColumns = ['sno', 'userid', 'email','userName', 'divisionCode', 'status', 'id'];
    dataSource: MatTableDataSource<UsersModel>;
    paramSubscription: Subscription;
    pagination =Constants.PAGINATION_NUMBERS;
    
    @ViewChild(MatPaginator, {static:true}) paginator: MatPaginator;
    @ViewChild(MatSort, {static:true}) sort: MatSort;
    @ViewChild('filter', {static:true}) filter: ElementRef;
    
    constructor(
        public dialog: MatDialog,
        private _router: Router,
        private router:ActivatedRoute,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService:CommonService,
        private sendAndRequestService :SendAndRequestService
    ) { }

    ngOnInit() { 
        var permissionName = this.commonService.getPermissionNameByLoggedData("MASTERS","USERS") ;               
        this.addPermission = this.commonService.getPermissionByType("Add",permissionName);
        this.editPermission = this.commonService.getPermissionByType("Edit",permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete",permissionName);     
    
        this.rolePermission = this.commonService.rolePermission();
        this.spinnerService.show();        
        this.getAllUsersData();
    }
    processEditAction(id: number) {          
        this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_USERS_BYID + id)
        this._router.navigate([id],{relativeTo: this.router});
    }
       
      
    

    deleteUserList(id) {
        
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
        });

        this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected user?';

        this.confirmDialogRef.afterClosed().subscribe(result => {
            if ( result )
            {
                this.spinnerService.show();
            this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.DELETE_USERS + id)
            .subscribe((data) => {
                if(data){
                    this.commonService.showAlertMessage('User Deleted Successfully.')
                    this.status = false;
                }
                setTimeout(()=>{ this.status = false }, 4000)
                this.getAllUsersData();
                this.status = true;
            });
            }
            this.confirmDialogRef = null;
        });       

        this.spinnerService.hide();
    }
    getAllUsersData(){
        
       const users: UsersModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_ALLUSERS).subscribe((data) => {
            this.data = data;                    
            for (let i = 0; i < this.data.length; i++) {
                if(!this.rolePermission){                                      
                     if(this.data[i].id == this.userdata.id){
                        this.data[i].sno = i+1;
                        users.push(this.data[i]);
                     }else if(this.userdata.divisionCode == 'All'){
                        this.data[i].sno = i+1;
                        users.push(this.data[i]);
                     }
                }else{
                    if(this.data[i].id == this.userdata.id){
                        this.data[i].sno = i + 1;
                        users.push(this.data[i]);
                    }else if(this.userdata.divisionCode == 'All'){
                        this.data[i].sno = i+1;
                        users.push(this.data[i]);
                     }
                }
            }            
            this.dataSource = new MatTableDataSource(users);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;            
            this.spinnerService.hide();
        }, error => {           
            this.spinnerService.hide();
        } );
    }
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }
    
    userRowsDialog(row: any){
        const dialogRef = this.dialog.open(AddUserRolesComponent, {
          height: '250px',
          width: '80%', 
          data: { userData : row
                }
        });
        
        dialogRef.afterClosed().subscribe(result => {
        });
        
    }
    
    }


@Component({
  selector: 'add-user-roles',
  templateUrl: 'add-user-roles.component.html',
})
export class AddUserRolesComponent implements OnInit  {
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    userData: any;
    masterRoles: any;
    userRoleFormGroup: FormGroup;
    userName: any;
    masterRoleId: any;
    actionTitle: any;
    response: any;
    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,  
    private sendAndRequestService:SendAndRequestService,
    private dialog: MatDialog,
    private commonService: CommonService,
    private dialogRef: MatDialogRef<AddUserRolesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
    ) {
      
      if(data) {
          this.userData = data.userData;
          //console.log('*** user data **'+this.userData.username);
          this.userName = this.userData.username
      }
  }
    
     ngOnInit() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.ROLE_TYPE.GET_MASTER_ROLE_LIST).subscribe((response) => {
            this.masterRoles = response;
        });
        this.userRoleFormGroup = this.formBuilder.group({
            userName:[ null, Validators.required],
            masterRoleId: [ null, Validators.required]
        });
        this.getUserRole();
     }
    
    getUserRole() {
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_ROLE_BASED_ON_USER+this.userData.id).subscribe((response) => {
            this.masterRoleId = response;
            
            if(this.masterRoleId){
                this.userRoleFormGroup.patchValue({
                    masterRoleId:this.masterRoleId
                })
                this.actionTitle = "Update";
            }else
                this.actionTitle = "Save" ;
        });
    }
    
    saveUserRole(){
        let userRole = {
            userId: this.userData.id,
            masterRoleId: this.userRoleFormGroup.value.masterRoleId
        }
       this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.SAVE_USER_ROLE,userRole,false).subscribe((response) => {
            this.response = response;
            if(this.response.code == 200 && !!this.response) {
                if(this.actionTitle == 'Save' )
                    this.commonService.showAlertMessage("Role Saved Successfully");
                else
                    this.commonService.showAlertMessage("Role updated Successfully"); 
            }else{
                if(this.actionTitle == 'Save' )
                    this.commonService.showAlertMessage("Role Saving Failed");
                else
                    this.commonService.showAlertMessage("Role Updating Failed");    
            }
        }); 
    }
    
    onGoBack(): void {
       this.dialogRef.close();
    }
}