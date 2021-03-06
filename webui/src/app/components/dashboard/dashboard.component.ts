import { Component, OnInit } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
declare function loadData(id:number, name:string, image:any);
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
        
    towerCarResponse: any;
    requestBody: any;
    towerCarDataSource: any;
    width = 200;
    height = 400;
    type = "mscolumn3d";
    dataFormat = "json";
    showTowerCarChart : boolean = false;
    divWiseEntryPendingResponse: any;
    divWiseEntryPendingDataSource: any = {};
    divWiseWidth: any;
    showDivWiseEnergyConsumEntryPending: boolean = false;
    pieType = "pie2d";

    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private sendAndRequestService: SendAndRequestService
  ) {
  
  }
  
  ngOnInit() {
    this.getTowerCarGraphsData();
    this.divWiseEnergyConsumEntryPending();
  }
  
  getTowerCarGraphsData(){
    this.requestBody = {
        queryType:'TOWER_CAR'    
    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, this.requestBody, false).subscribe((data) => {
      console.log(data);
      this.towerCarResponse = data;
      if(this.towerCarResponse) {
        this.towerCarDataSource = this.prepareTowerCarGraphData(this.towerCarResponse);
         // console.log('*** json values  ***'+JSON.stringify(this.towerCarDataSource));
          if(this.towerCarDataSource){
            this.showTowerCarChart = true;    
          }
        //this.showReportBotton = true; 
      }
    })    
  }
  
    prepareTowerCarGraphData(data: any){
        let dataSet = [];
        for(let i=0;i<data.length;i++){
            this.width = this.width +100;
            dataSet.push({
                seriesname: data[i].fStatus+data[i].productCategoryId,
                data:[
                    {
                        value: data[i].gtlCount,
                        displayvalue:data[i].gtlCount+" "+data[i].fStatus +" "+data[i].productCategoryId   
                    },
                    {
                        value: data[i].gntCount,
                        displayvalue:data[i].gntCount+" "+data[i].fStatus +" "+data[i].productCategoryId   
                    },
                    {
                        value: data[i].scCount,
                        displayvalue:data[i].scCount+" "+data[i].fStatus +" "+data[i].productCategoryId    
                    },
                    {
                        value: data[i].bzaCount,
                        displayvalue:data[i].bzaCount+" "+data[i].fStatus +" "+data[i].productCategoryId    
                    },
                    {
                        value: data[i].hybCount,
                        displayvalue:data[i].hybCount+" "+data[i].fStatus +" "+data[i].productCategoryId    
                    }
                ]
            })
        }
        return {
            chart: {
            caption: "Tower Car",
            xaxisname: "Divisions",
            yaxisname: "Count",
            formatnumberscale: "1",
            showValues: "1",
            rotateValues: "1",
            valueFontColor: "#000000",
            plottooltext:
              "<b>$dataValue</b> <b>$seriesName</b> in $label",
            theme: "fusion"
          },
          categories: [
            {
              category: [
                {
                  label: "GTL"
                },
                {
                  label: "GNT"
                },
                {
                  label: "SC"
                },
                {
                  label: "BZA"
                },
                {
                  label: "HYB"
                }
              ]
            }
          ],
          dataset: dataSet  
        }
    }
    
    
    divWiseEnergyConsumEntryPending () {
        this.requestBody = {
              queryType:'DIV_WIS_ENERGY_CONSUM_ENTRY_PENDING'    
          }
          this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, this.requestBody, false).subscribe((data) => {
            console.log(data);
            this.divWiseEntryPendingResponse = data; 
             // console.log('*** log***'+JSON.stringify(this.divWiseEntryPendingResponse));   
            if(this.divWiseEntryPendingResponse) {
              this.divWiseEntryPendingDataSource = this.prepareEnergyConsumEntryPendingData(this.divWiseEntryPendingResponse);
               //console.log('*** log***'+JSON.stringify(this.divWiseEntryPendingDataSource));
                if(this.divWiseEntryPendingDataSource){
                  this.showDivWiseEnergyConsumEntryPending = true;    
                }
            }
          });
    }
  
    prepareEnergyConsumEntryPendingData(data: any) {
        let dataValues =[];
        for(let i=0;i<data.length;i++){
            this.divWiseWidth = this.divWiseWidth +200;
            dataValues.push({               
                        label: data[i].division,
                        value: data[i].energyDataPending
            });
        }
    return {
         chart: {
            caption: "Energy Consumption Entry Pending",
            plottooltext: "<b>$label</b> of energy consumption Entry Pending <b>$value</b>",
            //showlegend: "1",
            showpercentvalues: "0",
            //legendposition: "bottom",
            //usedataplotcolorforlabels: "1",
            valuePosition: "inside",
            //showPercentInTooltip: "0",
            theme: "fusion"
          },   
         data: dataValues
        } 
    }
    
    
    
        
  /*   comment by adiReddy
  MenusList: any = [];
  tiles: Tile[] = [];
  loggedUserData: any = JSON.parse(sessionStorage.getItem('userData'));

  lastProcessedDivisionDataSource: any = {};
  lastProcessedJobsDataSource: any = {};
  lastProcessedOperationTypeDataSource: any = {};
  lastOneWeekDivisionsDataSourceObj: any = {};


  operationTypesDataSet: any = [];
  dashboardResponse: any;
  
  //subDivProductReponse: any;
  //subDivProductDataSource: any = {};
  //productCategoryMember: any = [];
  //productId: any ;
  //dashboardFormGroup: FormGroup;
  //showSubDivGraph: boolean = false;

  barDataSource: Object;
  jobsDataSource: Object;
  operationTypesDataSource: Object;

  widthone = 850;
  heightone = 400;
  type = "mscolumn3d";
  dataFormat = "json";

  width = 400;
  height = 300;

  multiBarType = "mscolumn3d";
  operationTypesMultiBarDataSource: any = {};


  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
   private sendAndRequestService:SendAndRequestService,
   private formBuilder: FormBuilder
  ) {


  }

  ngOnInit() {
    
    this.spinnerService.show();
    this.findDashboardData();
    /*
    this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES+'DASH_BOARD_CATEGORY').subscribe(response => {
      this.productCategoryMember = response;
    }, error => {
      console.log("ERROR >>> " + error)
    });
    
    this.dashboardFormGroup = this.formBuilder.group({
            'productId': [null],
        })
    ////    
    
    this.MenusList = [
      { ID: 2, menuName: 'Reports', menuUrl: 'home', icon: "fa fa-area-chart", color: "#64aeed", isSelected: true },
      { ID: 3, menuName: 'Schedule Settings', menuUrl: 'settings', icon: "fa fa-cogs", color: "#efb44e", isSelected: false },
      { ID: 4, menuName: 'Schedule Tracking', menuUrl: 'schedule', icon: "fa fa-briefcase", color: "#ff5f4f", isSelected: false },
      { ID: 5, menuName: 'Masters', menuUrl: 'masters', icon: "fa fa-wrench", color: "#715fb7", isSelected: false }];

   

  }


  findLastProcessedDivisionDataSource() {
    var divisionNameArray = this.dashboardResponse.lastOneWeekDetails
    .map(item => this.loggedUserData.divisionCode === 'All' ? item.divisionName : item.divisionCode)
    .filter((value, index, self) => self.indexOf(value) === index);
    var datapoints= [];
    for(var k=0;k<divisionNameArray.length;k++){
      var sum = 0;
      this.dashboardResponse.lastProcessedDetails.filter((data) => {
        if(this.loggedUserData.divisionCode === 'All'){
          if(data.divisionName === divisionNameArray[k]){
              sum += data.successTables + data.failedTables;
            }
        }else if(this.loggedUserData.divisionCode == divisionNameArray[k]){
          if(data.divisionCode === divisionNameArray[k]){
            sum += data.successTables + data.failedTables;
          }
        }
    });
    datapoints.push({
      label:divisionNameArray[k],
      value:sum
    })
  }
    return {
      chart: {
        caption: 'Division Wise [' + this.dashboardResponse.lastProcessedDetails[this.dashboardResponse.lastProcessedDetails.length-1].date + ']',
        xAxisName: 'Division Names',
        yAxisName: 'Tables Count',
        numberSuffix: 'K',
        theme: 'fusion'
      },
      data: datapoints
    };
  }
  
  /*
  getSubDivProductGraph() {
  	this.sendAndRequestService.requestForGET(Constants.app_urls.DASHBOARD.GET_SUBDIV_WISE_PROD+'/'+this.dashboardFormGroup.value.productId).subscribe(response => {
      if(response){
	      this.subDivProductReponse = response;
	      this.subDivProductDataSource = this.findSubDivWiseProdutDataSource();
	      this.showSubDivGraph = true ;
      }      
    }, error => {
      console.log("ERROR IN SUB DIV WISE PRODUCT LIST >>> " + error)
    });
  }
  
  findSubDivWiseProdutDataSource() {
  		var subDivNameArray = this.subDivProductReponse.subdivWiseProductList;
  		var datapoints = [];
  		for(var i = 0 ; i<subDivNameArray.length;i++ ) {
  			datapoints.push({
  				lebel:subDivNameArray[i].subdiv,
  				value:subDivNameArray[i].qoh
  			})
  		}
  		return {
	      chart: {
	        caption: ' sub division wise product details',
	        xAxisName: 'sub Div',
	        yAxisName: 'product count',
	        numberSuffix: 'K',
	        theme: 'fusion'
	      },
	      data: datapoints
    };
  } 
    ////

  findLastProcessedJobsDataSource() {
   
    var jobTypes = this.dashboardResponse.lastProcessedDetails
      .map(item => item.jobType)
      .filter((value, index, self) =>  self.indexOf(value) === index);

      var divisionNameArray = this.dashboardResponse.lastOneWeekDetails
      .map(item => this.loggedUserData.divisionCode === 'All' ? item.divisionName : item.divisionCode)
      .filter((value, index, self) => self.indexOf(value) === index);
    var job1 = 0;
    var job2 = 0;
    this.dashboardResponse.lastProcessedDetails.filter((data) => {
      if (data.jobType == jobTypes[0]) {
        job1 += data.successTables + data.failedTables;
      }
      else {
        job2 += data.successTables + data.failedTables;
      }

    });
    let category = []
    for(let i=0;i<divisionNameArray.length;i++){
      category.push({
        label:divisionNameArray[i]     
      })
    };
    return {
      chart: {
        caption: 'Divisions Wise ',
        xAxisName: 'Division Names',
        yAxisName: 'Tables Count',
        numberSuffix: 'K',
        theme: 'fusion',
        plottooltext:
        "<b>Job Type : $seriesname</b> <br> "+
        "<b>Value : $value</b> ",
      },
      categories: [
        {
          category: category
        }
      ],
      dataset: [
        {
        seriesname: jobTypes[0],
        data: [
          {
            value: job1
          }
        ]
      },
      {
        seriesname: jobTypes[1],
        data: [
          {
            value: job2
          }
        ]
      }        
      ]
    };
  }

  findLastProcessedOperationTypeDataSource(jobName) {    
    this.lastProcessedOperationTypeDataSource = {};
    if (!!jobName.dataObj) {
      jobName = jobName.dataObj.datasetName;
    }
    this.lastProcessedOperationTypeDataSource = {
      chart: {
        caption: jobName + ' Job Division Wise [' + this.dashboardResponse.lastProcessedDetails[this.dashboardResponse.lastProcessedDetails.length-1].date + ']',
        xaxisname: "Division Names",
        yaxisname: "Tables Count",
        formatnumberscale: "1",
        plottooltext:
          "<b>$dataValue</b> $label in <b>$seriesName</b> Operation Type",
        theme: "fusion",
        drawcrossline: "1"
      },
      categories: [
        {
          category: [
            {
              label: "Success Tables"
            },
            {
              label: "Failed Tables"
            }
          ]
        }
      ],
      dataset: this.prepareOperationTypesDataSet(jobName)
    };
  }
  prepareOperationTypesDataSet(jobName) {
    return [
      {
        seriesname: "CREATE",
        data: this.findOperationTypePoints(jobName, 'CREATE')
      },
      {
        seriesname: "UPDATE",
        data: this.findOperationTypePoints(jobName, 'UPDATE')
      },
      {
        seriesname: "DELETE",
        data: this.findOperationTypePoints(jobName, 'DELETE')
      }
    ]
  }

  findOperationTypePoints(jobName, type) {
    var data = [];
    this.dashboardResponse.lastProcessedDetails.filter((values) => {
      if (values.jobType == jobName && values.operationType == type) {
        data.push({
          value: values.successTables
        }, {
            value: values.failedTables
          }
        )
      }
    });
    return data;
  }

  findLastOneWeekDivisionsDataSource() {
    var divisionNameArray = this.dashboardResponse.lastOneWeekDetails
      .map(item => this.loggedUserData.divisionCode === 'All' ? item.divisionName : item.divisionCode)
      .filter((value, index, self) => self.indexOf(value) === index);
    this.lastOneWeekDivisionsDataSourceObj = {
      chart: {
        caption: "Last One Week Division Wise Tracking Info",
        xaxisname: "Dates",
        yaxisname: "Tables Count",
        formatnumberscale: "1",
        plottooltext:
          "Division <b>$seriesName</b> Tables Count <b>$dataValue</b>  in $label",
        theme: "fusion"
      },
      categories: [
        {
          category: this.findLastOneWeekDates()
        }
      ],
      dataset: this.findLastOneWeekDataSet(divisionNameArray)
    };
  }

  findLastOneWeekDataSet(divisionNameArray) {
    var dates = this.findLastOneWeekDates();
    var dataset = [];
    for (var d = 0; d < divisionNameArray.length; d++) {
      var data = [];
      for (var i = 0; i < dates.length; i++) {
        data.push(this.getDataSet(dates[i].label));
      }
      dataset.push({
        seriesname: divisionNameArray[d],
        data: data
      })
    }
    return dataset;
  }
  getDataSet(date) {
    const jobData = this.dashboardResponse.lastOneWeekDetails.filter((values) => {
      return values.date === date;
    });
    var sum = 0;
    !!jobData && jobData.filter((data) => {
      sum += data.successTables + data.failedTables
    });
    return { value: sum };
  }

  findLastOneWeekDates() {
    var divisionNameArray = this.dashboardResponse.lastOneWeekDetails
    .map(item => item.divisionName)
    .filter((value, index, self) => self.indexOf(value) === index);
    
    var datesArray = this.dashboardResponse.lastOneWeekDetails
      .map(item => item.date)
      .filter((value, index, self) => 
        self.indexOf(value) === index);
      datesArray.sort();      
      datesArray.reverse();    
      var len = !!datesArray && datesArray.length <= 7 ? datesArray.length : 7
    var dates = [];
    for (var i = 0; i < len; i++) {
      dates.push({
        label: datesArray[i]
      });
    }
    return dates;
  }


  startGraphs() {
    this.lastProcessedDivisionDataSource = this.findLastProcessedDivisionDataSource();
    this.lastProcessedJobsDataSource = this.findLastProcessedJobsDataSource();
    this.findLastProcessedOperationTypeDataSource('DIVISIONTOSTAGING');
    this.findLastOneWeekDivisionsDataSource();
    this.spinnerService.hide();
  }

  findDashboardData() {

    this.sendAndRequestService.requestForGET(Constants.app_urls.DASHBOARD.GET_DASHBOARD+ this.loggedUserData.divisionCode).subscribe(response => {
      this.dashboardResponse = response;      
      if(response){
        this.startGraphs();
      }
    }, error => {
      console.log("ERROR >>> " + error)
    });
  }

   */
}


export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
  url: string;
  icon: string
}
