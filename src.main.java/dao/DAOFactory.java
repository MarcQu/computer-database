package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ui.Interface;

public class DAOFactory {
	protected Connection conn;
	protected Logger logger;
	/**
	 * DAOFactory.
	 * @throws SQLException
	 */
	public DAOFactory() throws SQLException {
		this.initConnexion();
		this.logger = LoggerFactory.getLogger(DAOFactory.class);
	}
	
	/**
	 * Initialise la connexion à la BDD.
	 * @throws SQLException
	 */
	private void initConnexion() {
		String url = "jdbc:mysql://localhost:3306/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B1";
		Properties prp = new Properties();
		prp.put("user", "root");
		prp.put("password", "");
		try {
			this.conn = DriverManager.getConnection(url, prp);
		} catch (SQLException e) {
			e.printStackTrace();
			this.logger.error("Erreur lors de la connexion à la base de données");
		}		
	}
}
