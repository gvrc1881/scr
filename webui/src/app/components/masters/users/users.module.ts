import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './users.component';
import { UsersService } from '../../../services/users.service';
import { UserMenuComponent } from './user-menu/user-menu.component';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MaterialModule } from 'src/app/modules/material.modules';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RepositoryService } from 'src/app/services/repository.service';




const routes: Routes = [
    {
        path     : '',
        component: UserMenuComponent
    },
   
    {
        path     : ':id',
        component: UsersComponent
    },
    {
        path        : 'add-user',
        component:   UsersComponent
        //canActivate: [AuthGuard]
        //redirectTo:'dashboard'
    },
    

];

@NgModule({
    imports     : [        
        RouterModule.forChild(routes),
        Ng4LoadingSpinnerModule.forRoot(),
        MaterialModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        UsersComponent,
        UserMenuComponent,
       
    ],
    providers: [
        UsersService,
        RepositoryService
    ],
    exports     : [
        UsersComponent
    ]
})
export class UserModule
{
}
