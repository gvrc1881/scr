import { Component, OnInit } from '@angular/core';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { DashboardService } from 'src/app/services/dashboard.service';
declare function loadData(id:number, name:string, image:any);

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  MenusList: any = [];
  tiles: Tile[] = [];
  loggedUserData: any = JSON.parse(localStorage.getItem('userData'));

  lastProcessedDivisionDataSource: any = {};
  lastProcessedJobsDataSource: any = {};
  lastProcessedOperationTypeDataSource: any = {};
  lastOneWeekDivisionsDataSourceObj: any = {};


  operationTypesDataSet: any = [];
  dashboardResponse: any;

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
    private dashboardService: DashboardService,
  ) {


  }

  ngOnInit() {
    loadData(127,"Phanendra","");
    this.spinnerService.show();
    this.findDashboardData();
    //location.reload();
    
    this.MenusList = [
     // { ID: 6, menuName: 'Energy Bill Payments', menuUrl: 'home', icon: "fa fa-area-chart", color: "#64aeed", isSelected: true },
      { ID: 2, menuName: 'Reports', menuUrl: 'home', icon: "fa fa-area-chart", color: "#64aeed", isSelected: true },
      { ID: 3, menuName: 'Schedule Settings', menuUrl: 'settings', icon: "fa fa-cogs", color: "#efb44e", isSelected: false },
      { ID: 4, menuName: 'Schedule Tracking', menuUrl: 'schedule', icon: "fa fa-briefcase", color: "#ff5f4f", isSelected: false },
      { ID: 5, menuName: 'Masters', menuUrl: 'masters', icon: "fa fa-wrench", color: "#715fb7", isSelected: false }];

   

  }


  findLastProcessedDivisionDataSource() {
   //console.log('data:::'+JSON.stringify(this.dashboardResponse));
    var divisionNameArray = this.dashboardResponse.lastOneWeekDetails
    .map(item => this.loggedUserData.divisionCode === 'All' ? item.divisionName : item.divisionCode)
    .filter((value, index, self) => self.indexOf(value) === index);


    //console.log('divisionNameArray: '+JSON.stringify(divisionNameArray));
    //console.log(JSON.stringify(this.loggedUserData.divisionCode))
    var datapoints= [];
    for(var k=0;k<divisionNameArray.length;k++){
      var sum = 0;
      this.dashboardResponse.lastProcessedDetails.filter((data) => {
        if(this.loggedUserData.divisionCode === 'All'){
         // console.log(data)
          if(data.divisionName === divisionNameArray[k]){
              sum += data.successTables + data.failedTables;
            }
        }else if(this.loggedUserData.divisionCode == divisionNameArray[k]){
          if(data.divisionCode === divisionNameArray[k]){
            sum += data.successTables + data.failedTables;
          }
        }
      //console.log("sum value::"+sum);
    });
    datapoints.push({
      label:divisionNameArray[k],
      value:sum
    })
  }
  //console.log(JSON.stringify(datapoints));
    return {
      chart: {
        caption: 'Division Wise [' + this.dashboardResponse.lastProcessedDetails[this.dashboardResponse.lastProcessedDetails.length-1].date + ']',
        xAxisName: 'Division Names',
        yAxisName: 'Tables Count',
        numberSuffix: 'K',
        theme: 'fusion'
      },
      data: datapoints/* [
        { label: this.dashboardResponse.lastProcessedDetails[0].divisionName, value: sum }
      ] */
    };
  }

  findLastProcessedJobsDataSource() {
   
    var jobTypes = this.dashboardResponse.lastProcessedDetails
      .map(item => item.jobType)
      .filter((value, index, self) =>  self.indexOf(value) === index);

      var divisionNameArray = this.dashboardResponse.lastOneWeekDetails
      .map(item => this.loggedUserData.divisionCode === 'All' ? item.divisionName : item.divisionCode)
      .filter((value, index, self) => self.indexOf(value) === index);
  //console.log('divisionNameArray= '+JSON.stringify(divisionNameArray))
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
    //console.log('category: '+JSON.stringify(category))
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
    console.log('divisionNameArray: '+JSON.stringify(divisionNameArray));
    
    var datesArray = this.dashboardResponse.lastOneWeekDetails
      .map(item => item.date)
      .filter((value, index, self) => 
        //console.log('value: '+JSON.stringify(self));
        self.indexOf(value) === index);
      datesArray.sort();      
      datesArray.reverse();    
      console.log(datesArray)  
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

    this.dashboardService.findDashboardData(this.loggedUserData.divisionCode).subscribe(response => {
      this.dashboardResponse = response;      
      if(response){
       console.log(JSON.stringify(response));
        this.startGraphs();
      }
     // this.spinnerService.hide();
    }, error => {
      console.log("ERROR >>> " + error)
    });
  }


}


export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
  url: string;
  icon: string
}
