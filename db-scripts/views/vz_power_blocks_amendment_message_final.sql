--25
-- View: v_power_blocks_amendment_message_final


CREATE OR REPLACE VIEW v_power_blocks_amendment_message_final AS 
 SELECT pb.pb_operation_seq_id, pb.created_date, fsw.field_operated_switch_list, 
    pb.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, 
    pb.type_of_operation, pb.shadow_block, pb.elementary_section_code, grant_period,
       pb.section, pb.line, pb.line2, 
        CASE
            WHEN pb.line IS NULL OR pb.line2 IS NULL THEN 'UP/DN/UD/UP&DN'::text
            ELSE (pb.line::text || '/'::text) || pb.line2::text
        END AS pb_section_lines, 
    pb.req_department, pb.req_period, pb.reqn_by, 
        CASE
            WHEN pba.ptw_availed_from_date_time IS NULL THEN pb.ptw_availed_from_date_time
            ELSE pba.ptw_availed_from_date_time
        END AS ptw_availed_from_date_time, 
        CASE
            WHEN pba.ptw_availed_from_date_time IS NULL THEN 
                    case when pb.ptw_availed_from_date_time is null then pb.ptw_availed_from_date_time
                    else pb.ptw_availed_from_date_time+(grant_period::integer || ' Minutes'::text)::interval
                     end 
            ELSE 
                    case when  pba.ptw_availed_from_date_time is not null then  
                     pba.ptw_availed_from_date_time+(grant_period::integer || ' Minutes'::text)::interval 
                    else  pba.ptw_availed_from_date_time end 
            END AS ptw_availed_till_date_time, 
        CASE
            WHEN pba.tpc_no_ptw_issue IS NULL THEN pb.tpc_no_ptw_issue::text
            ELSE pba.tpc_no_ptw_issue::text || '*'::text
        END AS tpc_no_ptw_issue, 
        CASE
            WHEN pba.field_no_ptw_issue IS NULL THEN pb.field_no_ptw_issue::text
            ELSE pba.field_no_ptw_issue::text || '*'::text
        END AS field_no_ptw_issue, 
        CASE
            WHEN pba.ptw_availed_thru_date_time IS NULL THEN pb.ptw_availed_thru_date_time
            ELSE pba.ptw_availed_thru_date_time
        END AS ptw_availed_thru_date_time, 
        CASE
            WHEN pba.tpc_no_ptw_return IS NULL THEN pb.tpc_no_ptw_return::text
            ELSE pba.tpc_no_ptw_return::text || '*'::text
        END AS tpc_no_ptw_return, 
        CASE
            WHEN pba.field_no_ptw_return IS NULL THEN pb.field_no_ptw_return::text
            ELSE pba.field_no_ptw_return::text || '*'::text
        END AS field_no_ptw_return, 
    pb.purpose, pb.pb_requested_from_date_time, pb.pb_requested_thru_date_time, 
    pb.pb_granted_from_date_time, pb.pb_granted_thru_date_time, 
    pb.staff_to_work, pb.post, pb.switching_station, pb.switching_equipment, 
    pb.equipment_to_work, pb.special_remarks, pb.remarks, pb.tpc_board, 
    pb.schedule, pb.supervisor_incharge, pb.current_status, pb.created_on, 
    pb.created_by, pb.last_updated_stamp, pb.last_updated_tx_stamp, 
    pb.created_stamp, pb.created_tx_stamp, 
    pba.pb_operation_seq_id AS pba_operation_seq_id, pba.pb_amendment_seq_id, 
    pba.created_by AS pba_created_by, pba.updated_by AS pba_updated_by, 
    pba.delete AS pba_delete, pba.last_updated_stamp AS pba_last_updated_stamp, 
    pba.last_updated_tx_stamp AS pba_last_updated_tx_stamp, 
    pba.created_stamp AS pba_created_stamp, 
    pba.created_tx_stamp AS pba_created_tx_stamp, 
        CASE
            WHEN pba.ptw_availed_thru_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS ptw_availed_thru_date_time_amended, 
        CASE
            WHEN pba.ptw_availed_from_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS ptw_availed_from_date_time_amended
   FROM power_blocks pb
   LEFT JOIN power_blocks_amendment pba ON (pba.pb_operation_seq_id::text = pb.pb_operation_seq_id::text and pba.delete ='false' )
   LEFT JOIN ( SELECT pb_1.pb_operation_seq_id, pb_1.ptw_availed_thru_date_time, 
       pb_1.ptw_availed_from_date_time, sw.field_operated_switch_list
      FROM power_blocks pb_1
   LEFT JOIN ( SELECT switch_maintenence_history.pb_operation_seq_id, 
               switch_maintenence_history.is_field_operated, 
               string_agg(switch_maintenence_history.io_location::text, ', '::text) AS field_operated_switch_list
              FROM switch_maintenence_history
             WHERE switch_maintenence_history.is_field_operated::text = 'true'::text OR switch_maintenence_history.is_field_operated::text = 'TRUE'::text
             GROUP BY switch_maintenence_history.pb_operation_seq_id, switch_maintenence_history.is_field_operated) sw ON pb_1.pb_operation_seq_id::text = sw.pb_operation_seq_id::text) fsw ON fsw.pb_operation_seq_id::text = pb.pb_operation_seq_id::text, 
    facility fac
  WHERE pb.facility_id::text = fac.facility_id::text;
  