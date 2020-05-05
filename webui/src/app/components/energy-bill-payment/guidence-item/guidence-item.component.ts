import { OnInit, Component, ViewChild } from '@angular/core';
import { GuidenceItemService } from 'src/app/services/guidence-item.service';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Constants } from 'src/app/common/constants';
import { GuidenceItemModel } from 'src/app/models/guidence-item.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { ReportService } from 'src/app/services/report.service';

@Component({
    selector: 'guidence-item',
    templateUrl: './guidence-item.component.html',
    styleUrls: ['./guidence-item.component.scss']
})
export class GuidenceItemComponent implements OnInit{

    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addGuidenceItem: boolean ;
    title: string = "Save";
    guidenceItemFormGroup: FormGroup;
    guidenceItemList : any;
    guidenceItemDataSource: MatTableDataSource<GuidenceItemModel>;
    guidenceItemDisplayColumns = ['sno' , 'agencyRbRdso' , 'date' , 'detailsOfIssue' , 'heading' , 'closedRemark' , 'status' , 'id' ] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    editguidenceItemResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    statusItems: any;


    constructor(
        private _guidenceItemService: GuidenceItemService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private dialog: MatDialog,
        private reportService: ReportService
    ){

    }

    ngOnInit () {
        this.getAllGuidenceItemData();
        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","GUIDENCE ITEM") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName); //getPermission("Add", );
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        this.guidenceItemFormGroup = this.formBuilder.group({
            id: 0,
            'agencyRbRdso':[null],
            'date': [null],
            'detailsOfIssue': [null],
            'heading': [null],
            'letterNo' : [null],
            'reportContinue' : [null],
            'response': [null],
            'shortDescription' : [null],
            'status' : [null],
            'closedRemark' : [null]
        });
        this.reportService.statusItemDetails('GUIDENCE_ITEM').subscribe((data) => {
                 this.statusItems = data;
      		});
    }

    getAllGuidenceItemData() {
        console.log("get all guidence items");
        const guidenceItem : GuidenceItemModel[] = [];
        this._guidenceItemService.getAllGuidenceItems().subscribe((data) => {
            this.guidenceItemList = data;
            for (let i = 0; i < this.guidenceItemList.length; i++) {
                this.guidenceItemList[i].sno = i+1;
                guidenceItem.push(this.guidenceItemList[i]);              
            }
            this.guidenceItemDataSource = new MatTableDataSource(guidenceItem);
            this.guidenceItemDataSource.paginator = this.paginator;
            this.guidenceItemDataSource.sort = this.sort;

        } , error => {});

    }

    gudenceItemSubmit () {
        let agencyRbRdso: string = this.guidenceItemFormGroup.value.agencyRbRdso;
        let date: Date = this.guidenceItemFormGroup.value.date;
        let detailsOfIssue: string = this.guidenceItemFormGroup.value.detailsOfIssue;
        let heading: string = this.guidenceItemFormGroup.value.heading;
        let letterNo: string = this.guidenceItemFormGroup.value.letterNo;
        let reportContinue: string = this.guidenceItemFormGroup.value.reportContinue;
        let response: string = this.guidenceItemFormGroup.value.response;
        let shortDescription: string = this.guidenceItemFormGroup.value.shortDescription;
        let status: string = this.guidenceItemFormGroup.value.status;
        let closedRemark: string = this.guidenceItemFormGroup.value.closedRemark;
        this.addGuidenceItem = false;
        
        if (this.title ==  Constants.EVENTS.SAVE) {
            this._guidenceItemService.save({
                'agencyRbRdso':agencyRbRdso,
                'detailsOfIssue': detailsOfIssue,
                'date':date,
                'heading': heading,
                'letterNo' : letterNo,
                'reportContinue': reportContinue,
                'response' : response,
                'shortDescription' : shortDescription,
                'status' : status,
                'closedRemark' : closedRemark
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Successfully saved');
                this.getAllGuidenceItemData();
                this.guidenceItemFormGroup.reset();
            } , error => {});
        }else if (this.title == Constants.EVENTS.UPDATE ) {
            let id: number = this.editguidenceItemResponse.id;
            this._guidenceItemService.update({
                'id':id,
                'agencyRbRdso':agencyRbRdso,
                'detailsOfIssue': detailsOfIssue,
                'date':date,
                'heading': heading,
                'letterNo' : letterNo,
                'reportContinue': reportContinue,
                'response' : response,
                'shortDescription' : shortDescription,
                'status' : status,
                'closedRemark' : closedRemark
            }).subscribe((data) =>{
                this.commonService.showAlertMessage('Successfully updated');
                this.getAllGuidenceItemData();
                this.guidenceItemFormGroup.reset();
                this.addGuidenceItem =  false;
            } , error => {})
            
        }
    }

    editGuidenceItem (id) {
        this.addGuidenceItem = true;
        this.guidenceItemEditAction(id);
        this.title = 'Update';
    }

    guidenceItemEditAction(id: number) {
        this._guidenceItemService.findGuidenceItemById(id).subscribe((responseData) => {
            this.editguidenceItemResponse = responseData;
            this.guidenceItemFormGroup.patchValue({
                id: this.editguidenceItemResponse.id,
                agencyRbRdso:this.editguidenceItemResponse.agencyRbRdso,
                detailsOfIssue: this.editguidenceItemResponse.detailsOfIssue,
                date: this.editguidenceItemResponse.date,
                heading: this.editguidenceItemResponse.heading,
                letterNo: this.editguidenceItemResponse.letterNo,
                reportContinue: this.editguidenceItemResponse.reportContinue,
                response: this.editguidenceItemResponse.response,
                shortDescription: this.editguidenceItemResponse.shortDescription,
                status: this.editguidenceItemResponse.status,
                closedRemark: this.editguidenceItemResponse.closedRemark
            })
        } ,error => {})
    }


    deleteGuidenceItem (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected guidence item?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this._guidenceItemService.deleteGuidenceItem(id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Guidence Item Deleted Successfully');
                        this.getAllGuidenceItemData();
                    },error => {});
            }
        });
    }


    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.guidenceItemDataSource.filter = filterValue;
    }

    onGoBack() {
        this.guidenceItemFormGroup.reset();
        this.addGuidenceItem = false;
        this.title = 'Save';
    }

    NewGuidenceItem () {
        this.addGuidenceItem = true;
    }

}