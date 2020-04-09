package com.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.scr.jobs.SchedulerStart;
import com.scr.model.DivisionsHistory;
import com.scr.util.Constants;
import com.scr.util.Helper;

public class TestByDBLink {
	private static final Logger logger = Logger.getLogger(TestByDBLink.class);

	String divisionDb = "TRD_MAS$";
	String divisionHost = "122.175.41.15";
	String divisionUser = "postgres";
	String divisionPassword = "postgres";
	String divisionPort = "5432";
	
	String divisionDb_test = "scr-ui";
	String divisionHost_test = "localhost";
	String divisionUser_test = "postgres";
	String divisionPassword_test = "srijay";
	String divisionPort_test = "5432";
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		    	 
        TestByDBLink testByDBLink = new TestByDBLink();
		//testByDBLink.testLanIps();
		//testByDBLink.localBDTest();
		
		//testByDBLink.lanDBTest();
        String forCreate = "created_tx_stamp";
        String forUpdate = "last_updated_tx_stamp";
        String operationTypes= "CREATE";
		testByDBLink.insertDataDivisionsToTempCreateUpdateDelete(forCreate, operationTypes);
       
		//testByDBLink.insertDataTempToZonal();
        //testByDBLink.testSchemaConnection();
	}

	private void insertDataTempToZonal() {
		Connection con = null;
		try {
			String[] divisionSchemas = {"div-guntakal", "div-guntur", "div-hyderabad", "div-nanded", "div-vijayawada"};
			con = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/scr-ui","postgres","srijay"); 	
			String fromDate = "2000-01-01 00:00:00+05:30";
			String zonal = "\"zonal\""+".";
			String select = "\"div-guntakal\""+".";
			
			
			
			for(String divisionSchema: divisionSchemas) {
				
				String InsertQuery = "select tab_name,"
						+ "	 'INSERT INTO "+ zonal +"'||c.tab_name|| case when create_update_delete = 'CREATE' then '_cre'"
						+ "	when create_update_delete = 'DELETE' then '_del'"
						+ "	when create_update_delete ='UPDATE' then '_upd' end ||"
						+ " ' ('||list_col||' ) '|| "
						+ "	' select ' || list_col ||" + "	' FROM dblink(' || '''dbname=' || '" + divisionDb_test
						+ "' ||' port=' " + "	|| '" + divisionPort_test + "' ||' host='|| '" + divisionHost_test + "' ||' user='|| '" + divisionUser_test
						+ "' ||' password='|| '" + divisionPassword_test + "' || ''', '"
						+ "	||'''select ' || list_col ||' FROM '|| "+select+"'||c.tab_name || ' where created_tx_stamp > ''''"
						+ fromDate + "'''' and created_tx_stamp '|| ' <= now()'"						
						+ "	||''') as t('||list_col_data_type||');'  as query" + "" + "" + "	from "
						+ "	(select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , "
						+ "		string_agg(column_name ||' '||" + "	  case when data_type = 'integer' then 'INT'"
						+ "			 when data_type = 'character varying' then 'VARCHAR'"
						+ "			 when data_type = 'numeric' then 'INT'"
						+ "			 when data_type = 'time without time zone' then 'DATETIME'"
						+ "			 when data_type = 'time with time zone' then 'DATETIME'"
						+ "			 when data_type = 'timestamp without time zone' then 'timestamp'"
						+ "			 when data_type = 'timestamp with time zone' then 'timestamp'"				
						+ "			else data_type end, ', ') list_col_data_type , table_name     "
						+ "	from information_schema.columns where table_schema = '"+divisionSchema+"'"
						+ "	group by table_name ) a,"
						+ "	( select   tab_name , create_update_delete from  public.job_sch_activity_details " + "	 ) c "					
						+ "	where upper(c.tab_name) = upper(a.table_name)";				
				
				
				con = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/scr-ui","postgres","srijay"); 		
				System.out.println(InsertQuery);
				PreparedStatement psPreparedStatement = con.prepareStatement(InsertQuery);
				ResultSet resultSet = psPreparedStatement.executeQuery();
				LinkedHashMap<String, String> opeationQuery = new LinkedHashMap<>();
				boolean divisionFlag = true;
				int successTablesCount = 0;
				int failedTablesCount = 0;
				try {					
					int count = 0;
					while (resultSet != null && resultSet.next()) {
						try {
						
						logger.info("\t\t\t **********************************************************************************");
						logger.info("\t\t\t DB Link Query ="+resultSet.getString("query"));
						logger.info("\t\t\t Table Name= "+resultSet.getString("tab_name"));
						
							
							opeationQuery.put(resultSet.getString("tab_name"), resultSet.getString("query"));
							psPreparedStatement = con.prepareStatement(resultSet.getString("query"));
							int records = psPreparedStatement.executeUpdate();
							logger.info("\t\t\t Records: " + records);

							logger.info("\t\t\t Status: " + Constants.JOB_SUCCESS_MESSAGE);
							successTablesCount += 1;
							 
						}
						catch (Exception e) {						
							logger.info("\t\t\t Status: " + Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
							divisionFlag = false;
							failedTablesCount += 1;
						}				
						logger.info("\t\t\t Saved data divisions history successfully for table "+resultSet.getString("tab_name"));
						logger.info("\t\t\t **********************************************************************************\n");
						//if(count ==10)
						//	System.exit(0);
						count++;
					}	
					logger.info("\t\t division flag "+ divisionFlag +"\n");
					logger.info("\t\t success tables count "+ successTablesCount +"\n");
					logger.info("\t\t failure tables count "+ failedTablesCount +"\n");
					logger.info("\t\t total tables count "+ (successTablesCount + failedTablesCount) +"\n");
					logger.info("\t\t **********************************************************************************\n");
					System.exit(0);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void testSchemaConnection() throws ClassNotFoundException, SQLException {
		
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scr-ui", "postgres", "srijay");
			System.out.println("Connected...");
			PreparedStatement psPreparedStatement = con.prepareStatement("select * from temp.allocation");
			ResultSet resultSet = psPreparedStatement.executeQuery();
			while(resultSet != null && resultSet.next()) {
				System.out.println("id: "+resultSet.getString(1));
			}
		
		} catch (SQLException e) {
			System.out.println(" >>> ERROR: " + e);
		} finally {
			if(con != null)
				con.close();
		}
	}

	private void insertDataDivisionsToTempCreateUpdateDelete(String timestamp, String operationTypes) throws SQLException {
		Connection con = null; 
		String fromDate = "2000-01-01 00:00:00+05:30";
		//String div_guntakal = "\"div-guntakal\""+".";
		//String div_guntur = "\"div-guntur\""+".";
		//String div_hyderabad = "\"div-hyderabad\""+".";
		//String div_nanded = "\"div-nanded\""+".";
		String div_vijayawada = "\"div-vijayawada\""+".";
		
		try {
			con = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/scr-ui","postgres","srijay");
			
			//update job_sch_activity_details create_update_delete column
			
			Statement statement = con.createStatement();
			int updatedRecords = statement.executeUpdate("UPDATE public.job_sch_activity_details SET create_update_delete='"+operationTypes+"'");
			logger.info("Updated Records: "+updatedRecords);
			
			String query = "select tab_name,"
					+ "	 'INSERT INTO "+ div_vijayawada +"'||c.tab_name|| case when create_update_delete = 'CREATE' then '_cre'"
					+ "	when create_update_delete = 'DELETE' then '_del'"
					+ "	when create_update_delete ='UPDATE' then '_upd' end ||"
					+ " ' ('||list_col||' ) '|| "
					+ "	' select ' || list_col ||" + "	' FROM dblink(' || '''dbname=' || '" + divisionDb
					+ "' ||' port=' " + "	|| '" + divisionPort + "' ||' host='|| '" + divisionHost + "' ||' user='|| '" + divisionUser
					+ "' ||' password='|| '" + divisionPassword + "' || ''', '"
					+ "	||'''select ' || list_col ||' FROM '|| c.tab_name || ' where "+ timestamp +" > ''''"
					+ fromDate + "'''' and "+ timestamp +" '|| ' <= now()'"						
					+ "	||''') as t('||list_col_data_type||');'  as query" + "" + "" + "	from "
					+ "	(select string_agg(''''||column_name||'''', ', ') list_col1 , string_agg(column_name, ', ') list_col , "
					+ "		string_agg(column_name ||' '||" + "	  case when data_type = 'integer' then 'INT'"
					+ "			 when data_type = 'character varying' then 'VARCHAR'"
					+ "			 when data_type = 'numeric' then 'INT'"
					+ "			 when data_type = 'time without time zone' then 'DATETIME'"
					+ "			 when data_type = 'time with time zone' then 'DATETIME'"
					+ "			 when data_type = 'timestamp without time zone' then 'timestamp'"
					+ "			 when data_type = 'timestamp with time zone' then 'timestamp'"				
					+ "			else data_type end, ', ') list_col_data_type , table_name     "
					+ "	from information_schema.columns where table_schema = 'zonal' "
					+ "	group by table_name ) a,"
					+ "	( select   tab_name , create_update_delete from  public.job_sch_activity_details " + "	 ) c "					
					+ "	where upper(c.tab_name) = upper(a.table_name)";
			 				
			PreparedStatement psPreparedStatement = con.prepareStatement(query);
			ResultSet resultSet = psPreparedStatement.executeQuery();
			LinkedHashMap<String, String> opeationQuery = new LinkedHashMap<>();
			boolean divisionFlag = true;
			int successTablesCount = 0;
			int failedTablesCount = 0;
			try {					
				int count = 0;
				while (resultSet != null && resultSet.next()) {
					try {
					
					logger.info("\t\t\t **********************************************************************************");
					logger.info("\t\t\t DB Link Query ="+resultSet.getString("query"));
					logger.info("\t\t\t Table Name= "+resultSet.getString("tab_name"));
					
						
						opeationQuery.put(resultSet.getString("tab_name"), resultSet.getString("query"));
						psPreparedStatement = con.prepareStatement(resultSet.getString("query"));
						int records = psPreparedStatement.executeUpdate();
						logger.info("\t\t\t Records: " + records);

						logger.info("\t\t\t Status: " + Constants.JOB_SUCCESS_MESSAGE);
						successTablesCount += 1;
						 
					}
					catch (Exception e) {						
						logger.info("\t\t\t Status: " + Constants.JOB_FAILED_MESSAGE+" >>> "+e.getMessage());
						divisionFlag = false;
						failedTablesCount += 1;
					}				
					logger.info("\t\t\t Saved data divisions history successfully for table "+resultSet.getString("tab_name"));
					logger.info("\t\t\t **********************************************************************************\n");
					//if(count ==10)
					//	System.exit(0);
					count++;
				}	
				logger.info("\t\t division flag "+ divisionFlag +"\n");
				logger.info("\t\t success tables count "+ successTablesCount +"\n");
				logger.info("\t\t failure tables count "+ failedTablesCount +"\n");
				logger.info("\t\t total tables count "+ (successTablesCount + failedTablesCount) +"\n");
				logger.info("\t\t **********************************************************************************\n");
			}catch (Exception e) {
				
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(con != null)
				con.close();
		}
	}

	private void lanDBTest() throws SQLException {
		Connection con = null; 
		try {
				
            con = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/divisions-temp","postgres","srijay"); 
            DatabaseMetaData md = con.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = md.getTables(null, null, "%", types);
            while (rs.next()) {
             // System.out.println(rs.getString("TABLE_NAME"));
              String tableName = rs.getString("TABLE_NAME");
              ResultSet rsColumns = null;
              DatabaseMetaData meta = con.getMetaData();
              rsColumns = meta.getColumns(null, null, rs.getString("TABLE_NAME"), null);
              String columnsWithDataTypes = "";
              while (rsColumns.next()) {                
                columnsWithDataTypes += rsColumns.getString("COLUMN_NAME")+" "+rsColumns.getString("TYPE_NAME")+",";
              }
              columnsWithDataTypes = columnsWithDataTypes.trim().substring(0, columnsWithDataTypes.trim().length() - 1);
              System.out.println("-----------------------------------------------------------------------");
              String query = " INSERT INTO "+tableName+" "
              		+ " SELECT * FROM "            	
              		+ " dblink('dbname="+ divisionDb +" port="+divisionPort+" host="+ divisionHost +" user="+ divisionUser +" password="+ divisionPassword +"', "
              		+ " 'SELECT * FROM "+ tableName +"')"
              		+ " AS t("+ columnsWithDataTypes +") ";
              		
              System.out.println("query: "+query);
              
              PreparedStatement psPreparedStatement = con.prepareStatement(query);
              int flag = psPreparedStatement.executeUpdate();
  				System.out.println("Inserted records count =" + flag);
  				System.exit(0);
            }
            
           
		 } catch (SQLException e) {
	            System.out.println(" >>> ERROR: " +e);
	        } finally {
	            con.close();            
	        }
	}

	
	
	private void localBDTest() throws SQLException, ClassNotFoundException {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scr-ui", "postgres", "srijay");
			System.out.println("Connected...");
			String query = "select t.table_name" + 
					" from information_schema.tables t" + 
					" where t.table_schema = 'temp'  " + 
					" and t.table_type = 'BASE TABLE' " + 
					" order by t.table_name";
			PreparedStatement psStatement = con.prepareStatement(query);
			ResultSet resultSet = psStatement.executeQuery();
			int count = 0;
			String insert = "";
			while(resultSet != null && resultSet.next()) {
				System.out.println(resultSet.getString(1));
				String tableName = resultSet.getString(1);
				System.out.println("tableName: " + tableName);
				insert += count + ",CREATE," + count + ",,Daily," + tableName + "\n";
				//if (count == 50)
				//	break;
				count++;
			}
			System.out.println("count: " + count);
			System.out.println(insert.trim());
			
			/*
			 * DatabaseMetaData md = con.getMetaData(); String[] types = { "TABLE" };
			 * ResultSet rs = md.getTables(null, null, "%", types); int count = 0; String
			 * insert = ""; while (rs.next()) { String tableName =
			 * rs.getString("TABLE_NAME"); System.out.println("tableName: " + tableName);
			 * insert += count + ",CREATE," + count + ",,Daily," + tableName + "\n"; if
			 * (count == 50) break; count++; } System.out.println("count: " + count);
			 * System.out.println(insert.trim());
			 */
			 
		
		} catch (SQLException e) {
			System.out.println(" >>> ERROR: " + e);
		} finally {
			if(con != null)
				con.close();
		}
	}
	

	private void testLanIps() throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scr-ui", "postgres", "srijay");
			PreparedStatement psPreparedStatement = con.prepareStatement("select * from job_sch_activity_details");
			ResultSet resultSet = psPreparedStatement.executeQuery();
			int count = 0;
			while(resultSet != null && resultSet.next()) {
				System.out.println(resultSet.getString("tab_name"));
				String tableName = resultSet.getString("tab_name");
				 DatabaseMetaData dbm = con.getMetaData();
				    ResultSet rs = dbm.getTables(null, null, tableName, null);
				   
				if (rs.next()) {
					System.out.println("Table exists");
					String sql = "drop table " + tableName;// getCreateQuery(tableName);// "CREATE TABLE " + tableName +
															// "("+ columnsWithDataTypes +")";
					System.out.println("sql: " + sql);
					stmt = con.createStatement();
					stmt.execute(sql);
					stmt.close();
					// if(count == 10)
					// System.exit(0);

				} else {
					System.out.println("Table does not exist");
					String sql = getCreateQuery(tableName);// "CREATE TABLE " + tableName + "("+ columnsWithDataTypes
															// +")";
					System.out.println("sql: " + sql);
					try {

						stmt = con.createStatement();
						stmt.executeUpdate(sql);
						stmt.close();

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				if(count == 50)
					break;
				    count++;
			}
			
			/*DatabaseMetaData md = con.getMetaData();
			String[] types = { "TABLE" };
			ResultSet rs = md.getTables(null, null, "%", types);
			while (rs.next()) {				
				String tableName = rs.getString("TABLE_NAME");
				ResultSet rsColumns = null;
				DatabaseMetaData meta = con.getMetaData();
				rsColumns = meta.getColumns(null, null, rs.getString("TABLE_NAME"), null);
				String columnsWithDataTypes = "";
				while (rsColumns.next()) {
					columnsWithDataTypes += rsColumns.getString("COLUMN_NAME") + " " + rsColumns.getString("TYPE_NAME")
							+ ",";
				}
				columnsWithDataTypes = columnsWithDataTypes.trim().substring(0,
						columnsWithDataTypes.trim().length() - 1);
				System.out.println("-----------------------------------------------------------------------");
				String query = " INSERT INTO " + tableName + " " + " SELECT * FROM " + " dblink('dbname=" + divisionDb
						+ " port=" + divisionPort + " host=" + divisionHost + " user=" + divisionUser + " password="
						+ divisionPassword + "', " + " 'SELECT * FROM " + tableName + "')" + " AS t("
						+ columnsWithDataTypes + ") ";

				System.out.println("query: " + query);

				PreparedStatement psPreparedStatement = con.prepareStatement(query);
				int flag = psPreparedStatement.executeUpdate();
				System.out.println("Inserted records count =" + flag);
				 System.exit(0);
			}*/

		} catch (SQLException e) {
			System.out.println(" >>> ERROR: " + e);
		} finally {
			con.close();
		}

	}

	private String getCreateQuery(String tableName) throws SQLException {
		String query = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:postgresql://"+divisionHost+":"+divisionPort+"/"+divisionDb, divisionUser, divisionPassword);
			
				ResultSet rsColumns = null;
				DatabaseMetaData meta = con.getMetaData();
				rsColumns = meta.getColumns(null, null, tableName, null);				
				while (rsColumns.next()) {
					query += rsColumns.getString("COLUMN_NAME") + " " + rsColumns.getString("TYPE_NAME")
							+ ",";
				}
				query = query.trim().substring(0,
						query.trim().length() - 1);
			
			
		}catch (Exception e) {
			
		}finally {
			if(con != null)
				con.close();
		}
		return "CREATE TABLE " + tableName + "("+ query +")"; //query;
	}
}
