
CREATE OR REPLACE FUNCTION public.user_func_location(userid text)
  RETURNS text AS
$BODY$

DECLARE

               FIRST_LEVEL_RM_OF_UNIT TEXT;

               --NEXT_LEVEL_RM_OF_UNIT TEXT;

        UNIT_NAME TEXT;

               NO_OF_REPORTEES BIGINT;

               user_id text;

               N BIGINT;

               UNITNAMES TEXT;

               CUR_UNIT_NAMES CURSOR FOR SELECT UNIT_CODE FROM FUNCTIONAL_LOCATION_HIERARCHY flh_l where flh_l.head_login_id not in (select distinct flh_r.rm_login_id from FUNCTIONAL_LOCATION_HIERARCHY flh_r) order BY seq_id;

               UNIT_LIST  VARCHAR[] ;

BEGIN

 

               --UNIT_LIST='';

               RAISE NOTICE 'intitial -------UNIT_LIST-%', UNIT_LIST;

               SELECT COUNT(*) INTO NO_OF_REPORTEES FROM FUNCTIONAL_LOCATION_HIERARCHY WHERE RM_LOGIN_ID = USERID;

               RAISE NOTICE 'intitial -------UNIT_LIST-%', UNIT_LIST;

               IF  NO_OF_REPORTEES = 0 THEN  --USER HAS NO REPORTEES THEN HIS DEFAULT DEPOT/WH IS ONLY ONE UNIT

                              RAISE NOTICE 'IN LOOP UNIT NAME------- if no RM % -UNIT_NAME-%', N,UNITNAMES;

                              UNIT_LIST := array(

                       SELECT FACILITY_NAME 

                              FROM USER_DEFUALT_FAC_CONS_IND_ETC

                              WHERE FACILITY_TYPE_ID in ('WAREHOUSE', 'PLANT','SP', 'SSP', 'TSS')

                              AND USER_LOGIN_ID = USERID);

                              --UNIT_LIST= UNIT_LIST||UNIT_NAME||',';

                              RETURN UNIT_LIST;

               ELSE  -- GET THE LIST OF DEPOTS/WH COVERED TO USER DIRECTLY OR THROUGH THE REPORTEES

 

               RAISE NOTICE ' UNIT NAME------- For RM   % -',USERID ;

                              SELECT seq_id INTO user_id FROM FUNCTIONAL_LOCATION_HIERARCHY WHERE head_LOGIN_ID = USERID;

                              RAISE NOTICE 'user name id is ------- -% --', user_id;

                              UNIT_LIST := array(

                              WITH RECURSIVE nodes(seq_id,unit_code, unit_type, head_login_id, rm_seq_id ) AS

                              (

                              SELECT f1.seq_id, f1.unit_code,  f1.unit_type, f1.head_login_id, f1.rm_seq_id

                              FROM functional_location_hierarchy f1 WHERE f1.rm_seq_id::integer =  user_id::integer

                              UNION

                              SELECT f2.seq_id, f2.unit_code,  f2.unit_type, f2.head_login_id, f2.rm_seq_id

                              FROM functional_location_hierarchy f2, nodes f1 WHERE f2.rm_seq_id::integer = f1.seq_id::integer

                              )

                              SELECT unit_code FROM nodes  

                              order by rm_seq_id desc

                              );

                              RAISE NOTICE 'in loop-------UNIT_LIST-%', UNIT_LIST;

                             

                              RAISE NOTICE 'final --------NO OF LOCATIONS %  -- LIST OF LOCATIONS %', N, UNIT_LIST;

                              RETURN UNIT_LIST;

               END IF; 

               END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.user_func_location(text)
  OWNER TO postgres;