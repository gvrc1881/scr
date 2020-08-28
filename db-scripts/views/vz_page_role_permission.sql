-- select * from page_role_permission 

--drop view v_page_role_permission ;

create view v_page_role_permission as (
select 
prp.id prp_id,
prp.page ,
prp.status prp_status ,
prp.master_role_id , mr.role_name mr_role_name , mr.status_id mr_status_id ,

prp.menu_id , m.menu m_menu , m.sub_menu m_sub_menu, m.status_id m_status_id, 
prp.permission_id , p.permission p_permission , p.status_id P_status_id,

prp.created_by as created_by,
--u1.username as created_user,
prp.modified_by as modified_by
--u2.username as modified_user

from page_role_permission prp 
--left outer join users u1 on (prp.created_by = u1.id ) 
--left outer join users u2 on (prp.modified_by = u2.id) 
left outer join  master_roles mr on (prp.master_role_id = mr.id)
left outer join  menu m on ( prp.menu_id = m.id )
left outer join  permissions p on ( prp.permission_id  = p.id )
) ;
