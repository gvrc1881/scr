import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SubSectorComponent } from './sub-sector.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { AddSubSectorComponent } from './add-sub-sector/add-sub-sector.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: SubSectorComponent,
    },
    {
        path     : ':id',
        component: AddSubSectorComponent,
    },
    {
    	path: 'add-sub-sector',
    	component: AddSubSectorComponent,
    },
    
    
];


@NgModule({
    declarations: [
        SubSectorComponent,
        AddSubSectorComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        DecimalValidationsModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        CommonDirectivesModule, 
        OwlNativeDateTimeModule
    ],
    providers: [
        
    ],
    exports: [
    	SubSectorComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SubSectorModule {

}








