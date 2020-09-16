import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { FusionChartsModule } from 'angular-fusioncharts';
import { MaterialModule } from 'src/app/modules/material.modules';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddAshComponent } from './add-ash.component';

const routes: Routes = [
    {
        path     : '',
        component: AddAshComponent,
       
    },
    //{
      //  path: 'ashd',
       // component: AshDisplayComponent
    //}
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        FusionChartsModule,
        MaterialModule,
        FormsModule,
        DecimalValidationsModule,
        ReactiveFormsModule
    ],
    declarations: [
        AddAshComponent,
       // AshDisplayComponent,
    ],
    providers   : [
        Ng4LoadingSpinnerService,
    ],
    exports     : [
        AddAshComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AshAddModule
{
    
}

