---drop views --

drop view v_RU_subdiv_depot_wise_QOH;
drop view v_RC_subdiv_depot_wise_QOH;
drop view v_HX_subdiv_depot_wise_QOH;
drop view v_GTL_subdiv_depot_wise_QOH;
drop view v_bza_subdiv_depot_wise_qoh;
drop view v_GNT_subdiv_depot_wise_QOH;
drop view v_MRGA_Subdivision_wise_QOH;
drop view v_MRK_Subdivision_wise_QOH;
drop view v_HYB_subdiv_depot_wise_QOH;
drop view v_NLR_bpp_depot_wise_QOH;
drop view v_RJY_bpp_depot_wise_QOH;
drop view v_TUNI_bpp_depot_wise_QOH;
drop view v_BPP_subdiv_depot_wise_QOH;
drop view v_DKJ_subdiv_depot_wise_QOH;
drop view v_KZJ_subdiv_depot_wise_QOH;
drop view v_SKZR_subdiv_depot_wise_QOH;
drop view v_tdu_subdiv_depot_wise_qoh;		
drop view v_sc_subdiv_depot_wise_QOH;
drop view v_HYB_div_Subdivision_wise_QOH;
drop view v_GTL_div_Subdivision_wise_QOH;
drop  view v_SC_div_Subdivision_wise_QOH;
drop view v_GNT_div_Subdivision_wise_QOH;
drop view v_BZA_div_Subdivision_wise_QOH;
drop view v_hyb_depot_wise_QOH;
drop view v_gtl_depot_wise_QOH;
DROP VIEW public.v_GNT_depot_wise_QOH;		
DROP VIEW public.v_sc_depot_wise_QOH;
DROP VIEW public.v_bza_depot_wise_QOH;
DROP VIEW public.v_scr_division_wise_qoh;
drop view v_depot_all_category_item_qoh;

--create views-- 

--30


create view v_RU_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GTL' and upper(subdiv) ='RU';

--29

create view v_RC_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GTL' and upper(subdiv) ='RC';

--28

create view v_HX_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  
and upper(div) ='GTL' and upper(subdiv) ='HX';


--27

create view v_GTL_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  
and upper(div) ='GTL' and upper(subdiv) ='GTL';

--26


create view v_bza_subdiv_depot_wise_qoh as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  
and upper(div) ='BZA' and upper(subdiv) ='BZA';

--25

create view v_GNT_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GNT' and upper(subdiv) ='GNT';


--24

create view v_MRGA_Subdivision_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GNT' and upper(subdiv) ='MRGA';

--23

create view v_MRK_Subdivision_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GNT' and upper(subdiv) ='MRK';

--22

create view v_HYB_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='HYB' and upper(subdiv) ='HYB';

--21

create view v_NLR_bpp_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='BZA' and upper(subdiv) ='NLR';

--20

create view v_RJY_bpp_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='BZA' and upper(subdiv) ='RJY';

--19

create view v_TUNI_bpp_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='BZA' and upper(subdiv) ='TUNI';

--18

create view v_BPP_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='BZA' and upper(subdiv) ='BPP';
--17

create view v_DKJ_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where  product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' and
and upper(div) ='SC' and upper(subdiv) ='DKJ';

--16

create view v_KZJ_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where  product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' and
and upper(div) ='SC' and upper(subdiv) ='KZJ';

--15

create view v_SKZR_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where  product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' and
and upper(div) ='SC' and upper(subdiv) ='SKZR';

--14

CREATE OR REPLACE VIEW public.v_tdu_subdiv_depot_wise_qoh AS 
 SELECT v_depot_all_category_item_qoh.qoh,
    v_depot_all_category_item_qoh.depot_name,
    v_depot_all_category_item_qoh.qty_depot_name,
    v_depot_all_category_item_qoh.product_category_id,
    v_depot_all_category_item_qoh.product_id,
    v_depot_all_category_item_qoh.p_description,
    v_depot_all_category_item_qoh.abbreviation,
    v_depot_all_category_item_qoh.u_description,
    v_depot_all_category_item_qoh.quantity_uom_id,
    v_depot_all_category_item_qoh.facility_id,
    v_depot_all_category_item_qoh.subdiv,
    v_depot_all_category_item_qoh.div,
    v_depot_all_category_item_qoh.zone,
    'QoH By Depot'::text AS header,
    ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
        CASE
            WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
            ELSE v_depot_all_category_item_qoh.p_description
        END::text) || ' ) '::text AS material_id_desc
   FROM v_depot_all_category_item_qoh
  WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.div::text) = 'SC'::text AND upper(v_depot_all_category_item_qoh.subdiv::text) = 'TDU'::text;

