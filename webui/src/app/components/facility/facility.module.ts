import { NgModule } from '@angular/core';
import { FacilityComponent } from './facility.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path: '',
        component:FacilityComponent,
    }
];

@NgModule({
    declarations: [
        FacilityComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        DecimalValidationsModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        
    ],
    exports:[
        FacilityComponent
    ]
})
export class FacilityModule {

}