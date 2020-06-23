import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModelComponent } from './model.component';
 import { ModelService } from 'src/app/services/model.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path:'',
        component: ModelComponent,
    }
]

@NgModule({
    declarations: [
        ModelComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
       ModelService
   ],
    exports: [
        ModelComponent
    ]
})
export class ModelModule{

}