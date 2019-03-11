package shs.server;

public class DataSource {
	// TODO What's the point of this class?

	public void DateSource() {
		DataConfig.getInstanceConfig();
		@SuppressWarnings("unused")
		JDBCConnectionPool connectionPool = new JDBCConnectionPool();
	}
}
