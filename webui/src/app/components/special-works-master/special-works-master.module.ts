import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SpecialWorksMasterComponent } from './special-works-master.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddSpecialWorksMasterComponent } from './add-special-works-master/add-special-works-master.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';


const routes: Routes = [
    {
        path: '',
        component: SpecialWorksMasterComponent,
    },   
    {
        path     : ':id',
        component: AddSpecialWorksMasterComponent
    },
    {
        path        : 'add-special-works-master',
        component:   AddSpecialWorksMasterComponent
    },
];

@NgModule({
    declarations: [
        SpecialWorksMasterComponent,
        AddSpecialWorksMasterComponent,
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
    ],
    providers: [
        
    ],
    exports:[
        SpecialWorksMasterComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class SpecialWorksMasterModule {

}