import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AlertGroupComponent } from './alert-group.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddAlertGroupComponent } from './add-alert-group/add-alert-group.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: AlertGroupComponent,
    },   
    {
        path     : ':id',
        component: AddAlertGroupComponent
    },
    {
        path        : 'add-alert-group',
        component:   AddAlertGroupComponent
    },
];

@NgModule({
    declarations: [
        AlertGroupComponent,
        AddAlertGroupComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        CommonDirectivesModule
    ],
    providers: [
        
    ],
    exports:[
        AlertGroupComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AlertGroupModule {

}