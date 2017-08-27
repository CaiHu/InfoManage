package xiaohu.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	private static String Driver="oracle.jdbc.driver.OracleDriver";
	private static String Url="jdbc:oracle:thin:@localhost:1521:orcl";
	private static String userName="um";
	private static String passWord="um";
	
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
	static{
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Connection getConnection(){
		Connection conn=null;
		conn=threadLocal.get();
		try {
			if(null==conn||conn.isClosed()){
				conn=DriverManager.getConnection(Url,userName,passWord);
				threadLocal.set(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
