import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveDailyProgressComponent, AddAssetIdsDriveDialogComponent } from './drive-daily-progress.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [ 
    {
        path: '',
        component: DriveDailyProgressComponent,
    }
];

@NgModule({
    declarations: [
        DriveDailyProgressComponent,
        AddAssetIdsDriveDialogComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        DriveDailyProgressComponent
    ],
    entryComponents: [ AddAssetIdsDriveDialogComponent ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveDailyProgressModule {

}