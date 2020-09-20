import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ReportParameterModel } from 'src/app/models/reportParameter.model';
import { ReportPayload } from 'src/app/payloads/report.payload';
import { ReportModel } from 'src/app/models/report.model';
import { FailuresTableModel } from 'src/app/models/failures-table.model';
import { ProductModel } from 'src/app/models/product.model';
import { ScheduleModel } from 'src/app/models/schedule.model';
import { PreviousRouteService } from 'src/app/services/previousRoute.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { Location } from '@angular/common';
import { FacilityModel } from 'src/app/models/facility.model';

@Component({
       selector: 'app-report-by-query',
       templateUrl: 'report-by-query.component.html',
       styleUrls: ['./report-by-query.component.css']
})

export class ReportByQueryComponent implements OnInit {

       schedule: any;
       elementarySectionCode: any;
       department: any;
       oheFixedAsset: any;
       pbSwitchCode: any;
       assetId: any;
       assetType: any;
       zoneData: any;
       subDivisionData: any;
       divisionsData: any;
       parameterData: any;
       parameterNamesData: any;
       observationCategory: any;
       facilityData: any;
       failuresModel: any;
       productModel: ProductModel;
       scheduleData: ScheduleModel;
       submitedForm: any;
       facilityId: any;
       userHierarchy: any = JSON.parse(localStorage.getItem('userHierarchy'));
       reportModel: ReportModel;
       formValuses: any;
       zoneList: FacilityModel[] = [];
       divisionList: FacilityModel[] = [];
       subDivList: FacilityModel[] = [];
       facilityList: FacilityModel[] = [];
       pbSwitchControlData: any;
       userData: any = JSON.parse(localStorage.getItem('loggedUser'));
       userDefaultData: any;
       zoneObject: any;
       zoneCode: any;
       divisionObject: any;
       tpcBoardData: any;
       sub;/*It defines to store router map of subscribe*/
       id: any;  /* Its used to store the getting name on the report page  */
       otyp = ["Adobe portable Document Format(pdf)", "Comma Separated Value Text", "HTML Text", "Microsoft Excel", "Plain Text", "XML Text"]

       reportsByQuery: FormGroup;
       maxDate = new Date();
       constructor(
              private Activatedroute: ActivatedRoute,
              private formBuilder: FormBuilder,
              private location: Location,
              private router: Router,
              private sendAndRequestService: SendAndRequestService,
              private previousRouterUrl: PreviousRouteService) { }

       ngOnInit() {
              let previousUrl = '/' + this.router.url.split('/')[1];// this.previousRouterUrl.getPreviousUrl();

              console.log(this.router.url.split('/')[1])
              this.reportModel = new ReportModel();
              console.log(previousUrl)
              this.zoneCodeList();
              if (previousUrl === '/daily-progress-reports') {
                     this.assetType = 'TowerCar';
              }
              else if (previousUrl === '/asset-reports') {
                     this.assetType = 'OHE_FIXED_ASSET';
              }
              else if (previousUrl === '/asset-master-reports') {
                     this.assetType = 'OHE_FIXED_ASSET';
              }
              else if (previousUrl === '/psi-reports') {
                     this.assetType = 'PSI_FIXED_ASSET'
              }
              console.log(this.assetType)

              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ASSET_TYPES + this.assetType).subscribe((data) => {
                     this.assetType = data;
              })
              this.submitedForm = this.formBuilder.group({});
              this.sub = this.Activatedroute.paramMap.subscribe(params => {
                     this.id = params.get('reportId');
              });
              console.log('id = ' + this.id)
              this.facilityNames();
              this.elementarySections();
              this.observationCheckList();
              this.observationCategories();
              this.powerBlocks();
              this.pbSwitchControl();
              this.tpcBoardDetails();


              this.reportParameterNames();

