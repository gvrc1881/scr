package com.scr.util;

import org.springframework.stereotype.Component;

@Component
public class DashboardQueries {
	
	public static final String MATERIAL_QTY_RECEIVED_AND_CONSUMED_IN_GIVEN_PERIOD_BY_DEPOT = "select div.zone , div, subdiv ,  product_id as material_desc ," + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom" + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join" + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID," + 
			"        uom.abbreviation," + 
			" sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			" sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			" ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE   F.FACILITY_NAME = ? AND" + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			" and p.quantity_uom_id = uom.uom_id " + 
			" and f.division = ? " + 
			" and p.product_id = ? " + 
			" and f.sub_division = ? " + 
			" and zone = ? " + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"Group by  PRODUCT_ID, abbreviation , division , subdiv, div, data_div , div.zone, sub_division" + 
			"        ORDER BY div.zone , div, subdiv , PRODUCT_ID";
	
	public static final String MATERIAL_QTY_ONH_AND_BY_DEPOT = "select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc ," + 
			"case when QOH is null then 0 else qoh end QOH ," + 
			"  abbreviation uom" + 
			"from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			"left outer join" + 
			"( " + 
			" SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, " + 
			"        uom.abbreviation," + 
			" ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE    F.FACILITY_NAME= ? " + 
			"	AND" + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			" and p.quantity_uom_id = uom.uom_id" + 
			" and f.division =  ? " + 
			" and p.product_id = ?" + 
			" and f.sub_division = ?" + 
			"  and zone =? " + 
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q" + 
			"on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"" + 
			"        ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID";
	
	public static final String DIVISION_QUERY = "select div.zone , div, product_id as material_desc , " + 
			" case when sum(QOH) is null then 0 else sum(QOH) end QOH ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " +
			" SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, " + 
			"        uom.abbreviation," + 
			"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE " + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1'" +
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" group by div.zone , div, product_id  ,  abbreviation " + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	String SUB_DIVISION_QUERY = "select div.zone , div, subdiv , product_id as material_desc , " + 
			"case when sum(QOH) is null then 0 else sum(QOH) end QOH ," + 
			"  abbreviation uom  " + 
			"from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			"left outer join " + 
			"(" + 
			"SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, --round(minimum_stock), " + 
			"        uom.abbreviation," + 
			"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE" + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1' " + 
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q" + 
			"on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"" + 
			"group by div.zone , div, subdiv , product_id ,  abbreviation   " + 
			"        ORDER BY div.zone , div, subdiv , product_id " ;
	
	
	String DEPOT_QUERY = "select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc , " + 
			" case when QOH is null then 0 else qoh end QOH ," + 
			"  abbreviation uom  " + 
			" from" + 
			" ( select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        II.PRODUCT_ID AS PRODUCT_ID, II.FACILITY_ID AS FACILITY_ID, " + 
			"        uom.abbreviation," + 
			"	ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)),3) as QOH," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        INVENTORY_ITEM II ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE  " + 
			"         P.PRODUCT_ID = II.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = II.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" +
			"	and p.product_id = '0076-1' " + 
			"        GROUP BY II.PRODUCT_ID,II.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"        ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID";
	
	String DTMR_QUERY = " select div.zone , div,  product_id as material_desc , " + 
			" case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			" case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			" case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" +
			"	and p.product_id = '0076-1' "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation , division , div, data_div , div.zone, sub_division" + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	String SUB_DIVISION_WISE_PERIOD_QUERY = "select div.zone , div, subdiv ,  product_id as material_desc , " + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"    FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE  " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1' " +  
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation , division , subdiv, div, data_div , div.zone, sub_division" + 
			"        ORDER BY div.zone , div, subdiv , PRODUCT_ID ";
	
