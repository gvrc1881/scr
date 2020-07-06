import { NgModule } from '@angular/core';
import {NgForm} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule, MatInputModule, MatNativeDateModule } from '@angular/material';
import { ReportParameterDisplayComponent } from './reportParameterDisplay.component';
import{FormsModule}   from '@angular/forms';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';


const routes: Routes = [
    {
        path     : '',
        component :ReportParameterDisplayComponent
       
    },]
@NgModule({
    imports: [RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MatDatepickerModule,
        MatInputModule,
        MatNativeDateModule,
        FormsModule
         ],
    exports: [],
    declarations: [ReportParameterDisplayComponent],
    providers: [],
})
export class ReportParameterDisplayModule { }