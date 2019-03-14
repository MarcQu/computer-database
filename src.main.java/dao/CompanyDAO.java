package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zaxxer.hikari.HikariDataSource;

import mapper.CompanyMapper;
import model.Company;

@Component
public class CompanyDAO {
  private static CompanyDAO instance = null;
  private static final String COUNT_ALL = "SELECT COUNT(id) AS rowcount FROM company";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM company WHERE name like :search";
  private static final String SHOW = "SELECT id, name FROM company WHERE id = ?";
  private static final String CREATE = "INSERT INTO company(name) values(?)";
  private static final String LIST_ASC = "SELECT id, name FROM company ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String LIST_DESC = "SELECT id, name FROM company ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String SEARCH_ASC = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String SEARCH_DESC = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String LIST_ALL = "SELECT id, name FROM company";
  private static final String DELETE = "DELETE FROM company WHERE id = ?";
  private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";
  private Logger logger;
  @Autowired
  private CompanyMapper mapper;

  @Autowired
  private HikariDataSource dataSource;

  /**
   * CompanyFactory contient les méthodes spécifiques de la table company.
   * @throws SQLException SQLException
   */
  private CompanyDAO() throws SQLException {
    this.logger = LoggerFactory.getLogger(CompanyDAO.class);
  }

  /**
   * Méthode qui retourne l'instance unique de la classe CompanyFactory.
   * @return l'instance de la classe CompanyFactory
   * @throws SQLException SQLException
   */
  public static CompanyDAO getInstance() throws SQLException {
    if (CompanyDAO.instance == null) {
      CompanyDAO.instance = new CompanyDAO();
    }
    return CompanyDAO.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countCompanies(String search) throws SQLException {
    NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
    MapSqlParameterSource params = new MapSqlParameterSource();
    int nombre = 0;
    if (search == null || "".equals(search)) {
      nombre = template.queryForObject(COUNT_ALL, params, Integer.class);
    } else {
      params.addValue("search", new StringBuilder("%").append(search).append("%").toString());
      nombre = template.queryForObject(COUNT, params, Integer.class);
    }
    return nombre;
  }

  /**
   * Liste les companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> listCompanies(int nombre, int offset, String search, String sort) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    List<Company> companies = new ArrayList<>();

    if (search == null || "".equals(search)) {
      if ("desc".equals(sort)) {
        companies = template.query(LIST_DESC, new Object[] {nombre, offset}, mapper);
      } else {
        companies = template.query(LIST_ASC, new Object[] {nombre, offset}, mapper);
      }
    } else {
      if ("desc".equals(sort)) {
        companies = template.query(SEARCH_DESC, new Object[] {new StringBuilder("%").append(search).append("%").toString(), nombre, offset}, mapper);
      } else {
        companies = template.query(SEARCH_ASC, new Object[] {new StringBuilder("%").append(search).append("%").toString(), nombre, offset}, mapper);
      }
    }
    return companies;
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> listCompaniesAll() throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    List<Company> companies = template.query(LIST_ALL, mapper);
    return companies;
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param company la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> showCompanyDetails(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    return template.query(SHOW, new Object[] {company.getId()}, mapper);
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param company la compagnie à ajouter
   * @throws SQLException             SQLException
   */
  public void createCompany(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    template.update(CREATE, company.getName());
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param company la compagnie à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   */
  public void updateCompany(Company company, ArrayList<String> champs) throws SQLException {
    StringBuilder query = new StringBuilder("UPDATE company SET ");
    for (int i = 0; i < champs.size() - 1; i++) {
      query.append(champs.get(i)).append(" = ?, ");
    }
    query.append(champs.get(champs.size() - 1)).append(" = ? WHERE id = ?");

    JdbcTemplate template = new JdbcTemplate(dataSource);
    template.update(query.toString(), company.getName(), company.getId());
  }

  /**
   * Supprime une companie de la table company.
   * @param company la compagnie à supprimer
   * @throws SQLException SQLException
   */
  @Transactional
  public void deleteCompany(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    template.update(DELETE_COMPUTERS, company.getId());
    template.update(DELETE, company.getId());
  }
}
