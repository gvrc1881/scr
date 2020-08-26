
--drop view v_role_permission ;

create view v_role_permission as (
select 
rp.id as role_permission_id,
rp.created_by as created_by,
u1.username as created_user,
rp.created_date as created_date,
rp.modified_by as modified_by,
u2.username as modified_user,
rp.modified_date as modified_date,
rp.permission_id as permission_id,
p.permission as permission_code,
p.status_id as permission_status ,
rp.role_type_id as role_type_id,
rt.role_name as role_name ,
rt.role_type as role_type,
rt.status_id as role_type_status_id,
rp.status_id as status_id 
from role_permission rp 
left outer join users u1 on (rp.created_by = u1.id) 
left outer join users u2 on (rp.modified_by = u2.id) 
left outer join  permissions p on (rp.permission_id = p.id)
left outer join   roletype rt on (rp.role_type_id =rt.id )
) ;
