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
loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
userHierarchy:any = JSON.parse(localStorage.getItem('userHierarchy'));
selectedTpcBoard: any = localStorage.getItem('tpcBoard');
  constructor(
    private commonService: CommonService,
    private formBuilder: FormBuilder,
    private location: Location,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    this.tpcBoardDetails();
    this.tpcBoardForm();
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
   
   tpcBoardDetails()
    {
            
           this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TPC_BOARD_DETAILS).subscribe((data) => {
             this.tpcBoardData = data;
    }
           );

   }
   getFacilityList(){
    var tpcBoard = this.tpcBoardFormGroup.value.tpcBoard ;
    localStorage.setItem("tpcBoard", tpcBoard);
  this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_TPC_BOARD + tpcBoard).subscribe((response) => {
             this.facilityData = response;
             localStorage.setItem("depotData", JSON.stringify(response));
             let depotData=JSON.parse(localStorage.getItem('depotData'));
             // console.log("depotData"+JSON.stringify(depotData));
        });
}
onGoBack() {
  this.location.back();
}

}
