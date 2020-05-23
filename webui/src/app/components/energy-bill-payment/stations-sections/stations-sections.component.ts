import { OnInit, Component, ViewChild } from '@angular/core';
import { StationsSectionsService } from 'src/app/services/stations-sections.service';
import { ReportService  } from "src/app/services/report.service";
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators} from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { StationsSectionsModel } from 'src/app/models/stations-sections.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';

@Component({
    selector: 'stations-sections',
    templateUrl: './stations-sections.component.html',
    styleUrls: ['./stations-sections.component.scss']
})
export class StationsSectionsComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addStationsSections: boolean ;
    title: string = "Save";
    stationsSectionsFormGroup: FormGroup;
    stationsSectionsList : any;
    divisionsList:any;
    stationsSectionsDataSource: MatTableDataSource<StationsSectionsModel>;
    stationsSectionsDisplayColumns = ['sno' , 'stationCode' , 'stationName' , 'majorSectionRoute' , 'upSection' , 'upSectionName' , 'dnSection','dnSectionName','division' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editstationsSectionsResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;


    constructor(
        private stationsSectionsService: StationsSectionsService,
        private reportService: ReportService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog
    ){

    }

    ngOnInit () {
        console.log('in ngOnintit method:::');
        this.getAllStationsSectionsData();
        this.divisionDetails();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","Stations-sections") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); 
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.stationsSectionsFormGroup = this.formBuilder.group({
            id: 0,
            'stationCode':[null,Validators.maxLength(250)],
            'stationName': [null,Validators.maxLength(250)],
            'majorSectionRoute': [null,Validators.maxLength(250)],
            'upSection': [null,Validators.maxLength(250)],
            'upSectionName' : [null,Validators.maxLength(250)],
            'dnSection' : [null,Validators.maxLength(250)],
            'dnSectionName': [null,Validators.maxLength(250)],
            'division' : [null,Validators.maxLength(250)]
        });
    }

    getAllStationsSectionsData() {
        const stationsSections : StationsSectionsModel[] = [];
        this.stationsSectionsService.getAllStationsSections().subscribe((data) => {
            this.stationsSectionsList = data;
            for (let i = 0; i < this.stationsSectionsList.length; i++) {
                this.stationsSectionsList[i].sno = i+1;
                stationsSections.push(this.stationsSectionsList[i]);              
            }
            this.stationsSectionsDataSource = new MatTableDataSource(stationsSections);
            this.stationsSectionsDataSource.paginator = this.paginator;
            this.stationsSectionsDataSource.sort = this.sort;

        } , error => {});

    }

    stationsSectionsSubmit () {
        console.log("stationsSectionsSubmit");
        let stationCode: string = this.stationsSectionsFormGroup.value.stationCode;
        let stationName: string = this.stationsSectionsFormGroup.value.stationName;
        let majorSectionRoute: string = this.stationsSectionsFormGroup.value.majorSectionRoute;
        let upSection: string = this.stationsSectionsFormGroup.value.upSection;
        let upSectionName: string = this.stationsSectionsFormGroup.value.upSectionName;
        let dnSection: string = this.stationsSectionsFormGroup.value.dnSection;
        let dnSectionName: string = this.stationsSectionsFormGroup.value.dnSectionName;
        let division: string = this.stationsSectionsFormGroup.value.division;
       
        this.addStationsSections = false;
        console.log("AddSta"+this.addStationsSections);
        if (this.title ==  Constants.EVENTS.SAVE) {
            console.log("this.title"+this.title);
            this.stationsSectionsService.saveStationsSections({
                'stationCode':stationCode,
                'stationName': stationName,
                'majorSectionRoute':majorSectionRoute,
                'upSection': upSection,
                'upSectionName' : upSectionName,
                'dnSection': dnSection,
                'dnSectionName' : dnSectionName,
                'division' : division
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllStationsSectionsData();
                this.stationsSectionsFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editstationsSectionsResponse.id;
            this.stationsSectionsService.updateStationSections({
                'id':id,
                'stationCode':stationCode,
                'stationName': stationName,
                'majorSectionRoute':majorSectionRoute,
                'upSection': upSection,
                'upSectionName' : upSectionName,
                'dnSection': dnSection,
                'dnSectionName' : dnSectionName,
                'division' : division
            }).subscribe((data) =>{
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllStationsSectionsData();
                this.stationsSectionsFormGroup.reset();
                this.addStationsSections =  false;
            } , error => {})
            
        }
    }

    editStationsSections (id) {
        this.addStationsSections = true;
        this.stationsSectionsEditAction(id);
        this.title = 'Update';
    }

    stationsSectionsEditAction(id: number) {
        this.stationsSectionsService.findStationSectionsById(id).subscribe((responseData) => {
            this.editstationsSectionsResponse = responseData;
            this.stationsSectionsFormGroup.patchValue({
                id: this.editstationsSectionsResponse.id,
                stationCode:this.editstationsSectionsResponse.stationCode,
                stationName: this.editstationsSectionsResponse.stationName,
                majorSectionRoute: this.editstationsSectionsResponse.majorSectionRoute,
                upSection: this.editstationsSectionsResponse.upSection,
                upSectionName: this.editstationsSectionsResponse.upSectionName,
                dnSection: this.editstationsSectionsResponse.dnSection,
                dnSectionName: this.editstationsSectionsResponse.dnSectionName,
                division: this.editstationsSectionsResponse.division
            })
        } ,error => {})
    }


    deleteStationsSections (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected stations Sections?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.stationsSectionsService.deleteStationSections(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Stations Sections Deleted Successfully');
                        this.getAllStationsSectionsData();
                    },error => {});
            }
        });
    }
    divisionDetails()
    {
          
           this.reportService. divisionDetails().subscribe((data) => {
             this.divisionsList = data;
    }
           );

   }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.stationsSectionsDataSource.filter = filterValue;
    }

    onGoBack() {
        this.stationsSectionsFormGroup.reset();
        this.addStationsSections = false;
        this.title = 'Save';
    }


    NewStationsSections () {
        this.addStationsSections = true;
    }

}