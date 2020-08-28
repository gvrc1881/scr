--drop view v_submenu;

create view v_submenu as (
select 
sm.id as submenu_id,
sm.created_by as created_by,
u1.username as created_user,
sm.created_date as created_date,
sm.menu_id as menu_id,
sm.modified_by as modified_by,
u2.username as modified_user,
sm.modified_date as modified_date,
sm.orders as orders,
sm.status_id as status_id,
sm.sub_menu as sub_menu,
sm.sub_menu_icon as sub_menu_icon,
sm.sub_menu_url as sub_menu_url
from submenu sm
left outer join users u1 on (sm.created_by = u1.id) 
left outer join users u2 on (sm.modified_by = u2.id) 
) ;

 --  select * from submenu;
--  select * from v_submenu;
