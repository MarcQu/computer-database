package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zaxxer.hikari.HikariDataSource;

import mapper.CompanyMapper;
import model.Company;

@Repository
public class CompanyDAO {
  private static final String COUNT_ALL = "SELECT COUNT(id) AS rowcount FROM company";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM company WHERE name like :search";
  private static final String SHOW = "SELECT id, name FROM company WHERE id = ?";
  private static final String CREATE = "INSERT INTO company(name) values(?)";
  private static final String UPDATE = "UPDATE company SET name = ? WHERE id = ?";
  private static final String LIST_ASC = "SELECT id, name FROM company ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String LIST_DESC = "SELECT id, name FROM company ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String SEARCH_ASC = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String SEARCH_DESC = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String LIST_ALL = "SELECT id, name FROM company";
  private static final String DELETE = "DELETE FROM company WHERE id = ?";
  private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";
  private Logger logger;
  private CompanyMapper mapper;
  private HikariDataSource dataSource;

  /**
   * CompanyFactory contient les méthodes spécifiques de la table company.
   * @param mapper le mapper de company
   * @param dataSource la dataSource
   * @throws SQLException SQLException
   */
  public CompanyDAO(CompanyMapper mapper, HikariDataSource dataSource) throws SQLException {
    this.logger = LoggerFactory.getLogger(CompanyDAO.class);
    this.mapper = mapper;
    this.dataSource = dataSource;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int count(String search) throws SQLException {
    NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(this.dataSource);
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
  public List<Company> list(int nombre, int offset, String search, String sort) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(this.dataSource);
    List<Company> companies = new ArrayList<>();

    if (search == null || "".equals(search)) {
      if ("desc".equals(sort)) {
        companies = template.query(LIST_DESC, new Object[] {nombre, offset}, this.mapper);
      } else {
        companies = template.query(LIST_ASC, new Object[] {nombre, offset}, this.mapper);
      }
    } else {
      if ("desc".equals(sort)) {
        companies = template.query(SEARCH_DESC, new Object[] {new StringBuilder("%").append(search).append("%").toString(), nombre, offset}, this.mapper);
      } else {
        companies = template.query(SEARCH_ASC, new Object[] {new StringBuilder("%").append(search).append("%").toString(), nombre, offset}, this.mapper);
      }
    }
    return companies;
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> listAll() throws SQLException {
    JdbcTemplate template = new JdbcTemplate(this.dataSource);
    List<Company> companies = template.query(LIST_ALL, this.mapper);
    return companies;
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param company la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> showDetails(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(this.dataSource);
    return template.query(SHOW, new Object[] {company.getId()}, this.mapper);
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param company la compagnie à ajouter
   * @throws SQLException             SQLException
   */
  public void create(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(this.dataSource);
    template.update(CREATE, company.getName());
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param company la compagnie à mettre à jour
   * @throws SQLException SQLException
   */
  public void update(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(this.dataSource);
    template.update(UPDATE, company.getName(), company.getId());
  }

  /**
   * Supprime une companie de la table company.
   * @param company la compagnie à supprimer
   * @throws SQLException SQLException
   */
  @Transactional
  public void delete(Company company) throws SQLException {
    JdbcTemplate template = new JdbcTemplate(this.dataSource);
    template.update(DELETE_COMPUTERS, company.getId());
    template.update(DELETE, company.getId());
  }
}
