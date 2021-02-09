import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProjectActivityInspectionComponent } from './project-activity-inspection.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path: '',
        component: ProjectActivityInspectionComponent,
    },
];

@NgModule({
    declarations: [
        ProjectActivityInspectionComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,        
        Ng4LoadingSpinnerModule.forRoot(),
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        ProjectActivityInspectionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProjectActivityInspectionModule {

}