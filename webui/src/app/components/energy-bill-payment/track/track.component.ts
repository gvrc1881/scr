import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { TrackModel } from 'src/app/models/track.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { TrackPayload } from 'src/app/payloads/track.payload';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';



@Component({
    selector: 'track',
    templateUrl: './track.component.html',
    styleUrls: ['./track.component.css']
})
export class TrackComponent implements OnInit{

    pagination = Constants.PAGINATION_NUMBERS;
    FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
	addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    trackFormGroup: FormGroup;
    addTrack: boolean = false;
    title: string = Constants.EVENTS.ADD;
    trackList: any;
    editTrackResponse: any;
    facilityData:any;
    trackDataSource: MatTableDataSource<TrackModel>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    trackDisplayedColumns = ['sno' ,  'depot'  , 'TKM', 'electrifiedTKM' , 'RKM' ,'electrifiedRKM', 'remarks' , 'id' ];
    funLocTypeData: any;
    trackResponse: any;
    enableDepotType: boolean;
    enableDepots: boolean;
    
    constructor(
        private commonService: CommonService,
        private dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private formBuilder: FormBuilder,
        private sendAndRequestService:SendAndRequestService

    ){
    }
    
	ngOnInit() {
		var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","TRACK") ;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.getTrackData();
        this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FUNCTIONAL_LOCATION_TYPES).subscribe((data) => {
                 this.funLocTypeData = data;
      		});
    }
        
    addNewTrack() {
        this.addTrack = true;
        this.enableDepotType = true;
    	this.enableDepots = true;
        this.trackFormGroup = this.formBuilder.group({
             id: 0,
             "depotType":[null],
            "facilityId": [null, Validators.required , this.duplicateDepot.bind(this)],
            "tkm": [null, Validators.compose([Validators.required])],
            "rkm":[null, Validators.compose([Validators.required])],
            "remark": [null, Validators.maxLength(250)],
            "electrifiedRkm": [null, Validators.compose([Validators.required])],
            "electrifiedTkm": [null, Validators.compose([Validators.required])],
        });
    }
    
    duplicateDepot() {
        const q = new Promise((resolve, reject) => {
           this.sendAndRequestService.requestForGET(
                    Constants.app_urls.ENERGY_BILL_PAYMENTS.TRACK.EXISTS_DEPOT +
                this.trackFormGroup.controls['facilityId'].value.facilityId
          ).subscribe((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateDepot': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateDepot': true }); });
        });
        return q;
    }
    
    onTrackSubmit() {
    	TrackPayload.ADD_PAYLOAD.facilityId = this.trackFormGroup.value.facilityId;
        TrackPayload.ADD_PAYLOAD.tkm = this.trackFormGroup.value.tkm;
        TrackPayload.ADD_PAYLOAD.rkm = this.trackFormGroup.value.rkm;
        TrackPayload.ADD_PAYLOAD.remark = this.trackFormGroup.value.remark;
        TrackPayload.ADD_PAYLOAD.createdBy = this.loggedUserData.username;
		TrackPayload.ADD_PAYLOAD.electrifiedRkm = this.trackFormGroup.value.electrifiedRkm;
        TrackPayload.ADD_PAYLOAD.electrifiedTkm = this.trackFormGroup.value.electrifiedTkm;
        if (this.title == Constants.EVENTS.ADD) {
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.TRACK.SAVE_TRACK,TrackPayload.ADD_PAYLOAD, false).subscribe((data) => {
                this.trackResponse = data;
                if(this.trackResponse.code == 200 && !!this.trackResponse) {
                	this.commonService.showAlertMessage(this.trackResponse.message);
                	this.getTrackData();
                	this.trackFormGroup.reset();
                	this.addTrack = false;
                }else {
                	this.commonService.showAlertMessage("Track Data Saving Failed.");
                }	
            }, error => {
                console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Track Data Saving Failed.");
            })
        }else if(this.title == Constants.EVENTS.UPDATE){
            TrackPayload.UPDATE_PAYLOAD.id = this.editTrackResponse.id;
            TrackPayload.UPDATE_PAYLOAD.facilityId = this.editTrackResponse.facilityId;
	        TrackPayload.UPDATE_PAYLOAD.tkm = this.trackFormGroup.value.tkm;
	        TrackPayload.UPDATE_PAYLOAD.rkm = this.trackFormGroup.value.rkm;
	        TrackPayload.UPDATE_PAYLOAD.remark = this.trackFormGroup.value.remark;
	        TrackPayload.UPDATE_PAYLOAD.updatedBy  = this.loggedUserData.username;
			TrackPayload.UPDATE_PAYLOAD.electrifiedRkm = this.trackFormGroup.value.electrifiedRkm;
        	TrackPayload.UPDATE_PAYLOAD.electrifiedTkm = this.trackFormGroup.value.electrifiedTkm;
            TrackPayload.UPDATE_PAYLOAD.createdBy = this.editTrackResponse.createdBy;
            TrackPayload.UPDATE_PAYLOAD.createdOn = this.editTrackResponse.createdOn;
            TrackPayload.UPDATE_PAYLOAD.updatedOn = this.editTrackResponse.updatedOn;
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.TRACK.UPDATE_TRACK,TrackPayload.UPDATE_PAYLOAD, false).subscribe((data) => {
                this.trackResponse = data;
                if(this.trackResponse.code == 200 && !!this.trackResponse) {
                	this.commonService.showAlertMessage(this.trackResponse.message);
                	this.getTrackData();
                	this.trackFormGroup.reset();
                	this.addTrack = false;
                	this.title = Constants.EVENTS.ADD;
                }else {
                	this.commonService.showAlertMessage("Track Data Updating Failed.");
                }	
            } , error => {
                console.log('ERROR >>>');
        		this.spinnerService.hide();
        		this.commonService.showAlertMessage("Track Data Updating Failed.");
            });

        }
    }
    
    onGoBack() {
        this.trackFormGroup.reset();
        this.addTrack = false;
        this.title = Constants.EVENTS.ADD;
    }
    
    
    getTrackData() {
        const track : TrackModel [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TRACK.GET_TRACK).subscribe((data) => {
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
        this.title = Constants.EVENTS.UPDATE;
        /*this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES).subscribe((data) => {
                 this.facilityData = data;
        		});*/
        this.spinnerService.hide();
        this.enableDepots = false;
        this.enableDepotType = false;
        this.trackFormGroup = this.formBuilder.group({
             id: 0,
             "depotType":[null],
            "facilityId": [null, Validators.required ],
            "tkm": [null, Validators.compose([Validators.required])],
            "rkm":[null, Validators.compose([Validators.required])],
            "remark": [null, Validators.maxLength(250)],
            "electrifiedRkm": [null, Validators.compose([Validators.required])],
            "electrifiedTkm": [null, Validators.compose([Validators.required])],
        });
    }   
     
    trackEditAction(id: number) {
    	this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.TRACK.GET_TRACK_ID+id)
            .subscribe((responseData) => {
                this.editTrackResponse = responseData;
                this.trackFormGroup.patchValue({
                    id: this.editTrackResponse.id,
                    facilityId: this.editTrackResponse.facilityId.facilityName,
                    tkm: this.editTrackResponse.tkm,
                    rkm: this.editTrackResponse.rkm,
                    remark: this.editTrackResponse.remark,
                    electrifiedTkm: this.editTrackResponse.electrifiedTkm, 
                    electrifiedRkm: this.editTrackResponse.electrifiedRkm
                });
            } , error => {});
    } 
    
    
    deleteTrack (id) {
        this.addTrack = false;
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected track ?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.TRACK.DELETE_TRACK, id)
                    .subscribe((data) => {
                    	this.trackResponse = data;
                		if(this.trackResponse.code == 200 && !!this.trackResponse) {
                        	this.commonService.showAlertMessage('Track Deleted Successfully');
                        	this.getTrackData();
                         } else {
                         	this.commonService.showAlertMessage("Track Deletion Failed.");
                    	}	
                    },error => {
                    	console.log('ERROR >>>');
          				this.spinnerService.hide();
          				this.commonService.showAlertMessage("Track Deletion Failed.");	
                    });
            }
        });
    }
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.trackDataSource.filter = filterValue;
    }
    getFacilitys(){
    	var depotType = this.trackFormGroup.value.depotType ;
    	this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_DEPOTTYPE + depotType.code).subscribe((data) => {
                 this.facilityData = data;
        		});
    }
}
