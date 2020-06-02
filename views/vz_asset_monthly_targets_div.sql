
--18
-- View: v_asset_monthly_targets_div

CREATE OR REPLACE VIEW v_asset_monthly_targets_div AS 
 SELECT vamt.asset_type, vamt.schedule_type, vamt.year AS fy, 
    sum(vamt.target_jan) AS div_target_jan, 
    sum(vamt.target_feb) AS div_target_feb, 
    sum(vamt.target_mar) AS div_target_mar, 
    sum(vamt.target_apr) AS div_target_apr, 
    sum(vamt.target_may) AS div_target_may, 
    sum(vamt.target_jun) AS div_target_jun, 
    sum(vamt.target_jul) AS div_target_jul, 
    sum(vamt.target_aug) AS div_target_aug, 
    sum(vamt.target_sep) AS div_target_sep, 
    sum(vamt.target_oct) AS div_target_oct, 
    sum(vamt.target_nov) AS div_target_nov, 
    sum(vamt.target_dec) AS div_target_dec, 
    sum(vamt.cum_target_jan) AS div_cum_target_jan, 
    sum(vamt.cum_target_feb) AS div_cum_target_feb, 
    sum(vamt.cum_target_mar) AS div_cum_target_mar, 
    sum(vamt.cum_target_apr) AS div_cum_target_apr, 
    sum(vamt.cum_target_may) AS div_cum_target_may, 
    sum(vamt.cum_target_jun) AS div_cum_target_june, 
    sum(vamt.cum_target_jul) AS div_cum_target_jul, 
    sum(vamt.cum_target_aug) AS div_cum_target_aug, 
    sum(vamt.cum_target_sep) AS div_cum_target_sep, 
    sum(vamt.cum_target_oct) AS div_cum_target_oct, 
    sum(vamt.cum_target_nov) AS div_cum_target_nov, 
    sum(vamt.cum_target_dec) AS div_cum_target_dec, 
    sum(vamt.total_year_target) AS div_target_year
   FROM v_monthly_cum_targets vamt
  GROUP BY vamt.asset_type, vamt.schedule_type, vamt.year;
