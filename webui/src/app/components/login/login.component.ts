import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { AuthenticationService } from '../../services/authentication.service';
import { AlertService } from '../../services/alert.service';
import { CommonService } from '../../common/common.service';
import { FacilityModel } from 'src/app/models/facility.model';

import {
  ParentErrorStateMatcher,
} from '../../validators';
import { Router, ActivatedRoute } from '@angular/router';
import { UsersModel } from 'src/app/models/users.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  loginValidate: boolean = true;
  registrationFlag: boolean = true;
  returnUrl: string;
  userDetailsForm: FormGroup;
  accountDetailsForm: FormGroup;
  invalid: boolean = false;
  matching_passwords_group: FormGroup;
  country_phone_group: FormGroup;
  user: UsersModel;
  zoneList: FacilityModel[] = [];
  divisionList: FacilityModel[] = [];
  subDivList: FacilityModel[] = [];
  facilityList: FacilityModel[] = [];

  parentErrorStateMatcher = new ParentErrorStateMatcher();

  account_validation_messages = {
    'userName': [
      { type: 'required', message: 'User Name is required' },
      { type: 'pattern', message: 'Enter a valid UserName' }
    ],
    'password': [
      { type: 'required', message: 'Password is required' },
      { type: 'minlength', message: 'Password must be at least 5 characters long' },
      { type: 'pattern', message: 'Your password must contain at least one uppercase, one lowercase, and one number' }
    ]
  }

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private spinnerService: Ng4LoadingSpinnerService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private commonService: CommonService
  ) { }
  ngOnInit() {
    this.checkUsersExists();
    this.loginValidate = true;
    this.createForms();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/dashboard';
  }

  checkUsersExists() {
    this.authenticationService.checkUsersExists().subscribe(response => {
      this.registrationFlag = response;
    }, error => {
      console.log("ERROR >>> " + error)
    });
  }

  createForms() {
    // user links form validations
    this.accountDetailsForm = this.fb.group({
      userName: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.-]+$')
      ])),
      password: new FormControl('', Validators.compose([
        Validators.minLength(5),
        Validators.required
        // Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
      ]))//,

    })

  }

  onSubmitAccountDetails(value) {
    this.spinnerService.show();
    let userName: string = this.accountDetailsForm.value.userName;
    let password: string = this.accountDetailsForm.value.password;
    this.authenticationService.login(userName, password)
      .subscribe(
        data => {
          this.authenticationService.getLoginUserData(userName, password)
            .subscribe(
              userdata => {
                this.authenticationService.getUserData(userdata.email, password).subscribe(response => {
                  sessionStorage.setItem("menus", response.menus);
                  sessionStorage.setItem("loggedUser", JSON.stringify(response));
                  this.user = response;
                  this.router.navigate([this.returnUrl]);
                    sessionStorage.setItem("headerRefresh","refresh");
                    sessionStorage.setItem("userHierarchy", JSON.stringify(response));
                        sessionStorage.setItem("zoneData", JSON.stringify(response.zoneList));
                        sessionStorage.setItem("divisionData", JSON.stringify(response.divisionList));
                        sessionStorage.setItem("subDivData", JSON.stringify(response.subDivisionList));
                        sessionStorage.setItem("depotData", JSON.stringify(response.depotList));
                        sessionStorage.setItem("facilityData",JSON.stringify(response.facilityData));
                        sessionStorage.setItem("projectList",JSON.stringify(response.projects));
                    console.log("*** value ***"+JSON.stringify(response.projects));
                  // To get User Hierarchy 
                    /*
                  if (this.user) {
                    this.authenticationService.userHierarchy(this.user.userName)
                      .subscribe(response => {
                        sessionStorage.setItem("userHierarchy", JSON.stringify(response));
                        let userHierarchy = JSON.parse(sessionStorage.getItem('userHierarchy'));
                        for (let i = 0; i < userHierarchy.length; i++) {

                          if (userHierarchy[i].depotType == 'ZONE')
                            this.zoneList.push(userHierarchy[i]);

                          else if (userHierarchy[i].depotType == 'DIV')
                            this.divisionList.push(userHierarchy[i]);

                          else if (userHierarchy[i].depotType == 'SUBDIV')
                            this.subDivList.push(userHierarchy[i]);

                          else
                            this.facilityList.push(userHierarchy[i]);

                        }
                        sessionStorage.setItem("headerRefresh","refresh");
                        sessionStorage.setItem("zoneData", JSON.stringify(this.zoneList));
                        sessionStorage.setItem("divisionData", JSON.stringify(this.divisionList));
                        sessionStorage.setItem("subDivData", JSON.stringify(this.subDivList));
                        sessionStorage.setItem("depotData", JSON.stringify(this.facilityList));
                        
                        
                      }, error => {
                        console.log("Trigger Function Not Available >>> " + error);
                      }
                      );
                  } */
                  this.spinnerService.hide();
                }, error => {
                  console.log("ERROR >>> " + error)
                });
              },
              error => {
                this.alertService.error(error);
                this.spinnerService.hide();
                this.commonService.showAlertMessage("Invalid Credentials.")
              }
            );
        },
        error => {
          console.log(" >>> ERROR " + JSON.stringify(error));
          if (!!error && !!error.statusText && error.statusText == 'Unknown Error') {
            this.commonService.showAlertMessage('Server not yet started..')
          } else if (!!error && !!error.status && error.status > 200) {
            this.commonService.showAlertMessage('Invalid credentials.')
          }
          this.invalid = true;
          this.alertService.error(error);
          this.spinnerService.hide();
        });
  }



}
