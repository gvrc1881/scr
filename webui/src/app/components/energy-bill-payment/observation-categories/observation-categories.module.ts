import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ObservationCategoriesComponent } from './observation-categories.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { ObservationCategoriesService } from 'src/app/services/observation-categories.service';
import { ReportService } from 'src/app/services/report.service';

const routes: Routes = [
    {
        path:'',
        component: ObservationCategoriesComponent,
    }
]

@NgModule({
    declarations: [
        ObservationCategoriesComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        ObservationCategoriesService,
        ReportService
    ],
    exports: [
        ObservationCategoriesComponent
    ]
})
export class ObservationCategoriesModule{

}