	String DEPOT_WISE_PERIOD_QUERY = " select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc , " + 
			"case when received_qty is null then 0 else received_qty end received_qty ," + 
			"case when consumed_qty is null then 0 else consumed_qty end consumed_qty ," + 
			"case when qty_net_period_net_qty is null then 0 else qty_net_period_net_qty end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" left outer join " + 
			" ( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div" + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE   " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and p.product_id = '0076-1' " + 
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division" + 
			") q" + 
			"on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" " + 
			"        ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID ";
	
	
	public static String DIVISION_WISE_BY_PRODUCT = "select" + 
			"   div.zone," + 
			"   div," + 
			"   product_id as material_desc," + 
			"   case" + 
			"      when" + 
			"         sum(QOH) is null " + 
			"      then" + 
			"         0 " + 
			"      else" + 
			"         sum(QOH) " + 
			"   end" + 
			"   QOH , abbreviation uom " + 
			" ,  'QoH By Division of - '||product_id ||' - units '||abbreviation as header "+
			"from" + 
			"   (" + 
			"      select distinct" + 
			"         zone," + 
			"         data_div div," + 
			"         sub_division subdiv," + 
			"         facility_name depot_name," + 
			"         FACILITY_ID " + 
			"      from" + 
			"         facility " + 
			"   )" + 
			"   div " + 
			"   inner join" + 
			"      (" + 
			"         SELECT" + 
			"            II.PRODUCT_ID AS PRODUCT_ID," + 
			"            II.FACILITY_ID AS FACILITY_ID," + 
			"            uom.abbreviation," + 
			"            ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)), 3) as QOH," + 
			"            F.FACILITY_NAME AS FACILITY_NAME," + 
			"            f.sub_division," + 
			"            f.division," + 
			"            f.zone," + 
			"            f.data_div, f.description " + 
			"         FROM" + 
			"            INVENTORY_ITEM II," + 
			"            PRODUCT P," + 
			"            FACILITY F," + 
			"            uom " + 
			"         WHERE" + 
			"            P.PRODUCT_ID = II.PRODUCT_ID " + 
			"            AND F.FACILITY_ID = II.FACILITY_ID " + 
			"            and p.quantity_uom_id = uom.uom_id " + 
			"            and p.product_id = ? " + 
			"            and " + 
			"            (" + 
			"               Upper(f.Zone)" + 
			"            )" + 
			"            = Upper(?) " + 
			"         GROUP BY" + 
			"            II.PRODUCT_ID," + 
			"            II.FACILITY_ID," + 
			"            F.FACILITY_NAME," + 
			"            uom.abbreviation," + 
			"            f.division," + 
			"            f.data_div," + 
			"            f.zone," + 
			"            f.sub_division, f.description " + 
			"      )" + 
			"      q " + 
			"      on (div = q.data_div " + 
			"      and div.zone = q.zone " + 
			"      and subdiv = q.sub_division " + 
			"      and q.FACILITY_NAME = depot_name " + 
			"      and q.facility_id = div.FACILITY_ID ) " + 
			"group by" + 
			"   div.zone," + 
			"   div," + 
			"   product_id," + 
			"   abbreviation, description " + 
			" ORDER BY" + 
			"   div.zone," + 
			"   div," + 
			"   product_id";
	
	public static String SUB_DIVISION_WISE_BY_PRODUCT = "select" + 
			"    div.zone," + 
			"    div," + 
			"    subdiv," + 
			"    product_id as material_desc," + 
			"    case" + 
			"        when" + 
			"            sum(QOH) is null " + 
			"        then" + 
			"            0 " + 
			"        else" + 
			"            sum(QOH) " + 
			"    end" + 
			"    QOH , abbreviation uom " + 
			" ,  'QoH By Sub Division of - '||product_id ||' - units '||abbreviation as header "+
			"from" + 
			"    (" + 
			"        select distinct" + 
			"            zone," + 
			"            data_div div," + 
			"            sub_division subdiv," + 
			"            facility_name depot_name," + 
			"            FACILITY_ID " + 
			"        from" + 
			"            facility " + 
			"    )" + 
			"    div " + 
			"    inner join" + 
			"        (" + 
			"            SELECT" + 
			"                II.PRODUCT_ID AS PRODUCT_ID," + 
			"                II.FACILITY_ID AS FACILITY_ID," + 
			"                uom.abbreviation," + 
			"                ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)), 3) as QOH," + 
			"                F.FACILITY_NAME AS FACILITY_NAME," + 
			"                f.sub_division," + 
			"                f.division," + 
			"                f.zone," + 
			"                f.data_div, f.description " + 
			"            FROM" + 
			"                INVENTORY_ITEM II," + 
			"                PRODUCT P," + 
			"                FACILITY F," + 
			"                uom " + 
			"            WHERE" + 
			"                P.PRODUCT_ID = II.PRODUCT_ID " + 
			"                AND F.FACILITY_ID = II.FACILITY_ID " + 
			"                and p.quantity_uom_id = uom.uom_id " + 
			"                and " + 
			"                (" + 
			"                    Upper(f.division)" + 
			"                )" + 
			"                = ? " + 
			"                and " + 
			"                (" + 
			"                    Upper(f.Zone)" + 
			"                )" + 
			"                = ? " + 
			"                and p.product_id = ? " + 
			"            GROUP BY" + 
			"                II.PRODUCT_ID," + 
			"                II.FACILITY_ID," + 
			"                F.FACILITY_NAME," + 
			"                uom.abbreviation," + 
			"                f.division," + 
			"                f.data_div," + 
			"                f.zone," + 
			"                f.sub_division, f.description " + 
			"        )" + 
			"        q " + 
			"        on (div = q.data_div " + 
			"        and div.zone = q.zone " + 
			"        and subdiv = q.sub_division " + 
			"        and q.FACILITY_NAME = depot_name " + 
			"        and q.facility_id = div.FACILITY_ID ) " + 
			"group by" + 
			"    div.zone," + 
			"    div," + 
			"    subdiv," + 
			"    product_id," + 
			"    abbreviation, description " + 
			"ORDER BY" + 
			"    div.zone," + 
			"    div," + 
			"    subdiv," + 
			"    product_id";
	
