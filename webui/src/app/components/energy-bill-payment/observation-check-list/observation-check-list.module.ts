import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ObservationCheckListComponent } from './observation-check-list.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';


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
        
    ],
    exports: [
        ObservationCheckListComponent
    ]
})
export class ObservationCheckListModule{

}