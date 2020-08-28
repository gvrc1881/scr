create view v_job_status as (
select
js.job_status_id as job_status_id,
js.created_date as created_date,
js.is_active as is_active,
js.job_status_name as job_status_name,
js.modified_date as modified_date,
js.created_by as created_by,
u1.username as created_user,
js.modified_by as modified_by,
u2.username as modified_user
from job_status  js
left outer join users u1 on (js.created_by = u1.id) 
left outer join users u2 on (js.modified_by = u2.id) 

) ;

--  select * from job_status 
--  select * from v_job_status 
