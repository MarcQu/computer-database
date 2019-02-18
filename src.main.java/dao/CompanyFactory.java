package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CompanyFactory extends DAOFactory {
	/**
	 * CompanyFactory contient les méthodes spécifiques à la table company.
	 * @throws SQLException
	 */
	public CompanyFactory() throws SQLException {
		super();
	}
	
	/**
	 * Liste les companies contenues dans la table company.
	 * @param champs les champs de la table à afficher
	 * @throws SQLException
	 */
	public void listCompanies(ArrayList<String> champs) throws SQLException {
		Statement stmt = super.conn.createStatement();
		Scanner scanner = new Scanner(System.in);
		String query = "SELECT ";
		for (int i = 0; i<champs.size() - 1; i++) {
			query += champs.get(i) + ", ";
		}
		query += champs.get(champs.size() - 1) + " FROM company";
		ResultSet rs = stmt.executeQuery(query);
		int nombre = 0;
        while (rs.next()) {
        	if(nombre == 10) {
     		   System.out.println("Appuyer sur \"ENTRÉE\" pour continuer");
    		   scanner.nextLine();
    		   nombre = 0;
        	}
    		String ligne = "companie : ";
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
}
