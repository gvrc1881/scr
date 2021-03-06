import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AssetScheduleActivityAssocComponent } from './asset-schedule-activity-assoc.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
const routes: Routes = [
    {
        path:'',
        component: AssetScheduleActivityAssocComponent,
    }
]

@NgModule({
    declarations: [
        AssetScheduleActivityAssocComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        CommonDirectivesModule, 
        NumberValidationsModule,    
        DecimalValidationsModule,
        
    ],
    providers: [
       
   ],
    exports: [
        AssetScheduleActivityAssocComponent
    ]
})
export class AssetScheduleActivityAssocModule{

}