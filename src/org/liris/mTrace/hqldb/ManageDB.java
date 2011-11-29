package org.liris.mTrace.hqldb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.liris.mTrace.tools.pojo.UserPojo;

public class ManageDB {

	private Connection connexion;
	 
	/**
	 * driver JDBC
	 */
	private String jdbcDriver = "org.hsqldb.jdbcDriver";
 
	/**
	 * file mode
	 */
	private String database = "jdbc:hsqldb:hsql://localhost/d3kodeDB";
 
	/**
	 * user login
	 */
	private String user = "d3kode";
 
	/**
	 * password
	 */
	private String password = "edok3d";
 
	/**
	 * initial connexion
	 */
	public void connexionDB() {
		try {
			// On commence par charger le driver JDBC d'HSQLDB
			Class.forName(jdbcDriver).newInstance();
		} catch (InstantiationException e) {
			System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
 
		try {
			connexion = DriverManager.getConnection(database, user, password);
			
			if(!existe("USERS")){
				String requestCreateTableUsers = "CREATE TABLE USERS ( idUser INTEGER IDENTITY, login VARCHAR(256), password VARCHAR(256))";
				executerUpdate(requestCreateTableUsers);
				
				String requestCreateTableUserRole = "CREATE TABLE USER_ROLE ( login VARCHAR(256), role_name VARCHAR(256))";
				executerUpdate(requestCreateTableUserRole);
				
				// Création des utilisateurs
				//expert,novice,stagiaire,tomcat,manager-gui,manager-script
				String requeteUser1 = "INSERT INTO users(login,password) VALUES('dino', 'cosmas')";
				String requeteUserRole1 = "INSERT INTO user_role(login,role_name) VALUES('dino', 'admin')";
				String requeteUserRole2 = "INSERT INTO user_role(login,role_name) VALUES('dino', 'expert')";
				String requeteUserRole3 = "INSERT INTO user_role(login,role_name) VALUES('dino', 'tomcat')";
				String requeteUserRole4 = "INSERT INTO user_role(login,role_name) VALUES('dino', 'manager-gui')";
				String requeteUserRole5 = "INSERT INTO user_role(login,role_name) VALUES('dino', 'manager-script')";
				String requeteUserRole6 = "INSERT INTO user_role(login,role_name) VALUES('dino', 'stagiaire')";
				
				String requeteUser2 = "INSERT INTO users(login,password) VALUES('olivier', 'champalle')";
				String requeteUser3 = "INSERT INTO users(login,password) VALUES('stagiaire', 'stagiaire')";
				
				executerUpdate(requeteUser1);
				executerUpdate(requeteUserRole1);
				executerUpdate(requeteUserRole2);
				executerUpdate(requeteUserRole3);
				executerUpdate(requeteUserRole4);
				executerUpdate(requeteUserRole5);
				executerUpdate(requeteUserRole6);
				executerUpdate(requeteUser2);
				executerUpdate(requeteUser3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	public ResultSet userRoles() throws SQLException{
		String request = "SELECT iduser,login,password, role_name FROM USERS left outer join USER_ROLE ON USERS.login = USER_ROLE.login ORDER BY login";
		return executerRequete(request);
	}
	
	private boolean existe(String nomTable) throws SQLException{
		   boolean existe;
		   DatabaseMetaData dmd = connexion.getMetaData();
		   ResultSet tables = dmd.getTables(connexion.getCatalog(),null,nomTable,null);
		   existe = tables.next();
		   tables.close();
		   return existe;
		}
	
	/**
	 * Stop HSQLDB
	 * 
	 * @throws SQLException
	 */
	public void arretDB() throws SQLException {
		Statement st = connexion.createStatement();
 
		// On envoie l'instruction pour arreter proprement HSQLDB
		st.execute("SHUTDOWN");
		// On ferme la connexion
		connexion.close(); // if there are no other open connection
 
	}
	
	/**
	 * Execute update request
	 * 
	 * @param requete
	 *            contient la requête SQL
	 * @throws SQLException
	 */
	public void executerUpdate(String requete) throws SQLException {
		Statement statement;
		statement = connexion.createStatement();
		statement.executeUpdate(requete);
	}
 
	/**
	 * Execute select request
	 * 
	 * @param requete
	 *            contient la requête SQL
	 * @throws SQLException
	 */
	public ResultSet executerRequete(String requete) throws SQLException {
		Statement statement;
		statement = connexion.createStatement();
		ResultSet resultat = statement.executeQuery(requete);
		return resultat;
	}

	public static List<UserPojo> buildList() {
		return new ArrayList<UserPojo>();
	}

//	public Integer getCustomersCount(List<UserPojo> myCustomers) {
//		int ret = 0;
//		try {
//			String request = "SELECT count(*) as 'nb' FROM USERS";
//			ResultSet nbUser = executerRequete(request);
//			
//			ret = nbUser.getInt("nb");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return ret;
//	}

	public static Integer addUser(UserPojo user2) {
		Integer id = null;
		try {
			ManageDB manageDB = new ManageDB();
			manageDB.connexionDB();
			String request = "insert into USERS (iduser,login,password) VALUES(null,'"+ user2.getLogin() + "','" + user2.getPassword() + "')";
			manageDB.executerUpdate(request);
			for (String role : user2.getRoles()) {
				String requeteUserRole = "INSERT INTO user_role(login,role_name) VALUES('"+ user2.getLogin() + "', '" + role + "')";
				manageDB.executerUpdate(requeteUserRole);
			}
			
			String requestMaxId = "SELECT max(iduser) as id FROM USERS";
			ResultSet resultSet = manageDB.executerRequete(requestMaxId);
			if(resultSet.next()){
				id = resultSet.getInt("id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public static void delUser(UserPojo user2) {
		try {
			ManageDB manageDB = new ManageDB();
			manageDB.connexionDB();
			for (String role : user2.getRoles()) {
				String requeteUserRole = "DELETE FROM user_role WHERE role_name like '"+role+"'";
				manageDB.executerUpdate(requeteUserRole);
			}
			String request = "delete from USERS WHERE login like '"+ user2.getLogin() + "'";
			manageDB.executerUpdate(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void editUser(UserPojo user2) {
		try {
			ManageDB manageDB = new ManageDB();
			manageDB.connexionDB();

			String requeteUserRole = "DELETE FROM user_role WHERE login like '"+user2.getLogin()+"'";
			manageDB.executerUpdate(requeteUserRole);

			
			for (String role : user2.getRoles()) {
				requeteUserRole = "INSERT INTO user_role(login,role_name) VALUES('"+ user2.getLogin() + "', '" + role + "')";
				manageDB.executerUpdate(requeteUserRole);
			}
			
			String request = "UPDATE users SET login='"+user2.getLogin()+"' , password='"+user2.getPassword()+"' WHERE IDUSER = "+ user2.getId();
			manageDB.executerUpdate(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
