package interfaceGestion;


import connectionPool.JDBCConnectionPool;

public class Controller {

	private JDBCConnectionPool poolConnection; 

	public Controller(JDBCConnectionPool pool) {
		this.poolConnection = pool; 
	}

	public String connection(String identifiant, String password) {

		poolConnection.getConnection(); 



		String requeteID = "Select count(*) from login where identifiant=" + identifiant; 


		return "fkfkf"; 
	}

}
