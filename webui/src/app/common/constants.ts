export const Constants = {
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
    }

};