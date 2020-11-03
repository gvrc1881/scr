import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

@Component({
    selector   : 'fuse-confirm-popup',
    templateUrl: './confirm-popup.component.html',
    styleUrls  : ['./confirm-popup.component.scss']
})
export class FuseConfirmPopupComponent implements OnInit
{
    public confirmMessage: string;

    constructor(public dialogRef: MatDialogRef<FuseConfirmPopupComponent>)
    {
    }

    ngOnInit()
    {
    }

}
