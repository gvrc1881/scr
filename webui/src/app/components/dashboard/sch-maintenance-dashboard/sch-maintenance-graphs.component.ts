import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { Constants } from 'src/app/common/constants';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-sch-maintenance-graphs',
  templateUrl: './sch-maintenance-graphs.component.html',
  styleUrls: ['./sch-maintenance-graphs.component.css']
})
    
export class SchMaintenanceGraphsComponent implements OnInit {

    

    constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private datePipe: DatePipe,
    private sendAndRequestService: SendAndRequestService
  ) {
  
  }
  
  ngOnInit() {
    
  }
  
  
  
}