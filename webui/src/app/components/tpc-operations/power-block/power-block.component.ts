import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { PowerBlockModel } from 'src/app/models/tpc-operations.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';

@Component({
  selector: 'power-block',
  templateUrl: './power-block.component.html',
  styleUrls: []
})
export class PowerBlockComponent implements OnInit {

	addPermission: boolean = true;
  	editPermission: boolean = true;
  	deletePermission: boolean = true;
  	powerBlockList: any;
  	dataSource: MatTableDataSource<PowerBlockModel>;
  	displayedColumns = ['sno', 'depot', 'elementarySectionCode', 'grantPeriod',  'actions'];
  	confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  	
  	@ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  	@ViewChild(MatSort, { static: true }) sort: MatSort;
  	@ViewChild('filter', { static: true }) filter: ElementRef;  	

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }
  
  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("OPERATIONS","POWER BLOCKS") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);
	this.getPowerBlocksData();
    this.spinnerService.show();

  }
  
  getPowerBlocksData() {
    const powerBlock: PowerBlockModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.OPERATIONS.POWER_BLOCK.GET_POWER_BLOCK).subscribe((data) => {
      this.powerBlockList = data;
      for (let i = 0; i < this.powerBlockList.length; i++) {
        this.powerBlockList[i].sno = i + 1;
        powerBlock.push(this.powerBlockList[i]);
      }

      this.dataSource = new MatTableDataSource(powerBlock);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  
  processEditAction(id) {
    this.router.navigate([id], { relativeTo: this.route });
  }
  
  delete(id) {
    this.confirmDialogRef = this.dialog.open(FuseConfirmDialogComponent, {
      disableClose: false
    });
    this.confirmDialogRef.componentInstance.confirmMessage = 'Are you sure you want to delete?';
    this.confirmDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinnerService.show();
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.OPERATIONS.POWER_BLOCK.DELETE_POWER_BLOCK_BY_ID ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Power Block Successfully");
          this.getPowerBlocksData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Power Block Deletion Failed.");
        });
      }
      this.confirmDialogRef = null;
    });
  }
  
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.dataSource.filter = filterValue;
  }

}

















