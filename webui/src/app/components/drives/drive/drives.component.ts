import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DriveModel } from 'src/app/models/drive.model';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-drives',
  templateUrl: './drive.component.html',
  styleUrls: ['./drive.component.css']
})
export class DrivesComponent implements OnInit {
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));

  displayedColumns = ['sno', 'name', 'description', 'fromDate', 'toDate', 'depoType', 'assetType',
    'assetDescription', 'criteria', 'targetQuantity', 'isIdRequired', 'functionalUnit',
    'checkList', 'active', 'actions'];
  dataSource: MatTableDataSource<DriveModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  drivesList:any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private drivesService: DrivesService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.addPermission = this.commonService.getPermission("Add");
    this.editPermission = this.commonService.getPermission("Edit");
    this.deletePermission = this.commonService.getPermission("Delete");

    this.spinnerService.show();
    this.getDrivesData();

  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  getDrivesData() {
    const drive: DriveModel[] = [];
    this.drivesService.getDrivesData().subscribe((data) => {

      this.drivesList = data;
      for (let i = 0; i < this.drivesList.length; i++) {
        this.drivesList[i].sno = i + 1;
        drive.push(this.drivesList[i]);
      }

      this.dataSource = new MatTableDataSource(drive);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;            
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  processEditAction(id){
    this.router.navigate([id],{relativeTo: this.route});
  }
  delete(id){
    this.spinnerService.show();
    this.drivesService.deleteDriveData(id).subscribe(data => {
      console.log(JSON.stringify(data));
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Drive Successfully");
      this.getDrivesData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Drive Deletion Failed.");
    })
  }
}
