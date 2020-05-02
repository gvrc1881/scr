import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ObservationCheckListComponent } from './observation-check-list.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { ObservationsCheckListService } from 'src/app/services/observations-check-list.service';
import { ReportService } from 'src/app/services/report.service';

const routes: Routes = [
    {
        path:'',
        component: ObservationCheckListComponent,
    }
]

@NgModule({
    declarations: [
        ObservationCheckListComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        ObservationsCheckListService,
        ReportService
    ],
    exports: [
        ObservationCheckListComponent
    ]
})
export class ObservationCheckListModule{

}