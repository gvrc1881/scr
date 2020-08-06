import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AssetScheduleAssocComponent } from './asset-schedule-assoc.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path:'',
        component: AssetScheduleAssocComponent,
    }
]

@NgModule({
    declarations: [
        AssetScheduleAssocComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        CommonDirectivesModule,        
        DecimalValidationsModule,
        
    ],
    providers: [
       
   ],
    exports: [
        AssetScheduleAssocComponent
    ]
})
export class AssetScheduleAssocModule{

}