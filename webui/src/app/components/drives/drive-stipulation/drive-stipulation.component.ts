import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { StipulationstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FilesInformationDialogComponent } from '../../file-information-dialog/file-information-dialog.component';

@Component({
  selector: 'app-drive-stipulation',
  templateUrl: './drive-stipulation.component.html',
  styleUrls: ['./drive-stipulation.component.css']
})
export class DriveStipulationComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  filterData;
  displayedColumns = ['sno', 'stipulation', 'stipulationTo', 'dateOfStipulation', 'dateComplied',
    'compliance', 'attachment', 'compliedBy', 'assetType', 'actions'];
  dataSource: MatTableDataSource<StipulationstModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];
  stipulationsList: any;
  fileInformationDialogRef: MatDialogRef<FilesInformationDialogComponent>;
  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private drivesService: DrivesService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.addPermission = this.commonService.getPermission("Add");
    this.editPermission = this.commonService.getPermission("Edit");
    this.deletePermission = this.commonService.getPermission("Delete");

    this.spinnerService.show();
    this.getStipulationData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'stipulation', "Value": " " },
        { "Key": 'stipulationTo', "Value": " " },
        { "Key": 'dateOfStipulation', "Value": " " },
        { "Key": 'dateComplied', "Value": " " },
        { "Key": 'compliance', "Value": "" },
        { "Key": 'attachment', "Value": " " },
        { "Key": 'compliedBy', "Value": " " },
        { "Key": 'assetType', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };
  }

  getStipulationData() {
    const stipulations: StipulationstModel[] = [];
    this.drivesService.getStipulationData().subscribe((data) => {

      this.stipulationsList = data;
      for (let i = 0; i < this.stipulationsList.length; i++) {
        this.stipulationsList[i].sno = i + 1;
        this.stipulationsList[i].assetType = this.stipulationsList[i].assetType != null ? this.stipulationsList[i].assetType['productName'] : '';
        stipulations.push(this.stipulationsList[i]);
      }
      this.filterData.gridData = stipulations;
      this.dataSource = new MatTableDataSource(stipulations);
      this.filterData.dataSource = this.dataSource;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.spinnerService.hide();
    }, error => {
      this.spinnerService.hide();
    });
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  processEditAction(id) {
    this.router.navigate([id], { relativeTo: this.route });
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.filterData.dataSource.filter = filterValue;
  }
  delete(id) {
    this.spinnerService.show();
    this.drivesService.deleteStipulationData(id).subscribe(data => {
      console.log(JSON.stringify(data));
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Stipulation Successfully");
      this.getStipulationData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Inspection Stipulation Failed.");
    })
  }

  filesInfor: any;
  viewFilesDetails(id) {
    this.spinnerService.show();
    localStorage.setItem('driveFileType', 'Stipulation');
    localStorage.setItem('driveFileTypeId', id);
    this.drivesService.findStipulationDataById(id).subscribe((response) => {
      this.filesInfor = response;
      var data = [];
      if (this.filesInfor.attachment != '') {
        console.log(JSON.stringify(response));
        data = this.filesInfor.attachment.split(',');
        console.log('data= ' + JSON.stringify(data))
      }
      this.spinnerService.hide();
      this.fileInformationDialogRef = this.dialog.open(FilesInformationDialogComponent, {
        disableClose: false,
        height: '600px',
        width: '80%',
        data: data,
      });
    }, error => this.commonService.showAlertMessage(error));


  }


}
