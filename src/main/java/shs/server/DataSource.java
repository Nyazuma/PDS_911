package shs.server;

public class DataSource {

	public void DateSource() {
		DataConfig.getInstanceConfig();
		@SuppressWarnings("unused")
		JDBCConnectionPool connectionPool = new JDBCConnectionPool();
	}
}
