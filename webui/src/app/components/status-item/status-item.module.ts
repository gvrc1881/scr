import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { StatusItemComponent } from './status-item.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddStatusItemComponent } from './add-status-item/add-status-item.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';

const routes: Routes = [
    {
        path: '',
        component: StatusItemComponent,
    },   
    {
        path     : ':id',
        component: AddStatusItemComponent
    },
    {
        path        : 'add-status-item',
        component:   AddStatusItemComponent
    },
];

@NgModule({
    declarations: [
        StatusItemComponent,
        AddStatusItemComponent,
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
        CommonDirectivesModule
    ],
    providers: [
        
    ],
    exports:[
        StatusItemComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class StatusItemModule {

}