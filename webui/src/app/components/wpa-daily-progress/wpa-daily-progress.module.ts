import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { WPADailyProgressComponent } from './wpa-daily-progress.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path: '',
        component: WPADailyProgressComponent,
    }
];

@NgModule({
    declarations: [
        WPADailyProgressComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,        
        Ng4LoadingSpinnerModule.forRoot(),
    ],
    providers: [
        
    ],
    exports:[
        WPADailyProgressComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class WPADailyProgressModule {

}