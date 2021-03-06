import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import{SidingsModel} from 'src/app/models/sidings.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog,DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { MatDatepickerInputEvent } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { FacilityModel } from 'src/app/models/facility.model';
import { DataViewDialogComponent } from '../../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
    selector: 'app-sidings',
    templateUrl: './sidings.component.html',
    styleUrls: ['./sidings.component.scss'],
    providers: [
      {
          provide: DateAdapter, useClass: AppDateAdapter
      },
      {
          provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
      }
      ]
})
export class SidingsComponent implements OnInit {
    
  pagination = Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
    Titles = FieldLabelsConstant.TITLE;
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addSidingsItem: boolean ;
    stationCodeData:any;
    id: number = 0;
    title: string = Constants.EVENTS.ADD;
    sidingsItemList : any;
    toMinDate=new Date();
    today=new Date();
    sidingsItemDataSource: MatTableDataSource<SidingsModel>;
    dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;

    sidingsItemDisplayColumns = ['sno' ,'station' , 'sidingCode' , 'section' , 'sectionEletrifiedStatus' , 'sidingEletrifiedStatus' , 
    'privateRailway' ,'sidingProposed','proposedDate','approvalDate',
    'workOrderDate','workProgressPercentage','completionDate','actions' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    sidingsItemFormGroup: FormGroup;
    editsidingsItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    public status=['yes','No'];
    sidingEletrifiedStatus: any;
    onlyYes:boolean = false;
    selected: any;
    yes: any;
    onlyNo: boolean;
    divisionsList:any;
    zonesList:any;
    depotList:any;
    loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
    userHierarchy:any = JSON.parse(sessionStorage.getItem('userHierarchy'));
    zoneList: FacilityModel [] = [];
    divisionList:  FacilityModel [] = [];
    subDivList:  FacilityModel [] = [];
    facilityList: FacilityModel [] = [];
	enableZone: boolean ;
	enableDivision: boolean;
	
	enableDepot: boolean;
    
    depotData: any;

   
    constructor(
        
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private datePipe: DatePipe,
        private sendAndRequestService:SendAndRequestService

    ) { }
 
    ngOnInit()  {
        this.getAllSidingsData();
      
       
        var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","Sidings") ;
        this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
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
      duplicateSidingCodeAndId() {
        let id=this.id;
        let sidingCode: string = this.sidingsItemFormGroup.controls['sidingCode'].value;         

        const q = new Promise((resolve, reject) => {          
  
           this.sendAndRequestService.requestForGET(
                  Constants.app_urls.ENERGY_BILL_PAYMENTS.SIDINGS.EXIST_SIDING_CODE_AND_ID+id+'/'+sidingCode).subscribe
                  ((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateSidingCodeAndId': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateSidingCodeAndId': true }); });
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
                this.sidingsItemList[i].proposedDate = this.datePipe.transform(this.sidingsItemList[i].proposedDate, 'dd-MM-yyyy');
                this.sidingsItemList[i].approvalDate = this.datePipe.transform(this.sidingsItemList[i].approvalDate, 'dd-MM-yyyy');
                this.sidingsItemList[i].workOrderDate = this.datePipe.transform(this.sidingsItemList[i].workOrderDate, 'dd-MM-yyyy');
                this.sidingsItemList[i].completionDate = this.datePipe.transform(this.sidingsItemList[i].completionDate, 'dd-MM-yyyy');
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
        if (this.title ==  Constants.EVENTS.ADD) {
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
                this.displayHierarchyFields();
            } , error => {})
            
        }
    }

    editSidingsItem (id) {
        this.addSidingsItem = true;
        this.sidingsItemEditAction(id);
        this.title = Constants.EVENTS.UPDATE;
        this.enableZone = false;
        this.enableDivision = false;
       this.enableDepot = false;
    }

    sidingsItemEditAction(id: number) {
        this.sidingsItemFormGroup = this.formBuilder.group({
            id: 0,
            'station':[null,Validators.maxLength(250)],
            'sidingCode': [null, Validators.compose([Validators.required, Validators.maxLength(250)]), this.duplicateSidingCodeAndId.bind(this)],
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
                zone:this.editsidingsItemResponse.zone,
               division:this.editsidingsItemResponse.division,               
                depot:this.editsidingsItemResponse.depot,
                
                
            });
            this.toMinDate = new Date(this.editsidingsItemResponse.proposedDate);
            this.displayHierarchyFields();
            this.findDepots();
            this.findDivisions();
          
        } ,error => {})
        this.id=id;
        if (!isNaN(this.id)) {
            this.title = Constants.EVENTS.UPDATE;
          } else {
            this.title = Constants.EVENTS.ADD;      
          }
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
        this.title = Constants.EVENTS.ADD;
        this.enableDivision = false;
       
        this.enableDepot = false;
        this.displayHierarchyFields();
    }

  

     NewSlidingsItem () {
        this.addSidingsItem = true;
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
               this.enableZone = true;
           }
        }
       
   
}



findDivisions(){
    let zone: string = this.sidingsItemFormGroup.value.zone;
    this.divisionList=[];

    for (let i = 0; i < this.userHierarchy.length; i++) {
        
           if(this.userHierarchy[i].zone == zone && this.userHierarchy[i].depotType == 'DIV'){
           
               this.divisionList.push(this.userHierarchy[i]);
               this.enableDivision = true;
           }
        }
}



findDepots(){
    let division: string = this.sidingsItemFormGroup.value.division;
   this.facilityList=[];
    for (let i = 0; i < this.userHierarchy.length; i++) {
       
           if(this.userHierarchy[i].division == division  &&(this.userHierarchy[i].depotType =='OHE') ){
            
               this.facilityList.push(this.userHierarchy[i]);
               this.enableDepot = true;
           }
        }
}
ViewData(data){
    var result = {
      'title':this.Titles.SIDINGS_DATA,
      'dataSource':[

                    { label:FieldLabelsConstant.LABELS.STATION, value:data.station },
                    { label:FieldLabelsConstant.LABELS.SIDING_CODE, value:data.sidingCode },
                    { label:FieldLabelsConstant.LABELS.SECTION, value:data.section },
                    { label:FieldLabelsConstant.LABELS.SIDING_ELECTRIFIED_STATUS, value:data.sidingEletrifiedStatus},
                    { label:FieldLabelsConstant.LABELS.SECTION_ELECTRIFIED_STATUS, value:data.sectionEletrifiedStatus},
                    { label:FieldLabelsConstant.LABELS.PRIVATE_RAILWAY, value:data.privateRailway },
                    { label:FieldLabelsConstant.LABELS.STATUS, value:data.status },
                    { label:FieldLabelsConstant.LABELS.TKM, value:data.tkm },
                    { label:FieldLabelsConstant.LABELS.REMARKS, value:data.remarks },
                    { label:FieldLabelsConstant.LABELS.ENERGISED_DATE, value:data.energisedDate },
                    { label:FieldLabelsConstant.LABELS.ZONE, value:data.zone },
                    { label:FieldLabelsConstant.LABELS.PROPOSED_DATE, value:data.proposedDate },
                    { label:FieldLabelsConstant.LABELS.APPROVAL_DATE, value:data.approvalDate },
                    { label:FieldLabelsConstant.LABELS.WORK_ORDER_DATE, value:data.workOrderDate }
                  
                  ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
            
    }
