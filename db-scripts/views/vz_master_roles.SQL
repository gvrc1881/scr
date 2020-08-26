
--drop view v_master_roles;

create view v_master_roles as (
select 
mr.id as master_roles_id,
mr.created_by as created_by,
u1.username as created_user,
mr.created_date as created_date,
mr.modified_by as modified_by,
u2.username as modified_user,
mr.modified_date as modified_date,
mr.role_name as role_name,
mr.status_id as status_id
from master_roles mr
left outer join users u1 on (mr.created_by = u1.id) 
left outer join users u2 on (mr.modified_by = u2.id) 
);
