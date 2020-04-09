import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EnergyBillPaymentService } from 'src/app/services/energy-bill-payment.service';
import { ContentManagementComponent } from './content-management.component';
import { Routes, RouterModule } from '@angular/router';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { ContentManagementService } from 'src/app/services/content-management.service';
import { SatPopoverModule } from '@ncstate/sat-popover';
import { InlineEditComponent } from '../inline-edit/inline-edit.component';

const routes: Routes = [
    {
        path: '',
        component: ContentManagementComponent,
    }
];

@NgModule({
    declarations: [
        ContentManagementComponent,
        InlineEditComponent
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
        ContentManagementService
    ],
    exports:[
        ContentManagementComponent
    ],
    schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ContentManagementModule {

}