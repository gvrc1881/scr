import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { SidingsComponent } from './sidings.component';
import { SidingsService } from 'src/app/services/sidings.service';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path:'',
        component: SidingsComponent,
    }
]

@NgModule({
    declarations: [
        SidingsComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        DecimalValidationsModule
        
    ],
    providers: [SidingsService],
    exports: [
        SidingsComponent
    ]
})
export class SidingsModule{

}