import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ApproveDriveDailyProgressComponent, AddAssetIdsApproveDriveDialogComponent } from './approve-drive-daily-progress.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { ViewApproveDriveDailyProgressComponent } from './view-approve-drive-daily-progress/view-approve-drive-daily-progress.component';

const routes: Routes = [ 
    {
        path: '',
        component: ApproveDriveDailyProgressComponent,
    }
];

@NgModule({
    declarations: [
        ApproveDriveDailyProgressComponent,
        AddAssetIdsApproveDriveDialogComponent,
        ViewApproveDriveDailyProgressComponent
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
        ApproveDriveDailyProgressComponent
    ],
    entryComponents: [ AddAssetIdsApproveDriveDialogComponent,ViewApproveDriveDailyProgressComponent ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ApproveDriveDailyProgressModule{

}