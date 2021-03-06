import { NgModule } from '@angular/core';
import { TrackComponent } from './track.component';
import { Routes, RouterModule } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';

const routes: Routes = [
    {
        path: '',
        component: TrackComponent,
    }
];

@NgModule({
    declarations: [
        TrackComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        DecimalValidationsModule,
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        
    ],
    exports:[
        TrackComponent
    ]
})
export class TrackModule {

}