	public static String DEPOT_WISE = "SELECT div.zone," + 
			"       div," + 
			"       subdiv," + 
			"       div.FACILITY_ID," + 
			"       depot_name," + 
			"       product_id AS material_desc," + 
			"       CASE" + 
			"           WHEN QOH IS NULL THEN 0" + 
			"           ELSE qoh" + 
			"       END QOH," + 
			"       abbreviation uom" + 
			" ,  'QoH By Depot of - '||product_id ||' - units '||abbreviation as header "+
			" FROM " + 
			"  (SELECT DISTINCT ZONE," + 
			"                   data_div div," + 
			"                   sub_division subdiv," + 
			"                   facility_name depot_name," + 
			"                   FACILITY_ID" + 
			"   FROM facility) div " + 
			" INNER JOIN" + 
			"  (SELECT II.PRODUCT_ID AS PRODUCT_ID," + 
			"          II.FACILITY_ID AS FACILITY_ID," + 
			" uom.abbreviation," + 
			" ROUND(SUM((II.QUANTITY_ON_HAND_TOTAL)), 3) AS QOH," + 
			" F.FACILITY_NAME AS FACILITY_NAME," + 
			" f.sub_division," + 
			" f.division," + 
			" f.zone," +  
			" f.data_div, f.description " + 
			"   FROM INVENTORY_ITEM II," + 
			"        PRODUCT P," + 
			"        FACILITY F," + 
			"        uom" + 
			"   WHERE P.PRODUCT_ID = II.PRODUCT_ID" + 
			"     AND F.FACILITY_ID = II.FACILITY_ID" + 
			"     AND p.quantity_uom_id = uom.uom_id" + 
			"     AND QUANTITY_ON_HAND_TOTAL IS NOT NULL" + 
			"     AND (Upper(f.Zone)) = ?" + 
			"     AND (Upper(f.division) =?)" + 
			"     AND CASE" + 
			"             WHEN upper(f.sub_division)='' THEN f.sub_division=upper(?)" + 
			"             ELSE f.sub_division IS NOT NULL" + 
			"         END" + 
			"     AND p.product_id = ?" + 
			"   GROUP BY II.PRODUCT_ID," + 
			"            II.FACILITY_ID," + 
			"            F.FACILITY_NAME," + 
			"            uom.abbreviation," + 
			"            f.division," + 
			"            f.data_div," + 
			"            f.zone," + 
			"            f.sub_division, f.description) q ON (div = q.data_div" + 
			"                                  AND div.zone = q.zone" + 
			"                                  AND subdiv = q.sub_division" + 
			"                                  AND q.FACILITY_NAME = depot_name" + 
			"                                  AND q.facility_id = div.FACILITY_ID)" + 
			" ORDER BY div.zone," + 
			"         div," + 
			"         subdiv," + 
			"         depot_name ," + 
			"         PRODUCT_ID";
	
