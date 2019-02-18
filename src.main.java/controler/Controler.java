package controler;

import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ComputerFactory;
import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

public class Controler {
	private static Controler INSTANCE = null;
	private Logger logger;
	
	private Controler() {
		this.logger = LoggerFactory.getLogger(Controler.class);
	}
	/**
	 * Méthode qui retourne l'instance unique de la classe Controler.
	 * @return l'instance de la classe Controler
	 * @throws SQLException
	 */
    public static Controler getInstance() {           
        if (Controler.INSTANCE == null) {
        	Controler.INSTANCE = new Controler(); 
        }
        return Controler.INSTANCE;
    }
    
    /**
     * Retourne le logger de la factory.
     * @return logger 
     */
    public Logger getLogger() {
    	return this.logger;
    }
    
	/**
	 * Liste les companies contenues dans la table company.
	 * @param champs les champs de la table à afficher
	 * @return retour la liste des resultats de la requête
	 * @throws SQLException
	 */
    public ArrayList<Company> listCompanies(ArrayList<String> champs) throws SQLException {
    	return CompanyService.getInstance().listCompanies(champs);
    }
    
	/**
	 * Liste les ordinateurs contenus dans la table computer.
	 * @param champs les champs de la table à afficher
	 * @return retour la liste des resultats de la requête
	 * @throws SQLException
	 */
	public ArrayList<Computer> listComputers(ArrayList<String> champs) throws SQLException {
		return ComputerService.getInstance().listComputers(champs);
	}
	
	/**
	 * Affiche les informations d'un ordinateur contenu dans la table computer.
	 * @param numero l'id de l'ordinateur à afficher
	 * @return retour la liste des resultats de la requête
	 * @throws SQLException
	 */
	public ArrayList<Computer> showComputerDetails(int numero) throws SQLException {
		return ComputerService.getInstance().showComputerDetails(numero);
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
		ComputerService.getInstance().createComputer(name, introduced, discontinued, companyId);
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
		ComputerService.getInstance().updateComputer(id, name, introduced, discontinued, companyId, champs);
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
		ComputerService.getInstance().deleteComputer(id, name, introduced, discontinued, companyId, champs);
	}
}
