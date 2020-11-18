
----1.

create view v_work_group as
select w.id w_id , w.work_name , wg.id wg_id  ,agency , code, description, wg.division , doubling_trippling,
wg.rkm, wg.section, siding_yard_station, wg.tkm, work_group, zone  
from works w, work_group wg
where w.id = wg.work_id 

---2.
create view v_work_phases as
select w.id w_id ,   wp.id wp_id  , commence_date , completion_date , dependency_to_start , description , 
phase_name , planned_start_date , 
sequence , status  , target_completion_date , weightage 
from works w, work_phases wp 
where w.id = wp.work_id 

---3.
create view v_work_group_phases as
select w.id w_id ,   wp.id wp_id  , wg.id wg_id ,
w.work_name ,  agency , code,  doubling_trippling,
wg.rkm, wg.section, siding_yard_station, wg.tkm, work_group, 
commence_date , completion_date , dependency_to_start , wp.description , phase_name , planned_start_date , 
sequence , status  , target_completion_date , weightage 
from works w, work_phases wp , work_group wg
where w.id = wp.work_id 
and  w.id = wg.work_id

---4

--drop view v_work_group_phase_activity_population ;
create view v_work_group_phase_activity_population as

select wpasp.id wpasp_id , population , uom ,
w.work_name , work_group,  wg.section, agency , code,   siding_yard_station, 
 phase_name ,  wpa.name wpa_name , wp.sequence wp_sequence, 
work_group_id ,
w.id w_id ,   wp.id wp_id  , wg.id wg_id ,

wg.rkm, wg.tkm,  doubling_trippling, 
 wp.id wpa_id, 
asset_type , wpa.dependency_to_start wpa_dependency_to_start , depot_type , wpa.description wpa_description ,
is_check_list , is_object_id_required ,
 wpa.sequence  wpa_sequence, work_phase_id , 
commence_date , completion_date , wp.dependency_to_start , wp.description , planned_start_date , 
wp.status  , target_completion_date , weightage 

from  works w, work_phases wp , work_group wg , work_phase_activity wpa , wpa_section_population wpasp
where wpasp.work_group_id = wg.id
and wpasp.work_phase_activity_id = wpa.id
and wpa.work_phase_id = wp.id 
and wg.work_id = w.id 


-----5

--drop view v_wpa_daily_progress ;
create view v_wpa_daily_progress as

select date , performed_count , 
work_name , work_group ,  uom , wg.section , agency , phase_name , wpa.name wpa_name , 
wp.sequence wp_sequence , wpa.sequence wpa_sequence
from wpa_daily_progress wpadp , works w, work_phases wp , work_group wg , work_phase_activity wpa 
where wpadp.work_group_id = wg.id
and wpadp.work_phase_activity_id = wpa.id
and wpa.work_phase_id = wp.id 
and wg.work_id = w.id