	public static String DIVISION_WISE_WITH_PERIOD = "select div.zone , div,  product_id as material_desc , " + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" ,  'Materail received and consumed By Division of - '||product_id ||' - units '||abbreviation || ' from ' || ? " + 
			" ||' to '|| ? as header "+
			" from " + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" inner join " + 
			"( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div, f.description " + 
			"               FROM " + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom " + 
			"        WHERE    " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID " + 
			"        AND F.FACILITY_ID = d.FACILITY_ID " + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and (Upper(f.Zone)) = ? " + 
			"	and p.product_id = ? " + 
			"	and QUANTITY is not null " + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division, f.description " + 
			" ) q " + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation , division , div, data_div , div.zone, sub_division, description " + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	public static String SUB_DIVISION_WITH_PERIOD = "select div.zone , div,sub_division,  product_id as material_desc , " + 
			"case when sum(received_qty) is null then 0 else sum(received_qty) end received_qty ," + 
			"abs(case when sum(consumed_qty) is null then 0 else sum(consumed_qty) end ) consumed_qty ," + 
			"case when sum(qty_net_period_net_qty) is null then 0 else sum(qty_net_period_net_qty) end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" ,  'Materail received and consumed By Sub Division of - '||product_id ||' - units '||abbreviation || ' from ' || ? " + 
			" ||' to '|| ? as header "+			
			" from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div " + 
			" inner join" + 
			"( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div, f.description " + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE    " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	 and upper(zone) =upper(?)" + 
			"	and upper(f.division) = upper(?)" + 
			"	and p.product_id = ? " + 
			"	and QUANTITY is not null" + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division, f.description " + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			" Group by  PRODUCT_ID, abbreviation , division , div, data_div , div.zone, sub_division, description " + 
			"        ORDER BY div.zone , div,  PRODUCT_ID ";
	
	public static String DEPOT_WISE_WITH_PERIOD = "select div.zone , div, subdiv , div.FACILITY_ID , depot_name , product_id as material_desc , " + 
			"case when received_qty is null then 0 else received_qty end received_qty ," + 
			"case when consumed_qty is null then 0 else consumed_qty end consumed_qty ," + 
			"case when qty_net_period_net_qty is null then 0 else qty_net_period_net_qty end qty_net_period_net_qty ," + 
			"  abbreviation uom  " + 
			" ,  'Materail received and consumed By Depot of - '||product_id ||' - units '||abbreviation || ' from ' || ? " + 
			" ||' to '|| ? as header "+
			" from" + 
			" (select distinct zone, data_div div , sub_division subdiv , facility_name depot_name , FACILITY_ID  from facility ) div" + 
			" inner join " + 
			"( " + 
			" SELECT " + 
			"        d.PRODUCT_ID AS PRODUCT_ID, d.FACILITY_ID AS FACILITY_ID,  " + 
			"        uom.abbreviation," + 
			"	sum(case when ROUND(d.QUANTITY,3) < 0 then 0 else d.QUANTITY end ) as received_qty ," + 
			"	sum(case when ROUND(d.QUANTITY,3) > 0 then 0 else d.QUANTITY end ) as consumed_qty ," + 
			"	ROUND(SUM((d.QUANTITY)),3) qty_net_period_net_qty ," + 
			"       F.FACILITY_NAME AS FACILITY_NAME ,  f.sub_division , f.division, f.zone , f.data_div, f.description " + 
			"               FROM" + 
			"        dmtr d ," + 
			"       PRODUCT P," + 
			"        FACILITY F, uom" + 
			"        WHERE  " + 
			"         P.PRODUCT_ID = d.PRODUCT_ID" + 
			"        AND F.FACILITY_ID = d.FACILITY_ID" + 
			"	and p.quantity_uom_id = uom.uom_id" + 
			"	and upper(zone) = Upper(?) " + 
			"	and upper(f.division) = Upper(?)" + 
			"	and case when upper(f.sub_division)='' then f.sub_division=upper(?)" + 
			"			else f.sub_division is not null end" + 
			"	and p.product_id = ?" + 
			"	and QUANTITY is not null" + 
			" and transaction_date ::date >= ? ::date "+
			" and transaction_date :: date <= ? ::date  "+ 
			"        GROUP BY d.PRODUCT_ID,d.FACILITY_ID, F.FACILITY_NAME,uom.abbreviation , f.division ,  f.data_div , f.zone, f.sub_division, f.description " + 
			" ) q" + 
			" on (div = q.data_div and div.zone = q.zone and subdiv = q.sub_division and q.FACILITY_NAME = depot_name and q.facility_id = div.FACILITY_ID )" + 
			"    ORDER BY div.zone , div, subdiv , depot_name  , PRODUCT_ID ";
}
