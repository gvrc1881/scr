
--drop view v_scheduler_jobs_tracking ;

create view v_scheduler_jobs_tracking as (
select
sjt.tracking_id as tracking_id,
sjt.end_time as end_time,
sjt.job_status as job_status,
sjt.job_type_id as job_type_id,
sjt.process_status as process_status,
sjt.processed_date as processed_date,
sjt.run_by as run_by,
sjt.run_type as run_type,
sjt.start_time as start_time,
sjt.job_id as job_id,
sjt.repository_id as repository_id,
rep.repository_code ,
rep.repository_name,
sjt.time_interval_id as time_interval_id,
ti.time_interval , 
ti.is_active timeinterval_is_active
from scheduler_jobs_tracking  sjt 
--left outer join jobtypes jt on ( sjt.job_type_id = jt.job_type_id ) 
left outer join timeintervals ti on ( sjt.time_interval_id  = ti.time_interval_id) 
left outer join repository rep  on ( sjt.repository_id  = rep.repository_id ) 
--left outer join job_status js  on ( sjt.job_status_id  = js.job_status_id  ) 

, v_schedulerjobs vsj  , timeintervals ti , repository rep -- ,  job_status js-- jobtypes jt, 
where  sjt.time_interval_id  = ti.time_interval_id
and sjt.repository_id  = rep.repository_id
and sjt.job_id = vsj.job_id

)