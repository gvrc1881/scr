import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AlertGroupMemberComponent } from './alert-group-member.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddAlertGroupMemberComponent } from './add-alert-group-member/add-alert-group-member.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: AlertGroupMemberComponent,
    },   
    {
        path     : ':id',
        component: AddAlertGroupMemberComponent
    },
    {
        path        : 'add-alert-group-member',
        component:   AddAlertGroupMemberComponent
    },
];

@NgModule({
    declarations: [
        AlertGroupMemberComponent,
        AddAlertGroupMemberComponent,
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
        AlertGroupMemberComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AlertGroupMemberModule {

}