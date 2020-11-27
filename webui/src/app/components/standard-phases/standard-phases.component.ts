import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatDialogRef, MatDialog } from '@angular/material';
import { StandardPhasesModel } from 'src/app/models/standard-phase.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FuseConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { Constants } from 'src/app/common/constants';
import { SendAndRequestService } from 'src/app/services/sendAndRequest.service';
import { DataViewDialogComponent } from '../data-view-dialog/data-view-dialog.component';
import { FieldLabelsConstant } from 'src/app/common/field-labels.constants';

@Component({
  selector: 'app-standard-phases',
  templateUrl: './standard-phases.component.html',
  styleUrls: []
})
export class StandardPhasesComponent implements OnInit {

  pagination =Constants.PAGINATION_NUMBERS;
  FiledLabels = FieldLabelsConstant.LABELS;
  Titles = FieldLabelsConstant.TITLE;
  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));
  confirmDialogRef: MatDialogRef<FuseConfirmDialogComponent>;
  displayedColumns = ['sno', 'name', 'description','sequence', 'dependencyToStart', 'typeOfWork','weightage','actions'];
  dataSource: MatTableDataSource<StandardPhasesModel>;
  dataViewDialogRef:MatDialogRef<DataViewDialogComponent>;
  filterData;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  gridData = [];

  standardPhasesList: any;

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private commonService: CommonService,
    private router: Router,
    private route: ActivatedRoute,
    public dialog: MatDialog,
    private sendAndRequestService:SendAndRequestService
  ) { }

  ngOnInit() {
    var permissionName = this.commonService.getPermissionNameByLoggedData("PROJECT ADMIN","STD Phases") ;
  	this.addPermission = this.commonService.getPermissionByType("Add", permissionName);
    this.editPermission = this.commonService.getPermissionByType("Edit", permissionName);
    this.deletePermission = this.commonService.getPermissionByType("Delete", permissionName);

    this.spinnerService.show();
    this.getStandardPhasesData();
    this.filterData = {
      filterColumnNames: [
        { "Key": 'sno', "Value": " " },
        { "Key": 'name', "Value": " " },
        { "Key": 'description', "Value": " " },
        { "Key": 'sequence', "Value": " " },
        { "Key": 'dependencyToStart', "Value": "" },
        { "Key": 'typeOfWork', "Value": "" },
        { "Key": 'weightage', "Value": " " },
      ],
      gridData: this.gridData,
      dataSource: this.dataSource,
      paginator: this.paginator,
      sort: this.sort
    };


  }
  getStandardPhasesData() {
    const standardPhases: StandardPhasesModel[] = [];
    this.sendAndRequestService.requestForGET(Constants.app_urls.STANDARD_PHASES.GET_STANDARD_PHASES).subscribe((data) => {
      this.standardPhasesList = data;
      for (let i = 0; i < this.standardPhasesList.length; i++) {
        this.standardPhasesList[i].sno = i + 1;
        standardPhases.push(this.standardPhasesList[i]);
      }
      this.filterData.gridData = standardPhases;
      this.dataSource = new MatTableDataSource(standardPhases);
      this.commonService.updateDataSource(this.dataSource, this.displayedColumns);
      this.filterData.dataSource = this.dataSource;
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
        this.sendAndRequestService.requestForDELETE(Constants.app_urls.STANDARD_PHASES.DELETE_STANDARD_PHASES ,id).subscribe(data => {
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Deleted Standard Phases Successfully");
          this.getStandardPhasesData();
        }, error => {
          console.log('ERROR >>>');
          this.spinnerService.hide();
          this.commonService.showAlertMessage("Standard Phases Deletion Failed.");
        });
      }
      this.confirmDialogRef = null;
    });
  }
  updatePagination() {
    this.filterData.dataSource = this.filterData.dataSource;
    this.filterData.dataSource.paginator = this.paginator;
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.filterData.dataSource.filter = filterValue;
  }
  ViewData(data){
    var result = {
      'title':this.Titles.STANDARD_PHASES_DATA,
      'dataSource':[                                 
                    { label:FieldLabelsConstant.LABELS.NAME, value:data.name },
                    { label:FieldLabelsConstant.LABELS.DESCRIPTION, value:data.description },
                    { label:FieldLabelsConstant.LABELS.SEQUENCE, value:data.sequence },
                    { label:FieldLabelsConstant.LABELS.DEPENDENCY_TO_START, value:data.dependencyToStart },
                    { label:FieldLabelsConstant.LABELS.TYPE_OF_WORK, value:data.typeOfWork },
                    { label:FieldLabelsConstant.LABELS.WEIGHTAGE, value:data.weightage },
                    
                  ]
    }
    this.dataViewDialogRef = this.dialog.open(DataViewDialogComponent, {
      disableClose: false,
      height: '400px',
      width: '80%',       
      data:result,  
    });            
  }
}
