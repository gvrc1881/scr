import { OnInit, Component, ViewChild,ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatDialogRef } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { environment } from './../../../environments/environment';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { WPADailyProgressModel } from 'src/app/models/works.model';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { UserJurisdictionModel } from 'src/app/models/user-jurisdiction.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'user-jurisdiction',
    templateUrl: './user-jurisdiction.component.html',
    styleUrls: ['./user-jurisdiction.component.css']    
})

export class UserJurisdictionComponent implements OnInit {
        
    FiledLabels = FieldLabelsConstant.LABELS;
    addPermission: boolean;
    editPermission: boolean;
    deletePermission: boolean;
    dataSource: MatTableDataSource<UserJurisdictionModel>;
    displayedColumns = ['sno','user','project','group','section','id'];
    userJurisdictionList : any;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    @ViewChild('filter', { static: true }) filter: ElementRef;
    pagination=Constants.PAGINATION_NUMBERS;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    resp: any;
    

    constructor(
        public dialog: MatDialog,
        private formBuilder: FormBuilder,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService,
        private router: Router,
        private route: ActivatedRoute     
    ) {
    }
    
    ngOnInit() {
        var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","UserJurisdiction") ;//p == 0 ? 'No Permission' : p[0].permissionName;
    this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getUserJurisdictionData();
        
    }
    
    edit(id) {
        this.router.navigate(['edit-user-jurisdiction/' + id], { relativeTo: this.route });
    }
    
    getUserJurisdictionData() {
        const userJurisdiction: UserJurisdictionModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.USER_JURISDICTION.GET_USER_JURISDICTION)
            .subscribe((data) => {
                this.userJurisdictionList = data
                for (let i = 0; i < this.userJurisdictionList.length; i++) {
                    this.userJurisdictionList[i].sno = i + 1;
                    this.userJurisdictionList[i].section = !!this.userJurisdictionList[i].section ? this.userJurisdictionList[i].section['section'] : '';
                    userJurisdiction.push(this.userJurisdictionList[i]);
                  }
                  this.dataSource = new MatTableDataSource(userJurisdiction);
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
    
    delete(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected User Jurisdiction?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.PROJECT_ADMIN.USER_JURISDICTION.DELETE_USER_JURISDICTION, id).subscribe(data => {
                        this.resp = data;
                        if(this.resp.code == 200 && !!this.resp) {
                            this.commonService.showAlertMessage(this.resp.message);
                            this.getUserJurisdictionData();
                         } else {
                            this.commonService.showAlertMessage("User Jurisdiction Deletion Failed.");
                         }  
                    },error => {
                        console.log('ERROR >>>');
                        this.spinnerService.hide();
                        this.commonService.showAlertMessage("User Jurisdiction Deletion Failed.");
                    });
            }
        });
    }
    
    
    
}