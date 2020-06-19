import { OnInit, Component, ViewChild } from '@angular/core';
import { FootPatrollingInspectionService } from 'src/app/services/foot-patrolling-inspection.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { FootPatrollingInspectionModel } from 'src/app/models/foot-patrolling-inspection.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDatepickerInputEvent } from '@angular/material';
import { ReportService  } from "src/app/services/report.service";

@Component({
    selector: 'foot-patrolling-inspection',
    templateUrl: './foot-patrolling-inspection.component.html',
    styleUrls: ['./foot-patrolling-inspection.component.scss']
})
export class FootPatrollingInspectionComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addFPInspectionItem: boolean ;
    title: string = "Save";
    fpInspectionItemFormGroup: FormGroup;
    fpInspectionList : any;
    toMinDate=new Date();
    facilityData:any;
    fpInspectionItemDataSource: MatTableDataSource<FootPatrollingInspectionModel>;
    fpInspectionItemDisplayColumns = ['sno' ,'facilityId','inspectionType' , 'section' , 'inspectionBy' , 'startTime' , 'stopTime' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editfpInspectionItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private footPatrollingInspectionService: FootPatrollingInspectionService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private reportService: ReportService,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        this.getAllFootPatrollingInspectionData();
        this.depotTypeForOhe();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","FP Inspection") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.fpInspectionItemFormGroup = this.formBuilder.group({
            id: 0,
            'facilityId':[null],
            'inspectionType':[null],
            'section': [null],
            'inspectionBy': [null],
            'startTime': [null],
            'stopTime' : [null]
        });
        
    }
    
      public get f() { return this.fpInspectionItemFormGroup.controls; }

    addEvent(event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = event.value;
      }
    getAllFootPatrollingInspectionData() {
        const footPatrollingInspection : FootPatrollingInspectionModel[] = [];
        this.footPatrollingInspectionService.getAllFPInspectionItems().subscribe((data) => {
            this.fpInspectionList = data;
            for (let i = 0; i < this.fpInspectionList.length; i++) {
                this.fpInspectionList[i].sno = i+1;
                footPatrollingInspection.push(this.fpInspectionList[i]);              
            }
            this.fpInspectionItemDataSource = new MatTableDataSource(footPatrollingInspection);
            this.fpInspectionItemDataSource.paginator = this.paginator;
            this.fpInspectionItemDataSource.sort = this.sort;

        } , error => {});

    }

    fpInspectionItemSubmit () {
        let facilityId: string = this.fpInspectionItemFormGroup.value.facilityId;
        let inspectionType: string = this.fpInspectionItemFormGroup.value.inspectionType;
        let section: string = this.fpInspectionItemFormGroup.value.section;
        let inspectionBy: string = this.fpInspectionItemFormGroup.value.inspectionBy;
        let startTime: Date = this.fpInspectionItemFormGroup.value.startTime;
        let stopTime: Date = this.fpInspectionItemFormGroup.value.stopTime
        this.addFPInspectionItem = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            this.footPatrollingInspectionService.saveFPInspection({
                'facilityId':facilityId,
                'inspectionType':inspectionType,
                'section': section,
                'inspectionBy': inspectionBy,
                'startTime':startTime,
                'stopTime': stopTime
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllFootPatrollingInspectionData();
                this.fpInspectionItemFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editfpInspectionItemResponse.id;
            this.footPatrollingInspectionService.updateFPInspectionItem({
                'id':id,
                'facilityId':facilityId,
                'inspectionType':inspectionType,
                'section': section,
                'inspectionBy':inspectionBy,
                'startTime': startTime,
                'stopTime' : stopTime
            }).subscribe((data) =>{
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllFootPatrollingInspectionData();
                this.fpInspectionItemFormGroup.reset();
                this.addFPInspectionItem =  false;
            } , error => {})
            
        }
    }

    editFPInspectionItem (id) {
        this.addFPInspectionItem = true;
        this.fpInspectionItemEditAction(id);
        this.title = 'Update';
    }

    fpInspectionItemEditAction(id: number) {
        this.footPatrollingInspectionService.findFPInspectionItemById(id).subscribe((responseData) => {
            this.editfpInspectionItemResponse = responseData;
            this.fpInspectionItemFormGroup.patchValue({
                id: this.editfpInspectionItemResponse.id,
                facilityId:this.editfpInspectionItemResponse.facilityId,
                inspectionType:this.editfpInspectionItemResponse.inspectionType,
                section: this.editfpInspectionItemResponse.section,
                inspectionBy: this.editfpInspectionItemResponse.inspectionBy,
                startTime: this.editfpInspectionItemResponse.startTime,
                stopTime: this.editfpInspectionItemResponse.stopTime
            })
        } ,error => {})
    }


    deleteFPInspectionItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected fpInspection item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.footPatrollingInspectionService.deleteFPInspectionItem(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('FP Inspection Deleted Successfully');
                        this.getAllFootPatrollingInspectionData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.fpInspectionItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.fpInspectionItemFormGroup.reset();
        this.addFPInspectionItem = false;
        this.title = 'Save';
    }
    depotTypeForOhe()
        {  
               this.reportService. depotTypeForOhe().subscribe((data) => {
                 this.facilityData = data;
                // console.log('facilityData '+JSON.stringify(data))
        }
               );

       }
    
    NewFPInspectionItem () {
        this.addFPInspectionItem = true;
    }

}