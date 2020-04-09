import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { AuthenticationService } from '../../services/authentication.service';
import { AlertService } from '../../services/alert.service';
import {
  PasswordValidator,
  ParentErrorStateMatcher  
} from '../../validators';
import {  ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ResetPasswordComponent implements OnInit {

  tokenMessage: boolean = false;
  tokenValid: boolean = true;
  passwordBlock: boolean = true;
  resetPassword: boolean = false;
  returnUrl: string;
  userDetailsForm: FormGroup;
  resetPasswordDetailsForm: FormGroup;
  invalid: boolean = false;
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
      { type: 'minlength', message: 'Password must be at least 5 characters long' },
      { type: 'pattern', message: 'Your password must contain at least one uppercase, one lowercase, and one number' }
    ]
  }

  constructor(
    private fb: FormBuilder,
    private spinnerService: Ng4LoadingSpinnerService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private route: ActivatedRoute,

  ) { }

  ngOnInit() {
    this.tokenValid = !!this.route.snapshot.queryParams ? false : true;
    if (!this.tokenValid) {
      this.checkTokenIsValid(this.route.snapshot.queryParams.token);
    }
    this.createForms();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/dashboard';
  }

  checkTokenIsValid(token) {
    this.spinnerService.show();
    this.authenticationService.checkTokenIsValid(token)
      .subscribe(
        tokenValidResponse => {
          this.spinnerService.hide();

          this.tokenResponse = tokenValidResponse;
          if (!!tokenValidResponse && tokenValidResponse.code == 200) {
            this.tokenMessage = false;
            this.passwordBlock = true;
          } else {
            this.tokenMessage = true;
            this.passwordBlock = false;
          }

        },
        error => {
          this.alertService.error(error);
          this.spinnerService.hide();
        }
      );
  }


  createForms() {
    this.matching_passwords_group = new FormGroup({
      password: new FormControl('', Validators.compose([
        Validators.minLength(5),
        Validators.required,
        //Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
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



}
