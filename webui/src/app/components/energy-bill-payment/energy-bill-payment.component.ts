import { OnInit, Component, ViewChild } from '@angular/core';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EnergyBillPaymentService } from 'src/app/services/energy-bill-payment.service';
import { EnergyBillPaymentModel } from 'src/app/models/energy-bill-payment.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Constants } from 'src/app/common/constants';
import { MatTableDataSource, MatDialogRef, MatDialog, MatPaginator, MatSort } from '@angular/material';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
    selector: 'energy-bill-payment',
    templateUrl: './energy-bill-payment.component.html',
    styleUrls: ['./energy-bill-payment.component.scss']
})
export class EnergyBillPaymentComponent implements OnInit{
    
    addPermission: boolean = true;
    editPermission: boolean = true;
    deletePermission: boolean = true;
    addEnergyBillPayment: boolean = false;
    saveEnergyBillPayment: boolean;
    energyBillPaymentFormGroup: FormGroup;
    title: string = "Save";
    data: any;
    energyBillPaymentDataSource: MatTableDataSource<EnergyBillPaymentModel>;
    eneBillPaymentList: any;
    eneBillPaymentDisplayedColumns = ['sno' ,  'amount' , 'dateOfPayment' , 'division' , 'month' , 'year' , 'id' ];
    editEneBillPaymentResponse: any;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    

    constructor(
        private commonService: CommonService,
        private dialog: MatDialog,
       // private _energyBillPaymentService: EnergyBillPaymentService,
       private sendAndRequestService : SendAndRequestService,
        private spinnerService: Ng4LoadingSpinnerService,
        private formBuilder: FormBuilder
    ){
        console.log('in constructor');
    }
    ngOnInit(){
    	var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY","ENERGY BILL PAYMENT") ;//p == 0 ? 'No Permission' : p[0].permissionName;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
        /* this.addPermission = this.commonService.getPermission("Add");
        this.editPermission = this.commonService.getPermission("Edit");
        this.deletePermission = this.commonService.getPermission("Delete"); */
        this.getEnergyBillPaymentData();
        this.spinnerService.show();
        this.energyBillPaymentFormGroup = this.formBuilder.group({
            id: 0,
            'amount': [null],
            'dateOfPayment':[null],
            'division': [null],
            month:[null],
            year: [null]
        })
       
    }


    addNewEnergyBillPayment() {
        this.addEnergyBillPayment = true;
    }

    onGoBack() {
        this.energyBillPaymentFormGroup.reset();
        // this.saveEnergyBillPayment = false;
       // this.cloneupdate = true;
        this.addEnergyBillPayment = false;
        this.title = 'Save';
      }

    getEnergyBillPaymentData() {
        const eneBillPayment: EnergyBillPaymentModel [] = [];
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.GET_ENERGY_BILL_PAYMENTS).subscribe((data) => {
            this.eneBillPaymentList = data;
            for (let i = 0; i < this.eneBillPaymentList.length; i++) {
                this.eneBillPaymentList[i].sno = i+1;
               eneBillPayment.push(this.eneBillPaymentList[i]);
            }
            this.energyBillPaymentDataSource = new MatTableDataSource(eneBillPayment);
            this.energyBillPaymentDataSource.paginator = this.paginator;
            this.energyBillPaymentDataSource.sort = this.sort;
        }, error => {
           this.spinnerService.hide();     
        }); 
    }
    
    onEnergyBillPaymentSubmit() {
        let amount: number = this.energyBillPaymentFormGroup.value.amount;
        let dateOfPayment: Date = this.energyBillPaymentFormGroup.value.dateOfPayment;
        let division: string = this.energyBillPaymentFormGroup.value.division;
        let month: string = this.energyBillPaymentFormGroup.value.month;
        let year: string = this.energyBillPaymentFormGroup.value.year;
        // this.saveEnergyBillPayment = false;
        console.log('title::'+this.title);
        if (this.title == Constants.EVENTS.SAVE) {
            this.sendAndRequestService.requestForPOST(Constants.app_urls.ENERGY_BILL_PAYMENTS.SAVE,{
                "amount":amount,
                "dateOfPayment":dateOfPayment,
                "division": division,
                "month": month,
                "year": year
            }, false).subscribe((data) => {
                this.data = data;
                this.commonService.showAlertMessage("Successfully saved");
                this.getEnergyBillPaymentData();
                this.energyBillPaymentFormGroup.reset();
                this.addEnergyBillPayment = false;
            }, error => {
                this.commonService.showAlertMessage("Error in Add")
            })
        }else if(this.title == Constants.EVENTS.UPDATE){
            console.log('in else if block::');
            let id: number = this.editEneBillPaymentResponse.id;
            this.sendAndRequestService.requestForPUT(Constants.app_urls.ENERGY_BILL_PAYMENTS.UPDATE,{
                "id":id,
                "amount":amount,
                "dateOfPayment":dateOfPayment,
                "division": division,
                "month": month,
                "year": year
            }, false).subscribe((data) => {
                this.data = data;
                this.commonService.showAlertMessage("Successfully updated");
                this.getEnergyBillPaymentData();
                this.energyBillPaymentFormGroup.reset();
                this.addEnergyBillPayment = false;
            } , error => {
                this.commonService.showAlertMessage("Error in update")
            });

        }
    }

    editEneBillPayment(id) {
        this.spinnerService.show();
        this.addEnergyBillPayment = true;
        this.eneBillPaymentEditAction(id);
        this.title = "Update";
        this.spinnerService.hide();
    }

    eneBillPaymentEditAction(id: number) {
        this.sendAndRequestService.requestForGET(Constants.app_urls.ENERGY_BILL_PAYMENTS.EDIT + id)
            .subscribe((responseData) => {
                this.editEneBillPaymentResponse = responseData;
                this.energyBillPaymentFormGroup.patchValue({
                    id: this.editEneBillPaymentResponse.id,
                    amount: this.editEneBillPaymentResponse.amount,
                    division: this.editEneBillPaymentResponse.division,
                    month: this.editEneBillPaymentResponse.month,
                    year: this.editEneBillPaymentResponse.year
                });
            } , error => {});

    }

    deleteEneBillPayment (id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
            disableClose: false
          });
        this.confirmDialogRef.componentInstance.confirmMessage = "Are you sure you want to delete the selected energy bill payment?";
        this.confirmDialogRef.afterClosed().subscribe(result => {
            if(result){
                this.sendAndRequestService.requestForDELETE(Constants.app_urls.ENERGY_BILL_PAYMENTS.DELETE ,id)
                    .subscribe((data) => {
                        this.commonService.showAlertMessage('Energy Bill Payment Deleted Successfully');
                        this.getEnergyBillPaymentData();
                    },error => {});
            }
        });
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim();
        filterValue = filterValue.toLowerCase();
        this.energyBillPaymentDataSource.filter = filterValue;
    }
}