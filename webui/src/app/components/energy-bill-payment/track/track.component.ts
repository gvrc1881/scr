import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TrackService } from 'src/app/services/track.service';
import { ReportService } from 'src/app/services/report.service';
import { TrackModel } from 'src/app/models/track.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { TrackPayload } from 'src/app/payloads/track.payload';
import { FacilityModel } from 'src/app/models/facility.model';

@Component({
    selector: 'track',
    templateUrl: './track.component.html',
    styleUrls: ['./track.component.scss']
})
export class TrackComponent implements OnInit{
	
	addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    trackFormGroup: FormGroup;
    addTrack: boolean = false;
    title: string = "Save";
    trackList: any;
    editTrackResponse: any;
    facilityData:FacilityModel;
    trackDataSource: MatTableDataSource<TrackModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    trackDisplayedColumns = ['sno' ,  'depot'  , 'TKM' , 'RKM' , 'remarks' , 'id' ];
    funLocTypeData: any;
    
    constructor(
        private commonService: CommonService,
        private dialog: MatDialog,
        private trackService: TrackService,
        private reportService: ReportService,
        private spinnerService: Ng4LoadingSpinnerService,
        private formBuilder: FormBuilder
    ){
    }
    
	ngOnInit() {
		var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","TRACK") ;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getTrackData();
       // this.facilityNames();
        this.trackFormGroup = this.formBuilder.group({
             id: 0,
             "depotType":[null],
            "facilityId": [null, Validators.compose([Validators.required])],
            "tkm": [null],
            "rkm":[null],
            "remark": [null],
        });
        this.reportService.functionalLocationTypes().subscribe((data) => {
                 this.funLocTypeData = data;
      		});
    }
        
    addNewTrack() {
        this.addTrack = true;
    }
    
    onTrackSubmit() {
    	TrackPayload.ADD_PAYLOAD.facilityId = this.trackFormGroup.value.facilityId;
        TrackPayload.ADD_PAYLOAD.tkm = this.trackFormGroup.value.tkm;
        TrackPayload.ADD_PAYLOAD.rkm = this.trackFormGroup.value.rkm;
        TrackPayload.ADD_PAYLOAD.remark = this.trackFormGroup.value.remark;
        TrackPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.id;
        //console.log('json object::'+JSON.stringify(TrackPayload.ADD_PAYLOAD));
        if (this.title == Constants.EVENTS.SAVE) {
            this.trackService.saveTrack(TrackPayload.ADD_PAYLOAD).subscribe((data) => {
                //this.data = data;
                this.commonService.showAlertMessage("Successfully saved");
                this.getTrackData();
                this.trackFormGroup.reset();
                this.addTrack = false;
            }, error => {
                this.commonService.showAlertMessage("Error in Add")
            })
        }else if(this.title == Constants.EVENTS.UPDATE){
            console.log('in else if block::');
            TrackPayload.UPDATE_PAYLOAD.id = this.editTrackResponse.id;
            TrackPayload.UPDATE_PAYLOAD.facilityId = this.trackFormGroup.value.facilityId;
	        TrackPayload.UPDATE_PAYLOAD.tkm = this.trackFormGroup.value.tkm;
	        TrackPayload.UPDATE_PAYLOAD.rkm = this.trackFormGroup.value.rkm;
	        TrackPayload.UPDATE_PAYLOAD.remarks = this.trackFormGroup.value.remarks;
	        TrackPayload.UPDATE_PAYLOAD.updatedBy = this.loggedUserData.id;
            this.trackService.updateTrack(TrackPayload.UPDATE_PAYLOAD).subscribe((data) => {
                //this.data = data;
                this.commonService.showAlertMessage("Successfully updated");
                this.getTrackData();
                this.trackFormGroup.reset();
                this.addTrack = false;
            } , error => {
                this.commonService.showAlertMessage("Error in update")
            });

        }
    }
    
    onGoBack() {
        this.trackFormGroup.reset();
        this.addTrack = false;
        this.title = 'Save';
    }
    
    
    getTrackData() {
        const track : TrackModel [] = [];
        this.trackService.findAllTrack().subscribe((data) => {
            this.trackList = data;
            
            for (let i = 0; i < this.trackList.length; i++) {
                this.trackList[i].sno = i+1;
                this.trackList[i].depot = this.trackList[i].facilityId.facilityName;
               track.push(this.trackList[i]);
            }
            this.trackDataSource = new MatTableDataSource(track);
            this.trackDataSource.paginator = this.paginator;
            this.trackDataSource.sort = this.sort; 
        }, error => {
           this.spinnerService.hide();     
        }); 
    }
    
    editTrack(id) {
        this.spinnerService.show();
        this.addTrack = true;
        this.trackEditAction(id);
        this.title = "Update";
        this.spinnerService.hide();
    }   
     
    trackEditAction(id: number) {
    	 this.trackService.findTrackById(id)
            .subscribe((responseData) => {
                this.editTrackResponse = responseData;
                 console.log('responseData'+JSON.stringify(responseData));
                this.trackFormGroup.patchValue({
                    id: this.editTrackResponse.id,
                    facilityId: this.editTrackResponse.facilityId,
                    tkm: this.editTrackResponse.tkm,
                    rkm: this.editTrackResponse.rkm,
                    remark: this.editTrackResponse.remark,
                });
            } , error => {});
    } 
    
    
    deleteTrack (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected track ?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.trackService.deleteTrackById(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Track Deleted Successfully');
                        this.getTrackData();
                    },error => {});
            }
        });
    }
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.trackDataSource.filter = filterValue;
    }
    /*
    facilityNames() {
               this.reportService.facilityNames().subscribe((data) => {
                 this.facilityData = data;
        		});
    } */
    
    getFacilitys(){
    	var depotType = this.trackFormGroup.value.depotType ;
    	this.reportService.getFacilitysBasedOnDepotType(depotType.code).subscribe((data) => {
                 this.facilityData = data;
        		});
    }
}
