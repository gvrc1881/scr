---12
-- View: v_failures_count_duration_fy_mon_wk_daily

CREATE OR REPLACE VIEW v_failures_count_duration_fy_mon_wk_daily AS 
 SELECT d.type_of_failure, d.fy, d.month1, d.week1, d.date1, 
    d.cum_delay_time_daily, w.cum_delay_time_weekly, m.cum_delay_time_monthly, 
    fy.cum_delay_time_fy, d.daily_count, w.w_count, m.m_count, fy.fy_count
   FROM ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_fy, 
            sum(v_failures_count_duration_daily.daily_count) AS fy_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.fy) fy, 
    ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            v_failures_count_duration_daily.month1, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_monthly, 
            sum(v_failures_count_duration_daily.daily_count) AS m_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.month1, v_failures_count_duration_daily.fy) m, 
    ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            v_failures_count_duration_daily.month1, 
            v_failures_count_duration_daily.week1, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_weekly, 
            sum(v_failures_count_duration_daily.daily_count) AS w_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.month1, v_failures_count_duration_daily.week1, v_failures_count_duration_daily.fy) w, 
    ( SELECT v_failures_count_duration_daily.type_of_failure, 
            v_failures_count_duration_daily.fy, 
            v_failures_count_duration_daily.month1, 
            v_failures_count_duration_daily.week1, 
            v_failures_count_duration_daily.date1, 
            sum(v_failures_count_duration_daily.delay_time) AS cum_delay_time_daily, 
            sum(v_failures_count_duration_daily.daily_count) AS daily_count
           FROM v_failures_count_duration_daily
          GROUP BY v_failures_count_duration_daily.type_of_failure, v_failures_count_duration_daily.month1, v_failures_count_duration_daily.week1, v_failures_count_duration_daily.date1, v_failures_count_duration_daily.fy) d
  WHERE d.fy = w.fy AND d.fy = m.fy AND d.fy = fy.fy AND d.type_of_failure::text = fy.type_of_failure::text AND d.month1 = m.month1 AND d.month1 = w.month1 AND d.type_of_failure::text = m.type_of_failure::text AND d.week1 = w.week1 AND d.type_of_failure::text = w.type_of_failure::text;
