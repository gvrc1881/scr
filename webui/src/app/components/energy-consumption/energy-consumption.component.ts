import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatPaginator, MatSort, MatRadioChange, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DatePipe } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-energy-consumption',
  templateUrl: './energy-consumption.component.html',
  styleUrls: ['./energy-consumption.component.css'],
  providers: [
    {
      provide: DateAdapter, useClass: AppDateAdapter
    },
    {
      provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS
    }
  ]
})
export class EnergyConsumptionComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  filterData;
  displayedColumns = ['sno', 'Feeder_Name','Previous_Date', 'Multification_Factor', 'Joint_Reading',/*  'CMD', */
    'Old_KWH', 'Current_KWH', "Consumption_KWH", 'Old_KVAH', 'Current_KVAH', 'Consumption_KVAH',
    'Old_RKVAH_Lag', 'Current_RKVAH_Lag', 'Consumption_RKVAH_Lag',
    'Old_RKVAH_Lead', 'Current_RKVAH_Lead', 'Consumption_RKVAH_Lead'/* , 'PF', 'CPF', 'rmd', 'Vol_Max', 'Vol_Min', 'Max_Load' */, 'actions'];
  dataSource: MatTableDataSource<any>;
  confirmdialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  gridData = [];
  stipulations: any[] = [];
  exactDate: boolean = true;
  energyConsumptionData = [];
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  selectedExactDate = new Date();
  selectedBWFrom = new Date();
  selectedBWTo = new Date();
  minDate: Date;
  maxDate: Date;
  feedersList: any;
  feedersOriginalList:any;
  selectedFeederId = 0;
  row: number;
  divisionsList: any;
  selectedDivision:any;
  radioList =  [
      {"name": "Date", ID: "D1", "checked": true},
      {"name": "Station", ID: "D2", "checked": false}
    ]
    divisionCode:string;
    feederId:string;
  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private datePipe: DatePipe,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 1, 0, 1);
    this.maxDate = new Date();
    this.selectedBWTo = this.maxDate;
    var permissionName = this.commonService.getPermissionNameByLoggedData("Energy Consumption", "Energy Consumption");
        this.spinnerService.show();
    this.findFeedersList();
    this.divisionDetails();

    console.log("lll = "+localStorage.getItem('query'))
    var query = !!localStorage.getItem('query') ? localStorage.getItem('query') :  this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId;
    console.log('query = '+query);
    this.findEnergyConsumptionData(query);
    if(localStorage.getItem('query')){
      var values = localStorage.getItem('query').split('/');
      console.log(values);
      if(values[1] == 'exact'){
        //for(var i=0;i<this.radioChange.length;i++){
          this.exactDate = true;
          this.radioList[0].checked=true;
          this.radioList[1].checked=false;
          this.selectedExactDate = new Date(values[0]);
          this.divisionCode = values[3];
          console.log('sdfsdf= '+this.divisionCode)
        //}
      }else{
        this.exactDate = false;
          this.radioList[0].checked=false;
          this.radioList[1].checked=true;
          this.selectedBWFrom = new Date(values[0]);
          this.selectedBWTo = new Date(values[1]);
          this.divisionCode = values[3];
          this.feederId = values[2]
      }
    }

    localStorage.setItem('query','');
    
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": "Feeder_Name", "Value": "" },
        { "Key": "Previous_Date", "Value":" "},
        { "Key": "Multification_Factor", "Value": "" },
        { "Key": "Joint_Reading", "Value": "" },
      //  { "Key": 'CMD', "Value": " " },
        { "Key": 'Old_KWH', "Value": " " },
        { "Key": 'Current_KWH', "Value": " " },
        { "Key": "Consumption_KWH", "Value": "" },
        { "Key": 'Old_KVAH', "Value": " " },
        { "Key": 'Current_KVAH', "Value": " " },
        { "Key": "Consumption_KVAH", "Value": "" },
        { "Key": 'Old_RKVAH_Lag', "Value": " " },
        { "Key": 'Current_RKVAH_Lag', "Value": " " },
        { "Key": "Consumption_RKVAH_Lag", "Value": "" },
        { "Key": 'Old_RKVAH_Lead', "Value": " " },
        { "Key": 'Current_RKVAH_Lead', "Value": " " },
        { "Key": "Consumption_RKVAH_Lead", "Value": "" }
      /*  { "Key": 'PF', "Value": " " },
        { "Key": 'CPF', "Value": " " },
        { "Key": 'rmd', "Value": " " },
        { "Key": 'Vol_Max', "Value": " " },
        { "Key": 'Vol_Min', "Value": " " },
        { "Key": 'Max_Load', "Value": " " }*/
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.filterData.dataSource.filter = filterValue;
  }
  findFeedersList() {
    this.spinnerService.show();
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_CONSUMPTION.FIND_TSS_FEEDER_MASTER)
      .subscribe((response) => {
        this.feedersList = response;
        this.feedersOriginalList = response;
        this.spinnerService.hide();
      })
  }
  divisionDetails() {
    // var division = {
    //   address_1: null,
    // address_2: null,
    // city: null,
    // code: "SC",
    // createdBy: "system",
    // createdOn: null,
    // description: "South Central",
    // district: null,
    // divisionId: null,
    // headquarters: "Secunderabad",
    // id: 10,
    // pin: null,
    // state: null,
    // updatedOn: null
    // }
    //this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE,).subscribe((data) => {
     
    console.log("div==="+JSON.stringify(this.divisionsList));
    this.sendAndRequestService.requestForGET(Constants.app_urls.DRIVE.GET_DIVISIONS)
      .subscribe((data) => {
      this.divisionsList = data;
    });
  }
  findEnergyConsumptionData(query) {
    this.spinnerService.show();
    this.filterData = [];
    this.energyConsumptionData = [];
    this.stipulations = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_CONSUMPTION.FIND_ENERGY_CONSUMPTION + query)
      .subscribe((response) => {
        this.energyConsumptionData = response;
        console.log(response)
        for (let i = 0; i < this.energyConsumptionData.length; i++) {
          //var pkwh = parseFloat(this.energyConsumptionData[i].prev_kwh.split(']')[1]);
          //console.log('pkwh = '+pkwh);
          this.energyConsumptionData[i].sno = i + 1;
          this.energyConsumptionData[i].Feeder_Name = this.energyConsumptionData[i].feeder_name;
          this.energyConsumptionData[i].Previous_Date = this.energyConsumptionData[i].reading_gap_days.replace('days','').trim();
          this.energyConsumptionData[i].Multification_Factor = this.energyConsumptionData[i].multiplication_fac;
          this.energyConsumptionData[i].cmd = this.energyConsumptionData[i].cur_cmd;
         
          this.energyConsumptionData[i].Old_KWH = this.energyConsumptionData[i].prev_kwh;
          this.energyConsumptionData[i].Current_KWH = this.energyConsumptionData[i].cur_kwh;
          this.energyConsumptionData[i].Consumption_KWH = this.energyConsumptionData[i].cur_kwh != 0 ? (this.energyConsumptionData[i].cur_kwh - parseFloat(this.energyConsumptionData[i].prev_kwh)) * parseFloat(this.energyConsumptionData[i].multiplication_fac) : 0;
          this.energyConsumptionData[i].kwh_f = this.energyConsumptionData[i].Previous_Date.split('(')[0].length+1;
          this.energyConsumptionData[i].kwh_m = this.energyConsumptionData[i].kwh_f + this.energyConsumptionData[i].Previous_Date.split('(')[1].split(')')[0].length;
          this.energyConsumptionData[i].kwh_l = this.energyConsumptionData[i].kwh_m + this.energyConsumptionData[i].Previous_Date.split('(')[1].split(')')[1].length;

          this.energyConsumptionData[i].Old_KVAH = this.energyConsumptionData[i].prev_kvah;
          this.energyConsumptionData[i].Current_KVAH = this.energyConsumptionData[i].cur_kvah;
          this.energyConsumptionData[i].Consumption_KVAH = this.energyConsumptionData[i].cur_kvah != 0 ? (this.energyConsumptionData[i].cur_kvah - parseFloat(this.energyConsumptionData[i].prev_kvah)) * parseFloat(this.energyConsumptionData[i].multiplication_fac) : 0;

          this.energyConsumptionData[i].Old_RKVAH_Lag = this.energyConsumptionData[i].prev_rkvah_lag;
          this.energyConsumptionData[i].Current_RKVAH_Lag = this.energyConsumptionData[i].cur_rkvah_lag;
          this.energyConsumptionData[i].Consumption_RKVAH_Lag = this.energyConsumptionData[i].cur_rkvah_lag != 0 ? (this.energyConsumptionData[i].cur_rkvah_lag - parseFloat(this.energyConsumptionData[i].prev_rkvah_lag)) * parseFloat(this.energyConsumptionData[i].multiplication_fac) : 0;

          this.energyConsumptionData[i].Old_RKVAH_Lead = this.energyConsumptionData[i].prev_rkvah_lead;
          this.energyConsumptionData[i].Current_RKVAH_Lead = this.energyConsumptionData[i].cur_rkvah_lead;
          this.energyConsumptionData[i].Consumption_RKVAH_Lead = this.energyConsumptionData[i].cur_rkvah_lead != 0 ? (this.energyConsumptionData[i].cur_rkvah_lead - parseFloat(this.energyConsumptionData[i].prev_rkvah_lead)) * parseFloat(this.energyConsumptionData[i].multiplication_fac) : 0;

          this.energyConsumptionData[i].pf = this.energyConsumptionData[i].Consumption_KVAH != 0 && this.energyConsumptionData[i].Consumption_KWH != 0 ? this.energyConsumptionData[i].Consumption_KWH / this.energyConsumptionData[i].Consumption_KVAH : 0;
          this.energyConsumptionData[i].cpf = this.energyConsumptionData[i].jr_kvah != 0 ? this.energyConsumptionData[i].jr_kwh / this.energyConsumptionData[i].jr_kvah : 0;
          
          this.energyConsumptionData[i].rmd = this.energyConsumptionData[i].cur_rmd;
          this.energyConsumptionData[i].Vol_Max = this.energyConsumptionData[i].cur_vol_max;
          this.energyConsumptionData[i].Vol_Min = this.energyConsumptionData[i].cur_vol_min;
          this.energyConsumptionData[i].Max_Load = this.energyConsumptionData[i].cur_max_load;

          this.energyConsumptionData[i].energyReadingDate = this.exactDate == true ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') : this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') ;

          this.energyConsumptionData[i].editable = false;
          this.stipulations.push(this.energyConsumptionData[i]);
        }
        //console.log(this.energyConsumptionData);
        this.filterData.gridData = this.stipulations;
        this.dataSource = new MatTableDataSource(this.stipulations);
        this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
        this.filterData.dataSource = this.dataSource;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.spinnerService.hide();
      })
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  processUpdateAction(id) {

  }

  processEditAction(id) {
  //  console.log("edit = " + id);
    var row = this.dataSource.filteredData.find((item, index) => {
      return item.feeder_id == id;
    })
    //console.log(row);
    localStorage.setItem('ec', JSON.stringify(row));
    this.router.navigate([id], { relativeTo: this.route });
  }
  processCancelAction(row) {
    this.dataSource.filteredData.map((item, index) => {
      if (item.feeder_id == row.feeder_id) {
        this.dataSource.filteredData[index]['editable'] = false;
      }
    })
  }
  radioChange(event: MatRadioChange) {
    this.filterData.dataSource=[];
    if (event.value == 'Date') {
      this.exactDate = true;
      this.selectedExactDate = new Date();
    } else {
      this.exactDate = false;
      this.selectedBWFrom = new Date();
      this.selectedBWTo = this.maxDate;
      this.selectedFeederId = 0;
    }
  }
  exactDateEvent($event) {
    this.selectedExactDate = new Date($event.value);
  }
  bwFromDateEvent($event) {
    this.selectedBWFrom = new Date($event.value);
    this.selectedBWTo = this.maxDate;
    console.log(this.selectedBWTo);
  }
  bwToDateEvent($event) {
    this.selectedBWTo = new Date($event.value);
  }
  updateFeederName($event) {
    if ($event.value) {
      this.selectedFeederId = $event.value;
    }
  }

  updateDivision($event) {
   // console.log("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")
    if(!this.exactDate){
      this.feedersList = this.feedersOriginalList.filter(value =>{
        return value.dataDiv.toLowerCase() == $event.value.toLowerCase();
      });
      this.selectedDivision = $event.value;
    }else{
      this.selectedDivision = $event.value;
    }
  }
  delete(id) {

  }
  executeQuery() {
    var query = "";
    query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId +'/'+ this.selectedDivision : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd') + '/' + this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') + '/' + this.selectedFeederId+'/'+ this.selectedDivision;
    localStorage.setItem('query', query);
    this.findEnergyConsumptionData(query);
  }
}
