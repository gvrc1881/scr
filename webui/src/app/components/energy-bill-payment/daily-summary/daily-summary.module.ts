import { NgModule } from '@angular/core';
import { DailySummaryComponent } from './daily-summary.component';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DailySummaryService } from 'src/app/services/daily-summary.service';


const routes: Routes = [
    {
        path: '',
        component: DailySummaryComponent,
    }
];

@NgModule({
    declarations:[
        DailySummaryComponent
    ],
    imports:[
    	RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers:[
    	DailySummaryService
    ],
    exports:[
    	DailySummaryComponent
    ]

})
export class DailySummaryModule {

}