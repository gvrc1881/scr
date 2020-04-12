package com.scr.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class CloseJDBCObjects {
	static Logger logger = Logger.getLogger(CloseJDBCObjects.class);
	
	public  void releaseResouces(final Connection connection,
			final PreparedStatement pstmt, final ResultSet resultSet) {
		closeResultSet(resultSet);
		closePreparedStatement(pstmt);		
		closeConnection(connection);
	}

	public static void closePreparedStatement(final PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (final SQLException e) {
				logger.error("Couldn't close the prepared statement ", e);
			}
		}
	}

	public  void closeConnection(final Connection connection) {
		if (connection != null) {
			try {
				connection.close();			
			} catch (final SQLException e) {
				logger.error("Couldn't close the connection", e);
			}
		}
	}

	public static void closeResultSet(final ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (final SQLException e) {
				logger.error("Couldn't close the ResultSet ", e);
			}
		}
	}	
	

	/**
	 * <p>
	 * Close the statement.
	 * </p>
	 * 
	 * @param statement
	 *            - JDBC Statemenr
	 */
	public void closeStatement(final Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (final SQLException e) {
				logger.error("Couldn't close the statement ", e);
			}
		}
	}
	
	
	/**
	 * <p>
	 * Close the callable statement.
	 * </p>
	 * 
	 * @param callable statement
	 *            - JDBC Statemenr
	 */
	public static void closeCallableStatement(final CallableStatement callstmt) {
		if (callstmt != null) {
			try {
				callstmt.close();
			} catch (final SQLException e) {
				logger.error("Couldn't close the CallableStatement ", e);
			}
		}
	}
	
}