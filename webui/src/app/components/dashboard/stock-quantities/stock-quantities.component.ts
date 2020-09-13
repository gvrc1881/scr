import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-stock-quantities',
  templateUrl: './stock-quantities.component.html',
  styleUrls: ['./stock-quantities.component.css']
})
export class StockQuantitiesComponent implements OnInit {
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));
  isSubmit: boolean = false;
  addStockQuantities: FormGroup;
  stockQuantitiesErrors: any;
  toMinDate = new Date();

  statusList = [{ 'id': 1, "value": 'Yes' }, { 'id': 2, "value": 'No' }];
  zoneList: any;
  divisionList: any;
  subDivisionList: any;
  depotList: any;
  productList: any;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private sendAndRequestService: SendAndRequestService
  ) {
    this.stockQuantitiesErrors = {
      fromDate: {},
      toDate: {},
      zone: {},
      division: {},
      subDivision: {},
      depot: {},
      product: {}
    };
  }

  ngOnInit() {
    this.addStockQuantities = this.formBuilder.group({
      id: 0,
      'fromDate': [null, Validators.required],
      'toDate': [null],
      'zone': [null, Validators.required],
      'division': [null, Validators.required],
      'subDivision': [null, Validators.required],
      'depot': [null, Validators.required],
      'product': [null, Validators.required]
    });

    this.findZoneCodeList();
  }
  addEvent($event) {
    this.toMinDate = new Date($event.value);
  }

  findZoneCodeList() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
      this.zoneList = data;
    });
  }
  findDivisionCodeByZone(zone) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE + zone).subscribe((data) => {
      console.log(data)
      this.divisionList = data;
    })
  }

  findSubDivisionByDivision(division) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION + division).subscribe((data) => {
      console.log(data)
      this.subDivisionList = data;
    })
  }

  findDepotBySubDivision(subSivision) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_SUBDIVISION + subSivision).subscribe((data) => {
      console.log(data)
      this.depotList = data;
    })
  }

  findProductsByDepot(depot) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_SCHEDULE_CODE_BASED_ON_ASSETTYPE + depot).subscribe((data) => {
      console.log(data)
      this.productList = data;
    })
  }

  selectedZone($event) {
    console.log($event.value)
    if ($event.value) {
      this.divisionList = [];
      this.findDivisionCodeByZone($event.value);
    }
  }

  selectedDivision($event){
    if($event.value){
      this.subDivisionList = [];
      this.findSubDivisionByDivision($event.value);
    }
  }

  selectedSubDivision($event){
    if($event.value){
      this.depotList = [];
      this.findDepotBySubDivision($event.value);
    }
  }

  selectedDepot($event){
    if($event.value){
      this.productList = [];
      this.findProductsByDepot($event.value);
    }
  }
  
  submitStockQuantities () {
      console.log(this.addStockQuantities.controls);
      let request = {
        fromDate:this.addStockQuantities.controls.fromDate.value,
        toDate:this.addStockQuantities.controls.toDate.value,
        zone:this.addStockQuantities.controls.zone.value,
        division:this.addStockQuantities.controls.division.value,
        subDivision:this.addStockQuantities.controls.subDivision.value,
        depot:this.addStockQuantities.controls.depot.value,
        product:this.addStockQuantities.controls.product.value
      };
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, request , false).subscribe((data) => {
        console.log(data)
      })

  }
}
