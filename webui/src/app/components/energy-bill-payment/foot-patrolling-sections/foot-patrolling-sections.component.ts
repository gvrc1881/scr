import { OnInit, Component, ViewChild } from '@angular/core';
import { FootPatrollingSectionsService } from 'src/app/services/foot-patrolling-sections.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { FootPatrollingSectionsModel } from 'src/app/models/foot-patrolling-sections.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService  } from "src/app/services/report.service";
import { FacilityModel } from 'src/app/models/facility.model';

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
    facilityData:any;
    fpSectionsItemDataSource: MatTableDataSource<FootPatrollingSectionsModel>;
    fpSectionsItemDisplayColumns = ['sno' ,'facilityDepot','fpSection' , 'fromLocation' , 'toLocation' , 'fromDate' , 'toDate' , 'remarks' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editfpSectionsItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private footPatrollingSectionsService: FootPatrollingSectionsService,
        private reportService: ReportService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllFootPatrollingSectionsData();
        this.facilityNames();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","FOOT PATROLLING SECTIONS") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.fpSectionsItemFormGroup = this.formBuilder.group({
            id: 0,
            'facilityDepot':[null],
            'fpSection':[null],
            'fromLocation': [null],
            'toLocation': [null],
            'fromDate': [null],
            'toDate' : [null],
            'remarks' : [null]
        });
    }

    getAllFootPatrollingSectionsData() {
        const footPatrollingSections : FootPatrollingSectionsModel[] = [];
        this.footPatrollingSectionsService.getAllFPSectionsItems().subscribe((data) => {
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
            this.footPatrollingSectionsService.saveFPSections({
                'facilityDepot':facilityDepot,
                'fpSection':fpSection,
                'fromLocation': fromLocation,
                'toLocation': toLocation,
                'fromDate':fromDate,
                'toDate': toDate,
                'remarks' : remarks
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllFootPatrollingSectionsData();
                this.fpSectionsItemFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editfpSectionsItemResponse.id;
            this.footPatrollingSectionsService.updateFPSectionsItem({
                'id':id,
                'facilityDepot':facilityDepot,
                'fpSection':fpSection,
                'fromLocation': fromLocation,
                'toLocation':toLocation,
                'fromDate': fromDate,
                'toDate' : toDate,
                'remarks': remarks
            }).subscribe((data) =>{
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
        this.footPatrollingSectionsService.findFPSectionsItemById(id).subscribe((responseData) => {
            this.editfpSectionsItemResponse = responseData;
            this.fpSectionsItemFormGroup.patchValue({
                id: this.editfpSectionsItemResponse.id,
                facilityDepot:this.editfpSectionsItemResponse.facilityDepot,
                fpSection:this.editfpSectionsItemResponse.fpSection,
                fromLocation: this.editfpSectionsItemResponse.fromLocation,
                toLocation: this.editfpSectionsItemResponse.toLocation,
                fromDate: this.editfpSectionsItemResponse.fromDate,
                toDate: this.editfpSectionsItemResponse.toDate,
                remarks: this.editfpSectionsItemResponse.remarks
            })
        } ,error => {})
    }


    deleteFPSectionsItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected fpSections item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.footPatrollingSectionsService.deleteFPSectionsItem(id)
                    .subscribe((data) => {
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
    facilityNames()
        {
               const facilityData : FacilityModel[] = [];
               this.reportService. facilityNames().subscribe((data) => {
                 this.facilityData = data;
        }
               );

       }

    NewFPSectionsItem () {
        this.addFPSectionsItem = true;
    }

}