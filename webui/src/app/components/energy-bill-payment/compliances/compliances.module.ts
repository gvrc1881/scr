import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CompliancesComponent } from './compliances.component';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule,CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { CommonDirectivesModule } from 'src/app/modules/common-directives.module';

const routes: Routes = [
    {
        path:'',
        component: CompliancesComponent,
    }
]

@NgModule({
    declarations: [
        CompliancesComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        OwlNativeDateTimeModule,
        OwlDateTimeModule,
        CommonDirectivesModule
    ],
    providers: [
    ],
    //import
    exports: [
        CompliancesComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CompliancesModule{

}