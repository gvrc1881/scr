import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProjectPhaseActivityComponent } from './project-phase-activity.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddProjectPhaseActivityComponent } from './add-project-phase-activity/add-project-phase-activity.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { MatNativeDateModule } from '@angular/material/core';

const routes: Routes = [
    {
        path: '',
        component: ProjectPhaseActivityComponent,
    },   
    {
        path     : ':id',
        component: AddProjectPhaseActivityComponent
    },
    {
        path        : 'add-project-phase-activity',
        component:   AddProjectPhaseActivityComponent
    }
   
];

@NgModule({
    declarations: [
        ProjectPhaseActivityComponent, 
        AddProjectPhaseActivityComponent 
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        DecimalValidationsModule,
        MatNativeDateModule
    ],
    providers: [
        
    ],
    exports: [
        ProjectPhaseActivityComponent,
    ],
   
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjectPhaseActivityModule {

}