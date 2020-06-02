-- Table: gnt.allocation

-- DROP TABLE gnt.allocation;

CREATE TABLE gnt.allocation_upd
(
  allocation_id character varying(20),
  designations character varying(20),
  unit_code character varying(20),
  unit_type character varying(20),
  allocation_code character varying(20) NOT NULL,
  allocation_name character varying(60),
  from_date timestamp with time zone,
  thru_date timestamp with time zone,
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  material_type character varying(20),
  CONSTRAINT pk_allocation_upd PRIMARY KEY (allocation_code)
);


-- Table: gnt.uom_type

-- DROP TABLE gnt.uom_type;

CREATE TABLE gnt.uom_type_upd
(
  uom_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_uom_type_upd PRIMARY KEY (uom_type_id)
);


-- Table: gnt.inventory_item_type

-- DROP TABLE gnt.inventory_item_type;

CREATE TABLE gnt.inventory_item_type_upd
(
  inventory_item_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_inventory_item_type_upd PRIMARY KEY (inventory_item_type_id)
);


-- Table: gnt.uom

-- DROP TABLE gnt.uom;

CREATE TABLE gnt.uom_upd
(
  uom_id character varying(20) NOT NULL,
  uom_type_id character varying(20),
  abbreviation character varying(60),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  rly_uom_id character varying(20),
  CONSTRAINT pk_uom_upd PRIMARY KEY (uom_id)
);
-- Table: gnt.uom_conversion

-- DROP TABLE gnt.uom_conversion;

CREATE TABLE gnt.uom_conversion_upd
(
  uom_id character varying(20) NOT NULL,
  uom_id_to character varying(20) NOT NULL,
  conversion_factor double precision,
  custom_method_id character varying(20),
  decimal_scale numeric(20,0),
  rounding_mode character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_uom_conversion_upd PRIMARY KEY (uom_id, uom_id_to)
);

-- Table: gnt.facility_type

-- DROP TABLE gnt.facility_type;

