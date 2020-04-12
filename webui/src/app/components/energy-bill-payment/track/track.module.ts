import { NgModule } from '@angular/core';
import { TrackService } from 'src/app/services/track.service';
import { TrackComponent } from './track.component';
import { Routes, RouterModule } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

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
        Ng4LoadingSpinnerModule.forRoot()
    ],
    providers: [
        TrackService
    ],
    exports:[
        TrackComponent
    ]
})
export class TrackModule {

}