/**
 * 
 */
package com.scr.util;

/**
 * @author vt1056
 *
 */
public class Constants {
	
	public static final String NEW_LINE ="\n";
	public static final String TAB = "\t";
	public static final String BACK_SLASH = "//";
	public static final String UNDER_SCORE = "_";
	public static final String DOT = ".";
	public static final String SPLIT_DOT = "\\.";
	
	public static final String ALL = "All";
	public static final int ACTIVE_STATUS_ID = 1;
	public static final int UNACTIVE_STATUS_ID = 2;
	public static final Integer SUCCESS_CODE = 200;
	public static final Integer FAILURE_CODE = 500;
	
	public static final String JOB_SUCCESS_MESSAGE = "SUCCESS";
	public static final String JOB_FAILED_MESSAGE = "FAILED";
	
	public static final String JOB_PROCESSING_STATUS = "PROCESSING";
	public static final String JOB_COMPLETED_STATUS = "COMPLETED";
	
	public static final String RUN_SCHEDULER_TYPE = "SCHEDULER";
	public static final String RUN_RERUN_TYPE = "RERUN";
	public static final String RUN_RELOAD_TYPE = "RELOAD";	
	
	public static final Integer PROCESS_COMPLETED = ACTIVE_STATUS_ID;
	public static final Integer PROCESS_PENDING = UNACTIVE_STATUS_ID;
	public static final Integer PROCESS_FAILED = 3;
	public static final Integer PROCESS_NOT_STARTED = 4;
	
	public static final int SCHEDULED_JOBS_FREQUENCY_DAILY = 1;
	public static final int SCHEDULED_JOBS_FREQUENCY_WEEKLY = 2;
	public static final int SCHEDULED_JOBS_FREQUENCY_FORTNIGHTLY = 3;
	public static final int SCHEDULED_JOBS_FREQUENCY_MONTHLY = 4;	
	public static final int SCHEDULED_JOBS_FREQUENCY_QUARTERLY  = 5;
	public static final int SCHEDULED_JOBS_FREQUENCY_HALF_YEARLY = 6;
	public static final int SCHEDULED_JOBS_FREQUENCY_YEARLY = 7;
	public static final int SCHEDULED_JOBS_FREQUENCY_RELOAD = 10;
	
	public static final String SCHEDULED_JOBS_DAILY = "DAILY";
	public static final String SCHEDULED_JOBS_WEEKLY = "WEEKLY";
	public static final String SCHEDULED_JOBS_FORTNIGHTLY = "FORTNIGHTLY";
	public static final String SCHEDULED_JOBS_MONTHLY = "MONTHLY";	
	public static final String SCHEDULED_JOBS_QUARTERLY = "QUARTERLY";
	public static final String SCHEDULED_JOBS_HALF_YEARLY = "HALF YEARLY";
	public static final String SCHEDULED_JOBS_YEARLY = "YEARLY";
	
	
	public static final String SCHEDULED_JOBS_OPERATION_INSERT = "CREATE";
	public static final String SCHEDULED_JOBS_OPERATION_UPDATE = "UPDATE";
	public static final String SCHEDULED_JOBS_OPERATION_DELETE = "DELETE";
	
	
	public static final String SCHEDULER_DIVISION_TO_TEMP_JOB = "DIVISIONTOSTAGING";
	public static final String SCHEDULER_TEMP_TO_ZONAL_JOB = "STAGINGTOZONAL";
	public static final String SCHEDULER_ALL_JOB = "ALL";
	
	public static final Integer SCHEDULER_DIVISION_TO_TEMP_JOB_ID = 1;
	public static final Integer SCHEDULER_TEMP_TO_ZONAL_JOB_ID = 2;
	public static final Integer SCHEDULER_ALL_JOB_ID = 3;
	
	
	public static final String RESET_PASSWORD_SUBJECT = "Please Reset Your Password";
	public static final String RERUN_SCHEDULER_SUBJECT = "Rerun Scheduler Process";
	public static final String SCHEDULER_DB_CONNECTION_FAILED_SUBJECT = "Scheduler Database Connection Failed";
}