              ReportPayload.GET.reportId = this.id;
              for (let i = 0; i < this.userHierarchy.length; i++) {
                     if (this.userHierarchy[i].depotType == 'ZONE') {
                            this.zoneList.push(this.userHierarchy[i]);
                     }
              }
              for (let i = 0; i < this.userHierarchy.length; i++) {
                     if (this.userHierarchy[i].depotType == 'DIV') {
                            this.divisionList.push(this.userHierarchy[i]);
                     }
              }
              for (let i = 0; i < this.userHierarchy.length; i++) {
                     if (this.userHierarchy[i].depotType == 'SUBDIV') {
                            this.subDivList.push(this.userHierarchy[i]);
                     }
              }
              this.reportsByQuery = this.formBuilder.group({
                     'fromDate': [null],
                     'toDate': [null],
                     'zone': [null],
                     'division': [null],
                     'subDivision': [null],
                     'depot': [null],
                     'department': [null],
                     'observationCategory': [null],
                     'scheduleType': [null],
                     'assetType': [null],
                     'assetId': [null],
                     'scheduleDate': [null],
                     'switchType': [null],
                     'elementarySection': [null],
                     'tpcBoard': [null],
                     'elementarySectionCode': [null],
                     'activityType': [null],
                     'year': [null],
                     'fromkm': [null],
                     'tokm': [null],
                     'materialItem': [null],
              });
       }
       selectedValue($event, Type) {
              if ($event.value) {
                     switch (Type) {
                            case 'ZONE': {
                                   this.divisionCode($event.value);
                                   break;
                            }
                            case 'DIVISION': {
                                   this.subDivision($event.value);
                                   break;
                            }
                            case 'SUB DIVISION': {
                                   this.facility($event.value);
                                   break;
                            }                           
                     }
              }
       }
       reportParameterNames() {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_REPORT_PARAMETER_BASED_ON_REPORT_ID + this.id).subscribe((data) => {
                     this.parameterData = data;
              })
       }
       tpcBoardDetails() {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_TPC_BOARD_DETAILS).subscribe((data) => {
                     this.tpcBoardData = data;
              })
       }
       facilityNames() {
              const facilityData: ReportParameterModel[] = [];
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_NAMES).subscribe((data) => {
                     this.facilityData = data;
              }
              );

       }
       powerBlocks() {
              const failuresModel: FailuresTableModel[] = [];
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_POWER_BLOCKS).subscribe((data) => {
                     this.failuresModel = data;
              }
              );

       }
       observationCategories() {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_OBS_CATEGORIES).subscribe((data) => {
                     this.department = data;
              }
              );

       }
       zoneCodeList() {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_LIST).subscribe((data) => {
                     this.zoneData = data;
                     if (data) {
                            this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_USER_DEFAULT_DATA + this.userData.userName).subscribe((data) => {
                                   this.userDefaultData = data;
                                   if (this.userDefaultData.zone) {
                                          this.zoneCode = this.userDefaultData.zone;
                                          this.reportModel.zone = this.userDefaultData.zone;
                                          this.divisionCode(this.userDefaultData.zone);
                                   }
                                   if (this.userDefaultData.division) {
                                          this.reportModel.division = this.userDefaultData.division;
                                          this.subDivision(this.userDefaultData.division);
                                   }

                                   if (this.userDefaultData.subDivision) {
                                          this.reportModel.subDivision = this.userDefaultData.subDivision;
                                          this.facility(this.userDefaultData.subDivision);
                                   }
                            },
                                   error => error => {
                                          console.log(' >>> ERROR ' + error);
                                   })
                     }
              });
       }
       observationCheckList() {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_OBS_CHECK_LIST).subscribe((data) => {
                     this.observationCategory = data;
              }
              );

       }
       elementarySections() {

              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ELEMENTARY_SECTIONS).subscribe((data) => {
                     this.elementarySectionCode = data;
              }
              );

       }
       pbSwitchControl() {

              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_PB_SWITCH_CONTROL).subscribe((data) => {
                     this.pbSwitchControlData = data;
              }
              );

       }


       public activityType = ['measurement', 'activity', 'multi_measure_activity'];
       public pbSwitchType = ['REMOTE', 'MANUAL'];

       submitReportsByQuery() {

              this.reportModel.reportId = this.id;
              this.reportModel.zone = this.reportsByQuery.controls.zone.value;
              this.reportModel.division = this.reportsByQuery.controls.division.value;
              this.reportModel.subDivision = this.reportsByQuery.controls.subDivision.value;
              this.reportModel.fromDate = this.reportsByQuery.controls.fromDate.value;
              console.log("generateReport" + this.id)
              console.log("generateReport" + JSON.stringify(this.reportModel));
              this.submitedForm = "";
              this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_REPORT, this.reportModel, true)
                     .subscribe((response) => {
                            this.submitedForm = response;
                            let pdfWindow = window.open("download", "");
                            let content = encodeURIComponent(this.submitedForm.outputData);
                            let iframeStart = "<\iframe width='100%' height='100%' src='data:application/pdf;base64, ";
                            let iframeEnd = "'><\/iframe>";
                            pdfWindow.document.write(iframeStart + content + iframeEnd);
                     },
                            error => error => {
                                   console.log(' >>> ERROR ' + error);
                            })
       }
       schAssetType(productCategoryMemObj: any) {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_SCHEDULE_CODE_BASED_ON_ASSETTYPE + productCategoryMemObj).subscribe((data) => {
                     this.schedule = data;
              }
              )
       }
       schAssetIdAndType(scheduleCode: any) {
              this.sendAndRequestService.requestForPOST(Constants.app_urls.REPORTS.GET_ASSETID_BASED_ON_SCHEDULE_CODES_AND_ASSETTYPES, scheduleCode, false).subscribe((data) => {
                     this.assetId = data;

              }
              )
       }
       divisionCode(zoneCode: any) {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_ZONE_OBJECT + zoneCode).subscribe((data) => {
                     this.zoneObject = data;
                     this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_BASED_ON_ZONE + this.zoneObject.id).subscribe((data) => {
                            this.divisionsData = data;
                     })
              })
       }
       subDivision(divisionCode: any) {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_DIVISION_OBJECT + divisionCode).subscribe((data) => {
                     this.divisionObject = data;
                     this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_SUBDIVISION_BASED_ON_DIVISION + this.divisionObject.id).subscribe((data) => {
                            this.subDivisionData = data;
                     })
              })
       }
       facility(code: any) {
              this.sendAndRequestService.requestForGET(Constants.app_urls.REPORTS.GET_FACILITY_BASED_ON_SUBDIVISION + code).subscribe((data) => {
                     this.facilityId = data;
              }
              )
       }
       onGoBack() {
              this.location.back();
       }
}