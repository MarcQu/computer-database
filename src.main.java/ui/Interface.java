package ui;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.CompanyFactory;
import dao.ComputerFactory;
import dao.DAOFactory;

public class Interface {
	/**
	 * Afficher l'interface avec le choix de la table et des commandes
	 * @param sc scanner pour les entrées de l'utilisateur
	 * @throws SQLException
	 */
	private void showInterface(Logger logger, Scanner sc) throws SQLException {
		// Boucle pour le menu principal
		fin : while(true) {
			System.out.println("Veuillez choisir une table :");
			System.out.println(" - company");
			System.out.println(" - computer");
			System.out.println(" - quitter : quitte l'interface");
			System.out.print("-> ");
			String table = sc.nextLine();
			// Switch pour les différents menus
			switch (table) {
				case "company" :
					System.out.println("Liste des commandes :");
					System.out.println(" - listCompanies : liste les companies");
					System.out.println(" - aide : réafficher les commandes");
					System.out.println(" - retour : retour au menu précédent");
					System.out.print("-> ");
					useCommandCompany(logger, sc);
					break;
				case "computer" :
					System.out.println("Liste des commandes :");
					System.out.println(" - listComputers : liste les ordinateurs");
					System.out.println(" - showComputerDetails : affiche les details d'un ordinateur");
					System.out.println(" - createComputer : ajoute un ordinateur dans la base de données");
					System.out.println(" - updateComputer : met à jour les informations d'un ordinateur");
					System.out.println(" - deleteComputer : supprime un ordinateur de la base de données");
					System.out.println(" - aide : réafficher les commandes");
					System.out.println(" - retour : retour au menu précédent");
					System.out.print("-> ");
					useCommandComputer(logger, sc);
					break;
				case "quitter" :
					System.out.println("Fermeture");
					break fin;
				default :
					System.out.println("Table inconnue");
					break;
			}
		}
	}
	
	/**
	 * Appelle une fonction de la dao company en fonction de la commande.
	 * @param scanner pour les entrées de l'utilisateur
	 * @throws SQLException
	 */
	private void useCommandCompany(Logger logger, Scanner sc) throws SQLException {
		CompanyFactory factory = new CompanyFactory();
		// Boucle pour le menu company
		retour : while(true) {
			String command = sc.nextLine();
			// Switch pour les commandes
			switch (command) {
				case "listCompanies" :
					champsEntries(logger, factory, sc);
					System.out.print("-> ");
				    logger.info("listCompanies");
					break;
				case "aide" :
					System.out.println("Liste des commandes :");
					System.out.println(" - listCompanies : liste les companies");
					System.out.println(" - aide : réafficher les commandes");
					System.out.println(" - retour : retour au menu précédent");
					System.out.print("-> ");
					break;
				case "retour" :
					System.out.println("retour");
					break retour;
				default :
					System.out.println("Commande inconnue");
					System.out.print("-> ");
					break;
			}
		}
	}
	
