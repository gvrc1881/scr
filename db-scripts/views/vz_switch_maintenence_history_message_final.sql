--24
-- View: v_switch_maintenence_history_message_final


CREATE OR REPLACE VIEW v_switch_maintenence_history_message_final AS 
 SELECT sw.seq_id, pb.created_date, pb.tpc_board, pb.facility_id, 
    fac.facility_name, fac.facility_type_id, fac.depot_type, 
    pb.type_of_operation, pb.section, pb.current_status, sw.pb_operation_seq_id, 
    sw.io_location, sw.io_type, sw.io_opened_by, sw.open_time_lapse, 
    sw.open_done_time_lapse, sw.close_time_lapse, sw.close_done_time_lapse, 
        CASE
            WHEN swa.io_opened_date_time IS NULL THEN 
            CASE
                WHEN sw.open_time_lapse IS NULL THEN sw.io_opened_date_time
                ELSE sw.io_opened_date_time + ((sw.open_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_opened_date_time
        END AS swf_io_opened_date_time, 
        CASE
            WHEN swa.tpc_no_io_open IS NULL THEN sw.tpc_no_io_open::text
            ELSE swa.tpc_no_io_open::text || '*'::text
        END AS swf_tpc_no_io_open, 
        CASE
            WHEN swa.field_no_io_open IS NULL THEN sw.field_no_io_open::text
            ELSE swa.field_no_io_open::text || '*'::text
        END AS swf_field_no_io_open, 
        CASE
            WHEN swa.io_opened_date_time_done IS NULL THEN 
            CASE
                WHEN sw.open_done_time_lapse IS NULL THEN sw.io_opened_date_time_done
                ELSE sw.io_opened_date_time_done + ((sw.open_done_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_opened_date_time_done
        END AS swf_io_opened_date_time_done, 
        CASE
            WHEN swa.tpc_no_io_open_done IS NULL THEN sw.tpc_no_io_open_done::text
            ELSE swa.tpc_no_io_open_done::text || '*'::text
        END AS swf_tpc_no_io_open_done, 
        CASE
            WHEN swa.field_no_io_open_done IS NULL THEN sw.field_no_io_open_done::text
            ELSE swa.field_no_io_open_done::text || '*'::text
        END AS swf_field_no_io_open_done, 
        CASE
            WHEN swa.io_closed_date_time IS NULL THEN 
            CASE
                WHEN sw.close_time_lapse IS NULL THEN sw.io_closed_date_time
                ELSE sw.io_closed_date_time + ((sw.close_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_closed_date_time
        END AS swf_io_closed_date_time, 
        CASE
            WHEN swa.tpc_no_io_close IS NULL THEN sw.tpc_no_io_close::text
            ELSE swa.tpc_no_io_close::text || '*'::text
        END AS swf_tpc_no_io_close, 
        CASE
            WHEN swa.field_no_io_close IS NULL THEN sw.field_no_io_close::text
            ELSE swa.field_no_io_close::text || '*'::text
        END AS swf_field_no_io_close, 
        CASE
            WHEN swa.io_closed_date_time_done IS NULL THEN 
            CASE
                WHEN sw.close_done_time_lapse IS NULL THEN sw.io_closed_date_time_done
                ELSE sw.io_closed_date_time_done + ((sw.close_done_time_lapse::text || ' Minutes'::text)::interval)
            END
            ELSE swa.io_closed_date_time_done
        END AS swf_io_closed_date_time_done, 
        CASE
            WHEN swa.tpc_no_io_close_done IS NULL THEN sw.tpc_no_io_close_done::text
            ELSE swa.tpc_no_io_close_done::text || '*'::text
        END AS swf_tpc_no_io_close_done, 
        CASE
            WHEN swa.field_no_io_close_done IS NULL THEN sw.field_no_io_close_done::text
            ELSE swa.field_no_io_close_done::text || '*'::text
        END AS swf_field_no_io_close_done, 
    sw.io_closed_by, sw.is_field_operated, 
        CASE
            WHEN (date_part('day'::text, sw.io_closed_date_time_done - sw.io_opened_date_time_done) * 24::double precision * 60::double precision + date_part('hour'::text, sw.io_closed_date_time_done - sw.io_opened_date_time_done) * 60::double precision + date_part('minute'::text, sw.io_closed_date_time_done - sw.io_opened_date_time_done)) > 0::double precision THEN 'ISSUED'::text
            ELSE 'NOT_ISSUED'::text
        END AS issued_not_issued, 
    sw.last_updated_stamp AS sw_last_updated_stamp, 
    sw.last_updated_tx_stamp AS sw_last_updated_tx_stamp, 
    sw.created_stamp AS sw_created_stamp, 
    sw.created_tx_stamp AS sw_created_tx_stamp, swa.amendment_seq_id, 
    swa.io_opened_by AS swa_io_opened_by, swa.io_closed_by AS swa_io_closed_by, 
    swa.last_updated_stamp AS swa_last_updated_stamp, 
    swa.last_updated_tx_stamp AS swa_last_updated_tx_stamp, 
    swa.created_stamp AS swa_created_stamp, 
    swa.created_tx_stamp AS swa_created_tx_stamp, 
        CASE
            WHEN swa.io_opened_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_opened_date_time_amended, 
        CASE
            WHEN swa.io_opened_date_time_done IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_opened_date_time_done_amended, 
        CASE
            WHEN swa.io_closed_date_time IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_closed_date_time_amended, 
        CASE
            WHEN swa.io_closed_date_time_done IS NOT NULL THEN 'true'::text
            ELSE 'false'::text
        END AS io_closed_date_time_done_amended
   FROM switch_maintenence_history sw
   LEFT JOIN switch_maintenence_history_amendment swa ON (sw.seq_id::text = swa.seq_id::text  and swa.delete ='false'), 
    power_blocks pb, facility fac
  WHERE pb.facility_id::text = fac.facility_id::text AND pb.pb_operation_seq_id::text = sw.pb_operation_seq_id::text;
