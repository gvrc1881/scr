import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { WPASectionTargetsComponent } from './wpa-section-targets.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path: '',
        component: WPASectionTargetsComponent,
    }
];

@NgModule({
    declarations: [
        WPASectionTargetsComponent,
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
        WPASectionTargetsComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class WPASectionTargetsModule {

}