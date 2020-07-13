import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import{SidingsModel} from 'src/app/models/sidings.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { MatDatepickerInputEvent } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';

@Component({
    selector: 'app-sidings',
    templateUrl: './sidings.component.html',
    styleUrls: ['./sidings.component.scss']
})
export class SidingsComponent implements OnInit {
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addSidingsItem: boolean ;
    stationCodeData:any;
    title: string = "Save";
    sidingsItemList : any;
    toMinDate=new Date();
    today=new Date();
    sidingsItemDataSource: MatTableDataSource<SidingsModel>;
    sidingsItemDisplayColumns = ['sno' ,'zone','division','depot' ,'station' , 'sidingCode' , 'section' , 'sectionEletrifiedStatus' , 'sidingEletrifiedStatus' , 
    'privateRailway' ,'status',  'tkm','remarks','sidingProposed','proposedDate','approvalDate',
    'workOrderDate','workProgressPercentage','workProgressRemark','completionDate','id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    sidingsItemFormGroup: FormGroup;
    editsidingsItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    public status=['yes','No'];
    sidingEletrifiedStatus: any;
    onlyYes:boolean = true;
    selected: any;
    yes: any;
    onlyNo: boolean;
    divisionsList:any;
    zonesList:any;
    depotList:any;
    loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
    userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
    zoneList: FacilityModel [] = [];
    divisionList:  FacilityModel [] = [];
    subDivList:  FacilityModel [] = [];
    facilityList: FacilityModel [] = [];
	
    
    depotData: any;

   
    constructor(
        
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private sendAndRequestService:SendAndRequestService

    ) { }
 
    ngOnInit()  {
        this.getAllSidingsData();
      
        console.log("usernam-----e"+this.userHierarchy.length);
        var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","Sidings") ;
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.sidingsItemFormGroup = this.formBuilder.group({
            id: 0,
            'station':[null,Validators.maxLength(250)],
            'sidingCode': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateSidingCode.bind(this)],
            'section': [null,Validators.maxLength(250)],
            'sectionEletrifiedStatus': [null],
            'sidingEletrifiedStatus' : [null],
            'privateRailway' : [null,Validators.maxLength(250)],
            'status': [null,Validators.maxLength(250)],
            'tkm' : [null],
            'remarks' : [null, Validators.maxLength(250)],
            'sidingProposed' : [null],
            'proposedDate' : [null],
            'approvalDate' : [null],
            'workOrderDate' : [null],
            'workProgressPercentage' : [null],
            'workProgressRemark' : [null,Validators.maxLength(250)],
            'completionDate' : [null],
            'zone' : [null],
            'division':[null],
            'depot':[null],
            
        });

        this.displayHierarchyFields();
     }
      duplicateSidingCode() {
        const q = new Promise((resolve, reject) => {
          let siding: string = this.sidingsItemFormGroup.controls['sidingCode'].value;
          var filter = !!this.sidingsItemList && this.sidingsItemList.filter(sidings => {
            return sidings.sidingCode.toLowerCase() == siding.trim().toLowerCase();
          });
          if (filter.length > 0) {
            resolve({ 'duplicateSidingCode': true });
          }
          this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.EXISTS_SIDING_CODE +
            this.sidingsItemFormGroup.controls['sidingCode'].value
          ).subscribe((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateSidingCode': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateSidingCode': true }); });
        });
        return q;
      }
      addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
        this.toMinDate = new Date(event.value);
    }
     getAllSidingsData() {
        const sidingsDetails : SidingsModel[] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.GET_SIDINGS).subscribe((data) => {
            this.sidingsItemList = data;
            for (let i = 0; i < this.sidingsItemList.length; i++) {
                this.sidingsItemList[i].sno = i+1;
                sidingsDetails.push(this.sidingsItemList[i]);              
            }
            this.sidingsItemDataSource = new MatTableDataSource(sidingsDetails);
            this.sidingsItemDataSource.paginator = this.paginator;
            this.sidingsItemDataSource.sort = this.sort;

        } , error => {});
        
    }

     sidingsItemSubmit () {
        let station: string = this.sidingsItemFormGroup.value.station;
        let sidingCode: string = this.sidingsItemFormGroup.value.sidingCode;
        let section: string = this.sidingsItemFormGroup.value.section;
        let sectionEletrifiedStatus: string = this.sidingsItemFormGroup.value.sectionEletrifiedStatus;
        let sidingEletrifiedStatus: string = this.sidingsItemFormGroup.value.sidingEletrifiedStatus;
        let privateRailway: string = this.sidingsItemFormGroup.value.privateRailway;
        let status: string = this.sidingsItemFormGroup.value.status;
        let tkm: string = this.sidingsItemFormGroup.value.tkm;
        let remarks: string = this.sidingsItemFormGroup.value.remarks;
        let sidingProposed: string = this.sidingsItemFormGroup.value.sidingProposed;
        let proposedDate: Date = this.sidingsItemFormGroup.value.proposedDate;
        let approvalDate: Date = this.sidingsItemFormGroup.value.approvalDate;
        let workOrderDate: Date = this.sidingsItemFormGroup.value.workOrderDate;
        let workProgressPercentage: string = this.sidingsItemFormGroup.value.workProgressPercentage;
        let workProgressRemark: string = this.sidingsItemFormGroup.value.workProgressRemark;
        let completionDate: Date = this.sidingsItemFormGroup.value.completionDate;
        let zone: string = this.sidingsItemFormGroup.value.zone;
        let division: string = this.sidingsItemFormGroup.value.division;
        let depot: string = this.sidingsItemFormGroup.value.depot;
        this.addSidingsItem = false;
        if (this.title ==  Constants.EVENTS.SAVE) {
            var saveSidingsModel ={
                'station':station,
                'sidingCode': sidingCode,
                'section':section,
                'sectionEletrifiedStatus': sectionEletrifiedStatus,
                'sidingEletrifiedStatus' : sidingEletrifiedStatus,
                'privateRailway': privateRailway,
                'status' : status,
                'tkm' : tkm,
                'remarks' : remarks,
                'sidingProposed' : sidingProposed,
                'proposedDate' : proposedDate,
                'approvalDate' : approvalDate,
                'workOrderDate' : workOrderDate,
                'workProgressPercentage' : workProgressPercentage,
                'workProgressRemark' : workProgressRemark,
                'completionDate' : completionDate,
                "createdBy": this.loggedUserData.username,
                'zone':zone,
                'division':division,
                'depot':depot


            }              
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.SAVE_SIDINGS, saveSidingsModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllSidingsData();
                this.sidingsItemFormGroup.reset();
            } , error => {});
        }
        else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editsidingsItemResponse.id;
            var updateSidingsModel={
                'id':id,
                'station':station,
                'sidingCode': sidingCode,
                'section':section,
                'sectionEletrifiedStatus': sectionEletrifiedStatus,
                'sidingEletrifiedStatus' : sidingEletrifiedStatus,
                'privateRailway': privateRailway,
                'status' : status,
                'tkm' : tkm,
                'remarks' : remarks,
                'sidingProposed' : sidingProposed,
                'proposedDate' : proposedDate,
                'approvalDate' : approvalDate,
                'workOrderDate' : workOrderDate,
                'workProgressPercentage' : workProgressPercentage,
                'workProgressRemark' : workProgressRemark,
                'completionDate' : completionDate,
                "updatedBy": this.loggedUserData.username,
                'zone':zone,
                'division':division,
                'depot':depot
            }
                this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.UPDATE_SIDINGS, updateSidingsModel, false).subscribe(response => {
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllSidingsData();
                this.sidingsItemFormGroup.reset();
                this.addSidingsItem =  false;
            } , error => {})
            
        }
    }

    editSidingsItem (id) {
        this.addSidingsItem = true;
        this.sidingsItemEditAction(id);
        this.title = 'Update';
    }

    sidingsItemEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.GET_SIDINGS_ID+id).subscribe((responseData) => {
            this.editsidingsItemResponse = responseData;
            this.sidingsItemFormGroup.patchValue({
                id: this.editsidingsItemResponse.id,
                station:this.editsidingsItemResponse.station,
                sidingCode: this.editsidingsItemResponse.sidingCode,
                section: this.editsidingsItemResponse.section,
                sectionEletrifiedStatus: this.editsidingsItemResponse.sectionEletrifiedStatus,
                sidingEletrifiedStatus: this.editsidingsItemResponse.sidingEletrifiedStatus,
                privateRailway: this.editsidingsItemResponse.privateRailway,
                status: this.editsidingsItemResponse.status,
                tkm: this.editsidingsItemResponse.tkm,
                remarks: this.editsidingsItemResponse.remarks,
                sidingProposed: this.editsidingsItemResponse.sidingProposed,
                proposedDate: this.editsidingsItemResponse.proposedDate,
                approvalDate: !!this.editsidingsItemResponse.approvalDate ? new Date(this.editsidingsItemResponse.approvalDate) : '',
                workOrderDate: !!this.editsidingsItemResponse.workOrderDate ? new Date(this.editsidingsItemResponse.workOrderDate) : '',
                workProgressPercentage: this.editsidingsItemResponse.workProgressPercentage,
                workProgressRemark: this.editsidingsItemResponse.workProgressRemark,
                completionDate: this.editsidingsItemResponse.completionDate,
                zone: this.editsidingsItemResponse.zone,
                division: this.editsidingsItemResponse.division,
                depot: this.editsidingsItemResponse.depot,
            });
            this.toMinDate = new Date(this.editsidingsItemResponse.proposedDate);
        } ,error => {})
    }


    deleteSidingsItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected sidings item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.DELETE_SIDINGS, id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Sidings Item Deleted Successfully');
                        this.getAllSidingsData();
                    },error => {});
            }
        });
    }
        
    
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.sidingsItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.sidingsItemFormGroup.reset();
        this.addSidingsItem = false;
        this.title = 'Save';
    }

  

     NewSlidingsItem () {
        this.addSidingsItem = true;
    }
    statusChange() {
        if (this.sidingsItemFormGroup.value.sidingEletrifiedStatus == 'yes') {     
            this.onlyYes = false;
        } else {
            this.onlyYes = true;
        }
            }

  displayHierarchyFields(){
    this.zoneList = [];
    this.divisionList = [];
    this.facilityList = [];
    for (let i = 0; i < this.userHierarchy.length; i++) {
           if(this.userHierarchy[i].depotType == 'ZONE'){
               this.zoneList.push(this.userHierarchy[i]);
              
           }
        }
   
}



findDivisions(){
    let zone: string = this.sidingsItemFormGroup.value.zone;
    //this.divisionList=[];

    for (let i = 0; i < this.userHierarchy.length; i++) {
        
           if(this.userHierarchy[i].zone == zone && this.userHierarchy[i].depotType == 'DIV'){
           
               this.divisionList.push(this.userHierarchy[i]);
               
           }
        }
}



findDepots(){
    let division: string = this.sidingsItemFormGroup.value.division;
    //this.facilityList=[];
    for (let i = 0; i < this.userHierarchy.length; i++) {
       
           if(this.userHierarchy[i].division == division  &&(this.userHierarchy[i].depotType =='OHE') ){
            
               this.facilityList.push(this.userHierarchy[i]);
              
           }
        }
}
            
    }
