import { OnInit, Component, ViewChild } from '@angular/core';
import { Form } from '@angular/forms';
import { CommonService } from 'src/app/common/common.service';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { MakeModel } from 'src/app/models/make.model';
import { MakeService } from 'src/app/services/make.service';
import { Constants } from 'src/app/common/constants';
import { MakePayload } from 'src/app/payloads/make.payload';
import { FuseConfirmDialogComponent } from '../../../components/confirm-dialog/confirm-dialog.component';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';


@Component({
    selector: 'make',
    templateUrl: './make.component.html',
    styleUrls: ['./make.component.scss']
})

export class MakeComponent implements OnInit{
    editPermission: boolean = true;
    addPermission: boolean = true;
    deletePermission: boolean = true;
    addMake:boolean;
    makeFormGroup:FormGroup;
    title: string = "Save";
    makeList:any;
    updatedata: boolean = true;
    cloneupdate: boolean = true;
    editMakeResponse: any;
    responseStatus: any;
    makeErrors : any;
    saveMake:boolean;
    value:string;
    pattern = "^[A-Z0-9]+$";
    makeDataSource: MatTableDataSource<MakeModel>;
    makeDisplayColumns = ['sno','makeCode','description','brandName','makeType','id'] ;
    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;
    confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
    makeResponse:any;

    constructor( 
        private formBuilder: FormBuilder,
        private commonService: CommonService,
        private makeService:MakeService,
        private spinnerService: Ng4LoadingSpinnerService,
        public dialog: MatDialog
        ){
                this.makeErrors = {
                  makeName: {},
            
                };
            
    }

    ngOnInit () {

        var permissionName = this.commonService.getPermissionNameByLoggedData("ENERGY BILL PAYMENT","Make") ;
  		console.log("permissionName = "+permissionName);
  		this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    	this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    	this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
    
        console.log('in ngOnintit method:::');
        this.getAllMakeData();
        this.makeFormGroup = this.formBuilder.group({
            id: 0,
           // 'makeName':[null,Validators.compose([Validators.maxLength(255)]), this.duplicateMakeName.bind(this)],
            'makeCode': [null,Validators.compose([Validators.required,Validators.maxLength(255)]) , this.duplicateMakeCode.bind(this)],
            'description': [null, Validators.maxLength(255)],
            'brandName': [null, Validators.maxLength(255)],
            'makeType' : [null, Validators.maxLength(255)]
            
        });
        
    }
    changeMakeCode($event){
      $event.taget.value.toUpperCase();
    }
    makeSubmit(){ 

        //let makeName: string=this.makeFormGroup.value.makeName; 
         let makeCode: string=this.makeFormGroup.value.makeCode;
        console.log("makeCode==="+makeCode) ;
        let description: string=this.makeFormGroup.value.description;
        let brandName: string=this.makeFormGroup.value.brandName;
        let makeType: string=this.makeFormGroup.value.makeType;
       this.addMake=false;

       if(this.title == Constants.EVENTS.SAVE)
       {
           
            //MakePayload.ADD_PAYLOAD.makeName =makeName;
            MakePayload.ADD_PAYLOAD.makeCode =makeCode;
            MakePayload.ADD_PAYLOAD.description =description;
            MakePayload.ADD_PAYLOAD.brandName =brandName;
            MakePayload.ADD_PAYLOAD.makeType =makeType;
            console.log("ADD Payload =" + JSON.stringify(MakePayload.ADD_PAYLOAD))
            this.makeService.save(MakePayload.ADD_PAYLOAD).subscribe((data)=>{
              this.makeResponse=data;
              this.spinnerService.hide();
              if(this.makeResponse.code==200 && !!this.makeResponse){
                this.commonService.showAlertMessage(this.makeResponse.message);
                this.getAllMakeData();
           this.makeFormGroup.reset();
           this.addMake = false;
              }
              else{
                this.commonService.showAlertMessage("Make data Saving Failed.");
              }
            },error => {
        console.log('ERROR >>>');
    this.spinnerService.hide();
    this.commonService.showAlertMessage("Make Data Saving Failed.");
    })
  }
       else if (this.title == "Update") {
        
        this.saveMake = false;
        let id: number = this.editMakeResponse.id;
       MakePayload.UPDATE_PAYLOAD.id =id;
       //MakePayload.UPDATE_PAYLOAD.makeName =makeName;
       MakePayload.UPDATE_PAYLOAD.makeCode =makeCode;
       MakePayload.UPDATE_PAYLOAD.description =description;
       MakePayload.UPDATE_PAYLOAD.brandName =brandName;
       MakePayload.UPDATE_PAYLOAD.makeType =makeType;
        console.log("Update Payload =" + JSON.stringify(MakePayload.UPDATE_PAYLOAD))
        this.makeService.update(MakePayload.UPDATE_PAYLOAD)
          .subscribe((data) => {
            this.makeResponse = data;
            this.spinnerService.hide();
            if(this.makeResponse.code==200 && !!this.makeResponse){

              this.commonService.showAlertMessage(this.makeResponse.message);
           
            this.getAllMakeData();
           
            this.makeFormGroup.reset();
           
            this.addMake = false;
            this.title = "Save";
            this.saveMake = false;
            this.updatedata = true;
            }else {
              this.commonService.showAlertMessage("Make Data Saving Failed.");
            }
           
          }, error => {
            console.log('ERROR >>>');
            this.spinnerService.hide();
            this.commonService.showAlertMessage("Make Data Saving Failed.");
          });
      }
    }

