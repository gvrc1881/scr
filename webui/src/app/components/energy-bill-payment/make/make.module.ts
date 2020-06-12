import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MakeComponent } from './make.component';
 import { MakeService } from 'src/app/services/make.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

const routes: Routes = [
    {
        path:'',
        component: MakeComponent,
    }
]

@NgModule({
    declarations: [
        MakeComponent
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
       MakeService
   ],
    exports: [
        MakeComponent
    ]
})
export class MakeModule{

}