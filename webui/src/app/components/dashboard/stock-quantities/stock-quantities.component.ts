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
  findDivisionCodeByZone(zone: any) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE + zone.id).subscribe((data) => {
      this.divisionList = data;
    })
  }

  selectedZone($event) {
    if ($event.value) {
      this.divisionList = [];
      this.findDivisionCodeByZone($event.target.value);
    }
  }
  
  submitStockQuantities () {
  }
}
