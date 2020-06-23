export const Constants = {
    YES:"YES",
    NO:'NO',
    CODES:{
        'SUCCESS':200
    },
    app_urls:{
        LOGIN:"/login",
        REGISTRATION:"/registration",
        DASHBOARD:"dashboard",
        MENU_MASTER:"menuMaster",
        SUB_MENU_URL:"submenuMaster",
        FORGOT_PASSWORD:"/forgotPassword",
        RESET_PASSWORD:"/resetPassword",
        CHANGE_PASSWORD:"/changePassword",
        MAIL_CONFIRMATION:"/mailConfirmation",
        REPORTS:{
            
        },
        JOBS_TRACKING:{

        },
        MASTERS:{

        },
        ENERGY_BILL_PAYMENTS:{

        },
        DOCS:{

        },
        DRIVE:{
            DRIVE:{
                GET_DRIVES:"/drives",
                GET_FUNCTIONAL_LOCATIONS_TYPES:"/functionalLocationsTypes",
                GET_DRIVE_ID:"/driveById/",
                SAVE_DRIVE:"/saveDrive"
            },
            DRIVE_CATEGORY:{

            },
            DRIVE_CATEGORY_ASSOCIATION:{

            }
        },
        INSPECTIONS:{

        },
        DAILY_SUMMARY:{

        },
        FAILURES:{
            
        }
    },
    EVENTS:{
        ADD:"Add",
        SAVE:"Save",
        UPDATE:"Update",
        DELETE:"Delete"
    },
    OPERATION_TYPES:{
        CREATE:"CREATE",
        UPDATE:"UPDATE",
        DELETE:"DELETE"
    },
    ACTIVE_STATE_ID :1,
    INACTIVE_STATE_ID :2,
    ROLE_PERMISSIONS:[
        {id:1, permission:"View"},
        {id:2, permission:"View/Add"},
        {id:3, permission:"View/Add/Edit"},
        {id:4, permission:"View/Edit"},
        {id:5, permission:"View/Add/Edit/Delete"},
        {id:6, permission:"No Permission"}        
    ],

    ASSERT_TYPE:{
        'RB':'',
        'ZONE':'',
        'DIV':'',
        'SUBDIV':'',
        'TRD':'',
        'OHE':'OHE_FIXED_ASSET',
        'PSI':'PSI_FIXED_ASSET',
        'SP':'PSI_FIXED_ASSET',
        'SSP':'PSI_FIXED_ASSET',
        'TSS':'PSI_FIXED_ASSET',
        'FP':'',
        'RCC':'RCC_FIXED_ASSET',
        'TPCC':'',
        'DIVOFF':'',
        'SUBDIV_OFF':'',
        'ELS':'',
        'DLS':'',
        'RDSO':''
    },
    REGULAR_EXPRESSIONS:{
        'ALPHA_NUMARIC':'[a-zA-Z0-9\s]+',
        'ONLY_ALPHA_WITH_SPACE':'^[a-zA-Z]+(\s[a-zA-Z]+)?$'
    },
    STATUS_ITEMS:{
        "GAUGE_TYPE":"GAUGE_TYPE LIST",
        "ELECTRIFICATION_EXEC_AGENCY_TYPE":"ELECTRIFICATION_EXEC_AGENCY",
        "DOUBLE_TRIPLE_TYPE":"DOUBLE_TRIPLE_ETC_ELEC",
        "YES_NO_TYPE":"YESNO_STATUS"
    }
};