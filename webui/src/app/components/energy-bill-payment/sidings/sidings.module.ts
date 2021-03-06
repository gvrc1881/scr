import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { SidingsComponent } from './sidings.component';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';

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
        DecimalValidationsModule,
        OwlDateTimeModule,
        OwlNativeDateTimeModule
        
    ],
    providers: [],
    exports: [
        SidingsComponent
    ]
})
export class SidingsModule{

}