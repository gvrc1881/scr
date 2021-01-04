import { Component, OnInit, OnChanges, DoCheck, AfterViewInit, AfterContentInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRouteSnapshot, ActivatedRoute, NavigationEnd } from '@angular/router';
import { CommonService } from 'src/app/common/common.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, DoCheck, OnDestroy {
  loginValidate: boolean = true;
  dashboard: boolean = true;
  MenusList: any = [];
  menus: any = [];
  currentTab: string = "";
  userName: string;
  userdata: any;
  loggedUser: any;
  rolePermission: boolean = true;
  clicked = 'dashboard';
  mySubscription: any;
  constructor(
    private route: Router,
    private commonService: CommonService,
    private authService: AuthenticationService
  ) {
    this.clicked = window.location.pathname.substring(1);
  }
  ngOnDestroy() {
    if (this.mySubscription) {
      this.mySubscription.unsubscribe();
    }
  }

  ngDoCheck() {
    this.loginValidate = this.commonService.loginValidate(window.location.pathname);
    let refresh = localStorage.getItem("headerRefresh");
    if (refresh == 'refresh') {
     const path = window.location.pathname;
      this.findMenus(path);
      this.userdata = JSON.parse(localStorage.getItem('userData'));
      this.userName = !!this.userdata && !!this.userdata.username && this.userdata.username;
      localStorage.setItem("headerRefresh", 'noRefresh');
    }
  }
  ngOnInit() {
    this.loginValidate = this.commonService.loginValidate(window.location.pathname);
    if (!!localStorage.getItem('userData')) {
      this.userdata = JSON.parse(localStorage.getItem('userData'));
      this.userName = !!this.userdata && !!this.userdata.username && this.userdata.username;
    }
    const path = window.location.pathname;
    this.dashboard = path == '/dashboard' ? false : true;
    if (!!localStorage.getItem("loggedUser")) {
      this.loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
      this.rolePermission = this.commonService.rolePermission();
      this.findMenus(path);
    }else{
      //this.findMenus(path);
      console.log('path ='+path)
      this.clicked = path;
    }
  }
  select(i, path) {
   // const path = window.location.pathname;
    this.clicked = i;
   // this.findMenus(path);
  }
  findMenus(path) {
    this.MenusList = [];
    this.MenusList = [
      {
        ID: 1,
        menuName: 'Dashboard',
        menuUrl: 'dashboard',
        icon: "fa fa-home",
        color: "",
        isSelected: true,
        permission: this.commonService.findPermission('Dashboard', 'menu', 'view'),
        currentTab: !!path && path.includes("dashboard") || path.includes('stock-quantities') || path.includes('energy-graph') || path.includes('dash-board')|| path.includes('project-summary')? "open" : "",
        subMenus: [
          {
            subMenuName: "Dashboard",
            subMenuURL: "dashboard",
            color: "#1285EE",
            subMenuIcon: "fa fa-home",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Dashboard', 'submenu', 'view'),
            currentSubMenu: !!path && (path.includes("dashboard")) ? "active-item" : "",
          },
          {
            subMenuName: "Stock Quantities",
            subMenuURL: "stock-quantities",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Stock Quantities', 'submenu', 'view'),
            //permission:true,
            currentSubMenu: !!path && path.includes("stock-quantities") ? "active-item" : "",
          },
          {
            subMenuName: "Google Map",
            subMenuURL: "google-map",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            //permission: this.commonService.findPermission('Google Map', 'submenu', 'view'),
            permission:true,
            currentSubMenu: !!path && path.includes("google-map") ? "active-item" : "",
          },
          {
            subMenuName: "Energy Cons",
            subMenuURL: "energy-graph",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Energy Consumption Graph', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("energy-graph") ? "active-item" : "",
          },
          {
            subMenuName: "Dash Board",
            subMenuURL: "dash-board",
            color: "#1285EE",
            subMenuIcon: "fa fa-home",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Dash Board', 'submenu', 'view'),
            currentSubMenu: !!path && (path.includes("dash-board")) ? "active-item" : "",
          },
          {
            subMenuName: "Project Summary",
            subMenuURL: "project-summary",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Project Summary', 'submenu', 'view'),
            currentSubMenu: !!path && (path.includes("project-summary")) ? "active-item" : "",
          }
        ]
      },
      {
        ID: 2,
        menuName: 'Reports',
        menuUrl: 'daily-progress-reports',
        icon: "fa fa-area-chart",
        color: "#6212EE",
        permission: this.commonService.findPermission('Reports', 'menu', 'view'),
        isSelected: true,
        currentTab: !!path && (path.includes("report-names") || path.includes("daily-progress-reports") ||
          path.includes("asset-reports") || path.includes("asset-master-reports")) ||
          path.includes("inventory-reports") || path.includes("psi-reports") ||
          path.includes("zonal-reports") || path.includes("zmms-reports")|| path.includes("day-progress-reports")
          || path.includes("master-report") || path.includes("ask-rate-reports") || path.includes("s&t-reports") || path.includes("cpd-reports")
          || path.includes("project-progress-report") || path.includes("yard-siding-reports") || path.includes("proj-progress-percentage") || path.includes("month-progress") ? "open" : "",
        subMenus: [
          {
            subMenuName: "Daily Progress Reports",
            subMenuURL: "daily-progress-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Daily Progress Reports', 'submenu', 'view'),
            currentSubMenu: !!path && (path.includes("daily-progress-reports")) ? "active-item" : "",
          },
          {
            subMenuName: "Asset Reports",
            subMenuURL: "asset-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Asset Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("asset-reports") ? "active-item" : "",
          },
          {
            subMenuName: "Asset Master Reports",
            subMenuURL: "asset-master-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Asset Master Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("asset-master-reports") ? "active-item" : "",
          },
          {
            subMenuName: "Inventory Reports",
            subMenuURL: "inventory-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Inventory Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("inventory-reports") ? "active-item" : "",
          },
          {
            subMenuName: "PSI Reports",
            subMenuURL: "psi-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('PSI Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("psi-reports") ? "active-item" : "",
          },
          {
            subMenuName: "Zonal Reports",
            subMenuURL: "zonal-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Zonal Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("zonal-reports") ? "active-item" : "",

          },
          {
            subMenuName: "ZMMS Reports",
            subMenuURL: "zmms-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('ZMMS Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("zmms-reports") ? "active-item" : "",

          },   
          {
            subMenuName: "Day Progress Reports",
            subMenuURL: "day-progress-reports",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Day Progress Reports', 'submenu', 'view'),
            currentSubMenu: !!path && (path.includes("day-progress-reports")) ? "active-item" : "",
          },
          {
            subMenuName: "Master Reports",
            subMenuURL: "master-report",
            color: "#1285EE",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Master Reports', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("master-report") ? "active-item" : "",
          },
          // {
          //   subMenuName: "Assets Master Reports",
          //   subMenuURL: "assets-master-reports",
          //   color: "#1285EE",
          //   subMenuIcon: "fa fa-file",
          //   rolePermission: this.rolePermission,
          //   permission: this.commonService.findPermission('Assets Master Reports', 'submenu', 'view'),
          //   currentSubMenu: !!path && path.includes("assets-master-reports") ? "active-item" : "",
          // },  

            {
              subMenuName: "Ask Rate Reports",
              subMenuURL: "ask-rate-reports",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('Ask Rate Reports', 'submenu', 'view'),
              currentSubMenu: !!path && (path.includes("ask-rate-reports")) ? "active-item" : "",
            },
            {
              subMenuName: "S&T Reports",
              subMenuURL: "s&t-reports",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('S&T Reports', 'submenu', 'view'),
              currentSubMenu: !!path && path.includes("s&t-reports") ? "active-item" : "",
            },
            {
              subMenuName: "CPD Reports",
              subMenuURL: "cpd-reports",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('CPD Reports', 'submenu', 'view'),
              currentSubMenu: !!path && path.includes("cpd-reports") ? "active-item" : "",
            },
            {
              subMenuName: "Project Progress Report",
              subMenuURL: "project-progress-report",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('Project Progress Report', 'submenu', 'view'),
              currentSubMenu: !!path && path.includes("project-progress-report") ? "active-item" : "",
            },
            {
              subMenuName: "Yard Siding Reports",
              subMenuURL: "yard-siding-reports",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('Yard Siding Reports', 'submenu', 'view'),
              currentSubMenu: !!path && path.includes("yard-siding-reports") ? "active-item" : "",
            },     
            {
              subMenuName: "Month Progress",
              subMenuURL: "month-progress",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('Month Progress', 'submenu', 'view'),
              currentSubMenu: !!path && path.includes("month-progress") ? "active-item" : "",

            },  
            {
              subMenuName: "Proj Progress Percentage",
              subMenuURL: "proj-progress-percentage",
              color: "#1285EE",
              subMenuIcon: "fa fa-file",
              rolePermission: this.rolePermission,
              permission: this.commonService.findPermission('Proj Progress Percentage', 'submenu', 'view'),
              currentSubMenu: !!path && path.includes("proj-progress-percentage") ? "active-item" : "",

            }, 
        ]
      },
      {
        ID: 3,
        menuName: 'Jobs Tracking',
        menuUrl: 'schedule',
        icon: "fa fa-briefcase",
        color: "#6212EE",
        isSelected: false,
        permission: this.commonService.findPermission('Schedule Tracking', 'menu', 'view'),
        class: "chandra",
        currentTab: !!path && path.includes("schedule") || path.includes("jobs") || path.includes("divisions") ? "open admin-view" : " admin-view",
        subMenus: [
          {
            subMenuName: "Tracking Info",
            subMenuIcon: "fa fa-briefcase",
            subMenuURL: "schedule",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Tracking Info', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("schedule") ? "active-item" : "",
          },
          {
            subMenuName: "Jobs Info",
            subMenuIcon: "fa fa-briefcase",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Jobs Info', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("jobs") ? "active-item" : "",
          },
          {
            subMenuName: "Divisions Info",
            subMenuIcon: "fa fa-briefcase",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Divisions Info', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("divisions") ? "active-item" : "",
          }
        ]
      },
      {
        ID: 4,
        menuName: 'Masters',
        menuUrl: 'masters',
        icon: "fa fa-wrench",
        color: "#85929E",
        isSelected: false,
        permission: this.commonService.findPermission('Masters', 'menu', 'view'),
        currentTab: !!path && (path.includes("masters") || path.includes("roles") || path.includes("rolePermissions")
          || path.includes("users") || path.includes("department") || path.includes("settings") || path.includes("repository")
          || path.includes("jobType") || path.includes("timeInterval") || path.includes("measure-activity")) ? "open" : "",
        subMenus: [
          {
            subMenuName: "Roles",
            subMenuURL: "roles",
            subMenuIcon: "fa fa-lock",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Roles', 'submenu', 'view'),
            currentSubMenu: !!path && (path.includes("masters") || path.includes("roles")) ? "active-item" : "",
          },
          {
            subMenuName: "Roles Permission",
            subMenuURL: "rolePermissions",
            subMenuIcon: "fa fa-lock",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Roles Permission', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("rolePermissions") ? "active-item" : "",
          },
          {
            subMenuName: "Department",
            subMenuURL: "department",
            subMenuIcon: "fa fa-lock",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Department', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("department") ? "active-item" : "",
          },
          {
            subMenuName: "Users",
            subMenuURL: "users",
            subMenuIcon: "fa fa-users",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Users', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("users") ? "active-item" : "",
          },
          {
            subMenuName: "Schedule",
            subMenuURL: "settings",
            subMenuIcon: "fa fa-cogs",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Schedule', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("settings") ? "active-item" : "",
          },
          {
            subMenuName: "Repository",
            subMenuURL: "repository",
            subMenuIcon: "fa fa-bars",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Repository', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("repository") ? "active-item" : "",
          },
          {
            subMenuName: "Job Type",
            subMenuURL: "jobType",
            subMenuIcon: "fa fa-align-left",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Job Type', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("jobType") ? "active-item" : "",
          },
          {
            subMenuName: "Time Interval",
            subMenuURL: "timeInterval",
            subMenuIcon: "fa fa-align-left",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Time Interval', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("timeInterval") ? "active-item" : "",
          },



        ]
      },
      {
        ID: 5,
        menuName: 'Docs',
        menuUrl: 'contentManagement',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('Content Management', 'menu', 'view'),
        currentTab: !!path && path.includes("contentManagement") ? "open" : "",
        subMenus: [
          {
            subMenuName: "Content Management",
            subMenuURL: "contentManagement",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Content Management', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("contentManagement") ? "active-item" : "",
          }
        ]
      },
      {
        ID: 6,
        menuName: 'Drives',
        menuUrl: 'drives',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('Drives', 'menu', 'view'),
        currentTab: !!path && path.includes("drives") || path.includes("checklist") ||
          path.includes("target") || path.includes("progress-record") || path.includes("drive-daily-progress")
          ||
          path.includes("inspection") || path.includes("stipulation") ? "open" : "",
        subMenus: [
          {
            subMenuName: "Drives",
            subMenuURL: "drives",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Drives', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("drives") ? "active-item" : "",
          },
          {
            subMenuName: "Checklist",
            subMenuURL: "checklist",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Checklist', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("checklist") ? "active-item" : "",
          },
          {
            subMenuName: "Target",
            subMenuURL: "target",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Target', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("target") ? "active-item" : "",
          },
          {
            subMenuName: "Progress Record",
            subMenuURL: "progress-record",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Progress Record', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("progress-record") ? "active-item" : "",
          },
          {
            subMenuName: "Drive Daily Progress",
            subMenuURL: "drive-daily-progress",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Drive Daily Progress', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("drive-daily-progress") ? "active-item" : "",
          },
          {
            subMenuName: "Drive Target",
            subMenuURL: "drive-target",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Drive Target', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("drive-target") ? "active-item" : "",
          },

        ]
      },
      {
        ID: 7,
        menuName: 'Inspections',
        menuUrl: 'ins',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('Inspections', 'menu', 'view'),
        currentTab: !!path && path.includes("ins") || path.includes("ins-stipulation")
        || path.includes("tw-check") || path.includes("lt-check") || path.includes("ls-wire-check") ? "open" : "",
        subMenus: [
          {
            subMenuName: "CRS EIG Inspections",
            subMenuURL: "ins",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('CRS EIG Inspections', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("ins") ? "active-item" : "",
          },
          {
            subMenuName: "Stipulations",
            subMenuURL: "ins-stipulation",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Stipulations', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("ins-stipulation") ? "active-item" : "",
          },
          {
            subMenuName: "TW Check",
            subMenuURL: "tw-check",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('TW Check', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("tw-check") ? "active-item" : "",
          },
          {
            subMenuName: "LT Check",
            subMenuURL: "lt-check",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('LT Check', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("lt-check") ? "active-item" : "",
          },
          {
            subMenuName: "LS Wire Check",
            subMenuURL: "ls-wire-check",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('LS Wire Check', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("ls-wire-check") ? "active-item" : "",
          }
        ]
      },
      {
        ID: 8,
        menuName: 'Failures',
        menuUrl: 'cb-failure',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('Failures', 'menu', 'view'),
        currentTab: !!path && path.includes("cb-failure") || path.includes("grid-failure") ||
          path.includes("rc-failure") || path.includes("unusual-occurrence") ||
          path.includes("failure-occurrence") || path.includes("actions") || path.includes("failure-analysis") ? "open" : "",
        subMenus: [
          {
            subMenuName: "CB Failure",
            subMenuURL: "cb-failure",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
           // permission: this.commonService.findPermission('CB Tripping', 'submenu', 'view'),
             permission:true,
            currentSubMenu: !!path && path.includes("cb-failure") ? "active-item" : "",
          },
          {
            subMenuName: "Failure Analysis",
            subMenuURL: "failure-analysis",
            permission: this.commonService.findPermission('Failure Analysis', 'submenu', 'view'),
             //permission:true,
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("failure-analysis") ? "active-item" : "",
          },
          {
            subMenuName: "Grid Failure",
            subMenuURL: "grid-failure",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Grid Failures', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("grid-failure") ? "active-item" : "",
          },
         
          {
            subMenuName: "RC Failures",
            subMenuURL: "rc-failure",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('RC Failures', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("rc-failure") ? "active-item" : "",
          },
          {
            subMenuName: "Failure Occurrence",
            subMenuURL: "failure-occurrence",
            permission: this.commonService.findPermission('Failure Occurrence', 'submenu', 'view'),
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("failure-occurrence") ? "active-item" : "",
          },
          {
            subMenuName: "Unusual Occurrence",
            subMenuURL: "unusual-occurrence",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Unusual Occurrence', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("unusual-occurrence") ? "active-item" : "",
          }
        ]
      },
      {
        ID: 9,
        menuName: 'MAT CONFIG',
        menuUrl: 'make',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('CONFIG', 'menu', 'view'),
        currentTab: !!path && path.includes("make") || path.includes("model") || path.includes("product") || path.includes("product-category") || path.includes("product-category-member") ? "open" : "",
        subMenus: [
          {
            subMenuName: "Make",
            subMenuURL: "make",
            subMenuIcon: "",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Make', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("make") ? "active-item" : "",
          },
          {
            subMenuName: "Model",
            subMenuURL: "model",
            subMenuIcon: "",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Model', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("model") ? "active-item" : "",
          },
          {
            subMenuName: "Product",
            subMenuURL: "product",
            subMenuIcon: "fa fa-product-hunt",
            permission:  this.commonService.findPermission('Product', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("product") ? "active-item" : "",
          },
          {
            subMenuName: "Product Category",
            subMenuURL: "product-category",
            subMenuIcon: "fa fa-product-hunt",
            permission: this.commonService.findPermission('Product Category', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("product-category") ? "active-item" : "",
          },
          {
            subMenuName: "Product Category Member",
            subMenuURL: "product-category-member",
            subMenuIcon: "fa fa-product-hunt",
            permission: this.commonService.findPermission('Product Category Member', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("product-category-member") ? "active-item" : "",
          },
        ]
      },
      {
        ID: 10,
        menuName: 'REGISTER',
        menuUrl: 'amd',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('ASSET REGISTER', 'menu', 'view'),
        currentTab: !!path && path.includes("electrification-targets") || path.includes("sidings")
          || path.includes("track") || path.includes("amd") || path.includes("work") || path.includes("guidenceItem") || path.includes("ohe-location") || path.includes("gantry")
          || path.includes("sectors") || path.includes("copy-wp-and-wpa") || path.includes("sub-sectors") || path.includes("elementary-sections")
           || path.includes("work-daily-progress")  ? "open" : "",
        subMenus: [
          {
            subMenuName: "Asset Master",
            subMenuURL: "amd",
            subMenuIcon: "fa fas fa-cloud",
            rolePermission: this.rolePermission,
            permission:this.commonService.findPermission('Asset Master', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("amd") ? "active-item" : "",
          },
          {
            subMenuName: "Electrification Targets",
            subMenuURL: "electrification-targets",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Electrification Targets', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("electrification-targets") ? "active-item" : "",
          },
          {
            subMenuName: "Guidance",
            subMenuURL: "guidenceItem",
            subMenuIcon: "",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Guidence Item', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("guidenceItem") ? "active-item" : "",
          },
          {
            subMenuName: "Sidings",
            subMenuURL: "sidings",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Sidings', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("sidings") ? "active-item" : "",
          },

          {
            subMenuName: "Track",
            subMenuURL: "track",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Track', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("track") ? "active-item" : "",
          },

          {
            subMenuName: "Work",
            subMenuURL: "work",
            subMenuIcon: "",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('work', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("work") ? "active-item" : "",
          },
          {
            subMenuName: "Ohe Location",
            subMenuURL: "ohe-location",
            subMenuIcon: "fa fa-map-marker",
            rolePermission: this.rolePermission,
            permission:  this.commonService.findPermission('OHE LOCATIONS', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("ohe-location") ? "active-item" : "",
          },
          {
            subMenuName: "Gantry",
            subMenuURL: "gantry",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('GANTRY', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("gantry") ? "active-item" : "",
          },
          
          {
            subMenuName: "Sectors",
            subMenuURL: "sectors",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('SECTOR', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("sectors") ? "active-item" : "",
          },
          {
            subMenuName: "Sub Sectors",
            subMenuURL: "sub-sectors",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('SUB SECTOR', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("sub-sectors") ? "active-item" : "",
          },
          {
            subMenuName: "Elementary Sections",
            subMenuURL: "elementary-sections",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Elementary Sections', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("elementary-sections") ? "active-item" : "",
          },
          {
            subMenuName: "projectDailyProgress",
            subMenuURL: "work-daily-progress",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('projectDailyProgress', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("work-daily-progress") ? "active-item" : "",
          },
         
        ]
      },
      {
        ID: 11,
        menuName: 'FP',
        menuUrl: 'foot-patrolling-sections',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('FP', 'menu', 'view'),
        currentTab: !!path && path.includes("foot-patrolling-sections") || path.includes("observation-categories")
          || path.includes("observation-check-list") || path.includes("foot-patrolling-inspection")
          || path.includes("Observations")
          || path.includes("Compliances") ? "open" : "",
        subMenus: [
          {
            subMenuName: "FP Sections",
            subMenuURL: "foot-patrolling-sections",
            subMenuIcon: "fa fa-train",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('FP Sections', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("foot-patrolling-sections") ? "active-item" : "",
          },
          {
            subMenuName: "FP Inspection",
            subMenuURL: "foot-patrolling-inspection",
            subMenuIcon: "fa fa-train",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('FP Inspection', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("foot-patrolling-inspection") ? "active-item" : "",
          },
          {
            subMenuName: "Obs Categories",
            subMenuURL: "observation-categories",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('Obs Categories', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("Obs Categories") ? "active-item" : "",
          },
          {
            subMenuName: "Obs Check List",
            subMenuURL: "observation-check-list",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('Obs Check List', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("Obs Check List") ? "active-item" : "",
          },
          {
            subMenuName: "Observations",
            subMenuURL: "observation-details",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('OBSERVATIONS', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("observation-details") ? "active-item" : "",
          },
          {
            subMenuName: "Compliances",
            subMenuURL: "compliance-details",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('COMPLIANCES', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("compliance-details") ? "active-item" : "",
          }



        ]
      },
      {
        ID: 12,
        menuName: 'ENERGY',
        menuUrl: 'energyMeter',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('ENERGY', 'menu', 'view'),
        currentTab: !!path && path.includes("energyMeter")
          || path.includes("tractionEneTariff") || path.includes("energyBillPayment") || path.includes("energy-consumption")
          ? "open" : "",
        subMenus: [
          {
            subMenuName: "Energy Bill Payment",
            subMenuURL: "energyBillPayment",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Energy Bill Payment', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("energyBillPayment") ? "active-item" : "",
          },
          {
            subMenuName: "Energy Consumption",
            subMenuURL: "energy-consumption",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Energy Consumption', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("energy-consumption") ? "active-item" : "",
          },
          {
            subMenuName: "Energy Meter",
            subMenuURL: "energyMeter",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('ENERGY METER', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("energyMeter") ? "active-item" : "",
          },
          {
            subMenuName: "Tariff",
            subMenuURL: "tractionEneTariff",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Traction Energy Tariff', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("tractionEneTariff") ? "active-item" : "",
          },


        ]
      },
      {
        ID: 13,
        menuName: 'TRD CONFIG',
        menuUrl: 'facility',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('TRD CONFIG', 'menu', 'view'),
        currentTab: !!path && path.includes("stations-sections") || path.includes("tpc-board-depot-assoc") || path.includes("tpc-board") || path.includes("tss-feeder")
          || path.includes("asset-schedule-assoc") || path.includes("asset-schedule-activity-assoc") || path.includes("facility") || path.includes("measure-activity") || path.includes("assets-schedule") ? "open" : "",
        subMenus: [
          {
            subMenuName: "Functional Unit",
            subMenuURL: "facility",
            subMenuIcon: "fa fa-align-left",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Functional Unit', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("facility") ? "active-item" : "",
          },
          {
            subMenuName: "Asset Schedule Activity Assocation",
            subMenuURL: "asset-schedule-activity-assoc",
            subMenuIcon: "fa fa-align-left",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('ASSET SCHEDULE ACTIVITY ASSOCIATION', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("asset-schedule-activity-assoc") ? "active-item" : "",
          },
          {
            subMenuName: "Asset Schedule Assocation",
            subMenuURL: "asset-schedule-assoc",
            subMenuIcon: "fa fa-align-left",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('ASSET SCHEDULE ASSOCIATION', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("asset-schedule-assoc") ? "active-item" : "",
          },

        
          {
            subMenuName: "Measure-Activity",
            subMenuURL: "measure-activity",
            subMenuIcon: "fa fa-align-left",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Measure activity', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("measure-activity") ? "active-item" : "",
          },
          {
            subMenuName: "Sections-stations",
            subMenuURL: "stations-sections",
            subMenuIcon: "fa fa-train",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('Stations-sections', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("stations-sections") ? "active-item" : "",
          },
          {
            subMenuName: "TPC Board Assoc",
            subMenuURL: "tpc-board-depot-assoc",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('TPC Board Assoc', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("TPC Board Assoc") ? "active-item" : "",
          },
          {
            subMenuName: "TPC Board",
            subMenuURL: "tpc-board",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            //permission: true,
            permission: this.commonService.findPermission('TPC BOARD', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("TPC Board") ? "active-item" : "",
          },
          {
            subMenuName: "TSS Feeder",
            subMenuURL: "tss-feeder",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission:this.commonService.findPermission('TSS Feeder', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("TSS Feeder") ? "active-item" : "",
          },
          {
            subMenuName: "Assets Schedule",
            subMenuURL: "assets-schedule",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Assets schedule', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("Assets Schedule") ? "active-item" : "",
          },

        ]
      },

      {
        ID: 14,
        menuName: 'SCH MAINT',
        menuUrl: 'ashd',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission:true,
       // permission: this.commonService.findPermission('ash', 'menu', 'view'),
        currentTab: !!path && path.includes("ashd") || path.includes("daily-summary") || path.includes("daily-summary-forecast")
          || path.includes("officers-movement") || path.includes("powerBlocks") 
          || path.includes("asset-status-change") || path.includes("Special-works") || path.includes("Special-works-master")
          || path.includes("asset-schedule-progress")|| path.includes("asset-monthly-targets")? "open" : "",
        subMenus: [
          {
            subMenuName: "Daily Summary",
            subMenuURL: "daily-summary",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Daily Summary', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("daily-summary") ? "active-item" : "",
          },
          {
            subMenuName: "Daily Summary Forecast",
            subMenuURL: "daily-summary-forecast",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Daily Summary Forecast', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("daily-summary-forecast") ? "active-item" : "",
          },
          {
            subMenuName: "Officers Movement",
            subMenuURL: "officers-movement",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('Officers Movement', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("officers-movement") ? "active-item" : "",
          },
          {
            subMenuName: "Power Blocks",
            subMenuURL: "powerBlocks",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
           // permission: true,
           permission: this.commonService.findPermission('POWER BLOCKS', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("powerBlocks") ? "active-item" : "",
          },
          {
            subMenuName: "TW Status Change",
            subMenuURL: "asset-status-change",
            subMenuIcon: "",
            color: "#12E1EE",
            rolePermission: this.rolePermission,
           // permission: true,
           permission: this.commonService.findPermission('ASSET STATUS CHANGE', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("asset-status-change") ? "active-item" : "",
          },
          {
            subMenuName: "Special Works Master",
            subMenuURL: "Special-works-master",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('PRECAUTIONARY MEASURES', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("Special-works-master") ? "active-item" : "",
          },
          {
            subMenuName: "Special Works",
            subMenuURL: "Special-works",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('PRECAUTIONARY MEASURES', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("Special-works") ? "active-item" : "",
          }, 
          {
            subMenuName: "Asset Schedule Progress",
            subMenuURL:  "asset-schedule-progress",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Asset Schedule Progress', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("asset-schedule-progress") ? "active-item" : "",
          },
          {
            subMenuName: "Asset Monthly Targets",
            subMenuURL:  "asset-monthly-targets",
            subMenuIcon: "fa fa-file",
            permission: true,
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("asset-monthly-targets") ? "active-item" : "",
          },
        ]
      },
      {
        ID: 15,
        menuName: 'TPC',
        menuUrl: 'Tpc Board',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('TPC', 'menu', 'view'),
        currentTab: !!path && path.includes("Tpc Board")  ? "open" : "",
        subMenus: [
          {
            subMenuName: "TPC BOARD",
            subMenuURL: "Tpc Board",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('TPC BOARD', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("Tpc Board") ? "active-item" : "",
          },
        

        ]
      },
      {
        ID: 16,
        menuName: 'PROJECT ADMIN',
        menuUrl: 'projects',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('PROJECT ADMIN', 'menu', 'view'),
        currentTab: !!path && path.includes("projects") || path.includes("phases") || path.includes("phase-activity") 
        || path.includes("groups-sections") || path.includes("population") || path.includes("targets") 
        || path.includes("STD-phases") || path.includes("STD-phase-activity")  || path.includes("work-daily-progress")
         || path.includes("user-jurisdiction") || path.includes("Ohe-location-and-assets") || path.includes("assistance")  ? "open" : "",
        subMenus: [
          {
            subMenuName: "Project",
            subMenuURL: "projects",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Projects', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("projects") ? "active-item" : "",
          },
          
          {
            subMenuName: "Phases",
            subMenuURL: "phases",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Phases', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("phases") ? "active-item" : "",
          },
          {
            subMenuName: "Phase Activity",
            subMenuURL: "phase-activity",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Phase Activity', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("phase-activity") ? "active-item" : "",
          },
          {
            subMenuName: "Groups Sections",
            subMenuURL: "groups-sections",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Groups Sections', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("groups-sections") ? "active-item" : "",
          },
          {
            subMenuName: "Population",
            subMenuURL: "population",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Population', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("population") ? "active-item" : "",
          },
          {
            subMenuName: "Targets",
            subMenuURL: "targets",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Targets', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("targets") ? "active-item" : "",
          },
          {
            subMenuName: "STD Phases",
            subMenuURL: "STD-phases",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('STD Phases', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("STD-phases") ? "active-item" : "",
          },
          {
            subMenuName: "STD Phase Activity",
            subMenuURL: "STD-phase-activity",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('STD Phase Activity', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("STD-phase-activity") ? "active-item" : "",
          },
          {
            subMenuName: "CopyWPAndWPA",
            subMenuURL: "copy-wp-and-wpa",
            subMenuIcon: "",
            rolePermission: this.rolePermission,
          // permission: true,
           permission:this.commonService.findPermission('CopyWPAndWPA', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("copy-wp-and-wpa") ? "active-item" : "",
          },
          {
            subMenuName: "OheLocation And Assets",
            subMenuURL: "Ohe-location-and-assets",
            subMenuIcon: "fa fa-file",
            rolePermission: this.rolePermission,
          // permission: true,
           permission:true,
            currentSubMenu: !!path && path.includes("Ohe-location-and-assets") ? "active-item" : "",
          },
          {
            subMenuName: "UserJurisdiction",
            subMenuURL: "user-jurisdiction",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('UserJurisdiction', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("user-jurisdiction") ? "active-item" : "",
          },
          {
            subMenuName: "Assistance",
            subMenuURL: "assistance",
            subMenuIcon: "fa fa-file",
            permission: true,
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("assistance") ? "active-item" : "",
          },

        ]
      },
      {
        ID: 17,
        menuName: 'PROJECT PROGRESS',
        menuUrl: 'progress',
        icon: "fa fa-file",
        color: "#12E1EE",
        isSelected: true,
        permission: this.commonService.findPermission('PROJECT PROGRESS', 'menu', 'view'),
        currentTab: !!path && path.includes("progress") || path.includes("guidence")  ? "open" : "",
        subMenus: [       
          {
            subMenuName: "Progress",
            subMenuURL: "progress",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Progress', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("progress") ? "active-item" : "",
          },
          {
            subMenuName: "ProjectDailyProgress",
            subMenuURL: "work-daily-progress",
            subMenuIcon: "fa fa-track",
            rolePermission: this.rolePermission,
            permission: this.commonService.findPermission('projectDailyProgress', 'submenu', 'view'),
            currentSubMenu: !!path && path.includes("work-daily-progress") ? "active-item" : "",
          },
          {
            subMenuName: "Guidence",
            subMenuURL: "guidence",
            subMenuIcon: "fa fa-file",
            permission: this.commonService.findPermission('Guidence', 'submenu', 'view'),
            rolePermission: this.rolePermission,
            currentSubMenu: !!path && path.includes("guidence") ? "active-item" : "",
          },
          
        ]
      },
    ];
    localStorage.setItem("MenusList", this.MenusList);
  }

  logout() {
    // remove user from local storage to log user out
    console.log('logout')
    localStorage.setItem("headerRefresh", "");
    localStorage.removeItem('accessToken');
    localStorage.removeItem('userMenuList');
    localStorage.removeItem('userData');
    localStorage.removeItem('loggedUser');
    localStorage.removeItem('menus');
    localStorage.removeItem('userHierarchy');
    localStorage.removeItem('zoneData');
    localStorage.removeItem('divisionData');
    localStorage.removeItem('subDivData');
    localStorage.removeItem('depotData');
    localStorage.removeItem('tpcBoard');
  }
}