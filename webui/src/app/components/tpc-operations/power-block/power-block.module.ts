import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PowerBlockComponent } from './power-block.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddPowerBlockComponent } from './add-power-block/add-power-block.component';
import { SwitchOperationComponent } from './switch-operation/switch-operation.component';
import { AddSwitchDialogComponent } from '../power-block/switch-operation/switch-operation.component';
import { PTWIssueReturnAmendmentDialogComponent } from '../power-block/switch-operation/switch-operation.component';
import { SwitchAmendmentDialogComponent } from '../power-block/switch-operation/switch-operation.component';

import { DateFilterPipe } from 'src/app/common/date-filter.pipe';
import { DateFnsDateTimeAdapter } from 'src/app/common/date-fns-date-time-adapter.class';
const DATEFNS_FORMATS_EN_LOCALE = {
    parseInput: "dd-MM-yyyy HH:mm || dd/MM/yyyy", // multiple date input types separated by ||
    fullPickerInput: "dd-MM-yyyy HH:mm:ss",
    datePickerInput: "dd/MM/yyyy",
    timePickerInput: "HH:mm",
    monthYearLabel: "MMM yyyy",
    dateA11yLabel: "dd/MM/yyyy",
    monthYearA11yLabel: "MMMM yyyy"
  };

const routes: Routes = [
    {
        path: '',
        component: PowerBlockComponent,
    },
    {
        path     : ':id',
        component: AddPowerBlockComponent,
    },
    {
    	path: 'add-powerBlock',
    	component: AddPowerBlockComponent,
    },
    {
    	path: 'switch-operation/:pbId',
    	component: SwitchOperationComponent,
    }
    
];


@NgModule({
    declarations: [
        PowerBlockComponent,
        AddPowerBlockComponent,
        SwitchOperationComponent,
        AddSwitchDialogComponent,
        PTWIssueReturnAmendmentDialogComponent,
        SwitchAmendmentDialogComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
    ],
    providers: [
        
    ],
    exports: [
    	PowerBlockComponent,
    	SwitchOperationComponent
    ],
    entryComponents: [ AddSwitchDialogComponent,PTWIssueReturnAmendmentDialogComponent, SwitchAmendmentDialogComponent ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PowerBlockModule {

}








