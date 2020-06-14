import { Component, OnInit, Input } from '@angular/core';
import { ReportService  } from "src/app/services/report.service";
import {ActivatedRoute } from '@angular/router';
import{Router} from '@angular/router';
import { FormBuilder} from '@angular/forms';
import {ReportParameterModel} from 'src/app/models/reportParameter.model';
import { ReportPayload } from 'src/app/payloads/report.payload';
import { ReportModel } from 'src/app/models/report.model';
import { FacilityModel } from 'src/app/models/facility.model';
import {FailuresTableModel}from 'src/app/models/failures-table.model';
import { ProductModel } from 'src/app/models/product.model';
import { ScheduleModel } from 'src/app/models/schedule.model';
import { PreviousRouteService } from 'src/app/services/previousRoute.service';

@Component({
    selector: 'app-reportParameterDisplay',
    templateUrl: 'reportParameterDisplay.component.html',
    styleUrls: ['./reportParameterDisplay.component.css']
})

export class ReportParameterDisplayComponent implements OnInit {
    
     schedule:any;
     elementarySectionCode:any;
     department:any;
     oheFixedAsset:any;
     pbSwitchCode:any;
     assetId:any;
     assetType:any;
     zoneData:any;
     subDivisionData:any;
     divisionsData:any;
     parameterData:any;
     observationCategory:any;
     facilityData:FacilityModel;
     failuresModel:FailuresTableModel;
     productModel:ProductModel;
     scheduleData:ScheduleModel;
     submitedForm:any;
     facilityId:any;
     reportModel: ReportModel;
     formValuses: any;
     pbSwitchControlData:any;
     sub;/*It defines to store router map of subscribe*/
     id;  /* Its used to store the getting name on the report page  */
     otyp=["Adobe portable Document Format(pdf)","Comma Separated Value Text","HTML Text","Microsoft Excel","Plain Text","XML Text"]
    constructor(private reportService: ReportService,
       private Activatedroute:ActivatedRoute,
        private router:Router,
        private formBuilder: FormBuilder ,
        private previousRouterUrl: PreviousRouteService) { }
     
    ngOnInit() {        
       let previousUrl = this.previousRouterUrl.getPreviousUrl();
       console.log('previousUrl::::'+previousUrl);
       
       if(previousUrl === '/daily-progress-reports'){
              console.log("routerUrl"+this.router.url);
              this.assetType = 'TowerCar';     
       }
       else if(previousUrl === '/asset-reports') {
              this.assetType = 'OHE_FIXED_ASSET';      
       }
       else if(previousUrl === '/asset-master-reports') {
              this.assetType = 'OHE_FIXED_ASSET';      
       }
       else if(previousUrl === '/psi-reports'){
              this.assetType = 'PSI_FIXED_ASSET'
       }
      
       this.reportService.allAssetTypeReports(this.assetType).subscribe((data)=>{
         this.assetType =data;
     })
        this.reportModel = new ReportModel();
        this.submitedForm=this.formBuilder.group({});
        this.facilityNames();
        this.elementarySections();
        this.observationCheckList();
        this.observationCategories();
        this.powerBlocks();
        this.pbSwitchControl();
        this.zoneList();
        this.sub=this.Activatedroute.paramMap.subscribe(params => { 
            console.log(params);
             this.id = params.get('id'); 
             console.log("this is Getting Report Name :::"+this.id);   
         });
         
   
         this.reportParameterNames();
        
      ReportPayload.GET.reportId = this.id;
        // ReportPayload.GET.zone = "kHG";
        //ReportPayload.GET.division = "hyd";
        // ReportPayload.GET.reportHeader = "REport";
         //ReportPayload.GET.facilityId = "30017";
         //ReportPayload.GET.permission="View";
       
    }
   reportParameterNames()
         {
                const parameterData : FacilityModel[] = [];
                console.log('facilityModel');
                this.reportService. reportParameterNames().subscribe((data) => {
                  this.parameterData = data;
                  
                 // console.log('parameter Data '+JSON.stringify(data))
         }
                );

        }
        facilityNames()
        {
               const facilityData : ReportParameterModel[] = [];
               console.log('facilityData27-12-19');
               this.reportService. facilityNames().subscribe((data) => {
                 this.facilityData = data;
                // console.log('facility27-19-19'+this.facilityData);
                // console.log('facilityData '+JSON.stringify(data))
        }
               );

       }
       powerBlocks()
        {
               const failuresModel : FailuresTableModel[] = [];
               //console.log('failuresModel');
               this.reportService. powerBlocks().subscribe((data) => {
                 this.failuresModel = data;
                 //console.log('failuresModel'+this.facilityData);
                 
        }
               );

       }
       observationCategories()
        {
               
               this.reportService. observationCategories().subscribe((data) => {
                 this.department = data;
        }
               );

       }
       zoneList()
        {
              
               this.reportService. zoneList().subscribe((data) => {
                 this.zoneData = data;
        }
               );

       }
       observationCheckList()
        {
               
               this.reportService. observationCheckList().subscribe((data) => {
                 this.observationCategory = data;
        }
               );

       }
       elementarySections()
       {
               
              this.reportService. elementarySections().subscribe((data) => {
                this.elementarySectionCode = data;
       }
              );

      }
      pbSwitchControl()
      {
              
             this.reportService. pbSwitchControl().subscribe((data) => {
               this.pbSwitchControlData = data;
      }
             );

     }


