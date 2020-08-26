--drop view v_jobs_history ;

create view v_jobs_history as (
select
jh.id as id,
jh.end_time as end_time,
jh.failed_tables_count as failed_tables_count,
jh.job_status as job_status,
jh.operation_id as operation_id,
jh.opration_type as opration_type,
jh.processed_date as processed_date,
jh.start_time as start_time,
jh.status as status,
jh.success_tables_count as success_tables_count,
jh.total_tables_count as total_tables_count
from jobs_history  jh 
);

---  select * from jobs_history ; 
---  select * from v_jobs_history ; 


 -------------