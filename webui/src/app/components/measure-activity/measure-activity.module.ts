import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MeasureActivityComponent } from './measure-activity.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path:'',
        component: MeasureActivityComponent,
    }
]

@NgModule({
    declarations: [
        MeasureActivityComponent
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
       
   ],
    exports: [
        MeasureActivityComponent
    ]
})
export class MeasureActivityModule{

}