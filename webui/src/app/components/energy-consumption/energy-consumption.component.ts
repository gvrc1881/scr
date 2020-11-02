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
import { RouterExtService } from 'src/app/services/router-ext.service';
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
  displayedColumns = ['sno', 'Feeder_Name', 'Previous_Date', 'Multification_Factor', 'Joint_Reading',/*  'CMD', */
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
  feedersOriginalList: any;
  selectedFeederId:string;
  row: number;
  divisionsList: any;
  selectedDivision: any;
  radioList = [
    { "name": "Date", ID: "D1", "checked": true },
    { "name": "Station", ID: "D2", "checked": false }
  ]
  divisionCode: string;
  feederId: string;
  zoneData: any = JSON.parse(localStorage.getItem('zoneData'));
  divisionData: any = JSON.parse(localStorage.getItem('divisionData'));
  zoneObject: any;
  zoneCode: string
  userDefaultData: any;
  loggedUser: any = JSON.parse(localStorage.getItem('loggedUser'));
  previousUrl: string;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private datePipe: DatePipe,
    private router: Router,
    private route: ActivatedRoute,
    private routerService: RouterExtService
  ) {

  }

  ngOnInit() {
    const previousUrl = this.routerService.getPreviousUrl();
    console.log(previousUrl)
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_DEFAULT_DATA + this.loggedUser.userName).subscribe((data) => {
      this.userDefaultData = data;
      if (this.userDefaultData.zone) {
        this.zoneCode = this.userDefaultData.zone.toUpperCase();
        this.getDivisions(this.userDefaultData.zone);
      }
      if (this.userDefaultData.division) {
        this.divisionCode = this.userDefaultData.division.toUpperCase();
        if(previousUrl != "/energy-consumption/"){
          let query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + 0+ '/' + this.divisionCode : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd') + '/' + this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') + '/' + this.selectedFeederId + '/' + this.selectedDivision;
            this.selectedDivision = this.divisionCode;
            console.log(this.divisionCode)
            this.findEnergyConsumptionData(query);
        }
      }
    },
      error => error => {
        console.log(' >>> ERROR ' + error);
      });

    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 1, 0, 1);
    this.maxDate = new Date();
    this.selectedBWTo = this.maxDate;
    var permissionName = this.commonService.getPermissionNameByLoggedData("Energy Consumption", "Energy Consumption");
    this.spinnerService.show();
    this.findFeedersList();
    this.divisionDetails();
    if (previousUrl == '/energy-consumption/') {
      var query = !!localStorage.getItem('query') ? localStorage.getItem('query') : this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId;
      console.log('query = ' + query);
      if (localStorage.getItem('query')) {
        var values = localStorage.getItem('query').split('/');
        console.log(values);
        if (values[1] == 'exact') {
          this.exactDate = true;
          this.radioList[0].checked = true;
          this.radioList[1].checked = false;
          this.selectedExactDate = new Date(values[0]);
          this.divisionCode = values[3];
        } else {
          this.exactDate = false;
          this.radioList[0].checked = false;
          this.radioList[1].checked = true;
          this.selectedBWFrom = new Date(values[0]);
          this.selectedBWTo = new Date(values[1]);
          this.divisionCode = values[3];
          this.feederId = values[2]
        }
      }
      localStorage.setItem('query', '');
    }

    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": "Feeder_Name", "Value": "" },
        { "Key": "Previous_Date", "Value": " " },
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
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }

  getDivisions(zoneCode: any) {
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_OBJECT + zoneCode).subscribe((data) => {
      this.zoneObject = data;
      this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE + this.zoneObject.id).subscribe((data) => {
        this.divisionsList = data;
      })
    })
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

          this.energyConsumptionData[i].sno = i + 1;
          this.energyConsumptionData[i].Feeder_Name = this.energyConsumptionData[i].feederName;
          this.energyConsumptionData[i].Previous_Date = this.energyConsumptionData[i].readingGapDays != null ? this.energyConsumptionData[i].readingGapDays : '';
          this.energyConsumptionData[i].Multification_Factor = this.energyConsumptionData[i].multiplicationFac;
          this.energyConsumptionData[i].cmd = this.energyConsumptionData[i].curCmd;

          this.energyConsumptionData[i].Old_KWH = this.energyConsumptionData[i].prevKwh;
          this.energyConsumptionData[i].Current_KWH = this.energyConsumptionData[i].curKwh;
          this.energyConsumptionData[i].Consumption_KWH = this.energyConsumptionData[i].curKwh != 0 ? ((this.energyConsumptionData[i].curKwh - parseFloat(this.energyConsumptionData[i].prevKwh)) * parseFloat(this.energyConsumptionData[i].multiplicationRac)).toFixed(2) : 0;
          this.energyConsumptionData[i].kwh_f = this.energyConsumptionData[i].Previous_Date != null ? this.energyConsumptionData[i].Previous_Date.split('(')[0].length + 1 : new Date();
          this.energyConsumptionData[i].kwh_m = this.energyConsumptionData[i].kwh_f + this.energyConsumptionData[i].Previous_Date.includes("(") && this.energyConsumptionData[i].Previous_Date.split('(').length > 1 ? this.energyConsumptionData[i].Previous_Date.split('(')[1].split(')')[0].length : '';
          this.energyConsumptionData[i].kwh_l = this.energyConsumptionData[i].kwh_m + this.energyConsumptionData[i].Previous_Date.includes("(") && this.energyConsumptionData[i].Previous_Date.split('(').length > 1 ? this.energyConsumptionData[i].Previous_Date.split('(')[1].split(')')[1].length : '';

          this.energyConsumptionData[i].Old_KVAH = this.energyConsumptionData[i].prevKvah;
          this.energyConsumptionData[i].Current_KVAH = this.energyConsumptionData[i].curKvah;
          this.energyConsumptionData[i].Consumption_KVAH = this.energyConsumptionData[i].curKvah != 0 ? ((this.energyConsumptionData[i].curKvah - parseFloat(this.energyConsumptionData[i].prevKvah)) * parseFloat(this.energyConsumptionData[i].multiplicationFac)).toFixed(2) : 0;

          this.energyConsumptionData[i].Old_RKVAH_Lag = this.energyConsumptionData[i].prevRkvahLag;
          this.energyConsumptionData[i].Current_RKVAH_Lag = this.energyConsumptionData[i].curRkvahLag;
          this.energyConsumptionData[i].Consumption_RKVAH_Lag = this.energyConsumptionData[i].curRkvahLag != 0 ? ((this.energyConsumptionData[i].curRkvahLag - parseFloat(this.energyConsumptionData[i].prevRkvahLag)) * parseFloat(this.energyConsumptionData[i].multiplicationFac)).toFixed(2) : 0;

          this.energyConsumptionData[i].Old_RKVAH_Lead = this.energyConsumptionData[i].prevRkvahLead;
          this.energyConsumptionData[i].Current_RKVAH_Lead = this.energyConsumptionData[i].curRkvahLead;
          this.energyConsumptionData[i].Consumption_RKVAH_Lead = this.energyConsumptionData[i].curRkvahLead != 0 ? ((this.energyConsumptionData[i].curRkvahLead - parseFloat(this.energyConsumptionData[i].prevRkvahLead)) * parseFloat(this.energyConsumptionData[i].multiplicationFac)).toFixed(2) : 0;

          this.energyConsumptionData[i].pf = this.energyConsumptionData[i].Consumption_KVAH != 0 && this.energyConsumptionData[i].Consumption_KWH != 0 ? (this.energyConsumptionData[i].Consumption_KWH / this.energyConsumptionData[i].Consumption_KVAH).toFixed(2) : 0;
          this.energyConsumptionData[i].cpf = this.energyConsumptionData[i].jrKvah != 0 ? (this.energyConsumptionData[i].jrKwh / this.energyConsumptionData[i].jrKvah).toFixed(2) : 0;

          this.energyConsumptionData[i].rmd = this.energyConsumptionData[i].curRmd;
          this.energyConsumptionData[i].Vol_Max = this.energyConsumptionData[i].curVolMax;
          this.energyConsumptionData[i].Vol_Min = this.energyConsumptionData[i].curVolMin;
          this.energyConsumptionData[i].Max_Load = this.energyConsumptionData[i].curMaxLoad;

          this.energyConsumptionData[i].energyReadingDate = this.energyConsumptionData[i].requestedReadingDate;//this.exactDate == true ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') : this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') ;
          this.energyConsumptionData[i].id = this.energyConsumptionData[i].curId;
          this.energyConsumptionData[i].editable = false;
          this.stipulations.push(this.energyConsumptionData[i]);
        }
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
    var row = this.dataSource.filteredData.find((item, index) => {
      return item.feederId == id;
    })
    var query = "";
    query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId + '/' + this.selectedDivision : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd') + '/' + this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') + '/' + this.selectedFeederId + '/' + this.selectedDivision;
    localStorage.setItem('query', query);
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
    this.filterData.dataSource = [];
    if (event.value == 'Date') {
      this.exactDate = true;
      this.selectedExactDate = new Date();
      var query = "";
      query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId + '/' + this.selectedDivision : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd') + '/' + this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') + '/' + this.selectedFeederId + '/' + this.selectedDivision;
      this.findEnergyConsumptionData(query);
    } else {
      this.exactDate = false;
      this.selectedBWFrom = new Date();
      this.selectedBWTo = this.maxDate;
   //   console.log(this.selectedDivision)
      this.selectedFeederId = '';
      this.feedersList = this.feedersOriginalList.filter(value => {
        return value.dataDiv.toLowerCase() == this.selectedDivision.toLowerCase();
      });
     // console.log(this.feedersList[0]);
      this.feederId = this.feedersList != null && this.feedersList.length > 0 ? this.feedersList[0].feederId : '';
      var query = "";
      this.selectedFeederId = this.feederId;
      query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId + '/' + this.selectedDivision : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd') + '/' + this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') + '/' + this.feederId + '/' + this.selectedDivision;
      //console.log(query)
      if(this.feederId){
         this.findEnergyConsumptionData(query);
      }
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
    if (!this.exactDate) {
      this.feedersList = this.feedersOriginalList.filter(value => {
        return value.dataDiv.toLowerCase() == $event.value.toLowerCase();
      });
      this.selectedDivision = $event.value;
    } else {
      this.selectedDivision = $event.value;
    }
  }
  delete(id) {

  }
  executeQuery() {
    var query = "";
    query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') + '/exact/' + this.selectedFeederId + '/' + this.selectedDivision : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd') + '/' + this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') + '/' + this.selectedFeederId + '/' + this.selectedDivision;
    localStorage.setItem('query', query);
    this.findEnergyConsumptionData(query);
  }
}
