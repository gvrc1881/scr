import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AssistanceComponent } from './assistance.component';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { AddAssistanceComponent } from './add-assistance/add-assistance.component';
import { NumberValidationsModule } from 'src/app/modules/number-validations.module';
import { DecimalValidationsModule } from 'src/app/modules/decimal-validations.module';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';
import { filterDirective } from 'src/app/common/filter.directive';

const routes: Routes = [
    {
        path: '',
        component: AssistanceComponent,
    },   
    {
        path     : ':id',
        component: AddAssistanceComponent
    },
    {
        path        : 'add-assistance',
        component:   AddAssistanceComponent
    },
];

@NgModule({
    declarations: [
        AssistanceComponent,
        AddAssistanceComponent,
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,      
        Ng4LoadingSpinnerModule.forRoot(),
        NumberValidationsModule,
        CommonDirectivesModule,
        DecimalValidationsModule
    ],
    providers: [
        
    ],
    exports:[
        AssistanceComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AssistanceModule {

}