package service;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.CompanyFactory;
import model.Company;

public class CompanyService {
	private static CompanyService INSTANCE = null;
	private CompanyService() {
	}
	
	/**
	 * M�thode qui retourne l'instance unique de la classe CompanyService.
	 * @return l'instance de la classe CompanyService
	 * @throws SQLException
	 */
    public static CompanyService getInstance() {           
        if (CompanyService.INSTANCE == null) {
        	CompanyService.INSTANCE = new CompanyService(); 
        }
        return CompanyService.INSTANCE;
    }
    
	/**
	 * Liste les companies contenues dans la table company.
	 * @param champs les champs de la table � afficher
	 * @return retour la liste des resultats de la requ�te
	 * @throws SQLException
	 */
    public ArrayList<Company> listCompanies(ArrayList<String> champs) throws SQLException {
    	return CompanyFactory.getInstance().listCompanies(champs);
    }
}
