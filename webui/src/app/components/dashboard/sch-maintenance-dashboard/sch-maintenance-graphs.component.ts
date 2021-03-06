import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-sch-maintenance-graphs',
  templateUrl: './sch-maintenance-graphs.component.html',
  styleUrls: ['./sch-maintenance-graphs.component.css']
})
    
export class SchMaintenanceGraphsComponent implements OnInit {

    towerCarResponse: any;
    requestBody: any;
    towerCarDataSource: any;
    width = 200;
    height = 400;
    type = "mscolumn3d";
    dataFormat = "json";
    showTowerCarChart : boolean = false;

    constructor(
    private formBuilder: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private sendAndRequestService: SendAndRequestService
  ) {
  
  }
  
  ngOnInit() {
    this.getTowerCarGraphsData();
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
            this.width = this.width +200;
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
             // placevaluesinside: "1",
                valueFontColor: "#000000",
            plottooltext:
              "<b>$dataValue</b> <b>$seriesName</b> in $label",
            theme: "fusion"
          },
          data: [
            {
              tooltext: "<b>$seriesName</b>"
            }
          ],    
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
  
}