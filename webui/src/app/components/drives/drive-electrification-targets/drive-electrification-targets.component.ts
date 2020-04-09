import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ElectrificationTargetstModel } from 'src/app/models/drive.model';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-drive-electrification-targets',
  templateUrl: './drive-electrification-targets.component.html',
  styleUrls: ['./drive-electrification-targets.component.css']
})
export class DriveElectrificationTargetsComponent implements OnInit {

  editPermission: boolean = true;
  addPermission: boolean = true;
  deletePermission: boolean = true;
  userdata: any = JSON.parse(localStorage.getItem('userData'));

  displayedColumns = ['sno', 'section', 'guage', 'targetDate', 'status', 'division', 'executionAgency',
    'TKM', 'RKM', 'crsInspection', 'crsAuthorisation', 'targetSetBy','doublingTrippling','state','phase',
    'proposalScheme', 'sanctionByBoard', 'yearOfSanction','dateOfCompletion','actions'];
  dataSource: MatTableDataSource<ElectrificationTargetstModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  electrificationTargetsList:any;

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
    this.getElectrificationTargetsData();

  }

  getElectrificationTargetsData() {
    const electrificationTargets: ElectrificationTargetstModel[] = [];
    this.drivesService.getElectrificationTargetsData().subscribe((data) => {

      this.electrificationTargetsList = data;
      for (let i = 0; i < this.electrificationTargetsList.length; i++) {
        this.electrificationTargetsList[i].sno = i + 1;
        electrificationTargets.push(this.electrificationTargetsList[i]);
      }

      this.dataSource = new MatTableDataSource(electrificationTargets);
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
    this.drivesService.deleteElectrificationTargetsData(id).subscribe(data => {
      console.log(JSON.stringify(data));
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Deleted Electrification Targets Successfully");
      this.getElectrificationTargetsData();
    }, error => {
      console.log('ERROR >>>');
      this.spinnerService.hide();
      this.commonService.showAlertMessage("Electrification Targets Deletion Failed.");
    })
  }
}
