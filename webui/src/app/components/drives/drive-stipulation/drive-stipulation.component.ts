import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { StipulationstModel } from 'src/app/models/drive.model';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import { CommonService } from 'src/app/common/common.service';
import { DrivesService } from 'src/app/services/drives.service';
import { Router, ActivatedRoute } from '@angular/router';

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

  displayedColumns = ['sno', 'stipulation', 'stipulationTo', 'dateOfStipulation', 'dateComplied',
   'compliance', 'attachment', 'compliedBy', 'assetType','actions'];
  dataSource: MatTableDataSource<StipulationstModel>;


  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  stipulationsList:any;

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
    this.getStipulationData();

  }

  getStipulationData() {
    const stipulations: StipulationstModel[] = [];
    this.drivesService.getStipulationData().subscribe((data) => {

      this.stipulationsList = data;
      for (let i = 0; i < this.stipulationsList.length; i++) {
        this.stipulationsList[i].sno = i + 1;
        stipulations.push(this.stipulationsList[i]);
      }

      this.dataSource = new MatTableDataSource(stipulations);
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
}
