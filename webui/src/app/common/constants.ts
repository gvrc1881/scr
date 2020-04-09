export const Constants = {
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
    ]

    
};