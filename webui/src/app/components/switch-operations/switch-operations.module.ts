import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SwitchOperationsComponent } from './switch-operations.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path:'',
        component: SwitchOperationsComponent,
    }
]

@NgModule({
    declarations: [
        SwitchOperationsComponent
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
        SwitchOperationsComponent
    ]
})
export class SwitchOperationsModule{

}