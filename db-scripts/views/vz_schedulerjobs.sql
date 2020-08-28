
-- drop view v_schedulerjobs ;

create view v_schedulerjobs as (
select
sj.job_id as job_id,
sj.created_date as created_date,
sj.is_active as schedulerjob_is_active,
sj.last_run_timestamp as last_run_timestamp,
sj.modified_date as modified_date,
sj.created_by as created_by,
u1.username as created_user,
sj.job_status_id as job_status_id,
js.job_status_name ,
sj.job_type_id as job_type_id,
jt.job_type_name ,
jt.is_active ,
sj.modified_by as modified_by,
u2.username as modified_user,
sj.repository_id as repository_id,
rep.repository_code ,
rep.repository_name,
rep.is_active as repository_is_active ,
sj.time_interval_id as time_interval_id ,
ti.time_interval , 
ti.is_active timeinterval_is_active
from schedulerjobs  sj
left outer join users u1 on (sj.created_by = u1.id) 
left outer join users u2 on (sj.modified_by = u2.id) 
left outer join jobtypes jt on ( sj.job_type_id = jt.job_type_id ) 
left outer join timeintervals ti on ( sj.time_interval_id  = ti.time_interval_id) 
left outer join repository rep  on ( sj.repository_id  = rep.repository_id ) 
left outer join job_status js  on ( sj.job_status_id  = js.job_status_id  ) 

);

--  select * from schedulerjobs ; 
--  select * from v_schedulerjobs ; 


-------------