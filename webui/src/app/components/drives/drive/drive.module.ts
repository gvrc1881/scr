import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DrivesComponent } from './drives.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddDriveComponent } from './add-drive/add-drive.component';
import { DrivesService } from 'src/app/services/drives.service';
import { AddDriveCategoryComponent } from './add-drive-category/add-drive-category.component';
import { AddDriveCategoryAssociationComponent } from './add-drive-category-association/add-drive-category-association.component';

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
        AddDriveCategoryAssociationComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        DrivesService
    ],
    exports: [
        DrivesComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DriveModule {

}