      public activityType=['measurement','activity','multi_measure_activity'];
      public pbSwitchType=['REMOTE','MANUAL'];
      
      generateReport()
       {   

          this.reportModel.reportId=this.id;
          console.log("facilityId6-1-2020:::"+JSON.stringify(this.reportModel));
          this.submitedForm = "";
          console.log("Report Model::2-1-2020:::"+this.reportModel);
          this.reportService.generateReport(this.reportModel).subscribe((response) => {
          this.submitedForm = response;
          let pdfWindow = window.open("download","");
          let content = encodeURIComponent(this.submitedForm.outputData);
                    // let content=JSON.stringify(ReportPayload.GET.outputData);
          let iframeStart = "<\iframe width='100%' height='100%' src='data:application/pdf;base64, ";
          let iframeEnd= "'><\/iframe>";
          pdfWindow.document.write(iframeStart + content + iframeEnd);
          },
          error => error => {        
          console.log(' >>> ERROR ' + error);
            }) 
        }
        schAssetType(productCategoryMemObj: Object){
              //let values = Object.values(productCategoryMemObj)[9] ;
              var assetType = JSON.stringify(productCategoryMemObj);
              
              this.reportService.getScheduleCodesBasedOnAssetType(assetType).subscribe((data) => {
                     this.schedule = data;
              }    
              )
        }
        schAssetIdAndType(assetsScheHistObj: Object){
              var scheduleCode = JSON.stringify(assetsScheHistObj);
              this.reportService.getAssetIdBasedOnScheduleCodesAndAssetTypes(scheduleCode).subscribe((data) => {
                 this.assetId=data;    
              }    
              )
        }
        divisionCode(code: any){
              var zone = JSON.stringify(code);
              this.reportService.getDivisionBasedOnZone(zone).subscribe((data) => {
                this.divisionsData=data;   
              }    
              )
        }
        subDivision(code: any){
              var division = JSON.stringify(code);
              this.reportService.getSubDivisionBasedOnDivision(division).subscribe((data) => {
                this.subDivisionData=data;   
              }    
              )
        }
        facility(code: any){
             var subDivision = JSON.stringify(code);
             this.reportService.getFacilityBasedOnSubDivision(subDivision).subscribe((data) => {
               this.facilityId=data;
             }    
             )
       }
        
       onGoBack(){
              
       }
       }
        