	/**
	 * Appelle une fonction de la dao computer en fonction de la commande.
	 * @param sc scanner pour les entrées de l'utilisateur
	 * @throws SQLException
	 */
	private void useCommandComputer(Logger logger, Scanner sc) throws SQLException {
		ComputerFactory factory = new ComputerFactory();
		// Boucle pour le menu computer
		retour : while(true) {
			String command = sc.nextLine();
			String id;
			String name;
			String introduced;
			String discontinued;
			String companyId;
			ArrayList<String> champs = new ArrayList<String>();
			// Switch pour les commandes
			switch (command) {
				case "listComputers" :
					champsEntries(logger, factory, sc);
					System.out.print("-> ");
				    logger.info("listComputers");
					break;
				case "showComputerDetails" :
					System.out.println("Entrer un id d'ordinateur : ");
					id = sc.nextLine();
					//Boucle qui vérifie que l'id est un entier ou vide
					while(!id.matches("^\\d+$")) {
						System.out.println("Entrer un entier valide : ");
						id = sc.nextLine();
					}
					if(!"".equals(id)) {
						factory.showComputerDetails(Integer.parseInt(id));
					}
					System.out.print("-> ");
				    logger.info("showComputerDetails");
					break;
				case "createComputer" :
					System.out.println("Entrer un nom d'ordinateur : ");
					name = sc.nextLine();
					//Boucle qui vérifie que le nom n'est pas vide
					while("".equals(name)) {
						System.out.println("Le nom ne peut pas être vide");
						System.out.println("Entrer un nouveau nom d'ordinateur : ");
						name = sc.nextLine();
					}
					System.out.println("Entrer la date d'introduction de l'ordinateur : (YYYY-MM-DD HH:MM:SS)");
					introduced = sc.nextLine();
					//Boucle qui vérifie que le format de la date est correct ou vide
					while(!"".equals(introduced) && !introduced.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
						System.out.println("Entrer une date valide selon le format YYYY-MM-DD HH:MM:SS");
						introduced = sc.nextLine();
					}
					System.out.println("Entrer la date d'interruption de l'ordinateur : (YYYY-MM-DD HH:MM:SS)");
					discontinued = sc.nextLine();
					//Boucle qui vérifie que le format de la date est correct ou vide
					while(!"".equals(discontinued) && !discontinued.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
						System.out.println("Entrer une date valide selon le format YYYY-MM-DD HH:MM:SS");
						discontinued = sc.nextLine();
					}
					//Vérifie que si la date d'introduction est entrée, celle-ci est antérieur à la date d'interruption
					if (!"".equals(introduced) && !"".equals(discontinued)) {
						while(!discontinued.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$") || Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))) {
							System.out.println("La date d'interruption doit être postérieure à la date d'introduction et de la forme YYYY-MM-DD HH:MM:SS");
							System.out.println("Entrer une nouvelle date d'introduction : ");
							discontinued = sc.nextLine();
						}		
					}
					System.out.println("Entrer l'id de la companie : ");
					companyId = sc.nextLine();
					//Boucle qui vérifie que l'id companie est un entier ou vide
					while(!"".equals(companyId) && !companyId.matches("^\\d+$")) {
						System.out.println("Entrer un entier valide : ");
						companyId = sc.nextLine();
					}
					factory.createComputer(name, introduced, discontinued, companyId);
					System.out.print("-> ");
				    logger.info("createComputer");
					break;
				case "updateComputer" :
					System.out.println("Entrer l'id de l'ordinateur à modifier : ");
					id = sc.nextLine();
					//Boucle qui vérifie que l'id est un entier et non vide
					while("".equals(id) || !id.matches("^\\d+$")) {
						System.out.println("L'id doit être un entier et non vide");
						System.out.println("Entrer un nouvelle id d'ordinateur : ");
						id = sc.nextLine();
					}
					System.out.println("Entrer le nouveau nom : ");
					name = sc.nextLine();
					//Boucle qui vérifie que le nom n'est pas vide
					while("".equals(name)) {
						System.out.println("Le nom ne peut pas être vide");
						System.out.println("Entrer un nouveau nom d'ordinateur : ");
						name = sc.nextLine();
					}
					champs.add("name");
					System.out.println("Entrer la nouvelle date d'introduction : (YYYY-MM-DD HH:MM:SS)");
					introduced = sc.nextLine();
					//Boucle qui vérifie que le format de la date est correct ou vide
					while(!"".equals(introduced) && !introduced.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
						System.out.println("Entrer une date valide selon le format YYYY-MM-DD HH:MM:SS");
						introduced = sc.nextLine();
					}
					if (!"".equals(introduced)) {
						champs.add("introduced");
					}
					System.out.println("Entrer la nouvelle date d'interruption : (YYYY-MM-DD HH:MM:SS)");
					discontinued = sc.nextLine();
					//Boucle qui vérifie que le format de la date est correct ou vide
					while(!"".equals(discontinued) && !discontinued.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
						System.out.println("Entrer une date valide selon le format YYYY-MM-DD HH:MM:SS");
						discontinued = sc.nextLine();
					}
					//Vérifie que si la date d'introduction est entrée, celle-ci est antérieur à la date d'interruption
					if (!"".equals(introduced) && !"".equals(discontinued)) {
						while(!discontinued.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$") || Timestamp.valueOf(introduced).after(Timestamp.valueOf(discontinued))) {
							System.out.println("La date d'interruption doit être postérieure à la date d'introduction et de la forme YYYY-MM-DD HH:MM:SS");
							System.out.println("Entrer une nouvelle date d'introduction : ");
							discontinued = sc.nextLine();
						}		
					}
					if (!discontinued.equals("")) {
						champs.add("discontinued");
					}
					//Boucle qui vérifie que l'id companie est un entier ou vide
					System.out.println("Entrer le nouveau company_id : ");
					companyId = sc.nextLine();
					while(!"".equals(companyId) && !companyId.matches("^\\d+$")) {
						System.out.println("L'id companie doit être un entier ou vide");
						System.out.println("Entrer un nouvelle id companie d'ordinateur : ");
						companyId = sc.nextLine();
					}
					if (!"".equals(companyId)) {
						champs.add("company_id");
					}
					if (champs.size() > 0) {
						factory.updateComputer(id, name, introduced, discontinued, companyId, champs);
					}
					System.out.print("-> ");
				    logger.info("updateComputer");
					break;
				case "deleteComputer" :
					System.out.println("Entrer l'id de la companie à supprimer : ");
					id = sc.nextLine();
					//Boucle qui vérifie que l'id est un entier ou vide
					while(!"".equals(id) && !id.matches("^\\d+$")) {
						System.out.println("L'id doit être un entier ou vide");
						System.out.println("Entrer un nouvelle id d'ordinateur : ");
						id = sc.nextLine();
					}
					if (!id.equals("")) {
						champs.add("id");
					}
					System.out.println("Entrer le nom de la companie à supprimer : ");
					name = sc.nextLine();
					if (!name.equals("")) {
						champs.add("name");
					}
					System.out.println("Entrer la date d'introduction de la companie à supprimer : (YYYY-MM-DD HH:MM:SS)");
					introduced = sc.nextLine();
					//Boucle qui vérifie que le format de la date est correct ou vide
					while(!"".equals(introduced) && !introduced.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
						System.out.println("Entrer une date valide selon le format YYYY-MM-DD HH:MM:SS ou laisser le champ vide");
						introduced = sc.nextLine();
					}
					if (!introduced.equals("")) {
						champs.add("introduced");
					}
					System.out.println("Entrer la date d'interruption de la companie à supprimer : (YYYY-MM-DD HH:MM:SS)");
					discontinued = sc.nextLine();
					//Boucle qui vérifie que le format de la date est correct ou vide
					while(!"".equals(discontinued) && !discontinued.matches("^\\d{4}-(0\\d|1[0-2])-(0\\d|[1-2]\\d|3[0-1])\\s[0-5]\\d:[0-5]\\d:[0-5]\\d$")) {
						System.out.println("Entrer une date valide selon le format YYYY-MM-DD HH:MM:SS ou laisser le champ vide");
						discontinued = sc.nextLine();
					}
					if (!discontinued.equals("")) {
						champs.add("discontinued");
					}
					System.out.println("Entrer le company_id de la companie à supprimer : ");
					companyId = sc.nextLine();
					//Boucle qui vérifie que l'id company est un entier ou vide
					while(!"".equals(companyId) && !companyId.matches("^\\d+$")) {
						System.out.println("L'id companie doit être un entier ou vide");
						System.out.println("Entrer un nouvelle id companie d'ordinateur : ");
						companyId = sc.nextLine();
					}
					if (!companyId.equals("")) {
						champs.add("company_id");
					}
					if (champs.size() > 0) {
						factory.deleteComputer(id, name, introduced, discontinued, companyId, champs);
					}
					System.out.print("-> ");
				    logger.info("deleteComputer");
					break;
				case "aide" :
					System.out.println("Liste des commandes :");
					System.out.println(" - listComputers : liste les ordinateurs");
					System.out.println(" - showComputerDetails : affiche les details d'un ordinateur");
					System.out.println(" - createComputer : ajoute un ordinateur dans la base de données");
					System.out.println(" - updateComputer : met à jour les informations d'un ordinateur");
					System.out.println(" - deleteComputer : supprime un ordinateur de la base de données");
					System.out.println(" - aide : réafficher les commandes");
					System.out.println(" - retour : retour au menu précédent");
					System.out.print("-> ");
					break;
				case "retour" :
					System.out.println("retour");
					break retour;
				default :
					System.out.println("Commande inconnue");
					System.out.print("-> ");
					break;
			}
		}
	}
	
