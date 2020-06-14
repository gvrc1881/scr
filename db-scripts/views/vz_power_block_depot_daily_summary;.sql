--23

--view: v_power_block_depot_daily_summary;

-- get details of PB issued_or not_issued  for count of PB and issued duration in min daywise, depot, status (active/deleted), req dept (   ) wise , week/month/Qtr/year/FY etc, 
-- This  is used for next aggregation at depot level or other status for any time period of week/month/Qtr/year/FY etc, 
CREATE OR REPLACE VIEW v_power_block_depot_daily_summary AS
  SELECT v_power_block_switch_details.req_department, 
    v_power_block_switch_details.facility_name, 
    v_power_block_switch_details.depot_type, 
    v_power_block_switch_details.current_status, count(*) AS cnt, 
    sum(v_power_block_switch_details.req_period::integer) AS total_min_asked, 
    sum(v_power_block_switch_details.effective_pb_duration) AS pb_issued_min, 
    v_power_block_switch_details.issued_not_issued, 
    to_char(v_power_block_switch_details.pb_date, 'yyyy-mm-dd'::text) AS date1, 
    date_part('year'::text, v_power_block_switch_details.pb_date) AS year1, 
    date_part('month'::text, v_power_block_switch_details.pb_date) AS month1, 
    date_part('week'::text, v_power_block_switch_details.pb_date) AS week1, 
    v_power_block_switch_details.line, 
        CASE
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 1::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 2::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (v_power_block_switch_details.pb_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, v_power_block_switch_details.pb_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 4::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 5::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 6::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 7::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 8::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 9::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 10::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 11::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, v_power_block_switch_details.pb_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (v_power_block_switch_details.pb_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END AS fy
   FROM v_power_block_switch_details
  GROUP BY v_power_block_switch_details.req_department, v_power_block_switch_details.depot_type, v_power_block_switch_details.line, v_power_block_switch_details.current_status, v_power_block_switch_details.facility_name, v_power_block_switch_details.issued_not_issued, v_power_block_switch_details.pb_date, 
        CASE
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 1::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 2::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 3::double precision THEN (btrim(to_char(date_part('year'::text, (v_power_block_switch_details.pb_date - '1 year'::interval)::date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, v_power_block_switch_details.pb_date)::integer, 100), '99'::text))
            WHEN date_part('month'::text, v_power_block_switch_details.pb_date) = 4::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 5::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 6::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 7::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 8::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 9::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 10::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 11::double precision OR date_part('month'::text, v_power_block_switch_details.pb_date) = 12::double precision THEN (btrim(to_char(date_part('year'::text, v_power_block_switch_details.pb_date), '9999'::text)) || '-'::text) || btrim(to_char(mod(date_part('year'::text, (v_power_block_switch_details.pb_date + '1 year'::interval)::date)::integer, 100), '99'::text))
            ELSE NULL::text
        END, date_part('year'::text, v_power_block_switch_details.pb_date), date_part('month'::text, v_power_block_switch_details.pb_date), date_part('week'::text, v_power_block_switch_details.pb_date), to_char(v_power_block_switch_details.pb_date, 'yyyy-mm-dd'::text);
