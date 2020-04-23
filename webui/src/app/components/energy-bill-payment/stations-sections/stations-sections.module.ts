import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StationsSectionsComponent } from './stations-sections.component';
import { StationsSectionsService } from 'src/app/services/stations-sections.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path:'',
        component: StationsSectionsComponent,
    }
]

@NgModule({
    declarations: [
        StationsSectionsComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        StationsSectionsService
    ],
    exports: [
        StationsSectionsComponent
    ]
})
export class StationsSectionsModule{

}