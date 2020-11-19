import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProjectPhasesComponent } from './project-phases.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddProjectPhasesComponent } from './add-project-phases/add-project-phases.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { MatNativeDateModule } from '@angular/material/core';

const routes: Routes = [
    {
        path: '',
        component: ProjectPhasesComponent,
    },   
    {
        path     : ':id',
        component: AddProjectPhasesComponent
    },
    {
        path        : 'add-project',
        component:   AddProjectPhasesComponent
    }
   
];

@NgModule({
    declarations: [
        ProjectPhasesComponent, 
        AddProjectPhasesComponent 
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
        ProjectPhasesComponent,
    ],
   
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjectPhasesModule {

}