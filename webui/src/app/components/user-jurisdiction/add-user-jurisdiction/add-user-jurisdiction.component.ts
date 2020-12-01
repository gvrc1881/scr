import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';


@Component({
  selector: 'app-add-user-jurisdiction',
  templateUrl: './add-user-jurisdiction.component.html',
  styleUrls: [],
})
export class AddUserJurisdictionComponent implements OnInit {

  FiledLabels = FieldLabelsConstant.LABELS;
  title: string = '';
  userJurisdictionFormGroup: FormGroup;
    usersList: any;
    workList: any;
    workGroupList: any;
    sectionList: any;
  
  
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private dialog: MatDialog,
  
    private sendAndRequestService:SendAndRequestService
  ) 
  {
   
  }

  ngOnInit() {
      this.createUserJuridictionForm();
      this.findUsersData();
      this.findWorksData();
  }
    
  userJurisdictionFormSubmit() {
    console.log('** save function ***');
    let requestBody = {
        user: this.userJurisdictionFormGroup.value.user,
        work: this.userJurisdictionFormGroup.value.work,
        workGroup: this.userJurisdictionFormGroup.value.workGroup,
        section: this.userJurisdictionFormGroup.value.section    
    }
      console.log('*** request body ***'+JSON.stringify(requestBody));
      this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.USER_JURISDICTION.SAVE,requestBody,false)
        .subscribe((data) => {
                console.log("*** post method ****");
        })
  }
    
  findUsersData () {
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_ALLUSERS)
      .subscribe((data) => {
        this.usersList = data;
          console.log('*** length ***'+this.usersList.length);
      })    
  }
    
  findWorksData () {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK)
      .subscribe((data) => {
        this.workList = data;
          console.log('*** work length ***'+this.workList.length);
      })    
  }
    
  getWorkGroups() {
        this.sectionList = [];
        this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_GROUPS_BASED_ON_WORK + this.userJurisdictionFormGroup.value.work.id ).subscribe((response) => {
           this.workGroupList = response;
            console.log('*** workGroupList length ***'+this.workGroupList.length);
        });
  }
    
  getSections() {
    console.log('*** values ***'+this.userJurisdictionFormGroup.value.workGroup);
      if(this.userJurisdictionFormGroup.value.workGroup != null) {
          this.sendAndRequestService.requestForGET( Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.GET_SECTIONS_BASED_ON_GROUPS + this.userJurisdictionFormGroup.value.workGroup ).subscribe((response) => {
              this.sectionList = response;
              });
      }   
  }
    
  createUserJuridictionForm() {
      this.title = 'Save';
    this.userJurisdictionFormGroup = this.formBuilder.group({
      id: 0,
      'user': [ null ],
      'work': [ null ],
      'workGroup': [ null ],
      'section': [ null ],
    });
  }
    
   onGoBack() {
      this.router.navigate(['../'], { relativeTo: this.route });
   }
 
}



