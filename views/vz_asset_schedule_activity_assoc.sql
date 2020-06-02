--4
-- View: v_asset_schedule_activity_assoc
CREATE OR REPLACE VIEW v_asset_schedule_activity_assoc AS

  SELECT asa.asset_type,

     asa.schedule_code,
     asa.duration,
     asa.uom_of_duration,
     asa.is_dpr,
     asa.target_plan_months,

     asaa.activity_id,

     asaa.activity_position_id,

     mal.activity_name,

     mal.activity_type,

     asaa.make_code,

     asaa.model_code,

     asaa.lower_limit,

     asaa.upper_limit,

     asaa.description,

     asaa.seq_id,

     asaa.asa_seq_id,

     asaa.sub_asset_type,

     asaa.activity_flag,

     asaa.display_order,
     asaa.report_column_header

    FROM asset_schedule_activity_assoc asaa,

     asset_schedule_assoc asa,

     measure_or_activity_list mal

   WHERE asaa.asa_seq_id::text = asa.asa_seq_id::text AND

asaa.activity_id::text = mal.activity_id::text;

ALTER TABLE v_asset_schedule_activity_assoc

   OWNER TO postgres;