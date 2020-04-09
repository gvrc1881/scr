import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MainMenuComponent } from './main-menu.component';
import { MainMenuService } from '../../../services/main.menu.service';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MatDialogModule, MatGridListModule, MatMenuModule, MatIconModule, MatToolbarModule, MatSidenavModule, MatNavList, MatListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/modules/material.modules';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const routes = [
    {
        path     : '',
        component: MainMenuComponent
    }
];

@NgModule({
    declarations: [
        MainMenuComponent,

    ],
    imports     : [
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatGridListModule,
        MaterialModule,   
        ReactiveFormsModule       
    ],
    providers: [
        MainMenuService,
        MatDialogModule,
        MatNavList,
    ],
    exports     : [
        MainMenuComponent
    ]
})

export class MainMenuModule
{
}
