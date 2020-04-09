import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

@Component({
    selector   : 'change-password-dialog',
    templateUrl: './change-password-dialog.component.html',
    styleUrls  : ['./change-password-dialog.component.scss']
})
export class ChangePasswordDialogComponent implements OnInit
{
    public confirmMessage: string;

    constructor(public dialogRef: MatDialogRef<ChangePasswordDialogComponent>)
    {
    }

    ngOnInit()
    {
    }

}
