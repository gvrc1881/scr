import { NgModel, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GuidenceItemComponent } from './guidence-item.component';
import { GuidenceItemService } from 'src/app/services/guidence-item.service';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/modules/material.modules';

const routes: Routes = [
    {
        path:'',
        component: GuidenceItemComponent,
    }
]

@NgModule({
    declarations: [
        GuidenceItemComponent
    ],
    imports: [
        RouterModule.forChild(routes),
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule
        
    ],
    providers: [
        GuidenceItemService
    ],
    exports: [
        GuidenceItemComponent
    ]
})
export class GuidenceItemModule{

}