import { OnInit, Component, ViewChild } from '@angular/core';
import { DailySummaryService } from 'src/app/services/daily-summary.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { DailySummaryModel } from 'src/app/models/daily-summary.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { ReportService  } from "src/app/services/report.service";
import { FacilityModel } from 'src/app/models/facility.model';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
    selector: 'daily-summary',
    templateUrl: './daily-summary.component.html',
    styleUrls: ['./daily-summary.component.scss']
})
export class DailySummaryComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addDailySummary: boolean ;
    title: string = "Save";
    dailySummaryFormGroup: FormGroup;
    dailySummaryList : any;
    facilityData:any;
    dailySummaryDataSource: MatTableDataSource<DailySummaryModel>;
    dailySummaryDisplayColumns = ['sno' , 'createdDate' , 'facilityId' , 'nameOfStaff' , 'dayProgress' , 'npbProgress' , 'psiProgress' , 'tomorrowForecast','remarks','id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editdailySummaryResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;



    constructor(
        private dailySummaryService: DailySummaryService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private reportService:ReportService,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","DAILY SUMMARY") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
        this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.facilityNames();
        this.dailySummaryFormGroup = this.formBuilder.group({
            id: 0,
            'createdDate':[null],
            'facilityId':[null],
            'nameOfStaff':[null],
            'dayProgress': [null],
            'npbProgress': [null],
            'psiProgress': [null],
            'tomorrowForecast' : [null],
            'remarks' : [null]
        });
    }

    getAllDailySummaryData() {
        const dailyProgressSummery : DailySummaryModel[] = [];
        this.dailySummaryService.getAllDailySummary().subscribe((data) => {
            this.dailySummaryList = data;
            for (let i = 0; i < this.dailySummaryList.length; i++) {
                this.dailySummaryList[i].sno = i+1;
                dailyProgressSummery.push(this.dailySummaryList[i]);              
            }
            this.dailySummaryDataSource = new MatTableDataSource(dailyProgressSummery);
            this.dailySummaryDataSource.paginator = this.paginator;
            this.dailySummaryDataSource.sort = this.sort;

        } , error => {});

    }

    dailySummarySubmit() {
        let createdDate: Date = this.dailySummaryFormGroup.value.createdDate;
        let facilityId: string = this.dailySummaryFormGroup.value.facilityId;
        let nameOfStaff: string = this.dailySummaryFormGroup.value.nameOfStaff;
        let dayProgress: string = this.dailySummaryFormGroup.value.dayProgress;
        let npbProgress: string = this.dailySummaryFormGroup.value.npbProgress;
        let psiProgress: Date = this.dailySummaryFormGroup.value.psiProgress;
        let tomorrowForecast: string = this.dailySummaryFormGroup.value.tomorrowForecast;
        let remarks: string = this.dailySummaryFormGroup.value.remarks;
        this.addDailySummary = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            this.dailySummaryService.save({
                'createdDate':createdDate,
                'facilityId':facilityId,
                'nameOfStaff': nameOfStaff,
                'dayProgress': dayProgress,
                'npbProgress':npbProgress,
                'psiProgress':psiProgress,
                'tomorrowForecast':tomorrowForecast,
                'remarks' : remarks
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllDailySummaryData();
                this.dailySummaryFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editdailySummaryResponse.id;
            this.dailySummaryService.update({
                'id':id,
                'createdDate':createdDate,
                'facilityId':facilityId,
                'nameOfStaff': nameOfStaff,
                'dayProgress':dayProgress,
                'npbProgress': npbProgress,
                'psiProgress' : psiProgress,
                'tomorrowForecast' : tomorrowForecast,
                'remarks': remarks
            }).subscribe((data) =>{
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllDailySummaryData();
                this.dailySummaryFormGroup.reset();
                this.addDailySummary =  false;
            } , error => {})
            
        }
    }

    editDailySummaryItem (id) {
        this.addDailySummary = true;
        this.dailysummaryEditAction(id);
        this.title = 'Update';
    }

    dailysummaryEditAction(id: number) {
        this.dailySummaryService.findDailySummaryById(id).subscribe((responseData) => {
            this.editdailySummaryResponse = responseData;
            this.dailySummaryFormGroup.patchValue({
                id: this.editdailySummaryResponse.id,
                createdDate:this.editdailySummaryResponse.createdDate,
                facilityId:this.editdailySummaryResponse.facilityId,
                nameOfStaff: this.editdailySummaryResponse.nameOfStaff,
                dayProgress: this.editdailySummaryResponse.dayProgress,
                npbProgress: this.editdailySummaryResponse.npbProgress,
                psiProgress: this.editdailySummaryResponse.psiProgress,
                tomorrowForecast: this.editdailySummaryResponse.tomorrowForecast,
                remarks: this.editdailySummaryResponse.remarks
            })
        } ,error => {})
    }


    deleteDailySummary (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected Daily Summary?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.dailySummaryService.deleteDailySummary(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('DailySummary Deleted Successfully');
                        this.getAllDailySummaryData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.dailySummaryDataSource.filter = filterValue;
    }

    onGoBack() {
        this.dailySummaryFormGroup.reset();
        this.addDailySummary = false;
        this.title = 'Save';
    }
    facilityNames()
        {
               const facilityData : FacilityModel[] = [];
               this.reportService. facilityNames().subscribe((data) => {
                 this.facilityData = data;
        }
               );

       }

       NewDailySummary () {
        this.addDailySummary = true;
    }

}

    

    


   


    
    

   

