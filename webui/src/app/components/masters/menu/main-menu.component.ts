import { Component, ViewChild, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MatPaginator, MatDialog, MatSort, MatTableDataSource } from '@angular/material';
import { MainMenuService } from '../../../services/main.menu.service';
import { Subscription } from 'rxjs';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from '../../../common/common.service';
@Component({
    selector: 'app-main-menu',
    templateUrl: './main-menu.component.html',
    styleUrls: ['./main-menu.component.scss']
})
export class MainMenuComponent implements OnInit, OnDestroy {

    mainMenuForm: FormGroup;
    formErrors: any;
    menuForm: boolean;
    displayedColumns = ['sno', 'menu', 'menuURL', 'id'];
    dataSource: MatTableDataSource<MainMenuModel>;
    // checkInput:boolean=false;
    title: string = "Save";
    flag: boolean = false;
    id: number = 0;
    action: string = "";
    response:any;
    users: any =[];
    data: any ;
    userdata: any = JSON.parse(localStorage.getItem('userData'));
    MenusList: any = [];
    pattern = "[a-zA-Z][a-zA-Z ]*";
    responseStatus: Object = [];
    status: boolean;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild('filter', {static: true}) filter: ElementRef;
    // Horizontal Stepper
    horizontalStepperStep1: FormGroup;
    horizontalStepperStep2: FormGroup;
    horizontalStepperStep3: FormGroup;
    horizontalStepperStep1Errors: any;
    horizontalStepperStep2Errors: any;
    horizontalStepperStep3Errors: any;

    constructor(private formBuilder: FormBuilder,
        private _meinMenuService: MainMenuService,       
        public dialog: MatDialog,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService:CommonService        
    ) {
        // Reactive form errors
        this.formErrors = {
            menu: {},
            menu_url: {}
        };       
    }

