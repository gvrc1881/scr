import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TestInspectionComponent } from './test-inspection.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddTestInspectionComponent } from './add-test_inspection/add-test-inspection.component';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path: '',
        component: TestInspectionComponent,
    },   
    {
        path     : ':id',
        component: AddTestInspectionComponent
    },
    {
        path        : 'add-test-inspection',
        component:   AddTestInspectionComponent
    },
];

@NgModule({
    declarations: [
        TestInspectionComponent,
        AddTestInspectionComponent,
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
        TestInspectionComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class TestInspectionModule {

}