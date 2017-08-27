package xiaohu.dbutils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
private Connection connection;
	
	public TransactionManager(Connection connection){
		this.connection=connection;
	}
	
	public void beginTransaction() throws SQLException{
		connection.setAutoCommit(false);

	}
	public void commitAndClose(){
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void rollbackAndClose(){
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
