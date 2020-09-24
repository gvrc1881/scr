import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';
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
  periodFlag = false;
  groupByList = [{ 'id': 1, "value": 'Division' }, { 'id': 2, "value": 'Sub Division' }
    , { 'id': 3, "value": 'Depot' }];
  periodList = [{ 'id': 1, "value": 'No' }, { 'id': 2, "value": 'Yes' }];
  zoneList: any;
  divisionList: any;
  subDivisionList: any;
  depotList: any;
  productList: any;
  maxDate = new Date();
  group: number = 0;
  graph: boolean = false;
  periodGraph: boolean = false;
  response: any;
  dataSource: any;
  periodDataSource: any;
  chartConfig: any;
  width:number=0;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private datePipe: DatePipe,
    private sendAndRequestService: SendAndRequestService
  ) {
    this.stockQuantitiesErrors = {
      period: {},
      fromDate: {},
      toDate: {},
      groupBy: {},
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
      'period': [null, Validators.required],
      'fromDate': [null],
      'toDate': [null],
      'groupBy': [null, Validators.required],
      'zone': [null, Validators.required],
      'division': [null],
      'subDivision': [null],
      'depot': [null],
      'product': [null, Validators.required]
    });
    this.findProductsByDepot();
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

  findProductsByDepot() {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES+'DASH_BOARD_CATEGORY').subscribe((data) => {
      this.productList = data;
      //console.log(data)
    })
  }

  selectedPeriod($event) {
    if ($event.value) {
      if ($event.value == 1) {
        this.periodFlag = false;
      } else {
        this.periodFlag = true;
      }
    }
  }

  selectedGroupBy($event) {
    if ($event.value) {
      this.group = $event.value;
    }
  }

  selectedZone($event) {
    if ($event.value) {
      this.divisionList = [];
      this.findDivisionCodeByZone($event.value.id);
    }
  }

  selectedDivision($event) {
    if ($event.value) {
      this.subDivisionList = [];
      this.findSubDivisionByDivision($event.value.id);
    }
  }

  selectedSubDivision($event) {
    if ($event.value) {
      this.depotList = [];
      this.findDepotBySubDivision($event.value.code);
    }
  }

  selectedDepot($event) {
    if ($event.value) {
      this.productList = [];
      this.findProductsByDepot();
    }
  }

  submitStockQuantities() {
    //console.log(this.addStockQuantities.controls);
    var queryType = '';
    var caption = '';
    var xAxisName = '';
    if (this.addStockQuantities.controls.period.value == 1) {
      if (this.addStockQuantities.controls.groupBy.value == 1) {
        queryType = 'DIVISION';
        caption = 'Division Wise';
        xAxisName = 'Division';
      } else if (this.addStockQuantities.controls.groupBy.value == 2) {
        queryType = 'SUB_DIVISION';
        caption = 'Sub Division Wise';
        xAxisName = 'Sub Division';
      } else if (this.addStockQuantities.controls.groupBy.value == 3) {
        queryType = 'DEPOT';
        caption = 'Depot Wise';
        xAxisName = 'Depot';
      } else if (this.addStockQuantities.controls.groupBy.value == 4) {
        queryType = 'PRODUCT';
      }
    } else {
      if (this.addStockQuantities.controls.groupBy.value == 1) {
        queryType = 'DIVISION_PERIOD';
        caption = 'Division Wise From ' + this.datePipe.transform(this.addStockQuantities.controls.fromDate.value, 'yyyy-MM-dd') + ' To ' + this.datePipe.transform(this.addStockQuantities.controls.toDate.value, 'yyyy-MM-dd');
        xAxisName = 'Division';
      } else if (this.addStockQuantities.controls.groupBy.value == 2) {
        queryType = 'SUB_DIVISION_PERIOD';
        caption = 'Sub Division Wise From ' + this.datePipe.transform(this.addStockQuantities.controls.fromDate.value, 'yyyy-MM-dd') + ' To ' + this.datePipe.transform(this.addStockQuantities.controls.toDate.value, 'yyyy-MM-dd');
        xAxisName = 'Sub Division';
      } else if (this.addStockQuantities.controls.groupBy.value == 3) {
        queryType = 'DEPOT_PERIOD';
        caption = 'Depot Wise From ' + this.datePipe.transform(this.addStockQuantities.controls.fromDate.value, 'yyyy-MM-dd') + ' To ' + this.datePipe.transform(this.addStockQuantities.controls.toDate.value, 'yyyy-MM-dd');
        xAxisName = 'Depot';
      } else if (this.addStockQuantities.controls.groupBy.value == 4) {
        queryType = 'PRODUCT_PERIOD';
      }
    }
    let request = {
      // period:this.addStockQuantities.controls.period.value,
      fromDate: this.addStockQuantities.controls.fromDate.value != null ? this.addStockQuantities.controls.fromDate.value : '',
      toDate: this.addStockQuantities.controls.toDate.value != null ? this.addStockQuantities.controls.toDate.value : '',
      groupBy: this.addStockQuantities.controls.groupBy.value,
      zone: this.addStockQuantities.controls.zone.value.code,
      division: this.addStockQuantities.controls.division.value != null ? this.addStockQuantities.controls.division.value.code : '',
      subDivision: this.addStockQuantities.controls.subDivision.value != null ? this.addStockQuantities.controls.subDivision.value.headquarters : '',
      depot: this.addStockQuantities.controls.depot.value != null ? this.addStockQuantities.controls.depot.value.facilityName : '',
      facility: this.addStockQuantities.controls.product.value != null ? this.addStockQuantities.controls.product.value : '',
      //product: '0076-1',
      product: this.addStockQuantities.controls.product.value,
      queryType: queryType
    };
    this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, request, false).subscribe((data) => {
     this.dataSource ={};
     this.periodDataSource={};
      this.response = data;
      if (this.response != null && this.response.length > 0) {
        this.prepareDataSource(queryType, caption, xAxisName);
      }
    })
  }
  prepareDataSource(queryType, caption, xAxisName) {

    var datapoints = [];
    var datapointsperiod = [];
    var yAxisName = '';
    var yAxisNamep = '';
    var category = [];
    var qtyNetPeriodData = [];
    var receivedQtyData = [];
    for (var i = 0; i < this.response.length; i++) {
      //console.log(this.response[i]);
      var label = '';
      var qoh = 0;
      var qtyNetPeriod = 0;
      var receivedQty = 0;
      var width = 600;
      caption = this.response[i].header;
      if (queryType == 'DIVISION') {
        label = this.response[i].division;
        qoh = this.response[i].qoh;
        this.graph = true;
        this.periodGraph = false;
        yAxisName = 'qoh';
      } else if (queryType == 'SUB_DIVISION') {
        label = this.response[i].subDivision;
        qoh = this.response[i].qoh;
        this.graph = true;
        this.periodGraph = false;
        yAxisName = 'qoh';
      } else if (queryType == 'DEPOT') {
        label = this.response[i].depotName;
        qoh = this.response[i].qoh;
        width = 1200;
        this.graph = true;
        this.periodGraph = false;
        yAxisName = 'qoh';
      } else if (queryType == 'DIVISION_PERIOD' || queryType == 'SUB_DIVISION_PERIOD'||
      queryType == 'DEPOT_PERIOD') {
        if(queryType == 'DIVISION_PERIOD'){
          label = this.response[i].division;
        }
        else if(queryType == 'SUB_DIVISION_PERIOD'){
          label = this.response[i].subDivision;
        }
        else if(queryType == 'DEPOT_PERIOD'){
          width = 800;
          label = this.response[i].depotName;
        }
        qtyNetPeriod = this.response[i].qtyNetPeriod;
        receivedQty = this.response[i].receivedQty;
        this.graph = false;
        this.periodGraph = true;
        width=400;
        yAxisName = 'Qty Net Period';
        yAxisNamep = 'Received Net';
      }


      if (queryType == 'DIVISION' || queryType == 'SUB_DIVISION' || queryType == 'DEPOT') {
        datapoints.push({
          label: label,
          value: qoh
        })
      } else {
        category.push({label:label});
        qtyNetPeriodData.push({value:qtyNetPeriod});
        receivedQtyData.push({value:receivedQty});
        datapoints.push({
          label: label,
          value: qtyNetPeriod
        });
        datapointsperiod.push({
          label: label, value: receivedQty
        })
      }
    }
   
   this.width = width;
    if (queryType == 'DIVISION' || queryType == 'SUB_DIVISION' || queryType == 'DEPOT') {
    this.dataSource = {
      "chart": {
        caption: caption,
        xAxisName: xAxisName,
        yAxisName: yAxisName+' values',
        showvalues: "1",
        //numberSuffix: 'K',
        theme: 'fusion',        
        exportEnabled: "1",
        exportFileName: caption
      },
      "data": datapoints
    }
    this.chartConfig = {
      width: width,
      height: '400',
      type: 'column3d',
      dataFormat: 'json',
    };
    console.log(this.dataSource)
    this.graph = true;
    this.periodGraph = false;
  }else{
   
    this.periodDataSource = {
      chart: {
        caption: caption,
        xAxisName: xAxisName,
        yAxisName: 'Values',
        showvalues: "1",
       // numberSuffix: 'K',
        theme: 'fusion',
        exportEnabled: "1",
        exportFileName: caption
      },
      categories:[{category:category}],
      dataset:[
        {
          seriesname:'Net Quantity',
          data:qtyNetPeriodData
        },
        {
          seriesname:'Received Quantity',
          data:receivedQtyData
        }
      ]
    }
    this.graph = false;
    this.periodGraph = true;
    this.chartConfig = {
      width: '1000',
      height: '400',
      type: 'mscolumn3d',
      dataFormat: 'json',
    };
  }
  }
}
