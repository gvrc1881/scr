import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GuidenceItemComponent } from './guidence-item.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path:'',
        component: GuidenceItemComponent,
    }
]

@NgModule({
    declarations: [
        GuidenceItemComponent
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
        
    ],
    exports: [
        GuidenceItemComponent
    ]
})
export class GuidenceItemModule{

}