package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import model.Company;

public class CompanyFactory implements AutoCloseable {
	private Connection conn;
	private static CompanyFactory INSTANCE = null;
	/**
	 * CompanyFactory contient les méthodes spécifiques à la table company.
	 * @throws SQLException
	 */
	private CompanyFactory() throws SQLException {
		this.initConnexion();
	}
	
	/**
	 * M�thode qui retourne l'instance unique de la classe CompanyFactory.
	 * @return l'instance de la classe CompanyFactory
	 * @throws SQLException
	 */
    public static CompanyFactory getInstance() throws SQLException{           
        if (CompanyFactory.INSTANCE == null) {
        	CompanyFactory.INSTANCE = new CompanyFactory(); 
        }
        return CompanyFactory.INSTANCE;
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
		}		
	}
	
	/**
	 * Liste les companies contenues dans la table company.
	 * @param champs les champs de la table à afficher
	 * @param nombre le nombre de résultats à afficher
	 * @return retour la liste des resultats de la requète
	 * @throws SQLException
	 */
	public ArrayList<Company> listCompanies(int nombre, int offset, ArrayList<String> champs) throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		Statement stmt = this.conn.createStatement();
		Scanner scanner = new Scanner(System.in);
		String query = "SELECT ";
		for (int i = 0; i<champs.size() - 1; i++) {
			query += champs.get(i) + ", ";
		}
		query += champs.get(champs.size() - 1) + " FROM company LIMIT " + nombre + " OFFSET " + offset;
		ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
        	Company company = new Company();
    		for (int i = 0; i<champs.size(); i++) {
    			if(rs.getString(champs.get(i)) != null) {
        			switch(champs.get(i)) {
	    				case "id" :
	    					company.setId(Integer.parseInt(rs.getString(champs.get(i))));
	    					break;
	    				case "name" :
	    					company.setName(rs.getString(champs.get(i)));
	    					break;
	    				default :
	    					break;
        			}    				
    			}
    		}
    		companies.add(company);
        }
        return companies;
	}

	/**
	 * Liste toutes les companies contenues dans la table company.
	 * @param champs les champs de la table à afficher
	 * @return retour la liste des resultats de la requète
	 * @throws SQLException
	 */
	public ArrayList<Company> listCompaniesAll(ArrayList<String> champs) throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		Statement stmt = this.conn.createStatement();
		Scanner scanner = new Scanner(System.in);
		String query = "SELECT ";
		for (int i = 0; i<champs.size() - 1; i++) {
			query += champs.get(i) + ", ";
		}
		query += champs.get(champs.size() - 1) + " FROM company";
		ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
        	Company company = new Company();
    		for (int i = 0; i<champs.size(); i++) {
    			if(rs.getString(champs.get(i)) != null) {
        			switch(champs.get(i)) {
	    				case "id" :
	    					company.setId(Integer.parseInt(rs.getString(champs.get(i))));
	    					break;
	    				case "name" :
	    					company.setName(rs.getString(champs.get(i)));
	    					break;
	    				default :
	    					break;
        			}    				
    			}
    		}
    		companies.add(company);
        }
        return companies;
	}
	
	@Override
	public void close() throws Exception {
		this.conn.close();
	}
}
