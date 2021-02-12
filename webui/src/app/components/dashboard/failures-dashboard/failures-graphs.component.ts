import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-failures-graphs',
  templateUrl: './failures-graphs.component.html',
  styleUrls: ['./failures-graphs.component.css']
})
    
export class FailuresGraphsComponent implements OnInit {

    cbFailureResponse = {};
    requestBody: any;
    CBTrippingDataSource: any;
    width = 100;
    height = 400;
    type = "stackedcolumn3d";
    dataFormat = "json";
    showCBTrippingChart : boolean = false;

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
        queryType:'CB_FAILURE'    
    }
    this.sendAndRequestService.requestForPOST(Constants.app_urls.DASHBOARD.GET_GRAPHS_DATA, this.requestBody, false).subscribe((data) => {
      console.log(data);
      this.cbFailureResponse = data;
      if(this.cbFailureResponse) {
        this.CBTrippingDataSource = this.prepareTowerCarGraphData(this.cbFailureResponse);
          //console.log('*** json values  ***'+JSON.stringify(this.CBTrippingDataSource));
          if(this.CBTrippingDataSource){
            this.showCBTrippingChart = true;    
          }
        //this.showReportBotton = true; 
      }
    })    
  }
  
    prepareTowerCarGraphData(data: any){
        let dataSet = [];
        let seriesName = [];
        let dataCount = [];
        let dataDuration = [];
        for (let i=0;i<data.length;i++){
            this.width = this.width +100;
            dataCount.push(this.cbFailureResponse[i].count);
            dataDuration.push(this.cbFailureResponse[i].sum);
            seriesName.push(this.cbFailureResponse[i].depotName);
        }
        //console.log('*** legth ***'+seriesName.length);
        for (let i=0;i<seriesName.length;i++) {
            dataSet.push({
                seriesname: seriesName[i],
                  data: [
                    {
                      value: dataCount[i]
                    },
                    {
                      value: dataDuration[i]
                    }
                ]    
            })
        }
        return {
            chart: {
            caption: "CB Trippings",
            //xaxisname: "names",
            //yaxisname: "Values",
            formatnumberscale: "1",
             showValues: "1",
             rotateValues: "1",
             // placevaluesinside: "1",
                valueFontColor: "#000000",
            plottooltext:
              "<b>$seriesName</b> <b>$label</b> is $dataValue",
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
                  label: "Count"
                },
                {
                  label: "Duration"
                }
              ]
            }
          ],
          dataset: dataSet  
        }
    }
  
}