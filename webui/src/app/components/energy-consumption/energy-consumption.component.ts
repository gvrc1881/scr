import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatPaginator, MatSort, MatRadioChange, DateAdapter, MAT_DATE_FORMATS } from '@angular/material';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { AppDateAdapter, APP_DATE_FORMATS } from 'src/app/common/date.adapter';
import { DatePipe } from '@angular/common';

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
  displayedColumns = ['sno','Feeder_Name','Multification_Factor','Joint_Reading', 'CMD',
    'Old_KWH','Current_KWH',"Consumption_KWH",'Old_KVAH','Current_KVAH','Consumption_KVAH',
    'Old_RKVAH_Lag','Current_RKVAH_Lag','Consumption_RKVAH_Lag',
    'Old_RKVAH_Lead','Current_RKVAH_Lead','Consumption_RKVAH_Lead','PF','CPF', 'RMD','Vol_Max','Vol_Min','Max_Load', 'actions'];
  dataSource: MatTableDataSource<any>;
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  gridData = [];
  stipulations: any[] = [];
  exactDate:boolean=true;
  energyConsumptionData=[];
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  selectedExactDate=new Date();
  selectedBWFrom = new Date();
  selectedBWTo = new Date();
  feedersList:any;
  selectedFeederId=0;
  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private sendAndRequestService: SendAndRequestService,
    private datePipe: DatePipe
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("Energy Consumption","Energy Consumption") ;
    this.spinnerService.show();
    //this.selectedExactDate = this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd')
    var query = this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd') +'/exact/'+this.selectedFeederId;
    this.findEnergyConsumptionData(query);
    this.findFeedersList();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": "Feeder_Name", "Value":""},
        { "Key": "Multification_Factor", "Value":""},
        { "Key": "Joint_Reading", "Value":""},
        { "Key": 'CMD', "Value": " " },
        { "Key": 'Old_KWH', "Value": " " },
        { "Key": 'Current_KWH', "Value": " " },
        { "Key": "Consumption_KWH", "Value":""},
        { "Key": 'Old_KVAH', "Value": " " },
        { "Key": 'Current_KVAH', "Value": " " },
        { "Key": "Consumption_KVAH", "Value":""},
        { "Key": 'Old_RKVAH_Lag', "Value": " " },
        { "Key": 'Current_RKVAH_Lag', "Value": " " },
        { "Key": "Consumption_RKVAH_Lag", "Value":""},
        { "Key": 'Old_RKVAH_Lead', "Value": " " },
        { "Key": 'Current_RKVAH_Lead', "Value": " " },
        { "Key": "Consumption_RKVAH_Lead", "Value":""},
        { "Key": 'PF', "Value": " " },
        { "Key": 'CPF', "Value": " " },
        { "Key": 'RMD', "Value": " " },
        { "Key": 'Vol_Max', "Value": " " },
        { "Key": 'Vol_Min', "Value": " " },
        { "Key": 'Max_Load', "Value": " " }
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
  findFeedersList(){
    this.spinnerService.show();
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_CONSUMPTION.FIND_TSS_FEEDER_MASTER )
      .subscribe((response) => {
        this.feedersList = response;
        this.spinnerService.hide();
      })
  }

  findEnergyConsumptionData(query){    
    this.spinnerService.show();
    this.filterData=[];
    this.energyConsumptionData=[];
    this.stipulations=[];
    this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_CONSUMPTION.FIND_ENERGY_CONSUMPTION + query)
      .subscribe((response) => {
          this.energyConsumptionData = response;
      for (let i = 0; i < this.energyConsumptionData.length; i++) {
        this.energyConsumptionData[i].sno = i + 1;
        this.energyConsumptionData[i].Feeder_Name = this.energyConsumptionData[i].feeder_name;
        this.energyConsumptionData[i].Multification_Factor = this.energyConsumptionData[i].multification_fac;
        this.energyConsumptionData[i].CMD = this.energyConsumptionData[i].cur_cmd;
        this.energyConsumptionData[i].Old_KWH = this.energyConsumptionData[i].prev_kwh;
        this.energyConsumptionData[i].Current_KWH = this.energyConsumptionData[i].cur_kwh;
        this.energyConsumptionData[i].Consumption_KWH = (this.energyConsumptionData[i].cur_kwh-this.energyConsumptionData[i].prev_kwh) * this.energyConsumptionData[i].multification_fac;

        this.energyConsumptionData[i].Old_KVAH = this.energyConsumptionData[i].prev_kvah;
        this.energyConsumptionData[i].Current_KVAH = this.energyConsumptionData[i].cur_kvah;
        this.energyConsumptionData[i].Consumption_KVAH = (this.energyConsumptionData[i].cur_kvah - this.energyConsumptionData[i].prev_kvah) * this.energyConsumptionData[i].multification_fac;

        this.energyConsumptionData[i].Old_RKVAH_Lag = this.energyConsumptionData[i].prev_rkvah_lag;
        this.energyConsumptionData[i].Current_RKVAH_Lag = this.energyConsumptionData[i].cur_rkvah_lag;
        this.energyConsumptionData[i].Consumption_RKVAH_Lag = (this.energyConsumptionData[i].cur_rkvah_lag - this.energyConsumptionData[i].prev_rkvah_lag) * this.energyConsumptionData[i].multification_fac;

        this.energyConsumptionData[i].Old_RKVAH_Lead = this.energyConsumptionData[i].prev_rkvah_lead;
        this.energyConsumptionData[i].Current_RKVAH_Lead = this.energyConsumptionData[i].cur_rkvah_lead;
        this.energyConsumptionData[i].Consumption_RKVAH_Lead = (this.energyConsumptionData[i].cur_rkvah_lead - this.energyConsumptionData[i].prev_rkvah_lead) * this.energyConsumptionData[i].multification_fac;

        this.energyConsumptionData[i].RMD = this.energyConsumptionData[i].cur_rmd;
        this.energyConsumptionData[i].Vol_Max = this.energyConsumptionData[i].cur_vol_max;
        this.energyConsumptionData[i].Vol_Min = this.energyConsumptionData[i].cur_vol_min;
        this.energyConsumptionData[i].Max_Load = this.energyConsumptionData[i].cur_max_load;

        this.energyConsumptionData[i].editable = false;
/* 

        this.energyConsumptionData[i].Energy_Reading_Date = this.energyConsumptionData[i].requested_reading_date;
        this.energyConsumptionData[i].Joint_Meter = this.energyConsumptionData[i].first_reading_after_meter_fix;
        this.energyConsumptionData[i].KVAH = this.energyConsumptionData[i].prev_kvah;
        this.energyConsumptionData[i].KWH = this.energyConsumptionData[i].prev_kwh;
        this.energyConsumptionData[i].Max_Load = this.energyConsumptionData[i].cur_max_load;
        this.energyConsumptionData[i].CMD = this.energyConsumptionData[i].cur_cmd;
        this.energyConsumptionData[i].RKVAH_Lag = this.energyConsumptionData[i].prev_rkvah_lag;
        this.energyConsumptionData[i].RKVAH_Lead = this.energyConsumptionData[i].prev_rkvah_lead;
        this.energyConsumptionData[i].Sequence_Id = this.energyConsumptionData[i].feeder_id;
       
        this.energyConsumptionData[i].Last_Updated_Stamp = this.energyConsumptionData[i].recent_reading_date;
 */        this.stipulations.push(this.energyConsumptionData[i]);
      }
   //   console.log(JSON.stringify(this.stipulations))
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
  processUpdateAction(id){

  }

  processEditAction(row){
  //  console.log(row)
    this.dataSource.filteredData.map((item, index) =>{
    //  console.log(item);
      if(item.feeder_id == row.feeder_id){
        this.dataSource.filteredData[index]['editable'] = true;
      }     
    })
  }
  processCancelAction(row){
    this.dataSource.filteredData.map((item, index) =>{
    //  console.log(item);
      if(item.feeder_id == row.feeder_id){
        this.dataSource.filteredData[index]['editable'] = false;
      }     
    })
  }
  radioChange(event: MatRadioChange) {
    if(event.value == 1){
      this.exactDate = true;
      this.selectedExactDate = new Date();
    }else{
      this.exactDate = false;
      this.selectedBWFrom = new Date();
      this.selectedBWTo = new Date();
      this.selectedFeederId = 0;
    }    
  }
  exactDateEvent($event) {
    this.selectedExactDate = new Date($event.value);
  }
  bwFromDateEvent($event){
    this.selectedBWFrom = new Date($event.value);
    this.selectedBWTo = new Date($event.value);
  }
  bwToDateEvent($event){
    this.selectedBWTo = new Date($event.value);
  }
  updateFeederName($event){
    if($event.value){
      this.selectedFeederId = $event.value;
    }
  }
  delete(id){
    
  }
  executeQuery() {
    var query = "";
    query = this.exactDate ? this.datePipe.transform(this.selectedExactDate, 'yyyy-MM-dd')  +'/exact/'+this.selectedFeederId : this.datePipe.transform(this.selectedBWFrom, 'yyyy-MM-dd')  +'/'+ this.datePipe.transform(this.selectedBWTo, 'yyyy-MM-dd') +'/'+this.selectedFeederId;
    this.findEnergyConsumptionData(query);
  }
}
