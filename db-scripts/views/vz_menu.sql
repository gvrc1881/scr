--drop view v_menu;

create view v_menu as ( select 
m.id as menu_id,
m.created_by as created_by,
u1.username as created_user,
m.created_date as created_date,
menu as menu,
m.modified_by as modified_by,
u2.username as modified_user,
m.modified_date as modified_date,
m.status_id as menu_status_id,
m.sub_menu as sub_menu

from menu m
left outer join users u1 on (m.created_by = u1.id) 
left outer join users u2 on (m.modified_by = u2.id) 

 
);