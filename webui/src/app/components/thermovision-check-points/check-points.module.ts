import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CheckPointsComponent,CopyCheckPointsComponent } from './check-points.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddCheckPointsComponent } from './add-check-points/add-check-points.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime'; 
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { MatNativeDateModule } from '@angular/material/core';

const routes: Routes = [
    {
        path: '',
        component: CheckPointsComponent,
    },   
    {
        path     : ':id',
        component: AddCheckPointsComponent
    },
    {
        path        : 'add-project-phases',
        component:   AddCheckPointsComponent
    }
   
];

@NgModule({
    declarations: [
        CheckPointsComponent, 
        CopyCheckPointsComponent,
        AddCheckPointsComponent 
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        NumberValidationsModule,
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        DecimalValidationsModule,
        MatNativeDateModule
    ],
    providers: [
        
    ],
    exports: [
        CheckPointsComponent,
    ],
    entryComponents: [ CopyCheckPointsComponent ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CheckPointsModule {

}