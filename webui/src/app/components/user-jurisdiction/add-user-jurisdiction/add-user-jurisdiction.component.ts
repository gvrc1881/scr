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
    id: number;
    save: boolean = true;
    update: boolean = false;
    userJurisdictionFormErrors: any;
    resp: any;
    selectedGroups = [];
    selectedSections = [];
  
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
    // Reactive form errors
    this.userJurisdictionFormErrors = {
      user: {},
      work: {},
      workGroup: {},
      section: {}
    };   
  }

  ngOnInit() {
      this.id = +this.route.snapshot.params['id'];
      this.createUserJuridictionForm();
      this.findUsersData();
      this.findWorksData();
      if (!isNaN(this.id)) {
      /*
      this.userJurisdictionFormGroup.valueChanges.subscribe(() => {
        this.onFormValuesChanged();
      }); */
      this.spinnerService.show();
      this.save = false;
      this.update = true;
      this.title = Constants.EVENTS.UPDATE;
      this.getUserJurisdictionDataById(this.id);
    } else {
          this.id = 0;
      this.title = Constants.EVENTS.ADD;      
    }
  }
    
    getUserJurisdictionDataById(id) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.PROJECT_ADMIN.USER_JURISDICTION.GET_USER_JURISDICTION_ID+id)
            .subscribe((resp) => {
                //console.log('** edit body ***'+JSON.stringify(resp));
                this.resp = resp;
                this.selectedGroups.push(this.resp.workGroupId.id);
                if(this.resp.section){
                    this.selectedSections.push(this.resp.section.id);
                }    
                if(this.resp){
                    this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_GROUPS_BASED_ON_WORK + this.resp.workId.id ).subscribe((response) => {
                       this.workGroupList = response;
                    });
                    this.sendAndRequestService.requestForGET( Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.GET_SECTIONS_BASED_ON_GROUPS + this.selectedGroups ).subscribe((response) => {
                      this.sectionList = response;
                    });
                    this.id = this.resp.id;
                    this.userJurisdictionFormGroup.patchValue({
                        id: this.id,
                        user : this.resp.userId.id,
                        work : this.resp.workId.id,
                        workGruoup: this.selectedGroups,
                        section : this.selectedSections
                    })                    
                }
            });     
    }
    
  onFormValuesChanged() {
    for (const field in this.userJurisdictionFormErrors) {
      if (!this.userJurisdictionFormErrors.hasOwnProperty(field)) {
        continue;
      }
      this.userJurisdictionFormErrors[field] = {};
      const control = this.userJurisdictionFormErrors.get(field);

      if (control && control.dirty && !control.valid) {
        this.userJurisdictionFormErrors[field] = control.errors;
      }
    }
  }
    
  userJurisdictionFormSubmit() {
      if(this.save){
          let requestBody = {
            userId: this.userJurisdictionFormGroup.value.user,
            workId: this.userJurisdictionFormGroup.value.work,
            workGroup: this.userJurisdictionFormGroup.value.workGroup,
            section: this.userJurisdictionFormGroup.value.section    
        }
          this.sendAndRequestService.requestForPOST(Constants.app_urls.PROJECT_ADMIN.USER_JURISDICTION.SAVE,requestBody,false)
            .subscribe((data) => {
                this.resp = data;
                   if(this.resp.code == 200 && !!this.resp) {
                        this.commonService.showAlertMessage("User Jurisdiction Added Successfully.");
                   } else {
                        this.commonService.showAlertMessage("User Jurisdiction Addition Failed.");
                   }
                    this.router.navigate(['../'], { relativeTo: this.route });
            })
      }else {
          let requestBody = {
            id: this.id,
            userId: this.userJurisdictionFormGroup.value.user,
            workId: this.userJurisdictionFormGroup.value.work,
            workGroup: this.userJurisdictionFormGroup.value.workGroup,
            section: this.userJurisdictionFormGroup.value.section    
        }
          this.sendAndRequestService.requestForPUT(Constants.app_urls.PROJECT_ADMIN.USER_JURISDICTION.UPDATE,requestBody,false)
            .subscribe((data) => {
                this.resp = data;
                   if(this.resp.code == 200 && !!this.resp) {
                        this.commonService.showAlertMessage("User Jurisdiction updated Successfully.");
                   } else {
                        this.commonService.showAlertMessage("User Jurisdiction Updating Failed.");
                   }
                this.router.navigate(['../../'], { relativeTo: this.route });
          })
      }
  }
    
  findUsersData () {
    this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_ALLUSERS)
      .subscribe((data) => {
        this.usersList = data;
      })    
  }
    
  findWorksData () {
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK)
      .subscribe((data) => {
        this.workList = data;
      })    
  }
    
  getWorkGroups() {
        this.sectionList = [];
      if(this.userJurisdictionFormGroup.value.work) {
        this.sendAndRequestService.requestForGET( Constants.app_urls.ENERGY_BILL_PAYMENTS.WORK.GET_WORK_GROUPS_BASED_ON_WORK + this.userJurisdictionFormGroup.value.work ).subscribe((response) => {
           this.workGroupList = response;
        });          
      }
  }
    
  getSections() {
      if(this.userJurisdictionFormGroup.value.workGroup != null) {
          this.sendAndRequestService.requestForGET( Constants.app_urls.PROJECT_ADMIN.GROUPS_SECTIONS.GET_SECTIONS_BASED_ON_GROUPS + this.userJurisdictionFormGroup.value.workGroup ).subscribe((response) => {
              this.sectionList = response;
              });
      }   
  }
    
  createUserJuridictionForm() {
    this.userJurisdictionFormGroup = this.formBuilder.group({
      'user': [ null ],
      'work': [ null ],
      'workGroup': [ null ],
      'section': [ null ],
    });
  }
    
   onGoBack() {
       if (this.save) {
         this.router.navigate(['../'], { relativeTo: this.route });
       } else if (this.update) {
         this.router.navigate(['../../'], { relativeTo: this.route });
       }
   }
 
}



