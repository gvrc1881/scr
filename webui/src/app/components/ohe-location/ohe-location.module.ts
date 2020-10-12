import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OheLocationComponent } from './ohe-location.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddOheLocationComponent } from './add-ohe-location/add-ohe-location.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { filterDirective } from 'src/app/common/filter.directive';

const routes: Routes = [
    {
        path: '',
        component: OheLocationComponent,
    },   
    {
        path     : ':id',
        component: AddOheLocationComponent
    },
    {
        path        : 'add-product-category',
        component:   AddOheLocationComponent
    },
];

@NgModule({
    declarations: [
        OheLocationComponent,
        AddOheLocationComponent,
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
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        OheLocationComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class OheLocationModule {

}