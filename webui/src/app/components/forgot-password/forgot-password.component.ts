import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { AuthenticationService } from '../../services/authentication.service';
import { AlertService } from '../../services/alert.service';
import {
  PasswordValidator,
  ParentErrorStateMatcher
} from '../../validators';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm:boolean=true;
  mailConfirmationMessage:boolean=false;
  resetPasswordForm:boolean=false;

  email:string;
  forgotPasswordMessageCondition: boolean = false;
  forgotPasswordMessage: string = "";
  returnUrl: string;
  userDetailsForm: FormGroup;
  forgotPasswordDetailsForm: FormGroup;
  invalid: boolean = false;
  //matching_passwords_group: FormGroup;
  //country_phone_group: FormGroup;
  //parentErrorStateMatcher = new ParentErrorStateMatcher();

  tokenMessage: boolean = false;
  tokenValid: boolean = true;
  passwordBlock: boolean = true;
  resetPassword: boolean = false;
  //returnUrl: string;
  //userDetailsForm: FormGroup;
  resetPasswordDetailsForm: FormGroup;
  resetinvalid: boolean = false;
  matching_passwords_group: FormGroup;
  country_phone_group: FormGroup;
  tokenResponse: any;
  parentErrorStateMatcher = new ParentErrorStateMatcher();

  change_password_validation_messages = {
    'confirm_password': [
      { type: 'required', message: 'Confirm password is required' },
      { type: 'areEqual', message: 'Password mismatch' }
    ],
    'password': [
      { type: 'required', message: 'Password is required' },
      { type: 'minlength', message: 'Password must be at least 8 characters long' },
      { type: 'pattern', message: 'Your password must contain at least one uppercase, one lowercase, one number and one special character' }
    ]
  }

  account_validation_messages = {
    'email': [
      { type: 'required', message: 'Email is required' },
      { type: 'pattern', message: 'Enter a valid email' }
    ]
  }

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private spinnerService: Ng4LoadingSpinnerService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit() {
    window.scrollTo(0, 0);
    this.tokenValid = !!this.route.snapshot.queryParams && !!this.route.snapshot.queryParams.token ? false : true;    
    if (!this.tokenValid) {
      this.checkTokenIsValid(this.route.snapshot.queryParams.token);
      this.createResetForms();
      this.forgotPasswordForm=false;
      this.mailConfirmationMessage=false;
      this.resetPasswordForm=true;
    }  
    else{
      this.createForms();
      this.forgotPasswordForm=true;
      this.mailConfirmationMessage=false;
      this.resetPasswordForm=false;
    }
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/dashboard';
  }

  createForms() {
    // user links form validations
    this.forgotPasswordDetailsForm = this.fb.group({
      email: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ]))
    })

  }

  checkTokenIsValid(token) {
    this.spinnerService.show();
    this.authenticationService.checkTokenIsValid(token)
      .subscribe(
        tokenValidResponse => {         
          console.log(JSON.stringify(tokenValidResponse))
          this.tokenResponse = tokenValidResponse;
          if (!!tokenValidResponse && tokenValidResponse.code == 200) {
            this.tokenMessage = false;
            this.passwordBlock = true;
          } else {
            this.tokenMessage = true;
            this.passwordBlock = false;
          }
          this.spinnerService.hide();
        },
        error => {
          this.alertService.error(error);
          this.spinnerService.hide();
        }
      );
  }

  createResetForms() {
    this.matching_passwords_group = new FormGroup({
      password: new FormControl('', Validators.compose([
        Validators.minLength(5),
        Validators.required,
        Validators.pattern('^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&*+=]).*$')
      ])),
      confirm_password: new FormControl('', Validators.required)
    }, (formGroup: FormGroup) => {
      return PasswordValidator.areEqual(formGroup);
    });
    this.resetPasswordDetailsForm = this.fb.group({
      matching_passwords: this.matching_passwords_group,
    })

  }

  onSubmitResetPasswordDetails(value) {
    this.spinnerService.show();
    let email: string = this.tokenResponse.user.email;
    let password: string = value.matching_passwords.password;
    let user = {
      "email": email,
      "password": password
    }

    this.authenticationService.resetPassword(user)
      .subscribe(
        response => {

          if (!!response && response.code == 200) {
            this.resetPassword = true;
            this.passwordBlock = false;
          } else {
            this.resetPassword = false;
            this.passwordBlock = true;
          }

        },
        error => {
          this.invalid = true;
          this.alertService.error(error);
          this.spinnerService.hide();
        });
  }


  onSubmitForgotPasswordDetails(value) {
    this.spinnerService.show();
    let email: string = this.forgotPasswordDetailsForm.value.email;
    this.spinnerService.hide();
    this.authenticationService.forgotPassword(email)
      .subscribe(
        response => {
          if (!!response && response.code == 200) {
            //this.router.navigate(['/mailConfirmation']);
            this.forgotPasswordForm=false;
            this.mailConfirmationMessage=true;
            this.resetPasswordForm = false;
            this.email = email;
           // localStorage.setItem("email", email);
          } else {
            this.forgotPasswordMessageCondition = true;
            this.forgotPasswordMessage = response.message;
            this.forgotPasswordForm=true;
            this.mailConfirmationMessage=false;
            this.resetPasswordForm = false;
          }
        },
        error => {
          this.invalid = true;
          this.alertService.error(error);
          this.spinnerService.hide();
        });
  }

}
