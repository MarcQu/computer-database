package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class ComputerFactory extends DAOFactory {
	/**
	 * ComputerFactory contient les méthodes spécifiques à la table computer.
	 * @throws SQLException
	 */
	public ComputerFactory() throws SQLException {
		super();
	}

	/**
	 * Liste les ordinateurs contenus dans la table computer.
	 * @param champs les champs de la table à afficher
	 * @throws SQLException
	 */
	public void listComputers(ArrayList<String> champs) throws SQLException {
		Statement stmt = super.conn.createStatement();
		Scanner scanner = new Scanner(System.in);
		String query = "SELECT ";
		for (int i = 0; i<champs.size() - 1; i++) {
			query += champs.get(i) + ", ";
		}
		query += champs.get(champs.size() - 1) + " FROM computer";
		ResultSet rs = stmt.executeQuery(query);
		int nombre = 0;
        while (rs.next()) {
        	if(nombre == 10) {
     		   System.out.println("Appuyer sur \"ENTRÉE\" pour continuer");
    		   scanner.nextLine();
    		   nombre = 0;
        	}
    		String ligne = "computer : ";
    		for (int i = 0; i<champs.size() - 1; i++) {
    			if(rs.getString(champs.get(i)) != null) {
                    ligne += rs.getString(champs.get(i)) + " ";
    			}
    		}
    		ligne += rs.getString(champs.get(champs.size() - 1));
    		System.out.println(ligne);
    		nombre++;
        }
	}
	
	/**
	 * Affiche les informations d'un ordinateur contenu dans la table computer.
	 * @param numero l'id de l'ordinateur à afficher
	 * @throws SQLException
	 */
	public void showComputerDetails(int numero) throws SQLException {
		Statement stmt = super.conn.createStatement();			
		String query = "SELECT * FROM computer WHERE id = " + numero;
		ResultSet rs = stmt.executeQuery(query);
		String[] champs = {"id", "name", "introduced", "discontinued", "company_id"};
		while ( rs.next() ) {
			String ligne = "computer : ";
    		for (int i = 0; i<champs.length - 1; i++) {
    			if(rs.getString(champs[i]) != null) {
                    ligne += rs.getString(champs[i]) + " ";
    			}
    		}
    		ligne += rs.getString(champs[champs.length - 1]);
    		System.out.println(ligne);
        }
	}
	
	/** 
	 * Ajoute un ordinateur dans la table computer.
	 * @param name le nom de l'ordinateur à ajouter
	 * @param introduced la date d'introduction de l'ordinateur à ajouter
	 * @param discontinued la date d'interruption de l'ordinateur à ajouter
	 * @param companyId l'id de la companie de l'ordinateur à ajouter
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyId) throws SQLException, IllegalArgumentException {
		String query = "INSERT INTO computer(name, introduced, discontinued, company_id) values(?, ?, ?, ?)";
		PreparedStatement stmt = super.conn.prepareStatement(query);
		if ("".equals(name)) {
			stmt.setString(1, null);
		} else {
			stmt.setString(1, name);
		}
		if ("".equals(introduced)) {
			stmt.setTimestamp(2, null);
		} else {
			stmt.setTimestamp(2, Timestamp.valueOf(introduced));
		}
		if ("".equals(discontinued)) {
			stmt.setTimestamp(3, null);
		} else {
			stmt.setTimestamp(3, Timestamp.valueOf(discontinued));
		}
		if ("".equals(companyId)) {
			stmt.setString(4, null);
		} else {
			stmt.setInt(4, Integer.parseInt(companyId));
		}
		stmt.executeUpdate();
	}
	
	/** 
	 * Met à jour un ordinateur dans la table computer.
	 * @param id l'id de l'ordinateur à mettre à jour
	 * @param name le nom de l'ordinateur à mettre à jour
	 * @param introduced la date d'introduction de l'ordinateur à mettre à jour
	 * @param discontinued la date d'interruption de l'ordinateur à mettre à jour
	 * @param companyId l'id de la companie de l'ordinateur à mettre à jour
	 * @param champs les champs qui sont prises en compte par la mise à jour
	 * @throws SQLException
	 */
	public void updateComputer(String id, String name, String introduced, String discontinued, String companyId, ArrayList<String> champs) throws SQLException {
		String query = "UPDATE computer SET ";
		for (int i = 0; i<champs.size() - 1; i++) {
			query += champs.get(i) + " = ?, ";
		}
		query += champs.get(champs.size() - 1) + " = ? WHERE id = ?";
		PreparedStatement stmt = super.conn.prepareStatement(query);
		for (int i = 0; i<champs.size(); i++) {
			switch(champs.get(i)) {
				case "name" :
					if ("".equals(name)) {
						stmt.setString(i + 1, null);
					} else {
						stmt.setString(i + 1, name);
					}
					break;
				case "introduced" :
					if ("".equals(introduced)) {
						stmt.setTimestamp(i + 1, null);
					} else {
						stmt.setTimestamp(i + 1, Timestamp.valueOf(introduced));
					}
					break;
				case "discontinued" :
					if ("".equals(discontinued)) {
						stmt.setTimestamp(i + 1, null);
					} else {
						stmt.setTimestamp(i + 1, Timestamp.valueOf(discontinued));
					}
					break;				
				case "company_id" :
					if ("".equals(companyId)) {
						stmt.setString(i + 1, null);
					} else {
						stmt.setInt(i + 1, Integer.parseInt(companyId));
					}
					break;
				default :
					break;
			}
		}
		stmt.setInt(champs.size() + 1, Integer.parseInt(id));
		stmt.executeUpdate();
	}
	
	/** 
	 * Supprime un ordinateur de la table computer.
	 * @param id l'id de l'ordinateur à supprimer
	 * @param name le nom de l'ordinateur à supprimer
	 * @param introduced la date d'introduction de l'ordinateur à supprimer
	 * @param discontinued la date d'interruption de l'ordinateur à supprimer
	 * @param companyId l'id de la companie de l'ordinateur à supprimer
	 * @param champs les champs qui sont prises en compte par la suppression
	 * @throws SQLException
	 */
	public void deleteComputer(String id, String name, String introduced, String discontinued, String companyId, ArrayList<String> champs) throws SQLException {
		String query = "DELETE FROM computer WHERE ";
		for (int i = 0; i<champs.size() - 1; i++) {
			query += champs.get(i) + " = ? AND ";
		}
		query += champs.get(champs.size() - 1) + " = ?";
		PreparedStatement stmt = super.conn.prepareStatement(query);
		for (int i = 0; i<champs.size(); i++) {
			switch(champs.get(i)) {
				case "id" :
					if ("".equals(id)) {
						stmt.setString(i + 1, null);
					} else {
						stmt.setInt(i + 1, Integer.parseInt(id));
					}
					break;
				case "name" :
					if ("".equals(name)) {
						stmt.setString(i + 1, null);
					} else {
						stmt.setString(i + 1, name);
					}
					break;
				case "introduced" :
					if ("".equals(introduced)) {
						stmt.setTimestamp(i + 1, null);
					} else {
						stmt.setTimestamp(i + 1, Timestamp.valueOf(introduced));
					}
					break;
				case "discontinued" :
					if ("".equals(discontinued)) {
						stmt.setTimestamp(i + 1, null);
					} else {
						stmt.setTimestamp(i + 1, Timestamp.valueOf(discontinued));
					}
					break;				
				case "company_id" :
					if ("".equals(companyId)) {
						stmt.setString(i + 1, null);
					} else {
						stmt.setInt(i + 1, Integer.parseInt(companyId));
					}
					break;
				default :
					break;
			}
		}
		stmt.executeUpdate();
	}
}