    getAllMakeData() {
        console.log("get all  Make data");
        const make : MakeModel[] = [];
        this.makeService.getAllMake().subscribe((data) => {
            this.makeList = data;
            for (let i = 0; i < this.makeList.length; i++) {
                this.makeList[i].sno = i+1;
                make.push(this.makeList[i]);              
            }
            this.makeDataSource = new MatTableDataSource(make);
            this.makeDataSource.paginator = this.paginator;
            this.makeDataSource.sort = this.sort;

        } , error => {});

    }
    
    onGoBack() {
        this.makeFormGroup.reset();
        this.addMake = false;
        this.title = 'Save';
    }


    NewMake () {
        console.log('in ngOnintit method:::'+this.addMake);
        this.addMake = true;
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.makeDataSource.filter = filterValue;
      }

      deleteMake(id) {
        this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
          disableClose: false
        });
        this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete the selected make?';
        this.confirmDialogRef.afterClosed().subscribe(result => {
          if (result) {
            this.spinnerService.show();
            this.makeService.delete(id)
              .subscribe((data) => {
                this.commonService.showAlertMessage('Make Deleted Successfully.')
                this.getAllMakeData();
              }, error => {
                this.makeErrors = error;
                this.commonService.showAlertMessage(error.statusText);
                this.spinnerService.hide();
              });
          }
          this.confirmDialogRef = null;
        });
    
    
        this.spinnerService.hide();
    
      }
    
      editMake(id) {
        this.spinnerService.show();
        this.addMake = true;
        this.cloneupdate = false;
        this.MakeEditAction(id);
        this.title = "Update";
       this.spinnerService.hide();
      }

      MakeEditAction(id: number) {
        this.addMake = true;
        this.makeService.findMakeById(id).subscribe((resp) => {
          this.cloneupdate = false;
          this.updatedata = false;
          this.saveMake = false;
          this.editMakeResponse = resp;
          this.responseStatus = resp;
          this.makeFormGroup.patchValue({
            id: this.responseStatus.id,
            makeName: this.responseStatus.makeName,
            makeCode: this.responseStatus.makeCode,
            description: this.responseStatus.description,
            brandName: this.responseStatus.brandName,
            makeType: this.responseStatus.makeType,
            
          });
        }, error => this.makeErrors = error);
        this.commonService.scrollTop("forms");
      }

      onFormValuesChanged() {
        for (const field in this.makeErrors) {
          if (!this.makeErrors.hasOwnProperty(field)) {
            continue;
          }
          // Clear previous errors
          this.makeErrors[field] = {};
    
          // Get the control
          const control = this.makeFormGroup.get(field);
          if (control && control.dirty && !control.valid) {
            this.makeErrors[field] = control.errors;
          }
        }
      }
      // duplicateMakeName() {
      //   const q = new Promise((resolve, reject) => {
        
      //     let makeName: string = this.makeFormGroup.controls['makeName'].value;
        
         
      //     this.makeService.existsMakeName(
      //       makeName
      //     ).subscribe((duplicate) => {
      //       if (duplicate) {
      //         resolve({ 'duplicateMakeName': true });
      //       } else {
      //         resolve(null);
      //       }
      //     }, () => { resolve({ 'duplicateMakeName': true }); });
      //   });
      //   return q;
      // }

      duplicateMakeCode() {
        const q = new Promise((resolve, reject) => {
         
          let makeCode: string = this.makeFormGroup.controls['makeCode'].value;
        
         
          this.makeService.existsMakeCode(
            makeCode
          ).subscribe((duplicate) => {
            if (duplicate) {
              resolve({ 'duplicateMakeCode': true });
            } else {
              resolve(null);
            }
          }, () => { resolve({ 'duplicateMakeCode': true }); });
        });
        return q;
      }
     
}

    
    
    

   