create view v_user_roles as
select ur.user_id, username, role_name, ur.master_role_id 
from user_roles ur
left outer join master_roles mr on ( mr.id = ur.master_role_id)
left outer join users u on ( u.id = ur.user_id)
;

