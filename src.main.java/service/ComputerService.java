package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import dao.ComputerFactory;
import model.Company;
import model.Computer;

public class ComputerService {
	private static ComputerService INSTANCE = null;
	/**
	 * M�thode qui retourne l'instance unique de la classe ComputerService.
	 * @return l'instance de la classe ComputerService
	 * @throws SQLException
	 */
    public static ComputerService getInstance() {           
        if (ComputerService.INSTANCE == null) {
        	ComputerService.INSTANCE = new ComputerService(); 
        }
        return ComputerService.INSTANCE;
    }
    
	/**
	 * Liste les ordinateurs contenus dans la table computer.
	 * @param champs les champs de la table � afficher
	 * @throws SQLException
	 */
	public ArrayList<Computer> listComputers(ArrayList<String> champs) throws SQLException {
		return ComputerFactory.getInstance().listComputers(champs);
	}
	
	/**
	 * Affiche les informations d'un ordinateur contenu dans la table computer.
	 * @param numero l'id de l'ordinateur � afficher
	 * @return retour la liste des resultats de la requ�te
	 * @throws SQLException
	 */
	public ArrayList<Computer> showComputerDetails(int numero) throws SQLException {
		return ComputerFactory.getInstance().showComputerDetails(numero);
	}
	
	/** 
	 * Ajoute un ordinateur dans la table computer.
	 * @param name le nom de l'ordinateur � ajouter
	 * @param introduced la date d'introduction de l'ordinateur � ajouter
	 * @param discontinued la date d'interruption de l'ordinateur � ajouter
	 * @param companyId l'id de la companie de l'ordinateur � ajouter
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyId) throws SQLException, IllegalArgumentException {
		ComputerFactory.getInstance().createComputer(name, introduced, discontinued, companyId);
	}
	
	/** 
	 * Met � jour un ordinateur dans la table computer.
	 * @param id l'id de l'ordinateur � mettre � jour
	 * @param name le nom de l'ordinateur � mettre � jour
	 * @param introduced la date d'introduction de l'ordinateur � mettre � jour
	 * @param discontinued la date d'interruption de l'ordinateur � mettre � jour
	 * @param companyId l'id de la companie de l'ordinateur � mettre � jour
	 * @param champs les champs qui sont prises en compte par la mise � jour
	 * @throws SQLException
	 */
	public void updateComputer(String id, String name, String introduced, String discontinued, String companyId, ArrayList<String> champs) throws SQLException {
		ComputerFactory.getInstance().updateComputer(id, name, introduced, discontinued, companyId, champs);
	}
	
	/** 
	 * Supprime un ordinateur de la table computer.
	 * @param id l'id de l'ordinateur � supprimer
	 * @param name le nom de l'ordinateur � supprimer
	 * @param introduced la date d'introduction de l'ordinateur � supprimer
	 * @param discontinued la date d'interruption de l'ordinateur � supprimer
	 * @param companyId l'id de la companie de l'ordinateur � supprimer
	 * @param champs les champs qui sont prises en compte par la suppression
	 * @throws SQLException
	 */
	public void deleteComputer(String id, String name, String introduced, String discontinued, String companyId, ArrayList<String> champs) throws SQLException {
		ComputerFactory.getInstance().deleteComputer(id, name, introduced, discontinued, companyId, champs);
	}
}
