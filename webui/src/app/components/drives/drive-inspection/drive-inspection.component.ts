import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { InspectionstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';

@Component({
  selector: 'app-drive-inspection',
  templateUrl: './drive-inspection.component.html',
  styleUrls: ['./drive-inspection.component.css']
})
export class DriveInspectionComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));

  displayedColumns = ['sno', 'inspectionType', 'section', 'sectionStartLocation', 'sectionEndLocation',
   'dateOfInspection', 'RKM', 'TKM', 'remarks', 'authorisationDate', 'chargingDate', 'attachment',
    'station', 'stipulationsId', 'actions'];
  dataSource: MatTableDataSource<InspectionstModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  inspectionsList:any;

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
    this.getInspectionData();

  }

  getInspectionData() {
    const inspections: InspectionstModel[] = [];
    this.drivesService.getInspectionData().subscribe((data) => {

      this.inspectionsList = data;
      for (let i = 0; i < this.inspectionsList.length; i++) {
        this.inspectionsList[i].sno = i + 1;
        inspections.push(this.inspectionsList[i]);
      }

      this.dataSource = new MatTableDataSource(inspections);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;            
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }

}
