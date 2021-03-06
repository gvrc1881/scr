import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveInspectionComponent } from './drive-inspection.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveInspectionComponent } from './add-drive-inspection/add-drive-inspection.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: DriveInspectionComponent,
    },   
    {
        path     : ':id',
        component: AddDriveInspectionComponent
    },
    {
        path        : 'add-drive',
        component:   AddDriveInspectionComponent
    },
];

@NgModule({
    declarations: [
        DriveInspectionComponent,
        AddDriveInspectionComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,  
        NumberValidationsModule,   
        DecimalValidationsModule, 
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        CommonDirectivesModule,
    ],
    providers: [
        
    ],
    exports:[
        DriveInspectionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveInspectionModule {

}