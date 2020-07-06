import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ObservationCategoriesComponent } from './observation-categories.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';


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
        
    ],
    exports: [
        ObservationCategoriesComponent
    ]
})
export class ObservationCategoriesModule{

}