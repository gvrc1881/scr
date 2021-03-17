import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { SpecialWorksModel } from 'src/app/models/special-works.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { DatePipe } from '@angular/common';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-approve-special-works',
  templateUrl: './approve-special-works.component.html',
  styleUrls: []
})
export class ApproveSpecialWorksComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  depotsList: any = JSON.parse(sessionStorage.getItem('depotData'));
  userdata: any = JSON.parse(sessionStorage.getItem('userData')); 
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno','facilityId','dateOfWork', 'location','precautionaryMeasure','count','check'];
  dataSource: MatTableDataSource<SpecialWorksModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  facilityData:any;
  precautionaryMeasureData:any;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  inputFormGroup: FormGroup;
  enableSave: boolean;
  specialWorksData: any;
  resp: any;
  checked: boolean;
  maxDate = new Date();
  specialWorksList = [];
  selectedItems = [];

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    this.inputFormGroup = this.formBuilder.group({
      'dateOfWork': [null],
      'facilityId' : [null],
  });
    var permissionName = this.commonService.getPermissionNameByLoggedData("ASSET REGISTER","PRECAUTIONARY MEASURES") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    


  }
  saveAction(){
    this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.SAVE_APPROVE_SPECIAL_WORKS,this.selectedItems,false).subscribe((response) => {
        this.spinnerService.hide();
        this.resp = response;
        this.enableSave = false;
        if(this.resp.code == 200 && !!this.resp) {
            this.commonService.showAlertMessage("Special Works Updated Successfully");
               
        }else
            this.commonService.showAlertMessage("Special Works Updating Failed");
        
    });
}


getSpecialWorks() {
    this.spinnerService.show();
    this.enableSave = false;
    this.specialWorksList = [];
    this.selectedItems = [];
    this.dataSource = new MatTableDataSource(this.specialWorksList);
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.SPECIALWORKS.GET_NON_APPROVED_SPECIAL_WORKS_BASED_ON_FACILITY_DATE
    +this.inputFormGroup.controls['dateOfWork'].value +'/' + this.inputFormGroup.controls['facilityId'].value
        ).subscribe((data) => {
            this.specialWorksData = data;
            console.log('*** data ***'+JSON.stringify(data));
         for (let i = 0; i < this.specialWorksData.length; i++) {
            this.specialWorksData[i].sno = i + 1;
            this.specialWorksList.push(this.specialWorksData[i]);
         }
          //this.enableSave = true;
          this.dataSource = new MatTableDataSource(this.specialWorksList);
          this.spinnerService.hide();
      this.spinnerService.hide(); 
    }, error => {
      this.spinnerService.hide();
    });
}
 
  onCheckboxChange(e, row) {
    if (e.target.checked) {
        row.approvedStatus = "Approve";
        row.approveBy = this.userdata.username;
      this.selectedItems.push(row);
      this.enableSave = true;
    } else {
      this.selectedItems.splice(row.index, 1);
      if (this.selectedItems.length == 0) {
        this.enableSave = false;
      }
    }
  }
  selectAll(event) {
    for (var i = 0; i < this.specialWorksData.length; i++) {
            if(event.target.checked) {
                this.specialWorksData[i].checked = true;
                this.specialWorksData[i].approvedStatus = "Approve";
                this.specialWorksData[i].approveBy = this.userdata.username;
                
                this.selectedItems.push(this.specialWorksData[i]);                     
                this.enableSave = true;   
            }else {
                this.specialWorksData[i].checked = false;
                this.selectedItems = [];
                this.enableSave = false;
            }
            
        }
}
}
