package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import model.Company;

public class CompanyFactory implements AutoCloseable {
  private Connection conn;
  private static CompanyFactory instance = null;
  private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B1";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM company";
  /**
   * CompanyFactory contient les méthodes spécifiques à la table company.
   * @throws SQLException SQLException
   */
  private CompanyFactory() throws SQLException {
    this.conn = DAOFactory.getInstance(URL).getConnection();
  }

  /**
   * Méthode qui retourne l'instance unique de la classe CompanyFactory.
   * @return l'instance de la classe CompanyFactory
   * @throws SQLException SQLException
   */
  public static CompanyFactory getInstance() throws SQLException {
    if (CompanyFactory.instance == null) {
      CompanyFactory.instance = new CompanyFactory();
    }
    return CompanyFactory.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @return nombre le nombre de ligne
   * @throws SQLException
   */
  public int countCompanies() throws SQLException {
    Statement stmt = this.conn.createStatement();
    ResultSet rs = stmt.executeQuery(COUNT);
    rs.next();
    int nombre = rs.getInt("rowcount");
    return nombre;
  }

  /**
   * Liste les companies contenues dans la table company.
   * @param champs les champs de la table à afficher
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, ArrayList<String> champs)
      throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    Statement stmt = this.conn.createStatement();
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT ";
    for (int i = 0; i < champs.size() - 1; i++) {
      query += champs.get(i) + ", ";
    }
    query += champs.get(champs.size() - 1) + " FROM company LIMIT " + nombre + " OFFSET " + offset;
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
      Company company = new Company();
      for (int i = 0; i < champs.size(); i++) {
        if (rs.getString(champs.get(i)) != null) {
          switch (champs.get(i)) {
          case "id":
            company.setId(Integer.parseInt(rs.getString(champs.get(i))));
            break;
          case "name":
            company.setName(rs.getString(champs.get(i)));
            break;
          default:
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
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompaniesAll(ArrayList<String> champs) throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    Statement stmt = this.conn.createStatement();
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT ";
    for (int i = 0; i < champs.size() - 1; i++) {
      query += champs.get(i) + ", ";
    }
    query += champs.get(champs.size() - 1) + " FROM company";
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
      Company company = new Company();
      for (int i = 0; i < champs.size(); i++) {
        if (rs.getString(champs.get(i)) != null) {
          switch (champs.get(i)) {
          case "id":
            company.setId(Integer.parseInt(rs.getString(champs.get(i))));
            break;
          case "name":
            company.setName(rs.getString(champs.get(i)));
            break;
          default:
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
