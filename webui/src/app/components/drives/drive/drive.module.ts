import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DrivesComponent } from './drives.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveComponent } from './add-drive/add-drive.component';
import { AddDriveCategoryComponent } from './add-drive-category/add-drive-category.component';
import { AddDriveCategoryAssociationComponent } from './add-drive-category-association/add-drive-category-association.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path: '',
        component: DrivesComponent,
    },
    {
        path: 'drive/:id',
        component: AddDriveComponent
    },
    {
        path: 'add-drive',
        component: AddDriveComponent,
    },
    {
        path: 'add-drive-category',
        component: AddDriveCategoryComponent,
    },
    {
        path: 'drive-category/:id',
        component: AddDriveCategoryComponent,
    },
    {
        path: 'add-drive-category-association',
        component: AddDriveCategoryAssociationComponent
    },
    {
        path: 'drive-category-association/:id',
        component: AddDriveCategoryAssociationComponent
    },
];

@NgModule({
    declarations: [
        DrivesComponent,
        AddDriveComponent,
        AddDriveCategoryComponent,
        AddDriveCategoryAssociationComponent,
        
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
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports: [
        DrivesComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DriveModule {

}