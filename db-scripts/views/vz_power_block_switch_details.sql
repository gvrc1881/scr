--22
-- View: v_power_block_switch_details

CREATE OR REPLACE VIEW v_power_block_switch_details AS 
 SELECT pb.pb_operation_seq_id, pb.created_date AS pb_date, pb.req_department, 
    pb.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type, 
    pb.type_of_operation, pb.elementary_section_code AS es_sec_subsec_code,  shadow_block,
    pb.section AS pb_over_es_sec_subsec, pb.line, pb.line2,  
    pb.pb_requested_from_date_time, pb.pb_requested_thru_date_time, 
    pb.pb_granted_from_date_time, pb.pb_granted_thru_date_time, 
    pb.ptw_availed_from_date_time, pb.ptw_availed_thru_date_time, 
    pb.tpc_no_ptw_issue, pb.field_no_ptw_issue, pb.tpc_no_ptw_return, 
    pb.field_no_ptw_return, pb.req_period, pb.reqn_by,      pb.post, 
    pb.switching_station, pb.switching_equipment, pb.equipment_to_work, 
    pb.special_remarks, pb.remarks, pb.supervisor_incharge, pb.current_status, tpc_board , schedule, purpose , staff_to_work ,
    smh.seq_id, smh.pb_operation_seq_id AS smh_pb_operation_seq_id, 
    smh.io_location AS smh_io_location, smh.io_type AS smh_io_type, 
    smh.io_opened_by AS smh_io_opened_by, 
    smh.io_opened_date_time AS smh_io_opened_date_time, 
    smh.tpc_no_io_open AS smh_tpc_no_io_open, 
    smh.field_no_io_open AS smh_field_no_io_open, 
    smh.io_opened_date_time_done AS smh_io_opened_date_time_done, 
    smh.tpc_no_io_open_done AS smh_tpc_no_io_open_done, 
    smh.field_no_io_open_done AS smh_field_no_io_open_done, 
    smh.io_closed_by AS smh_io_closed_by, 
    smh.io_closed_date_time AS smh_io_closed_date_time, 
    smh.tpc_no_io_close AS smh_tpc_no_io_close, 
    smh.field_no_io_close AS smh_field_no_io_close, 
    smh.io_closed_date_time_done AS smh_io_closed_date_time_done, 
    smh.tpc_no_io_close_done AS smh_tpc_no_io_close_done, 
    smh.field_no_io_close_done AS smh_field_no_io_close_done, 
    smh.is_field_operated, 
    date_part('day'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 24::double precision * 60::double precision + date_part('hour'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 60::double precision + date_part('minute'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) AS effective_pb_duration, 
        CASE
            WHEN (date_part('day'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 24::double precision * 60::double precision + date_part('hour'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done) * 60::double precision + date_part('minute'::text, smh.io_closed_date_time_done - smh.io_opened_date_time_done)) > 0::double precision THEN 'ISSUED'::text
            ELSE 'NOT_ISSUED'::text
        END AS issued_not_issued
   FROM power_blocks pb, facility fac, switch_maintenence_history smh
  WHERE pb.facility_id::text = fac.facility_id::text AND pb.pb_operation_seq_id::text = smh.pb_operation_seq_id::text;
