/**
 * 
 */
package com.scr.jobs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.jobs.response.ExecuteInsertReaponse;
import com.scr.jobs.response.InsertQueriesResponse;
import com.scr.message.response.ResponseStatus;
import com.scr.model.Facility;
import com.scr.model.SchedulerJob;
import com.scr.model.User;
import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.services.FacilityService;
import com.scr.services.UserDefualtFacConsIndEtcService;
import com.scr.services.UserServices;
import com.scr.util.CloseJDBCObjects;
import com.scr.util.Constants;
import com.scr.util.Helper;

/**
 * @author vt1056
 *
 */
@Component
public class CommonUtility {

	private static final Logger logger = Logger.getLogger(CommonUtility.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserServices userServices;
	
	@Autowired
	private UserDefualtFacConsIndEtcService  userDefualtFacConsIndEtcService;

	@Autowired
	private FacilityService facilityService;

	private CloseJDBCObjects closeJDBCObjects = new CloseJDBCObjects();
	
	public InsertQueriesResponse getTableNamesWithInsertionQuery(String host, String port, String dbname,
			String user, String password, String fromDate, String tempSchema, String divisionSchema, String schema, String timestamp, String operationType) {
		InsertQueriesResponse response = new InsertQueriesResponse();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		//Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		Timestamp currentTimeStamp = Helper.currentTimeStamp();
		logger.info("***currentTimeStamp***"+currentTimeStamp);
		try {
			LinkedHashMap<String, String> insertQueries = new LinkedHashMap<String, String>();
			String query = "select tab_name,"
					+ "	 'INSERT INTO "+ tempSchema +".'||c.tab_name|| case when create_update_delete = 'CREATE' then '_cre'"
					+ "	when create_update_delete = 'DELETE' then '_del'"
					+ "	when create_update_delete ='UPDATE' then '_upd' end ||"
					+ " ' ('||list_col||' ) '|| "
					+ "	' select ' || list_col ||" + "	' FROM dblink(' || '''dbname=' || '" + dbname
					+ "' ||' port=' " + "	|| '" + port + "' ||' host='|| '" + host + "' ||' user='|| '" + user
					+ "' ||' password='|| '" + password + "' || ''', '"
					+ "	||'''select ' || list_col ||' FROM '|| c.tab_name || ' where "+ timestamp +" > ''''"
					+ fromDate + "'''' and "+ timestamp +" '|| ' <= ''''"+ currentTimeStamp +" '''''"						
					+ "	||''') as t('||list_col_data_type||');'  as query" + "" + "" + "	from "
					+ "	(select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , "
					+ "		string_agg(column_name ||' '||" + "	  case when data_type = 'integer' then 'INT'"
					+ "			 when data_type = 'character varying' then 'VARCHAR'"
					+ "			 when data_type = 'numeric' then 'numeric(18,6)'"
					+ "			 when data_type = 'time without time zone' then 'DATETIME'"
					+ "			 when data_type = 'time with time zone' then 'DATETIME'"
					+ "			 when data_type = 'timestamp without time zone' then 'timestamp'"
					+ "			 when data_type = 'timestamp with time zone' then 'timestamp'"				
					+ "			else data_type end, ', ') list_col_data_type , table_name     "
					+ "	from ( select isc.table_schema, isc.table_name, isc.column_name, isc.data_type " 
					+ " from information_schema.columns isc, div_extraction_table_columns req_col "  
					+ " where req_col.table_name = isc.table_name " 
					+ " and req_col.column_name = isc.column_name "
					+ " and upper(req_col.active) = 'YES' "
					+ " and isc.table_schema = 'public' ) inf "
					+ " where table_schema = 'public' and column_name not in ('data_div', 'id') "
					+ "	group by table_name ) a,"
					+ "	( select  creation_order, tab_name , create_update_delete from  public.job_sch_activity_details  , public.entity_dependency"
					+ " where upper(table_name) = upper(tab_name) "
					+ "and create_update_delete= '"+ operationType +"'  " + "	 ) c "					
					+ "	where upper(c.tab_name) = upper(a.table_name) order by creation_order";			
			con = dataSource.getConnection();
			logger.info("*** divisions to temp insert query*****"+query);
			psPreparedStatement = con.prepareStatement(query);
			resultSet = psPreparedStatement.executeQuery();
			while (resultSet != null && resultSet.next()) {
				insertQueries.put(resultSet.getString("tab_name"), resultSet.getString("query"));
			}
			response.setInsertQueries(insertQueries);
			response.setCode(Constants.SUCCESS_CODE);			
		} catch (SQLException sqle) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+sqle.getMessage());
			logger.info(" >>> ERROR " + sqle.getMessage());
		} catch (Exception e) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
			logger.info(" >>> ERROR " + e.getMessage());
		} finally {
			closeJDBCObjects.releaseResouces(con,psPreparedStatement, resultSet);
		}
		return response;
	}

	

	public InsertQueriesResponse getTableNamesWithDeleteQuery(String host, String port, String dbname, String user,
			String password, String fromDate, String tempSchema, String divisionSchema, String divisionSchema2,
			String timestamp, String operationType) {
		InsertQueriesResponse response = new InsertQueriesResponse();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		Timestamp currentTimeStamp = Helper.currentTimeStamp();
		try {
			LinkedHashMap<String, String> insertQueries = new LinkedHashMap<String, String>();
			String query = "select tab_name," + 
					"	 ' INSERT INTO "+tempSchema+".'||c.tab_name|| '_del'||						 " + 
					"	' ( '||list_col|| ',deleted_date_time' || ' ) '||" + 
					"	' select ' || list_col || ',deleted_date_time' ||" + 
					"	 ' FROM dblink(' || '''dbname=' || '"+ dbname +"' ||' port=' " + 
					"	|| '"+ port +"' || ' host='|| '"+ host +"' ||' user='|| '"+user+"' ||' password='||'"+password+"' || ''', '" + 
					"	||'''select ' || list_col || ',deleted_date_time' ||' FROM '|| 'del_his.' || c.tab_name || '_del_his' || ' where deleted_date_time > '''|| '''"+fromDate+"'''  ||''' and deleted_date_time '|| ' <= ''''"+ currentTimeStamp +" '''''"+ 
					"	||''') as t('||list_col_data_type|| ',deleted_date_time timestamp with time zone ' || ' );' " + 
					"	as query from " + 
					"	(select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , " + 
					"		string_agg(column_name ||' '||" + 
					"		    case when data_type = 'integer' then 'INT'" + 
					"			 when data_type = 'character varying' then 'VARCHAR'" + 
					"			 when data_type = 'numeric' then 'INT'" + 
					"			 when data_type = 'time without time zone' then 'DATETIME'" + 
					"			 when data_type = 'time with time zone' then 'DATETIME'" + 
					"			else data_type end, ', ') list_col_data_type , table_name     " + 
					"	from ( select isc.table_schema, isc.table_name, isc.column_name, isc.data_type " + 
					" from information_schema.columns isc, div_extraction_table_columns req_col " + 
					" where req_col.table_name = isc.table_name " + 
					" and req_col.column_name = isc.column_name " +
					" and upper(req_col.active) = 'YES' " +
					" and isc.table_schema = 'public' ) inf"
					+ " where table_schema ='public' AND column_name not in ('data_div', 'id') " + 
					"	group by table_name) a,	"  
					+ "	( select  creation_order, tab_name , create_update_delete from  public.job_sch_activity_details  , public.entity_dependency"
					+ " where upper(table_name) = upper(tab_name) "
					+ " and upper(active) = 'YES' 				"
					+ " and create_update_delete= '"+ operationType +"'  " + "	 ) c "
					+ "	where upper(c.tab_name) = upper(a.table_name)"
					+ "and create_update_delete ='DELETE'  order by creation_order";
					/*"	 (select tab_name , create_update_delete from  public.job_sch_activity_details" + 
					"		where" + 
					"	 create_update_delete = 'DELETE'" + 
					"	 ) c " + 
					"	where c.tab_name = a.table_name" + 
					"	and create_update_delete ='DELETE'";*/			
			con = dataSource.getConnection();			
			psPreparedStatement = con.prepareStatement(query);
			resultSet = psPreparedStatement.executeQuery();
			while (resultSet != null && resultSet.next()) {
				insertQueries.put(resultSet.getString("tab_name"), resultSet.getString("query"));
			}
			response.setInsertQueries(insertQueries);
			response.setCode(Constants.SUCCESS_CODE);			
		} catch (SQLException sqle) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+sqle.getMessage());
			logger.info(" >>> ERROR " + sqle.getMessage());
		} catch (Exception e) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
			logger.info(" >>> ERROR " + e.getMessage());
		} finally {
			closeJDBCObjects.releaseResouces(con,psPreparedStatement, resultSet);
		}
		return response;
	}
	
	public ExecuteInsertReaponse executeInsertQuery(String query) {
		ExecuteInsertReaponse response = new ExecuteInsertReaponse();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		try {
			con = dataSource.getConnection();
			psPreparedStatement = con.prepareStatement(query);
			response.setRecords(psPreparedStatement.executeUpdate());
			response.setStatus(Constants.JOB_SUCCESS_MESSAGE);
			response.setStatusCode(Constants.SUCCESS_CODE);
		}catch (SQLException sqle) {
			response.setStatus(Constants.JOB_FAILED_MESSAGE+" >>> "+sqle.getMessage());
			response.setStatusCode(Constants.FAILURE_CODE);
		} catch (Exception e) {
			logger.info(" >>> ERROR " + e.getMessage());
			response.setStatus(Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
			response.setStatusCode(Constants.FAILURE_CODE);
		} finally {
			closeJDBCObjects.releaseResouces(con,psPreparedStatement, null);
		}
		return response;
	}
	// To insert into zonal to staging 
	public InsertQueriesResponse findInsertionQueryZonalToTemp(String zonalSchema, String divisionSchema) {
		InsertQueriesResponse response = new InsertQueriesResponse();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		PreparedStatement mixedPreparedStatement = null;
		ResultSet resultSet = null;
		ResultSet mixtedResultSet = null;
		try {
			LinkedHashMap<String, String> insertQueries = new LinkedHashMap<String, String>();			
			/*String query = "select a.table_name as tab_name," + 
					"'INSERT INTO  "+ zonalSchema +".'||a.table_name|| ' ( '||list_col || '  '  " +
					"||  ',data_div  ) '|| " + 
					"' select ' || list_col || ' , ' || '''"+ divisionSchema +"'''|| ' FROM "+ divisionSchema +".'|| a.table_name || '_cre ; '" + 
					"as query from( " + 
					"select string_agg(''''||column_name||'''', ', ') || ' , ' || '''"+ divisionSchema +"''' list_col1 , " + 
					"	string_agg(column_name, ', ') " + 
					"	  list_col , " + 
					"table_name " + 
					"from information_schema.columns " + 
					"	where table_schema= '"+ zonalSchema +"' and column_name != 'data_div'" + 
					"group by table_name" + 
					") a;";*/
			
			String divisionTablesQuery = "select a.table_name as tab_name, " + 
					" 'INSERT INTO public.' ||a.table_name|| ' ( '||list_col ||' , ' || 'data_div , id' || ' ) '|| " + 
					"	' select ' || list_col || ' , ' || '''"+ divisionSchema +"'''|| ' , ' ||'nextVal('  || ''''|| table_name || '_id_seq' ||''')  FROM "+divisionSchema+".'|| a.table_name || '_cre ; ' " + 
					"	as query from " + 
					"	(" + 
					"	select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , " + 
					"			table_name     " + 
					"	 from ( select isc.table_schema, isc.table_name, isc.column_name, isc.data_type " + 
					" from information_schema.columns isc, div_extraction_table_columns req_col " + 
					" where req_col.table_name = isc.table_name " + 
					" and req_col.column_name = isc.column_name " +
					" and upper(req_col.active) = 'YES' " +
					" and isc.table_schema = 'public' ) inf " + 
					"	, job_sch_activity_details " + 
					"	where table_schema= 'public' " + 
					"	and table_scope ='DIVISION' " + 
					//"	and column_name != 'data_div' " +
					"	and column_name not in ('data_div','id') " +
					"	and tab_name = table_name " +
					"   and create_update_delete = 'CREATE'"+
					"   and upper(active) = 'YES' 				" +
					"	group by table_name " + 
					"	) a";
			logger.info("********division tables query********"+divisionTablesQuery);;
			con = dataSource.getConnection();
			psPreparedStatement = con.prepareStatement(divisionTablesQuery);
			resultSet = psPreparedStatement.executeQuery();
				while (resultSet != null && resultSet.next()) {
					insertQueries.put(resultSet.getString("tab_name"), resultSet.getString("query"));
				}
			/*String mixedTablesQuery = " select 	a.table_name as tab_name, " +
					" 'INSERT INTO zonal.'||a.table_name|| ' ( '||list_col ||' ,data_div) '|| " + 
					"	' select ' || dlist_col ||', '|| '''"+divisionSchema+"'''  || ' FROM "+divisionSchema+".'|| a.table_name||'_cre  d, zonal.'||a.table_name||' z ' || CONDITION ||' ; '" + 
					"	as query from " + 
					"	( " + 
					"	select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , " + 
					"	string_agg(''''||'d.'||column_name||'''', ', ') dlist_col1 , string_agg('d.'||column_name, ', ') dlist_col , " + 
					"			table_name     " + 
					"	from information_schema.columns inf" + 
					"	, job_sch_activity_details jbs" + 
					"	where table_schema= 'zonal' " + 
					"	and table_scope ='MIXED'" + 
					"	and tab_name = table_name" + 
					"	and create_update_delete = 'CREATE' " + 
					"	and column_name !='data_div' " + 
					"	group by table_name " + 
					"	) a " + 
					"	LEFT OUTER JOIN " + 
					"	( " + 
					"	select string_agg(where_clause , '  ')  condition, tab_name " + 
					"	from " + 
					"	( " + 
					"		select case when sno=1 then ' where z.' || unique_column ||' != '|| 'd.'|| unique_column " + 
					"			    when sno>1 then ' and z.' || unique_column ||' != '|| 'd.' || unique_column end as where_clause, tab_name, sno " + 
					"		FROM " + 
					"			(	" + 
					"			  select tab_name, unique_column ,row_number() over(partition by tab_name    ) sno --order by tab_name\r\n" + 
					"			from " + 
					"				( " + 
					"				SELECT " + 
					"				    tab_name ," + 
					"				    trim(regexp_split_to_table(mixed_entity_unique_cols, E';') ) unique_column " + 
					"				FROM job_sch_activity_details jsa " + 
					"				where table_scope ='MIXED' " + 
					"				and create_update_delete = 'CREATE' " + 
					"				and upper(active) = 'YES' " + 
					"				)a" + 
					"			)b" + 
					"	) F" + 
					"	GROUP BY TAB_NAME " + 
					"	) CON ON (CON.TAB_NAME = A.TABLE_NAME) ;";*/
			String 	mixedTablesQuery = "select 	a.table_name as tab_name, " + 
					"'INSERT INTO public.'||a.table_name|| ' ( '||list_col ||' ,data_div , id ) '|| 	" + 
					"' select ' || dlist_col ||', '|| '''"+divisionSchema+"'''  || ' , ' ||'nextVal('  || ''''|| table_name || '_id_seq' ||''')  FROM "+divisionSchema+".'|| a.table_name||'_cre  d where '" + 
					"|| d_concat_uniq || ' not in ' || ' ( select '|| z_concat_uniq || 'from public.'||a.table_name||' z ) ' " + 
					//"--|| CONDITION ||' ; '	" + 
					" as query from 	" + 
					"( " + 
					"select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , " + 
					"string_agg(''''||'d.'||column_name||'''', ', ') dlist_col1 , string_agg('d.'||column_name, ', ') dlist_col ," + 
					//"-- string_agg('d.'||column_name||' AS '||'''||'d.'||column_name||''', ', ') dlist_col_as ," + 
					"table_name     	" + 
					"from information_schema.columns inf	, job_sch_activity_details jbs	" + 
					"where table_schema= 'public' 	" + 
					"and table_scope ='MIXED'	" + 
					"and tab_name = table_name	" + 
					"and create_update_delete = 'CREATE' 	" + 
					"and column_name not in ('data_div','id')	" + 
					"group by table_name 	) a 	" + 
					"LEFT OUTER JOIN 	" + 
					"( 	" + 
					"select d_concat_uniq, z_concat_uniq ,tab_name " + 
					// "--string_agg(where_clause , '  ')  condition, tab_name 	" + 
					"from 	" + 
					"(" + 
					"select string_agg('case when d.'||unique_column||' is not null then '|| " + 
					"case when col_date_type ='coldatetime' then unique_column||'::date'  else  unique_column  end||" + 
					"' else ' || case when col_date_type ='coldatetime' then '''19000101''' else '''-NULL-''' end || ' end ','|| ') d_concat_uniq ," + 
					"" + 
					"string_agg('case when z.'||unique_column||' is not null then '|| " + 
					"case when col_date_type ='coldatetime' then unique_column||'::date'  else  unique_column  end||" + 
					"' else ' || case when col_date_type ='coldatetime' then '''19000101''' else '''-NULL-''' end || ' end ','|| ') z_concat_uniq " + 
					", tab_name " + 
					" FROM " + 
					"(" + 
					"select tab_name, col_date_type, unique_column ,row_number() over(partition by tab_name    ) sno "+
					//"--order by tab_name" + 
					"from 				" + 
					"( 				" + 
					"SELECT  tab_name ,trim(regexp_split_to_table(mixed_entity_unique_cols, E';') ) unique_column" + 
					" FROM job_sch_activity_details jsa 				" + 
					"where table_scope ='MIXED' 				" + 
					"and create_update_delete = 'CREATE' 				" + 
					"and upper(active) = 'YES' 				" + 
					")a," + 
					"(select case when data_type = 'date'or data_type = 'timestamp with time zone' or " + 
					"data_type = 'timestamp without time zone'  then 'coldatetime' else 'col_not_datetime' end as col_date_type, " + 
					"table_name, column_name" + 
					" from information_schema.columns " + 
					" )u " + 
					" where u.table_name = a.tab_name and u.column_name = a.unique_column " + 
					"" + 
					") b  " + 
					"GROUP BY TAB_NAME " + 
					" ) con_unq	" + 
					") CON ON (CON.TAB_NAME = A.TABLE_NAME) ;";
			
			
			logger.info("mixted tables query***"+mixedTablesQuery);;
			
			mixedPreparedStatement = con.prepareStatement(mixedTablesQuery);
			mixtedResultSet = mixedPreparedStatement.executeQuery();
			while (mixtedResultSet != null && mixtedResultSet.next() ) {
				insertQueries.put(mixtedResultSet.getString("tab_name"), mixtedResultSet.getString("query"));
			}
			response.setInsertQueries(insertQueries);
			response.setCode(Constants.SUCCESS_CODE);
		} catch (SQLException sqle) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+sqle.getMessage());
			logger.info(" >>> ERROR " + sqle.getMessage());
		} catch (Exception e) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
			logger.info(" >>> ERROR " + e.getMessage());
		} finally {
			closeJDBCObjects.releaseResouces(con,psPreparedStatement, resultSet);
			closeJDBCObjects.releaseResouces(con, mixedPreparedStatement, mixtedResultSet);
		}
		return response;
	}


	public InsertQueriesResponse findUpdateQueryZonalToTemp(String zonalSchema, String divisionSchema) {
		InsertQueriesResponse response = new InsertQueriesResponse();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			LinkedHashMap<String, String> insertQueries = new LinkedHashMap<String, String>();					
			String	query = " select p_table_name as tab_name, upstmt1 ||'  '||up_condition || ' ; '" + 
						" as query from" + 
						" (select  'update public.' || cols.p_table_name ||' SET ' ||" + 
						" string_agg(col , ' , ') ||  ', ' ||  'data_div = ' || '''"+divisionSchema+"''' || ' FROM '|| '"+divisionSchema+".'|| p_table_name || '_upd'  upstmt1 ,  p_table_name" + 
						" from " + 
						" ( select  P.column_name ||' = '||'"+divisionSchema+".' || " + 
						"	p.table_name || '_upd.'||s.column_name col," + 
						" p.table_name p_table_name, P.column_name P_column_name ,  " + 
						" p.ordinal_position,  S.table_name S_table_name , S.column_name s_column_name" + 
						" from" + 
						" (select column_name, table_name   ,   ordinal_position" + 
						" from information_schema.columns " + 
						" where table_schema='public'" + 
						" ) p, " + 
						" ( select column_name, table_name ,   ordinal_position     " + 
						" from information_schema.columns  " + 
						" where table_schema='"+divisionSchema+"'" + 
						" ) S " + 
						" where p.column_name = S.column_name" + 
						" and  P.table_name || '_upd'   = s.table_name 	" + 
						" ) cols " + 
						" group by p_table_name" + 
						" ) upst, " + 
						" (select string_agg(whereclause , '  ') up_condition, table_name" + 
						" from " + 
						" (select case when  position = 1 and key_column != 'data_div' then ' where "+zonalSchema+".'|| " + 
						" table_name ||'.'|| key_column ||' =  "+divisionSchema+".'|| " + 
						" table_name || '_upd.' ||key_column " +
						" when  position = 1 and key_column = 'data_div' then ' where"
						+ " "+zonalSchema+".'|| " + 
						" table_name ||'.'|| key_column ||' =  ' || '''"+divisionSchema+"'''"+
						//" when  position = 1 then ' where z.'|| key_column ||' =  s.'||key_column " + 
						" when  position > 1 and key_column != 'data_div' then ' and "+zonalSchema+".'|| " + 
						" table_name ||'.'|| key_column ||' =  "+divisionSchema+".'|| " + 
						" table_name || '_upd.' ||key_column " + 
						" when  position > 1 and key_column = 'data_div' then ' and"
						+ " "+zonalSchema+".'|| " + 
						" table_name ||'.'|| key_column ||' =  ' || '''"+divisionSchema+"'''"+			
						" end  as whereclause ,  table_name ,  position" + 
						" from " + 
						" (select kcu.table_schema, " + 
						" kcu.table_name," + 
						" tco.constraint_name," + 
						" kcu.ordinal_position as position," + 
						" kcu.column_name as key_column" + 
						" from information_schema.table_constraints tco" + 
						" join information_schema.key_column_usage kcu " + 
						"   on kcu.constraint_name = tco.constraint_name" + 
						"  and kcu.constraint_schema = tco.constraint_schema" + 
						" and kcu.constraint_name = tco.constraint_name" + 
						"	 where tco.constraint_type = 'UNIQUE'" +
						"  and tco.constraint_name like 'old_pk_%'"+
						"	and tco.table_schema='public'" + 
						" and kcu.table_name IN (select tab_name from job_sch_activity_details where  table_scope not in  ('APPLICATION','ZONAL') )"+
						"	order by kcu.table_schema," + 
						"	kcu.table_name," + 
						"	 position" + 
						") k" + 
						") wclause" + 
						" group by table_name" + 
						") w" + 
						" where   upst.p_table_name   = w.table_name  ;"; 
			
			logger.info("*** zonal update  query******"+query);
					
			con = dataSource.getConnection();			
			psPreparedStatement = con.prepareStatement(query);
			resultSet = psPreparedStatement.executeQuery();
			while (resultSet != null && resultSet.next()) {
				insertQueries.put(resultSet.getString("tab_name"), resultSet.getString("query"));
			}
			response.setInsertQueries(insertQueries);
			response.setCode(Constants.SUCCESS_CODE);
		} catch (SQLException sqle) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+sqle.getMessage());
			logger.info(" >>> ERROR " + sqle.getMessage());
		} catch (Exception e) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
			logger.info(" >>> ERROR " + e.getMessage());
		} finally {
			closeJDBCObjects.releaseResouces(con,psPreparedStatement, resultSet);
		}
		return response;
	}

	public InsertQueriesResponse findDeleteQueryTempToZonal(String zonalSchema, String divisionSchema) {
		InsertQueriesResponse response = new InsertQueriesResponse();
		Connection con = null;
		PreparedStatement psPreparedStatement = null;
		ResultSet resultSet = null;
		try {
			LinkedHashMap<String, String> insertQueries = new LinkedHashMap<String, String>();	
			String query = " select p_table_name as tab_name, " + 
					" 'delete FROM "+ zonalSchema +".' || p_table_name || ' z USING ( select * from '||" + 
					" ' "+divisionSchema+".'|| p_table_name || '_del ) s' ||" + 
					" up_condition " +
					//+ "|| ' and '" + 
					//" ||  ' z' || '.data_div = ' || '''"+divisionSchema+"'''|| ' ; '" + 
					" as query from" + 
					" (" + 
					" select  'delete "+zonalSchema+".' || cols.p_table_name ||' SET ' ||" + 
					" string_agg(col , ' , ') ||  ', ' ||  'data_div = ' || '''"+divisionSchema+"'''" + 
					" ||	' FROM '|| '"+divisionSchema+".'|| p_table_name || '_del.'  upstmt1 ,  p_table_name" + 
					" from " + 
					" ( " + 
					" select  P.column_name ||' = '||'gnt.' || " + 
					"	p.table_name || '_del.'||s.column_name col," + 
					" p.table_name p_table_name, P.column_name P_column_name ,  " + 
					" p.ordinal_position,  S.table_name S_table_name , S.column_name s_column_name" + 
					" from" + 
					" (" + 
					" select column_name, table_name   ,   ordinal_position" + 
					" from information_schema.columns " + 
					" where table_schema='"+zonalSchema+"'" + 
					" ) p, " + 
					" (" + 
					" select column_name, table_name ,   ordinal_position     " + 
					" from information_schema.columns  " + 
					" where table_schema='"+divisionSchema+"' " + 
					" ) S " + 
					" where p.column_name = S.column_name" + 
					" and  P.table_name || '_del'   = s.table_name " + 				
					" ) cols " + 
					" group by p_table_name" + 
					" ) upst, " + 
					" ( 					 " + 
					" select string_agg(whereclause , '  ') up_condition, table_name" + 
					" from " + 
					" (" + 
					
					/*when  position = 1 then ' where z.'|| key_column ||' =  s.'||key_column  
					when  position > 1 and key_column != 'data_div' then ' and z.' ||  key_column ||' =  s.'||  key_column  
					when  position > 1 and key_column = 'data_div' then ' and z.' || key_column 
					||' =  '|| '''gtl'''*/ 
					" select case " +
					" when  position = 1 and key_column != 'data_div' then ' where z.'|| key_column ||' =  s.'||key_column " + 
					" when  position = 1 and key_column = 'data_div' then ' where z.'|| key_column ||' =  ' || '''"+divisionSchema+"'''"+
					" when  position > 1 and key_column != 'data_div' then ' and z.'|| key_column ||' =  s.'|| key_column "+
					" when  position > 1 and key_column = 'data_div' then ' and z.'|| key_column ||' =  ' || '''"+divisionSchema+"'''"+
					/*" table_name|| '.' || key_column ||' =  s.'|| " + 
					" key_column " + 
					*/
					" end  as whereclause ,  table_name ,  position" + 
					" from" + 
					" (" + 
					" select kcu.table_schema," + 
					" kcu.table_name," + 
					" tco.constraint_name," + 
					" kcu.ordinal_position as position," + 
					" kcu.column_name as key_column" + 
					" from information_schema.table_constraints tco" + 
					" join information_schema.key_column_usage kcu " + 
					"   on kcu.constraint_name = tco.constraint_name" + 
					"  and kcu.constraint_schema = tco.constraint_schema" + 
					" and kcu.constraint_name = tco.constraint_name" + 
					"	 where tco.constraint_type = 'UNIQUE'" +
					"  and tco.constraint_name like 'old_pk_%' "+
					"	and tco.table_schema='"+zonalSchema+"'" + 
					" and kcu.table_name IN (select tab_name from job_sch_activity_details where  table_scope not in  ('APPLICATION','ZONAL') )" + 
					"	order by kcu.table_schema," + 
					"	kcu.table_name," + 
					"	 position" + 
					") k" + 
					") wclause" + 
					" group by table_name" + 
					") w" + 
					" where   upst.p_table_name   = w.table_name  ;";
			logger.info("***zonal delete query******"+query);
			con = dataSource.getConnection();
			psPreparedStatement = con.prepareStatement(query);
			resultSet = psPreparedStatement.executeQuery();
			while (resultSet != null && resultSet.next()) {
				insertQueries.put(resultSet.getString("tab_name"), resultSet.getString("query"));
			}
			response.setInsertQueries(insertQueries);
			response.setCode(Constants.SUCCESS_CODE);
		} catch (SQLException sqle) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+sqle.getMessage());
			logger.info(" >>> ERROR " + sqle.getMessage());
		} catch (Exception e) {
			response.setCode(Constants.FAILURE_CODE);
			response.setMessage(Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
			logger.info(" >>> ERROR " + e.getMessage());
		} finally {
			closeJDBCObjects.releaseResouces(con,psPreparedStatement, resultSet);
		}
		return response;
	}

	public ResponseStatus checkDBConnection(SchedulerJob job) {
		Connection conn = null;
		ResponseStatus status = new ResponseStatus();
				try {
					conn = DriverManager.getConnection("jdbc:postgresql://"+job.getRepository().getRepositoryIp()+":"+job.getRepository().getRepositoryPort()+"/"+job.getRepository().getRepositoryDbName(), job.getRepository().getRepositoryUser(), job.getRepository().getRepositoryPassword());
					status.setCode(Constants.SUCCESS_CODE);
				}catch (Exception e) {
					status.setCode(Constants.FAILURE_CODE);
					status.setMessage("ERROR >>> "+e);
				}finally {
					closeJDBCObjects.closeConnection(conn);
				}
		return status;
	}



	public ResponseStatus deleteExistingDivisionData(String tempSchema, String scheduledJobsOperationInsert) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;
		ResponseStatus status = new ResponseStatus();
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement("select deletion_order,table_name ,* from entity_dependency e, ( select distinct tab_name from job_sch_activity_details where table_scope ='DIVISION' and active = 'YES' ) a " + 
					" where tab_name = table_name order by 1");
			resultSet = preparedStatement.executeQuery();
			while (resultSet != null && resultSet.next()) {
				statement = conn.createStatement();
				logger.info("delete query::::"+"delete from " + tempSchema + "." + resultSet.getString("table_name")+ scheduledJobsOperationInsert);
				statement.execute("delete from " + tempSchema + "." + resultSet.getString("table_name")
						+ scheduledJobsOperationInsert);
				closeJDBCObjects.closeStatement(statement);
			}
			status.setCode(Constants.SUCCESS_CODE);
		} catch (Exception e) {
			status.setCode(Constants.FAILURE_CODE);
			status.setMessage("ERROR >>> " + e);
			logger.info("message:::"+e.getMessage());
		} finally {
			closeJDBCObjects.releaseResouces(conn, preparedStatement, resultSet);
		}
		return status;

	}



	public ResponseStatus deleteZonalTablesData(String divisionSchema, String zonalSchema) {
		ResponseStatus status = new ResponseStatus();
		Connection conn = null;
		Statement statement = null;
		try {
			conn = dataSource.getConnection();
			InsertQueriesResponse insertQueriesResponse = findDeleteQueryTempToZonal(zonalSchema, divisionSchema);
			for (Map.Entry<String, String> queries : insertQueriesResponse.getInsertQueries().entrySet()) {
				String tableName = "";
				try {
					tableName = queries.getKey();					
					statement = conn.createStatement();
					boolean response =	statement.execute("delete from "+zonalSchema+"."+tableName+" where data_div='"+divisionSchema+"'");
					System.out.println("Detele Table Status= "+response);
					closeJDBCObjects.closeStatement(statement);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			status.setCode(Constants.SUCCESS_CODE);
			status.setMessage(Constants.JOB_SUCCESS_MESSAGE);
		}catch (Exception e) {
			status.setCode(Constants.FAILURE_CODE);
			status.setMessage("ERROR >>> "+e.getMessage());
		}
		finally {
			closeJDBCObjects.releaseResouces(conn, null, null);
		}
		return status;
	}



	public List<Facility> findUserHierarchy(String user) {
		logger.info(" user name"+user);
		List<Facility> facilityList = new ArrayList<Facility>();
		Optional<UserDefualtFacConsIndEtc> userDefaultFacility =  userDefualtFacConsIndEtcService.findByUserLoginId(user);
		
		if (userDefaultFacility.isPresent()) {
			UserDefualtFacConsIndEtc userDefault = userDefaultFacility.get();
			logger.info("facility Id:::"+userDefault.getFacilityId());
			
			Optional<Facility> facility = facilityService.findByFacilityId(userDefault.getFacilityId());
			
			if (facility.isPresent()) {
				Facility currentFacility = facility.get();
				if(currentFacility.getDepotType() != null ){
					if("ZONE".equals(currentFacility.getDepotType())){
						List<Facility> zoneFacilityList = facilityService.findByZone(currentFacility.getFacilityName());
						facilityList.addAll(zoneFacilityList);
					}else if("DIV".equals(currentFacility.getDepotType())) {
						List<Facility> divFacilityList = facilityService.findByDivision(currentFacility.getFacilityName());
							facilityList.addAll(divFacilityList);
					}else if("SUBDIV".equals(currentFacility.getDepotType()) ) {
						List<Facility> subDivFacilityList = facilityService.findBySubDivision(currentFacility.getFacilityName());
							facilityList.addAll(subDivFacilityList);
					}else if("OHE".equals(currentFacility.getDepotType())) {
						facilityList.add(currentFacility);
					}else if("PSI".equals(currentFacility.getDepotType())) {
						List<Facility> psiFacilityList = facilityService.findByParentDepot(currentFacility.getFacilityName());
							facilityList.addAll(psiFacilityList);
					}else if("TRD".equals(currentFacility.getDepotType())) {
						List<Facility> trdFacilityList = facilityService.findByParentFacilityId(currentFacility.getFacilityName());
						facilityList.addAll(trdFacilityList);
					}else {
						facilityList.add(currentFacility);
					}
				}
			}
			logger.info("facilityList => "+facilityList.toString());
		}else {
			logger.info("*** default facility not assigned***");
		}
		return facilityList;
		/*Connection conn = null;
		CallableStatement cs = null;
		String result = null;
		try {
			conn = dataSource.getConnection();
			logger.info("***procedure execution ***");
			cs = conn.prepareCall("{ ? =  call user_func_location(?)}");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setString(2, user);
			cs.execute();
			result = cs.getString(1);
			logger.info("*** procedure execution end*** "+result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeJDBCObjects.releaseResouces(conn, null, null);
			try {
				cs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
	}



	@SuppressWarnings("resource")
	public void usersCreateUpdateDelete(SchedulerJob job) {
		// TODO Auto-generated method stub
		String schemaName = job.getRepository().getRepositoryCode();
		final String createQuery = "select user_login_id,current_password from "+schemaName+".user_login_cre;";
		final String updateQuery = "select user_login_id,current_password from "+schemaName+".user_login_upd;";
		final String deleteQuery = "select user_login_id,current_password from "+schemaName+".user_login_del;";
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(createQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<User> user = userServices.findByUserName(resultSet.getString("user_login_id"));
				if(user.isPresent()) {
					User updateUser = user.get();
					updateUser.setPassword(resultSet.getString("current_password"));
					updateUser.setModified_date(new Date(Calendar.getInstance().getTime().getTime()));
					userServices.saveUser(updateUser);
				}else {
					User newUser = new User();
					newUser.setUsername(resultSet.getString("user_login_id"));
					newUser.setPassword(resultSet.getString("current_password"));
					newUser.setCreated_date(new Date(Calendar.getInstance().getTime().getTime()));
					userServices.saveUser(newUser);
				}
			}
			
			preparedStatement = conn.prepareStatement(updateQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<User> user = userServices.findByUserName(resultSet.getString("user_login_id"));
				if(user.isPresent()) {
					User updateUser = user.get();
					updateUser.setPassword(resultSet.getString("current_password"));
					updateUser.setModified_date(new Date(Calendar.getInstance().getTime().getTime()));
					userServices.saveUser(updateUser);
				}
			}
			preparedStatement = conn.prepareStatement(deleteQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Optional<User> user = userServices.findByUserName(resultSet.getString("user_login_id"));
				if(user.isPresent()) {
					User updateUser = user.get();
					userServices.deleteById(updateUser.getId());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeJDBCObjects.releaseResouces(conn, preparedStatement, resultSet);
		}
		
		
	}


	

}