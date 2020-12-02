import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProjectComponent } from './projects.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddProjectComponent } from './add-projects/add-projects.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { DateTimeAdapter, OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { DateFnsDateTimeAdapter } from 'src/app/common/date-fns-date-time-adapter.class';
import { CopyWPAndWPAComponent } from '../copy-wp-and-wpa/copy-wp-and-wpa.component';
import { MatNativeDateModule } from '@angular/material/core';

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
        component: ProjectComponent,
    },   
    {
        path     : ':id',
        component: AddProjectComponent
    },
    {
        path        : 'add-project',
        component:   AddProjectComponent
    }/* ,
    {
         path        : 'copy-wp-and-wpa/:workId',
          component:   CopyWPAndWPAComponent
     } */
   
];

@NgModule({
    declarations: [
        ProjectComponent,
        AddProjectComponent,
      //  CopyWPAndWPAComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        CommonDirectivesModule,
        DecimalValidationsModule,
        OwlDateTimeModule,  
        OwlNativeDateTimeModule,
        MatNativeDateModule,
      //  CopyWPAndWPAModule
    ],
    providers: [
        { provide: DateTimeAdapter, useClass: DateFnsDateTimeAdapter },
        { provide: OWL_DATE_TIME_FORMATS, useValue: DATEFNS_FORMATS_EN_LOCALE }
        
    ],
    exports:[
        ProjectComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProjectModule {

}