ALTER TABLE public.v_tdu_subdiv_depot_wise_qoh
  OWNER TO postgres;

--13

create view v_sc_subdiv_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where 
product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and
upper(div) ='SC' and upper(subdiv) ='SC';

--12

create view v_HYB_div_Subdivision_wise_QOH as
select  product_category_id , product_id , p_description , abbreviation , u_description , quantity_uom_id , div , zone ,
sum(case when QOH is null then 0 else qoh end) as div_QOH , material_id_desc,subdiv
from
(
select Qoh,  depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , 
quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
 
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(zone) ='SCR' -- parameter division 
and  upper(div) ='HYB'
) sd
group by  product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id  ,  subdiv,
div , zone, material_id_desc;

--11

create view v_GTL_div_Subdivision_wise_QOH as
select  product_category_id , product_id , p_description , abbreviation , u_description , quantity_uom_id , div , zone ,
sum(case when QOH is null then 0 else qoh end) as div_QOH , material_id_desc,subdiv
from
(
select Qoh,  depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , 
quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
 
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(zone) ='SCR' -- parameter division 
and  upper(div) ='GTL'
) sd
group by  product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id  ,  subdiv,
div , zone, material_id_desc;

--10

create view v_SC_div_Subdivision_wise_QOH as
select  product_category_id , product_id , p_description , abbreviation , u_description , quantity_uom_id , div , zone ,
sum(case when QOH is null then 0 else qoh end) as div_QOH , material_id_desc,subdiv
from
(
select Qoh,  depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , 
quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
 
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(zone) ='SCR' -- parameter division 
and  upper(div) ='SC'
) sd
group by  product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id  ,  subdiv,
div , zone, material_id_desc;


--9

create view v_GNT_div_Subdivision_wise_QOH as
select  product_category_id , product_id , p_description , abbreviation , u_description , quantity_uom_id , div , zone ,
sum(case when QOH is null then 0 else qoh end) as div_QOH , material_id_desc,subdiv
from
(
select Qoh,  depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , 
quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
 
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(zone) ='SCR' -- parameter division 
and  upper(div) ='GNT'
) sd
group by  product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id  ,  subdiv,
div , zone, material_id_desc;

--8

create view v_BZA_div_Subdivision_wise_QOH as
select  product_category_id , product_id , p_description , abbreviation , u_description , quantity_uom_id , div , zone ,
sum(case when QOH is null then 0 else qoh end) as div_QOH , material_id_desc,subdiv
from
(
select Qoh,  depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , 
quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
 
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(zone) ='SCR' -- parameter division 
and  upper(div) ='BZA'
) sd
group by  product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id  ,  subdiv,
div , zone, material_id_desc;

--7

create view v_hyb_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='HYB';

--6

create view v_gtl_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GTL';

--5

create view v_GNT_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='GNT';


--4

create view v_sc_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='SC';

--3

create view v_bza_depot_wise_QOH as
select  Qoh, depot_name , qty_depot_name , product_category_id ,   product_id , p_description , abbreviation , u_description , quantity_uom_id , FACILITY_ID ,   subdiv ,  div ,   zone ,
    'QoH By Depot' as header,
   product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from v_depot_all_category_item_qoh
where product_category_id = 'RLYID_CATEGORY'  -- parameter 'CONSUMABLE_CATEGORY' -- 
and upper(div) ='BZA';

--2