CREATE TABLE gnt.facility_type_upd
(
  facility_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_facility_type_upd PRIMARY KEY (facility_type_id),
  CONSTRAINT facility_typepar FOREIGN KEY (parent_type_id)
      REFERENCES gnt.facility_type_upd (facility_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



-- Table: gnt.facility

-- DROP TABLE gnt.facility;

CREATE TABLE gnt.facility_upd
(
  facility_id character varying(20) NOT NULL,
  facility_type_id character varying(20),
  parent_facility_id character varying(20),
--  owner_party_id character varying(20),
  default_inventory_item_type_id character varying(20),
  facility_name character varying(100),
  primary_facility_group_id character varying(20),
--  square_footage numeric(20,0),
--  product_store_id character varying(20),
--  default_days_to_ship numeric(20,0),
  opened_date timestamp with time zone,
  closed_date timestamp with time zone,
  description character varying(255),
  default_weight_uom_id character varying(20),
 -- geo_point_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  reserve_order_enum_id character varying(20),
--  facility_size numeric(18,6),
--  facility_size_uom_id character varying(20),
--  default_dimension_uom_id character varying(20),
 -- skip_pack_inv_check character(1),
 -- manuf_alloc_enable character varying(20),
 -- organized character varying(20),
  is_disable character(1),
  remarks character varying(255),
  depot_type character varying(20),
  parent_depot character varying(100),
  division character varying(100),
  sub_division character varying(100),
  CONSTRAINT pk_facility_upd PRIMARY KEY (facility_id),
  CONSTRAINT fac_def_uom_upd FOREIGN KEY (default_weight_uom_id)
      REFERENCES gnt.uom_upd (uom_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fac_invitm_type_upd FOREIGN KEY (default_inventory_item_type_id)
      REFERENCES gnt.inventory_item_type_upd (inventory_item_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT facility_fctyp_upd FOREIGN KEY (facility_type_id)
      REFERENCES gnt.facility_type_upd (facility_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
 -- CONSTRAINT facility_geopt FOREIGN KEY (geo_point_id)
 --     REFERENCES gnt.geo_point (geo_point_id) MATCH SIMPLE
      --ON UPDATE NO ACTION ON DELETE NO ACTION,
 -- CONSTRAINT facility_owner FOREIGN KEY (owner_party_id)
 --     REFERENCES gnt.party (party_id) MATCH SIMPLE
 --     ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT facility_parent_upd FOREIGN KEY (parent_facility_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
--  CONSTRAINT facility_pgrp FOREIGN KEY (primary_facility_group_id)
  --    REFERENCES gnt.facility_group (facility_group_id) MATCH SIMPLE
    --  ON UPDATE NO ACTION ON DELETE NO ACTION,
 -- CONSTRAINT fclt_rordenum FOREIGN KEY (reserve_order_enum_id)
  --    REFERENCES gnt.enumeration (enum_id) MATCH SIMPLE
    --  ON UPDATE NO ACTION ON DELETE NO ACTION
);

--2
-- Table: gnt.app_device

-- DROP TABLE gnt.app_device;

CREATE TABLE gnt.app_device_upd
(
  seq_id character varying(20) NOT NULL,
  app_name character varying(100),
  device_id character varying(20),
  registration_id character varying(20),
  security_code character varying(20),
  active_status character varying(20),
  from_date timestamp with time zone,
  thru_date timestamp with time zone,
  created_by character varying(60),
  remarks character varying(255),
  deactivate_remarks character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  device_name character varying(100),
  CONSTRAINT pk_app_device_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.party

-- DROP TABLE gnt.party;

CREATE TABLE gnt.party_upd
(
  party_id character varying(20) NOT NULL,
  party_type_id character varying(20),
  external_id character varying(20),
  preferred_currency_uom_id character varying(20),
  description text,
  status_id character varying(20),
  created_date timestamp with time zone,
  created_by_user_login character varying(255),
  last_modified_date timestamp with time zone,
  last_modified_by_user_login character varying(255),
  data_source_id character varying(20),
  is_unread character(1),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_party_upd PRIMARY KEY (party_id)
);

-- Table: gnt.user_login

-- DROP TABLE gnt.user_login;

CREATE TABLE gnt.user_login_upd
(
  user_login_id character varying(255) NOT NULL,
  current_password character varying(60),
  password_hint character varying(255),
  is_system character(1),
  enabled character(1),
  has_logged_out character(1),
  require_password_change character(1),
  last_currency_uom character varying(20),
  last_locale character varying(10),
  last_time_zone character varying(60),
  disabled_date_time timestamp with time zone,
  successive_failed_logins numeric(20,0),
  user_ldap_dn character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  party_id character varying(20),
  external_auth_id character varying(255),
  CONSTRAINT pk_user_login_upd PRIMARY KEY (user_login_id)
);

-- Table: gnt.user_login_history

-- DROP TABLE gnt.user_login_history;

CREATE TABLE gnt.user_login_history_upd
(
  user_login_id character varying(255) NOT NULL,
  visit_id character varying(20),
  from_date timestamp with time zone NOT NULL,
  thru_date timestamp with time zone,
  password_used character varying(60),
  successful_login character(1),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  party_id character varying(20),
  CONSTRAINT pk_user_login_history_upd PRIMARY KEY (user_login_id, from_date)
);

--3
-- Table: gnt.app_device_login

-- DROP TABLE gnt.app_device_login;

CREATE TABLE gnt.app_device_login_upd
(
  seq_id character varying(20) NOT NULL,
  app_name character varying(100),
  device_id character varying(20),
  user_login_id character varying(255),
  active_status character varying(20),
  from_date timestamp with time zone,
  thru_date timestamp with time zone,
  created_by character varying(60),
  remarks character varying(255),
  deactivate_remarks character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_app_device_login_upd PRIMARY KEY (seq_id),
  CONSTRAINT app_device_usrlog FOREIGN KEY (user_login_id)
      REFERENCES gnt.user_login_upd (user_login_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--4
-- Table: gnt.app_device_unit

-- DROP TABLE gnt.app_device_unit;

CREATE TABLE gnt.app_device_unit_upd
(
  seq_id character varying(20) NOT NULL,
  app_device_seq_id character varying(20),
  unit_type character varying(20),
  unit_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_app_device_unit_upd PRIMARY KEY (seq_id),
  CONSTRAINT app_device_unit_seqid FOREIGN KEY (app_device_seq_id)
      REFERENCES gnt.app_device_upd (seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT app_device_unit_unitid FOREIGN KEY (unit_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--5
-- Table: gnt.asset_make_model_assoc

-- DROP TABLE gnt.asset_make_model_assoc;

CREATE TABLE gnt.asset_make_model_assoc_upd
(
  seq_id character varying(20),
  asset_type character varying(20) NOT NULL,
  model_code character varying(20),
  make_code character varying(20) NOT NULL,
  description character varying(255),
  created_on timestamp with time zone,
  created_by character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_asset_make_model_assoc_upd PRIMARY KEY (asset_type, make_code)
);

-- Table: gnt.work_effort_type

-- DROP TABLE gnt.work_effort_type;

CREATE TABLE gnt.work_effort_type_upd
(
  work_effort_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_work_effort_type_upd PRIMARY KEY (work_effort_type_id)
);


-- Table: gnt.telecom_number

-- DROP TABLE gnt.telecom_number;

CREATE TABLE gnt.telecom_number_upd
(
  contact_mech_id character varying(20) NOT NULL,
  country_code character varying(10),
  area_code character varying(10),
  contact_number character varying(60),
  ask_for_name character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_telecom_number_upd PRIMARY KEY (contact_mech_id)
);

-- Table: gnt.structure_type

-- 

CREATE TABLE gnt.structure_type_upd
(
  seq_id character varying(20) NOT NULL,
  code character varying(100),
  structure_group character varying(100),
  description character varying(255),
  specification character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_structure_type_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.status_type

-- DROP TABLE gnt.status_type;

CREATE TABLE gnt.status_type_upd
(
  status_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_status_type_upd PRIMARY KEY (status_type_id)
);


-- Table: gnt.status_item

-- DROP TABLE gnt.status_item;

CREATE TABLE gnt.status_item_upd
(
  status_id character varying(20) NOT NULL,
  status_type_id character varying(20),
  status_code character varying(60),
  sequence_id character varying(20),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_status_item_upd PRIMARY KEY (status_id)
);

-- Table: gnt.status_valid_change

-- DROP TABLE gnt.status_valid_change;

CREATE TABLE gnt.status_valid_change_upd
(
  status_id character varying(20) NOT NULL,
  status_id_to character varying(20) NOT NULL,
  condition_expression character varying(255),
  transition_name character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_status_valid_change_upd PRIMARY KEY (status_id, status_id_to)
);

-- Table: gnt.role_type

-- DROP TABLE gnt.role_type;

CREATE TABLE gnt.role_type_upd
(
  role_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_role_type_upd PRIMARY KEY (role_type_id)
);

-- Table: gnt.requirement_type

-- DROP TABLE gnt.requirement_type;

CREATE TABLE gnt.requirement_type_upd
(
  requirement_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_requirement_type_upd PRIMARY KEY (requirement_type_id)
) ;



-- Table: gnt.psi_report_registry

-- DROP TABLE gnt.psi_report_registry;

CREATE TABLE gnt.psi_report_registry_upd
(
  report_id character varying(20) NOT NULL,
  asset_type character varying(60),
  schedule_type character varying(60),
  description text,
  parent_report_id text,
  make character varying(60),
  model character varying(60),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  report_header text,
  report_code character varying(100),
  depot_type character varying(60),
  CONSTRAINT pk_psi_report_registry_upd PRIMARY KEY (report_id)
);

-- Table: gnt.report_group

-- DROP TABLE gnt.report_group;

CREATE TABLE gnt.report_group_upd
(
  report_group_id character varying(20) NOT NULL,
  description character varying(255),
  application character varying(100),
  show_in_select character(1),
  sequence_num numeric(20,0),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_report_group_upd PRIMARY KEY (report_group_id)
);

-- Table: gnt.report_registry

-- DROP TABLE gnt.report_registry;

CREATE TABLE gnt.report_registry_upd
(
  report_id character varying(20) NOT NULL,
  short_name character varying(60),
  description text,
  report_location character varying(255),
  setup_uri character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  report_header text,
  report_code character varying(100),
  CONSTRAINT pk_report_registry_upd PRIMARY KEY (report_id)
);

-- Table: gnt.report_group_member

-- DROP TABLE gnt.report_group_member;

CREATE TABLE gnt.report_group_member_upd
(
  report_group_id character varying(20) NOT NULL,
  report_id character varying(20) NOT NULL,
  sequence_num numeric(20,0),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_report_group_member_upd PRIMARY KEY (report_group_id, report_id)
);



-- Table: gnt.product_type

-- DROP TABLE gnt.product_type;

CREATE TABLE gnt.product_type_upd
(
  product_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  is_physical character(1),
  is_digital character(1),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_product_type_upd PRIMARY KEY (product_type_id)
);



-- Table: gnt.product

-- DROP TABLE gnt.product;

CREATE TABLE gnt.product_upd
(
  product_id character varying(20) NOT NULL,
  product_type_id character varying(20),
  primary_product_category_id character varying(20),
--  manufacturer_party_id character varying(20),
  facility_id character varying(20),
/*  introduction_date timestamp with time zone,
  support_discontinuation_date timestamp with time zone,
  sales_discontinuation_date timestamp with time zone,
  sales_disc_when_not_avail character(1),
  internal_name character varying(255),
  brand_name character varying(100),
 */
  comments character varying(255),
  product_name character varying(100),
  description character varying(255),
  long_description text,
  /*
  price_detail_text character varying(255),
  small_image_url character varying(255),
  medium_image_url character varying(255),
  large_image_url character varying(255),
  detail_image_url character varying(255),
  original_image_url character varying(255),
  detail_screen character varying(255),
  inventory_message character varying(255),
  require_inventory character(1),
*/
  quantity_uom_id character varying(20),
  quantity_included numeric(18,6),
  /*
  --pieces_included numeric(20,0),
  --require_amount character(1),
  fixed_amount numeric(18,2),
  amount_uom_type_id character varying(20),
 */
  weight_uom_id character varying(20),
  weight numeric(18,6),
  height_uom_id character varying(20),
  product_height numeric(18,6),
  --shipping_height numeric(18,6),
  width_uom_id character varying(20),
  product_width numeric(18,6),
  --shipping_width numeric(18,6),
  depth_uom_id character varying(20),
  product_depth numeric(18,6),
  /*--shipping_depth numeric(18,6),
  product_rating numeric(18,6),
  rating_type_enum character varying(20),
  returnable character(1),
  taxable character(1),
  charge_shipping character(1),
  auto_update_keywords character(1),
  include_in_promotions character(1),
  is_virtual character(1),
  is_variant character(1),
  virtual_variant_method_enum character varying(20),
  origin_geo_id character varying(20),
  requirement_method_enum_id character varying(20),
  bill_of_material_level numeric(20,0),
  reserv_max_persons numeric(18,6),
  reserv2nd_p_p_perc numeric(18,6),
  reserv_nth_p_p_perc numeric(18,6),
  config_id character varying(20),
  */
  created_date timestamp with time zone,
  created_by_user_login character varying(255),
  last_modified_date timestamp with time zone,
  last_modified_by_user_login character varying(255),
 -- in_shipping_box character(1),
 -- default_shipment_box_type_id character varying(20),
 -- product_make_details character varying(255),
  material_classification character(1),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  is_active character(1),
--  release_date timestamp with time zone,
  product_weight numeric(18,6),
  diameter_uom_id character varying(20),
  product_diameter numeric(18,6),
  is_serialized character varying(20),
  product_code_type_id character varying(20),
  pl_no character varying(20),
  rly_id character varying(20),
  trd_div_id character varying(20),
  CONSTRAINT pk_product_upd PRIMARY KEY (product_id)
);

-- Table: gnt.product_category_type

-- DROP TABLE gnt.product_category_type;

CREATE TABLE gnt.product_category_type_upd
(
  product_category_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_product_category_type_upd PRIMARY KEY (product_category_type_id)
);


-- Table: gnt.product_category

-- DROP TABLE gnt.product_category;

CREATE TABLE gnt.product_category_upd
(
  product_category_id character varying(20) NOT NULL,
  product_category_type_id character varying(20),
  primary_parent_category_id character varying(20),
  category_name character varying(100),
  description character varying(255),
  long_description text,
/*
  category_image_url character varying(255),
  link_one_image_url character varying(255),
  link_two_image_url character varying(255),
  detail_screen character varying(255),
  show_in_select character(1),
*/
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_product_category_upd PRIMARY KEY (product_category_id)
);

-- Table: gnt.product_category_member

-- DROP TABLE gnt.product_category_member;

CREATE TABLE gnt.product_category_member_upd
(
  product_category_id character varying(20) NOT NULL,
  product_id character varying(20) NOT NULL,
  from_date timestamp with time zone NOT NULL,
  thru_date timestamp with time zone,
  comments character varying(255),
  sequence_num numeric(20,0),
  quantity numeric(18,6),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_product_category_member_upd PRIMARY KEY (product_category_id, product_id, from_date)
);


-- Table: gnt.priority_type

-- DROP TABLE gnt.priority_type;

CREATE TABLE gnt.priority_type_upd
(
  priority_type_id character varying(20) NOT NULL,
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_priority_type_upd PRIMARY KEY (priority_type_id)
);


-- Table: gnt.period_type

-- DROP TABLE gnt.period_type;

CREATE TABLE gnt.period_type_upd
(
  period_type_id character varying(20) NOT NULL,
  description character varying(255),
  period_length numeric(20,0),
  uom_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_period_type_upd PRIMARY KEY (period_type_id)
);

-- Table: gnt.party_status

-- DROP TABLE gnt.party_status;

CREATE TABLE gnt.party_status_upd
(
  status_id character varying(20) NOT NULL,
  party_id character varying(20) NOT NULL,
  status_date timestamp with time zone NOT NULL,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_party_status_upd PRIMARY KEY (status_id, party_id, status_date)
);


-- Table: gnt.party_role

-- DROP TABLE gnt.party_role;

CREATE TABLE gnt.party_role_upd
(
  party_id character varying(20) NOT NULL,
  role_type_id character varying(20) NOT NULL,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_party_role_upd PRIMARY KEY (party_id, role_type_id)
);


-- Table: gnt.model

-- DROP TABLE gnt.model;

CREATE TABLE gnt.model_upd
(
  seq_id character varying(20),
  model_code character varying(20) NOT NULL,
  model_type character varying(20),
  model_name character varying(100),
  brand_name character varying(100),
  description character varying(255),
  created_on timestamp with time zone,
  created_by character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_model_upd PRIMARY KEY (model_code)
);

-- Table: gnt.measure_or_activity_list

-- DROP TABLE gnt.measure_or_activity_list;

CREATE TABLE gnt.measure_or_activity_list_upd
(
  seq_id character varying(20),
  activity_id character varying(20) NOT NULL,
  activity_name character varying(255),
  activity_type character varying(20),
  unit_of_measure character varying(20),
  description character varying(255),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_measure_or_activity_list_upd PRIMARY KEY (activity_id)
);


-- Table: gnt.ohe_location

-- DROP TABLE gnt.ohe_location;

CREATE TABLE gnt.ohe_location_upd
(
  seq_id character varying(20) NOT NULL,
  division character varying(20),
  section character varying(20),
  pwi character varying(100),
  ohe_mast character varying(100),
  eng_feature character varying(100),
  ohe_feature character varying(100),
  remark_one character varying(100),
  remark_two character varying(100),
  latitude character varying(100),
  longitude character varying(100),
  altitude character varying(100),
  validity character varying(100),
  satellites character varying(100),
  speed character varying(100),
  heading character varying(100),
  ohe_sequence character varying(100),
  curvature character varying(100),
  curvature_remark character varying(255),
  span character varying(100),
  span_remark character varying(255),
  date timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  chainage character varying(100),
  chainage_remark character varying(255),
  structure_type character varying(100),
  track_line character varying(100),
  kilometer double precision,
  sequence_no character varying(100),
  facility_id character varying(20),
  major_section character varying(20),
  adee_section character varying(20),
  CONSTRAINT pk_ohe_location_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.party_contact_mech

-- DROP TABLE gnt.party_contact_mech;

CREATE TABLE gnt.party_contact_mech_upd
(
  party_id character varying(20) NOT NULL,
  contact_mech_id character varying(20) NOT NULL,
  from_date timestamp with time zone NOT NULL,
  thru_date timestamp with time zone,
  role_type_id character varying(20),
  allow_solicitation character(1),
  extension character varying(255),
  verified character(1),
  comments character varying(255),
  years_with_contact_mech numeric(20,0),
  months_with_contact_mech numeric(20,0),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_party_contact_mech_upd PRIMARY KEY (party_id, contact_mech_id, from_date)
);


-- Table: gnt.party_contact_mech_purpose

-- DROP TABLE gnt.party_contact_mech_purpose;

CREATE TABLE gnt.party_contact_mech_purpose_upd
(
  party_id character varying(20) NOT NULL,
  contact_mech_id character varying(20) NOT NULL,
  contact_mech_purpose_type_id character varying(20) NOT NULL,
  from_date timestamp with time zone NOT NULL,
  thru_date timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_party_contact_mech_purpose_upd PRIMARY KEY (party_id, contact_mech_id, contact_mech_purpose_type_id, from_date)
);

-- Table: gnt.party_group

-- DROP TABLE gnt.party_group;

CREATE TABLE gnt.party_group_upd
(
  party_id character varying(20) NOT NULL,
  group_name character varying(100),
  group_name_local character varying(100),
  office_site_name character varying(100),
  annual_revenue numeric(18,2),
  num_employees numeric(20,0),
  ticker_symbol character varying(10),
  comments character varying(255),
  logo_image_url character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  is_incorporated character(1),
  federal_tax_id character varying(255),
  requires1099 character(1),
  vat_tin character varying(20),
  cst_no character varying(20),
  service_tax_no character varying(20),
  pan character varying(20),
  division character varying(100),
  division_code character varying(100),
  division_hq character varying(100),
  zone character varying(100),
  zone_code character varying(100),
  zone_hq character varying(100),
  CONSTRAINT pk_party_group_upd PRIMARY KEY (party_id)
);





-- Table: gnt.party_supplemental_data

-- DROP TABLE gnt.party_supplemental_data;

CREATE TABLE gnt.party_supplemental_data_upd
(
  party_id character varying(20) NOT NULL,
  parent_party_id character varying(20),
  department_name character varying(100),
  general_prof_title character varying(100),
  company_name character varying(100),
  company_name_local character varying(100),
  annual_revenue numeric(18,2),
  currency_uom_id character varying(20),
  number_employees numeric(20,0),
  industry_enum_id character varying(20),
  ownership_enum_id character varying(20),
  sic_code character varying(20),
  ticker_symbol character varying(20),
  important_note text,
  primary_postal_address_id character varying(20),
  primary_telecom_number_id character varying(20),
  primary_email_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  vat_tin_number character varying(20),
  cst_number character varying(20),
  pan_number character varying(20),
  CONSTRAINT pk_party_supplemental_data_upd PRIMARY KEY (party_id)
);



-- Table: gnt.line

-- DROP TABLE gnt.line;

CREATE TABLE gnt.line_upd
(
  line_id character varying(20) NOT NULL,
  line_code character varying(20),
  line_description character varying(20),
  remarks character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_line_upd PRIMARY KEY (line_id)
);

-- Table: gnt.lot

-- DROP TABLE gnt.lot;

CREATE TABLE gnt.lot_upd
(
  lot_id character varying(20) NOT NULL,
  creation_date timestamp with time zone,
  quantity numeric(18,6),
  expiration_date timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  comments text,
  uom_id character varying(20),
  supplier_party_id character varying(20),
  CONSTRAINT pk_lot_upd PRIMARY KEY (lot_id)
);

-- Table: gnt.make

-- DROP TABLE gnt.make;

CREATE TABLE gnt.make_upd
(
  seq_id character varying(20),
  make_code character varying(20) NOT NULL,
  make_name character varying(100),
  make_type character varying(20),
  brand_name character varying(20),
  description character varying(255),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_make_upd PRIMARY KEY (make_code)
);



-- Table: gnt.inspection_type

-- DROP TABLE gnt.inspection_type;

CREATE TABLE gnt.inspection_type_upd
(
  seq_id character varying(20) NOT NULL,
  department character varying(100),
  inspection_type character varying(255),
  description character varying(255),
  from_date date,
  thru_date date,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_inspection_type_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.inspection

-- DROP TABLE gnt.inspection;

CREATE TABLE gnt.inspection_upd
(
  inspection_seq_id character varying(20) NOT NULL,
  created_date timestamp with time zone,
  type_of_work character varying(20),
  facility_id character varying(20),
  section character varying(20),
  name_of_staff character varying(20),
  tech character varying(20),
  place_of_work character varying(20),
  observation character varying(20),
  start_location character varying(20),
  end_location character varying(20),
  next_day_plan character varying(20),
  status_of_work character varying(20),
  schedule character varying(20),
  from_date_time timestamp with time zone,
  thru_date_time timestamp with time zone,
  remarks character varying(255),
  current_status character varying(20),
  created_by character varying(255),
  created_on timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  staff_type character varying(20),
  CONSTRAINT pk_inspection_upd PRIMARY KEY (inspection_seq_id)
);

-- Table: gnt.good_identification_type

-- DROP TABLE gnt.good_identification_type;

CREATE TABLE gnt.good_identification_type_upd
(
  good_identification_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_good_identification_type_upd PRIMARY KEY (good_identification_type_id),
  CONSTRAINT good_id_type_par FOREIGN KEY (parent_type_id)
      REFERENCES gnt.good_identification_type_upd (good_identification_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.foot_patrolling_sections

-- DROP TABLE gnt.foot_patrolling_sections;

CREATE TABLE gnt.foot_patrolling_sections_upd
(
  seq_id character varying(20) NOT NULL,
  facility_depot character varying(20),
  fp_section character varying(100),
  from_location character varying(100),
  to_location character varying(100),
  remarks character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  from_date timestamp with time zone,
  to_date timestamp with time zone,
  CONSTRAINT pk_foot_patrolling_sections_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.departments

-- DROP TABLE gnt.departments;

CREATE TABLE gnt.departments_upd
(
  dept_id character varying(20) NOT NULL,
  dept_name character varying(20),
  description character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_departments_upd PRIMARY KEY (dept_id)
);


-- Table: gnt.content_purpose_type

-- DROP TABLE gnt.content_purpose_type;

CREATE TABLE gnt.content_purpose_type_upd
(
  content_purpose_type_id character varying(20) NOT NULL,
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_content_purpose_type_upd PRIMARY KEY (content_purpose_type_id)
);

-- Table: gnt.content_type

-- DROP TABLE gnt.content_type;

CREATE TABLE gnt.content_type_upd
(
  content_type_id character varying(20) NOT NULL,
  parent_type_id character varying(20),
  has_table character(1),
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_content_type_upd PRIMARY KEY (content_type_id),
  CONSTRAINT cntnt_type_parent FOREIGN KEY (parent_type_id)
      REFERENCES gnt.content_type_upd (content_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.country_code

-- DROP TABLE gnt.country_code;

CREATE TABLE gnt.country_code_upd
(
  country_code character varying(20) NOT NULL,
  country_abbr character varying(60),
  country_number character varying(60),
  country_name character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_country_code_upd PRIMARY KEY (country_code)
);


-- Table: gnt.country_capital

-- DROP TABLE gnt.country_capital;

CREATE TABLE gnt.country_capital_upd
(
  country_code character varying(20) NOT NULL,
  country_capital character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_country_capital_upd PRIMARY KEY (country_code),
  CONSTRAINT cntry_cap_to_code FOREIGN KEY (country_code)
      REFERENCES gnt.country_code_upd (country_code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.country_tele_code

-- DROP TABLE gnt.country_tele_code;

CREATE TABLE gnt.country_tele_code_upd
(
  country_code character varying(20) NOT NULL,
  tele_code character varying(60),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_country_tele_code_upd PRIMARY KEY (country_code),
  CONSTRAINT cntry_tele_to_code FOREIGN KEY (country_code)
      REFERENCES gnt.country_code_upd (country_code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) ;


-- Table: gnt.consignee

-- DROP TABLE gnt.consignee;

CREATE TABLE gnt.consignee_upd
(
  consignee_code character varying(20) NOT NULL,
  consignee_name character varying(100),
  from_date timestamp with time zone,
  thru_date timestamp with time zone,
  description character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  consignee_id character varying(20),
  designations character varying(20),
  unit_code character varying(20),
  unit_type character varying(20),
  CONSTRAINT pk_consignee_upd PRIMARY KEY (consignee_code)
) ;



-- Table: gnt.tpc_board

-- DROP TABLE gnt.tpc_board;

CREATE TABLE gnt.tpc_board_upd
(
  seq_id character varying(20) NOT NULL,
  tpc_board character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  description character varying(255),
  CONSTRAINT pk_tpc_board_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.tpc_board_reporting_facilitys

-- DROP TABLE gnt.tpc_board_reporting_facilitys;

CREATE TABLE gnt.tpc_board_reporting_facilitys_upd
(
  seq_id character varying(20) NOT NULL,
  head_designation character varying(20),
  unit_code character varying(20),
  unit_name character varying(20),
  unit_type character varying(20),
  report_manager character varying(20),
  tpc_board character varying(20),
  rm_seq_id character varying(20),
  group_id character varying(20),
  ord_level character varying(20),
  party_id character varying(20),
  unit_station character varying(20),
  head_login_id character varying(20),
  rm_login_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  unit_id character varying(20),
  description character varying(255),
  CONSTRAINT pk_tpc_board_reporting_facilit_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.tss_feeder_master

-- DROP TABLE gnt.tss_feeder_master;

CREATE TABLE gnt.tss_feeder_master_upd
(
  feeder_id character varying(20) NOT NULL,
  feeder_name character varying(20),
  tss_name character varying(20),
  description character varying(20),
  state_electricity_board character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_tss_feeder_master_upd PRIMARY KEY (feeder_id)
);


-- Table: gnt.user_defualt_fac_cons_ind_etc

-- DROP TABLE gnt.user_defualt_fac_cons_ind_etc;

CREATE TABLE gnt.user_defualt_fac_cons_ind_etc_upd
(
  sequence_id character varying(60) NOT NULL,
  facility_id character varying(60),
  facility_type_id character varying(60),
  facility_name character varying(100),
  consignee_code character varying(60),
  indentor character varying(60),
  user_login_id character varying(60),
  depot character varying(60),
  consignee character varying(60),
  matris_reqd_at character varying(60),
  description character varying(255),
  remarks character varying(255),
  from_date timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  thru_date timestamp with time zone,
  is_warehouse_manager character(1),
  CONSTRAINT pk_user_defualt_fac_cons_ind_etc_upd PRIMARY KEY (sequence_id),
  CONSTRAINT userdefualtfacconsindetc_fk FOREIGN KEY (user_login_id)
      REFERENCES gnt.user_login_upd (user_login_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.elementary_sections

-- DROP TABLE gnt.elementary_sections;

CREATE TABLE gnt.elementary_sections_upd
(
  seq_id character varying(20) NOT NULL,
  elementary_section_code character varying(100),
  facility_id character varying(20),
  tpc_board character varying(20),
  devision_id character varying(20),
  section_code character varying(60),
  station_code character varying(60),
  sector_code character varying(60),
  sub_sector_code character varying(60),
  siding_main character varying(100),
  track_code character varying(100),
  from_km character varying(20),
  from_seq character varying(20),
  to_km character varying(20),
  to_seq character varying(20),
  multi_es_remark character varying(255),
  longitudinal_dn character varying(255),
  description character varying(255),
  protection_crossover character varying(255),
  protection_turnout character varying(255),
  is_auto_dead character varying(255),
  remarks_shunting character varying(255),
  remarks_no character varying(255),
  longitudinal character varying(255),
  alternate_supply character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_elementary_sections_upd PRIMARY KEY (seq_id),
  CONSTRAINT fac_es FOREIGN KEY (facility_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.elementary_section_lines

-- DROP TABLE gnt.elementary_section_lines;

CREATE TABLE gnt.elementary_section_lines_upd
(
  seq_id character varying(20) NOT NULL,
  elementary_section_code character varying(100),
  mainline character varying(100),
  line character varying(100),
  from_location character varying(20),
  to_location character varying(20),
  from_km character varying(20),
  to_km character varying(20),
  from_seq character varying(20),
  to_seq character varying(20),
  remark1 character varying(255),
  remark2 character varying(255),
  remark3 character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_elementary_section_lines_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.elementary_section_controls

-- DROP TABLE gnt.elementary_section_controls;

CREATE TABLE gnt.elementary_section_controls_upd
(
  seq_id character varying(20) NOT NULL,
  elementary_section character varying(60),
  switch_type character varying(20),
  switch_code character varying(20),
  sub_sector_from character varying(20),
  sub_sector_to character varying(20),
  sub_sector_side character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  is_normally_opened character(1),
  CONSTRAINT pk_elementary_section_controls_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.energy_meter

-- DROP TABLE gnt.energy_meter;

CREATE TABLE gnt.energy_meter_upd
(
  seq_id character varying(20) NOT NULL,
  feeder_id character varying(20),
  meter_no character varying(20),
  multiplication_fac character varying(20),
  m_start_reading character varying(20),
  m_end_reading character varying(20),
  start_date timestamp with time zone,
  end_date timestamp with time zone,
  meter_make character varying(20),
  meter_model character varying(20),
  remarks character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  start_kwh character varying(20),
  start_kvah character varying(20),
  start_rkvah_lag character varying(20),
  start_rkvah_lead character varying(20),
  end_kwh character varying(20),
  end_kvah character varying(20),
  end_rkvah_lag character varying(20),
  end_rkvah_lead character varying(20),
  cmd character varying(20),
  CONSTRAINT pk_energy_meter_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.energy_consumption

-- DROP TABLE gnt.energy_consumption;

CREATE TABLE gnt.energy_consumption_upd
(
  seq_id character varying(20) NOT NULL,
  energy_reading_date date,
  location_type character varying(20),
  location character varying(20),
  kwh character varying(20),
  kvah character varying(20),
  rkvah_lag character varying(20),
  rkvah_lead character varying(20),
  cmd character varying(20),
  rmd character varying(20),
  pf character varying(20),
  cpf character varying(20),
  vol_max character varying(20),
  vol_min character varying(20),
  max_load character varying(20),
  max_load_time timestamp with time zone,
  joint_meter character(1),
  remarks character varying(255),
  current_status character varying(20),
  created_by character varying(255),
  created_on timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_energy_consumption_upd PRIMARY KEY (seq_id)
);




-- Table: gnt.functional_location_hierarchy

-- DROP TABLE gnt.functional_location_hierarchy;

CREATE TABLE gnt.functional_location_hierarchy_upd
(
  id character varying(20),
  head_designation character varying(60),
  unit_code character varying(20),
  unit_name character varying(20),
  unit_type character varying(20),
  report_manager character varying(255),
  rm_seq_id character varying(20),
  group_id character varying(60),
  org_level character varying(20),
  party_id character varying(60) NOT NULL,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  unit_station character varying(20),
  head_login_id character varying(60),
  rm_login_id character varying(60),
  CONSTRAINT pk_functional_location_hierarc_upd PRIMARY KEY (party_id)
);

-- Table: gnt.gantry_master_data

-- DROP TABLE gnt.gantry_master_data;

CREATE TABLE gnt.gantry_master_data_upd
(
  seq_id character varying(20) NOT NULL,
  gantry_code character varying(100),
  description character varying(255),
  elementary_sections character varying(255),
  protection_traverse_crossover character varying(255),
  protection_traverse_turnout character varying(255),
  protection_longitudnal_up character varying(255),
  protection_longitudnal_dn character varying(255),
  normally_open character varying(255),
  remarks character varying(255),
  facility_id character varying(20),
  from_location character varying(255),
  from_location_type character varying(255),
  to_location character varying(255),
  to_location_type character varying(255),
  division character varying(20),
  tpc_board character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_gantry_master_data_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.asset_schedule_assoc

-- DROP TABLE gnt.asset_schedule_assoc;

CREATE TABLE gnt.asset_schedule_assoc_upd
(
  asa_seq_id character varying(20),
  asset_type character varying(20) NOT NULL,
  schedule_code character varying(20) NOT NULL,
  sequence_code character varying(20),
  duration double precision,
  uom_of_duration character varying(20),
  description character varying(100),
  is_dpr character(1),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  target_plan_months character varying(20),
  CONSTRAINT pk_asset_schedule_assoc_upd PRIMARY KEY (asset_type, schedule_code)
);




-- Table: gnt.schedule

-- DROP TABLE gnt.schedule;

CREATE TABLE gnt.schedule_upd
(
  seq_id character varying(20),
  schedule_code character varying(20) NOT NULL,
  schedule_name character varying(20),
  schedule_type character varying(20),
  description character varying(100),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_schedule_upd PRIMARY KEY (schedule_code)
);

-- Table: gnt.sectors

-- DROP TABLE gnt.sectors;

CREATE TABLE gnt.sectors_upd
(
  seq_id character varying(20) NOT NULL,
  sector_code character varying(100),
  description character varying(255),
  from_location character varying(20),
  from_location_type character varying(20),
  to_location character varying(20),
  to_location_type character varying(20),
  division character varying(20),
  remark character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  facility_id character varying(20),
  line1 character varying(20),
  line2 character varying(20),
  CONSTRAINT pk_sectors_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.sub_sectors

-- DROP TABLE gnt.sub_sectors;

CREATE TABLE gnt.sub_sectors_upd
(
  seq_id character varying(20) NOT NULL,
  sub_sector_code character varying(100),
  description character varying(255),
  from_location character varying(20),
  from_location_type character varying(20),
  to_location character varying(20),
  to_location_type character varying(20),
  sector character varying(100),
  division character varying(20),
  remark character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  facility_id character varying(20),
  line1 character varying(20),
  line2 character varying(20),
  CONSTRAINT pk_sub_sectors_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.stations_sections

-- DROP TABLE gnt.stations_sections;

CREATE TABLE gnt.stations_sections_upd
(
  seq_id character varying(20) NOT NULL,
  station_code character varying(100),
  station_name character varying(100),
  major_section_route character varying(100),
  up_section character varying(20),
  up_section_name character varying(20),
  dn_section character varying(20),
  dn_section_name character varying(20),
  division character varying(20),
  remark character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_stations_sections_upd PRIMARY KEY (seq_id)
);



-- Table: gnt.asset_master_data

-- DROP TABLE gnt.asset_master_data;

CREATE TABLE gnt.asset_master_data_upd
(
  seq_id character varying(20) NOT NULL,
  asset_type character varying(20),
  section character varying(100),
  kilometer double precision,
  position_id character varying(20),
  part1 character varying(20),
  part2 character varying(20),
  part3 character varying(20),
  asset_id character varying(100),
  location_position character varying(100),
  facility_id character varying(20),
  elementary_section character varying(100),
  capacity_rating character varying(100),
  oem_serial character varying(100),
  rly_assigned_serial character varying(100),
  parent_asset_type character varying(20),
  parent_asset_type_id character varying(20),
  equipped_date timestamp with time zone,
  strip_date timestamp with time zone,
  date_of_commision timestamp with time zone,
  date_of_manufacture timestamp with time zone,
  date_of_received timestamp with time zone,
  source character varying(100),
  make character varying(100),
  model character varying(100),
  implantation character varying(20),
  structure character varying(20),
  warranty_amc character(1),
  warranty_amc_end_date timestamp with time zone,
  vendor character varying(20),
  expiry_date timestamp with time zone,
  created_on timestamp with time zone,
  created_by character varying(255),
  type character varying(100),
  major_section character varying(100),
  adee_section character varying(100),
  name_plate_details character varying(100),
  end1_side1 character varying(100),
  end2_side2 character varying(100),
  station character varying(100),
  lug_date timestamp with time zone,
  remark1 character varying(255),
  remark2 character varying(255),
  codal_life character varying(100),
  voltage character varying(100),
  batch character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_asset_master_data_upd PRIMARY KEY (seq_id),
  CONSTRAINT asset_facility FOREIGN KEY (facility_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT asset_mast_pro FOREIGN KEY (asset_type)
      REFERENCES gnt.product_upd (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.asset_master_history

-- DROP TABLE gnt.asset_master_history;

CREATE TABLE gnt.asset_master_history_upd
(
  seq_id character varying(20) NOT NULL,
  asset_type character varying(20),
  section character varying(20),
  kilometer double precision,
  position_id character varying(20),
  part1 character varying(20),
  part2 character varying(20),
  part3 character varying(20),
  asset_id character varying(255),
  facility_id character varying(20),
  elementary_section character varying(20),
  longitude character varying(20),
  latitude character varying(20),
  left_span character varying(20),
  right_span character varying(20),
  curvature character varying(20),
  oem_serial character varying(20),
  rly_assigned_serial character varying(20),
  parent_asset_type character varying(20),
  parent_asset_type_id character varying(20),
  equipped_date timestamp with time zone,
  strip_date timestamp with time zone,
  date_of_commision timestamp with time zone,
  date_of_manufacture timestamp with time zone,
  date_of_received timestamp with time zone,
  source character varying(20),
  make character varying(20),
  model character varying(20),
  implantation character varying(20),
  structure character varying(20),
  warranty_amc character(1),
  warranty_amc_end_date timestamp with time zone,
  vendor character varying(20),
  recent_schedule_done character varying(20),
  recent_schedule_date timestamp with time zone,
  next_schedule_due character varying(20),
  next_schedule_date timestamp with time zone,
  poh_date timestamp with time zone,
  aoh_date timestamp with time zone,
  expiry_date timestamp with time zone,
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_asset_master_history_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.asset_monthly_targets

-- DROP TABLE gnt.asset_monthly_targets;

CREATE TABLE gnt.asset_monthly_targets_upd
(
  seq_id character varying(20),
  facility_id character varying(20) NOT NULL,
  elementary_section character varying(20),
  schedule_type character varying(20) NOT NULL,
  asset_type character varying(20) NOT NULL,
  target_jan double precision,
  target_feb double precision,
  target_mar double precision,
  target_apr double precision,
  target_may double precision,
  target_june double precision,
  target_july double precision,
  target_aug double precision,
  target_sep double precision,
  target_oct double precision,
  target_nov double precision,
  target_dec double precision,
  year character varying(20) NOT NULL,
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_asset_monthly_targets_upd PRIMARY KEY (facility_id, schedule_type, asset_type, year)
);

-- Table: gnt.asset_schedule_activity_assoc

-- DROP TABLE gnt.asset_schedule_activity_assoc;

CREATE TABLE gnt.asset_schedule_activity_assoc_upd
(
  seq_id character varying(20) NOT NULL,
  asa_seq_id character varying(20),
  sub_asset_type character varying(20),
  activity_id character varying(20),
  activity_position_id character varying(20),
  make_code character varying(20),
  model_code character varying(20),
  lower_limit character varying(20),
  upper_limit character varying(20),
  description character varying(100),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  activity_flag character varying(20),
  display_order numeric(20,0),
  report_column_header character varying(255),
  CONSTRAINT pk_asset_schedule_activity_ass_upd PRIMARY KEY (seq_id),
  CONSTRAINT asset_schedule_act FOREIGN KEY (activity_id)
      REFERENCES gnt.measure_or_activity_list_upd (activity_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.asset_schedule_activity_record

-- DROP TABLE gnt.asset_schedule_activity_record;

CREATE TABLE gnt.asset_schedule_activity_record_upd
(
  asset_measure_obser_seq_id character varying(20),
  asset_id character varying(20) NOT NULL,
  schedule_code character varying(20) NOT NULL,
  schedule_date timestamp with time zone NOT NULL,
  schedule_actual_date timestamp with time zone,
  asset_schedule_history_id character varying(20),
  status character varying(20),
  m1 character varying(20),
  m2 character varying(20),
  m3 character varying(20),
  m4 character varying(20),
  m5 character varying(20),
  m6 character varying(20),
  m7 character varying(20),
  m8 character varying(20),
  m9 character varying(20),
  m10 character varying(20),
  m11 character varying(20),
  m12 character varying(20),
  m13 character varying(20),
  m14 character varying(20),
  m15 character varying(20),
  m16 character varying(20),
  m17 character varying(20),
  m18 character varying(20),
  m19 character varying(20),
  m20 character varying(20),
  m21 character varying(20),
  m22 character varying(20),
  a1 character varying(100),
  a2 character varying(100),
  a3 character varying(100),
  a4 character varying(100),
  a5 character varying(100),
  a6 character varying(100),
  a7 character varying(100),
  a8 character varying(100),
  a9 character varying(100),
  a10 character varying(100),
  a11 character varying(100),
  a12 character varying(100),
  a13 character varying(100),
  a14 character varying(100),
  a15 character varying(100),
  a16 character varying(100),
  a17 character varying(100),
  a18 character varying(100),
  a19 character varying(100),
  a20 character varying(100),
  a21 character varying(100),
  a22 character varying(100),
  details_of_maint character varying(255),
  done_by character varying(20),
  initial_of_incharge character varying(20),
  remarks character varying(255),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  facility_id character varying(20),
  asset_type character varying(20),
  device_id character varying(100),
  device_seq_id character varying(20),
  device_updated_stamp timestamp with time zone,
  device_last_updated_stamp timestamp with time zone,
  make character varying(20),
  model character varying(20),
  m23 character varying(20),
  m24 character varying(20),
  m25 character varying(20),
  m26 character varying(20),
  m27 character varying(20),
  m28 character varying(20),
  m29 character varying(20),
  m30 character varying(20),
  m31 character varying(20),
  m32 character varying(20),
  m33 character varying(20),
  m34 character varying(20),
  m35 character varying(20),
  m36 character varying(20),
  m37 character varying(20),
  m38 character varying(20),
  m39 character varying(20),
  m40 character varying(20),
  m41 character varying(20),
  m42 character varying(20),
  m43 character varying(20),
  m44 character varying(20),
  m45 character varying(20),
  m46 character varying(20),
  m47 character varying(20),
  m48 character varying(20),
  m49 character varying(20),
  m50 character varying(20),
  a23 character varying(100),
  a24 character varying(100),
  a25 character varying(100),
  a26 character varying(100),
  a27 character varying(100),
  a28 character varying(100),
  a29 character varying(100),
  a30 character varying(100),
  a31 character varying(100),
  a32 character varying(100),
  a33 character varying(100),
  a34 character varying(100),
  a35 character varying(100),
  a36 character varying(100),
  a37 character varying(100),
  a38 character varying(100),
  a39 character varying(100),
  a40 character varying(100),
  a41 character varying(100),
  a42 character varying(100),
  a43 character varying(100),
  a44 character varying(100),
  a45 character varying(100),
  a46 character varying(100),
  a47 character varying(100),
  a48 character varying(100),
  a49 character varying(100),
  a50 character varying(100),
  mma1 text,
  mma2 text,
  mma3 text,
  mma4 text,
  mma5 text,
  mma6 text,
  mma7 text,
  mma8 text,
  mma9 text,
  mma10 text,
  m51 character varying(20),
  m52 character varying(20),
  m53 character varying(20),
  m54 character varying(20),
  m55 character varying(20),
  m56 character varying(20),
  m57 character varying(20),
  m58 character varying(20),
  m59 character varying(20),
  m60 character varying(20),
  m61 character varying(20),
  m62 character varying(20),
  m63 character varying(20),
  m64 character varying(20),
  m65 character varying(20),
  m66 character varying(20),
  m67 character varying(20),
  m68 character varying(20),
  m69 character varying(20),
  m70 character varying(20),
  a51 character varying(100),
  a52 character varying(100),
  a53 character varying(100),
  a54 character varying(100),
  a55 character varying(100),
  a56 character varying(100),
  a57 character varying(100),
  a58 character varying(100),
  a59 character varying(100),
  a60 character varying(100),
  a61 character varying(100),
  a62 character varying(100),
  a63 character varying(100),
  a64 character varying(100),
  a65 character varying(100),
  a66 character varying(100),
  a67 character varying(100),
  a68 character varying(100),
  a69 character varying(100),
  a70 character varying(100),
  a71 character varying(100),
  a72 character varying(100),
  a73 character varying(100),
  a74 character varying(100),
  a75 character varying(100),
  a76 character varying(100),
  a77 character varying(100),
  a78 character varying(100),
  a79 character varying(100),
  a80 character varying(100),
  a81 character varying(100),
  a82 character varying(100),
  a83 character varying(100),
  a84 character varying(100),
  a85 character varying(100),
  a86 character varying(100),
  a87 character varying(100),
  a88 character varying(100),
  a89 character varying(100),
  a90 character varying(100),
  a91 character varying(100),
  a92 character varying(100),
  a93 character varying(100),
  a94 character varying(100),
  a95 character varying(100),
  a96 character varying(100),
  a97 character varying(100),
  a98 character varying(100),
  a99 character varying(100),
  a100 character varying(100),
  a101 character varying(100),
  a102 character varying(100),
  a103 character varying(100),
  a104 character varying(100),
  a105 character varying(100),
  a106 character varying(100),
  a107 character varying(100),
  a108 character varying(100),
  a109 character varying(100),
  a110 character varying(100),
  a111 character varying(100),
  a112 character varying(100),
  a113 character varying(100),
  a114 character varying(100),
  a115 character varying(100),
  a116 character varying(100),
  a117 character varying(100),
  a118 character varying(100),
  a119 character varying(100),
  a120 character varying(100),
  a121 character varying(100),
  a122 character varying(100),
  a123 character varying(100),
  a124 character varying(100),
  a125 character varying(100),
  a126 character varying(100),
  a127 character varying(100),
  a128 character varying(100),
  a129 character varying(100),
  a130 character varying(100),
  CONSTRAINT pk_asset_schedule_activity_rec_upd PRIMARY KEY (asset_id, schedule_code, schedule_date),
  CONSTRAINT asset_schedule_rec FOREIGN KEY (schedule_code)
      REFERENCES gnt.schedule_upd (schedule_code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.assets_schedule_history

-- DROP TABLE gnt.assets_schedule_history;

CREATE TABLE gnt.assets_schedule_history_upd
(
  seq_id character varying(20) NOT NULL,
  facility_id character varying(20),
  asset_type character varying(20),
  asset_id character varying(100),
  schedule_code character varying(20),
  schedule_date timestamp with time zone,
  status character varying(20),
  details_of_maint character varying(20),
  done_by character varying(20),
  initial_of_incharge character varying(20),
  remarks character varying(100),
  pb_operation_seq_id character varying(20),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  device_id character varying(100),
  device_seq_id character varying(20),
  device_created_stamp timestamp with time zone,
  device_last_updated_stamp timestamp with time zone,
  CONSTRAINT pk_assets_schedule_history_upd PRIMARY KEY (seq_id),
  CONSTRAINT asset_schedule_hist FOREIGN KEY (schedule_code)
      REFERENCES gnt.schedule_upd (schedule_code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.asset_status_update

-- DROP TABLE gnt.asset_status_update;

CREATE TABLE gnt.asset_status_update_upd
(
  seq_id character varying(20) NOT NULL,
  created_date timestamp with time zone,
  asset_id character varying(20),
  asset_type character varying(20),
  facility_id character varying(20),
  date_of_status timestamp with time zone,
  status character varying(20),
  defect_observed character varying(255),
  reason_of_status_change character varying(20),
  schedule character varying(20),
  remarks character varying(255),
  current_status character varying(20),
  created_by character varying(255),
  created_on timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_asset_status_update_upd PRIMARY KEY (seq_id)
);

-- Table: gnt.assets_monthly_targets_history

-- DROP TABLE gnt.assets_monthly_targets_history;

CREATE TABLE gnt.assets_monthly_targets_history_upd
(
  seq_id character varying(20) NOT NULL,
  depot character varying(100),
  asset_type character varying(100),
  schedule_code character varying(100),
  total_population double precision,
  target_year double precision,
  target_month double precision,
  completedyearly double precision,
  completed_monthly double precision,
  apr double precision,
  may double precision,
  jun double precision,
  jul double precision,
  aug double precision,
  sep double precision,
  oct double precision,
  nov double precision,
  "dec" double precision,
  jan double precision,
  feb double precision,
  mar double precision,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_assets_monthly_targets_hist_upd PRIMARY KEY (seq_id)
);



-- Table: gnt.daily_progress_summery

-- DROP TABLE gnt.daily_progress_summery;

CREATE TABLE gnt.daily_progress_summery_upd
(
  seq_id character varying(20) NOT NULL,
  created_date timestamp with time zone,
  facility_id character varying(20),
  location character varying(20),
  serial_no character varying(20),
  day_progress text,
  name_of_staff character varying(20),
  remarks character varying(255),
  current_status character varying(20),
  created_by character varying(255),
  created_on timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  npb_progress text,
  psi_progress text,
  tomorrow_forecast text,
  CONSTRAINT pk_daily_progress_summery_upd PRIMARY KEY (seq_id),
  CONSTRAINT fac_dps FOREIGN KEY (facility_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.dmtr

-- DROP TABLE gnt.dmtr;

CREATE TABLE gnt.dmtr_upd
(
  dmtr_seq_id character varying(20) NOT NULL,
  dmtr_id numeric(20,0),
  ledger_id numeric(20,0),
  product_id character varying(20),
  facility_id character varying(20),
  quantity numeric(18,6),
  transaction_date timestamp with time zone,
  work_effort_id character varying(60),
  advice_note_id character varying(20),
  it_challan_id_from character varying(60),
  it_challan_id_to character varying(60),
  stock_voucher_id character varying(20),
  order_id character varying(20),
  cash_bill_id character varying(60),
  work_order_name character varying(60),
  contractor_name character varying(60),
  return_voucher character varying(60),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_dmtr_upd PRIMARY KEY (dmtr_seq_id)
);

-- Table: gnt.dmtr_inventoryetc_assoc

-- DROP TABLE gnt.dmtr_inventoryetc_assoc;

CREATE TABLE gnt.dmtr_inventoryetc_assoc_upd
(
  dmtr_inventoryetc_assoc_seq_id character varying(20) NOT NULL,
  dmtr_seq_id character varying(20),
  inventory_item_id character varying(20),
  inventory_item_detail_seq_id character varying(20),
  inventory_transfer_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_dmtr_inventoryetc_assoc_upd PRIMARY KEY (dmtr_inventoryetc_assoc_seq_id)
);




-- Table: gnt.good_identification

-- DROP TABLE gnt.good_identification;

CREATE TABLE gnt.good_identification_upd
(
  good_identification_type_id character varying(20) NOT NULL,
  product_id character varying(20) NOT NULL,
  id_value character varying(60),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_good_identification_upd PRIMARY KEY (good_identification_type_id, product_id),
  CONSTRAINT good_id_prodict FOREIGN KEY (product_id)
      REFERENCES gnt.product_upd (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT good_id_type FOREIGN KEY (good_identification_type_id)
      REFERENCES gnt.good_identification_type_upd (good_identification_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.incoming_consignment

-- DROP TABLE gnt.incoming_consignment;

CREATE TABLE gnt.incoming_consignment_upd
(
  incoming_consignment_id character varying(20) NOT NULL,
  party_id character varying(20),
  order_id character varying(20),
  inward_no character varying(20),
  invoice_no character varying(20),
  gpi_no character varying(20),
  delivery_challan_no character varying(20),
  date_received timestamp with time zone,
  received_by character varying(20),
  no_of_items_received character varying(20),
  certificates_received character varying(20),
  no_of_documents character varying(20),
  comments character varying(20),
  entered_by character varying(20),
  status character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_incoming_consignment_upd PRIMARY KEY (incoming_consignment_id)
);


-- Table: gnt.incoming_consignment_detail

-- DROP TABLE gnt.incoming_consignment_detail;

CREATE TABLE gnt.incoming_consignment_detail_upd
(
  incoming_consignment_id character varying(20),
  incoming_consignment_detail_id character varying(20) NOT NULL,
  product_id character varying(20),
  quantity_received numeric(18,3),
  quantity_tested character varying(20),
  discrepancy_quantity character varying(20),
  test_status character varying(20),
  lot_id character varying(20),
  inventory_item_type character varying(20),
  order_item_seq_id character varying(20),
  order_id character varying(20),
  inward_no character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_incoming_consignment_detail_upd PRIMARY KEY (incoming_consignment_detail_id),
  CONSTRAINT incoming_consignment_id FOREIGN KEY (incoming_consignment_id)
      REFERENCES gnt.incoming_consignment_upd (incoming_consignment_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.indent_track

-- DROP TABLE gnt.indent_track;

CREATE TABLE gnt.indent_track_upd
(
  sequence_id character varying(20) NOT NULL,
  requirement_id character varying(20),
  indent_number character varying(20),
  requisition_no character varying(20),
  status_id character varying(20),
  updated_by character varying(100),
  updated_date timestamp with time zone,
  description character varying(255),
  remarks character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_indent_track_upd PRIMARY KEY (sequence_id)
);


-- Table: gnt.inventory_item

-- DROP TABLE gnt.inventory_item;

CREATE TABLE gnt.inventory_item_upd
(
  inventory_item_id character varying(20) NOT NULL,
  inventory_item_type_id character varying(20),
  product_id character varying(20),
  party_id character varying(20),
  inward_no character varying(20),
  owner_party_id character varying(20),
  status_id character varying(20),
  datetime_received timestamp with time zone,
  datetime_manufactured timestamp with time zone,
  expire_date timestamp with time zone,
  facility_id character varying(20),
  container_id character varying(20),
  lot_id character varying(20),
  uom_id character varying(20),
  bin_number character varying(20),
  location_seq_id character varying(20),
  comments character varying(255),
  quantity_on_hand_total numeric(18,6),
  available_to_promise_total numeric(18,6),
  quantity_on_hand numeric(18,6),
  available_to_promise numeric(18,6),
  serial_number character varying(255),
  soft_identifier character varying(255),
  activation_number character varying(255),
  activation_valid_thru timestamp with time zone,
  unit_cost numeric(18,6),
  currency_uom_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  acctg_tag_enum_id1 character varying(20),
  acctg_tag_enum_id2 character varying(20),
  acctg_tag_enum_id3 character varying(20),
  acctg_tag_enum_id4 character varying(20),
  acctg_tag_enum_id5 character varying(20),
  acctg_tag_enum_id6 character varying(20),
  acctg_tag_enum_id7 character varying(20),
  acctg_tag_enum_id8 character varying(20),
  acctg_tag_enum_id9 character varying(20),
  acctg_tag_enum_id10 character varying(20),
  parent_inventory_item_id character varying(20),
  accounting_quantity_total numeric(18,6),
  depot character varying(20),
  ward character varying(20),
  issue_note_no character varying(20),
  issue_note_remarks character varying(20),
  additional_specifications text,
  contrator_name character varying(20),
  work_order_name character varying(20),
  bill_no character varying(60),
  bill_date timestamp with time zone,
  purchase_from character varying(60),
  voucher_no character varying(20),
  external_depot character varying(100),
  work_order character varying(100),
  division character varying(100),
  functional_unit character varying(100),
  issue_purpose character varying(100),
  CONSTRAINT pk_inventory_item_upd PRIMARY KEY (inventory_item_id)
);


-- Table: gnt.item_issuance

-- DROP TABLE gnt.item_issuance;

CREATE TABLE gnt.item_issuance_upd
(
  item_issuance_id character varying(20) NOT NULL,
  order_id character varying(20),
  order_item_seq_id character varying(20),
  ship_group_seq_id character varying(20),
  inventory_item_id character varying(20),
  shipment_id character varying(20),
  shipment_item_seq_id character varying(20),
  fixed_asset_id character varying(20),
  maint_hist_seq_id character varying(20),
  issued_date_time timestamp with time zone,
  issued_by_user_login_id character varying(255),
  quantity numeric(18,6),
  cancel_quantity numeric(18,6),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_item_issuance_upd PRIMARY KEY (item_issuance_id)
);


-- Table: gnt.work_effort

-- DROP TABLE gnt.work_effort;

CREATE TABLE gnt.work_effort_upd
(
  work_effort_id character varying(60) NOT NULL,
  work_effort_type_id character varying(20),
  current_status_id character varying(20),
  last_status_update timestamp with time zone,
  work_effort_purpose_type_id character varying(20),
  work_effort_parent_id character varying(60),
  scope_enum_id character varying(20),
  priority numeric(20,0),
  severity numeric(20,0),
  percent_complete numeric(20,0),
  work_effort_name character varying(100),
  show_as_enum_id character varying(20),
  send_notification_email character(1),
  description character varying(255),
  location_desc character varying(255),
  estimated_start_date timestamp with time zone,
  estimated_completion_date timestamp with time zone,
  actual_start_date timestamp with time zone,
  actual_completion_date timestamp with time zone,
  estimated_milli_seconds double precision,
  estimated_setup_millis double precision,
  estimate_calc_method character varying(20),
  actual_milli_seconds double precision,
  actual_setup_millis double precision,
  total_milli_seconds_allowed double precision,
  total_money_allowed numeric(18,2),
  money_uom_id character varying(20),
  special_terms character varying(255),
  time_transparency numeric(20,0),
  universal_id character varying(60),
  source_reference_id character varying(60),
  fixed_asset_id character varying(20),
  facility_id character varying(20),
  info_url character varying(255),
  recurrence_info_id character varying(20),
  temp_expr_id character varying(20),
  runtime_data_id character varying(20),
  note_id character varying(20),
  service_loader_name character varying(100),
  quantity_to_produce numeric(18,6),
  quantity_produced numeric(18,6),
  quantity_rejected numeric(18,6),
  reserv_persons numeric(18,6),
  reserv2nd_p_p_perc numeric(18,6),
  reserv_nth_p_p_perc numeric(18,6),
  accommodation_map_id character varying(20),
  accommodation_spot_id character varying(20),
  revision_number numeric(20,0),
  created_date timestamp with time zone,
  created_by_user_login character varying(255),
  last_modified_date timestamp with time zone,
  last_modified_by_user_login character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  sequence_num numeric(20,0),
  CONSTRAINT pk_work_effort_upd PRIMARY KEY (work_effort_id),
  /*
  CONSTRAINT wk_effrt_acc_map FOREIGN KEY (accommodation_map_id)
      REFERENCES gnt.accommodation_map (accommodation_map_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wk_effrt_acc_spot FOREIGN KEY (accommodation_spot_id)
      REFERENCES gnt.accommodation_spot (accommodation_spot_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wk_effrt_curstts FOREIGN KEY (current_status_id)
      REFERENCES gnt.status_item (status_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wk_effrt_cus_met FOREIGN KEY (estimate_calc_method)
      REFERENCES gnt.custom_method (custom_method_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  */
  CONSTRAINT wk_effrt_facility FOREIGN KEY (facility_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      /*
  CONSTRAINT wk_effrt_fxdasst FOREIGN KEY (fixed_asset_id)
      REFERENCES gnt.fixed_asset (fixed_asset_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      */
  CONSTRAINT wk_effrt_mon_uom FOREIGN KEY (money_uom_id)
      REFERENCES gnt.uom_upd (uom_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  /*CONSTRAINT wk_effrt_note FOREIGN KEY (note_id)
      REFERENCES gnt.note_data (note_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      */
  CONSTRAINT wk_effrt_parent FOREIGN KEY (work_effort_parent_id)
      REFERENCES gnt.work_effort_upd (work_effort_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      /*
  CONSTRAINT wk_effrt_prptyp FOREIGN KEY (work_effort_purpose_type_id)
      REFERENCES gnt.work_effort_purpose_type (work_effort_purpose_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   
  CONSTRAINT wk_effrt_recinfo FOREIGN KEY (recurrence_info_id)
      REFERENCES gnt.recurrence_info (recurrence_info_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wk_effrt_rntmdta FOREIGN KEY (runtime_data_id)
      REFERENCES gnt.runtime_data (runtime_data_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wk_effrt_sc_enum FOREIGN KEY (scope_enum_id)
      REFERENCES gnt.enumeration (enum_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wk_effrt_tempexpr FOREIGN KEY (temp_expr_id)
      REFERENCES gnt.temporal_expression (temp_expr_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
         */
  CONSTRAINT wk_effrt_type FOREIGN KEY (work_effort_type_id)
      REFERENCES gnt.work_effort_type_upd (work_effort_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.work_effort_status

-- DROP TABLE gnt.work_effort_status;

CREATE TABLE gnt.work_effort_status_upd
(
  work_effort_id character varying(60) NOT NULL,
  status_id character varying(20) NOT NULL,
  status_datetime timestamp with time zone NOT NULL,
  set_by_user_login character varying(255),
  reason character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_work_effort_status_upd PRIMARY KEY (work_effort_id, status_id, status_datetime),
  CONSTRAINT wkeff_stts_sb_ul FOREIGN KEY (set_by_user_login)
      REFERENCES gnt.user_login_upd (user_login_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wkeff_stts_stts FOREIGN KEY (status_id)
      REFERENCES gnt.status_item_upd (status_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT wkeff_stts_we FOREIGN KEY (work_effort_id)
      REFERENCES gnt.work_effort_upd (work_effort_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.inventory_item_detail

-- DROP TABLE gnt.inventory_item_detail;

CREATE TABLE gnt.inventory_item_detail_upd
(
  inventory_item_id character varying(20) NOT NULL,
  inventory_item_detail_seq_id character varying(20) NOT NULL,
  effective_date timestamp with time zone,
  quantity_on_hand_diff numeric(18,6),
  available_to_promise_diff numeric(18,6),
  accounting_quantity_diff numeric(18,6),
  unit_cost numeric(18,6),
  order_id character varying(20),
  order_item_seq_id character varying(20),
  ship_group_seq_id character varying(20),
  shipment_id character varying(20),
  shipment_item_seq_id character varying(20),
  return_id character varying(20),
  return_item_seq_id character varying(20),
  work_effort_id character varying(60),
  fixed_asset_id character varying(20),
  maint_hist_seq_id character varying(20),
  item_issuance_id character varying(20),
  receipt_id character varying(20),
  physical_inventory_id character varying(20),
  reason_enum_id character varying(20),
  description character varying(255),
  voucher_no character varying(20),
  mir_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  contrator_name character varying(20),
  bill_no character varying(60),
  bill_date timestamp with time zone,
  work_order_name character varying(20),
  purchase_from character varying(60),
  external_depot character varying(100),
  work_order character varying(100),
  division character varying(100),
  functional_unit character varying(100),
  issue_purpose character varying(100),
  CONSTRAINT pk_inventory_item_detail_upd PRIMARY KEY (inventory_item_id, inventory_item_detail_seq_id),

  /*
  CONSTRAINT inv_itdtl_famnt FOREIGN KEY (fixed_asset_id, maint_hist_seq_id)
      REFERENCES gnt.fixed_asset_maint (fixed_asset_id, maint_hist_seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
*/
  CONSTRAINT inv_itdtl_invit FOREIGN KEY (inventory_item_id)
      REFERENCES gnt.inventory_item_upd (inventory_item_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inv_itdtl_itmis FOREIGN KEY (item_issuance_id)
      REFERENCES gnt.item_issuance_upd (item_issuance_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
/*
  CONSTRAINT inv_itdtl_phinv FOREIGN KEY (physical_inventory_id)
      REFERENCES gnt.physical_inventory (physical_inventory_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT inv_itdtl_reas FOREIGN KEY (reason_enum_id)
      REFERENCES gnt.enumeration (enum_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inv_itdtl_shrct FOREIGN KEY (receipt_id)
      REFERENCES gnt.shipment_receipt (receipt_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      */
  CONSTRAINT inv_itdtl_weff FOREIGN KEY (work_effort_id)
      REFERENCES gnt.work_effort_upd (work_effort_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.inventory_item_status

-- DROP TABLE gnt.inventory_item_status;

CREATE TABLE gnt.inventory_item_status_upd
(
  inventory_item_id character varying(20) NOT NULL,
  status_id character varying(20) NOT NULL,
  status_datetime timestamp with time zone NOT NULL,
  status_end_datetime timestamp with time zone,
  owner_party_id character varying(20),
  product_id character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_inventory_item_status_upd PRIMARY KEY (inventory_item_id, status_id, status_datetime)
);

-- Table: gnt.inventory_transfer

-- DROP TABLE gnt.inventory_transfer;

CREATE TABLE gnt.inventory_transfer_upd
(
  inventory_transfer_id character varying(20) NOT NULL,
  status_id character varying(20),
  inventory_item_id character varying(20),
  facility_id character varying(20),
  location_seq_id character varying(20),
  container_id character varying(20),
  facility_id_to character varying(20),
  location_seq_id_to character varying(20),
  container_id_to character varying(20),
  item_issuance_id character varying(20),
  send_date timestamp with time zone,
  receive_date timestamp with time zone,
  comments character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  advice_note_no character varying(20),
  rate_demanded character varying(20),
  controlling_officer character varying(100),
  unit character varying(20),
  credit_note_no character varying(20),
  wagon_no character varying(20),
  allocation character varying(100),
  rr_no character varying(20),
  depot_name character varying(100),
  depot character varying(20),
  ward character varying(20),
  voucher_no character varying(20),
  catg character varying(20),
  rateallowed character varying(20),
  challan_no character varying(60),
  certificate_status character varying(20),
  external_depot character varying(100),
  work_order character varying(100),
  division character varying(100),
  functional_unit character varying(100),
  contractor_name character varying(100),
  issue_purpose character varying(100),
  CONSTRAINT pk_inventory_transfer_upd PRIMARY KEY (inventory_transfer_id)
);

/*
-- Table: gnt.mir

-- DROP TABLE gnt.mir;

CREATE TABLE gnt.mir
(
  mir_seq_id character varying(20) NOT NULL,
  mir_id character varying(20),
  work_effort_id character varying(255),
  issued_time timestamp with time zone,
  issued_type character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_mir PRIMARY KEY (mir_seq_id)
);

*/
-- Table: gnt.pb_switch_control

-- DROP TABLE gnt.pb_switch_control;

CREATE TABLE gnt.pb_switch_control_upd
(
  seq_id character varying(20) NOT NULL,
  pb_extent_type character varying(20),
  pb_extent_code character varying(100),
  up_dn character varying(100),
  line character varying(100),
  switch_type character varying(20),
  switch_id character varying(20),
  is_normally_opened character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_pb_switch_control_upd PRIMARY KEY (seq_id)
);


-- Table: gnt.person

-- DROP TABLE gnt.person;

CREATE TABLE gnt.person_upd
(
  party_id character varying(20) NOT NULL,
  salutation character varying(100),
  first_name character varying(100),
  middle_name character varying(100),
  last_name character varying(100),
  personal_title character varying(100),
  suffix character varying(100),
  nickname character varying(100),
  first_name_local character varying(100),
  middle_name_local character varying(100),
  last_name_local character varying(100),
  other_local character varying(100),
  member_id character varying(20),
  gender character(1),
  birth_date date,
  height double precision,
  weight double precision,
  mothers_maiden_name character varying(255),
  marital_status character(1),
  social_security_number character varying(255),
  passport_number character varying(255),
  passport_expire_date date,
  total_years_work_experience double precision,
  comments character varying(255),
  employment_status_enum_id character varying(20),
  residence_status_enum_id character varying(20),
  occupation character varying(100),
  years_with_employer numeric(20,0),
  months_with_employer numeric(20,0),
  existing_customer character(1),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  deceased_date date,
  card_id character varying(60),
  physically_challenged character(1),
  passport_issued_in character varying(20),
  father_name character varying(100),
  mother_name character varying(100),
  husband_name character varying(100),
  CONSTRAINT pk_person_upd PRIMARY KEY (party_id)
);

-- Table: gnt.power_blocks

-- DROP TABLE gnt.power_blocks;

CREATE TABLE gnt.power_blocks_upd
(
  pb_operation_seq_id character varying(20) NOT NULL,
  created_date timestamp with time zone,
  facility_id character varying(100),
  type_of_operation character varying(20),
  shadow_block character varying(20),
  elementary_section_code character varying(100),
  section character varying(20),
  line character varying(20),
  line2 character varying(20),
  req_department character varying(20),
  req_period character varying(20),
  reqn_by character varying(60),
  ptw_availed_from_date_time timestamp with time zone,
  tpc_no_ptw_issue character varying(20),
  field_no_ptw_issue character varying(20),
  ptw_availed_thru_date_time timestamp with time zone,
  tpc_no_ptw_return character varying(20),
  field_no_ptw_return character varying(20),
  purpose character varying(255),
  pb_requested_from_date_time timestamp with time zone,
  pb_requested_thru_date_time timestamp with time zone,
  pb_granted_from_date_time timestamp with time zone,
  pb_granted_thru_date_time timestamp with time zone,
  staff_to_work character varying(20),
  post character varying(20),
  switching_station character varying(20),
  switching_equipment character varying(255),
  equipment_to_work character varying(255),
  special_remarks character varying(255),
  remarks character varying(255),
  tpc_board character varying(100),
  schedule character varying(100),
  supervisor_incharge character varying(20),
  current_status character varying(20),
  created_on timestamp with time zone,
  created_by character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  grant_period character varying(20),
  CONSTRAINT pk_power_blocks_upd PRIMARY KEY (pb_operation_seq_id)
);

-- Table: gnt.power_blocks_amendment

-- DROP TABLE gnt.power_blocks_amendment;

CREATE TABLE gnt.power_blocks_amendment_upd
(
  pb_operation_seq_id character varying(20),
  pb_amendment_seq_id character varying(20) NOT NULL,
  ptw_availed_from_date_time timestamp with time zone,
  tpc_no_ptw_issue character varying(20),
  field_no_ptw_issue character varying(20),
  ptw_availed_thru_date_time timestamp with time zone,
  tpc_no_ptw_return character varying(20),
  field_no_ptw_return character varying(20),
  created_by character varying(255),
  updated_by character varying(255),
  delete character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_power_blocks_amendment_upd PRIMARY KEY (pb_amendment_seq_id),
  CONSTRAINT pb_adm FOREIGN KEY (pb_operation_seq_id)
      REFERENCES gnt.power_blocks_upd (pb_operation_seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



-- Table: gnt.requirement_status

-- DROP TABLE gnt.requirement_status;

CREATE TABLE gnt.requirement_status_upd
(
  requirement_id character varying(20) NOT NULL,
  status_id character varying(20) NOT NULL,
  status_date timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_requirement_status_upd PRIMARY KEY (requirement_id, status_id)
);

;



-- Table: gnt.requirement

-- DROP TABLE gnt.requirement;

CREATE TABLE gnt.requirement_upd
(
  requirement_id character varying(20) NOT NULL,
  requirement_type_id character varying(20),
  facility_id character varying(20),
  deliverable_id character varying(20),
  fixed_asset_id character varying(20),
  product_id character varying(20),
  status_id character varying(20),
  description character varying(255),
  requirement_start_date timestamp with time zone,
  required_by_date timestamp with time zone,
  estimated_budget numeric(18,2),
  quantity numeric(18,6),
  use_case text,
  requisition_no character varying(20),
  depot character varying(20),
  indentor character varying(20),
  consignee character varying(20),
  consignee_code character varying(20),
  matris_reqd_at character varying(20),
  unit character varying(20),
  rate numeric(18,6),
  allocation_code character varying(60),
  last_prchse_particlrs character varying(100),
  nomenclature_no character varying(20),
  qty_in_stok_with_indntr character varying(20),
  tol_qty_agnst_othr_indts character varying(20),
  total_qty_against_po character varying(20),
  consumed_in_past_yar1 character varying(20),
  consumed_in_past_yar2 character varying(20),
  consumed_in_past_yar3 character varying(20),
  remarks character varying(255),
  supplier_depot character varying(20),
  voucher_no character varying(20),
  reason character varying(255),
  created_date timestamp with time zone,
  created_by_user_login character varying(255),
  last_modified_date timestamp with time zone,
  last_modified_by_user_login character varying(255),
  internal_name character varying(255),
  facility_voucher_sequence character varying(20),
  financial_year character varying(20),
  material_category character varying(255),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  order_item_type_id character varying(20),
  facility_id_to character varying(20),
  case_number character varying(60),
  period_from timestamp with time zone,
  period_to timestamp with time zone,
  material_depot character varying(20),
  sanctioned_imprest_item character varying(20),
  cat character varying(20),
  additional_specifications text,
  requisition_max_no numeric(20,0),
  CONSTRAINT pk_requirement_upd PRIMARY KEY (requirement_id),
  /*
  CONSTRAINT req_deliverable FOREIGN KEY (deliverable_id)
      REFERENCES gnt.deliverable (deliverable_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      */
  CONSTRAINT req_facility FOREIGN KEY (facility_id)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  /*
  CONSTRAINT req_fixed_asset FOREIGN KEY (fixed_asset_id)
      REFERENCES gnt.fixed_asset (fixed_asset_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT req_orditm_ty FOREIGN KEY (order_item_type_id)
      REFERENCES gnt.order_item_type (order_item_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  */
  CONSTRAINT req_product FOREIGN KEY (product_id)
      REFERENCES gnt.product_upd (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT req_stts FOREIGN KEY (status_id)
      REFERENCES gnt.status_item_upd (status_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT req_to_facility FOREIGN KEY (facility_id_to)
      REFERENCES gnt.facility_upd (facility_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT req_type FOREIGN KEY (requirement_type_id)
      REFERENCES gnt.requirement_type_upd (requirement_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: gnt.switch_maintenence_history

-- DROP TABLE gnt.switch_maintenence_history;

CREATE TABLE gnt.switch_maintenence_history_upd
(
  seq_id character varying(20) NOT NULL,
  pb_operation_seq_id character varying(20),
  io_location character varying(20),
  io_type character varying(20),
  io_opened_by character varying(100),
  io_opened_date_time timestamp with time zone,
  tpc_no_io_open character varying(20),
  field_no_io_open character varying(20),
  io_opened_date_time_done timestamp with time zone,
  tpc_no_io_open_done character varying(20),
  field_no_io_open_done character varying(20),
  io_closed_by character varying(100),
  io_closed_date_time timestamp with time zone,
  tpc_no_io_close character varying(20),
  field_no_io_close character varying(20),
  io_closed_date_time_done timestamp with time zone,
  tpc_no_io_close_done character varying(20),
  field_no_io_close_done character varying(20),
  is_field_operated character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  open_time_lapse character varying(20),
  open_done_time_lapse character varying(20),
  close_time_lapse character varying(20),
  close_done_time_lapse character varying(20),
  delete_status character varying(20),
  CONSTRAINT pk_switch_maintenence_history_upd PRIMARY KEY (seq_id),
  CONSTRAINT sw_pb FOREIGN KEY (pb_operation_seq_id)
      REFERENCES gnt.power_blocks_upd (pb_operation_seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: gnt.switch_maintenence_history_amendment

-- DROP TABLE gnt.switch_maintenence_history_amendment;

CREATE TABLE gnt.switch_maintenence_history_amendment_upd
(
  seq_id character varying(20),
  amendment_seq_id character varying(20) NOT NULL,
  io_opened_by character varying(100),
  io_opened_date_time timestamp with time zone,
  tpc_no_io_open character varying(20),
  field_no_io_open character varying(20),
  io_opened_date_time_done timestamp with time zone,
  tpc_no_io_open_done character varying(20),
  field_no_io_open_done character varying(20),
  io_closed_by character varying(100),
  io_closed_date_time timestamp with time zone,
  tpc_no_io_close character varying(20),
  field_no_io_close character varying(20),
  io_closed_date_time_done timestamp with time zone,
  tpc_no_io_close_done character varying(20),
  field_no_io_close_done character varying(20),
  is_field_operated character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  created_by character varying(255),
  updated_by character varying(255),
  delete character varying(255),
  CONSTRAINT pk_switch_maintenence_history_amendment_upd PRIMARY KEY (amendment_seq_id),
  CONSTRAINT sw_pb_adm FOREIGN KEY (seq_id)
      REFERENCES gnt.switch_maintenence_history_upd (seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);




-- Table: gnt.failures_upd

-- DROP TABLE gnt.failures_upd;

CREATE TABLE gnt.failures_upd
(
  failure_seq_id character varying(20) NOT NULL,
  created_date timestamp with time zone,
  type_of_failure character varying(20),
  feed_of character varying(20),
  extended_of character varying(20),
  from_date_time timestamp with time zone,
  thru_date_time timestamp with time zone,
  max_demand character varying(20),
  staff character varying(20),
  facility_id character varying(20),
  location character varying(20),
  section character varying(20),
  sub_station character varying(20),
  asset_id character varying(20),
  equipment character varying(20),
  failure_date_time timestamp with time zone,
  resume_date_time timestamp with time zone,
  relay_indication character varying(20),
  r_value character varying(20),
  x_value character varying(20),
  fault_distance character varying(20),
  voltage character varying(20),
  current character varying(20),
  phase_angle character varying(20),
  nature_of_closure character varying(20),
  occurrence character varying(20),
  train_no character varying(20),
  place character varying(20),
  duration character varying(20),
  cause_of_failure character varying(255),
  punctuality_affected_to character varying(255),
  time_delay character varying(20),
  remarks character varying(255),
  current_status character varying(20),
  created_by character varying(255),
  created_on timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  feed_extended_from_date_time timestamp with time zone,
  feed_extended_thru_date_time timestamp with time zone,
  feed_extended_duration character varying(20),
  impact character varying(20),
  asset_type character varying(20),
  tripped_identified_fault character varying(10),
  cascade_assets character varying(255),
  tpc_board character varying(20),
  actual_fault_distance character varying(20),
  division_local character varying(10),
  internal_external character varying(10),
  CONSTRAINT pk_failures_upd PRIMARY KEY (failure_seq_id)
);




-- Table: gnt.precautionary_measures_master_upd

-- DROP TABLE gnt.precautionary_measures_master_upd;

CREATE TABLE gnt.precautionary_measures_master_upd
(
  from_date_time timestamp with time zone,
  thru_date_time timestamp with time zone,
  location character varying(20),
  precautionary_measure character varying(255) NOT NULL,
  done_by character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_precautionary_measures_mast_upd PRIMARY KEY (precautionary_measure)
);




-- Table: gnt.precautionary_measure_upd

-- DROP TABLE gnt.precautionary_measure_upd;

CREATE TABLE gnt.precautionary_measure_upd
(
  seq_id character varying(20) NOT NULL,
  facility_name character varying(20),
  created_date timestamp with time zone,
  from_date_time timestamp with time zone,
  thru_date_time timestamp with time zone,
  location character varying(20),
  precautionary_measure character varying(255),
  remarks text,
  done_by character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_precautionary_measure_upd PRIMARY KEY (seq_id),
  CONSTRAINT precautionarymeasuresmaster_pr FOREIGN KEY (precautionary_measure)
      REFERENCES gnt.precautionary_measures_master_upd (precautionary_measure) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);





-- Table: gnt.foot_patrolling_inspection_upd

-- DROP TABLE gnt.foot_patrolling_inspection_upd;

CREATE TABLE gnt.foot_patrolling_inspection_upd
(
  seq_id character varying(20) NOT NULL,
  device_id character varying(100),
  device_seq_id character varying(100),
  inspection_type character varying(255),
  start_time timestamp with time zone,
  stop_time timestamp with time zone,
  inspection_by character varying(100),
  section character varying(100),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  facility_id character varying(20),
  km character varying(20),
  locatoion character varying(100),
  location character varying(100),
  CONSTRAINT pk_foot_patrolling_inspection_upd PRIMARY KEY (seq_id)
);



-- Table: gnt.observation_categories_upd

-- DROP TABLE gnt.observation_categories_upd;

CREATE TABLE gnt.observation_categories_upd
(
  seq_id character varying(20) NOT NULL,
  inspection_type character varying(255),
  department character varying(100),
  observation_category character varying(255),
  description character varying(255),
  remark character varying(255),
  from_date date,
  thru_date date,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  CONSTRAINT pk_observation_categories_upd PRIMARY KEY (seq_id)
);



-- Table: gnt.observations_check_list_upd

-- DROP TABLE gnt.observations_check_list_upd;

CREATE TABLE gnt.observations_check_list_upd
(
  seq_id character varying(20) NOT NULL,
  inspection_type character varying(255),
  observation_category character varying(255),
  observation_item character varying(255),
  description character varying(255),
  from_date date,
  thru_date date,
  priority character varying(20),
  display_sequence character varying(20),
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  severe character varying(255),
  CONSTRAINT pk_observations_check_list_upd PRIMARY KEY (seq_id),
  CONSTRAINT inspectiontype FOREIGN KEY (inspection_type)
      REFERENCES gnt.inspection_type_upd (seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);




-- Table: gnt.observations_upd

-- DROP TABLE gnt.observations_upd;

CREATE TABLE gnt.observations_upd
(
  seq_id character varying(20) NOT NULL,
  device_id character varying(100),
  device_seq_id character varying(100),
  inspection_seq_id character varying(20),
  observation_category character varying(255),
  observation_item character varying(255),
  observation character varying(255),
  description character varying(255),
  action character varying(255),
  action_by character varying(100),
  created_by character varying(100),
  created_date_time timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  location character varying(100),
  CONSTRAINT pk_observations_upd PRIMARY KEY (seq_id),
  CONSTRAINT footpatrollinginspection FOREIGN KEY (inspection_seq_id)
      REFERENCES gnt.foot_patrolling_inspection_upd (seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



-- Table: gnt.compliances_upd

-- DROP TABLE gnt.compliances_upd;

CREATE TABLE gnt.compliances_upd
(
  seq_id character varying(20) NOT NULL,
  device_id character varying(100),
  device_seq_id character varying(100),
  obeservation_seq_id character varying(20),
  compliance_fullfilled character varying(255),
  description character varying(255),
  status character varying(100),
  compliance_by character varying(100),
  complied_date_time timestamp with time zone,
  last_updated_stamp timestamp with time zone,
  last_updated_tx_stamp timestamp with time zone,
  created_stamp timestamp with time zone,
  created_tx_stamp timestamp with time zone,
  compliance_remark character varying(255),
  action character varying(255),
  CONSTRAINT pk_compliances_upd PRIMARY KEY (seq_id),
  CONSTRAINT observations FOREIGN KEY (obeservation_seq_id)
      REFERENCES gnt.observations_upd (seq_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);






