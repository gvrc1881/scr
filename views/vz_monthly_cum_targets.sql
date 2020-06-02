
---13
-- View: v_monthly_cum_targets

CREATE OR REPLACE VIEW v_monthly_cum_targets AS 
 SELECT asmt.seq_id, asmt.facility_id, asmt.facility_name, 
    asmt.facility_type_id, asmt.depot_type, asmt.schedule_type, asmt.asset_type, 
    asmt.target_jan, asmt.target_feb, asmt.target_mar, asmt.target_apr, 
    asmt.target_may, asmt.target_jun, asmt.target_jul, asmt.target_aug, 
    asmt.target_sep, asmt.target_oct, asmt.target_nov, asmt.target_dec, 
    asmt.target_apr AS cum_target_apr, 
    asmt.target_apr + asmt.target_may AS cum_target_may, 
    asmt.target_apr + asmt.target_may + asmt.target_jun AS cum_target_jun, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul AS cum_target_jul, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug AS cum_target_aug, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep AS cum_target_sep, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct AS cum_target_oct, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov AS cum_target_nov, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec AS cum_target_dec, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan AS cum_target_jan, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb AS cum_target_feb, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb + asmt.target_mar AS cum_target_mar, 
    asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb + asmt.target_mar AS total_year_target, 
    asmt.year, asmt.fy
   FROM v_asset_monthly_targets asmt;
