import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { DriveChecklistModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';

@Component({
  selector: 'app-drive-checklist',
  templateUrl: './drive-checklist.component.html',
  styleUrls: ['./drive-checklist.component.css']
})
export class DriveChecklistComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));

  displayedColumns = ['sno', 'drive', 'measureActivityList', 'displayOrder', 'lowerLimit', 'upperLimit', 'active', 'actions'];
  dataSource: MatTableDataSource<DriveChecklistModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  drivesCheckList:any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private drivesService: DrivesService,
  ) { }

  ngOnInit() {
    this.addPermission = this.commonService.getPermission("Add");
    this.editPermission = this.commonService.getPermission("Edit");
    this.deletePermission = this.commonService.getPermission("Delete");

    this.spinnerService.show();
    this.getDrivesData();

  }

  getDrivesData() {
    const drive: DriveChecklistModel[] = [];
    this.drivesService.getDrivesCheckListData().subscribe((data) => {

      this.drivesCheckList = data;
      for (let i = 0; i < this.drivesCheckList.length; i++) {
        this.drivesCheckList[i].sno = i + 1;
        drive.push(this.drivesCheckList[i]);
      }

      this.dataSource = new MatTableDataSource(drive);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;            
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }


}
