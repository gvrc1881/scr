import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, NgForm } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { UsersModel } from '../../../models/users.model';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from '../../../common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

    saveUser: boolean = false;
    updateUser: boolean = false;
    checkInput: boolean = false;
    genders = [{ ID: 1, Gender: 'Male' },
    { ID: 2, Gender: 'Female' }];
    rolesList:any = [];
    departmentList:any = [];
    repositoryList:any=[];
    active: boolean;
    dublicateemail: boolean;
    id: number = 0;
    users: FormGroup;
    resp:any;
    status: boolean;
    userFormErrors: any;
    title: string = "User";
    userdata: any = JSON.parse(sessionStorage.getItem('userData'));
    dataSource: MatTableDataSource<UsersModel>;
    response: any;
    rolePermission:boolean= true;
    data: any;

    pattern = "[a-zA-Z][a-zA-Z_ ]*";

    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;

    constructor(private formBuilder: FormBuilder,
        private _router: Router,
        public dialog: MatDialog,
        private route: ActivatedRoute,
        private spinnerService: Ng4LoadingSpinnerService,
        private commonService: CommonService,
        private sendAndRequestService:SendAndRequestService

    ) {
        // Reactive form errors
        this.userFormErrors = {
            userName: {},
            firstName: {},
            lastName: {},
            gender: {},
            email: {},
            mobilenum: {},
            password: {},
            repositoryCode:{}
        };
            //role: {},
            //department: {},
    }

    ngOnInit() {

        this.id = +this.route.snapshot.params['id'];
        this.rolePermission = this.commonService.rolePermission();        
        if (!isNaN(this.id)) {
            this.users = this.formBuilder.group({
                id: 0,
                'userName': [null, Validators.compose([Validators.required, Validators.pattern(this.pattern)])], // Validators.pattern('^[a-zA-Z0-9_.-]+$')
                'firstName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
                'lastName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
                'gender': [null, Validators.required],
                'email': [null, Validators.compose([Validators.required])],
                'mobilenum': [null, Validators.compose([Validators.required,Validators.maxLength(10)])],
                'repositoryCode': [null, Validators.required]
            });
            
                //'role': [null, Validators.required],
                //'department': [null, Validators.required],

        /*
            this.users.valueChanges.subscribe(() => {
                this.onFormValuesChanged();
            }); */
            this.spinnerService.show();
            this.saveUser = false;
            this.updateUser = true;
            this.checkInput = true;
            this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_USERS_BYID+this.id)
                .subscribe(resp => {
                    
this.resp = resp;
                    this.users.patchValue({
                        id: this.resp.id,
                        userName: this.resp.username,
                        firstName: this.resp.first_name,
                        lastName: this.resp.last_name,
                        gender: this.resp.gender,
                        email: this.resp.email,
                        mobilenum: this.resp.phone,
                        repositoryCode:this.resp.divisionCode,
                    });
                    this.spinnerService.hide();
                });
        } else {
            console.log('in else condiition');
            this.users = this.formBuilder.group({
                id: 0,
                'userName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
                'firstName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
                'lastName': [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.pattern(this.pattern)])],
                'gender': [null, Validators.required],
                'email': [null, Validators.compose([Validators.required]), this.emailValidation.bind(this)],               
                'mobilenum': [null, Validators.compose([Validators.required, Validators.minLength(10)])],
                'password': [null, Validators.compose([Validators.required, Validators.minLength(8)])],
                'repositoryCode': [null, Validators.required]
            });
            
            //'role': [null, Validators.required],
              //  'department': [null, Validators.required],



            this.users.valueChanges.subscribe(() => {
                this.onFormValuesChanged();
            });
            this.saveUser = true;
            this.updateUser = false;
            this.checkInput = false;
        }

        this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.ROLE_TYPE_MASTER)
            .subscribe((roles) => {
                
                this.rolesList = roles;
            })
            this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.USERS.GET_DEPARTMENT_MASTER).subscribe((department) => {
           
            this.departmentList = department;
        })
        this.sendAndRequestService.requestForGET(Constants.app_urls.MASTERS.SCHEDULER_SETTINGS.REPOSITORY.GET_REPOSITORIES).subscribe((repository) => {
          
            this.repositoryList = repository;
        })      
        
    }


    keyPress(event: any) {
        const pattern = /^[0-9]*$/;
        let inputChar = String.fromCharCode(event.charCode);
        if (event.keyCode != 8 && !pattern.test(inputChar)) {
            event.preventDefault();
        }
    }



    emailValidation() {
        const q = new Promise((resolve, reject) => {
            this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.USERS.EXIST_EMAIL,{

                'modifiedBy': null,
                'createdBy': null,

                'userID': null,
                'userName': null,
                'firstName': null,
                'lastName': null,
                'password': null,
                'roleType': null,
                'employeeId': null,
                'email': this.users.controls['email'].value,
                'privilegeId': null,
                'gender': null,
                'phone': null,
                'department': null,
                'id': null,
            }, false).subscribe((dublicate) => {
                if (dublicate) {
                    resolve({ 'emailValidation': true });

                } else {
                    resolve(null);
                }

            }, () => { resolve({ 'emailValidation': true }); });

        });
        return q;

    }


    onFormValuesChanged() {
        for (const field in this.userFormErrors) {
            if (!this.userFormErrors.hasOwnProperty(field)) {
                continue;
            }

            // Clear previous errors
            this.userFormErrors[field] = {};

            // Get the control
            const control = this.users.get(field);

            if (control && control.dirty && !control.valid) {
                this.userFormErrors[field] = control.errors;
            }
        }
    }
    clearFields() {
        
        this.users = this.formBuilder.group({
            userName: [null],
            firstName: [null],
            lastName: [null],
            gender: [null],
            email: [null],
            mobilenum: [null],
            password: [null],
            role: [null],
            department: [null],
            repositoryCode:[null]
        });


    }

    onUserFormSubmit() {

        let var_id: number = this.users.value.id;
        let var_userName: string = this.users.value.userName;
        let var_firstName: string = this.users.value.firstName;
        let var_lastName: string = this.users.value.lastName;
        let var_email: string = this.users.value.email;
        let var_password: string = this.users.value.password;
        let var_gender: number = this.users.value.gender;
        let var_mobilenumber: number = this.users.value.mobilenum;
        let var_role: string = this.users.value.role;
        let var_department: number = this.users.value.department;
        let repositoryCode:string = this.users.value.repositoryCode;       

        if (this.saveUser) {
            if (!this.users.valid) {
                return;
            }

                var saveUsersModel ={             
                "user_id": null,
                "username": var_userName,
                "email": var_email,
                "password": var_password,
                "first_name": var_firstName,
                "last_name": var_lastName,
                "gender": var_gender,
                "day_of_birth":null,               
                "department": "1",
                "designation":null,
                "address":null,
                "phone": var_mobilenumber,
                "issue_date":null,
                "expire_date":null,
                "privilege_id": "87",
                "createdBy": null,                
                "roleTypeId": "1",
                "status_id": 1,
                "divisionCode":repositoryCode    
                }         
                this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.USERS.SAVE_USERS, saveUsersModel, false).subscribe((data) => {
                this.data = data;
                this.commonService.showAlertMessage('User Added Successfully.')
                if (this.data) {
                    this.status = false;
                }
                setTimeout(() => { this.status = false }, 4000)              
                if (this.users.valid) {
                    this.users.reset();
                }               
                this._router.navigate(['../'], { relativeTo: this.route });
                this.status = true;

            }, error => {
                this.commonService.showAlertMessage(error.statusText)
                this.userFormErrors = error
            })

        }

        else if (this.updateUser) {
         
            this.sendAndRequestService.requestForPOST(Constants.app_urls.MASTERS.USERS.UPDATE_USERS,{

                "user_id": var_id,
                "id":var_id,
                "username": var_userName,
                "email": var_email,
                "password": var_password,
                "first_name": var_firstName,
                "last_name": var_lastName,
                "gender": var_gender,
                "day_of_birth":null,               
                "department": var_department,
                "designation":null,
                "address":null,
                "phone": var_mobilenumber,
                "issue_date":null,
                "expire_date":null,
                "privilege_id": "87",
                "createdBy": null,                
                "roleTypeId": var_role,
                "status_id": 1,
                "divisionCode":repositoryCode
            }, false)
                .subscribe((data) => {
                   
                    this.commonService.showAlertMessage('User Data Updated Successfully.')                  
                    setTimeout(() => { this.status = false }, 4000)                   
                    if (this.users.valid) {
                        this.users.reset();
                    }
                    this._router.navigate(['../'], { relativeTo: this.route });
                    this.status = true;
                }, error => {
                    this.commonService.showAlertMessage(error.statusText)
                    this.userFormErrors = error
                })
        }


    }


    onGoBack() {
        this._router.navigate(['../'], { relativeTo: this.route });
        this.users.reset();


    }


}












