import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AshDisplayComponent } from './ash-display.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { filterDirective } from 'src/app/common/filter.directive';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { AddAshComponent } from './add-ash/add-ash.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';

const routes: Routes = [
    {
        path: '',
        component: AshDisplayComponent,
    },
    {
        path     : ':id',
        component: AddAshComponent
    },
    {
        path        : 'add-ash',
        component:   AddAshComponent
    },   
    
];

@NgModule({
    declarations: [
        AshDisplayComponent,
        AddAshComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
        CommonDirectivesModule,
        MatDatepickerModule,
        MatNativeDateModule
    ],
    providers: [
        
    ],
    exports:[
        AshDisplayComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AshDisplayModule {

}