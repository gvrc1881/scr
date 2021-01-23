import { OnInit, Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonService } from 'src/app/common/common.service';
import { Constants } from 'src/app/common/constants';
import {  MatDialog } from '@angular/material';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-tpc-board',
  templateUrl: './tpc-board.component.html',
  styleUrls: ['./tpc-board.component.css']
})
export class TpcBoardComponent implements OnInit {
tpcBoardData:any;
tpcBoardFormGroup: FormGroup;
depotData:any;
facilityData:any;
divisionList:any;
loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));
userHierarchy:any = JSON.parse(sessionStorage.getItem('userHierarchy'));
selectedTpcBoard: any = sessionStorage.getItem('tpcBoard');
  constructor(
    private commonService: CommonService,
    private formBuilder: FormBuilder,
    private location: Location,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    //this.tpcBoardDetails();
    this.tpcBoardForm();
    this.divisionData();
  }

  tpcBoardForm() {
    this.tpcBoardFormGroup = this.formBuilder.group({
      id: 0,
      'tpcBoard':[null],
    });
      // console.log('*** tpc board***'+this.selectedTpcBoard);
      if(this.selectedTpcBoard) {
          this.tpcBoardFormGroup.patchValue({
            tpcBoard: this.selectedTpcBoard    
          });
      }
  }
   
   tpcBoardDetails(){
     let dataDiv=this.divisionList.division
      this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TPC_BOARD_BASED_ON_DIVISION+dataDiv).subscribe((data) => {
        this.tpcBoardData = data;
  }
      );

   }
   divisionData(){
     var username=this.loggedUserData.username
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_LOGIN_USER+username).subscribe((data) => {
      this.divisionList = data;
      if(this.divisionList){
        this.tpcBoardDetails();
      }
}
    );
   
   }
   getFacilityList(){
    var tpcBoard = this.tpcBoardFormGroup.value.tpcBoard ;
    sessionStorage.setItem("tpcBoard", tpcBoard);
  this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_TPC_BOARD + tpcBoard).subscribe((response) => {
             this.facilityData = response;
             sessionStorage.setItem("depotData", JSON.stringify(response));
             let depotData=JSON.parse(sessionStorage.getItem('depotData'));
             // console.log("depotData"+JSON.stringify(depotData));
        });
}
onGoBack() {
  this.location.back();
}

}
