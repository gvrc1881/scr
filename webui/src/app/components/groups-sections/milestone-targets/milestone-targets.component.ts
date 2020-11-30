import { Component, OnInit,ViewChild,Inject } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { MilestoneTargetsModel } from 'src/app/models/milestone-targets.model';
import { FuseConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from 'src/app/components/data-view-dialog/data-view-dialog.component';
import { Location } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS,MAT_DIALOG_DATA } from '@angular/material';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FuseConfirmPopupComponent } from 'src/app/components/confirm-popup/confirm-popup.component';
import { DatePipe } from '@angular/common';



@Component({  
    selector: 'app-milestone-targets',
    templateUrl: './milestone-targets.component.html',
    styleUrls: [],
    providers: [
        {
            provide: DateAdapter, useClass: AppDateAdapter
        },
        {
            provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
        }
        ]
})
export class MilestoneTargetsComponent implements OnInit {

    pagination = Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;    
	title: string =  Constants.EVENTS.ADD;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    id: number = 0;
    milestoneTargetsFormGroup: FormGroup;
    targetsList : any;  
    targetTypeList:any;
    statusList:any;
    groupList:any;
    resp:any;
    workId:any;
    workGroupId:any;
    project:any;
    valid:boolean;
    targetsDataSource: MatTableDataSource<MilestoneTargetsModel>;
    targetsDisplayColumns = ['sno' ,'workId','workGroupId','targetType','rkm' , 'tkm' ,'targetDate','status','remarks', 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    
    editTargetsResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

    constructor( 
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private datePipe: DatePipe,
        private sendAndRequestService:SendAndRequestService,
        private location: Location,
        @Inject(MAT_DIALOG_DATA) public data:any,
        private spinnerService: Ng4LoadingSpinnerService,
        public dialogRef: MatDialogRef<MilestoneTargetsComponent>,
    ){
        
    }
    
    ngOnInit () {
      this.getAllMilestoneTargetsData();
      this.findTargetTypes();
      this.findStatusTypes();
      this.findgroups();
      var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","Milestone Targets") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);  
        this.milestoneTargetsFormGroup = this.formBuilder.group({
            id: 0,
            'workId':[null],
            'workGroupId':[null],
            'targetType':[null],
            'rkm':[null],
            'tkm':[null],
            'targetDate': [null],
            'status':[null],
            'remarks':[null]
            
        });  
        this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.
            GET_GROUPS_SECTIONS_BY_ID+this.data).subscribe((data) => {
            this.resp = data;
            this.workId=this.resp.workName,
            this.workGroupId=this.resp.workGroup+'-'+this.resp.section;
           
            },  error => {
               
            });
           
    }
      
      
      public get f() { return this.milestoneTargetsFormGroup.controls; }

