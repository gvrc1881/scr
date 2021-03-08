import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { FusionChartsModule } from 'angular-fusioncharts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { GoogelMapComponent } from './google-map.component';
//import { AgmCoreModule } from '@agm/core';
//import {} from 'googlemaps';

const routes: Routes = [
    {
        path     : '',
        component: GoogelMapComponent,
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        FusionChartsModule,
        FormsModule,
        MaterialModule,
        ReactiveFormsModule,
        /*
        AgmCoreModule.forRoot({
            // please get your own API key here:
            // https://developers.google.com/maps/documentation/javascript/get-api-key?hl=en
            apiKey: 'AIzaSyBw4yZUws1cbRhOLLtGFFAu0c6XuGBvrzA'
          })*/
    ],
    declarations: [
        GoogelMapComponent,
    ],
    providers   : [
        Ng4LoadingSpinnerService,
    ],
    exports     : [
        GoogelMapComponent
    ]
})
export class GoogleMapModule
{
}