	/**
	 * Gère la séléction des champs.
	 * @param factory la factory qui gère la dao
	 * @param sc scanner pour les entrées de l'utilisateur
	 * @throws SQLException
	 */
	private void champsEntries(Logger logger, DAOFactory factory, Scanner sc) throws SQLException {
		// Demande à l'utilisateur quels champs seront affichés
		if ("class dao.CompanyFactory".equals(factory.getClass().toString())) {
			System.out.println("Lister l'id? (y/n)");
			String id = sc.nextLine();
			System.out.println("Lister le nom? (y/n)");	
			String name = sc.nextLine();
			ArrayList<String> champs = new ArrayList<String>();
			if ("y".equals(id.toLowerCase())) {
				champs.add("id");
			}
			if ("y".equals(name.toLowerCase())) {
				champs.add("name");
			}
			if (champs.size() > 0) {
				((CompanyFactory)factory).listCompanies(champs);
			}
		}
		if ("class dao.ComputerFactory".equals(factory.getClass().toString())) {
			System.out.println("Lister l'id? (y/n)");
			String id = sc.nextLine();
			System.out.println("Lister le nom? (y/n)");	
			String name = sc.nextLine();
			System.out.println("Lister l'introduced? (y/n)");
			String introduced = sc.nextLine();
			System.out.println("Lister le discontinued? (y/n)");	
			String discontinued = sc.nextLine();
			System.out.println("Lister l'id company? (y/n)");	
			String companyId = sc.nextLine();
			ArrayList<String> champs = new ArrayList<String>();
			if (id.toLowerCase().equals("y")) {
				champs.add("id");
			}
			if (name.toLowerCase().equals("y")) {
				champs.add("name");
			}
			if (introduced.toLowerCase().equals("y")) {
				champs.add("introduced");
			}
			if (discontinued.toLowerCase().equals("y")) {
				champs.add("discontinued");
			}
			if (companyId.toLowerCase().equals("y")) {
				champs.add("company_id");
			}
			if (champs.size() > 0) {
				((ComputerFactory)factory).listComputers(champs);
			}
		}
	}

	public static void main(String[] args) throws SQLException, IOException {
		Scanner sc = new Scanner(System.in);
		Interface inter = new Interface();
	    Logger logger = LoggerFactory.getLogger(Interface.class);
	    inter.showInterface(logger, sc);
		sc.close();
	}
}
