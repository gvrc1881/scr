import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RepositoryComponent } from './repository.component';
import { MatMenuModule, MatGridListModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/modules/material.modules';
import { UsersService } from 'src/app/services/users.service';
import { RepositoryService } from 'src/app/services/repository.service';

const routes: Routes = [
    {
        path     : '',
        component: RepositoryComponent,
       
    }
];

@NgModule({
    imports     : [     
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MatMenuModule,
        MatGridListModule,
        CommonModule,
        MaterialModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        RepositoryComponent,        
    ],
    providers   : [        
        RepositoryService
    ],
    exports     : [
        RepositoryComponent
    ]
})
export class RepositoryModule
{
}

