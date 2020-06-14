 -- Over Due Schedule For Map

select amd_kilometer , amd_asset_id,  amd_facility_id,  amd_section ,  No_of_assets_at_loc ,   all_assets_sch_data_at_loc ,all_asset_types_at_loc

, longitude ,latitude , altitude , ohe_mast , loc.section , pwi , overdue_str

FROM

(   -- -- end of AMD and thier ASH data details GROUP by KM, AI, F, SEC, OVERDUE_STR

               select amd_kilometer, amd_asset_id, --asset_at_location , no_of_assets , amd_asset_type ,

                amd_facility_id,  amd_section , count(*) No_of_assets_at_loc , string_agg(amd_asset_type , ',') all_asset_types_at_loc,

               string_agg( asset_recent_sch_data||'('||no_of_sch||')' , ' & ') all_assets_sch_data_at_loc , overdue_str

               from

               (

                              select amd_kilometer,--no_of_assets,

                              amd_asset_id, --asset_at_location , no_of_assets ,

                              amd_asset_type ,

                              amd_facility_id,  amd_section  , amd_asset_type ||'('||no_of_sch||')'|| asset_recent_sch_data asset_recent_sch_data ,no_of_sch , overdue_str

                              from

                              (

 

                                             select count(*), asset_ID amd_asset_id  , facility_id amd_facility_id , section amd_section , kilometer amd_kilometer  , asset_type  amd_asset_type

                                              from asset_master_data where asset_type in

                                             (

                                             select product_id from product_category_member  where product_category_id  ='OHE_FIXED_ASSET'

                                             )

                                             group by asset_id ,facility_id  , section   , kilometer  , asset_type

                                             --having count(*) > 1

                              ) amd

                              left outer join

                              -- For all recent ASH details

                              ( -- start of concatenate rec & next sch dates for each sch of an asset ,  overdue flag, no of assets at a location

                              select asset_id , asset_type , facility_id,  count(*) no_of_sch, string_agg( schedule_code ||'/'|| recent_schedule_date ||'/'||

                              case when to_char(next_Schedule_due_date, 'DD-MON-YYYY') is null then ' check Data for due date' else to_char(next_Schedule_due_date, 'DD-MON-YYYY')  end ,

                                ' # ') asset_recent_sch_data , string_agg(overdue_flag,',') overdue_str , count(*) no_of_assets_at_loc -- section ,

                              FROM

                                             ( -- start for Next Schedule date after recent schedule of assets with thier recent schedule done of each sch type

                                             select asset_id , asset_type , facility_id,  schedule_code ,  recent_schedule_date , -- section,

                                             case when recent_schedule_date is not null  then -- (date_of_commision + INTERVAL '1 month'  * month_duration )::date

                                                                                          (recent_schedule_date + INTERVAL '1 month' * month_duration)::date end next_Schedule_due_date,

                                             case when recent_schedule_date is not null and (recent_schedule_date + INTERVAL '1 month' * month_duration)::date is not null

                                             and (recent_schedule_date + INTERVAL '1 month' * month_duration)::date < now() then  'Yes' else 'NA' end as overdue_flag,                                                                                                            

                                             uom_of_duration , duration

                                                            from

                                                            (  -- start for list of assets with thier recent schedule done of each sch type -- GROUP by AID, AT, ST , F, UOM , Duration

                                                            select asset_id , ash.asset_type , facility_id, ash.schedule_code , max(schedule_date)::date recent_schedule_date , uom_of_duration , duration, --section,

                                                            case when uom_of_duration ='Time in Years'  then duration::integer*12

                                                            when uom_of_duration ='Time in Months'  then duration::integer*1  end as month_duration

                                                                                           -- schedule_code

                                                            from assets_schedule_history ash, asset_schedule_assoc asa

                                                            where ash.asset_type in

                                                                           (

                                                                           select product_id from product_category_member  where product_category_id  ='OHE_FIXED_ASSET'

                                                                           )

                                                            and ash.asset_type = asa.asset_type

                                                            and  ash.schedule_code =  asa.schedule_code

                                                            group by asset_id , ash.asset_type , facility_id,  ash.schedule_code , uom_of_duration , duration , -- section,

                                                                           case when uom_of_duration ='Time in Years'  then duration::integer*12

                                                                                          when uom_of_duration ='Time in Months'  then duration::integer*1

                                                                           end  -- schedule_code

                                                              -- End for list of assets with thier recent schedule done of each sch type GROUP by AID, AT, ST , F, UOM , Duration

                                                            ) a

                                                            -- END for Next Schedule date after recent schedule of assets with thier recent schedule done of each sch type

                                                                           --where asset_id = '241/21-25'

                                                                           --order by 1,2,3

                                             ) b

                                             group by asset_id , asset_type , facility_id --, section

                                --- END of of concatenate rec & next sch dates for each sch of an asset ,  overdue flag, no of assets at a location GROUP by AID, AT, F

                              ) LASH on ( LASH.asset_id = amd_asset_id and LASH.asset_type =  amd_asset_type and LASH.facility_id = amd_facility_id) -- and LASH.section = amd_section )

                             

               ) Al

               group by  amd_kilometer, amd_asset_id,   amd_facility_id,  amd_section ,overdue_str

               -- end of AMD and thier ASH data details GROUP by KM, AI, F, SEC, OVERDUE_STR

) asc_da

Left outer join

(

 select  longitude ,latitude , altitude , ohe_mast ,section , pwi  from ohe_location

) loc  on (ohe_mast = amd_asset_id)

