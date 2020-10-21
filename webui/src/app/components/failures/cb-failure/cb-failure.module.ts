import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CbFailureComponent } from './cb-failure.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { AddCbFailureComponent } from './add-cb-failure/add-cb-failure.component';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
const routes: Routes = [
    {
        path: '',
        component: CbFailureComponent,
    },   
    {
        path     : ':id',
        component: AddCbFailureComponent
    },
    {
        path        : 'add-cb-failure',
        component:   AddCbFailureComponent
    },
];

@NgModule({
    declarations: [
        CbFailureComponent,
        AddCbFailureComponent,
        
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        CommonDirectivesModule,
        DecimalValidationsModule,
    ],
    providers: [
        Ng4LoadingSpinnerService,
    ],
    exports:[
        CbFailureComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class CbFailureModule {

}