CREATE OR REPLACE VIEW public.v_scr_division_wise_qoh
 AS
 SELECT sd.product_category_id,
    sd.product_id,
    sd.p_description,
    sd.abbreviation,
    sd.u_description,
    sd.quantity_uom_id,
    sd.div,
    sd.zone,
    sum(
        CASE
            WHEN sd.qoh IS NULL THEN 0::numeric
            ELSE sd.qoh
        END) AS div_qoh,
    sd.material_id_desc
   FROM ( SELECT v_depot_all_category_item_qoh.qoh,
            v_depot_all_category_item_qoh.depot_name,
            v_depot_all_category_item_qoh.qty_depot_name,
            v_depot_all_category_item_qoh.product_category_id,
            v_depot_all_category_item_qoh.product_id,
            v_depot_all_category_item_qoh.p_description,
            v_depot_all_category_item_qoh.abbreviation,
            v_depot_all_category_item_qoh.u_description,
            v_depot_all_category_item_qoh.quantity_uom_id,
            v_depot_all_category_item_qoh.facility_id,
            v_depot_all_category_item_qoh.subdiv,
            v_depot_all_category_item_qoh.div,
            v_depot_all_category_item_qoh.zone,
            'QoH By Depot'::text AS header,
            ((v_depot_all_category_item_qoh.product_id::text || ' ( '::text) ||
                CASE
                    WHEN v_depot_all_category_item_qoh.p_description IS NULL THEN '--'::character varying
                    ELSE v_depot_all_category_item_qoh.p_description
                END::text) || ' ) '::text AS material_id_desc
           FROM v_depot_all_category_item_qoh
          WHERE v_depot_all_category_item_qoh.product_category_id::text = 'RLYID_CATEGORY'::text AND upper(v_depot_all_category_item_qoh.zone::text) = 'SCR'::text) sd
  GROUP BY sd.product_category_id, sd.product_id, sd.p_description, sd.abbreviation, sd.u_description, sd.quantity_uom_id, sd.div, sd.zone, sd.material_id_desc;

ALTER TABLE public.v_scr_division_wise_qoh
    OWNER TO postgres;
--1
 
CREATE OR REPLACE VIEW public.v_depot_all_category_item_qoh  as
select d_c_p.depot_name ,q.facility_name qty_depot_name , product_category_id ,  d_c_p.product_id , p_description , abbreviation , u_description , quantity_uom_id ,  
d_c_p.FACILITY_ID ,  d_c_p.subdiv , d_c_p.div ,  d_c_p.zone ,
case when QOH is null then 0 else qoh end QOH ,
     'QoH By Depot' as header,
  d_c_p.product_id||' ( '|| case when p_description is null then '--' else p_description end || ' ) ' as material_id_desc 
from
 (
 -- get all combinations of depot, category, product 
select depot_name , product_category_id ,  pcm.product_id , p_description , abbreviation , u_description , quantity_uom_id ,  FACILITY_ID ,  subdiv , div ,  zone
from 
(
 select distinct zone, division div , sub_division subdiv , facility_name depot_name , FACILITY_ID
 from facility f  where --(Upper(f.division)) = 'SC'  and 
 facility_type_id='WAREHOUSE'
 and (facility_name like '%OHE'  or facility_name like '%PSI' or facility_name like '%TRD' or facility_name like '%RCC%')

) f,
(
 select  product_category_id ,  pcm.product_id , p_description , abbreviation , u_description , quantity_uom_id
 from product_category_member pcm,
  (
  select  product_id,    
    case when quantity_uom_id is null then ' '  else quantity_uom_id end as quantity_uom_id,
    case when u.abbreviation is null then ' '  else u.abbreviation end as abbreviation,
      case when p.description is null then ' '  else p.description end as p_description ,
      case when u.description is null then ' '  else u.description end as u_description 
    from product p left outer join uom u on (p.quantity_uom_id = u.uom_id)
  ) p_u
 where p_u.product_id = pcm.product_id
) pcm

) d_c_p
left outer join
(
 SELECT  II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, 
 ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH,
 F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div
 FROM
 INVENTORY_ITEM II , PRODUCT P, FACILITY F
 WHERE P.PRODUCT_ID = II.PRODUCT_ID
 AND F.FACILITY_ID = II.FACILITY_ID
 --and p.product_id in (select  product_id from product_category_member --where product_category_id ='RLYID_CATEGORY')
 GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME, f.division ,  f.data_div , f.zone, f.sub_division
) q
on ( d_c_p.product_id = q.product_id and q.FACILITY_NAME = depot_name and q.facility_id = d_c_p.FACILITY_ID )




