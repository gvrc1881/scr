import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/modules/material.modules';
import { SubMenuComponent } from './submenu.component';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { SubMenuService } from '../../../services/sub-menu.service';
import { MatDialogModule, MatNavList, MatSelectModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
const routes = [
    {
        path     : '',
        component: SubMenuComponent
    }
];
@NgModule({
    declarations: [
        SubMenuComponent
        ],
    imports     : [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MaterialModule,
        ReactiveFormsModule
    ],
    providers: [
        SubMenuService,        
        MaterialModule ,
        
    ],
    exports     : [
        SubMenuComponent
    ]
})

export class SubMenuModule
{
}
