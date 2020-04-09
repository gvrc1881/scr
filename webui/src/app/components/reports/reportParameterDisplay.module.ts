import { NgModule } from '@angular/core';
import {NgForm} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { ReportService } from 'src/app/services/report.service';
import { MatDatepickerModule, MatInputModule, MatNativeDateModule } from '@angular/material';
import { ReportParameterDisplayComponent } from './reportParameterDisplay.component';
import{FormsModule}   from '@angular/forms';


const routes: Routes = [
    {
        path     : '',
        component :ReportParameterDisplayComponent
       
    },]
@NgModule({
    imports: [RouterModule.forChild(routes),
        //Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MatDatepickerModule,
        MatInputModule,
        MatNativeDateModule,
        FormsModule
        
    
       // MatToolbarModule,
        //MatSidenavModule,
         ],
    exports: [],
    declarations: [ReportParameterDisplayComponent],
    providers: [ReportService],
})
export class ReportParameterDisplayModule { }