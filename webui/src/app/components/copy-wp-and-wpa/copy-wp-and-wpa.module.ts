import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CopyWPAndWPAComponent } from './copy-wp-and-wpa.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path: '',
        component: CopyWPAndWPAComponent,
    }
];

@NgModule({
    declarations: [
        CopyWPAndWPAComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,        
        Ng4LoadingSpinnerModule.forRoot(),
    ],
    providers: [
        
    ],
    exports:[
        CopyWPAndWPAComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class CopyWPAndWPAModule {

}