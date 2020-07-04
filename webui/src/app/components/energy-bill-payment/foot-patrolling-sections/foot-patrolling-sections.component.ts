import { OnInit, Component, ViewChild } from '@angular/core';
import { FootPatrollingSectionsService } from 'src/app/services/foot-patrolling-sections.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { FootPatrollingSectionsModel } from 'src/app/models/foot-patrolling-sections.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService  } from "src/app/services/report.service";
import { FacilityModel } from 'src/app/models/facility.model';
import { MatDatepickerInputEvent } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
    selector: 'foot-patrolling-sections',
    templateUrl: './foot-patrolling-sections.component.html',
    styleUrls: ['./foot-patrolling-sections.component.scss']
})
export class FootPatrollingSectionsComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addFPSectionsItem: boolean ;
    title: string = "Save";
    fpSectionsItemFormGroup: FormGroup;
    fpSectionsList : any;
    toMinDate=new Date();
    currentDate = new Date();
    facilityData:any;
    fpSectionsItemDataSource: MatTableDataSource<FootPatrollingSectionsModel>;
    fpSectionsItemDisplayColumns = ['sno' ,'facilityDepot','fpSection' , 'fromLocation' , 'toLocation' , 'fromDate' , 'toDate' , 'remarks' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editfpSectionsItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        //private footPatrollingSectionsService: FootPatrollingSectionsService,
        private reportService: ReportService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private sendAndRequestService:SendAndRequestService
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllFootPatrollingSectionsData();
        this.depotTypeForOhe();
        var permissionName = this.commonService.getPermissionNameByLoggedData("FP","FP Sections") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.fpSectionsItemFormGroup = this.formBuilder.group({
            id: 0,
            'facilityDepot':[null],
            'fpSection':[null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicatefpSection.bind(this)],
            'fromLocation': [null],
            'toLocation': [null],
            'fromDate': [null],
            'toDate' : [null],
            'remarks' : [null,Validators.maxLength(250)]
        });
        
    }
      duplicatefpSection() {
        const q = new Promise((resolve, reject) => {
          let fpSection: string = this.fpSectionsItemFormGroup.controls['fpSection'].value;
          var filter = !!this.fpSectionsList && this.fpSectionsList.filter(fpSections => {
            return fpSections.fpSection.toLowerCase() == fpSection.trim().toLowerCase();
          });
          if (filter.length > 0) {
            resolve({ 'duplicatefpSection': true });
          }
          this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.EXIST_FP_SECTIONS +
            this.fpSectionsItemFormGroup.controls['fpSection'].value
          ).subscribe((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicatefpSection': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicatefpSection': true }); });
        });
        return q;
      }
      public get f() { return this.fpSectionsItemFormGroup.controls; }

      addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = new Date(event.value);
    }
    getAllFootPatrollingSectionsData() {
        const footPatrollingSections : FootPatrollingSectionsModel[] = [];
            this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.GET_FP_SECTIONS).subscribe((data) => {
            this.fpSectionsList = data;
            for (let i = 0; i < this.fpSectionsList.length; i++) {
                this.fpSectionsList[i].sno = i+1;
                footPatrollingSections.push(this.fpSectionsList[i]);              
            }
            this.fpSectionsItemDataSource = new MatTableDataSource(footPatrollingSections);
            this.fpSectionsItemDataSource.paginator = this.paginator;
            this.fpSectionsItemDataSource.sort = this.sort;

        } , error => {});

    }

    fpSectionsItemSubmit () {
        let facilityDepot: string = this.fpSectionsItemFormGroup.value.facilityDepot;
        let fpSection: string = this.fpSectionsItemFormGroup.value.fpSection;
        let fromLocation: string = this.fpSectionsItemFormGroup.value.fromLocation;
        let toLocation: string = this.fpSectionsItemFormGroup.value.toLocation;
        let fromDate: Date = this.fpSectionsItemFormGroup.value.fromDate;
        let toDate: Date = this.fpSectionsItemFormGroup.value.toDate;
        let remarks: string = this.fpSectionsItemFormGroup.value.remarks;
        this.addFPSectionsItem = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
                var saveFpSectCionsModel ={
                    'facilityDepot':facilityDepot,
                    'fpSection':fpSection,
                    'fromLocation': fromLocation,
                    'toLocation': toLocation,
                    'fromDate':fromDate,
                    'toDate': toDate,
                    'remarks' : remarks
                } 
                this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.SAVE_FP_SECTIONS, saveFpSectCionsModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllFootPatrollingSectionsData();
                this.fpSectionsItemFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editfpSectionsItemResponse.id;
            var updateFpSectCionsModel={
                'id':id,
                'facilityDepot':facilityDepot,
                'fpSection':fpSection,
                'fromLocation': fromLocation,
                'toLocation':toLocation,
                'fromDate': fromDate,
                'toDate' : toDate,
                'remarks': remarks
            }
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.UPDATE_FP_SECTIONS, updateFpSectCionsModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllFootPatrollingSectionsData();
                this.fpSectionsItemFormGroup.reset();
                this.addFPSectionsItem =  false;
            } , error => {})
            
        }
    }

    editFPSectionsItem (id) {
        this.addFPSectionsItem = true;
        this.fpSectionsItemEditAction(id);
        this.title = 'Update';
    }

    fpSectionsItemEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.GET_FP_SECTIONS_ID+'/'+id).subscribe((responseData) => {
            this.editfpSectionsItemResponse = responseData;
            this.fpSectionsItemFormGroup.patchValue({
                id: this.editfpSectionsItemResponse.id,
                facilityDepot:this.editfpSectionsItemResponse.facilityDepot,
                fpSection:this.editfpSectionsItemResponse.fpSection,
                fromLocation: this.editfpSectionsItemResponse.fromLocation,
                toLocation: this.editfpSectionsItemResponse.toLocation,
                fromDate: this.editfpSectionsItemResponse.fromDate,
                toDate: !!this.editfpSectionsItemResponse.toDate ? new Date(this.editfpSectionsItemResponse.toDate) : '',
                remarks: this.editfpSectionsItemResponse.remarks
            });
            this.toMinDate = new Date(this.editfpSectionsItemResponse.fromDate);

        } ,error => {})
    }


    deleteFPSectionsItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected fpSections item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.FP_SECTIONS.DELETE_FP_SECTIONS, id).subscribe(response => {
                        this.commonService.showAlertMessage('FP Sections Deleted Successfully');
                        this.getAllFootPatrollingSectionsData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.fpSectionsItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.fpSectionsItemFormGroup.reset();
        this.addFPSectionsItem = false;
        this.title = 'Save';
    }
    depotTypeForOhe()
        {  
               this.reportService. depotTypeForOhe().subscribe((data) => {
                 this.facilityData = data;
        }
               );

       }

    NewFPSectionsItem () {
        this.addFPSectionsItem = true;
    }

}