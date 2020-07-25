import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DriveStipulationComponent } from './drive-stipulation.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveStipulationComponent } from './add-drive-stipulation/add-drive-stipulation.component';
import { filterDirective } from 'src/app/common/filter.directive';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { FilterModule } from 'src/app/modules/filter.module';

const routes: Routes = [
    {
        path: '',
        component: DriveStipulationComponent,
    },   
    {
        path     : ':id',
        component: AddDriveStipulationComponent
    },
    {
        path        : 'add-drive',
        component:   AddDriveStipulationComponent
    },
];

@NgModule({
    declarations: [
        DriveStipulationComponent,
        AddDriveStipulationComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        FilterModule
    ],
    providers: [
        
    ],
    exports:[
        DriveStipulationComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class DriveStipulationModule {

}