    ngOnInit() {
        // Reactive Form      
        this.MenusList = JSON.parse(localStorage.getItem('userMenuList'));
        this.MenusList.sort((a,b) => a.MenuName.localeCompare(b.MenuName));
        this.menuForm = false;
        this.spinnerService.show();
        this.mainMenuForm = this.formBuilder.group({          
            id: 0,
            'menu': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)]), this.duplicateMenuName.bind(this)],            
            'menu_url': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
        });

        this.mainMenuForm.valueChanges.subscribe(() => {
            this.onFormValuesChanged();
        });



        //subscribe for read set MAT table datasource for GRID
        this.getGrid();

        //subscribe for read get params
        // this.paramSubscription = this.actRoute.queryParams.subscribe(
        //     (params: Params) => {
        //         this.id = params['id'];
        //         this.action = params['action'];

        //         if (this.action == 'edit') {
        //             this.title = "Edit";
        //         }
        //         //console.log(this.id +"--"+this.title);
        //         if (this.id > 0 && this.title == "Edit") {
        //             this.processEditAction(this.id);
        //         }
        //     }
        // );
        
    }

    //Populate Edit from values
    processEditAction(id) {        
        this._meinMenuService.getMainMenuById(id)
            .subscribe(resp => {                        
                this.response = resp;      
                this.mainMenuForm.patchValue({
                    id: this.response.id,
                    menu: this.response.menu,
                    menu_url: this.response.menuURL
                });
            }, error => this.formErrors = error);
        this.commonService.scrollTop("forms");
    }
    duplicateMenuName() {
        const q = new Promise((resolve, reject) => {
            this._meinMenuService.duplicateMenuName({
                'menu': this.commonService.removeSpaceMaoreThanOne(this.mainMenuForm.controls['menu'].value),
                'menuURL': this.commonService.removeSpaceMaoreThanOne(this.mainMenuForm.controls['menu_url'].value),
                'statusid': null,
                'createdBy': null,
                'modifiedBy': null,
                'menuColor': null,
                'menuIcon': null,
            }).subscribe((duplicateMenu) => {                             
                if (duplicateMenu) {
                    resolve({ 'duplicateMenuName': true });
                } else {
                    resolve(null);
                }
            }, () => { resolve({ 'duplicateMenuName': true }); });
        });
        return q;
    }


    //To destroy param subcribe
    ngOnDestroy() {
        //this.paramSubscription.unsubscribe();
    }
    // On form values change event
    onFormValuesChanged() {
        for (const field in this.formErrors) {
            if (!this.formErrors.hasOwnProperty(field)) {
                continue;
            }
            // Clear previous errors
            this.formErrors[field] = {};
            // Get the control
            const control = this.mainMenuForm.get(field);
            if (control && control.dirty && !control.valid) {
                this.formErrors[field] = control.errors;
            }
        }
    }

    //On form submit

    onMainMenuFormSubmit() {
        
        let var_id: string = this.mainMenuForm.value.id;
        let var_menu: string = this.mainMenuForm.value.menu;
        let var_menu_url: string = this.mainMenuForm.value.menu_url;
        if (!this.mainMenuForm.valid) {
            return;
        }

        if (this.title == "Save") { // on create menu form submit
            // this.checkInput=false;
            this.spinnerService.show();
            this._meinMenuService.saveMainMenu({
                'menu': this.commonService.removeSpaceMaoreThanOne(var_menu),
                'menuURL': this.commonService.removeSpaceMaoreThanOne(var_menu_url),
                'statusid': 1,
                'createdBy': this.userdata['one'].ID,
                'modifiedBy': this.userdata['one'].ID,
                'menuColor': '#2b84ee',
                'menuIcon': 1,
            }).subscribe((data) => {
                this.commonService.showAlertMessage('Menu Added Successfully.');         
                console.log("helllooooo")     
//if (data) {
                    this.status = false;
///} 
                setTimeout(() => { this.status = false }, 4000)               
                this.getGrid();

                this.mainMenuForm.reset();
                this.status = true;               
            }, error => error => {
                this.formErrors = error
                this.commonService.showAlertMessage(error.statusText);
                this.spinnerService.hide();
            });

        }
        else if (this.title == "Update") { // on edit menu form
            this.spinnerService.show();
            this._meinMenuService.updateMainMenu({
                'menu': var_menu,
                'menuURL': var_menu_url,
                'statusid': 1,
                'createdBy': this.userdata['one'].ID,
                'modifiedBy': this.userdata['one'].ID,
                'menuColor': '#2b84ee',
                'menuIcon': 1,
            }, var_id)
                .subscribe((data) => {
                    this.commonService.showAlertMessage('Menu Updated Successfully.');                           
                    this.title = "Save";                    
                    this.getGrid();
                    this.mainMenuForm.reset();
                }, error => {
                    this.formErrors = error
                    this.commonService.showAlertMessage(error.statusText);              
                    this.spinnerService.hide();
                });
        }

    }
    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }
    AddMenu() {
        this.menuForm = true;
    }
    // Update Main Menu
    updateMainmenu(id) {
        this.menuForm = true;      
        this.spinnerService.show();
        this.processEditAction(id);        
        this.title = "Update";
       // this.checkInput=true;
        this.spinnerService.hide();
        
        
    }
    onGoBack() {
        this.mainMenuForm.reset();
        this.menuForm = false;
    }
    // Delete main Menu.
    deleteMainmenu(id) {
        this._meinMenuService.deleteMainMenu(id)
            .subscribe((data) => {
                this.commonService.showAlertMessage('Menu Deleted Successfully.'); 
                this.getGrid();
                this.mainMenuForm.reset();
                this.status = true; 
                //this._router.navigate(['/main-menu']);
            }, error => {
                this.formErrors = error
                this.commonService.showAlertMessage(this.formErrors); 
                this.spinnerService.hide();
            });
    }

    private getGrid() {        
        this.menuForm = false;
       
        this._meinMenuService.getMainMenu().subscribe(data => {
            this.data = [];
            this.users = [];
            this.data = data;
            for (let i = 0; i < this.data.length; i++) {
                this.data[i].sno = i + 1;
                this.users.push(data[i]);
            }
            this.dataSource;
            
            this.dataSource = new MatTableDataSource(this.users);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
            this.spinnerService.hide();            
        }, error => {
            this.formErrors = error;
            this.spinnerService.hide();
        }
        );
    }

}

//custom validation for chec unique menu.
function checkUniqueMainMenu(control: FormControl) {
    let mainManuVal = control.value;
    // console.log(mainManuVal);
    // if (email && email.indexOf("@") != -1) {
    //   let [_, domain] = email.split("@");
    //   if (domain !== "codecraft.tv") {
    //     return {
    //       mainMenuExists: {
    //         parsedDomain: domain
    //       }
    //     }
    //   }
    // }
    return {
        mainMenuExists: {
            parsedDomain: 'test'
        }
    }


    // return null;
}

export interface MainMenuModel {
    createdBy: number,
    createdDate: string,
    id: number
    menu: String,
    menuColor: String,
    menuIcon: number
    menuURL: String,
    modifiedBy: number,
    modifiedDate: String,
    sno: number,
    statusId: number
}
