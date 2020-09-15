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

  groupByList = [{ 'id': 1, "value": 'Division' }, { 'id': 2, "value": 'Sub Division' }
  , { 'id': 3, "value": 'Depot' }, { 'id': 4, "value": 'Product' }];
  zoneList: any;
  divisionList: any;
  subDivisionList: any;
  depotList: any;
  productList: any;
  maxDate=new Date();
  group:number=0;
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
      groupBy:{},
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
      'groupBy': [null, Validators.required],
      'zone': [null, Validators.required],
      'division': [null],
      'subDivision': [null],
      'depot': [null],
      'product': [null]
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
      this.divisionList = data;
    })
  }

  findSubDivisionByDivision(division) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION + division).subscribe((data) => {
      this.subDivisionList = data;
    })
  }

  findDepotBySubDivision(subSivision) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_SUBDIVISION + subSivision).subscribe((data) => {
      this.depotList = data;
    })
  }

  findProductsByDepot(depot) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES + depot).subscribe((data) => {
      this.productList = data;
    })
  }

  selectedGroupBy($event){
    if($event.value){
      this.group = $event.value;
    }
  }

  selectedZone($event) {
    if ($event.value) {
      this.divisionList = [];
      this.findDivisionCodeByZone($event.value.id);
    }
  }

  selectedDivision($event){
    if($event.value){
      this.subDivisionList = [];
      this.findSubDivisionByDivision($event.value.id);
    }
  }

  selectedSubDivision($event){
    if($event.value){
      this.depotList = [];
      this.findDepotBySubDivision($event.value.code);
    }
  }

  selectedDepot($event){
    if($event.value){
      this.productList = [];
      this.findProductsByDepot($event.value.facilityId);
    }
  }
  
  submitStockQuantities () {
     // console.log(this.addStockQuantities.controls);
      let request = {
        fromDate:this.addStockQuantities.controls.fromDate.value,
        toDate:this.addStockQuantities.controls.toDate.value,
        groupBy:this.addStockQuantities.controls.groupBy.value,
        zone:this.addStockQuantities.controls.zone.value.code,
        division:this.addStockQuantities.controls.division.value.code,
        subDivision:this.addStockQuantities.controls.subDivision.value.headquarters,
        depot:this.addStockQuantities.controls.depot.value.facilityName,
        facility:this.addStockQuantities.controls.product.value,
        queryType:'MATERIAL_QTY_RECEIVED_AND_CONSUMED_IN_GIVEN_PERIOD_BY_DEPOT'
      };
      this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, request , false).subscribe((data) => {
        console.log(data)
      })

  }
}
