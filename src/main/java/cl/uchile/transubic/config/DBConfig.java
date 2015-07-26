package cl.uchile.transubic.config;

import org.apache.commons.dbcp.BasicDataSource;

public class DBConfig {
	
	private DBConfig() {
		
	}
	
	public static BasicDataSource dataSourceTransubic() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/transubic");
		ds.setUsername("root");
		ds.setPassword("");
		return ds;
	}
	
}