    getAllMilestoneTargetsData() {
        const targets : MilestoneTargetsModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.MILESTONE_TARGETS.GET_MILESTONE_TARGETS)
            .subscribe((data) => {
            this.targetsList = data;
            for (let i = 0; i < this.targetsList.length; i++) {
                this.targetsList[i].sno = i+1;
                this.targetsList[i].targetDate = this.datePipe.transform(this.targetsList[i].targetDate, 'dd-MM-yyyy ');

                targets.push(this.targetsList[i]);              
            }
            this.targetsDataSource = new MatTableDataSource(targets);
            this.targetsDataSource.paginator = this.paginator;
            this.targetsDataSource.sort = this.sort;

        } , error => {});

       

    }

    findgroups(){

        this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.GET_GROUPS_SECTIONS).subscribe((data) => {
            this.groupList = data;
            },  error => {
               this.commonService.showAlertMessage("Error in Get")
            });

    }

    findTargetTypes(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'MILESTONE_TARGET_TYPES').subscribe((data) => {
            this.targetTypeList = data;
            },  error => {
               this.commonService.showAlertMessage("Error in Get")
            });
    }


    findStatusTypes(){
        this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.DRIVE_CHECK_LIST.GET_STATUS_ITEM +'MILESTONE_TARGET_STATUS_TYPE').subscribe((data) => {
            this.statusList = data;
            },  error => {
               this.commonService.showAlertMessage("Error in Get")
            });
    }

    getTkm(){        
        if(this.milestoneTargetsFormGroup.value.rkm > this.milestoneTargetsFormGroup.value.tkm)
        {
            // this.confirmDialogRef = this.dialog.open(FuseConfirmPopupComponent, {
            //     disableClose: false
            //   });
            //   this.confirmDialogRef.componentInstance.confirmMessage = "TKM must be less than RKM";
            this.valid=false;
        }
    }

    targetsSubmit () {

        let workId=this.resp.workId;
        let workGroupId = this.resp;
        let targetType: string = this.milestoneTargetsFormGroup.value.targetType;
        let rkm:string=this.milestoneTargetsFormGroup.value.rkm;
        let tkm:string=this.milestoneTargetsFormGroup.value.tkm;
        let targetDate:string=this.milestoneTargetsFormGroup.value.targetDate;
        let status:string=this.milestoneTargetsFormGroup.value.status;
        let remarks:string=this.milestoneTargetsFormGroup.value.remarks;
        
        
        
        if (this.title ==  Constants.EVENTS.ADD) {
                var saveTargets={
                    'workId':workId,
                    'workGroupId':workGroupId,
                    'targetType':targetType,
                    'rkm':rkm,
                    'tkm':tkm,
                    'targetDate':targetDate,
                    'status':status,
                    'remarks':remarks,
                    "createdBy": this.loggedUserData.username,
                     "createdOn": new Date()
                }
                  
                this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.MILESTONE_TARGETS.SAVE, saveTargets, false).subscribe(response => {
                  this.commonService.showAlertMessage('Successfully saved');
                this.getAllMilestoneTargetsData();
                this.milestoneTargetsFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editTargetsResponse.id;
            var updateTargets={
                'id':id,
                'workId':workId,
                'workGroupId':workGroupId,
                'targetType':targetType,
                'rkm':rkm,
                'tkm':tkm,
                'targetDate':targetDate,
                'status':status,
                'remarks':remarks,
                "updatedBy": this.loggedUserData.username,
                     "updatedOn": new Date()
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.PROJECT_ADMIN.MILESTONE_TARGETS.UPDATE, updateTargets, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllMilestoneTargetsData();
                this.milestoneTargetsFormGroup.reset();
            } , error => {})
            
        }
    }

    editTargets (id) {
        this.targetsEditAction(id);
        this.title = Constants.EVENTS.UPDATE;
    }

    targetsEditAction(id: number) {
        this.milestoneTargetsFormGroup = this.formBuilder.group({
            id: 0,
            'workId':[null],
            'workGroupId':[null],
            'targetType':[null],
            'rkm':[null],
            'tkm':[null],
            'targetDate': [null],
            'status':[null],
            'remarks':[null]
        });
        this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.MILESTONE_TARGETS.GET_MILESTONE_TARGETS_BY_ID+id).subscribe((responseData) => {
            this.editTargetsResponse = responseData;
            this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_ID+this.editTargetsResponse.workId.id).subscribe((response) => {
                this.project = response;
                   this.milestoneTargetsFormGroup.patchValue({ workId: this.project.workName })       	
     });
            this.milestoneTargetsFormGroup.patchValue({ workGroupId: this.resp.workGroup+'-'+this.resp.section }),
            //this.milestoneTargetsFormGroup.patchValue({ workId: this.resp.workName})  
            this.milestoneTargetsFormGroup.patchValue({
                id: this.editTargetsResponse.id,
               // workId:this.resp.workId,
                //workGroupId:this.resp,
                targetType:this.editTargetsResponse.targetType,
                rkm:this.editTargetsResponse.rkm,
                tkm:this.editTargetsResponse.tkm,
                targetDate:this.editTargetsResponse.targetDate,
                status:this.editTargetsResponse.status,
                remarks:this.editTargetsResponse.remarks,
               
            });
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = Constants.EVENTS.UPDATE;
          } else {
            this.title = Constants.EVENTS.ADD;      
          }
    }


   
    deleteTargets(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
          disableClose: false
        });
        this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the Target?';
        this.confirmDialogRef.afterClosed().subscribe(result => {
          if (result) {
            this.spinnerService.show();
            this.sendAndRequestService.requestForDELETE(Constants.app_urls.PROJECT_ADMIN.MILESTONE_TARGETS.DELETE, id).subscribe(data => {
              this.spinnerService.hide();
              this.commonService.showAlertMessage("Deleted Targets Successfully");
              this.getAllMilestoneTargetsData();
            }, error => {
              console.log('ERROR >>>');
              this.spinnerService.hide();
              this.commonService.showAlertMessage("Targets Deletion Failed.");
            })
          }
          this.confirmDialogRef = null;
        });
      }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.targetsDataSource.filter = filterValue;
    }

    
   

}