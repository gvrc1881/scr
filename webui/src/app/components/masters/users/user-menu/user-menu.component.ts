import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogRef } from '@angular/material';
import { UsersService } from '../../../../services/users.service';
import { UsersModel } from '../../../../models/users.model';
import { Subscription } from 'rxjs';
import { UsersComponent } from '../users.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FuseConfirmDialogComponent } from '../../../../components/confirm-dialog/confirm-dialog.component';
import { CommonService } from 'src/app/common/common.service';



@Component({
    selector: 'app-user-menu',
    templateUrl: './user-menu.component.html',
    styleUrls: ['./user-menu.component.scss']
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
  // users : UsersComponent;
    loggedDelete:boolean=true;
    userdata: any = JSON.parse(localStorage.getItem('userData'));
    ID: number = 0;
    action: string = "";
    title: string = "Create";
    displayedColumns = ['sno', 'userid', 'email','userName', 'divisionCode', 'status', 'id'];
    dataSource: MatTableDataSource<UsersModel>;
    paramSubscription: Subscription;
    
    @ViewChild(MatPaginator, {static:true}) paginator: MatPaginator;
    @ViewChild(MatSort, {static:true}) sort: MatSort;
    @ViewChild('filter', {static:true}) filter: ElementRef;
    constructor(
        private _userMenuService: UsersService, 
        private actRoute: ActivatedRoute,
        public dialog: MatDialog,
        private _router: Router,
        private router:ActivatedRoute,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService:CommonService
    ) { }

    ngOnInit() {                
        this.addPermission = this.commonService.getPermission("Add");
        this.editPermission = this.commonService.getPermission("Edit");
        this.deletePermission = this.commonService.getPermission("Delete");     
    
        this.rolePermission = this.commonService.rolePermission();
        this.spinnerService.show();        
        this.getAllUsersData();
    }
    processEditAction(id: number) {          
        this._userMenuService.getUsersListById(id)
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
            this._userMenuService.deleteUserList(id)
            .subscribe((data) => {
                if(data){
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
        this._userMenuService.getUsersList().subscribe((data) => {
            this.data = data;                    
           // console.log(JSON.stringify(data))
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
    // ngOnDestroy() {
    //     this.paramSubscription.unsubscribe();
    // }
    }
